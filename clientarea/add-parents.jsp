<%-- aoweb-struts: Do not edit --%>
<%--
  Copyright 2007-2010 by AO Industries, Inc.,
  7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" buffer="256kb" autoFlush="true" pageEncoding="UTF-8" %>
<%@include file="/_taglibs.jsp" %>

<aoweb:exists path="/clientarea/add-parents.override.jsp">
    <jsp:include page="/clientarea/add-parents.override.jsp" />
</aoweb:exists>
<aoweb:notExists path="/clientarea/add-parents.override.jsp">
    <aoweb:exists path="/add-parents.jsp"><jsp:include page="/add-parents.jsp" /></aoweb:exists>
    <skin:parent>
        <%@include file="index.meta.jsp" %>
        <%@include file="index.children.jsp" %>
    </skin:parent>
</aoweb:notExists>
