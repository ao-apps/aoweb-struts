<%-- aoweb-struts: Do not edit --%>
<%--
  Copyright 2007-2009 by AO Industries, Inc.,
  7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" buffer="256kb" autoFlush="true" %>
<%@ include file="/WEB-INF/jsp/taglibs.jsp" %>

<skin:setContentType/>
<html:html lang="true">
    <skin:path>/signup/managed-server-4.do</skin:path>
    <skin:title><bean:message bundle="/signup/ApplicationResources" key="managed.title"/></skin:title>
    <skin:navImageAlt><bean:message bundle="/signup/ApplicationResources" key="managed.navImageAlt"/></skin:navImageAlt>
    <skin:keywords><bean:message bundle="/signup/ApplicationResources" key="managed.keywords"/></skin:keywords>
    <skin:description><bean:message bundle="/signup/ApplicationResources" key="managed.description"/></skin:description>
    <%@ include file="add-parents.jsp" %>
    <%@ include file="add-siblings.jsp" %>
    <skin:skin>
        <skin:content width="600">
            <skin:contentTitle><bean:message bundle="/signup/ApplicationResources" key="managed.title"/></skin:contentTitle>
            <skin:contentHorizontalDivider/>
            <skin:contentLine>
                <bean:define toScope="request" type="java.lang.String" id="stepNumber" value="4"/>
                <bean:define type="java.lang.String" id="actionPrefix" toScope="request" value="managed-server"/>
                <%@ include file="managed-server-steps.jsp" %>
                <br>
                <html:form action="/managed-server-4-completed.do">
                    <%@ include file="signup-business-form.jsp" %>
                </html:form>
            </skin:contentLine>
        </skin:content>
    </skin:skin>
</html:html>
