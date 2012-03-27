package com.aoindustries.website.clientarea.accounting;

/*
 * Copyright 2007-2009 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
 * All rights reserved.
 */
import com.aoindustries.creditcards.CreditCard;
import com.aoindustries.creditcards.TransactionResult;
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

    @Override
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

    @Override
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

    @Override
    public ActionErrors mapTransactionError(TransactionResult.ErrorCode errorCode) {
        String errorString = errorCode.toString();
        ActionErrors errors = new ActionErrors();
        switch(errorCode) {
            case INVALID_CARD_NUMBER:
                errors.add("cardNumber", new ActionMessage(errorString, false));
                return errors;
            case INVALID_EXPIRATION_DATE:
                errors.add("expirationDate", new ActionMessage(errorString, false));
                return errors;
            default:
                return super.mapTransactionError(errorCode);
        }
    }
}
