<%--
aoweb-struts-resources - Template webapp for legacy Struts-based site framework with AOServ Platform control panels.
Copyright (C) 2007-2009, 2015, 2016, 2018, 2019, 2020, 2021  AO Industries, Inc.
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
<%@ page import="java.math.BigDecimal" %>
<%@include file="/_taglibs.inc.jsp" %>

<ao:bundle basename="com.aoindustries.web.struts.clientarea.accounting.i18n.ApplicationResources">
	<skin:path>/clientarea/accounting/make-payment.do</skin:path>
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
				<skin:lightArea>
					<b><ao:message key="makePaymentStoredCardCompleted.confirm.title" /></b>
					<ao:hr />
					<ao:message key="makePaymentNewCardCompleted.confirm.successful" /><ao:br />
					<ao:br />

					<%-- Card stored --%>
					<logic:equal scope="request" name="cardStored" value="true">
						<ao:message key="makePaymentNewCardCompleted.cardStored" /><ao:br />
						<ao:br />
					</logic:equal>

					<%-- Card store error --%>
					<logic:present scope="request" name="storeError">
						<ao:message key="makePaymentNewCardCompleted.storeError" /><ao:br />
						<ao:br />
					</logic:present>

					<%-- Card set automatic --%>
					<logic:equal scope="request" name="cardSetAutomatic" value="true">
						<ao:message key="makePaymentNewCardCompleted.cardSetAutomatic" /><ao:br />
						<ao:br />
					</logic:equal>

					<%-- Cart set automatic error --%>
					<logic:present scope="request" name="setAutomaticError">
						<ao:message key="makePaymentNewCardCompleted.setAutomaticError" /><ao:br />
						<ao:br />
					</logic:present>

					<bean:define scope="request" name="makePaymentNewCardForm" property="cardNumber" id="cardNumber" type="java.lang.String" />
					<bean:define scope="request" name="makePaymentNewCardForm" property="maskedCardNumber" id="maskedCardNumber" type="java.lang.String" />
					<table class="ao-spread">
						<tbody>
							<tr>
								<th style="text-align:left; white-space:nowrap;"><ao:message key="makePaymentStoredCard.account.prompt" /></th>
								<td style="white-space:nowrap"><ao:out value="${account.name}" /></td>
							</tr>
							<tr>
								<th style="text-align:left; white-space:nowrap;"><ao:message key="makePaymentStoredCard.card.prompt" /></th>
								<td style="white-space:nowrap; font-family: monospace">
									<%@include file="_credit-card-image.inc.jsp" %>
									<c:out value="${aoweb:getCardNumberDisplay(maskedCardNumber)}"/>
								</td>
							</tr>
							<logic:notEmpty scope="request" name="makePaymentNewCardForm" property="description">
								<tr>
									<th style="white-space:nowrap;text-align:left"><ao:message key="makePaymentStoredCard.cardComment.prompt" /></th>
									<td style="white-space:nowrap">
										<ao:write scope="request" name="makePaymentNewCardForm" property="description" />
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
				<%@include file="security-policy.inc.jsp" %>
			</skin:contentLine>
		</skin:content>
	</skin:skin>
</ao:bundle>
