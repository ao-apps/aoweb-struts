package com.aoindustries.website.signup;

/*
 * Copyright 2007 by AO Industries, Inc.,
 * 816 Azalea Rd, Mobile, Alabama, 36693, U.S.A.
 * All rights reserved.
 */
import com.aoindustries.aoserv.client.AOServConnector;
import com.aoindustries.aoserv.client.CountryCode;
import com.aoindustries.aoserv.client.PackageDefinition;
import com.aoindustries.io.ChainWriter;
import com.aoindustries.website.Mailer;
import com.aoindustries.website.RootAOServConnector;
import com.aoindustries.website.Skin;
import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import javax.mail.MessagingException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

/**
 * @author  AO Industries, Inc.
 */
public class Dedicated6CompletedAction extends Dedicated6Action {

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
        // Forward to previous steps if they have not been completed
        if(!dedicatedSignupSelectServerFormComplete) return mapping.findForward("dedicated");
        if(!dedicatedSignupCustomizeServerFormComplete) return mapping.findForward("dedicated2");
        if(!signupBusinessFormComplete) return mapping.findForward("dedicated3");
        if(!signupTechnicalFormComplete) return mapping.findForward("dedicated4");
        if(!signupBillingInformationFormComplete) return mapping.findForward("dedicated5");

        // Let the parent class do the initialization of the request attributes for both the emails and the final JSP
        initRequestAttributes(
            request,
            dedicatedSignupSelectServerForm,
            dedicatedSignupCustomizeServerForm,
            signupBusinessForm,
            signupTechnicalForm,
            signupBillingInformationForm
        );

        // Used later
        HttpSession session = request.getSession();
        ServletContext servletContext = getServlet().getServletContext();
        AOServConnector rootConn = RootAOServConnector.getRootAOServConnector(servletContext);
        PackageDefinition packageDefinition = rootConn.packageDefinitions.get(dedicatedSignupSelectServerForm.getPackageDefinition());

        // Add the options
        Map<String,String> options = new HashMap<String,String>();

        // Power option
        options.put("powerOption", Integer.toString(dedicatedSignupCustomizeServerForm.getPowerOption()));

        // CPU option
        options.put("cpuOption", Integer.toString(dedicatedSignupCustomizeServerForm.getCpuOption()));

        // RAM option
        options.put("ramOption", Integer.toString(dedicatedSignupCustomizeServerForm.getRamOption()));
        
        // SATA Controller option
        options.put("sataControllerOption", Integer.toString(dedicatedSignupCustomizeServerForm.getSataControllerOption()));

        // SCSI Controller option
        options.put("scsiControllerOption", Integer.toString(dedicatedSignupCustomizeServerForm.getScsiControllerOption()));

        // IDE options
        int number = 0;
        for(String ideOption : dedicatedSignupCustomizeServerForm.getIdeOptions()) {
            if(ideOption!=null && ideOption.length()>0 && !ideOption.equals("-1")) {
                options.put("ideOptions["+(number++)+"]", ideOption);
            }
        }

        // SATA options
        number = 0;
        for(String sataOption : dedicatedSignupCustomizeServerForm.getSataOptions()) {
            if(sataOption!=null && sataOption.length()>0 && !sataOption.equals("-1")) {
                options.put("sataOptions["+(number++)+"]", sataOption);
            }
        }

        // SCSI options
        number = 0;
        for(String scsiOption : dedicatedSignupCustomizeServerForm.getScsiOptions()) {
            if(scsiOption!=null && scsiOption.length()>0 && !scsiOption.equals("-1")) {
                options.put("scsiOptions["+(number++)+"]", scsiOption);
            }
        }

