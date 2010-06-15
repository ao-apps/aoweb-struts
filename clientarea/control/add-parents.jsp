<%-- aoweb-struts: Do not edit --%>
<%--
  Copyright 2007-2010 by AO Industries, Inc.,
  7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" buffer="256kb" autoFlush="true" pageEncoding="UTF-8" %>
<%@include file="/_taglibs.jsp" %>

<aoweb:exists path="/clientarea/control/add-parents.override.jsp">
    <jsp:include page="/clientarea/control/add-parents.override.jsp" />
</aoweb:exists>
<aoweb:notExists path="/clientarea/control/add-parents.override.jsp">
    <%@include file="../add-parents.jsp" %>
    <skin:parent>
        <%@include file="index.meta.jsp" %>
        <%@include file="index.children.jsp" %>
    </skin:parent>
</aoweb:notExists>
