package com.aoindustries.website.clientarea.control.password;

/*
 * Copyright 2000-2007 by AO Industries, Inc.,
 * 816 Azalea Rd, Mobile, Alabama, 36693, U.S.A.
 * All rights reserved.
 */
import com.aoindustries.aoserv.client.AOServConnector;
import com.aoindustries.aoserv.client.BusinessAdministrator;
import com.aoindustries.aoserv.client.Username;
import com.aoindustries.website.AuthenticatedAction;
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
 * Prepares for business administrator password setting.  Populates lists in businessAdministratorPasswordSetterForm.
 *
 * @author  AO Industries, Inc.
 */
public class BusinessAdministratorPasswordSetterCompletedAction extends AuthenticatedAction {

    public ActionForward execute(
        ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response,
        Locale locale,
        Skin skin,
        AOServConnector aoConn
    ) throws Exception {
        BusinessAdministratorPasswordSetterForm businessAdministratorPasswordSetterForm = (BusinessAdministratorPasswordSetterForm)form;

        // Validation
        ActionMessages errors = businessAdministratorPasswordSetterForm.validate(mapping, request);
        if(errors!=null && !errors.isEmpty()) {
            saveErrors(request, errors);
            return mapping.findForward("input");
        }

        // Reset passwords here and clear the passwords from the form
        ActionMessages messages = new ActionMessages();
        List<String> usernames = businessAdministratorPasswordSetterForm.getUsernames();
        List<String> newPasswords = businessAdministratorPasswordSetterForm.getNewPasswords();
        List<String> confirmPasswords = businessAdministratorPasswordSetterForm.getConfirmPasswords();
        for(int c=0;c<usernames.size();c++) {
            String newPassword = newPasswords.get(c);
            if(newPassword.length()>0) {
                String username = usernames.get(c);
                BusinessAdministrator ba = aoConn.businessAdministrators.get(username);
                if(ba==null) throw new SQLException("Unable to find BusinessAdministrator: "+username);
                ba.setPassword(newPassword);
                messages.add("confirmPasswords[" + c + "].confirmPasswords", new ActionMessage("password.BusinessAdministratorPasswordSetter.field.confirmPasswords.passwordReset"));
                newPasswords.set(c, "");
                confirmPasswords.set(c, "");
            }
        }
        saveMessages(request, messages);

        return mapping.findForward("success");
    }
}
