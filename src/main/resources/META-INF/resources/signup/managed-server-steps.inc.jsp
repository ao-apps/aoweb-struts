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

<skin:lightArea>
	<fmt:bundle basename="com.aoindustries.website.signup.ApplicationResources">
		<b><fmt:message key="steps.title" /></b>
		<ao:hr />
		<bean:define scope="request" name="signupSelectPackageFormComplete" id="signupSelectPackageFormComplete" type="java.lang.String" />
		<bean:define scope="request" name="signupCustomizeServerFormComplete" id="signupCustomizeServerFormComplete" type="java.lang.String" />
		<bean:define scope="request" name="signupCustomizeManagementFormComplete" id="signupCustomizeManagementFormComplete" type="java.lang.String" />
		<bean:define scope="request" name="signupOrganizationFormComplete" id="signupOrganizationFormComplete" type="java.lang.String" />
		<bean:define scope="request" name="signupTechnicalFormComplete" id="signupTechnicalFormComplete" type="java.lang.String" />
		<bean:define scope="request" name="signupBillingInformationFormComplete" id="signupBillingInformationFormComplete" type="java.lang.String" />
		<bean:define name="stepNumber" id="myStepNumber" type="java.lang.String" />
		<table cellspacing='2' cellpadding='0'>
			<tr>
				<td>
					<logic:equal name="myStepNumber" value="1"><fmt:message key="steps.arrow" /></logic:equal>
					<logic:notEqual name="myStepNumber" value="1">&#160;</logic:notEqual>
				</td>
				<td><fmt:message key="steps.1" /></td>
				<td>
					<% if(myStepNumber.equals("8")) { %>
						<fmt:message key="steps.selectServer.label" />
					<% } else { %>
						<a class="aoDarkLink" href="javascript:selectStep('<ao:write scope="request" name="actionPrefix" />');"><fmt:message key="steps.selectServer.label" /></a>
					<% } %>
				</td>
				<td>
					<logic:equal scope="request" name="signupSelectPackageFormComplete" value="true">
						<fmt:message key="steps.completed" />
					</logic:equal>
					<logic:notEqual scope="request" name="signupSelectPackageFormComplete" value="true">
						<fmt:message key="steps.incomplete" />
					</logic:notEqual>
				</td>
			</tr>
			<tr>
				<td>
					<logic:equal name="myStepNumber" value="2"><fmt:message key="steps.arrow" /></logic:equal>
					<logic:notEqual name="myStepNumber" value="2">&#160;</logic:notEqual>
				</td>
				<td><fmt:message key="steps.2" /></td>
				<td>
					<% if(myStepNumber.equals("8") || !signupSelectPackageFormComplete.equals("true")) { %>
						<fmt:message key="steps.customizeServer.label" />
					<% } else { %>
						<a class="aoDarkLink" href="javascript:selectStep('<ao:write scope="request" name="actionPrefix" />-2');"><fmt:message key="steps.customizeServer.label" /></a>
					<% } %>
				</td>
				<td>
					<logic:equal scope="request" name="signupCustomizeServerFormComplete" value="true">
						<fmt:message key="steps.completed" />
					</logic:equal>
					<logic:notEqual scope="request" name="signupCustomizeServerFormComplete" value="true">
						<fmt:message key="steps.incomplete" />
					</logic:notEqual>
				</td>
			</tr>
			<tr>
				<td>
					<logic:equal name="myStepNumber" value="3"><fmt:message key="steps.arrow" /></logic:equal>
					<logic:notEqual name="myStepNumber" value="3">&#160;</logic:notEqual>
				</td>
				<td><fmt:message key="steps.3" /></td>
				<td>
					<% if(
							myStepNumber.equals("8")
							|| !signupSelectPackageFormComplete.equals("true")
							|| !signupCustomizeServerFormComplete.equals("true")
					   ) { %>
						<fmt:message key="steps.customizeManagement.label" />
					<% } else { %>
						<a class="aoDarkLink" href="javascript:selectStep('<ao:write scope="request" name="actionPrefix" />-3');"><fmt:message key="steps.customizeManagement.label" /></a>
					<% } %>
				</td>
				<td>
					<logic:equal scope="request" name="signupCustomizeManagementFormComplete" value="true">
						<fmt:message key="steps.completed" />
					</logic:equal>
					<logic:notEqual scope="request" name="signupCustomizeManagementFormComplete" value="true">
						<fmt:message key="steps.incomplete" />
					</logic:notEqual>
				</td>
			</tr>
			<tr>
				<td>
					<logic:equal name="myStepNumber" value="4"><fmt:message key="steps.arrow" /></logic:equal>
					<logic:notEqual name="myStepNumber" value="4">&#160;</logic:notEqual>
				</td>
				<td><fmt:message key="steps.4" /></td>
				<td>
					<% if(
							myStepNumber.equals("8")
							|| !signupSelectPackageFormComplete.equals("true")
							|| !signupCustomizeServerFormComplete.equals("true")
							|| !signupCustomizeManagementFormComplete.equals("true")
					   ) { %>
						<fmt:message key="steps.organizationInfo.label" />
					<% } else { %>
						<a class="aoDarkLink" href="javascript:selectStep('<ao:write scope="request" name="actionPrefix" />-4');"><fmt:message key="steps.organizationInfo.label" /></a>
					<% } %>
				</td>
				<td>
					<logic:equal scope="request" name="signupOrganizationFormComplete" value="true">
						<fmt:message key="steps.completed" />
					</logic:equal>
					<logic:notEqual scope="request" name="signupOrganizationFormComplete" value="true">
						<fmt:message key="steps.incomplete" />
					</logic:notEqual>
				</td>
			</tr>
			<tr>
				<td>
					<logic:equal name="myStepNumber" value="5"><fmt:message key="steps.arrow" /></logic:equal>
					<logic:notEqual name="myStepNumber" value="5">&#160;</logic:notEqual>
				</td>
				<td><fmt:message key="steps.5" /></td>
				<td>
					<% if(
							myStepNumber.equals("8")
							|| !signupSelectPackageFormComplete.equals("true")
							|| !signupCustomizeServerFormComplete.equals("true")
							|| !signupCustomizeManagementFormComplete.equals("true")
							|| !signupOrganizationFormComplete.equals("true")
					   ) { %>
						<fmt:message key="steps.technicalInfo.label" />
					<% } else { %>
						<a class="aoDarkLink" href="javascript:selectStep('<ao:write scope="request" name="actionPrefix" />-5');"><fmt:message key="steps.technicalInfo.label" /></a>
					<% } %>
				</td>
				<td>
					<logic:equal scope="request" name="signupTechnicalFormComplete" value="true">
						<fmt:message key="steps.completed" />
					</logic:equal>
					<logic:notEqual scope="request" name="signupTechnicalFormComplete" value="true">
						<fmt:message key="steps.incomplete" />
					</logic:notEqual>
				</td>
			</tr>
			<tr>
				<td>
					<logic:equal name="myStepNumber" value="6"><fmt:message key="steps.arrow" /></logic:equal>
					<logic:notEqual name="myStepNumber" value="6">&#160;</logic:notEqual>
				</td>
				<td><fmt:message key="steps.6" /></td>
				<td>
					<% if(
							myStepNumber.equals("8")
							|| !signupSelectPackageFormComplete.equals("true")
							|| !signupCustomizeServerFormComplete.equals("true")
							|| !signupCustomizeManagementFormComplete.equals("true")
							|| !signupOrganizationFormComplete.equals("true")
							|| !signupTechnicalFormComplete.equals("true")
					   ) { %>
						<fmt:message key="steps.billingInformation.label" />
					<% } else { %>
						<a class="aoDarkLink" href="javascript:selectStep('<ao:write scope="request" name="actionPrefix" />-6');"><fmt:message key="steps.billingInformation.label" /></a>
					<% } %>
				</td>
				<td>
					<logic:equal scope="request" name="signupBillingInformationFormComplete" value="true">
						<fmt:message key="steps.completed" />
					</logic:equal>
					<logic:notEqual scope="request" name="signupBillingInformationFormComplete" value="true">
						<fmt:message key="steps.incomplete" />
					</logic:notEqual>
				</td>
			</tr>
			<tr>
				<td>
					<logic:equal name="myStepNumber" value="7"><fmt:message key="steps.arrow" /></logic:equal>
					<logic:notEqual name="myStepNumber" value="7">&#160;</logic:notEqual>
				</td>
				<td><fmt:message key="steps.7" /></td>
				<td>
					<% if(
							myStepNumber.equals("8")
							|| !signupSelectPackageFormComplete.equals("true")
							|| !signupCustomizeServerFormComplete.equals("true")
							|| !signupCustomizeManagementFormComplete.equals("true")
							|| !signupOrganizationFormComplete.equals("true")
							|| !signupTechnicalFormComplete.equals("true")
							|| !signupBillingInformationFormComplete.equals("true")
					   ) { %>
						<fmt:message key="steps.confirmation.label" />
					<% } else { %>
						<a class="aoDarkLink" href="javascript:selectStep('<ao:write scope="request" name="actionPrefix" />-7');"><fmt:message key="steps.confirmation.label" /></a>
					<% } %>
				</td>
				<td>
					<logic:equal name="myStepNumber" value="8">
						<fmt:message key="steps.completed" />
					</logic:equal>
					<logic:notEqual name="myStepNumber" value="8">
						<fmt:message key="steps.incomplete" />
					</logic:notEqual>
				</td>
			</tr>
			<tr>
				<td>
					<logic:equal name="myStepNumber" value="8"><fmt:message key="steps.arrow" /></logic:equal>
					<logic:notEqual name="myStepNumber" value="8">&#160;</logic:notEqual>
				</td>
				<td><fmt:message key="steps.8" /></td>
				<td><fmt:message key="steps.finished.label" /></td>
				<td>&#160;</td>
			</tr>
		</table>
	</fmt:bundle>
</skin:lightArea>
