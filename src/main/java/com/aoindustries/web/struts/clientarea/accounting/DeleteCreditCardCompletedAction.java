/*
 * aoweb-struts - Template webapp for legacy Struts-based site framework with AOServ Platform control panels.
 * Copyright (C) 2007-2009, 2016, 2017, 2018, 2019, 2021, 2022  AO Industries, Inc.
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

import com.aoindustries.aoserv.client.AoservConnector;
import com.aoindustries.aoserv.client.master.Permission;
import com.aoindustries.aoserv.client.payment.CreditCard;
import com.aoindustries.aoserv.client.payment.Processor;
import com.aoindustries.aoserv.creditcards.AoservConnectorPrincipal;
import com.aoindustries.aoserv.creditcards.CreditCardFactory;
import com.aoindustries.aoserv.creditcards.CreditCardProcessorFactory;
import com.aoindustries.web.struts.PermissionAction;
import com.aoindustries.web.struts.SiteSettings;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Deletes the credit card.
 *
 * @author  AO Industries, Inc.
 */
public class DeleteCreditCardCompletedAction extends PermissionAction {

  @Override
  public ActionForward executePermissionGranted(
      ActionMapping mapping,
      ActionForm form,
      HttpServletRequest request,
      HttpServletResponse response,
      AoservConnector aoConn
  ) throws Exception {
    // Make sure the credit card still exists, redirect to credit-card-manager if doesn't
    CreditCard creditCard = null;
    String id = request.getParameter("id");
    if (id != null && !id.isEmpty()) {
      try {
        creditCard = aoConn.getPayment().getCreditCard().get(Integer.parseInt(id));
      } catch (NumberFormatException err) {
        getServlet().log(null, err);
      }
    }
    if (creditCard == null) {
      return mapping.findForward("credit-card-manager");
    }

    String cardNumber = creditCard.getCardInfo();

    // Lookup the card in the root connector (to get access to the processor)
    SiteSettings siteSettings = SiteSettings.getInstance(getServlet().getServletContext());
    AoservConnector rootConn = siteSettings.getRootAoservConnector();
    CreditCard rootCreditCard = rootConn.getPayment().getCreditCard().get(creditCard.getId());
    if (rootCreditCard == null) {
      throw new SQLException("Unable to find CreditCard: " + creditCard.getId());
    }

    // Delete the card from the bank and persistence
    Processor rootAoservCreditCardProcessor = rootCreditCard.getCreditCardProcessor();
    com.aoapps.payments.CreditCardProcessor processor = CreditCardProcessorFactory.getCreditCardProcessor(rootAoservCreditCardProcessor);
    processor.deleteCreditCard(
        new AoservConnectorPrincipal(rootConn, aoConn.getCurrentAdministrator().getUsername().getUsername().toString()),
        CreditCardFactory.getCreditCard(rootCreditCard)
    );

    // Set request attributes
    request.setAttribute("cardNumber", cardNumber);

    // Return status success
    return mapping.findForward("success");
  }

  @Override
  public Set<Permission.Name> getPermissions() {
    return Collections.singleton(Permission.Name.delete_credit_card);
  }
}
