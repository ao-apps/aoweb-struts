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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Finds the virtualServer and sets request attribute "virtualServer" if accessible.
 *
 * @author  AO Industries, Inc.
 */
public class VncViewerAction extends PermissionAction {

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
        try {
            VirtualServer virtualServer = aoConn.getVirtualServers().get(Integer.parseInt(request.getParameter("virtualServer")));
            if(virtualServer==null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return null;
            }
            String vncPassword = virtualServer.getVncPassword();
            if(vncPassword==null) {
                // VNC disabled
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return null;
            }
            if(vncPassword.equals(AOServObject.FILTERED)) {
                // Not accessible
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
                return null;
            }
            request.setAttribute("virtualServer", virtualServer);
            return mapping.findForward("success");
        } catch(NumberFormatException err) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }
    }

    @Override
    public Set<AOServPermission.Permission> getPermissions() {
        return CommandName.request_vnc_console_access.getPermissions();
    }
}
