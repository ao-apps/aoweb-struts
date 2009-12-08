package com.aoindustries.website.signup;

/*
 * Copyright 2007-2009 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
 * All rights reserved.
 */
import com.aoindustries.aoserv.client.AOServConnector;
import com.aoindustries.aoserv.client.Brand;
import com.aoindustries.aoserv.client.CountryCode;
import com.aoindustries.aoserv.client.PackageDefinition;
import com.aoindustries.io.ChainWriter;
import com.aoindustries.website.Mailer;
import com.aoindustries.website.SiteSettings;
import com.aoindustries.website.Skin;
import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.util.MessageResources;

/**
 * Managed6CompletedAction and Dedicated6CompletedAction both use this to setup the request attributes.  This is implemented
 * here because inheritence is not possible and neither one is logically above the other.
 *
 * @author  AO Industries, Inc.
 */
final public class ServerConfirmationCompletedActionHelper {

    /**
     * Make no instances.
     */
    private ServerConfirmationCompletedActionHelper() {}

    public static void addOptions(Map<String,String> options, SignupCustomizeServerForm signupCustomizeServerForm) {
        // Power option
        options.put("powerOption", Integer.toString(signupCustomizeServerForm.getPowerOption()));

        // CPU option
        options.put("cpuOption", Integer.toString(signupCustomizeServerForm.getCpuOption()));

        // RAM option
        options.put("ramOption", Integer.toString(signupCustomizeServerForm.getRamOption()));
        
        // SATA Controller option
        options.put("sataControllerOption", Integer.toString(signupCustomizeServerForm.getSataControllerOption()));

        // SCSI Controller option
        options.put("scsiControllerOption", Integer.toString(signupCustomizeServerForm.getScsiControllerOption()));

        // Disk options
        int number = 0;
        for(String diskOption : signupCustomizeServerForm.getDiskOptions()) {
            if(diskOption!=null && diskOption.length()>0 && !diskOption.equals("-1")) {
                options.put("diskOptions["+(number++)+"]", diskOption);
            }
        }
    }
    
    public static void addOptions(Map<String,String> options, SignupCustomizeManagementForm signupCustomizeManagementForm) {
        options.put("backupOnsiteOption", Integer.toString(signupCustomizeManagementForm.getBackupOnsiteOption()));
        options.put("backupOffsiteOption", Integer.toString(signupCustomizeManagementForm.getBackupOffsiteOption()));
        options.put("backupDvdOption", signupCustomizeManagementForm.getBackupDvdOption());
        options.put("distributionScanOption", Integer.toString(signupCustomizeManagementForm.getDistributionScanOption()));
        options.put("failoverOption", Integer.toString(signupCustomizeManagementForm.getFailoverOption()));
    }

