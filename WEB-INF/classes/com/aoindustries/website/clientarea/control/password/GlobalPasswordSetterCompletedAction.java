package com.aoindustries.website.clientarea.control.password;

/*
 * Copyright 2000-2007 by AO Industries, Inc.,
 * 816 Azalea Rd, Mobile, Alabama, 36693, U.S.A.
 * All rights reserved.
 */
import com.aoindustries.aoserv.client.AOServConnector;
import com.aoindustries.aoserv.client.AOServPermission;
import com.aoindustries.aoserv.client.BusinessAdministrator;
import com.aoindustries.aoserv.client.Username;
import com.aoindustries.website.PermissionAction;
import com.aoindustries.website.Skin;
import java.sql.SQLException;
import java.util.ArrayList;
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
public class GlobalPasswordSetterCompletedAction extends PermissionAction {

    public ActionForward executePermissionGranted(
        ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response,
        Locale locale,
        Skin skin,
        AOServConnector aoConn
    ) throws Exception {
        GlobalPasswordSetterForm globalPasswordSetterForm = (GlobalPasswordSetterForm)form;

        // Validation
        ActionMessages errors = globalPasswordSetterForm.validate(mapping, request);
        if(errors!=null && !errors.isEmpty()) {
            saveErrors(request, errors);
            return mapping.findForward("input");
        }

        // Reset passwords here and clear the passwords from the form
        ActionMessages messages = new ActionMessages();
        List<String> usernames = globalPasswordSetterForm.getUsernames();
        List<String> newPasswords = globalPasswordSetterForm.getNewPasswords();
        List<String> confirmPasswords = globalPasswordSetterForm.getConfirmPasswords();
        for(int c=0;c<usernames.size();c++) {
            String newPassword = newPasswords.get(c);
            if(newPassword.length()>0) {
                String username = usernames.get(c);
                Username un = aoConn.usernames.get(username);
                if(un==null) throw new SQLException("Unable to find Username: "+username);
                un.setPassword(newPassword);
                messages.add("confirmPasswords[" + c + "].confirmPasswords", new ActionMessage("password.globalPasswordSetter.field.confirmPasswords.passwordReset"));
                newPasswords.set(c, "");
                confirmPasswords.set(c, "");
            }
        }
        saveMessages(request, messages);

        return mapping.findForward("success");
    }

    public List<String> getPermissions() {
        List<String> permissions = new ArrayList<String>();
        permissions.add(AOServPermission.SET_BUSINESS_ADMINISTRATOR_PASSWORD);
        permissions.add(AOServPermission.SET_INTERBASE_SERVER_USER_PASSWORD);
        permissions.add(AOServPermission.SET_LINUX_SERVER_ACCOUNT_PASSWORD);
        permissions.add(AOServPermission.SET_MYSQL_SERVER_USER_PASSWORD);
        permissions.add(AOServPermission.SET_POSTGRES_SERVER_USER_PASSWORD);
        return permissions;
    }
}
