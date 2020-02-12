<%--
aoweb-struts-resources - Web resources for legacy Struts-based site framework with AOServ Platform control panels.
Copyright (C) 2007-2009, 2016, 2018, 2019, 2020  AO Industries, Inc.
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

<ao:form method="post" action="make-payment-stored-card.do">
	<div>
		<ao:input name="account" type="hidden" value="${param.account}" />
		<c:if test="${!empty param.currency}">
			<ao:input name="currency" type="hidden" value="${param.currency}" />
		</c:if>
		<skin:lightArea>
			<ao:bundle basename="com.aoindustries.website.clientarea.accounting.ApplicationResources">
				<b><ao:message key="makePaymentSelectCard.selectCard.list.title" /></b>
				<ao:hr />
				<c:set var="hasDescription" value="false" />
				<c:forEach var="creditCard" items="${creditCards}">
					<c:if test="${!empty creditCard.description}">
						<c:set var="hasDescription" value="true" />
					</c:if>
				</c:forEach>
				<table class="spread">
					<thead>
						<tr>
							<th style='white-space:nowrap'><ao:message key="makePaymentSelectCard.select.header" /></th>
							<th style='white-space:nowrap'><ao:message key="makePaymentSelectCard.cardType.header" /></th>
							<th style='white-space:nowrap'><ao:message key="makePaymentSelectCard.cardNumber.header" /></th>
							<th style='white-space:nowrap'><ao:message key="makePaymentSelectCard.expirationDate.header" /></th>
							<c:if test="${hasDescription}">
								<th style='white-space:nowrap'><ao:message key="makePaymentSelectCard.comments.header" /></th>
							</c:if>
						</tr>
					</thead>
					<tbody>
						<logic:iterate scope="request" name="creditCards" id="creditCard">
							<skin:lightDarkTableRow>
								<td style="white-space:nowrap">
									<ao:input
										type="radio"
										id="id_${creditCard.id}"
										name="id"
										value="${creditCard.id}"
										checked="${lastPaymentCreditCard == creditCard.providerUniqueId}"
									/>
								</td>
								<c:set var="cardNumber" value="${creditCard.cardInfo}"/>
								<td style="white-space:nowrap"><label for="id_${fn:escapeXml(creditCard.id)}"><%@include file="_credit-card-image.inc.jsp" %></label></td>
								<td style="white-space:nowrap; font-family: monospace"><label for="id_${fn:escapeXml(creditCard.id)}"><c:out value="${aoweb:getCardNumberDisplay(cardNumber)}"/></label></td>
								<td style="white-space:nowrap; font-family: monospace"><label for="id_${fn:escapeXml(creditCard.id)}"><c:out value="${aoweb:getExpirationDisplay(creditCard.expirationMonth, creditCard.expirationYear)}"/></label></td>
								<c:if test="${hasDescription}">
									<td style="white-space:nowrap">
										<logic:notEmpty name="creditCard" property="description">
											<label for="id_${fn:escapeXml(creditCard.id)}"><ao:write name="creditCard" property="description" /></label>
										</logic:notEmpty>
									</td>
								</c:if>
							</skin:lightDarkTableRow>
						</logic:iterate>
						<skin:lightDarkTableRow>
							<td style="white-space:nowrap">
								<ao:input type="radio" id="id_" name="id" value="" checked="${empty lastPaymentCreditCard}" />
							</td>
							<td style='white-space:nowrap' colspan="${fn:escapeXml(3 + (hasDescription ? 1 : 0))}"><label for="id_"><ao:message key="makePaymentSelectCard.newCard.link" /></label></td>
						</skin:lightDarkTableRow>
					</tbody>
					<tfoot>
						<tr>
							<td style="white-space:nowrap;text-align:center" colspan="${fn:escapeXml(4 + (hasDescription ? 1 : 0))}">
								<ao:input type="submit" name="submitButton" value="${ao:message('makePaymentSelectCard.field.submit.label')}" />
							</td>
						</tr>
					</tfoot>
				</table>
			</ao:bundle>
		</skin:lightArea>
		<%@include file="security-policy.inc.jsp" %>
	</div>
</ao:form>