        // Store to the database
        int pkey;
        String statusKey;
        try {
            CountryCode businessCountry = rootConn.countryCodes.get(signupBusinessForm.getBusinessCountry());
            CountryCode baCountry = GenericValidator.isBlankOrNull(signupTechnicalForm.getBaCountry()) ? null : rootConn.countryCodes.get(signupTechnicalForm.getBaCountry());

            pkey = rootConn.signupRequests.addSignupRequest(
                rootConn.getThisBusinessAdministrator().getUsername().getPackage().getBusiness(),
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
            statusKey = "dedicated6Completed.success";
        } catch(RuntimeException err) {
            getServlet().log("Unable to store signup", err);
            pkey = -1;
            statusKey = "dedicated6Completed.error";
        } catch(IOException err) {
            getServlet().log("Unable to store signup", err);
            pkey = -1;
            statusKey = "dedicated6Completed.error";
        } catch(SQLException err) {
            getServlet().log("Unable to store signup", err);
            pkey = -1;
            statusKey = "dedicated6Completed.error";
        }
        
        // Send confirmation email to support
        Locale contentLocale = (Locale)session.getAttribute(Globals.LOCALE_KEY);
        sendSummaryEmail(skin, request, session, pkey, statusKey, servletContext.getInitParameter("com.aoindustries.website.signup.admin.address"), contentLocale, Locale.US, rootConn, packageDefinition, dedicatedSignupSelectServerForm, dedicatedSignupCustomizeServerForm, signupBusinessForm, signupTechnicalForm, signupBillingInformationForm);

        // Send confirmation email to customer
        Set<String> addresses = new HashSet<String>();
        addresses.add(signupTechnicalForm.getBaEmail());
        addresses.add(signupBillingInformationForm.getBillingEmail());
        Set<String> successAddresses = new HashSet<String>();
        Set<String> failureAddresses = new HashSet<String>();
        Iterator<String> I=addresses.iterator();
        while(I.hasNext()) {
            String address=I.next();
            boolean success = sendSummaryEmail(skin, request, session, pkey, statusKey, address, contentLocale, contentLocale, rootConn, packageDefinition, dedicatedSignupSelectServerForm, dedicatedSignupCustomizeServerForm, signupBusinessForm, signupTechnicalForm, signupBillingInformationForm);
            if(success) successAddresses.add(address);
            else failureAddresses.add(address);
        }
        
        // Clear dedicated signup-specific forms from the session
        session.removeAttribute("dedicatedSignupSelectServerForm");
        session.removeAttribute("dedicatedSignupCustomizeServerForm");

        // Store request attributes
        //request.setAttribute("dedicatedSignupSelectServerForm", dedicatedSignupSelectServerForm);
        //request.setAttribute("dedicatedSignupCustomizeServerForm", dedicatedSignupCustomizeServerForm);
        request.setAttribute("statusKey", statusKey);
        request.setAttribute("pkey", Integer.toString(pkey));
        request.setAttribute("successAddresses", successAddresses);
        request.setAttribute("failureAddresses", failureAddresses);

        return mapping.findForward("success");
    }

