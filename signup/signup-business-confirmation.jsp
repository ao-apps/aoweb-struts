<%-- aoweb-struts: Do not edit --%>
<%--
  Copyright 2007-2011 by AO Industries, Inc.,
  7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" buffer="256kb" autoFlush="true" pageEncoding="UTF-8" %>
<%@include file="/_taglibs.jsp" %>

<fmt:bundle basename="com.aoindustries.website.signup.ApplicationResources">
    <tr>
        <td><fmt:message key="signup.required" /></td>
        <td><fmt:message key="signupBusinessForm.businessName.prompt" /></td>
        <td><ao:write scope="session" name="signupBusinessForm" property="businessName" /></td>
    </tr>
    <tr>
        <td><fmt:message key="signup.required" /></td>
        <td><fmt:message key="signupBusinessForm.businessPhone.prompt" /></td>
        <td><ao:write scope="session" name="signupBusinessForm" property="businessPhone" /></td>
    </tr>
    <tr>
        <td><fmt:message key="signup.notRequired" /></td>
        <td><fmt:message key="signupBusinessForm.businessFax.prompt" /></td>
        <td><ao:write scope="session" name="signupBusinessForm" property="businessFax" /></td>
    </tr>
    <tr>
        <td><fmt:message key="signup.required" /></td>
        <td><fmt:message key="signupBusinessForm.businessAddress1.prompt" /></td>
        <td><ao:write scope="session" name="signupBusinessForm" property="businessAddress1" /></td>
    </tr>
    <logic:notEmpty scope="session" name="signupBusinessForm" property="businessAddress2">
        <tr>
            <td><fmt:message key="signup.notRequired" /></td>
            <td><fmt:message key="signupBusinessForm.businessAddress2.prompt" /></td>
            <td><ao:write scope="session" name="signupBusinessForm" property="businessAddress2" /></td>
        </tr>
    </logic:notEmpty>
    <tr>
        <td><fmt:message key="signup.required" /></td>
        <td><fmt:message key="signupBusinessForm.businessCity.prompt" /></td>
        <td><ao:write scope="session" name="signupBusinessForm" property="businessCity" /></td>
    </tr>
    <tr>
        <td><fmt:message key="signup.notRequired" /></td>
        <td><fmt:message key="signupBusinessForm.businessState.prompt" /></td>
        <td><ao:write scope="session" name="signupBusinessForm" property="businessState" /></td>
    </tr>
    <tr>
        <td><fmt:message key="signup.required" /></td>
        <td><fmt:message key="signupBusinessForm.businessCountry.prompt" /></td>
        <td><ao:write scope="request" name="businessCountry" /></td>
    </tr>
    <tr>
        <td><fmt:message key="signup.notRequired" /></td>
        <td><fmt:message key="signupBusinessForm.businessZip.prompt" /></td>
        <td><ao:write scope="session" name="signupBusinessForm" property="businessZip" /></td>
    </tr>
</fmt:bundle>