    /**
     * Stores to the database, if possible.  Sets request attributes "pkey" and "statusKey", both as String type.
     */
    public static void storeToDatabase(
        ActionServlet servlet,
        HttpServletRequest request,
        AOServConnector rootConn,
        PackageDefinition packageDefinition,
        SignupBusinessForm signupBusinessForm,
        SignupTechnicalForm signupTechnicalForm,
        SignupBillingInformationForm signupBillingInformationForm,
        Map<String,String> options
    ) {
        // Store to the database
        int pkey;
        String statusKey;
        try {
            CountryCode businessCountry = rootConn.getCountryCodes().get(signupBusinessForm.getBusinessCountry());
            CountryCode baCountry = GenericValidator.isBlankOrNull(signupTechnicalForm.getBaCountry()) ? null : rootConn.getCountryCodes().get(signupTechnicalForm.getBaCountry());

            pkey = rootConn.getSignupRequests().addSignupRequest(
                rootConn.getThisBusinessAdministrator().getUsername().getPackage().getBusiness().getBrand(),
                request.getRemoteAddr(),
                packageDefinition,
                signupBusinessForm.getBusinessName(),
                signupBusinessForm.getBusinessPhone(),
                signupBusinessForm.getBusinessFax(),
                signupBusinessForm.getBusinessAddress1(),
                signupBusinessForm.getBusinessAddress2(),
                signupBusinessForm.getBusinessCity(),
                signupBusinessForm.getBusinessState(),
                businessCountry,
                signupBusinessForm.getBusinessZip(),
                signupTechnicalForm.getBaName(),
                signupTechnicalForm.getBaTitle(),
                signupTechnicalForm.getBaWorkPhone(),
                signupTechnicalForm.getBaCellPhone(),
                signupTechnicalForm.getBaHomePhone(),
                signupTechnicalForm.getBaFax(),
                signupTechnicalForm.getBaEmail(),
                signupTechnicalForm.getBaAddress1(),
                signupTechnicalForm.getBaAddress2(),
                signupTechnicalForm.getBaCity(),
                signupTechnicalForm.getBaState(),
                baCountry,
                signupTechnicalForm.getBaZip(),
                signupTechnicalForm.getBaUsername(),
                signupBillingInformationForm.getBillingContact(),
                signupBillingInformationForm.getBillingEmail(),
                signupBillingInformationForm.getBillingUseMonthly(),
                signupBillingInformationForm.getBillingPayOneYear(),
                signupTechnicalForm.getBaPassword(),
                signupBillingInformationForm.getBillingCardholderName(),
                signupBillingInformationForm.getBillingCardNumber(),
                signupBillingInformationForm.getBillingExpirationMonth(),
                signupBillingInformationForm.getBillingExpirationYear(),
                signupBillingInformationForm.getBillingStreetAddress(),
                signupBillingInformationForm.getBillingCity(),
                signupBillingInformationForm.getBillingState(),
                signupBillingInformationForm.getBillingZip(),
                options
            );
            statusKey = "serverConfirmationCompleted.success";
        } catch(RuntimeException err) {
            servlet.log("Unable to store signup", err);
            pkey = -1;
            statusKey = "serverConfirmationCompleted.error";
        } catch(IOException err) {
            servlet.log("Unable to store signup", err);
            pkey = -1;
            statusKey = "serverConfirmationCompleted.error";
        } catch(SQLException err) {
            servlet.log("Unable to store signup", err);
            pkey = -1;
            statusKey = "serverConfirmationCompleted.error";
        }

        request.setAttribute("statusKey", statusKey);
        request.setAttribute("pkey", Integer.toString(pkey));
    }
    
    public static void sendSupportSummaryEmail(
        ActionServlet servlet,
        HttpServletRequest request,
        String pkey,
        String statusKey,
        Locale userLocale,
        SiteSettings siteSettings,
        PackageDefinition packageDefinition,
        SignupCustomizeServerForm signupCustomizeServerForm,
        SignupCustomizeManagementForm signupCustomizeManagementForm,
        SignupBusinessForm signupBusinessForm,
        SignupTechnicalForm signupTechnicalForm,
        SignupBillingInformationForm signupBillingInformationForm
    ) {
        try {
            sendSummaryEmail(servlet, request, pkey, statusKey, siteSettings.getBrand().getAowebStrutsSignupAdminAddress(), userLocale, Locale.US, siteSettings, packageDefinition, signupCustomizeServerForm, signupCustomizeManagementForm, signupBusinessForm, signupTechnicalForm, signupBillingInformationForm);
        } catch(Exception err) {
            servlet.log("Unable to send sign up details to support admin address", err);
        }
    }
    
