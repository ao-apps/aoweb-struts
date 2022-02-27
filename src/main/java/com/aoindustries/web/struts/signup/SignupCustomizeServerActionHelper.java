/*
 * aoweb-struts - Template webapp for legacy Struts-based site framework with AOServ Platform control panels.
 * Copyright (C) 2007-2009, 2015, 2016, 2018, 2019, 2020, 2021, 2022  AO Industries, Inc.
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

import com.aoapps.html.Union_TBODY_THEAD_TFOOT;
import com.aoapps.lang.i18n.Money;
import com.aoapps.lang.i18n.Monies;
import com.aoindustries.aoserv.client.AOServConnector;
import com.aoindustries.aoserv.client.billing.PackageDefinition;
import com.aoindustries.aoserv.client.billing.PackageDefinitionLimit;
import com.aoindustries.aoserv.client.billing.Resource;
import com.aoindustries.web.struts.SiteSettings;
import static com.aoindustries.web.struts.signup.Resources.PACKAGE_RESOURCES;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.validator.GenericValidator;

/**
 * Managed2Action and Dedicated2Action both use this to setup the request attributes.  This is implemented
 * here because inheritance is not possible and neither one is logically above the other.
 *
 * @author  AO Industries, Inc.
 */
public final class SignupCustomizeServerActionHelper {

	/** Make no instances. */
	private SignupCustomizeServerActionHelper() {throw new AssertionError();}

