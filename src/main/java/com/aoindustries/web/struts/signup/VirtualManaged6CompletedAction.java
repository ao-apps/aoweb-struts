/*
 * aoweb-struts - Template webapp for legacy Struts-based site framework with AOServ Platform control panels.
 * Copyright (C) 2007-2009, 2016, 2019, 2021, 2022  AO Industries, Inc.
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
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author  AO Industries, Inc.
 */
public class VirtualManaged6CompletedAction extends VirtualManaged6Action {

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
		if(!signupSelectPackageFormComplete) return mapping.findForward("virtual-managed-server-completed");
		if(!signupCustomizeServerFormComplete) return mapping.findForward("virtual-managed-server-2-completed");
		if(!signupCustomizeManagementFormComplete) return mapping.findForward("virtual-managed-server-3-completed");
		if(!signupOrganizationFormComplete) return mapping.findForward("virtual-managed-server-4-completed");
		if(!signupTechnicalFormComplete) return mapping.findForward("virtual-managed-server-5-completed");
		if(!signupBillingInformationFormComplete) {
			// Init values for the form
			return super.executeVirtualManagedStep(
				mapping,
				request,
				response,
				signupSelectPackageForm,
				signupSelectPackageFormComplete,
				signupCustomizeServerForm,
				signupCustomizeServerFormComplete,
				signupCustomizeManagementForm,
				signupCustomizeManagementFormComplete,
				signupOrganizationForm,
				signupOrganizationFormComplete,
				signupTechnicalForm,
				signupTechnicalFormComplete,
				signupBillingInformationForm,
				signupBillingInformationFormComplete
			);
		}
		return mapping.findForward("virtual-managed-server-7");
	}

	/**
	 * Clears checkboxes when not in form.
	 */
	@Override
	protected void clearCheckboxes(HttpServletRequest request, ActionForm form) {
		SignupBillingInformationForm signupBillingInformationForm = (SignupBillingInformationForm)form;
		// Clear the checkboxes if not present in this request
		if(!"on".equals(request.getParameter("billingUseMonthly"))) signupBillingInformationForm.setBillingUseMonthly(false);
		if(!"on".equals(request.getParameter("billingPayOneYear"))) signupBillingInformationForm.setBillingPayOneYear(false);
	}

	/**
	 * Errors are not cleared for the complete step.
	 */
	@Override
	protected void clearErrors(HttpServletRequest req) {
		// Do nothing
	}
}
