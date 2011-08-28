package com.aoindustries.website.clientarea.control.vnc;

/*
 * Copyright 2009-2011 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
 * All rights reserved.
 */
import com.aoindustries.aoserv.client.AOServConnector;
import com.aoindustries.aoserv.client.AOServObject;
import com.aoindustries.aoserv.client.AOServPermission;
import com.aoindustries.aoserv.client.VirtualServer;
import com.aoindustries.aoserv.client.command.CommandName;
import com.aoindustries.website.PermissionAction;
import com.aoindustries.website.SiteSettings;
import com.aoindustries.website.Skin;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Gets the list of accessible consoles and stores as request attribute <code>vncVirtualServers</code>.
 *
 * @author  AO Industries, Inc.
 */
public class VncConsoleAction extends PermissionAction {

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
        SortedSet<VirtualServer> vncVirtualServers = new TreeSet<VirtualServer>();
        for(VirtualServer virtualServer : aoConn.getVirtualServers().getSet()) {
            String vncPassword = virtualServer.getVncPassword();
            if(vncPassword!=null && !vncPassword.equals(AOServObject.FILTERED)) vncVirtualServers.add(virtualServer);
        }
        request.setAttribute("vncVirtualServers", vncVirtualServers);

        return mapping.findForward("success");
    }

    @Override
    public Set<AOServPermission.Permission> getPermissions() {
        return CommandName.request_vnc_console_access.getPermissions();
    }
}
