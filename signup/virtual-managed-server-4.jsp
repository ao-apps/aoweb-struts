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
	<fmt:bundle basename="com.aoindustries.website.signup.ApplicationResources">
		<skin:path>/signup/virtual-managed-server-4.do</skin:path>
		<skin:title><fmt:message key="virtualManaged.title" /></skin:title>
		<skin:navImageAlt><fmt:message key="virtualManaged.navImageAlt" /></skin:navImageAlt>
		<skin:keywords><fmt:message key="virtualManaged.keywords" /></skin:keywords>
		<skin:description><fmt:message key="virtualManaged.description" /></skin:description>
		<%@include file="add-parents.jsp" %>
		<skin:skin>
			<skin:content width="600">
				<skin:contentTitle><fmt:message key="virtualManaged.title" /></skin:contentTitle>
				<skin:contentHorizontalDivider />
				<skin:contentLine>
					<bean:define toScope="request" type="java.lang.String" id="stepNumber" value="4" />
					<bean:define type="java.lang.String" id="actionPrefix" toScope="request" value="virtual-managed-server" />
					<%@include file="managed-server-steps.jsp" %>
					<br />
					<html:form action="/virtual-managed-server-4-completed.do">
						<%@include file="signup-business-form.jsp" %>
					</html:form>
				</skin:contentLine>
			</skin:content>
		</skin:skin>
	</fmt:bundle>
</html:html>
