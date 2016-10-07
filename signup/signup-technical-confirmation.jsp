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
        <td><fmt:message key="signupTechnicalForm.baName.prompt" /></td>
        <td><ao:write scope="session" name="signupTechnicalForm" property="baName" /></td>
    </tr>
    <tr>
        <td><fmt:message key="signup.notRequired" /></td>
        <td><fmt:message key="signupTechnicalForm.baTitle.prompt" /></td>
        <td><ao:write scope="session" name="signupTechnicalForm" property="baTitle" /></td>
    </tr>
    <tr>
        <td><fmt:message key="signup.required" /></td>
        <td><fmt:message key="signupTechnicalForm.baWorkPhone.prompt" /></td>
        <td><ao:write scope="session" name="signupTechnicalForm" property="baWorkPhone" /></td>
    </tr>
    <tr>
        <td><fmt:message key="signup.notRequired" /></td>
        <td><fmt:message key="signupTechnicalForm.baCellPhone.prompt" /></td>
        <td><ao:write scope="session" name="signupTechnicalForm" property="baCellPhone" /></td>
    </tr>
    <tr>
        <td><fmt:message key="signup.notRequired" /></td>
        <td><fmt:message key="signupTechnicalForm.baHomePhone.prompt" /></td>
        <td><ao:write scope="session" name="signupTechnicalForm" property="baHomePhone" /></td>
    </tr>
    <tr>
        <td><fmt:message key="signup.notRequired" /></td>
        <td><fmt:message key="signupTechnicalForm.baFax.prompt" /></td>
        <td><ao:write scope="session" name="signupTechnicalForm" property="baFax" /></td>
    </tr>
    <tr>
        <td><fmt:message key="signup.required" /></td>
        <td><fmt:message key="signupTechnicalForm.baEmail.prompt" /></td>
        <td><ao:write scope="session" name="signupTechnicalForm" property="baEmail" /></td>
    </tr>
    <tr>
        <td><fmt:message key="signup.notRequired" /></td>
        <td><fmt:message key="signupTechnicalForm.baAddress1.prompt" /></td>
        <td><ao:write scope="session" name="signupTechnicalForm" property="baAddress1" /></td>
    </tr>
    <logic:notEmpty scope="session" name="signupTechnicalForm" property="baAddress2">
        <tr>
            <td><fmt:message key="signup.notRequired" /></td>
            <td><fmt:message key="signupTechnicalForm.baAddress2.prompt" /></td>
            <td><ao:write scope="session" name="signupTechnicalForm" property="baAddress2" /></td>
        </tr>
    </logic:notEmpty>
    <tr>
        <td><fmt:message key="signup.notRequired" /></td>
        <td><fmt:message key="signupTechnicalForm.baCity.prompt" /></td>
        <td><ao:write scope="session" name="signupTechnicalForm" property="baCity" /></td>
    </tr>
    <tr>
        <td><fmt:message key="signup.notRequired" /></td>
        <td><fmt:message key="signupTechnicalForm.baState.prompt" /></td>
        <td><ao:write scope="session" name="signupTechnicalForm" property="baState" /></td>
    </tr>
    <tr>
        <td><fmt:message key="signup.notRequired" /></td>
        <td><fmt:message key="signupTechnicalForm.baCountry.prompt" /></td>
        <td><ao:write scope="request" name="baCountry" /></td>
    </tr>
    <tr>
        <td><fmt:message key="signup.notRequired" /></td>
        <td><fmt:message key="signupTechnicalForm.baZip.prompt" /></td>
        <td><ao:write scope="session" name="signupTechnicalForm" property="baZip" /></td>
    </tr>
    <tr>
        <td><fmt:message key="signup.required" /></td>
        <td><fmt:message key="signupTechnicalForm.baUsername.prompt" /></td>
        <td><ao:write scope="session" name="signupTechnicalForm" property="baUsername" /></td>
    </tr>
    <tr>
        <td><fmt:message key="signup.notRequired" /></td>
        <td><fmt:message key="signupTechnicalForm.baPassword.prompt" /></td>
        <td><ao:write scope="session" name="signupTechnicalForm" property="baPassword" /></td>
    </tr>
</fmt:bundle>
