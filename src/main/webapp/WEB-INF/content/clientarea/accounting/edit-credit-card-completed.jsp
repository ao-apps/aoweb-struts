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
<%@include file="/WEB-INF/taglibs.jspf" %>

<ao:bundle basename="com.aoindustries.web.struts.clientarea.accounting.i18n.ApplicationResources">
	<skin:path>/clientarea/accounting/edit-credit-card-completed.do</skin:path>
	<logic:equal name="siteSettings" property="brand.aowebStrutsNoindex" value="true"><skin:meta name="robots">noindex</skin:meta></logic:equal>
	<skin:title>
		<logic:notEqual name="editCreditCardForm" property="isActive" value="false">
			<ao:message key="editCreditCardCompleted.title.edit" />
		</logic:notEqual>
		<logic:equal name="editCreditCardForm" property="isActive" value="false">
			<ao:message key="editCreditCardCompleted.title.reactivate" />
		</logic:equal>
	</skin:title>
	<skin:navImageAlt>
		<logic:notEqual name="editCreditCardForm" property="isActive" value="false">
			<ao:message key="editCreditCardCompleted.navImageAlt.edit" />
		</logic:notEqual>
		<logic:equal name="editCreditCardForm" property="isActive" value="false">
			<ao:message key="editCreditCardCompleted.navImageAlt.reactivate" />
		</logic:equal>
	</skin:navImageAlt>
	<skin:keywords><ao:message key="editCreditCardCompleted.keywords" /></skin:keywords>
	<skin:description><ao:message key="editCreditCardCompleted.description" /></skin:description>
	<%@include file="add-parents.inc.jsp" %>
	<skin:parent><%@include file="credit-card-manager.meta.inc.jsp" %></skin:parent>
	<skin:skin>
		<skin:content width="600">
			<skin:contentTitle>
				<logic:notEqual name="editCreditCardForm" property="isActive" value="false">
					<ao:message key="editCreditCardCompleted.title.edit" />
				</logic:notEqual>
				<logic:equal name="editCreditCardForm" property="isActive" value="false">
					<ao:message key="editCreditCardCompleted.title.reactivate" />
				</logic:equal>
			</skin:contentTitle>
			<skin:contentHorizontalDivider />
			<skin:contentLine>
				<logic:present scope="request" name="permissionDenied">
					<%@include file="../../permission-denied.jspf" %>
				</logic:present>
				<logic:notPresent scope="request" name="permissionDenied">
					<skin:lightArea>
						<b>
							<logic:notEqual name="editCreditCardForm" property="isActive" value="false">
								<ao:message key="editCreditCardCompleted.successMessage.title.edit" />
							</logic:notEqual>
							<logic:equal name="editCreditCardForm" property="isActive" value="false">
								<ao:message key="editCreditCardCompleted.successMessage.title.reactivate" />
							</logic:equal>
						</b>
						<ao:hr />
						<logic:notEqual name="editCreditCardForm" property="isActive" value="false">
							<ao:message key="editCreditCardCompleted.successMessage.text.edit" arg0="${aoweb:getCardNumberDisplay(cardNumber)}" /><ao:br />
						</logic:notEqual>
						<logic:equal name="editCreditCardForm" property="isActive" value="false">
							<ao:message key="editCreditCardCompleted.successMessage.text.reactivate" arg0="${aoweb:getCardNumberDisplay(cardNumber)}" /><ao:br />
						</logic:equal>
						<ul>
							<logic:equal scope="request" name="updatedCardDetails" value="true">
								<bean:define id="somethingChanged" value="true"/>
								<li><ao:message key="editCreditCardCompleted.successMessage.updatedCardDetails" /></li>
							</logic:equal>
							<logic:equal scope="request" name="updatedCardNumber" value="true">
								<bean:define id="somethingChanged" value="true"/>
								<li><ao:message key="editCreditCardCompleted.successMessage.updatedCardNumber" /></li>
							</logic:equal>
							<logic:equal scope="request" name="updatedExpirationDate" value="true">
								<bean:define id="somethingChanged" value="true"/>
								<li><ao:message key="editCreditCardCompleted.successMessage.updatedExpirationDate" /></li>
							</logic:equal>
							<logic:equal scope="request" name="reactivatedCard" value="true">
								<bean:define id="somethingChanged" value="true"/>
								<li><ao:message key="editCreditCardCompleted.successMessage.reactivatedCard" /></li>
							</logic:equal>
							<logic:notPresent name="somethingChanged">
								<li><ao:message key="editCreditCardCompleted.successMessage.nothingChanged" /></li>
							</logic:notPresent>
						</ul>
						<ao:a href="/clientarea/accounting/credit-card-manager.do"><ao:message key="editCreditCardCompleted.creditCardManager.link" /></ao:a>
					</skin:lightArea>
				</logic:notPresent>
			</skin:contentLine>
		</skin:content>
	</skin:skin>
</ao:bundle>
