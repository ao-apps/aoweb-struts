<%--
  Copyright 2007 by AO Industries, Inc.,
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
    <skin:path>/signup/dedicated-server-2.do</skin:path>
    <skin:title><bean:message bundle="/signup/ApplicationResources" key="dedicated.title"/></skin:title>
    <skin:navImageAlt><bean:message bundle="/signup/ApplicationResources" key="dedicated.navImageAlt"/></skin:navImageAlt>
    <skin:keywords><bean:message bundle="/signup/ApplicationResources" key="dedicated.keywords"/></skin:keywords>
    <skin:description><bean:message bundle="/signup/ApplicationResources" key="dedicated.description"/></skin:description>
    <%@ include file="add-parents.jsp" %>
    <%@ include file="add-siblings.jsp" %>
    <skin:skin onLoad="recalcMonthly();">
        <skin:content width="600">
            <skin:contentTitle><bean:message bundle="/signup/ApplicationResources" key="dedicated.title"/></skin:contentTitle>
            <skin:contentHorizontalDivider/>
            <skin:contentLine>
                <script language="JavaScript1.2"><!--
                    var signupCustomizeServerFormName = 'dedicatedSignupCustomizeServerForm';
                    function selectStep(step) {
                        var form = document.forms['dedicatedSignupCustomizeServerForm'];
                        form.selectedStep.value=step;
                        form.submit();
                    }
                // --></script>
                <bean:define toScope="request" type="java.lang.String" id="stepNumber" value="2"/>
                <bean:define type="java.lang.String" id="actionPrefix" toScope="request" value="dedicated-server"/>
                <%@ include file="dedicated-server-steps.jsp" %>
                <br>
                <html:form action="/dedicated-server-2-completed.do">
                    <%@ include file="signup-customize-server-form.jsp" %>
                </html:form>
            </skin:contentLine>
        </skin:content>
    </skin:skin>
</html:html>
