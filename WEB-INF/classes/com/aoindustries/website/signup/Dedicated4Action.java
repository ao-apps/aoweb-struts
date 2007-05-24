package com.aoindustries.website.signup;

/*
 * Copyright 2007 by AO Industries, Inc.,
 * 816 Azalea Rd, Mobile, Alabama, 36693, U.S.A.
 * All rights reserved.
 */
import com.aoindustries.aoserv.client.AOServConnector;
import com.aoindustries.aoserv.client.CountryCode;
import com.aoindustries.website.RootAOServConnector;
import com.aoindustries.website.Skin;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

/**
 * @author  AO Industries, Inc.
 */
public class Dedicated4Action extends DedicatedStepAction {

    public ActionForward executeDedicatedStep(
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
    ) throws Exception {
        if(!dedicatedSignupSelectServerFormComplete) return mapping.findForward("dedicated");
        if(!dedicatedSignupCustomizeServerFormComplete) return mapping.findForward("dedicated2");
        if(!signupBusinessFormComplete) return mapping.findForward("dedicated3");

        AOServConnector rootConn=RootAOServConnector.getRootAOServConnector(getServlet().getServletContext());

        // Build the list of countries
        List<CountryOption> countryOptions = new ArrayList<CountryOption>();
        countryOptions.add(new CountryOption("", "---"));
        final int prioritySize = 10;
        int[] priorityCounter = new int[1];
        boolean selectedOne = false;
	List<CountryCode> cc = rootConn.countryCodes.getCountryCodesByPriority(prioritySize, priorityCounter);
	for (int i = 0; i<cc.size(); i++) {
            if(priorityCounter[0]!=0 && i==priorityCounter[0]) {
                countryOptions.add(new CountryOption("", "---"));
            }
            String code = cc.get(i).getCode();
            String ccname = cc.get(i).getName();
            countryOptions.add(new CountryOption(code, ccname));
        }
        
        // Generate random passwords, keeping the selected password at index 0
        List<String> passwords = new ArrayList<String>(16);
        if(!GenericValidator.isBlankOrNull(signupTechnicalForm.getBaPassword())) passwords.add(signupTechnicalForm.getBaPassword());
        while(passwords.size()<16) passwords.add(rootConn.linuxAccounts.generatePassword());

        // Store to the request
        request.setAttribute("countryOptions", countryOptions);
        request.setAttribute("passwords", passwords);

        // Clear errors if they should not be displayed
        clearErrors(request);

        return mapping.findForward("input");
    }

    public static class CountryOption {

        final private String code;
        final private String name;

        private CountryOption(String code, String name) {
            this.code = code;
            this.name = name;
        }
        
        public String getCode() {
            return code;
        }
        
        public String getName() {
            return name;
        }
    }

    /**
     * May clear specific errors here.
     */
    protected void clearErrors(HttpServletRequest request) {
        saveErrors(request, new ActionMessages());
    }
}
