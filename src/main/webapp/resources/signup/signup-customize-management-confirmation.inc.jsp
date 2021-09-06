<%--
aoweb-struts - Template webapp for legacy Struts-based site framework with AOServ Platform control panels.
Copyright (C) 2007-2009, 2016, 2019, 2020, 2021  AO Industries, Inc.
	support@aoindustries.com
	7262 Bull Pen Cir
	Mobile, AL 36695

This file is part of aoweb-struts.

aoweb-struts is free software: you can redistribute it and/or modify
it under the terms of the GNU Lesser General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

aoweb-struts is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public License
along with aoweb-struts.  If not, see <http://www.gnu.org/licenses/>.
--%>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@include file="/_taglibs.inc.jsp" %>

<ao:bundle basename="com.aoindustries.web.struts.signup.i18n.ApplicationResources">
	<logic:notEmpty scope="request" name="backupOnsiteOption">
		<tr>
			<td><ao:message key="signup.notRequired" /></td>
			<td><ao:message key="signupCustomizeManagementConfirmation.backupOnsite.prompt" /></td>
			<td><ao:write name="backupOnsiteOption" /></td>
		</tr>
	</logic:notEmpty>
	<logic:notEmpty scope="request" name="backupOffsiteOption">
		<tr>
			<td><ao:message key="signup.notRequired" /></td>
			<td><ao:message key="signupCustomizeManagementConfirmation.backupOffsite.prompt" /></td>
			<td><ao:write name="backupOffsiteOption" /></td>
		</tr>
	</logic:notEmpty>
	<logic:notEmpty scope="request" name="backupDvdOption">
		<tr>
			<td><ao:message key="signup.notRequired" /></td>
			<td><ao:message key="signupCustomizeManagementConfirmation.backupDvd.prompt" /></td>
			<td><ao:write name="backupDvdOption" /></td>
		</tr>
	</logic:notEmpty>
	<logic:notEmpty scope="request" name="distributionScanOption">
		<tr>
			<td><ao:message key="signup.notRequired" /></td>
			<td><ao:message key="signupCustomizeManagementConfirmation.distributionScan.prompt" /></td>
			<td><ao:write name="distributionScanOption" /></td>
		</tr>
	</logic:notEmpty>
	<logic:notEmpty scope="request" name="failoverOption">
		<tr>
			<td><ao:message key="signup.notRequired" /></td>
			<td><ao:message key="signupCustomizeManagementConfirmation.failover.prompt" /></td>
			<td><ao:write name="failoverOption" /></td>
		</tr>
	</logic:notEmpty>
	<tr>
		<td><ao:message key="signup.notRequired" /></td>
		<td><ao:message key="signupCustomizeManagementConfirmation.totalMonthlyRate.prompt" /></td>
		<td><ao:write name="totalMonthlyRate" /></td>
	</tr>
</ao:bundle>
