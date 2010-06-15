/*
 * Copyright 2000-2010 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
 * All rights reserved.
 */
package com.aoindustries.website.clientarea.control.password;

import com.aoindustries.aoserv.client.AOServConnector;
import com.aoindustries.aoserv.client.AOServPermission;
import com.aoindustries.aoserv.client.PostgresServer;
import com.aoindustries.aoserv.client.PostgresUser;
import com.aoindustries.aoserv.client.Username;
import com.aoindustries.aoserv.client.command.CommandName;
import com.aoindustries.aoserv.client.command.SetPostgresUserPasswordCommand;
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
 * Prepares for business administrator password setting.  Populates lists in postgreSQLPasswordSetterForm.
 *
 * @author  AO Industries, Inc.
 */
public class PostgreSQLPasswordSetterAction extends PermissionAction {

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
        PostgreSQLPasswordSetterForm postgreSQLPasswordSetterForm = (PostgreSQLPasswordSetterForm)form;

        SortedSet<PostgresUser> pus = new TreeSet<PostgresUser>(aoConn.getPostgresUsers().getSet());

        List<String> businesses = new ArrayList<String>(pus.size());
        List<String> usernames = new ArrayList<String>(pus.size());
        List<String> postgreSQLServers = new ArrayList<String>(pus.size());
        List<String> aoServers = new ArrayList<String>(pus.size());
        List<String> newPasswords = new ArrayList<String>(pus.size());
        List<String> confirmPasswords = new ArrayList<String>(pus.size());
        for(PostgresUser pu : pus) {
            if(!new SetPostgresUserPasswordCommand(pu.getKey(), null).validate(aoConn).containsKey(SetPostgresUserPasswordCommand.PARAM_POSTGRES_USER)) {
                Username un = pu.getUsername();
                PostgresServer ps = pu.getPostgresServer();
                businesses.add(un.getBusiness().getAccounting().toString());
                usernames.add(un.getUsername().toString());
                postgreSQLServers.add(ps.getName().toString());
                aoServers.add(ps.getAoServerResource().getAoServer().getHostname().toString());
                newPasswords.add("");
                confirmPasswords.add("");
            }
        }

        // Store to the form
        postgreSQLPasswordSetterForm.setBusinesses(businesses);
        postgreSQLPasswordSetterForm.setUsernames(usernames);
        postgreSQLPasswordSetterForm.setPostgreSQLServers(postgreSQLServers);
        postgreSQLPasswordSetterForm.setAoServers(aoServers);
        postgreSQLPasswordSetterForm.setNewPasswords(newPasswords);
        postgreSQLPasswordSetterForm.setConfirmPasswords(confirmPasswords);

        return mapping.findForward("success");
    }

    @Override
    public Set<AOServPermission.Permission> getPermissions() {
        return CommandName.set_postgres_user_password.getPermissions();
    }
}
