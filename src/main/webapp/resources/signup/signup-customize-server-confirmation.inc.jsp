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
	<logic:notEmpty scope="request" name="powerOption">
		<tr>
			<td><ao:message key="signup.notRequired" /></td>
			<td><ao:message key="signupCustomizeServerConfirmation.power.prompt" /></td>
			<td><ao:write name="powerOption" /></td>
		</tr>
	</logic:notEmpty>
	<tr>
		<td><ao:message key="signup.notRequired" /></td>
		<td><ao:message key="signupCustomizeServerConfirmation.cpu.prompt" /></td>
		<td><ao:write name="cpuOption" type="application/xhtml+xml" /></td>
	</tr>
	<tr>
		<td><ao:message key="signup.notRequired" /></td>
		<td><ao:message key="signupCustomizeServerConfirmation.ram.prompt" /></td>
		<td><ao:write name="ramOption" /></td>
	</tr>
	<logic:notEmpty scope="request" name="sataControllerOption">
		<tr>
			<td><ao:message key="signup.notRequired" /></td>
			<td><ao:message key="signupCustomizeServerConfirmation.sataController.prompt" /></td>
			<td><ao:write name="sataControllerOption" /></td>
		</tr>
	</logic:notEmpty>
	<logic:notEmpty scope="request" name="scsiControllerOption">
		<tr>
			<td><ao:message key="signup.notRequired" /></td>
			<td><ao:message key="signupCustomizeServerConfirmation.scsiController.prompt" /></td>
			<td><ao:write name="scsiControllerOption" /></td>
		</tr>
	</logic:notEmpty>
	<logic:iterate name="diskOptions" id="diskOption">
		<tr>
			<td><ao:message key="signup.notRequired" /></td>
			<td><ao:message key="signupCustomizeServerConfirmation.disk.prompt" /></td>
			<td><ao:write name="diskOption" /></td>
		</tr>
	</logic:iterate>
	<tr>
		<td><ao:message key="signup.notRequired" /></td>
		<td><ao:message key="signupCustomizeServerConfirmation.setup.prompt" /></td>
		<td>
			<logic:notPresent name="setup">
				<ao:message key="signupSelectPackageForm.setup.none" />
			</logic:notPresent>
			<logic:present name="setup">
				<ao:write name="setup" />
			</logic:present>
		</td>
	</tr>
	<tr>
		<td><ao:message key="signup.notRequired" /></td>
		<td style='white-space:nowrap'><ao:message key="signupCustomizeServerConfirmation.monthlyRate.prompt" /></td>
		<td><ao:write name="monthlyRate" /></td>
	</tr>
</ao:bundle>
