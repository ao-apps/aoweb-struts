package com.aoindustries.website.signup;

/*
 * Copyright 2007-2009 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
 * All rights reserved.
 */
import com.aoindustries.aoserv.client.PackageDefinition;
import com.aoindustries.aoserv.client.PackageDefinitionLimit;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;

/**
 * Keeps track of one possible server configuration.  Also provides a set of static methods that create commonly-used configurations
 * such as maximum and minimum.
 *
 * @author  AO Industries, Inc.
 */
public class ServerConfiguration {

    private static String getDiskDescription(int numDrives, PackageDefinitionLimit pdl, Locale userLocale) throws SQLException, IOException {
        if(pdl==null || numDrives==0) return null;
        String description = pdl.getResource().toString(userLocale);
        if(numDrives==1) {
            if(description.startsWith("2x")) return description;
            else return "Single " + description;
        } else {
            return numDrives + "x" + description;
        }
    }

    public static ServerConfiguration getMinimumConfiguration(PackageDefinition packageDefinition, Locale userLocale) throws SQLException, IOException {
        List<PackageDefinitionLimit> limits = packageDefinition.getLimits();

        // Calculate the total minimum monthly
        BigDecimal setup = packageDefinition.getSetupFee();
        BigDecimal minimumMonthly = packageDefinition.getMonthlyRate();
        if(minimumMonthly==null) minimumMonthly = BigDecimal.valueOf(0, 2);

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
                        BigDecimal additionalRate = limit.getAdditionalRate();
                        if(additionalRate==null) additionalRate=BigDecimal.valueOf(0, 2);
                        BigDecimal cheapestRate = cheapestPower.getAdditionalRate();
                        if(cheapestRate==null) cheapestRate=BigDecimal.valueOf(0, 2);
                        if(additionalRate.compareTo(cheapestRate)<0) cheapestPower = limit;
                    }
                }
            } else if(resourceName.startsWith("hardware_processor_")) {
                int limitCpu = limit.getHardLimit();
                if(limitCpu>0) {
                    if(limitCpu>maxCPUs) maxCPUs = limitCpu;
                    if(cheapestCPU==null) cheapestCPU = limit;
                    else {
                        BigDecimal additionalRate = limit.getAdditionalRate();
                        if(additionalRate==null) additionalRate=BigDecimal.valueOf(0, 2);
                        BigDecimal cheapestRate = cheapestCPU.getAdditionalRate();
                        if(cheapestRate==null) cheapestRate=BigDecimal.valueOf(0, 2);
                        if(additionalRate.compareTo(cheapestRate)<0) cheapestCPU = limit;
                    }
                }
            } else if(resourceName.startsWith("hardware_ram_")) {
                int limitRAM = limit.getHardLimit();
                if(limitRAM>0) {
                    if(limitRAM>maxRAMs) maxRAMs = limitRAM;
                    if(cheapestRAM==null) cheapestRAM = limit;
                    else {
                        BigDecimal additionalRate = limit.getAdditionalRate();
                        if(additionalRate==null) additionalRate=BigDecimal.valueOf(0, 2);
                        BigDecimal cheapestRate = cheapestRAM.getAdditionalRate();
                        if(cheapestRate==null) cheapestRate=BigDecimal.valueOf(0, 2);
                        if(additionalRate.compareTo(cheapestRate)<0) cheapestRAM = limit;
                    }
                }
            } else if(resourceName.startsWith("hardware_disk_controller_sata_")) {
                int limitSataController = limit.getHardLimit();
                if(limitSataController>0) {
                    if(limitSataController>maxSataControllers) maxSataControllers = limitSataController;
                    if(cheapestSataController==null) cheapestSataController = limit;
                    else {
                        BigDecimal additionalRate = limit.getAdditionalRate();
                        if(additionalRate==null) additionalRate=BigDecimal.valueOf(0, 2);
                        BigDecimal cheapestRate = cheapestSataController.getAdditionalRate();
                        if(cheapestRate==null) cheapestRate=BigDecimal.valueOf(0, 2);
                        if(additionalRate.compareTo(cheapestRate)<0) cheapestSataController = limit;
                    }
                }
            } else if(resourceName.startsWith("hardware_disk_controller_scsi_")) {
                int limitScsiController = limit.getHardLimit();
                if(limitScsiController>0) {
                    if(limitScsiController>maxScsiControllers) maxScsiControllers = limitScsiController;
                    if(cheapestScsiController==null) cheapestScsiController = limit;
                    else {
                        BigDecimal additionalRate = limit.getAdditionalRate();
                        if(additionalRate==null) additionalRate=BigDecimal.valueOf(0, 2);
                        BigDecimal cheapestRate = cheapestScsiController.getAdditionalRate();
                        if(cheapestRate==null) cheapestRate=BigDecimal.valueOf(0, 2);
                        if(additionalRate.compareTo(cheapestRate)<0) cheapestScsiController = limit;
                    }
                }
            } else if(resourceName.startsWith("hardware_disk_")) {
                int hardLimit = limit.getHardLimit();
                if(hardLimit>0) {
                    if(cheapestDisk==null) cheapestDisk = limit;
                    else {
                        BigDecimal additionalRate = limit.getAdditionalRate();
                        if(additionalRate==null) additionalRate=BigDecimal.valueOf(0, 2);
                        BigDecimal cheapestRate = cheapestDisk.getAdditionalRate();
                        if(cheapestRate==null) cheapestRate=BigDecimal.valueOf(0, 2);
                        if(additionalRate.compareTo(cheapestRate)<0) cheapestDisk = limit;
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
            minimumPower.append(cheapestPower.getResource().toString(userLocale));
        }

        // Add the Power costs
        if(cheapestPower!=null && cheapestPower.getAdditionalRate()!=null) minimumMonthly = minimumMonthly.add(cheapestPower.getAdditionalRate().multiply(BigDecimal.valueOf(maxPowers)));

        // Build the CPU descriptions
        if(cheapestCPU==null) throw new SQLException("Unable to find cheapestCPU");
        StringBuilder minimumCpu = new StringBuilder();
        if(maxCPUs!=1) minimumCpu.append(maxCPUs).append('x');
        minimumCpu.append(cheapestCPU.getResource().toString(userLocale));

        // Add the CPU costs
        if(cheapestCPU.getAdditionalRate()!=null) minimumMonthly = minimumMonthly.add(cheapestCPU.getAdditionalRate().multiply(BigDecimal.valueOf(maxCPUs)));

        // Build the RAM description
        if(cheapestRAM==null) throw new SQLException("Unable to find cheapestRAM");
        StringBuilder minimumRam = new StringBuilder();
        minimumRam.append(cheapestRAM.getResource().toString(userLocale));

        // Add the RAM cost
        if(cheapestRAM.getAdditionalRate()!=null) minimumMonthly = minimumMonthly.add(cheapestRAM.getAdditionalRate());

        // Build the SATA controller description
        StringBuilder minimumSataController = new StringBuilder();
        if(cheapestSataController!=null) minimumSataController.append(cheapestSataController.getResource().toString(userLocale));

        // Add the SataController cost
        if(cheapestSataController!=null && cheapestSataController.getAdditionalRate()!=null) minimumMonthly = minimumMonthly.add(cheapestSataController.getAdditionalRate());

        // Build the SCSI controller description
        StringBuilder minimumScsiController = new StringBuilder();
        if(cheapestScsiController!=null) minimumScsiController.append(cheapestScsiController.getResource().toString(userLocale));

        // Add the ScsiController cost
        if(cheapestScsiController!=null && cheapestScsiController.getAdditionalRate()!=null) minimumMonthly = minimumMonthly.add(cheapestScsiController.getAdditionalRate());

        // Build the disk description
        String minimumIDE = getDiskDescription(1, cheapestDisk.getResource().getName().startsWith("hardware_disk_ide_") ? cheapestDisk : null, userLocale);
        String minimumSATA = getDiskDescription(1, cheapestDisk.getResource().getName().startsWith("hardware_disk_sata_") ? cheapestDisk : null, userLocale);
        String minimumSCSI = getDiskDescription(1, cheapestDisk.getResource().getName().startsWith("hardware_disk_scsi_") ? cheapestDisk : null, userLocale);

        // Add the disk cost
        if(cheapestDisk.getAdditionalRate()!=null) minimumMonthly = minimumMonthly.add(cheapestDisk.getAdditionalRate());

        return new ServerConfiguration(
            packageDefinition.getPkey(),
            packageDefinition.getDisplay(),
            minimumPower.toString(),
            minimumCpu.toString(), //.replaceAll(", ", "<br />&#160;&#160;&#160;&#160;"),
            minimumRam.toString(),
            minimumSataController.toString(),
            minimumScsiController.toString(),
            minimumIDE,
            minimumSATA,
            minimumSCSI,
            setup,
            minimumMonthly.compareTo(BigDecimal.ZERO)==0 ? null : minimumMonthly
        );
    }
    
    public static ServerConfiguration getMaximumConfiguration(PackageDefinition packageDefinition, Locale userLocale) throws SQLException, IOException {
        List<PackageDefinitionLimit> limits = packageDefinition.getLimits();

        // Calculate the total maximum monthly
        BigDecimal setup = packageDefinition.getSetupFee();
        BigDecimal maximumMonthly = packageDefinition.getMonthlyRate();
        if(maximumMonthly==null) maximumMonthly = BigDecimal.valueOf(0, 2);

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
                        BigDecimal additionalRate = limit.getAdditionalRate();
                        if(additionalRate==null) additionalRate=BigDecimal.valueOf(0, 2);
                        BigDecimal expensiveRate = expensivePower.getAdditionalRate();
                        if(expensiveRate==null) expensiveRate=BigDecimal.valueOf(0, 2);
                        if(additionalRate.compareTo(expensiveRate)>0) expensivePower = limit;
                    }
                }
            } else if(resourceName.startsWith("hardware_processor_")) {
                int limitCpu = limit.getHardLimit();
                if(limitCpu>0) {
                    if(limitCpu>maxCPUs) maxCPUs = limitCpu;
                    if(expensiveCPU==null) expensiveCPU = limit;
                    else {
                        BigDecimal additionalRate = limit.getAdditionalRate();
                        if(additionalRate==null) additionalRate=BigDecimal.valueOf(0, 2);
                        BigDecimal expensiveRate = expensiveCPU.getAdditionalRate();
                        if(expensiveRate==null) expensiveRate=BigDecimal.valueOf(0, 2);
                        if(additionalRate.compareTo(expensiveRate)>0) expensiveCPU = limit;
                    }
                }
            } else if(resourceName.startsWith("hardware_ram_")) {
                int limitRAM = limit.getHardLimit();
                if(limitRAM>0) {
                    if(limitRAM>maxRAMs) maxRAMs = limitRAM;
                    if(expensiveRAM==null) expensiveRAM = limit;
                    else {
                        BigDecimal additionalRate = limit.getAdditionalRate();
                        if(additionalRate==null) additionalRate=BigDecimal.valueOf(0, 2);
                        BigDecimal expensiveRate = expensiveRAM.getAdditionalRate();
                        if(expensiveRate==null) expensiveRate=BigDecimal.valueOf(0, 2);
                        if(additionalRate.compareTo(expensiveRate)>0) expensiveRAM = limit;
                    }
                }
            } else if(resourceName.startsWith("hardware_disk_controller_sata_")) {
                int limitSataController = limit.getHardLimit();
                if(limitSataController>0) {
                    if(limitSataController>maxSataControllers) maxSataControllers = limitSataController;
                    if(expensiveSataController==null) expensiveSataController = limit;
                    else {
                        BigDecimal additionalRate = limit.getAdditionalRate();
                        if(additionalRate==null) additionalRate=BigDecimal.valueOf(0, 2);
                        BigDecimal expensiveRate = expensiveSataController.getAdditionalRate();
                        if(expensiveRate==null) expensiveRate=BigDecimal.valueOf(0, 2);
                        if(additionalRate.compareTo(expensiveRate)>0) expensiveSataController = limit;
                    }
                }
            } else if(resourceName.startsWith("hardware_disk_controller_scsi_")) {
                int limitScsiController = limit.getHardLimit();
                if(limitScsiController>0) {
                    if(limitScsiController>maxScsiControllers) maxScsiControllers = limitScsiController;
                    if(expensiveScsiController==null) expensiveScsiController = limit;
                    else {
                        BigDecimal additionalRate = limit.getAdditionalRate();
                        if(additionalRate==null) additionalRate=BigDecimal.valueOf(0, 2);
                        BigDecimal expensiveRate = expensiveScsiController.getAdditionalRate();
                        if(expensiveRate==null) expensiveRate=BigDecimal.valueOf(0, 2);
                        if(additionalRate.compareTo(expensiveRate)>0) expensiveScsiController = limit;
                    }
                }
            } else if(resourceName.startsWith("hardware_disk_ide_")) {
                int limitIDE = limit.getHardLimit();
                if(limitIDE>0) {
                    if(limitIDE>maxIDEs) maxIDEs = limitIDE;
                    if(expensiveIDE==null) expensiveIDE = limit;
                    else {
                        BigDecimal additionalRate = limit.getAdditionalRate();
                        if(additionalRate==null) additionalRate=BigDecimal.valueOf(0, 2);
                        BigDecimal expensiveRate = expensiveIDE.getAdditionalRate();
                        if(expensiveRate==null) expensiveRate=BigDecimal.valueOf(0, 2);
                        if(additionalRate.compareTo(expensiveRate)>0) expensiveIDE = limit;
                    }
                }
            } else if(resourceName.startsWith("hardware_disk_sata_")) {
                int limitSATA = limit.getHardLimit();
                if(limitSATA>0) {
                    if(limitSATA>maxSATAs) maxSATAs = limitSATA;
                    if(expensiveSATA==null) expensiveSATA = limit;
                    else {
                        BigDecimal additionalRate = limit.getAdditionalRate();
                        if(additionalRate==null) additionalRate=BigDecimal.valueOf(0, 2);
                        BigDecimal expensiveRate = expensiveSATA.getAdditionalRate();
                        if(expensiveRate==null) expensiveRate=BigDecimal.valueOf(0, 2);
                        if(additionalRate.compareTo(expensiveRate)>0) expensiveSATA = limit;
                    }
                }
            } else if(resourceName.startsWith("hardware_disk_scsi_")) {
                int limitSCSI = limit.getHardLimit();
                if(limitSCSI>0) {
                    if(limitSCSI>maxSCSIs) maxSCSIs = limitSCSI;
                    if(expensiveSCSI==null) expensiveSCSI = limit;
                    else {
                        BigDecimal additionalRate = limit.getAdditionalRate();
                        if(additionalRate==null) additionalRate=BigDecimal.valueOf(0, 2);
                        BigDecimal expensiveRate = expensiveSCSI.getAdditionalRate();
                        if(expensiveRate==null) expensiveRate=BigDecimal.valueOf(0, 2);
                        if(additionalRate.compareTo(expensiveRate)>0) expensiveSCSI = limit;
                    }
                }
            }
        }

        // Build the Power descriptions
        StringBuilder maximumPower = new StringBuilder();
        if(expensivePower!=null) {
            if(maxPowers!=1) maximumPower.append(maxPowers).append('x');
            maximumPower.append(expensivePower.getResource().toString(userLocale));
        }

        // Add the Power costs
        if(expensivePower!=null && expensivePower.getAdditionalRate()!=null) maximumMonthly = maximumMonthly.add(expensivePower.getAdditionalRate().multiply(BigDecimal.valueOf(maxPowers)));

        // Build the CPU descriptions
        if(expensiveCPU==null) throw new SQLException("Unable to find expensiveCPU");
        StringBuilder maximumCpu = new StringBuilder();
        if(maxCPUs!=1) maximumCpu.append(maxCPUs).append('x');
        maximumCpu.append(expensiveCPU.getResource().toString(userLocale));

        // Add the CPU costs
        if(expensiveCPU.getAdditionalRate()!=null) maximumMonthly = maximumMonthly.add(expensiveCPU.getAdditionalRate().multiply(BigDecimal.valueOf(maxCPUs)));

        // Build the RAM description
        if(expensiveRAM==null) throw new SQLException("Unable to find expensiveRAM");
        StringBuilder maximumRam = new StringBuilder();
        if(maxRAMs>1) maximumRam.append(maxRAMs).append('x');
        maximumRam.append(expensiveRAM.getResource().toString(userLocale));

        // Add the RAM cost
        if(expensiveRAM.getAdditionalRate()!=null) maximumMonthly = maximumMonthly.add(expensiveRAM.getAdditionalRate().multiply(BigDecimal.valueOf(maxRAMs)));

        // Build the SATA controller description
        StringBuilder maximumSataController = new StringBuilder();
        if(expensiveSataController!=null) {
            if(maxSataControllers>1) maximumSataController.append(maxSataControllers).append('x');
            maximumSataController.append(expensiveSataController.getResource().toString(userLocale));
        }

        // Add the SataController cost
        if(expensiveSataController!=null && expensiveSataController.getAdditionalRate()!=null) maximumMonthly = maximumMonthly.add(expensiveSataController.getAdditionalRate().multiply(BigDecimal.valueOf(maxSataControllers)));

        // Build the SCSI controller description
        StringBuilder maximumScsiController = new StringBuilder();
        if(expensiveScsiController!=null) {
            if(maxScsiControllers>1) maximumScsiController.append(maxScsiControllers).append('x');
            maximumScsiController.append(expensiveScsiController.getResource().toString(userLocale));
        }

        // Add the ScsiController cost
        if(expensiveScsiController!=null && expensiveScsiController.getAdditionalRate()!=null) maximumMonthly = maximumMonthly.add(expensiveScsiController.getAdditionalRate().multiply(BigDecimal.valueOf(maxScsiControllers)));

        // Build the disk description
        String maximumIDE = getDiskDescription(maxIDEs, expensiveIDE, userLocale);
        String maximumSATA = getDiskDescription(maxSATAs, expensiveSATA, userLocale);
        String maximumSCSI = getDiskDescription(maxSCSIs, expensiveSCSI, userLocale);

        // Add the disk cost
        if(expensiveIDE!=null && expensiveIDE.getAdditionalRate()!=null) maximumMonthly = maximumMonthly.add(expensiveIDE.getAdditionalRate().multiply(BigDecimal.valueOf(maxIDEs)));
        if(expensiveSATA!=null && expensiveSATA.getAdditionalRate()!=null) maximumMonthly = maximumMonthly.add(expensiveSATA.getAdditionalRate().multiply(BigDecimal.valueOf(maxSATAs)));
        if(expensiveSCSI!=null && expensiveSCSI.getAdditionalRate()!=null) maximumMonthly = maximumMonthly.add(expensiveSCSI.getAdditionalRate().multiply(BigDecimal.valueOf(maxSCSIs)));

        return new ServerConfiguration(
            packageDefinition.getPkey(),
            packageDefinition.getDisplay(),
            maximumPower.toString(),
            maximumCpu.toString(), // .replaceAll(", ", "<br />&#160;&#160;&#160;&#160;"),
            maximumRam.toString(),
            maximumSataController.toString(),
            maximumScsiController.toString(),
            maximumIDE,
            maximumSATA,
            maximumSCSI,
            setup,
            maximumMonthly.compareTo(BigDecimal.ZERO)==0 ? null : maximumMonthly
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
