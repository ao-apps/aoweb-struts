<%--
aoweb-struts-resources - Template webapp for legacy Struts-based site framework with AOServ Platform control panels.
Copyright (C) 2007-2009, 2016, 2019, 2020, 2021  AO Industries, Inc.
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

<div>
	<ao:input type="hidden" name="selectedStep" />
	<ao:script>
		function selectStep(step) {
			var form = document.forms['signupOrganizationForm'];
			form.selectedStep.value=step;
			form.submit();
		}
	</ao:script>
	<skin:lightArea>
		<ao:bundle basename="com.aoindustries.web.struts.signup.i18n.ApplicationResources">
			<table class="ao-packed">
				<thead>
					<tr><td colspan="4"><b><ao:message key="signupOrganizationForm.stepLabel" /></b><ao:br /><ao:hr /></td></tr>
					<tr><td colspan="4"><i><ao:message key="signupOrganizationForm.stepHelp" /></i><ao:br /><ao:br /></td></tr>
				</thead>
				<tbody>
					<tr>
						<td style="white-space:nowrap"><ao:message key="signup.required" /></td>
						<td style="white-space:nowrap"><ao:message key="signupOrganizationForm.organizationName.prompt" /></td>
						<td style="white-space:nowrap"><html:text size="32" property="organizationName" maxlength="255" /></td>
						<td style="white-space:nowrap"><html:errors bundle="/signup/ApplicationResources" property="organizationName" /></td>
					</tr>
					<tr>
						<td style="white-space:nowrap"><ao:message key="signup.required" /></td>
						<td style="white-space:nowrap"><ao:message key="signupOrganizationForm.organizationPhone.prompt" /></td>
						<td style="white-space:nowrap"><html:text size="18" property="organizationPhone" maxlength="255" /></td>
						<td style="white-space:nowrap"><html:errors bundle="/signup/ApplicationResources" property="organizationPhone" /></td>
					</tr>
					<tr>
						<td style="white-space:nowrap"><ao:message key="signup.notRequired" /></td>
						<td style="white-space:nowrap"><ao:message key="signupOrganizationForm.organizationFax.prompt" /></td>
						<td style="white-space:nowrap"><html:text size="18" property="organizationFax" maxlength="255" /></td>
						<td style="white-space:nowrap"><html:errors bundle="/signup/ApplicationResources" property="organizationFax" /></td>
					</tr>
					<tr><td colspan="4">&#160;</td></tr>
					<tr>
						<td style="white-space:nowrap"><ao:message key="signup.required" /></td>
						<td style="white-space:nowrap"><ao:message key="signupOrganizationForm.organizationAddress1.prompt" /></td>
						<td style="white-space:nowrap"><html:text size="32" property="organizationAddress1" maxlength="255" /></td>
						<td style="white-space:nowrap"><html:errors bundle="/signup/ApplicationResources" property="organizationAddress1" /></td>
					</tr>
					<tr>
						<td style="white-space:nowrap"><ao:message key="signup.notRequired" /></td>
						<td style="white-space:nowrap"><ao:message key="signupOrganizationForm.organizationAddress2.prompt" /></td>
						<td style="white-space:nowrap"><html:text size="32" property="organizationAddress2" maxlength="255" /></td>
						<td style="white-space:nowrap"><html:errors bundle="/signup/ApplicationResources" property="organizationAddress2" /></td>
					</tr>
					<tr>
						<td style="white-space:nowrap"><ao:message key="signup.required" /></td>
						<td style="white-space:nowrap"><ao:message key="signupOrganizationForm.organizationCity.prompt" /></td>
						<td style="white-space:nowrap"><html:text size="16" property="organizationCity" maxlength="255" /></td>
						<td style="white-space:nowrap"><html:errors bundle="/signup/ApplicationResources" property="organizationCity" /></td>
					</tr>
					<tr>
						<td style="white-space:nowrap"><ao:message key="signup.notRequired" /></td>
						<td style="white-space:nowrap"><ao:message key="signupOrganizationForm.organizationState.prompt" /></td>
						<td style="white-space:nowrap"><html:text size="5" property="organizationState" maxlength="255" /></td>
						<td style="white-space:nowrap"><html:errors bundle="/signup/ApplicationResources" property="organizationState" /></td>
					</tr>
					<tr>
						<td style="white-space:nowrap"><ao:message key="signup.required" /></td>
						<td style="white-space:nowrap"><ao:message key="signupOrganizationForm.organizationCountry.prompt" /></td>
						<td style="white-space:nowrap">
							<html:select property="organizationCountry">
								<c:set var="selectedOne" value="false" />
								<c:set var="organizationCountry" value="${signupOrganizationForm.organizationCountry}" />
								<logic:iterate scope="request" name="countryOptions" id="countryOption">
									<c:choose>
										<c:when test="${!selectedOne && countryOption.code == organizationCountry}">
											<ao:option value="${countryOption.code}" selected="true"><ao:out value="${countryOption.name}" /></ao:option>
											<c:set var="selectedOne" value="true" />
										</c:when>
										<c:otherwise>
											<ao:option value="${countryOption.code}"><ao:out value="${countryOption.name}" /></ao:option>
										</c:otherwise>
									</c:choose>
								</logic:iterate>
							</html:select>
						</td>
						<td style="white-space:nowrap"><html:errors bundle="/signup/ApplicationResources" property="organizationCountry" /></td>
					</tr>
					<tr>
						<td style="white-space:nowrap"><ao:message key="signup.notRequired" /></td>
						<td style="white-space:nowrap"><ao:message key="signupOrganizationForm.organizationZip.prompt" /></td>
						<td style="white-space:nowrap"><html:text size="10" property="organizationZip" maxlength="255" /></td>
						<td style="white-space:nowrap"><html:errors bundle="/signup/ApplicationResources" property="organizationZip" /></td>
					</tr>
				</tbody>
				<tfoot>
					<tr><td colspan="4" style="text-align:center"><ao:br /><ao:input type="submit" value="${ao:message('signupOrganizationForm.submit.label')}" /><ao:br /><ao:br /></td></tr>
				</tfoot>
			</table>
		 </ao:bundle>
	</skin:lightArea>
</div>
