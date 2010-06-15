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
        <skin:path>/signup/reseller-5.do</skin:path>
        <skin:title><fmt:message key="reseller.title" /></skin:title>
        <skin:navImageAlt><fmt:message key="reseller.navImageAlt" /></skin:navImageAlt>
        <skin:keywords><fmt:message key="reseller.keywords" /></skin:keywords>
        <skin:description><fmt:message key="reseller.description" /></skin:description>
        <%@include file="add-parents.jsp" %>
        <skin:skin>
            <skin:content width="600">
                <skin:contentTitle><fmt:message key="reseller.title" /></skin:contentTitle>
                <skin:contentHorizontalDivider />
                <skin:contentLine>
                    <ao:script>
                        function selectStep(step) {
                                 if(step=="reseller")   window.location.href=<ao:url>reseller.do</ao:url>;
                            else if(step=="reseller-2") window.location.href=<ao:url>reseller-2.do</ao:url>;
                            else if(step=="reseller-3") window.location.href=<ao:url>reseller-3.do</ao:url>;
                            else if(step=="reseller-4") window.location.href=<ao:url>reseller-4.do</ao:url>;
                        }
                    </ao:script>
                    <bean:define toScope="request" type="java.lang.String" id="stepNumber" value="5" />
                    <bean:define type="java.lang.String" id="actionPrefix" toScope="request" value="reseller" />
                    <%@include file="minimal-steps.jsp" %>
                    <br />
                    <form action="<ao:url>reseller-5-completed.do</ao:url>" method="post">
                        <%@include file="minimal-confirmation.jsp" %>
                    </form>
                </skin:contentLine>
            </skin:content>
        </skin:skin>
    </fmt:bundle>
</html:html>
