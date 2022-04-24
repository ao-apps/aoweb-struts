/*
 * aoweb-struts - Template webapp for legacy Struts-based site framework with AOServ Platform control panels.
 * Copyright (C) 2009, 2016, 2019, 2021, 2022  AO Industries, Inc.
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

package com.aoindustries.web.struts.signup;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author  AO Industries, Inc.
 */
public class AOServ3CompletedAction extends AOServ3Action {

  @Override
  public ActionForward executeAOServStep(
      ActionMapping mapping,
      HttpServletRequest request,
      HttpServletResponse response,
      AOServSignupSelectPackageForm signupSelectPackageForm,
      boolean signupSelectPackageFormComplete,
      SignupOrganizationForm signupOrganizationForm,
      boolean signupOrganizationFormComplete,
      SignupTechnicalForm signupTechnicalForm,
      boolean signupTechnicalFormComplete,
      SignupBillingInformationForm signupBillingInformationForm,
      boolean signupBillingInformationFormComplete
  ) throws Exception {
    if (!signupSelectPackageFormComplete) {
      return mapping.findForward("aoserv-completed");
    }
    if (!signupOrganizationFormComplete) {
      return mapping.findForward("aoserv-2-completed");
    }
    if (!signupTechnicalFormComplete) {
      // Init values for the form
      return super.executeAOServStep(
          mapping,
          request,
          response,
          signupSelectPackageForm,
          signupSelectPackageFormComplete,
          signupOrganizationForm,
          signupOrganizationFormComplete,
          signupTechnicalForm,
          signupTechnicalFormComplete,
          signupBillingInformationForm,
          signupBillingInformationFormComplete
      );
    }
    if (!signupBillingInformationFormComplete) {
      return mapping.findForward("aoserv-4");
    }
    return mapping.findForward("aoserv-5");
  }

  /**
   * Errors are not cleared for the complete step.
   */
  @Override
  protected void clearErrors(HttpServletRequest req) {
    // Do nothing
  }
}
