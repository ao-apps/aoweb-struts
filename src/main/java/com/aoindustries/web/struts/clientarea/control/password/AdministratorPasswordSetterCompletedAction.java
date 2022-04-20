/*
 * aoweb-struts - Template webapp for legacy Struts-based site framework with AOServ Platform control panels.
 * Copyright (C) 2000-2009, 2016, 2017, 2018, 2019, 2021, 2022  AO Industries, Inc.
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

package com.aoindustries.web.struts.clientarea.control.password;

import com.aoindustries.aoserv.client.AOServConnector;
import com.aoindustries.aoserv.client.account.Administrator;
import com.aoindustries.aoserv.client.linux.User;
import com.aoindustries.aoserv.client.master.Permission;
import com.aoindustries.web.struts.AuthenticatedAction;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

/**
 * @author  AO Industries, Inc.
 */
public class AdministratorPasswordSetterCompletedAction extends AuthenticatedAction {

  @Override
  public ActionForward execute(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response,
    AOServConnector aoConn
  ) throws Exception {
    AdministratorPasswordSetterForm administratorPasswordSetterForm = (AdministratorPasswordSetterForm)form;

    // Validation
    ActionMessages errors = administratorPasswordSetterForm.validate(mapping, request);
    if (errors != null && !errors.isEmpty()) {
      saveErrors(request, errors);
      return mapping.findForward("input");
    }

    // Reset passwords here and clear the passwords from the form
    Administrator thisBA = aoConn.getCurrentAdministrator();
    ActionMessages messages = new ActionMessages();
    List<String> usernames = administratorPasswordSetterForm.getUsernames();
    List<String> newPasswords = administratorPasswordSetterForm.getNewPasswords();
    List<String> confirmPasswords = administratorPasswordSetterForm.getConfirmPasswords();
    for (int c=0;c<usernames.size();c++) {
      String newPassword = newPasswords.get(c);
      if (newPassword.length()>0) {
        User.Name username = User.Name.valueOf(usernames.get(c));
        if (!thisBA.hasPermission(Permission.Name.set_business_administrator_password) && !thisBA.getUsername().getUsername().equals(username)) {
          Permission aoPerm = aoConn.getMaster().getPermission().get(Permission.Name.set_business_administrator_password);
          if (aoPerm == null) {
            throw new SQLException("Unable to find AOServPermission: "+Permission.Name.set_business_administrator_password);
          }
          request.setAttribute("permission", aoPerm);
          ActionForward forward = mapping.findForward("permission-denied");
          if (forward == null) {
            throw new Exception("Unable to find ActionForward: permission-denied");
          }
          return forward;
        }
        Administrator ba = aoConn.getAccount().getAdministrator().get(username);
        if (ba == null) {
          throw new SQLException("Unable to find Administrator: " + username);
        }
        ba.setPassword(newPassword);
        messages.add("confirmPasswords[" + c + "].confirmPasswords", new ActionMessage("administratorPasswordSetter.field.confirmPasswords.passwordReset"));
        newPasswords.set(c, "");
        confirmPasswords.set(c, "");
      }
    }
    saveMessages(request, messages);

    return mapping.findForward("success");
  }
}
