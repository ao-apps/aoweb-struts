/*
 * Copyright 2000-2011 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
 * All rights reserved.
 */
package com.aoindustries.website.clientarea.ticket;

import com.aoindustries.aoserv.client.AOServConnector;
import com.aoindustries.aoserv.client.AOServPermission;
import com.aoindustries.aoserv.client.Business;
import com.aoindustries.aoserv.client.Ticket;
import com.aoindustries.aoserv.client.TicketPriority;
import com.aoindustries.aoserv.client.command.AddTicketAnnotationCommand;
import com.aoindustries.aoserv.client.command.CommandName;
import com.aoindustries.aoserv.client.command.SetTicketBusinessCommand;
import com.aoindustries.aoserv.client.command.SetTicketClientPriorityCommand;
import com.aoindustries.aoserv.client.command.SetTicketContactEmailsCommand;
import com.aoindustries.aoserv.client.command.SetTicketContactPhoneNumbersCommand;
import com.aoindustries.aoserv.client.command.SetTicketSummaryCommand;
import com.aoindustries.aoserv.client.validator.AccountingCode;
import com.aoindustries.website.PermissionAction;
import com.aoindustries.website.SiteSettings;
import com.aoindustries.website.Skin;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

/**
 * Provides the list of tickets.
 *
 * @author  AO Industries, Inc.
 */
public class EditCompletedAction extends PermissionAction {

    @Override
    public ActionForward executePermissionGranted(
        ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response,
        SiteSettings siteSettings,
        Skin skin,
        AOServConnector aoConn
    ) throws Exception {
        TicketForm ticketForm = (TicketForm)form;

        // Look for the existing ticket
        String pkeyS = request.getParameter("pkey");
        if(pkeyS==null) return mapping.findForward("index");
        int pkey;
        try {
            pkey = Integer.parseInt(pkeyS);
        } catch(NumberFormatException err) {
            return mapping.findForward("index");
        }
        Ticket ticket = aoConn.getTickets().get(pkey);
        if(ticket==null) {
            request.setAttribute(com.aoindustries.website.Constants.HTTP_SERVLET_RESPONSE_STATUS, Integer.valueOf(HttpServletResponse.SC_NOT_FOUND));
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Ticket not found");
            return null;
        }
        request.setAttribute("ticket", ticket);

        // Validation
        ActionMessages errors = ticketForm.validate(mapping, request);
        if(errors!=null && !errors.isEmpty()) {
            saveErrors(request, errors);
            return mapping.findForward("input");
        }

        // Request attribute defaults
        boolean businessUpdated = false;
        boolean contactEmailsUpdated = false;
        boolean contactPhoneNumbersUpdated = false;
        boolean clientPriorityUpdated = false;
        boolean summaryUpdated = false;
        boolean annotationAdded = false;

        // Update anything that changed
        Business newBusiness = aoConn.getBusinesses().get(AccountingCode.valueOf(ticketForm.getAccounting()));
        Business oldBusiness = ticket.getBusiness();
        if(!newBusiness.equals(oldBusiness)) {
            new SetTicketBusinessCommand(ticket, oldBusiness, newBusiness).execute(aoConn);
            businessUpdated = true;
        }
        if(!ticketForm.getContactEmails().equals(ticket.getContactEmails())) {
            new SetTicketContactEmailsCommand(ticket, ticketForm.getContactEmails()).execute(aoConn);
            contactEmailsUpdated = true;
        }
        if(!ticketForm.getContactPhoneNumbers().equals(ticket.getContactPhoneNumbers())) {
            new SetTicketContactPhoneNumbersCommand(ticket, ticketForm.getContactPhoneNumbers()).execute(aoConn);
            contactPhoneNumbersUpdated = true;
        }
        TicketPriority clientPriority = aoConn.getTicketPriorities().get(ticketForm.getClientPriority());
        if(!clientPriority.equals(ticket.getClientPriority())) {
            new SetTicketClientPriorityCommand(ticket, clientPriority).execute(aoConn);
            clientPriorityUpdated = true;
        }
        if(!ticketForm.getSummary().equals(ticket.getSummary())) {
            new SetTicketSummaryCommand(ticket, ticketForm.getSummary()).execute(aoConn);
            summaryUpdated = true;
        }
        if(ticketForm.getAnnotationSummary().length()>0) {
            new AddTicketAnnotationCommand(
                ticket,
                ticketForm.getAnnotationSummary(),
                ticketForm.getAnnotationDetails()
            ).execute(aoConn);
            annotationAdded = true;
        }

        // Set the request attributes
        request.setAttribute("businessUpdated", businessUpdated);
        request.setAttribute("contactEmailsUpdated", contactEmailsUpdated);
        request.setAttribute("contactPhoneNumbersUpdated", contactPhoneNumbersUpdated);
        request.setAttribute("clientPriorityUpdated", clientPriorityUpdated);
        request.setAttribute("summaryUpdated", summaryUpdated);
        request.setAttribute("annotationAdded", annotationAdded);
        request.setAttribute(
            "nothingChanged",
            !businessUpdated
            && !contactEmailsUpdated
            && !contactPhoneNumbersUpdated
            && !clientPriorityUpdated
            && !summaryUpdated
            && !annotationAdded
        );
        return mapping.findForward("success");
    }

    static final Set<AOServPermission.Permission> permissions;
    static {
        Set<AOServPermission.Permission> newPermissions = EnumSet.noneOf(AOServPermission.Permission.class);
        newPermissions.addAll(CommandName.add_ticket_annotation.getPermissions());
        newPermissions.add(AOServPermission.Permission.edit_ticket);
        permissions = Collections.unmodifiableSet(newPermissions);
    }
    @Override
    public Set<AOServPermission.Permission> getPermissions() {
        return permissions;
    }
}
