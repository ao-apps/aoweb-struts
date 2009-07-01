<%-- aoweb-struts: Do not edit --%>
<%--
  Copyright 2007-2009 by AO Industries, Inc.,
  7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" buffer="256kb" autoFlush="true" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/taglibs.jsp" %>

<skin:setContentType/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html:html lang="true" xhtml="true">
    <skin:path>/login.do</skin:path>
    <skin:meta name="ROBOTS">NOINDEX</skin:meta>
    <skin:title><bean:message bundle="/ApplicationResources" key="login.title"/></skin:title>
    <skin:navImageAlt><bean:message bundle="/ApplicationResources" key="login.navImageAlt"/></skin:navImageAlt>
    <skin:keywords><bean:message bundle="/ApplicationResources" key="login.keywords"/></skin:keywords>
    <skin:description><bean:message bundle="/ApplicationResources" key="login.description"/></skin:description>
    <aoweb:exists path="/WEB-INF/jsp/add-parents.jsp">
        <jsp:include page="/WEB-INF/jsp/add-parents.jsp"/>
    </aoweb:exists>
    <aoweb:exists path="/WEB-INF/jsp/add-siblings.jsp">
        <jsp:include page="/WEB-INF/jsp/add-siblings.jsp"/>
    </aoweb:exists>
    <skin:skin onload="document.forms['loginForm'].username.focus(); document.forms['loginForm'].username.select();">
        <skin:content width="600">
            <skin:contentTitle><bean:message bundle="/ApplicationResources" key="login.title"/></skin:contentTitle>
            <skin:contentHorizontalDivider/>
            <skin:contentLine>
                <html:javascript staticJavascript='false' bundle="/ApplicationResources" formName="loginForm"/><noscript><!-- Do nothing --></noscript>
                <skin:lightArea>
                    <b>
                        <logic:present scope="request" name="authenticationMessage"><bean:write scope="request" name="authenticationMessage" filter="false"/></logic:present>
                        <logic:notPresent scope="request" name="authenticationMessage"><bean:message bundle="/ApplicationResources" key="login.pleaseLogin"/></logic:notPresent>
                    </b>
                    <hr />
                    <html:form action="/login-completed" onsubmit="return validateLoginForm(this);"><div>
                        <%-- Add the authenticationTarget to the form because the new session could expire before they login and lost their target --%>
                        <logic:present scope="session" name="authenticationTarget">
                            <bean:define scope="session" name="authenticationTarget" type="java.lang.String" id="authenticationTarget"/>
                            <input type="hidden" name="authenticationTarget" value="<%= authenticationTarget %>" />
                        </logic:present>
                        <table cellspacing='2' cellpadding='0'>
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
                    </div></html:form>
                </skin:lightArea>
            </skin:contentLine>
        </skin:content>
    </skin:skin>
</html:html>
