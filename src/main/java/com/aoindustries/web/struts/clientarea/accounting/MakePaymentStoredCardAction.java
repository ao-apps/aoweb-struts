/*
 * aoweb-struts - Template webapp for legacy Struts-based site framework with AOServ Platform control panels.
 * Copyright (C) 2007-2009, 2015, 2016, 2018, 2019, 2021  AO Industries, Inc.
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
import com.aoapps.net.URIEncoder;
import com.aoindustries.aoserv.client.AOServConnector;
import com.aoindustries.aoserv.client.account.Account;
import com.aoindustries.aoserv.client.billing.Currency;
import com.aoindustries.aoserv.client.master.Permission;
import com.aoindustries.aoserv.client.payment.CreditCard;
import com.aoindustries.web.struts.PermissionAction;
import com.aoindustries.web.struts.Skin;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Payment from stored credit card.
 *
 * @author  AO Industries, Inc.
 */
public class MakePaymentStoredCardAction extends PermissionAction {

	/**
	 * When permission denied, redirect straight to the new card step.
	 */
	@Override
	public ActionForward executePermissionDenied(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response,
		AOServConnector aoConn,
		List<Permission> permissions
	) throws Exception {
		// Redirect when they don't have permissions to retrieve stored cards
		String currency = request.getParameter("currency");
		StringBuilder href = new StringBuilder();
		href
			.append(Skin.getSkin(request).getUrlBase(request))
			.append("clientarea/accounting/make-payment-new-card.do?account=")
			.append(URIEncoder.encodeURIComponent(request.getParameter("account")));
		if(!GenericValidator.isBlankOrNull(currency)) {
			href
				.append("&currency=")
				.append(URIEncoder.encodeURIComponent(request.getParameter("currency")));
		}
		response.sendRedirect(
			response.encodeRedirectURL(
				URIEncoder.encodeURI(
					href.toString()
				)
			)
		);
		return null;
	}

	@Override
	public ActionForward executePermissionGranted(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response,
		AOServConnector aoConn
	) throws Exception {
		MakePaymentStoredCardForm makePaymentStoredCardForm = (MakePaymentStoredCardForm)form;

		Account account;
		try {
			account = aoConn.getAccount().getAccount().get(Account.Name.valueOf(makePaymentStoredCardForm.getAccount()));
		} catch(ValidationException e) {
			return mapping.findForward("make-payment");
		}
		if(account == null) {
			// Redirect back to make-payment if account not found
			return mapping.findForward("make-payment");
		}

		Currency currency = aoConn.getBilling().getCurrency().get(makePaymentStoredCardForm.getCurrency());

		// If the card id is "", new card was selected
		String idString = makePaymentStoredCardForm.getId();
		if(idString == null) {
			// id not provided, redirect back to make-payment
			return mapping.findForward("make-payment");
		}
		if(idString.isEmpty()) {
			StringBuilder href = new StringBuilder();
			href
				.append(Skin.getSkin(request).getUrlBase(request))
				.append("clientarea/accounting/make-payment-new-card.do?account=")
				.append(URIEncoder.encodeURIComponent(request.getParameter("account")));
			if(currency != null) {
				href
					.append("&currency=")
					.append(URIEncoder.encodeURIComponent(currency.getCurrencyCode()));
			}
			response.sendRedirect(
				response.encodeRedirectURL(
					URIEncoder.encodeURI(
						href.toString()
					)
				)
			);
			return null;
		}

		int id;
		try {
			id = Integer.parseInt(idString);
		} catch(NumberFormatException err) {
			// Can't parse int, redirect back to make-payment
			return mapping.findForward("make-payment");
		}
		CreditCard creditCard = aoConn.getPayment().getCreditCard().get(id);
		if(creditCard == null) {
			// creditCard not found, redirect back to make-payment
			return mapping.findForward("make-payment");
		}

		// Prompt for amount of payment defaults to current balance.
		if(currency != null) {
			Money balance = aoConn.getBilling().getTransaction().getAccountBalance(account).get(currency.getCurrency());
			if(balance != null && balance.getUnscaledValue() > 0) {
				makePaymentStoredCardForm.setPaymentAmount(balance.getValue().toPlainString());
			} else {
				makePaymentStoredCardForm.setPaymentAmount("");
			}
		} else {
			// No currency, no default payment amount
			makePaymentStoredCardForm.setPaymentAmount("");
		}

		request.setAttribute("account", account);
		request.setAttribute("creditCard", creditCard);

		return mapping.findForward("success");
	}

	@Override
	public Set<Permission.Name> getPermissions() {
		return Collections.singleton(Permission.Name.get_credit_cards);
	}
}
