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
import java.sql.SQLException;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Configures the selection of the card to use for automatic billing.
 *
 * @author  AO Industries, Inc.
 */
public class ConfigureAutomaticBillingCompletedAction extends PermissionAction {

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

		// CreditCard must be selected or "", and part of the account
		String pkey = request.getParameter("pkey");
		if(pkey==null) {
			return mapping.findForward("credit-card-manager");
		}
		CreditCard creditCard;
		if(pkey.length()==0) {
			creditCard=null;
		} else {
			creditCard = aoConn.getPayment().getCreditCard().get(Integer.parseInt(pkey));
			if(creditCard == null) return mapping.findForward("credit-card-manager");
			if(!creditCard.getAccount().equals(account)) throw new SQLException("Requested account and CreditCard account do not match: "+creditCard.getAccount().getName()+"!="+account.getName());
		}

		account.setUseMonthlyCreditCard(creditCard);

		// Store request attributes
		request.setAttribute("account", account);
		request.setAttribute("creditCard", creditCard);

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