    /**
     * Sends the customer emails and stores the successAddresses and failureAddresses as request attributes.
     */
    public static void sendCustomerSummaryEmails(
        ActionServlet servlet,
        HttpServletRequest request,
        String pkey,
        String statusKey,
        Locale userLocale,
        SiteSettings siteSettings,
        PackageDefinition packageDefinition,
        SignupCustomizeServerForm signupCustomizeServerForm,
        SignupCustomizeManagementForm signupCustomizeManagementForm,
        SignupBusinessForm signupBusinessForm,
        SignupTechnicalForm signupTechnicalForm,
        SignupBillingInformationForm signupBillingInformationForm
    ) {
        Set<String> addresses = new HashSet<String>();
        addresses.add(signupTechnicalForm.getBaEmail());
        addresses.add(signupBillingInformationForm.getBillingEmail());
        Set<String> successAddresses = new HashSet<String>();
        Set<String> failureAddresses = new HashSet<String>();
        Iterator<String> I=addresses.iterator();
        while(I.hasNext()) {
            String address=I.next();
            boolean success = sendSummaryEmail(servlet, request, pkey, statusKey, address, userLocale, userLocale, siteSettings, packageDefinition, signupCustomizeServerForm, signupCustomizeManagementForm, signupBusinessForm, signupTechnicalForm, signupBillingInformationForm);
            if(success) successAddresses.add(address);
            else failureAddresses.add(address);
        }

        // Store request attributes
        request.setAttribute("successAddresses", successAddresses);
        request.setAttribute("failureAddresses", failureAddresses);
    }

