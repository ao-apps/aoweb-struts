/*
 * aoweb-struts - Template webapp for legacy Struts-based site framework with AOServ Platform control panels.
 * Copyright (C) 2007-2009, 2016, 2019, 2021  AO Industries, Inc.
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

import com.aoapps.net.URIEncoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author  AO Industries, Inc.
 */
public class LogoutAction extends SkinAction {

	@Override
	public ActionForward execute(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response,
		Skin skin
	) throws Exception {
		// Handle logout
		HttpSession session = request.getSession(false);
		if(session != null) {
			session.removeAttribute(Constants.AO_CONN);
			session.removeAttribute(Constants.AUTHENTICATED_AO_CONN);
			session.removeAttribute(Constants.AUTHENTICATION_TARGET);
			session.removeAttribute(Constants.SU_REQUESTED);
		}

		// Try redirect
		String target = request.getParameter("target");
		if(target!=null && target.length()>0) {
			response.sendRedirect(
				response.encodeRedirectURL(
					URIEncoder.encodeURI(
						target
					)
				)
			);
			return null;
		}

		return mapping.findForward("success");
	}
}
