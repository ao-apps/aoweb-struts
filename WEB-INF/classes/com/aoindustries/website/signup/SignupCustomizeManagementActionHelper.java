/*
 * Copyright 2007-2010 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
 * All rights reserved.
 */
package com.aoindustries.website.signup;

import com.aoindustries.aoserv.client.AOServConnector;
import com.aoindustries.aoserv.client.PackageDefinition;
import com.aoindustries.aoserv.client.PackageDefinitionLimit;
import com.aoindustries.aoserv.client.ResourceType;
import com.aoindustries.io.ChainWriter;
import com.aoindustries.website.SiteSettings;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.validator.GenericValidator;

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
        HttpServletResponse response,
        SignupSelectPackageForm signupSelectPackageForm,
        SignupCustomizeServerForm signupCustomizeServerForm,
        SignupCustomizeManagementForm signupCustomizeManagementForm
    ) throws IOException {
        AOServConnector<?,?> rootConn = SiteSettings.getInstance(servletContext).getRootAOServConnector();
        PackageDefinition packageDefinition = rootConn.getPackageDefinitions().get(signupSelectPackageForm.getPackageDefinition());
        List<PackageDefinitionLimit> limits = packageDefinition.getLimits();

        // Get the total harddrive space in gigabytes
        int totalHardwareDiskSpace = SignupCustomizeServerActionHelper.getTotalHardwareDiskSpace(rootConn, signupCustomizeServerForm);

        // Find all the options
        List<Option> backupOnsiteOptions = new ArrayList<Option>();
        List<Option> backupOffsiteOptions = new ArrayList<Option>();
        List<Option> distributionScanOptions = new ArrayList<Option>();
        List<Option> failoverOptions = new ArrayList<Option>();
        for(PackageDefinitionLimit limit : limits) {
            ResourceType resource = limit.getResourceType();
            String resourceName = resource.getName();
            if(resourceName.startsWith("backup_onsite_")) {
                int limitPower = limit.getHardLimit();
                if(limitPower==PackageDefinitionLimit.UNLIMITED || limitPower>0) {
                    // This is per gigabyte of physical space
                    BigDecimal additionalRate = limit.getAdditionalRate();
                    if(additionalRate==null) additionalRate = BigDecimal.valueOf(0, 2);
                    backupOnsiteOptions.add(new Option(limit.getPkey(), resource.toString(), additionalRate.multiply(BigDecimal.valueOf(totalHardwareDiskSpace))));
                }
            } else if(resourceName.startsWith("backup_offsite_")) {
                int limitPower = limit.getHardLimit();
                if(limitPower==PackageDefinitionLimit.UNLIMITED || limitPower>0) {
                    // This is per gigabyte of physical space
                    BigDecimal additionalRate = limit.getAdditionalRate();
                    if(additionalRate==null) additionalRate=BigDecimal.valueOf(0, 2);
                    backupOffsiteOptions.add(new Option(limit.getPkey(), resource.toString(), additionalRate.multiply(BigDecimal.valueOf(totalHardwareDiskSpace))));
                }
            }
        }
        // Distribution scan option
        {
            ResourceType resource = rootConn.getResourceTypes().get(ResourceType.DISTRIBUTION_SCAN);
            PackageDefinitionLimit limit = packageDefinition.getLimit(resource);
            if(limit!=null) {
                int hard = limit.getHardLimit();
                if(hard==PackageDefinitionLimit.UNLIMITED || hard>0) {
                    BigDecimal additionalRate = limit.getAdditionalRate();
                    if(additionalRate==null) additionalRate=BigDecimal.valueOf(0, 2);
                    distributionScanOptions.add(new Option(limit.getPkey(), resource.toString(), additionalRate));
                }
            }
        }
        // Failover option
        {
            ResourceType resource = rootConn.getResourceTypes().get(ResourceType.FAILOVER);
            PackageDefinitionLimit limit = packageDefinition.getLimit(resource);
            if(limit!=null) {
                int hard = limit.getHardLimit();
                if(hard==PackageDefinitionLimit.UNLIMITED || hard>0) {
                    // This is per gigabyte of physical space
                    BigDecimal additionalRate = limit.getAdditionalRate();
                    if(additionalRate==null) additionalRate=BigDecimal.valueOf(0, 2);
                    additionalRate = additionalRate.multiply(BigDecimal.valueOf(totalHardwareDiskSpace));
                    failoverOptions.add(new Option(limit.getPkey(), resource.toString(), additionalRate));

                    // Only once the failover option is available will the MySQL replication option be available
                    ResourceType mrResource = rootConn.getResourceTypes().get(ResourceType.MYSQL_REPLICATION);
                    PackageDefinitionLimit mrLimit = packageDefinition.getLimit(mrResource);
                    if(mrLimit!=null) {
                        int mrHard = mrLimit.getHardLimit();
                        if(mrHard==PackageDefinitionLimit.UNLIMITED || mrHard>0) {
                            BigDecimal mrAdditionalRate = mrLimit.getAdditionalRate();
                            if(mrAdditionalRate==null) mrAdditionalRate=BigDecimal.valueOf(0, 2);
                            failoverOptions.add(new Option(mrLimit.getPkey(), mrResource.toString(), additionalRate.add(mrAdditionalRate)));
                        }
                    }
                }
            }
        }

        if(!backupOnsiteOptions.isEmpty()) backupOnsiteOptions.add(0, new Option(-1, "No On-Site Backup", BigDecimal.valueOf(0, 2)));
        if(!backupOffsiteOptions.isEmpty()) backupOffsiteOptions.add(0, new Option(-1, "No Off-Site Backup", BigDecimal.valueOf(0, 2)));
        if(!distributionScanOptions.isEmpty()) distributionScanOptions.add(0, new Option(-1, "No daily scans", BigDecimal.valueOf(0, 2)));
        if(!failoverOptions.isEmpty()) failoverOptions.add(0, new Option(-1, "No Fail-Over Mirror", BigDecimal.valueOf(0, 2)));

        // Sort by price
        Collections.sort(backupOnsiteOptions, new Option.PriceComparator());
        Collections.sort(backupOffsiteOptions, new Option.PriceComparator());
        Collections.sort(distributionScanOptions, new Option.PriceComparator());
        Collections.sort(failoverOptions, new Option.PriceComparator());

        // Clear any customization settings that are not part of the current package definition (this happens when they
        // select a different package type)
        if(signupCustomizeManagementForm.getBackupOnsiteOption()!=-1) {
            PackageDefinitionLimit pdl = rootConn.getPackageDefinitionLimits().get(signupCustomizeManagementForm.getBackupOnsiteOption());
            if(pdl==null || !packageDefinition.equals(pdl.getPackageDefinition())) signupCustomizeManagementForm.setBackupOnsiteOption(-1);
        }
        if(signupCustomizeManagementForm.getBackupOffsiteOption()!=-1) {
            PackageDefinitionLimit pdl = rootConn.getPackageDefinitionLimits().get(signupCustomizeManagementForm.getBackupOffsiteOption());
            if(pdl==null || !packageDefinition.equals(pdl.getPackageDefinition())) signupCustomizeManagementForm.setBackupOffsiteOption(-1);
        }
        if(signupCustomizeManagementForm.getDistributionScanOption()!=-1) {
            PackageDefinitionLimit pdl = rootConn.getPackageDefinitionLimits().get(signupCustomizeManagementForm.getDistributionScanOption());
            if(pdl==null || !packageDefinition.equals(pdl.getPackageDefinition())) signupCustomizeManagementForm.setDistributionScanOption(-1);
        }
        if(signupCustomizeManagementForm.getFailoverOption()!=-1) {
            PackageDefinitionLimit pdl = rootConn.getPackageDefinitionLimits().get(signupCustomizeManagementForm.getFailoverOption());
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
        HttpServletResponse response,
        SignupSelectPackageForm signupSelectPackageForm,
        SignupCustomizeServerForm signupCustomizeServerForm,
        SignupCustomizeManagementForm signupCustomizeManagementForm
    ) throws IOException {
        // Lookup things needed by the view
        AOServConnector<?,?> rootConn = SiteSettings.getInstance(servletContext).getRootAOServConnector();
        PackageDefinition packageDefinition = rootConn.getPackageDefinitions().get(signupSelectPackageForm.getPackageDefinition());

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
        AOServConnector<?,?> rootConn,
        SignupCustomizeManagementForm signupCustomizeManagementForm
    ) throws IOException {
        String backupOnsiteOption = getBackupOnsiteOption(rootConn, signupCustomizeManagementForm);
        if(!GenericValidator.isBlankOrNull(backupOnsiteOption)) {
            emailOut.print("    <tr>\n"
                         + "        <td>").print(ApplicationResources.accessor.getMessage("signup.notRequired")).print("</td>\n"
                         + "        <td>").print(ApplicationResources.accessor.getMessage("signupCustomizeManagementConfirmation.backupOnsite.prompt")).print("</td>\n"
                         + "        <td>").print(backupOnsiteOption).print("</td>\n"
                         + "    </tr>\n");
        }
        String backupOffsiteOption = getBackupOffsiteOption(rootConn, signupCustomizeManagementForm);
        if(!GenericValidator.isBlankOrNull(backupOffsiteOption)) {
            emailOut.print("    <tr>\n"
                         + "        <td>").print(ApplicationResources.accessor.getMessage("signup.notRequired")).print("</td>\n"
                         + "        <td>").print(ApplicationResources.accessor.getMessage("signupCustomizeManagementConfirmation.backupOffsite.prompt")).print("</td>\n"
                         + "        <td>").print(backupOffsiteOption).print("</td>\n"
                         + "    </tr>\n");
        }
        String backupDvdOption = getBackupDvdOption(rootConn, signupCustomizeManagementForm);
        if(!GenericValidator.isBlankOrNull(backupDvdOption)) {
            emailOut.print("    <tr>\n"
                         + "        <td>").print(ApplicationResources.accessor.getMessage("signup.notRequired")).print("</td>\n"
                         + "        <td>").print(ApplicationResources.accessor.getMessage("signupCustomizeManagementConfirmation.backupDvd.prompt")).print("</td>\n"
                         + "        <td>").print(backupDvdOption).print("</td>\n"
                         + "    </tr>\n");
        }
        String distributionScanOption = getDistributionScanOption(rootConn, signupCustomizeManagementForm);
        if(!GenericValidator.isBlankOrNull(distributionScanOption)) {
            emailOut.print("    <tr>\n"
                         + "        <td>").print(ApplicationResources.accessor.getMessage("signup.notRequired")).print("</td>\n"
                         + "        <td>").print(ApplicationResources.accessor.getMessage("signupCustomizeManagementConfirmation.distributionScan.prompt")).print("</td>\n"
                         + "        <td>").print(distributionScanOption).print("</td>\n"
                         + "    </tr>\n");
        }
        String failoverOption = getFailoverOption(rootConn, signupCustomizeManagementForm);
        if(!GenericValidator.isBlankOrNull(failoverOption)) {
            emailOut.print("    <tr>\n"
                         + "        <td>").print(ApplicationResources.accessor.getMessage("signup.notRequired")).print("</td>\n"
                         + "        <td>").print(ApplicationResources.accessor.getMessage("signupCustomizeManagementConfirmation.failover.prompt")).print("</td>\n"
                         + "        <td>").print(failoverOption).print("</td>\n"
                         + "    </tr>\n");
        }
        emailOut.print("    <tr>\n"
                     + "        <td>").print(ApplicationResources.accessor.getMessage("signup.notRequired")).print("</td>\n"
                     + "        <td>").print(ApplicationResources.accessor.getMessage("signupCustomizeManagementConfirmation.totalMonthlyRate.prompt")).print("</td>\n"
                     + "        <td>$").print(request.getAttribute("totalMonthlyRate")).print("</td>\n"
                     + "    </tr>\n");
    }

    /**
     * Gets the total monthly rate for the server, basic server + hardware options + management options
     */
    public static BigDecimal getTotalMonthlyRate(
        AOServConnector<?,?> rootConn,
        SignupCustomizeServerForm signupCustomizeServerForm,
        SignupCustomizeManagementForm signupCustomizeManagementForm,
        PackageDefinition packageDefinition
    ) throws IOException {
        BigDecimal monthlyRate = SignupCustomizeServerActionHelper.getHardwareMonthlyRate(rootConn, signupCustomizeServerForm, packageDefinition);

        int totalDiskSpace = SignupCustomizeServerActionHelper.getTotalHardwareDiskSpace(rootConn, signupCustomizeServerForm);

        // Add the backup onsite option
        int backupOnsiteOption = signupCustomizeManagementForm.getBackupOnsiteOption();
        if(backupOnsiteOption!=-1) {
            PackageDefinitionLimit pdl = rootConn.getPackageDefinitionLimits().get(backupOnsiteOption);
            BigDecimal rate = pdl.getAdditionalRate();
            if(rate!=null) monthlyRate = monthlyRate.add(rate.multiply(BigDecimal.valueOf(totalDiskSpace)));
        }

        // Add the backup offsite option
        int backupOffsiteOption = signupCustomizeManagementForm.getBackupOffsiteOption();
        if(backupOffsiteOption!=-1) {
            PackageDefinitionLimit pdl = rootConn.getPackageDefinitionLimits().get(backupOffsiteOption);
            BigDecimal rate = pdl.getAdditionalRate();
            if(rate!=null) monthlyRate = monthlyRate.add(rate.multiply(BigDecimal.valueOf(totalDiskSpace)));
        }

        // Add the distributionScanOption option
        int distributionScanOption = signupCustomizeManagementForm.getDistributionScanOption();
        if(distributionScanOption!=-1) {
            PackageDefinitionLimit pdl = rootConn.getPackageDefinitionLimits().get(distributionScanOption);
            BigDecimal rate = pdl.getAdditionalRate();
            if(rate!=null) monthlyRate = monthlyRate.add(rate);
        }

        // Add the backup offsite option
        int failoverOption = signupCustomizeManagementForm.getFailoverOption();
        if(failoverOption!=-1) {
            PackageDefinitionLimit pdl = rootConn.getPackageDefinitionLimits().get(failoverOption);
            String resourceName = pdl.getResourceType().getName();
            if(ResourceType.FAILOVER.equals(resourceName)) {
                // Failover mirror only
                BigDecimal rate = pdl.getAdditionalRate();
                if(rate!=null) monthlyRate = monthlyRate.add(rate.multiply(BigDecimal.valueOf(totalDiskSpace)));
            } else if(ResourceType.MYSQL_REPLICATION.equals(resourceName)) {
                // Failover mirror plus MySQL replication
                ResourceType failoverResource = rootConn.getResourceTypes().get(ResourceType.FAILOVER);
                PackageDefinitionLimit failoverPDL = packageDefinition.getLimit(failoverResource);
                BigDecimal additionalRate = BigDecimal.valueOf(0, 2);
                BigDecimal failoverRate = failoverPDL.getAdditionalRate();
                if(failoverRate!=null) additionalRate = failoverRate.multiply(BigDecimal.valueOf(totalDiskSpace));
                BigDecimal rate = pdl.getAdditionalRate();
                if(rate!=null) additionalRate = additionalRate.add(rate);
                if(additionalRate!=null) monthlyRate = monthlyRate.add(additionalRate);
            }
        }

        return monthlyRate;
    }

    public static String getBackupOnsiteOption(AOServConnector<?,?> rootConn, SignupCustomizeManagementForm signupCustomizeManagementForm) throws IOException {
        int option = signupCustomizeManagementForm.getBackupOnsiteOption();
        if(option==-1) return null;
        PackageDefinitionLimit pdl = rootConn.getPackageDefinitionLimits().get(option);
        return pdl.getResourceType().toString();
    }

    public static String getBackupOffsiteOption(AOServConnector<?,?> rootConn, SignupCustomizeManagementForm signupCustomizeManagementForm) throws IOException {
        int option = signupCustomizeManagementForm.getBackupOffsiteOption();
        if(option==-1) return null;
        PackageDefinitionLimit pdl = rootConn.getPackageDefinitionLimits().get(option);
        return pdl.getResourceType().toString();
    }

    public static String getBackupDvdOption(AOServConnector<?,?> rootConn, SignupCustomizeManagementForm signupCustomizeManagementForm) {
        String option = signupCustomizeManagementForm.getBackupDvdOption();
        if(option==null || option.length()==0) return null;
        return option;
    }

    public static String getDistributionScanOption(AOServConnector<?,?> rootConn, SignupCustomizeManagementForm signupCustomizeManagementForm) throws IOException {
        int option = signupCustomizeManagementForm.getDistributionScanOption();
        if(option==-1) return null;
        PackageDefinitionLimit pdl = rootConn.getPackageDefinitionLimits().get(option);
        return pdl.getResourceType().toString();
    }

    public static String getFailoverOption(AOServConnector<?,?> rootConn, SignupCustomizeManagementForm signupCustomizeManagementForm) throws IOException {
        int option = signupCustomizeManagementForm.getFailoverOption();
        if(option==-1) return null;
        PackageDefinitionLimit pdl = rootConn.getPackageDefinitionLimits().get(option);
        return pdl.getResourceType().toString();
    }
}
