<%-- aoweb-struts: Do not edit --%>
<%--
  Copyright 2007-2009 by AO Industries, Inc.,
  7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" buffer="256kb" autoFlush="true" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/taglibs.jsp" %>

<skin:setContentType />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html:html lang="true" xhtml="true">
    <fmt:bundle basename="com.aoindustries.website.clientarea.accounting.ApplicationResources">
        <skin:path>/clientarea/accounting/delete-credit-card-completed.do?pkey=<%= request.getParameter("pkey") %></skin:path>
        <logic:equal name="siteSettings" property="brand.aowebStrutsNoindex" value="true"><skin:meta name="ROBOTS">NOINDEX</skin:meta></logic:equal>
        <skin:title><fmt:message key="deleteCreditCardCompleted.title" /></skin:title>
        <skin:navImageAlt><fmt:message key="deleteCreditCardCompleted.navImageAlt" /></skin:navImageAlt>
        <skin:keywords><fmt:message key="deleteCreditCardCompleted.keywords" /></skin:keywords>
        <skin:description><fmt:message key="deleteCreditCardCompleted.description" /></skin:description>
        <%@ include file="add-parents.jsp" %>
        <skin:addParent useEncryption="true" path="/clientarea/accounting/credit-card-manager.do">
            <skin:title><fmt:message key="creditCardManager.title" /></skin:title>
            <skin:navImageAlt><fmt:message key="creditCardManager.navImageAlt" /></skin:navImageAlt>
        </skin:addParent>
        <%@ include file="add-siblings.jsp" %>
        <skin:skin>
            <skin:content width="600">
                <skin:contentTitle><fmt:message key="deleteCreditCardCompleted.title" /></skin:contentTitle>
                <skin:contentHorizontalDivider />
                <skin:contentLine>
                    <logic:present scope="request" name="permissionDenied">
                        <%@ include file="../../permission-denied.jsp" %>
                    </logic:present>
                    <logic:notPresent scope="request" name="permissionDenied">
                        <skin:lightArea>
                            <fmt:message key="deleteCreditCardCompleted.successMessage.title" />
                            <hr />
                            <fmt:message key="deleteCreditCardCompleted.successMessage.text">
                                <fmt:param><c:out value="${fn:toLowerCase(cardNumber)}" /></fmt:param>
                            </fmt:message><br />
                            <br />
                            <html:link action="/credit-card-manager"><fmt:message key="deleteCreditCardCompleted.creditCardManager.link" /></html:link>
                        </skin:lightArea>
                    </logic:notPresent>
                </skin:contentLine>
            </skin:content>
        </skin:skin>
    </fmt:bundle>
</html:html>
