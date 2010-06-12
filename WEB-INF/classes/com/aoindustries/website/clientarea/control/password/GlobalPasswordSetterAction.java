package com.aoindustries.website.clientarea.control.password;

/*
 * Copyright 2000-2009 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
 * All rights reserved.
 */
import com.aoindustries.aoserv.client.AOServConnector;
import com.aoindustries.aoserv.client.AOServPermission;
import com.aoindustries.aoserv.client.Username;
import com.aoindustries.aoserv.client.command.CommandName;
import com.aoindustries.aoserv.client.command.SetUsernamePasswordCommand;
import com.aoindustries.website.PermissionAction;
import com.aoindustries.website.SiteSettings;
import com.aoindustries.website.Skin;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Prepares for business administrator password setting.  Populates lists in globalPasswordSetterForm.
 *
 * @author  AO Industries, Inc.
 */
public class GlobalPasswordSetterAction extends PermissionAction {

    @Override
    public ActionForward executePermissionGranted(
        ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response,
        SiteSettings siteSettings,
        Locale locale,
        Skin skin,
        AOServConnector<?,?> aoConn
    ) throws Exception {
        GlobalPasswordSetterForm globalPasswordSetterForm = (GlobalPasswordSetterForm)form;

        SortedSet<Username> uns = new TreeSet<Username>(aoConn.getUsernames().getSet());

        List<String> businesses = new ArrayList<String>(uns.size());
        List<String> usernames = new ArrayList<String>(uns.size());
        List<String> newPasswords = new ArrayList<String>(uns.size());
        List<String> confirmPasswords = new ArrayList<String>(uns.size());
        for(Username un : uns) {
            if(!new SetUsernamePasswordCommand(un.getUsername(), null).validate(aoConn).containsKey(SetUsernamePasswordCommand.PARAM_USERNAME)) {
                businesses.add(un.getBusiness().getAccounting().getAccounting());
                usernames.add(un.getUsername().getId());
                newPasswords.add("");
                confirmPasswords.add("");
            }
        }

        // Store to the form
        globalPasswordSetterForm.setBusinesses(businesses);
        globalPasswordSetterForm.setUsernames(usernames);
        globalPasswordSetterForm.setNewPasswords(newPasswords);
        globalPasswordSetterForm.setConfirmPasswords(confirmPasswords);

        return mapping.findForward("success");
    }

    public Set<AOServPermission.Permission> getPermissions() {
        return CommandName.set_username_password.getPermissions();
    }
}
