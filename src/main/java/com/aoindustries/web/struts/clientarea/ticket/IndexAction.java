/*
 * aoweb-struts - Template webapp for legacy Struts-based site framework with AOServ Platform control panels.
 * Copyright (C) 2000-2009, 2016, 2018, 2019, 2021, 2022  AO Industries, Inc.
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

package com.aoindustries.web.struts.clientarea.ticket;

import com.aoindustries.aoserv.client.AoservConnector;
import com.aoindustries.aoserv.client.ticket.Status;
import com.aoindustries.aoserv.client.ticket.Ticket;
import com.aoindustries.web.struts.AuthenticatedAction;
import java.util.ArrayList;
import java.util.List;
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
public class IndexAction  extends AuthenticatedAction {

  @Override
  public ActionForward execute(
      ActionMapping mapping,
      ActionForm form,
      HttpServletRequest request,
      HttpServletResponse response,
      AoservConnector aoConn
  ) throws Exception {
    List<Ticket> tickets = aoConn.getTicket().getTicket().getRows();

    List<Ticket> filteredTickets = new ArrayList<>(tickets.size());
    for (Ticket ticket : tickets) {
      // Only show support or project tickets here
      //String type = ticket.getTicketType().getType();
      //if (
      //    type.equals(TicketType.SUPPORT)
      //    || type.equals(TicketType.PROJECTS)
      //) {
      // Do not show junk or deleted tickets
      String status = ticket.getStatus().getStatus();
      if (
          !status.equals(Status.JUNK)
              && !status.equals(Status.DELETED)
      ) {
        filteredTickets.add(ticket);
      }
      //}
    }

    // Set request values
    request.setAttribute("tickets", filteredTickets);

    return mapping.findForward("success");
  }
}
