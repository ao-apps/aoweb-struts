<%-- aoweb-struts --%>
<%--
  Copyright 2007-2009 by AO Industries, Inc.,
  7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" buffer="256kb" autoFlush="true" %>
<%@ include file="/WEB-INF/jsp/taglibs.jsp" %>

<aoweb:exists path="/WEB-INF/jsp/clientarea/accounting/security-policy.override.jsp">
    <jsp:include page="/WEB-INF/jsp/clientarea/accounting/security-policy.override.jsp"/>
</aoweb:exists>
<aoweb:notExists path="/WEB-INF/jsp/clientarea/accounting/security-policy.override.jsp">
    <br>
    <skin:lightArea width="500">
        <bean:message bundle="/clientarea/accounting/ApplicationResources" key="securityPolicy.securityNotice.title"/>
        <hr>
        <%-- Should make a per-provider notice based on root-level business --%>
        <bean:message bundle="/clientarea/accounting/ApplicationResources" key="securityPolicy.securityNotice.body"/>
    </skin:lightArea>
</aoweb:notExists>
