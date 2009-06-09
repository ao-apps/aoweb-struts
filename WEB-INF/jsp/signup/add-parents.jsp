<%-- aoweb-struts: Do not edit --%>
<%--
  Copyright 2007-2009 by AO Industries, Inc.,
  7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" buffer="256kb" autoFlush="true" %>
<%@ include file="/WEB-INF/jsp/taglibs.jsp" %>

<aoweb:exists path="/WEB-INF/jsp/add-parents.jsp">
    <jsp:include page="/WEB-INF/jsp/add-parents.jsp"/>
</aoweb:exists>
<skin:addParent useEncryption="false" path="/signup/SignUpIndex.ao">
    <skin:title><bean:message bundle="/signup/ApplicationResources" key="index.title"/></skin:title>
    <skin:navImageAlt><bean:message bundle="/signup/ApplicationResources" key="index.navImageAlt"/></skin:navImageAlt>
</skin:addParent>
