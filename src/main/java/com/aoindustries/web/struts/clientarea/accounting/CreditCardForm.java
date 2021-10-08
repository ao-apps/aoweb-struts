/*
 * aoweb-struts - Template webapp for legacy Struts-based site framework with AOServ Platform control panels.
 * Copyright (C) 2007-2009, 2015, 2016, 2019, 2021  AO Industries, Inc.
 *     support@aoindustries.com
 *     7262 Bull Pen Cir
 *     Mobile, AL 36695
 *
 * This file is part of aoweb-struts.
 *
 * aoweb-struts is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * aoweb-struts is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with aoweb-struts.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.aoindustries.web.struts.clientarea.accounting;

import com.aoapps.payments.CreditCard;
import com.aoapps.payments.TransactionResult;
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
public abstract class CreditCardForm extends ActionForm implements Serializable {

	private static final long serialVersionUID = 2L;

	private String cardNumber;
	private String expirationMonth;
	private String expirationYear;
	private String cardCode;
	private String account;
	private String firstName;
	private String lastName;
	private String companyName;
	private String streetAddress1;
	private String streetAddress2;
	private String city;
	private String state;
	private String postalCode;
	private String countryCode;
	private String description;

	protected CreditCardForm() {
	}

	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		setCardNumber("");
		setExpirationMonth("");
		setExpirationYear("");
		setCardCode("");
		setAccount("");
		setFirstName("");
		setLastName("");
		setCompanyName("");
		setStreetAddress1("");
		setStreetAddress2("");
		setCity("");
		setState("");
		setPostalCode("");
		setCountryCode("");
		setDescription("");
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber==null ? null : cardNumber.trim();
	}

	public String getMaskedCardNumber() {
		return CreditCard.maskCreditCardNumber(cardNumber);
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

	public String getCardCode() {
		return cardCode;
	}

	public void setCardCode(String cardCode) {
		this.cardCode = cardCode==null ? null : cardCode.trim();
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
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

	@Override
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
		ActionErrors errors = super.validate(mapping, request);
		if(errors==null) errors = new ActionErrors();
		if(GenericValidator.isBlankOrNull(account)) errors.add("account", new ActionMessage("creditCardForm.account.required"));
		if(GenericValidator.isBlankOrNull(firstName)) errors.add("firstName", new ActionMessage("creditCardForm.firstName.required"));
		if(GenericValidator.isBlankOrNull(lastName)) errors.add("lastName", new ActionMessage("creditCardForm.lastName.required"));
		if(GenericValidator.isBlankOrNull(streetAddress1)) errors.add("streetAddress1", new ActionMessage("creditCardForm.streetAddress1.required"));
		if(GenericValidator.isBlankOrNull(city)) errors.add("city", new ActionMessage("creditCardForm.city.required"));
		if(GenericValidator.isBlankOrNull(state)) errors.add("state", new ActionMessage("creditCardForm.state.required"));
		if(GenericValidator.isBlankOrNull(countryCode)) errors.add("countryCode", new ActionMessage("creditCardForm.countryCode.required"));
		if(GenericValidator.isBlankOrNull(postalCode)) errors.add("postalCode", new ActionMessage("creditCardForm.postalCode.required"));
		return errors;
	}

	/**
	 * Maps CreditCardProcessor-specific errors to appropriate errors.
	 *
	 * @return the <code>ActionErrors</code> with the mapping or <code>null</code> if unable to map
	 */
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
			case INVALID_CARD_CODE:
				errors.add("cardCode", new ActionMessage(errorString, false));
				return errors;
			case INVALID_CARD_NAME:
				errors.add("firstName", new ActionMessage(errorString, false));
				errors.add("lastName", new ActionMessage(errorString, false));
				return errors;
			case INVALID_CARD_EMAIL:
				errors.add("email", new ActionMessage(errorString, false));
				return errors;
			case INVALID_CARD_PHONE:
				errors.add("phone", new ActionMessage(errorString, false));
				return errors;
			case INVALID_CARD_FAX:
				errors.add("fax", new ActionMessage(errorString, false));
				return errors;
			case INVALID_CUSTOMER_TAX_ID:
				errors.add("customerTaxId", new ActionMessage(errorString, false));
				return errors;
			case INVALID_CARD_ADDRESS:
				errors.add("streetAddress1", new ActionMessage(errorString, false));
				return errors;
			case INVALID_CARD_CITY:
				errors.add("city", new ActionMessage(errorString, false));
				return errors;
			case INVALID_CARD_STATE:
				errors.add("state", new ActionMessage(errorString, false));
				return errors;
			case INVALID_CARD_POSTAL_CODE:
				errors.add("postalCode", new ActionMessage(errorString, false));
				return errors;
			case INVALID_CARD_COUNTRY_CODE:
				errors.add("countryCode", new ActionMessage(errorString, false));
				return errors;
			default:
				return null;
		}
	}
}
