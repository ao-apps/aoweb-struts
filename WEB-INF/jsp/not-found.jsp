<%--
  Copyright 2007-2009 by AO Industries, Inc.,
  7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" %>
<%@ page isErrorPage="true" %>
<% response.setStatus(HttpServletResponse.SC_NOT_FOUND); %>
<%@ page buffer="256kb" %>
<%@ page autoFlush="true" %>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/aoweb-struts-skin.tld" prefix="skin" %>
<%
    // Set locale request attribute if not yet done
    if(request.getAttribute(com.aoindustries.website.Constants.LOCALE)==null) {
        java.util.Locale locale = com.aoindustries.website.LocaleAction.getEffectiveLocale(request);
        request.setAttribute(com.aoindustries.website.Constants.LOCALE, locale);
    }

    // Set the skin request attribute if not yet done
    if(request.getAttribute(com.aoindustries.website.Constants.SKIN)==null) {
        com.aoindustries.website.Skin skin = com.aoindustries.website.SkinAction.getSkin(getServletContext(), request, response);
        request.setAttribute(com.aoindustries.website.Constants.SKIN, skin);
    }
%>
<skin:setContentType/>
<html:html lang="true">
    <skin:path>/not-found.do</skin:path>
    <skin:title><bean:message bundle="/ApplicationResources" key="notFound.title"/></skin:title>
    <skin:navImageAlt><bean:message bundle="/ApplicationResources" key="notFound.navImageAlt"/></skin:navImageAlt>
    <skin:keywords><bean:message bundle="/ApplicationResources" key="notFound.keywords"/></skin:keywords>
    <skin:description><bean:message bundle="/ApplicationResources" key="notFound.description"/></skin:description>
    <%@ include file="add-parents.jsp" %>
    <%@ include file="add-siblings.jsp" %>
    <skin:skin>
        <skin:content width="600">
            <skin:contentTitle><bean:message bundle="/ApplicationResources" key="notFound.title"/></skin:contentTitle>
            <skin:contentHorizontalDivider/>
            <skin:contentLine>
                <bean:message bundle="/ApplicationResources" key="notFound.message"/><br>
                <br>
                <% if("true".equals(getServletContext().getInitParameter("exception.showError"))) { %>
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
                            <bean:message bundle="/ApplicationResources" key="notFound.jspException.title"/>
                            <hr>
                            <table border="1" cellspacing="0" cellpadding="2">
                                <tr>
                                    <th nowrap><bean:message bundle="/ApplicationResources" key="notFound.servletName.header"/></th>
                                    <td nowrap><%= errorData.getServletName() %></td>
                                </tr>
                                <tr>
                                    <th nowrap><bean:message bundle="/ApplicationResources" key="notFound.requestURI.header"/></th>
                                    <td nowrap><%= errorData.getRequestURI() %></td>
                                </tr>
                                <tr>
                                    <th nowrap><bean:message bundle="/ApplicationResources" key="notFound.statusCode.header"/></th>
                                    <td nowrap><%= errorData.getStatusCode() %></td>
                                </tr>
                                <tr>
                                    <th nowrap><bean:message bundle="/ApplicationResources" key="notFound.throwable.header"/></th>
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
                    <%-- Servlet Exception --%>
                    <% Exception myException = pageContext==null ? null : pageContext.getException(); %>
                    <% if(myException!=null) { %>
                        <skin:lightArea>
                            <bean:message bundle="/ApplicationResources" key="notFound.servletException.title"/>
                            <hr>
<pre><%= org.apache.commons.lang.StringEscapeUtils.escapeHtml(com.aoindustries.util.ErrorPrinter.getStackTraces(myException)) %></pre>
                        </skin:lightArea>
                    <% } %>
                <% } %>
            </skin:contentLine>
        </skin:content>
    </skin:skin>
</html:html>
