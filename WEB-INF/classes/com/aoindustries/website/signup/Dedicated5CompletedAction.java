package com.aoindustries.website.signup;

/*
 * Copyright 2007 by AO Industries, Inc.,
 * 816 Azalea Rd, Mobile, Alabama, 36693, U.S.A.
 * All rights reserved.
 */
import com.aoindustries.website.Skin;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author  AO Industries, Inc.
 */
public class Dedicated5CompletedAction extends Dedicated5Action {

    public ActionForward executeDedicatedStep(
        ActionMapping mapping,
        HttpServletRequest request,
        HttpServletResponse response,
        Locale locale,
        Skin skin,
        DedicatedSignupSelectServerForm dedicatedSignupSelectServerForm,
        boolean dedicatedSignupSelectServerFormComplete,
        DedicatedSignupCustomizeServerForm dedicatedSignupCustomizeServerForm,
        boolean dedicatedSignupCustomizeServerFormComplete,
        SignupBusinessForm signupBusinessForm,
        boolean signupBusinessFormComplete,
        SignupTechnicalForm signupTechnicalForm,
        boolean signupTechnicalFormComplete,
        SignupBillingInformationForm signupBillingInformationForm,
        boolean signupBillingInformationFormComplete
    ) throws Exception {
        // Clear the checkboxes if not present in this request
        if(!"on".equals(request.getParameter("billingUseMonthly"))) signupBillingInformationForm.setBillingUseMonthly(false);
        if(!"on".equals(request.getParameter("billingPayOneYear"))) signupBillingInformationForm.setBillingPayOneYear(false);
        
        // Forward to previous steps if they have not been completed
        if(!dedicatedSignupSelectServerFormComplete) return mapping.findForward("dedicated");
        if(!dedicatedSignupCustomizeServerFormComplete)  return mapping.findForward("dedicated2");
        if(!signupBusinessFormComplete)  return mapping.findForward("dedicated3");
        if(!signupTechnicalFormComplete)  return mapping.findForward("dedicated4");
        if(!signupBillingInformationFormComplete) {
            // Init values for the form
            return super.executeDedicatedStep(
                mapping,
                request,
                response,
                locale,
                skin,
                dedicatedSignupSelectServerForm,
                dedicatedSignupSelectServerFormComplete,
                dedicatedSignupCustomizeServerForm,
                dedicatedSignupCustomizeServerFormComplete,
                signupBusinessForm,
                signupBusinessFormComplete,
                signupTechnicalForm,
                signupTechnicalFormComplete,
                signupBillingInformationForm,
                signupBillingInformationFormComplete
            );
        }
        return mapping.findForward("dedicated6");
    }

    /**
     * Errors are not cleared for the complete step.
     */
    protected void clearErrors(HttpServletRequest req) {
        // Do nothing
    }
}
