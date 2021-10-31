<%--
aoweb-struts - Template webapp for legacy Struts-based site framework with AOServ Platform control panels.
Copyright (C) 2021  AO Industries, Inc.
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
along with aoweb-struts.  If not, see <https://www.gnu.org/licenses/>.
--%><%@ page language="java" pageEncoding="UTF-8"
%><%@ page isErrorPage="true"
%><%@include file="/WEB-INF/taglibs.jspf"
%><%
	if(exception != null) log(null, exception);
	// Set the error status
	if(!response.isCommitted()) response.setStatus(HttpServletResponse. SC_UNAUTHORIZED);

	// Set siteSettings request attribute if not yet done
	com.aoindustries.web.struts.SiteSettings siteSettings = com.aoindustries.web.struts.Constants.SITE_SETTINGS.context(request).get();
	if(siteSettings == null) {
		siteSettings = com.aoindustries.web.struts.SiteSettings.getInstance(getServletContext());
		com.aoindustries.web.struts.Constants.SITE_SETTINGS.context(request).set(siteSettings);
	}

	// Set locale request attribute if not yet done
	if(com.aoindustries.web.struts.Constants.LOCALE.context(request).get() == null) {
		java.util.Locale locale = com.aoindustries.web.struts.LocaleFilter.getEffectiveLocale(siteSettings, request, response);
		com.aoindustries.web.struts.Constants.LOCALE.context(request).set(locale);
	}

	// Set the skin request attribute if not yet done
	if(com.aoindustries.web.struts.Constants.SKIN.context(request).get() == null) {
		com.aoindustries.web.struts.Skin skin = com.aoindustries.web.struts.Skin.getSkin(siteSettings, request);
		com.aoindustries.web.struts.Constants.SKIN.context(request).set(skin);
	}
%>
<ao:bundle basename="com.aoindustries.web.struts.i18n.ApplicationResources">
	<skin:path>/unauthorized.do</skin:path>
	<skin:title><ao:message key="unauthorized.title" /></skin:title>
	<skin:navImageAlt><ao:message key="unauthorized.navImageAlt" /></skin:navImageAlt>
	<skin:description><ao:message key="unauthorized.description" /></skin:description>
	<%@include file="add-parents.jspf" %>
	<skin:skin>
		<skin:content width="600">
			<skin:contentTitle><ao:message key="unauthorized.title" /></skin:contentTitle>
			<skin:contentHorizontalDivider />
			<skin:contentLine>
				<ao:message key="unauthorized.description" /><ao:br />
				<ao:br />
				<%@include file="error-data.jspf" %>
			</skin:contentLine>
		</skin:content>
	</skin:skin>
</ao:bundle>
