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
    <skin:path>/clientarea/ticket/create.do</skin:path>
    <skin:title><bean:message bundle="/clientarea/ticket/ApplicationResources" key="create.title"/></skin:title>
    <skin:navImageAlt><bean:message bundle="/clientarea/ticket/ApplicationResources" key="create.navImageAlt"/></skin:navImageAlt>
    <skin:keywords><bean:message bundle="/clientarea/ticket/ApplicationResources" key="create.keywords"/></skin:keywords>
    <skin:description><bean:message bundle="/clientarea/ticket/ApplicationResources" key="create.description"/></skin:description>
    <jsp:include page="add-parents.jsp"/>
    <jsp:include page="add-siblings.jsp"/>
    <skin:skin>
        <skin:content>
            <skin:contentTitle><bean:message bundle="/clientarea/ticket/ApplicationResources" key="create.title"/></skin:contentTitle>
            <skin:contentHorizontalDivider/>
            <skin:contentLine>
                <logic:present scope="request" name="permissionDenied">
                    <%@ include file="../../permission-denied.jsp" %>
                </logic:present>
                <logic:notPresent scope="request" name="permissionDenied">
                    <skin:lightArea>
                        <bean:message bundle="/clientarea/ticket/ApplicationResources" key="create-completed.message"/>
                        <html:link action="/edit" paramId="pkey" paramScope="request" paramName="pkey"><bean:write scope="request" name="pkey"/></html:link>
                    </skin:lightArea>
                </logic:notPresent>
            </skin:contentLine>
        </skin:content>
    </skin:skin>
</html:html>
