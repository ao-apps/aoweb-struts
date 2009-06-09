<%-- aoweb-struts: Do not edit --%>
<%--
  Copyright 2000-2009 by AO Industries, Inc.,
  7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" buffer="256kb" autoFlush="true" %>
<%@ include file="/WEB-INF/jsp/taglibs.jsp" %>

<aoweb:exists path="/WEB-INF/jsp/clientarea/ticket/add-siblings.override.jsp">
    <jsp:include page="/WEB-INF/jsp/clientarea/ticket/add-siblings.override.jsp"/>
</aoweb:exists>
<aoweb:notExists path="/WEB-INF/jsp/clientarea/ticket/add-siblings.override.jsp">
    <skin:addSibling useEncryption="true" path="/clientarea/ticket/create.do">
        <skin:title><bean:message bundle="/clientarea/ticket/ApplicationResources" key="create.title"/></skin:title>
        <skin:navImageAlt><bean:message bundle="/clientarea/ticket/ApplicationResources" key="create.navImageAlt"/></skin:navImageAlt>
        <skin:description><bean:message bundle="/clientarea/ticket/ApplicationResources" key="create.description"/></skin:description>
    </skin:addSibling>
</aoweb:notExists>
