<%-- aoweb-struts: Do not edit --%>
<%--
  Copyright 2000-2009 by AO Industries, Inc.,
  7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@include file="/_taglibs.jsp" %>

<skin:setContentType />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html:html lang="true" xhtml="true">
    <%@include file="add-parents.jsp" %>
    <%@include file="business-administrator-password-setter.meta.jsp" %>
    <skin:skin>
        <skin:content width="600">
            <fmt:bundle basename="com.aoindustries.website.clientarea.control.ApplicationResources">
                <skin:contentTitle><fmt:message key="password.businessAdministratorPasswordSetter.title" /></skin:contentTitle>
                <skin:contentHorizontalDivider />
                <skin:contentLine>
                    <logic:empty scope="request" name="businessAdministratorPasswordSetterForm" property="packages">
                        <b><fmt:message key="password.businessAdministratorPasswordSetter.noAccounts" /></b>
                    </logic:empty>
                    <logic:notEmpty scope="request" name="businessAdministratorPasswordSetterForm" property="packages">
                        <html:form action="/password/business-administrator-password-setter-completed">
                            <skin:lightArea>
                                <table cellspacing='0' cellpadding='2'>
                                    <tr>
                                        <bean:size scope="request" name="aoConn" property="packages.map" id="packagesSize" />
                                        <logic:greaterThan name="packagesSize" value="1">
                                            <th><fmt:message key="password.businessAdministratorPasswordSetter.header.package" /></th>
                                        </logic:greaterThan>
                                        <th><fmt:message key="password.businessAdministratorPasswordSetter.header.username" /></th>
                                        <th colspan='2'><fmt:message key="password.businessAdministratorPasswordSetter.header.newPassword" /></th>
                                        <th><fmt:message key="password.businessAdministratorPasswordSetter.header.confirmPassword" /></th>
                                        <th>&#160;</th>
                                    </tr>
                                    <logic:iterate scope="request" name="businessAdministratorPasswordSetterForm" property="packages" id="pack" indexId="index">
                                        <tr>
                                            <logic:greaterThan name="packagesSize" value="1">
                                                <td><ao:write name="pack" /></td>
                                            </logic:greaterThan>
                                            <td>
                                                <html:hidden property='<%= "packages[" + index + "]" %>' />
                                                <code><html:hidden property='<%= "usernames[" + index + "]" %>' write="true" /></code>
                                            </td>
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
                                    <tr><td colspan='6' align='center'><ao:input type="submit"><fmt:message key="password.businessAdministratorPasswordSetter.field.submit.label" /></ao:input></td></tr>
                                </table>
                            </skin:lightArea>
                        </html:form>
                    </logic:notEmpty>
                </skin:contentLine>
            </fmt:bundle>
        </skin:content>
    </skin:skin>
</html:html>
