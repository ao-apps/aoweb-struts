package com.aoindustries.website.clientarea.control.password;

/*
 * Copyright 2000-2009 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
 * All rights reserved.
 */
import com.aoindustries.aoserv.client.AOServConnector;
import com.aoindustries.aoserv.client.AOServPermission;
import com.aoindustries.aoserv.client.MySQLServer;
import com.aoindustries.aoserv.client.MySQLUser;
import com.aoindustries.aoserv.client.Username;
import com.aoindustries.website.PermissionAction;
import com.aoindustries.website.SiteSettings;
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
        Locale locale,
        Skin skin,
        AOServConnector aoConn
    ) throws Exception {
        MySQLPasswordSetterForm mySQLPasswordSetterForm = (MySQLPasswordSetterForm)form;

        List<MySQLUser> mus = aoConn.getMysqlUsers().getRows();

        List<String> businesses = new ArrayList<String>(mus.size());
        List<String> usernames = new ArrayList<String>(mus.size());
        List<String> mySQLServers = new ArrayList<String>(mus.size());
        List<String> aoServers = new ArrayList<String>(mus.size());
        List<String> newPasswords = new ArrayList<String>(mus.size());
        List<String> confirmPasswords = new ArrayList<String>(mus.size());
        for(MySQLUser mu : mus) {
            if(mu.canSetPassword()) {
                Username un = mu.getUsername();
                MySQLServer ms = mu.getMySQLServer();
                businesses.add(un.getBusiness().getAccounting());
                usernames.add(un.getUsername());
                mySQLServers.add(ms.getName());
                aoServers.add(ms.getAoServerResource().getAoServer().getHostname());
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

    public List<AOServPermission.Permission> getPermissions() {
        return Collections.singletonList(AOServPermission.Permission.set_mysql_user_password);
    }
}
