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
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author  AO Industries, Inc.
 */
public class Backup4CompletedAction extends Backup4Action {

    @Override
    public ActionForward executeBackupStep(
        ActionMapping mapping,
        HttpServletRequest request,
        HttpServletResponse response,
        SiteSettings siteSettings,
        Skin skin,
        BackupSignupSelectPackageForm signupSelectPackageForm,
        boolean signupSelectPackageFormComplete,
        SignupBusinessForm signupBusinessForm,
        boolean signupBusinessFormComplete,
        SignupTechnicalForm signupTechnicalForm,
        boolean signupTechnicalFormComplete,
        SignupBillingInformationForm signupBillingInformationForm,
        boolean signupBillingInformationFormComplete
    ) throws Exception {
        // Forward to previous steps if they have not been completed
        if(!signupSelectPackageFormComplete) return mapping.findForward("backup-completed");
        if(!signupBusinessFormComplete)  return mapping.findForward("backup-2-completed");
        if(!signupTechnicalFormComplete)  return mapping.findForward("backup-3-completed");
        if(!signupBillingInformationFormComplete) {
            // Init values for the form
            return super.executeBackupStep(
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
        return mapping.findForward("backup-5");
    }

    /**
     * Clears checkboxes when not in form.
     */
    @Override
    protected void clearCheckboxes(HttpServletRequest request, ActionForm form) {
        SignupBillingInformationForm signupBillingInformationForm = (SignupBillingInformationForm)form;
        // Clear the checkboxes if not present in this request
        if(!"on".equals(request.getParameter("billingUseMonthly"))) signupBillingInformationForm.setBillingUseMonthly(false);
        if(!"on".equals(request.getParameter("billingPayOneYear"))) signupBillingInformationForm.setBillingPayOneYear(false);
    }

    /**
     * Errors are not cleared for the complete step.
     */
    @Override
    protected void clearErrors(HttpServletRequest req) {
        // Do nothing
    }
}
