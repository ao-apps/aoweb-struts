<%-- aoweb-struts: Do not edit --%>
<%--
Copyright 2007-2009, 2015, 2016 by AO Industries, Inc.,
7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
All rights reserved.
--%>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@include file="/_taglibs.jsp" %>

<skin:setContentType />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html:html lang="true" xhtml="true">
	<fmt:bundle basename="com.aoindustries.website.clientarea.accounting.ApplicationResources">
		<skin:path>/clientarea/accounting/configure-automatic-billing-completed.do</skin:path>
		<logic:equal name="siteSettings" property="brand.aowebStrutsNoindex" value="true"><skin:meta name="ROBOTS">NOINDEX</skin:meta></logic:equal>
		<skin:title><fmt:message key="configureAutomaticBillingCompleted.title" /></skin:title>
		<skin:navImageAlt><fmt:message key="configureAutomaticBillingCompleted.navImageAlt" /></skin:navImageAlt>
		<skin:keywords><fmt:message key="configureAutomaticBillingCompleted.keywords" /></skin:keywords>
		<skin:description><fmt:message key="configureAutomaticBillingCompleted.description" /></skin:description>
		<%@include file="add-parents.jsp" %>
		<skin:parent><%@include file="credit-card-manager.meta.jsp" %></skin:parent>
		<skin:skin>
			<skin:content width="600">
				<skin:contentTitle><fmt:message key="configureAutomaticBillingCompleted.title" /></skin:contentTitle>
				<skin:contentHorizontalDivider />
				<skin:contentLine>
					<logic:present scope="request" name="permissionDenied">
						<%@include file="../../_permission-denied.jsp" %>
					</logic:present>
					<logic:notPresent scope="request" name="permissionDenied">
						<skin:lightArea>
							<logic:present scope="request" name="creditCard">
								<bean:define scope="request" name="creditCard" id="creditCard" type="com.aoindustries.aoserv.client.CreditCard" />
								<fmt:message key="configureAutomaticBillingCompleted.setUseMonthly.title" />
								<hr />
								<fmt:message key="configureAutomaticBillingCompleted.setUseMonthly.text">
									<fmt:param><c:out value="${business.accounting}" /></fmt:param>
									<fmt:param><c:out value="${fn:toLowerCase(creditCard.cardInfo)}" /></fmt:param>
								</fmt:message>
							</logic:present>
							<logic:notPresent scope="request" name="creditCard">
								<fmt:message key="configureAutomaticBillingCompleted.clearUseMonthly.title" />
								<hr />
								<fmt:message key="configureAutomaticBillingCompleted.clearUseMonthly.text">
									<fmt:param><c:out value="${business.accounting}" /></fmt:param>
								</fmt:message>
							</logic:notPresent>
							<br />
							<br />
							<html:link action="/credit-card-manager"><fmt:message key="configureAutomaticBillingCompleted.creditCardManager.link" /></html:link>
						</skin:lightArea>
					</logic:notPresent>
				</skin:contentLine>
			</skin:content>
		</skin:skin>
	</fmt:bundle>
</html:html>
