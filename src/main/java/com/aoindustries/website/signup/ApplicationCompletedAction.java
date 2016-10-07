package com.aoindustries.website.signup;

/*
 * Copyright 2009 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
 * All rights reserved.
 */
import com.aoindustries.website.SiteSettings;
import com.aoindustries.website.Skin;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author  AO Industries, Inc.
 */
public class ApplicationCompletedAction extends ApplicationAction {

    @Override
    public ActionForward executeApplicationStep(
        ActionMapping mapping,
        HttpServletRequest request,
        HttpServletResponse response,
        SiteSettings siteSettings,
        Locale locale,
        Skin skin,
        ApplicationSignupSelectPackageForm signupSelectPackageForm,
        boolean signupSelectPackageFormComplete,
        SignupDomainForm signupDomainForm,
        boolean signupDomainFormComplete,
        SignupBusinessForm signupBusinessForm,
        boolean signupBusinessFormComplete,
        SignupTechnicalForm signupTechnicalForm,
        boolean signupTechnicalFormComplete,
        SignupBillingInformationForm signupBillingInformationForm,
        boolean signupBillingInformationFormComplete
    ) throws Exception {
        if(!signupSelectPackageFormComplete) {
            return super.executeApplicationStep(
                mapping,
                request,
                response,
                siteSettings,
                locale,
                skin,
                signupSelectPackageForm,
                signupSelectPackageFormComplete,
                signupDomainForm,
                signupDomainFormComplete,
                signupBusinessForm,
                signupBusinessFormComplete,
                signupTechnicalForm,
                signupTechnicalFormComplete,
                signupBillingInformationForm,
                signupBillingInformationFormComplete
            );
        }
        return mapping.findForward("application-2");
    }

    /**
     * Errors are not cleared for the complete step.
     */
    @Override
    protected void clearErrors(HttpServletRequest req) {
        // Do nothing
    }
}
