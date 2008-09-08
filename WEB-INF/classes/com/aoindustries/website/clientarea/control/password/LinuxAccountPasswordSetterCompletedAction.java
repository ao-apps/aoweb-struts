package com.aoindustries.website.clientarea.control.password;

/*
 * Copyright 2000-2008 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
 * All rights reserved.
 */
import com.aoindustries.aoserv.client.AOServConnector;
import com.aoindustries.aoserv.client.AOServPermission;
import com.aoindustries.aoserv.client.AOServer;
import com.aoindustries.aoserv.client.LinuxAccount;
import com.aoindustries.aoserv.client.LinuxServerAccount;
import com.aoindustries.aoserv.client.Server;
import com.aoindustries.aoserv.client.Username;
import com.aoindustries.website.PermissionAction;
import com.aoindustries.website.Skin;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
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
public class LinuxAccountPasswordSetterCompletedAction extends PermissionAction {

    public ActionForward executePermissionGranted(
        ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response,
        Locale locale,
        Skin skin,
        AOServConnector aoConn
    ) throws Exception {
        LinuxAccountPasswordSetterForm linuxAccountPasswordSetterForm = (LinuxAccountPasswordSetterForm)form;

        // Validation
        ActionMessages errors = linuxAccountPasswordSetterForm.validate(mapping, request);
        if(errors!=null && !errors.isEmpty()) {
            saveErrors(request, errors);
            return mapping.findForward("input");
        }

        // Reset passwords here and clear the passwords from the form
        ActionMessages messages = new ActionMessages();
        List<String> usernames = linuxAccountPasswordSetterForm.getUsernames();
        List<String> aoServers = linuxAccountPasswordSetterForm.getAoServers();
        List<String> newPasswords = linuxAccountPasswordSetterForm.getNewPasswords();
        List<String> confirmPasswords = linuxAccountPasswordSetterForm.getConfirmPasswords();
        for(int c=0;c<usernames.size();c++) {
            String newPassword = newPasswords.get(c);
            if(newPassword.length()>0) {
                String username = usernames.get(c);
                LinuxAccount la = aoConn.linuxAccounts.get(username);
                if(la==null) throw new SQLException("Unable to find LinuxAccount: "+username);
                String hostname = aoServers.get(c);
                Server server = aoConn.servers.get(hostname);
                if(server==null) throw new SQLException("Unable to find Server: "+server);
                AOServer aoServer = server.getAOServer();
                if(aoServer==null) throw new SQLException("Unable to find AOServer: "+aoServer);
                LinuxServerAccount lsa = la.getLinuxServerAccount(aoServer);
                if(lsa==null) throw new SQLException("Unable to find LinuxServerAccount: "+username+" on "+hostname);
                lsa.setPassword(newPassword);
                messages.add("confirmPasswords[" + c + "].confirmPasswords", new ActionMessage("password.linuxAccountPasswordSetter.field.confirmPasswords.passwordReset"));
                newPasswords.set(c, "");
                confirmPasswords.set(c, "");
            }
        }
        saveMessages(request, messages);

        return mapping.findForward("success");
    }

    public List<AOServPermission.Permission> getPermissions() {
        return Collections.singletonList(AOServPermission.Permission.set_linux_server_account_password);
    }
}
