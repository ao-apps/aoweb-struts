<%--
  Copyright 2007-2008 by AO Industries, Inc.,
  7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
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
    <TD><bean:message bundle="/signup/ApplicationResources" key="signupBillingInformationForm.billingContact.prompt"/></TD>
    <TD><bean:write scope="session" name="signupBillingInformationForm" property="billingContact"/></TD>
</TR>
<TR>
    <TD><bean:message bundle="/signup/ApplicationResources" key="signup.required"/></TD>
    <TD><bean:message bundle="/signup/ApplicationResources" key="signupBillingInformationForm.billingEmail.prompt"/></TD>
    <TD><bean:write scope="session" name="signupBillingInformationForm" property="billingEmail"/></TD>
</TR>
<TR>
    <TD><bean:message bundle="/signup/ApplicationResources" key="signup.required"/></TD>
    <TD><bean:message bundle="/signup/ApplicationResources" key="signupBillingInformationForm.billingCardholderName.prompt"/></TD>
    <TD><bean:write scope="session" name="signupBillingInformationForm" property="billingCardholderName"/></TD>
</TR>
<TR>
    <TD><bean:message bundle="/signup/ApplicationResources" key="signup.required"/></TD>
    <TD><bean:message bundle="/signup/ApplicationResources" key="signupBillingInformationForm.billingCardNumber.prompt"/></TD>
    <TD><bean:write scope="request" name="billingCardNumber"/></TD>
</TR>
<TR>
    <TD><bean:message bundle="/signup/ApplicationResources" key="signup.required"/></TD>
    <TD><bean:message bundle="/signup/ApplicationResources" key="signupBillingInformationForm.billingExpirationDate.prompt"/></TD>
    <TD><bean:message bundle="/signup/ApplicationResources" key="signupBillingInformationForm.billingExpirationDate.hidden"/></TD>
</TR>
<TR>
    <TD><bean:message bundle="/signup/ApplicationResources" key="signup.required"/></TD>
    <TD><bean:message bundle="/signup/ApplicationResources" key="signupBillingInformationForm.billingStreetAddress.prompt"/></TD>
    <TD><bean:write scope="session" name="signupBillingInformationForm" property="billingStreetAddress"/></TD>
</TR>
<TR>
    <TD><bean:message bundle="/signup/ApplicationResources" key="signup.required"/></TD>
    <TD><bean:message bundle="/signup/ApplicationResources" key="signupBillingInformationForm.billingCity.prompt"/></TD>
    <TD><bean:write scope="session" name="signupBillingInformationForm" property="billingCity"/></TD>
</TR>
<TR>
    <TD><bean:message bundle="/signup/ApplicationResources" key="signup.required"/></TD>
    <TD><bean:message bundle="/signup/ApplicationResources" key="signupBillingInformationForm.billingState.prompt"/></TD>
    <TD><bean:write scope="session" name="signupBillingInformationForm" property="billingState"/></TD>
</TR>
<TR>
    <TD><bean:message bundle="/signup/ApplicationResources" key="signup.required"/></TD>
    <TD><bean:message bundle="/signup/ApplicationResources" key="signupBillingInformationForm.billingZip.prompt"/></TD>
    <TD><bean:write scope="session" name="signupBillingInformationForm" property="billingZip"/></TD>
</TR>
<TR>
    <TD><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired"/></TD>
    <TD><bean:message bundle="/signup/ApplicationResources" key="signupBillingInformationForm.billingUseMonthly.prompt"/></TD>
    <TD>
        <logic:equal scope="session" name="signupBillingInformationForm" property="billingUseMonthly" value="true">
            <bean:message bundle="/signup/ApplicationResources" key="signupBillingInformationForm.billingUseMonthly.yes"/>
        </logic:equal>
        <logic:notEqual scope="session" name="signupBillingInformationForm" property="billingUseMonthly" value="true">
            <bean:message bundle="/signup/ApplicationResources" key="signupBillingInformationForm.billingUseMonthly.no"/>
        </logic:notEqual>
    </TD>
</TR>
<TR>
    <TD><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired"/></TD>
    <TD><bean:message bundle="/signup/ApplicationResources" key="signupBillingInformationForm.billingPayOneYear.prompt"/></TD>
    <TD>
        <logic:equal scope="session" name="signupBillingInformationForm" property="billingPayOneYear" value="true">
            <bean:message bundle="/signup/ApplicationResources" key="signupBillingInformationForm.billingPayOneYear.yes"/>
        </logic:equal>
        <logic:notEqual scope="session" name="signupBillingInformationForm" property="billingPayOneYear" value="true">
            <bean:message bundle="/signup/ApplicationResources" key="signupBillingInformationForm.billingPayOneYear.no"/>
        </logic:notEqual>
    </TD>
</TR>
