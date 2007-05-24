package com.aoindustries.website.signup;

/*
 * Copyright 2007 by AO Industries, Inc.,
 * 816 Azalea Rd, Mobile, Alabama, 36693, U.S.A.
 * All rights reserved.
 */
import com.aoindustries.aoserv.client.AOServConnector;
import com.aoindustries.aoserv.client.Business;
import com.aoindustries.aoserv.client.PackageCategory;
import com.aoindustries.aoserv.client.PackageDefinition;
import com.aoindustries.aoserv.client.PackageDefinitionLimit;
import com.aoindustries.sql.SQLUtility;
import com.aoindustries.website.RootAOServConnector;
import com.aoindustries.website.Skin;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author  AO Industries, Inc.
 */
public class DedicatedAction extends DedicatedStepAction {

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
        AOServConnector rootConn = RootAOServConnector.getRootAOServConnector(getServlet().getServletContext());
        PackageCategory category = rootConn.packageCategories.get(PackageCategory.DEDICATED);
        Business rootBusiness = rootConn.getThisBusinessAdministrator().getUsername().getPackage().getBusiness();
        List<PackageDefinition> packageDefinitions = rootBusiness.getPackageDefinitions(category);
        List<Server> servers = new ArrayList<Server>();
        
        for(PackageDefinition packageDefinition : packageDefinitions) {
            if(packageDefinition.isActive()) {
                List<PackageDefinitionLimit> limits = packageDefinition.getLimits();

                // Calculate the total minimum monthly
                int setup = packageDefinition.getSetupFee();
                if(setup==-1) setup = 0;
                int minimumMonthly = packageDefinition.getMonthlyRate();
                if(minimumMonthly==-1) minimumMonthly = 0;
                int maximumMonthly = minimumMonthly;

                // Find the maximum number of different resources and the cheapest options
                int maxPowers = 0;
                PackageDefinitionLimit cheapestPower = null;
                PackageDefinitionLimit expensivePower = null;
                int maxCPUs = 0;
                PackageDefinitionLimit cheapestCPU = null;
                PackageDefinitionLimit expensiveCPU = null;
                int maxRAMs = 0;
                PackageDefinitionLimit cheapestRAM = null;
                PackageDefinitionLimit expensiveRAM = null;
                int maxSataControllers = 0;
                PackageDefinitionLimit cheapestSataController = null;
                PackageDefinitionLimit expensiveSataController = null;
                int maxScsiControllers = 0;
                PackageDefinitionLimit cheapestScsiController = null;
                PackageDefinitionLimit expensiveScsiController = null;
                int maxIDEs = 0;
                PackageDefinitionLimit cheapestIDE = null;
                PackageDefinitionLimit expensiveIDE = null;
                int maxSATAs = 0;
                PackageDefinitionLimit cheapestSATA = null;
                PackageDefinitionLimit expensiveSATA = null;
                int maxSCSIs = 0;
                PackageDefinitionLimit cheapestSCSI = null;
                PackageDefinitionLimit expensiveSCSI = null;
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
                            if(expensivePower==null) expensivePower = limit;
                            else {
                                int additionalRate = limit.getAdditionalRate();
                                if(additionalRate==-1) additionalRate=0;
                                int expensiveRate = expensivePower.getAdditionalRate();
                                if(expensiveRate==-1) expensiveRate=0;
                                if(additionalRate>expensiveRate) expensivePower = limit;
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
                            if(expensiveCPU==null) expensiveCPU = limit;
                            else {
                                int additionalRate = limit.getAdditionalRate();
                                if(additionalRate==-1) additionalRate=0;
                                int expensiveRate = expensiveCPU.getAdditionalRate();
                                if(expensiveRate==-1) expensiveRate=0;
                                if(additionalRate>expensiveRate) expensiveCPU = limit;
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
                            if(expensiveRAM==null) expensiveRAM = limit;
                            else {
                                int additionalRate = limit.getAdditionalRate();
                                if(additionalRate==-1) additionalRate=0;
                                int expensiveRate = expensiveRAM.getAdditionalRate();
                                if(expensiveRate==-1) expensiveRate=0;
                                if(additionalRate>expensiveRate) expensiveRAM = limit;
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
                            if(expensiveSataController==null) expensiveSataController = limit;
                            else {
                                int additionalRate = limit.getAdditionalRate();
                                if(additionalRate==-1) additionalRate=0;
                                int expensiveRate = expensiveSataController.getAdditionalRate();
                                if(expensiveRate==-1) expensiveRate=0;
                                if(additionalRate>expensiveRate) expensiveSataController = limit;
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
                            if(expensiveScsiController==null) expensiveScsiController = limit;
                            else {
                                int additionalRate = limit.getAdditionalRate();
                                if(additionalRate==-1) additionalRate=0;
                                int expensiveRate = expensiveScsiController.getAdditionalRate();
                                if(expensiveRate==-1) expensiveRate=0;
                                if(additionalRate>expensiveRate) expensiveScsiController = limit;
                            }
                        }
                    } else if(resourceName.startsWith("hardware_disk_ide_")) {
                        int limitIDE = limit.getHardLimit();
                        if(limitIDE>0) {
                            if(limitIDE>maxIDEs) maxIDEs = limitIDE;
                            if(cheapestIDE==null) cheapestIDE = limit;
                            else {
                                int additionalRate = limit.getAdditionalRate();
                                if(additionalRate==-1) additionalRate=0;
                                int cheapestRate = cheapestIDE.getAdditionalRate();
                                if(cheapestRate==-1) cheapestRate=0;
                                if(additionalRate<cheapestRate) cheapestIDE = limit;
                            }
                            if(expensiveIDE==null) expensiveIDE = limit;
                            else {
                                int additionalRate = limit.getAdditionalRate();
                                if(additionalRate==-1) additionalRate=0;
                                int expensiveRate = expensiveIDE.getAdditionalRate();
                                if(expensiveRate==-1) expensiveRate=0;
                                if(additionalRate>expensiveRate) expensiveIDE = limit;
                            }
                        }
                    } else if(resourceName.startsWith("hardware_disk_sata_")) {
                        int limitSATA = limit.getHardLimit();
                        if(limitSATA>0) {
                            if(limitSATA>maxSATAs) maxSATAs = limitSATA;
                            if(cheapestSATA==null) cheapestSATA = limit;
                            else {
                                int additionalRate = limit.getAdditionalRate();
                                if(additionalRate==-1) additionalRate=0;
                                int cheapestRate = cheapestSATA.getAdditionalRate();
                                if(cheapestRate==-1) cheapestRate=0;
                                if(additionalRate<cheapestRate) cheapestSATA = limit;
                            }
                            if(expensiveSATA==null) expensiveSATA = limit;
                            else {
                                int additionalRate = limit.getAdditionalRate();
                                if(additionalRate==-1) additionalRate=0;
                                int expensiveRate = expensiveSATA.getAdditionalRate();
                                if(expensiveRate==-1) expensiveRate=0;
                                if(additionalRate>expensiveRate) expensiveSATA = limit;
                            }
                        }
                    } else if(resourceName.startsWith("hardware_disk_scsi_")) {
                        int limitSCSI = limit.getHardLimit();
                        if(limitSCSI>0) {
                            if(limitSCSI>maxSCSIs) maxSCSIs = limitSCSI;
                            if(cheapestSCSI==null) cheapestSCSI = limit;
                            else {
                                int additionalRate = limit.getAdditionalRate();
                                if(additionalRate==-1) additionalRate=0;
                                int cheapestRate = cheapestSCSI.getAdditionalRate();
                                if(cheapestRate==-1) cheapestRate=0;
                                if(additionalRate<cheapestRate) cheapestSCSI = limit;
                            }
                            if(expensiveSCSI==null) expensiveSCSI = limit;
                            else {
                                int additionalRate = limit.getAdditionalRate();
                                if(additionalRate==-1) additionalRate=0;
                                int expensiveRate = expensiveSCSI.getAdditionalRate();
                                if(expensiveRate==-1) expensiveRate=0;
                                if(additionalRate>expensiveRate) expensiveSCSI = limit;
                            }
                        }
                    }
                }

                // Build the Power descriptions
                StringBuilder minimumPower = new StringBuilder();
                if(cheapestPower!=null) {
                    if(maxPowers!=1) minimumPower.append(maxPowers).append('x');
                    minimumPower.append(cheapestPower.getResource().getDescription());
                }

                StringBuilder maximumPower = new StringBuilder();
                if(expensivePower!=null) {
                    if(maxPowers!=1) maximumPower.append(maxPowers).append('x');
                    maximumPower.append(expensivePower.getResource().getDescription());
                }

                // Add the Power costs
                if(cheapestPower!=null) minimumMonthly += cheapestPower.getAdditionalRate()==-1 ? 0 : (cheapestPower.getAdditionalRate() * maxPowers);
                if(expensivePower!=null) maximumMonthly += expensivePower.getAdditionalRate()==-1 ? 0 : (expensivePower.getAdditionalRate() * maxPowers);

                // Build the CPU descriptions
                if(cheapestCPU==null) throw new SQLException("Unable to find cheapestCPU");
                StringBuilder minimumCpu = new StringBuilder();
                if(maxCPUs!=1) minimumCpu.append(maxCPUs).append('x');
                minimumCpu.append(cheapestCPU.getResource().getDescription());

                if(expensiveCPU==null) throw new SQLException("Unable to find expensiveCPU");
                StringBuilder maximumCpu = new StringBuilder();
                if(maxCPUs!=1) maximumCpu.append(maxCPUs).append('x');
                maximumCpu.append(expensiveCPU.getResource().getDescription());

                // Add the CPU costs
                minimumMonthly += cheapestCPU.getAdditionalRate()==-1 ? 0 : (cheapestCPU.getAdditionalRate() * maxCPUs);
                maximumMonthly += expensiveCPU.getAdditionalRate()==-1 ? 0 : (expensiveCPU.getAdditionalRate() * maxCPUs);

                // Build the RAM description
                if(cheapestRAM==null) throw new SQLException("Unable to find cheapestRAM");
                StringBuilder minimumRam = new StringBuilder();
                minimumRam.append(cheapestRAM.getResource().getDescription());

                if(expensiveRAM==null) throw new SQLException("Unable to find expensiveRAM");
                StringBuilder maximumRam = new StringBuilder();
                if(maxRAMs>1) maximumRam.append(maxRAMs).append('x');
                maximumRam.append(expensiveRAM.getResource().getDescription());

                // Add the RAM cost
                minimumMonthly += cheapestRAM.getAdditionalRate()==-1 ? 0 : cheapestRAM.getAdditionalRate();
                maximumMonthly += expensiveRAM.getAdditionalRate()==-1 ? 0 : (expensiveRAM.getAdditionalRate() * maxRAMs);

                // Build the SATA controller description
                StringBuilder minimumSataController = new StringBuilder();
                if(cheapestSataController!=null) minimumSataController.append(cheapestSataController.getResource().getDescription());

                StringBuilder maximumSataController = new StringBuilder();
                if(expensiveSataController!=null) {
                    if(maxSataControllers>1) maximumSataController.append(maxSataControllers).append('x');
                    maximumSataController.append(expensiveSataController.getResource().getDescription());
                }

                // Add the SataController cost
                if(cheapestSataController!=null) minimumMonthly += cheapestSataController.getAdditionalRate()==-1 ? 0 : cheapestSataController.getAdditionalRate();
                if(expensiveSataController!=null) maximumMonthly += expensiveSataController.getAdditionalRate()==-1 ? 0 : (expensiveSataController.getAdditionalRate() * maxSataControllers);

                // Build the SCSI controller description
                StringBuilder minimumScsiController = new StringBuilder();
                if(cheapestScsiController!=null) minimumScsiController.append(cheapestScsiController.getResource().getDescription());

                StringBuilder maximumScsiController = new StringBuilder();
                if(expensiveScsiController!=null) {
                    if(maxScsiControllers>1) maximumScsiController.append(maxScsiControllers).append('x');
                    maximumScsiController.append(expensiveScsiController.getResource().getDescription());
                }

                // Add the ScsiController cost
                if(cheapestScsiController!=null) minimumMonthly += cheapestScsiController.getAdditionalRate()==-1 ? 0 : cheapestScsiController.getAdditionalRate();
                if(expensiveScsiController!=null) maximumMonthly += expensiveScsiController.getAdditionalRate()==-1 ? 0 : (expensiveScsiController.getAdditionalRate() * maxScsiControllers);

                // Only keep the cheapest of the cheap disks
                PackageDefinitionLimit cheapestDisk = cheapestIDE;
                cheapestDisk = cheapest(cheapestDisk, cheapestSATA);
                cheapestDisk = cheapest(cheapestDisk, cheapestSCSI);
                if(cheapestDisk==null) throw new SQLException("Unable to find cheapestDisk");

                if(!cheapestDisk.equals(cheapestIDE)) cheapestIDE=null;
                if(!cheapestDisk.equals(cheapestSATA)) cheapestSATA=null;
                if(!cheapestDisk.equals(cheapestSCSI)) cheapestSCSI=null;

                // Build the disk description
                String minimumIDE = getDiskDescription(1, cheapestIDE);
                String maximumIDE = getDiskDescription(maxIDEs, expensiveIDE);
                String minimumSATA = getDiskDescription(1, cheapestSATA);
                String maximumSATA = getDiskDescription(maxSATAs, expensiveSATA);
                String minimumSCSI = getDiskDescription(1, cheapestSCSI);
                String maximumSCSI = getDiskDescription(maxSCSIs, expensiveSCSI);

                // Add the disk cost
                minimumMonthly += cheapestDisk.getAdditionalRate()==-1 ? 0 : cheapestDisk.getAdditionalRate();
                if(expensiveIDE!=null) maximumMonthly += expensiveIDE.getAdditionalRate()==-1 ? 0 : (expensiveIDE.getAdditionalRate() * maxIDEs);
                if(expensiveSATA!=null) maximumMonthly += expensiveSATA.getAdditionalRate()==-1 ? 0 : (expensiveSATA.getAdditionalRate() * maxSATAs);
                if(expensiveSCSI!=null) maximumMonthly += expensiveSCSI.getAdditionalRate()==-1 ? 0 : (expensiveSCSI.getAdditionalRate() * maxSCSIs);
                
                servers.add(
                    new Server(
                        packageDefinition.getPKey(),
                        packageDefinition.getDisplay(),
                        minimumPower.toString(),
                        maximumPower.toString(),
                        minimumCpu.toString().replaceAll(", ", "<br>&nbsp;&nbsp;&nbsp;&nbsp;"),
                        maximumCpu.toString().replaceAll(", ", "<br>&nbsp;&nbsp;&nbsp;&nbsp;"),
                        minimumRam.toString(),
                        maximumRam.toString(),
                        minimumSataController.toString(),
                        maximumSataController.toString(),
                        minimumScsiController.toString(),
                        maximumScsiController.toString(),
                        minimumIDE,
                        maximumIDE,
                        minimumSATA,
                        maximumSATA,
                        minimumSCSI,
                        maximumSCSI,
                        setup==0 ? null : new BigDecimal(SQLUtility.getDecimal(setup)),
                        minimumMonthly==0 ? null : new BigDecimal(SQLUtility.getDecimal(minimumMonthly)),
                        maximumMonthly==0 ? null : new BigDecimal(SQLUtility.getDecimal(maximumMonthly))
                    )
                );
            }
        }

        
        request.setAttribute("servers", servers);

        return mapping.findForward("input");
    }
    
