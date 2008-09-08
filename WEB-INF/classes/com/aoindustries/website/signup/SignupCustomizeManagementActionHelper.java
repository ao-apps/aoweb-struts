package com.aoindustries.website.signup;

/*
 * Copyright 2007-2008 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
 * All rights reserved.
 */
import com.aoindustries.aoserv.client.AOServConnector;
import com.aoindustries.aoserv.client.PackageDefinition;
import com.aoindustries.aoserv.client.PackageDefinitionLimit;
import com.aoindustries.aoserv.client.Resource;
import com.aoindustries.io.ChainWriter;
import com.aoindustries.sql.SQLUtility;
import com.aoindustries.website.RootAOServConnector;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.util.MessageResources;

/**
 * Managed2Action and Dedicated2Action both use this to setup the request attributes.  This is implemented
 * here because inheritence is not possible and neither one is logically above the other.
 *
 * @author  AO Industries, Inc.
 */
final public class SignupCustomizeManagementActionHelper {

    /**
     * Make no instances.
     */
    private SignupCustomizeManagementActionHelper() {}

    public static void setRequestAttributes(
        ServletContext servletContext,
        HttpServletRequest request,
        SignupSelectServerForm signupSelectServerForm,
        SignupCustomizeServerForm signupCustomizeServerForm,
        SignupCustomizeManagementForm signupCustomizeManagementForm
    ) throws IOException, SQLException {
        AOServConnector rootConn = RootAOServConnector.getRootAOServConnector(servletContext);
        PackageDefinition packageDefinition = rootConn.packageDefinitions.get(signupSelectServerForm.getPackageDefinition());
        if(packageDefinition==null) throw new SQLException("Unable to find PackageDefinition: "+signupSelectServerForm.getPackageDefinition());
        List<PackageDefinitionLimit> limits = packageDefinition.getLimits();

        // Get the total harddrive space in gigabytes
        int totalHardwareDiskSpace = SignupCustomizeServerActionHelper.getTotalHardwareDiskSpace(rootConn, signupCustomizeServerForm);

        // Find all the options
        List<Option> backupOnsiteOptions = new ArrayList<Option>();
        List<Option> backupOffsiteOptions = new ArrayList<Option>();
        List<Option> distributionScanOptions = new ArrayList<Option>();
        List<Option> failoverOptions = new ArrayList<Option>();
        for(PackageDefinitionLimit limit : limits) {
            Resource resource = limit.getResource();
            String resourceName = resource.getName();
            if(resourceName.startsWith("backup_onsite_")) {
                int limitPower = limit.getHardLimit();
                if(limitPower==PackageDefinitionLimit.UNLIMITED || limitPower>0) {
                    // This is per gigabyte of physical space
                    int additionalRate = limit.getAdditionalRate();
                    if(additionalRate==-1) additionalRate=0;
                    additionalRate *= totalHardwareDiskSpace;
                    backupOnsiteOptions.add(new Option(limit.getPkey(), resource.getDescription(), new BigDecimal(SQLUtility.getDecimal(additionalRate))));
                }
            } else if(resourceName.startsWith("backup_offsite_")) {
                int limitPower = limit.getHardLimit();
                if(limitPower==PackageDefinitionLimit.UNLIMITED || limitPower>0) {
                    // This is per gigabyte of physical space
                    int additionalRate = limit.getAdditionalRate();
                    if(additionalRate==-1) additionalRate=0;
                    additionalRate *= totalHardwareDiskSpace;
                    backupOffsiteOptions.add(new Option(limit.getPkey(), resource.getDescription(), new BigDecimal(SQLUtility.getDecimal(additionalRate))));
                }
            }
        }
        // Distribution scan option
        {
            Resource resource = rootConn.resources.get(Resource.DISTRIBUTION_SCAN);
            if(resource==null) {
                servletContext.log(null, new SQLException("Unable to find Resource: "+Resource.DISTRIBUTION_SCAN));
            } else {
                PackageDefinitionLimit limit = packageDefinition.getLimit(resource);
                if(limit!=null) {
                    int hard = limit.getHardLimit();
                    if(hard==PackageDefinitionLimit.UNLIMITED || hard>0) {
                        int additionalRate = limit.getAdditionalRate();
                        if(additionalRate==-1) additionalRate=0;
                        distributionScanOptions.add(new Option(limit.getPkey(), resource.getDescription(), new BigDecimal(SQLUtility.getDecimal(additionalRate))));
                    }
                }
            }
        }
        // Failover option
        {
            Resource resource = rootConn.resources.get(Resource.FAILOVER);
            if(resource==null) {
                servletContext.log(null, new SQLException("Unable to find Resource: "+Resource.FAILOVER));
            } else {
                PackageDefinitionLimit limit = packageDefinition.getLimit(resource);
                if(limit!=null) {
                    int hard = limit.getHardLimit();
                    if(hard==PackageDefinitionLimit.UNLIMITED || hard>0) {
                        // This is per gigabyte of physical space
                        int additionalRate = limit.getAdditionalRate();
                        if(additionalRate==-1) additionalRate=0;
                        additionalRate *= totalHardwareDiskSpace;
                        failoverOptions.add(new Option(limit.getPkey(), resource.getDescription(), new BigDecimal(SQLUtility.getDecimal(additionalRate))));
                        
                        // Only once the failover option is available will the MySQL replication option be available
                        Resource mrResource = rootConn.resources.get(Resource.MYSQL_REPLICATION);
                        if(mrResource==null) {
                            servletContext.log(null, new SQLException("Unable to find Resource: "+Resource.MYSQL_REPLICATION));
                        } else {
                            PackageDefinitionLimit mrLimit = packageDefinition.getLimit(mrResource);
                            if(mrLimit!=null) {
                                int mrHard = mrLimit.getHardLimit();
                                if(mrHard==PackageDefinitionLimit.UNLIMITED || mrHard>0) {
                                    int mrAdditionalRate = mrLimit.getAdditionalRate();
                                    if(mrAdditionalRate==-1) mrAdditionalRate=0;
                                    failoverOptions.add(new Option(mrLimit.getPkey(), mrResource.getDescription(), new BigDecimal(SQLUtility.getDecimal(additionalRate+mrAdditionalRate))));
                                }
                            }
                        }
                    }
                }
            }
        }

        if(!backupOnsiteOptions.isEmpty()) backupOnsiteOptions.add(0, new Option(-1, "No On-Site Backup", new BigDecimal("0.00")));
        if(!backupOffsiteOptions.isEmpty()) backupOffsiteOptions.add(0, new Option(-1, "No Off-Site Backup", new BigDecimal("0.00")));
        if(!distributionScanOptions.isEmpty()) distributionScanOptions.add(0, new Option(-1, "No daily scans", new BigDecimal("0.00")));
        if(!failoverOptions.isEmpty()) failoverOptions.add(0, new Option(-1, "No Fail-Over Mirror", new BigDecimal("0.00")));

        // Sort by price
        Collections.sort(backupOnsiteOptions, new Option.PriceComparator());
        Collections.sort(backupOffsiteOptions, new Option.PriceComparator());
        Collections.sort(distributionScanOptions, new Option.PriceComparator());
        Collections.sort(failoverOptions, new Option.PriceComparator());

        // Clear any customization settings that are not part of the current package definition (this happens when they
        // select a different package type)
        if(signupCustomizeManagementForm.getBackupOnsiteOption()!=-1) {
            PackageDefinitionLimit pdl = rootConn.packageDefinitionLimits.get(signupCustomizeManagementForm.getBackupOnsiteOption());
            if(pdl==null || !packageDefinition.equals(pdl.getPackageDefinition())) signupCustomizeManagementForm.setBackupOnsiteOption(-1);
        }
        if(signupCustomizeManagementForm.getBackupOffsiteOption()!=-1) {
            PackageDefinitionLimit pdl = rootConn.packageDefinitionLimits.get(signupCustomizeManagementForm.getBackupOffsiteOption());
            if(pdl==null || !packageDefinition.equals(pdl.getPackageDefinition())) signupCustomizeManagementForm.setBackupOffsiteOption(-1);
        }
        if(signupCustomizeManagementForm.getDistributionScanOption()!=-1) {
            PackageDefinitionLimit pdl = rootConn.packageDefinitionLimits.get(signupCustomizeManagementForm.getDistributionScanOption());
            if(pdl==null || !packageDefinition.equals(pdl.getPackageDefinition())) signupCustomizeManagementForm.setDistributionScanOption(-1);
        }
        if(signupCustomizeManagementForm.getFailoverOption()!=-1) {
            PackageDefinitionLimit pdl = rootConn.packageDefinitionLimits.get(signupCustomizeManagementForm.getFailoverOption());
            if(pdl==null || !packageDefinition.equals(pdl.getPackageDefinition())) signupCustomizeManagementForm.setFailoverOption(-1);
        }

        // Store to request
        request.setAttribute("packageDefinition", packageDefinition);
        request.setAttribute("hardwareRate", SignupCustomizeServerActionHelper.getHardwareMonthlyRate(rootConn, signupCustomizeServerForm, packageDefinition));
        request.setAttribute("backupOnsiteOptions", backupOnsiteOptions);
        request.setAttribute("backupOffsiteOptions", backupOffsiteOptions);
        request.setAttribute("distributionScanOptions", distributionScanOptions);
        request.setAttribute("failoverOptions", failoverOptions);
    }

