<%-- aoweb-struts: Do not edit --%>
<%--
Copyright 2009, 2015, 2016 by AO Industries, Inc.,
7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
All rights reserved.
--%>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@include file="/_taglibs.inc.jsp" %>

<fmt:bundle basename="com.aoindustries.website.clientarea.control.ApplicationResources">
	<skin:path>/clientarea/control/vnc/vnc-console.do</skin:path>
	<logic:equal name="siteSettings" property="brand.aowebStrutsNoindex" value="true"><skin:meta name="ROBOTS">NOINDEX</skin:meta></logic:equal>
	<skin:title><fmt:message key="vnc.vncConsole.title" /></skin:title>
	<skin:navImageAlt><fmt:message key="vnc.vncConsole.navImageAlt" /></skin:navImageAlt>
	<skin:keywords><fmt:message key="vnc.vncConsole.keywords" /></skin:keywords>
	<skin:description><fmt:message key="vnc.vncConsole.description" /></skin:description>
</fmt:bundle>