	public static void setRequestAttributes(
		ServletContext servletContext,
		HttpServletRequest request,
		HttpServletResponse response,
		SignupSelectPackageForm signupSelectPackageForm,
		SignupCustomizeServerForm signupCustomizeServerForm
	) throws IOException, SQLException {
		AOServConnector rootConn = SiteSettings.getInstance(servletContext).getRootAOServConnector();
		PackageDefinition packageDefinition = rootConn.getBilling().getPackageDefinition().get(signupSelectPackageForm.getPackageDefinition());
		if(packageDefinition == null) throw new SQLException("Unable to find PackageDefinition: " + signupSelectPackageForm.getPackageDefinition());
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
		int maxDisks = 0;
		PackageDefinitionLimit cheapestDisk = null;
		for(PackageDefinitionLimit limit : limits) {
			String resourceName = limit.getResource().getName();
			if(resourceName.startsWith("hardware_power_")) {
				int limitPower = limit.getHardLimit();
				if(limitPower > 0) {
					if(limitPower>maxPowers) maxPowers = limitPower;
					if(cheapestPower == null) cheapestPower = limit;
					else {
						Monies additionalRate = Monies.of(limit.getAdditionalRate());
						Monies cheapestRate = Monies.of(cheapestPower.getAdditionalRate());
						if(additionalRate.compareTo(cheapestRate) < 0) cheapestPower = limit;
					}
				}
			} else if(resourceName.startsWith("hardware_processor_")) {
				int limitCpu = limit.getHardLimit();
				if(limitCpu > 0) {
					if(limitCpu>maxCPUs) maxCPUs = limitCpu;
					if(cheapestCPU == null) cheapestCPU = limit;
					else {
						Monies additionalRate = Monies.of(limit.getAdditionalRate());
						Monies cheapestRate = Monies.of(cheapestCPU.getAdditionalRate());
						if(additionalRate.compareTo(cheapestRate) < 0) cheapestCPU = limit;
					}
				}
			} else if(resourceName.startsWith("hardware_ram_")) {
				int limitRAM = limit.getHardLimit();
				if(limitRAM > 0) {
					if(limitRAM>maxRAMs) maxRAMs = limitRAM;
					if(cheapestRAM == null) cheapestRAM = limit;
					else {
						Monies additionalRate = Monies.of(limit.getAdditionalRate());
						Monies cheapestRate = Monies.of(cheapestRAM.getAdditionalRate());
						if(additionalRate.compareTo(cheapestRate) < 0) cheapestRAM = limit;
					}
				}
			} else if(resourceName.startsWith("hardware_disk_controller_sata_")) {
				int limitSataController = limit.getHardLimit();
				if(limitSataController > 0) {
					if(limitSataController>maxSataControllers) maxSataControllers = limitSataController;
					if(cheapestSataController == null) cheapestSataController = limit;
					else {
						Monies additionalRate = Monies.of(limit.getAdditionalRate());
						Monies cheapestRate = Monies.of(cheapestSataController.getAdditionalRate());
						if(additionalRate.compareTo(cheapestRate) < 0) cheapestSataController = limit;
					}
				}
			} else if(resourceName.startsWith("hardware_disk_controller_scsi_")) {
				int limitScsiController = limit.getHardLimit();
				if(limitScsiController > 0) {
					if(limitScsiController>maxScsiControllers) maxScsiControllers = limitScsiController;
					if(cheapestScsiController == null) cheapestScsiController = limit;
					else {
						Monies additionalRate = Monies.of(limit.getAdditionalRate());
						Monies cheapestRate = Monies.of(cheapestScsiController.getAdditionalRate());
						if(additionalRate.compareTo(cheapestRate) < 0) cheapestScsiController = limit;
					}
				}
			} else if(resourceName.startsWith("hardware_disk_")) {
				int hardLimit = limit.getHardLimit();
				if(hardLimit > 0) {
					if(cheapestDisk == null) cheapestDisk = limit;
					else {
						Monies additionalRate = Monies.of(limit.getAdditionalRate());
						Monies cheapestRate = Monies.of(cheapestDisk.getAdditionalRate());
						if(additionalRate.compareTo(cheapestRate) < 0) cheapestDisk = limit;
					}
					if(hardLimit>maxDisks) maxDisks = hardLimit;
				}
			}
		}
		if(cheapestCPU == null) throw new SQLException("Unable to find cheapestCPU");
		if(cheapestRAM == null) throw new SQLException("Unable to find cheapestRAM");
		if(cheapestDisk == null) throw new SQLException("Unable to find cheapestDisk");

		// Find all the options
		List<Option> powerOptions = new ArrayList<>();
		List<Option> cpuOptions = new ArrayList<>();
		List<Option> ramOptions = new ArrayList<>();
		List<Option> sataControllerOptions = new ArrayList<>();
		List<Option> scsiControllerOptions = new ArrayList<>();
		List<List<Option>> diskOptions = new ArrayList<>();
		for(int c = 0; c < maxDisks; c++) {
			diskOptions.add(new ArrayList<>());
		}
		for(PackageDefinitionLimit limit : limits) {
			Resource resource = limit.getResource();
			String resourceName = resource.getName();
			if(resourceName.startsWith("hardware_power_")) {
				int limitPower = limit.getHardLimit();
				if(limitPower > 0) {
					assert cheapestPower != null;
					Monies additionalRate = Monies.of(limit.getAdditionalRate());
					Monies cheapestRate = Monies.of(cheapestPower.getAdditionalRate());
					String description = maxPowers == 1 ? resource.toString() : (maxPowers+"x"+resource.toString());
					powerOptions.add(new Option(limit.getPkey(), description, additionalRate.subtract(cheapestRate).multiply(BigDecimal.valueOf(maxPowers))));
				}
			} else if(resourceName.startsWith("hardware_processor_")) {
				int limitCpu = limit.getHardLimit();
				if(limitCpu > 0) {
					Monies additionalRate = Monies.of(limit.getAdditionalRate());
					Monies cheapestRate = Monies.of(cheapestCPU.getAdditionalRate());
					String description = maxCPUs == 1 ? resource.toString() : (maxCPUs+"x"+resource.toString());
					cpuOptions.add(new Option(limit.getPkey(), description, additionalRate.subtract(cheapestRate).multiply(BigDecimal.valueOf(maxCPUs))));
				}
			} else if(resourceName.startsWith("hardware_ram_")) {
				int limitRAM = limit.getHardLimit();
				if(limitRAM > 0) {
					Monies additionalRate = Monies.of(limit.getAdditionalRate());
					Monies cheapestRate = Monies.of(cheapestRAM.getAdditionalRate());
					String description = maxRAMs == 1 ? resource.toString() : (maxRAMs+"x"+resource.toString());
					ramOptions.add(new Option(limit.getPkey(), description, additionalRate.subtract(cheapestRate).multiply(BigDecimal.valueOf(maxRAMs))));
				}
			} else if(resourceName.startsWith("hardware_disk_controller_sata_")) {
				int limitSataController = limit.getHardLimit();
				if(limitSataController > 0) {
					assert cheapestSataController != null;
					Monies additionalRate = Monies.of(limit.getAdditionalRate());
					Monies cheapestRate = Monies.of(cheapestSataController.getAdditionalRate());
					String description = maxSataControllers == 1 ? resource.toString() : (maxSataControllers+"x"+resource.toString());
					sataControllerOptions.add(new Option(limit.getPkey(), description, additionalRate.subtract(cheapestRate).multiply(BigDecimal.valueOf(maxSataControllers))));
				}
			} else if(resourceName.startsWith("hardware_disk_controller_scsi_")) {
				int limitScsiController = limit.getHardLimit();
				if(limitScsiController > 0) {
					assert cheapestScsiController != null;
					Monies additionalRate = Monies.of(limit.getAdditionalRate());
					Monies cheapestRate = Monies.of(cheapestScsiController.getAdditionalRate());
					String description = maxScsiControllers == 1 ? resource.toString() : (maxScsiControllers+"x"+resource.toString());
					scsiControllerOptions.add(new Option(limit.getPkey(), description, additionalRate.subtract(cheapestRate).multiply(BigDecimal.valueOf(maxScsiControllers))));
				}
			} else if(resourceName.startsWith("hardware_disk_")) {
				int limitDisk = limit.getHardLimit();
				if(limitDisk > 0) {
					Monies additionalRate = Monies.of(limit.getAdditionalRate());
					Monies adjustedRate = additionalRate;
					// Discount adjusted rate if the cheapest disk is of this type
					if(cheapestDisk.getResource().getName().startsWith("hardware_disk_")) {
						Monies cheapestRate = Monies.of(cheapestDisk.getAdditionalRate());
						adjustedRate = adjustedRate.subtract(cheapestRate);
					}
					for(int c=0;c<maxDisks;c++) {
						List<Option> options = diskOptions.get(c);
						// Add none option
						if(maxDisks>1 && options.isEmpty()) options.add(new Option(-1, "None", c == 0 ? adjustedRate.subtract(additionalRate) : Monies.of()));
						options.add(new Option(limit.getPkey(), resource.toString(), c == 0 ? adjustedRate : additionalRate));
					}
				}
			}
		}

		// Sort by price
		Collections.sort(powerOptions, Option.priceComparator);
		Collections.sort(cpuOptions, Option.priceComparator);
		Collections.sort(ramOptions, Option.priceComparator);
		Collections.sort(sataControllerOptions, Option.priceComparator);
		Collections.sort(scsiControllerOptions, Option.priceComparator);
		for(List<Option> diskOptionList : diskOptions) {
			Collections.sort(diskOptionList, Option.priceComparator);
		}

		// Clear any customization settings that are not part of the current package definition (this happens when they
		// select a different package type)
		if(signupCustomizeServerForm.getPowerOption() != -1) {
			PackageDefinitionLimit pdl = rootConn.getBilling().getPackageDefinitionLimit().get(signupCustomizeServerForm.getPowerOption());
			if(pdl == null || !packageDefinition.equals(pdl.getPackageDefinition())) signupCustomizeServerForm.setPowerOption(-1);
		}
		if(signupCustomizeServerForm.getCpuOption() != -1) {
			PackageDefinitionLimit pdl = rootConn.getBilling().getPackageDefinitionLimit().get(signupCustomizeServerForm.getCpuOption());
			if(pdl == null || !packageDefinition.equals(pdl.getPackageDefinition())) signupCustomizeServerForm.setCpuOption(-1);
		}
		if(signupCustomizeServerForm.getRamOption() != -1) {
			PackageDefinitionLimit pdl = rootConn.getBilling().getPackageDefinitionLimit().get(signupCustomizeServerForm.getRamOption());
			if(pdl == null || !packageDefinition.equals(pdl.getPackageDefinition())) signupCustomizeServerForm.setRamOption(-1);
		}
		if(signupCustomizeServerForm.getSataControllerOption() != -1) {
			PackageDefinitionLimit pdl = rootConn.getBilling().getPackageDefinitionLimit().get(signupCustomizeServerForm.getSataControllerOption());
			if(pdl == null || !packageDefinition.equals(pdl.getPackageDefinition())) signupCustomizeServerForm.setSataControllerOption(-1);
		}
		if(signupCustomizeServerForm.getScsiControllerOption() != -1) {
			PackageDefinitionLimit pdl = rootConn.getBilling().getPackageDefinitionLimit().get(signupCustomizeServerForm.getScsiControllerOption());
			if(pdl == null || !packageDefinition.equals(pdl.getPackageDefinition())) signupCustomizeServerForm.setScsiControllerOption(-1);
		}
		List<String> formDiskOptions = signupCustomizeServerForm.getDiskOptions();
		while(formDiskOptions.size() > maxDisks) {
			formDiskOptions.remove(formDiskOptions.size() - 1);
		}
		for(int c=0;c<formDiskOptions.size();c++) {
			String s = formDiskOptions.get(c);
			if(s != null && s.length() > 0 && !s.equals("-1")) {
				int pkey = Integer.parseInt(s);
				PackageDefinitionLimit pdl = rootConn.getBilling().getPackageDefinitionLimit().get(pkey);
				if(pdl == null || !packageDefinition.equals(pdl.getPackageDefinition())) formDiskOptions.set(c, "-1");
			}
		}

		// Determine if at least one disk is selected
		boolean isAtLeastOneDiskSelected = signupCustomizeServerForm.isAtLeastOneDiskSelected();

		// Default to cheapest if not already selected
		if(cheapestPower != null && signupCustomizeServerForm.getPowerOption() == -1) signupCustomizeServerForm.setPowerOption(cheapestPower.getPkey());
		if(signupCustomizeServerForm.getCpuOption() == -1) signupCustomizeServerForm.setCpuOption(cheapestCPU.getPkey());
		if(signupCustomizeServerForm.getRamOption() == -1) signupCustomizeServerForm.setRamOption(cheapestRAM.getPkey());
		if(cheapestSataController != null && signupCustomizeServerForm.getSataControllerOption() == -1) signupCustomizeServerForm.setSataControllerOption(cheapestSataController.getPkey());
		if(cheapestScsiController != null && signupCustomizeServerForm.getScsiControllerOption() == -1) signupCustomizeServerForm.setScsiControllerOption(cheapestScsiController.getPkey());
		for(int c=0;c<maxDisks;c++) {
			List<Option> options = diskOptions.get(c);
			if(!options.isEmpty()) {
				Option firstOption = options.get(0);
				if(!isAtLeastOneDiskSelected && options.size()>=2 && firstOption.getPriceDifference().compareTo(Monies.of()) < 0) {
					firstOption = options.get(1);
				}
				String defaultSelected = Integer.toString(firstOption.getPackageDefinitionLimit());
				if(formDiskOptions.size()<=c || formDiskOptions.get(c) == null || formDiskOptions.get(c).length() == 0 || formDiskOptions.get(c).equals("-1")) formDiskOptions.set(c, defaultSelected);
			} else {
				formDiskOptions.set(c, "-1");
			}
		}

		// Find the basePrice (base plus minimum number of cheapest of each resource class)
		Monies basePrice = Monies.of(packageDefinition.getMonthlyRate());
		if(cheapestPower != null && cheapestPower.getAdditionalRate() != null) basePrice = basePrice.add(cheapestPower.getAdditionalRate().multiply(BigDecimal.valueOf(maxPowers)));
		if(cheapestCPU.getAdditionalRate() != null) basePrice = basePrice.add(cheapestCPU.getAdditionalRate().multiply(BigDecimal.valueOf(maxCPUs)));
		basePrice = basePrice.add(cheapestRAM.getAdditionalRate());
		if(cheapestSataController != null) basePrice = basePrice.add(cheapestSataController.getAdditionalRate());
		if(cheapestScsiController != null) basePrice = basePrice.add(cheapestScsiController.getAdditionalRate());
		basePrice = basePrice.add(cheapestDisk.getAdditionalRate());

		// Store to request
		request.setAttribute("packageDefinition", packageDefinition);
		request.setAttribute("powerOptions", powerOptions);
		request.setAttribute("cpuOptions", cpuOptions);
		request.setAttribute("ramOptions", ramOptions);
		request.setAttribute("sataControllerOptions", sataControllerOptions);
		request.setAttribute("scsiControllerOptions", scsiControllerOptions);
		request.setAttribute("diskOptions", diskOptions);
		request.setAttribute("basePrice", basePrice);
	}

