package com.aoindustries.website.signup;

/*
 * Copyright 2007-2009 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
 * All rights reserved.
 */
import com.aoindustries.aoserv.client.PackageDefinition;
import com.aoindustries.aoserv.client.PackageDefinitionLimit;
import com.aoindustries.sql.SQLUtility;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

/**
 * Keeps track of one possible server configuration.  Also provides a set of static methods that create commonly-used configurations
 * such as maximum and minimum.
 *
 * @author  AO Industries, Inc.
 */
public class ServerConfiguration {

    private static String getDiskDescription(int numDrives, PackageDefinitionLimit pdl) throws SQLException {
        if(pdl==null || numDrives==0) return null;
        String description = pdl.getResource().getDescription();
        if(numDrives==1) {
            if(description.startsWith("2x")) return description;
            else return "Single " + description;
        } else {
            return numDrives + "x" + description;
        }
    }

    public static ServerConfiguration getMinimumConfiguration(PackageDefinition packageDefinition) throws SQLException, IOException {
        List<PackageDefinitionLimit> limits = packageDefinition.getLimits();

        // Calculate the total minimum monthly
        int setup = packageDefinition.getSetupFee();
        if(setup==-1) setup = 0;
        int minimumMonthly = packageDefinition.getMonthlyRate();
        if(minimumMonthly==-1) minimumMonthly = 0;

        // Find the maximum number of different resources and the cheapest options
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

        // Build the Power descriptions
        StringBuilder minimumPower = new StringBuilder();
        if(cheapestPower!=null) {
            if(maxPowers!=1) minimumPower.append(maxPowers).append('x');
            minimumPower.append(cheapestPower.getResource().getDescription());
        }

        // Add the Power costs
        if(cheapestPower!=null) minimumMonthly += cheapestPower.getAdditionalRate()==-1 ? 0 : (cheapestPower.getAdditionalRate() * maxPowers);

        // Build the CPU descriptions
        if(cheapestCPU==null) throw new SQLException("Unable to find cheapestCPU");
        StringBuilder minimumCpu = new StringBuilder();
        if(maxCPUs!=1) minimumCpu.append(maxCPUs).append('x');
        minimumCpu.append(cheapestCPU.getResource().getDescription());

        // Add the CPU costs
        minimumMonthly += cheapestCPU.getAdditionalRate()==-1 ? 0 : (cheapestCPU.getAdditionalRate() * maxCPUs);

        // Build the RAM description
        if(cheapestRAM==null) throw new SQLException("Unable to find cheapestRAM");
        StringBuilder minimumRam = new StringBuilder();
        minimumRam.append(cheapestRAM.getResource().getDescription());

        // Add the RAM cost
        minimumMonthly += cheapestRAM.getAdditionalRate()==-1 ? 0 : cheapestRAM.getAdditionalRate();

        // Build the SATA controller description
        StringBuilder minimumSataController = new StringBuilder();
        if(cheapestSataController!=null) minimumSataController.append(cheapestSataController.getResource().getDescription());

        // Add the SataController cost
        if(cheapestSataController!=null) minimumMonthly += cheapestSataController.getAdditionalRate()==-1 ? 0 : cheapestSataController.getAdditionalRate();

        // Build the SCSI controller description
        StringBuilder minimumScsiController = new StringBuilder();
        if(cheapestScsiController!=null) minimumScsiController.append(cheapestScsiController.getResource().getDescription());

        // Add the ScsiController cost
        if(cheapestScsiController!=null) minimumMonthly += cheapestScsiController.getAdditionalRate()==-1 ? 0 : cheapestScsiController.getAdditionalRate();

        // Build the disk description
        String minimumIDE = getDiskDescription(1, cheapestDisk.getResource().getName().startsWith("hardware_disk_ide_") ? cheapestDisk : null);
        String minimumSATA = getDiskDescription(1, cheapestDisk.getResource().getName().startsWith("hardware_disk_sata_") ? cheapestDisk : null);
        String minimumSCSI = getDiskDescription(1, cheapestDisk.getResource().getName().startsWith("hardware_disk_scsi_") ? cheapestDisk : null);

        // Add the disk cost
        minimumMonthly += cheapestDisk.getAdditionalRate()==-1 ? 0 : cheapestDisk.getAdditionalRate();

        return new ServerConfiguration(
            packageDefinition.getPkey(),
            packageDefinition.getDisplay(),
            minimumPower.toString(),
            minimumCpu.toString().replaceAll(", ", "<br>&nbsp;&nbsp;&nbsp;&nbsp;"),
            minimumRam.toString(),
            minimumSataController.toString(),
            minimumScsiController.toString(),
            minimumIDE,
            minimumSATA,
            minimumSCSI,
            setup==0 ? null : new BigDecimal(SQLUtility.getDecimal(setup)),
            minimumMonthly==0 ? null : new BigDecimal(SQLUtility.getDecimal(minimumMonthly))
        );
    }
    
