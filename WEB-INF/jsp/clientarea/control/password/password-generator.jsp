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
    <skin:path>/clientarea/control/password/password-generator.do</skin:path>
    <skin:meta name="ROBOTS">NOINDEX</skin:meta>
    <skin:title><bean:message bundle="/clientarea/control/ApplicationResources" key="password.passwordGenerator.title"/></skin:title>
    <skin:navImageAlt><bean:message bundle="/clientarea/control/ApplicationResources" key="password.passwordGenerator.navImageAlt"/></skin:navImageAlt>
    <skin:keywords><bean:message bundle="/clientarea/control/ApplicationResources" key="password.passwordGenerator.keywords"/></skin:keywords>
    <skin:description><bean:message bundle="/clientarea/control/ApplicationResources" key="password.passwordGenerator.description"/></skin:description>
    <%@ include file="add-parents.jsp" %>
    <%@ include file="add-siblings.jsp" %>
    <skin:skin>
        <skin:content width="600">
            <skin:contentTitle><bean:message bundle="/clientarea/control/ApplicationResources" key="password.passwordGenerator.title"/></skin:contentTitle>
            <skin:contentHorizontalDivider/>
            <skin:contentLine>
                <bean:message bundle="/clientarea/control/ApplicationResources" key="password.passwordGenerator.followingMayUse"/><br />
                <br />
                <code>
                    <logic:iterate name="generatedPasswords" id="generatedPassword">
                        <bean:write name="generatedPassword"/><br />
                    </logic:iterate>
                </code>
            </skin:contentLine>
        </skin:content>
    </skin:skin>
</html:html>
