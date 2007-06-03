package com.aoindustries.website.signup;

/*
 * Copyright 2007 by AO Industries, Inc.,
 * 816 Azalea Rd, Mobile, Alabama, 36693, U.S.A.
 * All rights reserved.
 */
import com.aoindustries.website.HttpsAction;
import com.aoindustries.website.Skin;
import java.util.Locale;
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
abstract public class VirtualManagedStepAction extends HttpsAction {

    /**
     * Initializes the step details.
     */
    final public ActionForward executeProtocolAccepted(
        ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response,
        Locale locale,
        Skin skin
    ) throws Exception {
        // Clear checkboxes that were not part of the request
        clearCheckboxes(request, form);

        // Perform redirect if requested a different step
        String selectedStep = request.getParameter("selectedStep");
        if(selectedStep!=null && (selectedStep=selectedStep.trim()).length()>0) {
            if(
                "virtualManaged".equals(selectedStep)
                || "virtualManaged2".equals(selectedStep)
                || "virtualManaged3".equals(selectedStep)
                || "virtualManaged4".equals(selectedStep)
                || "virtualManaged5".equals(selectedStep)
                || "virtualManaged6".equals(selectedStep)
                || "virtualManaged7".equals(selectedStep)
            ) {
                return mapping.findForward(selectedStep);
            }
        }

        HttpSession session = request.getSession();

        SignupSelectServerForm signupSelectServerForm = (SignupSelectServerForm)session.getAttribute("virtualManagedSignupSelectServerForm");
        SignupCustomizeServerForm signupCustomizeServerForm = (SignupCustomizeServerForm)session.getAttribute("virtualManagedSignupCustomizeServerForm");
        SignupCustomizeManagementForm signupCustomizeManagementForm = (SignupCustomizeManagementForm)session.getAttribute("virtualManagedSignupCustomizeManagementForm");
        SignupBusinessForm signupBusinessForm = (SignupBusinessForm)session.getAttribute("signupBusinessForm");
        SignupTechnicalForm signupTechnicalForm = (SignupTechnicalForm)session.getAttribute("signupTechnicalForm");
        SignupBillingInformationForm signupBillingInformationForm = (SignupBillingInformationForm)session.getAttribute("signupBillingInformationForm");

        ActionMessages signupSelectServerFormErrors = signupSelectServerForm==null ? null : signupSelectServerForm.validate(mapping, request);
        ActionMessages signupCustomizeServerFormErrors = signupCustomizeServerForm==null ? null : signupCustomizeServerForm.validate(mapping, request);
        ActionMessages signupCustomizeManagementFormErrors = signupCustomizeManagementForm==null ? null : signupCustomizeManagementForm.validate(mapping, request);
        ActionMessages signupBusinessFormErrors = signupBusinessForm==null ? null : signupBusinessForm.validate(mapping, request);
        ActionMessages signupTechnicalFormErrors = signupTechnicalForm==null ? null : signupTechnicalForm.validate(mapping, request);
        ActionMessages signupBillingInformationFormErrors = signupBillingInformationForm==null ? null : signupBillingInformationForm.validate(mapping, request);

        boolean signupSelectServerFormComplete = signupSelectServerForm==null ? false : !doAddErrors(request, signupSelectServerFormErrors);
        boolean signupCustomizeServerFormComplete = signupCustomizeServerForm==null ? false : !doAddErrors(request, signupCustomizeServerFormErrors);
        boolean signupCustomizeManagementFormComplete;
        if(signupCustomizeManagementForm==null) {
            signupCustomizeManagementFormComplete = false;
        } else {
            if(doAddErrors(request, signupCustomizeManagementFormErrors)) signupCustomizeManagementFormComplete = false;
            else if(!"true".equals(signupCustomizeManagementForm.getFormCompleted())) signupCustomizeManagementFormComplete = false;
            else signupCustomizeManagementFormComplete = true;
        }
        boolean signupBusinessFormComplete = signupBusinessForm==null ? false : !doAddErrors(request, signupBusinessFormErrors);
        boolean signupTechnicalFormComplete = signupTechnicalForm==null ? false : !doAddErrors(request, signupTechnicalFormErrors);
        boolean signupBillingInformationFormComplete = signupBillingInformationForm==null ? false : !doAddErrors(request, signupBillingInformationFormErrors);

        request.setAttribute("signupSelectServerFormComplete", signupSelectServerFormComplete ? "true" : "false");
        request.setAttribute("signupCustomizeServerFormComplete", signupCustomizeServerFormComplete ? "true" : "false");
        request.setAttribute("signupCustomizeManagementFormComplete", signupCustomizeManagementFormComplete ? "true" : "false");
        request.setAttribute("signupBusinessFormComplete", signupBusinessFormComplete ? "true" : "false");
        request.setAttribute("signupTechnicalFormComplete", signupTechnicalFormComplete ? "true" : "false");
        request.setAttribute("signupBillingInformationFormComplete", signupBillingInformationFormComplete ? "true" : "false");

        return executeVirtualManagedStep(
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

    public abstract ActionForward executeVirtualManagedStep(
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
    ) throws Exception;
}
