<%-- aoweb-struts: Do not edit --%>
<%--
  Copyright 2000-2009 by AO Industries, Inc.,
  7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" buffer="256kb" autoFlush="true" %>
<%@ include file="/WEB-INF/jsp/taglibs.jsp" %>

<skin:setContentType/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html:html lang="true" xhtml="true">
    <skin:path>/clientarea/control/password/password-evaluator.do</skin:path>
    <logic:equal name="siteSettings" property="brand.aowebStrutsNoindex" value="true"><skin:meta name="ROBOTS">NOINDEX</skin:meta></logic:equal>
    <skin:title><bean:message bundle="/clientarea/control/ApplicationResources" key="password.passwordEvaluator.title"/></skin:title>
    <skin:navImageAlt><bean:message bundle="/clientarea/control/ApplicationResources" key="password.passwordEvaluator.navImageAlt"/></skin:navImageAlt>
    <skin:keywords><bean:message bundle="/clientarea/control/ApplicationResources" key="password.passwordEvaluator.keywords"/></skin:keywords>
    <skin:description><bean:message bundle="/clientarea/control/ApplicationResources" key="password.passwordEvaluator.description"/></skin:description>
    <%@ include file="add-parents.jsp" %>
    <%@ include file="add-siblings.jsp" %>
    <skin:skin onload="document.forms['passwordEvaluatorForm'].password.select(); document.forms['passwordEvaluatorForm'].password.focus();">
        <skin:content width="600">
            <skin:contentTitle><bean:message bundle="/clientarea/control/ApplicationResources" key="password.passwordEvaluator.title"/></skin:contentTitle>
            <skin:contentHorizontalDivider/>
            <skin:contentLine>
                <html:javascript staticJavascript='false' bundle="/clientarea/control/ApplicationResources" formName="passwordEvaluatorForm"/><noscript><!-- Do nothing --></noscript>
                <center>
                    <skin:lightArea>
                        <html:form action="/password/password-evaluator-completed" onsubmit="return validatePasswordEvaluatorForm(this);">
                            <b><bean:message bundle="/clientarea/control/ApplicationResources" key="password.passwordEvaluator.prompt"/></b>
                            <hr />
                            <bean:message bundle="/clientarea/control/ApplicationResources" key="password.passwordEvaluator.field.password.prompt"/><html:password size="16" property="password" /> <html:errors bundle="/clientarea/control/ApplicationResources" property="password"/>
                            <logic:present scope="request" name="results">
                                <br /><br />
                                <table cellspacing="0" cellpadding="4">
                                    <logic:iterate scope="request" name="results" id="result" type="com.aoindustries.aoserv.client.PasswordChecker.Result">
                                        <tr>
                                            <td><bean:write name="result" property="category"/>:</td>
                                            <td><bean:write name="result" property="result"/></td>
                                        </tr>
                                    </logic:iterate>
                                </table>
                            </logic:present><br />
                            <br />
                            <center><html:submit><bean:message bundle="/clientarea/control/ApplicationResources" key="password.passwordEvaluator.field.submit.label"/></html:submit></center>
                        </html:form>
                    </skin:lightArea>
                </center>
            </skin:contentLine>
        </skin:content>
    </skin:skin>
</html:html>
