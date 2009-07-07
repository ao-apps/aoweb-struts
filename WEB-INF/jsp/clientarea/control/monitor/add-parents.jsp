<%-- aoweb-struts: Do not edit --%>
<%--
  Copyright 2007-2009 by AO Industries, Inc.,
  7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" buffer="256kb" autoFlush="true" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/taglibs.jsp" %>

<%@ include file="../add-parents.jsp" %>
<fmt:bundle basename="com.aoindustries.website.clientarea.control.ApplicationResources">
    <skin:addParent useEncryption="false" path="/clientarea/control/monitor/MonitorIndex.ao">
        <skin:title><fmt:message key="monitor.index.title" /></skin:title>
        <skin:title><fmt:message key="monitor.index.navImageAlt" /></skin:title>
    </skin:addParent>
</fmt:bundle>
