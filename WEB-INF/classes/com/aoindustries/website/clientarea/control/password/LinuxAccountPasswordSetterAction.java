/*
 * Copyright 2000-2011 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
 * All rights reserved.
 */
package com.aoindustries.website.clientarea.control.password;

import com.aoindustries.aoserv.client.AOServConnector;
import com.aoindustries.aoserv.client.AOServPermission;
import com.aoindustries.aoserv.client.LinuxAccount;
import com.aoindustries.aoserv.client.Username;
import com.aoindustries.aoserv.client.command.CommandName;
import com.aoindustries.aoserv.client.command.SetLinuxAccountPasswordCommand;
import com.aoindustries.website.PermissionAction;
import com.aoindustries.website.SiteSettings;
import com.aoindustries.website.Skin;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Prepares for business administrator password setting.  Populates lists in linuxAccountPasswordSetterForm.
 *
 * @author  AO Industries, Inc.
 */
public class LinuxAccountPasswordSetterAction extends PermissionAction {

    @Override
    public ActionForward executePermissionGranted(
        ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response,
        SiteSettings siteSettings,
        Skin skin,
        AOServConnector aoConn
    ) throws Exception {
        LinuxAccountPasswordSetterForm linuxAccountPasswordSetterForm = (LinuxAccountPasswordSetterForm)form;

        SortedSet<LinuxAccount> las = new TreeSet<LinuxAccount>(aoConn.getLinuxAccounts().getSet());

        List<String> businesses = new ArrayList<String>(las.size());
        List<String> usernames = new ArrayList<String>(las.size());
        List<String> aoServers = new ArrayList<String>(las.size());
        List<String> newPasswords = new ArrayList<String>(las.size());
        List<String> confirmPasswords = new ArrayList<String>(las.size());
        for(LinuxAccount la : las) {
            if(new SetLinuxAccountPasswordCommand(la, "X1234Yzw").checkExecute(aoConn).isEmpty()) {
                Username un = la.getUsername();
                businesses.add(un.getBusiness().getAccounting().toString());
                usernames.add(un.getUsername().toString());
                aoServers.add(la.getAoServer().getHostname().toString());
                newPasswords.add("");
                confirmPasswords.add("");
            }
        }

        // Store to the form
        linuxAccountPasswordSetterForm.setBusinesses(businesses);
        linuxAccountPasswordSetterForm.setUsernames(usernames);
        linuxAccountPasswordSetterForm.setAoServers(aoServers);
        linuxAccountPasswordSetterForm.setNewPasswords(newPasswords);
        linuxAccountPasswordSetterForm.setConfirmPasswords(confirmPasswords);

        return mapping.findForward("success");
    }
    
    @Override
    public Set<AOServPermission.Permission> getPermissions() {
        return CommandName.set_linux_account_password.getPermissions();
    }
}
