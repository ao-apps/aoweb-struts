<%-- aoweb-struts: Do not edit --%>
<%--
  Copyright 2007-2009 by AO Industries, Inc.,
  7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" buffer="256kb" autoFlush="true" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/taglibs.jsp" %>

<fmt:bundle basename="com.aoindustries.website.signup.ApplicationResources">
    <skin:addSibling useEncryption="true" path="/signup/VirtualSignUp.ao">
        <skin:title><fmt:message key="virtual.title" /></skin:title>
        <skin:navImageAlt><fmt:message key="virtual.navImageAlt" /></skin:navImageAlt>
        <skin:description><fmt:message key="virtual.description" /></skin:description>
    </skin:addSibling>
    <skin:addSibling useEncryption="true" path="/signup/virtual-dedicated-server.do">
        <skin:title><fmt:message key="virtualDedicated.title" /></skin:title>
        <skin:navImageAlt><fmt:message key="virtualDedicated.navImageAlt" /></skin:navImageAlt>
        <skin:description><fmt:message key="virtualDedicated.description" /></skin:description>
    </skin:addSibling>
    <skin:addSibling useEncryption="true" path="/signup/virtual-managed-server.do">
        <skin:title><fmt:message key="virtualManaged.title" /></skin:title>
        <skin:navImageAlt><fmt:message key="virtualManaged.navImageAlt" /></skin:navImageAlt>
        <skin:description><fmt:message key="virtualManaged.description" /></skin:description>
    </skin:addSibling>
    <skin:addSibling useEncryption="true" path="/signup/dedicated-server.do">
        <skin:title><fmt:message key="dedicated.title" /></skin:title>
        <skin:navImageAlt><fmt:message key="dedicated.navImageAlt" /></skin:navImageAlt>
        <skin:description><fmt:message key="dedicated.description" /></skin:description>
    </skin:addSibling>
    <skin:addSibling useEncryption="true" path="/signup/managed-server.do">
        <skin:title><fmt:message key="managed.title" /></skin:title>
        <skin:navImageAlt><fmt:message key="managed.navImageAlt" /></skin:navImageAlt>
        <skin:description><fmt:message key="managed.description" /></skin:description>
    </skin:addSibling>
</fmt:bundle>
