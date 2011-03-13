package com.aoindustries.website.signup;

/*
 * Copyright 2007-2011 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
 * All rights reserved.
 */
import com.aoindustries.website.SiteSettings;
import com.aoindustries.website.Skin;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

/**
 * @author  AO Industries, Inc.
 */
public class Managed5Action extends ManagedStepAction {

    public ActionForward executeManagedStep(
        ActionMapping mapping,
        HttpServletRequest request,
        HttpServletResponse response,
        SiteSettings siteSettings,
        Skin skin,
        ManagedSignupSelectPackageForm signupSelectPackageForm,
        boolean signupSelectPackageFormComplete,
        ManagedSignupCustomizeServerForm signupCustomizeServerForm,
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
        if(!signupSelectPackageFormComplete) return mapping.findForward("managed-server-completed");
        if(!signupCustomizeServerFormComplete) return mapping.findForward("managed-server-2-completed");
        if(!signupCustomizeManagementFormComplete) return mapping.findForward("managed-server-3-completed");
        if(!signupBusinessFormComplete) return mapping.findForward("managed-server-4-completed");

        SignupTechnicalActionHelper.setRequestAttributes(getServlet().getServletContext(), request, signupTechnicalForm);

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
