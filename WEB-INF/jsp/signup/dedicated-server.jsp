<%-- aoweb-struts: Do not edit --%>
<%--
  Copyright 2000-2009 by AO Industries, Inc.,
  7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" buffer="256kb" autoFlush="true" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/taglibs.jsp" %>

<skin:setContentType/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html:html lang="true" xhtml="true">
    <skin:path>/signup/dedicated-server.do</skin:path>
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
                        var form = document.forms['dedicatedSignupSelectServerForm'];
                        form.selectedStep.value=step;
                        form.submit();
                    }
                    // ]]>
                </script>
                <bean:define toScope="request" type="java.lang.String" id="stepNumber" value="1"/>
                <bean:define type="java.lang.String" id="actionPrefix" toScope="request" value="dedicated-server"/>
                <%@ include file="dedicated-server-steps.jsp" %>
                <br />
                <html:form action="/dedicated-server-completed.do">
                    <input type="hidden" name="selectedStep" value="" />
                    <%@ include file="signup-select-server-form.jsp" %>
                </html:form>
            </skin:contentLine>
        </skin:content>
    </skin:skin>
</html:html>
