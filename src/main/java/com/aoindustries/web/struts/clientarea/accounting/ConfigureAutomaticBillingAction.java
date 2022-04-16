/*
 * aoweb-struts - Template webapp for legacy Struts-based site framework with AOServ Platform control panels.
 * Copyright (C) 2007-2009, 2016, 2018, 2019, 2021, 2022  AO Industries, Inc.
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

package com.aoindustries.web.struts.clientarea.accounting;

import com.aoindustries.aoserv.client.AOServConnector;
import com.aoindustries.aoserv.client.account.Account;
import com.aoindustries.aoserv.client.master.Permission;
import com.aoindustries.aoserv.client.payment.CreditCard;
import com.aoindustries.web.struts.PermissionAction;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Allows the selection of the card to use for automatic billing.
 *
 * @author  AO Industries, Inc.
 */
public class ConfigureAutomaticBillingAction extends PermissionAction {

	@Override
	public ActionForward executePermissionGranted(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response,
		AOServConnector aoConn
	) throws Exception {
		// Account must be selected and accessible
		String account_name = request.getParameter("account");
		if(GenericValidator.isBlankOrNull(account_name)) {
			return mapping.findForward("credit-card-manager");
		}
		Account account = aoConn.getAccount().getAccount().get(Account.Name.valueOf(account_name));
		if(account == null) {
			return mapping.findForward("credit-card-manager");
		}

		// Get the list of cards for the account, must have at least one card.
		List<CreditCard> creditCards = account.getCreditCards();
		// Build list of active cards
		List<CreditCard> activeCards = new ArrayList<>(creditCards.size());
		CreditCard automaticCard = null;
		for(CreditCard creditCard : creditCards) {
			if(creditCard.getIsActive()) {
				activeCards.add(creditCard);
				// The first automatic card is used
				if(automaticCard==null && creditCard.getUseMonthly()) automaticCard = creditCard;
			}
		}
		if(activeCards.isEmpty()) {
			return mapping.findForward("credit-card-manager");
		}

		// Store request attributes
		request.setAttribute("account", account);
		request.setAttribute("activeCards", activeCards);
		request.setAttribute("automaticCard", automaticCard);

		return mapping.findForward("success");
	}

	private static final Set<Permission.Name> permissions = Collections.unmodifiableSet(
		EnumSet.of(
			Permission.Name.get_credit_cards,
			Permission.Name.edit_credit_card
		)
	);

	@Override
	public Set<Permission.Name> getPermissions() {
		return permissions;
	}
}
