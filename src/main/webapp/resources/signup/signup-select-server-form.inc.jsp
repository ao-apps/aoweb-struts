<%--
aoweb-struts - Template webapp for legacy Struts-based site framework with AOServ Platform control panels.
Copyright (C) 2000-2009, 2016, 2019, 2020, 2021  AO Industries, Inc.
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

<skin:lightArea>
	<skin:popupGroup>
		<ao:bundle basename="com.aoindustries.web.struts.signup.i18n.ApplicationResources">
			<table class="ao-no-border">
				<thead>
					<tr><td colspan="6"><b><ao:message key="signupSelectServerForm.stepLabel" /></b><ao:br /><ao:hr /></td></tr>
					<tr><td colspan="6"><i><ao:message key="signupSelectServerForm.stepHelp" /></i><ao:br /><ao:br /></td></tr>
					<tr>
						<th style='white-space:pre'><ao:message key="signupSelectPackageForm.select.header" /></th>
						<th style='white-space:pre'><ao:message key="signupSelectServerForm.packageDefinition.header" /></th>
						<th style='white-space:pre'><ao:message key="signupSelectPackageForm.setup.header" /></th>
						<th style='white-space:pre'><ao:message key="signupSelectPackageForm.minimumMonthlyRate.header" /></th>
						<th style='white-space:pre'><ao:message key="signupSelectPackageForm.maximumMonthlyRate.header" /></th>
						<th style='white-space:nowrap'>&#160;</th>
					</tr>
				</thead>
				<tbody>
					<logic:iterate scope="request" name="servers" id="server" indexId="serverIndex">
						<bean:define name="server" property="minimumConfiguration.packageDefinition" id="packageDefinition" type="java.lang.Integer" />
						<skin:lightDarkTableRow>
							<td style="white-space:nowrap"><html:radio styleId="packageDefinition_${packageDefinition}" property="packageDefinition" idName="server" value="minimumConfiguration.packageDefinition" /></td>
							<td style="white-space:nowrap">
								<label for="packageDefinition_${fn:escapeXml(packageDefinition)}"><b><ao:write name="server" property="minimumConfiguration.name" /></b></label>
								<skin:popup>
									<table class="ao-no-border" style='font-size:80%;'>
										<thead>
											<tr>
												<td colspan="3" class='aoPopupLightRow' style='font-size:100%;'>
													<table class="ao-packed" style="width:100%">
														<tbody>
															<tr>
																<th class='aoPopupLightRow'><ao:write name="server" property="minimumConfiguration.name" /></th>
																<td class='aoPopupLightRow' style="text-align:right"><skin:popupClose /></td>
															</tr>
														</tbody>
													</table>
												</td>
											</tr>
											<tr>
												<th class='aoPopupDarkRow'><ao:message key="signupSelectPackageForm.resource.header" /></th>
												<th class='aoPopupDarkRow'><ao:message key="signupSelectPackageForm.minimum.header" /></th>
												<th class='aoPopupDarkRow'><ao:message key="signupSelectPackageForm.maximum.header" /></th>
											</tr>
										</thead>
										<tbody>
											<% int row2 = 0; %>
											<tr class='<%= ((row2++)&1)==0 ? "aoPopupLightRow" : "aoPopupDarkRow" %>'>
												<td style="white-space:nowrap"><b><ao:message key="signupSelectPackageForm.cpu.header" /></b></td>
												<td style="white-space:nowrap"><ao:write name="server" property="minimumConfiguration.cpu" type="application/xhtml+xml" /></td>
												<td style="white-space:nowrap"><ao:write name="server" property="maximumConfiguration.cpu" type="application/xhtml+xml" /></td>
											</tr>
											<tr class='<%= ((row2++)&1)==0 ? "aoPopupLightRow" : "aoPopupDarkRow" %>'>
												<td style="white-space:nowrap"><b><ao:message key="signupSelectPackageForm.ram.header" /></b></td>
												<td style="white-space:nowrap"><ao:write name="server" property="minimumConfiguration.ram" /></td>
												<td style="white-space:nowrap"><ao:write name="server" property="maximumConfiguration.ram" /></td>
											</tr>
											<logic:notEmpty name="server" property="maximumConfiguration.disk">
												<tr class='<%= ((row2++)&1)==0 ? "aoPopupLightRow" : "aoPopupDarkRow" %>'>
													<td style="white-space:nowrap"><b><ao:message key="signupSelectPackageForm.disk.header" /></b></td>
													<td style="white-space:nowrap">
														<logic:empty name="server" property="minimumConfiguration.disk">&#160;</logic:empty>
														<logic:notEmpty name="server" property="minimumConfiguration.disk"><ao:write name="server" property="minimumConfiguration.disk" /></logic:notEmpty>
													</td>
													<td style="white-space:nowrap"><ao:write name="server" property="maximumConfiguration.disk" /></td>
												</tr>
											</logic:notEmpty>
										</tbody>
									</table>
								</skin:popup>
							</td>
							<td style="white-space:nowrap; text-align:center">
								<logic:empty name="server" property="minimumConfiguration.setup"><ao:message key="signupSelectPackageForm.setup.none" /></logic:empty>
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
				</tbody>
				<tfoot>
					<tr><td colspan="6" style="text-align:center"><ao:br /><ao:input type="submit" value="${ao:message('signupSelectServerForm.submit.label')}" /><ao:br /><ao:br /></td></tr>
				</tfoot>
			</table>
		</ao:bundle>
	</skin:popupGroup>
</skin:lightArea>
