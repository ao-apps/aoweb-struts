/*
 * aoweb-struts - Template webapp for legacy Struts-based site framework with AOServ Platform control panels.
 * Copyright (C) 2007-2009, 2015, 2016, 2019, 2021, 2022  AO Industries, Inc.
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

import com.aoapps.web.resources.registry.Registry;
import com.aoindustries.web.struts.PageAction;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

/**
 * @author  AO Industries, Inc.
 */
public abstract class VirtualDedicatedStepAction extends PageAction {

  /**
   * Initializes the step details.
   */
  @Override
  public final ActionForward execute(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response,
    Registry pageRegistry
  ) throws Exception {
    // Clear checkboxes that were not part of the request
    clearCheckboxes(request, form);

    // Perform redirect if requested a different step
    String selectedStep = request.getParameter("selectedStep");
    if (selectedStep != null && (selectedStep=selectedStep.trim()).length()>0) {
      if (
        "virtual-dedicated-server".equals(selectedStep)
        || "virtual-dedicated-server-2".equals(selectedStep)
        || "virtual-dedicated-server-3".equals(selectedStep)
        || "virtual-dedicated-server-4".equals(selectedStep)
        || "virtual-dedicated-server-5".equals(selectedStep)
        || "virtual-dedicated-server-6".equals(selectedStep)
      ) {
        return mapping.findForward(selectedStep);
      }
    }

    HttpSession session = request.getSession();

    VirtualDedicatedSignupSelectPackageForm signupSelectPackageForm = SignupHelper.getSessionActionForm(servlet, session, VirtualDedicatedSignupSelectPackageForm.SESSION_ATTRIBUTE, VirtualDedicatedSignupSelectPackageForm::new);
    VirtualDedicatedSignupCustomizeServerForm signupCustomizeServerForm = SignupHelper.getSessionActionForm(servlet, session, VirtualDedicatedSignupCustomizeServerForm.SESSION_ATTRIBUTE, VirtualDedicatedSignupCustomizeServerForm::new);
    SignupOrganizationForm signupOrganizationForm = SignupHelper.getSessionActionForm(servlet, session, SignupOrganizationForm.SESSION_ATTRIBUTE, SignupOrganizationForm::new);
    SignupTechnicalForm signupTechnicalForm = SignupHelper.getSessionActionForm(servlet, session, SignupTechnicalForm.SESSION_ATTRIBUTE, SignupTechnicalForm::new);
    SignupBillingInformationForm signupBillingInformationForm = SignupHelper.getSessionActionForm(servlet, session, SignupBillingInformationForm.SESSION_ATTRIBUTE, SignupBillingInformationForm::new);

    ActionMessages signupSelectPackageFormErrors = signupSelectPackageForm.validate(mapping, request);
    ActionMessages signupCustomizeServerFormErrors = signupCustomizeServerForm.validate(mapping, request);
    ActionMessages signupOrganizationFormErrors = signupOrganizationForm.validate(mapping, request);
    ActionMessages signupTechnicalFormErrors = signupTechnicalForm.validate(mapping, request);
    ActionMessages signupBillingInformationFormErrors = signupBillingInformationForm.validate(mapping, request);

    boolean signupSelectPackageFormComplete = !doAddErrors(request, signupSelectPackageFormErrors);
    boolean signupCustomizeServerFormComplete = !doAddErrors(request, signupCustomizeServerFormErrors);
    boolean signupOrganizationFormComplete = !doAddErrors(request, signupOrganizationFormErrors);
    boolean signupTechnicalFormComplete = !doAddErrors(request, signupTechnicalFormErrors);
    boolean signupBillingInformationFormComplete = !doAddErrors(request, signupBillingInformationFormErrors);

    request.setAttribute("signupSelectPackageFormComplete", Boolean.toString(signupSelectPackageFormComplete));
    request.setAttribute("signupCustomizeServerFormComplete", Boolean.toString(signupCustomizeServerFormComplete));
    request.setAttribute("signupOrganizationFormComplete", Boolean.toString(signupOrganizationFormComplete));
    request.setAttribute("signupTechnicalFormComplete", Boolean.toString(signupTechnicalFormComplete));
    request.setAttribute("signupBillingInformationFormComplete", Boolean.toString(signupBillingInformationFormComplete));

    return executeVirtualDedicatedStep(
      mapping,
      request,
      response,
      signupSelectPackageForm,
      signupSelectPackageFormComplete,
      signupCustomizeServerForm,
      signupCustomizeServerFormComplete,
      signupOrganizationForm,
      signupOrganizationFormComplete,
      signupTechnicalForm,
      signupTechnicalFormComplete,
      signupBillingInformationForm,
      signupBillingInformationFormComplete
    );
  }

  /**
   * Clears checkboxes when not in form.
   */
  @SuppressWarnings("NoopMethodInAbstractClass")
  protected void clearCheckboxes(HttpServletRequest request, ActionForm form) {
    // Do nothing by default
  }

  /**
   * Saves the provided errors and return <code>true</code> if there were errors to save.
   */
  private boolean doAddErrors(HttpServletRequest request, ActionMessages errors) {
    if (errors != null && !errors.isEmpty()) {
      addErrors(request, errors);
      return true;
    }
    return false;
  }

  public abstract ActionForward executeVirtualDedicatedStep(
    ActionMapping mapping,
    HttpServletRequest request,
    HttpServletResponse response,
    VirtualDedicatedSignupSelectPackageForm signupSelectPackageForm,
    boolean signupSelectPackageFormComplete,
    VirtualDedicatedSignupCustomizeServerForm signupCustomizeServerForm,
    boolean signupCustomizeServerFormComplete,
    SignupOrganizationForm signupOrganizationForm,
    boolean signupOrganizationFormComplete,
    SignupTechnicalForm signupTechnicalForm,
    boolean signupTechnicalFormComplete,
    SignupBillingInformationForm signupBillingInformationForm,
    boolean signupBillingInformationFormComplete
  ) throws Exception;
}