	/**
	 * Gets the hardware monthly rate for the server, basic server + hardware options
	 */
	public static Monies getHardwareMonthlyRate(AOServConnector rootConn, SignupCustomizeServerForm signupCustomizeServerForm, PackageDefinition packageDefinition) throws IOException, SQLException {
		Monies monthlyRate = Monies.of(packageDefinition.getMonthlyRate());

		// Add the power option
		int powerOption = signupCustomizeServerForm.getPowerOption();
		if(powerOption != -1) {
			PackageDefinitionLimit powerPDL = rootConn.getBilling().getPackageDefinitionLimit().get(powerOption);
			int numPower = powerPDL.getHardLimit();
			Money powerRate = powerPDL.getAdditionalRate();
			if(powerRate != null) monthlyRate = monthlyRate.add(powerRate.multiply(BigDecimal.valueOf(numPower)));
		}

		// Add the cpu option
		PackageDefinitionLimit cpuPDL = rootConn.getBilling().getPackageDefinitionLimit().get(signupCustomizeServerForm.getCpuOption());
		int numCpu = cpuPDL.getHardLimit();
		Money cpuRate = cpuPDL.getAdditionalRate();
		if(cpuRate != null) monthlyRate = monthlyRate.add(cpuRate).multiply(BigDecimal.valueOf(numCpu));

		// Add the RAM option
		PackageDefinitionLimit ramPDL = rootConn.getBilling().getPackageDefinitionLimit().get(signupCustomizeServerForm.getRamOption());
		int numRam = ramPDL.getHardLimit();
		Money ramRate = ramPDL.getAdditionalRate();
		if(ramRate != null) monthlyRate = monthlyRate.add(ramRate.multiply(BigDecimal.valueOf(numRam)));

		// Add the SATA controller option
		int sataControllerOption = signupCustomizeServerForm.getSataControllerOption();
		if(sataControllerOption != -1) {
			PackageDefinitionLimit sataControllerPDL = rootConn.getBilling().getPackageDefinitionLimit().get(sataControllerOption);
			int numSataController = sataControllerPDL.getHardLimit();
			Money sataControllerRate = sataControllerPDL.getAdditionalRate();
			if(sataControllerRate != null) monthlyRate = monthlyRate.add(sataControllerRate.multiply(BigDecimal.valueOf(numSataController)));
		}

		// Add the SCSI controller option
		int scsiControllerOption = signupCustomizeServerForm.getScsiControllerOption();
		if(scsiControllerOption != -1) {
			PackageDefinitionLimit scsiControllerPDL = rootConn.getBilling().getPackageDefinitionLimit().get(scsiControllerOption);
			int numScsiController = scsiControllerPDL.getHardLimit();
			Money scsiControllerRate = scsiControllerPDL.getAdditionalRate();
			if(scsiControllerRate != null) monthlyRate = monthlyRate.add(scsiControllerRate.multiply(BigDecimal.valueOf(numScsiController)));
		}

		// Add the disk options
		for(String pkey : signupCustomizeServerForm.getDiskOptions()) {
			if(pkey != null && pkey.length()>0 && !pkey.equals("-1")) {
				PackageDefinitionLimit diskPDL = rootConn.getBilling().getPackageDefinitionLimit().get(Integer.parseInt(pkey));
				if(diskPDL != null) {
					monthlyRate = monthlyRate.add(diskPDL.getAdditionalRate());
				}
			}
		}

		return monthlyRate;
	}

