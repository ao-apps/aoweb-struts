package com.aoindustries.website.signup;

/*
 * Copyright 2007-2009 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
 * All rights reserved.
 */
import com.aoindustries.aoserv.client.AOServConnector;
import com.aoindustries.aoserv.client.LinuxAccountTable;
import com.aoindustries.io.ChainWriter;
import com.aoindustries.website.SiteSettings;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.util.MessageResources;

/**
 * Managed4Action and Dedicated4Action both use this to setup the request attributes.  This is implemented
 * here because inheritence is not possible and neither one is logically above the other.
 *
 * @author  AO Industries, Inc.
 */
final public class SignupTechnicalActionHelper {

    /**
     * Make no instances.
     */
    private SignupTechnicalActionHelper() {}

    public static void setRequestAttributes(
        ServletContext servletContext,
        HttpServletRequest request,
        SignupTechnicalForm signupTechnicalForm
    ) throws IOException, SQLException {
        AOServConnector rootConn=SiteSettings.getInstance(servletContext).getRootAOServConnector();

        // Build the list of countries
        List<SignupBusinessActionHelper.CountryOption> countryOptions = SignupBusinessActionHelper.getCountryOptions(rootConn);

        // Generate random passwords, keeping the selected password at index 0
        List<String> passwords = new ArrayList<String>(16);
        if(!GenericValidator.isBlankOrNull(signupTechnicalForm.getBaPassword())) passwords.add(signupTechnicalForm.getBaPassword());
        while(passwords.size()<16) passwords.add(LinuxAccountTable.generatePassword());

        // Store to the request
        request.setAttribute("countryOptions", countryOptions);
        request.setAttribute("passwords", passwords);
    }

    public static String getBaCountry(AOServConnector rootConn, SignupTechnicalForm signupTechnicalForm) throws IOException, SQLException {
        String baCountry = signupTechnicalForm.getBaCountry();
        return baCountry==null || baCountry.length()==0 ? "" : rootConn.getCountryCodes().get(baCountry).getName();
    }

    public static void setConfirmationRequestAttributes(
        ServletContext servletContext,
        HttpServletRequest request,
        SignupTechnicalForm signupTechnicalForm
    ) throws IOException, SQLException {
        // Lookup things needed by the view
        AOServConnector rootConn = SiteSettings.getInstance(servletContext).getRootAOServConnector();

        // Store as request attribute for the view
        request.setAttribute("baCountry", getBaCountry(rootConn, signupTechnicalForm));
    }

