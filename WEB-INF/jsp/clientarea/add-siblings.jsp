<%-- aoweb-struts: Do not edit --%>
<%--
  Copyright 2007-2009 by AO Industries, Inc.,
  7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" buffer="256kb" autoFlush="true" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/taglibs.jsp" %>

<aoweb:exists path="/WEB-INF/jsp/clientarea/add-siblings.override.jsp">
    <jsp:include page="/WEB-INF/jsp/clientarea/add-siblings.override.jsp" />
</aoweb:exists>
<aoweb:notExists path="/WEB-INF/jsp/clientarea/add-siblings.override.jsp">
    <fmt:bundle basename="com.aoindustries.website.clientarea.control.ApplicationResources">
        <skin:addSibling useEncryption="false" path="/clientarea/control/index.do">
            <skin:title><fmt:message key="index.title" /></skin:title>
            <skin:navImageAlt><fmt:message key="index.navImageAlt" /></skin:navImageAlt>
            <skin:description><fmt:message key="index.description" /></skin:description>
        </skin:addSibling>
    </fmt:bundle>
    <fmt:bundle basename="com.aoindustries.website.clientarea.accounting.ApplicationResources">
        <skin:addSibling useEncryption="false" path="/clientarea/accounting/index.do">
            <skin:title><fmt:message key="index.title" /></skin:title>
            <skin:navImageAlt><fmt:message key="index.navImageAlt" /></skin:navImageAlt>
            <skin:description><fmt:message key="index.description" /></skin:description>
        </skin:addSibling>
    </fmt:bundle>
    <fmt:bundle basename="com.aoindustries.website.clientarea.ticket.ApplicationResources">
        <skin:addSibling useEncryption="true" path="/clientarea/ticket/index.do">
            <skin:title><fmt:message key="index.title" /></skin:title>
            <skin:navImageAlt><fmt:message key="index.navImageAlt" /></skin:navImageAlt>
            <skin:description><fmt:message key="index.description" /></skin:description>
        </skin:addSibling>
    </fmt:bundle>
</aoweb:notExists>
