<%-- aoweb-struts: Do not edit --%>
<%--
  Copyright 2007-2009 by AO Industries, Inc.,
  7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" buffer="256kb" autoFlush="true" %>
<%@ include file="/WEB-INF/jsp/taglibs.jsp" %>

<tr>
    <td><bean:message bundle="/signup/ApplicationResources" key="signup.required"/></td>
    <td><bean:message bundle="/signup/ApplicationResources" key="signupBusinessForm.businessName.prompt"/></td>
    <td><bean:write scope="session" name="signupBusinessForm" property="businessName"/></td>
</tr>
<tr>
    <td><bean:message bundle="/signup/ApplicationResources" key="signup.required"/></td>
    <td><bean:message bundle="/signup/ApplicationResources" key="signupBusinessForm.businessPhone.prompt"/></td>
    <td><bean:write scope="session" name="signupBusinessForm" property="businessPhone"/></td>
</tr>
<tr>
    <td><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired"/></td>
    <td><bean:message bundle="/signup/ApplicationResources" key="signupBusinessForm.businessFax.prompt"/></td>
    <td><bean:write scope="session" name="signupBusinessForm" property="businessFax"/></td>
</tr>
<tr>
    <td><bean:message bundle="/signup/ApplicationResources" key="signup.required"/></td>
    <td><bean:message bundle="/signup/ApplicationResources" key="signupBusinessForm.businessAddress1.prompt"/></td>
    <td><bean:write scope="session" name="signupBusinessForm" property="businessAddress1"/></td>
</tr>
<logic:notEmpty scope="session" name="signupBusinessForm" property="businessAddress2">
    <tr>
        <td><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired"/></td>
        <td><bean:message bundle="/signup/ApplicationResources" key="signupBusinessForm.businessAddress2.prompt"/></td>
        <td><bean:write scope="session" name="signupBusinessForm" property="businessAddress2"/></td>
    </tr>
</logic:notEmpty>
<tr>
    <td><bean:message bundle="/signup/ApplicationResources" key="signup.required"/></td>
    <td><bean:message bundle="/signup/ApplicationResources" key="signupBusinessForm.businessCity.prompt"/></td>
    <td><bean:write scope="session" name="signupBusinessForm" property="businessCity"/></td>
</tr>
<tr>
    <td><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired"/></td>
    <td><bean:message bundle="/signup/ApplicationResources" key="signupBusinessForm.businessState.prompt"/></td>
    <td><bean:write scope="session" name="signupBusinessForm" property="businessState"/></td>
</tr>
<tr>
    <td><bean:message bundle="/signup/ApplicationResources" key="signup.required"/></td>
    <td><bean:message bundle="/signup/ApplicationResources" key="signupBusinessForm.businessCountry.prompt"/></td>
    <td><bean:write scope="request" name="businessCountry"/></td>
</tr>
<tr>
    <td><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired"/></td>
    <td><bean:message bundle="/signup/ApplicationResources" key="signupBusinessForm.businessZip.prompt"/></td>
    <td><bean:write scope="session" name="signupBusinessForm" property="businessZip"/></td>
</tr>
