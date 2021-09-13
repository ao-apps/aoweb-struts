/*
 * aoweb-struts - Template webapp for legacy Struts-based site framework with AOServ Platform control panels.
 * Copyright (C) 2007-2013, 2015, 2016, 2018, 2019, 2021  AO Industries, Inc.
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

import com.aoapps.lang.i18n.Money;
import com.aoapps.lang.validation.ValidationException;
import com.aoapps.payments.CreditCard;
import com.aoindustries.aoserv.client.AOServConnector;
import com.aoindustries.aoserv.client.account.Account;
import com.aoindustries.aoserv.client.account.Administrator;
import com.aoindustries.aoserv.client.account.Profile;
import com.aoindustries.aoserv.client.billing.Currency;
import com.aoindustries.web.struts.AuthenticatedAction;
import com.aoindustries.web.struts.SiteSettings;
import com.aoindustries.web.struts.signup.SignupOrganizationActionHelper;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Gets the form for adding a credit card.
 *
 * @author  AO Industries, Inc.
 */
public class MakePaymentNewCardAction extends AuthenticatedAction {

	@Override
	public ActionForward execute(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response,
		AOServConnector aoConn
	) throws Exception {
		MakePaymentNewCardForm makePaymentNewCardForm = (MakePaymentNewCardForm)form;

		Account account;
		try {
			account = aoConn.getAccount().getAccount().get(Account.Name.valueOf(makePaymentNewCardForm.getAccount()));
		} catch(ValidationException e) {
			return mapping.findForward("make-payment");
		}
		if(account == null) {
			// Redirect back to make-payment if account not found
			return mapping.findForward("make-payment");
		}

		// Populate the initial details from the selected account name or authenticated user
		Locale locale = response.getLocale();
		Profile profile = account.getProfile();
		if(profile != null) {
			makePaymentNewCardForm.setFirstName(AddCreditCardAction.getFirstName(profile.getBillingContact(), locale));
			makePaymentNewCardForm.setLastName(AddCreditCardAction.getLastName(profile.getBillingContact(), locale));
			makePaymentNewCardForm.setCompanyName(profile.getName());
			makePaymentNewCardForm.setStreetAddress1(profile.getAddress1());
			makePaymentNewCardForm.setStreetAddress2(profile.getAddress2());
			makePaymentNewCardForm.setCity(profile.getCity());
			makePaymentNewCardForm.setState(profile.getState());
			makePaymentNewCardForm.setPostalCode(profile.getZIP());
			makePaymentNewCardForm.setCountryCode(profile.getCountry().getCode());
		} else {
			Administrator thisBA = aoConn.getCurrentAdministrator();
			makePaymentNewCardForm.setFirstName(AddCreditCardAction.getFirstName(thisBA.getName(), locale));
			makePaymentNewCardForm.setLastName(AddCreditCardAction.getLastName(thisBA.getName(), locale));
			makePaymentNewCardForm.setStreetAddress1(thisBA.getAddress1());
			makePaymentNewCardForm.setStreetAddress2(thisBA.getAddress2());
			makePaymentNewCardForm.setCity(thisBA.getCity());
			makePaymentNewCardForm.setState(thisBA.getState());
			makePaymentNewCardForm.setPostalCode(thisBA.getZIP());
			makePaymentNewCardForm.setCountryCode(thisBA.getCountry() == null ? "" : thisBA.getCountry().getCode());
		}

		initRequestAttributes(request, getServlet().getServletContext());

		Currency currency = aoConn.getBilling().getCurrency().get(makePaymentNewCardForm.getCurrency());
		if(currency != null) {
			// Prompt for amount of payment defaults to current balance.
			Money balance = aoConn.getBilling().getTransaction().getAccountBalance(account).get(currency.getCurrency());
			if(balance != null && balance.getUnscaledValue() > 0) {
				makePaymentNewCardForm.setPaymentAmount(balance.getValue().toPlainString());
			} else {
				makePaymentNewCardForm.setPaymentAmount("");
			}
		} else {
			// No currency, no default payment amount
			makePaymentNewCardForm.setPaymentAmount("");
		}

		request.setAttribute("account", account);

		return mapping.findForward("success");
	}

	protected void initRequestAttributes(HttpServletRequest request, ServletContext context) throws SQLException, IOException {
		// Build the list of years
		List<String> expirationYears = new ArrayList<>(1 + CreditCard.EXPIRATION_YEARS_FUTURE);
		int startYear = new GregorianCalendar().get(Calendar.YEAR);
		for(int c = 0; c <= CreditCard.EXPIRATION_YEARS_FUTURE; c++) {
			expirationYears.add(Integer.toString(startYear + c));
		}

		// Build the list of countries
		// We use the root connector to provide a better set of country values
		List<SignupOrganizationActionHelper.CountryOption> countryOptions = SignupOrganizationActionHelper.getCountryOptions(SiteSettings.getInstance(context).getRootAOServConnector());

		// Store to request attributes
		request.setAttribute("expirationYears", expirationYears);
		request.setAttribute("countryOptions", countryOptions);
	}
}
