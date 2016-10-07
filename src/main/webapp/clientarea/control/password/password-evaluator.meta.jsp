<%-- aoweb-struts: Do not edit --%>
<%--
  Copyright 2000-2009, 2015, 2016 by AO Industries, Inc.,
  7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@include file="/_taglibs.jsp" %>

<fmt:bundle basename="com.aoindustries.website.clientarea.control.ApplicationResources">
	<skin:path>/clientarea/control/password/password-evaluator.do</skin:path>
	<logic:equal name="siteSettings" property="brand.aowebStrutsNoindex" value="true"><skin:meta name="ROBOTS">NOINDEX</skin:meta></logic:equal>
	<skin:title><fmt:message key="password.passwordEvaluator.title" /></skin:title>
	<skin:navImageAlt><fmt:message key="password.passwordEvaluator.navImageAlt" /></skin:navImageAlt>
	<skin:keywords><fmt:message key="password.passwordEvaluator.keywords" /></skin:keywords>
	<skin:description><fmt:message key="password.passwordEvaluator.description" /></skin:description>
</fmt:bundle>
