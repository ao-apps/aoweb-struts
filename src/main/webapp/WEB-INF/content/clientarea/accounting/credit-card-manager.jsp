<%--
aoweb-struts - Template webapp for legacy Struts-based site framework with AOServ Platform control panels.
Copyright (C) 2007-2009, 2016, 2018, 2019, 2020, 2021  AO Industries, Inc.
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

<%@include file="add-parents.inc.jsp" %>
<%@include file="credit-card-manager.meta.inc.jsp" %>
<skin:skin>
	<skin:content width="600">
		<ao:bundle basename="com.aoindustries.web.struts.clientarea.accounting.i18n.ApplicationResources">
			<skin:contentTitle><ao:message key="creditCardManager.title" /></skin:contentTitle>
			<skin:contentHorizontalDivider />
			<skin:contentLine>
				<logic:present scope="request" name="permissionDenied">
					<%@include file="../../permission-denied.jspf" %>
				</logic:present>
				<logic:notPresent scope="request" name="permissionDenied">
					<skin:popupGroup>
						<skin:lightArea>
							<c:set var="hasDescription" value="false" />
							<c:forEach var="accountAndCreditCards" items="${accountCreditCards}">
								<c:if test="${!hasDescription}">
									<c:forEach var="creditCard" items="${accountAndCreditCards.creditCards}">
										<c:if test="${!empty creditCard.description}">
											<c:set var="hasDescription" value="true" />
										</c:if>
									</c:forEach>
								</c:if>
							</c:forEach>
							<table class="ao-spread">
								<thead>
									<tr>
										<logic:equal scope="request" name="showAccount" value="true">
											<th><ao:message key="creditCardManager.header.account" /></th>
										</logic:equal>
										<th><ao:message key="creditCardManager.header.cardType" /></th>
										<th><ao:message key="creditCardManager.header.maskedCardNumber" /></th>
										<th><ao:message key="creditCardManager.header.expirationDate" /></th>
										<th style='white-space:nowrap'>
											<ao:message key="creditCardManager.header.status" />
											<skin:popup>
												<ao:message key="creditCardManager.header.status.popup" type="application/xhtml+xml" />
											</skin:popup>
										</th>
										<th colspan="3"><ao:message key="creditCardManager.header.actions" /></th>
										<c:if test="${hasDescription}">
											<th><ao:message key="creditCardManager.header.description" /></th>
										</c:if>
									</tr>
								</thead>
								<tbody>
									<logic:iterate scope="request" name="accountCreditCards" id="accountAndCreditCards" indexId="accountsIndex">
										<bean:define name="accountAndCreditCards" property="creditCards" id="creditCards" type="java.util.List<com.aoindustries.aoserv.client.payment.CreditCard>" />
										<bean:size name="creditCards" id="creditCardsSize" />
										<%--tr class="<ao:out value="${(accountsIndex%2)==0 ? 'aoLightRow' : 'aoDarkRow'}" />">
											<td colspan="${fn:escapeXml(8 + (hasDescription ? 1 : 0))}"><ao:hr /></td>
										</tr--%>
										<logic:notEqual name="creditCardsSize" value="0">
											<tr class="<ao:out value="${(accountsIndex%2)==0 ? 'aoLightRow' : 'aoDarkRow'}" />">
												<logic:equal scope="request" name="showAccount" value="true">
													<td style='white-space:nowrap' rowspan="<ao:out value="${creditCardsSize+1}" />"><ao:write name="accountAndCreditCards" property="account.name" /></td>
												</logic:equal>
												<logic:iterate name="creditCards" id="creditCard" type="com.aoindustries.aoserv.client.payment.CreditCard" indexId="creditCardsIndex">
													<logic:notEqual name="creditCardsIndex" value="0">
														</tr>
														<tr class="<ao:out value="${((accountsIndex+creditCardsIndex)%2)==0 ? 'aoLightRow' : 'aoDarkRow'}" />">
													</logic:notEqual>
													<c:set var="cardNumber" value="${creditCard.cardInfo}"/>
													<td style="white-space:nowrap"><%@include file="credit-card-image.jspf" %></td>
													<td style="white-space:nowrap; font-family: monospace"><c:out value="${aoweb:getCardNumberDisplay(cardNumber)}"/></td>
													<td style="white-space:nowrap; font-family: monospace"><c:out value="${aoweb:getExpirationDisplay(creditCard.expirationMonth, creditCard.expirationYear)}"/></td>
													<logic:equal name="creditCard" property="isActive" value="true">
														<logic:notEqual name="creditCard" property="useMonthly" value="true">
															<td style="white-space:nowrap"><ao:message key="creditCardManager.header.status.active" /></td>
														</logic:notEqual>
														<logic:equal name="creditCard" property="useMonthly" value="true">
															<td style="white-space:nowrap">
																<ao:message key="creditCardManager.header.status.useMonthly" />
																<skin:popup width="200">
																	<ao:message key="creditCardManager.status.useMonthly.popup" />
																</skin:popup>
															</td>
														</logic:equal>
													</logic:equal>
													<logic:notEqual name="creditCard" property="isActive" value="true">
														<td style="white-space:nowrap">
															<ao:message key="creditCardManager.header.status.deactivated" />
															<logic:notEmpty name="creditCard" property="deactivatedOnString">
																<logic:notEmpty name="creditCard" property="deactivateReason">
																	<skin:popup width="280">
																		<ao:message
																			key="creditCardManager.header.status.deactivated.popup"
																			type="application/xhtml+xml"
																			arg0="${fn:escapeXml(creditCard.deactivatedOnString)}"
																			arg1="${fn:escapeXml(creditCard.deactivateReason)}"
																		/>
																	</skin:popup>
																</logic:notEmpty>
															</logic:notEmpty>
														</td>
													</logic:notEqual>
													<td style="white-space:nowrap">
														<c:set var="currencies" value="${accountAndCreditCards.account.accountBalance.currencies}" />
														<ao:choose>
															<ao:when test="#{fn:length(currencies) == 0}">
																<%-- Handle the no-currencies case --%>
																<div>
																	<ao:a
																		href="make-payment-stored-card.do"
																		param.account="${accountAndCreditCards.account.name}"
																		param.id="${creditCard.id}"
																	>
																		<ao:message key="creditCardManager.makePayment.link" />
																	</ao:a>
																</div>
															</ao:when>
															<ao:otherwise>
																<c:forEach var="currency" items="${currencies}">
																	<div>
																		<ao:a
																			href="make-payment-stored-card.do"
																			param.account="${accountAndCreditCards.account.name}"
																			param.currency="${currency.currencyCode}"
																			param.id="${creditCard.id}"
																		>
																			<ao:choose>
																				<ao:when test="#{fn:length(currencies) == 1}">
																					<ao:message key="creditCardManager.makePayment.link" />
																				</ao:when>
																				<ao:otherwise>
																					<ao:message key="creditCardManager.makePayment.linkInCurrency" arg0="${currency.currencyCode}"/>
																				</ao:otherwise>
																			</ao:choose>
																		</ao:a>
																	</div>
																</c:forEach>
															</ao:otherwise>
														</ao:choose>
													</td>
													<td style="white-space:nowrap">
														<ao:a href="edit-credit-card.do" param.persistenceId="${creditCard.pkey}">
															<logic:equal name="creditCard" property="isActive" value="true">
																<ao:message key="creditCardManager.edit.link" />
															</logic:equal>
															<logic:notEqual name="creditCard" property="isActive" value="true">
																<ao:message key="creditCardManager.reactivate.link" />
															</logic:notEqual>
														</ao:a>
													</td>
													<td style="white-space:nowrap">
														<ao:a href="delete-credit-card.do" param.id="${creditCard.id}">
															<ao:message key="creditCardManager.delete.link" />
														</ao:a>
													</td>
													<c:if test="${hasDescription}">
														<td style="white-space:nowrap">
															<logic:notEmpty name="creditCard" property="description">
																<ao:write name="creditCard" property="description" />
															</logic:notEmpty>
														</td>
													</c:if>
												</logic:iterate>
											</tr>
										</logic:notEqual>
										<tr class="<ao:out value="${(accountsIndex%2)==0 ? 'aoLightRow' : 'aoDarkRow'}" />">
											<logic:equal name="creditCardsSize" value="0">
												<logic:equal scope="request" name="showAccount" value="true">
													<td rowspan="<ao:out value="${creditCardsSize+1}" />"><ao:write name="accountAndCreditCards" property="account.name" /></td>
												</logic:equal>
											</logic:equal>
											<td style='white-space:nowrap' colspan="${fn:escapeXml(8 + (hasDescription ? 1 : 0))}">
												<ao:a href="add-credit-card.do" param.account="${accountAndCreditCards.account.name}">
													<ao:message key="creditCardManager.addCreditCard.link" />
												</ao:a>
												<logic:equal name="accountAndCreditCards" property="hasActiveCard" value="true">
													|
													<ao:a href="configure-automatic-billing.do" param.account="${accountAndCreditCards.account.name}">
														<ao:message key="creditCardManager.configureAutomaticBilling.link" />
													</ao:a>
												</logic:equal>
											</td>
										</tr>
									</logic:iterate>
								</tbody>
							</table>
						</skin:lightArea>
						<%@include file="security-policy.jspf" %>
					</skin:popupGroup>
				</logic:notPresent>
			</skin:contentLine>
		</ao:bundle>
	</skin:content>
</skin:skin>
