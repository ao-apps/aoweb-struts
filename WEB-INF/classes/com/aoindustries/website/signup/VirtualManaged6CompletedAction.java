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
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author  AO Industries, Inc.
 */
public class VirtualManaged6CompletedAction extends VirtualManaged6Action {

    public ActionForward executeVirtualManagedStep(
        ActionMapping mapping,
        HttpServletRequest request,
        HttpServletResponse response,
        Locale locale,
        Skin skin,
        SignupSelectServerForm signupSelectServerForm,
        boolean signupSelectServerFormComplete,
        SignupCustomizeServerForm signupCustomizeServerForm,
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
        // Forward to previous steps if they have not been completed
        if(!signupSelectServerFormComplete) return mapping.findForward("virtualManagedCompleted");
        if(!signupCustomizeServerFormComplete) return mapping.findForward("virtualManaged2Completed");
        if(!signupCustomizeManagementFormComplete) return mapping.findForward("virtualManaged3Completed");
        if(!signupBusinessFormComplete) return mapping.findForward("virtualManaged4Completed");
        if(!signupTechnicalFormComplete) return mapping.findForward("virtualManaged5Completed");
        if(!signupBillingInformationFormComplete) {
            // Init values for the form
            return super.executeVirtualManagedStep(
                mapping,
                request,
                response,
                locale,
                skin,
                signupSelectServerForm,
                signupSelectServerFormComplete,
                signupCustomizeServerForm,
                signupCustomizeServerFormComplete,
                signupCustomizeManagementForm,
                signupCustomizeManagementFormComplete,
                signupBusinessForm,
                signupBusinessFormComplete,
                signupTechnicalForm,
                signupTechnicalFormComplete,
                signupBillingInformationForm,
                signupBillingInformationFormComplete
            );
        }
        return mapping.findForward("virtualManaged7");
    }

    /**
     * Clears checkboxes when not in form.
     */
    protected void clearCheckboxes(HttpServletRequest request, ActionForm form) {
        SignupBillingInformationForm signupBillingInformationForm = (SignupBillingInformationForm)form;
        // Clear the checkboxes if not present in this request
        if(!"on".equals(request.getParameter("billingUseMonthly"))) signupBillingInformationForm.setBillingUseMonthly(false);
        if(!"on".equals(request.getParameter("billingPayOneYear"))) signupBillingInformationForm.setBillingPayOneYear(false);
    }

    /**
     * Errors are not cleared for the complete step.
     */
    protected void clearErrors(HttpServletRequest req) {
        // Do nothing
    }
}
