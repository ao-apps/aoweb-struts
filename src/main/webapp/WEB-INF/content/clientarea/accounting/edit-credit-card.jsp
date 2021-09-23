<%--
aoweb-struts - Template webapp for legacy Struts-based site framework with AOServ Platform control panels.
Copyright (C) 2007-2009, 2015, 2016, 2018, 2019, 2020, 2021  AO Industries, Inc.
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
<%@include file="/WEB-INF/taglibs.jspf" %>

<ao:bundle basename="com.aoindustries.web.struts.clientarea.accounting.i18n.ApplicationResources">
	<skin:path>
		/clientarea/accounting/edit-credit-card.do
		<ao:param name="persistenceId" value="${editCreditCardForm.persistenceId}" />
	</skin:path>
	<logic:equal name="siteSettings" property="brand.aowebStrutsNoindex" value="true"><skin:meta name="robots">noindex</skin:meta></logic:equal>
	<skin:title>
		<logic:notEqual name="editCreditCardForm" property="isActive" value="false">
			<ao:message key="editCreditCard.title.edit" />
		</logic:notEqual>
		<logic:equal name="editCreditCardForm" property="isActive" value="false">
			<ao:message key="editCreditCard.title.reactivate" />
		</logic:equal>
	</skin:title>
	<skin:navImageAlt>
		<logic:notEqual name="editCreditCardForm" property="isActive" value="false">
			<ao:message key="editCreditCard.navImageAlt.edit" />
		</logic:notEqual>
		<logic:equal name="editCreditCardForm" property="isActive" value="false">
			<ao:message key="editCreditCard.navImageAlt.reactivate" />
		</logic:equal>
	</skin:navImageAlt>
	<skin:keywords><ao:message key="editCreditCard.keywords" /></skin:keywords>
	<skin:description><ao:message key="editCreditCard.description" /></skin:description>
	<%@include file="add-parents.jspf" %>
	<skin:parent><%@include file="credit-card-manager.meta.jspf" %></skin:parent>
	<skin:skin formtype="struts1">
		<skin:content width="600">
			<skin:contentTitle>
				<logic:notEqual name="editCreditCardForm" property="isActive" value="false">
					<ao:message key="editCreditCard.title.edit" />
				</logic:notEqual>
				<logic:equal name="editCreditCardForm" property="isActive" value="false">
					<ao:message key="editCreditCard.title.reactivate" />
				</logic:equal>
			</skin:contentTitle>
			<skin:contentHorizontalDivider />
			<skin:contentLine>
				<logic:present scope="request" name="permissionDenied">
					<%@include file="../../permission-denied.jspf" %>
				</logic:present>
				<logic:notPresent scope="request" name="permissionDenied">
					<bean:define scope="request" name="creditCard" id="creditCard" type="com.aoindustries.aoserv.client.payment.CreditCard" />
					<skin:popupGroup>
						<html:form action="/edit-credit-card-completed"><div>
							<html:hidden property="persistenceId" />
							<html:hidden property="isActive" />
							<skin:lightArea>
								<b>
									<logic:notEqual name="editCreditCardForm" property="isActive" value="false">
										<ao:message key="editCreditCard.form.title.edit" />
									</logic:notEqual>
									<logic:equal name="editCreditCardForm" property="isActive" value="false">
										<ao:message key="editCreditCard.form.title.reactivate" />
									</logic:equal>
								</b>
								<ao:hr />
								<table class="ao-no-border">
									<thead>
										<tr>
											<td style="white-space:nowrap;text-align:center" colspan="4"><%@include file="credit-card-images.jspf" %></td>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td style="white-space:nowrap"><ao:message key="editCreditCard.required.yes" /></td>
											<td style="white-space:nowrap"><ao:message key="creditCardForm.account.prompt" /></td>
											<td style="white-space:nowrap"><html:hidden property="account" write="true" /></td>
											<td style="white-space:nowrap"><html:errors bundle="/clientarea/accounting/ApplicationResources" property="account" /></td>
										</tr>
										<tr>
											<td style="white-space:nowrap"><ao:message key="editCreditCard.required.yes" /></td>
											<td style="white-space:nowrap"><ao:message key="creditCardForm.firstName.prompt" /></td>
											<td style="white-space:nowrap"><html:text property="firstName" size="16" /></td>
											<td style="white-space:nowrap"><html:errors bundle="/clientarea/accounting/ApplicationResources" property="firstName" /></td>
										</tr>
										<tr>
											<td style="white-space:nowrap"><ao:message key="editCreditCard.required.yes" /></td>
											<td style="white-space:nowrap"><ao:message key="creditCardForm.lastName.prompt" /></td>
											<td style="white-space:nowrap"><html:text property="lastName" size="16" /></td>
											<td style="white-space:nowrap"><html:errors bundle="/clientarea/accounting/ApplicationResources" property="lastName" /></td>
										</tr>
										<tr>
											<td style="white-space:nowrap"><ao:message key="editCreditCard.required.no" /></td>
											<td style="white-space:nowrap"><ao:message key="creditCardForm.companyName.prompt" /></td>
											<td style="white-space:nowrap"><html:text property="companyName" size="32" /></td>
											<td style="white-space:nowrap"><html:errors bundle="/clientarea/accounting/ApplicationResources" property="companyName" /></td>
										</tr>
										<tr>
											<td style="white-space:nowrap"><ao:message key="editCreditCard.required.no" /></td>
											<td style="white-space:nowrap"><ao:message key="editCreditCard.maskedCardNumber.prompt" /></td>
											<td style="white-space:nowrap; font-family: monospace"><c:out value="${aoweb:getCardNumberDisplay(creditCard.cardInfo)}"/></td>
											<td style="white-space:nowrap"><html:errors bundle="/clientarea/accounting/ApplicationResources" property="maskedCardNumber" /></td>
										</tr>
										<tr>
											<td style="white-space:nowrap"><ao:message key="editCreditCard.required.no" /></td>
											<td style="white-space:nowrap"><ao:message key="editCreditCard.cardNumber.prompt" /></td>
											<td style="white-space:nowrap"><html:text property="cardNumber" size="20" /></td>
											<td style="white-space:nowrap"><html:errors bundle="/clientarea/accounting/ApplicationResources" property="cardNumber" /></td>
										</tr>
										<c:set var="expirationDisplay" value="${aoweb:getExpirationDisplay(creditCard.expirationMonth, creditCard.expirationYear)}" />
										<c:if test="${!empty expirationDisplay}">
											<tr>
												<td style="white-space:nowrap"><ao:message key="editCreditCard.required.no" /></td>
												<td style="white-space:nowrap"><ao:message key="editCreditCard.expirationDate.prompt" /></td>
												<td style="white-space:nowrap; font-family: monospace"><c:out value="${expirationDisplay}"/></td>
												<td style="white-space:nowrap"></td>
											</tr>
										</c:if>
										<tr>
											<td style="white-space:nowrap"><ao:message key="editCreditCard.required.no" /></td>
											<td style="white-space:nowrap"><ao:message key="editCreditCard.newExpirationDate.prompt" /></td>
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
											<td style="white-space:nowrap"><ao:message key="editCreditCard.required.no" /></td>
											<td style="white-space:nowrap"><ao:message key="editCreditCard.cardCode.prompt" /></td>
											<td style="white-space:nowrap"><html:text property="cardCode" size="5" /></td>
											<td style="white-space:nowrap"><html:errors bundle="/clientarea/accounting/ApplicationResources" property="cardCode" /></td>
										</tr>
										<tr>
											<td style="white-space:nowrap"><ao:message key="editCreditCard.required.yes" /></td>
											<td style="white-space:nowrap"><ao:message key="creditCardForm.streetAddress1.prompt" /></td>
											<td style="white-space:nowrap"><html:text property="streetAddress1" size="32" /></td>
											<td style="white-space:nowrap"><html:errors bundle="/clientarea/accounting/ApplicationResources" property="streetAddress1" /></td>
										</tr>
										<tr>
											<td style="white-space:nowrap"><ao:message key="editCreditCard.required.no" /></td>
											<td style="white-space:nowrap"><ao:message key="creditCardForm.streetAddress2.prompt" /></td>
											<td style="white-space:nowrap"><html:text property="streetAddress2" size="32" /></td>
											<td style="white-space:nowrap"><html:errors bundle="/clientarea/accounting/ApplicationResources" property="streetAddress2" /></td>
										</tr>
										<tr>
											<td style="white-space:nowrap"><ao:message key="editCreditCard.required.yes" /></td>
											<td style="white-space:nowrap"><ao:message key="creditCardForm.city.prompt" /></td>
											<td style="white-space:nowrap"><html:text property="city" size="16" /></td>
											<td style="white-space:nowrap"><html:errors bundle="/clientarea/accounting/ApplicationResources" property="city" /></td>
										</tr>
										<tr>
											<td style="white-space:nowrap"><ao:message key="editCreditCard.required.no" /></td>
											<td style="white-space:nowrap"><ao:message key="creditCardForm.state.prompt" /></td>
											<td style="white-space:nowrap"><html:text property="state" size="5" /></td>
											<td style="white-space:nowrap"><html:errors bundle="/clientarea/accounting/ApplicationResources" property="state" /></td>
										</tr>
										<tr>
											<td style="white-space:nowrap"><ao:message key="editCreditCard.required.yes" /></td>
											<td style="white-space:nowrap"><ao:message key="creditCardForm.countryCode.prompt" /></td>
											<td style="white-space:nowrap">
												<html:select property="countryCode">
													<c:set var="selectedOne" value="false" />
													<c:set var="country" value="${editCreditCardForm.countryCode}" />
													<logic:iterate scope="request" name="countryOptions" id="countryOption">
														<c:choose>
															<c:when test="${!selectedOne && countryOption.code == country}">
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
											<td style="white-space:nowrap"><html:errors bundle="/clientarea/accounting/ApplicationResources" property="countryCode" /></td>
										</tr>
										<tr>
											<td style="white-space:nowrap"><ao:message key="editCreditCard.required.no" /></td>
											<td style="white-space:nowrap"><ao:message key="creditCardForm.postalCode.prompt" /></td>
											<td style="white-space:nowrap"><html:text property="postalCode" size="10" /></td>
											<td style="white-space:nowrap"><html:errors bundle="/clientarea/accounting/ApplicationResources" property="postalCode" /></td>
										</tr>
										<tr>
											<td style="white-space:nowrap"><ao:message key="editCreditCard.required.no" /></td>
											<td style="white-space:nowrap"><ao:message key="creditCardForm.description.prompt" /></td>
											<td style="white-space:nowrap"><html:text property="description" size="32" /></td>
											<td style="white-space:nowrap"><html:errors bundle="/clientarea/accounting/ApplicationResources" property="description" /></td>
										</tr>
									</tbody>
									<tfoot>
										<tr>
											<td style="white-space:nowrap;text-align:center" colspan="4">
												<logic:notEqual name="editCreditCardForm" property="isActive" value="false">
													<ao:input type="submit" value="${ao:message('editCreditCard.field.submit.label.edit')}" />
												</logic:notEqual>
												<logic:equal name="editCreditCardForm" property="isActive" value="false">
													<ao:input type="submit" value="${ao:message('editCreditCard.field.submit.label.reactivate')}" />
												</logic:equal>
											</td>
										</tr>
									</tfoot>
								</table>
							</skin:lightArea>
						</div></html:form>
						<%@include file="security-policy.jspf" %>
					</skin:popupGroup>
				</logic:notPresent>
			</skin:contentLine>
		</skin:content>
	</skin:skin>
</ao:bundle>
