<%--
aoweb-struts-resources - Web resources for legacy Struts-based site framework with AOServ Platform control panels.
Copyright (C) 2000-2009, 2016, 2019  AO Industries, Inc.
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

<skin:lightArea>
	<skin:popupGroup>
		<fmt:bundle basename="com.aoindustries.website.signup.ApplicationResources">
			<table cellspacing="0" cellpadding="2">
				<tr><td colspan="6"><b><fmt:message key="signupSelectServerForm.stepLabel" /></b><ao:br /><hr /></td></tr>
				<tr><td colspan="6"><fmt:message key="signupSelectServerForm.stepHelp" /><ao:br /><ao:br /></td></tr>
				<tr>
					<th style='white-space:nowrap'><fmt:message key="signupSelectPackageForm.select.header" /></th>
					<th style='white-space:nowrap'><fmt:message key="signupSelectServerForm.packageDefinition.header" /></th>
					<th style='white-space:nowrap'><fmt:message key="signupSelectPackageForm.setup.header" /></th>
					<th style='white-space:nowrap'><fmt:message key="signupSelectPackageForm.minimumMonthlyRate.header" /></th>
					<th style='white-space:nowrap'><fmt:message key="signupSelectPackageForm.maximumMonthlyRate.header" /></th>
					<th style='white-space:nowrap'>&#160;</th>
				</tr>
				<logic:iterate scope="request" name="servers" id="server" indexId="serverIndex">
					<bean:define name="server" property="minimumConfiguration.packageDefinition" id="packageDefinition" type="java.lang.Integer" />
					<skin:lightDarkTableRow>
						<td style="white-space:nowrap"><html:radio styleId="packageDefinition_${packageDefinition}" property="packageDefinition" idName="server" value="minimumConfiguration.packageDefinition" /></td>
						<td style="white-space:nowrap">
							<label for="packageDefinition_${fn:escapeXml(packageDefinition)}"><b><ao:write name="server" property="minimumConfiguration.name" /></b></label>
							<skin:popup>
								<table cellspacing="0" cellpadding="2" style='font-size:80%;'>
									<tr>
										<td colspan="3" class='aoPopupLightRow' style='font-size:100%;'>
											<table style="width:100%" cellspacing="0" cellpadding="0">
												<tr>
													<th class='aoPopupLightRow'><ao:write name="server" property="minimumConfiguration.name" /></th>
													<td class='aoPopupLightRow' style="text-align:right"><skin:popupClose /></td>
												</tr>
											</table>
										</td>
									</tr>
									<tr>
										<th class='aoPopupDarkRow'><fmt:message key="signupSelectPackageForm.resource.header" /></th>
										<th class='aoPopupDarkRow'><fmt:message key="signupSelectPackageForm.minimum.header" /></th>
										<th class='aoPopupDarkRow'><fmt:message key="signupSelectPackageForm.maximum.header" /></th>
									</tr>
									<% int row2 = 0; %>
									<tr class='<%= ((row2++)&1)==0 ? "aoPopupLightRow" : "aoPopupDarkRow" %>'>
										<td style="white-space:nowrap"><b><fmt:message key="signupSelectPackageForm.cpu.header" /></b></td>
										<td style="white-space:nowrap"><ao:write name="server" property="minimumConfiguration.cpu" type="application/xhtml+xml" /></td>
										<td style="white-space:nowrap"><ao:write name="server" property="maximumConfiguration.cpu" type="application/xhtml+xml" /></td>
									</tr>
									<tr class='<%= ((row2++)&1)==0 ? "aoPopupLightRow" : "aoPopupDarkRow" %>'>
										<td style="white-space:nowrap"><b><fmt:message key="signupSelectPackageForm.ram.header" /></b></td>
										<td style="white-space:nowrap"><ao:write name="server" property="minimumConfiguration.ram" /></td>
										<td style="white-space:nowrap"><ao:write name="server" property="maximumConfiguration.ram" /></td>
									</tr>
									<logic:notEmpty name="server" property="maximumConfiguration.disk">
										<tr class='<%= ((row2++)&1)==0 ? "aoPopupLightRow" : "aoPopupDarkRow" %>'>
											<td style="white-space:nowrap"><b><fmt:message key="signupSelectPackageForm.disk.header" /></b></td>
											<td style="white-space:nowrap">
												<logic:empty name="server" property="minimumConfiguration.disk">&#160;</logic:empty>
												<logic:notEmpty name="server" property="minimumConfiguration.disk"><ao:write name="server" property="minimumConfiguration.disk" /></logic:notEmpty>
											</td>
											<td style="white-space:nowrap"><ao:write name="server" property="maximumConfiguration.disk" /></td>
										</tr>
									</logic:notEmpty>
								</table>
							</skin:popup>
						</td>
						<td style="white-space:nowrap; text-align:center">
							<logic:empty name="server" property="minimumConfiguration.setup"><fmt:message key="signupSelectPackageForm.setup.none" /></logic:empty>
							<logic:notEmpty name="server" property="minimumConfiguration.setup"><ao:write name="server" property="minimumConfiguration.setup" /></logic:notEmpty>
						</td>
						<td style="white-space:nowrap; text-align:center"><ao:write name="server" property="minimumConfiguration.monthly" /></td>
						<td style="white-space:nowrap; text-align:center"><ao:write name="server" property="maximumConfiguration.monthly" /></td>
						<logic:equal name="serverIndex" value="0">
							<bean:size scope="request" name="servers" id="serversSize" />
							<td rowspan="<%= serversSize %>" style="white-space:nowrap">
								<html:errors bundle="/signup/ApplicationResources" property="packageDefinition" />
							</td>
						</logic:equal>
					</skin:lightDarkTableRow>
				</logic:iterate>
				<tr><td colspan="6" style="text-align:center"><ao:br /><ao:input type="submit"><fmt:message key="signupSelectServerForm.submit.label" /></ao:input><ao:br /><ao:br /></td></tr>
			</table>
		</fmt:bundle>
	</skin:popupGroup>
</skin:lightArea>
