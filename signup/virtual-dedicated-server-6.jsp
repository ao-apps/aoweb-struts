<%-- aoweb-struts: Do not edit --%>
<%--
  Copyright 2007-2011 by AO Industries, Inc.,
  7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" buffer="256kb" autoFlush="true" pageEncoding="UTF-8" %>
<%@include file="/_taglibs.jsp" %>

<skin:setContentType />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html:html lang="true" xhtml="true">
    <fmt:bundle basename="com.aoindustries.website.signup.ApplicationResources">
        <skin:path>/signup/virtual-dedicated-server-6.do</skin:path>
        <skin:title><fmt:message key="virtualDedicated.title" /></skin:title>
        <skin:navImageAlt><fmt:message key="virtualDedicated.navImageAlt" /></skin:navImageAlt>
        <skin:keywords><fmt:message key="virtualDedicated.keywords" /></skin:keywords>
        <skin:description><fmt:message key="virtualDedicated.description" /></skin:description>
        <%@include file="add-parents.jsp" %>
        <skin:skin>
            <skin:content width="600">
                <skin:contentTitle><fmt:message key="virtualDedicated.title" /></skin:contentTitle>
                <skin:contentHorizontalDivider />
                <skin:contentLine>
                    <ao:script>
                        function selectStep(step) {
                                 if(step=="virtual-dedicated-server")   window.location.href=<ao:url>virtual-dedicated-server.do</ao:url>;
                            else if(step=="virtual-dedicated-server-2") window.location.href=<ao:url>virtual-dedicated-server-2.do</ao:url>;
                            else if(step=="virtual-dedicated-server-3") window.location.href=<ao:url>virtual-dedicated-server-3.do</ao:url>;
                            else if(step=="virtual-dedicated-server-4") window.location.href=<ao:url>virtual-dedicated-server-4.do</ao:url>;
                            else if(step=="virtual-dedicated-server-5") window.location.href=<ao:url>virtual-dedicated-server-5.do</ao:url>;
                        }
                    </ao:script>
                    <bean:define toScope="request" type="java.lang.String" id="stepNumber" value="6" />
                    <bean:define type="java.lang.String" id="actionPrefix" toScope="request" value="virtual-dedicated-server" />
                    <%@include file="dedicated-server-steps.jsp" %>
                    <br />
                    <form action="<ao:url>virtual-dedicated-server-6-completed.do</ao:url>" method="post">
                        <%@include file="dedicated-server-confirmation.jsp" %>
                    </form>
                </skin:contentLine>
            </skin:content>
        </skin:skin>
    </fmt:bundle>
</html:html>