    /**
     * Sends a summary email and returns <code>true</code> if successful.
     */
    private static boolean sendSummaryEmail(
        ActionServlet servlet,
        HttpServletRequest request,
        String pkey,
        String statusKey,
        String recipient,
        Locale userLocale,
        Locale subjectLocale,
        SiteSettings siteSettings,
        PackageDefinition packageDefinition,
        SignupCustomizeServerForm signupCustomizeServerForm,
        SignupCustomizeManagementForm signupCustomizeManagementForm,
        SignupBusinessForm signupBusinessForm,
        SignupTechnicalForm signupTechnicalForm,
        SignupBillingInformationForm signupBillingInformationForm
    ) {
        try {
            // Find the locale and related resource bundles
            String charset = Skin.getCharacterSet(userLocale);
            MessageResources signupApplicationResources = (MessageResources)request.getAttribute("/signup/ApplicationResources");
            if(signupApplicationResources==null) throw new JspException("Unable to load resources: /signup/ApplicationResources");

            // Generate the email contents
            CharArrayWriter cout = new CharArrayWriter();
            ChainWriter emailOut = new ChainWriter(cout);
            String htmlLang = getHtmlLang(userLocale);
            emailOut.print("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">\n"
                    + "<html xmlns=\"http://www.w3.org/1999/xhtml\"");
            if(htmlLang!=null) emailOut.print(" lang=\"").print(htmlLang).print("\" xml:lang=\"").print(htmlLang).print('"');
            emailOut.print(">\n"
                         + "<head>\n"
                         + "    <meta http-equiv='Content-Type' content='text/html; charset=").print(charset).print("' />\n");
            // Embed the text-only style sheet
            InputStream cssIn = servlet.getServletContext().getResourceAsStream("/textskin/global.css");
            if(cssIn!=null) {
                try {
                    emailOut.print("    <style type=\"text/css\">\n"
                                 + "      /* <![CDATA[ */\n");
                    Reader cssReader = new InputStreamReader(cssIn);
                    try {
                        char[] buff = new char[4096];
                        int ret;
                        while((ret=cssReader.read(buff, 0, 4096))!=-1) emailOut.write(buff, 0, ret);
                    } finally {
                        cssIn.close();
                    }
                    emailOut.print("      /* ]]> */\n"
                                 + "    </style>\n");
                } finally {
                    cssIn.close();
                }
            } else {
                servlet.log("Warning: Unable to find resource: /global/textskin.css");
            }
            emailOut.print("</head>\n"
                         + "<body>\n"
                         + "<table style='border:0px' cellpadding=\"0\" cellspacing=\"0\">\n"
                         + "    <tr><td style='white-space:nowrap' colspan=\"3\">\n"
                         + "        ").print(signupApplicationResources.getMessage(userLocale, statusKey, pkey)).print("<br />\n"
                         + "        <br />\n"
                         + "        ").print(signupApplicationResources.getMessage(userLocale, "serverConfirmationCompleted.belowIsSummary")).print("<br />\n"
                         + "        <hr />\n"
                         + "    </td></tr>\n"
                         + "    <tr><th colspan=\"3\">").print(signupApplicationResources.getMessage(userLocale, "steps.selectServer.label")).print("</th></tr>\n");
            SignupSelectServerActionHelper.printConfirmation(emailOut, userLocale, packageDefinition, signupApplicationResources);
            emailOut.print("    <tr><td colspan=\"3\">&#160;</td></tr>\n"
                         + "    <tr><th colspan=\"3\">").print(signupApplicationResources.getMessage(userLocale, "steps.customizeServer.label")).print("</th></tr>\n");
            AOServConnector rootConn = siteSettings.getRootAOServConnector();
            SignupCustomizeServerActionHelper.printConfirmation(request, emailOut, userLocale, rootConn, packageDefinition, signupCustomizeServerForm, signupApplicationResources);
            if(signupCustomizeManagementForm!=null) {
                emailOut.print("    <tr><td colspan=\"3\">&#160;</td></tr>\n"
                             + "    <tr><th colspan=\"3\">").print(signupApplicationResources.getMessage(userLocale, "steps.customizeManagement.label")).print("</th></tr>\n");
                SignupCustomizeManagementActionHelper.printConfirmation(
                    request,
                    emailOut,
                    userLocale,
                    rootConn,
                    signupCustomizeManagementForm,
                    signupApplicationResources
                );
            }
            emailOut.print("    <tr><td colspan=\"3\">&#160;</td></tr>\n"
                         + "    <tr><th colspan=\"3\">").print(signupApplicationResources.getMessage(userLocale, "steps.businessInfo.label")).print("</th></tr>\n");
            SignupBusinessActionHelper.printConfirmation(emailOut, userLocale, signupApplicationResources, rootConn, signupBusinessForm);
            emailOut.print("    <tr><td colspan=\"3\">&#160;</td></tr>\n"
                         + "    <tr><th colspan=\"3\">").print(signupApplicationResources.getMessage(userLocale, "steps.technicalInfo.label")).print("</th></tr>\n");
            SignupTechnicalActionHelper.printConfirmation(emailOut, userLocale, signupApplicationResources, rootConn, signupTechnicalForm);
            emailOut.print("    <tr><td colspan=\"3\">&#160;</td></tr>\n"
                         + "    <tr><th colspan=\"3\">").print(signupApplicationResources.getMessage(userLocale, "steps.billingInformation.label")).print("</th></tr>\n");
            SignupBillingInformationActionHelper.printConfirmation(emailOut, userLocale, signupApplicationResources, signupBillingInformationForm);
            emailOut.print("</table>\n"
                         + "</body>\n"
                         + "</html>\n");
            emailOut.flush();

            // Send the email
            Brand brand = siteSettings.getBrand();
            Mailer.sendEmail(
                brand.getSignupEmailAddress().getDomain().getAOServer().getHostname(),
                "text/html",
                charset,
                brand.getSignupEmailAddress().toString(),
                brand.getSignupEmailDisplay(),
                Collections.singletonList(recipient),
                signupApplicationResources.getMessage(subjectLocale, "serverConfirmationCompleted.email.subject", pkey),
                cout.toString()
            );

            return true;
        } catch(RuntimeException err) {
            servlet.log("Unable to send sign up details to "+recipient, err);
            return false;
        } catch(IOException err) {
            servlet.log("Unable to send sign up details to "+recipient, err);
            return false;
        } catch(SQLException err) {
            servlet.log("Unable to send sign up details to "+recipient, err);
            return false;
        } catch(MessagingException err) {
            servlet.log("Unable to send sign up details to "+recipient, err);
            return false;
        } catch(JspException err) {
            servlet.log("Unable to send sign up details to "+recipient, err);
            return false;
        }
    }

    public static String getHtmlLang(Locale locale) {
        String language = locale.getLanguage();
        if(language!=null) {
            String country = locale.getCountry();
            if(country!=null) {
                String variant = locale.getVariant();
                if(variant!=null) return language+"-"+country+"-"+variant;
                return language+"-"+country;
            }
            return language;
        }
        return null;
    }
}
