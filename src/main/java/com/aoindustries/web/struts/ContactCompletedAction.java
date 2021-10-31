/*
 * aoweb-struts - Template webapp for legacy Struts-based site framework with AOServ Platform control panels.
 * Copyright (C) 2000-2009, 2015, 2016, 2018, 2021  AO Industries, Inc.
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

import com.aoapps.net.Email;
import com.aoapps.web.resources.registry.Registry;
import com.aoindustries.aoserv.client.AOServConnector;
import com.aoindustries.aoserv.client.ticket.Language;
import com.aoindustries.aoserv.client.ticket.Priority;
import com.aoindustries.aoserv.client.ticket.TicketType;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

/**
 * @author  AO Industries, Inc.
 */
public class ContactCompletedAction extends PageAction {

	@Override
	public ActionForward execute(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response,
		Registry pageRegistry
	) throws Exception {
		ContactForm contactForm = (ContactForm)form;

		// Validation
		ActionMessages errors = contactForm.validate(mapping, request);
		if(errors!=null && !errors.isEmpty()) {
			saveErrors(request, errors);
			return mapping.findForward("input");
		}

		SiteSettings siteSettings = SiteSettings.getInstance(getServlet().getServletContext());
		AOServConnector rootConn = siteSettings.getRootAOServConnector();
		Locale locale = response.getLocale();
		Language language = rootConn.getTicket().getLanguage().get(locale.getLanguage());
		if(language==null) {
			language = rootConn.getTicket().getLanguage().get(Language.EN);
			if(language == null) throw new SQLException("Unable to find Language: " + Language.EN);
		}
		TicketType ticketType = rootConn.getTicket().getTicketType().get(TicketType.CONTACT);
		if(ticketType == null) throw new SQLException("Unable to find TicketType: " + TicketType.CONTACT);
		Priority clientPriority = rootConn.getTicket().getPriority().get(Priority.NORMAL);
		if(clientPriority == null) throw new SQLException("Unable to find Priority: " + Priority.NORMAL);
		Email from = Email.valueOf(contactForm.getFrom());
		rootConn.getTicket().getTicket().addTicket(
			siteSettings.getBrand(),
			null,
			language,
			null,
			ticketType,
			from,
			contactForm.getSubject(),
			contactForm.getMessage(),
			clientPriority,
			Collections.singleton(from),
			""
		);

		return mapping.findForward("success");
	}
}
