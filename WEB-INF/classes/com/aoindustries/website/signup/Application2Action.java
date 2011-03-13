package com.aoindustries.website.signup;

/*
 * Copyright 2009-2011 by AO Industries, Inc.,
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
public class Application2Action extends ApplicationStepAction {

    public ActionForward executeApplicationStep(
        ActionMapping mapping,
        HttpServletRequest request,
        HttpServletResponse response,
        SiteSettings siteSettings,
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
        if(!signupSelectPackageFormComplete) return mapping.findForward("application-completed");

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
