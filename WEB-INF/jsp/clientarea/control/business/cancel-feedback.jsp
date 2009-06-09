<%-- aoweb-struts: Do not edit --%>
<%--
  Copyright 2003-2009 by AO Industries, Inc.,
  7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" buffer="256kb" autoFlush="true" %>
<%@ include file="/WEB-INF/jsp/taglibs.jsp" %>

<skin:setContentType/>

<bean:define scope="request" name="business" id="business" type="com.aoindustries.aoserv.client.Business"/>

<html:html lang="true">
    <skin:path>/clientarea/control/business/cancel-feedback.do?business=<%= business.getAccounting() %></skin:path>
    <logic:equal name="siteSettings" property="brand.aowebStrutsNoindex" value="true"><skin:meta name="ROBOTS">NOINDEX</skin:meta></logic:equal>
    <skin:title><bean:message bundle="/clientarea/control/ApplicationResources" key="business.cancel.title"/></skin:title>
    <skin:navImageAlt><bean:message bundle="/clientarea/control/ApplicationResources" key="business.cancel.navImageAlt"/></skin:navImageAlt>
    <skin:keywords><bean:message bundle="/clientarea/control/ApplicationResources" key="business.cancel.keywords"/></skin:keywords>
    <skin:description><bean:message bundle="/clientarea/control/ApplicationResources" key="business.cancel.description"/></skin:description>
    <%@ include file="add-parents.jsp" %>
    <%@ include file="add-siblings.jsp" %>
    <skin:skin>
        <skin:content width="600">
            <skin:contentTitle><bean:message bundle="/clientarea/control/ApplicationResources" key="business.cancel.title"/></skin:contentTitle>
            <skin:contentHorizontalDivider/>
            <skin:contentLine>
                <logic:present scope="request" name="permissionDenied">
                    <%@ include file="../../../permission-denied.jsp" %>
                </logic:present>
                <logic:notPresent scope="request" name="permissionDenied">
                    <skin:lightArea>
                        <bean:message bundle="/clientarea/control/ApplicationResources" key="business.cancel-feedback.prompt" arg0="<%= business.getAccounting() %>"/>
                        <html:form action="/business/cancel-feedback-completed">
                            <html:hidden property="business"/>
                            <html:textarea property="reason" rows="16" cols="80"/><BR>
                            <BR>
                            <CENTER><html:submit><bean:message bundle="/clientarea/control/ApplicationResources" key="business.cancel-feedback.submit.label"/></html:submit></CENTER>
                        </html:form>
                    </skin:lightArea>
                </logic:notPresent>
            </skin:contentLine>
        </skin:content>
    </skin:skin>
</html:html>
