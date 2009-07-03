<%-- aoweb-struts: Do not edit --%>
<%--
  Copyright 2007-2009 by AO Industries, Inc.,
  7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" buffer="256kb" autoFlush="true" pageEncoding="UTF-8" %>
<%@ page import="com.aoindustries.util.EncodingUtils" %>
<%@ page import="com.aoindustries.util.StringUtility" %>
<%@ include file="/WEB-INF/jsp/taglibs.jsp" %>

<skin:setContentType />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html:html lang="true" xhtml="true">
    <skin:path>/signup/virtual-dedicated-server-6.do</skin:path>
    <skin:title><bean:message bundle="/signup/ApplicationResources" key="virtualDedicated.title" /></skin:title>
    <skin:navImageAlt><bean:message bundle="/signup/ApplicationResources" key="virtualDedicated.navImageAlt" /></skin:navImageAlt>
    <skin:keywords><bean:message bundle="/signup/ApplicationResources" key="virtualDedicated.keywords" /></skin:keywords>
    <skin:description><bean:message bundle="/signup/ApplicationResources" key="virtualDedicated.description" /></skin:description>
    <%@ include file="add-parents.jsp" %>
    <%@ include file="add-siblings.jsp" %>
    <skin:skin>
        <skin:content width="600">
            <skin:contentTitle><bean:message bundle="/signup/ApplicationResources" key="virtualDedicated.title" /></skin:contentTitle>
            <skin:contentHorizontalDivider />
            <skin:contentLine>
                <script type='text/javascript'>
                    // <![CDATA[
                    function selectStep(step) {
                        if(step=="virtual-dedicated-server") window.location.href="<% EncodingUtils.encodeJavaScriptString(StringUtility.replace(response.encodeURL("virtual-dedicated-server.do"), "&amp;", "&"), out); %>";
                        else if(step=="virtual-dedicated-server-2") window.location.href="<% EncodingUtils.encodeJavaScriptString(StringUtility.replace(response.encodeURL("virtual-dedicated-server-2.do"), "&amp;", "&"), out); %>";
                        else if(step=="virtual-dedicated-server-3") window.location.href="<% EncodingUtils.encodeJavaScriptString(StringUtility.replace(response.encodeURL("virtual-dedicated-server-3.do"), "&amp;", "&"), out); %>";
                        else if(step=="virtual-dedicated-server-4") window.location.href="<% EncodingUtils.encodeJavaScriptString(StringUtility.replace(response.encodeURL("virtual-dedicated-server-4.do"), "&amp;", "&"), out); %>";
                        else if(step=="virtual-dedicated-server-5") window.location.href="<% EncodingUtils.encodeJavaScriptString(StringUtility.replace(response.encodeURL("virtual-dedicated-server-5.do"), "&amp;", "&"), out); %>";
                    }
                    // ]]>
                </script>
                <bean:define toScope="request" type="java.lang.String" id="stepNumber" value="6" />
                <bean:define type="java.lang.String" id="actionPrefix" toScope="request" value="virtual-dedicated-server" />
                <%@ include file="dedicated-server-steps.jsp" %>
                <br />
                <form action="<%= response.encodeURL("virtual-dedicated-server-6-completed.do") %>" method="post">
                    <%@ include file="dedicated-server-confirmation.jsp" %>
                </form>
            </skin:contentLine>
        </skin:content>
    </skin:skin>
</html:html>
