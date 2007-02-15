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
import com.aoindustries.website.Constants;
import com.aoindustries.website.PermissionAction;
import com.aoindustries.website.Skin;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

/**
 * Prepares for business administrator password setting.  Populates lists in globalPasswordSetterForm.
 *
 * @author  AO Industries, Inc.
 */
public class GlobalPasswordSetterAction extends PermissionAction {

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

        List<Username> uns = aoConn.usernames.getRows();

        List<String> packages = new ArrayList<String>(uns.size());
        List<String> usernames = new ArrayList<String>(uns.size());
        List<String> newPasswords = new ArrayList<String>(uns.size());
        List<String> confirmPasswords = new ArrayList<String>(uns.size());
        for(Username un : uns) {
            if(un.canSetPassword()) {
                packages.add(un.getPackage().getName());
                usernames.add(un.getUsername());
                newPasswords.add("");
                confirmPasswords.add("");
            }
        }

        // Store to the form
        globalPasswordSetterForm.setPackages(packages);
        globalPasswordSetterForm.setUsernames(usernames);
        globalPasswordSetterForm.setNewPasswords(newPasswords);
        globalPasswordSetterForm.setConfirmPasswords(confirmPasswords);

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
