<%--
aoweb-struts - Template webapp for legacy Struts-based site framework with AOServ Platform control panels.
Copyright (C) 2009, 2016, 2019, 2020, 2021  AO Industries, Inc.
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

<%@include file="../add-parents.inc.jsp" %>
<%@include file="vnc-console.meta.inc.jsp" %>
<skin:skin>
	<skin:content width="600">
		<ao:bundle basename="com.aoindustries.web.struts.clientarea.control.i18n.ApplicationResources">
			<skin:contentTitle><ao:message key="vnc.vncConsole.title" /></skin:contentTitle>
			<skin:contentHorizontalDivider />
			<skin:contentLine>
				<logic:present scope="request" name="permissionDenied">
					<%@include file="../../../_permission-denied.jspf" %>
				</logic:present>
				<logic:notPresent scope="request" name="permissionDenied">
					<skin:lightArea>
						<logic:empty scope="request" name="vncVirtualServers">
							<ao:message key="vnc.vncConsole.noServers" />
						</logic:empty>
						<logic:notEmpty scope="request" name="vncVirtualServers">
							<table>
								<tr>
									<th><ao:message key="vnc.vncConsole.server.header" /></th>
									<th><ao:message key="vnc.vncConsole.host.header" /></th>
									<th><ao:message key="vnc.vncConsole.sslRequired.header" /></th>
									<th><ao:message key="vnc.vncConsole.password.header" /></th>
									<th><ao:message key="vnc.vncConsole.connectNow.header" /></th>
								</tr>
								<logic:iterate scope="request" name="vncVirtualServers" id="vncVirtualServer">
									<skin:lightDarkTableRow>
										<td><bean:write name="vncVirtualServer" property="host.name" /></td>
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
										<td><ao:message key="vnc.vncConsole.sslRequired.yes" /></td>
										<td><code><c:out value="${vncVirtualServer.vncPassword}" /></code></td>
										<td style="white-space:nowrap">
											<ao:a href="/clientarea/control/vnc/noVNC-1.2.0/vnc.html#autoconnect=true&path=${ao:encodeURIComponent(empty pageContext.request.contextPath ? '' : fn:substringAfter(pageContext.request.contextPath, '/').concat('/'))}${ao:encodeURIComponent('clientarea/control/vnc/vnc-console-websocket')}&password=${ao:encodeURIComponent(vncVirtualServer.vncPassword)}&shared=false&show_dot=true">
												<ao:message key="vnc.vncConsole.connectNow.link" />
												<%--
												<ao:onclick>
													window.open(
														this.href,
														"vnc" + (new Date()).getTime(),
														"toolbar=0,location=0,directories=0,status=0,menubar=0,scrollbars=1,resizable=1,width=380,height=200,left="
															+ ((screen.availWidth - 380) / 2 - 64)
															+ ",top="
															+ ((screen.availHeight - 200) / 2 - 48)
													);
													return false;
												</ao:onclick>
												--%>
											</ao:a>
										</td>
									</skin:lightDarkTableRow>
								</logic:iterate>
							</table>
						</logic:notEmpty>
					</skin:lightArea>
				</logic:notPresent>
			</skin:contentLine>
		</ao:bundle>
	</skin:content>
</skin:skin>
