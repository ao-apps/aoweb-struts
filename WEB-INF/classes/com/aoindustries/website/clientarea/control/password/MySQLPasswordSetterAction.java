/*
 * Copyright 2000-2010 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
 * All rights reserved.
 */
package com.aoindustries.website.clientarea.control.password;

import com.aoindustries.aoserv.client.AOServConnector;
import com.aoindustries.aoserv.client.AOServPermission;
import com.aoindustries.aoserv.client.MySQLServer;
import com.aoindustries.aoserv.client.MySQLUser;
import com.aoindustries.aoserv.client.Username;
import com.aoindustries.aoserv.client.command.CommandName;
import com.aoindustries.aoserv.client.command.SetMySQLUserPasswordCommand;
import com.aoindustries.website.PermissionAction;
import com.aoindustries.website.SiteSettings;
import com.aoindustries.website.Skin;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Prepares for business administrator password setting.  Populates lists in mySQLPasswordSetterForm.
 *
 * @author  AO Industries, Inc.
 */
public class MySQLPasswordSetterAction extends PermissionAction {

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
        MySQLPasswordSetterForm mySQLPasswordSetterForm = (MySQLPasswordSetterForm)form;

        SortedSet<MySQLUser> mus = new TreeSet<MySQLUser>(aoConn.getMysqlUsers().getSet());

        List<String> businesses = new ArrayList<String>(mus.size());
        List<String> usernames = new ArrayList<String>(mus.size());
        List<String> mySQLServers = new ArrayList<String>(mus.size());
        List<String> aoServers = new ArrayList<String>(mus.size());
        List<String> newPasswords = new ArrayList<String>(mus.size());
        List<String> confirmPasswords = new ArrayList<String>(mus.size());
        for(MySQLUser mu : mus) {
            if(!new SetMySQLUserPasswordCommand(mu, null).validate(aoConn).containsKey(SetMySQLUserPasswordCommand.PARAM_MYSQL_USER)) {
                Username un = mu.getUsername();
                MySQLServer ms = mu.getMysqlServer();
                businesses.add(un.getBusiness().getAccounting().toString());
                usernames.add(un.getUsername().toString());
                mySQLServers.add(ms.getName().toString());
                aoServers.add(ms.getAoServer().getHostname().toString());
                newPasswords.add("");
                confirmPasswords.add("");
            }
        }

        // Store to the form
        mySQLPasswordSetterForm.setBusinesses(businesses);
        mySQLPasswordSetterForm.setUsernames(usernames);
        mySQLPasswordSetterForm.setMySQLServers(mySQLServers);
        mySQLPasswordSetterForm.setAoServers(aoServers);
        mySQLPasswordSetterForm.setNewPasswords(newPasswords);
        mySQLPasswordSetterForm.setConfirmPasswords(confirmPasswords);

        return mapping.findForward("success");
    }

    @Override
    public Set<AOServPermission.Permission> getPermissions() {
        return CommandName.set_mysql_user_password.getPermissions();
    }
}