    public static void setConfirmationRequestAttributes(
        ServletContext servletContext,
        HttpServletRequest request,
        SignupSelectServerForm signupSelectServerForm,
        SignupCustomizeServerForm signupCustomizeServerForm,
        SignupCustomizeManagementForm signupCustomizeManagementForm
    ) throws IOException, SQLException {
        // Lookup things needed by the view
        AOServConnector rootConn = RootAOServConnector.getRootAOServConnector(servletContext);
        PackageDefinition packageDefinition = rootConn.packageDefinitions.get(signupSelectServerForm.getPackageDefinition());

        // Store as request attribute for the view
        request.setAttribute("totalMonthlyRate", getTotalMonthlyRate(rootConn, signupCustomizeServerForm, signupCustomizeManagementForm, packageDefinition));
        request.setAttribute("backupOnsiteOption", getBackupOnsiteOption(rootConn, signupCustomizeManagementForm));
        request.setAttribute("backupOffsiteOption", getBackupOffsiteOption(rootConn, signupCustomizeManagementForm));
        request.setAttribute("backupDvdOption", getBackupDvdOption(rootConn, signupCustomizeManagementForm));
        request.setAttribute("distributionScanOption", getDistributionScanOption(rootConn, signupCustomizeManagementForm));
        request.setAttribute("failoverOption", getFailoverOption(rootConn, signupCustomizeManagementForm));
    }

