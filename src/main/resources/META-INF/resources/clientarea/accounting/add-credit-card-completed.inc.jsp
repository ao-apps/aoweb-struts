<%-- aoweb-struts: Do not edit --%>
<%--
Copyright 2007-2009, 2015, 2016 by AO Industries, Inc.,
7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
All rights reserved.
--%>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@include file="/_taglibs.inc.jsp" %>

<skin:setContentType />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html:html lang="true" xhtml="true">
	<fmt:bundle basename="com.aoindustries.website.clientarea.accounting.ApplicationResources">
		<skin:path>/clientarea/accounting/add-credit-card-completed.do</skin:path>
		<logic:equal name="siteSettings" property="brand.aowebStrutsNoindex" value="true"><skin:meta name="ROBOTS">NOINDEX</skin:meta></logic:equal>
		<skin:title><fmt:message key="addCreditCardCompleted.title" /></skin:title>
		<skin:navImageAlt><fmt:message key="addCreditCardCompleted.navImageAlt" /></skin:navImageAlt>
		<skin:keywords><fmt:message key="addCreditCardCompleted.keywords" /></skin:keywords>
		<skin:description><fmt:message key="addCreditCardCompleted.description" /></skin:description>
		<%@include file="add-parents.inc.jsp" %>
		<skin:parent><%@include file="credit-card-manager.meta.inc.jsp" %></skin:parent>
		<skin:skin>
			<skin:content width="600">
				<skin:contentTitle><fmt:message key="addCreditCardCompleted.title" /></skin:contentTitle>
				<skin:contentHorizontalDivider />
				<skin:contentLine>
					<logic:present scope="request" name="permissionDenied">
						<%@include file="../../_permission-denied.inc.jsp" %>
					</logic:present>
					<logic:notPresent scope="request" name="permissionDenied">
						<skin:lightArea>
							<fmt:message key="addCreditCardCompleted.successMessage.title" />
							<hr />
							<fmt:message key="addCreditCardCompleted.successMessage.text">
								<fmt:param><c:out value="${fn:toLowerCase(cardNumber)}" /></fmt:param>
							</fmt:message><br />
							<br />
							<html:link action="/credit-card-manager"><fmt:message key="addCreditCardCompleted.creditCardManager.link" /></html:link>
						</skin:lightArea>
					</logic:notPresent>
				</skin:contentLine>
			</skin:content>
		</skin:skin>
	</fmt:bundle>
</html:html>
