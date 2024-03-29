/*
 * aoweb-struts - Template webapp for legacy Struts-based site framework with AOServ Platform control panels.
 * Copyright (C) 2007-2009, 2015, 2016, 2017, 2018, 2019, 2020, 2021, 2022  AO Industries, Inc.
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

package com.aoindustries.web.struts;

import com.aoapps.lang.validation.ValidationException;
import com.aoapps.servlet.attribute.AttributeEE;
import com.aoapps.web.resources.registry.Registry;
import com.aoindustries.aoserv.client.AoservConnector;
import com.aoindustries.aoserv.client.linux.User;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Ensures the user is logged in.  Forwards to "login" if not logged in.  Otherwise, it sets the
 * request attribute {@link Constants#AO_CONN} and then calls
 * <code>execute(ActionMapping,ActionForm,HttpServletRequest,HttpServletResponse,Locale,Skin,AoservConnector)</code>.
 * The default implementation of this new <code>execute</code> method simply returns the mapping
 * of "success".<br>
 * <br>
 * More simply put, without overriding the new execute method, this action returns either the mapping
 * for "login" or "success".
 *
 * @author  AO Industries, Inc.
 */
public abstract class AuthenticatedAction extends PageAction {

  private static final Logger logger = Logger.getLogger(AuthenticatedAction.class.getName());

  @Override
  public final ActionForward execute(
      ActionMapping mapping,
      ActionForm form,
      HttpServletRequest request,
      HttpServletResponse response,
      Registry pageRegistry
  ) throws Exception {
    // Handle login
    AoservConnector aoConn = getAoConn(request, response);
    if (aoConn == null) {
      String target = request.getRequestURL().toString();
      if (!target.endsWith("/login.do")) {
        String queryString = request.getQueryString();
        if (queryString != null) {
          target = target + '?' + queryString;
        }
        Constants.AUTHENTICATION_TARGET.context(request.getSession()).set(target);
      } else {
        Constants.AUTHENTICATION_TARGET.context(request.getSession(false)).remove();
      }
      return mapping.findForward("login");
    }

    // Set request values
    Constants.AO_CONN.context(request).set(aoConn);

    return execute(mapping, form, request, response, aoConn);
  }

  /**
   * Gets the AoservConnector that represents the actual login id.  This will not change when
   * the user performs a switch user ({@link Constants#SU})..
   */
  public static AoservConnector getAuthenticatedAoConn(HttpServletRequest request, HttpServletResponse response) {
    return Constants.AUTHENTICATED_AO_CONN.context(request.getSession(false)).get();
  }

  /**
   * Gets the AoservConnector for the user or <code>null</code> if not logged in.
   * This also handles the {@link Constants#SU} behavior that was stored in the session by {@link SwitchUserRequestListener}.
   */
  public static AoservConnector getAoConn(HttpServletRequest request, HttpServletResponse response) {
    AoservConnector authenticatedAoConn = getAuthenticatedAoConn(request, response);
    // Not logged in
    if (authenticatedAoConn == null) {
      return null;
    }

    HttpSession session = request.getSession();
    // Is a switch-user requested?
    AttributeEE.Session<String> suRequestedAttribute = Constants.SU_REQUESTED.context(session);
    String su = suRequestedAttribute.get();
    if (su != null) {
      suRequestedAttribute.remove();
      try {
        AoservConnector aoConn;
        if (su.isEmpty()) {
          aoConn = authenticatedAoConn;
        } else {
          try {
            User.Name suId = User.Name.valueOf(su);
            aoConn = authenticatedAoConn.switchUsers(suId);
          } catch (ValidationException e) {
            // Ignore requests for invalid su
            aoConn = authenticatedAoConn;
          }
        }
        Constants.AO_CONN.context(session).set(aoConn);
        return aoConn;
      } catch (IOException err) {
        logger.log(Level.SEVERE, null, err);
      }
    }

    // Look for previous effective user
    AoservConnector aoConn = Constants.AO_CONN.context(session).get();
    if (aoConn != null) {
      return aoConn;
    }

    // Default effective user to authenticated user
    Constants.AO_CONN.context(session).set(authenticatedAoConn);
    return authenticatedAoConn;
  }

  /**
   * Once authentication has been handled, this version of the execute method is invoked.
   * The default implementation of this method simply returns the mapping of "success".
   */
  public ActionForward execute(
      ActionMapping mapping,
      ActionForm form,
      HttpServletRequest request,
      HttpServletResponse response,
      AoservConnector aoConn
  ) throws Exception {
    return mapping.findForward("success");
  }
}
