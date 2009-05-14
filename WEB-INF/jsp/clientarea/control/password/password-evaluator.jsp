<%--
  Copyright 2000-2009 by AO Industries, Inc.,
  7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" %>
<%@ page buffer="256kb" %>
<%@ page autoFlush="true" %>
<%@ include file="/WEB-INF/jsp/taglibs.jsp" %>

<skin:setContentType/>
<html:html lang="true">
    <skin:path>/clientarea/control/password/password-evaluator.do</skin:path>
    <logic:equal name="siteSettings" property="noindexAowebStruts" value="true"><skin:meta name="ROBOTS">NOINDEX</skin:meta></logic:equal>
    <skin:title><bean:message bundle="/clientarea/control/ApplicationResources" key="password.passwordEvaluator.title"/></skin:title>
    <skin:navImageAlt><bean:message bundle="/clientarea/control/ApplicationResources" key="password.passwordEvaluator.navImageAlt"/></skin:navImageAlt>
    <skin:keywords><bean:message bundle="/clientarea/control/ApplicationResources" key="password.passwordEvaluator.keywords"/></skin:keywords>
    <skin:description><bean:message bundle="/clientarea/control/ApplicationResources" key="password.passwordEvaluator.description"/></skin:description>
    <%@ include file="add-parents.jsp" %>
    <%@ include file="add-siblings.jsp" %>
    <skin:skin onLoad="document.forms['passwordEvaluatorForm'].password.select(); document.forms['passwordEvaluatorForm'].password.focus();">
        <skin:content width="600">
            <skin:contentTitle><bean:message bundle="/clientarea/control/ApplicationResources" key="password.passwordEvaluator.title"/></skin:contentTitle>
            <skin:contentHorizontalDivider/>
            <skin:contentLine>
                <html:javascript staticJavascript='false' bundle="/clientarea/control/ApplicationResources" formName="passwordEvaluatorForm"/>
                <center>
                    <skin:lightArea>
                        <html:form action="/password/password-evaluator-completed" onsubmit="return validatePasswordEvaluatorForm(this);">
                            <B><bean:message bundle="/clientarea/control/ApplicationResources" key="password.passwordEvaluator.prompt"/></B>
                            <HR>
                            <bean:message bundle="/clientarea/control/ApplicationResources" key="password.passwordEvaluator.field.password.prompt"/><html:password size="16" property="password" /> <html:errors bundle="/clientarea/control/ApplicationResources" property="password"/>
                            <logic:present scope="request" name="results">
                                <br><br>
                                <TABLE border="0" cellspacing="0" cellpadding="4">
                                    <logic:iterate scope="request" name="results" id="result" type="com.aoindustries.aoserv.client.PasswordChecker.Result">
                                        <TR>
                                            <TD><bean:write name="result" property="category"/>:</TD>
                                            <TD><bean:write name="result" property="result"/></TD>
                                        </TR>
                                    </logic:iterate>
                                </TABLE>
                            </logic:present><br>
                            <br>
                            <CENTER><html:submit><bean:message bundle="/clientarea/control/ApplicationResources" key="password.passwordEvaluator.field.submit.label"/></html:submit></CENTER>
                        </html:form>
                    </skin:lightArea>
                </center>
            </skin:contentLine>
        </skin:content>
    </skin:skin>
</html:html>
