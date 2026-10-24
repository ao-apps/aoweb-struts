/*
 * aoweb-struts - Template webapp for legacy Struts-based site framework with AOServ Platform control panels.
 * Copyright (C) 2021, 2022, 2023, 2025, 2026  AO Industries, Inc.
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

import static com.aoindustries.web.struts.clientarea.control.vnc.VncConsoleProxySocketHandler.desCipher;
import static com.aoindustries.web.struts.clientarea.control.vnc.VncConsoleProxySocketHandler.protocolVersion_3_3;
import static com.aoindustries.web.struts.clientarea.control.vnc.VncConsoleProxySocketHandler.protocolVersion_3_5;
import static com.aoindustries.web.struts.clientarea.control.vnc.VncConsoleProxySocketHandler.protocolVersion_3_8;

import com.aoapps.hodgepodge.io.AOPool;
import com.aoapps.hodgepodge.io.stream.StreamableInput;
import com.aoapps.hodgepodge.io.stream.StreamableOutput;
import com.aoapps.hodgepodge.net.IpReputation;
import com.aoapps.lang.Throwables;
import com.aoapps.net.InetAddress;
import com.aoindustries.aoserv.client.AoservClientConfiguration;
import com.aoindustries.aoserv.client.AoservConnector;
import com.aoindustries.aoserv.client.infrastructure.VirtualServer;
import com.aoindustries.aoserv.client.linux.Server;
import com.aoindustries.aoserv.client.schema.AoservProtocol;
import com.aoindustries.aoserv.daemon.client.AoservDaemonConnection;
import com.aoindustries.aoserv.daemon.client.AoservDaemonConnector;
import com.aoindustries.aoserv.daemon.client.AoservDaemonProtocol;
import com.aoindustries.web.struts.AowebExecutors;
import com.aoindustries.web.struts.SiteSettings;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletRequestEvent;
import jakarta.servlet.ServletRequestListener;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.websocket.CloseReason;
import jakarta.websocket.HandshakeResponse;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.HandshakeRequest;
import jakarta.websocket.server.ServerEndpoint;
import jakarta.websocket.server.ServerEndpointConfig;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handles websocket connections and proxies through to the
 * behind-the-scenes VNC server.
 *
 * @author  AO Industries, Inc.
 */
@ServerEndpoint(value = "/clientarea/control/vnc/vnc-console-websocket", configurator = VncConsoleProxyWebsocketServer.GetRemoteInetAddressConfigurator.class)
public class VncConsoleProxyWebsocketServer {

  private static final Logger logger = Logger.getLogger(VncConsoleProxyWebsocketServer.class.getName());

  private static final String CLIENT_IP_PROPERTY = VncConsoleProxyWebsocketServer.class.getName() + ".clientIp";

  private static final int MAX_HANDSHAKE_BUFFER_SIZE = 1024; // Added to prevent OOM heap exhaustion

  @WebListener
  public static class GetRemoteInetAddressRequestListener implements ServletRequestListener {

