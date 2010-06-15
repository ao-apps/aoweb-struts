package com.aoindustries.website.clientarea.ticket;

/*
 * Copyright 2000-2010 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
 * All rights reserved.
 */
import com.aoindustries.aoserv.client.AOServConnector;
import com.aoindustries.aoserv.client.AOServPermission;
import com.aoindustries.aoserv.client.BusinessAdministrator;
import com.aoindustries.aoserv.client.TicketPriority;
import com.aoindustries.aoserv.client.command.CommandName;
import com.aoindustries.website.PermissionAction;
import com.aoindustries.website.SiteSettings;
import com.aoindustries.website.Skin;
import java.util.Set;
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
public class CreateAction extends PermissionAction {

    @Override
    public ActionForward executePermissionGranted(
        ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response,
        SiteSettings siteSettings,
        Skin skin,
        AOServConnector<?,?> aoConn
    ) throws Exception {
        TicketForm ticketForm = (TicketForm)form;
        BusinessAdministrator thisBusinessAdministrator = aoConn.getThisBusinessAdministrator();

        // Default to the business of the authenticated user
        ticketForm.setAccounting(thisBusinessAdministrator.getUsername().getBusiness().getAccounting().toString());

        // Default to normal priority
        ticketForm.setClientPriority(TicketPriority.NORMAL);

        // Default contact emails
        // ticketForm.setContactEmails(thisBusinessAdministrator.getEmail());

        return mapping.findForward("success");
    }

    public Set<AOServPermission.Permission> getPermissions() {
        return CommandName.add_ticket.getPermissions();
    }
}
