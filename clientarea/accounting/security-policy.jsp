<%-- aoweb-struts: Do not edit --%>
<%--
  Copyright 2007-2009 by AO Industries, Inc.,
  7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" buffer="256kb" autoFlush="true" pageEncoding="UTF-8" %>
<%@include file="/_taglibs.jsp" %>

<aoweb:exists path="/clientarea/accounting/security-policy.override.jsp">
    <jsp:include page="/clientarea/accounting/security-policy.override.jsp" />
</aoweb:exists>
<aoweb:notExists path="/clientarea/accounting/security-policy.override.jsp">
    <br />
    <skin:lightArea width="500">
        <fmt:bundle basename="com.aoindustries.website.clientarea.accounting.ApplicationResources">
            <fmt:message key="securityPolicy.securityNotice.title" />
            <hr />
            <%-- Should make a per-provider notice based on root-level business --%>
            <fmt:message key="securityPolicy.securityNotice.body" />
        </fmt:bundle>
    </skin:lightArea>
</aoweb:notExists>
