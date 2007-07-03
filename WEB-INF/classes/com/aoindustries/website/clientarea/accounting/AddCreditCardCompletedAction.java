package com.aoindustries.website.clientarea.accounting;

/*
 * Copyright 2007 by AO Industries, Inc.,
 * 816 Azalea Rd, Mobile, Alabama, 36693, U.S.A.
 * All rights reserved.
 */
import com.aoindustries.aoserv.client.AOServConnector;
import com.aoindustries.aoserv.creditcards.AOServConnectorPrincipal;
import com.aoindustries.aoserv.creditcards.BusinessGroup;
import com.aoindustries.aoserv.creditcards.CreditCardProcessorFactory;
import com.aoindustries.creditcards.CreditCard;
import com.aoindustries.creditcards.CreditCardProcessor;
import com.aoindustries.website.RootAOServConnector;
import com.aoindustries.website.Skin;
import java.sql.SQLException;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

/**
 * Stores the credit card.
 *
 * @author  AO Industries, Inc.
 */
public class AddCreditCardCompletedAction extends AddCreditCardAction {

    public ActionForward executePermissionGranted(
        ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response,
        Locale locale,
        Skin skin,
        AOServConnector aoConn
    ) throws Exception {
        AddCreditCardForm addCreditCardForm=(AddCreditCardForm)form;

        String accounting = addCreditCardForm.getAccounting();
        if(GenericValidator.isBlankOrNull(accounting)) {
            // Redirect back to credit-card-manager it no accounting selected
            return mapping.findForward("credit-card-manager");
        }

        // Validation
        ActionMessages errors = addCreditCardForm.validate(mapping, request);
        if(errors!=null && !errors.isEmpty()) {
            saveErrors(request, errors);
            // Init request values before showing input
            initRequestAttributes(request, aoConn);
            return mapping.findForward("input");
        }

        // Get the credit card processor for the root connector of this website
        CreditCardProcessor creditCardProcessor = CreditCardProcessorFactory.getCreditCardProcessor(RootAOServConnector.getRootAOServConnector(getServlet().getServletContext()));
        if(creditCardProcessor==null) throw new SQLException("Unable to find enabled CreditCardProcessor for root connector");

        // Add card
        if(!creditCardProcessor.canStoreCreditCards(locale)) throw new SQLException("CreditCardProcessor indicates it does not support storing credit cards.");

        creditCardProcessor.storeCreditCard(
            new AOServConnectorPrincipal(aoConn),
            new BusinessGroup(aoConn.businesses.get(accounting)),
            new CreditCard(
                locale,
                null,
                null,
                null,
                null,
                null,
                addCreditCardForm.getCardNumber(),
                null,
                Byte.parseByte(addCreditCardForm.getExpirationMonth()),
                Short.parseShort(addCreditCardForm.getExpirationYear()),
                null,
                addCreditCardForm.getFirstName(),
                addCreditCardForm.getLastName(),
                addCreditCardForm.getCompanyName(),
                addCreditCardForm.getEmail(),
                addCreditCardForm.getPhone(),
                addCreditCardForm.getFax(),
                null,
                addCreditCardForm.getCustomerTaxId(),
                addCreditCardForm.getStreetAddress1(),
                addCreditCardForm.getStreetAddress2(),
                addCreditCardForm.getCity(),
                addCreditCardForm.getState(),
                addCreditCardForm.getPostalCode(),
                addCreditCardForm.getCountryCode(),
                addCreditCardForm.getDescription()
            ),
            locale
        );

        request.setAttribute("cardNumber", CreditCard.maskCreditCardNumber(addCreditCardForm.getCardNumber()));

        return mapping.findForward("success");
    }
}