    public static void printConfirmation(ChainWriter emailOut, Locale userLocale, MessageResources signupApplicationResources, AOServConnector rootConn, SignupTechnicalForm signupTechnicalForm) throws IOException, SQLException {
        emailOut.print("    <tr>\n"
                     + "        <td>").print(signupApplicationResources.getMessage(userLocale, "signup.required")).print("</td>\n"
                     + "        <td>").print(signupApplicationResources.getMessage(userLocale, "signupTechnicalForm.baName.prompt")).print("</td>\n"
                     + "        <td>").encodeHtml(signupTechnicalForm.getBaName()).print("</td>\n"
                     + "    </tr>\n"
                     + "    <tr>\n"
                     + "        <td>").print(signupApplicationResources.getMessage(userLocale, "signup.notRequired")).print("</td>\n"
                     + "        <td>").print(signupApplicationResources.getMessage(userLocale, "signupTechnicalForm.baTitle.prompt")).print("</td>\n"
                     + "        <td>").encodeHtml(signupTechnicalForm.getBaTitle()).print("</td>\n"
                     + "    </tr>\n"
                     + "    <tr>\n"
                     + "        <td>").print(signupApplicationResources.getMessage(userLocale, "signup.required")).print("</td>\n"
                     + "        <td>").print(signupApplicationResources.getMessage(userLocale, "signupTechnicalForm.baWorkPhone.prompt")).print("</td>\n"
                     + "        <td>").encodeHtml(signupTechnicalForm.getBaWorkPhone()).print("</td>\n"
                     + "    </tr>\n"
                     + "    <tr>\n"
                     + "        <td>").print(signupApplicationResources.getMessage(userLocale, "signup.notRequired")).print("</td>\n"
                     + "        <td>").print(signupApplicationResources.getMessage(userLocale, "signupTechnicalForm.baCellPhone.prompt")).print("</td>\n"
                     + "        <td>").encodeHtml(signupTechnicalForm.getBaCellPhone()).print("</td>\n"
                     + "    </tr>\n"
                     + "    <tr>\n"
                     + "        <td>").print(signupApplicationResources.getMessage(userLocale, "signup.notRequired")).print("</td>\n"
                     + "        <td>").print(signupApplicationResources.getMessage(userLocale, "signupTechnicalForm.baHomePhone.prompt")).print("</td>\n"
                     + "        <td>").encodeHtml(signupTechnicalForm.getBaHomePhone()).print("</td>\n"
                     + "    </tr>\n"
                     + "    <tr>\n"
                     + "        <td>").print(signupApplicationResources.getMessage(userLocale, "signup.notRequired")).print("</td>\n"
                     + "        <td>").print(signupApplicationResources.getMessage(userLocale, "signupTechnicalForm.baFax.prompt")).print("</td>\n"
                     + "        <td>").encodeHtml(signupTechnicalForm.getBaFax()).print("</td>\n"
                     + "    </tr>\n"
                     + "    <tr>\n"
                     + "        <td>").print(signupApplicationResources.getMessage(userLocale, "signup.required")).print("</td>\n"
                     + "        <td>").print(signupApplicationResources.getMessage(userLocale, "signupTechnicalForm.baEmail.prompt")).print("</td>\n"
                     + "        <td>").encodeHtml(signupTechnicalForm.getBaEmail()).print("</td>\n"
                     + "    </tr>\n"
                     + "    <tr>\n"
                     + "        <td>").print(signupApplicationResources.getMessage(userLocale, "signup.notRequired")).print("</td>\n"
                     + "        <td>").print(signupApplicationResources.getMessage(userLocale, "signupTechnicalForm.baAddress1.prompt")).print("</td>\n"
                     + "        <td>").encodeHtml(signupTechnicalForm.getBaAddress1()).print("</td>\n"
                     + "    </tr>\n");
        if(!GenericValidator.isBlankOrNull(signupTechnicalForm.getBaAddress2())) {
            emailOut.print("    <tr>\n"
                         + "        <td>").print(signupApplicationResources.getMessage(userLocale, "signup.notRequired")).print("</td>\n"
                         + "        <td>").print(signupApplicationResources.getMessage(userLocale, "signupTechnicalForm.baAddress2.prompt")).print("</td>\n"
                         + "        <td>").encodeHtml(signupTechnicalForm.getBaAddress2()).print("</td>\n"
                         + "    </tr>\n");
        }
        emailOut.print("    <tr>\n"
                     + "        <td>").print(signupApplicationResources.getMessage(userLocale, "signup.notRequired")).print("</td>\n"
                     + "        <td>").print(signupApplicationResources.getMessage(userLocale, "signupTechnicalForm.baCity.prompt")).print("</td>\n"
                     + "        <td>").encodeHtml(signupTechnicalForm.getBaCity()).print("</td>\n"
                     + "    </tr>\n"
                     + "    <tr>\n"
                     + "        <td>").print(signupApplicationResources.getMessage(userLocale, "signup.notRequired")).print("</td>\n"
                     + "        <td>").print(signupApplicationResources.getMessage(userLocale, "signupTechnicalForm.baState.prompt")).print("</td>\n"
                     + "        <td>").encodeHtml(signupTechnicalForm.getBaState()).print("</td>\n"
                     + "    </tr>\n"
                     + "    <tr>\n"
                     + "        <td>").print(signupApplicationResources.getMessage(userLocale, "signup.notRequired")).print("</td>\n"
                     + "        <td>").print(signupApplicationResources.getMessage(userLocale, "signupTechnicalForm.baCountry.prompt")).print("</td>\n"
                     + "        <td>").encodeHtml(getBaCountry(rootConn, signupTechnicalForm)).print("</td>\n"
                     + "    </tr>\n"
                     + "    <tr>\n"
                     + "        <td>").print(signupApplicationResources.getMessage(userLocale, "signup.notRequired")).print("</td>\n"
                     + "        <td>").print(signupApplicationResources.getMessage(userLocale, "signupTechnicalForm.baZip.prompt")).print("</td>\n"
                     + "        <td>").encodeHtml(signupTechnicalForm.getBaZip()).print("</td>\n"
                     + "    </tr>\n"
                     + "    <tr>\n"
                     + "        <td>").print(signupApplicationResources.getMessage(userLocale, "signup.required")).print("</td>\n"
                     + "        <td>").print(signupApplicationResources.getMessage(userLocale, "signupTechnicalForm.baUsername.prompt")).print("</td>\n"
                     + "        <td>").encodeHtml(signupTechnicalForm.getBaUsername()).print("</td>\n"
                     + "    </tr>\n"
                     + "    <tr>\n"
                     + "        <td>").print(signupApplicationResources.getMessage(userLocale, "signup.notRequired")).print("</td>\n"
                     + "        <td>").print(signupApplicationResources.getMessage(userLocale, "signupTechnicalForm.baPassword.prompt")).print("</td>\n"
                     + "        <td>").encodeHtml(signupTechnicalForm.getBaPassword()).print("</td>\n"
                     + "    </tr>\n");
    }
}
