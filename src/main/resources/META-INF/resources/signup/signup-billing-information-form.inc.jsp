<%--
aoweb-struts-resources - Web resources for legacy Struts-based site framework with AOServ Platform control panels.
Copyright (C) 2007-2009, 2016, 2019, 2020  AO Industries, Inc.
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
			var form = document.forms['signupBillingInformationForm'];
			form.selectedStep.value=step;
			form.submit();
		}
	</ao:script>
	<skin:lightArea>
		<ao:bundle basename="com.aoindustries.website.signup.ApplicationResources">
			<table class="ao-packed">
				<thead>
					<tr><td colspan="4"><b><ao:message key="dedicated5.stepLabel" /></b><ao:br /><ao:hr /></td></tr>
					<tr><td colspan="4"><i><ao:message key="dedicated5.stepHelp" /></i><ao:br /><ao:br /></td></tr>
				</thead>
				<tbody>
					<tr>
						<td style="white-space:nowrap"><ao:message key="signup.required" /></td>
						<td style="white-space:nowrap"><ao:message key="signupBillingInformationForm.billingContact.prompt" /></td>
						<td style="white-space:nowrap"><html:text size="32" property="billingContact" maxlength="255" /></td>
						<td style="white-space:nowrap"><html:errors bundle="/signup/ApplicationResources" property="billingContact" /></td>
					</tr>
					<tr>
						<td style="white-space:nowrap"><ao:message key="signup.required" /></td>
						<td style="white-space:nowrap"><ao:message key="signupBillingInformationForm.billingEmail.prompt" /></td>
						<td style="white-space:nowrap"><html:text size="32" property="billingEmail" maxlength="255" /></td>
						<td style="white-space:nowrap"><html:errors bundle="/signup/ApplicationResources" property="billingEmail" /></td>
					</tr>
					<tr><td colspan="4">&#160;</td></tr>
					<tr><td colspan="4" style="text-align:center"><%@include file="/clientarea/accounting/_credit-card-images.inc.jsp" %></td></tr>
					<tr><td colspan="4">&#160;</td></tr>
					<tr>
						<td style="white-space:nowrap"><ao:message key="signup.required" /></td>
						<td style="white-space:nowrap"><ao:message key="signupBillingInformationForm.billingCardholderName.prompt" /></td>
						<td style="white-space:nowrap"><html:text size="32" property="billingCardholderName" maxlength="255" /></td>
						<td style="white-space:nowrap"><html:errors bundle="/signup/ApplicationResources" property="billingCardholderName" /></td>
					</tr>
					<tr>
						<td style="white-space:nowrap"><ao:message key="signup.required" /></td>
						<td style="white-space:nowrap"><ao:message key="signupBillingInformationForm.billingCardNumber.prompt" /></td>
						<td style="white-space:nowrap"><html:text size="32" property="billingCardNumber" maxlength="255" /></td>
						<td style="white-space:nowrap"><html:errors bundle="/signup/ApplicationResources" property="billingCardNumber" /></td>
					</tr>
					<tr>
						<td style="white-space:nowrap"><ao:message key="signup.required" /></td>
						<td style="white-space:nowrap"><ao:message key="signupBillingInformationForm.billingExpirationDate.prompt" /></td>
						<td style="white-space:nowrap">
							<html:select property="billingExpirationMonth">
								<html:option value=""><ao:message key="signupBillingInformationForm.billingExpirationMonth.none.display" /></html:option>
								<html:option value="01"><ao:message key="signupBillingInformationForm.billingExpirationMonth.jan.display" /></html:option>
								<html:option value="02"><ao:message key="signupBillingInformationForm.billingExpirationMonth.feb.display" /></html:option>
								<html:option value="03"><ao:message key="signupBillingInformationForm.billingExpirationMonth.mar.display" /></html:option>
								<html:option value="04"><ao:message key="signupBillingInformationForm.billingExpirationMonth.apr.display" /></html:option>
								<html:option value="05"><ao:message key="signupBillingInformationForm.billingExpirationMonth.may.display" /></html:option>
								<html:option value="06"><ao:message key="signupBillingInformationForm.billingExpirationMonth.jun.display" /></html:option>
								<html:option value="07"><ao:message key="signupBillingInformationForm.billingExpirationMonth.jul.display" /></html:option>
								<html:option value="08"><ao:message key="signupBillingInformationForm.billingExpirationMonth.aug.display" /></html:option>
								<html:option value="09"><ao:message key="signupBillingInformationForm.billingExpirationMonth.sep.display" /></html:option>
								<html:option value="10"><ao:message key="signupBillingInformationForm.billingExpirationMonth.oct.display" /></html:option>
								<html:option value="11"><ao:message key="signupBillingInformationForm.billingExpirationMonth.nov.display" /></html:option>
								<html:option value="12"><ao:message key="signupBillingInformationForm.billingExpirationMonth.dec.display" /></html:option>
							</html:select>&#160;/&#160;<html:select property="billingExpirationYear">
								<html:option value=""><ao:message key="signupBillingInformationForm.billingExpirationYear.none.display" /></html:option>
								<html:options name="billingExpirationYears" />
							</html:select>
						</td>
						<td style="white-space:nowrap"><html:errors bundle="/signup/ApplicationResources" property="billingExpirationDate" /></td>
					</tr>
					<%-- TODO: Add card security code, matching fields and labels used in aoweb --%>
					<tr><td colspan="4">&#160;</td></tr>
					<tr><td colspan="4"><i><ao:message key="dedicated5.cardAddressHelp" /></i></td></tr>
					<tr><td colspan="4">&#160;</td></tr>
					<tr>
						<td style="white-space:nowrap"><ao:message key="signup.required" /></td>
						<td style="white-space:nowrap"><ao:message key="signupBillingInformationForm.billingStreetAddress.prompt" /></td>
						<td style="white-space:nowrap"><html:text size="32" property="billingStreetAddress" maxlength="255" /></td>
						<td style="white-space:nowrap"><html:errors bundle="/signup/ApplicationResources" property="billingStreetAddress" /></td>
					</tr>
					<tr>
						<td style="white-space:nowrap"><ao:message key="signup.required" /></td>
						<td style="white-space:nowrap"><ao:message key="signupBillingInformationForm.billingCity.prompt" /></td>
						<td style="white-space:nowrap"><html:text size="14" property="billingCity" maxlength="255" /></td>
						<td style="white-space:nowrap"><html:errors bundle="/signup/ApplicationResources" property="billingCity" /></td>
					</tr>
					<tr>
						<td style="white-space:nowrap"><ao:message key="signup.required" /></td>
						<td style="white-space:nowrap"><ao:message key="signupBillingInformationForm.billingState.prompt" /></td>
						<td style="white-space:nowrap"><html:text size="5" property="billingState" maxlength="255" /></td>
						<td style="white-space:nowrap"><html:errors bundle="/signup/ApplicationResources" property="billingState" /></td>
					</tr>
					<tr>
						<td style="white-space:nowrap"><ao:message key="signup.required" /></td>
						<td style="white-space:nowrap"><ao:message key="signupBillingInformationForm.billingZip.prompt" /></td>
						<td style="white-space:nowrap"><html:text size="10" property="billingZip" maxlength="255" /></td>
						<td style="white-space:nowrap"><html:errors bundle="/signup/ApplicationResources" property="billingZip" /></td>
					</tr>
					<tr><td colspan="4">&#160;</td></tr>
					<tr valign='top'>
						<td style="white-space:nowrap" colspan="2">&#160;</td>
						<td style="white-space:nowrap"><label><html:checkbox property="billingUseMonthly" /><ao:message key="signupBillingInformationForm.billingUseMonthly.value" /></label><ao:br /><ao:br /></td>
						<td style="white-space:nowrap"><html:errors bundle="/signup/ApplicationResources" property="billingUseMonthly" /></td>
					</tr>
					<tr valign='top'>
						<td style="white-space:nowrap" colspan="2">&#160;</td>
						<td style="white-space:nowrap"><label><html:checkbox property="billingPayOneYear" /><ao:message key="signupBillingInformationForm.billingPayOneYear.value" /></label></td>
						<td style="white-space:nowrap"><html:errors bundle="/signup/ApplicationResources" property="billingPayOneYear" /></td>
					</tr>
				</tbody>
				<tfoot>
					<tr><td colspan="4" style="text-align:center"><ao:br /><ao:input type="submit" value="${ao:message('dedicated5.submit.label')}" /><ao:br /><ao:br /></td></tr>
				</tfoot>
			</table>
		</ao:bundle>
	</skin:lightArea>
</div>
