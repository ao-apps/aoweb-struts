<%-- aoweb-struts: Do not edit --%>
<%--
Copyright 2007-2009, 2015, 2016 by AO Industries, Inc.,
7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
All rights reserved.
--%>
<%@ page language="java" pageEncoding="UTF-8" isErrorPage="true" %>
<% request.setAttribute(com.aoindustries.website.Constants.HTTP_SERVLET_RESPONSE_STATUS, Integer.valueOf(HttpServletResponse.SC_NOT_FOUND)); %>
<% response.setStatus(HttpServletResponse.SC_NOT_FOUND); %>
<%@include file="/_taglibs.jsp" %>
<%
	// Set siteSettings request attribute if not yet done
	com.aoindustries.website.SiteSettings siteSettings = (com.aoindustries.website.SiteSettings)request.getAttribute(com.aoindustries.website.Constants.SITE_SETTINGS);
	if(siteSettings==null) {
		siteSettings = com.aoindustries.website.SiteSettings.getInstance(getServletContext());
		request.setAttribute(com.aoindustries.website.Constants.SITE_SETTINGS, siteSettings);
	}

	// Set locale request attribute if not yet done
	if(request.getAttribute(com.aoindustries.website.Constants.LOCALE)==null) {
		java.util.Locale locale = com.aoindustries.website.LocaleAction.getEffectiveLocale(siteSettings, request, response);
		request.setAttribute(com.aoindustries.website.Constants.LOCALE, locale);
	}

	// Set the skin request attribute if not yet done
	if(request.getAttribute(com.aoindustries.website.Constants.SKIN)==null) {
		com.aoindustries.website.Skin skin = com.aoindustries.website.SkinAction.getSkin(siteSettings, request, response);
		request.setAttribute(com.aoindustries.website.Constants.SKIN, skin);
	}
%>
<skin:setContentType />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html:html lang="true" xhtml="true">
	<fmt:bundle basename="com.aoindustries.website.ApplicationResources">
		<skin:path>/not-found.do</skin:path>
		<skin:title><fmt:message key="notFound.title" /></skin:title>
		<skin:navImageAlt><fmt:message key="notFound.navImageAlt" /></skin:navImageAlt>
		<skin:keywords><fmt:message key="notFound.keywords" /></skin:keywords>
		<skin:description><fmt:message key="notFound.description" /></skin:description>
		<aoweb:exists path="/add-parents.jsp"><jsp:include page="/add-parents.jsp" /></aoweb:exists>
		<skin:skin>
			<skin:content width="600">
				<skin:contentTitle><fmt:message key="notFound.title" /></skin:contentTitle>
				<skin:contentHorizontalDivider />
				<skin:contentLine>
					<fmt:message key="notFound.message" /><br />
					<br />
					<logic:equal scope="request" name="siteSettings" property="exceptionShowError" value="true">
						<%-- Error Data --%>
						<logic:present name="javax.servlet.jsp.jspPageContext" property="errorData">
							<skin:lightArea>
								<fmt:message key="exception.jspException.title" />
								<hr />
								<table style='border:1px' cellspacing="0" cellpadding="2">
									<tr>
										<th style='white-space:nowrap; text-align:left'><fmt:message key="exception.servletName.header" /></th>
										<td style="white-space:nowrap"><ao:write name="javax.servlet.jsp.jspPageContext" property="errorData.servletName" /></td>
									</tr>
									<tr>
										<th style='white-space:nowrap; text-align:left'><fmt:message key="exception.requestURI.header" /></th>
										<td style="white-space:nowrap"><ao:write name="javax.servlet.jsp.jspPageContext" property="errorData.requestURI" /></td>
									</tr>
									<tr>
										<th style='white-space:nowrap; text-align:left'><fmt:message key="exception.statusCode.header" /></th>
										<td style="white-space:nowrap"><ao:write name="javax.servlet.jsp.jspPageContext" property="errorData.statusCode" /></td>
									</tr>
									<tr>
										<th style='white-space:nowrap; text-align:left'><fmt:message key="exception.throwable.header" /></th>
										<td style="white-space:nowrap">
											<logic:notEmpty name="javax.servlet.jsp.jspPageContext" property="errorData.throwable">
												<pre><ao:text><ao:getStackTraces name="javax.servlet.jsp.jspPageContext" property="errorData.throwable" /></ao:text></pre>
											</logic:notEmpty>
											<logic:empty name="javax.servlet.jsp.jspPageContext" property="errorData.throwable">
												&#160;
											</logic:empty>
										</td>
									</tr>
								</table>
							</skin:lightArea><br />
							<br />
						</logic:present>
						<%-- Servlet Exception --%>
						<logic:notEmpty name="javax.servlet.jsp.jspPageContext" property="exception">
							<skin:lightArea>
								<fmt:message key="exception.servletException.title" />
								<hr />
								<pre><ao:text><ao:getStackTraces name="javax.servlet.jsp.jspPageContext" property="exception" /></ao:text></pre>
							</skin:lightArea>
						</logic:notEmpty>
					</logic:equal>
				</skin:contentLine>
			</skin:content>
		</skin:skin>
	</fmt:bundle>
</html:html>
