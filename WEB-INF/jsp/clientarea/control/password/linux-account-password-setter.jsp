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
    <skin:path>/clientarea/control/password/linux-account-password-setter.do</skin:path>
    <logic:equal name="siteSettings" property="brand.aowebStrutsNoindex" value="true"><skin:meta name="ROBOTS">NOINDEX</skin:meta></logic:equal>
    <skin:title><bean:message bundle="/clientarea/control/ApplicationResources" key="password.linuxAccountPasswordSetter.title" /></skin:title>
    <skin:navImageAlt><bean:message bundle="/clientarea/control/ApplicationResources" key="password.linuxAccountPasswordSetter.navImageAlt" /></skin:navImageAlt>
    <skin:keywords><bean:message bundle="/clientarea/control/ApplicationResources" key="password.linuxAccountPasswordSetter.keywords" /></skin:keywords>
    <skin:description><bean:message bundle="/clientarea/control/ApplicationResources" key="password.linuxAccountPasswordSetter.description" /></skin:description>
    <%@ include file="add-parents.jsp" %>
    <%@ include file="add-siblings.jsp" %>
    <skin:skin>
        <skin:content width="600">
            <skin:contentTitle><bean:message bundle="/clientarea/control/ApplicationResources" key="password.linuxAccountPasswordSetter.title" /></skin:contentTitle>
            <skin:contentHorizontalDivider />
            <skin:contentLine>
                <logic:present scope="request" name="permissionDenied">
                    <%@ include file="../../../permission-denied.jsp" %>
                </logic:present>
                <logic:notPresent scope="request" name="permissionDenied">
                    <logic:empty scope="request" name="linuxAccountPasswordSetterForm" property="packages">
                        <b><bean:message bundle="/clientarea/control/ApplicationResources" key="password.linuxAccountPasswordSetter.noAccounts" /></b>
                    </logic:empty>
                    <logic:notEmpty scope="request" name="linuxAccountPasswordSetterForm" property="packages">
                        <html:form action="/password/linux-account-password-setter-completed">
                            <skin:lightArea>
                                <table cellspacing='0' cellpadding='2'>
                                    <tr>
                                        <bean:size scope="request" name="aoConn" property="packages.map" id="packagesSize" />
                                        <logic:greaterThan name="packagesSize" value="1">
                                            <th><bean:message bundle="/clientarea/control/ApplicationResources" key="password.linuxAccountPasswordSetter.header.package" /></th>
                                        </logic:greaterThan>
                                        <th><bean:message bundle="/clientarea/control/ApplicationResources" key="password.linuxAccountPasswordSetter.header.username" /></th>
                                        <bean:size scope="request" name="aoConn" property="aoServers.map" id="aoServersSize" />
                                        <logic:greaterThan name="aoServersSize" value="1">
                                            <th><bean:message bundle="/clientarea/control/ApplicationResources" key="password.linuxAccountPasswordSetter.header.aoServer" /></th>
                                        </logic:greaterThan>
                                        <th colspan='2'><bean:message bundle="/clientarea/control/ApplicationResources" key="password.linuxAccountPasswordSetter.header.newPassword" /></th>
                                        <th><bean:message bundle="/clientarea/control/ApplicationResources" key="password.linuxAccountPasswordSetter.header.confirmPassword" /></th>
                                        <th>&#160;</th>
                                    </tr>
                                    <logic:iterate scope="request" name="linuxAccountPasswordSetterForm" property="packages" id="pack" indexId="index">
                                        <tr>
                                            <logic:greaterThan name="packagesSize" value="1">
                                                <td><ao:write name="pack" /></td>
                                            </logic:greaterThan>
                                            <td>
                                                <html:hidden property='<%= "packages[" + index + "]" %>' />
                                                <code><html:hidden property='<%= "usernames[" + index + "]" %>' write="true" /></code>
                                                <html:hidden property='<%= "aoServers[" + index + "]" %>' />
                                            </td>
                                            <logic:greaterThan name="aoServersSize" value="1">
                                                <td><code><ao:write name="linuxAccountPasswordSetterForm" property='<%= "aoServers[" + index + "]" %>' /></code></td>
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
                                    <tr><td colspan='7' align='center'><html:submit><bean:message bundle="/clientarea/control/ApplicationResources" key="password.linuxAccountPasswordSetter.field.submit.label" /></html:submit></td></tr>
                                </table>
                            </skin:lightArea>
                        </html:form>
                    </logic:notEmpty>
                </logic:notPresent>
            </skin:contentLine>
        </skin:content>
    </skin:skin>
</html:html>
