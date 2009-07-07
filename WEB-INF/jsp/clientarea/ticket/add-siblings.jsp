<%-- aoweb-struts: Do not edit --%>
<%--
  Copyright 2000-2009 by AO Industries, Inc.,
  7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" buffer="256kb" autoFlush="true" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/taglibs.jsp" %>

<aoweb:exists path="/WEB-INF/jsp/clientarea/ticket/add-siblings.override.jsp">
    <jsp:include page="/WEB-INF/jsp/clientarea/ticket/add-siblings.override.jsp" />
</aoweb:exists>
<aoweb:notExists path="/WEB-INF/jsp/clientarea/ticket/add-siblings.override.jsp">
    <fmt:bundle basename="com.aoindustries.website.clientarea.ticket.ApplicationResources">
        <skin:addSibling useEncryption="true" path="/clientarea/ticket/create.do">
            <skin:title><fmt:message key="create.title" /></skin:title>
            <skin:navImageAlt><fmt:message key="create.navImageAlt" /></skin:navImageAlt>
            <skin:description><fmt:message key="create.description" /></skin:description>
        </skin:addSibling>
    </fmt:bundle>
</aoweb:notExists>
