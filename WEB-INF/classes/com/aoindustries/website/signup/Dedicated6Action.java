package com.aoindustries.website.signup;

/*
 * Copyright 2007 by AO Industries, Inc.,
 * 816 Azalea Rd, Mobile, Alabama, 36693, U.S.A.
 * All rights reserved.
 */
import com.aoindustries.aoserv.client.AOServConnector;
import com.aoindustries.aoserv.client.PackageDefinition;
import com.aoindustries.aoserv.client.PackageDefinitionLimit;
import com.aoindustries.sql.SQLUtility;
import com.aoindustries.website.RootAOServConnector;
import com.aoindustries.website.Skin;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

/**
 * @author  AO Industries, Inc.
 */
public class Dedicated6Action extends DedicatedStepAction {

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
        if(!signupTechnicalFormComplete) return mapping.findForward("dedicated4");
        if(!signupBillingInformationFormComplete) return mapping.findForward("dedicated5");

        initRequestAttributes(
            request,
            dedicatedSignupSelectServerForm,
            dedicatedSignupCustomizeServerForm,
            signupBusinessForm,
            signupTechnicalForm,
            signupBillingInformationForm
        );

        return mapping.findForward("input");
    }

    protected void initRequestAttributes(
        HttpServletRequest request,
        DedicatedSignupSelectServerForm dedicatedSignupSelectServerForm,
        DedicatedSignupCustomizeServerForm dedicatedSignupCustomizeServerForm,
        SignupBusinessForm signupBusinessForm,
        SignupTechnicalForm signupTechnicalForm,
        SignupBillingInformationForm signupBillingInformationForm
    ) throws IOException {
        // Lookup things needed by the view
        AOServConnector rootConn = RootAOServConnector.getRootAOServConnector(getServlet().getServletContext());
        PackageDefinition packageDefinition = rootConn.packageDefinitions.get(dedicatedSignupSelectServerForm.getPackageDefinition());
        BigDecimal monthlyRate = new BigDecimal(SQLUtility.getDecimal(packageDefinition.getMonthlyRate()));

        // Add the power option
        int powerOption = dedicatedSignupCustomizeServerForm.getPowerOption();
        if(powerOption!=-1) {
            PackageDefinitionLimit powerPDL = rootConn.packageDefinitionLimits.get(powerOption);
            int numPower = powerPDL.getHardLimit();
            int powerRate = powerPDL.getAdditionalRate();
            if(powerRate>0) monthlyRate = monthlyRate.add(new BigDecimal(SQLUtility.getDecimal(numPower * powerRate)));
        }

        // Add the cpu option
        PackageDefinitionLimit cpuPDL = rootConn.packageDefinitionLimits.get(dedicatedSignupCustomizeServerForm.getCpuOption());
        int numCpu = cpuPDL.getHardLimit();
        int cpuRate = cpuPDL.getAdditionalRate();
        if(cpuRate>0) monthlyRate = monthlyRate.add(new BigDecimal(SQLUtility.getDecimal(numCpu * cpuRate)));

        // Add the RAM option
        PackageDefinitionLimit ramPDL = rootConn.packageDefinitionLimits.get(dedicatedSignupCustomizeServerForm.getRamOption());
        int numRam = ramPDL.getHardLimit();
        int ramRate = ramPDL.getAdditionalRate();
        if(ramRate>0) monthlyRate = monthlyRate.add(new BigDecimal(SQLUtility.getDecimal(numRam * ramRate)));

        // Add the SATA controller option
        int sataControllerOption = dedicatedSignupCustomizeServerForm.getSataControllerOption();
        if(sataControllerOption!=-1) {
            PackageDefinitionLimit sataControllerPDL = rootConn.packageDefinitionLimits.get(sataControllerOption);
            int numSataController = sataControllerPDL.getHardLimit();
            int sataControllerRate = sataControllerPDL.getAdditionalRate();
            if(sataControllerRate>0) monthlyRate = monthlyRate.add(new BigDecimal(SQLUtility.getDecimal(numSataController * sataControllerRate)));
        }

        // Add the SCSI controller option
        int scsiControllerOption = dedicatedSignupCustomizeServerForm.getScsiControllerOption();
        if(scsiControllerOption!=-1) {
            PackageDefinitionLimit scsiControllerPDL = rootConn.packageDefinitionLimits.get(scsiControllerOption);
            int numScsiController = scsiControllerPDL.getHardLimit();
            int scsiControllerRate = scsiControllerPDL.getAdditionalRate();
            if(scsiControllerRate>0) monthlyRate = monthlyRate.add(new BigDecimal(SQLUtility.getDecimal(numScsiController * scsiControllerRate)));
        }

        // Add the IDE options
        for(String pkey : dedicatedSignupCustomizeServerForm.getIdeOptions()) {
            if(pkey!=null && pkey.length()>0 && !pkey.equals("-1")) {
                PackageDefinitionLimit idePDL = rootConn.packageDefinitionLimits.get(Integer.parseInt(pkey));
                if(idePDL!=null) {
                    int ideRate = idePDL.getAdditionalRate();
                    if(ideRate>0) monthlyRate = monthlyRate.add(new BigDecimal(SQLUtility.getDecimal(ideRate)));
                }
            }
        }

        // Add the SATA options
        for(String pkey : dedicatedSignupCustomizeServerForm.getSataOptions()) {
            if(pkey!=null && pkey.length()>0 && !pkey.equals("-1")) {
                PackageDefinitionLimit sataPDL = rootConn.packageDefinitionLimits.get(Integer.parseInt(pkey));
                if(sataPDL!=null) {
                    int sataRate = sataPDL.getAdditionalRate();
                    if(sataRate>0) monthlyRate = monthlyRate.add(new BigDecimal(SQLUtility.getDecimal(sataRate)));
                }
            }
        }

        // Add the SCSI options
        for(String pkey : dedicatedSignupCustomizeServerForm.getScsiOptions()) {
            if(pkey!=null && pkey.length()>0 && !pkey.equals("-1")) {
                PackageDefinitionLimit scsiPDL = rootConn.packageDefinitionLimits.get(Integer.parseInt(pkey));
                if(scsiPDL!=null) {
                    int scsiRate = scsiPDL.getAdditionalRate();
                    if(scsiRate>0) monthlyRate = monthlyRate.add(new BigDecimal(SQLUtility.getDecimal(scsiRate)));
                }
            }
        }

        // Store as request attribute for the view
        request.setAttribute("packageDefinition", packageDefinition);
        request.setAttribute("setup", getSetup(packageDefinition));
        request.setAttribute("monthlyRate", monthlyRate);
        request.setAttribute("powerOption", getPowerOption(rootConn, dedicatedSignupCustomizeServerForm));
        request.setAttribute("cpuOption", getCpuOption(rootConn, dedicatedSignupCustomizeServerForm));
        request.setAttribute("ramOption", getRamOption(rootConn, dedicatedSignupCustomizeServerForm));
        request.setAttribute("sataControllerOption", getSataControllerOption(rootConn, dedicatedSignupCustomizeServerForm));
        request.setAttribute("scsiControllerOption", getScsiControllerOption(rootConn, dedicatedSignupCustomizeServerForm));
        request.setAttribute("ideOptions", getIdeOptions(rootConn, dedicatedSignupCustomizeServerForm));
        request.setAttribute("sataOptions", getSataOptions(rootConn, dedicatedSignupCustomizeServerForm));
        request.setAttribute("scsiOptions", getScsiOptions(rootConn, dedicatedSignupCustomizeServerForm));
        request.setAttribute("businessCountry", getBusinessCountry(rootConn, signupBusinessForm));
        request.setAttribute("baCountry", getBaCountry(rootConn, signupTechnicalForm));
        request.setAttribute("billingCardNumber", getBillingCardNumber(signupBillingInformationForm));
    }

    /**
     * Only shows the first two and last four digits of a card number.
     */
    public static String hideCreditCardNumber(String number) {
        if(number==null) return "";
        number=number.trim();
        int len=number.length();
        if(len==0) return "";
        StringBuilder SB=new StringBuilder(len);
        for(int c=0;c<len;c++) {
            char ch=number.charAt(c);
            if(
                ch<'0'
                || ch>'9'
                || c<2
                || c>=(len-4)
            ) SB.append(number.charAt(c));
            else SB.append('X');
        }
        return SB.toString();
    }

    protected BigDecimal getSetup(PackageDefinition packageDefinition) {
        int setupFee = packageDefinition.getSetupFee();
        return setupFee==-1 ? null : new BigDecimal(SQLUtility.getDecimal(setupFee));
    }

    protected String getPowerOption(AOServConnector rootConn, DedicatedSignupCustomizeServerForm dedicatedSignupCustomizeServerForm) {
        int powerOption = dedicatedSignupCustomizeServerForm.getPowerOption();
        if(powerOption==-1) return null;
        PackageDefinitionLimit powerPDL = rootConn.packageDefinitionLimits.get(powerOption);
        int numPower = powerPDL.getHardLimit();
        if(numPower==1) return powerPDL.getResource().getDescription();
        else return numPower + "x" + powerPDL.getResource().getDescription();
    }

    protected String getCpuOption(AOServConnector rootConn, DedicatedSignupCustomizeServerForm dedicatedSignupCustomizeServerForm) {
        PackageDefinitionLimit cpuPDL = rootConn.packageDefinitionLimits.get(dedicatedSignupCustomizeServerForm.getCpuOption());
        int numCpu = cpuPDL.getHardLimit();
        if(numCpu==1) return cpuPDL.getResource().getDescription().replaceAll(", ", "<br>&nbsp;&nbsp;&nbsp;&nbsp;");
        else return numCpu + "x" + cpuPDL.getResource().getDescription().replaceAll(", ", "<br>&nbsp;&nbsp;&nbsp;&nbsp;");
    }
    
    protected String getRamOption(AOServConnector rootConn, DedicatedSignupCustomizeServerForm dedicatedSignupCustomizeServerForm) {
        PackageDefinitionLimit ramPDL = rootConn.packageDefinitionLimits.get(dedicatedSignupCustomizeServerForm.getRamOption());
        int numRam = ramPDL.getHardLimit();
        if(numRam==1) return ramPDL.getResource().getDescription();
        else return numRam + "x" + ramPDL.getResource().getDescription();
    }
    
    protected String getSataControllerOption(AOServConnector rootConn, DedicatedSignupCustomizeServerForm dedicatedSignupCustomizeServerForm) {
        int sataControllerOption = dedicatedSignupCustomizeServerForm.getSataControllerOption();
        if(sataControllerOption==-1) return null;
        PackageDefinitionLimit sataControllerPDL = rootConn.packageDefinitionLimits.get(sataControllerOption);
        int numSataController = sataControllerPDL.getHardLimit();
        if(numSataController==1) return sataControllerPDL.getResource().getDescription();
        else return numSataController + "x" + sataControllerPDL.getResource().getDescription();
    }

    protected String getScsiControllerOption(AOServConnector rootConn, DedicatedSignupCustomizeServerForm dedicatedSignupCustomizeServerForm) {
        int scsiControllerOption = dedicatedSignupCustomizeServerForm.getScsiControllerOption();
        if(scsiControllerOption==-1) return null;
        PackageDefinitionLimit scsiControllerPDL = rootConn.packageDefinitionLimits.get(scsiControllerOption);
        int numScsiController = scsiControllerPDL.getHardLimit();
        if(numScsiController==1) return scsiControllerPDL.getResource().getDescription();
        else return numScsiController + "x" + scsiControllerPDL.getResource().getDescription();
    }

    protected List<String> getIdeOptions(AOServConnector rootConn, DedicatedSignupCustomizeServerForm dedicatedSignupCustomizeServerForm) {
        List<String> ideOptions = new ArrayList<String>();
        for(String pkey : dedicatedSignupCustomizeServerForm.getIdeOptions()) {
            if(pkey!=null && pkey.length()>0 && !pkey.equals("-1")) {
                PackageDefinitionLimit idePDL = rootConn.packageDefinitionLimits.get(Integer.parseInt(pkey));
                if(idePDL!=null) {
                    int ideRate = idePDL.getAdditionalRate();
                    ideOptions.add(idePDL.getResource().getDescription());
                }
            }
        }
        return ideOptions;
    }

    protected List<String> getSataOptions(AOServConnector rootConn, DedicatedSignupCustomizeServerForm dedicatedSignupCustomizeServerForm) {
        List<String> sataOptions = new ArrayList<String>();
        for(String pkey : dedicatedSignupCustomizeServerForm.getSataOptions()) {
            if(pkey!=null && pkey.length()>0 && !pkey.equals("-1")) {
                PackageDefinitionLimit sataPDL = rootConn.packageDefinitionLimits.get(Integer.parseInt(pkey));
                if(sataPDL!=null) {
                    int sataRate = sataPDL.getAdditionalRate();
                    sataOptions.add(sataPDL.getResource().getDescription());
                }
            }
        }
        return sataOptions;
    }

    protected List<String> getScsiOptions(AOServConnector rootConn, DedicatedSignupCustomizeServerForm dedicatedSignupCustomizeServerForm) {
        List<String> scsiOptions = new ArrayList<String>();
        for(String pkey : dedicatedSignupCustomizeServerForm.getScsiOptions()) {
            if(pkey!=null && pkey.length()>0 && !pkey.equals("-1")) {
                PackageDefinitionLimit scsiPDL = rootConn.packageDefinitionLimits.get(Integer.parseInt(pkey));
                if(scsiPDL!=null) {
                    int scsiRate = scsiPDL.getAdditionalRate();
                    scsiOptions.add(scsiPDL.getResource().getDescription());
                }
            }
        }
        return scsiOptions;
    }
    
    protected String getBusinessCountry(AOServConnector rootConn, SignupBusinessForm signupBusinessForm) {
        return rootConn.countryCodes.get(signupBusinessForm.getBusinessCountry()).getName();
    }
    
    protected String getBaCountry(AOServConnector rootConn, SignupTechnicalForm signupTechnicalForm) {
        String baCountry = signupTechnicalForm.getBaCountry();
        return baCountry==null || baCountry.length()==0 ? "" : rootConn.countryCodes.get(baCountry).getName();
    }
    
    protected String getBillingCardNumber(SignupBillingInformationForm signupBillingInformationForm) {
        return hideCreditCardNumber(signupBillingInformationForm.getBillingCardNumber());
    }
}
