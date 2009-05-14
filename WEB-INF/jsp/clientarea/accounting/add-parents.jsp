<%-- aoweb-struts --%>
<%--
  Copyright 2007-2009 by AO Industries, Inc.,
  7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" buffer="256kb" autoFlush="true" %>
<%@ include file="/WEB-INF/jsp/taglibs.jsp" %>

<aoweb:exists page="add-parents.override.jsp">
    <jsp:include page="add-parents.override.jsp"/>
</aoweb:exists>
<aoweb:notExists page="add-parents.override.jsp">
    <%@ include file="../add-parents.jsp" %>
    <skin:addParent useEncryption="false" path="/clientarea/accounting/index.do">
        <skin:title><bean:message bundle="/clientarea/accounting/ApplicationResources" key="index.title"/></skin:title>
        <skin:title><bean:message bundle="/clientarea/accounting/ApplicationResources" key="index.navImageAlt"/></skin:title>
    </skin:addParent>
</aoweb:notExists>
