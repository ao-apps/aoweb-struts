<%--
aoweb-struts - Template webapp for legacy Struts-based site framework with AOServ Platform control panels.
Copyright (C) 2000-2009, 2016, 2018, 2019, 2020, 2021, 2022  AO Industries, Inc.
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

<%@include file="add-parents.jspf" %>
<%@include file="postgresql-password-setter.meta.jspf" %>
<skin:skin formtype="struts1">
  <skin:content width="600">
    <ao:bundle basename="com.aoindustries.web.struts.clientarea.control.password.i18n.ApplicationResources">
      <skin:contentTitle><ao:message key="postgreSQLPasswordSetter.title" /></skin:contentTitle>
      <skin:contentHorizontalDivider />
      <skin:contentLine>
        <logic:present scope="request" name="permissionDenied">
          <%@include file="../../../permission-denied.jspf" %>
        </logic:present>
        <logic:notPresent scope="request" name="permissionDenied">
          <logic:empty scope="request" name="postgresqlPasswordSetterForm" property="packages">
            <b><ao:message key="postgreSQLPasswordSetter.noAccounts" /></b>
          </logic:empty>
          <logic:notEmpty scope="request" name="postgresqlPasswordSetterForm" property="packages">
            <html:form action="/password/postgresql-password-setter-completed">
              <skin:lightArea>
                <table class="ao-no-border">
                  <thead>
                    <tr>
                      <bean:size scope="request" name="aoConn" property="billing.Package.map" id="packagesSize" />
                      <logic:greaterThan name="packagesSize" value="1">
                        <th><ao:message key="postgreSQLPasswordSetter.header.package" /></th>
                      </logic:greaterThan>
                      <th><ao:message key="postgreSQLPasswordSetter.header.username" /></th>
                      <bean:size scope="request" name="aoConn" property="postgresql.Server.map" id="postgresServersSize" />
                      <logic:greaterThan name="postgresServersSize" value="1">
                        <th><ao:message key="postgreSQLPasswordSetter.header.postgreSQLServer" /></th>
                      </logic:greaterThan>
                      <bean:size scope="request" name="aoConn" property="linux.Server.map" id="serversSize" />
                      <logic:greaterThan name="serversSize" value="1">
                        <th><ao:message key="postgreSQLPasswordSetter.header.server" /></th>
                      </logic:greaterThan>
                      <th colspan='2'><ao:message key="postgreSQLPasswordSetter.header.newPassword" /></th>
                      <th><ao:message key="postgreSQLPasswordSetter.header.confirmPassword" /></th>
                      <th>&#160;</th>
                    </tr>
                  </thead>
                  <tbody>
                    <logic:iterate scope="request" name="postgresqlPasswordSetterForm" property="packages" id="pack" indexId="index">
                      <tr>
                        <logic:greaterThan name="packagesSize" value="1">
                          <td><ao:write name="pack" /></td>
                        </logic:greaterThan>
                        <td>
                          <html:hidden property='<%= "packages[" + index + "]" %>' />
                          <code><html:hidden property='<%= "usernames[" + index + "]" %>' write="true" /></code>
                          <html:hidden property='<%= "postgresqlServers[" + index + "]" %>' />
                          <html:hidden property='<%= "servers[" + index + "]" %>' />
                        </td>
                        <logic:greaterThan name="postgresServersSize" value="1">
                          <td><code><ao:write name="postgresqlPasswordSetterForm" property='<%= "postgresqlServers[" + index + "]" %>' /></code></td>
                        </logic:greaterThan>
                        <logic:greaterThan name="serversSize" value="1">
                          <td><code><ao:write name="postgresqlPasswordSetterForm" property='<%= "servers[" + index + "]" %>' /></code></td>
                        </logic:greaterThan>
                        <td><html:password size="20" property='<%= "newPasswords[" + index + "]" %>' /></td>
                        <td style="white-space:nowrap">
                          <html:errors bundle="/clientarea/control/password/ApplicationResources" property='<%= "newPasswords[" + index + "].newPasswords" %>' />
                          <html:messages id="message" message="true" bundle="/clientarea/control/password/ApplicationResources" property='<%= "newPasswords[" + index + "].newPasswords" %>'>
                            <ao:write name="message" /><ao:br />
                          </html:messages>
                        </td>
                        <td><html:password size="20" property='<%= "confirmPasswords[" + index + "]" %>' /></td>
                        <td style="white-space:nowrap">
                          <html:errors bundle="/clientarea/control/password/ApplicationResources" property='<%= "confirmPasswords[" + index + "].confirmPasswords" %>' />
                          <html:messages id="message" message="true" bundle="/clientarea/control/password/ApplicationResources" property='<%= "confirmPasswords[" + index + "].confirmPasswords" %>'>
                            <ao:write name="message" /><ao:br />
                          </html:messages>
                        </td>
                      </tr>
                    </logic:iterate>
                  </tbody>
                  <tfoot>
                    <tr><td colspan="8" style="text-align:center"><ao:input type="submit" value="${ao:message('postgreSQLPasswordSetter.field.submit.label')}" /></td></tr>
                  </tfoot>
                </table>
              </skin:lightArea>
            </html:form>
          </logic:notEmpty>
        </logic:notPresent>
      </skin:contentLine>
    </ao:bundle>
  </skin:content>
</skin:skin>
