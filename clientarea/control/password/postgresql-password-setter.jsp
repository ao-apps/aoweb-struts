<%-- aoweb-struts: Do not edit --%>
<%--
  Copyright 2000-2010 by AO Industries, Inc.,
  7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" buffer="256kb" autoFlush="true" pageEncoding="UTF-8" %>
<%@include file="/_taglibs.jsp" %>

<skin:setContentType />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html:html lang="true" xhtml="true">
    <%@include file="add-parents.jsp" %>
    <%@include file="postgresql-password-setter.meta.jsp" %>
    <skin:skin>
        <skin:content width="600">
            <fmt:bundle basename="com.aoindustries.website.clientarea.control.ApplicationResources">
                <skin:contentTitle><fmt:message key="password.postgreSQLPasswordSetter.title" /></skin:contentTitle>
                <skin:contentHorizontalDivider />
                <skin:contentLine>
                    <logic:present scope="request" name="permissionDenied">
                        <%@include file="../../../_permission-denied.jsp" %>
                    </logic:present>
                    <logic:notPresent scope="request" name="permissionDenied">
                        <logic:empty scope="request" name="postgreSQLPasswordSetterForm" property="businesses">
                            <b><fmt:message key="password.postgreSQLPasswordSetter.noAccounts" /></b>
                        </logic:empty>
                        <logic:notEmpty scope="request" name="postgreSQLPasswordSetterForm" property="businesses">
                            <html:form action="/password/postgresql-password-setter-completed">
                                <skin:lightArea>
                                    <table cellspacing='0' cellpadding='2'>
                                        <tr>
                                            <bean:size scope="request" name="aoConn" property="businesses.map" id="businessesSize" />
                                            <logic:greaterThan name="businessesSize" value="1">
                                                <th><fmt:message key="password.postgreSQLPasswordSetter.header.business" /></th>
                                            </logic:greaterThan>
                                            <th><fmt:message key="password.postgreSQLPasswordSetter.header.username" /></th>
                                            <bean:size scope="request" name="aoConn" property="postgresServers.map" id="postgresServersSize" />
                                            <logic:greaterThan name="postgresServersSize" value="1">
                                                <th><fmt:message key="password.postgreSQLPasswordSetter.header.postgreSQLServer" /></th>
                                            </logic:greaterThan>
                                            <bean:size scope="request" name="aoConn" property="aoServers.map" id="aoServersSize" />
                                            <logic:greaterThan name="aoServersSize" value="1">
                                                <th><fmt:message key="password.postgreSQLPasswordSetter.header.aoServer" /></th>
                                            </logic:greaterThan>
                                            <th colspan='2'><fmt:message key="password.postgreSQLPasswordSetter.header.newPassword" /></th>
                                            <th><fmt:message key="password.postgreSQLPasswordSetter.header.confirmPassword" /></th>
                                            <th>&#160;</th>
                                        </tr>
                                        <logic:iterate scope="request" name="postgreSQLPasswordSetterForm" property="businesses" id="accounting" indexId="index">
                                            <tr>
                                                <logic:greaterThan name="businessesSize" value="1">
                                                    <td><ao:write name="accounting" /></td>
                                                </logic:greaterThan>
                                                <td>
                                                    <html:hidden property='<%= "businesses[" + index + "]" %>' />
                                                    <code><html:hidden property='<%= "usernames[" + index + "]" %>' write="true" /></code>
                                                    <html:hidden property='<%= "postgreSQLServers[" + index + "]" %>' />
                                                    <html:hidden property='<%= "aoServers[" + index + "]" %>' />
                                                </td>
                                                <logic:greaterThan name="postgresServersSize" value="1">
                                                    <td><code><ao:write name="postgreSQLPasswordSetterForm" property='<%= "postgreSQLServers[" + index + "]" %>' /></code></td>
                                                </logic:greaterThan>
                                                <logic:greaterThan name="aoServersSize" value="1">
                                                    <td><code><ao:write name="postgreSQLPasswordSetterForm" property='<%= "aoServers[" + index + "]" %>' /></code></td>
                                                </logic:greaterThan>
                                                <td><html:password size="20" property='<%= "newPasswords[" + index + "]" %>' /></td>
                                                <td style="white-space:nowrap">
                                                    <html:errors bundle="/clientarea/control/ApplicationResources" property='<%= "newPasswords[" + index + "].newPasswords" %>' />
                                                    <html:messages id="message" message="true" bundle="/clientarea/control/ApplicationResources" property='<%= "newPasswords[" + index + "].newPasswords" %>'>
                                                        <ao:write name="message" /><br />
                                                    </html:messages>
                                                </td>
                                                <td><html:password size="20" property='<%= "confirmPasswords[" + index + "]" %>' /></td>
                                                <td style="white-space:nowrap">
                                                    <html:errors bundle="/clientarea/control/ApplicationResources" property='<%= "confirmPasswords[" + index + "].confirmPasswords" %>' />
                                                    <html:messages id="message" message="true" bundle="/clientarea/control/ApplicationResources" property='<%= "confirmPasswords[" + index + "].confirmPasswords" %>'>
                                                        <ao:write name="message" /><br />
                                                    </html:messages>
                                                </td>
                                            </tr>
                                        </logic:iterate>
                                        <tr><td colspan='8' align='center'><ao:input type="submit"><fmt:message key="password.postgreSQLPasswordSetter.field.submit.label" /></ao:input></td></tr>
                                    </table>
                                </skin:lightArea>
                            </html:form>
                        </logic:notEmpty>
                    </logic:notPresent>
                </skin:contentLine>
            </fmt:bundle>
        </skin:content>
    </skin:skin>
</html:html>
