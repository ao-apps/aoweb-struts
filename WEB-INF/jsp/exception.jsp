<%--
  Copyright 2007 by AO Industries, Inc.,
  816 Azalea Rd, Mobile, Alabama, 36693, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" %>
<%@ page isErrorPage="true" %>
<% response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); %>
<%@ page buffer="256kb" %>
<%@ page autoFlush="false" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld"       prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld"       prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld"      prefix="logic" %>
<%@ taglib uri="/WEB-INF/aoweb-struts-skin.tld" prefix="skin" %>
<%
    // Set locale request attribute if not yet done
    if(request.getAttribute(com.aoindustries.website.Constants.LOCALE)==null) {
        java.util.Locale locale = com.aoindustries.website.LocaleAction.getEffectiveLocale(request, response);
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
                            <bean:message bundle="/ApplicationResources" key="exception.jspException.title"/>
                            <hr>
                            <table border="1" cellspacing="0" cellpadding="2">
                                <tr>
                                    <th><bean:message bundle="/ApplicationResources" key="exception.servletName.header"/></th>
                                    <td><%= errorData.getServletName() %></td>
                                </tr>
                                <tr>
                                    <th><bean:message bundle="/ApplicationResources" key="exception.requestURI.header"/></th>
                                    <td><%= errorData.getRequestURI() %></td>
                                </tr>
                                <tr>
                                    <th><bean:message bundle="/ApplicationResources" key="exception.statusCode.header"/></th>
                                    <td><%= errorData.getStatusCode() %></td>
                                </tr>
                                <tr>
                                    <th><bean:message bundle="/ApplicationResources" key="exception.throwable.header"/></th>
                                    <td>
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
                <% } %>
            </skin:contentLine>
        </skin:content>
    </skin:skin>
</html:html>
