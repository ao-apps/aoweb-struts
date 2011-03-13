<%-- aoweb-struts: Do not edit --%>
<%--
  Copyright 2000-2011 by AO Industries, Inc.,
  7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" buffer="256kb" autoFlush="true" pageEncoding="UTF-8" %>
<%@include file="/_taglibs.jsp" %>

<fmt:bundle basename="com.aoindustries.website.clientarea.control.ApplicationResources">
    <skin:path>/clientarea/control/index.do</skin:path>
    <skin:encrypt>false</skin:encrypt>
    <logic:equal name="siteSettings" property="brand.aowebStrutsNoindex" value="true"><skin:meta name="ROBOTS">NOINDEX</skin:meta></logic:equal>
    <skin:title><fmt:message key="index.title" /></skin:title>
    <skin:navImageAlt><fmt:message key="index.navImageAlt" /></skin:navImageAlt>
    <skin:keywords><fmt:message key="index.keywords" /></skin:keywords>
    <skin:description><fmt:message key="index.description" /></skin:description>
</fmt:bundle>
