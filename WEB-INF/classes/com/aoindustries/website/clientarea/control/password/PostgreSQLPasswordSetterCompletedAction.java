/*
 * Copyright 2000-2010 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
 * All rights reserved.
 */
package com.aoindustries.website.clientarea.control.password;

import com.aoindustries.aoserv.client.AOServConnector;
import com.aoindustries.aoserv.client.AOServPermission;
import com.aoindustries.aoserv.client.AOServer;
import com.aoindustries.aoserv.client.PostgresServer;
import com.aoindustries.aoserv.client.PostgresUser;
import com.aoindustries.aoserv.client.command.CommandName;
import com.aoindustries.aoserv.client.validator.DomainName;
import com.aoindustries.aoserv.client.validator.PostgresUserId;
import com.aoindustries.website.PermissionAction;
import com.aoindustries.website.SiteSettings;
import com.aoindustries.website.Skin;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

/**
 * @author  AO Industries, Inc.
 */
public class PostgreSQLPasswordSetterCompletedAction extends PermissionAction {

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

        // Validation
        ActionMessages errors = postgreSQLPasswordSetterForm.validate(mapping, request);
        if(errors!=null && !errors.isEmpty()) {
            saveErrors(request, errors);
            return mapping.findForward("input");
        }

        // Reset passwords here and clear the passwords from the form
        ActionMessages messages = new ActionMessages();
        List<String> usernames = postgreSQLPasswordSetterForm.getUsernames();
        List<String> aoServers = postgreSQLPasswordSetterForm.getAoServers();
        List<String> postgreSQLServers = postgreSQLPasswordSetterForm.getPostgreSQLServers();
        List<String> newPasswords = postgreSQLPasswordSetterForm.getNewPasswords();
        List<String> confirmPasswords = postgreSQLPasswordSetterForm.getConfirmPasswords();
        for(int c=0;c<usernames.size();c++) {
            String newPassword = newPasswords.get(c);
            if(newPassword.length()>0) {
                PostgresUserId username = PostgresUserId.valueOf(usernames.get(c));
                DomainName hostname = DomainName.valueOf(aoServers.get(c));
                AOServer aoServer = aoConn.getAoServers().filterUnique(AOServer.COLUMN_HOSTNAME, hostname);
                String serverName = postgreSQLServers.get(c);
                PostgresServer ps = aoServer.getPostgresServer(serverName);
                PostgresUser pu = ps.getPostgresUser(username);
                pu.setPassword(newPassword);
                messages.add("confirmPasswords[" + c + "].confirmPasswords", new ActionMessage("password.postgreSQLPasswordSetter.field.confirmPasswords.passwordReset"));
                newPasswords.set(c, "");
                confirmPasswords.set(c, "");
            }
        }
        saveMessages(request, messages);

        return mapping.findForward("success");
    }

    public Set<AOServPermission.Permission> getPermissions() {
        return CommandName.set_postgres_user_password.getPermissions();
    }
}
