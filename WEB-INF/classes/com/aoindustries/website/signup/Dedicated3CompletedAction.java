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

/**
 * @author  AO Industries, Inc.
 */
public class Dedicated3CompletedAction extends Dedicated3Action {

    @Override
    public ActionForward executeDedicatedStep(
        ActionMapping mapping,
        HttpServletRequest request,
        HttpServletResponse response,
        SiteSettings siteSettings,
        Skin skin,
        DedicatedSignupSelectPackageForm signupSelectPackageForm,
        boolean signupSelectPackageFormComplete,
        DedicatedSignupCustomizeServerForm signupCustomizeServerForm,
        boolean signupCustomizeServerFormComplete,
        SignupBusinessForm signupBusinessForm,
        boolean signupBusinessFormComplete,
        SignupTechnicalForm signupTechnicalForm,
        boolean signupTechnicalFormComplete,
        SignupBillingInformationForm signupBillingInformationForm,
        boolean signupBillingInformationFormComplete
    ) throws Exception {
        if(!signupSelectPackageFormComplete) return mapping.findForward("dedicated-server-completed");
        if(!signupCustomizeServerFormComplete)  return mapping.findForward("dedicated-server-2-completed");
        if(!signupBusinessFormComplete) {
            // Init values for the form
            return super.executeDedicatedStep(
                mapping,
                request,
                response,
                siteSettings,
                skin,
                signupSelectPackageForm,
                signupSelectPackageFormComplete,
                signupCustomizeServerForm,
                signupCustomizeServerFormComplete,
                signupBusinessForm,
                signupBusinessFormComplete,
                signupTechnicalForm,
                signupTechnicalFormComplete,
                signupBillingInformationForm,
                signupBillingInformationFormComplete
            );
        }
        if(!signupTechnicalFormComplete) return mapping.findForward("dedicated-server-4");
        if(!signupBillingInformationFormComplete) return mapping.findForward("dedicated-server-5");
        return mapping.findForward("dedicated-server-6");
    }

    /**
     * Errors are not cleared for the complete step.
     */
    @Override
    protected void clearErrors(HttpServletRequest req) {
        // Do nothing
    }
}
