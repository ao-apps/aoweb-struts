package com.aoindustries.website.clientarea.control.vnc;

/*
 * Copyright 2009 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
 * All rights reserved.
 */
import com.aoindustries.aoserv.client.AOServClientConfiguration;
import com.aoindustries.aoserv.client.AOServConnector;
import com.aoindustries.aoserv.client.AOServer;
import com.aoindustries.aoserv.client.IPAddress;
import com.aoindustries.aoserv.client.Server;
import com.aoindustries.aoserv.client.VirtualServer;
import com.aoindustries.aoserv.daemon.client.AOServDaemonConnection;
import com.aoindustries.aoserv.daemon.client.AOServDaemonConnector;
import com.aoindustries.aoserv.daemon.client.AOServDaemonProtocol;
import com.aoindustries.io.AOPool;
import com.aoindustries.io.CompressedDataInputStream;
import com.aoindustries.io.CompressedDataOutputStream;
import com.aoindustries.website.LogFactory;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.util.logging.Level;
import javax.servlet.ServletContext;

/**
 * Handles a single connection.
 *
 * @author  AO Industries, Inc.
 */
public class VncConsoleProxySocketHandler {

    public VncConsoleProxySocketHandler(final ServletContext servletContext, final AOServConnector rootConn, final Socket socket) {
        // This thread will read from socket
        Thread thread = new Thread(
            new Runnable() {
                public void run() {
                    try {
                        // TODO: Find which server is being connected to based on the authentication information
                        // TODO: This is just a hard-coded default for now
                        Server server = rootConn.getServers().get("AOINDUSTRIES/noc.hotairnetwork.net");
                        if(server==null) throw new SQLException("Unable to find Server: AOINDUSTRIES/noc.hotairnetwork.net");
                        VirtualServer virtualServer = server.getVirtualServer();
                        if(virtualServer==null) throw new SQLException("Unable to find VirtualServer: AOINDUSTRIES/noc.hotairnetwork.net");
                        // Connect through AOServ system
                        AOServer.DaemonAccess daemonAccess = virtualServer.requestVncConsoleAccess();
                        AOServDaemonConnector daemonConnector=AOServDaemonConnector.getConnector(
                            daemonAccess.getHost(),
                            IPAddress.WILDCARD_IP,
                            daemonAccess.getPort(),
                            daemonAccess.getProtocol(),
                            null,
                            100,
                            AOPool.DEFAULT_MAX_CONNECTION_AGE,
                            AOServClientConfiguration.getSslTruststorePath(),
                            AOServClientConfiguration.getSslTruststorePassword(),
                            LogFactory.getLogger(servletContext, getClass())
                        );
                        final AOServDaemonConnection daemonConn=daemonConnector.getConnection();
                        try {
                            final CompressedDataOutputStream daemonOut = daemonConn.getOutputStream();
                            daemonOut.writeCompressedInt(AOServDaemonProtocol.VNC_CONSOLE);
                            daemonOut.writeLong(daemonAccess.getKey());
                            daemonOut.flush();

                            final CompressedDataInputStream daemonIn = daemonConn.getInputStream();
                            int result=daemonIn.read();
                            if(result==AOServDaemonProtocol.NEXT) {
                                final OutputStream socketOut = socket.getOutputStream();
                                final InputStream socketIn = socket.getInputStream();
                                // socketIn -> daemonOut in another thread
                                Thread inThread = new Thread(
                                    new Runnable() {
                                        public void run() {
                                            try {
                                                try {
                                                    byte[] buff = new byte[4096];
                                                    int ret;
                                                    while((ret=socketIn.read(buff, 0, 4096))!=-1) {
                                                        daemonOut.write(buff, 0, ret);
                                                        daemonOut.flush();
                                                    }
                                                } finally {
                                                    daemonConn.close();
                                                }
                                            } catch(ThreadDeath TD) {
                                                throw TD;
                                            } catch(Throwable T) {
                                                LogFactory.getLogger(servletContext, getClass()).log(Level.SEVERE, null, T);
                                            }
                                        }
                                    },
                                    "VncConsoleProxySocketHandler socketIn->daemonOut"
                                );
                                inThread.setDaemon(true); // Don't prevent JVM shutdown
                                inThread.setPriority(Thread.NORM_PRIORITY+2); // Higher priority for higher performance
                                inThread.start();

                                // daemonIn -> socketOut in this thread
                                byte[] buff = new byte[4096];
                                int ret;
                                while((ret=daemonIn.read(buff, 0, 4096))!=-1) {
                                    socketOut.write(buff, 0, ret);
                                    socketOut.flush();
                                }
                            } else {
                                if (result == AOServDaemonProtocol.IO_EXCEPTION) throw new IOException(daemonIn.readUTF());
                                else if (result == AOServDaemonProtocol.SQL_EXCEPTION) throw new SQLException(daemonIn.readUTF());
                                else if (result==-1) throw new EOFException();
                                else throw new IOException("Unknown result: " + result);
                            }
                        } finally {
                            daemonConn.close(); // Always close after VNC tunnel
                            daemonConnector.releaseConnection(daemonConn);
                        }
                    } catch(ThreadDeath TD) {
                        throw TD;
                    } catch(Throwable T) {
                        LogFactory.getLogger(servletContext, getClass()).log(Level.SEVERE, null, T);
                    } finally {
                        try {
                            socket.close();
                        } catch(IOException err) {
                            LogFactory.getLogger(servletContext, getClass()).log(Level.INFO, null, err);
                        }
                    }
                }
            },
            "VncConsoleProxySocketHandler daemonIn->socketOut"
        );
        thread.setDaemon(true); // Don't prevent JVM shutdown
        thread.setPriority(Thread.NORM_PRIORITY+2); // Higher priority for higher performance
        thread.start();
    }
}
