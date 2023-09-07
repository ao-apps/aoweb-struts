/*
 * aoweb-struts - Template webapp for legacy Struts-based site framework with AOServ Platform control panels.
 * Copyright (C) 2009-2013, 2016, 2017, 2018, 2019, 2020, 2021, 2022, 2023  AO Industries, Inc.
 *     support@aoindustries.com
 *     7262 Bull Pen Cir
 *     Mobile, AL 36695
 *
 * This file is part of aoweb-struts.
 *
 * aoweb-struts is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * aoweb-struts is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with aoweb-struts.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.aoindustries.web.struts.clientarea.control.vnc;

import com.aoapps.hodgepodge.io.AOPool;
import com.aoapps.hodgepodge.io.stream.StreamableInput;
import com.aoapps.hodgepodge.io.stream.StreamableOutput;
import com.aoapps.net.InetAddress;
import com.aoindustries.aoserv.client.AoservClientConfiguration;
import com.aoindustries.aoserv.client.AoservConnector;
import com.aoindustries.aoserv.client.infrastructure.VirtualServer;
import com.aoindustries.aoserv.client.linux.Server;
import com.aoindustries.aoserv.client.schema.AoservProtocol;
import com.aoindustries.aoserv.daemon.client.AoservDaemonConnection;
import com.aoindustries.aoserv.daemon.client.AoservDaemonConnector;
import com.aoindustries.aoserv.daemon.client.AoservDaemonProtocol;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLHandshakeException;
import javax.servlet.ServletContext;

/**
 * Handles a single connection.
 *
 * @author  AO Industries, Inc.
 */
// Matches VncConsoleTunnel
public class VncConsoleProxySocketHandler {

  private static final Logger logger = Logger.getLogger(VncConsoleProxySocketHandler.class.getName());

  static final byte[] protocolVersion_3_3 = {
      // "RFB 003.003\n"
      (byte) 'R',
      (byte) 'F',
      (byte) 'B',
      (byte) ' ',
      (byte) '0',
      (byte) '0',
      (byte) '3',
      (byte) '.',
      (byte) '0',
      (byte) '0',
      (byte) '3',
      (byte) '\n'
  };
  static final byte[] protocolVersion_3_5 = {
      // "RFB 003.005\n"
      (byte) 'R',
      (byte) 'F',
      (byte) 'B',
      (byte) ' ',
      (byte) '0',
      (byte) '0',
      (byte) '3',
      (byte) '.',
      (byte) '0',
      (byte) '0',
      (byte) '5',
      (byte) '\n'
  };
  static final byte[] protocolVersion_3_8 = {
      // "RFB 003.008\n"
      (byte) 'R',
      (byte) 'F',
      (byte) 'B',
      (byte) ' ',
      (byte) '0',
      (byte) '0',
      (byte) '3',
      (byte) '.',
      (byte) '0',
      (byte) '0',
      (byte) '8',
      (byte) '\n'
  };

