<%-- aoweb-struts: Do not edit --%>
<%--
  Copyright 2000-2009 by AO Industries, Inc.,
  7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" buffer="256kb" autoFlush="true" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/taglibs.jsp" %>

<skin:setContentType />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html:html lang="true" xhtml="true">
    <fmt:bundle basename="com.aoindustries.website.clientarea.control.ApplicationResources">
        <skin:path>/clientarea/control/password/postgresql-password-setter.do</skin:path>
        <logic:equal name="siteSettings" property="brand.aowebStrutsNoindex" value="true"><skin:meta name="ROBOTS">NOINDEX</skin:meta></logic:equal>
        <skin:title><fmt:message key="password.postgreSQLPasswordSetter.title" /></skin:title>
        <skin:navImageAlt><fmt:message key="password.postgreSQLPasswordSetter.navImageAlt" /></skin:navImageAlt>
        <skin:keywords><fmt:message key="password.postgreSQLPasswordSetter.keywords" /></skin:keywords>
        <skin:description><fmt:message key="password.postgreSQLPasswordSetter.description" /></skin:description>
        <%@ include file="add-parents.jsp" %>
        <%@ include file="add-siblings.jsp" %>
        <skin:skin>
            <skin:content width="600">
                <skin:contentTitle><fmt:message key="password.postgreSQLPasswordSetter.title" /></skin:contentTitle>
                <skin:contentHorizontalDivider />
                <skin:contentLine>
                    <logic:present scope="request" name="permissionDenied">
                        <%@ include file="../../../permission-denied.jsp" %>
                    </logic:present>
                    <logic:notPresent scope="request" name="permissionDenied">
                        <logic:empty scope="request" name="postgreSQLPasswordSetterForm" property="packages">
                            <b><fmt:message key="password.postgreSQLPasswordSetter.noAccounts" /></b>
                        </logic:empty>
                        <logic:notEmpty scope="request" name="postgreSQLPasswordSetterForm" property="packages">
                            <html:form action="/password/postgresql-password-setter-completed">
                                <skin:lightArea>
                                    <table cellspacing='0' cellpadding='2'>
                                        <tr>
                                            <bean:size scope="request" name="aoConn" property="packages.map" id="packagesSize" />
                                            <logic:greaterThan name="packagesSize" value="1">
                                                <th><fmt:message key="password.postgreSQLPasswordSetter.header.package" /></th>
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
                                        <logic:iterate scope="request" name="postgreSQLPasswordSetterForm" property="packages" id="pack" indexId="index">
                                            <tr>
                                                <logic:greaterThan name="packagesSize" value="1">
                                                    <td><ao:write name="pack" /></td>
                                                </logic:greaterThan>
                                                <td>
                                                    <html:hidden property='<%= "packages[" + index + "]" %>' />
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
            </skin:content>
        </skin:skin>
    </fmt:bundle>
</html:html>