    public static PackageDefinitionLimit cheapest(PackageDefinitionLimit pdl1, PackageDefinitionLimit pdl2) {
        if(pdl1==null) return pdl2;
        if(pdl2==null) return pdl1;
        int cost1 = pdl1.getAdditionalRate();
        if(cost1==-1) cost1 = 0;
        int cost2 = pdl2.getAdditionalRate();
        if(cost2==-1) cost2 = 0;
        return (cost2<cost1) ? pdl2 : pdl1;
    }
    
    private static String getDiskDescription(int numDrives, PackageDefinitionLimit pdl) {
        if(pdl==null || numDrives==0) return null;
        if(numDrives==1) return "Single " + pdl.getResource().getDescription();
        else return numDrives + "x" + pdl.getResource().getDescription();
    }

    public static class Server {
        final private int packageDefinition;
        final private String name;
        final private String minimumPower;
        final private String maximumPower;
        final private String minimumCpu;
        final private String maximumCpu;
        final private String minimumRam;
        final private String maximumRam;
        final private String minimumSataController;
        final private String maximumSataController;
        final private String minimumScsiController;
        final private String maximumScsiController;
        final private String minimumIDE;
        final private String maximumIDE;
        final private String minimumSATA;
        final private String maximumSATA;
        final private String minimumSCSI;
        final private String maximumSCSI;
        final private BigDecimal setup;
        final private BigDecimal minimumMonthly;
        final private BigDecimal maximumMonthly;

