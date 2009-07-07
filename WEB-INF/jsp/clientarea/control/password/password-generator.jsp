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
        <skin:path>/clientarea/control/password/password-generator.do</skin:path>
        <skin:meta name="ROBOTS">NOINDEX</skin:meta>
        <skin:title><fmt:message key="password.passwordGenerator.title" /></skin:title>
        <skin:navImageAlt><fmt:message key="password.passwordGenerator.navImageAlt" /></skin:navImageAlt>
        <skin:keywords><fmt:message key="password.passwordGenerator.keywords" /></skin:keywords>
        <skin:description><fmt:message key="password.passwordGenerator.description" /></skin:description>
        <%@ include file="add-parents.jsp" %>
        <%@ include file="add-siblings.jsp" %>
        <skin:skin>
            <skin:content width="600">
                <skin:contentTitle><fmt:message key="password.passwordGenerator.title" /></skin:contentTitle>
                <skin:contentHorizontalDivider />
                <skin:contentLine>
                    <fmt:message key="password.passwordGenerator.followingMayUse" /><br />
                    <br />
                    <code>
                        <logic:iterate name="generatedPasswords" id="generatedPassword">
                            <ao:write name="generatedPassword" /><br />
                        </logic:iterate>
                    </code>
                </skin:contentLine>
            </skin:content>
        </skin:skin>
    </fmt:bundle>
</html:html>
