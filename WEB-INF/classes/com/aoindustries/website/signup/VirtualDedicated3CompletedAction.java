package com.aoindustries.website.signup;

/*
 * Copyright 2007-2009 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
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
public class VirtualDedicated3CompletedAction extends VirtualDedicated3Action {

    public ActionForward executeVirtualDedicatedStep(
        ActionMapping mapping,
        HttpServletRequest request,
        HttpServletResponse response,
        Locale locale,
        Skin skin,
        SignupSelectServerForm signupSelectServerForm,
        boolean signupSelectServerFormComplete,
        SignupCustomizeServerForm signupCustomizeServerForm,
        boolean signupCustomizeServerFormComplete,
        SignupBusinessForm signupBusinessForm,
        boolean signupBusinessFormComplete,
        SignupTechnicalForm signupTechnicalForm,
        boolean signupTechnicalFormComplete,
        SignupBillingInformationForm signupBillingInformationForm,
        boolean signupBillingInformationFormComplete
    ) throws Exception {
        if(!signupSelectServerFormComplete) return mapping.findForward("virtual-dedicated-server-completed");
        if(!signupCustomizeServerFormComplete)  return mapping.findForward("virtual-dedicated-server-2-completed");
        if(!signupBusinessFormComplete) {
            // Init values for the form
            return super.executeVirtualDedicatedStep(
                mapping,
                request,
                response,
                locale,
                skin,
                signupSelectServerForm,
                signupSelectServerFormComplete,
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
        if(!signupTechnicalFormComplete) return mapping.findForward("virtual-dedicated-server-4");
        if(!signupBillingInformationFormComplete) return mapping.findForward("virtual-dedicated-server-5");
        return mapping.findForward("virtual-dedicated-server-6");
    }

    /**
     * Errors are not cleared for the complete step.
     */
    protected void clearErrors(HttpServletRequest req) {
        // Do nothing
    }
}