	public static String getPowerOption(AOServConnector rootConn, SignupCustomizeServerForm signupCustomizeServerForm) throws IOException, SQLException {
		int powerOption = signupCustomizeServerForm.getPowerOption();
		if(powerOption == -1) return null;
		PackageDefinitionLimit powerPDL = rootConn.getBilling().getPackageDefinitionLimit().get(powerOption);
		int numPower = powerPDL.getHardLimit();
		if(numPower == 1) return powerPDL.getResource().toString();
		else return numPower + "x" + powerPDL.getResource().toString();
	}

	public static String getCpuOption(AOServConnector rootConn, SignupCustomizeServerForm signupCustomizeServerForm) throws IOException, SQLException {
		PackageDefinitionLimit cpuPDL = rootConn.getBilling().getPackageDefinitionLimit().get(signupCustomizeServerForm.getCpuOption());
		int numCpu = cpuPDL.getHardLimit();
		if(numCpu == 1) return cpuPDL.getResource().toString();
		else return numCpu + "x" + cpuPDL.getResource().toString();
	}

	public static String getRamOption(AOServConnector rootConn, SignupCustomizeServerForm signupCustomizeServerForm) throws IOException, SQLException {
		PackageDefinitionLimit ramPDL = rootConn.getBilling().getPackageDefinitionLimit().get(signupCustomizeServerForm.getRamOption());
		int numRam = ramPDL.getHardLimit();
		if(numRam == 1) return ramPDL.getResource().toString();
		else return numRam + "x" + ramPDL.getResource().toString();
	}

