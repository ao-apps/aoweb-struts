<%-- aoweb-struts: Do not edit --%>
<%--
  Copyright 2003-2011 by AO Industries, Inc.,
  7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" buffer="256kb" autoFlush="true" pageEncoding="UTF-8" %>
<%@include file="/_taglibs.jsp" %>

<skin:setContentType />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html:html lang="true" xhtml="true">
    <%@include file="add-parents.jsp" %>
    <fmt:bundle basename="com.aoindustries.website.clientarea.control.ApplicationResources">
        <skin:path>/clientarea/control/business/cancel-feedback.do?business=${business.accounting}</skin:path>
        <logic:equal name="siteSettings" property="brand.aowebStrutsNoindex" value="true"><skin:meta name="ROBOTS">NOINDEX</skin:meta></logic:equal>
        <skin:title><fmt:message key="business.cancel.title" /></skin:title>
        <skin:navImageAlt><fmt:message key="business.cancel.navImageAlt" /></skin:navImageAlt>
        <skin:keywords><fmt:message key="business.cancel.keywords" /></skin:keywords>
        <skin:description><fmt:message key="business.cancel.description" /></skin:description>
        <skin:skin>
            <skin:content width="600">
                <skin:contentTitle><fmt:message key="business.cancel.title" /></skin:contentTitle>
                <skin:contentHorizontalDivider />
                <skin:contentLine>
                    <logic:present scope="request" name="permissionDenied">
                        <%@include file="../../../_permission-denied.jsp" %>
                    </logic:present>
                    <logic:notPresent scope="request" name="permissionDenied">
                        <skin:lightArea>
                            <fmt:message key="business.cancel-feedback.prompt">
                                <fmt:param><c:out value="${business.accounting}" /></fmt:param>
                            </fmt:message>
                            <html:form action="/business/cancel-feedback-completed">
                                <div>
                                    <html:hidden property="business" />
                                    <html:textarea property="reason" rows="16" cols="80" />
                                </div>
                                <div style="text-align:center; margin-top:1em;">
                                    <ao:input type="submit"><fmt:message key="business.cancel-feedback.submit.label" /></ao:input>
                                </div>
                            </html:form>
                        </skin:lightArea>
                    </logic:notPresent>
                </skin:contentLine>
            </skin:content>
        </skin:skin>
    </fmt:bundle>
</html:html>
