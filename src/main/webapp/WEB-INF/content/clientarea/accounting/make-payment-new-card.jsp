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
	<c:set var="currency" value="${aoConn.billing.Currency.map[makePaymentNewCardForm.currency]}" />
	<skin:path>
		/clientarea/accounting/make-payment-new-card.do
		<ao:param name="account" value="${makePaymentNewCardForm.account}" />
		<c:if test="${!empty currency}">
			<ao:param name="currency" value="${currency.currencyCode}" />
		</c:if>
	</skin:path>
	<logic:equal name="siteSettings" property="brand.aowebStrutsNoindex" value="true"><skin:meta name="robots">noindex</skin:meta></logic:equal>
	<skin:title><ao:message key="makePayment.title" /></skin:title>
	<skin:navImageAlt><ao:message key="makePayment.navImageAlt" /></skin:navImageAlt>
	<skin:keywords><ao:message key="makePayment.keywords" /></skin:keywords>
	<skin:description><ao:message key="makePayment.description" /></skin:description>
	<%@include file="add-parents.inc.jsp" %>
	<skin:skin>
		<skin:content width="600">
			<skin:contentTitle><ao:message key="makePayment.title" /></skin:contentTitle>
			<skin:contentHorizontalDivider />
			<skin:contentLine>
				<skin:popupGroup>
					<html:form action="/make-payment-new-card-completed">
						<skin:lightArea>
							<b><ao:message key="makePaymentNewCard.form.title" /></b>
							<ao:hr />
							<logic:present scope="request" name="errorReason">
								<ao:message key="makePaymentStoredCardError.error.description" arg0="${fn:escapeXml(errorReason)}" />
								<ao:hr />
							</logic:present>
							<logic:present scope="request" name="declineReason">
								<ao:message key="makePaymentStoredCardDeclined.declined.description" arg0="${fn:escapeXml(declineReason)}" />
								<ao:hr />
							</logic:present>
							<table class="ao-no-border">
								<thead>
									<c:set scope="request" var="creditCardForm" value="${makePaymentNewCardForm}" />
									<%@include file="credit-card-form.jspf" %>
								</thead>
								<tbody>
									<c:if test="${!empty currency}">
										<c:set var="balance" value="${account.accountBalance.map[currency.currency]}" />
										<c:if test="${!empty balance}">
											<tr>
												<td style="white-space:nowrap"><ao:message key="creditCardForm.required.no" /></td>
												<th style="white-space:nowrap;text-align:left"><ao:message key="makePaymentStoredCard.accountBalance.prompt" /></th>
												<td style="white-space:nowrap">
													<ao:choose>
														<ao:when test="#{balance.unscaledValue < 0}">
															<ao:message key="makePaymentSelectCard.balance.value.credit" arg0="${balance.negate()}" />
														</ao:when>
														<ao:when test="#{balance.unscaledValue > 0}">
															<ao:message key="makePaymentSelectCard.balance.value.debt" arg0="${balance}" />
														</ao:when>
														<ao:otherwise>
															<ao:message key="makePaymentSelectCard.balance.value.zero" arg0="${balance}" />
														</ao:otherwise>
													</ao:choose>
												</td>
												<td style="white-space:nowrap"><html:errors bundle="/clientarea/accounting/ApplicationResources" property="accountBalance" /></td>
											</tr>
										</c:if>
									</c:if>
									<tr>
										<td style="white-space:nowrap"><ao:message key="creditCardForm.required.yes" /></td>
										<th style="white-space:nowrap;text-align:left"><ao:message key="makePaymentStoredCard.paymentAmount.prompt" /></th>
										<td style="white-space:nowrap">
											<ao:choose>
												<ao:when test="#{empty currency}">
													<ao:select name="currency">
														<ao:option value="">---</ao:option>
														<c:forEach var="c" items="${aoConn.billing.Currency.rows}">
															<ao:option value="${c.currencyCode}"><ao:out value="${c.currencyCode}" /></ao:option>
														</c:forEach>
													</ao:select><html:text property="paymentAmount" size="8" />
												</ao:when>
												<ao:otherwise>
													<ao:input type="hidden" name="currency" value="${currency.currencyCode}" />
													<ao:out value="${aoweb:getCurrencySymbol(currency.currency)}" /><html:text property="paymentAmount" size="8" />
												</ao:otherwise>
											</ao:choose>
										</td>
										<td style="white-space:nowrap"><html:errors bundle="/clientarea/accounting/ApplicationResources" property="paymentAmount" /></td>
									</tr>
									<tr>
										<td style="white-space:nowrap"><ao:message key="creditCardForm.required.no" /></td>
										<th style="white-space:nowrap;text-align:left"><ao:message key="makePaymentNewCard.storeCard.prompt" /></th>
										<td style='white-space:nowrap' colspan="2">
											<label><html:radio property="storeCard" value=""><ao:message key="makePaymentNewCard.storeCard.no" /></html:radio></label><ao:br />
											<label><html:radio property="storeCard" value="store"><ao:message key="makePaymentNewCard.storeCard.store" /></html:radio></label><ao:br />
											<label><html:radio property="storeCard" value="automatic"><ao:message key="makePaymentNewCard.storeCard.automatic" /></html:radio></label>
										</td>
									</tr>
								</tbody>
								<tfoot>
									<tr><td style="white-space:nowrap;text-align:center" colspan="4">
										<ao:input type="submit" value="${ao:message('makePaymentNewCard.field.submit.label')}">
											<ao:onclick>
												this.disabled='true';
												this.form.submit();
												return false;
											</ao:onclick>
										</ao:input>
									</td></tr>
								</tfoot>
							</table>
						</skin:lightArea>
					</html:form>
					<%@include file="security-policy.jspf" %>
				</skin:popupGroup>
			</skin:contentLine>
		</skin:content>
	</skin:skin>
</ao:bundle>
