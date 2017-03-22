/*
 * aoweb-struts-core - Core API for legacy Struts-based site framework with AOServ Platform control panels.
 * Copyright (C) 2007-2009, 2015, 2016, 2017  AO Industries, Inc.
 *     support@aoindustries.com
 *     7262 Bull Pen Cir
 *     Mobile, AL 36695
 *
 * This file is part of aoweb-struts-core.
 *
 * aoweb-struts-core is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * aoweb-struts-core is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with aoweb-struts-core.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.aoindustries.website;

import com.aoindustries.aoserv.client.AOServConnector;
import com.aoindustries.aoserv.client.validator.UserId;
import com.aoindustries.validation.ValidationException;
import java.io.IOException;
import java.util.Locale;
import java.util.logging.Level;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Ensures the user is logged in.  Forwards to "login" if not logged in.  Otherwise, it sets the
 * request attribute "aoConn" and then calls
 * <code>execute(ActionMapping,ActionForm,HttpServletRequest,HttpServletResponse,Locale,Skin,AOServConnector)</code>.
 * The default implementation of this new <code>execute</code> method simply returns the mapping
 * of "success".<br />
 * <br />
 * More simply put, without overriding the new execute method, this action returns either the mapping
 * for "login" or "success".
 *
 * @author  AO Industries, Inc.
 */
abstract public class AuthenticatedAction extends SkinAction {

	@Override
	final public ActionForward execute(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response,
		SiteSettings siteSettings,
		Locale locale,
		Skin skin
	) throws Exception {
		// Handle login
		AOServConnector aoConn = getAoConn(request, response);
		if(aoConn==null) {
			String target = request.getRequestURL().toString();
			if(!target.endsWith("/login.do")) {
				String queryString = request.getQueryString();
				if(queryString!=null) target = target+'?'+queryString;
				request.getSession().setAttribute(Constants.AUTHENTICATION_TARGET, target);
			} else {
				request.getSession().removeAttribute(Constants.AUTHENTICATION_TARGET);
			}
			return mapping.findForward("login");
		}

		// Set request values
		request.setAttribute("aoConn", aoConn);

		return execute(mapping, form, request, response, siteSettings, locale, skin, aoConn);
	}

	/**
	 * Gets the AOServConnector that represents the actual login id.  This will not change when
	 * the user performs a switch user ("su")..
	 */
	public static AOServConnector getAuthenticatedAoConn(HttpServletRequest request, HttpServletResponse response) {
		return (AOServConnector)request.getSession().getAttribute(Constants.AUTHENTICATED_AO_CONN);
	}

	/**
	 * Gets the AOServConnector for the user or <code>null</code> if not logged in.  This also handles the "su" behavior that was
	 * stored in the session by <code>SkinAction</code>.
	 */
	public static AOServConnector getAoConn(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		AOServConnector authenticatedAoConn = getAuthenticatedAoConn(request, response);
		// Not logged in
		if(authenticatedAoConn==null) return null;

		// Is a "su" requested?
		String su=(String)session.getAttribute(Constants.SU_REQUESTED);
		if(su!=null) {
			session.removeAttribute(Constants.SU_REQUESTED);
			try {
				AOServConnector aoConn;
				if(su.isEmpty()) {
					aoConn = authenticatedAoConn;
				} else {
					try {
						UserId suId = UserId.valueOf(su);
						aoConn = authenticatedAoConn.switchUsers(suId);
					} catch(ValidationException e) {
						// Ignore requests for invalid su
						aoConn = authenticatedAoConn;
					}
				}
				session.setAttribute(Constants.AO_CONN, aoConn);
				return aoConn;
			} catch(IOException err) {
				LogFactory.getLogger(session.getServletContext(), AuthenticatedAction.class).log(Level.SEVERE, null, err);
			}
		}

		// Look for previous effective user
		AOServConnector aoConn = (AOServConnector)session.getAttribute(Constants.AO_CONN);
		if(aoConn!=null) return aoConn;

		// Default effective user to authenticated user
		session.setAttribute(Constants.AO_CONN, authenticatedAoConn);
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
		SiteSettings siteSettings,
		Locale locale,
		Skin skin,
		AOServConnector aoConn
	) throws Exception {
		return mapping.findForward("success");
	}
}
