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
    <skin:path>/signup/dedicated2.do</skin:path>
    <skin:title><bean:message bundle="/signup/ApplicationResources" key="dedicated.title"/></skin:title>
    <skin:navImageAlt><bean:message bundle="/signup/ApplicationResources" key="dedicated.navImageAlt"/></skin:navImageAlt>
    <skin:keywords><bean:message bundle="/signup/ApplicationResources" key="dedicated.keywords"/></skin:keywords>
    <skin:description><bean:message bundle="/signup/ApplicationResources" key="dedicated.description"/></skin:description>
    <%@ include file="addParents.jsp" %>
    <%@ include file="addSiblings.jsp" %>
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
                <bean:define type="java.lang.String" id="actionPrefix" toScope="request" value="dedicated"/>
                <%@ include file="serverSteps.jsp" %>
                <br>
                <html:form action="/dedicated2Completed.do">
                    <%@ include file="dedicatedSignupCustomizeServerForm.jsp" %>
                </html:form>
            </skin:contentLine>
        </skin:content>
    </skin:skin>
</html:html>
