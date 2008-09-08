package com.aoindustries.website.clientarea.accounting;

/*
 * Copyright 2007-2008 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
 * All rights reserved.
 */
import com.aoindustries.creditcards.CreditCard;
import java.io.Serializable;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

/**
 * @author  AO Industries, Inc.
 */
public class EditCreditCardForm extends CreditCardForm implements Serializable {

    private static final long serialVersionUID = 1L;

    private String persistenceId;
    private String isActive;
    private String cardNumber;
    private String expirationMonth;
    private String expirationYear;

    public EditCreditCardForm() {
    }

    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);
        setPersistenceId("");
        setIsActive("");
        setCardNumber("");
        setExpirationMonth("");
        setExpirationYear("");
    }

    public String getPersistenceId() {
        return persistenceId;
    }

    public void setPersistenceId(String persistenceId) {
        this.persistenceId = persistenceId;
    }

    public String getIsActive() {
        return isActive;
    }
    
    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getExpirationMonth() {
        return expirationMonth;
    }

    public void setExpirationMonth(String expirationMonth) {
        this.expirationMonth = expirationMonth;
    }

    public String getExpirationYear() {
        return expirationYear;
    }

    public void setExpirationYear(String expirationYear) {
        this.expirationYear = expirationYear;
    }

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = super.validate(mapping, request);
        if(errors==null) errors = new ActionErrors();
        // persistenceId
        if(GenericValidator.isBlankOrNull(persistenceId)) errors.add("persistenceId", new ActionMessage("editCreditCardForm.persistenceId.required"));
        
        // cardNumber
        if(
            !GenericValidator.isBlankOrNull(cardNumber)
            && !GenericValidator.isCreditCard(CreditCard.numbersOnly(cardNumber))
        ) errors.add("cardNumber", new ActionMessage("editCreditCardForm.cardNumber.invalid"));

        // expirationMonth and expirationYear required when cardNumber provided
        if(!GenericValidator.isBlankOrNull(cardNumber)) {
            if(
                GenericValidator.isBlankOrNull(expirationMonth)
                || GenericValidator.isBlankOrNull(expirationYear)
            ) errors.add("expirationDate", new ActionMessage("editCreditCardForm.expirationDate.required"));
        } else {
            // If either month or year provided, both must be provided
            if(
                !GenericValidator.isBlankOrNull(expirationMonth)
                && GenericValidator.isBlankOrNull(expirationYear)
            ) {
                errors.add("expirationDate", new ActionMessage("editCreditCardForm.expirationDate.monthWithoutYear"));
            } else if(
                GenericValidator.isBlankOrNull(expirationMonth)
                && !GenericValidator.isBlankOrNull(expirationYear)
            ) {
                errors.add("expirationDate", new ActionMessage("editCreditCardForm.expirationDate.yearWithoutMonth"));
            }
        }
        return errors;
    }
}
