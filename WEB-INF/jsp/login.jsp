<%-- aoweb-struts --%>
<%--
  Copyright 2007-2009 by AO Industries, Inc.,
  7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" buffer="256kb" autoFlush="true" %>
<%@ include file="/WEB-INF/jsp/taglibs.jsp" %>

<skin:setContentType/>
<html:html lang="true">
    <skin:path>/login.do</skin:path>
    <skin:title><bean:message bundle="/ApplicationResources" key="login.title"/></skin:title>
    <skin:navImageAlt><bean:message bundle="/ApplicationResources" key="login.navImageAlt"/></skin:navImageAlt>
    <skin:keywords><bean:message bundle="/ApplicationResources" key="login.keywords"/></skin:keywords>
    <skin:description><bean:message bundle="/ApplicationResources" key="login.description"/></skin:description>
    <%@ include file="add-parents.jsp" %>
    <%@ include file="add-siblings.jsp" %>
    <skin:skin onLoad="document.forms['loginForm'].username.focus(); document.forms['loginForm'].username.select();">
        <skin:content width="600">
            <skin:contentTitle><bean:message bundle="/ApplicationResources" key="login.title"/></skin:contentTitle>
            <skin:contentHorizontalDivider/>
            <skin:contentLine>
                <html:javascript staticJavascript='false' bundle="/ApplicationResources" formName="loginForm"/>
                <skin:lightArea>
                    <B>
                        <logic:present scope="request" name="authenticationMessage"><bean:write scope="request" name="authenticationMessage"/></logic:present>
                        <logic:notPresent scope="request" name="authenticationMessage"><bean:message bundle="/ApplicationResources" key="login.pleaseLogin"/></logic:notPresent>
                    </B>
                    <HR>
                    <html:form action="/login-completed" onsubmit="return validateLoginForm(this);">
                        <table border='0' cellspacing='2' cellpadding='0'>
                            <tr>
                                <td><bean:message bundle="/ApplicationResources" key="login.field.username.prompt"/></td>
                                <td><html:text size="16" property="username" /></td>
                                <td><html:errors bundle="/ApplicationResources" property="username"/></td>
                            </tr>
                            <tr>
                                <td><bean:message bundle="/ApplicationResources" key="login.field.password.prompt"/></td>
                                <td><html:password size="16" property="password" /></td>
                                <td><html:errors bundle="/ApplicationResources" property="password"/></td>
                            </tr>
                            <tr>
                                <td colspan='3' align='center'><html:submit><bean:message bundle="/ApplicationResources" key="login.field.submit.label"/></html:submit></td>
                            </tr>
                        </table>
                    </html:form>
                </skin:lightArea>
            </skin:contentLine>
        </skin:content>
    </skin:skin>
</html:html>
