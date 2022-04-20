/*
 * aoweb-struts - Template webapp for legacy Struts-based site framework with AOServ Platform control panels.
 * Copyright (C) 2007-2009, 2015, 2016, 2021, 2022  AO Industries, Inc.
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

import com.aoapps.security.Identifier;
import com.aoapps.servlet.attribute.ScopeEE;
import com.aoapps.web.resources.registry.Registry;
import java.util.Map;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.collections4.map.LRUMap;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author  AO Industries, Inc.
 */
public class LoginAction extends PageAction {

  private static final Logger logger = Logger.getLogger(LoginAction.class.getName());

  /**
   * The maximum number of targets to store in session.  Oldest targets are removed once this number is hit.
   */
  private static final int MAX_TARGETS = 100;

  private static final ScopeEE.Session.Attribute<Map<String, Identifier>> TARGETS_ATTR =
    ScopeEE.SESSION.attribute(Constants.TARGETS);

  /**
   * Adds a target to the user session.
   *
   * @return  The target id or {@code null} if the target URL should not be used for post-login redirects
   */
  public static String addTarget(Supplier<HttpSession> session, String targetUrl) {
    if (targetUrl.endsWith("/login.do")) {
      return null;
    }
    Identifier id;
    HttpSession hs = session.get();
    Map<String, Identifier> targets = TARGETS_ATTR.context(hs).computeIfAbsent(__ -> new LRUMap<>(MAX_TARGETS));
    synchronized (targets) {
      id = targets.computeIfAbsent(targetUrl, __ -> new Identifier());
    }
    return id.toString();
  }

  /**
   * Gets the target URL from user session.
   */
  public static String getTargetUrl(HttpSession session, String target) {
    if (session != null && target != null && !target.isEmpty()) {
      Identifier id;
      try {
        id = new Identifier(target);
      } catch (IllegalArgumentException e) {
        if (logger.isLoggable(Level.FINER)) {
          logger.log(Level.FINER, "Invalid target: " + target, e);
        } else if (logger.isLoggable(Level.FINE)) {
          logger.log(Level.FINE, "Invalid target: " + target);
        }
        return null;
      }
      Map<String, Identifier> targets = TARGETS_ATTR.context(session).get();
      if (targets != null) {
        synchronized (targets) {
          // Sequential scan is OK since lookup by ID is rare - login time only
          for (Map.Entry<String, Identifier> e : targets.entrySet()) {
            if (e.getValue().equals(id)) {
              return e.getKey();
            }
          }
        }
      }
    }
    return null;
  }

  @Override
  public ActionForward execute(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response,
    Registry pageRegistry
  ) throws Exception {
    HttpSession session = request.getSession(false);
    if (session != null) {
      String target = request.getParameter("target");
      String targetUrl = getTargetUrl(session, target);
      if (targetUrl != null) {
        assert !targetUrl.endsWith("/login.do");
        Constants.AUTHENTICATION_TARGET.context(session).set(targetUrl);
      }
    }

    // Return success
    return mapping.findForward("success");
  }
}
