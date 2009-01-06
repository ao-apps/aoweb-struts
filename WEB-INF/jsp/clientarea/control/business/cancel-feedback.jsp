<%--
  Copyright 2003-2009 by AO Industries, Inc.,
  7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
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

<bean:define scope="request" name="business" id="business" type="com.aoindustries.aoserv.client.Business"/>

<html:html lang="true">
    <skin:path>/clientarea/control/business/cancel-feedback.do?business=<%= business.getAccounting() %></skin:path>
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