        private Server(
            int packageDefinition,
            String name,
            String minimumPower,
            String maximumPower,
            String minimumCpu,
            String maximumCpu,
            String minimumRam,
            String maximumRam,
            String minimumSataController,
            String maximumSataController,
            String minimumScsiController,
            String maximumScsiController,
            String minimumIDE,
            String maximumIDE,
            String minimumSATA,
            String maximumSATA,
            String minimumSCSI,
            String maximumSCSI,
            BigDecimal setup,
            BigDecimal minimumMonthly,
            BigDecimal maximumMonthly
        ) {
            this.packageDefinition = packageDefinition;
            this.name = name;
            this.minimumPower = minimumPower;
            this.maximumPower = maximumPower;
            this.minimumCpu = minimumCpu;
            this.maximumCpu = maximumCpu;
            this.minimumRam = minimumRam;
            this.maximumRam = maximumRam;
            this.minimumSataController = minimumSataController;
            this.maximumSataController = maximumSataController;
            this.minimumScsiController = minimumScsiController;
            this.maximumScsiController = maximumScsiController;
            this.minimumIDE = minimumIDE;
            this.maximumIDE = maximumIDE;
            this.minimumSATA = minimumSATA;
            this.maximumSATA = maximumSATA;
            this.minimumSCSI = minimumSCSI;
            this.maximumSCSI = maximumSCSI;
            this.setup = setup;
            this.minimumMonthly = minimumMonthly;
            this.maximumMonthly = maximumMonthly;
        }

