<%-- aoweb-struts: Do not edit --%>
<%--
Copyright 2009, 2015, 2016 by AO Industries, Inc.,
7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
All rights reserved.
--%>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@include file="/_taglibs.inc.jsp" %>

<skin:setContentType />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html:html lang="true" xhtml="true">
	<fmt:bundle basename="com.aoindustries.website.signup.ApplicationResources">
		<skin:path>/signup/aoserv.do</skin:path>
		<skin:title><fmt:message key="aoserv.title" /></skin:title>
		<skin:navImageAlt><fmt:message key="aoserv.navImageAlt" /></skin:navImageAlt>
		<skin:keywords><fmt:message key="aoserv.keywords" /></skin:keywords>
		<skin:description><fmt:message key="aoserv.description" /></skin:description>
		<%@include file="add-parents.inc.jsp" %>
		<skin:skin>
			<skin:content width="600">
				<skin:contentTitle><fmt:message key="aoserv.title" /></skin:contentTitle>
				<skin:contentHorizontalDivider />
				<skin:contentLine>
					<bean:define toScope="request" type="java.lang.String" id="stepNumber" value="6" />
					<bean:define type="java.lang.String" id="actionPrefix" toScope="request" value="aoserv" />
					<%@include file="minimal-steps.inc.jsp" %>
					<br />
					<%@include file="minimal-confirmation-completed.inc.jsp" %>
				</skin:contentLine>
			</skin:content>
		</skin:skin>
	</fmt:bundle>
</html:html>