	public static String getSataControllerOption(AOServConnector rootConn, SignupCustomizeServerForm signupCustomizeServerForm) throws IOException, SQLException {
		int sataControllerOption = signupCustomizeServerForm.getSataControllerOption();
		if(sataControllerOption == -1) return null;
		PackageDefinitionLimit sataControllerPDL = rootConn.getBilling().getPackageDefinitionLimit().get(sataControllerOption);
		int numSataController = sataControllerPDL.getHardLimit();
		if(numSataController == 1) return sataControllerPDL.getResource().toString();
		else return numSataController + "x" + sataControllerPDL.getResource().toString();
	}

	public static String getScsiControllerOption(AOServConnector rootConn, SignupCustomizeServerForm signupCustomizeServerForm) throws IOException, SQLException {
		int scsiControllerOption = signupCustomizeServerForm.getScsiControllerOption();
		if(scsiControllerOption == -1) return null;
		PackageDefinitionLimit scsiControllerPDL = rootConn.getBilling().getPackageDefinitionLimit().get(scsiControllerOption);
		int numScsiController = scsiControllerPDL.getHardLimit();
		if(numScsiController == 1) return scsiControllerPDL.getResource().toString();
		else return numScsiController + "x" + scsiControllerPDL.getResource().toString();
	}

