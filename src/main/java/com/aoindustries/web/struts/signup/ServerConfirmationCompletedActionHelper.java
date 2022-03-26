/*
 * aoweb-struts - Template webapp for legacy Struts-based site framework with AOServ Platform control panels.
 * Copyright (C) 2007-2009, 2015, 2016, 2017, 2018, 2019, 2020, 2021, 2022  AO Industries, Inc.
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

import com.aoapps.encoding.Doctype;
import com.aoapps.encoding.EncodingContext;
import com.aoapps.encoding.MediaWriter;
import com.aoapps.encoding.Serialization;
import com.aoapps.hodgepodge.io.FindReplaceWriter;
import com.aoapps.hodgepodge.io.NativeToPosixWriter;
import com.aoapps.html.Document;
import com.aoapps.html.any.AnyDocument;
import com.aoapps.html.any.AnyMETA;
import com.aoapps.html.any.AnySCRIPT;
import com.aoapps.html.any.AnySTYLE;
import com.aoapps.lang.i18n.ThreadLocale;
import com.aoapps.lang.io.IoUtils;
import com.aoapps.net.Email;
import com.aoapps.net.HostAddress;
import com.aoapps.net.InetAddress;
import com.aoapps.taglib.GlobalAttributes;
import com.aoapps.taglib.HtmlTag;
import com.aoindustries.aoserv.client.AOServConnector;
import com.aoindustries.aoserv.client.billing.PackageDefinition;
import com.aoindustries.aoserv.client.linux.User;
import com.aoindustries.aoserv.client.payment.CountryCode;
import com.aoindustries.aoserv.client.reseller.Brand;
import com.aoindustries.web.struts.Mailer;
import com.aoindustries.web.struts.SiteSettings;
import com.aoindustries.web.struts.TextSkin;
import static com.aoindustries.web.struts.signup.Resources.PACKAGE_RESOURCES;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionServlet;

/**
 * Managed6CompletedAction and Dedicated6CompletedAction both use this to setup the request attributes.  This is implemented
 * here because inheritance is not possible and neither one is logically above the other.
 *
 * @author  AO Industries, Inc.
 */
public final class ServerConfirmationCompletedActionHelper {

	/** Make no instances. */
	private ServerConfirmationCompletedActionHelper() {throw new AssertionError();}

	public static void addOptions(Map<String, String> options, SignupCustomizeServerForm signupCustomizeServerForm) {
		// Power option
		options.put("powerOption", Integer.toString(signupCustomizeServerForm.getPowerOption()));

		// CPU option
		options.put("cpuOption", Integer.toString(signupCustomizeServerForm.getCpuOption()));

		// RAM option
		options.put("ramOption", Integer.toString(signupCustomizeServerForm.getRamOption()));

		// SATA Controller option
		options.put("sataControllerOption", Integer.toString(signupCustomizeServerForm.getSataControllerOption()));

		// SCSI Controller option
		options.put("scsiControllerOption", Integer.toString(signupCustomizeServerForm.getScsiControllerOption()));

		// Disk options
		int number = 0;
		for(String diskOption : signupCustomizeServerForm.getDiskOptions()) {
			if(diskOption!=null && diskOption.length()>0 && !diskOption.equals("-1")) {
				options.put("diskOptions["+(number++)+"]", diskOption);
			}
		}
	}

	public static void addOptions(Map<String, String> options, SignupCustomizeManagementForm signupCustomizeManagementForm) {
		options.put("backupOnsiteOption", Integer.toString(signupCustomizeManagementForm.getBackupOnsiteOption()));
		options.put("backupOffsiteOption", Integer.toString(signupCustomizeManagementForm.getBackupOffsiteOption()));
		options.put("backupDvdOption", signupCustomizeManagementForm.getBackupDvdOption());
		options.put("distributionScanOption", Integer.toString(signupCustomizeManagementForm.getDistributionScanOption()));
		options.put("failoverOption", Integer.toString(signupCustomizeManagementForm.getFailoverOption()));
	}

