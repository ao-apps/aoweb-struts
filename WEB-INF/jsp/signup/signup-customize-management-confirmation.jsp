<%-- aoweb-struts: Do not edit --%>
<%--
  Copyright 2007-2009 by AO Industries, Inc.,
  7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" buffer="256kb" autoFlush="true" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/taglibs.jsp" %>

<fmt:bundle basename="com.aoindustries.website.signup.ApplicationResources">
    <logic:notEmpty scope="request" name="backupOnsiteOption">
        <tr>
            <td><fmt:message key="signup.notRequired" /></td>
            <td><fmt:message key="signupCustomizeManagementConfirmation.backupOnsite.prompt" /></td>
            <td><ao:write name="backupOnsiteOption" /></td>
        </tr>
    </logic:notEmpty>
    <logic:notEmpty scope="request" name="backupOffsiteOption">
        <tr>
            <td><fmt:message key="signup.notRequired" /></td>
            <td><fmt:message key="signupCustomizeManagementConfirmation.backupOffsite.prompt" /></td>
            <td><ao:write name="backupOffsiteOption" /></td>
        </tr>
    </logic:notEmpty>
    <logic:notEmpty scope="request" name="backupDvdOption">
        <tr>
            <td><fmt:message key="signup.notRequired" /></td>
            <td><fmt:message key="signupCustomizeManagementConfirmation.backupDvd.prompt" /></td>
            <td><ao:write name="backupDvdOption" /></td>
        </tr>
    </logic:notEmpty>
    <logic:notEmpty scope="request" name="distributionScanOption">
        <tr>
            <td><fmt:message key="signup.notRequired" /></td>
            <td><fmt:message key="signupCustomizeManagementConfirmation.distributionScan.prompt" /></td>
            <td><ao:write name="distributionScanOption" /></td>
        </tr>
    </logic:notEmpty>
    <logic:notEmpty scope="request" name="failoverOption">
        <tr>
            <td><fmt:message key="signup.notRequired" /></td>
            <td><fmt:message key="signupCustomizeManagementConfirmation.failover.prompt" /></td>
            <td><ao:write name="failoverOption" /></td>
        </tr>
    </logic:notEmpty>
    <tr>
        <td><fmt:message key="signup.notRequired" /></td>
        <td><fmt:message key="signupCustomizeManagementConfirmation.totalMonthlyRate.prompt" /></td>
        <td>$<ao:write name="totalMonthlyRate" /></td>
    </tr>
</fmt:bundle>
