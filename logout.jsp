<%-- aoweb-struts: Do not edit --%>
<%--
  Copyright 2007-2009, 2015, 2016 by AO Industries, Inc.,
  7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@include file="/_taglibs.jsp" %>

<skin:setContentType />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html:html lang="true" xhtml="true">
	<fmt:bundle basename="com.aoindustries.website.ApplicationResources">
		<skin:path>/logout.do</skin:path>
		<skin:meta name="ROBOTS">NOINDEX</skin:meta>
		<skin:title><fmt:message key="logout.title" /></skin:title>
		<skin:navImageAlt><fmt:message key="logout.navImageAlt" /></skin:navImageAlt>
		<skin:keywords><fmt:message key="logout.keywords" /></skin:keywords>
		<skin:description><fmt:message key="logout.description" /></skin:description>
		<aoweb:exists path="/add-parents.jsp"><jsp:include page="/add-parents.jsp" /></aoweb:exists>
		<skin:skin>
			<skin:content width="600">
				<skin:contentTitle><fmt:message key="logout.title" /></skin:contentTitle>
				<skin:contentHorizontalDivider />
				<skin:contentLine>
					<fmt:message key="logout.text" />
				</skin:contentLine>
			</skin:content>
		</skin:skin>
	</fmt:bundle>
</html:html>
