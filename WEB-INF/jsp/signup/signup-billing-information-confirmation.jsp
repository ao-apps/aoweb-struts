<%-- aoweb-struts: Do not edit --%>
<%--
  Copyright 2007-2009 by AO Industries, Inc.,
  7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" buffer="256kb" autoFlush="true" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/taglibs.jsp" %>

<tr>
    <td><bean:message bundle="/signup/ApplicationResources" key="signup.required" /></td>
    <td><bean:message bundle="/signup/ApplicationResources" key="signupBillingInformationForm.billingContact.prompt" /></td>
    <td><bean:write scope="session" name="signupBillingInformationForm" property="billingContact" /></td>
</tr>
<tr>
    <td><bean:message bundle="/signup/ApplicationResources" key="signup.required" /></td>
    <td><bean:message bundle="/signup/ApplicationResources" key="signupBillingInformationForm.billingEmail.prompt" /></td>
    <td><bean:write scope="session" name="signupBillingInformationForm" property="billingEmail" /></td>
</tr>
<tr>
    <td><bean:message bundle="/signup/ApplicationResources" key="signup.required" /></td>
    <td><bean:message bundle="/signup/ApplicationResources" key="signupBillingInformationForm.billingCardholderName.prompt" /></td>
    <td><bean:write scope="session" name="signupBillingInformationForm" property="billingCardholderName" /></td>
</tr>
<tr>
    <td><bean:message bundle="/signup/ApplicationResources" key="signup.required" /></td>
    <td><bean:message bundle="/signup/ApplicationResources" key="signupBillingInformationForm.billingCardNumber.prompt" /></td>
    <td><bean:write scope="request" name="billingCardNumber" /></td>
</tr>
<tr>
    <td><bean:message bundle="/signup/ApplicationResources" key="signup.required" /></td>
    <td><bean:message bundle="/signup/ApplicationResources" key="signupBillingInformationForm.billingExpirationDate.prompt" /></td>
    <td><bean:message bundle="/signup/ApplicationResources" key="signupBillingInformationForm.billingExpirationDate.hidden" /></td>
</tr>
<tr>
    <td><bean:message bundle="/signup/ApplicationResources" key="signup.required" /></td>
    <td><bean:message bundle="/signup/ApplicationResources" key="signupBillingInformationForm.billingStreetAddress.prompt" /></td>
    <td><bean:write scope="session" name="signupBillingInformationForm" property="billingStreetAddress" /></td>
</tr>
<tr>
    <td><bean:message bundle="/signup/ApplicationResources" key="signup.required" /></td>
    <td><bean:message bundle="/signup/ApplicationResources" key="signupBillingInformationForm.billingCity.prompt" /></td>
    <td><bean:write scope="session" name="signupBillingInformationForm" property="billingCity" /></td>
</tr>
<tr>
    <td><bean:message bundle="/signup/ApplicationResources" key="signup.required" /></td>
    <td><bean:message bundle="/signup/ApplicationResources" key="signupBillingInformationForm.billingState.prompt" /></td>
    <td><bean:write scope="session" name="signupBillingInformationForm" property="billingState" /></td>
</tr>
<tr>
    <td><bean:message bundle="/signup/ApplicationResources" key="signup.required" /></td>
    <td><bean:message bundle="/signup/ApplicationResources" key="signupBillingInformationForm.billingZip.prompt" /></td>
    <td><bean:write scope="session" name="signupBillingInformationForm" property="billingZip" /></td>
</tr>
<tr>
    <td><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired" /></td>
    <td><bean:message bundle="/signup/ApplicationResources" key="signupBillingInformationForm.billingUseMonthly.prompt" /></td>
    <td>
        <logic:equal scope="session" name="signupBillingInformationForm" property="billingUseMonthly" value="true">
            <bean:message bundle="/signup/ApplicationResources" key="signupBillingInformationForm.billingUseMonthly.yes" />
        </logic:equal>
        <logic:notEqual scope="session" name="signupBillingInformationForm" property="billingUseMonthly" value="true">
            <bean:message bundle="/signup/ApplicationResources" key="signupBillingInformationForm.billingUseMonthly.no" />
        </logic:notEqual>
    </td>
</tr>
<tr>
    <td><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired" /></td>
    <td><bean:message bundle="/signup/ApplicationResources" key="signupBillingInformationForm.billingPayOneYear.prompt" /></td>
    <td>
        <logic:equal scope="session" name="signupBillingInformationForm" property="billingPayOneYear" value="true">
            <bean:message bundle="/signup/ApplicationResources" key="signupBillingInformationForm.billingPayOneYear.yes" />
        </logic:equal>
        <logic:notEqual scope="session" name="signupBillingInformationForm" property="billingPayOneYear" value="true">
            <bean:message bundle="/signup/ApplicationResources" key="signupBillingInformationForm.billingPayOneYear.no" />
        </logic:notEqual>
    </td>
</tr>
