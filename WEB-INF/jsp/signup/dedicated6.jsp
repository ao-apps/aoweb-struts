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
    <skin:path>/signup/dedicated6.do</skin:path>
    <skin:title><bean:message bundle="/signup/ApplicationResources" key="dedicated.title"/></skin:title>
    <skin:navImageAlt><bean:message bundle="/signup/ApplicationResources" key="dedicated.navImageAlt"/></skin:navImageAlt>
    <skin:keywords><bean:message bundle="/signup/ApplicationResources" key="dedicated.keywords"/></skin:keywords>
    <skin:description><bean:message bundle="/signup/ApplicationResources" key="dedicated.description"/></skin:description>
    <%@ include file="addParents.jsp" %>
    <%@ include file="addSiblings.jsp" %>
    <skin:skin>
        <skin:content width="600">
            <skin:contentTitle><bean:message bundle="/signup/ApplicationResources" key="dedicated.title"/></skin:contentTitle>
            <skin:contentHorizontalDivider/>
            <skin:contentLine>
                <script language="JavaScript1.2"><!--
                    function selectStep(step) {
                        if(step=="dedicated") window.location.href="<%= response.encodeURL("dedicated.do") %>";
                        else if(step=="dedicated2") window.location.href="<%= response.encodeURL("dedicated2.do") %>";
                        else if(step=="dedicated3") window.location.href="<%= response.encodeURL("dedicated3.do") %>";
                        else if(step=="dedicated4") window.location.href="<%= response.encodeURL("dedicated4.do") %>";
                        else if(step=="dedicated5") window.location.href="<%= response.encodeURL("dedicated5.do") %>";
                    }
                // --></script>
                <bean:define toScope="request" type="java.lang.String" id="stepNumber" value="6"/>
                <bean:define type="java.lang.String" id="actionPrefix" toScope="request" value="dedicated"/>
                <%@ include file="dedicatedSteps.jsp" %>
                <br>
                <form action="<%= response.encodeURL("dedicated6Completed.do") %>" method="POST">
                    <%@ include file="dedicatedConfirmation.jsp" %>
                </form>
            </skin:contentLine>
        </skin:content>
    </skin:skin>
</html:html>