  @SuppressWarnings({"UseSpecificCatch", "TooBroadCatch", "UtilityClassWithPublicConstructor", "CallToThreadStartDuringObjectConstruction"})
  public VncConsoleProxySocketHandler(final ServletContext servletContext, final AoservConnector rootConn, final Socket socket) {
    // This thread will read from socket
    Thread thread = new Thread(
        () -> {
          try {
            final OutputStream socketOut = socket.getOutputStream();
            final InputStream socketIn = socket.getInputStream();

            // Find which server is being connected to based on the authentication information
            try {
              // Protocol Version handshake
              socketOut.write(protocolVersion_3_3);
              socketOut.flush();
              for (int c = 0; c < protocolVersion_3_3.length; c++) {
                int b = socketIn.read();
                if (b == -1) {
                  throw new EOFException("EOF from socketIn");
                }
                if (
                    protocolVersion_3_3[c] != b
                        && protocolVersion_3_5[c] != b // Accept 3.5 but treat as 3.3
                        && protocolVersion_3_8[c] != b // Accept 3.8 but treat as 3.3
                ) {
                  throw new IOException("Mismatched protocolVersion from VNC client through socket: #" + c + ": " + (char) b);
                }
              }
            } catch (SSLHandshakeException err) {
              String message = err.getMessage();
              Level level;
              if (
                  // Do not routinely log messages that are normal due to monitoring simply connecting only
                  !(
                      // Java 11
                      "Remote host terminated the handshake".equals(message)
                          // Java 8
                          || "Remote host closed connection during handshake".equals(message)
                  )
              ) {
                level = Level.SEVERE;
              } else {
                level = Level.FINE;
              }
              logger.log(level, null, err);
              return;
            }
            // Security type
            socketOut.write(0);
            socketOut.write(0);
            socketOut.write(0);
            socketOut.write(2);
            // VNC Authentication
            byte[] challenge = new byte[16];
            AoservConnector.getSecureRandom().nextBytes(challenge);
            socketOut.write(challenge);
            socketOut.flush();
            byte[] response = new byte[16];
            for (int c = 0; c < 16; c++) {
              int b = socketIn.read();
              if (b == -1) {
                throw new EOFException("EOF from socketIn");
              }
              response[c] = (byte) b;
            }
            VirtualServer virtualServer = null;
            for (VirtualServer vs : rootConn.getInfrastructure().getVirtualServer().getRows()) {
              String vncPassword = vs.getVncPassword();
              if (vncPassword != null && !vncPassword.equals(AoservProtocol.FILTERED)) {
                byte[] expectedResponse = desCipher(challenge, vncPassword);
                if (Arrays.equals(response, expectedResponse)) {
                  virtualServer = vs;
                  break;
                }
              }
            }
            if (virtualServer == null) {
              // Virtual Host not found
              logger.warning("Virtual Host not found");
              Thread.sleep(5000);
              socketOut.write(0);
              socketOut.write(0);
              socketOut.write(0);
              socketOut.write(1);
              socketOut.flush();
            } else {
              // Connect and authenticate to the real VNC server before sending security result

              // Connect through AOServ Platform
              Server.DaemonAccess daemonAccess = virtualServer.requestVncConsoleAccess();
              logger.fine("Got daemon access");
              AoservDaemonConnector daemonConnector = AoservDaemonConnector.getConnector(
                  daemonAccess.getHost(),
                  InetAddress.UNSPECIFIED_IPV4,
                  daemonAccess.getPort(),
                  daemonAccess.getProtocol(),
                  null,
                  100,
                  AOPool.DEFAULT_MAX_CONNECTION_AGE,
                  AoservClientConfiguration.getSslTruststorePath(),
                  AoservClientConfiguration.getSslTruststorePassword()
              );
              logger.fine("Got daemon connector");
              try (AoservDaemonConnection daemonConn = daemonConnector.getConnection()) {
                logger.fine("Got daemon connection");
                try {
                  final StreamableOutput daemonOut = daemonConn.getRequestOut(AoservDaemonProtocol.VNC_CONSOLE);
                  daemonOut.writeLong(daemonAccess.getKey());
                  daemonOut.flush();
                  logger.fine("Sent daemon request");

                  final StreamableInput daemonIn = daemonConn.getResponseIn();
                  int result = daemonIn.read();
                  logger.fine("Got daemon result");
                  if (result == AoservDaemonProtocol.NEXT) {
                    // Authenticate to actual VNC
                    // Protocol Version handshake
                    for (int c = 0; c < protocolVersion_3_3.length; c++) {
                      int b = daemonIn.read();
                      if (b == -1) {
                        throw new EOFException("EOF from daemonIn");
                      }
                      if (
                          protocolVersion_3_3[c] != b // Hardware virtualized
                              && protocolVersion_3_8[c] != b // Paravirtualized
                      ) {
                        throw new IOException("Mismatched protocolVersion from VNC server through daemon: #" + c + ": " + (char) b);
                      }
                    }
                    daemonOut.write(protocolVersion_3_3);
                    daemonOut.flush();
                      // Security Type
                      {
                        int securityType1 = daemonIn.read();
                        if (securityType1 == -1) {
                          throw new EOFException("EOF from daemonIn reading securityType1");
                        }
                        int securityType2 = daemonIn.read();
                        if (securityType2 == -1) {
                          throw new EOFException("EOF from daemonIn reading securityType2");
                        }
                        int securityType3 = daemonIn.read();
                        if (securityType3 == -1) {
                          throw new EOFException("EOF from daemonIn reading securityType3");
                        }
                        int securityType4 = daemonIn.read();
                        if (securityType4 == -1) {
                          throw new EOFException("EOF from daemonIn reading securityType4");
                        }
                        if (
                            securityType1 != 0
                                || securityType2 != 0
                                || securityType3 != 0
                                || securityType4 != 2
                        ) {
                          throw new IOException(
                              "Mismatched security type from VNC server through daemon: ("
                                  + securityType1
                                  + ", " + securityType2
                                  + ", " + securityType3
                                  + ", " + securityType4
                                  + ")"
                          );
                        }
                      }
                    // VNC Authentication
                    for (int c = 0; c < 16; c++) {
                      int b = daemonIn.read();
                      if (b == -1) {
                        throw new EOFException("EOF from daemonIn");
                      }
                      challenge[c] = (byte) b;
                    }
                    response = desCipher(challenge, virtualServer.getVncPassword());
                    daemonOut.write(response);
                    daemonOut.flush();
                    if (
                        daemonIn.read() != 0
                            || daemonIn.read() != 0
                            || daemonIn.read() != 0
                            || daemonIn.read() != 0
                    ) {
                      Thread.sleep(5000);
                      socketOut.write(0);
                      socketOut.write(0);
                      socketOut.write(0);
                      socketOut.write(1);
                      socketOut.flush();
                      throw new IOException("Authentication to real VNC server failed");
                    }
                    socketOut.write(0);
                    socketOut.write(0);
                    socketOut.write(0);
                    socketOut.write(0);

                    // socketIn -> daemonOut in another thread
                    Thread inThread = new Thread(
                        () -> {
                          try {
                            try {
                              byte[] buff = new byte[4096];
                              int ret;
                              while ((ret = socketIn.read(buff, 0, 4096)) != -1) {
                                daemonOut.write(buff, 0, ret);
                                daemonOut.flush();
                              }
                            } finally {
                              // Always close after VNC tunnel since this is a connection-terminal command
                              daemonConn.abort();
                            }
                          } catch (ThreadDeath td) {
                            throw td;
                          } catch (Throwable t) {
                            logger.log(Level.SEVERE, null, t);
                          }
                        },
                        VncConsoleProxySocketHandler.class.getSimpleName() + ": " + socket.getInetAddress() + ":" + socket.getPort()
                            + " -> " + socket.getLocalAddress() + ":" + socket.getLocalPort() + ", socketIn -> daemonOut: " + virtualServer.getHost().getName()
                    );
                    inThread.setDaemon(true); // Don't prevent JVM shutdown
                    inThread.setPriority(Thread.NORM_PRIORITY + 2); // Higher priority for higher performance
                    inThread.start();

                    // daemonIn -> socketOut in this thread
                    byte[] buff = new byte[4096];
                    int ret;
                    while ((ret = daemonIn.read(buff, 0, 4096)) != -1) {
                      socketOut.write(buff, 0, ret);
                      socketOut.flush();
                    }
                  } else {
                    if (result == AoservDaemonProtocol.IO_EXCEPTION) {
                      throw new IOException(daemonIn.readUTF());
                    } else if (result == AoservDaemonProtocol.SQL_EXCEPTION) {
                      throw new SQLException(daemonIn.readUTF());
                    } else if (result == -1) {
                      throw new EOFException("EOF from daemonIn");
                    } else {
                      throw new IOException("Unknown result: " + result);
                    }
                  }
                } finally {
                  // Always close after VNC tunnel since this is a connection-terminal command
                  daemonConn.abort();
                }
              }
            }
          } catch (SocketException err) {
            // Do not log any socket exceptions
          } catch (ThreadDeath td) {
            throw td;
          } catch (InterruptedException e) {
            logger.log(Level.SEVERE, null, e);
            // Restore the interrupted status
            Thread.currentThread().interrupt();
          } catch (Throwable t) {
            logger.log(Level.SEVERE, null, t);
          } finally {
            try {
              socket.close();
            } catch (IOException err) {
              logger.log(Level.INFO, null, err);
            }
          }
        },
        VncConsoleProxySocketHandler.class.getSimpleName() + ": " + socket.getInetAddress() + ":" + socket.getPort()
            + " -> " + socket.getLocalAddress() + ":" + socket.getLocalPort() + ", daemonIn -> socketOut"
    );
    thread.setDaemon(true); // Don't prevent JVM shutdown
    thread.setPriority(Thread.NORM_PRIORITY + 2); // Higher priority for higher performance
    thread.start();
  }

  /**
   * This is the same as AuthPanel.java
   */
  public static byte[] desCipher(byte[] challenge, String password) {
    if (password.length() > 8) {
      password = password.substring(0, 8);
    } // Truncate to 8 chars
    // vncEncryptBytes in the UNIX libvncauth truncates password
    // after the first zero byte. We do to.
    int firstZero = password.indexOf(0);
    if (firstZero != -1) {
      password = password.substring(0, firstZero);
    }
    byte[] key = {0, 0, 0, 0, 0, 0, 0, 0};
    System.arraycopy(password.getBytes(), 0, key, 0, password.length());
    byte[] response = new byte[16];
    System.arraycopy(challenge, 0, response, 0, 16);
    DesCipher des = new DesCipher(key);
    des.encrypt(response, 0, response, 0);
    des.encrypt(response, 8, response, 8);
    return response;
  }
}
