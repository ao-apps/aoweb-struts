<%-- aoweb-struts: Do not edit --%>
<%--
  Copyright 2009, 2015 by AO Industries, Inc.,
  7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" buffer="256kb" autoFlush="true" pageEncoding="UTF-8" %>
<%@include file="/_taglibs.jsp" %>

<skin:setContentType />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html:html lang="true" xhtml="true">
	<fmt:bundle basename="com.aoindustries.website.ApplicationResources">
		<skin:path>/set-resource-bundle-value.do</skin:path>
		<skin:meta name="ROBOTS">NOINDEX</skin:meta>
		<skin:title><fmt:message key="setResourceBundleValue.title" /></skin:title>
		<skin:navImageAlt><fmt:message key="setResourceBundleValue.navImageAlt" /></skin:navImageAlt>
		<skin:keywords><fmt:message key="setResourceBundleValue.keywords" /></skin:keywords>
		<skin:description><fmt:message key="setResourceBundleValue.description" /></skin:description>
		<aoweb:exists path="/add-parents.jsp"><jsp:include page="/add-parents.jsp" /></aoweb:exists>
		<skin:skin>
			<skin:content width="600">
				<skin:contentTitle><fmt:message key="setResourceBundleValue.title" /></skin:contentTitle>
				<skin:contentHorizontalDivider />
				<skin:contentLine>
					<skin:lightArea>
						<b><fmt:message key="setResourceBundleValue.header" /></b>
						<hr />
						<table border="0" cellspacing="0" cellpadding="2">
							<tr><th style="text-align:left"><fmt:message key="setResourceBundleValue.baseName.label" /></th><td><ao:write scope="request" name="baseName" /></td></tr>
							<tr><th style="text-align:left"><fmt:message key="setResourceBundleValue.locale.label" /></th><td><ao:write scope="request" name="locale" /></td></tr>
							<tr><th style="text-align:left"><fmt:message key="setResourceBundleValue.key.label" /></th><td><ao:write scope="request" name="key" /></td></tr>
							<tr><th style="text-align:left"><fmt:message key="setResourceBundleValue.value.label" /></th><td><ao:write scope="request" name="value" /></td></tr>
							<tr><th style="text-align:left"><fmt:message key="setResourceBundleValue.modified.label" /></th><td><ao:write scope="request" name="modified" /></td></tr>
						</table>
					</skin:lightArea>
				</skin:contentLine>
			</skin:content>
		</skin:skin>
	</fmt:bundle>
</html:html>