	public static List<String> getDiskOptions(AOServConnector rootConn, SignupCustomizeServerForm signupCustomizeServerForm) throws IOException, SQLException {
		List<String> diskOptions = new ArrayList<>();
		for(String pkey : signupCustomizeServerForm.getDiskOptions()) {
			if(pkey != null && pkey.length()>0 && !pkey.equals("-1")) {
				PackageDefinitionLimit diskPDL = rootConn.getBilling().getPackageDefinitionLimit().get(Integer.parseInt(pkey));
				if(diskPDL != null) diskOptions.add(diskPDL.getResource().toString());
			}
		}
		return diskOptions;
	}

	public static void setConfirmationRequestAttributes(
		ServletContext servletContext,
		HttpServletRequest request,
		HttpServletResponse response,
		SignupSelectPackageForm signupSelectPackageForm,
		SignupCustomizeServerForm signupCustomizeServerForm
	) throws IOException, SQLException {
		// Lookup things needed by the view
		AOServConnector rootConn = SiteSettings.getInstance(servletContext).getRootAOServConnector();
		PackageDefinition packageDefinition = rootConn.getBilling().getPackageDefinition().get(signupSelectPackageForm.getPackageDefinition());

		// Store as request attribute for the view
		request.setAttribute("monthlyRate", getHardwareMonthlyRate(rootConn, signupCustomizeServerForm, packageDefinition));
		request.setAttribute("powerOption", getPowerOption(rootConn, signupCustomizeServerForm));
		request.setAttribute("cpuOption", getCpuOption(rootConn, signupCustomizeServerForm));
		request.setAttribute("ramOption", getRamOption(rootConn, signupCustomizeServerForm));
		request.setAttribute("sataControllerOption", getSataControllerOption(rootConn, signupCustomizeServerForm));
		request.setAttribute("scsiControllerOption", getScsiControllerOption(rootConn, signupCustomizeServerForm));
		request.setAttribute("diskOptions", getDiskOptions(rootConn, signupCustomizeServerForm));
	}

