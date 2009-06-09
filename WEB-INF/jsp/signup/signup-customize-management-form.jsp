<%-- aoweb-struts: Do not edit --%>
<%--
  Copyright 2007-2009 by AO Industries, Inc.,
  7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" buffer="256kb" autoFlush="true" %>
<%@ include file="/WEB-INF/jsp/taglibs.jsp" %>

<script language="JavaScript"><!--
    function formatDecimal(pennies) {
        var penniesOnly=pennies%100;
        var dollars=(pennies-penniesOnly)/100;
        if(penniesOnly<10) return dollars+'.0'+penniesOnly;
        return dollars+'.'+penniesOnly;
    }
    function recalcMonthly() {
        var form = document.forms[signupCustomizeManagementFormName];
        var totalMonthly = Math.round(Number(form.hardwareRate.value)*100);

        // Add the backup onsite options
        <bean:size scope="request" name="backupOnsiteOptions" id="backupOnsiteOptionsSize"/>
        <logic:equal name="backupOnsiteOptionsSize" value="1">
            <logic:iterate scope="request" name="backupOnsiteOptions" id="option">
                if(form.backupOnsiteOption.checked) totalMonthly = totalMonthly + Math.round(<bean:write name="option" property="priceDifference"/>*100);
            </logic:iterate>
        </logic:equal>
        <logic:notEqual name="backupOnsiteOptionsSize" value="1">
            <logic:iterate scope="request" name="backupOnsiteOptions" id="option" indexId="index">
                if(form.backupOnsiteOption[<bean:write name="index"/>].checked) totalMonthly = totalMonthly + Math.round(<bean:write name="option" property="priceDifference"/>*100);
            </logic:iterate>
        </logic:notEqual>

        // Add the backup offsite options
        <bean:size scope="request" name="backupOffsiteOptions" id="backupOffsiteOptionsSize"/>
        <logic:equal name="backupOffsiteOptionsSize" value="1">
            <logic:iterate scope="request" name="backupOffsiteOptions" id="option">
                if(form.backupOffsiteOption.checked) totalMonthly = totalMonthly + Math.round(<bean:write name="option" property="priceDifference"/>*100);
            </logic:iterate>
        </logic:equal>
        <logic:notEqual name="backupOffsiteOptionsSize" value="1">
            <logic:iterate scope="request" name="backupOffsiteOptions" id="option" indexId="index">
                if(form.backupOffsiteOption[<bean:write name="index"/>].checked) totalMonthly = totalMonthly + Math.round(<bean:write name="option" property="priceDifference"/>*100);
            </logic:iterate>
        </logic:notEqual>

        // Add the distro scan options
        <bean:size scope="request" name="distributionScanOptions" id="distributionScanOptionsSize"/>
        <logic:equal name="distributionScanOptionsSize" value="1">
            <logic:iterate scope="request" name="distributionScanOptions" id="option">
                if(form.distributionScanOption.checked) totalMonthly = totalMonthly + Math.round(<bean:write name="option" property="priceDifference"/>*100);
            </logic:iterate>
        </logic:equal>
        <logic:notEqual name="distributionScanOptionsSize" value="1">
            <logic:iterate scope="request" name="distributionScanOptions" id="option" indexId="index">
                if(form.distributionScanOption[<bean:write name="index"/>].checked) totalMonthly = totalMonthly + Math.round(<bean:write name="option" property="priceDifference"/>*100);
            </logic:iterate>
        </logic:notEqual>

        // Add the failover options
        <bean:size scope="request" name="failoverOptions" id="failoverOptionsSize"/>
        <logic:equal name="failoverOptionsSize" value="1">
            <logic:iterate scope="request" name="failoverOptions" id="option">
                if(form.failoverOption.checked) totalMonthly = totalMonthly + Math.round(<bean:write name="option" property="priceDifference"/>*100);
            </logic:iterate>
        </logic:equal>
        <logic:notEqual name="failoverOptionsSize" value="1">
            <logic:iterate scope="request" name="failoverOptions" id="option" indexId="index">
                if(form.failoverOption[<bean:write name="index"/>].checked) totalMonthly = totalMonthly + Math.round(<bean:write name="option" property="priceDifference"/>*100);
            </logic:iterate>
        </logic:notEqual>

        form.totalMonthly.value="$"+formatDecimal(totalMonthly);
    }
