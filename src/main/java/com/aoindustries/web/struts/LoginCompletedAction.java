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

import com.aoapps.net.URIEncoder;
import com.aoapps.web.resources.registry.Registry;
import com.aoindustries.aoserv.client.AOServConnector;
import com.aoindustries.aoserv.client.linux.User;
import java.io.IOException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

/**
 * @author  AO Industries, Inc.
 */
public class LoginCompletedAction extends PageAction {

  private static final Logger logger = Logger.getLogger(LoginCompletedAction.class.getName());

  private static final com.aoapps.lang.i18n.Resources RESOURCES =
      com.aoapps.lang.i18n.Resources.getResources(ResourceBundle::getBundle, LoginCompletedAction.class);

  @Override
  public ActionForward execute(
      ActionMapping mapping,
      ActionForm form,
      HttpServletRequest request,
      HttpServletResponse response,
      Registry pageRegistry
  ) throws Exception {
    LoginForm loginForm = (LoginForm) form;

    ActionMessages errors = loginForm.validate(mapping, request);
    if (errors != null && !errors.isEmpty()) {
      saveErrors(request, errors);
      return mapping.findForward("input");
    }

    User.Name username = User.Name.valueOf(loginForm.getUsername());
    String password = loginForm.getPassword();

    try {
      // Get connector
      AOServConnector aoConn = AOServConnector.getConnector(username, password);
      aoConn.ping();

      // Store in session
      HttpSession session = request.getSession();
      Constants.AUTHENTICATED_AO_CONN.context(session).set(aoConn);
      Constants.AO_CONN.context(session).set(aoConn);

      // Try redirect
      String target = Constants.AUTHENTICATION_TARGET.context(session).replace(null);
      if (target != null && !target.isEmpty()) {
        response.sendRedirect(
            response.encodeRedirectURL(
                URIEncoder.encodeURI(
                    target
                )
            )
        );
        return null;
      }

      // Redirect to the clientarea instead of returning mapping because of switch from HTTPS to HTTP
      response.sendRedirect(
          response.encodeRedirectURL(
              URIEncoder.encodeURI(
                  Skin.getSkin(request).getUrlBase(request) + "clientarea/index.do"
              )
          )
      );
      return null;
      // Return success
      //return mapping.findForward("success");
    } catch (IOException err) {
      String message = err.getMessage();
      if (message != null) {
        if (message.contains("Unable to find Administrator")) {
          message = RESOURCES.getMessage("accountNotFound");
        } else if (message.contains("Connection attempted with invalid password")) {
          message = RESOURCES.getMessage("badPassword");
        } else if (message.contains("Administrator disabled")) {
          message = RESOURCES.getMessage("accountDisabled");
        } else {
          message = null;
        }
      }
      if (message != null) {
        Constants.AUTHENTICATION_MESSAGE.context(request).set(message);
      } else {
        logger.log(Level.SEVERE, null, err);
      }
      return mapping.findForward("failure");
    }
  }
}
