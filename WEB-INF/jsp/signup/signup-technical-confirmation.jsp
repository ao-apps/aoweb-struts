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
    <td><bean:message bundle="/signup/ApplicationResources" key="signupTechnicalForm.baName.prompt" /></td>
    <td><bean:write scope="session" name="signupTechnicalForm" property="baName" /></td>
</tr>
<tr>
    <td><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired" /></td>
    <td><bean:message bundle="/signup/ApplicationResources" key="signupTechnicalForm.baTitle.prompt" /></td>
    <td><bean:write scope="session" name="signupTechnicalForm" property="baTitle" /></td>
</tr>
<tr>
    <td><bean:message bundle="/signup/ApplicationResources" key="signup.required" /></td>
    <td><bean:message bundle="/signup/ApplicationResources" key="signupTechnicalForm.baWorkPhone.prompt" /></td>
    <td><bean:write scope="session" name="signupTechnicalForm" property="baWorkPhone" /></td>
</tr>
<tr>
    <td><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired" /></td>
    <td><bean:message bundle="/signup/ApplicationResources" key="signupTechnicalForm.baCellPhone.prompt" /></td>
    <td><bean:write scope="session" name="signupTechnicalForm" property="baCellPhone" /></td>
</tr>
<tr>
    <td><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired" /></td>
    <td><bean:message bundle="/signup/ApplicationResources" key="signupTechnicalForm.baHomePhone.prompt" /></td>
    <td><bean:write scope="session" name="signupTechnicalForm" property="baHomePhone" /></td>
</tr>
<tr>
    <td><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired" /></td>
    <td><bean:message bundle="/signup/ApplicationResources" key="signupTechnicalForm.baFax.prompt" /></td>
    <td><bean:write scope="session" name="signupTechnicalForm" property="baFax" /></td>
</tr>
<tr>
    <td><bean:message bundle="/signup/ApplicationResources" key="signup.required" /></td>
    <td><bean:message bundle="/signup/ApplicationResources" key="signupTechnicalForm.baEmail.prompt" /></td>
    <td><bean:write scope="session" name="signupTechnicalForm" property="baEmail" /></td>
</tr>
<tr>
    <td><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired" /></td>
    <td><bean:message bundle="/signup/ApplicationResources" key="signupTechnicalForm.baAddress1.prompt" /></td>
    <td><bean:write scope="session" name="signupTechnicalForm" property="baAddress1" /></td>
</tr>
<logic:notEmpty scope="session" name="signupTechnicalForm" property="baAddress2">
    <tr>
        <td><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired" /></td>
        <td><bean:message bundle="/signup/ApplicationResources" key="signupTechnicalForm.baAddress2.prompt" /></td>
        <td><bean:write scope="session" name="signupTechnicalForm" property="baAddress2" /></td>
    </tr>
</logic:notEmpty>
<tr>
    <td><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired" /></td>
    <td><bean:message bundle="/signup/ApplicationResources" key="signupTechnicalForm.baCity.prompt" /></td>
    <td><bean:write scope="session" name="signupTechnicalForm" property="baCity" /></td>
</tr>
<tr>
    <td><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired" /></td>
    <td><bean:message bundle="/signup/ApplicationResources" key="signupTechnicalForm.baState.prompt" /></td>
    <td><bean:write scope="session" name="signupTechnicalForm" property="baState" /></td>
</tr>
<tr>
    <td><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired" /></td>
    <td><bean:message bundle="/signup/ApplicationResources" key="signupTechnicalForm.baCountry.prompt" /></td>
    <td><bean:write scope="request" name="baCountry" /></td>
</tr>
<tr>
    <td><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired" /></td>
    <td><bean:message bundle="/signup/ApplicationResources" key="signupTechnicalForm.baZip.prompt" /></td>
    <td><bean:write scope="session" name="signupTechnicalForm" property="baZip" /></td>
</tr>
<tr>
    <td><bean:message bundle="/signup/ApplicationResources" key="signup.required" /></td>
    <td><bean:message bundle="/signup/ApplicationResources" key="signupTechnicalForm.baUsername.prompt" /></td>
    <td><bean:write scope="session" name="signupTechnicalForm" property="baUsername" /></td>
</tr>
<tr>
    <td><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired" /></td>
    <td><bean:message bundle="/signup/ApplicationResources" key="signupTechnicalForm.baPassword.prompt" /></td>
    <td><bean:write scope="session" name="signupTechnicalForm" property="baPassword" /></td>
</tr>
