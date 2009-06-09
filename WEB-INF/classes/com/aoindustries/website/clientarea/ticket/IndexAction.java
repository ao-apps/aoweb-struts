package com.aoindustries.website.clientarea.ticket;

/*
 * Copyright 2000-2009 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
 * All rights reserved.
 */
import com.aoindustries.aoserv.client.AOServConnector;
import com.aoindustries.aoserv.client.Ticket;
import com.aoindustries.website.AuthenticatedAction;
import com.aoindustries.website.Skin;
import com.aoindustries.website.SiteSettings;
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
public class IndexAction  extends AuthenticatedAction {

    @Override
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
        List<Ticket> tickets = aoConn.getTickets().getRows();

        // Set request values
        request.setAttribute("tickets", tickets);

        return mapping.findForward("success");
    }
}
