<%-- aoweb-struts --%>
<%--
  Copyright 2007-2009 by AO Industries, Inc.,
  7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" buffer="256kb" autoFlush="true" %>
<%@ include file="/WEB-INF/jsp/taglibs.jsp" %>

<aoweb:exists page="add-siblings.override.jsp">
    <jsp:include page="add-siblings.override.jsp"/>
</aoweb:exists>
<aoweb:notExists page="add-siblings.override.jsp">
    <skin:addSibling useEncryption="false" path="/clientarea/control/index.do">
        <skin:title><bean:message bundle="/clientarea/control/ApplicationResources" key="index.title"/></skin:title>
        <skin:navImageAlt><bean:message bundle="/clientarea/control/ApplicationResources" key="index.navImageAlt"/></skin:navImageAlt>
        <skin:description><bean:message bundle="/clientarea/control/ApplicationResources" key="index.description"/></skin:description>
    </skin:addSibling>
    <skin:addSibling useEncryption="false" path="/clientarea/accounting/index.do">
        <skin:title><bean:message bundle="/clientarea/accounting/ApplicationResources" key="index.title"/></skin:title>
        <skin:navImageAlt><bean:message bundle="/clientarea/accounting/ApplicationResources" key="index.navImageAlt"/></skin:navImageAlt>
        <skin:description><bean:message bundle="/clientarea/accounting/ApplicationResources" key="index.description"/></skin:description>
    </skin:addSibling>
</aoweb:notExists>
