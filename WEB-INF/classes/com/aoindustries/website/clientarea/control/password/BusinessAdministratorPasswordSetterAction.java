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
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

/**
 * Prepares for business administrator password setting.  Populates lists in businessAdministratorPasswordSetterForm.
 *
 * @author  AO Industries, Inc.
 */
public class BusinessAdministratorPasswordSetterAction extends AuthenticatedAction {

    static void populate(AOServConnector aoConn, BusinessAdministratorPasswordSetterForm businessAdministratorPasswordSetterForm) {
        List<BusinessAdministrator> bas = aoConn.businessAdministrators.getRows();

        List<String> packages = new ArrayList<String>(bas.size());
        List<String> usernames = new ArrayList<String>(bas.size());
        List<String> newPasswords = new ArrayList<String>(bas.size());
        List<String> confirmPasswords = new ArrayList<String>(bas.size());
        for(BusinessAdministrator ba : bas) {
            if(ba.canSetPassword()) {
                Username un = ba.getUsername();
                packages.add(un.getPackage().getName());
                usernames.add(un.getUsername());
                newPasswords.add("");
                confirmPasswords.add("");
            }
        }

        // Store to the form
        businessAdministratorPasswordSetterForm.setPackages(packages);
        businessAdministratorPasswordSetterForm.setUsernames(usernames);
        businessAdministratorPasswordSetterForm.setNewPasswords(newPasswords);
        businessAdministratorPasswordSetterForm.setConfirmPasswords(confirmPasswords);
    }

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
        BusinessAdministratorPasswordSetterAction.populate(aoConn, businessAdministratorPasswordSetterForm);
        return mapping.findForward("success");
    }
}
