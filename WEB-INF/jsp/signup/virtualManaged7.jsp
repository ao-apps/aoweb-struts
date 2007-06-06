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
    <skin:path>/signup/virtualManaged7.do</skin:path>
    <skin:title><bean:message bundle="/signup/ApplicationResources" key="virtualManaged.title"/></skin:title>
    <skin:navImageAlt><bean:message bundle="/signup/ApplicationResources" key="virtualManaged.navImageAlt"/></skin:navImageAlt>
    <skin:keywords><bean:message bundle="/signup/ApplicationResources" key="virtualManaged.keywords"/></skin:keywords>
    <skin:description><bean:message bundle="/signup/ApplicationResources" key="virtualManaged.description"/></skin:description>
    <%@ include file="addParents.jsp" %>
    <%@ include file="addSiblings.jsp" %>
    <skin:skin>
        <skin:content width="600">
            <skin:contentTitle><bean:message bundle="/signup/ApplicationResources" key="virtualManaged.title"/></skin:contentTitle>
            <skin:contentHorizontalDivider/>
            <skin:contentLine>
                <script language="JavaScript1.2"><!--
                    function selectStep(step) {
                        if(step=="virtualManaged") window.location.href="<%= response.encodeURL("virtualManaged.do") %>";
                        else if(step=="virtualManaged2") window.location.href="<%= response.encodeURL("virtualManaged2.do") %>";
                        else if(step=="virtualManaged3") window.location.href="<%= response.encodeURL("virtualManaged3.do") %>";
                        else if(step=="virtualManaged4") window.location.href="<%= response.encodeURL("virtualManaged4.do") %>";
                        else if(step=="virtualManaged5") window.location.href="<%= response.encodeURL("virtualManaged5.do") %>";
                        else if(step=="virtualManaged6") window.location.href="<%= response.encodeURL("virtualManaged6.do") %>";
                    }
                // --></script>
                <bean:define toScope="request" type="java.lang.String" id="stepNumber" value="7"/>
                <bean:define type="java.lang.String" id="actionPrefix" toScope="request" value="virtualManaged"/>
                <%@ include file="managedSteps.jsp" %>
                <br>
                <form action="<%= response.encodeURL("virtualManaged7Completed.do") %>" method="POST">
                    <%@ include file="managedConfirmation.jsp" %>
                </form>
            </skin:contentLine>
        </skin:content>
    </skin:skin>
</html:html>
