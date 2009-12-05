package com.aoindustries.website.signup;

/*
 * Copyright 2007-2009 by AO Industries, Inc.,
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
import org.apache.struts.action.ActionMessages;

/**
 * @author  AO Industries, Inc.
 */
public class Managed2Action extends ManagedStepAction {

    public ActionForward executeManagedStep(
        ActionMapping mapping,
        HttpServletRequest request,
        HttpServletResponse response,
        SiteSettings siteSettings,
        Locale locale,
        Skin skin,
        ManagedSignupSelectServerForm signupSelectServerForm,
        boolean signupSelectServerFormComplete,
        ManagedSignupCustomizeServerForm signupCustomizeServerForm,
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
        if(!signupSelectServerFormComplete) return mapping.findForward("managed-server-completed");

        SignupCustomizeServerActionHelper.setRequestAttributes(getServlet().getServletContext(), request, response, signupSelectServerForm, signupCustomizeServerForm, true);

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

    /*private static class PriceComparator implements Comparator<Option> {
        public int compare(Option pdl1, Option pdl2) {
            return pdl1.getPriceDifference().compareTo(pdl2.getPriceDifference());
        }
    }*/
}
