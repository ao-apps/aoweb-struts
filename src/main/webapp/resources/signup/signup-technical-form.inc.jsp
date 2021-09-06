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

<div>
	<ao:input type="hidden" name="selectedStep" />
	<ao:script>
		function selectStep(step) {
			var form = document.forms['signupTechnicalForm'];
			form.selectedStep.value=step;
			form.submit();
		}
	</ao:script>
	<skin:lightArea>
		<ao:bundle basename="com.aoindustries.web.struts.signup.i18n.ApplicationResources">
			<table class="ao-packed">
				<thead>
					<tr><td colspan="4"><b><ao:message key="signupTechnicalForm.stepLabel" /></b><ao:br /><ao:hr /></td></tr>
					<tr><td colspan="4"><i><ao:message key="signupTechnicalForm.stepHelp" /></i><ao:br /><ao:br /></td></tr>
				</thead>
				<tbody>
					<tr>
						<td style="white-space:nowrap"><ao:message key="signup.required" /></td>
						<td style="white-space:nowrap"><ao:message key="signupTechnicalForm.baName.prompt" /></td>
						<td style="white-space:nowrap"><html:text size="32" property="baName" maxlength="255" /></td>
						<td style="white-space:nowrap"><html:errors bundle="/signup/ApplicationResources" property="baName" /></td>
					</tr>
					<tr>
						<td style="white-space:nowrap"><ao:message key="signup.notRequired" /></td>
						<td style="white-space:nowrap"><ao:message key="signupTechnicalForm.baTitle.prompt" /></td>
						<td style="white-space:nowrap"><html:text size="32" property="baTitle" maxlength="255" /></td>
						<td style="white-space:nowrap"><html:errors bundle="/signup/ApplicationResources" property="baTitle" /></td>
					</tr>
					<tr>
						<td style="white-space:nowrap"><ao:message key="signup.required" /></td>
						<td style="white-space:nowrap"><ao:message key="signupTechnicalForm.baWorkPhone.prompt" /></td>
						<td style="white-space:nowrap"><html:text size="18" property="baWorkPhone" maxlength="255" /></td>
						<td style="white-space:nowrap"><html:errors bundle="/signup/ApplicationResources" property="baWorkPhone" /></td>
					</tr>
					<tr>
						<td style="white-space:nowrap"><ao:message key="signup.notRequired" /></td>
						<td style="white-space:nowrap"><ao:message key="signupTechnicalForm.baCellPhone.prompt" /></td>
						<td style="white-space:nowrap"><html:text size="18" property="baCellPhone" maxlength="255" /></td>
						<td style="white-space:nowrap"><html:errors bundle="/signup/ApplicationResources" property="baCellPhone" /></td>
					</tr>
					<tr>
						<td style="white-space:nowrap"><ao:message key="signup.notRequired" /></td>
						<td style="white-space:nowrap"><ao:message key="signupTechnicalForm.baHomePhone.prompt" /></td>
						<td style="white-space:nowrap"><html:text size="18" property="baHomePhone" maxlength="255" /></td>
						<td style="white-space:nowrap"><html:errors bundle="/signup/ApplicationResources" property="baHomePhone" /></td>
					</tr>
					<tr>
						<td style="white-space:nowrap"><ao:message key="signup.notRequired" /></td>
						<td style="white-space:nowrap"><ao:message key="signupTechnicalForm.baFax.prompt" /></td>
						<td style="white-space:nowrap"><html:text size="18" property="baFax" maxlength="255" /></td>
						<td style="white-space:nowrap"><html:errors bundle="/signup/ApplicationResources" property="baFax" /></td>
					</tr>
					<tr>
						<td style="white-space:nowrap"><ao:message key="signup.required" /></td>
						<td style="white-space:nowrap"><ao:message key="signupTechnicalForm.baEmail.prompt" /></td>
						<td style="white-space:nowrap"><html:text size="20" property="baEmail" maxlength="255" /></td>
						<td style="white-space:nowrap"><html:errors bundle="/signup/ApplicationResources" property="baEmail" /></td>
					</tr>
					<tr><td colspan="4"><ao:br /><i><ao:message key="signupTechnicalForm.addressHelp" /></i><ao:br /><ao:br /></td></tr>
					<tr>
						<td style="white-space:nowrap"><ao:message key="signup.notRequired" /></td>
						<td style="white-space:nowrap"><ao:message key="signupTechnicalForm.baAddress1.prompt" /></td>
						<td style="white-space:nowrap"><html:text size="32" property="baAddress1" maxlength="255" /></td>
						<td style="white-space:nowrap"><html:errors bundle="/signup/ApplicationResources" property="baAddress1" /></td>
					</tr>
					<tr>
						<td style="white-space:nowrap"><ao:message key="signup.notRequired" /></td>
						<td style="white-space:nowrap"><ao:message key="signupTechnicalForm.baAddress2.prompt" /></td>
						<td style="white-space:nowrap"><html:text size="32" property="baAddress2" maxlength="255" /></td>
						<td style="white-space:nowrap"><html:errors bundle="/signup/ApplicationResources" property="baAddress2" /></td>
					</tr>
					<tr>
						<td style="white-space:nowrap"><ao:message key="signup.notRequired" /></td>
						<td style="white-space:nowrap"><ao:message key="signupTechnicalForm.baCity.prompt" /></td>
						<td style="white-space:nowrap"><html:text size="16" property="baCity" maxlength="255" /></td>
						<td style="white-space:nowrap"><html:errors bundle="/signup/ApplicationResources" property="baCity" /></td>
					</tr>
					<tr>
						<td style="white-space:nowrap"><ao:message key="signup.notRequired" /></td>
						<td style="white-space:nowrap"><ao:message key="signupTechnicalForm.baState.prompt" /></td>
						<td style="white-space:nowrap"><html:text size="5" property="baState" maxlength="255" /></td>
						<td style="white-space:nowrap"><html:errors bundle="/signup/ApplicationResources" property="baState" /></td>
					</tr>
					<tr>
						<td style="white-space:nowrap"><ao:message key="signup.notRequired" /></td>
						<td style="white-space:nowrap"><ao:message key="signupTechnicalForm.baCountry.prompt" /></td>
						<td style="white-space:nowrap">
							<html:select property="baCountry">
								<c:set var="selectedOne" value="false" />
								<c:set var="baCountry" value="${signupTechnicalForm.baCountry}" />
								<logic:iterate scope="request" name="countryOptions" id="countryOption">
									<c:choose>
										<c:when test="${!selectedOne && countryOption.code == baCountry}">
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
						<td style="white-space:nowrap"><html:errors bundle="/signup/ApplicationResources" property="baCountry" /></td>
					</tr>
					<tr>
						<td style="white-space:nowrap"><ao:message key="signup.notRequired" /></td>
						<td style="white-space:nowrap"><ao:message key="signupTechnicalForm.baZip.prompt" /></td>
						<td style="white-space:nowrap"><html:text size="10" property="baZip" maxlength="255" /></td>
						<td style="white-space:nowrap"><html:errors bundle="/signup/ApplicationResources" property="baZip" /></td>
					</tr>
					<tr><td colspan="4">&#160;</td><td></td></tr>
					<tr>
						<td style="white-space:nowrap"><ao:message key="signup.required" /></td>
						<td style="white-space:nowrap"><ao:message key="signupTechnicalForm.baUsername.prompt" /></td>
						<td style="white-space:nowrap"><html:text size="14" property="baUsername" maxlength="255" /></td>
						<td style="white-space:nowrap"><html:errors bundle="/signup/ApplicationResources" property="baUsername" /></td>
					</tr>
					<tr>
						<td style="white-space:nowrap"><ao:message key="signup.notRequired" /></td>
						<td style="white-space:nowrap"><ao:message key="signupTechnicalForm.baPassword.prompt" /></td>
						<td style="white-space:nowrap">
							<html:select property="baPassword">
								<html:options name="passwords" />
							</html:select>
						</td>
						<td style="white-space:nowrap"><html:errors bundle="/signup/ApplicationResources" property="baPassword" /></td>
					</tr>
				</tbody>
				<tfoot>
					<tr><td colspan="4" style="text-align:center"><ao:br /><ao:input type="submit" value="${ao:message('signupTechnicalForm.submit.label')}" /><ao:br /><ao:br /></td></tr>
				</tfoot>
			</table>
		</ao:bundle>
	</skin:lightArea>
</div>