    /**
     * Sends a summary email and returns <code>true</code> if successful.
     */
    private boolean sendSummaryEmail(
        Skin skin,
        HttpServletRequest request,
        HttpSession session,
        int pkey,
        String statusKey,
        String recipient,
        Locale contentLocale,
        Locale subjectLocale,
        AOServConnector rootConn,
        PackageDefinition packageDefinition,
        DedicatedSignupSelectServerForm dedicatedSignupSelectServerForm,
        DedicatedSignupCustomizeServerForm dedicatedSignupCustomizeServerForm,
        SignupBusinessForm signupBusinessForm,
        SignupTechnicalForm signupTechnicalForm,
        SignupBillingInformationForm signupBillingInformationForm
    ) {
        try {
            // Find the locale and related resource bundles
            String charset = skin.getCharacterSet(contentLocale);
            MessageResources signupApplicationResources = (MessageResources)request.getAttribute("/signup/ApplicationResources");
            if(signupApplicationResources==null) throw new JspException("Unable to load resources: /signup/ApplicationResources");

            // Generate the email contents
            CharArrayWriter cout = new CharArrayWriter();
            ChainWriter emailOut = new ChainWriter(cout);
            String htmlLang = getHtmlLang(contentLocale);
            emailOut.print("<HTML");
            if(htmlLang!=null) emailOut.print(" lang=\"").print(htmlLang).print('"');
            emailOut.print(">\n"
                         + "<HEAD>\n"
                         + "    <META http-equiv='Content-Type' content='text/html; charset=").print(charset).print("'>\n");
            // Embed the text-only style sheet
            InputStream cssIn = getServlet().getServletContext().getResourceAsStream("/textskin.css");
            if(cssIn!=null) {
                try {
                    emailOut.print("    <style type=\"text/css\">\n");
                    Reader cssReader = new InputStreamReader(cssIn);
                    try {
                        char[] buff = new char[4096];
                        int ret;
                        while((ret=cssReader.read(buff, 0, 4096))!=-1) emailOut.write(buff, 0, ret);
                    } finally {
                        cssIn.close();
                    }
                    emailOut.print("    </style>\n");
                } finally {
                    cssIn.close();
                }
            } else {
                getServlet().log("Warning: Unable to find resource: /textskin.css");
            }
            emailOut.print("</HEAD>\n"
                         + "<BODY>\n"
                         + "<TABLE BORDER=\"0\" CELLPADDING=\"0\" CELLSPACING=\"0\">\n"
                         + "    <TR><TD nowrap colspan=\"3\">\n"
                         + "        ").print(signupApplicationResources.getMessage(contentLocale, statusKey, Integer.toString(pkey))).print("<BR>\n"
                         + "        <BR>\n"
                         + "        ").print(signupApplicationResources.getMessage(contentLocale, "dedicated6Completed.belowIsSummary")).print("<BR>\n"
                         + "        <HR>\n"
                         + "    </TD></TR>\n"
                         + "    <TR><TH colspan=\"3\">").print(signupApplicationResources.getMessage(contentLocale, "steps.selectServer.label")).print("</TH></TR>\n"
                         + "    <TR>\n"
                         + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signup.notRequired")).print("</TD>\n"
                         + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "dedicatedSignupSelectServerForm.packageDefinition.prompt")).print("</TD>\n"
                         + "        <TD>").writeHtml(packageDefinition.getDisplay()).print("</TD>\n"
                         + "    </TR>\n"
                         + "    <TR><TD colspan=\"3\">&nbsp;</TD></TR>\n"
                         + "    <TR><TH colspan=\"3\">").print(signupApplicationResources.getMessage(contentLocale, "steps.customizeServer.label")).print("</TH></TR>\n");
            String powerOption = getPowerOption(rootConn, dedicatedSignupCustomizeServerForm);
            if(!GenericValidator.isBlankOrNull(powerOption)) {
                emailOut.print("    <TR>\n"
                             + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signup.notRequired")).print("</TD>\n"
                             + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "dedicated6.power.prompt")).print("</TD>\n"
                             + "        <TD>").print(powerOption).print("</TD>\n"
                             + "    </TR>\n");
            }
            emailOut.print("    <TR>\n"
                         + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signup.notRequired")).print("</TD>\n"
                         + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "dedicated6.cpu.prompt")).print("</TD>\n"
                         + "        <TD>").print(getCpuOption(rootConn, dedicatedSignupCustomizeServerForm)).print("</TD>\n"
                         + "    </TR>\n"
                         + "    <TR>\n"
                         + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signup.notRequired")).print("</TD>\n"
                         + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "dedicated6.ram.prompt")).print("</TD>\n"
                         + "        <TD>").writeHtml(getRamOption(rootConn, dedicatedSignupCustomizeServerForm)).print("</TD>\n"
                         + "    </TR>\n");
            String sataControllerOption = getSataControllerOption(rootConn, dedicatedSignupCustomizeServerForm);
            if(!GenericValidator.isBlankOrNull(sataControllerOption)) {
                emailOut.print("    <TR>\n"
                             + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signup.notRequired")).print("</TD>\n"
                             + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "dedicated6.sataController.prompt")).print("</TD>\n"
                             + "        <TD>").print(sataControllerOption).print("</TD>\n"
                             + "    </TR>\n");
            }
            String scsiControllerOption = getScsiControllerOption(rootConn, dedicatedSignupCustomizeServerForm);
            if(!GenericValidator.isBlankOrNull(scsiControllerOption)) {
                emailOut.print("    <TR>\n"
                             + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signup.notRequired")).print("</TD>\n"
                             + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "dedicated6.scsiController.prompt")).print("</TD>\n"
                             + "        <TD>").print(scsiControllerOption).print("</TD>\n"
                             + "    </TR>\n");
            }
            for(String ideOption : getIdeOptions(rootConn, dedicatedSignupCustomizeServerForm)) {
                emailOut.print("    <TR>\n"
                             + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signup.notRequired")).print("</TD>\n"
                             + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "dedicated6.ide.prompt")).print("</TD>\n"
                             + "        <TD>").writeHtml(ideOption).print("</TD>\n"
                             + "    </TR>\n");
            }
            for(String sataOption : getSataOptions(rootConn, dedicatedSignupCustomizeServerForm)) {
                emailOut.print("    <TR>\n"
                             + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signup.notRequired")).print("</TD>\n"
                             + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "dedicated6.sata.prompt")).print("</TD>\n"
                             + "        <TD>").writeHtml(sataOption).print("</TD>\n"
                             + "    </TR>\n");
            }
            for(String scsiOption : getScsiOptions(rootConn, dedicatedSignupCustomizeServerForm)) {
                emailOut.print("    <TR>\n"
                             + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signup.notRequired")).print("</TD>\n"
                             + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "dedicated6.scsi.prompt")).print("</TD>\n"
                             + "        <TD>").writeHtml(scsiOption).print("</TD>\n"
                             + "    </TR>\n");
            }
            emailOut.print("    <TR>\n"
                         + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signup.notRequired")).print("</TD>\n"
                         + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "dedicated6.setup.prompt")).print("</TD>\n"
                         + "        <TD>\n");
            BigDecimal setup = getSetup(packageDefinition);
            if(setup==null) {
                emailOut.print("            ").print(signupApplicationResources.getMessage(contentLocale, "dedicated.setup.none")).print("\n");
            } else {
                emailOut.print("            $").print(setup).print("\n");
            }
            emailOut.print("        </TD>\n"
                         + "    </TR>\n"
                         + "    <TR>\n"
                         + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signup.notRequired")).print("</TD>\n"
                         + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "dedicated6.monthlyRate.prompt")).print("</TD>\n"
                         + "        <TD>$").print(request.getAttribute("monthlyRate")).print("</TD>\n"
                         + "    </TR>\n"
                         + "    <TR><TD colspan=\"3\">&nbsp;</TD></TR>\n"
                         + "    <TR><TH colspan=\"3\">").print(signupApplicationResources.getMessage(contentLocale, "steps.businessInfo.label")).print("</TH></TR>\n"
                         + "    <TR>\n"
                         + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signup.notRequired")).print("</TD>\n"
                         + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signupBusinessForm.businessName.prompt")).print("</TD>\n"
                         + "        <TD>").writeHtml(signupBusinessForm.getBusinessName()).print("</TD>\n"
                         + "    </TR>\n"
                         + "    <TR>\n"
                         + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signup.notRequired")).print("</TD>\n"
                         + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signupBusinessForm.businessPhone.prompt")).print("</TD>\n"
                         + "        <TD>").writeHtml(signupBusinessForm.getBusinessPhone()).print("</TD>\n"
                         + "    </TR>\n"
                         + "    <TR>\n"
                         + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signup.notRequired")).print("</TD>\n"
                         + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signupBusinessForm.businessFax.prompt")).print("</TD>\n"
                         + "        <TD>").writeHtml(signupBusinessForm.getBusinessFax()).print("</TD>\n"
                         + "    </TR>\n"
                         + "    <TR>\n"
                         + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signup.notRequired")).print("</TD>\n"
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
                         + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signup.notRequired")).print("</TD>\n"
                         + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signupBusinessForm.businessCity.prompt")).print("</TD>\n"
                         + "        <TD>").writeHtml(signupBusinessForm.getBusinessCity()).print("</TD>\n"
                         + "    </TR>\n"
                         + "    <TR>\n"
                         + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signup.notRequired")).print("</TD>\n"
                         + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signupBusinessForm.businessState.prompt")).print("</TD>\n"
                         + "        <TD>").writeHtml(signupBusinessForm.getBusinessState()).print("</TD>\n"
                         + "    </TR>\n"
                         + "    <TR>\n"
                         + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signup.notRequired")).print("</TD>\n"
                         + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signupBusinessForm.businessCountry.prompt")).print("</TD>\n"
                         + "        <TD>").writeHtml(getBusinessCountry(rootConn, signupBusinessForm)).print("</TD>\n"
                         + "    </TR>\n"
                         + "    <TR>\n"
                         + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signup.notRequired")).print("</TD>\n"
                         + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signupBusinessForm.businessZip.prompt")).print("</TD>\n"
                         + "        <TD>").writeHtml(signupBusinessForm.getBusinessZip()).print("</TD>\n"
                         + "    </TR>\n"
                         + "    <TR><TD colspan=\"3\">&nbsp;</TD></TR>\n"
                         + "    <TR><TH colspan=\"3\">").print(signupApplicationResources.getMessage(contentLocale, "steps.technicalInfo.label")).print("</TH></TR>\n"
                         + "    <TR>\n"
                         + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signup.notRequired")).print("</TD>\n"
                         + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signupTechnicalForm.baName.prompt")).print("</TD>\n"
                         + "        <TD>").writeHtml(signupTechnicalForm.getBaName()).print("</TD>\n"
                         + "    </TR>\n"
                         + "    <TR>\n"
                         + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signup.notRequired")).print("</TD>\n"
                         + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signupTechnicalForm.baTitle.prompt")).print("</TD>\n"
                         + "        <TD>").writeHtml(signupTechnicalForm.getBaTitle()).print("</TD>\n"
                         + "    </TR>\n"
                         + "    <TR>\n"
                         + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signup.notRequired")).print("</TD>\n"
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
                         + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signup.notRequired")).print("</TD>\n"
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
                         + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signup.notRequired")).print("</TD>\n"
                         + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signupTechnicalForm.baUsername.prompt")).print("</TD>\n"
                         + "        <TD>").writeHtml(signupTechnicalForm.getBaUsername()).print("</TD>\n"
                         + "    </TR>\n"
                         + "    <TR>\n"
                         + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signup.notRequired")).print("</TD>\n"
                         + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signupTechnicalForm.baPassword.prompt")).print("</TD>\n"
                         + "        <TD>").writeHtml(signupTechnicalForm.getBaPassword()).print("</TD>\n"
                         + "    </TR>\n"
                         + "    <TR><TD colspan=\"3\">&nbsp;</TD></TR>\n"
                         + "    <TR><TH colspan=\"3\">").print(signupApplicationResources.getMessage(contentLocale, "steps.billingInformation.label")).print("</TH></TR>\n"
                         + "    <TR>\n"
                         + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signup.notRequired")).print("</TD>\n"
                         + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signupBillingInformationForm.billingContact.prompt")).print("</TD>\n"
                         + "        <TD>").writeHtml(signupBillingInformationForm.getBillingContact()).print("</TD>\n"
                         + "    </TR>\n"
                         + "    <TR>\n"
                         + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signup.notRequired")).print("</TD>\n"
                         + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signupBillingInformationForm.billingEmail.prompt")).print("</TD>\n"
                         + "        <TD>").writeHtml(signupBillingInformationForm.getBillingEmail()).print("</TD>\n"
                         + "    </TR>\n"
                         + "    <TR>\n"
                         + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signup.notRequired")).print("</TD>\n"
                         + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signupBillingInformationForm.billingCardholderName.prompt")).print("</TD>\n"
                         + "        <TD>").writeHtml(signupBillingInformationForm.getBillingCardholderName()).print("</TD>\n"
                         + "    </TR>\n"
                         + "    <TR>\n"
                         + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signup.notRequired")).print("</TD>\n"
                         + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signupBillingInformationForm.billingCardNumber.prompt")).print("</TD>\n"
                         + "        <TD>").writeHtml(getBillingCardNumber(signupBillingInformationForm)).print("</TD>\n"
                         + "    </TR>\n"
                         + "    <TR>\n"
                         + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signup.notRequired")).print("</TD>\n"
                         + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signupBillingInformationForm.billingExpirationDate.prompt")).print("</TD>\n"
                         + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "dedicated6.billingExpirationDate.hidden")).print("</TD>\n"
                         + "    </TR>\n"
                         + "    <TR>\n"
                         + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signup.notRequired")).print("</TD>\n"
                         + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signupBillingInformationForm.billingStreetAddress.prompt")).print("</TD>\n"
                         + "        <TD>").writeHtml(signupBillingInformationForm.getBillingStreetAddress()).print("</TD>\n"
                         + "    </TR>\n"
                         + "    <TR>\n"
                         + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signup.notRequired")).print("</TD>\n"
                         + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signupBillingInformationForm.billingCity.prompt")).print("</TD>\n"
                         + "        <TD>").writeHtml(signupBillingInformationForm.getBillingCity()).print("</TD>\n"
                         + "    </TR>\n"
                         + "    <TR>\n"
                         + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signup.notRequired")).print("</TD>\n"
                         + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signupBillingInformationForm.billingState.prompt")).print("</TD>\n"
                         + "        <TD>").writeHtml(signupBillingInformationForm.getBillingState()).print("</TD>\n"
                         + "    </TR>\n"
                         + "    <TR>\n"
                         + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signup.notRequired")).print("</TD>\n"
                         + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signupBillingInformationForm.billingZip.prompt")).print("</TD>\n"
                         + "        <TD>").writeHtml(signupBillingInformationForm.getBillingZip()).print("</TD>\n"
                         + "    </TR>\n"
                         + "    <TR>\n"
                         + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signup.notRequired")).print("</TD>\n"
                         + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "dedicated6.billingUseMonthly.prompt")).print("</TD>\n"
                         + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, signupBillingInformationForm.getBillingUseMonthly() ? "dedicated6.billingUseMonthly.yes" : "dedicated6.billingUseMonthly.no")).print("</TD>\n"
                         + "    </TR>\n"
                         + "    <TR>\n"
                         + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signup.notRequired")).print("</TD>\n"
                         + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "dedicated6.billingPayOneYear.prompt")).print("</TD>\n"
                         + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, signupBillingInformationForm.getBillingPayOneYear() ? "dedicated6.billingPayOneYear.yes" : "dedicated6.billingPayOneYear.no")).print("</TD>\n"
                         + "    </TR>\n"
                         + "</TABLE>\n"
                         + "</BODY>\n"
                         + "</HTML>\n");
            emailOut.flush();

            // Send the email
            ServletContext servletContext = getServlet().getServletContext();
            Mailer.sendEmail(
                servletContext.getInitParameter("com.aoindustries.website.smtp.server"),
                "text/html",
                charset,
                servletContext.getInitParameter("com.aoindustries.website.signup.from.address"),
                servletContext.getInitParameter("com.aoindustries.website.signup.from.personal"),
                Collections.singletonList(recipient),
                signupApplicationResources.getMessage(subjectLocale, "dedicated6Completed.email.subject", Integer.toString(pkey)),
                cout.toString()
            );

            return true;
        } catch(RuntimeException err) {
            getServlet().log("Unable to send sign up details to "+recipient, err);
            return false;
        } catch(IOException err) {
            getServlet().log("Unable to send sign up details to "+recipient, err);
            return false;
        } catch(MessagingException err) {
            getServlet().log("Unable to send sign up details to "+recipient, err);
            return false;
        } catch(JspException err) {
            getServlet().log("Unable to send sign up details to "+recipient, err);
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
