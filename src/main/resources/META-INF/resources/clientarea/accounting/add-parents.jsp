<%-- aoweb-struts: Do not edit --%>
<%--
Copyright 2007-2009, 2016 by AO Industries, Inc.,
7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
All rights reserved.
--%>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@include file="/_taglibs.jsp" %>

<aoweb:exists path="/clientarea/accounting/add-parents.override.jsp">
	<jsp:include page="/clientarea/accounting/add-parents.override.jsp" />
</aoweb:exists>
<aoweb:notExists path="/clientarea/accounting/add-parents.override.jsp">
	<%@include file="../add-parents.jsp" %>
	<skin:parent>
		<%@include file="index.meta.jsp" %>
		<%@include file="index.children.jsp" %>
	</skin:parent>
</aoweb:notExists>
