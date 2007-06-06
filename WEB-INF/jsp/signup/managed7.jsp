<%--
  Copyright 2007 by AO Industries, Inc.,
  816 Azalea Rd, Mobile, Alabama, 36693, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld"       prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld"       prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld"      prefix="logic" %>
<%@ taglib uri="/WEB-INF/aoweb-struts-skin.tld" prefix="skin" %>
<skin:setContentType/>
<html:html lang="true">
    <skin:path>/signup/managed7.do</skin:path>
    <skin:title><bean:message bundle="/signup/ApplicationResources" key="managed.title"/></skin:title>
    <skin:navImageAlt><bean:message bundle="/signup/ApplicationResources" key="managed.navImageAlt"/></skin:navImageAlt>
    <skin:keywords><bean:message bundle="/signup/ApplicationResources" key="managed.keywords"/></skin:keywords>
    <skin:description><bean:message bundle="/signup/ApplicationResources" key="managed.description"/></skin:description>
    <%@ include file="addParents.jsp" %>
    <%@ include file="addSiblings.jsp" %>
    <skin:skin>
        <skin:content width="600">
            <skin:contentTitle><bean:message bundle="/signup/ApplicationResources" key="managed.title"/></skin:contentTitle>
            <skin:contentHorizontalDivider/>
            <skin:contentLine>
                <script language="JavaScript1.2"><!--
                    function selectStep(step) {
                        if(step=="managed") window.location.href="<%= response.encodeURL("managed.do") %>";
                        else if(step=="managed2") window.location.href="<%= response.encodeURL("managed2.do") %>";
                        else if(step=="managed3") window.location.href="<%= response.encodeURL("managed3.do") %>";
                        else if(step=="managed4") window.location.href="<%= response.encodeURL("managed4.do") %>";
                        else if(step=="managed5") window.location.href="<%= response.encodeURL("managed5.do") %>";
                        else if(step=="managed6") window.location.href="<%= response.encodeURL("managed6.do") %>";
                    }
                // --></script>
                <bean:define toScope="request" type="java.lang.String" id="stepNumber" value="7"/>
                <bean:define type="java.lang.String" id="actionPrefix" toScope="request" value="managed"/>
                <%@ include file="managedSteps.jsp" %>
                <br>
                <form action="<%= response.encodeURL("managed7Completed.do") %>" method="POST">
                    <%@ include file="managedConfirmation.jsp" %>
                </form>
            </skin:contentLine>
        </skin:content>
    </skin:skin>
</html:html>
