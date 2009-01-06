package com.aoindustries.website.signup;

/*
 * Copyright 2007-2009 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
 * All rights reserved.
 */
import com.aoindustries.aoserv.client.AOServConnector;
import com.aoindustries.aoserv.client.CountryCode;
import com.aoindustries.io.ChainWriter;
import com.aoindustries.website.RootAOServConnector;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.util.MessageResources;

/**
 * Managed3Action and Dedicated3Action both use this to setup the request attributes.  This is implemented
 * here because inheritence is not possible and neither one is logically above the other.
 *
 * @author  AO Industries, Inc.
 */
final public class SignupBusinessActionHelper {

    /**
     * Make no instances.
     */
    private SignupBusinessActionHelper() {}

    public static void setRequestAttributes(
        ServletContext servletContext,
        HttpServletRequest request
    ) throws IOException {
        AOServConnector rootConn=RootAOServConnector.getRootAOServConnector(servletContext);

        // Build the list of countries
        List<CountryOption> countryOptions = getCountryOptions(rootConn);

        // Store to the request
        request.setAttribute("countryOptions", countryOptions);
    }

    /**
     * Gets the options for use in a country list.
     * Note: you probably want to use the RootAOServConnector to provide a more helpful list than a
     * default user connector.
     *
     * @see  RootAOServConnector
     */
    public static List<CountryOption> getCountryOptions(AOServConnector aoConn) {
        // Build the list of countries
        List<CountryOption> countryOptions = new ArrayList<CountryOption>();
        countryOptions.add(new CountryOption("", "---"));
        final int prioritySize = 10;
        int[] priorityCounter = new int[1];
        boolean selectedOne = false;
	List<CountryCode> cc = aoConn.countryCodes.getCountryCodesByPriority(prioritySize, priorityCounter);
	for (int i = 0; i<cc.size(); i++) {
            if(priorityCounter[0]!=0 && i==priorityCounter[0]) {
                countryOptions.add(new CountryOption("", "---"));
            }
            String code = cc.get(i).getCode();
            String ccname = cc.get(i).getName();
            countryOptions.add(new CountryOption(code, ccname));
        }
        return countryOptions;
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

    public static String getBusinessCountry(AOServConnector rootConn, SignupBusinessForm signupBusinessForm) {
        return rootConn.countryCodes.get(signupBusinessForm.getBusinessCountry()).getName();
    }

    public static void setConfirmationRequestAttributes(
        ServletContext servletContext,
        HttpServletRequest request,
        SignupBusinessForm signupBusinessForm
    ) throws IOException {
        // Lookup things needed by the view
        AOServConnector rootConn = RootAOServConnector.getRootAOServConnector(servletContext);

        // Store as request attribute for the view
        request.setAttribute("businessCountry", getBusinessCountry(rootConn, signupBusinessForm));
    }

    public static void printConfirmation(ChainWriter emailOut, Locale contentLocale, MessageResources signupApplicationResources, AOServConnector rootConn, SignupBusinessForm signupBusinessForm) {
        emailOut.print("    <TR>\n"
                     + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signup.required")).print("</TD>\n"
                     + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signupBusinessForm.businessName.prompt")).print("</TD>\n"
                     + "        <TD>").writeHtml(signupBusinessForm.getBusinessName()).print("</TD>\n"
                     + "    </TR>\n"
                     + "    <TR>\n"
                     + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signup.required")).print("</TD>\n"
                     + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signupBusinessForm.businessPhone.prompt")).print("</TD>\n"
                     + "        <TD>").writeHtml(signupBusinessForm.getBusinessPhone()).print("</TD>\n"
                     + "    </TR>\n"
                     + "    <TR>\n"
                     + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signup.notRequired")).print("</TD>\n"
                     + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signupBusinessForm.businessFax.prompt")).print("</TD>\n"
                     + "        <TD>").writeHtml(signupBusinessForm.getBusinessFax()).print("</TD>\n"
                     + "    </TR>\n"
                     + "    <TR>\n"
                     + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signup.required")).print("</TD>\n"
                     + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signupBusinessForm.businessAddress1.prompt")).print("</TD>\n"
                     + "        <TD>").writeHtml(signupBusinessForm.getBusinessAddress1()).print("</TD>\n"
                     + "    </TR>\n");
        if(!GenericValidator.isBlankOrNull(signupBusinessForm.getBusinessAddress2())) {
            emailOut.print("    <TR>\n"
                         + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signup.notRequired")).print("</TD>\n"
                         + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signupBusinessForm.businessAddress2.prompt")).print("</TD>\n"
                         + "        <TD>").writeHtml(signupBusinessForm.getBusinessAddress2()).print("</TD>\n"
                         + "    </TR>\n");
        }
        emailOut.print("    <TR>\n"
                     + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signup.required")).print("</TD>\n"
                     + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signupBusinessForm.businessCity.prompt")).print("</TD>\n"
                     + "        <TD>").writeHtml(signupBusinessForm.getBusinessCity()).print("</TD>\n"
                     + "    </TR>\n"
                     + "    <TR>\n"
                     + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signup.notRequired")).print("</TD>\n"
                     + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signupBusinessForm.businessState.prompt")).print("</TD>\n"
                     + "        <TD>").writeHtml(signupBusinessForm.getBusinessState()).print("</TD>\n"
                     + "    </TR>\n"
                     + "    <TR>\n"
                     + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signup.required")).print("</TD>\n"
                     + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signupBusinessForm.businessCountry.prompt")).print("</TD>\n"
                     + "        <TD>").writeHtml(getBusinessCountry(rootConn, signupBusinessForm)).print("</TD>\n"
                     + "    </TR>\n"
                     + "    <TR>\n"
                     + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signup.notRequired")).print("</TD>\n"
                     + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signupBusinessForm.businessZip.prompt")).print("</TD>\n"
                     + "        <TD>").writeHtml(signupBusinessForm.getBusinessZip()).print("</TD>\n"
                     + "    </TR>\n");
    }
}
