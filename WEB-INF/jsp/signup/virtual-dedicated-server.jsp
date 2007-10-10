<%--
  Copyright 2000-2007 by AO Industries, Inc.,
  816 Azalea Rd, Mobile, Alabama, 36693, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" %>
<%@ page buffer="256kb" %>
<%@ page autoFlush="true" %>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/aoweb-struts-skin.tld" prefix="skin" %>
<skin:setContentType/>
<html:html lang="true">
    <skin:path>/signup/virtual-dedicated-server.do</skin:path>
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
                        var form = document.forms['virtualDedicatedSignupSelectServerForm'];
                        form.selectedStep.value=step;
                        form.submit();
                    }
                // --></script>
                <bean:define toScope="request" type="java.lang.String" id="stepNumber" value="1"/>
                <bean:define type="java.lang.String" id="actionPrefix" toScope="request" value="virtual-dedicated-server"/>
                <%@ include file="dedicated-server-steps.jsp" %>
                <br>
                <html:form action="/virtual-dedicated-server-completed.do">
                    <input type="hidden" name="selectedStep" value="">
                    <%@ include file="signup-select-server-form.jsp" %>
                </html:form>
            </skin:contentLine>
        </skin:content>
    </skin:skin>
</html:html>
