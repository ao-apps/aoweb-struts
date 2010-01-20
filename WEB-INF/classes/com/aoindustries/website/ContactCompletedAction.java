package com.aoindustries.website;

/*
 * Copyright 2000-2009 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
 * All rights reserved.
 */
import com.aoindustries.aoserv.client.AOServConnector;
import com.aoindustries.aoserv.client.Language;
import com.aoindustries.aoserv.client.TicketPriority;
import com.aoindustries.aoserv.client.TicketType;
import com.aoindustries.aoserv.client.validator.Email;
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
public class ContactCompletedAction extends HttpsAction {

    @Override
    public ActionForward executeProtocolAccepted(
        ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response,
        SiteSettings siteSettings,
        Locale locale,
        Skin skin
    ) throws Exception {
        ContactForm contactForm = (ContactForm)form;

        // Validation
        ActionMessages errors = contactForm.validate(mapping, request);
        if(errors!=null && !errors.isEmpty()) {
            saveErrors(request, errors);
            return mapping.findForward("input");
        }

        AOServConnector<?,?> rootConn = siteSettings.getRootAOServConnector();
        Language language = rootConn.getLanguages().get(locale.getLanguage());
        if(language==null) {
            language = rootConn.getLanguages().get(Language.EN);
        }
        TicketType ticketType = rootConn.getTicketTypes().get(TicketType.CONTACT);
        TicketPriority clientPriority = rootConn.getTicketPriorities().get(TicketPriority.NORMAL);
        siteSettings.getBrand().addTicket(
            null,
            language,
            null,
            ticketType,
            Email.valueOf(contactForm.getFrom()),
            contactForm.getSubject(),
            contactForm.getMessage(),
            clientPriority,
            contactForm.getFrom(),
            ""
        );

        return mapping.findForward("success");
    }
}
