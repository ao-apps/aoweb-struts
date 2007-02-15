package com.aoindustries.website.clientarea.control.password;

/*
 * Copyright 2000-2007 by AO Industries, Inc.,
 * 816 Azalea Rd, Mobile, Alabama, 36693, U.S.A.
 * All rights reserved.
 */
import com.aoindustries.aoserv.client.AOServConnector;
import com.aoindustries.aoserv.client.AOServPermission;
import com.aoindustries.aoserv.client.PostgresServer;
import com.aoindustries.aoserv.client.PostgresServerUser;
import com.aoindustries.aoserv.client.PostgresUser;
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
 * Prepares for business administrator password setting.  Populates lists in postgreSQLPasswordSetterForm.
 *
 * @author  AO Industries, Inc.
 */
public class PostgreSQLPasswordSetterAction extends PermissionAction {

    public ActionForward executePermissionGranted(
        ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response,
        Locale locale,
        Skin skin,
        AOServConnector aoConn
    ) throws Exception {
        PostgreSQLPasswordSetterForm postgreSQLPasswordSetterForm = (PostgreSQLPasswordSetterForm)form;

        List<PostgresServerUser> psus = aoConn.postgresServerUsers.getRows();

        List<String> packages = new ArrayList<String>(psus.size());
        List<String> usernames = new ArrayList<String>(psus.size());
        List<String> postgreSQLServers = new ArrayList<String>(psus.size());
        List<String> aoServers = new ArrayList<String>(psus.size());
        List<String> newPasswords = new ArrayList<String>(psus.size());
        List<String> confirmPasswords = new ArrayList<String>(psus.size());
        for(PostgresServerUser psu : psus) {
            if(psu.canSetPassword()) {
                PostgresUser pu = psu.getPostgresUser();
                Username un = pu.getUsername();
                PostgresServer ps = psu.getPostgresServer();
                packages.add(un.getPackage().getName());
                usernames.add(un.getUsername());
                postgreSQLServers.add(ps.getName());
                aoServers.add(ps.getAOServer().getServer().getHostname());
                newPasswords.add("");
                confirmPasswords.add("");
            }
        }

        // Store to the form
        postgreSQLPasswordSetterForm.setPackages(packages);
        postgreSQLPasswordSetterForm.setUsernames(usernames);
        postgreSQLPasswordSetterForm.setPostgreSQLServers(postgreSQLServers);
        postgreSQLPasswordSetterForm.setAoServers(aoServers);
        postgreSQLPasswordSetterForm.setNewPasswords(newPasswords);
        postgreSQLPasswordSetterForm.setConfirmPasswords(confirmPasswords);

        return mapping.findForward("success");
    }

    public List<String> getPermissions() {
        return Collections.singletonList(AOServPermission.SET_POSTGRES_SERVER_USER_PASSWORD);
    }
}
