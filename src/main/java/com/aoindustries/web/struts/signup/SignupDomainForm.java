/*
 * aoweb-struts - Template webapp for legacy Struts-based site framework with AOServ Platform control panels.
 * Copyright (C) 2009, 2016, 2017, 2021  AO Industries, Inc.
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
public class SignupDomainForm extends ActionForm implements Serializable, SessionActionForm {

	public static final ScopeEE.Session.Attribute<SignupDomainForm> SESSION_ATTRIBUTE =
		ScopeEE.SESSION.attribute("signupDomainForm");

	private static final long serialVersionUID = 1L;

	private String choice;
	private String domain;

	public SignupDomainForm() {
		setChoice("");
		setDomain("");
	}

	@Override
	public boolean isEmpty() {
		return
			"".equals(choice)
			&& "".equals(domain)
		;
	}

	final public String getChoice() {
		return choice;
	}

	final public void setChoice(String choice) {
		this.choice = choice;
	}

	final public String getDomain() {
		return domain;
	}

	final public void setDomain(String domain) {
		this.domain = domain.trim();
	}

	@Override
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
		ActionErrors errors = super.validate(mapping, request);
		if(errors==null) errors = new ActionErrors();
		if(GenericValidator.isBlankOrNull(choice)) errors.add("choice", new ActionMessage("signupDomainForm.choice.required"));
		else {
			if(!"later".equals(choice) && GenericValidator.isBlankOrNull(domain)) errors.add("domain", new ActionMessage("signupDomainForm.domain.required"));
		}
		return errors;
	}
}
