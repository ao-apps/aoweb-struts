<%-- aoweb-struts: Do not edit --%>
<%--
  Copyright 2007-2009 by AO Industries, Inc.,
  7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" buffer="256kb" autoFlush="true" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/taglibs.jsp" %>

<logic:notEmpty scope="request" name="powerOption">
    <tr>
        <td><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired" /></td>
        <td><bean:message bundle="/signup/ApplicationResources" key="signupCustomizeServerConfirmation.power.prompt" /></td>
        <td><ao:write name="powerOption" /></td>
    </tr>
</logic:notEmpty>
<tr>
    <td><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired" /></td>
    <td><bean:message bundle="/signup/ApplicationResources" key="signupCustomizeServerConfirmation.cpu.prompt" /></td>
    <td><ao:write name="cpuOption" type="application/xhtml+xml" /></td>
</tr>
<tr>
    <td><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired" /></td>
    <td><bean:message bundle="/signup/ApplicationResources" key="signupCustomizeServerConfirmation.ram.prompt" /></td>
    <td><ao:write name="ramOption" /></td>
</tr>
<logic:notEmpty scope="request" name="sataControllerOption">
    <tr>
        <td><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired" /></td>
        <td><bean:message bundle="/signup/ApplicationResources" key="signupCustomizeServerConfirmation.sataController.prompt" /></td>
        <td><ao:write name="sataControllerOption" /></td>
    </tr>
</logic:notEmpty>
<logic:notEmpty scope="request" name="scsiControllerOption">
    <tr>
        <td><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired" /></td>
        <td><bean:message bundle="/signup/ApplicationResources" key="signupCustomizeServerConfirmation.scsiController.prompt" /></td>
        <td><ao:write name="scsiControllerOption" /></td>
    </tr>
</logic:notEmpty>
<logic:iterate name="ideOptions" id="ideOption">
    <tr>
        <td><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired" /></td>
        <td><bean:message bundle="/signup/ApplicationResources" key="signupCustomizeServerConfirmation.ide.prompt" /></td>
        <td><ao:write name="ideOption" /></td>
    </tr>
</logic:iterate>
<logic:iterate name="sataOptions" id="sataOption">
    <tr>
        <td><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired" /></td>
        <td><bean:message bundle="/signup/ApplicationResources" key="signupCustomizeServerConfirmation.sata.prompt" /></td>
        <td><ao:write name="sataOption" /></td>
    </tr>
</logic:iterate>
<logic:iterate name="scsiOptions" id="scsiOption">
    <tr>
        <td><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired" /></td>
        <td><bean:message bundle="/signup/ApplicationResources" key="signupCustomizeServerConfirmation.scsi.prompt" /></td>
        <td><ao:write name="scsiOption" /></td>
    </tr>
</logic:iterate>
<tr>
    <td><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired" /></td>
    <td><bean:message bundle="/signup/ApplicationResources" key="signupCustomizeServerConfirmation.setup.prompt" /></td>
    <td>
        <logic:notPresent name="setup">
            <bean:message bundle="/signup/ApplicationResources" key="signupSelectServerForm.setup.none" />
        </logic:notPresent>
        <logic:present name="setup">
            $<ao:write name="setup" />
        </logic:present>
    </td>
</tr>
<tr>
    <td><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired" /></td>
    <td><bean:message bundle="/signup/ApplicationResources" key="signupCustomizeServerConfirmation.monthlyRate.prompt" /></td>
    <td>$<ao:write name="monthlyRate" /></td>
</tr>
