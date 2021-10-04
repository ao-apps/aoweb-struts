/*
 * aoweb-struts - Template webapp for legacy Struts-based site framework with AOServ Platform control panels.
 * Copyright (C) 2007-2009, 2016, 2017, 2018, 2019, 2020, 2021  AO Industries, Inc.
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
package com.aoindustries.web.struts.signup;

import com.aoapps.lang.exception.WrappedException;
import com.aoapps.lang.validation.ValidationException;
import com.aoapps.lang.validation.ValidationResult;
import com.aoapps.net.Email;
import com.aoapps.servlet.attribute.ScopeEE;
import com.aoindustries.aoserv.client.AOServConnector;
import com.aoindustries.aoserv.client.account.User;
import com.aoindustries.web.struts.SessionActionForm;
import com.aoindustries.web.struts.SiteSettings;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionServlet;

/**
 * @author  AO Industries, Inc.
 */
public class SignupTechnicalForm extends ActionForm implements Serializable, SessionActionForm {

	public static final ScopeEE.Session.Attribute<SignupTechnicalForm> SESSION_ATTRIBUTE =
		ScopeEE.SESSION.attribute("signupTechnicalForm");

	private static final long serialVersionUID = 1L;

	private String baName;
	private String baTitle;
	private String baWorkPhone;
	private String baCellPhone;
	private String baHomePhone;
	private String baFax;
	private String baEmail;
	private String baAddress1;
	private String baAddress2;
	private String baCity;
	private String baState;
	private String baCountry;
	private String baZip;
	private String baUsername;
	private String baPassword;

	public SignupTechnicalForm() {
		setBaName("");
		setBaTitle("");
		setBaWorkPhone("");
		setBaCellPhone("");
		setBaHomePhone("");
		setBaFax("");
		setBaEmail("");
		setBaAddress1("");
		setBaAddress2("");
		setBaCity("");
		setBaState("");
		setBaCountry("");
		setBaZip("");
		setBaUsername("");
		setBaPassword("");
	}

	@Override
	public boolean isEmpty() {
		return
			"".equals(baName)
			&& "".equals(baTitle)
			&& "".equals(baWorkPhone)
			&& "".equals(baCellPhone)
			&& "".equals(baHomePhone)
			&& "".equals(baFax)
			&& "".equals(baEmail)
			&& "".equals(baAddress1)
			&& "".equals(baAddress2)
			&& "".equals(baCity)
			&& "".equals(baState)
			&& "".equals(baCountry)
			&& "".equals(baZip)
			&& "".equals(baUsername)
			&& "".equals(baPassword)
		;
	}

	public final String getBaName() {
		return baName;
	}

	public final void setBaName(String baName) {
		this.baName = baName.trim();
	}

	public final String getBaTitle() {
		return baTitle;
	}

	public final void setBaTitle(String baTitle) {
		this.baTitle = baTitle.trim();
	}

	public final String getBaWorkPhone() {
		return baWorkPhone;
	}

	public final void setBaWorkPhone(String baWorkPhone) {
		this.baWorkPhone = baWorkPhone.trim();
	}

	public final String getBaCellPhone() {
		return baCellPhone;
	}

	public final void setBaCellPhone(String baCellPhone) {
		this.baCellPhone = baCellPhone.trim();
	}

	public final String getBaHomePhone() {
		return baHomePhone;
	}

	public final void setBaHomePhone(String baHomePhone) {
		this.baHomePhone = baHomePhone.trim();
	}

	public final String getBaFax() {
		return baFax;
	}

	public final void setBaFax(String baFax) {
		this.baFax = baFax.trim();
	}

	public final String getBaEmail() {
		return baEmail;
	}

	public final void setBaEmail(String baEmail) {
		this.baEmail = baEmail.trim();
	}

	public final String getBaAddress1() {
		return baAddress1;
	}

	public final void setBaAddress1(String baAddress1) {
		this.baAddress1 = baAddress1.trim();
	}

	public final String getBaAddress2() {
		return baAddress2;
	}

	public final void setBaAddress2(String baAddress2) {
		this.baAddress2 = baAddress2.trim();
	}

	public final String getBaCity() {
		return baCity;
	}

	public final void setBaCity(String baCity) {
		this.baCity = baCity.trim();
	}

	public final String getBaState() {
		return baState;
	}

	public final void setBaState(String baState) {
		this.baState = baState.trim();
	}

	public final String getBaCountry() {
		return baCountry;
	}

	public final void setBaCountry(String baCountry) {
		this.baCountry = baCountry.trim();
	}

	public final String getBaZip() {
		return baZip;
	}

	public final void setBaZip(String baZip) {
		this.baZip = baZip.trim();
	}

	public final String getBaUsername() {
		return baUsername;
	}

	public final void setBaUsername(String baUsername) {
		this.baUsername = baUsername.trim();
	}

	public final String getBaPassword() {
		return baPassword;
	}

	public final void setBaPassword(String baPassword) {
		this.baPassword = baPassword;
	}

	@Override
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
		ActionErrors errors = super.validate(mapping, request);
		if(errors==null) errors = new ActionErrors();
		try {
			if(GenericValidator.isBlankOrNull(baName)) errors.add("baName", new ActionMessage("signupTechnicalForm.baName.required"));
			if(GenericValidator.isBlankOrNull(baWorkPhone)) errors.add("baWorkPhone", new ActionMessage("signupTechnicalForm.baWorkPhone.required"));
			if(GenericValidator.isBlankOrNull(baEmail)) {
				errors.add("baEmail", new ActionMessage("signupTechnicalForm.baEmail.required"));
			//} else if(!GenericValidator.isEmail(baEmail)) {
			//	errors.add("baEmail", new ActionMessage("signupTechnicalForm.baEmail.invalid"));
			} else {
				ValidationResult baEmailCheck = Email.validate(baEmail);
				if(!baEmailCheck.isValid()) {
					errors.add("baEmail", new ActionMessage(baEmailCheck.toString(), false));
				}
			}
			if(GenericValidator.isBlankOrNull(baUsername)) errors.add("baUsername", new ActionMessage("signupTechnicalForm.baUsername.required"));
			else {
				ActionServlet myServlet = getServlet();
				if(myServlet!=null) {
					AOServConnector rootConn = SiteSettings.getInstance(myServlet.getServletContext()).getRootAOServConnector();
					String lowerUsername = baUsername.toLowerCase();
					ValidationResult check = User.Name.validate(lowerUsername);
					if(!check.isValid()) {
						errors.add("baUsername", new ActionMessage(check.toString(), false));
					} else {
						User.Name userId;
						try {
							userId = User.Name.valueOf(lowerUsername);
						} catch(ValidationException e) {
							throw new AssertionError("Already validated", e);
						}
						if(!rootConn.getAccount().getUser().isUsernameAvailable(userId)) errors.add("baUsername", new ActionMessage("signupTechnicalForm.baUsername.unavailable"));
					}
				}
			}
			return errors;
		} catch(IOException | SQLException err) {
			throw new WrappedException(err);
		}
	}
}
