/*
 * aoweb-struts-core - Core API for legacy Struts-based site framework with AOServ Platform control panels.
 * Copyright (C) 2007-2009, 2015, 2016, 2019  AO Industries, Inc.
 *     support@aoindustries.com
 *     7262 Bull Pen Cir
 *     Mobile, AL 36695
 *
 * This file is part of aoweb-struts-core.
 *
 * aoweb-struts-core is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * aoweb-struts-core is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with aoweb-struts-core.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.aoindustries.website.signup;

import com.aoindustries.creditcards.CreditCard;
import com.aoindustries.encoding.ChainWriter;
import static com.aoindustries.website.signup.ApplicationResources.accessor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

/**
 * Managed5Action and Dedicated5Action both use this to setup the request attributes.  This is implemented
 * here because inheritance is not possible and neither one is logically above the other.
 *
 * @author  AO Industries, Inc.
 */
final public class SignupBillingInformationActionHelper {

	/**
	 * Make no instances.
	 */
	private SignupBillingInformationActionHelper() {}

	public static void setRequestAttributes(HttpServletRequest request) {
		setBillingExpirationYearsRequestAttribute(request);
	}

	public static void setBillingExpirationYearsRequestAttribute(HttpServletRequest request) {
		// Build the list of years
		List<String> billingExpirationYears = new ArrayList<>(12);
		int startYear = Calendar.getInstance().get(Calendar.YEAR);
		for(int c=0;c<12;c++) billingExpirationYears.add(Integer.toString(startYear+c));

		// Store to request attributes
		request.setAttribute("billingExpirationYears", billingExpirationYears);
	}

	/**
	 * Only shows the first two and last four digits of a card number.
	 *
	 * @deprecated  Please call CreditCard.maskCreditCardNumber directly.
	 */
	@Deprecated
	public static String hideCreditCardNumber(String number) {
		return CreditCard.maskCreditCardNumber(number);
	}

	public static String getBillingCardNumber(SignupBillingInformationForm signupBillingInformationForm) {
		return CreditCard.maskCreditCardNumber(signupBillingInformationForm.getBillingCardNumber());
	}

	public static void setConfirmationRequestAttributes(
		ServletContext servletContext,
		HttpServletRequest request,
		SignupBillingInformationForm signupBillingInformationForm
	) throws IOException {
		// Store as request attribute for the view
		request.setAttribute("billingCardNumber", getBillingCardNumber(signupBillingInformationForm));
	}

	public static void printConfirmation(ChainWriter emailOut, SignupBillingInformationForm signupBillingInformationForm) throws IOException {
		emailOut.print("    <tr>\n"
					 + "        <td>").print(accessor.getMessage("signup.required")).print("</td>\n"
					 + "        <td>").print(accessor.getMessage("signupBillingInformationForm.billingContact.prompt")).print("</td>\n"
					 + "        <td>").encodeXhtml(signupBillingInformationForm.getBillingContact()).print("</td>\n"
					 + "    </tr>\n"
					 + "    <tr>\n"
					 + "        <td>").print(accessor.getMessage("signup.required")).print("</td>\n"
					 + "        <td>").print(accessor.getMessage("signupBillingInformationForm.billingEmail.prompt")).print("</td>\n"
					 + "        <td>").encodeXhtml(signupBillingInformationForm.getBillingEmail()).print("</td>\n"
					 + "    </tr>\n"
					 + "    <tr>\n"
					 + "        <td>").print(accessor.getMessage("signup.required")).print("</td>\n"
					 + "        <td>").print(accessor.getMessage("signupBillingInformationForm.billingCardholderName.prompt")).print("</td>\n"
					 + "        <td>").encodeXhtml(signupBillingInformationForm.getBillingCardholderName()).print("</td>\n"
					 + "    </tr>\n"
					 + "    <tr>\n"
					 + "        <td>").print(accessor.getMessage("signup.required")).print("</td>\n"
					 + "        <td>").print(accessor.getMessage("signupBillingInformationForm.billingCardNumber.prompt")).print("</td>\n"
					 + "        <td>").encodeXhtml(getBillingCardNumber(signupBillingInformationForm)).print("</td>\n"
					 + "    </tr>\n"
					 + "    <tr>\n"
					 + "        <td>").print(accessor.getMessage("signup.required")).print("</td>\n"
					 + "        <td>").print(accessor.getMessage("signupBillingInformationForm.billingExpirationDate.prompt")).print("</td>\n"
					 + "        <td>").print(accessor.getMessage("signupBillingInformationForm.billingExpirationDate.hidden")).print("</td>\n"
					 + "    </tr>\n"
					 + "    <tr>\n"
					 + "        <td>").print(accessor.getMessage("signup.required")).print("</td>\n"
					 + "        <td>").print(accessor.getMessage("signupBillingInformationForm.billingStreetAddress.prompt")).print("</td>\n"
					 + "        <td>").encodeXhtml(signupBillingInformationForm.getBillingStreetAddress()).print("</td>\n"
					 + "    </tr>\n"
					 + "    <tr>\n"
					 + "        <td>").print(accessor.getMessage("signup.required")).print("</td>\n"
					 + "        <td>").print(accessor.getMessage("signupBillingInformationForm.billingCity.prompt")).print("</td>\n"
					 + "        <td>").encodeXhtml(signupBillingInformationForm.getBillingCity()).print("</td>\n"
					 + "    </tr>\n"
					 + "    <tr>\n"
					 + "        <td>").print(accessor.getMessage("signup.required")).print("</td>\n"
					 + "        <td>").print(accessor.getMessage("signupBillingInformationForm.billingState.prompt")).print("</td>\n"
					 + "        <td>").encodeXhtml(signupBillingInformationForm.getBillingState()).print("</td>\n"
					 + "    </tr>\n"
					 + "    <tr>\n"
					 + "        <td>").print(accessor.getMessage("signup.required")).print("</td>\n"
					 + "        <td>").print(accessor.getMessage("signupBillingInformationForm.billingZip.prompt")).print("</td>\n"
					 + "        <td>").encodeXhtml(signupBillingInformationForm.getBillingZip()).print("</td>\n"
					 + "    </tr>\n"
					 + "    <tr>\n"
					 + "        <td>").print(accessor.getMessage("signup.notRequired")).print("</td>\n"
					 + "        <td>").print(accessor.getMessage("signupBillingInformationForm.billingUseMonthly.prompt")).print("</td>\n"
					 + "        <td>").print(accessor.getMessage(signupBillingInformationForm.getBillingUseMonthly() ? "signupBillingInformationForm.billingUseMonthly.yes" : "signupBillingInformationForm.billingUseMonthly.no")).print("</td>\n"
					 + "    </tr>\n"
					 + "    <tr>\n"
					 + "        <td>").print(accessor.getMessage("signup.notRequired")).print("</td>\n"
					 + "        <td>").print(accessor.getMessage("signupBillingInformationForm.billingPayOneYear.prompt")).print("</td>\n"
					 + "        <td>").print(accessor.getMessage(signupBillingInformationForm.getBillingPayOneYear() ? "signupBillingInformationForm.billingPayOneYear.yes" : "signupBillingInformationForm.billingPayOneYear.no")).print("</td>\n"
					 + "    </tr>\n");
	}
}
