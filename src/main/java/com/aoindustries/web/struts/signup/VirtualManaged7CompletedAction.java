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

package com.aoindustries.web.struts.signup;

import com.aoindustries.aoserv.client.AOServConnector;
import com.aoindustries.aoserv.client.billing.PackageDefinition;
import com.aoindustries.web.struts.SiteSettings;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionServlet;

/**
 * @author  AO Industries, Inc.
 */
public class VirtualManaged7CompletedAction extends VirtualManaged7Action {

  @Override
  public ActionForward executeVirtualManagedStep(
      ActionMapping mapping,
      HttpServletRequest request,
      HttpServletResponse response,
      VirtualManagedSignupSelectPackageForm signupSelectPackageForm,
      boolean signupSelectPackageFormComplete,
      VirtualManagedSignupCustomizeServerForm signupCustomizeServerForm,
      boolean signupCustomizeServerFormComplete,
      SignupCustomizeManagementForm signupCustomizeManagementForm,
      boolean signupCustomizeManagementFormComplete,
      SignupOrganizationForm signupOrganizationForm,
      boolean signupOrganizationFormComplete,
      SignupTechnicalForm signupTechnicalForm,
      boolean signupTechnicalFormComplete,
      SignupBillingInformationForm signupBillingInformationForm,
      boolean signupBillingInformationFormComplete
  ) throws Exception {
    // Forward to previous steps if they have not been completed
    if (!signupSelectPackageFormComplete) {
      return mapping.findForward("virtual-managed-server-completed");
    }
    if (!signupCustomizeServerFormComplete) {
      return mapping.findForward("virtual-managed-server-2-completed");
    }
    if (!signupCustomizeManagementFormComplete) {
      return mapping.findForward("virtual-managed-server-3-completed");
    }
    if (!signupOrganizationFormComplete) {
      return mapping.findForward("virtual-managed-server-4-completed");
    }
    if (!signupTechnicalFormComplete) {
      return mapping.findForward("virtual-managed-server-5-completed");
    }
    if (!signupBillingInformationFormComplete) {
      return mapping.findForward("virtual-managed-server-6-completed");
    }

    // Let the parent class do the initialization of the request attributes for both the emails and the final JSP
    initRequestAttributes(
        request,
        response,
        signupSelectPackageForm,
        signupCustomizeServerForm,
        signupCustomizeManagementForm,
        signupOrganizationForm,
        signupTechnicalForm,
        signupBillingInformationForm
    );

    // Used later
    ActionServlet myServlet = getServlet();
    SiteSettings siteSettings = SiteSettings.getInstance(myServlet.getServletContext());
    AOServConnector rootConn = siteSettings.getRootAOServConnector();
    PackageDefinition packageDefinition = rootConn.getBilling().getPackageDefinition().get(signupSelectPackageForm.getPackageDefinition());

    // Build the options map
    Map<String, String> options = new HashMap<>();
    ServerConfirmationCompletedActionHelper.addOptions(options, signupCustomizeServerForm);
    ServerConfirmationCompletedActionHelper.addOptions(options, signupCustomizeManagementForm);

    // Store to the database
    ServerConfirmationCompletedActionHelper.storeToDatabase(myServlet, request, rootConn, packageDefinition, signupOrganizationForm, signupTechnicalForm, signupBillingInformationForm, options);
    String pkey = (String) request.getAttribute("pkey");
    String statusKey = (String) request.getAttribute("statusKey");

    // Send confirmation email to support
    ServerConfirmationCompletedActionHelper.sendSupportSummaryEmail(
        myServlet,
        request,
        pkey,
        statusKey,
        packageDefinition,
        signupCustomizeServerForm,
        signupCustomizeManagementForm,
        signupOrganizationForm,
        signupTechnicalForm,
        signupBillingInformationForm
    );

    // Send confirmation email to customer
    ServerConfirmationCompletedActionHelper.sendCustomerSummaryEmails(
        myServlet,
        request,
        pkey,
        statusKey,
        packageDefinition,
        signupCustomizeServerForm,
        signupCustomizeManagementForm,
        signupOrganizationForm,
        signupTechnicalForm,
        signupBillingInformationForm
    );

    // Clear virtualManaged signup-specific forms from the session
    HttpSession session = request.getSession(false);
    VirtualManagedSignupSelectPackageForm.SESSION_ATTRIBUTE.context(session).remove();
    VirtualManagedSignupCustomizeServerForm.SESSION_ATTRIBUTE.context(session).remove();
    SignupCustomizeManagementForm.VIRTUAL_MANAGED_SESSION_ATTRIBUTE.context(session).remove();

    return mapping.findForward("success");
  }
}
