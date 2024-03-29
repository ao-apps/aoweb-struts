/*
 * aoweb-struts - Template webapp for legacy Struts-based site framework with AOServ Platform control panels.
 * Copyright (C) 2007-2009, 2015, 2016, 2018, 2021, 2022  AO Industries, Inc.
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

import com.aoapps.web.resources.registry.Registry;
import com.aoindustries.aoserv.client.password.PasswordChecker;
import com.aoindustries.web.struts.PageAction;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

/**
 * Evaluates the strength of passwords, stores the results as a <code>PasswordChecker.Result[]</code> in request attribute "results".
 *
 * @author  AO Industries, Inc.
 */
public class PasswordEvaluatorCompletedAction extends PageAction {

  @Override
  public ActionForward execute(
      ActionMapping mapping,
      ActionForm form,
      HttpServletRequest request,
      HttpServletResponse response,
      Registry pageRegistry
  ) throws Exception {
    PasswordEvaluatorForm passwordEvaluatorForm = (PasswordEvaluatorForm) form;

    ActionMessages errors = passwordEvaluatorForm.validate(mapping, request);
    if (errors != null && !errors.isEmpty()) {
      saveErrors(request, errors);
      return mapping.findForward("input");
    }

    // Evaluate the password
    String password = passwordEvaluatorForm.getPassword();
    List<PasswordChecker.Result> results = PasswordChecker.checkPassword(null, password, PasswordChecker.PasswordStrength.STRICT);

    // Set request values
    request.setAttribute("results", results);

    return mapping.findForward("success");
  }
}
