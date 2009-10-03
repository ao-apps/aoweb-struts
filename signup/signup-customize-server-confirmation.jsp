<%-- aoweb-struts: Do not edit --%>
<%--
  Copyright 2007-2009 by AO Industries, Inc.,
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
    <logic:iterate name="ideOptions" id="ideOption">
        <tr>
            <td><fmt:message key="signup.notRequired" /></td>
            <td><fmt:message key="signupCustomizeServerConfirmation.ide.prompt" /></td>
            <td><ao:write name="ideOption" /></td>
        </tr>
    </logic:iterate>
    <logic:iterate name="sataOptions" id="sataOption">
        <tr>
            <td><fmt:message key="signup.notRequired" /></td>
            <td><fmt:message key="signupCustomizeServerConfirmation.sata.prompt" /></td>
            <td><ao:write name="sataOption" /></td>
        </tr>
    </logic:iterate>
    <logic:iterate name="scsiOptions" id="scsiOption">
        <tr>
            <td><fmt:message key="signup.notRequired" /></td>
            <td><fmt:message key="signupCustomizeServerConfirmation.scsi.prompt" /></td>
            <td><ao:write name="scsiOption" /></td>
        </tr>
    </logic:iterate>
    <tr>
        <td><fmt:message key="signup.notRequired" /></td>
        <td><fmt:message key="signupCustomizeServerConfirmation.setup.prompt" /></td>
        <td>
            <logic:notPresent name="setup">
                <fmt:message key="signupSelectServerForm.setup.none" />
            </logic:notPresent>
            <logic:present name="setup">
                $<ao:write name="setup" />
            </logic:present>
        </td>
    </tr>
    <tr>
        <td><fmt:message key="signup.notRequired" /></td>
        <td><fmt:message key="signupCustomizeServerConfirmation.monthlyRate.prompt" /></td>
        <td>$<ao:write name="monthlyRate" /></td>
    </tr>
</fmt:bundle>
