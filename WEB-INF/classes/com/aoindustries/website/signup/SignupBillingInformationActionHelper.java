package com.aoindustries.website.signup;

/*
 * Copyright 2007-2009 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
 * All rights reserved.
 */
import com.aoindustries.creditcards.CreditCard;
import com.aoindustries.io.ChainWriter;
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
        // Store as request attribute for the view
        request.setAttribute("billingCardNumber", getBillingCardNumber(signupBillingInformationForm));
    }

    public static void printConfirmation(ChainWriter emailOut, Locale userLocale, MessageResources signupApplicationResources, SignupBillingInformationForm signupBillingInformationForm) throws IOException {
        emailOut.print("    <tr>\n"
                     + "        <td>").print(signupApplicationResources.getMessage(userLocale, "signup.required")).print("</td>\n"
                     + "        <td>").print(signupApplicationResources.getMessage(userLocale, "signupBillingInformationForm.billingContact.prompt")).print("</td>\n"
                     + "        <td>").encodeHtml(signupBillingInformationForm.getBillingContact()).print("</td>\n"
                     + "    </tr>\n"
                     + "    <tr>\n"
                     + "        <td>").print(signupApplicationResources.getMessage(userLocale, "signup.required")).print("</td>\n"
                     + "        <td>").print(signupApplicationResources.getMessage(userLocale, "signupBillingInformationForm.billingEmail.prompt")).print("</td>\n"
                     + "        <td>").encodeHtml(signupBillingInformationForm.getBillingEmail()).print("</td>\n"
                     + "    </tr>\n"
                     + "    <tr>\n"
                     + "        <td>").print(signupApplicationResources.getMessage(userLocale, "signup.required")).print("</td>\n"
                     + "        <td>").print(signupApplicationResources.getMessage(userLocale, "signupBillingInformationForm.billingCardholderName.prompt")).print("</td>\n"
                     + "        <td>").encodeHtml(signupBillingInformationForm.getBillingCardholderName()).print("</td>\n"
                     + "    </tr>\n"
                     + "    <tr>\n"
                     + "        <td>").print(signupApplicationResources.getMessage(userLocale, "signup.required")).print("</td>\n"
                     + "        <td>").print(signupApplicationResources.getMessage(userLocale, "signupBillingInformationForm.billingCardNumber.prompt")).print("</td>\n"
                     + "        <td>").encodeHtml(getBillingCardNumber(signupBillingInformationForm)).print("</td>\n"
                     + "    </tr>\n"
                     + "    <tr>\n"
                     + "        <td>").print(signupApplicationResources.getMessage(userLocale, "signup.required")).print("</td>\n"
                     + "        <td>").print(signupApplicationResources.getMessage(userLocale, "signupBillingInformationForm.billingExpirationDate.prompt")).print("</td>\n"
                     + "        <td>").print(signupApplicationResources.getMessage(userLocale, "signupBillingInformationForm.billingExpirationDate.hidden")).print("</td>\n"
                     + "    </tr>\n"
                     + "    <tr>\n"
                     + "        <td>").print(signupApplicationResources.getMessage(userLocale, "signup.required")).print("</td>\n"
                     + "        <td>").print(signupApplicationResources.getMessage(userLocale, "signupBillingInformationForm.billingStreetAddress.prompt")).print("</td>\n"
                     + "        <td>").encodeHtml(signupBillingInformationForm.getBillingStreetAddress()).print("</td>\n"
                     + "    </tr>\n"
                     + "    <tr>\n"
                     + "        <td>").print(signupApplicationResources.getMessage(userLocale, "signup.required")).print("</td>\n"
                     + "        <td>").print(signupApplicationResources.getMessage(userLocale, "signupBillingInformationForm.billingCity.prompt")).print("</td>\n"
                     + "        <td>").encodeHtml(signupBillingInformationForm.getBillingCity()).print("</td>\n"
                     + "    </tr>\n"
                     + "    <tr>\n"
                     + "        <td>").print(signupApplicationResources.getMessage(userLocale, "signup.required")).print("</td>\n"
                     + "        <td>").print(signupApplicationResources.getMessage(userLocale, "signupBillingInformationForm.billingState.prompt")).print("</td>\n"
                     + "        <td>").encodeHtml(signupBillingInformationForm.getBillingState()).print("</td>\n"
                     + "    </tr>\n"
                     + "    <tr>\n"
                     + "        <td>").print(signupApplicationResources.getMessage(userLocale, "signup.required")).print("</td>\n"
                     + "        <td>").print(signupApplicationResources.getMessage(userLocale, "signupBillingInformationForm.billingZip.prompt")).print("</td>\n"
                     + "        <td>").encodeHtml(signupBillingInformationForm.getBillingZip()).print("</td>\n"
                     + "    </tr>\n"
                     + "    <tr>\n"
                     + "        <td>").print(signupApplicationResources.getMessage(userLocale, "signup.notRequired")).print("</td>\n"
                     + "        <td>").print(signupApplicationResources.getMessage(userLocale, "signupBillingInformationForm.billingUseMonthly.prompt")).print("</td>\n"
                     + "        <td>").print(signupApplicationResources.getMessage(userLocale, signupBillingInformationForm.getBillingUseMonthly() ? "signupBillingInformationForm.billingUseMonthly.yes" : "signupBillingInformationForm.billingUseMonthly.no")).print("</td>\n"
                     + "    </tr>\n"
                     + "    <tr>\n"
                     + "        <td>").print(signupApplicationResources.getMessage(userLocale, "signup.notRequired")).print("</td>\n"
                     + "        <td>").print(signupApplicationResources.getMessage(userLocale, "signupBillingInformationForm.billingPayOneYear.prompt")).print("</td>\n"
                     + "        <td>").print(signupApplicationResources.getMessage(userLocale, signupBillingInformationForm.getBillingPayOneYear() ? "signupBillingInformationForm.billingPayOneYear.yes" : "signupBillingInformationForm.billingPayOneYear.no")).print("</td>\n"
                     + "    </tr>\n");
    }
}
