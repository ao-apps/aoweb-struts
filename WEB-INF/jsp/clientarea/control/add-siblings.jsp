<%-- aoweb-struts: Do not edit --%>
<%--
  Copyright 2007-2009 by AO Industries, Inc.,
  7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" buffer="256kb" autoFlush="true" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/taglibs.jsp" %>

<aoweb:exists path="/WEB-INF/jsp/clientarea/control/add-siblings.override.jsp">
    <jsp:include page="/WEB-INF/jsp/clientarea/control/add-siblings.override.jsp" />
</aoweb:exists>
<aoweb:notExists path="/WEB-INF/jsp/clientarea/control/add-siblings.override.jsp">
    <fmt:bundle basename="com.aoindustries.website.clientarea.control.ApplicationResources">
        <skin:addSibling useEncryption="false" path="/clientarea/control/password/index.do">
            <skin:title><fmt:message key="password.index.title" /></skin:title>
            <skin:navImageAlt><fmt:message key="password.index.navImageAlt" /></skin:navImageAlt>
            <skin:description><fmt:message key="password.index.description" /></skin:description>
        </skin:addSibling>
    </fmt:bundle>
</aoweb:notExists>
