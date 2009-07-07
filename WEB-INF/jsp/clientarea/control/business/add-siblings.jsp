<%-- aoweb-struts: Do not edit --%>
<%--
  Copyright 2007-2009 by AO Industries, Inc.,
  7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" buffer="256kb" autoFlush="true" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/taglibs.jsp" %>

<aoweb:exists path="/WEB-INF/jsp/clientarea/control/business/add-siblings.override.jsp">
    <jsp:include page="/WEB-INF/jsp/clientarea/control/business/add-siblings.override.jsp" />
</aoweb:exists>
<aoweb:notExists path="/WEB-INF/jsp/clientarea/control/business/add-siblings.override.jsp">
    <fmt:bundle basename="com.aoindustries.website.clientarea.control.ApplicationResources">
        <skin:addSibling useEncryption="true" path="/clientarea/control/business/cancel.do">
            <skin:title><fmt:message key="business.cancel.title" /></skin:title>
            <skin:navImageAlt><fmt:message key="business.cancel.navImageAlt" /></skin:navImageAlt>
            <skin:description><fmt:message key="business.cancel.description" /></skin:description>
        </skin:addSibling>
        <skin:addSibling useEncryption="true" path="/clientarea/control/business/Disable.ao">
            <skin:title><fmt:message key="business.disable.title" /></skin:title>
            <skin:navImageAlt><fmt:message key="business.disable.navImageAlt" /></skin:navImageAlt>
            <skin:description><fmt:message key="business.disable.description" /></skin:description>
        </skin:addSibling>
        <skin:addSibling useEncryption="true" path="/clientarea/control/business/Profiles.ao">
            <skin:title><fmt:message key="business.profiles.title" /></skin:title>
            <skin:navImageAlt><fmt:message key="business.profiles.navImageAlt" /></skin:navImageAlt>
            <skin:description><fmt:message key="business.profiles.description" /></skin:description>
        </skin:addSibling>
    </fmt:bundle>
</aoweb:notExists>
