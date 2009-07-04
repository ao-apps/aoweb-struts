<%-- aoweb-struts: Do not edit --%>
<%--
  Copyright 2007-2009 by AO Industries, Inc.,
  7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" buffer="256kb" autoFlush="true" pageEncoding="UTF-8" isErrorPage="true" %>
<% request.setAttribute(com.aoindustries.website.Constants.HTTP_SERVLET_RESPONSE_STATUS, Integer.valueOf(HttpServletResponse.SC_NOT_FOUND)); %>
<% response.setStatus(HttpServletResponse.SC_NOT_FOUND); %>
<%@ include file="/WEB-INF/jsp/taglibs.jsp" %>
<%
    // Set siteSettings request attribute if not yet done
    com.aoindustries.website.SiteSettings siteSettings = (com.aoindustries.website.SiteSettings)request.getAttribute(com.aoindustries.website.Constants.SITE_SETTINGS);
    if(siteSettings==null) {
        siteSettings = com.aoindustries.website.SiteSettings.getInstance(getServletContext());
        request.setAttribute(com.aoindustries.website.Constants.SITE_SETTINGS, siteSettings);
    }

    // Set locale request attribute if not yet done
    if(request.getAttribute(com.aoindustries.website.Constants.LOCALE)==null) {
        java.util.Locale locale = com.aoindustries.website.LocaleAction.getEffectiveLocale(siteSettings, request);
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
    <skin:path>/not-found.do</skin:path>
    <skin:title><bean:message bundle="/ApplicationResources" key="notFound.title" /></skin:title>
    <skin:navImageAlt><bean:message bundle="/ApplicationResources" key="notFound.navImageAlt" /></skin:navImageAlt>
    <skin:keywords><bean:message bundle="/ApplicationResources" key="notFound.keywords" /></skin:keywords>
    <skin:description><bean:message bundle="/ApplicationResources" key="notFound.description" /></skin:description>
    <aoweb:exists path="/WEB-INF/jsp/add-parents.jsp">
        <jsp:include page="/WEB-INF/jsp/add-parents.jsp" />
    </aoweb:exists>
    <aoweb:exists path="/WEB-INF/jsp/add-siblings.jsp">
        <jsp:include page="/WEB-INF/jsp/add-siblings.jsp" />
    </aoweb:exists>
    <skin:skin>
        <skin:content width="600">
            <skin:contentTitle><bean:message bundle="/ApplicationResources" key="notFound.title" /></skin:contentTitle>
            <skin:contentHorizontalDivider />
            <skin:contentLine>
                <bean:message bundle="/ApplicationResources" key="notFound.message" /><br />
                <br />
                <logic:equal scope="request" name="siteSettings" property="exceptionShowError" value="true">
                    <%-- Error Data --%>
                    <% {
                        javax.servlet.jsp.ErrorData errorData;
                        try {
                            errorData = pageContext==null ? null : pageContext.getErrorData();
                            if(errorData!=null) pageContext.setAttribute("errorData", errorData);
                        } catch(NullPointerException err) {
                            errorData = null;
                        }
                    } %>
                    <logic:present scope="page" name="errorData">
                        <skin:lightArea>
                            <bean:message bundle="/ApplicationResources" key="notFound.jspException.title" />
                            <hr />
                            <table style='border:1px' cellspacing="0" cellpadding="2">
                                <tr>
                                    <th style='white-space:nowrap'><bean:message bundle="/ApplicationResources" key="notFound.servletName.header" /></th>
                                    <td style="white-space:nowrap"><bean:write scope="page" name="errorData" property="servletName" /></td>
                                </tr>
                                <tr>
                                    <th style='white-space:nowrap'><bean:message bundle="/ApplicationResources" key="notFound.requestURI.header" /></th>
                                    <td style="white-space:nowrap"><bean:write scope="page" name="errorData" property="requestURI" /></td>
                                </tr>
                                <tr>
                                    <th style='white-space:nowrap'><bean:message bundle="/ApplicationResources" key="notFound.statusCode.header" /></th>
                                    <td style="white-space:nowrap"><bean:write scope="page" name="errorData" property="statusCode" /></td>
                                </tr>
                                <tr>
                                    <th style='white-space:nowrap'><bean:message bundle="/ApplicationResources" key="notFound.throwable.header" /></th>
                                    <td style="white-space:nowrap">
                                        <logic:notEmpty scope="page" name="errorData" property="throwable">
                                            <ao:pre><ao:getStackTraces scope="page" name="errorData" property="throwable" /></ao:pre>
                                        </logic:notEmpty>
                                        <logic:empty scope="page" name="errorData" property="throwable">
                                            &#160;
                                        </logic:empty>
                                    </td>
                                </tr>
                            </table>
                        </skin:lightArea><br />
                        <br />
                    </logic:present>
                    <%-- Servlet Exception --%>
                    <%
                        if(pageContext!=null) {
                            Exception servletException = pageContext.getException();
                            if(servletException!=null) pageContext.setAttribute("servletException", servletException);
                        }
                    %>
                    <logic:present scope="page" name="servletException">
                        <skin:lightArea>
                            <bean:message bundle="/ApplicationResources" key="notFound.servletException.title" />
                            <hr />
                            <ao:pre><ao:getStackTraces scope="page" name="servletException" /></ao:pre>
                        </skin:lightArea>
                    </logic:present>
                </logic:equal>
            </skin:contentLine>
        </skin:content>
    </skin:skin>
</html:html>
