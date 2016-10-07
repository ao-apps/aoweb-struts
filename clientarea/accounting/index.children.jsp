<%-- aoweb-struts: Do not edit --%>
<%--
  Copyright 2000-2009, 2016 by AO Industries, Inc.,
  7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@include file="/_taglibs.jsp" %>

<aoweb:exists path="/clientarea/accounting/index.children.override.jsp">
    <jsp:include page="/clientarea/accounting/index.children.override.jsp" />
</aoweb:exists>
<aoweb:notExists path="/clientarea/accounting/index.children.override.jsp">
    <%--<skin:child><%@include file="account-history.meta.jsp" %></skin:child>--%>
    <skin:child><%@include file="credit-card-manager.meta.jsp" %></skin:child>
    <skin:child><%@include file="make-payment.meta.jsp" %></skin:child>
</aoweb:notExists>
