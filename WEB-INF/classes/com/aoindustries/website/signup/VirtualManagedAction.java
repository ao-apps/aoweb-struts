package com.aoindustries.website.signup;

/*
 * Copyright 2007-2009 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
 * All rights reserved.
 */
import com.aoindustries.aoserv.client.PackageCategory;
import com.aoindustries.website.SiteSettings;
import com.aoindustries.website.Skin;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

/**
 * @author  AO Industries, Inc.
 */
public class VirtualManagedAction extends VirtualManagedStepAction {

    public ActionForward executeVirtualManagedStep(
        ActionMapping mapping,
        HttpServletRequest request,
        HttpServletResponse response,
        SiteSettings siteSettings,
        Locale locale,
        Skin skin,
        VirtualManagedSignupSelectPackageForm signupSelectPackageForm,
        boolean signupSelectPackageFormComplete,
        VirtualManagedSignupCustomizeServerForm signupCustomizeServerForm,
        boolean signupCustomizeServerFormComplete,
        SignupCustomizeManagementForm signupCustomizeManagementForm,
        boolean signupCustomizeManagementFormComplete,
        SignupBusinessForm signupBusinessForm,
        boolean signupBusinessFormComplete,
        SignupTechnicalForm signupTechnicalForm,
        boolean signupTechnicalFormComplete,
        SignupBillingInformationForm signupBillingInformationForm,
        boolean signupBillingInformationFormComplete
    ) throws Exception {
        List<SignupSelectServerActionHelper.Server> servers = SignupSelectServerActionHelper.getServers(getServlet().getServletContext(), PackageCategory.VIRTUAL_MANAGED);
        if(servers.size()==1) {
            response.sendRedirect(
                response.encodeRedirectURL(
                    skin.getHttpsUrlBase(request)
                    +"signup/virtual-managed-server-completed.do?packageDefinition="
                    +servers.get(0).getMinimumConfiguration().getPackageDefinition()
                )
            );
            return null;
        }
        SignupSelectServerActionHelper.setRequestAttributes(getServlet().getServletContext(), request, response, PackageCategory.VIRTUAL_MANAGED);

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