    private static final ThreadLocal<String> clientIp = new ThreadLocal<>();

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
      logger.finer(() -> "Thread at requestInitialized: " + Thread.currentThread().getName());
      ServletRequest req = sre.getServletRequest();
      if (req instanceof HttpServletRequest httpReq) {
        clientIp.set(httpReq.getRemoteAddr());
      } else {
        clientIp.remove();
      }
    }

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
      clientIp.remove();
    }
  }

  public static class GetRemoteInetAddressConfigurator extends ServerEndpointConfig.Configurator {

    @Override
    public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
      logger.finer(() -> "Thread at modifyHandshake...: " + Thread.currentThread().getName());
      String clientIp = GetRemoteInetAddressRequestListener.clientIp.get();
      logger.finer("clientIp = " + clientIp);
      if (clientIp == null || (clientIp = clientIp.trim()).isEmpty()) {
        throw new SecurityException("WebSocket connection rejected: Client IP could not be determined.");
      }
      java.net.InetAddress clientInetAddress;
      try {
        clientInetAddress = java.net.InetAddress.getByName(clientIp);
      } catch (UnknownHostException e) {
        throw new SecurityException("WebSocket connection rejected: Client IP could not be resolved for " + clientIp, e);
      }
      // TODO: To be able to test in NetBeans/Tomcat locally, access the "devMode" from Maven properties, either
      // by adding to all web.xml (which are filtered), or by writing into a Maven.properties file
      if (clientInetAddress.isLoopbackAddress()) {
        // Also allow X-Forwarded-For header when request coming from a proxy through loopback
        final String headerName = "X-Forwarded-For";
        List<String> xforwardedFor = request.getHeaders().get(headerName);
        if (xforwardedFor == null) {
          throw new SecurityException("WebSocket connection rejected: No " + headerName + " header and loopback connection");
        }
        // Verify Apache will not forward (or how it forwards) spoofed x-forward-for headers if ever allowing more than one
        if (xforwardedFor.size() != 1) {
          throw new SecurityException("WebSocket connection rejected: Only one value expected for " + headerName + " header and loopback connection, got " + xforwardedFor);
        }
        String firstHeader = xforwardedFor.get(0);
        logger.finer("firstHeader = " + firstHeader);
        if (firstHeader == null || (firstHeader = firstHeader.trim()).isEmpty()) {
          throw new SecurityException("WebSocket connection rejected: Empty value for " + headerName + " on loopback connection.");
        }
        // Verify Apache will not forward (or how it forwards) spoofed x-forward-for headers if ever allowing more than one separated by commas
        if (firstHeader.indexOf(',') != -1) {
          // Could use first value here if needed (but confirm how Apache orders the header values)
          throw new SecurityException("WebSocket connection rejected: Not allowing comma-separated value for " + headerName + " header and loopback connection, got " + firstHeader);
        }
        try {
          clientInetAddress = java.net.InetAddress.getByName(firstHeader);
        } catch (UnknownHostException e) {
          throw new SecurityException("WebSocket connection rejected: Client IP could not be resolved for " + firstHeader, e);
        }
      }
      sec.getUserProperties().put(CLIENT_IP_PROPERTY, clientInetAddress);
    }
  }

  private static ServletContext servletContext;

  /**
   * Initializes the VNC console proxy websocket server during {@linkplain ServletContextListener application start-up}.
   */
  @WebListener("Initializes the VNC console proxy websocket server during application start-up.")
  public static class Initializer implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
      servletContext = event.getServletContext();
      logger.fine("Got servlet context");
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
      // Do nothing
    }
  }

  public VncConsoleProxyWebsocketServer() {
    logger.fine("Constructed");
  }

  private enum Phase {
    Protocol,
    Auth,
    Proxy,
    Error
  }

  private volatile Phase phase = Phase.Protocol;
  private volatile IpReputation.Connection repConn;
  private volatile Future<Void> protocolFuture;
  private volatile Future<Void> authFuture;
  private volatile Future<Void> proxyFuture;
  private volatile AoservDaemonConnection daemonConn;
  private volatile StreamableInput daemonIn;
  private volatile StreamableOutput daemonOut;
  private volatile Thread outThread;

  @OnOpen
  public void onOpen(Session session) throws IOException {
    java.net.InetAddress clientIp = (java.net.InetAddress) session.getUserProperties().get(CLIENT_IP_PROPERTY);
    if (logger.isLoggable(Level.FINE)) {
      logger.fine(
          "session.id: " + session.getId()
              + ", session.negotiatedSubprotocol: " + session.getNegotiatedSubprotocol()
              + ", session.protocolVersion: " + session.getProtocolVersion()
              + ", clientIp: " + clientIp
      );
    }
    repConn = VncConsoleProxySocketHandler.ipReputation.init(clientIp);
    switch (repConn.accept()) {
      case REJECT -> session.close();
      case ACCEPT -> {
        repConn.started();
        protocolFuture = session.getAsyncRemote().sendBinary(ByteBuffer.wrap(protocolVersion_3_3));
      }
      default -> throw new AssertionError();
    }
  }

  @OnClose
  public void onClose(Session session, CloseReason reason) throws IOException {
    logger.fine("closing session.id: " + session.getId() + ", reason: " + reason);
    StreamableInput myDaemonIn = daemonIn;
    if (myDaemonIn != null) {
      myDaemonIn.close();
    }
    StreamableOutput myDaemonOut = daemonOut;
    if (myDaemonOut != null) {
      myDaemonOut.close();
    }
    AoservDaemonConnection myDaemonConn = daemonConn;
    if (myDaemonConn != null) {
      // Always close after VNC tunnel since this is a connection-terminal command
      daemonConn = null;
      myDaemonConn.abort();
    }
    Thread myOutThread = outThread;
    if (myOutThread != null) {
      outThread = null;
      myOutThread.interrupt();
    }
  }

  private final ByteArrayOutputStream buffer = new ByteArrayOutputStream();

  /**
   * The current challenge, synchronized on "this".
   */
  private byte[] challenge;

  @OnMessage
  @SuppressWarnings({"BroadCatchBlock", "TooBroadCatch", "AssignmentToCatchBlockParameter"})
  public void onMessage(byte[] message, Session session) throws IOException, SQLException, InterruptedException {
    if (phase == Phase.Protocol || phase == Phase.Auth) {
      if (message != null) {
        if (message.length > (MAX_HANDSHAKE_BUFFER_SIZE - buffer.size())) {
          session.close();
          throw new SecurityException("WebSocket handshake rejected: Payload size limits exceeded.");
        }
        buffer.write(message);
        message = null;
      }
    }
    if (phase == Phase.Protocol) {
      if (buffer.size() >= protocolVersion_3_3.length) {
        assert protocolFuture.isDone();
        byte[] bytes = buffer.toByteArray();
        buffer.reset();
        if (bytes.length > protocolVersion_3_3.length) {
          buffer.write(bytes, protocolVersion_3_3.length, bytes.length - protocolVersion_3_3.length);
        }
        for (int c = 0; c < protocolVersion_3_3.length; c++) {
          int b = bytes[c];
          if (
              protocolVersion_3_3[c] != b
                  && protocolVersion_3_5[c] != b // Accept 3.5 but treat as 3.3
                  && protocolVersion_3_8[c] != b // Accept 3.8 but treat as 3.3
          ) {
            throw new IOException("Mismatched protocolVersion from VNC client through socket: #" + c + ": " + (char) b);
          }
        }
        byte[] auth = new byte[4 + 16];
        // Security type
        auth[0] = 0;
        auth[1] = 0;
        auth[2] = 0;
        auth[3] = 2;
        // VNC Authentication
        synchronized (this) {
          assert challenge == null;
          challenge = new byte[16];
          AoservConnector.getSecureRandom().nextBytes(challenge);
          System.arraycopy(challenge, 0, auth, 4, 16);
        }
        authFuture = session.getAsyncRemote().sendBinary(ByteBuffer.wrap(auth));
        phase = Phase.Auth;
      }
    }
    if (phase == Phase.Auth) {
      if (buffer.size() >= 16) {
        assert authFuture.isDone();
        byte[] bytes = buffer.toByteArray();
        buffer.reset();
        if (bytes.length > 16) {
          buffer.write(bytes, 16, bytes.length - 16);
        }
        byte[] response = new byte[16];
        System.arraycopy(bytes, 0, response, 0, 16);
        VirtualServer virtualServer = null;
        if (servletContext == null) {
          throw new IllegalStateException("ServletContext not initialized by " + Initializer.class.getName());
        }
        SiteSettings siteSettings = SiteSettings.getInstance(servletContext);
        AoservConnector rootConn = siteSettings.getRootAoservConnector();
        for (VirtualServer vs : rootConn.getInfrastructure().getVirtualServer().getRows()) {
          String vncPassword = vs.getVncPassword();
          if (vncPassword != null && !vncPassword.equals(AoservProtocol.FILTERED)) {
            byte[] expectedResponse;
            synchronized (this) {
              expectedResponse = desCipher(challenge, vncPassword);
            }
            if (Arrays.equals(response, expectedResponse)) {
              virtualServer = vs;
              break;
            }
          }
        }
        if (virtualServer == null) {
          // Virtual Host not found
          logger.warning("Virtual Host not found");
          repConn.fail();
          Thread.sleep(VncConsoleProxySocketHandler.FAIL_DELAY_MILLIS);
          session.getAsyncRemote().sendBinary(
              ByteBuffer.wrap(new byte[] {0, 0, 0, 1}),
              sendResult -> {
                try {
                  session.close();
                } catch (IOException e) {
                  logger.log(Level.FINE, "Error closing WebSocket session", e);
                }
              }
          );
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
          AoservDaemonConnection myDaemonConn = daemonConnector.getConnection();
          daemonConn = myDaemonConn;
          logger.fine("Got daemon connection");
          daemonOut = myDaemonConn.getRequestOut(AoservDaemonProtocol.VNC_CONSOLE);
          daemonOut.writeLong(daemonAccess.getKey());
          daemonOut.flush();
          logger.fine("Sent daemon request");

          daemonIn = myDaemonConn.getResponseIn();
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
            byte[] daemonChallenge = new byte[16];
            for (int c = 0; c < 16; c++) {
              int b = daemonIn.read();
              if (b == -1) {
                throw new EOFException("EOF from daemonIn");
              }
              daemonChallenge[c] = (byte) b;
            }
            response = desCipher(daemonChallenge, virtualServer.getVncPassword());
            daemonOut.write(response);
            daemonOut.flush();
            if (
                daemonIn.read() != 0
                    || daemonIn.read() != 0
                    || daemonIn.read() != 0
                    || daemonIn.read() != 0
            ) {
              repConn.fail();
              Thread.sleep(VncConsoleProxySocketHandler.FAIL_DELAY_MILLIS);
              session.getAsyncRemote().sendBinary(
                  ByteBuffer.wrap(new byte[] {0, 0, 0, 1}),
                  sendResult -> {
                    try {
                      session.close();
                    } catch (IOException e) {
                      logger.log(Level.FINE, "Error closing WebSocket session", e);
                    }
                  }
              );
              throw new IOException("Authentication to real VNC server failed");
            }
            repConn.success();
            proxyFuture = session.getAsyncRemote().sendBinary(ByteBuffer.wrap(new byte[] {0, 0, 0, 0}));
            // daemonIn -> socketOut in another thread
            assert outThread == null;
            String outThreadName = "VncConsoleProxyWebsocketServer daemonIn → socketOut: " + virtualServer.getHost().getName();
            AowebExecutors.getExecutors(servletContext).getUnbounded().submit(() -> {
              Thread myOutThread = Thread.currentThread();
              outThread = myOutThread;
              String oldOutThreadName = myOutThread.getName();
              try {
                myOutThread.setName(outThreadName);
                try {
                  byte[] buff = new byte[4096];
                  int ret;
                  while ((ret = daemonIn.read(buff, 0, 4096)) != -1) {
                    proxyFuture.get();
                    proxyFuture = session.getAsyncRemote().sendBinary(ByteBuffer.wrap(Arrays.copyOf(buff, ret)));
                  }
                  // Always close after VNC tunnel since this is a connection-terminal command
                  session.close(new CloseReason(CloseReason.CloseCodes.GOING_AWAY, "EOF at daemonIn"));
                } finally {
                  logger.fine("EOF at daemonIn, closing daemonConn");
                  // Always close after VNC tunnel since this is a connection-terminal command
                  myDaemonConn.abort();
                }
              } catch (ThreadDeath td) {
                throw td;
              } catch (Throwable t) {
                try {
                  session.close(new CloseReason(CloseReason.CloseCodes.UNEXPECTED_CONDITION, t.toString()));
                } catch (Error | RuntimeException | IOException e) {
                  t = Throwables.addSuppressed(t, e);
                }
                logger.log(Level.SEVERE, "TODO: message here", t);
              } finally {
                myOutThread.setName(oldOutThreadName);
              }
            });
            phase = Phase.Proxy;
          } else {
            String errMessage = null;
            try {
              if (result == AoservDaemonProtocol.IO_EXCEPTION) {
                throw new IOException(errMessage = daemonIn.readUTF());
              } else if (result == AoservDaemonProtocol.SQL_EXCEPTION) {
                throw new SQLException(errMessage = daemonIn.readUTF());
              } else if (result == -1) {
                throw new EOFException(errMessage = "EOF from daemonIn");
              } else {
                throw new IOException(errMessage = "Unknown result: " + result);
              }
            } finally {
              session.close(new CloseReason(CloseReason.CloseCodes.UNEXPECTED_CONDITION, errMessage));
            }
          }
        }
      }
    }
    if (phase == Phase.Proxy) {
      // socketIn -> daemonOut in this thread
      boolean doFlush = false;
      if (buffer.size() > 0) {
        buffer.writeTo(daemonOut);
        buffer.reset();
        doFlush = true;
      }
      if (message != null && message.length > 0) {
        daemonOut.write(message);
        doFlush = true;
      }
      if (doFlush) {
        daemonOut.flush();
      }
    }
  }

  @OnError
  public void onError(Session session, Throwable t) throws IOException {
    phase = Phase.Error;
    try {
      logger.log(Level.SEVERE, "TODO: message here", t);
    } finally {
      session.close();
    }
  }
}
