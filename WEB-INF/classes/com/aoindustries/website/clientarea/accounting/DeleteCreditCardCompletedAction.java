package com.aoindustries.website.clientarea.accounting;

/*
 * Copyright 2007 by AO Industries, Inc.,
 * 816 Azalea Rd, Mobile, Alabama, 36693, U.S.A.
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
import com.aoindustries.website.RootAOServConnector;
import com.aoindustries.website.Skin;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
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

    public ActionForward executePermissionGranted(
        ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response,
        Locale locale,
        Skin skin,
        AOServConnector aoConn
    ) throws Exception {
        // Make sure the credit card still exists, redirect to credit-card-manager if doesn't
        CreditCard creditCard = null;
        String S = request.getParameter("pkey");
        if(S!=null && S.length()>0) {
            try {
                int pkey = Integer.parseInt(S);
                creditCard = aoConn.creditCards.get(pkey);
            } catch(NumberFormatException err) {
                getServlet().log(null, err);
            }
        }
        if(creditCard==null) return mapping.findForward("credit-card-manager");

        String cardNumber = creditCard.getCardInfo();

        // Lookup the card in the root connector (to get access to the processor)
        AOServConnector rootConn = RootAOServConnector.getRootAOServConnector(getServlet().getServletContext());
        CreditCard rootCreditCard = rootConn.creditCards.get(creditCard.getPKey());
        if(rootCreditCard==null) throw new SQLException("Unable to find CreditCard: "+creditCard.getPKey());

        // Delete the card from the bank and persistence
        CreditCardProcessor rootAoservCCP = rootCreditCard.getCreditCardProcessor();
        com.aoindustries.creditcards.CreditCardProcessor processor = CreditCardProcessorFactory.getCreditCardProcessor(rootAoservCCP);
        processor.deleteCreditCard(
            new AOServConnectorPrincipal(rootConn, aoConn.getThisBusinessAdministrator().getUsername().getUsername()),
            CreditCardFactory.getCreditCard(rootCreditCard, locale),
            locale
        );

        // Set request attributes
        request.setAttribute("cardNumber", cardNumber);

        // Return status success
        return mapping.findForward("success");
    }

    public List<AOServPermission.Permission> getPermissions() {
        return Collections.singletonList(AOServPermission.Permission.delete_credit_card);
    }
}
