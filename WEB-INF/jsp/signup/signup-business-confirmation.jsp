<%--
  Copyright 2007 by AO Industries, Inc.,
  816 Azalea Rd, Mobile, Alabama, 36693, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" %>
<%@ page buffer="256kb" %>
<%@ page autoFlush="true" %>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/aoweb-struts-skin.tld" prefix="skin" %>

<TR>
    <TD><bean:message bundle="/signup/ApplicationResources" key="signup.required"/></TD>
    <TD><bean:message bundle="/signup/ApplicationResources" key="signupBusinessForm.businessName.prompt"/></TD>
    <TD><bean:write scope="session" name="signupBusinessForm" property="businessName"/></TD>
</TR>
<TR>
    <TD><bean:message bundle="/signup/ApplicationResources" key="signup.required"/></TD>
    <TD><bean:message bundle="/signup/ApplicationResources" key="signupBusinessForm.businessPhone.prompt"/></TD>
    <TD><bean:write scope="session" name="signupBusinessForm" property="businessPhone"/></TD>
</TR>
<TR>
    <TD><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired"/></TD>
    <TD><bean:message bundle="/signup/ApplicationResources" key="signupBusinessForm.businessFax.prompt"/></TD>
    <TD><bean:write scope="session" name="signupBusinessForm" property="businessFax"/></TD>
</TR>
<TR>
    <TD><bean:message bundle="/signup/ApplicationResources" key="signup.required"/></TD>
    <TD><bean:message bundle="/signup/ApplicationResources" key="signupBusinessForm.businessAddress1.prompt"/></TD>
    <TD><bean:write scope="session" name="signupBusinessForm" property="businessAddress1"/></TD>
</TR>
<logic:notEmpty scope="session" name="signupBusinessForm" property="businessAddress2">
    <TR>
        <TD><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired"/></TD>
        <TD><bean:message bundle="/signup/ApplicationResources" key="signupBusinessForm.businessAddress2.prompt"/></TD>
        <TD><bean:write scope="session" name="signupBusinessForm" property="businessAddress2"/></TD>
    </TR>
</logic:notEmpty>
<TR>
    <TD><bean:message bundle="/signup/ApplicationResources" key="signup.required"/></TD>
    <TD><bean:message bundle="/signup/ApplicationResources" key="signupBusinessForm.businessCity.prompt"/></TD>
    <TD><bean:write scope="session" name="signupBusinessForm" property="businessCity"/></TD>
</TR>
<TR>
    <TD><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired"/></TD>
    <TD><bean:message bundle="/signup/ApplicationResources" key="signupBusinessForm.businessState.prompt"/></TD>
    <TD><bean:write scope="session" name="signupBusinessForm" property="businessState"/></TD>
</TR>
<TR>
    <TD><bean:message bundle="/signup/ApplicationResources" key="signup.required"/></TD>
    <TD><bean:message bundle="/signup/ApplicationResources" key="signupBusinessForm.businessCountry.prompt"/></TD>
    <TD><bean:write scope="request" name="businessCountry"/></TD>
</TR>
<TR>
    <TD><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired"/></TD>
    <TD><bean:message bundle="/signup/ApplicationResources" key="signupBusinessForm.businessZip.prompt"/></TD>
    <TD><bean:write scope="session" name="signupBusinessForm" property="businessZip"/></TD>
</TR>
