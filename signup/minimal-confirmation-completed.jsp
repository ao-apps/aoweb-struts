<%-- aoweb-struts: Do not edit --%>
<%--
  Copyright 2009 by AO Industries, Inc.,
  7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@include file="/_taglibs.jsp" %>

<skin:lightArea>
    <fmt:bundle basename="com.aoindustries.website.signup.ApplicationResources">
        <table cellpadding="0" cellspacing="0">
            <tr><td style="white-space:nowrap" colspan="3">
                <fmt:message key="${statusKey}">
                    <fmt:param><c:out value="${pkey}" /></fmt:param>
                </fmt:message><br />
                <br />
                <logic:iterate scope="request" name="successAddresses" id="successAddress">
                    <fmt:message key="serverConfirmationCompleted.successAddress">
                        <fmt:param><c:out value="${successAddress}" /></fmt:param>
                    </fmt:message><br />
                </logic:iterate>
                <logic:iterate scope="request" name="failureAddresses" id="failureAddress">
                    <fmt:message key="serverConfirmationCompleted.failureAddress">
                        <fmt:param><c:out value="${failureAddress}" /></fmt:param>
                    </fmt:message><br />
                </logic:iterate>
                <br />
                <fmt:message key="serverConfirmationCompleted.belowIsSummary" /><br />
                <hr />
            </td></tr>
            <tr><th colspan="3"><fmt:message key="steps.selectPackage.label" /></th></tr>
            <%@include file="signup-select-package-confirmation.jsp" %>
            <tr><td colspan="3">&#160;</td></tr>
            <tr><th colspan="3"><fmt:message key="steps.businessInfo.label" /></th></tr>
            <%@include file="signup-business-confirmation.jsp" %>
            <tr><td colspan="3">&#160;</td></tr>
            <tr><th colspan="3"><fmt:message key="steps.technicalInfo.label" /></th></tr>
            <%@include file="signup-technical-confirmation.jsp" %>
            <tr><td colspan="3">&#160;</td></tr>
            <tr><th colspan="3"><fmt:message key="steps.billingInformation.label" /></th></tr>
            <%@include file="signup-billing-information-confirmation.jsp" %>
        </table>
    </fmt:bundle>
</skin:lightArea>
