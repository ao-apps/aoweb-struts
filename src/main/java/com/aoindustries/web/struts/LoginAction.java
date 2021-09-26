/*
 * aoweb-struts - Template webapp for legacy Struts-based site framework with AOServ Platform control panels.
 * Copyright (C) 2007-2009, 2015, 2016, 2021  AO Industries, Inc.
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
 * along with aoweb-struts.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.aoindustries.web.struts;

import com.aoapps.security.Identifier;
import com.aoapps.web.resources.registry.Registry;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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

	private static class Target implements Serializable {

		private static final long serialVersionUID = 1L;

		private final Identifier id;
		private final String targetUrl;

		private Target(Identifier id, String targetUrl) {
			this.id = id;
			this.targetUrl = targetUrl;
		}
	}

	/**
	 * Adds a target to the user session.
	 *
	 * @return  The target id or {@code null} if the target URL should not be used for post-login redirects
	 */
	public static String addTarget(Supplier<HttpSession> session, String targetUrl) {
		if(targetUrl.endsWith("/login.do")) {
			return null;
		}
		Identifier id = new Identifier();
		Target target = new Target(id, targetUrl);
		HttpSession hs = session.get();
		synchronized(hs) {
			@SuppressWarnings("unchecked")
			LinkedList<Target> targets = (LinkedList<Target>)hs.getAttribute(Constants.TARGETS);
			if(targets == null) {
				targets = new LinkedList<>();
				hs.setAttribute(Constants.TARGETS, targets);
			}
			while(targets.size() >= MAX_TARGETS) {
				targets.removeLast();
			}
			targets.addFirst(target);
		}
		return id.toString();
	}

	/**
	 * Gets the target URL from user session.
	 */
	public static String getTargetUrl(HttpSession session, String target) {
		if(session != null && target != null && !target.isEmpty()) {
			Identifier id;
			try {
				id = new Identifier(target);
			} catch(IllegalArgumentException e) {
				if(logger.isLoggable(Level.FINER)) {
					logger.log(Level.FINER, "Invalid target: " + target, e);
				} else if(logger.isLoggable(Level.FINE)) {
					logger.log(Level.FINE, "Invalid target: " + target);
				}
				return null;
			}
			synchronized(session) {
				@SuppressWarnings("unchecked")
				LinkedList<Target> targets = (LinkedList<Target>)session.getAttribute(Constants.TARGETS);
				if(targets != null) {
					for(Target t : targets) {
						if(t.id.equals(id)) {
							return t.targetUrl;
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
		if(session != null) {
			String target = request.getParameter("target");
			String targetUrl = getTargetUrl(session, target);
			if(targetUrl != null) {
				assert !targetUrl.endsWith("/login.do");
				session.setAttribute(Constants.AUTHENTICATION_TARGET, targetUrl);
			}
		}

		// Return success
		return mapping.findForward("success");
	}
}
