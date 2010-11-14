package com.aoindustries.website.clientarea.control.monitor;

/*
 * Copyright 2000-2010 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
 * All rights reserved.
 */
import com.aoindustries.aoserv.client.AOServConnector;
import com.aoindustries.aoserv.client.AOServPermission;
import com.aoindustries.aoserv.client.AOServer;
import com.aoindustries.aoserv.client.FailoverFileReplication;
import com.aoindustries.aoserv.client.FailoverMySQLReplication;
import com.aoindustries.aoserv.client.MySQLServer;
import com.aoindustries.aoserv.client.command.CommandName;
import com.aoindustries.aoserv.client.command.GetMySQLMasterStatusCommand;
import com.aoindustries.aoserv.client.command.GetMySQLSlaveStatusCommand;
import com.aoindustries.aoserv.client.validator.DomainName;
import com.aoindustries.website.PermissionAction;
import com.aoindustries.website.SiteSettings;
import com.aoindustries.website.Skin;
import com.aoindustries.website.clientarea.control.ApplicationResources;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Retrieves the mysql slave statuses.
 *
 * @author  AO Industries, Inc.
 */
public class MySQLReplicationMonitorAction extends PermissionAction {

    @Override
    public ActionForward executePermissionGranted(
        ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response,
        SiteSettings siteSettings,
        Skin skin,
        AOServConnector<?,?> aoConn
    ) throws Exception {
        AOServConnector<?,?> rootConn = siteSettings.getRootAOServConnector();

        List<MySQLServerRow> mysqlServerRows = new ArrayList<MySQLServerRow>();
        for(MySQLServer mysqlServer : new TreeSet<MySQLServer>(aoConn.getMysqlServers().getSet())) {
            AOServer aoServer = mysqlServer.getAoServer();
            AOServer failoverServer;
            try {
                failoverServer = aoServer.getFailoverServer();
            } catch(NoSuchElementException err) {
                // May be filtered, need to use RootAOServConnector
                failoverServer = rootConn.getAoServers().get(aoServer.getKey()).getFailoverServer();
            }

            StringBuilder server = new StringBuilder();
            server.append(aoServer.getHostname());
            if(failoverServer!=null) server.append(" on ").append(failoverServer.getHostname());

            SortedSet<FailoverMySQLReplication> fmrs = new TreeSet<FailoverMySQLReplication>(mysqlServer.getFailoverMySQLReplications());
            if(!fmrs.isEmpty()) {
                // Query the slaves first, this way the master will always appear equal to or ahead of the slaves
                // since we can't query them both exactly at the same time.
                List<ReplicationRow> replications = new ArrayList<ReplicationRow>();
                for(FailoverMySQLReplication fmr : fmrs) {
                    DomainName slave;
                    AOServer replicationAoServer = fmr.getAOServer();
                    if(replicationAoServer!=null) {
                        // ao_server-based
                        slave = replicationAoServer.getHostname();
                    } else {
                        FailoverFileReplication ffr = fmr.getFailoverFileReplication();
                        try {
                            slave = ffr.getBackupPartition().getAOServer().getHostname();
                        } catch(NoSuchElementException err) {
                            // May be filtered, need to use RootAOServConnector
                            slave = rootConn.getFailoverFileReplications().get(ffr.getKey()).getBackupPartition().getAOServer().getHostname();
                        }
                    }
                    try {
                        GetMySQLSlaveStatusCommand.SlaveStatus slaveStatus = new GetMySQLSlaveStatusCommand(fmr).execute(aoConn);
                        if(slaveStatus==null) {
                            replications.add(
                                new ReplicationRow(
                                    true,
                                    slave,
                                    ApplicationResources.accessor.getMessage("monitor.mysqlReplicationMonitor.slaveNotRunning")
                                )
                            );
                        } else {
                            String secondsBehindMaster = slaveStatus.getSecondsBehindMaster();
                            boolean error;
                            if(secondsBehindMaster==null) error = true;
                            else {
                                try {
                                    int secondsBehind = Integer.parseInt(secondsBehindMaster);
                                    int lowSecondsBehind = fmr.getMonitoringSecondsBehindLow();
                                    error = lowSecondsBehind!=-1 && secondsBehind >= lowSecondsBehind;
                                } catch(NumberFormatException err) {
                                    error = true;
                                }
                            }
                            replications.add(
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
                        }
                    } catch(IOException err) {
                        replications.add(
                            new ReplicationRow(
                                true,
                                slave,
                                ApplicationResources.accessor.getMessage("monitor.mysqlReplicationMonitor.ioException", err.getMessage())
                            )
                        );
                    }
                }
                // Next, query the master and add the results to the rows
                GetMySQLMasterStatusCommand.MasterStatus masterStatus;
                MySQLServerRow mysqlServerRow;
                try {
                    masterStatus = new GetMySQLMasterStatusCommand(mysqlServer).execute(aoConn);
                    if(masterStatus==null) {
                        mysqlServerRow = new MySQLServerRow(
                            mysqlServer.getVersion().getVersion(),
                            server.toString(),
                            ApplicationResources.accessor.getMessage("monitor.mysqlReplicationMonitor.masterNotRunning"),
                            replications
                        );
                    } else {
                        mysqlServerRow = new MySQLServerRow(
                            mysqlServer.getVersion().getVersion(),
                            server.toString(),
                            masterStatus.getFile(),
                            masterStatus.getPosition(),
                            replications
                        );
                    }
                } catch(IOException err) {
                    masterStatus = null;
                    mysqlServerRow = new MySQLServerRow(
                        mysqlServer.getVersion().getVersion(),
                        server.toString(),
                        ApplicationResources.accessor.getMessage("monitor.mysqlReplicationMonitor.ioException", err.getMessage()),
                        replications
                    );
                }
                /*
                // Also an individual replication row error if too far behind in log file position or can't determine how far behind
                if(masterStatus==null) {
                    mysqlServerRow.error = true;
                    for(ReplicationRow replication : replications) replication.error = true;
                } else {
                    String masterLogFile = masterStatus.getFile();
                    String masterLogPosString = masterStatus.getPosition();
                    if(masterLogFile==null || masterLogPosString==null) {
                        for(ReplicationRow replication : replications) replication.error = true;
                    } else {
                        try {
                            long masterLogPos = Long.parseLong(masterLogPosString);
                            for(ReplicationRow replication : replications) {
                                String slaveLogFile = replication.getSlaveLogFile();
                                String slaveLogPosString = replication.getSlaveLogPos();
                                if(slaveLogFile==null || slaveLogPosString==null) {
                                    replication.error = true;
                                } else {
                                    try {
                                        long slaveLogPos = Long.parseLong(slaveLogPosString);
                                        long difference = masterLogPos - slaveLogPos;
                                        if(
                                            !slaveLogFile.equals(masterLogFile)
                                            || difference>=ERROR_BYTES_BEHIND
                                            || difference<0
                                        ) replication.error = true;
                                    } catch(NumberFormatException err) {
                                        replication.error = true;
                                    }
                                }
                            }
                        } catch(NumberFormatException err) {
                            for(ReplicationRow replication : replications) replication.error = true;
                        }
                    }
                }*/
                mysqlServerRows.add(mysqlServerRow);
            }
        }
        
        // Store in request attributes
        request.setAttribute("mysqlServerRows", mysqlServerRows);

        return mapping.findForward("success");
    }

    private static final Set<AOServPermission.Permission> unmodifiablePermissions;
    static {
        Set<AOServPermission.Permission> permissions = new HashSet<AOServPermission.Permission>();
        permissions.addAll(CommandName.get_mysql_master_status.getPermissions());
        permissions.addAll(CommandName.get_mysql_slave_status.getPermissions());
        unmodifiablePermissions = Collections.unmodifiableSet(permissions);
    }

    @Override
    public Set<AOServPermission.Permission> getPermissions() {
        return unmodifiablePermissions;
    }

    public static class MySQLServerRow {

        final private String version;
        final private String master;
        final private List<ReplicationRow> replications;
        private boolean error = false;
        final private String lineError;
        final private String masterLogFile;
        final private String masterLogPos;

        private MySQLServerRow(String version, String master, String lineError, List<ReplicationRow> replications) {
            this.version = version;
            this.master = master;
            this.replications = replications;
            this.error = true;
            this.lineError = lineError;
            this.masterLogFile = null;
            this.masterLogPos = null;
        }

        private MySQLServerRow(String version, String master, String masterLogFile, String masterLogPos, List<ReplicationRow> replications) {
            this.version = version;
            this.master = master;
            this.replications = replications;
            this.error = false;
            this.lineError = null;
            this.masterLogFile = masterLogFile;
            this.masterLogPos = masterLogPos;
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
        
        public String getLineError() {
            return lineError;
        }

        public String getMasterLogFile() {
            return masterLogFile;
        }

        public String getMasterLogPos() {
            return masterLogPos;
        }
    }

    public static class ReplicationRow {

        private boolean error;
        final private DomainName slave;
        final private String lineError;
        final private String slaveIOState;
        final private String slaveLogFile;
        final private String slaveLogPos;
        final private String slaveIORunning;
        final private String slaveSQLRunning;
        final private String lastErrno;
        final private String lastError;
        final private String secondsBehindMaster;

        private ReplicationRow(boolean error, DomainName slave, String lineError) {
            this.error = error;
            this.slave = slave;
            this.lineError = lineError;
            this.slaveIOState = null;
            this.slaveLogFile = null;
            this.slaveLogPos = null;
            this.slaveIORunning = null;
            this.slaveSQLRunning = null;
            this.lastErrno = null;
            this.lastError = null;
            this.secondsBehindMaster = null;
            
        }

        private ReplicationRow(
            boolean error,
            DomainName slave,
            String slaveIOState,
            String slaveLogFile,
            String slaveLogPos,
            String slaveIORunning,
            String slaveSQLRunning,
            String lastErrno,
            String lastError,
            String secondsBehindMaster
        ) {
            this.error = error;
            this.slave = slave;
            this.lineError = null;
            this.slaveIOState = slaveIOState;
            this.slaveLogFile = slaveLogFile;
            this.slaveLogPos = slaveLogPos;
            this.slaveIORunning = slaveIORunning;
            this.slaveSQLRunning = slaveSQLRunning;
            this.lastErrno = lastErrno;
            this.lastError = lastError;
            this.secondsBehindMaster = secondsBehindMaster;
        }

        public boolean getError() {
            return error;
        }

        public DomainName getSlave() {
            return slave;
        }
        
        public String getLineError() {
            return lineError;
        }

        public String getSlaveIOState() {
            return slaveIOState;
        }

        public String getSlaveLogFile() {
            return slaveLogFile;
        }

        public String getSlaveLogPos() {
            return slaveLogPos;
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
