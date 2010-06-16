/*
 * Copyright 2007-2010 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
 * All rights reserved.
 */
package com.aoindustries.website.clientarea.accounting;

import com.aoindustries.creditcards.TransactionResult;
import com.aoindustries.util.i18n.Money;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Currency;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

/**
 * @author  AO Industries, Inc.
 */
public class MakePaymentNewCardForm extends AddCreditCardForm implements Serializable {

    private static final long serialVersionUID = 1L;

    private String currency;
    private String paymentAmount;
    
    /**
     * Should be one of "", "store", "automatic"
     */
    private String storeCard;

    public MakePaymentNewCardForm() {
    }

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);
        setCurrency("");
        setPaymentAmount("");
        setStoreCard("");
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(String paymentAmount) {
        paymentAmount = paymentAmount.trim();
        // if(paymentAmount.startsWith("$")) paymentAmount=paymentAmount.substring(1);
        this.paymentAmount = paymentAmount;
    }

    public String getStoreCard() {
        return storeCard;
    }
    
    public void setStoreCard(String storeCard) {
        this.storeCard = storeCard;
    }

    @Override
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = super.validate(mapping, request);
        if(errors==null) errors = new ActionErrors();
        if(GenericValidator.isBlankOrNull(currency)) {
            errors.add("currency", new ActionMessage("makePaymentStoredCardForm.currency.required"));
        } else {
            if(GenericValidator.isBlankOrNull(paymentAmount)) {
                errors.add("paymentAmount", new ActionMessage("makePaymentStoredCardForm.paymentAmount.required"));
            } else {
                try {
                    // Make sure can parse as Money (checks scale for us)
                    Money money = new Money(Currency.getInstance(currency), new BigDecimal(this.paymentAmount));
                    if(money.getValue().compareTo(BigDecimal.ZERO)<=0) {
                        errors.add("paymentAmount", new ActionMessage("makePaymentStoredCardForm.paymentAmount.mustBeGeaterThanZero"));
                    }
                } catch(NumberFormatException err) {
                    errors.add("paymentAmount", new ActionMessage("makePaymentStoredCardForm.paymentAmount.invalid"));
                }
            }
        }
        return errors;
    }

    @Override
    public ActionErrors mapTransactionError(TransactionResult.ErrorCode errorCode) {
        String errorString = errorCode.toString();
        ActionErrors errors = new ActionErrors();
        switch(errorCode) {
            case INVALID_AMOUNT:
                errors.add("paymentAmount", new ActionMessage(errorString, false));
                return errors;
            default:
                return super.mapTransactionError(errorCode);
        }
    }
}
