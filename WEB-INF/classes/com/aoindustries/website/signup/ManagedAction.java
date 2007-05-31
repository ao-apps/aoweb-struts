package com.aoindustries.website.signup;

/*
 * Copyright 2007 by AO Industries, Inc.,
 * 816 Azalea Rd, Mobile, Alabama, 36693, U.S.A.
 * All rights reserved.
 */
import com.aoindustries.aoserv.client.AOServConnector;
import com.aoindustries.aoserv.client.Business;
import com.aoindustries.aoserv.client.PackageCategory;
import com.aoindustries.aoserv.client.PackageDefinition;
import com.aoindustries.aoserv.client.PackageDefinitionLimit;
import com.aoindustries.sql.SQLUtility;
import com.aoindustries.website.RootAOServConnector;
import com.aoindustries.website.Skin;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
public class ManagedAction extends ManagedStepAction {

    public ActionForward executeManagedStep(
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
        SignupSelectServerActionHelper.setRequestAttributes(getServlet().getServletContext(), request, PackageCategory.MANAGED);

        return mapping.findForward("input");
    }
}
