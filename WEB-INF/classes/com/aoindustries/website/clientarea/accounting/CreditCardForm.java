package com.aoindustries.website.clientarea.accounting;

/*
 * Copyright 2007 by AO Industries, Inc.,
 * 816 Azalea Rd, Mobile, Alabama, 36693, U.S.A.
 * All rights reserved.
 */
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
abstract public class CreditCardForm extends ActionForm implements Serializable {

    private static final long serialVersionUID = 1L;

    private String accounting;
    private String firstName;
    private String lastName;
    private String companyName;
    private String email;
    private String phone;
    private String fax;
    private String customerTaxId;
    private String streetAddress1;
    private String streetAddress2;
    private String city;
    private String state;
    private String postalCode;
    private String countryCode;
    private String description;

    public CreditCardForm() {
    }

    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);
        setAccounting("");
        setFirstName("");
        setLastName("");
        setCompanyName("");
        setEmail("");
        setPhone("");
        setFax("");
        setCustomerTaxId("");
        setStreetAddress1("");
        setStreetAddress2("");
        setCity("");
        setState("");
        setPostalCode("");
        setCountryCode("");
        setDescription("");
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getAccounting() {
        return accounting;
    }

    public void setAccounting(String accounting) {
        this.accounting = accounting;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName.trim();
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName.trim();
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName==null ? "" : companyName.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email==null ? "" : email.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone==null ? "" : phone.trim();
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax==null ? "" : fax.trim();
    }

    public String getCustomerTaxId() {
        return customerTaxId;
    }

    public void setCustomerTaxId(String customerTaxId) {
        this.customerTaxId = customerTaxId==null ? "" : customerTaxId.trim();
    }

    public String getStreetAddress1() {
        return streetAddress1;
    }

    public void setStreetAddress1(String streetAddress1) {
        this.streetAddress1 = streetAddress1==null ? "" : streetAddress1.trim();
    }

    public String getStreetAddress2() {
        return streetAddress2;
    }

    public void setStreetAddress2(String streetAddress2) {
        this.streetAddress2 = streetAddress2==null ? "" : streetAddress2.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city==null ? "" : city.trim();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state==null ? "" : state.trim();
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode==null ? "" : postalCode.trim();
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode==null ? "" : countryCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description==null ? "" : description.trim();
    }

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = super.validate(mapping, request);
        if(errors==null) errors = new ActionErrors();
        if(GenericValidator.isBlankOrNull(accounting)) errors.add("accounting", new ActionMessage("creditCardForm.accounting.required"));
        if(GenericValidator.isBlankOrNull(firstName)) errors.add("firstName", new ActionMessage("creditCardForm.firstName.required"));
        if(GenericValidator.isBlankOrNull(lastName)) errors.add("lastName", new ActionMessage("creditCardForm.lastName.required"));
        if(!GenericValidator.isBlankOrNull(email) && !GenericValidator.isEmail(email)) errors.add("email", new ActionMessage("creditCardForm.email.invalid"));
        if(GenericValidator.isBlankOrNull(streetAddress1)) errors.add("streetAddress1", new ActionMessage("creditCardForm.streetAddress1.required"));
        if(GenericValidator.isBlankOrNull(city)) errors.add("city", new ActionMessage("creditCardForm.city.required"));
        if(GenericValidator.isBlankOrNull(countryCode)) errors.add("countryCode", new ActionMessage("creditCardForm.countryCode.required"));
        return errors;
    }
}
