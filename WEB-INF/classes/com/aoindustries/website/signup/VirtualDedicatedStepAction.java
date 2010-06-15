package com.aoindustries.website.signup;

/*
 * Copyright 2007-2010 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
 * All rights reserved.
 */
import com.aoindustries.website.HttpsAction;
import com.aoindustries.website.SiteSettings;
import com.aoindustries.website.Skin;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

/**
 * @author  AO Industries, Inc.
 */
abstract public class VirtualDedicatedStepAction extends HttpsAction {

    /**
     * Initializes the step details.
     */
    @Override
    public ActionForward executeProtocolAccepted(
        ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response,
        SiteSettings siteSettings,
        Skin skin
    ) throws Exception {
        // Clear checkboxes that were not part of the request
        clearCheckboxes(request, form);

        // Perform redirect if requested a different step
        String selectedStep = request.getParameter("selectedStep");
        if(selectedStep!=null && (selectedStep=selectedStep.trim()).length()>0) {
            if(
                "virtual-dedicated-server".equals(selectedStep)
                || "virtual-dedicated-server-2".equals(selectedStep)
                || "virtual-dedicated-server-3".equals(selectedStep)
                || "virtual-dedicated-server-4".equals(selectedStep)
                || "virtual-dedicated-server-5".equals(selectedStep)
                || "virtual-dedicated-server-6".equals(selectedStep)
            ) {
                return mapping.findForward(selectedStep);
            }
        }

        HttpSession session = request.getSession();

        VirtualDedicatedSignupSelectPackageForm signupSelectPackageForm = SignupHelper.getSessionActionForm(servlet, session, VirtualDedicatedSignupSelectPackageForm.class, "virtualDedicatedSignupSelectPackageForm");
        VirtualDedicatedSignupCustomizeServerForm signupCustomizeServerForm = SignupHelper.getSessionActionForm(servlet, session, VirtualDedicatedSignupCustomizeServerForm.class, "virtualDedicatedSignupCustomizeServerForm");
        SignupBusinessForm signupBusinessForm = SignupHelper.getSessionActionForm(servlet, session, SignupBusinessForm.class, "signupBusinessForm");
        SignupTechnicalForm signupTechnicalForm = SignupHelper.getSessionActionForm(servlet, session, SignupTechnicalForm.class, "signupTechnicalForm");
        SignupBillingInformationForm signupBillingInformationForm = SignupHelper.getSessionActionForm(servlet, session, SignupBillingInformationForm.class, "signupBillingInformationForm");

        ActionMessages signupSelectPackageFormErrors = signupSelectPackageForm.validate(mapping, request);
        ActionMessages signupCustomizeServerFormErrors = signupCustomizeServerForm.validate(mapping, request);
        ActionMessages signupBusinessFormErrors = signupBusinessForm.validate(mapping, request);
        ActionMessages signupTechnicalFormErrors = signupTechnicalForm.validate(mapping, request);
        ActionMessages signupBillingInformationFormErrors = signupBillingInformationForm.validate(mapping, request);

        boolean signupSelectPackageFormComplete = !doAddErrors(request, signupSelectPackageFormErrors);
        boolean signupCustomizeServerFormComplete = !doAddErrors(request, signupCustomizeServerFormErrors);
        boolean signupBusinessFormComplete = !doAddErrors(request, signupBusinessFormErrors);
        boolean signupTechnicalFormComplete = !doAddErrors(request, signupTechnicalFormErrors);
        boolean signupBillingInformationFormComplete = !doAddErrors(request, signupBillingInformationFormErrors);

        request.setAttribute("signupSelectPackageFormComplete", signupSelectPackageFormComplete ? "true" : "false");
        request.setAttribute("signupCustomizeServerFormComplete", signupCustomizeServerFormComplete ? "true" : "false");
        request.setAttribute("signupBusinessFormComplete", signupBusinessFormComplete ? "true" : "false");
        request.setAttribute("signupTechnicalFormComplete", signupTechnicalFormComplete ? "true" : "false");
        request.setAttribute("signupBillingInformationFormComplete", signupBillingInformationFormComplete ? "true" : "false");

        return executeVirtualDedicatedStep(
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

    /**
     * Clears checkboxes when not in form.
     */
    protected void clearCheckboxes(HttpServletRequest request, ActionForm form) {
        // Do nothing by default
    }

    /**
     * Saves the provided errors and return <code>true</code> if there were errors to save.
     */
    private boolean doAddErrors(HttpServletRequest request, ActionMessages errors) {
        if(errors!=null && !errors.isEmpty()) {
            addErrors(request, errors);
            return true;
        }
        return false;
    }

    public abstract ActionForward executeVirtualDedicatedStep(
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
    ) throws Exception;
}
