/*
 * aoweb-struts - Template webapp for legacy Struts-based site framework with AOServ Platform control panels.
 * Copyright (C) 2009-2013, 2015, 2016, 2017, 2018, 2019, 2020, 2021, 2022  AO Industries, Inc.
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
import com.aoapps.encoding.Serialization;
import com.aoapps.hodgepodge.io.FindReplaceWriter;
import com.aoapps.hodgepodge.io.NativeToPosixWriter;
import com.aoapps.html.Document;
import com.aoapps.html.any.AnyDocument;
import com.aoapps.html.util.HeadUtil;
import com.aoapps.lang.i18n.ThreadLocale;
import com.aoapps.lang.io.IoUtils;
import com.aoapps.net.HostAddress;
import com.aoapps.taglib.GlobalAttributes;
import com.aoapps.taglib.HtmlTag;
import com.aoindustries.aoserv.client.AOServConnector;
import com.aoindustries.aoserv.client.billing.PackageDefinition;
import com.aoindustries.aoserv.client.reseller.Brand;
import com.aoindustries.web.struts.Mailer;
import com.aoindustries.web.struts.SiteSettings;
import com.aoindustries.web.struts.TextSkin;
import static com.aoindustries.web.struts.signup.Resources.PACKAGE_RESOURCES;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionServlet;

/**
 * @author  AO Industries, Inc.
 */
public final class MinimalConfirmationCompletedActionHelper {

	/** Make no instances. */
	private MinimalConfirmationCompletedActionHelper() {throw new AssertionError();}

	// TODO: Have this generate a ticket instead, with full details.  Remove "all except bank card numbers" in other places once done.
	@SuppressWarnings({"UseSpecificCatch", "TooBroadCatch"})
	public static void sendSupportSummaryEmail(
		ActionServlet servlet,
		HttpServletRequest request,
		String pkey,
		String statusKey,
		PackageDefinition packageDefinition,
		SignupOrganizationForm signupOrganizationForm,
		SignupTechnicalForm signupTechnicalForm,
		SignupBillingInformationForm signupBillingInformationForm
	) {
		try {
			SiteSettings siteSettings = SiteSettings.getInstance(servlet.getServletContext());
			sendSummaryEmail(servlet, pkey, statusKey, siteSettings.getBrand().getAowebStrutsSignupAdminAddress(), packageDefinition, signupOrganizationForm, signupTechnicalForm, signupBillingInformationForm);
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
			boolean success = sendSummaryEmail(servlet, pkey, statusKey, address, packageDefinition, signupOrganizationForm, signupTechnicalForm, signupBillingInformationForm);
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
	@SuppressWarnings({"UseSpecificCatch", "TooBroadCatch"})
	private static boolean sendSummaryEmail(
		ActionServlet servlet,
		String pkey,
		String statusKey,
		String recipient,
		PackageDefinition packageDefinition,
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
			HtmlTag.beginHtmlTag(userLocale, document.getRawUnsafe(), serialization, (GlobalAttributes)null); document.unsafe("\n"
			+ "<head>\n");
			String contentType = serialization.getContentType() + "; charset=" + charset;
			HeadUtil.standardMeta(document, contentType);
			document.title__(subject);
			// Embed the text-only style sheet
			InputStream cssIn = servlet.getServletContext().getResourceAsStream(TextSkin.TEXTSKIN_CSS.getUri());
			if(cssIn != null) {
				try {
					try (Writer style = document.style()._c()) {
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
			document.unsafe("</head>\n"
			+ "<body>\n"
			+ "<table style=\"border:0px\" cellpadding=\"0\" cellspacing=\"0\">\n"
			+ "    <tr><td style=\"white-space:nowrap\" colspan=\"3\">\n"
			+ "        ").unsafe(PACKAGE_RESOURCES.getMessage(statusKey, pkey)).br__().unsafe("\n"
			+ "        ").br__().unsafe("\n"
			+ "        ").unsafe(PACKAGE_RESOURCES.getMessage("serverConfirmationCompleted.belowIsSummary")).br__().unsafe("\n"
			+ "        ").hr__().unsafe("\n"
			+ "    </td></tr>\n"
			+ "    <tr><th colspan=\"3\">").text(PACKAGE_RESOURCES.getMessage("steps.selectPackage.label")).unsafe("</th></tr>\n");
			SignupSelectPackageActionHelper.writeEmailConfirmation(document, packageDefinition);
			SiteSettings siteSettings = SiteSettings.getInstance(servlet.getServletContext());
			AOServConnector rootConn = siteSettings.getRootAOServConnector();
			document.unsafe("    <tr><td colspan=\"3\">&#160;</td></tr>\n"
			+ "    <tr><th colspan=\"3\">").text(PACKAGE_RESOURCES.getMessage("steps.organizationInfo.label")).unsafe("</th></tr>\n");
			SignupOrganizationActionHelper.writeEmailConfirmation(document, rootConn, signupOrganizationForm);
			document.unsafe("    <tr><td colspan=\"3\">&#160;</td></tr>\n"
			+ "    <tr><th colspan=\"3\">").text(PACKAGE_RESOURCES.getMessage("steps.technicalInfo.label")).unsafe("</th></tr>\n");
			SignupTechnicalActionHelper.writeEmailConfirmation(document, rootConn, signupTechnicalForm);
			document.unsafe("    <tr><td colspan=\"3\">&#160;</td></tr>\n"
			+ "    <tr><th colspan=\"3\">").text(PACKAGE_RESOURCES.getMessage("steps.billingInformation.label")).unsafe("</th></tr>\n");
			SignupBillingInformationActionHelper.writeEmailConfirmation(document, signupBillingInformationForm);
			document.unsafe("</table>\n"
			+ "</body>\n");
			HtmlTag.endHtmlTag(document.getRawUnsafe()); document.autoNl();

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