    public static void printConfirmation(
        HttpServletRequest request,
        ChainWriter emailOut,
        Locale contentLocale,
        AOServConnector rootConn,
        SignupCustomizeManagementForm signupCustomizeManagementForm,
        MessageResources signupApplicationResources
    ) {
        String backupOnsiteOption = getBackupOnsiteOption(rootConn, signupCustomizeManagementForm);
        if(!GenericValidator.isBlankOrNull(backupOnsiteOption)) {
            emailOut.print("    <TR>\n"
                         + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signup.notRequired")).print("</TD>\n"
                         + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signupCustomizeManagementConfirmation.backupOnsite.prompt")).print("</TD>\n"
                         + "        <TD>").print(backupOnsiteOption).print("</TD>\n"
                         + "    </TR>\n");
        }
        String backupOffsiteOption = getBackupOffsiteOption(rootConn, signupCustomizeManagementForm);
        if(!GenericValidator.isBlankOrNull(backupOffsiteOption)) {
            emailOut.print("    <TR>\n"
                         + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signup.notRequired")).print("</TD>\n"
                         + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signupCustomizeManagementConfirmation.backupOffsite.prompt")).print("</TD>\n"
                         + "        <TD>").print(backupOffsiteOption).print("</TD>\n"
                         + "    </TR>\n");
        }
        String backupDvdOption = getBackupDvdOption(rootConn, signupCustomizeManagementForm);
        if(!GenericValidator.isBlankOrNull(backupDvdOption)) {
            emailOut.print("    <TR>\n"
                         + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signup.notRequired")).print("</TD>\n"
                         + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signupCustomizeManagementConfirmation.backupDvd.prompt")).print("</TD>\n"
                         + "        <TD>").print(backupDvdOption).print("</TD>\n"
                         + "    </TR>\n");
        }
        String distributionScanOption = getDistributionScanOption(rootConn, signupCustomizeManagementForm);
        if(!GenericValidator.isBlankOrNull(distributionScanOption)) {
            emailOut.print("    <TR>\n"
                         + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signup.notRequired")).print("</TD>\n"
                         + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signupCustomizeManagementConfirmation.distributionScan.prompt")).print("</TD>\n"
                         + "        <TD>").print(distributionScanOption).print("</TD>\n"
                         + "    </TR>\n");
        }
        String failoverOption = getFailoverOption(rootConn, signupCustomizeManagementForm);
        if(!GenericValidator.isBlankOrNull(failoverOption)) {
            emailOut.print("    <TR>\n"
                         + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signup.notRequired")).print("</TD>\n"
                         + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signupCustomizeManagementConfirmation.failover.prompt")).print("</TD>\n"
                         + "        <TD>").print(failoverOption).print("</TD>\n"
                         + "    </TR>\n");
        }
        emailOut.print("    <TR>\n"
                     + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signup.notRequired")).print("</TD>\n"
                     + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signupCustomizeManagementConfirmation.totalMonthlyRate.prompt")).print("</TD>\n"
                     + "        <TD>$").print(request.getAttribute("totalMonthlyRate")).print("</TD>\n"
                     + "    </TR>\n");
    }

