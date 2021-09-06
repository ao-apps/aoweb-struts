<%--
aoweb-struts-resources - Template webapp for legacy Struts-based site framework with AOServ Platform control panels.
Copyright (C) 2007-2009, 2016, 2020, 2021  AO Industries, Inc.
	support@aoindustries.com
	7262 Bull Pen Cir
	Mobile, AL 36695

This file is part of aoweb-struts-resources.

aoweb-struts-resources is free software: you can redistribute it and/or modify
it under the terms of the GNU Lesser General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

aoweb-struts-resources is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public License
along with aoweb-struts-resources.  If not, see <http://www.gnu.org/licenses/>.
--%>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@include file="/_taglibs.inc.jsp" %>

<ao:bundle basename="com.aoindustries.web.struts.signup.i18n.ApplicationResources">
	<tr>
		<td><ao:message key="signup.required" /></td>
		<td><ao:message key="signupBillingInformationForm.billingContact.prompt" /></td>
		<td><ao:write scope="session" name="signupBillingInformationForm" property="billingContact" /></td>
	</tr>
	<tr>
		<td><ao:message key="signup.required" /></td>
		<td><ao:message key="signupBillingInformationForm.billingEmail.prompt" /></td>
		<td><ao:write scope="session" name="signupBillingInformationForm" property="billingEmail" /></td>
	</tr>
	<tr>
		<td><ao:message key="signup.required" /></td>
		<td><ao:message key="signupBillingInformationForm.billingCardholderName.prompt" /></td>
		<td><ao:write scope="session" name="signupBillingInformationForm" property="billingCardholderName" /></td>
	</tr>
	<tr>
		<td><ao:message key="signup.required" /></td>
		<td><ao:message key="signupBillingInformationForm.billingCardNumber.prompt" /></td>
		<td><ao:write scope="request" name="billingCardNumber" /></td>
	</tr>
	<tr>
		<td><ao:message key="signup.required" /></td>
		<td><ao:message key="signupBillingInformationForm.billingExpirationDate.prompt" /></td>
		<td><ao:message key="signupBillingInformationForm.billingExpirationDate.hidden" /></td>
	</tr>
	<tr>
		<td><ao:message key="signup.required" /></td>
		<td><ao:message key="signupBillingInformationForm.billingStreetAddress.prompt" /></td>
		<td><ao:write scope="session" name="signupBillingInformationForm" property="billingStreetAddress" /></td>
	</tr>
	<tr>
		<td><ao:message key="signup.required" /></td>
		<td><ao:message key="signupBillingInformationForm.billingCity.prompt" /></td>
		<td><ao:write scope="session" name="signupBillingInformationForm" property="billingCity" /></td>
	</tr>
	<tr>
		<td><ao:message key="signup.required" /></td>
		<td><ao:message key="signupBillingInformationForm.billingState.prompt" /></td>
		<td><ao:write scope="session" name="signupBillingInformationForm" property="billingState" /></td>
	</tr>
	<tr>
		<td><ao:message key="signup.required" /></td>
		<td><ao:message key="signupBillingInformationForm.billingZip.prompt" /></td>
		<td><ao:write scope="session" name="signupBillingInformationForm" property="billingZip" /></td>
	</tr>
	<tr>
		<td><ao:message key="signup.notRequired" /></td>
		<td><ao:message key="signupBillingInformationForm.billingUseMonthly.prompt" /></td>
		<td>
			<logic:equal scope="session" name="signupBillingInformationForm" property="billingUseMonthly" value="true">
				<ao:message key="signupBillingInformationForm.billingUseMonthly.yes" />
			</logic:equal>
			<logic:notEqual scope="session" name="signupBillingInformationForm" property="billingUseMonthly" value="true">
				<ao:message key="signupBillingInformationForm.billingUseMonthly.no" />
			</logic:notEqual>
		</td>
	</tr>
	<tr>
		<td><ao:message key="signup.notRequired" /></td>
		<td><ao:message key="signupBillingInformationForm.billingPayOneYear.prompt" /></td>
		<td>
			<logic:equal scope="session" name="signupBillingInformationForm" property="billingPayOneYear" value="true">
				<ao:message key="signupBillingInformationForm.billingPayOneYear.yes" />
			</logic:equal>
			<logic:notEqual scope="session" name="signupBillingInformationForm" property="billingPayOneYear" value="true">
				<ao:message key="signupBillingInformationForm.billingPayOneYear.no" />
			</logic:notEqual>
		</td>
	</tr>
</ao:bundle>
