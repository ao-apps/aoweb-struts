package com.aoindustries.website.clientarea.accounting;

/*
 * Copyright 2007 by AO Industries, Inc.,
 * 816 Azalea Rd, Mobile, Alabama, 36693, U.S.A.
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
public class AddCreditCardForm extends CreditCardForm implements Serializable {

    private static final long serialVersionUID = 1L;

    private String cardNumber;
    private String expirationMonth;
    private String expirationYear;

    public AddCreditCardForm() {
    }

    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);
        setCardNumber("");
        setExpirationMonth("");
        setExpirationYear("");
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
        // cardNumber
        if(GenericValidator.isBlankOrNull(cardNumber)) errors.add("cardNumber", new ActionMessage("addCreditCardForm.cardNumber.required"));
        else if(!GenericValidator.isCreditCard(CreditCard.numbersOnly(cardNumber))) errors.add("cardNumber", new ActionMessage("addCreditCardForm.cardNumber.invalid"));
        // expirationMonth and expirationYear
        if(
            GenericValidator.isBlankOrNull(expirationMonth)
            || GenericValidator.isBlankOrNull(expirationYear)
        ) errors.add("expirationDate", new ActionMessage("addCreditCardForm.expirationDate.required"));
        return errors;
    }
}
