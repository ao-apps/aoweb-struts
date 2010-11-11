package com.aoindustries.website.clientarea.control.password;

/*
 * Copyright 2000-2010 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
 * All rights reserved.
 */
import com.aoindustries.aoserv.client.AOServConnector;
import com.aoindustries.aoserv.client.AOServPermission;
import com.aoindustries.aoserv.client.Username;
import com.aoindustries.aoserv.client.command.CommandName;
import com.aoindustries.aoserv.client.command.SetUsernamePasswordCommand;
import com.aoindustries.aoserv.client.validator.UserId;
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
public class GlobalPasswordSetterCompletedAction extends PermissionAction {

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
                UserId username = UserId.valueOf(usernames.get(c));
                Username un = aoConn.getUsernames().get(username);
                new SetUsernamePasswordCommand(un, newPassword).execute(aoConn);
                messages.add("confirmPasswords[" + c + "].confirmPasswords", new ActionMessage("password.globalPasswordSetter.field.confirmPasswords.passwordReset"));
                newPasswords.set(c, "");
                confirmPasswords.set(c, "");
            }
        }
        saveMessages(request, messages);

        return mapping.findForward("success");
    }

    @Override
    public Set<AOServPermission.Permission> getPermissions() {
        return CommandName.set_username_password.getPermissions();
    }
}
