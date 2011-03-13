package com.aoindustries.website.clientarea.accounting;

/*
 * Copyright 2007-2011 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
 * All rights reserved.
 */
import com.aoindustries.aoserv.client.AOServConnector;
import com.aoindustries.aoserv.client.AOServPermission;
import com.aoindustries.aoserv.client.Business;
import com.aoindustries.aoserv.client.CreditCard;
import com.aoindustries.aoserv.client.CreditCardTransaction;
import com.aoindustries.aoserv.client.validator.AccountingCode;
import com.aoindustries.website.PermissionAction;
import com.aoindustries.website.SiteSettings;
import com.aoindustries.website.Skin;
import java.util.Collections;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Gets the list of businesses or redirects to next step if only one business accessible.
 *
 * @author  AO Industries, Inc.
 */
public class MakePaymentSelectCardAction extends PermissionAction {

    /**
     * When permission denied, redirect straight to the new card step.
     */
    @Override
    final public ActionForward executePermissionDenied(
        ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response,
        SiteSettings siteSettings,
        Skin skin,
        AOServConnector aoConn,
        SortedSet<AOServPermission> permissions
    ) throws Exception {
        // Redirect when they don't have permissions to retrieve stored cards
        response.sendRedirect(response.encodeRedirectURL(skin.getHttpsUrlBase(request)+"clientarea/accounting/make-payment-new-card.do?accounting="+request.getParameter("accounting")));
        return null;
    }

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
        // Find the requested business
        String accounting = request.getParameter("accounting");
        Business business = accounting==null ? null : aoConn.getBusinesses().get(AccountingCode.valueOf(accounting));
        if(business==null) {
            // Redirect back to make-payment if business not found
            return mapping.findForward("make-payment");
        }

        // Get the list of active credit cards stored for this business
        SortedSet<CreditCard> creditCards = new TreeSet<CreditCard>();
        for(CreditCard creditCard : business.getCreditCards()) {
            if(creditCard.getDeactivatedOn()==null) creditCards.add(creditCard);
        }
        
        if(creditCards.isEmpty()) {
            // Redirect to new card if none stored
            response.sendRedirect(response.encodeRedirectURL(skin.getHttpsUrlBase(request)+"clientarea/accounting/make-payment-new-card.do?accounting="+request.getParameter("accounting")));
            return null;
        } else {
            // Store to request attributes, return success
            request.setAttribute("business", business);
            request.setAttribute("creditCards", creditCards);
            Set<CreditCardTransaction> ccts = business.getCreditCardTransactions();
            request.setAttribute("lastPaymentCreditCard", ccts.isEmpty() ? null : Collections.max(ccts).getCreditCardProviderUniqueId());
            return mapping.findForward("success");
        }
    }

    @Override
    public Set<AOServPermission.Permission> getPermissions() {
        return Collections.singleton(AOServPermission.Permission.get_credit_cards);
    }
}
