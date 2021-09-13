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

import com.aoapps.lang.validation.ValidationException;
import com.aoapps.net.URIEncoder;
import com.aoindustries.aoserv.client.AOServConnector;
import com.aoindustries.aoserv.client.account.Account;
import com.aoindustries.aoserv.client.billing.Currency;
import com.aoindustries.aoserv.client.master.Permission;
import com.aoindustries.aoserv.client.payment.CreditCard;
import com.aoindustries.aoserv.client.payment.Payment;
import com.aoindustries.web.struts.PermissionAction;
import com.aoindustries.web.struts.Skin;
import java.util.ArrayList;
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
 * Gets the list of accounts or redirects to next step if only one account accessible.
 *
 * @author  AO Industries, Inc.
 */
public class MakePaymentSelectCardAction extends PermissionAction {

	/**
	 * When permission denied, redirect straight to the new card step.
	 */
	@Override
	final public ActionForward executePermissionDenied(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response,
		AOServConnector aoConn,
		List<Permission> permissions
	) throws Exception {
		// Redirect when they don't have permissions to retrieve stored cards
		StringBuilder href = new StringBuilder();
		href
			.append(Skin.getSkin(request).getUrlBase(request))
			.append("clientarea/accounting/make-payment-new-card.do?account=")
			.append(URIEncoder.encodeURIComponent(request.getParameter("account")));
		String currency = request.getParameter("currency");
		if(!GenericValidator.isBlankOrNull(currency)) {
			href
				.append("&currency=")
				.append(URIEncoder.encodeURIComponent(currency));
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
	final public ActionForward executePermissionGranted(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response,
		AOServConnector aoConn
	) throws Exception {
		Account account;
		try {
			account = aoConn.getAccount().getAccount().get(Account.Name.valueOf(request.getParameter("account")));
		} catch(ValidationException e) {
			return mapping.findForward("make-payment");
		}
		if(account == null) {
			// Redirect back to make-payment if account not found
			return mapping.findForward("make-payment");
		}

		Currency currency = aoConn.getBilling().getCurrency().get(request.getParameter("currency"));

		// Get the list of active credit cards stored for this account
		List<CreditCard> allCreditCards = account.getCreditCards();
		List<CreditCard> creditCards = new ArrayList<>(allCreditCards.size());
		for(CreditCard creditCard : allCreditCards) {
			if(creditCard.getDeactivatedOn()==null) creditCards.add(creditCard);
		}

		if(creditCards.isEmpty()) {
			// Redirect to new card if none stored
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
		} else {
			// Store to request attributes, return success
			request.setAttribute("account", account);
			request.setAttribute("creditCards", creditCards);
			Payment lastCCT = account.getLastCreditCardTransaction();
			request.setAttribute("lastPaymentCreditCard", lastCCT==null ? null : lastCCT.getCreditCardProviderUniqueId());
			return mapping.findForward("success");
		}
	}

	@Override
	public Set<Permission.Name> getPermissions() {
		return Collections.singleton(Permission.Name.get_credit_cards);
	}
}