    public static ServerConfiguration getMaximumConfiguration(PackageDefinition packageDefinition) throws SQLException, IOException {
        List<PackageDefinitionLimit> limits = packageDefinition.getLimits();

        // Calculate the total maximum monthly
        int setup = packageDefinition.getSetupFee();
        if(setup==-1) setup = 0;
        int maximumMonthly = packageDefinition.getMonthlyRate();
        if(maximumMonthly==-1) maximumMonthly = 0;

        // Find the maximum number of different resources and the most expensive options
        int maxPowers = 0;
        PackageDefinitionLimit expensivePower = null;
        int maxCPUs = 0;
        PackageDefinitionLimit expensiveCPU = null;
        int maxRAMs = 0;
        PackageDefinitionLimit expensiveRAM = null;
        int maxSataControllers = 0;
        PackageDefinitionLimit expensiveSataController = null;
        int maxScsiControllers = 0;
        PackageDefinitionLimit expensiveScsiController = null;
        int maxIDEs = 0;
        PackageDefinitionLimit expensiveIDE = null;
        int maxSATAs = 0;
        PackageDefinitionLimit expensiveSATA = null;
        int maxSCSIs = 0;
        PackageDefinitionLimit expensiveSCSI = null;
        for(PackageDefinitionLimit limit : limits) {
            String resourceName = limit.getResource().getName();
            if(resourceName.startsWith("hardware_power_")) {
                int limitPower = limit.getHardLimit();
                if(limitPower>0) {
                    if(limitPower>maxPowers) maxPowers = limitPower;
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
        StringBuilder maximumPower = new StringBuilder();
        if(expensivePower!=null) {
            if(maxPowers!=1) maximumPower.append(maxPowers).append('x');
            maximumPower.append(expensivePower.getResource().getDescription());
        }

        // Add the Power costs
        if(expensivePower!=null) maximumMonthly += expensivePower.getAdditionalRate()==-1 ? 0 : (expensivePower.getAdditionalRate() * maxPowers);

        // Build the CPU descriptions
        if(expensiveCPU==null) throw new SQLException("Unable to find expensiveCPU");
        StringBuilder maximumCpu = new StringBuilder();
        if(maxCPUs!=1) maximumCpu.append(maxCPUs).append('x');
        maximumCpu.append(expensiveCPU.getResource().getDescription());

        // Add the CPU costs
        maximumMonthly += expensiveCPU.getAdditionalRate()==-1 ? 0 : (expensiveCPU.getAdditionalRate() * maxCPUs);

        // Build the RAM description
        if(expensiveRAM==null) throw new SQLException("Unable to find expensiveRAM");
        StringBuilder maximumRam = new StringBuilder();
        if(maxRAMs>1) maximumRam.append(maxRAMs).append('x');
        maximumRam.append(expensiveRAM.getResource().getDescription());

        // Add the RAM cost
        maximumMonthly += expensiveRAM.getAdditionalRate()==-1 ? 0 : (expensiveRAM.getAdditionalRate() * maxRAMs);

        // Build the SATA controller description
        StringBuilder maximumSataController = new StringBuilder();
        if(expensiveSataController!=null) {
            if(maxSataControllers>1) maximumSataController.append(maxSataControllers).append('x');
            maximumSataController.append(expensiveSataController.getResource().getDescription());
        }

        // Add the SataController cost
        if(expensiveSataController!=null) maximumMonthly += expensiveSataController.getAdditionalRate()==-1 ? 0 : (expensiveSataController.getAdditionalRate() * maxSataControllers);

        // Build the SCSI controller description
        StringBuilder maximumScsiController = new StringBuilder();
        if(expensiveScsiController!=null) {
            if(maxScsiControllers>1) maximumScsiController.append(maxScsiControllers).append('x');
            maximumScsiController.append(expensiveScsiController.getResource().getDescription());
        }

        // Add the ScsiController cost
        if(expensiveScsiController!=null) maximumMonthly += expensiveScsiController.getAdditionalRate()==-1 ? 0 : (expensiveScsiController.getAdditionalRate() * maxScsiControllers);

        // Build the disk description
        String maximumIDE = getDiskDescription(maxIDEs, expensiveIDE);
        String maximumSATA = getDiskDescription(maxSATAs, expensiveSATA);
        String maximumSCSI = getDiskDescription(maxSCSIs, expensiveSCSI);

        // Add the disk cost
        if(expensiveIDE!=null) maximumMonthly += expensiveIDE.getAdditionalRate()==-1 ? 0 : (expensiveIDE.getAdditionalRate() * maxIDEs);
        if(expensiveSATA!=null) maximumMonthly += expensiveSATA.getAdditionalRate()==-1 ? 0 : (expensiveSATA.getAdditionalRate() * maxSATAs);
        if(expensiveSCSI!=null) maximumMonthly += expensiveSCSI.getAdditionalRate()==-1 ? 0 : (expensiveSCSI.getAdditionalRate() * maxSCSIs);

        return new ServerConfiguration(
            packageDefinition.getPkey(),
            packageDefinition.getDisplay(),
            maximumPower.toString(),
            maximumCpu.toString().replaceAll(", ", "<br>&nbsp;&nbsp;&nbsp;&nbsp;"),
            maximumRam.toString(),
            maximumSataController.toString(),
            maximumScsiController.toString(),
            maximumIDE,
            maximumSATA,
            maximumSCSI,
            setup==0 ? null : new BigDecimal(SQLUtility.getDecimal(setup)),
            maximumMonthly==0 ? null : new BigDecimal(SQLUtility.getDecimal(maximumMonthly))
        );
    }

    final private int packageDefinition;
    final private String name;
    final private String power;
    final private String cpu;
    final private String ram;
    final private String sataController;
    final private String scsiController;
    final private String ide;
    final private String sata;
    final private String scsi;
    final private BigDecimal setup;
    final private BigDecimal monthly;

    public ServerConfiguration(
        int packageDefinition,
        String name,
        String power,
        String cpu,
        String ram,
        String sataController,
        String scsiController,
        String ide,
        String sata,
        String scsi,
        BigDecimal setup,
        BigDecimal monthly
    ) {
        this.packageDefinition = packageDefinition;
        this.name = name;
        this.power = power;
        this.cpu = cpu;
        this.ram = ram;
        this.sataController = sataController;
        this.scsiController = scsiController;
        this.ide = ide;
        this.sata = sata;
        this.scsi = scsi;
        this.setup = setup;
        this.monthly = monthly;
    }

    public int getPackageDefinition() {
        return packageDefinition;
    }

    public String getName() {
        return name;
    }

    public String getPower() {
        return power;
    }

    public String getCpu() {
        return cpu;
    }

    public String getRam() {
        return ram;
    }

    public String getSataController() {
        return sataController;
    }

    public String getScsiController() {
        return scsiController;
    }

    public BigDecimal getSetup() {
        return setup;
    }

    public BigDecimal getMonthly() {
        return monthly;
    }

    public String getIde() {
        return ide;
    }

    public String getSata() {
        return sata;
    }

    public String getScsi() {
        return scsi;
    }
}
