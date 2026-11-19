/*
 * aoweb-struts - Template webapp for legacy Struts-based site framework with AOServ Platform control panels.
 * Copyright (C) 2026  AO Industries, Inc.
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
import com.aoindustries.web.struts.AuthenticatedServlet;
import com.aoindustries.web.struts.Constants;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Handles virtual server control requests.
 *
 * @author  AO Industries, Inc.
 */
public abstract class ControlVirtualServerServlet extends AuthenticatedServlet {

  private static final long serialVersionUID = 1L;

  @Override
  public final void doGet(HttpServletRequest request, HttpServletResponse response, AoservConnector aoConn) throws IOException {
    response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
  }

  @Override
  protected final void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    // Must be logged in
    HttpSession session = request.getSession(false);
    // TODO: Review all uses of getSession(false) and make sure they are null-safe like this one.
    //       Or carefully review ao-servlet-util null handling.
    AoservConnector aoConn = (session == null) ? null : Constants.AO_CONN.context(session).get();
    if (aoConn == null) {
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    } else {
      try {
        int id = Integer.parseInt(request.getParameter("virtual-server"));
        VirtualServer virtualServer = aoConn.getInfrastructure().getVirtualServer().get(id);
        if (virtualServer == null) {
          response.sendError(HttpServletResponse.SC_NOT_FOUND);
        } else {
          controlVirtualServer(virtualServer);
        }
      } catch (SQLException e) {
        throw new ServletException(e);
      }
    }
  }

  protected abstract void controlVirtualServer(VirtualServer virtualServer) throws IOException, SQLException;
}
