package com.aoindustries.website.signup;

/*
 * Copyright 2007 by AO Industries, Inc.,
 * 816 Azalea Rd, Mobile, Alabama, 36693, U.S.A.
 * All rights reserved.
 */
import com.aoindustries.aoserv.client.AOServConnector;
import com.aoindustries.aoserv.client.PackageDefinition;
import com.aoindustries.aoserv.client.PackageDefinitionLimit;
import com.aoindustries.sql.SQLUtility;
import com.aoindustries.website.RootAOServConnector;
import com.aoindustries.website.Skin;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

/**
 * @author  AO Industries, Inc.
 */
public class VirtualManaged6Action extends VirtualManagedStepAction {

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
        SignupBusinessForm signupBusinessForm,
        boolean signupBusinessFormComplete,
        SignupTechnicalForm signupTechnicalForm,
        boolean signupTechnicalFormComplete,
        SignupBillingInformationForm signupBillingInformationForm,
        boolean signupBillingInformationFormComplete
    ) throws Exception {
        if(!signupSelectServerFormComplete) return mapping.findForward("virtualManaged");
        if(!signupCustomizeServerFormComplete) return mapping.findForward("virtualManaged2");
        if(!signupBusinessFormComplete) return mapping.findForward("virtualManaged3");
        if(!signupTechnicalFormComplete) return mapping.findForward("virtualManaged4");
        if(!signupBillingInformationFormComplete) return mapping.findForward("virtualManaged5");

        initRequestAttributes(
            request,
            signupSelectServerForm,
            signupCustomizeServerForm,
            signupBusinessForm,
            signupTechnicalForm,
            signupBillingInformationForm
        );

        return mapping.findForward("input");
    }

    protected void initRequestAttributes(
        HttpServletRequest request,
        SignupSelectServerForm signupSelectServerForm,
        SignupCustomizeServerForm signupCustomizeServerForm,
        SignupBusinessForm signupBusinessForm,
        SignupTechnicalForm signupTechnicalForm,
        SignupBillingInformationForm signupBillingInformationForm
    ) throws IOException {
        ServletContext servletContext = getServlet().getServletContext();

        SignupSelectServerActionHelper.setConfirmationRequestAttributes(servletContext, request, signupSelectServerForm);
        SignupCustomizeServerActionHelper.setConfirmationRequestAttributes(servletContext, request, signupSelectServerForm, signupCustomizeServerForm);
        SignupBusinessActionHelper.setConfirmationRequestAttributes(servletContext, request, signupBusinessForm);
        SignupTechnicalActionHelper.setConfirmationRequestAttributes(servletContext, request, signupTechnicalForm);
        SignupBillingInformationActionHelper.setConfirmationRequestAttributes(servletContext, request, signupBillingInformationForm);
    }
}
