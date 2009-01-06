package com.aoindustries.website.signup;

/*
 * Copyright 2007-2009 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
 * All rights reserved.
 */
import com.aoindustries.creditcards.CreditCard;
import java.io.Serializable;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

/**
 * @author  AO Industries, Inc.
 */
public class SignupBillingInformationForm extends ActionForm implements Serializable {

    private static final long serialVersionUID = 1L;

    private String billingContact;
    private String billingEmail;
    private boolean billingUseMonthly;
    private boolean billingPayOneYear;
    private String billingCardholderName;
    private String billingCardNumber;
    private String billingExpirationMonth;
    private String billingExpirationYear;
    private String billingStreetAddress;
    private String billingCity;
    private String billingState;
    private String billingZip;

    public SignupBillingInformationForm() {
        setBillingContact("");
        setBillingEmail("");
        setBillingUseMonthly(false);
        setBillingPayOneYear(false);
        setBillingCardholderName("");
        setBillingCardNumber("");
        setBillingExpirationMonth("");
        setBillingExpirationYear("");
        setBillingStreetAddress("");
        setBillingCity("");
        setBillingState("");
        setBillingZip("");
    }

    /*
     * This is cleared in Dedicated5CompletedAction instead
     *
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        billingUseMonthly = false;
        billingPayOneYear = false;
    }
     */

    public String getBillingContact() {
        return billingContact;
    }

    public void setBillingContact(String billingContact) {
        this.billingContact = billingContact.trim();
    }

    public String getBillingEmail() {
        return billingEmail;
    }

    public void setBillingEmail(String billingEmail) {
        this.billingEmail = billingEmail.trim();
    }

    public boolean getBillingUseMonthly() {
        return billingUseMonthly;
    }

    public void setBillingUseMonthly(boolean billingUseMonthly) {
        this.billingUseMonthly = billingUseMonthly;
    }

    public boolean getBillingPayOneYear() {
        return billingPayOneYear;
    }

    public void setBillingPayOneYear(boolean billingPayOneYear) {
        this.billingPayOneYear = billingPayOneYear;
    }

    public String getBillingCardholderName() {
        return billingCardholderName;
    }

    public void setBillingCardholderName(String billingCardholderName) {
        this.billingCardholderName = billingCardholderName.trim();
    }

    public String getBillingCardNumber() {
        return billingCardNumber;
    }

    public void setBillingCardNumber(String billingCardNumber) {
        this.billingCardNumber = billingCardNumber.trim();
    }

    public String getBillingExpirationMonth() {
        return billingExpirationMonth;
    }

    public void setBillingExpirationMonth(String billingExpirationMonth) {
        this.billingExpirationMonth = billingExpirationMonth.trim();
    }

    public String getBillingExpirationYear() {
        return billingExpirationYear;
    }

    public void setBillingExpirationYear(String billingExpirationYear) {
        this.billingExpirationYear = billingExpirationYear.trim();
    }

    public String getBillingStreetAddress() {
        return billingStreetAddress;
    }

    public void setBillingStreetAddress(String billingStreetAddress) {
        this.billingStreetAddress = billingStreetAddress.trim();
    }

    public String getBillingCity() {
        return billingCity;
    }

    public void setBillingCity(String billingCity) {
        this.billingCity = billingCity.trim();
    }

    public String getBillingState() {
        return billingState;
    }

    public void setBillingState(String billingState) {
        this.billingState = billingState.trim();
    }

    public String getBillingZip() {
        return billingZip;
    }

    public void setBillingZip(String billingZip) {
        this.billingZip = billingZip.trim();
    }

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = super.validate(mapping, request);
        if(errors==null) errors = new ActionErrors();
        if(GenericValidator.isBlankOrNull(billingContact)) errors.add("billingContact", new ActionMessage("signupBillingInformationForm.billingContact.required"));
        if(GenericValidator.isBlankOrNull(billingEmail)) {
            errors.add("billingEmail", new ActionMessage("signupBillingInformationForm.billingEmail.required"));
        } else if(!GenericValidator.isEmail(billingEmail)) {
            errors.add("billingEmail", new ActionMessage("signupBillingInformationForm.billingEmail.invalid"));
        }
        if(GenericValidator.isBlankOrNull(billingCardholderName)) errors.add("billingCardholderName", new ActionMessage("signupBillingInformationForm.billingCardholderName.required"));
        if(GenericValidator.isBlankOrNull(billingCardNumber)) {
            errors.add("billingCardNumber", new ActionMessage("signupBillingInformationForm.billingCardNumber.required"));
        } else if(!GenericValidator.isCreditCard(CreditCard.numbersOnly(billingCardNumber))) {
            errors.add("billingCardNumber", new ActionMessage("signupBillingInformationForm.billingCardNumber.invalid"));
        }
        if(
            GenericValidator.isBlankOrNull(billingExpirationMonth)
            || GenericValidator.isBlankOrNull(billingExpirationYear)
        ) errors.add("billingExpirationDate", new ActionMessage("signupBillingInformationForm.billingExpirationDate.required"));
        if(GenericValidator.isBlankOrNull(billingStreetAddress)) errors.add("billingStreetAddress", new ActionMessage("signupBillingInformationForm.billingStreetAddress.required"));
        if(GenericValidator.isBlankOrNull(billingCity)) errors.add("billingCity", new ActionMessage("signupBillingInformationForm.billingCity.required"));
        if(GenericValidator.isBlankOrNull(billingState)) errors.add("billingState", new ActionMessage("signupBillingInformationForm.billingState.required"));
        if(GenericValidator.isBlankOrNull(billingZip)) errors.add("billingZip", new ActionMessage("signupBillingInformationForm.billingZip.required"));
        return errors;
    }

    /**
     * @deprecated  Please call CreditCard.numbersOnly directly.
     */
    public static String numbersOnly(String S) {
        return CreditCard.numbersOnly(S);
    }
}
