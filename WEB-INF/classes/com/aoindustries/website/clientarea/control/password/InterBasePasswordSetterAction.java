package com.aoindustries.website.clientarea.control.password;

/*
 * Copyright 2000-2007 by AO Industries, Inc.,
 * 816 Azalea Rd, Mobile, Alabama, 36693, U.S.A.
 * All rights reserved.
 */
import com.aoindustries.aoserv.client.AOServConnector;
import com.aoindustries.aoserv.client.AOServPermission;
import com.aoindustries.aoserv.client.InterBaseServerUser;
import com.aoindustries.aoserv.client.InterBaseUser;
import com.aoindustries.aoserv.client.Username;
import com.aoindustries.website.PermissionAction;
import com.aoindustries.website.Skin;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

/**
 * Prepares for business administrator password setting.  Populates lists in InterBasePasswordSetterForm.
 *
 * @author  AO Industries, Inc.
 */
public class InterBasePasswordSetterAction extends PermissionAction {

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

        List<InterBaseServerUser> isus = aoConn.interBaseServerUsers.getRows();

        List<String> packages = new ArrayList<String>(isus.size());
        List<String> usernames = new ArrayList<String>(isus.size());
        List<String> fullNames = new ArrayList<String>(isus.size());
        List<String> aoServers = new ArrayList<String>(isus.size());
        List<String> newPasswords = new ArrayList<String>(isus.size());
        List<String> confirmPasswords = new ArrayList<String>(isus.size());
        for(InterBaseServerUser isu : isus) {
            if(isu.canSetPassword()) {
                InterBaseUser iu = isu.getInterBaseUser();
                Username un = iu.getUsername();
                packages.add(un.getPackage().getName());
                usernames.add(un.getUsername());
                fullNames.add(iu.getFullName());
                aoServers.add(isu.getAOServer().getServer().getHostname());
                newPasswords.add("");
                confirmPasswords.add("");
            }
        }

        // Store to the form
        interBasePasswordSetterForm.setPackages(packages);
        interBasePasswordSetterForm.setUsernames(usernames);
        interBasePasswordSetterForm.setFullNames(fullNames);
        interBasePasswordSetterForm.setAoServers(aoServers);
        interBasePasswordSetterForm.setNewPasswords(newPasswords);
        interBasePasswordSetterForm.setConfirmPasswords(confirmPasswords);

        return mapping.findForward("success");
    }
    
    public List<String> getPermissions() {
        return Collections.singletonList(AOServPermission.SET_INTERBASE_SERVER_USER_PASSWORD);
    }
}
