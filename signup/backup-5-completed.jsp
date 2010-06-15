<%-- aoweb-struts: Do not edit --%>
<%--
  Copyright 2009-2010 by AO Industries, Inc.,
  7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" buffer="256kb" autoFlush="true" pageEncoding="UTF-8" %>
<%@include file="/_taglibs.jsp" %>

<skin:setContentType />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html:html lang="true" xhtml="true">
    <fmt:bundle basename="com.aoindustries.website.signup.ApplicationResources">
        <skin:path>/signup/backup.do</skin:path>
        <skin:title><fmt:message key="backup.title" /></skin:title>
        <skin:navImageAlt><fmt:message key="backup.navImageAlt" /></skin:navImageAlt>
        <skin:keywords><fmt:message key="backup.keywords" /></skin:keywords>
        <skin:description><fmt:message key="backup.description" /></skin:description>
        <%@include file="add-parents.jsp" %>
        <skin:skin>
            <skin:content width="600">
                <skin:contentTitle><fmt:message key="backup.title" /></skin:contentTitle>
                <skin:contentHorizontalDivider />
                <skin:contentLine>
                    <bean:define toScope="request" type="java.lang.String" id="stepNumber" value="6" />
                    <bean:define type="java.lang.String" id="actionPrefix" toScope="request" value="backup" />
                    <%@include file="minimal-steps.jsp" %>
                    <br />
                    <%@include file="minimal-confirmation-completed.jsp" %>
                </skin:contentLine>
            </skin:content>
        </skin:skin>
    </fmt:bundle>
</html:html>
