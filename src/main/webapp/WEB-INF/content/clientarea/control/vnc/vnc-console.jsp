<%--
aoweb-struts - Template webapp for legacy Struts-based site framework with AOServ Platform control panels.
Copyright (C) 2009, 2016, 2019, 2020, 2021, 2022, 2026  AO Industries, Inc.
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
--%>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/taglibs.jspf" %>

<%@include file="../add-parents.jspf" %>
<%@include file="vnc-console.meta.jspf" %>
<skin:skin>
  <skin:content width="600">
    <ao:bundle basename="com.aoindustries.web.struts.clientarea.control.vnc.i18n.ApplicationResources">
      <skin:contentTitle><ao:message key="vncConsole.title" /></skin:contentTitle>
      <skin:contentHorizontalDivider />
      <skin:contentLine>
        <logic:present scope="request" name="permissionDenied">
          <%@include file="../../../permission-denied.jspf" %>
        </logic:present>
        <logic:notPresent scope="request" name="permissionDenied">
          <skin:lightArea>
            <logic:empty scope="request" name="vncVirtualServers">
              <ao:message key="vncConsole.noServers" />
            </logic:empty>
            <logic:notEmpty scope="request" name="vncVirtualServers">
              <ao:script>
                document.addEventListener('submit', async function(event) {
                  const form = event.target;

                  if (
                    !(form instanceof HTMLFormElement)
                    || !form.action.includes('/clientarea/control/vnc/')
                  ) {
                    return;
                  }

                  // Respect existing onsubmit handlers, including confirmation dialogs.
                  if (event.defaultPrevented) {
                    return;
                  }

                  event.preventDefault();

                  const submitButton = form.querySelector('[type="submit"]');
                  if (submitButton) {
                    submitButton.disabled = true;
                  }

                  try {
                    const response = await fetch(form.action, {
                      method: form.method,
                      headers: {
                        'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'
                      },
                      body: new URLSearchParams(new FormData(form))
                    });

                    if (response.status === 401) {
                      window.location.reload();
                      return;
                    }

                    if (!response.ok) {
                      throw new Error(
                        'Request failed: HTTP ' + response.status + ' ' + response.statusText
                      );
                    }

                    // Refresh to display the resulting virtual-server state.
                    window.location.reload();
                  } catch (error) {
                    console.error('Virtual server operation failed:', error);
                    window.alert('The operation failed: ' + error);

                    if (submitButton) {
                      submitButton.disabled = false;
                    }
                  }
                });
              </ao:script>
              <table>
                <tr>
                  <th><ao:message key="vncConsole.server.header" /></th>
                  <th><ao:message key="vncConsole.host.header" /></th>
                  <th><ao:message key="vncConsole.sslRequired.header" /></th>
                  <th><ao:message key="vncConsole.password.header" /></th>
                  <th><ao:message key="vncConsole.connectNow.header" /></th>
                  <th colspan="4"><ao:message key="vncConsole.instanceControls" /></th>
                </tr>
                <c:set var="currentAccount" value="${aoConn.currentAdministrator.username['package'].account}" />
                <logic:iterate scope="request" name="vncVirtualServers" id="vncVirtualServer">
                  <c:set var="accountHost" value="${currentAccount.getAccountHost(vncVirtualServer.host)}" />
                  <skin:lightDarkTableRow>
                    <td><bean:write name="vncVirtualServer" property="host.name" /></td>
                    <td>
                      <c:choose>
                        <c:when test="${siteSettings.brand.aowebStrutsVncBind.port.port < 5900}">
                          <c:out value="${siteSettings.brand.aowebStrutsHttpsUrl.host}" />::<c:out value="${siteSettings.brand.aowebStrutsVncBind.port.port}" />
                        </c:when>
                        <c:otherwise>
                          <c:out value="${siteSettings.brand.aowebStrutsHttpsUrl.host}" />:<c:out value="${siteSettings.brand.aowebStrutsVncBind.port.port - 5900}" />
                        </c:otherwise>
                      </c:choose>
                    </td>
                    <td><ao:message key="vncConsole.sslRequired.yes" /></td>
                    <c:choose>
                      <c:when test="${accountHost.canVncConsole() && vncVirtualServer.vncPassword != null && vncVirtualServer.vncPassword != '*'}">
                        <td><code><c:out value="${vncVirtualServer.vncPassword}" /></code></td>
                        <td style="white-space:nowrap">
                          <ao:a href="/clientarea/control/vnc/noVNC-1.3.0/vnc.html#autoconnect=true&path=${ao:encodeURIComponent(empty pageContext.request.contextPath ? '' : fn:substringAfter(pageContext.request.contextPath, '/').concat('/'))}${ao:encodeURIComponent('clientarea/control/vnc/vnc-console-websocket')}&password=${ao:encodeURIComponent(vncVirtualServer.vncPassword)}&shared=false&show_dot=true">
                            <ao:message key="vncConsole.connectNow.link" />
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
                      </c:when>
                      <c:otherwise>
                        <td colspan="2"><ao:message key="vncConsole.connectNow.notAllowed" /></td>
                      </c:otherwise>
                    </c:choose>
                    <c:choose>
                      <c:when test="${accountHost.canControlVirtualServer()}">
                        <td>
                          <ao:form method="post" action="/clientarea/control/vnc/reboot-virtual-server">
                            <ao:input type="hidden" name="virtual-server" value="${vncVirtualServer.host.pkey}" />
                            <ao:onsubmit>
                              return window.confirm(<ao:message key="vncConsole.rebootConfirm" arg0="${vncVirtualServer.host.name}" />);
                            </ao:onsubmit>
                            <ao:input type="submit" value="${ao:message('vncConsole.rebootSubmitButton')}" />
                          </ao:form>
                        </td>
                        <td>
                          <ao:form method="post" action="/clientarea/control/vnc/shutdown-virtual-server">
                            <ao:input type="hidden" name="virtual-server" value="${vncVirtualServer.host.pkey}" />
                            <ao:onsubmit>
                              return window.confirm(<ao:message key="vncConsole.shutdownConfirm" arg0="${vncVirtualServer.host.name}" />);
                            </ao:onsubmit>
                            <ao:input type="submit" value="${ao:message('vncConsole.shutdownSubmitButton')}" />
                          </ao:form>
                        </td>
                        <td>
                          <ao:form method="post" action="/clientarea/control/vnc/destroy-virtual-server">
                            <ao:input type="hidden" name="virtual-server" value="${vncVirtualServer.host.pkey}" />
                            <ao:onsubmit>
                              return window.confirm(<ao:message key="vncConsole.destroyConfirm" arg0="${vncVirtualServer.host.name}" />);
                            </ao:onsubmit>
                            <ao:input type="submit" value="${ao:message('vncConsole.destroySubmitButton')}" />
                          </ao:form>
                        </td>
                        <td>
                          <ao:form method="post" action="/clientarea/control/vnc/create-virtual-server">
                            <ao:input type="hidden" name="virtual-server" value="${vncVirtualServer.host.pkey}" />
                            <ao:onsubmit>
                              return window.confirm(<ao:message key="vncConsole.createConfirm" arg0="${vncVirtualServer.host.name}" />);
                            </ao:onsubmit>
                            <ao:input type="submit" value="${ao:message('vncConsole.createSubmitButton')}" />
                          </ao:form>
                        </td>
                      </c:when>
                      <c:otherwise>
                        <td colspan="4"><ao:message key="vncConsole.instanceControls.notAllowed" /></td>
                      </c:otherwise>
                    </c:choose>
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