	/**
	 * Stores to the database, if possible.  Sets request attributes "pkey" and "statusKey", both as String type.
	 */
	@SuppressWarnings({"UseSpecificCatch", "TooBroadCatch"})
	public static void storeToDatabase(
		ActionServlet servlet,
		HttpServletRequest request,
		AOServConnector rootConn,
		PackageDefinition packageDefinition,
		SignupOrganizationForm signupOrganizationForm,
		SignupTechnicalForm signupTechnicalForm,
		SignupBillingInformationForm signupBillingInformationForm,
		Map<String, String> options
	) {
		// Store to the database
		int pkey;
		String statusKey;
		try {
			CountryCode organizationCountry = rootConn.getPayment().getCountryCode().get(signupOrganizationForm.getOrganizationCountry());
			CountryCode baCountry = GenericValidator.isBlankOrNull(signupTechnicalForm.getBaCountry()) ? null : rootConn.getPayment().getCountryCode().get(signupTechnicalForm.getBaCountry());

			pkey = rootConn.getSignup().getRequest().addSignupRequest(rootConn.getCurrentAdministrator().getUsername().getPackage().getAccount().getBrand(),
				InetAddress.valueOf(request.getRemoteAddr()),
				packageDefinition,
				signupOrganizationForm.getOrganizationName(),
				signupOrganizationForm.getOrganizationPhone(),
				signupOrganizationForm.getOrganizationFax(),
				signupOrganizationForm.getOrganizationAddress1(),
				signupOrganizationForm.getOrganizationAddress2(),
				signupOrganizationForm.getOrganizationCity(),
				signupOrganizationForm.getOrganizationState(),
				organizationCountry,
				signupOrganizationForm.getOrganizationZip(),
				signupTechnicalForm.getBaName(),
				signupTechnicalForm.getBaTitle(),
				signupTechnicalForm.getBaWorkPhone(),
				signupTechnicalForm.getBaCellPhone(),
				signupTechnicalForm.getBaHomePhone(),
				signupTechnicalForm.getBaFax(),
				Email.valueOf(signupTechnicalForm.getBaEmail()),
				signupTechnicalForm.getBaAddress1(),
				signupTechnicalForm.getBaAddress2(),
				signupTechnicalForm.getBaCity(),
				signupTechnicalForm.getBaState(),
				baCountry,
				signupTechnicalForm.getBaZip(),
				User.Name.valueOf(signupTechnicalForm.getBaUsername()),
				signupBillingInformationForm.getBillingContact(),
				Email.valueOf(signupBillingInformationForm.getBillingEmail()),
				signupBillingInformationForm.getBillingUseMonthly(),
				signupBillingInformationForm.getBillingPayOneYear(),
				signupTechnicalForm.getBaPassword(),
				signupBillingInformationForm.getBillingCardholderName(),
				signupBillingInformationForm.getBillingCardNumber(),
				signupBillingInformationForm.getBillingExpirationMonth(),
				signupBillingInformationForm.getBillingExpirationYear(),
				signupBillingInformationForm.getBillingStreetAddress(),
				signupBillingInformationForm.getBillingCity(),
				signupBillingInformationForm.getBillingState(),
				signupBillingInformationForm.getBillingZip(),
				options
			);
			statusKey = "serverConfirmationCompleted.success";
		} catch(ThreadDeath td) {
			throw td;
		} catch(Throwable t) {
			servlet.log("Unable to store signup", t);
			pkey = -1;
			statusKey = "serverConfirmationCompleted.error";
		}

		request.setAttribute("statusKey", statusKey);
		request.setAttribute("pkey", Integer.toString(pkey));
	}

	// TODO: Have this generate a ticket instead, with full details.  Remove "all except bank card numbers" in other places once done.
	@SuppressWarnings({"UseSpecificCatch", "TooBroadCatch"})
	public static void sendSupportSummaryEmail(
		ActionServlet servlet,
		HttpServletRequest request,
		String pkey,
		String statusKey,
		PackageDefinition packageDefinition,
		SignupCustomizeServerForm signupCustomizeServerForm,
		SignupCustomizeManagementForm signupCustomizeManagementForm,
		SignupOrganizationForm signupOrganizationForm,
		SignupTechnicalForm signupTechnicalForm,
		SignupBillingInformationForm signupBillingInformationForm
	) {
		try {
			SiteSettings siteSettings = SiteSettings.getInstance(servlet.getServletContext());
			sendSummaryEmail(servlet, request, pkey, statusKey, siteSettings.getBrand().getAowebStrutsSignupAdminAddress(), packageDefinition, signupCustomizeServerForm, signupCustomizeManagementForm, signupOrganizationForm, signupTechnicalForm, signupBillingInformationForm);
		} catch(ThreadDeath td) {
			throw td;
		} catch(Throwable t) {
			servlet.log("Unable to send sign up details to support admin address", t);
		}
	}

