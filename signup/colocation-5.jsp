<%-- aoweb-struts: Do not edit --%>
<%--
  Copyright 2009 by AO Industries, Inc.,
  7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" buffer="256kb" autoFlush="true" pageEncoding="UTF-8" %>
<%@include file="/_taglibs.jsp" %>

<skin:setContentType />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html:html lang="true" xhtml="true">
    <fmt:bundle basename="com.aoindustries.website.signup.ApplicationResources">
        <skin:path>/signup/colocation-5.do</skin:path>
        <skin:title><fmt:message key="colocation.title" /></skin:title>
        <skin:navImageAlt><fmt:message key="colocation.navImageAlt" /></skin:navImageAlt>
        <skin:keywords><fmt:message key="colocation.keywords" /></skin:keywords>
        <skin:description><fmt:message key="colocation.description" /></skin:description>
        <%@include file="add-parents.jsp" %>
        <skin:skin>
            <skin:content width="600">
                <skin:contentTitle><fmt:message key="colocation.title" /></skin:contentTitle>
                <skin:contentHorizontalDivider />
                <skin:contentLine>
                    <ao:script>
                        function selectStep(step) {
                                 if(step=="colocation")   window.location.href=<ao:url>colocation.do</ao:url>;
                            else if(step=="colocation-2") window.location.href=<ao:url>colocation-2.do</ao:url>;
                            else if(step=="colocation-3") window.location.href=<ao:url>colocation-3.do</ao:url>;
                            else if(step=="colocation-4") window.location.href=<ao:url>colocation-4.do</ao:url>;
                        }
                    </ao:script>
                    <bean:define toScope="request" type="java.lang.String" id="stepNumber" value="5" />
                    <bean:define type="java.lang.String" id="actionPrefix" toScope="request" value="colocation" />
                    <%@include file="minimal-steps.jsp" %>
                    <br />
                    <form action="<ao:url>colocation-5-completed.do</ao:url>" method="post">
                        <%@include file="minimal-confirmation.jsp" %>
                    </form>
                </skin:contentLine>
            </skin:content>
        </skin:skin>
    </fmt:bundle>
</html:html>
