<%-- aoweb-struts: Do not edit --%>
<%--
  Copyright 2007-2009 by AO Industries, Inc.,
  7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" buffer="256kb" autoFlush="true" %>
<%@ include file="/WEB-INF/jsp/taglibs.jsp" %>

<skin:addSibling useEncryption="true" path="/signup/VirtualSignUp.ao">
    <skin:title><bean:message bundle="/signup/ApplicationResources" key="virtual.title"/></skin:title>
    <skin:navImageAlt><bean:message bundle="/signup/ApplicationResources" key="virtual.navImageAlt"/></skin:navImageAlt>
    <skin:description><bean:message bundle="/signup/ApplicationResources" key="virtual.description"/></skin:description>
</skin:addSibling>
<skin:addSibling useEncryption="true" path="/signup/virtual-dedicated-server.do">
    <skin:title><bean:message bundle="/signup/ApplicationResources" key="virtualDedicated.title"/></skin:title>
    <skin:navImageAlt><bean:message bundle="/signup/ApplicationResources" key="virtualDedicated.navImageAlt"/></skin:navImageAlt>
    <skin:description><bean:message bundle="/signup/ApplicationResources" key="virtualDedicated.description"/></skin:description>
</skin:addSibling>
<skin:addSibling useEncryption="true" path="/signup/virtual-managed-server.do">
    <skin:title><bean:message bundle="/signup/ApplicationResources" key="virtualManaged.title"/></skin:title>
    <skin:navImageAlt><bean:message bundle="/signup/ApplicationResources" key="virtualManaged.navImageAlt"/></skin:navImageAlt>
    <skin:description><bean:message bundle="/signup/ApplicationResources" key="virtualManaged.description"/></skin:description>
</skin:addSibling>
<skin:addSibling useEncryption="true" path="/signup/dedicated-server.do">
    <skin:title><bean:message bundle="/signup/ApplicationResources" key="dedicated.title"/></skin:title>
    <skin:navImageAlt><bean:message bundle="/signup/ApplicationResources" key="dedicated.navImageAlt"/></skin:navImageAlt>
    <skin:description><bean:message bundle="/signup/ApplicationResources" key="dedicated.description"/></skin:description>
</skin:addSibling>
<skin:addSibling useEncryption="true" path="/signup/managed-server.do">
    <skin:title><bean:message bundle="/signup/ApplicationResources" key="managed.title"/></skin:title>
    <skin:navImageAlt><bean:message bundle="/signup/ApplicationResources" key="managed.navImageAlt"/></skin:navImageAlt>
    <skin:description><bean:message bundle="/signup/ApplicationResources" key="managed.description"/></skin:description>
</skin:addSibling>