	/**
	 * Sends the customer emails and stores the successAddresses and failureAddresses as request attributes.
	 */
	public static void sendCustomerSummaryEmails(
		ActionServlet servlet,
		HttpServletRequest request,
		String pkey,
		String statusKey,
		PackageDefinition packageDefinition,
		SignupCustomizeServerForm signupCustomizeServerForm,
		SignupCustomizeManagementForm signupCustomizeManagementForm,
		SignupOrganizationForm signupOrganizationForm,
		SignupTechnicalForm signupTechnicalForm,
		SignupBillingInformationForm signupBillingInformationForm
	) {
		Set<String> addresses = new HashSet<>();
		addresses.add(signupTechnicalForm.getBaEmail());
		addresses.add(signupBillingInformationForm.getBillingEmail());
		Set<String> successAddresses = new HashSet<>();
		Set<String> failureAddresses = new HashSet<>();
		Iterator<String> iter = addresses.iterator();
		while(iter.hasNext()) {
			String address = iter.next();
			boolean success = sendSummaryEmail(servlet, request, pkey, statusKey, address, packageDefinition, signupCustomizeServerForm, signupCustomizeManagementForm, signupOrganizationForm, signupTechnicalForm, signupBillingInformationForm);
			if(success) successAddresses.add(address);
			else failureAddresses.add(address);
		}

		// Store request attributes
		request.setAttribute("successAddresses", successAddresses);
		request.setAttribute("failureAddresses", failureAddresses);
	}

