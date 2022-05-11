/*
 * aoweb-struts - Template webapp for legacy Struts-based site framework with AOServ Platform control panels.
 * Copyright (C) 2009, 2016, 2018, 2019, 2021, 2022  AO Industries, Inc.
 *     support@aoindustries.com
 *     7262 Bull Pen Cir
 *     Mobile, AL 36695
 *
 * This file is part of aoweb-struts.
 *
 * aoweb-struts is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * aoweb-struts is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with aoweb-struts.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.aoindustries.web.struts.clientarea.control.vnc;

import com.aoindustries.aoserv.client.AoservConnector;
import com.aoindustries.aoserv.client.infrastructure.VirtualServer;
import com.aoindustries.aoserv.client.master.Permission;
import com.aoindustries.aoserv.client.schema.AoservProtocol;
import com.aoindustries.web.struts.PermissionAction;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
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
      AoservConnector aoConn
  ) throws Exception {
    List<VirtualServer> virtualServers = aoConn.getInfrastructure().getVirtualServer().getRows();
    List<VirtualServer> vncVirtualServers = new ArrayList<>(virtualServers.size());
    for (VirtualServer virtualServer : virtualServers) {
      String vncPassword = virtualServer.getVncPassword();
      if (vncPassword != null && !vncPassword.equals(AoservProtocol.FILTERED)) {
        vncVirtualServers.add(virtualServer);
      }
    }
    request.setAttribute("vncVirtualServers", vncVirtualServers);

    return mapping.findForward("success");
  }

  @Override
  public Set<Permission.Name> getPermissions() {
    return Collections.singleton(Permission.Name.vnc_console);
  }
}
