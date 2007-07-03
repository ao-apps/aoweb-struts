package com.aoindustries.website.clientarea.accounting;

/*
 * Copyright 2007 by AO Industries, Inc.,
 * 816 Azalea Rd, Mobile, Alabama, 36693, U.S.A.
 * All rights reserved.
 */
import com.aoindustries.aoserv.client.AOServConnector;
import com.aoindustries.aoserv.client.CreditCard;
import com.aoindustries.aoserv.creditcards.AOServConnectorPrincipal;
import com.aoindustries.aoserv.creditcards.BusinessGroup;
import com.aoindustries.aoserv.creditcards.CreditCardProcessorFactory;
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
public class EditCreditCardCompletedAction extends EditCreditCardAction {

    public ActionForward executePermissionGranted(
        ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response,
        Locale locale,
        Skin skin,
        AOServConnector aoConn
    ) throws Exception {
        EditCreditCardForm editCreditCardForm=(EditCreditCardForm)form;

        String persistenceId = editCreditCardForm.getPersistenceId();
        if(GenericValidator.isBlankOrNull(persistenceId)) {
            // Redirect back to credit-card-manager if no persistenceId selected
            return mapping.findForward("credit-card-manager");
        }
        int pkey;
        try {
            pkey = Integer.parseInt(persistenceId);
        } catch(NumberFormatException err) {
            getServlet().log(null, err);
            // Redirect back to credit-card-manager if persistenceId can't be parsed to int
            return mapping.findForward("credit-card-manager");
        }
        // Find the credit card
        CreditCard creditCard = aoConn.creditCards.get(pkey);
        if(creditCard==null) {
            // Redirect back to credit-card-manager if card no longer exists or is inaccessible
            return mapping.findForward("credit-card-manager");
        }

        // Validation
        ActionMessages errors = editCreditCardForm.validate(mapping, request);
        if(errors!=null && !errors.isEmpty()) {
            saveErrors(request, errors);
            // Init request values before showing input
            initRequestAttributes(request, aoConn);
            request.setAttribute("creditCard", creditCard);
            return mapping.findForward("input");
        }

        String newCardNumber = editCreditCardForm.getCardNumber();
        String newExpirationMonth = editCreditCardForm.getExpirationMonth();
        String newExpirationYear = editCreditCardForm.getExpirationYear();
        if(!GenericValidator.isBlankOrNull(newCardNumber)) {
            // TODO: Update card number and expiration
        } else {
            if(
                !GenericValidator.isBlankOrNull(newExpirationMonth)
                && !GenericValidator.isBlankOrNull(newExpirationYear)
            ) {
                // TODO: Update expiration only
            }
        }

        if(
            !nullOrBlankEquals(editCreditCardForm.getFirstName(), creditCard.getFirstName())
            || !nullOrBlankEquals(editCreditCardForm.getLastName(), creditCard.getLastName())
            || !nullOrBlankEquals(editCreditCardForm.getCompanyName(), creditCard.getCompanyName())
            || !nullOrBlankEquals(editCreditCardForm.getEmail(), creditCard.getEmail())
            || !nullOrBlankEquals(editCreditCardForm.getPhone(), creditCard.getPhone())
            || !nullOrBlankEquals(editCreditCardForm.getFax(), creditCard.getFax())
            || !nullOrBlankEquals(editCreditCardForm.getCustomerTaxId(), creditCard.getCustomerTaxId())
            || !nullOrBlankEquals(editCreditCardForm.getStreetAddress1(), creditCard.getStreetAddress1())
            || !nullOrBlankEquals(editCreditCardForm.getStreetAddress2(), creditCard.getStreetAddress2())
            || !nullOrBlankEquals(editCreditCardForm.getCity(), creditCard.getCity())
            || !nullOrBlankEquals(editCreditCardForm.getState(), creditCard.getState())
            || !nullOrBlankEquals(editCreditCardForm.getPostalCode(), creditCard.getPostalCode())
            || !nullOrBlankEquals(editCreditCardForm.getCountryCode(), creditCard.getCountryCode().getCode())
            || !nullOrBlankEquals(editCreditCardForm.getDescription(), creditCard.getDescription())
        ) {
            // Update rest of the fields
            creditCard.update(
                editCreditCardForm.getFirstName(),
                editCreditCardForm.getLastName(),
                editCreditCardForm.getCompanyName(),
                editCreditCardForm.getEmail(),
                editCreditCardForm.getPhone(),
                editCreditCardForm.getFax(),
                editCreditCardForm.getCustomerTaxId(),
                editCreditCardForm.getStreetAddress1(),
                editCreditCardForm.getStreetAddress2(),
                editCreditCardForm.getCity(),
                editCreditCardForm.getState(),
                editCreditCardForm.getPostalCode(),
                editCreditCardForm.getCountryCode(),
                editCreditCardForm.getDescription()
            );
        }
        
        if(!creditCard.getIsActive()) {
            // TODO: Reactivate if not active
            // TODO: creditCard.reactivate();
        }

        // Set the cardNumber request attribute
        String cardNumber;
        if(!GenericValidator.isBlankOrNull(editCreditCardForm.getCardNumber())) cardNumber = com.aoindustries.creditcards.CreditCard.maskCreditCardNumber(editCreditCardForm.getCardNumber());
        else cardNumber = creditCard.getCardInfo();
        request.setAttribute("cardNumber", cardNumber);

        return mapping.findForward("success");
    }
    
    /**
     * Considers two strings equal, where null and "" are considered equal.
     */
    private static boolean nullOrBlankEquals(String S1, String S2) {
        if(S1==null) S1="";
        if(S2==null) S2="";
        return S1.equals(S2);
    }
}