	public static void writeEmailConfirmation(
		HttpServletRequest request,
		Union_TBODY_THEAD_TFOOT<?> tbody,
		AOServConnector rootConn,
		PackageDefinition packageDefinition,
		SignupCustomizeServerForm signupCustomizeServerForm
	) throws IOException, SQLException {
		String powerOption = getPowerOption(rootConn, signupCustomizeServerForm);
		if(!GenericValidator.isBlankOrNull(powerOption)) {
			tbody.tr__(tr -> tr
				.td__(PACKAGE_RESOURCES.getMessage("signup.notRequired"))
				.td__(PACKAGE_RESOURCES.getMessage("signupCustomizeServerConfirmation.power.prompt"))
				.td__(powerOption)
			);
		}
		tbody.tr__(tr -> tr
			.td__(PACKAGE_RESOURCES.getMessage("signup.notRequired"))
			.td__(PACKAGE_RESOURCES.getMessage("signupCustomizeServerConfirmation.cpu.prompt"))
			.td__(getCpuOption(rootConn, signupCustomizeServerForm))
		)
		.tr__(tr -> tr
			.td__(PACKAGE_RESOURCES.getMessage("signup.notRequired"))
			.td__(PACKAGE_RESOURCES.getMessage("signupCustomizeServerConfirmation.ram.prompt"))
			.td__(getRamOption(rootConn, signupCustomizeServerForm))
		);
		String sataControllerOption = getSataControllerOption(rootConn, signupCustomizeServerForm);
		if(!GenericValidator.isBlankOrNull(sataControllerOption)) {
			tbody.tr__(tr -> tr
				.td__(PACKAGE_RESOURCES.getMessage("signup.notRequired"))
				.td__(PACKAGE_RESOURCES.getMessage("signupCustomizeServerConfirmation.sataController.prompt"))
				.td__(sataControllerOption)
			);
		}
		String scsiControllerOption = getScsiControllerOption(rootConn, signupCustomizeServerForm);
		if(!GenericValidator.isBlankOrNull(scsiControllerOption)) {
			tbody.tr__(tr -> tr
				.td__(PACKAGE_RESOURCES.getMessage("signup.notRequired"))
				.td__(PACKAGE_RESOURCES.getMessage("signupCustomizeServerConfirmation.scsiController.prompt"))
				.td__(scsiControllerOption)
			);
		}
		for(String diskOption : getDiskOptions(rootConn, signupCustomizeServerForm)) {
			tbody.tr__(tr -> tr
				.td__(PACKAGE_RESOURCES.getMessage("signup.notRequired"))
				.td__(PACKAGE_RESOURCES.getMessage("signupCustomizeServerConfirmation.disk.prompt"))
				.td__(diskOption)
			);
		}
		tbody.tr__(tr -> tr
			.td__(PACKAGE_RESOURCES.getMessage("signup.notRequired"))
			.td__(PACKAGE_RESOURCES.getMessage("signupCustomizeServerConfirmation.setup.prompt"))
			.td__(td -> {
				Money setup = packageDefinition.getSetupFee();
				if(setup == null) {
					td.text(PACKAGE_RESOURCES.getMessage("signupCustomizeServerConfirmation.setup.none"));
				} else {
					td.text(setup);
				}
			})
		)
		.tr__(tr -> tr
			.td__(PACKAGE_RESOURCES.getMessage("signup.notRequired"))
			.td().style("white-space:nowrap").__(PACKAGE_RESOURCES.getMessage("signupCustomizeServerConfirmation.monthlyRate.prompt"))
			.td__(request.getAttribute("monthlyRate"))
		);
	}

	/**
	 * Gets the total amount of hard drive space in gigabytes.
	 */
	public static int getTotalHardwareDiskSpace(AOServConnector rootConn, SignupCustomizeServerForm signupCustomizeServerForm) throws IOException, SQLException {
		if(signupCustomizeServerForm == null) return 0;
		int total = 0;
		for(String diskOption : signupCustomizeServerForm.getDiskOptions()) {
			if(diskOption != null && diskOption.length()>0 && !"-1".equals(diskOption)) {
				PackageDefinitionLimit limit = rootConn.getBilling().getPackageDefinitionLimit().get(Integer.parseInt(diskOption));
				if(limit != null) {
					Resource resource = limit.getResource();
					String name = resource.getName();
					if(name.startsWith("hardware_disk_")) {
						// Is in formation hardware_disk_RPM_SIZE
						int pos = name.indexOf('_', 14);
						if(pos != -1) {
							int pos2 = name.indexOf('_', pos+1);
							if(pos2 == -1) {
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
