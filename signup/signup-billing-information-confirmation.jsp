<%-- aoweb-struts: Do not edit --%>
<%--
  Copyright 2007-2009 by AO Industries, Inc.,
  7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@include file="/_taglibs.jsp" %>

<fmt:bundle basename="com.aoindustries.website.signup.ApplicationResources">
    <tr>
        <td><fmt:message key="signup.required" /></td>
        <td><fmt:message key="signupBillingInformationForm.billingContact.prompt" /></td>
        <td><ao:write scope="session" name="signupBillingInformationForm" property="billingContact" /></td>
    </tr>
    <tr>
        <td><fmt:message key="signup.required" /></td>
        <td><fmt:message key="signupBillingInformationForm.billingEmail.prompt" /></td>
        <td><ao:write scope="session" name="signupBillingInformationForm" property="billingEmail" /></td>
    </tr>
    <tr>
        <td><fmt:message key="signup.required" /></td>
        <td><fmt:message key="signupBillingInformationForm.billingCardholderName.prompt" /></td>
        <td><ao:write scope="session" name="signupBillingInformationForm" property="billingCardholderName" /></td>
    </tr>
    <tr>
        <td><fmt:message key="signup.required" /></td>
        <td><fmt:message key="signupBillingInformationForm.billingCardNumber.prompt" /></td>
        <td><ao:write scope="request" name="billingCardNumber" /></td>
    </tr>
    <tr>
        <td><fmt:message key="signup.required" /></td>
        <td><fmt:message key="signupBillingInformationForm.billingExpirationDate.prompt" /></td>
        <td><fmt:message key="signupBillingInformationForm.billingExpirationDate.hidden" /></td>
    </tr>
    <tr>
        <td><fmt:message key="signup.required" /></td>
        <td><fmt:message key="signupBillingInformationForm.billingStreetAddress.prompt" /></td>
        <td><ao:write scope="session" name="signupBillingInformationForm" property="billingStreetAddress" /></td>
    </tr>
    <tr>
        <td><fmt:message key="signup.required" /></td>
        <td><fmt:message key="signupBillingInformationForm.billingCity.prompt" /></td>
        <td><ao:write scope="session" name="signupBillingInformationForm" property="billingCity" /></td>
    </tr>
    <tr>
        <td><fmt:message key="signup.required" /></td>
        <td><fmt:message key="signupBillingInformationForm.billingState.prompt" /></td>
        <td><ao:write scope="session" name="signupBillingInformationForm" property="billingState" /></td>
    </tr>
    <tr>
        <td><fmt:message key="signup.required" /></td>
        <td><fmt:message key="signupBillingInformationForm.billingZip.prompt" /></td>
        <td><ao:write scope="session" name="signupBillingInformationForm" property="billingZip" /></td>
    </tr>
    <tr>
        <td><fmt:message key="signup.notRequired" /></td>
        <td><fmt:message key="signupBillingInformationForm.billingUseMonthly.prompt" /></td>
        <td>
            <logic:equal scope="session" name="signupBillingInformationForm" property="billingUseMonthly" value="true">
                <fmt:message key="signupBillingInformationForm.billingUseMonthly.yes" />
            </logic:equal>
            <logic:notEqual scope="session" name="signupBillingInformationForm" property="billingUseMonthly" value="true">
                <fmt:message key="signupBillingInformationForm.billingUseMonthly.no" />
            </logic:notEqual>
        </td>
    </tr>
    <tr>
        <td><fmt:message key="signup.notRequired" /></td>
        <td><fmt:message key="signupBillingInformationForm.billingPayOneYear.prompt" /></td>
        <td>
            <logic:equal scope="session" name="signupBillingInformationForm" property="billingPayOneYear" value="true">
                <fmt:message key="signupBillingInformationForm.billingPayOneYear.yes" />
            </logic:equal>
            <logic:notEqual scope="session" name="signupBillingInformationForm" property="billingPayOneYear" value="true">
                <fmt:message key="signupBillingInformationForm.billingPayOneYear.no" />
            </logic:notEqual>
        </td>
    </tr>
</fmt:bundle>
