<%--
aoweb-struts-resources - Web resources for legacy Struts-based site framework with AOServ Platform control panels.
Copyright (C) 2007-2009, 2016, 2019  AO Industries, Inc.
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

<fmt:bundle basename="com.aoindustries.website.signup.ApplicationResources">
	<tr>
		<td><fmt:message key="signup.required" /></td>
		<td><fmt:message key="signupOrganizationForm.organizationName.prompt" /></td>
		<td><ao:write scope="session" name="signupOrganizationForm" property="organizationName" /></td>
	</tr>
	<tr>
		<td><fmt:message key="signup.required" /></td>
		<td><fmt:message key="signupOrganizationForm.organizationPhone.prompt" /></td>
		<td><ao:write scope="session" name="signupOrganizationForm" property="organizationPhone" /></td>
	</tr>
	<tr>
		<td><fmt:message key="signup.notRequired" /></td>
		<td><fmt:message key="signupOrganizationForm.organizationFax.prompt" /></td>
		<td><ao:write scope="session" name="signupOrganizationForm" property="organizationFax" /></td>
	</tr>
	<tr>
		<td><fmt:message key="signup.required" /></td>
		<td><fmt:message key="signupOrganizationForm.organizationAddress1.prompt" /></td>
		<td><ao:write scope="session" name="signupOrganizationForm" property="organizationAddress1" /></td>
	</tr>
	<logic:notEmpty scope="session" name="signupOrganizationForm" property="organizationAddress2">
		<tr>
			<td><fmt:message key="signup.notRequired" /></td>
			<td><fmt:message key="signupOrganizationForm.organizationAddress2.prompt" /></td>
			<td><ao:write scope="session" name="signupOrganizationForm" property="organizationAddress2" /></td>
		</tr>
	</logic:notEmpty>
	<tr>
		<td><fmt:message key="signup.required" /></td>
		<td><fmt:message key="signupOrganizationForm.organizationCity.prompt" /></td>
		<td><ao:write scope="session" name="signupOrganizationForm" property="organizationCity" /></td>
	</tr>
	<tr>
		<td><fmt:message key="signup.notRequired" /></td>
		<td><fmt:message key="signupOrganizationForm.organizationState.prompt" /></td>
		<td><ao:write scope="session" name="signupOrganizationForm" property="organizationState" /></td>
	</tr>
	<tr>
		<td><fmt:message key="signup.required" /></td>
		<td><fmt:message key="signupOrganizationForm.organizationCountry.prompt" /></td>
		<td><ao:write scope="request" name="organizationCountry" /></td>
	</tr>
	<tr>
		<td><fmt:message key="signup.notRequired" /></td>
		<td><fmt:message key="signupOrganizationForm.organizationZip.prompt" /></td>
		<td><ao:write scope="session" name="signupOrganizationForm" property="organizationZip" /></td>
	</tr>
</fmt:bundle>
