<%-- aoweb-struts --%>
<%--
  Copyright 2007-2009 by AO Industries, Inc.,
  7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" buffer="256kb" autoFlush="true" %>
<%@ include file="/WEB-INF/jsp/taglibs.jsp" %>

<skin:setContentType/>
<html:html lang="true">
    <skin:path>/signup/virtual-managed-server-7.do</skin:path>
    <skin:title><bean:message bundle="/signup/ApplicationResources" key="virtualManaged.title"/></skin:title>
    <skin:navImageAlt><bean:message bundle="/signup/ApplicationResources" key="virtualManaged.navImageAlt"/></skin:navImageAlt>
    <skin:keywords><bean:message bundle="/signup/ApplicationResources" key="virtualManaged.keywords"/></skin:keywords>
    <skin:description><bean:message bundle="/signup/ApplicationResources" key="virtualManaged.description"/></skin:description>
    <%@ include file="add-parents.jsp" %>
    <%@ include file="add-siblings.jsp" %>
    <skin:skin>
        <skin:content width="600">
            <skin:contentTitle><bean:message bundle="/signup/ApplicationResources" key="virtualManaged.title"/></skin:contentTitle>
            <skin:contentHorizontalDivider/>
            <skin:contentLine>
                <script language="JavaScript1.2"><!--
                    function selectStep(step) {
                        if(step=="virtual-managed-server") window.location.href="<%= response.encodeURL("virtual-managed-server.do") %>";
                        else if(step=="virtual-managed-server-2") window.location.href="<%= response.encodeURL("virtual-managed-server-2.do") %>";
                        else if(step=="virtual-managed-server-3") window.location.href="<%= response.encodeURL("virtual-managed-server-3.do") %>";
                        else if(step=="virtual-managed-server-4") window.location.href="<%= response.encodeURL("virtual-managed-server-4.do") %>";
                        else if(step=="virtual-managed-server-5") window.location.href="<%= response.encodeURL("virtual-managed-server-5.do") %>";
                        else if(step=="virtual-managed-server-6") window.location.href="<%= response.encodeURL("virtual-managed-server-6.do") %>";
                    }
                // --></script>
                <bean:define toScope="request" type="java.lang.String" id="stepNumber" value="7"/>
                <bean:define type="java.lang.String" id="actionPrefix" toScope="request" value="virtual-managed-server"/>
                <%@ include file="managed-server-steps.jsp" %>
                <br>
                <form action="<%= response.encodeURL("virtual-managed-server-7-completed.do") %>" method="POST">
                    <%@ include file="managed-server-confirmation.jsp" %>
                </form>
            </skin:contentLine>
        </skin:content>
    </skin:skin>
</html:html>