    /**
     * Gets the total monthly rate for the server, basic server + hardware options + management options
     */
    public static BigDecimal getTotalMonthlyRate(
        AOServConnector rootConn,
        SignupCustomizeServerForm signupCustomizeServerForm,
        SignupCustomizeManagementForm signupCustomizeManagementForm,
        PackageDefinition packageDefinition
    ) throws SQLException {
        BigDecimal monthlyRate = SignupCustomizeServerActionHelper.getHardwareMonthlyRate(rootConn, signupCustomizeServerForm, packageDefinition);

        int totalDiskSpace = SignupCustomizeServerActionHelper.getTotalHardwareDiskSpace(rootConn, signupCustomizeServerForm);

        // Add the backup onsite option
        int backupOnsiteOption = signupCustomizeManagementForm.getBackupOnsiteOption();
        if(backupOnsiteOption!=-1) {
            PackageDefinitionLimit pdl = rootConn.packageDefinitionLimits.get(backupOnsiteOption);
            int rate = pdl.getAdditionalRate();
            if(rate>0) monthlyRate = monthlyRate.add(new BigDecimal(SQLUtility.getDecimal(rate * totalDiskSpace)));
        }

        // Add the backup offsite option
        int backupOffsiteOption = signupCustomizeManagementForm.getBackupOffsiteOption();
        if(backupOffsiteOption!=-1) {
            PackageDefinitionLimit pdl = rootConn.packageDefinitionLimits.get(backupOffsiteOption);
            int rate = pdl.getAdditionalRate();
            if(rate>0) monthlyRate = monthlyRate.add(new BigDecimal(SQLUtility.getDecimal(rate * totalDiskSpace)));
        }

        // Add the distributionScanOption option
        int distributionScanOption = signupCustomizeManagementForm.getDistributionScanOption();
        if(distributionScanOption!=-1) {
            PackageDefinitionLimit pdl = rootConn.packageDefinitionLimits.get(distributionScanOption);
            int rate = pdl.getAdditionalRate();
            if(rate>0) monthlyRate = monthlyRate.add(new BigDecimal(SQLUtility.getDecimal(rate)));
        }

        // Add the backup offsite option
        int failoverOption = signupCustomizeManagementForm.getFailoverOption();
        if(failoverOption!=-1) {
            PackageDefinitionLimit pdl = rootConn.packageDefinitionLimits.get(failoverOption);
            String resourceName = pdl.getResource().getName();
            if(Resource.FAILOVER.equals(resourceName)) {
                // Failover mirror only
                int rate = pdl.getAdditionalRate();
                if(rate>0) monthlyRate = monthlyRate.add(new BigDecimal(SQLUtility.getDecimal(rate * totalDiskSpace)));
            } else if(Resource.MYSQL_REPLICATION.equals(resourceName)) {
                // Failover mirror plus MySQL replication
                Resource failoverResource = rootConn.resources.get(Resource.FAILOVER);
                if(failoverResource==null) throw new SQLException("Unable to find Resource: "+Resource.FAILOVER);
                PackageDefinitionLimit failoverPDL = packageDefinition.getLimit(failoverResource);
                if(failoverPDL==null) throw new SQLException("Unable to find PackageDefinitionLimit: "+Resource.FAILOVER+" on PackageDefinition #"+packageDefinition.getPkey());
                int additionalRate = 0;
                int failoverRate = failoverPDL.getAdditionalRate();
                if(failoverRate>0) additionalRate = failoverRate * totalDiskSpace;
                int rate = pdl.getAdditionalRate();
                if(rate>0) additionalRate += rate;
                if(additionalRate>0) monthlyRate = monthlyRate.add(new BigDecimal(SQLUtility.getDecimal(additionalRate)));
            }
        }

        return monthlyRate;
    }

