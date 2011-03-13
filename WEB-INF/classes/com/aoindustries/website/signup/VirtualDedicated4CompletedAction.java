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
public class VirtualDedicated4CompletedAction extends VirtualDedicated4Action {

    @Override
    public ActionForward executeVirtualDedicatedStep(
        ActionMapping mapping,
        HttpServletRequest request,
        HttpServletResponse response,
        SiteSettings siteSettings,
        Skin skin,
        VirtualDedicatedSignupSelectPackageForm signupSelectPackageForm,
        boolean signupSelectPackageFormComplete,
        VirtualDedicatedSignupCustomizeServerForm signupCustomizeServerForm,
        boolean signupCustomizeServerFormComplete,
        SignupBusinessForm signupBusinessForm,
        boolean signupBusinessFormComplete,
        SignupTechnicalForm signupTechnicalForm,
        boolean signupTechnicalFormComplete,
        SignupBillingInformationForm signupBillingInformationForm,
        boolean signupBillingInformationFormComplete
    ) throws Exception {
        if(!signupSelectPackageFormComplete) return mapping.findForward("virtual-dedicated-server-completed");
        if(!signupCustomizeServerFormComplete)  return mapping.findForward("virtual-dedicated-server-2-completed");
        if(!signupBusinessFormComplete)  return mapping.findForward("virtual-dedicated-server-3-completed");
        if(!signupTechnicalFormComplete) {
            // Init values for the form
            return super.executeVirtualDedicatedStep(
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
        if(!signupBillingInformationFormComplete) return mapping.findForward("virtual-dedicated-server-5");
        return mapping.findForward("virtual-dedicated-server-6");
    }

    /**
     * Errors are not cleared for the complete step.
     */
    @Override
    protected void clearErrors(HttpServletRequest req) {
        // Do nothing
    }
}
