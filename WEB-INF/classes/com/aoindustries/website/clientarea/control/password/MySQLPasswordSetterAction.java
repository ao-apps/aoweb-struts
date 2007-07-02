package com.aoindustries.website.clientarea.control.password;

/*
 * Copyright 2000-2007 by AO Industries, Inc.,
 * 816 Azalea Rd, Mobile, Alabama, 36693, U.S.A.
 * All rights reserved.
 */
import com.aoindustries.aoserv.client.AOServConnector;
import com.aoindustries.aoserv.client.AOServPermission;
import com.aoindustries.aoserv.client.MySQLServer;
import com.aoindustries.aoserv.client.MySQLServerUser;
import com.aoindustries.aoserv.client.MySQLUser;
import com.aoindustries.aoserv.client.Username;
import com.aoindustries.website.PermissionAction;
import com.aoindustries.website.Skin;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

/**
 * Prepares for business administrator password setting.  Populates lists in mySQLPasswordSetterForm.
 *
 * @author  AO Industries, Inc.
 */
public class MySQLPasswordSetterAction extends PermissionAction {

    public ActionForward executePermissionGranted(
        ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response,
        Locale locale,
        Skin skin,
        AOServConnector aoConn
    ) throws Exception {
        MySQLPasswordSetterForm mySQLPasswordSetterForm = (MySQLPasswordSetterForm)form;

        List<MySQLServerUser> msus = aoConn.mysqlServerUsers.getRows();

        List<String> packages = new ArrayList<String>(msus.size());
        List<String> usernames = new ArrayList<String>(msus.size());
        List<String> mySQLServers = new ArrayList<String>(msus.size());
        List<String> aoServers = new ArrayList<String>(msus.size());
        List<String> newPasswords = new ArrayList<String>(msus.size());
        List<String> confirmPasswords = new ArrayList<String>(msus.size());
        for(MySQLServerUser msu : msus) {
            if(msu.canSetPassword()) {
                MySQLUser mu = msu.getMySQLUser();
                Username un = mu.getUsername();
                MySQLServer ms = msu.getMySQLServer();
                packages.add(un.getPackage().getName());
                usernames.add(un.getUsername());
                mySQLServers.add(ms.getName());
                aoServers.add(ms.getAOServer().getServer().getHostname());
                newPasswords.add("");
                confirmPasswords.add("");
            }
        }

        // Store to the form
        mySQLPasswordSetterForm.setPackages(packages);
        mySQLPasswordSetterForm.setUsernames(usernames);
        mySQLPasswordSetterForm.setMySQLServers(mySQLServers);
        mySQLPasswordSetterForm.setAoServers(aoServers);
        mySQLPasswordSetterForm.setNewPasswords(newPasswords);
        mySQLPasswordSetterForm.setConfirmPasswords(confirmPasswords);

        return mapping.findForward("success");
    }

    public List<AOServPermission.Permission> getPermissions() {
        return Collections.singletonList(AOServPermission.Permission.set_mysql_server_user_password);
    }
}
