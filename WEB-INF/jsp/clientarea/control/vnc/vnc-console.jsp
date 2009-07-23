<%-- aoweb-struts: Do not edit --%>
<%--
  Copyright 2009 by AO Industries, Inc.,
  7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" buffer="256kb" autoFlush="true" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/taglibs.jsp" %>

<skin:setContentType />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html:html lang="true" xhtml="true">
    <fmt:bundle basename="com.aoindustries.website.clientarea.control.ApplicationResources">
        <skin:path>/clientarea/control/vnc/vnc-console.do</skin:path>
        <logic:equal name="siteSettings" property="brand.aowebStrutsNoindex" value="true"><skin:meta name="ROBOTS">NOINDEX</skin:meta></logic:equal>
        <skin:title><fmt:message key="vnc.vncConsole.title" /></skin:title>
        <skin:navImageAlt><fmt:message key="vnc.vncConsole.navImageAlt" /></skin:navImageAlt>
        <skin:keywords><fmt:message key="vnc.vncConsole.keywords" /></skin:keywords>
        <skin:description><fmt:message key="vnc.vncConsole.description" /></skin:description>
        <%@ include file="../add-parents.jsp" %>
        <%@ include file="../add-siblings.jsp" %>
        <skin:skin>
            <skin:content width="600">
                <skin:contentTitle><fmt:message key="vnc.vncConsole.title" /></skin:contentTitle>
                <skin:contentHorizontalDivider />
                <skin:contentLine>
                    <logic:present scope="request" name="permissionDenied">
                        <%@ include file="../../../permission-denied.jsp" %>
                    </logic:present>
                    <logic:notPresent scope="request" name="permissionDenied">
                        <skin:lightArea>
                            <logic:empty scope="request" name="vncVirtualServers">
                                <fmt:message key="vnc.vncConsole.noServers" />
                            </logic:empty>
                            <logic:notEmpty scope="request" name="vncVirtualServers">
                                <table>
                                    <tr>
                                        <th><fmt:message key="vnc.vncConsole.server.header" /></th>
                                        <th><fmt:message key="vnc.vncConsole.host.header" /></th>
                                        <th><fmt:message key="vnc.vncConsole.sslRequired.header" /></th>
                                        <th><fmt:message key="vnc.vncConsole.password.header" /></th>
                                        <th><fmt:message key="vnc.vncConsole.connectNow.header" /></th>
                                    </tr>
                                    <logic:iterate scope="request" name="vncVirtualServers" id="vncVirtualServer">
                                        <skin:lightDarkTableRow>
                                            <td><bean:write name="vncVirtualServer" property="server.name" /></td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${siteSettings.brand.aowebStrutsVncBind.port.port < 5900}">
                                                        <c:out value="${siteSettings.brand.aowebStrutsHttpsURL.host}" />::<c:out value="${siteSettings.brand.aowebStrutsVncBind.port.port}" />
                                                    </c:when>
                                                    <c:otherwise>
                                                        <c:out value="${siteSettings.brand.aowebStrutsHttpsURL.host}" />:<c:out value="${siteSettings.brand.aowebStrutsVncBind.port.port - 5900}" />
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td><fmt:message key="vnc.vncConsole.sslRequired.yes" /></td>
                                            <td><c:out value="${vncVirtualServer.vncPassword}" /></td>
                                            <td style="white-space:nowrap">
                                                <html:link action="/vnc/vnc-viewer.do" onclick="window.open(this.href, 'vnc'+(new Date()).getTime(), 'toolbar=0,location=0,directories=0,status=0,menubar=0,scrollbars=1,resizable=1,width=380,height=200,left='+((screen.availWidth-380)/2-64)+',top='+((screen.availHeight-200)/2-48)); return false;">
                                                    <html:param name="virtualServer">${vncVirtualServer.server.pkey}</html:param>
                                                    <fmt:message key="vnc.vncConsole.connectNow.link" />
                                                </html:link>
                                            </td>
                                        </skin:lightDarkTableRow>
                                    </logic:iterate>
                                </table>
                            </logic:notEmpty>
                        </skin:lightArea>
                    </logic:notPresent>
                </skin:contentLine>
            </skin:content>
        </skin:skin>
    </fmt:bundle>
</html:html>
