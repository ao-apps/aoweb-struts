<%-- aoweb-struts: Do not edit --%>
<%--
  Copyright 2000-2009 by AO Industries, Inc.,
  7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" buffer="256kb" autoFlush="true" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/taglibs.jsp" %>

<skin:setContentType />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html:html lang="true" xhtml="true">
    <skin:path>/clientarea/ticket/edit.do?pkey=<ao:write scope="request" name="ticket" property="pkey" /></skin:path>
    <skin:title><bean:message bundle="/clientarea/ticket/ApplicationResources" key="edit.title" /></skin:title>
    <skin:navImageAlt><bean:message bundle="/clientarea/ticket/ApplicationResources" key="edit.navImageAlt" /></skin:navImageAlt>
    <skin:keywords><bean:message bundle="/clientarea/ticket/ApplicationResources" key="edit.keywords" /></skin:keywords>
    <skin:description><bean:message bundle="/clientarea/ticket/ApplicationResources" key="edit.description" /></skin:description>
    <jsp:include page="add-parents.jsp" />
    <jsp:include page="add-siblings.jsp" />
    <skin:skin>
        <skin:content>
            <skin:contentTitle><bean:message bundle="/clientarea/ticket/ApplicationResources" key="edit.title" /></skin:contentTitle>
            <skin:contentHorizontalDivider />
            <skin:contentLine>
                <logic:present scope="request" name="permissionDenied">
                    <%@ include file="../../permission-denied.jsp" %>
                </logic:present>
                <logic:notPresent scope="request" name="permissionDenied">
                    <skin:lightArea>
                        <bean:message bundle="/clientarea/ticket/ApplicationResources" key="edit-completed.ticketChanges" />
                        <hr />
                        <ul>
                            <logic:equal scope="request" name="businessUpdated" value="true">
                                <li><bean:message bundle="/clientarea/ticket/ApplicationResources" key="edit-completed.businessUpdated" /></li>
                            </logic:equal>
                            <logic:equal scope="request" name="contactEmailsUpdated" value="true">
                                <li><bean:message bundle="/clientarea/ticket/ApplicationResources" key="edit-completed.contactEmailsUpdated" /></li>
                            </logic:equal>
                            <logic:equal scope="request" name="contactPhoneNumbersUpdated" value="true">
                                <li><bean:message bundle="/clientarea/ticket/ApplicationResources" key="edit-completed.contactPhoneNumbersUpdated" /></li>
                            </logic:equal>
                            <logic:equal scope="request" name="clientPriorityUpdated" value="true">
                                <li><bean:message bundle="/clientarea/ticket/ApplicationResources" key="edit-completed.clientPriorityUpdated" /></li>
                            </logic:equal>
                            <logic:equal scope="request" name="summaryUpdated" value="true">
                                <li><bean:message bundle="/clientarea/ticket/ApplicationResources" key="edit-completed.summaryUpdated" /></li>
                            </logic:equal>
                            <logic:equal scope="request" name="annotationAdded" value="true">
                                <li><bean:message bundle="/clientarea/ticket/ApplicationResources" key="edit-completed.annotationAdded" /></li>
                            </logic:equal>
                            <logic:equal scope="request" name="nothingChanged" value="true">
                                <li><bean:message bundle="/clientarea/ticket/ApplicationResources" key="edit-completed.nothingChanged" /></li>
                            </logic:equal>
                        </ul>
                        <bean:message bundle="/clientarea/ticket/ApplicationResources" key="edit-completed.index.backTo" /><html:link action="/index"><bean:message bundle="/clientarea/ticket/ApplicationResources" key="edit-completed.index.link" /></html:link>
                    </skin:lightArea>
                </logic:notPresent>
            </skin:contentLine>
        </skin:content>
    </skin:skin>
</html:html>
