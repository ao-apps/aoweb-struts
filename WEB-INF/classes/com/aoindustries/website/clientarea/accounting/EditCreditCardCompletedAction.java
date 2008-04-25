package com.aoindustries.website.clientarea.accounting;

/*
 * Copyright 2007 by AO Industries, Inc.,
 * 816 Azalea Rd, Mobile, Alabama, 36693, U.S.A.
 * All rights reserved.
 */
import com.aoindustries.aoserv.client.AOServConnector;
import com.aoindustries.aoserv.client.CountryCode;
import com.aoindustries.aoserv.client.CreditCard;
import com.aoindustries.aoserv.creditcards.AOServConnectorPrincipal;
import com.aoindustries.aoserv.creditcards.CreditCardFactory;
import com.aoindustries.aoserv.creditcards.CreditCardProcessorFactory;
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
            initRequestAttributes(request, getServlet().getServletContext());
            request.setAttribute("creditCard", creditCard);
            return mapping.findForward("input");
        }

        // Tells the view layer what was updated
        boolean updatedCardNumber = false;
        boolean updatedExpirationDate = false;
        boolean updatedCardDetails = false;
        boolean reactivatedCard = false;

        String newCardNumber = editCreditCardForm.getCardNumber();
        String newExpirationMonth = editCreditCardForm.getExpirationMonth();
        String newExpirationYear = editCreditCardForm.getExpirationYear();
        if(!GenericValidator.isBlankOrNull(newCardNumber)) {
            // Update card number and expiration
            // Root connector used to get processor
            AOServConnector rootConn = RootAOServConnector.getRootAOServConnector(getServlet().getServletContext());
            CreditCard rootCreditCard = rootConn.creditCards.get(creditCard.getPkey());
            if(rootCreditCard==null) throw new SQLException("Unable to find CreditCard: "+creditCard.getPkey());
            CreditCardProcessor rootProcessor = CreditCardProcessorFactory.getCreditCardProcessor(rootCreditCard.getCreditCardProcessor());
            rootProcessor.updateCreditCardNumberAndExpiration(
                new AOServConnectorPrincipal(rootConn, aoConn.getThisBusinessAdministrator().getUsername().getUsername()),
                CreditCardFactory.getCreditCard(rootCreditCard, locale),
                newCardNumber,
                Byte.parseByte(newExpirationMonth),
                Short.parseShort(newExpirationYear),
                locale
            );
            updatedCardNumber = true;
            updatedExpirationDate = true;
        } else {
            if(
                !GenericValidator.isBlankOrNull(newExpirationMonth)
                && !GenericValidator.isBlankOrNull(newExpirationYear)
            ) {
                // Update expiration only
                // Root connector used to get processor
                AOServConnector rootConn = RootAOServConnector.getRootAOServConnector(getServlet().getServletContext());
                CreditCard rootCreditCard = rootConn.creditCards.get(creditCard.getPkey());
                if(rootCreditCard==null) throw new SQLException("Unable to find CreditCard: "+creditCard.getPkey());
                CreditCardProcessor rootProcessor = CreditCardProcessorFactory.getCreditCardProcessor(rootCreditCard.getCreditCardProcessor());
                rootProcessor.updateCreditCardExpiration(
                    new AOServConnectorPrincipal(rootConn, aoConn.getThisBusinessAdministrator().getUsername().getUsername()),
                    CreditCardFactory.getCreditCard(rootCreditCard, locale),
                    Byte.parseByte(newExpirationMonth),
                    Short.parseShort(newExpirationYear),
                    locale
                );
                updatedExpirationDate = true;
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
            CountryCode countryCode = aoConn.countryCodes.get(editCreditCardForm.getCountryCode());
            if(countryCode==null) throw new SQLException("Unable to find CountryCode: "+editCreditCardForm.getCountryCode());
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
                countryCode,
                editCreditCardForm.getDescription()
            );
            updatedCardDetails = true;
        }
        
        if(!creditCard.getIsActive()) {
            // Reactivate if not active
            creditCard.reactivate();
            reactivatedCard = true;
        }

        // Set the cardNumber request attribute
        String cardNumber;
        if(!GenericValidator.isBlankOrNull(editCreditCardForm.getCardNumber())) cardNumber = com.aoindustries.creditcards.CreditCard.maskCreditCardNumber(editCreditCardForm.getCardNumber());
        else cardNumber = creditCard.getCardInfo();
        request.setAttribute("cardNumber", cardNumber);
        
        // Store which steps were done
        request.setAttribute("updatedCardNumber", updatedCardNumber ? "true" : "false");
        request.setAttribute("updatedExpirationDate", updatedExpirationDate ? "true" : "false");
        request.setAttribute("updatedCardDetails", updatedCardDetails ? "true" : "false");
        request.setAttribute("reactivatedCard", reactivatedCard ? "true" : "false");

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
