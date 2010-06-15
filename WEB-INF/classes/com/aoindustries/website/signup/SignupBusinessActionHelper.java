package com.aoindustries.website.signup;

/*
 * Copyright 2007-2010 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
 * All rights reserved.
 */
import com.aoindustries.aoserv.client.AOServConnector;
import com.aoindustries.aoserv.client.CountryCode;
import com.aoindustries.io.ChainWriter;
import com.aoindustries.website.SiteSettings;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
        AOServConnector rootConn=SiteSettings.getInstance(servletContext).getRootAOServConnector();

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
    public static List<CountryOption> getCountryOptions(AOServConnector<?,?> aoConn) throws IOException {
        // Build the list of countries
        List<CountryOption> countryOptions = new ArrayList<CountryOption>();
        countryOptions.add(new CountryOption("", "---"));
        final int prioritySize = 10;
        int[] priorityCounter = new int[1];
        boolean selectedOne = false;
	List<CountryCode> cc = aoConn.getCountryCodes().getCountryCodesByPriority(prioritySize, priorityCounter);
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

    public static String getBusinessCountry(AOServConnector rootConn, SignupBusinessForm signupBusinessForm) throws IOException {
        return rootConn.getCountryCodes().get(signupBusinessForm.getBusinessCountry()).getName();
    }

    public static void setConfirmationRequestAttributes(
        ServletContext servletContext,
        HttpServletRequest request,
        SignupBusinessForm signupBusinessForm
    ) throws IOException {
        // Lookup things needed by the view
        AOServConnector rootConn = SiteSettings.getInstance(servletContext).getRootAOServConnector();

        // Store as request attribute for the view
        request.setAttribute("businessCountry", getBusinessCountry(rootConn, signupBusinessForm));
    }

    public static void printConfirmation(ChainWriter emailOut, MessageResources signupApplicationResources, AOServConnector rootConn, SignupBusinessForm signupBusinessForm) throws IOException {
        emailOut.print("    <tr>\n"
                     + "        <td>").print(signupApplicationResources.accessor.getMessage("signup.required")).print("</td>\n"
                     + "        <td>").print(signupApplicationResources.accessor.getMessage("signupBusinessForm.businessName.prompt")).print("</td>\n"
                     + "        <td>").encodeHtml(signupBusinessForm.getBusinessName()).print("</td>\n"
                     + "    </tr>\n"
                     + "    <tr>\n"
                     + "        <td>").print(signupApplicationResources.accessor.getMessage("signup.required")).print("</td>\n"
                     + "        <td>").print(signupApplicationResources.accessor.getMessage("signupBusinessForm.businessPhone.prompt")).print("</td>\n"
                     + "        <td>").encodeHtml(signupBusinessForm.getBusinessPhone()).print("</td>\n"
                     + "    </tr>\n"
                     + "    <tr>\n"
                     + "        <td>").print(signupApplicationResources.accessor.getMessage("signup.notRequired")).print("</td>\n"
                     + "        <td>").print(signupApplicationResources.accessor.getMessage("signupBusinessForm.businessFax.prompt")).print("</td>\n"
                     + "        <td>").encodeHtml(signupBusinessForm.getBusinessFax()).print("</td>\n"
                     + "    </tr>\n"
                     + "    <tr>\n"
                     + "        <td>").print(signupApplicationResources.accessor.getMessage("signup.required")).print("</td>\n"
                     + "        <td>").print(signupApplicationResources.accessor.getMessage("signupBusinessForm.businessAddress1.prompt")).print("</td>\n"
                     + "        <td>").encodeHtml(signupBusinessForm.getBusinessAddress1()).print("</td>\n"
                     + "    </tr>\n");
        if(!GenericValidator.isBlankOrNull(signupBusinessForm.getBusinessAddress2())) {
            emailOut.print("    <tr>\n"
                         + "        <td>").print(signupApplicationResources.accessor.getMessage("signup.notRequired")).print("</td>\n"
                         + "        <td>").print(signupApplicationResources.accessor.getMessage("signupBusinessForm.businessAddress2.prompt")).print("</td>\n"
                         + "        <td>").encodeHtml(signupBusinessForm.getBusinessAddress2()).print("</td>\n"
                         + "    </tr>\n");
        }
        emailOut.print("    <tr>\n"
                     + "        <td>").print(signupApplicationResources.accessor.getMessage("signup.required")).print("</td>\n"
                     + "        <td>").print(signupApplicationResources.accessor.getMessage("signupBusinessForm.businessCity.prompt")).print("</td>\n"
                     + "        <td>").encodeHtml(signupBusinessForm.getBusinessCity()).print("</td>\n"
                     + "    </tr>\n"
                     + "    <tr>\n"
                     + "        <td>").print(signupApplicationResources.accessor.getMessage("signup.notRequired")).print("</td>\n"
                     + "        <td>").print(signupApplicationResources.accessor.getMessage("signupBusinessForm.businessState.prompt")).print("</td>\n"
                     + "        <td>").encodeHtml(signupBusinessForm.getBusinessState()).print("</td>\n"
                     + "    </tr>\n"
                     + "    <tr>\n"
                     + "        <td>").print(signupApplicationResources.accessor.getMessage("signup.required")).print("</td>\n"
                     + "        <td>").print(signupApplicationResources.accessor.getMessage("signupBusinessForm.businessCountry.prompt")).print("</td>\n"
                     + "        <td>").encodeHtml(getBusinessCountry(rootConn, signupBusinessForm)).print("</td>\n"
                     + "    </tr>\n"
                     + "    <tr>\n"
                     + "        <td>").print(signupApplicationResources.accessor.getMessage("signup.notRequired")).print("</td>\n"
                     + "        <td>").print(signupApplicationResources.accessor.getMessage("signupBusinessForm.businessZip.prompt")).print("</td>\n"
                     + "        <td>").encodeHtml(signupBusinessForm.getBusinessZip()).print("</td>\n"
                     + "    </tr>\n");
    }
}
