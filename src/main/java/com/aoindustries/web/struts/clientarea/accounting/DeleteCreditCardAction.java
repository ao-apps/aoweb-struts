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

import com.aoindustries.aoserv.client.AoservConnector;
import com.aoindustries.aoserv.client.master.Permission;
import com.aoindustries.aoserv.client.payment.CreditCard;
import com.aoindustries.web.struts.PermissionAction;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Prompts if the user really wants to delete a credit card.
 *
 * @author  AO Industries, Inc.
 */
public class DeleteCreditCardAction extends PermissionAction {

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

    // Set request attributes
    request.setAttribute("creditCard", creditCard);

    // Return status success
    return mapping.findForward("success");
  }

  private static final Set<Permission.Name> permissions = Collections.unmodifiableSet(
      EnumSet.of(
          Permission.Name.get_credit_cards,
          Permission.Name.delete_credit_card
      )
  );

  @Override
  public Set<Permission.Name> getPermissions() {
    return permissions;
  }
}
