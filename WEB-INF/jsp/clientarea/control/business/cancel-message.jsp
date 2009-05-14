<%-- aoweb-struts --%>
<%--
  Copyright 2003-2009 by AO Industries, Inc.,
  7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" buffer="256kb" autoFlush="true" %>
<%@ include file="/WEB-INF/jsp/taglibs.jsp" %>

<aoweb:exists page="cancel-message.override.jsp">
    <jsp:include page="cancel-message.override.jsp"/>
</aoweb:exists>
<aoweb:notExists page="cancel-message.override.jsp">
    <bean:message bundle="/clientarea/control/ApplicationResources" key="business.cancel.message"/>
</aoweb:notExists>
