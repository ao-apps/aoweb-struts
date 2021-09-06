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
	<skin:path>/clientarea/accounting/configure-automatic-billing-completed.do</skin:path>
	<logic:equal name="siteSettings" property="brand.aowebStrutsNoindex" value="true"><skin:meta name="robots">noindex</skin:meta></logic:equal>
	<skin:title><ao:message key="configureAutomaticBillingCompleted.title" /></skin:title>
	<skin:navImageAlt><ao:message key="configureAutomaticBillingCompleted.navImageAlt" /></skin:navImageAlt>
	<skin:keywords><ao:message key="configureAutomaticBillingCompleted.keywords" /></skin:keywords>
	<skin:description><ao:message key="configureAutomaticBillingCompleted.description" /></skin:description>
	<%@include file="add-parents.inc.jsp" %>
	<skin:parent><%@include file="credit-card-manager.meta.inc.jsp" %></skin:parent>
	<skin:skin>
		<skin:content width="600">
			<skin:contentTitle><ao:message key="configureAutomaticBillingCompleted.title" /></skin:contentTitle>
			<skin:contentHorizontalDivider />
			<skin:contentLine>
				<logic:present scope="request" name="permissionDenied">
					<%@include file="../../_permission-denied.inc.jsp" %>
				</logic:present>
				<logic:notPresent scope="request" name="permissionDenied">
					<skin:lightArea>
						<logic:present scope="request" name="creditCard">
							<bean:define scope="request" name="creditCard" id="creditCard" type="com.aoindustries.aoserv.client.payment.CreditCard" />
							<b><ao:message key="configureAutomaticBillingCompleted.setUseMonthly.title" /></b>
							<ao:hr />
							<ao:message key="configureAutomaticBillingCompleted.setUseMonthly.text" arg0="${account.name}" arg1="${aoweb:getCardNumberDisplay(creditCard.cardInfo)}" />
						</logic:present>
						<logic:notPresent scope="request" name="creditCard">
							<b><ao:message key="configureAutomaticBillingCompleted.clearUseMonthly.title" /></b>
							<ao:hr />
							<ao:message key="configureAutomaticBillingCompleted.clearUseMonthly.text" arg0="${account.name}" />
						</logic:notPresent>
						<ao:br />
						<ao:br />
						<ao:a href="credit-card-manager.do"><ao:message key="configureAutomaticBillingCompleted.creditCardManager.link" /></ao:a>
					</skin:lightArea>
				</logic:notPresent>
			</skin:contentLine>
		</skin:content>
	</skin:skin>
</ao:bundle>
