<%-- aoweb-struts: Do not edit --%>
<%--
  Copyright 2007-2009 by AO Industries, Inc.,
  7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" buffer="256kb" autoFlush="true" %>
<%@ include file="/WEB-INF/jsp/taglibs.jsp" %>

<logic:notEmpty scope="request" name="backupOnsiteOption">
    <tr>
        <td><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired"/></td>
        <td><bean:message bundle="/signup/ApplicationResources" key="signupCustomizeManagementConfirmation.backupOnsite.prompt"/></td>
        <td><bean:write name="backupOnsiteOption"/></td>
    </tr>
</logic:notEmpty>
<logic:notEmpty scope="request" name="backupOffsiteOption">
    <tr>
        <td><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired"/></td>
        <td><bean:message bundle="/signup/ApplicationResources" key="signupCustomizeManagementConfirmation.backupOffsite.prompt"/></td>
        <td><bean:write name="backupOffsiteOption"/></td>
    </tr>
</logic:notEmpty>
<logic:notEmpty scope="request" name="backupDvdOption">
    <tr>
        <td><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired"/></td>
        <td><bean:message bundle="/signup/ApplicationResources" key="signupCustomizeManagementConfirmation.backupDvd.prompt"/></td>
        <td><bean:write name="backupDvdOption"/></td>
    </tr>
</logic:notEmpty>
<logic:notEmpty scope="request" name="distributionScanOption">
    <tr>
        <td><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired"/></td>
        <td><bean:message bundle="/signup/ApplicationResources" key="signupCustomizeManagementConfirmation.distributionScan.prompt"/></td>
        <td><bean:write name="distributionScanOption"/></td>
    </tr>
</logic:notEmpty>
<logic:notEmpty scope="request" name="failoverOption">
    <tr>
        <td><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired"/></td>
        <td><bean:message bundle="/signup/ApplicationResources" key="signupCustomizeManagementConfirmation.failover.prompt"/></td>
        <td><bean:write name="failoverOption"/></td>
    </tr>
</logic:notEmpty>
<tr>
    <td><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired"/></td>
    <td><bean:message bundle="/signup/ApplicationResources" key="signupCustomizeManagementConfirmation.totalMonthlyRate.prompt"/></td>
    <td>$<bean:write name="totalMonthlyRate"/></td>
</tr>
