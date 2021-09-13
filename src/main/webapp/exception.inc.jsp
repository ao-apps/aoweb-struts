<%--
aoweb-struts - Template webapp for legacy Struts-based site framework with AOServ Platform control panels.
Copyright (C) 2007-2009, 2015, 2016, 2019, 2020, 2021  AO Industries, Inc.
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
<%@ page isErrorPage="true" %>
<% response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); %>
<%@include file="/_taglibs.inc.jsp" %>
<%
	// Set siteSettings request attribute if not yet done
	com.aoindustries.web.struts.SiteSettings siteSettings = (com.aoindustries.web.struts.SiteSettings)request.getAttribute(com.aoindustries.web.struts.Constants.SITE_SETTINGS);
	if(siteSettings == null) {
		siteSettings = com.aoindustries.web.struts.SiteSettings.getInstance(getServletContext());
		request.setAttribute(com.aoindustries.web.struts.Constants.SITE_SETTINGS, siteSettings);
	}

	// Set locale request attribute if not yet done
	if(request.getAttribute(com.aoindustries.web.struts.Constants.LOCALE) == null) {
		java.util.Locale locale = com.aoindustries.web.struts.LocaleFilter.getEffectiveLocale(siteSettings, request, response);
		request.setAttribute(com.aoindustries.web.struts.Constants.LOCALE, locale);
	}

	// Set the skin request attribute if not yet done
	if(request.getAttribute(com.aoindustries.web.struts.Constants.SKIN) == null) {
		com.aoindustries.web.struts.Skin skin = com.aoindustries.web.struts.Skin.getSkin(siteSettings, request);
		request.setAttribute(com.aoindustries.web.struts.Constants.SKIN, skin);
	}
%>
<ao:bundle basename="com.aoindustries.web.struts.i18n.ApplicationResources">
	<skin:path>/exception.do</skin:path>
	<skin:title><ao:message key="exception.title" /></skin:title>
	<skin:navImageAlt><ao:message key="exception.navImageAlt" /></skin:navImageAlt>
	<skin:keywords><ao:message key="exception.keywords" /></skin:keywords>
	<skin:description><ao:message key="exception.description" /></skin:description>
	<aoweb:exists path="/add-parents.inc.jsp"><jsp:include page="/add-parents.inc.jsp" /></aoweb:exists>
	<skin:skin>
		<skin:content width="600">
			<skin:contentTitle><ao:message key="exception.title" /></skin:contentTitle>
			<skin:contentHorizontalDivider />
			<skin:contentLine>
				<ao:message key="exception.message" /><ao:br />
				<ao:br />
				<logic:equal scope="request" name="siteSettings" property="exceptionShowError" value="true">
					<%-- Error Data --%>
					<logic:present name="javax.servlet.jsp.jspPageContext" property="errorData">
						<skin:lightArea>
							<b><ao:message key="exception.jspException.title" /></b>
							<ao:hr />
							<table class="ao-grid">
								<tbody>
									<tr>
										<th style='white-space:nowrap; text-align:left'><ao:message key="exception.servletName.header" /></th>
										<td style="white-space:nowrap"><ao:write name="javax.servlet.jsp.jspPageContext" property="errorData.servletName" /></td>
									</tr>
									<tr>
										<th style='white-space:nowrap; text-align:left'><ao:message key="exception.requestURI.header" /></th>
										<td style="white-space:nowrap"><ao:write name="javax.servlet.jsp.jspPageContext" property="errorData.requestURI" /></td>
									</tr>
									<tr>
										<th style='white-space:nowrap; text-align:left'><ao:message key="exception.statusCode.header" /></th>
										<td style="white-space:nowrap"><ao:write name="javax.servlet.jsp.jspPageContext" property="errorData.statusCode" /></td>
									</tr>
									<tr>
										<th style='white-space:nowrap; text-align:left'><ao:message key="exception.throwable.header" /></th>
										<td style="white-space:nowrap">
											<logic:notEmpty name="javax.servlet.jsp.jspPageContext" property="errorData.throwable">
												<pre><ao:getStackTraces name="javax.servlet.jsp.jspPageContext" property="errorData.throwable" /></pre>
											</logic:notEmpty>
											<logic:empty name="javax.servlet.jsp.jspPageContext" property="errorData.throwable">
												&#160;
											</logic:empty>
										</td>
									</tr>
								</tbody>
							</table>
						</skin:lightArea><ao:br />
						<ao:br />
					</logic:present>
					<%-- Struts Exceptions --%>
					<logic:present scope="request" name="exception">
						<skin:lightArea>
							<b><ao:message key="exception.strutsException.title" /></b>
							<ao:hr />
							<pre><ao:getStackTraces scope="request" name="exception" /></pre>
						</skin:lightArea><ao:br />
						<ao:br />
					</logic:present>
					<logic:present scope="request" name="org.apache.struts.action.EXCEPTION">
						<skin:lightArea>
							<b><ao:message key="exception.strutsException.title" /></b>
							<ao:hr />
							<pre><ao:getStackTraces scope="request" name="org.apache.struts.action.EXCEPTION" /></pre>
						</skin:lightArea><ao:br />
						<ao:br />
					</logic:present>
					<%-- Servlet Exception --%>
					<logic:notEmpty name="javax.servlet.jsp.jspPageContext" property="exception">
						<skin:lightArea>
							<b><ao:message key="exception.servletException.title" /></b>
							<ao:hr />
							<pre><ao:getStackTraces name="javax.servlet.jsp.jspPageContext" property="exception" /></pre>
						</skin:lightArea>
					</logic:notEmpty>
				</logic:equal>
			</skin:contentLine>
		</skin:content>
	</skin:skin>
</ao:bundle>
