package com.aoindustries.website.signup;

/*
 * Copyright 2009-2010 by AO Industries, Inc.,
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
public class SystemAdministrationCompletedAction extends SystemAdministrationAction {

    @Override
    public ActionForward executeSystemAdministrationStep(
        ActionMapping mapping,
        HttpServletRequest request,
        HttpServletResponse response,
        SiteSettings siteSettings,
        Skin skin,
        SystemAdministrationSignupSelectPackageForm signupSelectPackageForm,
        boolean signupSelectPackageFormComplete,
        SignupBusinessForm signupBusinessForm,
        boolean signupBusinessFormComplete,
        SignupTechnicalForm signupTechnicalForm,
        boolean signupTechnicalFormComplete,
        SignupBillingInformationForm signupBillingInformationForm,
        boolean signupBillingInformationFormComplete
    ) throws Exception {
        if(!signupSelectPackageFormComplete) {
            return super.executeSystemAdministrationStep(
                mapping,
                request,
                response,
                siteSettings,
                skin,
                signupSelectPackageForm,
                signupSelectPackageFormComplete,
                signupBusinessForm,
                signupBusinessFormComplete,
                signupTechnicalForm,
                signupTechnicalFormComplete,
                signupBillingInformationForm,
                signupBillingInformationFormComplete
            );
        }
        if(!signupBusinessFormComplete) return mapping.findForward("system-administration-2");
        if(!signupTechnicalFormComplete) return mapping.findForward("system-administration-3");
        if(!signupBillingInformationFormComplete) return mapping.findForward("system-administration-4");
        return mapping.findForward("system-administration-5");
    }

    /**
     * Errors are not cleared for the complete step.
     */
    @Override
    protected void clearErrors(HttpServletRequest req) {
        // Do nothing
    }
}
