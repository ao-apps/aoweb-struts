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

    public static void printConfirmation(ChainWriter emailOut, Locale contentLocale, MessageResources signupApplicationResources, AOServConnector rootConn, SignupTechnicalForm signupTechnicalForm) throws IOException, SQLException {
        emailOut.print("    <TR>\n"
                     + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signup.required")).print("</TD>\n"
                     + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signupTechnicalForm.baName.prompt")).print("</TD>\n"
                     + "        <TD>").writeHtml(signupTechnicalForm.getBaName()).print("</TD>\n"
                     + "    </TR>\n"
                     + "    <TR>\n"
                     + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signup.notRequired")).print("</TD>\n"
                     + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signupTechnicalForm.baTitle.prompt")).print("</TD>\n"
                     + "        <TD>").writeHtml(signupTechnicalForm.getBaTitle()).print("</TD>\n"
                     + "    </TR>\n"
                     + "    <TR>\n"
                     + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signup.required")).print("</TD>\n"
                     + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signupTechnicalForm.baWorkPhone.prompt")).print("</TD>\n"
                     + "        <TD>").writeHtml(signupTechnicalForm.getBaWorkPhone()).print("</TD>\n"
                     + "    </TR>\n"
                     + "    <TR>\n"
                     + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signup.notRequired")).print("</TD>\n"
                     + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signupTechnicalForm.baCellPhone.prompt")).print("</TD>\n"
                     + "        <TD>").writeHtml(signupTechnicalForm.getBaCellPhone()).print("</TD>\n"
                     + "    </TR>\n"
                     + "    <TR>\n"
                     + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signup.notRequired")).print("</TD>\n"
                     + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signupTechnicalForm.baHomePhone.prompt")).print("</TD>\n"
                     + "        <TD>").writeHtml(signupTechnicalForm.getBaHomePhone()).print("</TD>\n"
                     + "    </TR>\n"
                     + "    <TR>\n"
                     + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signup.notRequired")).print("</TD>\n"
                     + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signupTechnicalForm.baFax.prompt")).print("</TD>\n"
                     + "        <TD>").writeHtml(signupTechnicalForm.getBaFax()).print("</TD>\n"
                     + "    </TR>\n"
                     + "    <TR>\n"
                     + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signup.required")).print("</TD>\n"
                     + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signupTechnicalForm.baEmail.prompt")).print("</TD>\n"
                     + "        <TD>").writeHtml(signupTechnicalForm.getBaEmail()).print("</TD>\n"
                     + "    </TR>\n"
                     + "    <TR>\n"
                     + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signup.notRequired")).print("</TD>\n"
                     + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signupTechnicalForm.baAddress1.prompt")).print("</TD>\n"
                     + "        <TD>").writeHtml(signupTechnicalForm.getBaAddress1()).print("</TD>\n"
                     + "    </TR>\n");
        if(!GenericValidator.isBlankOrNull(signupTechnicalForm.getBaAddress2())) {
            emailOut.print("    <TR>\n"
                         + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signup.notRequired")).print("</TD>\n"
                         + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signupTechnicalForm.baAddress2.prompt")).print("</TD>\n"
                         + "        <TD>").writeHtml(signupTechnicalForm.getBaAddress2()).print("</TD>\n"
                         + "    </TR>\n");
        }
        emailOut.print("    <TR>\n"
                     + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signup.notRequired")).print("</TD>\n"
                     + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signupTechnicalForm.baCity.prompt")).print("</TD>\n"
                     + "        <TD>").writeHtml(signupTechnicalForm.getBaCity()).print("</TD>\n"
                     + "    </TR>\n"
                     + "    <TR>\n"
                     + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signup.notRequired")).print("</TD>\n"
                     + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signupTechnicalForm.baState.prompt")).print("</TD>\n"
                     + "        <TD>").writeHtml(signupTechnicalForm.getBaState()).print("</TD>\n"
                     + "    </TR>\n"
                     + "    <TR>\n"
                     + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signup.notRequired")).print("</TD>\n"
                     + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signupTechnicalForm.baCountry.prompt")).print("</TD>\n"
                     + "        <TD>").writeHtml(getBaCountry(rootConn, signupTechnicalForm)).print("</TD>\n"
                     + "    </TR>\n"
                     + "    <TR>\n"
                     + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signup.notRequired")).print("</TD>\n"
                     + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signupTechnicalForm.baZip.prompt")).print("</TD>\n"
                     + "        <TD>").writeHtml(signupTechnicalForm.getBaZip()).print("</TD>\n"
                     + "    </TR>\n"
                     + "    <TR>\n"
                     + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signup.required")).print("</TD>\n"
                     + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signupTechnicalForm.baUsername.prompt")).print("</TD>\n"
                     + "        <TD>").writeHtml(signupTechnicalForm.getBaUsername()).print("</TD>\n"
                     + "    </TR>\n"
                     + "    <TR>\n"
                     + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signup.notRequired")).print("</TD>\n"
                     + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signupTechnicalForm.baPassword.prompt")).print("</TD>\n"
                     + "        <TD>").writeHtml(signupTechnicalForm.getBaPassword()).print("</TD>\n"
                     + "    </TR>\n");
    }
}
