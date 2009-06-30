<%-- aoweb-struts: Do not edit --%>
<%--
  Copyright 2007-2009 by AO Industries, Inc.,
  7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" buffer="256kb" autoFlush="true" %>
<%@ page import="com.aoindustries.io.ChainWriter" %>
<%@ page import="com.aoindustries.util.StringUtility" %>
<%@ include file="/WEB-INF/jsp/taglibs.jsp" %>

<skin:setContentType/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html:html lang="true" xhtml="true">
    <skin:path>/signup/dedicated-server-6.do</skin:path>
    <skin:title><bean:message bundle="/signup/ApplicationResources" key="dedicated.title"/></skin:title>
    <skin:navImageAlt><bean:message bundle="/signup/ApplicationResources" key="dedicated.navImageAlt"/></skin:navImageAlt>
    <skin:keywords><bean:message bundle="/signup/ApplicationResources" key="dedicated.keywords"/></skin:keywords>
    <skin:description><bean:message bundle="/signup/ApplicationResources" key="dedicated.description"/></skin:description>
    <%@ include file="add-parents.jsp" %>
    <%@ include file="add-siblings.jsp" %>
    <skin:skin>
        <skin:content width="600">
            <skin:contentTitle><bean:message bundle="/signup/ApplicationResources" key="dedicated.title"/></skin:contentTitle>
            <skin:contentHorizontalDivider/>
            <skin:contentLine>
                <script type='text/javascript'>
                    // <![CDATA[
                    function selectStep(step) {
                        if(step=="dedicated-server") window.location.href="<% ChainWriter.writeJavaScriptString(StringUtility.replace(response.encodeURL("dedicated-server.do"), "&amp;", "&"), out); %>";
                        else if(step=="dedicated-server-2") window.location.href="<% ChainWriter.writeJavaScriptString(StringUtility.replace(response.encodeURL("dedicated-server-2.do"), "&amp;", "&"), out); %>";
                        else if(step=="dedicated-server-3") window.location.href="<% ChainWriter.writeJavaScriptString(StringUtility.replace(response.encodeURL("dedicated-server-3.do"), "&amp;", "&"), out); %>";
                        else if(step=="dedicated-server-4") window.location.href="<% ChainWriter.writeJavaScriptString(StringUtility.replace(response.encodeURL("dedicated-server-4.do"), "&amp;", "&"), out); %>";
                        else if(step=="dedicated-server-5") window.location.href="<% ChainWriter.writeJavaScriptString(StringUtility.replace(response.encodeURL("dedicated-server-5.do"), "&amp;", "&"), out); %>";
                    }
                    // ]]>
                </script>
                <bean:define toScope="request" type="java.lang.String" id="stepNumber" value="6"/>
                <bean:define type="java.lang.String" id="actionPrefix" toScope="request" value="dedicated-server"/>
                <%@ include file="dedicated-server-steps.jsp" %>
                <br />
                <form action="<%= response.encodeURL("dedicated-server-6-completed.do") %>" method="POST">
                    <%@ include file="dedicated-server-confirmation.jsp" %>
                </form>
            </skin:contentLine>
        </skin:content>
    </skin:skin>
</html:html>
