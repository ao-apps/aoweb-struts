package com.aoindustries.website.clientarea.accounting;

/*
 * Copyright 2007 by AO Industries, Inc.,
 * 816 Azalea Rd, Mobile, Alabama, 36693, U.S.A.
 * All rights reserved.
 */
import com.aoindustries.aoserv.client.AOServConnector;
import com.aoindustries.aoserv.client.AOServPermission;
import com.aoindustries.aoserv.client.Business;
import com.aoindustries.aoserv.client.CreditCard;
import com.aoindustries.aoserv.client.CreditCardTransaction;
import com.aoindustries.website.HttpsAction;
import com.aoindustries.website.PermissionAction;
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
 * Gets the list of businesses or redirects to next step if only one business accessible.
 *
 * @author  AO Industries, Inc.
 */
public class MakePaymentSelectCardAction extends PermissionAction {

    /**
     * When permission denied, redirect straight to the new card step.
     */
    final public ActionForward executePermissionDenied(
        ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response,
        Locale locale,
        Skin skin,
        AOServConnector aoConn,
        List<AOServPermission> permissions
    ) throws Exception {
        // Redirect when they don't have permissions to retrieve stored cards
        response.sendRedirect(response.encodeRedirectURL(skin.getHttpsUrlBase(request)+"clientarea/accounting/make-payment-new-card.do?accounting="+request.getParameter("accounting")));
        return null;
    }

    final public ActionForward executePermissionGranted(
        ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response,
        Locale locale,
        Skin skin,
        AOServConnector aoConn
    ) throws Exception {
        // Find the requested business
        String accounting = request.getParameter("accounting");
        Business business = accounting==null ? null : aoConn.businesses.get(accounting);
        if(business==null) {
            // Redirect back to make-payment if business not found
            return mapping.findForward("make-payment");
        }

        // Get the list of active credit cards stored for this business
        List<CreditCard> allCreditCards = business.getCreditCards();
        List<CreditCard> creditCards = new ArrayList<CreditCard>(allCreditCards.size());
        for(CreditCard creditCard : allCreditCards) {
            if(creditCard.getDeactivatedOn()==-1) creditCards.add(creditCard);
        }
        
        if(creditCards.isEmpty()) {
            // Redirect to new card if none stored
            response.sendRedirect(response.encodeRedirectURL(skin.getHttpsUrlBase(request)+"clientarea/accounting/make-payment-new-card.do?accounting="+request.getParameter("accounting")));
            return null;
        } else {
            // Store to request attributes, return success
            request.setAttribute("business", business);
            request.setAttribute("creditCards", creditCards);
            CreditCardTransaction lastCCT = business.getLastCreditCardTransaction();
            request.setAttribute("lastPaymentCreditCard", lastCCT==null ? null : lastCCT.getCreditCardProviderUniqueId());
            return mapping.findForward("success");
        }
    }

    public List<AOServPermission.Permission> getPermissions() {
        return Collections.singletonList(AOServPermission.Permission.get_credit_cards);
    }
}
