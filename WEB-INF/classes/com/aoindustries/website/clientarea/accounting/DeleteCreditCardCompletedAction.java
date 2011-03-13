package com.aoindustries.website.clientarea.accounting;

/*
 * Copyright 2007-2011 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
 * All rights reserved.
 */
import com.aoindustries.aoserv.client.AOServConnector;
import com.aoindustries.aoserv.client.AOServPermission;
import com.aoindustries.aoserv.client.CreditCard;
import com.aoindustries.aoserv.client.CreditCardProcessor;
import com.aoindustries.aoserv.creditcards.AOServConnectorPrincipal;
import com.aoindustries.aoserv.creditcards.CreditCardFactory;
import com.aoindustries.aoserv.creditcards.CreditCardProcessorFactory;
import com.aoindustries.website.PermissionAction;
import com.aoindustries.website.SiteSettings;
import com.aoindustries.website.Skin;
import java.util.Collections;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Deletes the credit card.
 *
 * @author  AO Industries, Inc.
 */
public class DeleteCreditCardCompletedAction extends PermissionAction {

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
        // Make sure the credit card still exists, redirect to credit-card-manager if doesn't
        CreditCard creditCard = null;
        String S = request.getParameter("pkey");
        if(S!=null && S.length()>0) {
            try {
                int pkey = Integer.parseInt(S);
                creditCard = aoConn.getCreditCards().get(pkey);
            } catch(NumberFormatException err) {
                getServlet().log(null, err);
            }
        }
        if(creditCard==null) return mapping.findForward("credit-card-manager");

        String cardNumber = creditCard.getCardInfo();

        // Lookup the card in the root connector (to get access to the processor)
        AOServConnector rootConn = siteSettings.getRootAOServConnector();
        CreditCard rootCreditCard = rootConn.getCreditCards().get(creditCard.getPkey());

        // Delete the card from the bank and persistence
        CreditCardProcessor rootAoservCCP = rootCreditCard.getCreditCardProcessor();
        com.aoindustries.creditcards.CreditCardProcessor processor = CreditCardProcessorFactory.getCreditCardProcessor(rootAoservCCP);
        processor.deleteCreditCard(
            new AOServConnectorPrincipal(rootConn, aoConn.getThisBusinessAdministrator().getUsername().getUsername().toString()),
            CreditCardFactory.getCreditCard(rootCreditCard)
        );

        // Set request attributes
        request.setAttribute("cardNumber", cardNumber);

        // Return status success
        return mapping.findForward("success");
    }

    @Override
    public Set<AOServPermission.Permission> getPermissions() {
        return Collections.singleton(AOServPermission.Permission.delete_credit_card);
    }
}
