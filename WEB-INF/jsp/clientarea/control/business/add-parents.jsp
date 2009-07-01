<%-- aoweb-struts: Do not edit --%>
<%--
  Copyright 2007-2009 by AO Industries, Inc.,
  7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" buffer="256kb" autoFlush="true" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/taglibs.jsp" %>

<aoweb:exists path="/WEB-INF/jsp/clientarea/control/business/add-parents.override.jsp">
    <jsp:include page="/WEB-INF/jsp/clientarea/control/business/add-parents.override.jsp"/>
</aoweb:exists>
<aoweb:notExists path="/WEB-INF/jsp/clientarea/control/business/add-parents.override.jsp">
    <%@ include file="../add-parents.jsp" %>
    <skin:addParent useEncryption="false" path="/clientarea/control/business/BusinessIndex.ao">
        <skin:title><bean:message bundle="/clientarea/control/ApplicationResources" key="business.index.title"/></skin:title>
        <skin:title><bean:message bundle="/clientarea/control/ApplicationResources" key="business.index.navImageAlt"/></skin:title>
    </skin:addParent>
</aoweb:notExists>