        public int getPackageDefinition() {
            return packageDefinition;
        }

        public String getName() {
            return name;
        }

        public String getMinimumPower() {
            return minimumPower;
        }

        public String getMaximumPower() {
            return maximumPower;
        }

        public String getMinimumCpu() {
            return minimumCpu;
        }

        public String getMaximumCpu() {
            return maximumCpu;
        }

        public String getMinimumRam() {
            return minimumRam;
        }

        public String getMaximumRam() {
            return maximumRam;
        }

        public String getMinimumSataController() {
            return minimumSataController;
        }

        public String getMaximumSataController() {
            return maximumSataController;
        }

        public String getMinimumScsiController() {
            return minimumScsiController;
        }

        public String getMaximumScsiController() {
            return maximumScsiController;
        }

        public BigDecimal getSetup() {
            return setup;
        }

        public BigDecimal getMinimumMonthly() {
            return minimumMonthly;
        }

        public BigDecimal getMaximumMonthly() {
            return maximumMonthly;
        }

        public String getMinimumIDE() {
            return minimumIDE;
        }

        public String getMaximumIDE() {
            return maximumIDE;
        }

        public String getMinimumSATA() {
            return minimumSATA;
        }

        public String getMaximumSATA() {
            return maximumSATA;
        }

        public String getMinimumSCSI() {
            return minimumSCSI;
        }

        public String getMaximumSCSI() {
            return maximumSCSI;
        }
    }
}
