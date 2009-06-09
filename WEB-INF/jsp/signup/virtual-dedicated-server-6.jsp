<%-- aoweb-struts: Do not edit --%>
<%--
  Copyright 2007-2009 by AO Industries, Inc.,
  7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" buffer="256kb" autoFlush="true" %>
<%@ include file="/WEB-INF/jsp/taglibs.jsp" %>

<skin:setContentType/>
<html:html lang="true">
    <skin:path>/signup/virtual-dedicated-server-6.do</skin:path>
    <skin:title><bean:message bundle="/signup/ApplicationResources" key="virtualDedicated.title"/></skin:title>
    <skin:navImageAlt><bean:message bundle="/signup/ApplicationResources" key="virtualDedicated.navImageAlt"/></skin:navImageAlt>
    <skin:keywords><bean:message bundle="/signup/ApplicationResources" key="virtualDedicated.keywords"/></skin:keywords>
    <skin:description><bean:message bundle="/signup/ApplicationResources" key="virtualDedicated.description"/></skin:description>
    <%@ include file="add-parents.jsp" %>
    <%@ include file="add-siblings.jsp" %>
    <skin:skin>
        <skin:content width="600">
            <skin:contentTitle><bean:message bundle="/signup/ApplicationResources" key="virtualDedicated.title"/></skin:contentTitle>
            <skin:contentHorizontalDivider/>
            <skin:contentLine>
                <script language="JavaScript1.2"><!--
                    function selectStep(step) {
                        if(step=="virtual-dedicated-server") window.location.href="<%= response.encodeURL("virtual-dedicated-server.do") %>";
                        else if(step=="virtual-dedicated-server-2") window.location.href="<%= response.encodeURL("virtual-dedicated-server-2.do") %>";
                        else if(step=="virtual-dedicated-server-3") window.location.href="<%= response.encodeURL("virtual-dedicated-server-3.do") %>";
                        else if(step=="virtual-dedicated-server-4") window.location.href="<%= response.encodeURL("virtual-dedicated-server-4.do") %>";
                        else if(step=="virtual-dedicated-server-5") window.location.href="<%= response.encodeURL("virtual-dedicated-server-5.do") %>";
                    }
                // --></script>
                <bean:define toScope="request" type="java.lang.String" id="stepNumber" value="6"/>
                <bean:define type="java.lang.String" id="actionPrefix" toScope="request" value="virtual-dedicated-server"/>
                <%@ include file="dedicated-server-steps.jsp" %>
                <br>
                <form action="<%= response.encodeURL("virtual-dedicated-server-6-completed.do") %>" method="POST">
                    <%@ include file="dedicated-server-confirmation.jsp" %>
                </form>
            </skin:contentLine>
        </skin:content>
    </skin:skin>
</html:html>
