package com.aoindustries.website.signup;

/*
 * Copyright 2007-2009 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
 * All rights reserved.
 */
import com.aoindustries.aoserv.client.AOServConnector;
import com.aoindustries.creditcards.CreditCard;
import com.aoindustries.io.ChainWriter;
import com.aoindustries.website.RootAOServConnector;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.util.MessageResources;

/**
 * Managed5Action and Dedicated5Action both use this to setup the request attributes.  This is implemented
 * here because inheritence is not possible and neither one is logically above the other.
 *
 * @author  AO Industries, Inc.
 */
final public class SignupBillingInformationActionHelper {

    /**
     * Make no instances.
     */
    private SignupBillingInformationActionHelper() {}

    public static void setRequestAttributes(HttpServletRequest request) {
        setBillingExpirationYearsRequestAttribute(request);
    }
    
    public static void setBillingExpirationYearsRequestAttribute(HttpServletRequest request) {
        // Build the list of years
        List<String> billingExpirationYears = new ArrayList<String>(12);
        int startYear = Calendar.getInstance().get(Calendar.YEAR);
        for(int c=0;c<12;c++) billingExpirationYears.add(Integer.toString(startYear+c));

        // Store to request attributes
        request.setAttribute("billingExpirationYears", billingExpirationYears);
    }

    /**
     * Only shows the first two and last four digits of a card number.
     *
     * @deprecated  Please call CreditCard.maskCreditCardNumber directly.
     */
    public static String hideCreditCardNumber(String number) {
        return CreditCard.maskCreditCardNumber(number);
    }

    public static String getBillingCardNumber(SignupBillingInformationForm signupBillingInformationForm) {
        return CreditCard.maskCreditCardNumber(signupBillingInformationForm.getBillingCardNumber());
    }

    public static void setConfirmationRequestAttributes(
        ServletContext servletContext,
        HttpServletRequest request,
        SignupBillingInformationForm signupBillingInformationForm
    ) throws IOException {
        // Lookup things needed by the view
        AOServConnector rootConn = RootAOServConnector.getRootAOServConnector(servletContext);

        // Store as request attribute for the view
        request.setAttribute("billingCardNumber", getBillingCardNumber(signupBillingInformationForm));
    }

    public static void printConfirmation(ChainWriter emailOut, Locale contentLocale, MessageResources signupApplicationResources, SignupBillingInformationForm signupBillingInformationForm) {
        emailOut.print("    <TR>\n"
                     + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signup.required")).print("</TD>\n"
                     + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signupBillingInformationForm.billingContact.prompt")).print("</TD>\n"
                     + "        <TD>").writeHtml(signupBillingInformationForm.getBillingContact()).print("</TD>\n"
                     + "    </TR>\n"
                     + "    <TR>\n"
                     + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signup.required")).print("</TD>\n"
                     + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signupBillingInformationForm.billingEmail.prompt")).print("</TD>\n"
                     + "        <TD>").writeHtml(signupBillingInformationForm.getBillingEmail()).print("</TD>\n"
                     + "    </TR>\n"
                     + "    <TR>\n"
                     + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signup.required")).print("</TD>\n"
                     + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signupBillingInformationForm.billingCardholderName.prompt")).print("</TD>\n"
                     + "        <TD>").writeHtml(signupBillingInformationForm.getBillingCardholderName()).print("</TD>\n"
                     + "    </TR>\n"
                     + "    <TR>\n"
                     + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signup.required")).print("</TD>\n"
                     + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signupBillingInformationForm.billingCardNumber.prompt")).print("</TD>\n"
                     + "        <TD>").writeHtml(getBillingCardNumber(signupBillingInformationForm)).print("</TD>\n"
                     + "    </TR>\n"
                     + "    <TR>\n"
                     + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signup.required")).print("</TD>\n"
                     + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signupBillingInformationForm.billingExpirationDate.prompt")).print("</TD>\n"
                     + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signupBillingInformationForm.billingExpirationDate.hidden")).print("</TD>\n"
                     + "    </TR>\n"
                     + "    <TR>\n"
                     + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signup.required")).print("</TD>\n"
                     + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signupBillingInformationForm.billingStreetAddress.prompt")).print("</TD>\n"
                     + "        <TD>").writeHtml(signupBillingInformationForm.getBillingStreetAddress()).print("</TD>\n"
                     + "    </TR>\n"
                     + "    <TR>\n"
                     + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signup.required")).print("</TD>\n"
                     + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signupBillingInformationForm.billingCity.prompt")).print("</TD>\n"
                     + "        <TD>").writeHtml(signupBillingInformationForm.getBillingCity()).print("</TD>\n"
                     + "    </TR>\n"
                     + "    <TR>\n"
                     + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signup.required")).print("</TD>\n"
                     + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signupBillingInformationForm.billingState.prompt")).print("</TD>\n"
                     + "        <TD>").writeHtml(signupBillingInformationForm.getBillingState()).print("</TD>\n"
                     + "    </TR>\n"
                     + "    <TR>\n"
                     + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signup.required")).print("</TD>\n"
                     + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signupBillingInformationForm.billingZip.prompt")).print("</TD>\n"
                     + "        <TD>").writeHtml(signupBillingInformationForm.getBillingZip()).print("</TD>\n"
                     + "    </TR>\n"
                     + "    <TR>\n"
                     + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signup.notRequired")).print("</TD>\n"
                     + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signupBillingInformationForm.billingUseMonthly.prompt")).print("</TD>\n"
                     + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, signupBillingInformationForm.getBillingUseMonthly() ? "signupBillingInformationForm.billingUseMonthly.yes" : "signupBillingInformationForm.billingUseMonthly.no")).print("</TD>\n"
                     + "    </TR>\n"
                     + "    <TR>\n"
                     + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signup.notRequired")).print("</TD>\n"
                     + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signupBillingInformationForm.billingPayOneYear.prompt")).print("</TD>\n"
                     + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, signupBillingInformationForm.getBillingPayOneYear() ? "signupBillingInformationForm.billingPayOneYear.yes" : "signupBillingInformationForm.billingPayOneYear.no")).print("</TD>\n"
                     + "    </TR>\n");
    }
}
