/*
 * aoweb-struts - Template webapp for legacy Struts-based site framework with AOServ Platform control panels.
 * Copyright (C) 2007-2009, 2015, 2016, 2018, 2019, 2021, 2022  AO Industries, Inc.
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

import com.aoapps.net.URIEncoder;
import com.aoindustries.aoserv.client.billing.PackageCategory;
import com.aoindustries.web.struts.Skin;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

/**
 * @author  AO Industries, Inc.
 */
public class DedicatedAction extends DedicatedStepAction {

  @Override
  public ActionForward executeDedicatedStep(
      ActionMapping mapping,
      HttpServletRequest request,
      HttpServletResponse response,
      DedicatedSignupSelectPackageForm signupSelectPackageForm,
      boolean signupSelectPackageFormComplete,
      DedicatedSignupCustomizeServerForm signupCustomizeServerForm,
      boolean signupCustomizeServerFormComplete,
      SignupOrganizationForm signupOrganizationForm,
      boolean signupOrganizationFormComplete,
      SignupTechnicalForm signupTechnicalForm,
      boolean signupTechnicalFormComplete,
      SignupBillingInformationForm signupBillingInformationForm,
      boolean signupBillingInformationFormComplete
  ) throws Exception {
    List<SignupSelectServerActionHelper.Host> servers = SignupSelectServerActionHelper.getServers(getServlet().getServletContext(), PackageCategory.DEDICATED);
    if (servers.size() == 1) {
      response.sendRedirect(
          response.encodeRedirectURL(
              URIEncoder.encodeURI(
                  Skin.getSkin(request).getUrlBase(request)
                      + "signup/dedicated-server-completed.do?packageDefinition="
                      + URIEncoder.encodeURIComponent(Integer.toString(servers.get(0).getMinimumConfiguration().getPackageDefinition()))
              )
          )
      );
      return null;
    }
    SignupSelectServerActionHelper.setRequestAttributes(getServlet().getServletContext(), request, response, PackageCategory.DEDICATED);

    // Clear errors if they should not be displayed
    clearErrors(request);

    return mapping.findForward("input");
  }

  /**
   * May clear specific errors here.
   */
  protected void clearErrors(HttpServletRequest request) {
    saveErrors(request, new ActionMessages());
  }
}
