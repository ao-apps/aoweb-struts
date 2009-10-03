<%-- aoweb-struts: Do not edit --%>
<%--
  Copyright 2000-2009 by AO Industries, Inc.,
  7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" buffer="256kb" autoFlush="true" pageEncoding="UTF-8" %>
<%@include file="/_taglibs.jsp" %>

<skin:setContentType />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html:html lang="true" xhtml="true">
    <fmt:bundle basename="com.aoindustries.website.ApplicationResources">
        <skin:path>/contact.do</skin:path>
        <skin:title><fmt:message key="contact.title" /></skin:title>
        <skin:navImageAlt><fmt:message key="contact.navImageAlt" /></skin:navImageAlt>
        <skin:keywords><fmt:message key="contact.keywords" /></skin:keywords>
        <skin:description><fmt:message key="contact.description" /></skin:description>
        <aoweb:exists path="/add-parents.jsp"><jsp:include page="/add-parents.jsp" /></aoweb:exists>
        <skin:skin>
            <skin:content width="600">
                <skin:contentTitle><fmt:message key="contact.title" /></skin:contentTitle>
                <skin:contentHorizontalDivider />
                <skin:contentLine>
                    <skin:lightArea>
                        <fmt:message key="contact-completed.messageReceived" />
                    </skin:lightArea>
                </skin:contentLine>
            </skin:content>
        </skin:skin>
    </fmt:bundle>
</html:html>
