<%--
aoweb-struts - Template webapp for legacy Struts-based site framework with AOServ Platform control panels.
Copyright (C) 2009, 2015, 2016, 2019, 2020, 2021  AO Industries, Inc.
	support@aoindustries.com
	7262 Bull Pen Cir
	Mobile, AL 36695

This file is part of aoweb-struts.

aoweb-struts is free software: you can redistribute it and/or modify
it under the terms of the GNU Lesser General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

aoweb-struts is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public License
along with aoweb-struts.  If not, see <http://www.gnu.org/licenses/>.
--%>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/taglibs.jspf" %>

<ao:bundle basename="com.aoindustries.web.struts.i18n.ApplicationResources">
	<skin:path>/set-resource-bundle-value.do</skin:path>
	<skin:meta name="robots">noindex</skin:meta>
	<skin:title><ao:message key="setResourceBundleValue.title" /></skin:title>
	<skin:navImageAlt><ao:message key="setResourceBundleValue.navImageAlt" /></skin:navImageAlt>
	<skin:keywords><ao:message key="setResourceBundleValue.keywords" /></skin:keywords>
	<skin:description><ao:message key="setResourceBundleValue.description" /></skin:description>
	<aoweb:exists path="/WEB-INF/content/add-parents.inc.jsp"><jsp:include page="/WEB-INF/content/add-parents.inc.jsp" /></aoweb:exists>
	<skin:skin>
		<skin:content width="600">
			<skin:contentTitle><ao:message key="setResourceBundleValue.title" /></skin:contentTitle>
			<skin:contentHorizontalDivider />
			<skin:contentLine>
				<skin:lightArea>
					<b><ao:message key="setResourceBundleValue.header" /></b>
					<ao:hr />
					<table class="ao-no-border">
						<tbody>
							<tr><th style="text-align:left"><ao:message key="setResourceBundleValue.baseName.label" /></th><td><ao:write scope="request" name="baseName" /></td></tr>
							<tr><th style="text-align:left"><ao:message key="setResourceBundleValue.locale.label" /></th><td><ao:write scope="request" name="locale" /></td></tr>
							<tr><th style="text-align:left"><ao:message key="setResourceBundleValue.key.label" /></th><td><ao:write scope="request" name="key" /></td></tr>
							<tr><th style="text-align:left"><ao:message key="setResourceBundleValue.value.label" /></th><td><ao:write scope="request" name="value" /></td></tr>
							<tr><th style="text-align:left"><ao:message key="setResourceBundleValue.modified.label" /></th><td><ao:write scope="request" name="modified" /></td></tr>
						</tbody>
					</table>
				</skin:lightArea>
			</skin:contentLine>
		</skin:content>
	</skin:skin>
</ao:bundle>
