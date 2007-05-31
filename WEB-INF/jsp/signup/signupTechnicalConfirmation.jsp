<%--
  Copyright 2007 by AO Industries, Inc.,
  816 Azalea Rd, Mobile, Alabama, 36693, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld"       prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld"       prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld"      prefix="logic" %>
<%@ taglib uri="/WEB-INF/aoweb-struts-skin.tld" prefix="skin" %>

<TR>
    <TD><bean:message bundle="/signup/ApplicationResources" key="signup.required"/></TD>
    <TD><bean:message bundle="/signup/ApplicationResources" key="signupTechnicalForm.baName.prompt"/></TD>
    <TD><bean:write scope="session" name="signupTechnicalForm" property="baName"/></TD>
</TR>
<TR>
    <TD><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired"/></TD>
    <TD><bean:message bundle="/signup/ApplicationResources" key="signupTechnicalForm.baTitle.prompt"/></TD>
    <TD><bean:write scope="session" name="signupTechnicalForm" property="baTitle"/></TD>
</TR>
<TR>
    <TD><bean:message bundle="/signup/ApplicationResources" key="signup.required"/></TD>
    <TD><bean:message bundle="/signup/ApplicationResources" key="signupTechnicalForm.baWorkPhone.prompt"/></TD>
    <TD><bean:write scope="session" name="signupTechnicalForm" property="baWorkPhone"/></TD>
</TR>
<TR>
    <TD><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired"/></TD>
    <TD><bean:message bundle="/signup/ApplicationResources" key="signupTechnicalForm.baCellPhone.prompt"/></TD>
    <TD><bean:write scope="session" name="signupTechnicalForm" property="baCellPhone"/></TD>
</TR>
<TR>
    <TD><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired"/></TD>
    <TD><bean:message bundle="/signup/ApplicationResources" key="signupTechnicalForm.baHomePhone.prompt"/></TD>
    <TD><bean:write scope="session" name="signupTechnicalForm" property="baHomePhone"/></TD>
</TR>
<TR>
    <TD><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired"/></TD>
    <TD><bean:message bundle="/signup/ApplicationResources" key="signupTechnicalForm.baFax.prompt"/></TD>
    <TD><bean:write scope="session" name="signupTechnicalForm" property="baFax"/></TD>
</TR>
<TR>
    <TD><bean:message bundle="/signup/ApplicationResources" key="signup.required"/></TD>
    <TD><bean:message bundle="/signup/ApplicationResources" key="signupTechnicalForm.baEmail.prompt"/></TD>
    <TD><bean:write scope="session" name="signupTechnicalForm" property="baEmail"/></TD>
</TR>
<TR>
    <TD><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired"/></TD>
    <TD><bean:message bundle="/signup/ApplicationResources" key="signupTechnicalForm.baAddress1.prompt"/></TD>
    <TD><bean:write scope="session" name="signupTechnicalForm" property="baAddress1"/></TD>
</TR>
<logic:notEmpty scope="session" name="signupTechnicalForm" property="baAddress2">
    <TR>
        <TD><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired"/></TD>
        <TD><bean:message bundle="/signup/ApplicationResources" key="signupTechnicalForm.baAddress2.prompt"/></TD>
        <TD><bean:write scope="session" name="signupTechnicalForm" property="baAddress2"/></TD>
    </TR>
</logic:notEmpty>
<TR>
    <TD><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired"/></TD>
    <TD><bean:message bundle="/signup/ApplicationResources" key="signupTechnicalForm.baCity.prompt"/></TD>
    <TD><bean:write scope="session" name="signupTechnicalForm" property="baCity"/></TD>
</TR>
<TR>
    <TD><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired"/></TD>
    <TD><bean:message bundle="/signup/ApplicationResources" key="signupTechnicalForm.baState.prompt"/></TD>
    <TD><bean:write scope="session" name="signupTechnicalForm" property="baState"/></TD>
</TR>
<TR>
    <TD><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired"/></TD>
    <TD><bean:message bundle="/signup/ApplicationResources" key="signupTechnicalForm.baCountry.prompt"/></TD>
    <TD><bean:write scope="request" name="baCountry"/></TD>
</TR>
<TR>
    <TD><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired"/></TD>
    <TD><bean:message bundle="/signup/ApplicationResources" key="signupTechnicalForm.baZip.prompt"/></TD>
    <TD><bean:write scope="session" name="signupTechnicalForm" property="baZip"/></TD>
</TR>
<TR>
    <TD><bean:message bundle="/signup/ApplicationResources" key="signup.required"/></TD>
    <TD><bean:message bundle="/signup/ApplicationResources" key="signupTechnicalForm.baUsername.prompt"/></TD>
    <TD><bean:write scope="session" name="signupTechnicalForm" property="baUsername"/></TD>
</TR>
<TR>
    <TD><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired"/></TD>
    <TD><bean:message bundle="/signup/ApplicationResources" key="signupTechnicalForm.baPassword.prompt"/></TD>
    <TD><bean:write scope="session" name="signupTechnicalForm" property="baPassword"/></TD>
</TR>
