<%-- aoweb-struts: Do not edit --%>
<%--
  Copyright 2007-2009 by AO Industries, Inc.,
  7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" buffer="256kb" autoFlush="true" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/taglibs.jsp" %>

<skin:setContentType/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html:html lang="true" xhtml="true">
    <skin:path>/signup/virtual-managed-server-3.do</skin:path>
    <skin:title><bean:message bundle="/signup/ApplicationResources" key="virtualManaged.title"/></skin:title>
    <skin:navImageAlt><bean:message bundle="/signup/ApplicationResources" key="virtualManaged.navImageAlt"/></skin:navImageAlt>
    <skin:keywords><bean:message bundle="/signup/ApplicationResources" key="virtualManaged.keywords"/></skin:keywords>
    <skin:description><bean:message bundle="/signup/ApplicationResources" key="virtualManaged.description"/></skin:description>
    <%@ include file="add-parents.jsp" %>
    <%@ include file="add-siblings.jsp" %>
    <skin:skin onload="recalcMonthly();">
        <skin:content width="600">
            <skin:contentTitle><bean:message bundle="/signup/ApplicationResources" key="virtualManaged.title"/></skin:contentTitle>
            <skin:contentHorizontalDivider/>
            <skin:contentLine>
                <script type='text/javascript'>
                    // <![CDATA[
                    var signupCustomizeManagementFormName = 'virtualManagedSignupCustomizeManagementForm';
                    function selectStep(step) {
                        var form = document.forms['virtualManagedSignupCustomizeManagementForm'];
                        form.selectedStep.value=step;
                        form.submit();
                    }
                    // ]]>
                </script>
                <bean:define toScope="request" type="java.lang.String" id="stepNumber" value="3"/>
                <bean:define type="java.lang.String" id="actionPrefix" toScope="request" value="virtual-managed-server"/>
                <%@ include file="managed-server-steps.jsp" %>
                <br />
                <html:form action="/virtual-managed-server-3-completed.do">
                    <%@ include file="signup-customize-management-form.jsp" %>
                </html:form>
            </skin:contentLine>
        </skin:content>
    </skin:skin>
</html:html>
