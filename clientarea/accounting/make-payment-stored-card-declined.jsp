<%-- aoweb-struts: Do not edit --%>
<%--
  Copyright 2007-2010 by AO Industries, Inc.,
  7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" buffer="256kb" autoFlush="true" pageEncoding="UTF-8" %>
<%@include file="/_taglibs.jsp" %>

<skin:setContentType />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html:html lang="true" xhtml="true">
    <%@include file="add-parents.jsp" %>
    <fmt:bundle basename="com.aoindustries.website.clientarea.accounting.ApplicationResources">
        <skin:path>/clientarea/accounting/make-payment-select-card.do?accounting=${makePaymentStoredCardForm.accounting}</skin:path>
        <logic:equal name="siteSettings" property="brand.aowebStrutsNoindex" value="true"><skin:meta name="ROBOTS">NOINDEX</skin:meta></logic:equal>
        <skin:title><fmt:message key="makePayment.title" /></skin:title>
        <skin:navImageAlt><fmt:message key="makePayment.navImageAlt" /></skin:navImageAlt>
        <skin:keywords><fmt:message key="makePayment.keywords" /></skin:keywords>
        <skin:description><fmt:message key="makePayment.description" /></skin:description>
        <skin:skin>
            <skin:content width="600">
                <skin:contentTitle><fmt:message key="makePayment.title" /></skin:contentTitle>
                <skin:contentHorizontalDivider />
                <skin:contentLine>
                    <skin:lightArea>
                        <fmt:message key="makePaymentStoredCardDeclined.declined.header" />
                        <hr />
                        <fmt:message key="makePaymentStoredCardDeclined.declined.description">
                            <fmt:param><c:out value="${declineReason}" /></fmt:param>
                        </fmt:message>
                    </skin:lightArea><br />
                    <%@include file="make-payment-select-card-shared.jsp" %>
                </skin:contentLine>
            </skin:content>
        </skin:skin>
    </fmt:bundle>
</html:html>
