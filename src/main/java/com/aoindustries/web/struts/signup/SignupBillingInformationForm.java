/*
 * aoweb-struts - Template webapp for legacy Struts-based site framework with AOServ Platform control panels.
 * Copyright (C) 2007-2009, 2016, 2017, 2018, 2021  AO Industries, Inc.
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
 * along with aoweb-struts.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.aoindustries.web.struts.signup;

import com.aoapps.lang.validation.ValidationResult;
import com.aoapps.net.Email;
import com.aoapps.payments.CreditCard;
import com.aoapps.servlet.attribute.ScopeEE;
import com.aoindustries.web.struts.SessionActionForm;
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
public class SignupBillingInformationForm extends ActionForm implements Serializable, SessionActionForm {

	public static final ScopeEE.Session.Attribute<SignupBillingInformationForm> SESSION_ATTRIBUTE =
		ScopeEE.SESSION.attribute("signupBillingInformationForm");

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

	@Override
	public boolean isEmpty() {
		return
			"".equals(billingContact)
			&& "".equals(billingEmail)
			&& !billingUseMonthly
			&& !billingPayOneYear
			&& "".equals(billingCardholderName)
			&& "".equals(billingCardNumber)
			&& "".equals(billingExpirationMonth)
			&& "".equals(billingExpirationYear)
			&& "".equals(billingStreetAddress)
			&& "".equals(billingCity)
			&& "".equals(billingState)
			&& "".equals(billingZip)
		;
	}

	/*
	 * This is cleared in Dedicated5CompletedAction instead
	 *
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		billingUseMonthly = false;
		billingPayOneYear = false;
	}
	 */

	public final String getBillingContact() {
		return billingContact;
	}

	public final void setBillingContact(String billingContact) {
		this.billingContact = billingContact.trim();
	}

	public final String getBillingEmail() {
		return billingEmail;
	}

	public final void setBillingEmail(String billingEmail) {
		this.billingEmail = billingEmail.trim();
	}

	public final boolean getBillingUseMonthly() {
		return billingUseMonthly;
	}

	public final void setBillingUseMonthly(boolean billingUseMonthly) {
		this.billingUseMonthly = billingUseMonthly;
	}

	public final boolean getBillingPayOneYear() {
		return billingPayOneYear;
	}

	public final void setBillingPayOneYear(boolean billingPayOneYear) {
		this.billingPayOneYear = billingPayOneYear;
	}

	public final String getBillingCardholderName() {
		return billingCardholderName;
	}

	public final void setBillingCardholderName(String billingCardholderName) {
		this.billingCardholderName = billingCardholderName.trim();
	}

	public final String getBillingCardNumber() {
		return billingCardNumber;
	}

	public final void setBillingCardNumber(String billingCardNumber) {
		this.billingCardNumber = billingCardNumber.trim();
	}

	public final String getBillingExpirationMonth() {
		return billingExpirationMonth;
	}

	public final void setBillingExpirationMonth(String billingExpirationMonth) {
		this.billingExpirationMonth = billingExpirationMonth.trim();
	}

	public final String getBillingExpirationYear() {
		return billingExpirationYear;
	}

	public final void setBillingExpirationYear(String billingExpirationYear) {
		this.billingExpirationYear = billingExpirationYear.trim();
	}

	public final String getBillingStreetAddress() {
		return billingStreetAddress;
	}

	public final void setBillingStreetAddress(String billingStreetAddress) {
		this.billingStreetAddress = billingStreetAddress.trim();
	}

	public final String getBillingCity() {
		return billingCity;
	}

	public final void setBillingCity(String billingCity) {
		this.billingCity = billingCity.trim();
	}

	public final String getBillingState() {
		return billingState;
	}

	public final void setBillingState(String billingState) {
		this.billingState = billingState.trim();
	}

	public final String getBillingZip() {
		return billingZip;
	}

	public final void setBillingZip(String billingZip) {
		this.billingZip = billingZip.trim();
	}

	@Override
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
		ActionErrors errors = super.validate(mapping, request);
		if(errors==null) errors = new ActionErrors();
		if(GenericValidator.isBlankOrNull(billingContact)) errors.add("billingContact", new ActionMessage("signupBillingInformationForm.billingContact.required"));
		if(GenericValidator.isBlankOrNull(billingEmail)) {
			errors.add("billingEmail", new ActionMessage("signupBillingInformationForm.billingEmail.required"));
		//} else if(!GenericValidator.isEmail(billingEmail)) {
		//	errors.add("billingEmail", new ActionMessage("signupBillingInformationForm.billingEmail.invalid"));
		} else {
			ValidationResult billingEmailCheck = Email.validate(billingEmail);
			if(!billingEmailCheck.isValid()) {
				errors.add("billingEmail", new ActionMessage(billingEmailCheck.toString(), false));
			}
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
	@Deprecated(forRemoval = true)
	public static String numbersOnly(String s) {
		return CreditCard.numbersOnly(s);
	}
}
