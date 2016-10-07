<%-- aoweb-struts: Do not edit --%>
<%--
  Copyright 2007-2009, 2016 by AO Industries, Inc.,
  7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@include file="/_taglibs.jsp" %>

<c:set var="rootConn" value="${siteSettings.rootAOServConnector}" />
<c:set var="activePackageDefinitions" value="${rootConn.thisBusinessAdministrator.username['package'].business.activePackageDefinitions}" />
<c:set var="packageCategories" value="${rootConn.packageCategories.map}" />
<c:if test="${activePackageDefinitions[packageCategories.application]!=null}">
    <skin:child><%@include file="application.meta.jsp" %></skin:child>
</c:if>
<c:if test="${activePackageDefinitions[packageCategories.virtual]!=null}">
    <skin:child><%@include file="virtual-hosting.meta.jsp" %></skin:child>
</c:if>
<c:if test="${activePackageDefinitions[packageCategories.virtual_dedicated]!=null}">
    <skin:child><%@include file="virtual-dedicated-server.meta.jsp" %></skin:child>
</c:if>
<c:if test="${activePackageDefinitions[packageCategories.virtual_managed]!=null}">
    <skin:child><%@include file="virtual-managed-server.meta.jsp" %></skin:child>
</c:if>
<c:if test="${activePackageDefinitions[packageCategories.dedicated]!=null}">
    <skin:child><%@include file="dedicated-server.meta.jsp" %></skin:child>
</c:if>
<c:if test="${activePackageDefinitions[packageCategories.managed]!=null}">
    <skin:child><%@include file="managed-server.meta.jsp" %></skin:child>
</c:if>
<c:if test="${activePackageDefinitions[packageCategories.aoserv]!=null}">
    <skin:child><%@include file="aoserv.meta.jsp" %></skin:child>
</c:if>
<c:if test="${activePackageDefinitions[packageCategories.backup]!=null}">
    <skin:child><%@include file="backup.meta.jsp" %></skin:child>
</c:if>
<c:if test="${activePackageDefinitions[packageCategories.colocation]!=null}">
    <skin:child><%@include file="colocation.meta.jsp" %></skin:child>
</c:if>
<c:if test="${activePackageDefinitions[packageCategories.reseller]!=null}">
    <skin:child><%@include file="reseller.meta.jsp" %></skin:child>
</c:if>
<c:if test="${activePackageDefinitions[packageCategories.sysadmin]!=null}">
    <skin:child><%@include file="system-administration.meta.jsp" %></skin:child>
</c:if>