// --></script>
<input type="hidden" name="selectedStep" value="">
<skin:lightArea>
    <table border="0" cellspacing="0" cellpadding="2">
        <tr><th colspan="2" class='ao_light_row'>
            <font size="+1"><bean:write scope="request" name="packageDefinition" property="display"/></font>
        </th></tr>
        <logic:notEmpty scope="request" name="backupOnsiteOptions">
            <tr>
                <th>
                    <bean:message bundle="/signup/ApplicationResources" key="signupCustomizeManagementForm.selectBackupOnsite"/><br>
                    <html:errors bundle="/signup/ApplicationResources" property="backupOnsiteOption"/>
                </th>
                <th><bean:message bundle="/signup/ApplicationResources" key="signupCustomizeManagementForm.backupOnsiteMonthly"/></th>
            </tr>
            <logic:iterate scope="request" name="backupOnsiteOptions" id="option">
                <tr>
                    <td nowrap>
                        <html:radio onclick="recalcMonthly();" property="backupOnsiteOption" idName="option" value="packageDefinitionLimit"/>
                        <bean:write name="option" property="display"/>
                    </td>
                    <td>$<bean:write name="option" property="priceDifference"/></td>
                </tr>
            </logic:iterate>
        </logic:notEmpty>
        <logic:notEmpty scope="request" name="backupOffsiteOptions">
            <tr>
                <th>
                    <bean:message bundle="/signup/ApplicationResources" key="signupCustomizeManagementForm.selectBackupOffsite"/><br>
                    <html:errors bundle="/signup/ApplicationResources" property="backupOffsiteOption"/>
                </th>
                <th><bean:message bundle="/signup/ApplicationResources" key="signupCustomizeManagementForm.backupOffsiteMonthly"/></th>
            </tr>
            <logic:iterate scope="request" name="backupOffsiteOptions" id="option">
                <tr>
                    <td nowrap>
                        <html:radio onclick="recalcMonthly();" property="backupOffsiteOption" idName="option" value="packageDefinitionLimit"/>
                        <bean:write name="option" property="display"/>
                    </td>
                    <td>$<bean:write name="option" property="priceDifference"/></td>
                </tr>
            </logic:iterate>
        </logic:notEmpty>
        <logic:notEmpty scope="request" name="distributionScanOptions">
            <tr>
                <th>
                    <bean:message bundle="/signup/ApplicationResources" key="signupCustomizeManagementForm.selectDistributionScan"/><br>
                    <html:errors bundle="/signup/ApplicationResources" property="distributionScanOption"/>
                </th>
                <th><bean:message bundle="/signup/ApplicationResources" key="signupCustomizeManagementForm.distributionScanMonthly"/></th>
            </tr>
            <logic:iterate scope="request" name="distributionScanOptions" id="option">
                <tr>
                    <td nowrap>
                        <html:radio onclick="recalcMonthly();" property="distributionScanOption" idName="option" value="packageDefinitionLimit"/>
                        <bean:write name="option" property="display"/>
                    </td>
                    <td>$<bean:write name="option" property="priceDifference"/></td>
                </tr>
            </logic:iterate>
        </logic:notEmpty>
        <logic:notEmpty scope="request" name="failoverOptions">
            <tr>
                <th>
                    <bean:message bundle="/signup/ApplicationResources" key="signupCustomizeManagementForm.selectFailover"/><br>
                    <html:errors bundle="/signup/ApplicationResources" property="failoverOption"/>
                </th>
                <th><bean:message bundle="/signup/ApplicationResources" key="signupCustomizeManagementForm.failoverMonthly"/></th>
            </tr>
            <logic:iterate scope="request" name="failoverOptions" id="option">
                <tr>
                    <td nowrap>
                        <html:radio onclick="recalcMonthly();" property="failoverOption" idName="option" value="packageDefinitionLimit"/>
                        <bean:write name="option" property="display"/>
                    </td>
                    <td>$<bean:write name="option" property="priceDifference"/></td>
                </tr>
            </logic:iterate>
        </logic:notEmpty>
        <tr>
            <th><bean:message bundle="/signup/ApplicationResources" key="signupCustomizeManagementForm.hardwareRate.title"/></th>
            <th align='left'>
                <input type="hidden" name="formCompleted" value="true">
                <input type="hidden" name="hardwareRate" value='<bean:write scope="request" name="hardwareRate"/>'>
                <input type="text" name="hardwareRateDisplay" readonly size="10" value='$<bean:write scope="request" name="hardwareRate"/>'>
            </th>
        </tr>
        <tr>
            <th><bean:message bundle="/signup/ApplicationResources" key="signupCustomizeManagementForm.total"/></th>
            <th align='left'>
                <input type="text" name="totalMonthly" readonly size="10" value='$<bean:write scope="request" name="hardwareRate"/>'>
            </th>
        </tr>
        <tr><td colspan="2" align="center"><br><html:submit><bean:message bundle="/signup/ApplicationResources" key="signupCustomizeManagementForm.submit.label"/></html:submit><br><br></td></tr>
    </table>
</skin:lightArea>
