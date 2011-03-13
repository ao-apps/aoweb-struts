package com.aoindustries.website.signup;

/*
 * Copyright 2009-2011 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
 * All rights reserved.
 */
import com.aoindustries.aoserv.client.AOServConnector;
import com.aoindustries.aoserv.client.PackageDefinition;
import com.aoindustries.website.SiteSettings;
import com.aoindustries.website.Skin;
import java.util.HashMap;
import java.util.Map;
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
public class Backup5CompletedAction extends Backup5Action {

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
        if(!signupBusinessFormComplete) return mapping.findForward("backup-2-completed");
        if(!signupTechnicalFormComplete) return mapping.findForward("backup-3-completed");
        if(!signupBillingInformationFormComplete) return mapping.findForward("backup-4-completed");

        // Let the parent class do the initialization of the request attributes for both the emails and the final JSP
        initRequestAttributes(
            request,
            response,
            signupSelectPackageForm,
            signupBusinessForm,
            signupTechnicalForm,
            signupBillingInformationForm
        );

        // Used later
        HttpSession session = request.getSession();
        ActionServlet myServlet = getServlet();
        AOServConnector rootConn = siteSettings.getRootAOServConnector();
        PackageDefinition packageDefinition = rootConn.getPackageDefinitions().get(signupSelectPackageForm.getPackageDefinition());

        // Build the options map
        Map<String,String> options = new HashMap<String,String>();

        // Store to the database
        ServerConfirmationCompletedActionHelper.storeToDatabase(myServlet, request, rootConn, packageDefinition, signupBusinessForm, signupTechnicalForm, signupBillingInformationForm, options);
        String pkey = (String)request.getAttribute("pkey");
        String statusKey = (String)request.getAttribute("statusKey");

        // Send confirmation email to support
        MinimalConfirmationCompletedActionHelper.sendSupportSummaryEmail(
            myServlet,
            request,
            pkey,
            statusKey,
            siteSettings,
            packageDefinition,
            signupBusinessForm,
            signupTechnicalForm,
            signupBillingInformationForm
        );

        // Send confirmation email to customer
        MinimalConfirmationCompletedActionHelper.sendCustomerSummaryEmails(
            myServlet,
            request,
            pkey,
            statusKey,
            siteSettings,
            packageDefinition,
            signupBusinessForm,
            signupTechnicalForm,
            signupBillingInformationForm
        );
        
        // Clear backup signup-specific forms from the session
        session.removeAttribute("backupSignupSelectPackageForm");

        return mapping.findForward("success");
    }
}
