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
abstract public class DedicatedStepAction extends HttpsAction {

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
        HttpSession session = request.getSession();

        DedicatedSignupSelectServerForm dedicatedSignupSelectServerForm = (DedicatedSignupSelectServerForm)session.getAttribute("dedicatedSignupSelectServerForm");
        DedicatedSignupCustomizeServerForm dedicatedSignupCustomizeServerForm = (DedicatedSignupCustomizeServerForm)session.getAttribute("dedicatedSignupCustomizeServerForm");
        SignupBusinessForm signupBusinessForm = (SignupBusinessForm)session.getAttribute("signupBusinessForm");
        SignupTechnicalForm signupTechnicalForm = (SignupTechnicalForm)session.getAttribute("signupTechnicalForm");
        SignupBillingInformationForm signupBillingInformationForm = (SignupBillingInformationForm)session.getAttribute("signupBillingInformationForm");

        ActionMessages dedicatedSignupSelectServerFormErrors = dedicatedSignupSelectServerForm==null ? null : dedicatedSignupSelectServerForm.validate(mapping, request);
        ActionMessages dedicatedSignupCustomizeServerFormErrors = dedicatedSignupCustomizeServerForm==null ? null : dedicatedSignupCustomizeServerForm.validate(mapping, request);
        ActionMessages signupBusinessFormErrors = signupBusinessForm==null ? null : signupBusinessForm.validate(mapping, request);
        ActionMessages signupTechnicalFormErrors = signupTechnicalForm==null ? null : signupTechnicalForm.validate(mapping, request);
        ActionMessages signupBillingInformationFormErrors = signupBillingInformationForm==null ? null : signupBillingInformationForm.validate(mapping, request);

        boolean dedicatedSignupSelectServerFormComplete = dedicatedSignupSelectServerForm==null ? false : !doAddErrors(request, dedicatedSignupSelectServerFormErrors);
        boolean dedicatedSignupCustomizeServerFormComplete = dedicatedSignupCustomizeServerForm==null ? false : !doAddErrors(request, dedicatedSignupCustomizeServerFormErrors);
        boolean signupBusinessFormComplete = signupBusinessForm==null ? false : !doAddErrors(request, signupBusinessFormErrors);
        boolean signupTechnicalFormComplete = signupTechnicalForm==null ? false : !doAddErrors(request, signupTechnicalFormErrors);
        boolean signupBillingInformationFormComplete = signupBillingInformationForm==null ? false : !doAddErrors(request, signupBillingInformationFormErrors);

        request.setAttribute("dedicatedSignupSelectServerFormComplete", dedicatedSignupSelectServerFormComplete ? "true" : "false");
        request.setAttribute("dedicatedSignupCustomizeServerFormComplete", dedicatedSignupCustomizeServerFormComplete ? "true" : "false");
        request.setAttribute("signupBusinessFormComplete", signupBusinessFormComplete ? "true" : "false");
        request.setAttribute("signupTechnicalFormComplete", signupTechnicalFormComplete ? "true" : "false");
        request.setAttribute("signupBillingInformationFormComplete", signupBillingInformationFormComplete ? "true" : "false");

        return executeDedicatedStep(
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

    public abstract ActionForward executeDedicatedStep(
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
    ) throws Exception;
}
