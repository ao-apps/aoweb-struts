<%-- aoweb-struts: Do not edit --%>
<%--
  Copyright 2000-2009 by AO Industries, Inc.,
  7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" buffer="256kb" autoFlush="true" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/taglibs.jsp" %>

<skin:setContentType/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html:html lang="true" xhtml="true">
    <skin:path>/clientarea/control/password/business-administrator-password-setter.do</skin:path>
    <logic:equal name="siteSettings" property="brand.aowebStrutsNoindex" value="true"><skin:meta name="ROBOTS">NOINDEX</skin:meta></logic:equal>
    <skin:title><bean:message bundle="/clientarea/control/ApplicationResources" key="password.businessAdministratorPasswordSetter.title"/></skin:title>
    <skin:navImageAlt><bean:message bundle="/clientarea/control/ApplicationResources" key="password.businessAdministratorPasswordSetter.navImageAlt"/></skin:navImageAlt>
    <skin:keywords><bean:message bundle="/clientarea/control/ApplicationResources" key="password.businessAdministratorPasswordSetter.keywords"/></skin:keywords>
    <skin:description><bean:message bundle="/clientarea/control/ApplicationResources" key="password.businessAdministratorPasswordSetter.description"/></skin:description>
    <%@ include file="add-parents.jsp" %>
    <%@ include file="add-siblings.jsp" %>
    <skin:skin>
        <skin:content width="600">
            <skin:contentTitle><bean:message bundle="/clientarea/control/ApplicationResources" key="password.businessAdministratorPasswordSetter.title"/></skin:contentTitle>
            <skin:contentHorizontalDivider/>
            <skin:contentLine>
                <logic:empty scope="request" name="businessAdministratorPasswordSetterForm" property="packages">
                    <b><bean:message bundle="/clientarea/control/ApplicationResources" key="password.businessAdministratorPasswordSetter.noAccounts"/></b>
                </logic:empty>
                <logic:notEmpty scope="request" name="businessAdministratorPasswordSetterForm" property="packages">
                    <html:form action="/password/business-administrator-password-setter-completed">
                        <skin:lightArea>
                            <table cellspacing='0' cellpadding='2'>
                                <tr>
                                    <bean:size scope="request" name="aoConn" property="packages.map" id="packagesSize"/>
                                    <logic:greaterThan name="packagesSize" value="1">
                                        <th><bean:message bundle="/clientarea/control/ApplicationResources" key="password.businessAdministratorPasswordSetter.header.package"/></th>
                                    </logic:greaterThan>
                                    <th><bean:message bundle="/clientarea/control/ApplicationResources" key="password.businessAdministratorPasswordSetter.header.username"/></th>
                                    <th colspan='2'><bean:message bundle="/clientarea/control/ApplicationResources" key="password.businessAdministratorPasswordSetter.header.newPassword"/></th>
                                    <th><bean:message bundle="/clientarea/control/ApplicationResources" key="password.businessAdministratorPasswordSetter.header.confirmPassword"/></th>
                                    <th>&#160;</th>
                                </tr>
                                <logic:iterate scope="request" name="businessAdministratorPasswordSetterForm" property="packages" id="pack" indexId="index">
                                    <tr>
                                        <logic:greaterThan name="packagesSize" value="1">
                                            <td><bean:write name="pack" filter="true"/></td>
                                        </logic:greaterThan>
                                        <td><html:hidden property='<%= "packages[" + index + "]" %>'/><code><html:hidden property='<%= "usernames[" + index + "]" %>' write="true" /></code></td>
                                        <td><html:password size="20" property='<%= "newPasswords[" + index + "]" %>' /></td>
                                        <td style="white-space:nowrap">
                                            <html:errors bundle="/clientarea/control/ApplicationResources" property='<%= "newPasswords[" + index + "].newPasswords" %>'/>
                                            <html:messages id="message" message="true" bundle="/clientarea/control/ApplicationResources" property='<%= "newPasswords[" + index + "].newPasswords" %>'>
                                                <bean:write name="message"/><br />
                                            </html:messages>
                                        </td>
                                        <td><html:password size="20" property='<%= "confirmPasswords[" + index + "]" %>' /></td>
                                        <td style="white-space:nowrap">
                                            <html:errors bundle="/clientarea/control/ApplicationResources" property='<%= "confirmPasswords[" + index + "].confirmPasswords" %>'/>
                                            <html:messages id="message" message="true" bundle="/clientarea/control/ApplicationResources" property='<%= "confirmPasswords[" + index + "].confirmPasswords" %>'>
                                                <bean:write name="message"/><br />
                                            </html:messages>
                                        </td>
                                    </tr>
                                </logic:iterate>
                                <tr><td colspan='6' align='center'><html:submit><bean:message bundle="/clientarea/control/ApplicationResources" key="password.businessAdministratorPasswordSetter.field.submit.label"/></html:submit></td></tr>
                            </table>
                        </skin:lightArea>
                    </html:form>
                </logic:notEmpty>
            </skin:contentLine>
        </skin:content>
    </skin:skin>
</html:html>
