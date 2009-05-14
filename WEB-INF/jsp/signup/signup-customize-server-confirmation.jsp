<%-- aoweb-struts --%>
<%--
  Copyright 2007-2009 by AO Industries, Inc.,
  7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" buffer="256kb" autoFlush="true" %>
<%@ include file="/WEB-INF/jsp/taglibs.jsp" %>

<logic:notEmpty scope="request" name="powerOption">
    <TR>
        <TD><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired"/></TD>
        <TD><bean:message bundle="/signup/ApplicationResources" key="signupCustomizeServerConfirmation.power.prompt"/></TD>
        <TD><bean:write name="powerOption"/></TD>
    </TR>
</logic:notEmpty>
<TR>
    <TD><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired"/></TD>
    <TD><bean:message bundle="/signup/ApplicationResources" key="signupCustomizeServerConfirmation.cpu.prompt"/></TD>
    <TD><bean:write name="cpuOption" filter="false"/></TD>
</TR>
<TR>
    <TD><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired"/></TD>
    <TD><bean:message bundle="/signup/ApplicationResources" key="signupCustomizeServerConfirmation.ram.prompt"/></TD>
    <TD><bean:write name="ramOption"/></TD>
</TR>
<logic:notEmpty scope="request" name="sataControllerOption">
    <TR>
        <TD><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired"/></TD>
        <TD><bean:message bundle="/signup/ApplicationResources" key="signupCustomizeServerConfirmation.sataController.prompt"/></TD>
        <TD><bean:write name="sataControllerOption"/></TD>
    </TR>
</logic:notEmpty>
<logic:notEmpty scope="request" name="scsiControllerOption">
    <TR>
        <TD><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired"/></TD>
        <TD><bean:message bundle="/signup/ApplicationResources" key="signupCustomizeServerConfirmation.scsiController.prompt"/></TD>
        <TD><bean:write name="scsiControllerOption"/></TD>
    </TR>
</logic:notEmpty>
<logic:iterate name="ideOptions" id="ideOption">
    <TR>
        <TD><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired"/></TD>
        <TD><bean:message bundle="/signup/ApplicationResources" key="signupCustomizeServerConfirmation.ide.prompt"/></TD>
        <TD><bean:write name="ideOption"/></TD>
    </TR>
</logic:iterate>
<logic:iterate name="sataOptions" id="sataOption">
    <TR>
        <TD><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired"/></TD>
        <TD><bean:message bundle="/signup/ApplicationResources" key="signupCustomizeServerConfirmation.sata.prompt"/></TD>
        <TD><bean:write name="sataOption"/></TD>
    </TR>
</logic:iterate>
<logic:iterate name="scsiOptions" id="scsiOption">
    <TR>
        <TD><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired"/></TD>
        <TD><bean:message bundle="/signup/ApplicationResources" key="signupCustomizeServerConfirmation.scsi.prompt"/></TD>
        <TD><bean:write name="scsiOption"/></TD>
    </TR>
</logic:iterate>
<TR>
    <TD><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired"/></TD>
    <TD><bean:message bundle="/signup/ApplicationResources" key="signupCustomizeServerConfirmation.setup.prompt"/></TD>
    <TD>
        <logic:notPresent name="setup">
            <bean:message bundle="/signup/ApplicationResources" key="signupSelectServerForm.setup.none"/>
        </logic:notPresent>
        <logic:present name="setup">
            $<bean:write name="setup"/>
        </logic:present>
    </TD>
</TR>
<TR>
    <TD><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired"/></TD>
    <TD><bean:message bundle="/signup/ApplicationResources" key="signupCustomizeServerConfirmation.monthlyRate.prompt"/></TD>
    <TD>$<bean:write name="monthlyRate"/></TD>
</TR>
