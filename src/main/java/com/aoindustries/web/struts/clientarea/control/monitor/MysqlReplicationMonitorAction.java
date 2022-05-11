/*
 * aoweb-struts - Template webapp for legacy Struts-based site framework with AOServ Platform control panels.
 * Copyright (C) 2000-2013, 2016, 2017, 2018, 2019, 2021, 2022  AO Industries, Inc.
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

package com.aoindustries.web.struts.clientarea.control.monitor;

import com.aoapps.lang.i18n.Resources;
import com.aoapps.net.DomainName;
import com.aoindustries.aoserv.client.AoservConnector;
import com.aoindustries.aoserv.client.backup.BackupPartition;
import com.aoindustries.aoserv.client.backup.FileReplication;
import com.aoindustries.aoserv.client.backup.MysqlReplication;
import com.aoindustries.aoserv.client.master.Permission;
import com.aoindustries.aoserv.client.mysql.Server;
import com.aoindustries.web.struts.PermissionAction;
import com.aoindustries.web.struts.SiteSettings;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
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
public class MysqlReplicationMonitorAction extends PermissionAction {

  private static final Resources RESOURCES =
      Resources.getResources(ResourceBundle::getBundle, MysqlReplicationMonitorAction.class);

  @Override
  public ActionForward executePermissionGranted(
      ActionMapping mapping,
      ActionForm form,
      HttpServletRequest request,
      HttpServletResponse response,
      AoservConnector aoConn
  ) throws Exception {
    SiteSettings siteSettings = SiteSettings.getInstance(getServlet().getServletContext());
    AoservConnector rootConn = siteSettings.getRootAoservConnector();

    List<MysqlServerRow> mysqlServerRows = new ArrayList<>();
    List<Server> mysqlServers = aoConn.getMysql().getServer().getRows();
    for (Server mysqlServer : mysqlServers) {
      com.aoindustries.aoserv.client.linux.Server linuxServer = mysqlServer.getLinuxServer();
      com.aoindustries.aoserv.client.linux.Server failoverServer;
      try {
        failoverServer = linuxServer.getFailoverServer();
      } catch (SQLException err) {
        // May be filtered, need to use RootAoservConnector
        failoverServer = rootConn.getLinux().getServer().get(linuxServer.getPkey()).getFailoverServer();
      }

      StringBuilder server = new StringBuilder();
      server.append(linuxServer.getHostname());
      if (failoverServer != null) {
        server.append(" on ").append(failoverServer.getHostname());
      }

      List<MysqlReplication> fmrs = mysqlServer.getFailoverMysqlReplications();
      if (!fmrs.isEmpty()) {
        // Query the slaves first, this way the master will always appear equal to or ahead of the slaves
        // since we can't query them both exactly at the same time.
        List<ReplicationRow> replications = new ArrayList<>();
        for (MysqlReplication fmr : fmrs) {
          DomainName slave;
          com.aoindustries.aoserv.client.linux.Server replicationAoServer = fmr.getLinuxServer();
          if (replicationAoServer != null) {
            // ao_server-based
            slave = replicationAoServer.getHostname();
          } else {
            FileReplication ffr = fmr.getFailoverFileReplication();
            BackupPartition bp = ffr.getBackupPartition();
            if (bp == null) {
              // May be filtered, need to use RootAoservConnector
              slave = rootConn.getBackup().getFileReplication().get(ffr.getPkey()).getBackupPartition().getLinuxServer().getHostname();
            } else {
              slave = bp.getLinuxServer().getHostname();
            }
          }
          try {
            MysqlReplication.SlaveStatus slaveStatus = fmr.getSlaveStatus();
            if (slaveStatus == null) {
              replications.add(
                  new ReplicationRow(
                      true,
                      slave,
                      RESOURCES.getMessage("slaveNotRunning")
                  )
              );
            } else {
              String secondsBehindMaster = slaveStatus.getSecondsBehindMaster();
              boolean error;
              if (secondsBehindMaster == null) {
                error = true;
              } else {
                try {
                  int secondsBehind = Integer.parseInt(secondsBehindMaster);
                  int lowSecondsBehind = fmr.getMonitoringSecondsBehindLow();
                  error = lowSecondsBehind != -1 && secondsBehind >= lowSecondsBehind;
                } catch (NumberFormatException err) {
                  error = true;
                }
              }
              replications.add(
                  new ReplicationRow(
                      error,
                      slave,
                      slaveStatus.getSlaveIoState(),
                      slaveStatus.getMasterLogFile(),
                      slaveStatus.getReadMasterLogPos(),
                      slaveStatus.getSlaveIoRunning(),
                      slaveStatus.getSlaveSqlRunning(),
                      slaveStatus.getLastErrno(),
                      slaveStatus.getLastError(),
                      secondsBehindMaster
                  )
              );
            }
          } catch (SQLException err) {
            replications.add(
                new ReplicationRow(
                    true,
                    slave,
                    RESOURCES.getMessage("sqlException", err.getMessage())
                )
            );
          } catch (IOException err) {
            replications.add(
                new ReplicationRow(
                    true,
                    slave,
                    RESOURCES.getMessage("ioException", err.getMessage())
                )
            );
          }
        }
        // Next, query the master and add the results to the rows
        Server.MasterStatus masterStatus;
        MysqlServerRow mysqlServerRow;
        try {
          masterStatus = mysqlServer.getMasterStatus();
          if (masterStatus == null) {
            mysqlServerRow = new MysqlServerRow(
                mysqlServer.getVersion().getVersion(),
                server.toString(),
                RESOURCES.getMessage("masterNotRunning"),
                replications
            );
          } else {
            mysqlServerRow = new MysqlServerRow(
                mysqlServer.getVersion().getVersion(),
                server.toString(),
                masterStatus.getFile(),
                masterStatus.getPosition(),
                replications
            );
          }
        } catch (SQLException err) {
          masterStatus = null;
          mysqlServerRow = new MysqlServerRow(
              mysqlServer.getVersion().getVersion(),
              server.toString(),
              RESOURCES.getMessage("sqlException", err.getMessage()),
              replications
          );
        } catch (IOException err) {
          masterStatus = null;
          mysqlServerRow = new MysqlServerRow(
              mysqlServer.getVersion().getVersion(),
              server.toString(),
              RESOURCES.getMessage("ioException", err.getMessage()),
              replications
          );
        }
        /*
        // Also an individual replication row error if too far behind in log file position or can't determine how far behind
        if (masterStatus == null) {
          mysqlServerRow.error = true;
          for (ReplicationRow replication : replications) replication.error = true;
        } else {
          String masterLogFile = masterStatus.getFile();
          String masterLogPosString = masterStatus.getPosition();
          if (masterLogFile == null || masterLogPosString == null) {
            for (ReplicationRow replication : replications) replication.error = true;
          } else {
            try {
              long masterLogPos = Long.parseLong(masterLogPosString);
              for (ReplicationRow replication : replications) {
                String slaveLogFile = replication.getSlaveLogFile();
                String slaveLogPosString = replication.getSlaveLogPos();
                if (slaveLogFile == null || slaveLogPosString == null) {
                  replication.error = true;
                } else {
                  try {
                    long slaveLogPos = Long.parseLong(slaveLogPosString);
                    long difference = masterLogPos - slaveLogPos;
                    if (
                      !slaveLogFile.equals(masterLogFile)
                      || difference >= ERROR_BYTES_BEHIND
                      || difference<0
                    ) {
                      replication.error = true;
                    }
                  } catch (NumberFormatException err) {
                    replication.error = true;
                  }
                }
              }
            } catch (NumberFormatException err) {
              for (ReplicationRow replication : replications) replication.error = true;
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

  private static final Set<Permission.Name> permissions = Collections.unmodifiableSet(
      EnumSet.of(
          Permission.Name.get_mysql_master_status,
          Permission.Name.get_mysql_slave_status
      )
  );

  @Override
  public Set<Permission.Name> getPermissions() {
    return permissions;
  }

  public static class MysqlServerRow {

    private final String version;
    private final String master;
    private final List<ReplicationRow> replications;
    private boolean error;
    private final String lineError;
    private final String masterLogFile;
    private final String masterLogPos;

    private MysqlServerRow(String version, String master, String lineError, List<ReplicationRow> replications) {
      this.version = version;
      this.master = master;
      this.replications = replications;
      this.error = true;
      this.lineError = lineError;
      this.masterLogFile = null;
      this.masterLogPos = null;
    }

    private MysqlServerRow(String version, String master, String masterLogFile, String masterLogPos, List<ReplicationRow> replications) {
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

    private final boolean error;
    private final DomainName slave;
    private final String lineError;
    private final String slaveIoState;
    private final String slaveLogFile;
    private final String slaveLogPos;
    private final String slaveIoRunning;
    private final String slaveSqlRunning;
    private final String lastErrno;
    private final String lastError;
    private final String secondsBehindMaster;

    private ReplicationRow(boolean error, DomainName slave, String lineError) {
      this.error = error;
      this.slave = slave;
      this.lineError = lineError;
      this.slaveIoState = null;
      this.slaveLogFile = null;
      this.slaveLogPos = null;
      this.slaveIoRunning = null;
      this.slaveSqlRunning = null;
      this.lastErrno = null;
      this.lastError = null;
      this.secondsBehindMaster = null;

    }

    private ReplicationRow(
        boolean error,
        DomainName slave,
        String slaveIoState,
        String slaveLogFile,
        String slaveLogPos,
        String slaveIoRunning,
        String slaveSqlRunning,
        String lastErrno,
        String lastError,
        String secondsBehindMaster
    ) {
      this.error = error;
      this.slave = slave;
      this.lineError = null;
      this.slaveIoState = slaveIoState;
      this.slaveLogFile = slaveLogFile;
      this.slaveLogPos = slaveLogPos;
      this.slaveIoRunning = slaveIoRunning;
      this.slaveSqlRunning = slaveSqlRunning;
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

    public String getSlaveIoState() {
      return slaveIoState;
    }

    public String getSlaveLogFile() {
      return slaveLogFile;
    }

    public String getSlaveLogPos() {
      return slaveLogPos;
    }

    public String getSlaveIoRunning() {
      return slaveIoRunning;
    }

    public String getSlaveSqlRunning() {
      return slaveSqlRunning;
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
