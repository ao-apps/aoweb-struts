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
import com.aoindustries.sql.SQLUtility;
import com.aoindustries.util.WrappedException;
import com.aoindustries.website.RootAOServConnector;
import com.aoindustries.website.Skin;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

/**
 * @author  AO Industries, Inc.
 */
public class Dedicated2Action extends DedicatedStepAction {

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
        
        AOServConnector rootConn = RootAOServConnector.getRootAOServConnector(getServlet().getServletContext());
        PackageDefinition packageDefinition = rootConn.packageDefinitions.get(dedicatedSignupSelectServerForm.getPackageDefinition());
        if(packageDefinition==null) throw new SQLException("Unable to find PackageDefinition: "+dedicatedSignupSelectServerForm.getPackageDefinition());
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
                } else throw new WrappedException(new SQLException("Unexpected type of disk: "+resourceName));
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
                    powerOptions.add(new Option(limit.getPKey(), description, new BigDecimal(SQLUtility.getDecimal(maxPowers * (additionalRate-cheapestRate)))));
                }
            } else if(resourceName.startsWith("hardware_processor_")) {
                int limitCpu = limit.getHardLimit();
                if(limitCpu>0) {
                    int additionalRate = limit.getAdditionalRate();
                    if(additionalRate==-1) additionalRate=0;
                    int cheapestRate = cheapestCPU.getAdditionalRate();
                    if(cheapestRate==-1) cheapestRate=0;
                    String description = maxCPUs==1 ? resource.getDescription() : (maxCPUs+"x"+resource.getDescription());
                    cpuOptions.add(new Option(limit.getPKey(), description, new BigDecimal(SQLUtility.getDecimal(maxCPUs * (additionalRate-cheapestRate)))));
                }
            } else if(resourceName.startsWith("hardware_ram_")) {
                int limitRAM = limit.getHardLimit();
                if(limitRAM>0) {
                    int additionalRate = limit.getAdditionalRate();
                    if(additionalRate==-1) additionalRate=0;
                    int cheapestRate = cheapestRAM.getAdditionalRate();
                    if(cheapestRate==-1) cheapestRate=0;
                    String description = maxRAMs==1 ? resource.getDescription() : (maxRAMs+"x"+resource.getDescription());
                    ramOptions.add(new Option(limit.getPKey(), description, new BigDecimal(SQLUtility.getDecimal(maxRAMs * (additionalRate-cheapestRate)))));
                }
            } else if(resourceName.startsWith("hardware_disk_controller_sata_")) {
                int limitSataController = limit.getHardLimit();
                if(limitSataController>0) {
                    int additionalRate = limit.getAdditionalRate();
                    if(additionalRate==-1) additionalRate=0;
                    int cheapestRate = cheapestSataController.getAdditionalRate();
                    if(cheapestRate==-1) cheapestRate=0;
                    String description = maxSataControllers==1 ? resource.getDescription() : (maxSataControllers+"x"+resource.getDescription());
                    sataControllerOptions.add(new Option(limit.getPKey(), description, new BigDecimal(SQLUtility.getDecimal(maxSataControllers * (additionalRate-cheapestRate)))));
                }
            } else if(resourceName.startsWith("hardware_disk_controller_scsi_")) {
                int limitScsiController = limit.getHardLimit();
                if(limitScsiController>0) {
                    int additionalRate = limit.getAdditionalRate();
                    if(additionalRate==-1) additionalRate=0;
                    int cheapestRate = cheapestScsiController.getAdditionalRate();
                    if(cheapestRate==-1) cheapestRate=0;
                    String description = maxScsiControllers==1 ? resource.getDescription() : (maxScsiControllers+"x"+resource.getDescription());
                    scsiControllerOptions.add(new Option(limit.getPKey(), description, new BigDecimal(SQLUtility.getDecimal(maxScsiControllers * (additionalRate-cheapestRate)))));
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
                        if(options.isEmpty()) options.add(new Option(-1, "None", new BigDecimal(SQLUtility.getDecimal(c==0 ? (adjustedRate-additionalRate) : 0))));
                        options.add(new Option(limit.getPKey(), resource.getDescription(), new BigDecimal(SQLUtility.getDecimal(c==0 ? adjustedRate : additionalRate))));
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
                        if(options.isEmpty()) options.add(new Option(-1, "None", new BigDecimal(SQLUtility.getDecimal(c==0 ? (adjustedRate-additionalRate) : 0))));
                        options.add(new Option(limit.getPKey(), resource.getDescription(), new BigDecimal(SQLUtility.getDecimal(c==0 ? adjustedRate : additionalRate))));
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
                        if(options.isEmpty()) options.add(new Option(-1, "None", new BigDecimal(SQLUtility.getDecimal(c==0 ? (adjustedRate-additionalRate) : 0))));
                        options.add(new Option(limit.getPKey(), resource.getDescription(), new BigDecimal(SQLUtility.getDecimal(c==0 ? adjustedRate : additionalRate))));
                    }
                }
            }
        }

        // Sort by price
        Collections.sort(powerOptions, new PriceComparator());
        Collections.sort(cpuOptions, new PriceComparator());
        Collections.sort(ramOptions, new PriceComparator());
        Collections.sort(sataControllerOptions, new PriceComparator());
        Collections.sort(scsiControllerOptions, new PriceComparator());
        for(List<Option> ideOptionList : ideOptions) Collections.sort(ideOptionList, new PriceComparator());
        for(List<Option> sataOptionList : sataOptions) Collections.sort(sataOptionList, new PriceComparator());
        for(List<Option> scsiOptionList : scsiOptions) Collections.sort(scsiOptionList, new PriceComparator());

        // Clear any customization settings that are not part of the current package definition (this happens when they
        // select a different package type)
        if(dedicatedSignupCustomizeServerForm.getPowerOption()!=-1) {
            PackageDefinitionLimit pdl = rootConn.packageDefinitionLimits.get(dedicatedSignupCustomizeServerForm.getPowerOption());
            if(pdl==null || !packageDefinition.equals(pdl.getPackageDefinition())) dedicatedSignupCustomizeServerForm.setPowerOption(-1);
        }
        if(dedicatedSignupCustomizeServerForm.getCpuOption()!=-1) {
            PackageDefinitionLimit pdl = rootConn.packageDefinitionLimits.get(dedicatedSignupCustomizeServerForm.getCpuOption());
            if(pdl==null || !packageDefinition.equals(pdl.getPackageDefinition())) dedicatedSignupCustomizeServerForm.setCpuOption(-1);
        }
        if(dedicatedSignupCustomizeServerForm.getRamOption()!=-1) {
            PackageDefinitionLimit pdl = rootConn.packageDefinitionLimits.get(dedicatedSignupCustomizeServerForm.getRamOption());
            if(pdl==null || !packageDefinition.equals(pdl.getPackageDefinition())) dedicatedSignupCustomizeServerForm.setRamOption(-1);
        }
        if(dedicatedSignupCustomizeServerForm.getSataControllerOption()!=-1) {
            PackageDefinitionLimit pdl = rootConn.packageDefinitionLimits.get(dedicatedSignupCustomizeServerForm.getSataControllerOption());
            if(pdl==null || !packageDefinition.equals(pdl.getPackageDefinition())) dedicatedSignupCustomizeServerForm.setSataControllerOption(-1);
        }
        if(dedicatedSignupCustomizeServerForm.getScsiControllerOption()!=-1) {
            PackageDefinitionLimit pdl = rootConn.packageDefinitionLimits.get(dedicatedSignupCustomizeServerForm.getScsiControllerOption());
            if(pdl==null || !packageDefinition.equals(pdl.getPackageDefinition())) dedicatedSignupCustomizeServerForm.setScsiControllerOption(-1);
        }
        List<String> formIdeOptions = dedicatedSignupCustomizeServerForm.getIdeOptions();
        while(formIdeOptions.size()>maxIDEs) formIdeOptions.remove(formIdeOptions.size()-1);
        for(int c=0;c<formIdeOptions.size();c++) {
            String S = formIdeOptions.get(c);
            if(S!=null && S.length()>0 && !S.equals("-1")) {
                int pkey = Integer.parseInt(S);
                PackageDefinitionLimit pdl = rootConn.packageDefinitionLimits.get(pkey);
                if(pdl==null || !packageDefinition.equals(pdl.getPackageDefinition())) formIdeOptions.set(c, "-1");
            }
        }
        List<String> formSataOptions = dedicatedSignupCustomizeServerForm.getSataOptions();
        while(formSataOptions.size()>maxSATAs) formSataOptions.remove(formSataOptions.size()-1);
        for(int c=0;c<formSataOptions.size();c++) {
            String S = formSataOptions.get(c);
            if(S!=null && S.length()>0 && !S.equals("-1")) {
                int pkey = Integer.parseInt(S);
                PackageDefinitionLimit pdl = rootConn.packageDefinitionLimits.get(pkey);
                if(pdl==null || !packageDefinition.equals(pdl.getPackageDefinition())) formSataOptions.set(c, "-1");
            }
        }
        List<String> formScsiOptions = dedicatedSignupCustomizeServerForm.getScsiOptions();
        while(formSataOptions.size()>maxSATAs) formSataOptions.remove(formSataOptions.size()-1);
        for(int c=0;c<formScsiOptions.size();c++) {
            String S = formScsiOptions.get(c);
            if(S!=null && S.length()>0 && !S.equals("-1")) {
                int pkey = Integer.parseInt(S);
                PackageDefinitionLimit pdl = rootConn.packageDefinitionLimits.get(pkey);
                if(pdl==null || !packageDefinition.equals(pdl.getPackageDefinition())) formScsiOptions.set(c, "-1");
            }
        }

        // Default to cheapest if not already selected
        if(cheapestPower!=null && dedicatedSignupCustomizeServerForm.getPowerOption()==-1) dedicatedSignupCustomizeServerForm.setPowerOption(cheapestPower.getPKey());
        if(dedicatedSignupCustomizeServerForm.getCpuOption()==-1) dedicatedSignupCustomizeServerForm.setCpuOption(cheapestCPU.getPKey());
        if(dedicatedSignupCustomizeServerForm.getRamOption()==-1) dedicatedSignupCustomizeServerForm.setRamOption(cheapestRAM.getPKey());
        if(cheapestSataController!=null && dedicatedSignupCustomizeServerForm.getSataControllerOption()==-1) dedicatedSignupCustomizeServerForm.setSataControllerOption(cheapestSataController.getPKey());
        if(cheapestScsiController!=null && dedicatedSignupCustomizeServerForm.getScsiControllerOption()==-1) dedicatedSignupCustomizeServerForm.setScsiControllerOption(cheapestScsiController.getPKey());
        for(int c=0;c<maxIDEs;c++) {
            List<Option> options = ideOptions.get(c);
            if(!options.isEmpty()) {
                Option firstOption = options.get(0);
                if(options.size()>=2 && firstOption.getPriceDifference().compareTo(BigDecimal.ZERO)<0) {
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
                if(options.size()>=2 && firstOption.getPriceDifference().compareTo(BigDecimal.ZERO)<0) {
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
                if(options.size()>=2 && firstOption.getPriceDifference().compareTo(BigDecimal.ZERO)<0) {
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

        // Clear errors if they should not be displayed
        clearErrors(request);

        return mapping.findForward("input");
    }
    
    /**
     * May clear specific errors here.
     */
    protected void clearErrors(HttpServletRequest request) {
        saveErrors(request, new ActionMessages());
    }

    private static class PriceComparator implements Comparator<Option> {
        public int compare(Option pdl1, Option pdl2) {
            return pdl1.getPriceDifference().compareTo(pdl2.getPriceDifference());
        }
    }
}