    public static String getBackupOnsiteOption(AOServConnector rootConn, SignupCustomizeManagementForm signupCustomizeManagementForm) {
        int option = signupCustomizeManagementForm.getBackupOnsiteOption();
        if(option==-1) return null;
        PackageDefinitionLimit pdl = rootConn.packageDefinitionLimits.get(option);
        return pdl.getResource().getDescription();
    }

    public static String getBackupOffsiteOption(AOServConnector rootConn, SignupCustomizeManagementForm signupCustomizeManagementForm) {
        int option = signupCustomizeManagementForm.getBackupOffsiteOption();
        if(option==-1) return null;
        PackageDefinitionLimit pdl = rootConn.packageDefinitionLimits.get(option);
        return pdl.getResource().getDescription();
    }

    public static String getBackupDvdOption(AOServConnector rootConn, SignupCustomizeManagementForm signupCustomizeManagementForm) {
        String option = signupCustomizeManagementForm.getBackupDvdOption();
        if(option==null || option.length()==0) return null;
        return option;
    }

    public static String getDistributionScanOption(AOServConnector rootConn, SignupCustomizeManagementForm signupCustomizeManagementForm) {
        int option = signupCustomizeManagementForm.getDistributionScanOption();
        if(option==-1) return null;
        PackageDefinitionLimit pdl = rootConn.packageDefinitionLimits.get(option);
        return pdl.getResource().getDescription();
    }

    public static String getFailoverOption(AOServConnector rootConn, SignupCustomizeManagementForm signupCustomizeManagementForm) {
        int option = signupCustomizeManagementForm.getFailoverOption();
        if(option==-1) return null;
        PackageDefinitionLimit pdl = rootConn.packageDefinitionLimits.get(option);
        return pdl.getResource().getDescription();
    }
}
