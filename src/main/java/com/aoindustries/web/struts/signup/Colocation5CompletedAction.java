/*
 * aoweb-struts - Template webapp for legacy Struts-based site framework with AOServ Platform control panels.
 * Copyright (C) 2009, 2016, 2018, 2019, 2021  AO Industries, Inc.
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
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionServlet;

/**
 * @author  AO Industries, Inc.
 */
public class Colocation5CompletedAction extends Colocation5Action {

	@Override
	public ActionForward executeColocationStep(
		ActionMapping mapping,
		HttpServletRequest request,
		HttpServletResponse response,
		ColocationSignupSelectPackageForm signupSelectPackageForm,
		boolean signupSelectPackageFormComplete,
		SignupOrganizationForm signupOrganizationForm,
		boolean signupOrganizationFormComplete,
		SignupTechnicalForm signupTechnicalForm,
		boolean signupTechnicalFormComplete,
		SignupBillingInformationForm signupBillingInformationForm,
		boolean signupBillingInformationFormComplete
	) throws Exception {
		// Forward to previous steps if they have not been completed
		if(!signupSelectPackageFormComplete) return mapping.findForward("colocation-completed");
		if(!signupOrganizationFormComplete) return mapping.findForward("colocation-2-completed");
		if(!signupTechnicalFormComplete) return mapping.findForward("colocation-3-completed");
		if(!signupBillingInformationFormComplete) return mapping.findForward("colocation-4-completed");

		// Let the parent class do the initialization of the request attributes for both the emails and the final JSP
		initRequestAttributes(
			request,
			response,
			signupSelectPackageForm,
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

		// Store to the database
		ServerConfirmationCompletedActionHelper.storeToDatabase(myServlet, request, rootConn, packageDefinition, signupOrganizationForm, signupTechnicalForm, signupBillingInformationForm, options);
		String pkey = (String)request.getAttribute("pkey");
		String statusKey = (String)request.getAttribute("statusKey");

		// Send confirmation email to support
		MinimalConfirmationCompletedActionHelper.sendSupportSummaryEmail(
			myServlet,
			request,
			pkey,
			statusKey,
			packageDefinition,
			signupOrganizationForm,
			signupTechnicalForm,
			signupBillingInformationForm
		);

		// Send confirmation email to customer
		MinimalConfirmationCompletedActionHelper.sendCustomerSummaryEmails(
			myServlet,
			request,
			pkey,
			statusKey,
			packageDefinition,
			signupOrganizationForm,
			signupTechnicalForm,
			signupBillingInformationForm
		);

		// Clear colocation signup-specific forms from the session
		ColocationSignupSelectPackageForm.SESSION_ATTRIBUTE.context(request.getSession(false)).remove();

		return mapping.findForward("success");
	}
}
