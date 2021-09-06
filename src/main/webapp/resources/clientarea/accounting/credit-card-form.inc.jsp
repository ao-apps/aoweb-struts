<%--
aoweb-struts - Template webapp for legacy Struts-based site framework with AOServ Platform control panels.
Copyright (C) 2007-2009, 2015, 2016, 2019, 2020, 2021  AO Industries, Inc.
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

<ao:bundle basename="com.aoindustries.web.struts.clientarea.accounting.i18n.ApplicationResources">
	<tr>
		<td style="white-space:nowrap;text-align:center" colspan="4"><%@include file="_credit-card-images.inc.jsp" %></td>
	</tr>
	<tr>
		<td style="white-space:nowrap"><ao:message key="creditCardForm.required.yes" /></td>
		<th style="white-space:nowrap;text-align:left"><ao:message key="creditCardForm.account.prompt" /></th>
		<td style="white-space:nowrap"><html:hidden property="account" write="true" /></td>
		<td style="white-space:nowrap"><html:errors bundle="/clientarea/accounting/ApplicationResources" property="account" /></td>
	</tr>
	<tr>
		<td style="white-space:nowrap"><ao:message key="creditCardForm.required.yes" /></td>
		<th style="white-space:nowrap;text-align:left"><ao:message key="creditCardForm.firstName.prompt" /></th>
		<td style="white-space:nowrap"><html:text property="firstName" size="16" /></td>
		<td style="white-space:nowrap"><html:errors bundle="/clientarea/accounting/ApplicationResources" property="firstName" /></td>
	</tr>
	<tr>
		<td style="white-space:nowrap"><ao:message key="creditCardForm.required.yes" /></td>
		<th style="white-space:nowrap;text-align:left"><ao:message key="creditCardForm.lastName.prompt" /></th>
		<td style="white-space:nowrap"><html:text property="lastName" size="16" /></td>
		<td style="white-space:nowrap"><html:errors bundle="/clientarea/accounting/ApplicationResources" property="lastName" /></td>
	</tr>
	<tr>
		<td style="white-space:nowrap"><ao:message key="creditCardForm.required.no" /></td>
		<th style="white-space:nowrap;text-align:left"><ao:message key="creditCardForm.companyName.prompt" /></th>
		<td style="white-space:nowrap"><html:text property="companyName" size="32" /></td>
		<td style="white-space:nowrap"><html:errors bundle="/clientarea/accounting/ApplicationResources" property="companyName" /></td>
	</tr>
	<tr>
		<td style="white-space:nowrap"><ao:message key="creditCardForm.required.yes" /></td>
		<th style="white-space:nowrap;text-align:left"><ao:message key="creditCardForm.cardNumber.prompt" /></th>
		<td style="white-space:nowrap"><html:text property="cardNumber" size="20" /></td>
		<td style="white-space:nowrap"><html:errors bundle="/clientarea/accounting/ApplicationResources" property="cardNumber" /></td>
	</tr>
	<tr>
		<td style="white-space:nowrap"><ao:message key="creditCardForm.required.yes" /></td>
		<th style="white-space:nowrap;text-align:left"><ao:message key="creditCardForm.expirationDate.prompt" /></th>
		<td style="white-space:nowrap">
			<ao:bundle basename="com.aoindustries.web.struts.signup.i18n.ApplicationResources">
				<html:select property="expirationMonth">
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
				</html:select>&#160;/&#160;<html:select property="expirationYear">
					<html:option value=""><ao:message key="signupBillingInformationForm.billingExpirationYear.none.display" /></html:option>
					<html:options name="expirationYears" />
				</html:select>
			</ao:bundle>
		</td>
		<td style="white-space:nowrap"><html:errors bundle="/clientarea/accounting/ApplicationResources" property="expirationDate" /></td>
	</tr>
	<tr>
		<td style="white-space:nowrap"><ao:message key="creditCardForm.required.yes" /></td>
		<th style="white-space:nowrap;text-align:left"><ao:message key="creditCardForm.cardCode.prompt" /></th>
		<td style="white-space:nowrap"><html:text property="cardCode" size="5" /></td>
		<td style="white-space:nowrap"><html:errors bundle="/clientarea/accounting/ApplicationResources" property="cardCode" /></td>
	</tr>
	<tr>
		<td style="white-space:nowrap"><ao:message key="creditCardForm.required.yes" /></td>
		<th style="white-space:nowrap;text-align:left"><ao:message key="creditCardForm.streetAddress1.prompt" /></th>
		<td style="white-space:nowrap"><html:text property="streetAddress1" size="32" /></td>
		<td style="white-space:nowrap"><html:errors bundle="/clientarea/accounting/ApplicationResources" property="streetAddress1" /></td>
	</tr>
	<tr>
		<td style="white-space:nowrap"><ao:message key="creditCardForm.required.no" /></td>
		<th style="white-space:nowrap;text-align:left"><ao:message key="creditCardForm.streetAddress2.prompt" /></th>
		<td style="white-space:nowrap"><html:text property="streetAddress2" size="32" /></td>
		<td style="white-space:nowrap"><html:errors bundle="/clientarea/accounting/ApplicationResources" property="streetAddress2" /></td>
	</tr>
	<tr>
		<td style="white-space:nowrap"><ao:message key="creditCardForm.required.yes" /></td>
		<th style="white-space:nowrap;text-align:left"><ao:message key="creditCardForm.city.prompt" /></th>
		<td style="white-space:nowrap"><html:text property="city" size="16" /></td>
		<td style="white-space:nowrap"><html:errors bundle="/clientarea/accounting/ApplicationResources" property="city" /></td>
	</tr>
	<tr>
		<td style="white-space:nowrap"><ao:message key="creditCardForm.required.yes" /></td>
		<th style="white-space:nowrap;text-align:left"><ao:message key="creditCardForm.state.prompt" /></th>
		<td style="white-space:nowrap"><html:text property="state" size="5" /></td>
		<td style="white-space:nowrap"><html:errors bundle="/clientarea/accounting/ApplicationResources" property="state" /></td>
	</tr>
	<tr>
		<td style="white-space:nowrap"><ao:message key="creditCardForm.required.yes" /></td>
		<th style="white-space:nowrap;text-align:left"><ao:message key="creditCardForm.countryCode.prompt" /></th>
		<td style="white-space:nowrap">
			<html:select property="countryCode">
				<c:set var="didSelected" value="false" />
				<logic:iterate scope="request" name="countryOptions" id="countryOption">
					<c:set var="selected" value="${countryOption.code == creditCardForm.countryCode}" />
					<ao:option value="${countryOption.code}" selected="${selected && !didOne}"><ao:out value="${countryOption.name}" /></ao:option>
					<c:if test="${selected}">
						<c:set var="didOne" value="true" />
					</c:if>
				</logic:iterate>
			</html:select>
		</td>
		<td style="white-space:nowrap"><html:errors bundle="/clientarea/accounting/ApplicationResources" property="countryCode" /></td>
	</tr>
	<tr>
		<td style="white-space:nowrap"><ao:message key="creditCardForm.required.yes" /></td>
		<th style="white-space:nowrap;text-align:left"><ao:message key="creditCardForm.postalCode.prompt" /></th>
		<td style="white-space:nowrap"><html:text property="postalCode" size="10" /></td>
		<td style="white-space:nowrap"><html:errors bundle="/clientarea/accounting/ApplicationResources" property="postalCode" /></td>
	</tr>
	<tr>
		<td style="white-space:nowrap"><ao:message key="creditCardForm.required.no" /></td>
		<th style="white-space:nowrap;text-align:left"><ao:message key="creditCardForm.description.prompt" /></th>
		<td style="white-space:nowrap"><html:text property="description" size="32" /></td>
		<td style="white-space:nowrap"><html:errors bundle="/clientarea/accounting/ApplicationResources" property="description" /></td>
	</tr>
</ao:bundle>
