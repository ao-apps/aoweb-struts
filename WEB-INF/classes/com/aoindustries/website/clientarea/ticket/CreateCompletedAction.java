package com.aoindustries.website.clientarea.ticket;

/*
 * Copyright 2000-2011 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
 * All rights reserved.
 */
import com.aoindustries.aoserv.client.AOServConnector;
import com.aoindustries.aoserv.client.AOServPermission;
import com.aoindustries.aoserv.client.Business;
import com.aoindustries.aoserv.client.Language;
import com.aoindustries.aoserv.client.TicketPriority;
import com.aoindustries.aoserv.client.TicketType;
import com.aoindustries.aoserv.client.command.AddTicketCommand;
import com.aoindustries.aoserv.client.command.CommandName;
import com.aoindustries.aoserv.client.validator.AccountingCode;
import com.aoindustries.util.i18n.ThreadLocale;
import com.aoindustries.website.PermissionAction;
import com.aoindustries.website.SiteSettings;
import com.aoindustries.website.Skin;
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
public class CreateCompletedAction extends PermissionAction {

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

        // Validation
        ActionMessages errors = ticketForm.validate(mapping, request);
        if(errors!=null && !errors.isEmpty()) {
            saveErrors(request, errors);
            return mapping.findForward("input");
        }

        Business business = aoConn.getBusinesses().get(AccountingCode.valueOf(ticketForm.getAccounting()));
        Language language = aoConn.getLanguages().get(ThreadLocale.get().getLanguage());
        if(language==null) {
            language = aoConn.getLanguages().get(Language.EN);
        }
        TicketType ticketType = aoConn.getTicketTypes().get(TicketType.SUPPORT);
        TicketPriority clientPriority = aoConn.getTicketPriorities().get(ticketForm.getClientPriority());
        int pkey = new AddTicketCommand(
            siteSettings.getBrand(),
            business,
            language,
            null,
            ticketType,
            null,
            ticketForm.getSummary(),
            ticketForm.getDetails(),
            clientPriority,
            ticketForm.getContactEmails(),
            ticketForm.getContactPhoneNumbers()
        ).execute(aoConn);
        request.setAttribute("pkey", pkey);

        return mapping.findForward("success");
    }

    @Override
    public Set<AOServPermission.Permission> getPermissions() {
        return CommandName.add_ticket.getPermissions();
    }
}
