package com.aoindustries.website.clientarea.control.monitor;

/*
 * Copyright 2000-2007 by AO Industries, Inc.,
 * 816 Azalea Rd, Mobile, Alabama, 36693, U.S.A.
 * All rights reserved.
 */
import com.aoindustries.aoserv.client.AOServConnector;
import com.aoindustries.aoserv.client.AOServPermission;
import com.aoindustries.aoserv.client.AOServer;
import com.aoindustries.aoserv.client.FailoverMySQLReplication;
import com.aoindustries.aoserv.client.MySQLServer;
import com.aoindustries.util.ErrorPrinter;
import com.aoindustries.website.PermissionAction;
import com.aoindustries.website.Skin;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

/**
 * Retrives the mysql slave statuses.
 *
 * @author  AO Industries, Inc.
 */
public class MySQLReplicationMonitorAction extends PermissionAction {

    private static final int ERROR_SECONDS_BEHIND = 10;

    public ActionForward executePermissionGranted(
        ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response,
        Locale locale,
        Skin skin,
        AOServConnector aoConn
    ) throws Exception {
        MessageResources controlApplicationResources = (MessageResources)request.getAttribute("/clientarea/control/ApplicationResources");
        if(controlApplicationResources==null) throw new JspException("Unable to load resources: /clientarea/control/ApplicationResources");

        List<MySQLServerRow> mysqlServerRows = new ArrayList<MySQLServerRow>();
        List<MySQLServer> mysqlServers = aoConn.mysqlServers.getRows();
        for(MySQLServer mysqlServer : mysqlServers) {
            StringBuilder name = new StringBuilder();
            AOServer aoServer = mysqlServer.getAOServer();
            AOServer failoverServer = aoServer.getFailoverServer();

            StringBuilder server = new StringBuilder();
            server.append(aoServer.getServer().getHostname());
            if(failoverServer!=null) server.append(" on ").append(failoverServer.getServer().getHostname());

            List<FailoverMySQLReplication> fmrs = mysqlServer.getFailoverMySQLReplications();
            if(!fmrs.isEmpty()) {
                MySQLServerRow mysqlServerRow = new MySQLServerRow(mysqlServer.getVersion().getVersion(), server.toString());
                mysqlServerRows.add(mysqlServerRow);
                for(FailoverMySQLReplication fmr : fmrs) {
                    String slave = fmr.getFailoverFileReplication().getToAOServer().getServer().getHostname();
                    try {
                        FailoverMySQLReplication.SlaveStatus slaveStatus = fmr.getSlaveStatus();
                        if(slaveStatus==null) {
                            mysqlServerRow.replications.add(
                                new ReplicationRow(
                                    true,
                                    slave,
                                    controlApplicationResources.getMessage("monitor.mysqlReplicationMonitor.slaveNotRunning")
                                )
                            );
                            mysqlServerRow.error = true;
                        } else {
                            String secondsBehindMaster = slaveStatus.getSecondsBehindMaster();
                            boolean error;
                            if(secondsBehindMaster==null) error = true;
                            else {
                                try {
                                    int secondsBehind = Integer.parseInt(secondsBehindMaster);
                                    error = secondsBehind > ERROR_SECONDS_BEHIND;
                                } catch(NumberFormatException err) {
                                    error = true;
                                }
                            }
                            mysqlServerRow.replications.add(
                                new ReplicationRow(
                                    error,
                                    slave,
                                    slaveStatus.getSlaveIOState(),
                                    slaveStatus.getMasterLogFile(),
                                    slaveStatus.getReadMasterLogPos(),
                                    slaveStatus.getSlaveIORunning(),
                                    slaveStatus.getSlaveSQLRunning(),
                                    slaveStatus.getLastErrno(),
                                    slaveStatus.getLastError(),
                                    secondsBehindMaster
                                )
                            );
                            if(error) mysqlServerRow.error = true;
                        }
                    } catch(SQLException err) {
                        mysqlServerRow.replications.add(
                            new ReplicationRow(
                                true,
                                slave,
                                controlApplicationResources.getMessage("monitor.mysqlReplicationMonitor.sqlException", err.getMessage())
                            )
                        );
                        mysqlServerRow.error = true;
                    } catch(IOException err) {
                        mysqlServerRow.replications.add(
                            new ReplicationRow(
                                true,
                                slave,
                                controlApplicationResources.getMessage("monitor.mysqlReplicationMonitor.ioException", err.getMessage())
                            )
                        );
                        mysqlServerRow.error = true;
                    }
                }
            }
        }
        
        // Store in request attributes
        request.setAttribute("mysqlServerRows", mysqlServerRows);

        return mapping.findForward("success");
    }
    
    public List<String> getPermissions() {
        return Collections.singletonList(AOServPermission.GET_MYSQL_SLAVE_STATUS);
    }
    
    public static class MySQLServerRow {

        final private String version;
        final private String master;
        final private List<ReplicationRow> replications = new ArrayList<ReplicationRow>();
        private boolean error = false;

        private MySQLServerRow(String version, String master) {
            this.version = version;
            this.master = master;
        }

        public String getVersion() {
            return version;
        }
        
        public String getMaster() {
            return master;
        }

        public List<ReplicationRow> getReplications() {
            return replications;
        }
        
        public boolean getError() {
            return error;
        }
    }

    public static class ReplicationRow {

        final private boolean error;
        final private String slave;
        final private String lineError;
        final private String slaveIOState;
        final private String masterLogFile;
        final private String readMasterLogPos;
        final private String slaveIORunning;
        final private String slaveSQLRunning;
        final private String lastErrno;
        final private String lastError;
        final private String secondsBehindMaster;

        private ReplicationRow(boolean error, String slave, String lineError) {
            this.error = error;
            this.slave = slave;
            this.lineError = lineError;
            this.slaveIOState = null;
            this.masterLogFile = null;
            this.readMasterLogPos = null;
            this.slaveIORunning = null;
            this.slaveSQLRunning = null;
            this.lastErrno = null;
            this.lastError = null;
            this.secondsBehindMaster = null;
            
        }

        private ReplicationRow(boolean error, String slave, String slaveIOState, String masterLogFile, String readMasterLogPos, String slaveIORunning, String slaveSQLRunning, String lastErrno, String lastError, String secondsBehindMaster) {
            this.error = error;
            this.slave = slave;
            this.lineError = null;
            this.slaveIOState = slaveIOState;
            this.masterLogFile = masterLogFile;
            this.readMasterLogPos = readMasterLogPos;
            this.slaveIORunning = slaveIORunning;
            this.slaveSQLRunning = slaveSQLRunning;
            this.lastErrno = lastErrno;
            this.lastError = lastError;
            this.secondsBehindMaster = secondsBehindMaster;
        }

        public boolean getError() {
            return error;
        }

        public String getSlave() {
            return slave;
        }
        
        public String getLineError() {
            return lineError;
        }

        public String getSlaveIOState() {
            return slaveIOState;
        }

        public String getMasterLogFile() {
            return masterLogFile;
        }

        public String getReadMasterLogPos() {
            return readMasterLogPos;
        }
        
        public String getSlaveIORunning() {
            return slaveIORunning;
        }

        public String getSlaveSQLRunning() {
            return slaveSQLRunning;
        }

        public String getLastErrno() {
            return lastErrno;
        }
        
        public String getLastError() {
            return lastError;
        }

        public String getSecondsBehindMaster() {
            return secondsBehindMaster;
        }
    }
}
