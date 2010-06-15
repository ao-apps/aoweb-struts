<%-- aoweb-struts: Do not edit --%>
<%--
  Copyright 2007-2010 by AO Industries, Inc.,
  7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" buffer="256kb" autoFlush="true" pageEncoding="UTF-8" %>
<%@include file="/_taglibs.jsp" %>

<fmt:bundle basename="com.aoindustries.website.signup.ApplicationResources">
    <logic:notEmpty scope="request" name="powerOption">
        <tr>
            <td><fmt:message key="signup.notRequired" /></td>
            <td><fmt:message key="signupCustomizeServerConfirmation.power.prompt" /></td>
            <td><ao:write name="powerOption" /></td>
        </tr>
    </logic:notEmpty>
    <tr>
        <td><fmt:message key="signup.notRequired" /></td>
        <td><fmt:message key="signupCustomizeServerConfirmation.cpu.prompt" /></td>
        <td><ao:write name="cpuOption" type="application/xhtml+xml" /></td>
    </tr>
    <tr>
        <td><fmt:message key="signup.notRequired" /></td>
        <td><fmt:message key="signupCustomizeServerConfirmation.ram.prompt" /></td>
        <td><ao:write name="ramOption" /></td>
    </tr>
    <logic:notEmpty scope="request" name="sataControllerOption">
        <tr>
            <td><fmt:message key="signup.notRequired" /></td>
            <td><fmt:message key="signupCustomizeServerConfirmation.sataController.prompt" /></td>
            <td><ao:write name="sataControllerOption" /></td>
        </tr>
    </logic:notEmpty>
    <logic:notEmpty scope="request" name="scsiControllerOption">
        <tr>
            <td><fmt:message key="signup.notRequired" /></td>
            <td><fmt:message key="signupCustomizeServerConfirmation.scsiController.prompt" /></td>
            <td><ao:write name="scsiControllerOption" /></td>
        </tr>
    </logic:notEmpty>
    <logic:iterate name="diskOptions" id="diskOption">
        <tr>
            <td><fmt:message key="signup.notRequired" /></td>
            <td><fmt:message key="signupCustomizeServerConfirmation.disk.prompt" /></td>
            <td><ao:write name="diskOption" /></td>
        </tr>
    </logic:iterate>
    <tr>
        <td><fmt:message key="signup.notRequired" /></td>
        <td><fmt:message key="signupCustomizeServerConfirmation.setup.prompt" /></td>
        <td>
            <logic:notPresent name="setup">
                <fmt:message key="signupSelectPackageForm.setup.none" />
            </logic:notPresent>
            <logic:present name="setup">
                $<ao:write name="setup" />
            </logic:present>
        </td>
    </tr>
    <tr>
        <td><fmt:message key="signup.notRequired" /></td>
        <td style='white-space:nowrap'><fmt:message key="signupCustomizeServerConfirmation.monthlyRate.prompt" /></td>
        <td>$<ao:write name="monthlyRate" /></td>
    </tr>
</fmt:bundle>
