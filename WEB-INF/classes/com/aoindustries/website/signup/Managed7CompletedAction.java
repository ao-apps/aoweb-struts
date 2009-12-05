package com.aoindustries.website.signup;

/*
 * Copyright 2007-2009 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
 * All rights reserved.
 */
import com.aoindustries.aoserv.client.AOServConnector;
import com.aoindustries.aoserv.client.PackageDefinition;
import com.aoindustries.website.SiteSettings;
import com.aoindustries.website.Skin;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionServlet;

/**
 * @author  AO Industries, Inc.
 */
public class Managed7CompletedAction extends Managed7Action {

    @Override
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
        // Forward to previous steps if they have not been completed
        if(!signupSelectServerFormComplete) return mapping.findForward("managed-server-completed");
        if(!signupCustomizeServerFormComplete) return mapping.findForward("managed-server-2-completed");
        if(!signupCustomizeManagementFormComplete) return mapping.findForward("managed-server-3-completed");
        if(!signupBusinessFormComplete) return mapping.findForward("managed-server-4-completed");
        if(!signupTechnicalFormComplete) return mapping.findForward("managed-server-5-completed");
        if(!signupBillingInformationFormComplete) return mapping.findForward("managed-server-6-completed");

        // Let the parent class do the initialization of the request attributes for both the emails and the final JSP
        initRequestAttributes(
            request,
            response,
            signupSelectServerForm,
            signupCustomizeServerForm,
            signupCustomizeManagementForm,
            signupBusinessForm,
            signupTechnicalForm,
            signupBillingInformationForm
        );

        // Used later
        HttpSession session = request.getSession();
        ActionServlet myServlet = getServlet();
        ServletContext servletContext = myServlet.getServletContext();
        AOServConnector rootConn = siteSettings.getRootAOServConnector();
        PackageDefinition packageDefinition = rootConn.getPackageDefinitions().get(signupSelectServerForm.getPackageDefinition());

        // Build the options map
        Map<String,String> options = new HashMap<String,String>();
        ConfirmationCompletedActionHelper.addOptions(options, signupCustomizeServerForm);
        ConfirmationCompletedActionHelper.addOptions(options, signupCustomizeManagementForm);

        // Store to the database
        ConfirmationCompletedActionHelper.storeToDatabase(myServlet, request, rootConn, packageDefinition, signupBusinessForm, signupTechnicalForm, signupBillingInformationForm, options);
        String pkey = (String)request.getAttribute("pkey");
        String statusKey = (String)request.getAttribute("statusKey");

        Locale userLocale = (Locale)session.getAttribute(Globals.LOCALE_KEY);

        // Send confirmation email to support
        ConfirmationCompletedActionHelper.sendSupportSummaryEmail(
            myServlet,
            request,
            pkey,
            statusKey,
            userLocale,
            siteSettings,
            packageDefinition,
            signupCustomizeServerForm,
            signupCustomizeManagementForm,
            signupBusinessForm,
            signupTechnicalForm,
            signupBillingInformationForm
        );

        // Send confirmation email to customer
        ConfirmationCompletedActionHelper.sendCustomerSummaryEmails(
            myServlet,
            request,
            pkey,
            statusKey,
            userLocale,
            siteSettings,
            packageDefinition,
            signupCustomizeServerForm,
            signupCustomizeManagementForm,
            signupBusinessForm,
            signupTechnicalForm,
            signupBillingInformationForm
        );
        
        // Clear managed signup-specific forms from the session
        session.removeAttribute("managedSignupSelectServerForm");
        session.removeAttribute("managedSignupCustomizeServerForm");
        session.removeAttribute("managedSignupCustomizeManagementForm");

        return mapping.findForward("success");
    }
}
