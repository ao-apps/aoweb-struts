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
<%@include file="/_taglibs.inc.jsp" %>

<ao:bundle basename="com.aoindustries.web.struts.clientarea.accounting.i18n.ApplicationResources">
	<skin:path>
		/clientarea/accounting/configure-automatic-billing.do
		<ao:param name="account" value="${param.account}"/>
	</skin:path>
	<logic:equal name="siteSettings" property="brand.aowebStrutsNoindex" value="true"><skin:meta name="robots">noindex</skin:meta></logic:equal>
	<skin:title><ao:message key="configureAutomaticBilling.title" /></skin:title>
	<skin:navImageAlt><ao:message key="configureAutomaticBilling.navImageAlt" /></skin:navImageAlt>
	<skin:keywords><ao:message key="configureAutomaticBilling.keywords" /></skin:keywords>
	<skin:description><ao:message key="configureAutomaticBilling.description" /></skin:description>
	<%@include file="add-parents.inc.jsp" %>
	<skin:parent><%@include file="credit-card-manager.meta.inc.jsp" %></skin:parent>
	<skin:skin>
		<skin:content width="600">
			<skin:contentTitle><ao:message key="configureAutomaticBilling.title" /></skin:contentTitle>
			<skin:contentHorizontalDivider />
			<skin:contentLine>
				<logic:present scope="request" name="permissionDenied">
					<%@include file="../../_permission-denied.inc.jsp" %>
				</logic:present>
				<logic:notPresent scope="request" name="permissionDenied">
					<form id="configurationAutomaticBillingForm" method="post" action="<ao:url>configure-automatic-billing-completed.do</ao:url>"><div>
						<ao:input name="account" type="hidden" value="${param.account}" />
						<skin:lightArea>
							<b><ao:message key="configureAutomaticBilling.cardList.title" /></b>
							<ao:hr />
							<ao:message key="configureAutomaticBilling.account.label" />
							<ao:write scope="request" name="account" property="name" /><ao:br />
							<ao:br />
							<c:set var="hasDescription" value="false" />
							<c:forEach var="creditCard" items="${activeCards}">
								<c:if test="${!empty creditCard.description}">
									<c:set var="hasDescription" value="true" />
								</c:if>
							</c:forEach>
							<table class="ao-spread">
								<thead>
									<tr>
										<th><ao:message key="configureAutomaticBilling.header.select" /></th>
										<th><ao:message key="configureAutomaticBilling.header.cardType" /></th>
										<th><ao:message key="configureAutomaticBilling.header.maskedCardNumber" /></th>
										<th><ao:message key="configureAutomaticBilling.header.expirationDate" /></th>
										<c:if test="${hasDescription}">
											<th><ao:message key="configureAutomaticBilling.header.description" /></th>
										</c:if>
									</tr>
								</thead>
								<tbody>
									<logic:iterate scope="request" name="activeCards" id="creditCard" type="com.aoindustries.aoserv.client.payment.CreditCard" indexId="row">
										<skin:lightDarkTableRow>
											<td style="white-space:nowrap">
												<logic:notPresent scope="request" name="automaticCard">
													<ao:input
														type="radio"
														id="pkey_${creditCard.pkey}"
														name="pkey"
														value="${creditCard.pkey}"
														onchange="this.form.submitButton.disabled=false;"
													/>
												</logic:notPresent>
												<logic:present scope="request" name="automaticCard">
													<logic:equal scope="request" name="automaticCard" property="pkey" value="<%= Integer.toString(creditCard.getPkey()) %>">
														<ao:input
															type="radio"
															id="pkey_${creditCard.pkey}"
															name="pkey"
															value="${creditCard.pkey}"
															checked="true"
															onchange="this.form.submitButton.disabled=true;"
														/>
													</logic:equal>
													<logic:notEqual scope="request" name="automaticCard" property="pkey" value="<%= Integer.toString(creditCard.getPkey()) %>">
														<ao:input
															type="radio"
															id="pkey_${creditCard.pkey}"
															name="pkey"
															value="${creditCard.pkey}"
															onchange="this.form.submitButton.disabled=false;"
														/>
													</logic:notEqual>
												</logic:present>
											</td>
											<c:set var="cardNumber" value="${creditCard.cardInfo}"/>
											<td style="white-space:nowrap"><label for="pkey_${fn:escapeXml(creditCard.pkey)}"><%@include file="_credit-card-image.inc.jsp" %></label></td>
											<td style="white-space:nowrap; font-family: monospace"><label for="pkey_${fn:escapeXml(creditCard.pkey)}"><c:out value="${aoweb:getCardNumberDisplay(cardNumber)}"/></label></td>
											<td style="white-space:nowrap; font-family: monospace"><label for="pkey_${fn:escapeXml(creditCard.pkey)}"><c:out value="${aoweb:getExpirationDisplay(creditCard.expirationMonth, creditCard.expirationYear)}"/></label></td>
											<c:if test="${hasDescription}">
												<td style="white-space:nowrap">
													<logic:notEmpty name="creditCard" property="description">
														<label for="pkey_${fn:escapeXml(creditCard.pkey)}"><ao:write name="creditCard" property="description" /></label>
													</logic:notEmpty>
												</td>
											</c:if>
										</skin:lightDarkTableRow>
									</logic:iterate>
									<skin:lightDarkTableRow>
										<td style="white-space:nowrap">
											<logic:notPresent scope="request" name="automaticCard">
												<ao:input type="radio" id="pkey_" name="pkey" value="" checked="true" onchange="this.form.submitButton.disabled=true;" />
											</logic:notPresent>
											<logic:present scope="request" name="automaticCard">
												<ao:input type="radio" id="pkey_" name="pkey" value="" onchange="this.form.submitButton.disabled=false;" />
											</logic:present>
										</td>
										<td style='white-space:nowrap' colspan="${fn:escapeXml(3 + (hasDescription ? 1 : 0))}"><label for="pkey_"><ao:message key="configureAutomaticBilling.noAutomaticBilling" /></label></td>
									</skin:lightDarkTableRow>
								</tbody>
								<tfoot>
									<tr>
										<td style="white-space:nowrap;text-align:center" colspan="${fn:escapeXml(4 + (hasDescription ? 1 : 0))}">
											<ao:input type="submit" name="submitButton" value="${ao:message('configureAutomaticBilling.field.submit.label')}" />
											<%-- Disable using JavaScript to avoid dependency on JavaScript --%>
											<ao:script>document.forms["configurationAutomaticBillingForm"].submitButton.disabled = true;</ao:script>
										</td>
									</tr>
								</tfoot>
							</table>
						</skin:lightArea>
					</div></form>
				</logic:notPresent>
			</skin:contentLine>
		</skin:content>
	</skin:skin>
</ao:bundle>
