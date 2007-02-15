package com.aoindustries.website.clientarea.control.password;

/*
 * Copyright 2000-2007 by AO Industries, Inc.,
 * 816 Azalea Rd, Mobile, Alabama, 36693, U.S.A.
 * All rights reserved.
 */
import com.aoindustries.aoserv.client.AOServConnector;
import com.aoindustries.aoserv.client.AOServPermission;
import com.aoindustries.aoserv.client.AOServer;
import com.aoindustries.aoserv.client.InterBaseServerUser;
import com.aoindustries.aoserv.client.InterBaseUser;
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
public class InterBasePasswordSetterCompletedAction extends PermissionAction {

    public ActionForward executePermissionGranted(
        ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response,
        Locale locale,
        Skin skin,
        AOServConnector aoConn
    ) throws Exception {
        InterBasePasswordSetterForm interBasePasswordSetterForm = (InterBasePasswordSetterForm)form;

        // Validation
        ActionMessages errors = interBasePasswordSetterForm.validate(mapping, request);
        if(errors!=null && !errors.isEmpty()) {
            saveErrors(request, errors);
            return mapping.findForward("input");
        }

        // Reset passwords here and clear the passwords from the form
        ActionMessages messages = new ActionMessages();
        List<String> usernames = interBasePasswordSetterForm.getUsernames();
        List<String> aoServers = interBasePasswordSetterForm.getAoServers();
        List<String> newPasswords = interBasePasswordSetterForm.getNewPasswords();
        List<String> confirmPasswords = interBasePasswordSetterForm.getConfirmPasswords();
        for(int c=0;c<usernames.size();c++) {
            String newPassword = newPasswords.get(c);
            if(newPassword.length()>0) {
                String username = usernames.get(c);
                InterBaseUser iu = aoConn.interBaseUsers.get(username);
                if(iu==null) throw new SQLException("Unable to find InterBaseUser: "+username);
                String hostname = aoServers.get(c);
                Server server = aoConn.servers.get(hostname);
                if(server==null) throw new SQLException("Unable to find Server: "+server);
                AOServer aoServer = server.getAOServer();
                if(aoServer==null) throw new SQLException("Unable to find AOServer: "+aoServer);
                InterBaseServerUser isu = iu.getInterBaseServerUser(aoServer);
                if(isu==null) throw new SQLException("Unable to find InterBaseServerUser: "+username+" on "+hostname);
                isu.setPassword(newPassword);
                messages.add("confirmPasswords[" + c + "].confirmPasswords", new ActionMessage("password.interBasePasswordSetter.field.confirmPasswords.passwordReset"));
                newPasswords.set(c, "");
                confirmPasswords.set(c, "");
            }
        }
        saveMessages(request, messages);

        return mapping.findForward("success");
    }

    public List<String> getPermissions() {
        return Collections.singletonList(AOServPermission.SET_INTERBASE_SERVER_USER_PASSWORD);
    }
}
