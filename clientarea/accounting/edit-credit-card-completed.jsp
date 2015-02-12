<%-- aoweb-struts: Do not edit --%>
<%--
  Copyright 2007-2009, 2015 by AO Industries, Inc.,
  7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" buffer="256kb" autoFlush="true" pageEncoding="UTF-8" %>
<%@include file="/_taglibs.jsp" %>

<skin:setContentType />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html:html lang="true" xhtml="true">
	<fmt:bundle basename="com.aoindustries.website.clientarea.accounting.ApplicationResources">
		<skin:path>/clientarea/accounting/edit-credit-card-completed.do</skin:path>
		<logic:equal name="siteSettings" property="brand.aowebStrutsNoindex" value="true"><skin:meta name="ROBOTS">NOINDEX</skin:meta></logic:equal>
		<skin:title>
			<logic:notEqual name="editCreditCardForm" property="isActive" value="false">
				<fmt:message key="editCreditCardCompleted.title.edit" />
			</logic:notEqual>
			<logic:equal name="editCreditCardForm" property="isActive" value="false">
				<fmt:message key="editCreditCardCompleted.title.reactivate" />
			</logic:equal>
		</skin:title>
		<skin:navImageAlt>
			<logic:notEqual name="editCreditCardForm" property="isActive" value="false">
				<fmt:message key="editCreditCardCompleted.navImageAlt.edit" />
			</logic:notEqual>
			<logic:equal name="editCreditCardForm" property="isActive" value="false">
				<fmt:message key="editCreditCardCompleted.navImageAlt.reactivate" />
			</logic:equal>
		</skin:navImageAlt>
		<skin:keywords><fmt:message key="editCreditCardCompleted.keywords" /></skin:keywords>
		<skin:description><fmt:message key="editCreditCardCompleted.description" /></skin:description>
		<%@include file="add-parents.jsp" %>
		<skin:parent><%@include file="credit-card-manager.meta.jsp" %></skin:parent>
		<skin:skin>
			<skin:content width="600">
				<skin:contentTitle>
					<logic:notEqual name="editCreditCardForm" property="isActive" value="false">
						<fmt:message key="editCreditCardCompleted.title.edit" />
					</logic:notEqual>
					<logic:equal name="editCreditCardForm" property="isActive" value="false">
						<fmt:message key="editCreditCardCompleted.title.reactivate" />
					</logic:equal>
				</skin:contentTitle>
				<skin:contentHorizontalDivider />
				<skin:contentLine>
					<logic:present scope="request" name="permissionDenied">
						<%@include file="../../_permission-denied.jsp" %>
					</logic:present>
					<logic:notPresent scope="request" name="permissionDenied">
						<skin:lightArea>
							<logic:notEqual name="editCreditCardForm" property="isActive" value="false">
								<fmt:message key="editCreditCardCompleted.successMessage.title.edit" />
							</logic:notEqual>
							<logic:equal name="editCreditCardForm" property="isActive" value="false">
								<fmt:message key="editCreditCardCompleted.successMessage.title.reactivate" />
							</logic:equal>
							<hr />
							<logic:notEqual name="editCreditCardForm" property="isActive" value="false">
								<fmt:message key="editCreditCardCompleted.successMessage.text.edit">
									<fmt:param><c:out value="${fn:toLowerCase(cardNumber)}" /></fmt:param>
								</fmt:message><br />
							</logic:notEqual>
							<logic:equal name="editCreditCardForm" property="isActive" value="false">
								<fmt:message key="editCreditCardCompleted.successMessage.text.reactivate">
									<fmt:param><c:out value="${fn:toLowerCase(cardNumber)}" /></fmt:param>
								</fmt:message><br />
							</logic:equal>
							<ul>
								<logic:equal scope="request" name="updatedCardDetails" value="true">
									<bean:define id="somethingChanged" value="true"/>
									<li><fmt:message key="editCreditCardCompleted.successMessage.updatedCardDetails" /></li>
								</logic:equal>
								<logic:equal scope="request" name="updatedCardNumber" value="true">
									<bean:define id="somethingChanged" value="true"/>
									<li><fmt:message key="editCreditCardCompleted.successMessage.updatedCardNumber" /></li>
								</logic:equal>
								<logic:equal scope="request" name="updatedExpirationDate" value="true">
									<bean:define id="somethingChanged" value="true"/>
									<li><fmt:message key="editCreditCardCompleted.successMessage.updatedExpirationDate" /></li>
								</logic:equal>
								<logic:equal scope="request" name="reactivatedCard" value="true">
									<bean:define id="somethingChanged" value="true"/>
									<li><fmt:message key="editCreditCardCompleted.successMessage.reactivatedCard" /></li>
								</logic:equal>
								<logic:notPresent name="somethingChanged">
									<li><fmt:message key="editCreditCardCompleted.successMessage.nothingChanged" /></li>
								</logic:notPresent>
							</ul>
							<html:link action="/credit-card-manager"><fmt:message key="editCreditCardCompleted.creditCardManager.link" /></html:link>
						</skin:lightArea>
					</logic:notPresent>
				</skin:contentLine>
			</skin:content>
		</skin:skin>
	</fmt:bundle>
</html:html>
