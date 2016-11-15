/*
 * aoweb-struts-core - Core API for legacy Struts-based site framework with AOServ Platform control panels.
 * Copyright (C) 2000-2009, 2016  AO Industries, Inc.
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
package com.aoindustries.website.clientarea.ticket;

import com.aoindustries.aoserv.client.AOServConnector;
import com.aoindustries.aoserv.client.AOServPermission;
import com.aoindustries.aoserv.client.Business;
import com.aoindustries.aoserv.client.Ticket;
import com.aoindustries.website.PermissionAction;
import com.aoindustries.website.SiteSettings;
import com.aoindustries.website.Skin;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Provides the list of tickets.
 *
 * @author  AO Industries, Inc.
 */
public class EditAction extends PermissionAction {

	@Override
	public ActionForward executePermissionGranted(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response,
		SiteSettings siteSettings,
		Locale locale,
		Skin skin,
		AOServConnector aoConn
	) throws Exception {
		TicketForm ticketForm = (TicketForm)form;

		// Look for the existing ticket
		String pkeyS = request.getParameter("pkey");
		if(pkeyS==null) {
			request.setAttribute(com.aoindustries.website.Constants.HTTP_SERVLET_RESPONSE_STATUS, HttpServletResponse.SC_BAD_REQUEST);
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "pkey required");
			return null;
		}
		int pkey;
		try {
			pkey = Integer.parseInt(pkeyS);
		} catch(NumberFormatException err) {
			request.setAttribute(com.aoindustries.website.Constants.HTTP_SERVLET_RESPONSE_STATUS, HttpServletResponse.SC_BAD_REQUEST);
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid pkey");
			return null;
		}
		Ticket ticket = aoConn.getTickets().get(pkey);
		if(ticket==null) {
			request.setAttribute(com.aoindustries.website.Constants.HTTP_SERVLET_RESPONSE_STATUS, HttpServletResponse.SC_NOT_FOUND);
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "Ticket not found");
			return null;
		}

		// Populate the ticket form
		Business business = ticket.getBusiness();
		ticketForm.setAccounting(business==null ? "" : business.getAccounting().toString());
		ticketForm.setClientPriority(ticket.getClientPriority().getPriority());
		ticketForm.setContactEmails(ticket.getContactEmails());
		ticketForm.setContactPhoneNumbers(ticket.getContactPhoneNumbers());
		ticketForm.setDetails(ticket.getDetails());
		ticketForm.setSummary(ticket.getSummary());

		// Set the request attributes
		request.setAttribute("ticket", ticket);

		return mapping.findForward("success");
	}

	private static final List<AOServPermission.Permission> permissions = new ArrayList<AOServPermission.Permission>(2);
	private static final List<AOServPermission.Permission> unmodifiablePermissions = Collections.unmodifiableList(permissions);
	static {
		permissions.add(AOServPermission.Permission.add_ticket);
		permissions.add(AOServPermission.Permission.edit_ticket);
	}
	@Override
	public List<AOServPermission.Permission> getPermissions() {
		return unmodifiablePermissions;
	}
}
