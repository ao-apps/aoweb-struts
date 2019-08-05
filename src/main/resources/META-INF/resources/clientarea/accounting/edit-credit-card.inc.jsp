<%--
aoweb-struts-resources - Web resources for legacy Struts-based site framework with AOServ Platform control panels.
Copyright (C) 2007-2009, 2015, 2016, 2018, 2019  AO Industries, Inc.
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

<skin:setContentType />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html:html lang="true" xhtml="true">
	<fmt:bundle basename="com.aoindustries.website.clientarea.accounting.ApplicationResources">
		<skin:path>
			/clientarea/accounting/edit-credit-card.do
			<ao:param name="persistenceId"><ao:write scope="request" name="editCreditCardForm" property="persistenceId" /></ao:param>
		</skin:path>
		<logic:equal name="siteSettings" property="brand.aowebStrutsNoindex" value="true"><skin:meta name="ROBOTS">NOINDEX</skin:meta></logic:equal>
		<skin:title>
			<logic:notEqual name="editCreditCardForm" property="isActive" value="false">
				<fmt:message key="editCreditCard.title.edit" />
			</logic:notEqual>
			<logic:equal name="editCreditCardForm" property="isActive" value="false">
				<fmt:message key="editCreditCard.title.reactivate" />
			</logic:equal>
		</skin:title>
		<skin:navImageAlt>
			<logic:notEqual name="editCreditCardForm" property="isActive" value="false">
				<fmt:message key="editCreditCard.navImageAlt.edit" />
			</logic:notEqual>
			<logic:equal name="editCreditCardForm" property="isActive" value="false">
				<fmt:message key="editCreditCard.navImageAlt.reactivate" />
			</logic:equal>
		</skin:navImageAlt>
		<skin:keywords><fmt:message key="editCreditCard.keywords" /></skin:keywords>
		<skin:description><fmt:message key="editCreditCard.description" /></skin:description>
		<%@include file="add-parents.inc.jsp" %>
		<skin:parent><%@include file="credit-card-manager.meta.inc.jsp" %></skin:parent>
		<skin:skin>
			<skin:content width="600">
				<skin:contentTitle>
					<logic:notEqual name="editCreditCardForm" property="isActive" value="false">
						<fmt:message key="editCreditCard.title.edit" />
					</logic:notEqual>
					<logic:equal name="editCreditCardForm" property="isActive" value="false">
						<fmt:message key="editCreditCard.title.reactivate" />
					</logic:equal>
				</skin:contentTitle>
				<skin:contentHorizontalDivider />
				<skin:contentLine>
					<logic:present scope="request" name="permissionDenied">
						<%@include file="../../_permission-denied.inc.jsp" %>
					</logic:present>
					<logic:notPresent scope="request" name="permissionDenied">
						<bean:define scope="request" name="creditCard" id="creditCard" type="com.aoindustries.aoserv.client.payment.CreditCard" />
						<skin:popupGroup>
							<html:form action="/edit-credit-card-completed"><div>
								<html:hidden property="persistenceId" />
								<html:hidden property="isActive" />
								<skin:lightArea>
									<logic:notEqual name="editCreditCardForm" property="isActive" value="false">
										<fmt:message key="editCreditCard.form.title.edit" />
									</logic:notEqual>
									<logic:equal name="editCreditCardForm" property="isActive" value="false">
										<fmt:message key="editCreditCard.form.title.reactivate" />
									</logic:equal>
									<hr />
									<table cellspacing="0" cellpadding="2">
										<tr>
											<td style='white-space:nowrap' colspan="4" align="center"><%@include file="_credit-card-images.inc.jsp" %></td>
										</tr>
										<tr>
											<td style="white-space:nowrap"><fmt:message key="editCreditCard.required.yes" /></td>
											<td style="white-space:nowrap"><fmt:message key="creditCardForm.account.prompt" /></td>
											<td style="white-space:nowrap"><html:hidden property="account" write="true" /></td>
											<td style="white-space:nowrap"><html:errors bundle="/clientarea/accounting/ApplicationResources" property="account" /></td>
										</tr>
										<tr>
											<td style="white-space:nowrap"><fmt:message key="editCreditCard.required.yes" /></td>
											<td style="white-space:nowrap"><fmt:message key="creditCardForm.firstName.prompt" /></td>
											<td style="white-space:nowrap"><html:text property="firstName" size="16" /></td>
											<td style="white-space:nowrap"><html:errors bundle="/clientarea/accounting/ApplicationResources" property="firstName" /></td>
										</tr>
										<tr>
											<td style="white-space:nowrap"><fmt:message key="editCreditCard.required.yes" /></td>
											<td style="white-space:nowrap"><fmt:message key="creditCardForm.lastName.prompt" /></td>
											<td style="white-space:nowrap"><html:text property="lastName" size="16" /></td>
											<td style="white-space:nowrap"><html:errors bundle="/clientarea/accounting/ApplicationResources" property="lastName" /></td>
										</tr>
										<tr>
											<td style="white-space:nowrap"><fmt:message key="editCreditCard.required.no" /></td>
											<td style="white-space:nowrap"><fmt:message key="creditCardForm.companyName.prompt" /></td>
											<td style="white-space:nowrap"><html:text property="companyName" size="32" /></td>
											<td style="white-space:nowrap"><html:errors bundle="/clientarea/accounting/ApplicationResources" property="companyName" /></td>
										</tr>
										<tr>
											<td style="white-space:nowrap"><fmt:message key="editCreditCard.required.no" /></td>
											<td style="white-space:nowrap"><fmt:message key="editCreditCard.maskedCardNumber.prompt" /></td>
											<td style="white-space:nowrap; font-family: monospace"><c:out value="${aoweb:getCardNumberDisplay(creditCard.cardInfo)}"/></td>
											<td style="white-space:nowrap"><html:errors bundle="/clientarea/accounting/ApplicationResources" property="maskedCardNumber" /></td>
										</tr>
										<tr>
											<td style="white-space:nowrap"><fmt:message key="editCreditCard.required.no" /></td>
											<td style="white-space:nowrap"><fmt:message key="editCreditCard.cardNumber.prompt" /></td>
											<td style="white-space:nowrap"><html:text property="cardNumber" size="20" /></td>
											<td style="white-space:nowrap"><html:errors bundle="/clientarea/accounting/ApplicationResources" property="cardNumber" /></td>
										</tr>
										<c:set var="expirationDisplay" value="${aoweb:getExpirationDisplay(creditCard.expirationMonth, creditCard.expirationYear)}" />
										<c:if test="${!empty expirationDisplay}">
											<tr>
												<td style="white-space:nowrap"><fmt:message key="editCreditCard.required.no" /></td>
												<td style="white-space:nowrap"><fmt:message key="editCreditCard.expirationDate.prompt" /></td>
												<td style="white-space:nowrap; font-family: monospace"><c:out value="${expirationDisplay}"/></td>
												<td style="white-space:nowrap"></td>
											</tr>
										</c:if>
										<tr>
											<td style="white-space:nowrap"><fmt:message key="editCreditCard.required.no" /></td>
											<td style="white-space:nowrap"><fmt:message key="editCreditCard.newExpirationDate.prompt" /></td>
											<td style="white-space:nowrap">
												<fmt:bundle basename="com.aoindustries.website.signup.ApplicationResources">
													<html:select property="expirationMonth">
														<html:option value=""><fmt:message key="signupBillingInformationForm.billingExpirationMonth.none.display" /></html:option>
														<html:option value="01"><ao:xhtml><ao:text><fmt:message key="signupBillingInformationForm.billingExpirationMonth.jan.display" /></ao:text></ao:xhtml></html:option>
														<html:option value="02"><ao:xhtml><ao:text><fmt:message key="signupBillingInformationForm.billingExpirationMonth.feb.display" /></ao:text></ao:xhtml></html:option>
														<html:option value="03"><ao:xhtml><ao:text><fmt:message key="signupBillingInformationForm.billingExpirationMonth.mar.display" /></ao:text></ao:xhtml></html:option>
														<html:option value="04"><ao:xhtml><ao:text><fmt:message key="signupBillingInformationForm.billingExpirationMonth.apr.display" /></ao:text></ao:xhtml></html:option>
														<html:option value="05"><ao:xhtml><ao:text><fmt:message key="signupBillingInformationForm.billingExpirationMonth.may.display" /></ao:text></ao:xhtml></html:option>
														<html:option value="06"><ao:xhtml><ao:text><fmt:message key="signupBillingInformationForm.billingExpirationMonth.jun.display" /></ao:text></ao:xhtml></html:option>
														<html:option value="07"><ao:xhtml><ao:text><fmt:message key="signupBillingInformationForm.billingExpirationMonth.jul.display" /></ao:text></ao:xhtml></html:option>
														<html:option value="08"><ao:xhtml><ao:text><fmt:message key="signupBillingInformationForm.billingExpirationMonth.aug.display" /></ao:text></ao:xhtml></html:option>
														<html:option value="09"><ao:xhtml><ao:text><fmt:message key="signupBillingInformationForm.billingExpirationMonth.sep.display" /></ao:text></ao:xhtml></html:option>
														<html:option value="10"><ao:xhtml><ao:text><fmt:message key="signupBillingInformationForm.billingExpirationMonth.oct.display" /></ao:text></ao:xhtml></html:option>
														<html:option value="11"><ao:xhtml><ao:text><fmt:message key="signupBillingInformationForm.billingExpirationMonth.nov.display" /></ao:text></ao:xhtml></html:option>
														<html:option value="12"><ao:xhtml><ao:text><fmt:message key="signupBillingInformationForm.billingExpirationMonth.dec.display" /></ao:text></ao:xhtml></html:option>
													</html:select>&#160;/&#160;<html:select property="expirationYear">
														<html:option value=""><ao:xhtml><ao:text><fmt:message key="signupBillingInformationForm.billingExpirationYear.none.display" /></ao:text></ao:xhtml></html:option>
														<html:options name="expirationYears" />
													</html:select>
												</fmt:bundle>
											</td>
											<td style="white-space:nowrap"><html:errors bundle="/clientarea/accounting/ApplicationResources" property="expirationDate" /></td>
										</tr>
										<tr>
											<td style="white-space:nowrap"><fmt:message key="editCreditCard.required.no" /></td>
											<td style="white-space:nowrap"><fmt:message key="editCreditCard.cardCode.prompt" /></td>
											<td style="white-space:nowrap"><html:text property="cardCode" size="5" /></td>
											<td style="white-space:nowrap"><html:errors bundle="/clientarea/accounting/ApplicationResources" property="cardCode" /></td>
										</tr>
										<tr>
											<td style="white-space:nowrap"><fmt:message key="editCreditCard.required.yes" /></td>
											<td style="white-space:nowrap"><fmt:message key="creditCardForm.streetAddress1.prompt" /></td>
											<td style="white-space:nowrap"><html:text property="streetAddress1" size="32" /></td>
											<td style="white-space:nowrap"><html:errors bundle="/clientarea/accounting/ApplicationResources" property="streetAddress1" /></td>
										</tr>
										<tr>
											<td style="white-space:nowrap"><fmt:message key="editCreditCard.required.no" /></td>
											<td style="white-space:nowrap"><fmt:message key="creditCardForm.streetAddress2.prompt" /></td>
											<td style="white-space:nowrap"><html:text property="streetAddress2" size="32" /></td>
											<td style="white-space:nowrap"><html:errors bundle="/clientarea/accounting/ApplicationResources" property="streetAddress2" /></td>
										</tr>
										<tr>
											<td style="white-space:nowrap"><fmt:message key="editCreditCard.required.yes" /></td>
											<td style="white-space:nowrap"><fmt:message key="creditCardForm.city.prompt" /></td>
											<td style="white-space:nowrap"><html:text property="city" size="16" /></td>
											<td style="white-space:nowrap"><html:errors bundle="/clientarea/accounting/ApplicationResources" property="city" /></td>
										</tr>
										<tr>
											<td style="white-space:nowrap"><fmt:message key="editCreditCard.required.no" /></td>
											<td style="white-space:nowrap"><fmt:message key="creditCardForm.state.prompt" /></td>
											<td style="white-space:nowrap"><html:text property="state" size="5" /></td>
											<td style="white-space:nowrap"><html:errors bundle="/clientarea/accounting/ApplicationResources" property="state" /></td>
										</tr>
										<tr>
											<td style="white-space:nowrap"><fmt:message key="editCreditCard.required.yes" /></td>
											<td style="white-space:nowrap"><fmt:message key="creditCardForm.countryCode.prompt" /></td>
											<td style="white-space:nowrap">
												<html:select property="countryCode">
													<bean:define id="didOne" type="java.lang.String" value="false" />
													<bean:define name="editCreditCardForm" property="countryCode" id="country" type="java.lang.String" />
													<logic:iterate scope="request" name="countryOptions" id="countryOption">
														<logic:equal name="countryOption" property="code" value="<%= country %>">
															<% if(!didOne.equals("true")) { %>
																<option value='<ao:write name="countryOption" property="code" />' selected="selected"><ao:write name="countryOption" property="name" /></option>
																<% didOne = "true"; %>
															<% } else { %>
																<option value='<ao:write name="countryOption" property="code" />'><ao:write name="countryOption" property="name" /></option>
															<% } %>
														</logic:equal>
														<logic:notEqual name="countryOption" property="code" value="<%= country %>">
															<option value='<ao:write name="countryOption" property="code" />'><ao:write name="countryOption" property="name" /></option>
														</logic:notEqual>
													</logic:iterate>
												</html:select>
											</td>
											<td style="white-space:nowrap"><html:errors bundle="/clientarea/accounting/ApplicationResources" property="countryCode" /></td>
										</tr>
										<tr>
											<td style="white-space:nowrap"><fmt:message key="editCreditCard.required.no" /></td>
											<td style="white-space:nowrap"><fmt:message key="creditCardForm.postalCode.prompt" /></td>
											<td style="white-space:nowrap"><html:text property="postalCode" size="10" /></td>
											<td style="white-space:nowrap"><html:errors bundle="/clientarea/accounting/ApplicationResources" property="postalCode" /></td>
										</tr>
										<tr>
											<td style="white-space:nowrap"><fmt:message key="editCreditCard.required.no" /></td>
											<td style="white-space:nowrap"><fmt:message key="creditCardForm.description.prompt" /></td>
											<td style="white-space:nowrap"><html:text property="description" size="32" /></td>
											<td style="white-space:nowrap"><html:errors bundle="/clientarea/accounting/ApplicationResources" property="description" /></td>
										</tr>
										<tr>
											<td style='white-space:nowrap' colspan="4" align="center">
												<ao:input type="submit">
													<logic:notEqual name="editCreditCardForm" property="isActive" value="false">
														<fmt:message key="editCreditCard.field.submit.label.edit" />
													</logic:notEqual>
													<logic:equal name="editCreditCardForm" property="isActive" value="false">
														<fmt:message key="editCreditCard.field.submit.label.reactivate" />
													</logic:equal>
												</ao:input>
											</td>
										</tr>
									</table>
								</skin:lightArea>
							</div></html:form>
							<%@include file="security-policy.inc.jsp" %>
						</skin:popupGroup>
					</logic:notPresent>
				</skin:contentLine>
			</skin:content>
		</skin:skin>
	</fmt:bundle>
</html:html>
