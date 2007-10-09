package com.aoindustries.website.signup;

/*
 * Copyright 2007 by AO Industries, Inc.,
 * 816 Azalea Rd, Mobile, Alabama, 36693, U.S.A.
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
import java.util.Comparator;
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
final public class SignupCustomizeServerActionHelper {

    /**
     * Make no instances.
     */
    private SignupCustomizeServerActionHelper() {}

    public static void setRequestAttributes(
        ServletContext servletContext,
        HttpServletRequest request,
        SignupSelectServerForm signupSelectServerForm,
        SignupCustomizeServerForm signupCustomizeServerForm,
        boolean includeNoHardDriveOption
    ) throws IOException, SQLException {
        AOServConnector rootConn = RootAOServConnector.getRootAOServConnector(servletContext);
        PackageDefinition packageDefinition = rootConn.packageDefinitions.get(signupSelectServerForm.getPackageDefinition());
        if(packageDefinition==null) throw new SQLException("Unable to find PackageDefinition: "+signupSelectServerForm.getPackageDefinition());
        List<PackageDefinitionLimit> limits = packageDefinition.getLimits();

        // Find the cheapest resources to scale prices from
        int maxPowers = 0;
        PackageDefinitionLimit cheapestPower = null;
        int maxCPUs = 0;
        PackageDefinitionLimit cheapestCPU = null;
        int maxRAMs = 0;
        PackageDefinitionLimit cheapestRAM = null;
        int maxSataControllers = 0;
        PackageDefinitionLimit cheapestSataController = null;
        int maxScsiControllers = 0;
        PackageDefinitionLimit cheapestScsiController = null;
        int maxIDEs = 0;
        int maxSATAs = 0;
        int maxSCSIs = 0;
        PackageDefinitionLimit cheapestDisk = null;
        for(PackageDefinitionLimit limit : limits) {
            String resourceName = limit.getResource().getName();
            if(resourceName.startsWith("hardware_power_")) {
                int limitPower = limit.getHardLimit();
                if(limitPower>0) {
                    if(limitPower>maxPowers) maxPowers = limitPower;
                    if(cheapestPower==null) cheapestPower = limit;
                    else {
                        int additionalRate = limit.getAdditionalRate();
                        if(additionalRate==-1) additionalRate=0;
                        int cheapestRate = cheapestPower.getAdditionalRate();
                        if(cheapestRate==-1) cheapestRate=0;
                        if(additionalRate<cheapestRate) cheapestPower = limit;
                    }
                }
            } else if(resourceName.startsWith("hardware_processor_")) {
                int limitCpu = limit.getHardLimit();
                if(limitCpu>0) {
                    if(limitCpu>maxCPUs) maxCPUs = limitCpu;
                    if(cheapestCPU==null) cheapestCPU = limit;
                    else {
                        int additionalRate = limit.getAdditionalRate();
                        if(additionalRate==-1) additionalRate=0;
                        int cheapestRate = cheapestCPU.getAdditionalRate();
                        if(cheapestRate==-1) cheapestRate=0;
                        if(additionalRate<cheapestRate) cheapestCPU = limit;
                    }
                }
            } else if(resourceName.startsWith("hardware_ram_")) {
                int limitRAM = limit.getHardLimit();
                if(limitRAM>0) {
                    if(limitRAM>maxRAMs) maxRAMs = limitRAM;
                    if(cheapestRAM==null) cheapestRAM = limit;
                    else {
                        int additionalRate = limit.getAdditionalRate();
                        if(additionalRate==-1) additionalRate=0;
                        int cheapestRate = cheapestRAM.getAdditionalRate();
                        if(cheapestRate==-1) cheapestRate=0;
                        if(additionalRate<cheapestRate) cheapestRAM = limit;
                    }
                }
            } else if(resourceName.startsWith("hardware_disk_controller_sata_")) {
                int limitSataController = limit.getHardLimit();
                if(limitSataController>0) {
                    if(limitSataController>maxSataControllers) maxSataControllers = limitSataController;
                    if(cheapestSataController==null) cheapestSataController = limit;
                    else {
                        int additionalRate = limit.getAdditionalRate();
                        if(additionalRate==-1) additionalRate=0;
                        int cheapestRate = cheapestSataController.getAdditionalRate();
                        if(cheapestRate==-1) cheapestRate=0;
                        if(additionalRate<cheapestRate) cheapestSataController = limit;
                    }
                }
            } else if(resourceName.startsWith("hardware_disk_controller_scsi_")) {
                int limitScsiController = limit.getHardLimit();
                if(limitScsiController>0) {
                    if(limitScsiController>maxScsiControllers) maxScsiControllers = limitScsiController;
                    if(cheapestScsiController==null) cheapestScsiController = limit;
                    else {
                        int additionalRate = limit.getAdditionalRate();
                        if(additionalRate==-1) additionalRate=0;
                        int cheapestRate = cheapestScsiController.getAdditionalRate();
                        if(cheapestRate==-1) cheapestRate=0;
                        if(additionalRate<cheapestRate) cheapestScsiController = limit;
                    }
                }
            } else if(resourceName.startsWith("hardware_disk_")) {
                int hardLimit = limit.getHardLimit();
                if(hardLimit>0) {
                    if(cheapestDisk==null) cheapestDisk = limit;
                    else {
                        int additionalRate = limit.getAdditionalRate();
                        if(additionalRate==-1) additionalRate=0;
                        int cheapestRate = cheapestDisk.getAdditionalRate();
                        if(cheapestRate==-1) cheapestRate=0;
                        if(additionalRate<cheapestRate) cheapestDisk = limit;
                    }
                    if(resourceName.startsWith("hardware_disk_ide_")) {
                        if(hardLimit>maxIDEs) maxIDEs = hardLimit;
                    } else if(resourceName.startsWith("hardware_disk_sata_")) {
                        if(hardLimit>maxSATAs) maxSATAs = hardLimit;
                    } else if(resourceName.startsWith("hardware_disk_scsi_")) {
                        if(hardLimit>maxSCSIs) maxSCSIs = hardLimit;
                    }
                } else throw new SQLException("Unexpected type of disk: "+resourceName);
            }
        }
        if(cheapestCPU==null) throw new SQLException("Unable to find cheapestCPU");
        if(cheapestRAM==null) throw new SQLException("Unable to find cheapestRAM");
        if(cheapestDisk==null) throw new SQLException("Unable to find cheapestDisk");

        // Find all the options
        List<Option> powerOptions = new ArrayList<Option>();
        List<Option> cpuOptions = new ArrayList<Option>();
        List<Option> ramOptions = new ArrayList<Option>();
        List<Option> sataControllerOptions = new ArrayList<Option>();
        List<Option> scsiControllerOptions = new ArrayList<Option>();
        List<List<Option>> ideOptions = new ArrayList<List<Option>>();
        for(int c=0;c<maxIDEs;c++) ideOptions.add(new ArrayList<Option>());
        List<List<Option>> sataOptions = new ArrayList<List<Option>>();
        for(int c=0;c<maxSATAs;c++) sataOptions.add(new ArrayList<Option>());
        List<List<Option>> scsiOptions = new ArrayList<List<Option>>();
        for(int c=0;c<maxSCSIs;c++) scsiOptions.add(new ArrayList<Option>());
        for(PackageDefinitionLimit limit : limits) {
            Resource resource = limit.getResource();
            String resourceName = resource.getName();
            if(resourceName.startsWith("hardware_power_")) {
                int limitPower = limit.getHardLimit();
                if(limitPower>0) {
                    int additionalRate = limit.getAdditionalRate();
                    if(additionalRate==-1) additionalRate=0;
                    int cheapestRate = cheapestPower.getAdditionalRate();
                    if(cheapestRate==-1) cheapestRate=0;
                    String description = maxPowers==1 ? resource.getDescription() : (maxPowers+"x"+resource.getDescription());
                    powerOptions.add(new Option(limit.getPkey(), description, new BigDecimal(SQLUtility.getDecimal(maxPowers * (additionalRate-cheapestRate)))));
                }
            } else if(resourceName.startsWith("hardware_processor_")) {
                int limitCpu = limit.getHardLimit();
                if(limitCpu>0) {
                    int additionalRate = limit.getAdditionalRate();
                    if(additionalRate==-1) additionalRate=0;
                    int cheapestRate = cheapestCPU.getAdditionalRate();
                    if(cheapestRate==-1) cheapestRate=0;
                    String description = maxCPUs==1 ? resource.getDescription() : (maxCPUs+"x"+resource.getDescription());
                    cpuOptions.add(new Option(limit.getPkey(), description, new BigDecimal(SQLUtility.getDecimal(maxCPUs * (additionalRate-cheapestRate)))));
                }
            } else if(resourceName.startsWith("hardware_ram_")) {
                int limitRAM = limit.getHardLimit();
                if(limitRAM>0) {
                    int additionalRate = limit.getAdditionalRate();
                    if(additionalRate==-1) additionalRate=0;
                    int cheapestRate = cheapestRAM.getAdditionalRate();
                    if(cheapestRate==-1) cheapestRate=0;
                    String description = maxRAMs==1 ? resource.getDescription() : (maxRAMs+"x"+resource.getDescription());
                    ramOptions.add(new Option(limit.getPkey(), description, new BigDecimal(SQLUtility.getDecimal(maxRAMs * (additionalRate-cheapestRate)))));
                }
            } else if(resourceName.startsWith("hardware_disk_controller_sata_")) {
                int limitSataController = limit.getHardLimit();
                if(limitSataController>0) {
                    int additionalRate = limit.getAdditionalRate();
                    if(additionalRate==-1) additionalRate=0;
                    int cheapestRate = cheapestSataController.getAdditionalRate();
                    if(cheapestRate==-1) cheapestRate=0;
                    String description = maxSataControllers==1 ? resource.getDescription() : (maxSataControllers+"x"+resource.getDescription());
                    sataControllerOptions.add(new Option(limit.getPkey(), description, new BigDecimal(SQLUtility.getDecimal(maxSataControllers * (additionalRate-cheapestRate)))));
                }
            } else if(resourceName.startsWith("hardware_disk_controller_scsi_")) {
                int limitScsiController = limit.getHardLimit();
                if(limitScsiController>0) {
                    int additionalRate = limit.getAdditionalRate();
                    if(additionalRate==-1) additionalRate=0;
                    int cheapestRate = cheapestScsiController.getAdditionalRate();
                    if(cheapestRate==-1) cheapestRate=0;
                    String description = maxScsiControllers==1 ? resource.getDescription() : (maxScsiControllers+"x"+resource.getDescription());
                    scsiControllerOptions.add(new Option(limit.getPkey(), description, new BigDecimal(SQLUtility.getDecimal(maxScsiControllers * (additionalRate-cheapestRate)))));
                }
            } else if(resourceName.startsWith("hardware_disk_ide_")) {
                int limitDisk = limit.getHardLimit();
                if(limitDisk>0) {
                    int additionalRate = limit.getAdditionalRate();
                    if(additionalRate==-1) additionalRate=0;
                    int adjustedRate = additionalRate;
                    // Discount adjusted rate if the cheapest disk is of this type
                    if(cheapestDisk.getResource().getName().startsWith("hardware_disk_ide_")) {
                        int cheapestRate = cheapestDisk.getAdditionalRate();
                        if(cheapestRate==-1) cheapestRate=0;
                        adjustedRate -= cheapestRate;
                    }
                    for(int c=0;c<maxIDEs;c++) {
                        List<Option> options = ideOptions.get(c);
                        // Add none opption
                        if(includeNoHardDriveOption && options.isEmpty()) options.add(new Option(-1, "None", new BigDecimal(SQLUtility.getDecimal(c==0 ? (adjustedRate-additionalRate) : 0))));
                        options.add(new Option(limit.getPkey(), resource.getDescription(), new BigDecimal(SQLUtility.getDecimal(c==0 ? adjustedRate : additionalRate))));
                    }
                }
            } else if(resourceName.startsWith("hardware_disk_sata_")) {
                int limitDisk = limit.getHardLimit();
                if(limitDisk>0) {
                    int additionalRate = limit.getAdditionalRate();
                    if(additionalRate==-1) additionalRate=0;
                    int adjustedRate = additionalRate;
                    // Discount first rate if the cheapest disk is of this type
                    if(cheapestDisk.getResource().getName().startsWith("hardware_disk_sata_")) {
                        int cheapestRate = cheapestDisk.getAdditionalRate();
                        if(cheapestRate==-1) cheapestRate=0;
                        adjustedRate -= cheapestRate;
                    }
                    for(int c=0;c<maxSATAs;c++) {
                        List<Option> options = sataOptions.get(c);
                        // Add none opption
                        if(includeNoHardDriveOption && options.isEmpty()) options.add(new Option(-1, "None", new BigDecimal(SQLUtility.getDecimal(c==0 ? (adjustedRate-additionalRate) : 0))));
                        options.add(new Option(limit.getPkey(), resource.getDescription(), new BigDecimal(SQLUtility.getDecimal(c==0 ? adjustedRate : additionalRate))));
                    }
                }
            } else if(resourceName.startsWith("hardware_disk_scsi_")) {
                int limitDisk = limit.getHardLimit();
                if(limitDisk>0) {
                    int additionalRate = limit.getAdditionalRate();
                    if(additionalRate==-1) additionalRate=0;
                    int adjustedRate = additionalRate;
                    // Discount first rate if the cheapest disk is of this type
                    if(cheapestDisk.getResource().getName().startsWith("hardware_disk_scsi_")) {
                        int cheapestRate = cheapestDisk.getAdditionalRate();
                        if(cheapestRate==-1) cheapestRate=0;
                        adjustedRate -= cheapestRate;
                    }
                    for(int c=0;c<maxSCSIs;c++) {
                        List<Option> options = scsiOptions.get(c);
                        // Add none opption
                        if(includeNoHardDriveOption && options.isEmpty()) options.add(new Option(-1, "None", new BigDecimal(SQLUtility.getDecimal(c==0 ? (adjustedRate-additionalRate) : 0))));
                        options.add(new Option(limit.getPkey(), resource.getDescription(), new BigDecimal(SQLUtility.getDecimal(c==0 ? adjustedRate : additionalRate))));
                    }
                }
            }
        }

        // Sort by price
        Collections.sort(powerOptions, new Option.PriceComparator());
        Collections.sort(cpuOptions, new Option.PriceComparator());
        Collections.sort(ramOptions, new Option.PriceComparator());
        Collections.sort(sataControllerOptions, new Option.PriceComparator());
        Collections.sort(scsiControllerOptions, new Option.PriceComparator());
        for(List<Option> ideOptionList : ideOptions) Collections.sort(ideOptionList, new Option.PriceComparator());
        for(List<Option> sataOptionList : sataOptions) Collections.sort(sataOptionList, new Option.PriceComparator());
        for(List<Option> scsiOptionList : scsiOptions) Collections.sort(scsiOptionList, new Option.PriceComparator());

        // Clear any customization settings that are not part of the current package definition (this happens when they
        // select a different package type)
        if(signupCustomizeServerForm.getPowerOption()!=-1) {
            PackageDefinitionLimit pdl = rootConn.packageDefinitionLimits.get(signupCustomizeServerForm.getPowerOption());
            if(pdl==null || !packageDefinition.equals(pdl.getPackageDefinition())) signupCustomizeServerForm.setPowerOption(-1);
        }
        if(signupCustomizeServerForm.getCpuOption()!=-1) {
            PackageDefinitionLimit pdl = rootConn.packageDefinitionLimits.get(signupCustomizeServerForm.getCpuOption());
            if(pdl==null || !packageDefinition.equals(pdl.getPackageDefinition())) signupCustomizeServerForm.setCpuOption(-1);
        }
        if(signupCustomizeServerForm.getRamOption()!=-1) {
            PackageDefinitionLimit pdl = rootConn.packageDefinitionLimits.get(signupCustomizeServerForm.getRamOption());
            if(pdl==null || !packageDefinition.equals(pdl.getPackageDefinition())) signupCustomizeServerForm.setRamOption(-1);
        }
        if(signupCustomizeServerForm.getSataControllerOption()!=-1) {
            PackageDefinitionLimit pdl = rootConn.packageDefinitionLimits.get(signupCustomizeServerForm.getSataControllerOption());
            if(pdl==null || !packageDefinition.equals(pdl.getPackageDefinition())) signupCustomizeServerForm.setSataControllerOption(-1);
        }
        if(signupCustomizeServerForm.getScsiControllerOption()!=-1) {
            PackageDefinitionLimit pdl = rootConn.packageDefinitionLimits.get(signupCustomizeServerForm.getScsiControllerOption());
            if(pdl==null || !packageDefinition.equals(pdl.getPackageDefinition())) signupCustomizeServerForm.setScsiControllerOption(-1);
        }
        List<String> formIdeOptions = signupCustomizeServerForm.getIdeOptions();
        while(formIdeOptions.size()>maxIDEs) formIdeOptions.remove(formIdeOptions.size()-1);
        for(int c=0;c<formIdeOptions.size();c++) {
            String S = formIdeOptions.get(c);
            if(S!=null && S.length()>0 && !S.equals("-1")) {
                int pkey = Integer.parseInt(S);
                PackageDefinitionLimit pdl = rootConn.packageDefinitionLimits.get(pkey);
                if(pdl==null || !packageDefinition.equals(pdl.getPackageDefinition())) formIdeOptions.set(c, "-1");
            }
        }
        List<String> formSataOptions = signupCustomizeServerForm.getSataOptions();
        while(formSataOptions.size()>maxSATAs) formSataOptions.remove(formSataOptions.size()-1);
        for(int c=0;c<formSataOptions.size();c++) {
            String S = formSataOptions.get(c);
            if(S!=null && S.length()>0 && !S.equals("-1")) {
                int pkey = Integer.parseInt(S);
                PackageDefinitionLimit pdl = rootConn.packageDefinitionLimits.get(pkey);
                if(pdl==null || !packageDefinition.equals(pdl.getPackageDefinition())) formSataOptions.set(c, "-1");
            }
        }
        List<String> formScsiOptions = signupCustomizeServerForm.getScsiOptions();
        while(formSataOptions.size()>maxSATAs) formSataOptions.remove(formSataOptions.size()-1);
        for(int c=0;c<formScsiOptions.size();c++) {
            String S = formScsiOptions.get(c);
            if(S!=null && S.length()>0 && !S.equals("-1")) {
                int pkey = Integer.parseInt(S);
                PackageDefinitionLimit pdl = rootConn.packageDefinitionLimits.get(pkey);
                if(pdl==null || !packageDefinition.equals(pdl.getPackageDefinition())) formScsiOptions.set(c, "-1");
            }
        }

        // Determine if at least one disk is selected
        boolean isAtLeastOneDiskSelected = signupCustomizeServerForm.isAtLeastOneDiskSelected();

        // Default to cheapest if not already selected
        if(cheapestPower!=null && signupCustomizeServerForm.getPowerOption()==-1) signupCustomizeServerForm.setPowerOption(cheapestPower.getPkey());
        if(signupCustomizeServerForm.getCpuOption()==-1) signupCustomizeServerForm.setCpuOption(cheapestCPU.getPkey());
        if(signupCustomizeServerForm.getRamOption()==-1) signupCustomizeServerForm.setRamOption(cheapestRAM.getPkey());
        if(cheapestSataController!=null && signupCustomizeServerForm.getSataControllerOption()==-1) signupCustomizeServerForm.setSataControllerOption(cheapestSataController.getPkey());
        if(cheapestScsiController!=null && signupCustomizeServerForm.getScsiControllerOption()==-1) signupCustomizeServerForm.setScsiControllerOption(cheapestScsiController.getPkey());
        for(int c=0;c<maxIDEs;c++) {
            List<Option> options = ideOptions.get(c);
            if(!options.isEmpty()) {
                Option firstOption = options.get(0);
                if(!isAtLeastOneDiskSelected && options.size()>=2 && firstOption.getPriceDifference().compareTo(BigDecimal.ZERO)<0) {
                    firstOption = options.get(1);
                }
                String defaultSelected = Integer.toString(firstOption.getPackageDefinitionLimit());
                if(formIdeOptions.size()<=c || formIdeOptions.get(c)==null || formIdeOptions.get(c).length()==0 || formIdeOptions.get(c).equals("-1")) formIdeOptions.set(c, defaultSelected);
            } else {
                formIdeOptions.set(c, "-1");
            }
        }
        for(int c=0;c<maxSATAs;c++) {
            List<Option> options = sataOptions.get(c);
            if(!options.isEmpty()) {
                Option firstOption = options.get(0);
                if(!isAtLeastOneDiskSelected && options.size()>=2 && firstOption.getPriceDifference().compareTo(BigDecimal.ZERO)<0) {
                    firstOption = options.get(1);
                }
                String defaultSelected = Integer.toString(firstOption.getPackageDefinitionLimit());
                if(formSataOptions.size()<=c || formSataOptions.get(c)==null || formSataOptions.get(c).length()==0 || formSataOptions.get(c).equals("-1")) formSataOptions.set(c, defaultSelected);
            } else {
                formSataOptions.set(c, "-1");
            }
        }
        for(int c=0;c<maxSCSIs;c++) {
            List<Option> options = scsiOptions.get(c);
            if(!options.isEmpty()) {
                Option firstOption = options.get(0);
                if(!isAtLeastOneDiskSelected && options.size()>=2 && firstOption.getPriceDifference().compareTo(BigDecimal.ZERO)<0) {
                    firstOption = options.get(1);
                }
                String defaultSelected = Integer.toString(firstOption.getPackageDefinitionLimit());
                if(formScsiOptions.size()<=c || formScsiOptions.get(c)==null || formScsiOptions.get(c).length()==0 || formScsiOptions.get(c).equals("-1")) formScsiOptions.set(c, defaultSelected);
            } else {
                formScsiOptions.set(c, "-1");
            }
        }

        // Find the basePrice (base plus minimum number of cheapest of each resource class)
        int basePrice = packageDefinition.getMonthlyRate();
        if(basePrice==-1) basePrice = 0;
        if(cheapestPower!=null) basePrice += cheapestPower.getAdditionalRate()==-1 ? 0 : (cheapestPower.getAdditionalRate() * maxPowers);
        basePrice += cheapestCPU.getAdditionalRate()==-1 ? 0 : (cheapestCPU.getAdditionalRate() * maxCPUs);
        basePrice += cheapestRAM.getAdditionalRate()==-1 ? 0 : cheapestRAM.getAdditionalRate();
        if(cheapestSataController!=null) basePrice += cheapestSataController.getAdditionalRate()==-1 ? 0 : cheapestSataController.getAdditionalRate();
        if(cheapestScsiController!=null) basePrice += cheapestScsiController.getAdditionalRate()==-1 ? 0 : cheapestScsiController.getAdditionalRate();
        basePrice += cheapestDisk.getAdditionalRate()==-1 ? 0 : cheapestDisk.getAdditionalRate();

        // Store to request
        request.setAttribute("packageDefinition", packageDefinition);
        request.setAttribute("powerOptions", powerOptions);
        request.setAttribute("cpuOptions", cpuOptions);
        request.setAttribute("ramOptions", ramOptions);
        request.setAttribute("sataControllerOptions", sataControllerOptions);
        request.setAttribute("scsiControllerOptions", scsiControllerOptions);
        request.setAttribute("ideOptions", ideOptions);
        request.setAttribute("sataOptions", sataOptions);
        request.setAttribute("scsiOptions", scsiOptions);
        request.setAttribute("basePrice", new BigDecimal(SQLUtility.getDecimal(basePrice)));
    }

    /**
     * Gets the hardware monthly rate for the server, basic server + hardware options
     */
    public static BigDecimal getHardwareMonthlyRate(AOServConnector rootConn, SignupCustomizeServerForm signupCustomizeServerForm, PackageDefinition packageDefinition) {
        BigDecimal monthlyRate = new BigDecimal(SQLUtility.getDecimal(packageDefinition.getMonthlyRate()));

        // Add the power option
        int powerOption = signupCustomizeServerForm.getPowerOption();
        if(powerOption!=-1) {
            PackageDefinitionLimit powerPDL = rootConn.packageDefinitionLimits.get(powerOption);
            int numPower = powerPDL.getHardLimit();
            int powerRate = powerPDL.getAdditionalRate();
            if(powerRate>0) monthlyRate = monthlyRate.add(new BigDecimal(SQLUtility.getDecimal(numPower * powerRate)));
        }

        // Add the cpu option
        PackageDefinitionLimit cpuPDL = rootConn.packageDefinitionLimits.get(signupCustomizeServerForm.getCpuOption());
        int numCpu = cpuPDL.getHardLimit();
        int cpuRate = cpuPDL.getAdditionalRate();
        if(cpuRate>0) monthlyRate = monthlyRate.add(new BigDecimal(SQLUtility.getDecimal(numCpu * cpuRate)));

        // Add the RAM option
        PackageDefinitionLimit ramPDL = rootConn.packageDefinitionLimits.get(signupCustomizeServerForm.getRamOption());
        int numRam = ramPDL.getHardLimit();
        int ramRate = ramPDL.getAdditionalRate();
        if(ramRate>0) monthlyRate = monthlyRate.add(new BigDecimal(SQLUtility.getDecimal(numRam * ramRate)));

        // Add the SATA controller option
        int sataControllerOption = signupCustomizeServerForm.getSataControllerOption();
        if(sataControllerOption!=-1) {
            PackageDefinitionLimit sataControllerPDL = rootConn.packageDefinitionLimits.get(sataControllerOption);
            int numSataController = sataControllerPDL.getHardLimit();
            int sataControllerRate = sataControllerPDL.getAdditionalRate();
            if(sataControllerRate>0) monthlyRate = monthlyRate.add(new BigDecimal(SQLUtility.getDecimal(numSataController * sataControllerRate)));
        }

        // Add the SCSI controller option
        int scsiControllerOption = signupCustomizeServerForm.getScsiControllerOption();
        if(scsiControllerOption!=-1) {
            PackageDefinitionLimit scsiControllerPDL = rootConn.packageDefinitionLimits.get(scsiControllerOption);
            int numScsiController = scsiControllerPDL.getHardLimit();
            int scsiControllerRate = scsiControllerPDL.getAdditionalRate();
            if(scsiControllerRate>0) monthlyRate = monthlyRate.add(new BigDecimal(SQLUtility.getDecimal(numScsiController * scsiControllerRate)));
        }

        // Add the IDE options
        for(String pkey : signupCustomizeServerForm.getIdeOptions()) {
            if(pkey!=null && pkey.length()>0 && !pkey.equals("-1")) {
                PackageDefinitionLimit idePDL = rootConn.packageDefinitionLimits.get(Integer.parseInt(pkey));
                if(idePDL!=null) {
                    int ideRate = idePDL.getAdditionalRate();
                    if(ideRate>0) monthlyRate = monthlyRate.add(new BigDecimal(SQLUtility.getDecimal(ideRate)));
                }
            }
        }

        // Add the SATA options
        for(String pkey : signupCustomizeServerForm.getSataOptions()) {
            if(pkey!=null && pkey.length()>0 && !pkey.equals("-1")) {
                PackageDefinitionLimit sataPDL = rootConn.packageDefinitionLimits.get(Integer.parseInt(pkey));
                if(sataPDL!=null) {
                    int sataRate = sataPDL.getAdditionalRate();
                    if(sataRate>0) monthlyRate = monthlyRate.add(new BigDecimal(SQLUtility.getDecimal(sataRate)));
                }
            }
        }

        // Add the SCSI options
        for(String pkey : signupCustomizeServerForm.getScsiOptions()) {
            if(pkey!=null && pkey.length()>0 && !pkey.equals("-1")) {
                PackageDefinitionLimit scsiPDL = rootConn.packageDefinitionLimits.get(Integer.parseInt(pkey));
                if(scsiPDL!=null) {
                    int scsiRate = scsiPDL.getAdditionalRate();
                    if(scsiRate>0) monthlyRate = monthlyRate.add(new BigDecimal(SQLUtility.getDecimal(scsiRate)));
                }
            }
        }
        return monthlyRate;
    }

    public static String getPowerOption(AOServConnector rootConn, SignupCustomizeServerForm signupCustomizeServerForm) {
        int powerOption = signupCustomizeServerForm.getPowerOption();
        if(powerOption==-1) return null;
        PackageDefinitionLimit powerPDL = rootConn.packageDefinitionLimits.get(powerOption);
        int numPower = powerPDL.getHardLimit();
        if(numPower==1) return powerPDL.getResource().getDescription();
        else return numPower + "x" + powerPDL.getResource().getDescription();
    }

    public static String getCpuOption(AOServConnector rootConn, SignupCustomizeServerForm signupCustomizeServerForm) {
        PackageDefinitionLimit cpuPDL = rootConn.packageDefinitionLimits.get(signupCustomizeServerForm.getCpuOption());
        int numCpu = cpuPDL.getHardLimit();
        if(numCpu==1) return cpuPDL.getResource().getDescription().replaceAll(", ", "<br>&nbsp;&nbsp;&nbsp;&nbsp;");
        else return numCpu + "x" + cpuPDL.getResource().getDescription().replaceAll(", ", "<br>&nbsp;&nbsp;&nbsp;&nbsp;");
    }
    
    public static String getRamOption(AOServConnector rootConn, SignupCustomizeServerForm signupCustomizeServerForm) {
        PackageDefinitionLimit ramPDL = rootConn.packageDefinitionLimits.get(signupCustomizeServerForm.getRamOption());
        int numRam = ramPDL.getHardLimit();
        if(numRam==1) return ramPDL.getResource().getDescription();
        else return numRam + "x" + ramPDL.getResource().getDescription();
    }
    
    public static String getSataControllerOption(AOServConnector rootConn, SignupCustomizeServerForm signupCustomizeServerForm) {
        int sataControllerOption = signupCustomizeServerForm.getSataControllerOption();
        if(sataControllerOption==-1) return null;
        PackageDefinitionLimit sataControllerPDL = rootConn.packageDefinitionLimits.get(sataControllerOption);
        int numSataController = sataControllerPDL.getHardLimit();
        if(numSataController==1) return sataControllerPDL.getResource().getDescription();
        else return numSataController + "x" + sataControllerPDL.getResource().getDescription();
    }

    public static String getScsiControllerOption(AOServConnector rootConn, SignupCustomizeServerForm signupCustomizeServerForm) {
        int scsiControllerOption = signupCustomizeServerForm.getScsiControllerOption();
        if(scsiControllerOption==-1) return null;
        PackageDefinitionLimit scsiControllerPDL = rootConn.packageDefinitionLimits.get(scsiControllerOption);
        int numScsiController = scsiControllerPDL.getHardLimit();
        if(numScsiController==1) return scsiControllerPDL.getResource().getDescription();
        else return numScsiController + "x" + scsiControllerPDL.getResource().getDescription();
    }

    public static List<String> getIdeOptions(AOServConnector rootConn, SignupCustomizeServerForm signupCustomizeServerForm) {
        List<String> ideOptions = new ArrayList<String>();
        for(String pkey : signupCustomizeServerForm.getIdeOptions()) {
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

    public static List<String> getSataOptions(AOServConnector rootConn, SignupCustomizeServerForm signupCustomizeServerForm) {
        List<String> sataOptions = new ArrayList<String>();
        for(String pkey : signupCustomizeServerForm.getSataOptions()) {
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

    public static List<String> getScsiOptions(AOServConnector rootConn, SignupCustomizeServerForm signupCustomizeServerForm) {
        List<String> scsiOptions = new ArrayList<String>();
        for(String pkey : signupCustomizeServerForm.getScsiOptions()) {
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
    
    public static void setConfirmationRequestAttributes(
        ServletContext servletContext,
        HttpServletRequest request,
        SignupSelectServerForm signupSelectServerForm,
        SignupCustomizeServerForm signupCustomizeServerForm
    ) throws IOException {
        // Lookup things needed by the view
        AOServConnector rootConn = RootAOServConnector.getRootAOServConnector(servletContext);
        PackageDefinition packageDefinition = rootConn.packageDefinitions.get(signupSelectServerForm.getPackageDefinition());

        // Store as request attribute for the view
        request.setAttribute("packageDefinition", packageDefinition);
        request.setAttribute("monthlyRate", getHardwareMonthlyRate(rootConn, signupCustomizeServerForm, packageDefinition));
        request.setAttribute("powerOption", getPowerOption(rootConn, signupCustomizeServerForm));
        request.setAttribute("cpuOption", getCpuOption(rootConn, signupCustomizeServerForm));
        request.setAttribute("ramOption", getRamOption(rootConn, signupCustomizeServerForm));
        request.setAttribute("sataControllerOption", getSataControllerOption(rootConn, signupCustomizeServerForm));
        request.setAttribute("scsiControllerOption", getScsiControllerOption(rootConn, signupCustomizeServerForm));
        request.setAttribute("ideOptions", getIdeOptions(rootConn, signupCustomizeServerForm));
        request.setAttribute("sataOptions", getSataOptions(rootConn, signupCustomizeServerForm));
        request.setAttribute("scsiOptions", getScsiOptions(rootConn, signupCustomizeServerForm));
    }

    public static void printConfirmation(
        HttpServletRequest request,
        ChainWriter emailOut,
        Locale contentLocale,
        AOServConnector rootConn,
        PackageDefinition packageDefinition,
        SignupCustomizeServerForm signupCustomizeServerForm,
        MessageResources signupApplicationResources
    ) {
        String powerOption = getPowerOption(rootConn, signupCustomizeServerForm);
        if(!GenericValidator.isBlankOrNull(powerOption)) {
            emailOut.print("    <TR>\n"
                         + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signup.notRequired")).print("</TD>\n"
                         + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signupCustomizeServerConfirmation.power.prompt")).print("</TD>\n"
                         + "        <TD>").print(powerOption).print("</TD>\n"
                         + "    </TR>\n");
        }
        emailOut.print("    <TR>\n"
                     + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signup.notRequired")).print("</TD>\n"
                     + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signupCustomizeServerConfirmation.cpu.prompt")).print("</TD>\n"
                     + "        <TD>").print(getCpuOption(rootConn, signupCustomizeServerForm)).print("</TD>\n"
                     + "    </TR>\n"
                     + "    <TR>\n"
                     + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signup.notRequired")).print("</TD>\n"
                     + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signupCustomizeServerConfirmation.ram.prompt")).print("</TD>\n"
                     + "        <TD>").writeHtml(getRamOption(rootConn, signupCustomizeServerForm)).print("</TD>\n"
                     + "    </TR>\n");
        String sataControllerOption = getSataControllerOption(rootConn, signupCustomizeServerForm);
        if(!GenericValidator.isBlankOrNull(sataControllerOption)) {
            emailOut.print("    <TR>\n"
                         + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signup.notRequired")).print("</TD>\n"
                         + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signupCustomizeServerConfirmation.sataController.prompt")).print("</TD>\n"
                         + "        <TD>").print(sataControllerOption).print("</TD>\n"
                         + "    </TR>\n");
        }
        String scsiControllerOption = getScsiControllerOption(rootConn, signupCustomizeServerForm);
        if(!GenericValidator.isBlankOrNull(scsiControllerOption)) {
            emailOut.print("    <TR>\n"
                         + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signup.notRequired")).print("</TD>\n"
                         + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signupCustomizeServerConfirmation.scsiController.prompt")).print("</TD>\n"
                         + "        <TD>").print(scsiControllerOption).print("</TD>\n"
                         + "    </TR>\n");
        }
        for(String ideOption : getIdeOptions(rootConn, signupCustomizeServerForm)) {
            emailOut.print("    <TR>\n"
                         + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signup.notRequired")).print("</TD>\n"
                         + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signupCustomizeServerConfirmation.ide.prompt")).print("</TD>\n"
                         + "        <TD>").writeHtml(ideOption).print("</TD>\n"
                         + "    </TR>\n");
        }
        for(String sataOption : getSataOptions(rootConn, signupCustomizeServerForm)) {
            emailOut.print("    <TR>\n"
                         + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signup.notRequired")).print("</TD>\n"
                         + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signupCustomizeServerConfirmation.sata.prompt")).print("</TD>\n"
                         + "        <TD>").writeHtml(sataOption).print("</TD>\n"
                         + "    </TR>\n");
        }
        for(String scsiOption : getScsiOptions(rootConn, signupCustomizeServerForm)) {
            emailOut.print("    <TR>\n"
                         + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signup.notRequired")).print("</TD>\n"
                         + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signupCustomizeServerConfirmation.scsi.prompt")).print("</TD>\n"
                         + "        <TD>").writeHtml(scsiOption).print("</TD>\n"
                         + "    </TR>\n");
        }
        emailOut.print("    <TR>\n"
                     + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signup.notRequired")).print("</TD>\n"
                     + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signupCustomizeServerConfirmation.setup.prompt")).print("</TD>\n"
                     + "        <TD>\n");
        BigDecimal setup = SignupSelectServerActionHelper.getSetup(packageDefinition);
        if(setup==null) {
            emailOut.print("            ").print(signupApplicationResources.getMessage(contentLocale, "signupCustomizeServerConfirmation.setup.none")).print("\n");
        } else {
            emailOut.print("            $").print(setup).print("\n");
        }
        emailOut.print("        </TD>\n"
                     + "    </TR>\n"
                     + "    <TR>\n"
                     + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signup.notRequired")).print("</TD>\n"
                     + "        <TD>").print(signupApplicationResources.getMessage(contentLocale, "signupCustomizeServerConfirmation.monthlyRate.prompt")).print("</TD>\n"
                     + "        <TD>$").print(request.getAttribute("monthlyRate")).print("</TD>\n"
                     + "    </TR>\n");
    }
    
    /**
     * Gets the total amount of hard drive space in gigabytes.
     */
    public static int getTotalHardwareDiskSpace(AOServConnector rootConn, SignupCustomizeServerForm signupCustomizeServerForm) {
        if(signupCustomizeServerForm==null) return 0;
        int total = 0;
        for(String ideOption : signupCustomizeServerForm.getIdeOptions()) {
            if(ideOption!=null && ideOption.length()>0 && !"-1".equals(ideOption)) {
                PackageDefinitionLimit limit = rootConn.packageDefinitionLimits.get(Integer.parseInt(ideOption));
                if(limit!=null) {
                    Resource resource = limit.getResource();
                    String name = resource.getName();
                    if(name.startsWith("hardware_disk_ide_")) {
                        // Is in formation hardware_disk_ide_RPM_SIZE
                        int pos = name.indexOf('_', 18);
                        if(pos!=-1) {
                            int pos2 = name.indexOf('_', pos+1);
                            if(pos2==-1) {
                                // Not raid
                                total += Integer.parseInt(name.substring(pos+1));
                            } else {
                                // Does it end with _raid1, double space if it does.
                                if(name.endsWith("_raid1")) {
                                    total += 2*Integer.parseInt(name.substring(pos+1, pos2));
                                } else {
                                    total += Integer.parseInt(name.substring(pos+1, pos2));
                                }
                            }
                        }
                    }
                }
            }
        }
        for(String sataOption : signupCustomizeServerForm.getSataOptions()) {
            if(sataOption!=null && sataOption.length()>0 && !"-1".equals(sataOption)) {
                PackageDefinitionLimit limit = rootConn.packageDefinitionLimits.get(Integer.parseInt(sataOption));
                if(limit!=null) {
                    Resource resource = limit.getResource();
                    String name = resource.getName();
                    if(name.startsWith("hardware_disk_sata_")) {
                        // Is in formation hardware_disk_sata_RPM_SIZE
                        int pos = name.indexOf('_', 19);
                        if(pos!=-1) {
                            int pos2 = name.indexOf('_', pos+1);
                            if(pos2==-1) {
                                // Not raid
                                total += Integer.parseInt(name.substring(pos+1));
                            } else {
                                // Does it end with _raid1, double space if it does.
                                if(name.endsWith("_raid1")) {
                                    total += 2*Integer.parseInt(name.substring(pos+1, pos2));
                                } else {
                                    total += Integer.parseInt(name.substring(pos+1, pos2));
                                }
                            }
                        }
                    }
                }
            }
        }
        for(String scsiOption : signupCustomizeServerForm.getScsiOptions()) {
            if(scsiOption!=null && scsiOption.length()>0 && !"-1".equals(scsiOption)) {
                PackageDefinitionLimit limit = rootConn.packageDefinitionLimits.get(Integer.parseInt(scsiOption));
                if(limit!=null) {
                    Resource resource = limit.getResource();
                    String name = resource.getName();
                    if(name.startsWith("hardware_disk_scsi_")) {
                        // Is in formation hardware_disk_scsi_RPM_SIZE
                        int pos = name.indexOf('_', 19);
                        if(pos!=-1) {
                            int pos2 = name.indexOf('_', pos+1);
                            if(pos2==-1) {
                                // Not raid
                                total += Integer.parseInt(name.substring(pos+1));
                            } else {
                                // Does it end with _raid1, double space if it does.
                                if(name.endsWith("_raid1")) {
                                    total += 2*Integer.parseInt(name.substring(pos+1, pos2));
                                } else {
                                    total += Integer.parseInt(name.substring(pos+1, pos2));
                                }
                            }
                        }
                    }
                }
            }
        }
        return total;
    }
}
