<%-- aoweb-struts: Do not edit --%>
<%--
  Copyright 2007-2009 by AO Industries, Inc.,
  7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" buffer="256kb" autoFlush="true" %>
<%@ include file="/WEB-INF/jsp/taglibs.jsp" %>

<logic:notEmpty scope="request" name="backupOnsiteOption">
    <TR>
        <TD><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired"/></TD>
        <TD><bean:message bundle="/signup/ApplicationResources" key="signupCustomizeManagementConfirmation.backupOnsite.prompt"/></TD>
        <TD><bean:write name="backupOnsiteOption"/></TD>
    </TR>
</logic:notEmpty>
<logic:notEmpty scope="request" name="backupOffsiteOption">
    <TR>
        <TD><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired"/></TD>
        <TD><bean:message bundle="/signup/ApplicationResources" key="signupCustomizeManagementConfirmation.backupOffsite.prompt"/></TD>
        <TD><bean:write name="backupOffsiteOption"/></TD>
    </TR>
</logic:notEmpty>
<logic:notEmpty scope="request" name="backupDvdOption">
    <TR>
        <TD><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired"/></TD>
        <TD><bean:message bundle="/signup/ApplicationResources" key="signupCustomizeManagementConfirmation.backupDvd.prompt"/></TD>
        <TD><bean:write name="backupDvdOption"/></TD>
    </TR>
</logic:notEmpty>
<logic:notEmpty scope="request" name="distributionScanOption">
    <TR>
        <TD><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired"/></TD>
        <TD><bean:message bundle="/signup/ApplicationResources" key="signupCustomizeManagementConfirmation.distributionScan.prompt"/></TD>
        <TD><bean:write name="distributionScanOption"/></TD>
    </TR>
</logic:notEmpty>
<logic:notEmpty scope="request" name="failoverOption">
    <TR>
        <TD><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired"/></TD>
        <TD><bean:message bundle="/signup/ApplicationResources" key="signupCustomizeManagementConfirmation.failover.prompt"/></TD>
        <TD><bean:write name="failoverOption"/></TD>
    </TR>
</logic:notEmpty>
<TR>
    <TD><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired"/></TD>
    <TD><bean:message bundle="/signup/ApplicationResources" key="signupCustomizeManagementConfirmation.totalMonthlyRate.prompt"/></TD>
    <TD>$<bean:write name="totalMonthlyRate"/></TD>
</TR>
