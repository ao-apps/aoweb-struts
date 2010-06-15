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
    <%@include file="password-evaluator.meta.jsp" %>
    <skin:skin onload="document.forms['passwordEvaluatorForm'].password.select(); document.forms['passwordEvaluatorForm'].password.focus();">
        <skin:content width="600">
            <fmt:bundle basename="com.aoindustries.website.clientarea.control.ApplicationResources">
                <skin:contentTitle><fmt:message key="password.passwordEvaluator.title" /></skin:contentTitle>
                <skin:contentHorizontalDivider />
                <skin:contentLine align="center">
                    <html:javascript staticJavascript='false' bundle="/clientarea/control/ApplicationResources" formName="passwordEvaluatorForm" />
                    <skin:lightArea>
                        <html:form action="/password/password-evaluator-completed" onsubmit="return validatePasswordEvaluatorForm(this);">
                            <div>
                                <b><fmt:message key="password.passwordEvaluator.prompt" /></b>
                                <hr />
                                <fmt:message key="password.passwordEvaluator.field.password.prompt" /><html:password size="16" property="password" /> <html:errors bundle="/clientarea/control/ApplicationResources" property="password" />
                                <logic:present scope="request" name="results">
                                    <br /><br />
                                    <table cellspacing="0" cellpadding="4">
                                        <logic:iterate scope="request" name="results" id="result" type="com.aoindustries.aoserv.client.PasswordChecker.Result">
                                            <tr>
                                                <td><ao:write name="result" property="category" />:</td>
                                                <td><ao:write name="result" property="result" /></td>
                                            </tr>
                                        </logic:iterate>
                                    </table>
                                </logic:present><br />
                                <br />
                                <div style="text-align:center"><ao:input type="submit"><fmt:message key="password.passwordEvaluator.field.submit.label" /></ao:input></div>
                            </div>
                        </html:form>
                    </skin:lightArea>
                </skin:contentLine>
            </fmt:bundle>
        </skin:content>
    </skin:skin>
</html:html>
