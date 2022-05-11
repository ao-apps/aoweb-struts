/*
 * aoweb-struts - Template webapp for legacy Struts-based site framework with AOServ Platform control panels.
 * Copyright (C) 2007-2009, 2016, 2018, 2019, 2020, 2021, 2022  AO Industries, Inc.
 *     support@aoindustries.com
 *     7262 Bull Pen Cir
 *     Mobile, AL 36695
 *
 * This file is part of aoweb-struts.
 *
 * aoweb-struts is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * aoweb-struts is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with aoweb-struts.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.aoindustries.web.struts.signup;

import com.aoapps.lang.i18n.Money;
import com.aoapps.lang.i18n.Monies;
import com.aoindustries.aoserv.client.billing.PackageDefinition;
import com.aoindustries.aoserv.client.billing.PackageDefinitionLimit;
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

  private static String getDiskDescription(int numDrives, PackageDefinitionLimit pdl) throws SQLException, IOException {
    if (pdl == null || numDrives == 0) {
      return null;
    }
    String description = pdl.getResource().toString();
    if (numDrives == 1) {
      if (description.startsWith("2x")) {
        return description;
      } else {
        return "Single " + description;
      }
    } else {
      return numDrives + "x" + description;
    }
  }

  public static ServerConfiguration getMinimumConfiguration(PackageDefinition packageDefinition) throws SQLException, IOException {
    final List<PackageDefinitionLimit> limits = packageDefinition.getLimits();

    // Calculate the total minimum monthly
    final Money setup = packageDefinition.getSetupFee();
    Monies minimumMonthly = Monies.of(packageDefinition.getMonthlyRate());

    // Find the maximum number of different resources and the cheapest options
    int maxPowers = 0;
    PackageDefinitionLimit cheapestPower = null;
    int maxCpus = 0;
    PackageDefinitionLimit cheapestCpu = null;
    int maxRams = 0;
    PackageDefinitionLimit cheapestRam = null;
    int maxSataControllers = 0;
    PackageDefinitionLimit cheapestSataController = null;
    int maxScsiControllers = 0;
    PackageDefinitionLimit cheapestScsiController = null;
    int maxDisks = 0;
    PackageDefinitionLimit cheapestDisk = null;
    for (PackageDefinitionLimit limit : limits) {
      String resourceName = limit.getResource().getName();
      if (resourceName.startsWith("hardware_power_")) {
        int limitPower = limit.getHardLimit();
        if (limitPower > 0) {
          if (limitPower > maxPowers) {
            maxPowers = limitPower;
          }
          if (cheapestPower == null) {
            cheapestPower = limit;
          } else {
            Monies additionalRate = Monies.of(limit.getAdditionalRate());
            Monies cheapestRate = Monies.of(cheapestPower.getAdditionalRate());
            if (additionalRate.compareTo(cheapestRate) < 0) {
              cheapestPower = limit;
            }
          }
        }
      } else if (resourceName.startsWith("hardware_processor_")) {
        int limitCpu = limit.getHardLimit();
        if (limitCpu > 0) {
          if (limitCpu > maxCpus) {
            maxCpus = limitCpu;
          }
          if (cheapestCpu == null) {
            cheapestCpu = limit;
          } else {
            Monies additionalRate = Monies.of(limit.getAdditionalRate());
            Monies cheapestRate = Monies.of(cheapestCpu.getAdditionalRate());
            if (additionalRate.compareTo(cheapestRate) < 0) {
              cheapestCpu = limit;
            }
          }
        }
      } else if (resourceName.startsWith("hardware_ram_")) {
        int limitRam = limit.getHardLimit();
        if (limitRam > 0) {
          if (limitRam > maxRams) {
            maxRams = limitRam;
          }
          if (cheapestRam == null) {
            cheapestRam = limit;
          } else {
            Monies additionalRate = Monies.of(limit.getAdditionalRate());
            Monies cheapestRate = Monies.of(cheapestRam.getAdditionalRate());
            if (additionalRate.compareTo(cheapestRate) < 0) {
              cheapestRam = limit;
            }
          }
        }
      } else if (resourceName.startsWith("hardware_disk_controller_sata_")) {
        int limitSataController = limit.getHardLimit();
        if (limitSataController > 0) {
          if (limitSataController > maxSataControllers) {
            maxSataControllers = limitSataController;
          }
          if (cheapestSataController == null) {
            cheapestSataController = limit;
          } else {
            Monies additionalRate = Monies.of(limit.getAdditionalRate());
            Monies cheapestRate = Monies.of(cheapestSataController.getAdditionalRate());
            if (additionalRate.compareTo(cheapestRate) < 0) {
              cheapestSataController = limit;
            }
          }
        }
      } else if (resourceName.startsWith("hardware_disk_controller_scsi_")) {
        int limitScsiController = limit.getHardLimit();
        if (limitScsiController > 0) {
          if (limitScsiController > maxScsiControllers) {
            maxScsiControllers = limitScsiController;
          }
          if (cheapestScsiController == null) {
            cheapestScsiController = limit;
          } else {
            Monies additionalRate = Monies.of(limit.getAdditionalRate());
            Monies cheapestRate = Monies.of(cheapestScsiController.getAdditionalRate());
            if (additionalRate.compareTo(cheapestRate) < 0) {
              cheapestScsiController = limit;
            }
          }
        }
      } else if (resourceName.startsWith("hardware_disk_")) {
        int hardLimit = limit.getHardLimit();
        if (hardLimit > 0) {
          if (cheapestDisk == null) {
            cheapestDisk = limit;
          } else {
            Monies additionalRate = Monies.of(limit.getAdditionalRate());
            Monies cheapestRate = Monies.of(cheapestDisk.getAdditionalRate());
            if (additionalRate.compareTo(cheapestRate) < 0) {
              cheapestDisk = limit;
            }
          }
          if (hardLimit > maxDisks) {
            maxDisks = hardLimit;
          }
        }
      }
    }
    if (cheapestCpu == null) {
      throw new SQLException("Unable to find cheapestCpu");
    }
    if (cheapestRam == null) {
      throw new SQLException("Unable to find cheapestRam");
    }
    if (cheapestDisk == null) {
      throw new SQLException("Unable to find cheapestDisk");
    }

    // Build the Power descriptions
    StringBuilder minimumPower = new StringBuilder();
    if (cheapestPower != null) {
      if (maxPowers != 1) {
        minimumPower.append(maxPowers).append('×');
      }
      minimumPower.append(cheapestPower.getResource().toString());
    }

    // Add the Power costs
    if (cheapestPower != null && cheapestPower.getAdditionalRate() != null) {
      minimumMonthly = minimumMonthly.add(cheapestPower.getAdditionalRate().multiply(BigDecimal.valueOf(maxPowers)));
    }

    // Build the CPU descriptions
    if (cheapestCpu == null) {
      throw new SQLException("Unable to find cheapestCpu");
    }
    StringBuilder minimumCpu = new StringBuilder();
    if (maxCpus != 1) {
      minimumCpu.append(maxCpus).append('×');
    }
    minimumCpu.append(cheapestCpu.getResource().toString());

    // Add the CPU costs
    if (cheapestCpu.getAdditionalRate() != null) {
      minimumMonthly = minimumMonthly.add(cheapestCpu.getAdditionalRate().multiply(BigDecimal.valueOf(maxCpus)));
    }

    // Build the RAM description
    if (cheapestRam == null) {
      throw new SQLException("Unable to find cheapestRam");
    }
    StringBuilder minimumRam = new StringBuilder();
    minimumRam.append(cheapestRam.getResource().toString());

    // Add the RAM cost
    minimumMonthly = minimumMonthly.add(cheapestRam.getAdditionalRate());

    // Build the SATA controller description
    StringBuilder minimumSataController = new StringBuilder();
    if (cheapestSataController != null) {
      minimumSataController.append(cheapestSataController.getResource().toString());
    }

    // Add the SataController cost
    if (cheapestSataController != null) {
      minimumMonthly = minimumMonthly.add(cheapestSataController.getAdditionalRate());
    }

    // Build the SCSI controller description
    StringBuilder minimumScsiController = new StringBuilder();
    if (cheapestScsiController != null) {
      minimumScsiController.append(cheapestScsiController.getResource().toString());
    }

    // Add the ScsiController cost
    if (cheapestScsiController != null) {
      minimumMonthly = minimumMonthly.add(cheapestScsiController.getAdditionalRate());
    }

    // Build the disk description
    String minimumDisk = getDiskDescription(1, cheapestDisk);

    // Add the disk cost
    minimumMonthly = minimumMonthly.add(cheapestDisk.getAdditionalRate());

    return new ServerConfiguration(
        packageDefinition.getPkey(),
        packageDefinition.getDisplay(),
        minimumPower.toString(),
        minimumCpu.toString(),
        minimumRam.toString(),
        minimumSataController.toString(),
        minimumScsiController.toString(),
        minimumDisk,
        setup,
        minimumMonthly.isZero() ? null : minimumMonthly
    );
  }

  public static ServerConfiguration getMaximumConfiguration(PackageDefinition packageDefinition) throws SQLException, IOException {
    final List<PackageDefinitionLimit> limits = packageDefinition.getLimits();

    // Calculate the total maximum monthly
    final Money setup = packageDefinition.getSetupFee();
    Monies maximumMonthly = Monies.of(packageDefinition.getMonthlyRate());

    // Find the maximum number of different resources and the most expensive options
    int maxPowers = 0;
    PackageDefinitionLimit expensivePower = null;
    int maxCpus = 0;
    PackageDefinitionLimit expensiveCpu = null;
    int maxRams = 0;
    PackageDefinitionLimit expensiveRam = null;
    int maxSataControllers = 0;
    PackageDefinitionLimit expensiveSataController = null;
    int maxScsiControllers = 0;
    PackageDefinitionLimit expensiveScsiController = null;
    int maxDisks = 0;
    PackageDefinitionLimit expensiveDisk = null;
    for (PackageDefinitionLimit limit : limits) {
      String resourceName = limit.getResource().getName();
      if (resourceName.startsWith("hardware_power_")) {
        int limitPower = limit.getHardLimit();
        if (limitPower > 0) {
          if (limitPower > maxPowers) {
            maxPowers = limitPower;
          }
          if (expensivePower == null) {
            expensivePower = limit;
          } else {
            Monies additionalRate = Monies.of(limit.getAdditionalRate());
            Monies expensiveRate = Monies.of(expensivePower.getAdditionalRate());
            if (additionalRate.compareTo(expensiveRate) > 0) {
              expensivePower = limit;
            }
          }
        }
      } else if (resourceName.startsWith("hardware_processor_")) {
        int limitCpu = limit.getHardLimit();
        if (limitCpu > 0) {
          if (limitCpu > maxCpus) {
            maxCpus = limitCpu;
          }
          if (expensiveCpu == null) {
            expensiveCpu = limit;
          } else {
            Monies additionalRate = Monies.of(limit.getAdditionalRate());
            Monies expensiveRate = Monies.of(expensiveCpu.getAdditionalRate());
            if (additionalRate.compareTo(expensiveRate) > 0) {
              expensiveCpu = limit;
            }
          }
        }
      } else if (resourceName.startsWith("hardware_ram_")) {
        int limitRam = limit.getHardLimit();
        if (limitRam > 0) {
          if (limitRam > maxRams) {
            maxRams = limitRam;
          }
          if (expensiveRam == null) {
            expensiveRam = limit;
          } else {
            Monies additionalRate = Monies.of(limit.getAdditionalRate());
            Monies expensiveRate = Monies.of(expensiveRam.getAdditionalRate());
            if (additionalRate.compareTo(expensiveRate) > 0) {
              expensiveRam = limit;
            }
          }
        }
      } else if (resourceName.startsWith("hardware_disk_controller_sata_")) {
        int limitSataController = limit.getHardLimit();
        if (limitSataController > 0) {
          if (limitSataController > maxSataControllers) {
            maxSataControllers = limitSataController;
          }
          if (expensiveSataController == null) {
            expensiveSataController = limit;
          } else {
            Monies additionalRate = Monies.of(limit.getAdditionalRate());
            Monies expensiveRate = Monies.of(expensiveSataController.getAdditionalRate());
            if (additionalRate.compareTo(expensiveRate) > 0) {
              expensiveSataController = limit;
            }
          }
        }
      } else if (resourceName.startsWith("hardware_disk_controller_scsi_")) {
        int limitScsiController = limit.getHardLimit();
        if (limitScsiController > 0) {
          if (limitScsiController > maxScsiControllers) {
            maxScsiControllers = limitScsiController;
          }
          if (expensiveScsiController == null) {
            expensiveScsiController = limit;
          } else {
            Monies additionalRate = Monies.of(limit.getAdditionalRate());
            Monies expensiveRate = Monies.of(expensiveScsiController.getAdditionalRate());
            if (additionalRate.compareTo(expensiveRate) > 0) {
              expensiveScsiController = limit;
            }
          }
        }
      } else if (resourceName.startsWith("hardware_disk_")) {
        int limitDisk = limit.getHardLimit();
        if (limitDisk > 0) {
          if (limitDisk > maxDisks) {
            maxDisks = limitDisk;
          }
          if (expensiveDisk == null) {
            expensiveDisk = limit;
          } else {
            Monies additionalRate = Monies.of(limit.getAdditionalRate());
            Monies expensiveRate = Monies.of(expensiveDisk.getAdditionalRate());
            if (additionalRate.compareTo(expensiveRate) > 0) {
              expensiveDisk = limit;
            }
          }
        }
      }
    }

    // Build the Power descriptions
    StringBuilder maximumPower = new StringBuilder();
    if (expensivePower != null) {
      if (maxPowers != 1) {
        maximumPower.append(maxPowers).append('×');
      }
      maximumPower.append(expensivePower.getResource().toString());
    }

    // Add the Power costs
    if (expensivePower != null && expensivePower.getAdditionalRate() != null) {
      maximumMonthly = maximumMonthly.add(expensivePower.getAdditionalRate().multiply(BigDecimal.valueOf(maxPowers)));
    }

    // Build the CPU descriptions
    if (expensiveCpu == null) {
      throw new SQLException("Unable to find expensiveCpu");
    }
    StringBuilder maximumCpu = new StringBuilder();
    if (maxCpus != 1) {
      maximumCpu.append(maxCpus).append('×');
    }
    maximumCpu.append(expensiveCpu.getResource().toString());

    // Add the CPU costs
    if (expensiveCpu.getAdditionalRate() != null) {
      maximumMonthly = maximumMonthly.add(expensiveCpu.getAdditionalRate().multiply(BigDecimal.valueOf(maxCpus)));
    }

    // Build the RAM description
    if (expensiveRam == null) {
      throw new SQLException("Unable to find expensiveRam");
    }
    StringBuilder maximumRam = new StringBuilder();
    if (maxRams > 1) {
      maximumRam.append(maxRams).append('×');
    }
    maximumRam.append(expensiveRam.getResource().toString());

    // Add the RAM cost
    if (expensiveRam.getAdditionalRate() != null) {
      maximumMonthly = maximumMonthly.add(expensiveRam.getAdditionalRate().multiply(BigDecimal.valueOf(maxRams)));
    }

    // Build the SATA controller description
    StringBuilder maximumSataController = new StringBuilder();
    if (expensiveSataController != null) {
      if (maxSataControllers > 1) {
        maximumSataController.append(maxSataControllers).append('×');
      }
      maximumSataController.append(expensiveSataController.getResource().toString());
    }

    // Add the SataController cost
    if (expensiveSataController != null && expensiveSataController.getAdditionalRate() != null) {
      maximumMonthly = maximumMonthly.add(expensiveSataController.getAdditionalRate().multiply(BigDecimal.valueOf(maxSataControllers)));
    }

    // Build the SCSI controller description
    StringBuilder maximumScsiController = new StringBuilder();
    if (expensiveScsiController != null) {
      if (maxScsiControllers > 1) {
        maximumScsiController.append(maxScsiControllers).append('×');
      }
      maximumScsiController.append(expensiveScsiController.getResource().toString());
    }

    // Add the ScsiController cost
    if (expensiveScsiController != null && expensiveScsiController.getAdditionalRate() != null) {
      maximumMonthly = maximumMonthly.add(expensiveScsiController.getAdditionalRate().multiply(BigDecimal.valueOf(maxScsiControllers)));
    }

    // Build the disk description
    String maximumDisk = getDiskDescription(maxDisks, expensiveDisk);

    // Add the disk cost
    if (expensiveDisk != null && expensiveDisk.getAdditionalRate() != null) {
      maximumMonthly = maximumMonthly.add(expensiveDisk.getAdditionalRate().multiply(BigDecimal.valueOf(maxDisks)));
    }

    return new ServerConfiguration(
        packageDefinition.getPkey(),
        packageDefinition.getDisplay(),
        maximumPower.toString(),
        maximumCpu.toString(),
        maximumRam.toString(),
        maximumSataController.toString(),
        maximumScsiController.toString(),
        maximumDisk,
        setup,
        maximumMonthly.isZero() ? null : maximumMonthly
    );
  }

  private final int packageDefinition;
  private final String name;
  private final String power;
  private final String cpu;
  private final String ram;
  private final String sataController;
  private final String scsiController;
  private final String disk;
  private final Money setup;
  private final Monies monthly;

  public ServerConfiguration(
      int packageDefinition,
      String name,
      String power,
      String cpu,
      String ram,
      String sataController,
      String scsiController,
      String disk,
      Money setup,
      Monies monthly
  ) {
    this.packageDefinition = packageDefinition;
    this.name = name;
    this.power = power;
    this.cpu = cpu;
    this.ram = ram;
    this.sataController = sataController;
    this.scsiController = scsiController;
    this.disk = disk;
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

  public Money getSetup() {
    return setup;
  }

  public Monies getMonthly() {
    return monthly;
  }

  public String getDisk() {
    return disk;
  }
}
