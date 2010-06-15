package com.aoindustries.website.clientarea.control.password;

/*
 * Copyright 2000-2010 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
 * All rights reserved.
 */
import com.aoindustries.aoserv.client.AOServConnector;
import com.aoindustries.aoserv.client.AOServPermission;
import com.aoindustries.aoserv.client.BusinessAdministrator;
import com.aoindustries.aoserv.client.Username;
import com.aoindustries.aoserv.client.command.SetBusinessAdministratorPasswordCommand;
import com.aoindustries.util.Collections;
import com.aoindustries.website.AuthenticatedAction;
import com.aoindustries.website.SiteSettings;
import com.aoindustries.website.Skin;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Prepares for business administrator password setting.  Populates lists in businessAdministratorPasswordSetterForm.
 *
 * @author  AO Industries, Inc.
 */
public class BusinessAdministratorPasswordSetterAction extends AuthenticatedAction {

    @Override
    public ActionForward execute(
        ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response,
        SiteSettings siteSettings,
        Skin skin,
        AOServConnector<?,?> aoConn
    ) throws Exception {
        BusinessAdministratorPasswordSetterForm businessAdministratorPasswordSetterForm = (BusinessAdministratorPasswordSetterForm)form;

        BusinessAdministrator thisBA = aoConn.getThisBusinessAdministrator();
        
        SortedSet<BusinessAdministrator> bas =
            thisBA.hasPermission(AOServPermission.Permission.set_business_administrator_password)
            ? new TreeSet<BusinessAdministrator>(aoConn.getBusinessAdministrators().getSet())
            : Collections.singletonSortedSet(thisBA)
        ;

        List<String> businesses = new ArrayList<String>(bas.size());
        List<String> usernames = new ArrayList<String>(bas.size());
        List<String> newPasswords = new ArrayList<String>(bas.size());
        List<String> confirmPasswords = new ArrayList<String>(bas.size());
        for(BusinessAdministrator ba : bas) {
            if(!new SetBusinessAdministratorPasswordCommand(ba.getUsername().getUsername(), null).validate(aoConn).containsKey(SetBusinessAdministratorPasswordCommand.PARAM_USERNAME)) {
                Username un = ba.getUsername();
                businesses.add(un.getBusiness().getAccounting().getAccounting());
                usernames.add(un.getUsername().getId());
                newPasswords.add("");
                confirmPasswords.add("");
            }
        }

        // Store to the form
        businessAdministratorPasswordSetterForm.setBusinesses(businesses);
        businessAdministratorPasswordSetterForm.setUsernames(usernames);
        businessAdministratorPasswordSetterForm.setNewPasswords(newPasswords);
        businessAdministratorPasswordSetterForm.setConfirmPasswords(confirmPasswords);

        return mapping.findForward("success");
    }
}
