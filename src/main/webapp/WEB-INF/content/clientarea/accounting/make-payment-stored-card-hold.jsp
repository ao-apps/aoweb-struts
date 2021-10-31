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
along with aoweb-struts.  If not, see <https://www.gnu.org/licenses/>.
--%>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/taglibs.jspf" %>

<ao:bundle basename="com.aoindustries.web.struts.clientarea.accounting.i18n.ApplicationResources">
	<skin:path>/clientarea/accounting/make-payment.do</skin:path>
	<logic:equal name="siteSettings" property="brand.aowebStrutsNoindex" value="true"><skin:meta name="robots">noindex</skin:meta></logic:equal>
	<skin:title><ao:message key="makePayment.title" /></skin:title>
	<skin:navImageAlt><ao:message key="makePayment.navImageAlt" /></skin:navImageAlt>
	<skin:keywords><ao:message key="makePayment.keywords" /></skin:keywords>
	<skin:description><ao:message key="makePayment.description" /></skin:description>
	<%@include file="add-parents.jspf" %>
	<skin:skin>
		<skin:content width="600">
			<skin:contentTitle><ao:message key="makePayment.title" /></skin:contentTitle>
			<skin:contentHorizontalDivider />
			<skin:contentLine>
				<skin:lightArea>
					<b><ao:message key="makePaymentStoredCardHold.hold.title" /></b>
					<ao:hr />
					<ao:message type="xhtml" key="makePaymentStoredCardHold.hold.followingProcessed" arg0="${fn:escapeXml(reviewReason)}" />
					<ao:message key="makePaymentStoredCardHold.hold.detailsFollow" />
					<bean:define scope="request" name="creditCard" id="creditCard" type="com.aoindustries.aoserv.client.payment.CreditCard" />
					<table class="ao-spread">
						<tbody>
							<tr>
								<th style="text-align:left; white-space:nowrap;"><ao:message key="makePaymentStoredCard.account.prompt" /></th>
								<td style="white-space:nowrap"><ao:write scope="request" name="account" /></td>
							</tr>
							<tr>
								<th style="text-align:left; white-space:nowrap;"><ao:message key="makePaymentStoredCard.card.prompt" /></th>
								<td style="white-space:nowrap; font-family: monospace">
									<c:set var="cardNumber" value="${creditCard.cardInfo}"/>
									<%@include file="credit-card-image.jspf" %>
									<c:out value="${aoweb:getCardNumberDisplay(cardNumber)}"/>
								</td>
							</tr>
							<c:set var="expirationDisplay" value="${aoweb:getExpirationDisplay(creditCard.expirationMonth, creditCard.expirationYear)}"/>
							<c:if test="${!empty expirationDisplay}">
								<tr>
									<th style="text-align:left; white-space:nowrap;"><ao:message key="makePaymentStoredCard.expirationDate.prompt" /></th>
									<td style="white-space:nowrap; font-family: monospace"><c:out value="${expirationDisplay}"/></td>
								</tr>
							</c:if>
							<logic:notEmpty name="creditCard" property="description">
								<tr>
									<th style="text-align:left; white-space:nowrap;"><ao:message key="makePaymentStoredCard.cardComment.prompt" /></th>
									<td style="white-space:nowrap">
										<ao:write name="creditCard" property="description" />
									</td>
								</tr>
							</logic:notEmpty>
							<tr>
								<th style="text-align:left; white-space:nowrap;"><ao:message key="makePaymentStoredCard.paymentAmount.prompt" /></th>
								<td style="white-space:nowrap"><ao:out value="${aoTransaction.amount.negate()}" /></td>
							</tr>
							<tr>
								<th style="text-align:left; white-space:nowrap;"><ao:message key="makePaymentStoredCardCompleted.transid.prompt" /></th>
								<td style="white-space:nowrap"><ao:write scope="request" name="aoTransaction" property="transid" /></td>
							</tr>
							<tr>
								<th style="text-align:left; white-space:nowrap;"><ao:message key="makePaymentStoredCardCompleted.approvalCode.prompt" /></th>
								<td style="white-space:nowrap"><ao:write scope="request" name="transaction" property="authorizationResult.approvalCode" /></td>
							</tr>
							<tr>
								<th style="white-space:nowrap;text-align:left"><ao:message key="makePaymentStoredCardCompleted.newBalance.prompt" /></th>
								<td style="white-space:nowrap">
									<c:forEach var="balance" items="${account.accountBalance.values}">
										<ao:choose>
											<ao:when test="#{balance.unscaledValue < 0}">
												<div>
													<ao:message key="makePaymentStoredCardCompleted.newBalance.value.credit" arg0="${balance.negate()}" />
												</div>
											</ao:when>
											<ao:when test="#{balance.unscaledValue > 0}">
												<div style="color:red"><strong>
													<ao:message key="makePaymentStoredCardCompleted.newBalance.value.debt" arg0="${balance}" />
												</strong></div>
											</ao:when>
											<ao:otherwise>
												<div>
													<ao:message key="makePaymentStoredCardCompleted.newBalance.value.zero" arg0="${balance}" />
												</div>
											</ao:otherwise>
										</ao:choose>
									</c:forEach>
								</td>
							</tr>
						</tbody>
					</table><ao:br />
					<ao:message type="xhtml" key="makePaymentStoredCardCompleted.contactAndThankYou" />
				</skin:lightArea>
				<%@include file="security-policy.jspf" %>
			</skin:contentLine>
		</skin:content>
	</skin:skin>
</ao:bundle>
