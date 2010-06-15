<%-- aoweb-struts: Do not edit --%>
<%--
  Copyright 2003-2010 by AO Industries, Inc.,
  7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" buffer="256kb" autoFlush="true" pageEncoding="UTF-8" %>
<%@include file="/_taglibs.jsp" %>

<aoweb:exists path="/clientarea/control/business/cancel-message.override.jsp">
    <jsp:include page="/clientarea/control/business/cancel-message.override.jsp" />
</aoweb:exists>
<aoweb:notExists path="/clientarea/control/business/cancel-message.override.jsp">
    <fmt:bundle basename="com.aoindustries.website.clientarea.control.ApplicationResources">
        <fmt:message key="business.cancel.message" />
    </fmt:bundle>
</aoweb:notExists>
