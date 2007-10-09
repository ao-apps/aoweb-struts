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
import com.aoindustries.sql.SQLUtility;
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
 * Payment from stored credit card.
 *
 * @author  AO Industries, Inc.
 */
public class MakePaymentStoredCardAction extends PermissionAction {

    /**
     * When permission denied, redirect straight to the new card step.
     */
    public ActionForward executePermissionDenied(
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

    public ActionForward executePermissionGranted(
        ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response,
        Locale locale,
        Skin skin,
        AOServConnector aoConn
    ) throws Exception {
        MakePaymentStoredCardForm makePaymentStoredCardForm = (MakePaymentStoredCardForm)form;

        // Find the requested business
        String accounting = makePaymentStoredCardForm.getAccounting();
        Business business = accounting==null ? null : aoConn.businesses.get(accounting);
        if(business==null) {
            // Redirect back to make-payment if business not found
            return mapping.findForward("make-payment");
        }

        // If the card pkey is "", new card was selected
        String pkeyString = makePaymentStoredCardForm.getPkey();
        if(pkeyString==null) {
            // pkey not provided, redirect back to make-payment
            return mapping.findForward("make-payment");
        }
        if("".equals(pkeyString)) {
            response.sendRedirect(response.encodeRedirectURL(skin.getHttpsUrlBase(request)+"clientarea/accounting/make-payment-new-card.do?accounting="+request.getParameter("accounting")));
            return null;
        }

        int pkey;
        try {
            pkey = Integer.parseInt(pkeyString);
        } catch(NumberFormatException err) {
            // Can't parse int, redirect back to make-payment
            return mapping.findForward("make-payment");
        }
        CreditCard creditCard = aoConn.creditCards.get(pkey);
        if(creditCard==null) {
            // creditCard not found, redirect back to make-payment
            return mapping.findForward("make-payment");
        }

        // Prompt for amount of payment defaults to current balance.
        int balance = business.getAccountBalance();
        if(balance>0) {
            makePaymentStoredCardForm.setPaymentAmount(SQLUtility.getDecimal(balance));
        } else {
            makePaymentStoredCardForm.setPaymentAmount("");
        }

        request.setAttribute("business", business);
        request.setAttribute("creditCard", creditCard);

        return mapping.findForward("success");
    }

    public List<AOServPermission.Permission> getPermissions() {
        return Collections.singletonList(AOServPermission.Permission.get_credit_cards);
    }
}
