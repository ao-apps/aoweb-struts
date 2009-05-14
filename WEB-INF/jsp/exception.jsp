<%--
  Copyright 2007-2009 by AO Industries, Inc.,
  7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" %>
<%@ page isErrorPage="true" %>
<% response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); %>
<%@ page buffer="256kb" %>
<%@ page autoFlush="true" %>
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
<skin:setContentType/>
<html:html lang="true">
    <skin:path>/exception.do</skin:path>
    <skin:title><bean:message bundle="/ApplicationResources" key="exception.title"/></skin:title>
    <skin:navImageAlt><bean:message bundle="/ApplicationResources" key="exception.navImageAlt"/></skin:navImageAlt>
    <skin:keywords><bean:message bundle="/ApplicationResources" key="exception.keywords"/></skin:keywords>
    <skin:description><bean:message bundle="/ApplicationResources" key="exception.description"/></skin:description>
    <%@ include file="add-parents.jsp" %>
    <%@ include file="add-siblings.jsp" %>
    <skin:skin>
        <skin:content width="600">
            <skin:contentTitle><bean:message bundle="/ApplicationResources" key="exception.title"/></skin:contentTitle>
            <skin:contentHorizontalDivider/>
            <skin:contentLine>
                <bean:message bundle="/ApplicationResources" key="exception.message"/><br>
                <br>
                <logic:equal scope="request" name="siteSettings" property="exceptionShowError" value="true">
                    <%-- Error Data --%>
                    <%
                        javax.servlet.jsp.ErrorData errorData;
                        try {
                            errorData = pageContext==null ? null : pageContext.getErrorData();
                        } catch(NullPointerException err) {
                            errorData = null;
                        }
                    %>
                    <% if(errorData!=null) {%>
                        <skin:lightArea>
                            <bean:message bundle="/ApplicationResources" key="exception.jspException.title"/>
                            <hr>
                            <table border="1" cellspacing="0" cellpadding="2">
                                <tr>
                                    <th nowrap><bean:message bundle="/ApplicationResources" key="exception.servletName.header"/></th>
                                    <td nowrap><%= errorData.getServletName() %></td>
                                </tr>
                                <tr>
                                    <th nowrap><bean:message bundle="/ApplicationResources" key="exception.requestURI.header"/></th>
                                    <td nowrap><%= errorData.getRequestURI() %></td>
                                </tr>
                                <tr>
                                    <th nowrap><bean:message bundle="/ApplicationResources" key="exception.statusCode.header"/></th>
                                    <td nowrap><%= errorData.getStatusCode() %></td>
                                </tr>
                                <tr>
                                    <th nowrap><bean:message bundle="/ApplicationResources" key="exception.throwable.header"/></th>
                                    <td nowrap>
                                        <% Throwable throwable = errorData.getThrowable(); %>
                                        <% if(throwable!=null) { %>
<pre><%= org.apache.commons.lang.StringEscapeUtils.escapeHtml(com.aoindustries.util.ErrorPrinter.getStackTraces(throwable)) %></pre>
                                        <% } else { %>
                                            &nbsp;
                                        <% } %>
                                    </td>
                                </tr>
                            </table>
                        </skin:lightArea><br>
                        <br>
                    <% } %>
                    <%-- Struts Exception --%>
                    <logic:present scope="request" name="exception">
                        <skin:lightArea>
                            <bean:message bundle="/ApplicationResources" key="exception.strutsException.title"/>
                            <hr>
                            <bean:define scope="request" name="exception" id="strutsException" type="java.lang.Exception"/>
<pre><%= org.apache.commons.lang.StringEscapeUtils.escapeHtml(com.aoindustries.util.ErrorPrinter.getStackTraces(strutsException)) %></pre>
                        </skin:lightArea><br>
                        <br>
                    </logic:present>
                    <%-- Servlet Exception --%>
                    <% Exception myException = pageContext==null ? null : pageContext.getException(); %>
                    <% if(myException!=null) { %>
                        <skin:lightArea>
                            <bean:message bundle="/ApplicationResources" key="exception.servletException.title"/>
                            <hr>
<pre><%= org.apache.commons.lang.StringEscapeUtils.escapeHtml(com.aoindustries.util.ErrorPrinter.getStackTraces(myException)) %></pre>
                        </skin:lightArea>
                    <% } %>
                </logic:equal>
            </skin:contentLine>
        </skin:content>
    </skin:skin>
</html:html>