	/**
	 * Sends a summary email and returns <code>true</code> if successful.
	 */
	@SuppressWarnings({"deprecation", "UseSpecificCatch", "TooBroadCatch"})
	private static boolean sendSummaryEmail(
		ActionServlet servlet,
		HttpServletRequest request,
		String pkey,
		String statusKey,
		String recipient,
		PackageDefinition packageDefinition,
		SignupCustomizeServerForm signupCustomizeServerForm,
		SignupCustomizeManagementForm signupCustomizeManagementForm,
		SignupOrganizationForm signupOrganizationForm,
		SignupTechnicalForm signupTechnicalForm,
		SignupBillingInformationForm signupBillingInformationForm
	) {
		try {
			String subject = PACKAGE_RESOURCES.getMessage("serverConfirmationCompleted.email.subject", pkey);

			// Find the locale and related resource bundles
			Locale userLocale = ThreadLocale.get();
			Charset charset = AnyDocument.ENCODING; // TODO: US-ASCII with automatic entity encoding

			// Generate the email contents
			// TODO: Test emails
			StringWriter buffer = new StringWriter();
			Document document = new Document(
				new EncodingContext() {
					@Override
					public Serialization getSerialization() {
						return Serialization.SGML;
					}
					@Override
					public Doctype getDoctype() {
						return Doctype.STRICT;
					}
					@Override
					public Charset getCharacterEncoding() {
						return charset;
					}
				},
				NativeToPosixWriter.getInstance(new FindReplaceWriter(buffer, "\n", "\r\n"))
			);
			document.setAutonli(true);
			document.setIndent(true);
			document.xmlDeclaration();
			document.doctype();
			Serialization serialization = document.encodingContext.getSerialization();
			HtmlTag.beginHtmlTag(userLocale, document.out, serialization, (GlobalAttributes)null); document.out.write("\n"
			+ "<head>\n");
			String contentType = serialization.getContentType() + "; charset=" + charset;
			if(document.encodingContext.getDoctype() == Doctype.HTML5) {
				document.meta().charset().__();
			} else {
				document
					.meta().httpEquiv(AnyMETA.HttpEquiv.CONTENT_TYPE).content(contentType).__()
					// Default style language
					.meta().httpEquiv(AnyMETA.HttpEquiv.CONTENT_STYLE_TYPE).content(AnySTYLE.Type.TEXT_CSS).__()
					.meta().httpEquiv(AnyMETA.HttpEquiv.CONTENT_SCRIPT_TYPE).content(AnySCRIPT.Type.TEXT_JAVASCRIPT).__();
			}
			document.title__(subject);
			// Embed the text-only style sheet
			InputStream cssIn = servlet.getServletContext().getResourceAsStream(TextSkin.TEXTSKIN_CSS.getUri());
			if(cssIn != null) {
				try {
					try (MediaWriter style = document.style()._c()) {
						Reader cssReader = new InputStreamReader(cssIn);
						try {
							IoUtils.copy(cssReader, style);
						} finally {
							cssIn.close();
						}
					}
				} finally {
					cssIn.close();
				}
			} else {
				servlet.log("Warning: Unable to find resource: " + TextSkin.TEXTSKIN_CSS);
			}
			document.out.append("</head>\n"
			+ "<body>\n"
			+ "<table style=\"border:0px\" cellpadding=\"0\" cellspacing=\"0\">\n"
			+ "    <tr><td style=\"white-space:nowrap\" colspan=\"3\">\n"
			+ "        ").append(PACKAGE_RESOURCES.getMessage(statusKey, pkey)); document.br__().out.write("\n"
			+ "        "); document.br__().out.append("\n"
			+ "        ").append(PACKAGE_RESOURCES.getMessage("serverConfirmationCompleted.belowIsSummary")); document.br__().out.write("\n"
			+ "        "); document.hr__().out.write("\n"
			+ "    </td></tr>\n"
			+ "    <tr><th colspan=\"3\">"); document.text(PACKAGE_RESOURCES.getMessage("steps.selectServer.label")); document.out.write("</th></tr>\n");
			SignupSelectServerActionHelper.writeEmailConfirmation(document, packageDefinition);
			document.out.write("    <tr><td colspan=\"3\">&#160;</td></tr>\n"
			+ "    <tr><th colspan=\"3\">"); document.text(PACKAGE_RESOURCES.getMessage("steps.customizeServer.label")); document.out.write("</th></tr>\n");
			SiteSettings siteSettings = SiteSettings.getInstance(servlet.getServletContext());
			AOServConnector rootConn = siteSettings.getRootAOServConnector();
			SignupCustomizeServerActionHelper.writeEmailConfirmation(request, document, rootConn, packageDefinition, signupCustomizeServerForm);
			if(signupCustomizeManagementForm!=null) {
				document.out.write("    <tr><td colspan=\"3\">&#160;</td></tr>\n"
				+ "    <tr><th colspan=\"3\">"); document.text(PACKAGE_RESOURCES.getMessage("steps.customizeManagement.label")); document.out.write("</th></tr>\n");
				SignupCustomizeManagementActionHelper.writeEmailConfirmation(
					request,
					document,
					rootConn,
					signupCustomizeManagementForm
				);
			}
			document.out.write("    <tr><td colspan=\"3\">&#160;</td></tr>\n"
			+ "    <tr><th colspan=\"3\">"); document.text(PACKAGE_RESOURCES.getMessage("steps.organizationInfo.label")); document.out.write("</th></tr>\n");
			SignupOrganizationActionHelper.writeEmailConfirmation(document, rootConn, signupOrganizationForm);
			document.out.write("    <tr><td colspan=\"3\">&#160;</td></tr>\n"
			+ "    <tr><th colspan=\"3\">"); document.text(PACKAGE_RESOURCES.getMessage("steps.technicalInfo.label")); document.out.write("</th></tr>\n");
			SignupTechnicalActionHelper.writeEmailConfirmation(document, rootConn, signupTechnicalForm);
			document.out.write("    <tr><td colspan=\"3\">&#160;</td></tr>\n"
			+ "    <tr><th colspan=\"3\">"); document.text(PACKAGE_RESOURCES.getMessage("steps.billingInformation.label")); document.out.write("</th></tr>\n");
			SignupBillingInformationActionHelper.writeEmailConfirmation(document, signupBillingInformationForm);
			document.out.write("</table>\n"
			+ "</body>\n");
			HtmlTag.endHtmlTag(document.out); document.autoNl();

			// Send the email
			Brand brand = siteSettings.getBrand();
			Mailer.sendEmail(HostAddress.valueOf(brand.getSignupEmailAddress().getDomain().getLinuxServer().getHostname()),
				contentType,
				charset,
				brand.getSignupEmailAddress().toString(),
				brand.getSignupEmailDisplay(),
				Collections.singletonList(recipient),
				subject,
				buffer.toString()
			);

			return true;
		} catch(ThreadDeath td) {
			throw td;
		} catch(Throwable t) {
			servlet.log("Unable to send sign up details to "+recipient, t);
			return false;
		}
	}
}
