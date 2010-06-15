<%-- aoweb-struts: Do not edit --%>
<%--
  Copyright 2007-2010 by AO Industries, Inc.,
  7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" buffer="256kb" autoFlush="true" pageEncoding="UTF-8" %>
<%@include file="/_taglibs.jsp" %>

<div>
    <ao:script>
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
            <bean:size scope="request" name="backupOnsiteOptions" id="backupOnsiteOptionsSize" />
            <logic:equal name="backupOnsiteOptionsSize" value="1">
                <logic:iterate scope="request" name="backupOnsiteOptions" id="option">
                    if(form.backupOnsiteOption.checked) totalMonthly = totalMonthly + Math.round(parseFloat(<ao:write name="option" property="priceDifference" />)*100);
                </logic:iterate>
            </logic:equal>
            <logic:notEqual name="backupOnsiteOptionsSize" value="1">
                <logic:iterate scope="request" name="backupOnsiteOptions" id="option" indexId="index">
                    if(form.backupOnsiteOption[parseInt(<ao:write name="index" />)].checked) totalMonthly = totalMonthly + Math.round(parseFloat(<ao:write name="option" property="priceDifference" />)*100);
                </logic:iterate>
            </logic:notEqual>

            // Add the backup offsite options
            <bean:size scope="request" name="backupOffsiteOptions" id="backupOffsiteOptionsSize" />
            <logic:equal name="backupOffsiteOptionsSize" value="1">
                <logic:iterate scope="request" name="backupOffsiteOptions" id="option">
                    if(form.backupOffsiteOption.checked) totalMonthly = totalMonthly + Math.round(parseFloat(<ao:write name="option" property="priceDifference" />)*100);
                </logic:iterate>
            </logic:equal>
            <logic:notEqual name="backupOffsiteOptionsSize" value="1">
                <logic:iterate scope="request" name="backupOffsiteOptions" id="option" indexId="index">
                    if(form.backupOffsiteOption[parseInt(<ao:write name="index" />)].checked) totalMonthly = totalMonthly + Math.round(parseFloat(<ao:write name="option" property="priceDifference" />)*100);
                </logic:iterate>
            </logic:notEqual>

            // Add the distro scan options
            <bean:size scope="request" name="distributionScanOptions" id="distributionScanOptionsSize" />
            <logic:equal name="distributionScanOptionsSize" value="1">
                <logic:iterate scope="request" name="distributionScanOptions" id="option">
                    if(form.distributionScanOption.checked) totalMonthly = totalMonthly + Math.round(parseFloat(<ao:write name="option" property="priceDifference" />)*100);
                </logic:iterate>
            </logic:equal>
            <logic:notEqual name="distributionScanOptionsSize" value="1">
                <logic:iterate scope="request" name="distributionScanOptions" id="option" indexId="index">
                    if(form.distributionScanOption[parseInt(<ao:write name="index" />)].checked) totalMonthly = totalMonthly + Math.round(parseFloat(<ao:write name="option" property="priceDifference" />)*100);
                </logic:iterate>
            </logic:notEqual>

            // Add the failover options
            <bean:size scope="request" name="failoverOptions" id="failoverOptionsSize" />
            <logic:equal name="failoverOptionsSize" value="1">
                <logic:iterate scope="request" name="failoverOptions" id="option">
                    if(form.failoverOption.checked) totalMonthly = totalMonthly + Math.round(parseFloat(<ao:write name="option" property="priceDifference" />)*100);
                </logic:iterate>
            </logic:equal>
            <logic:notEqual name="failoverOptionsSize" value="1">
                <logic:iterate scope="request" name="failoverOptions" id="option" indexId="index">
                    if(form.failoverOption[parseInt(<ao:write name="index" />)].checked) totalMonthly = totalMonthly + Math.round(parseFloat(<ao:write name="option" property="priceDifference" />)*100);
                </logic:iterate>
            </logic:notEqual>

            form.totalMonthly.value="$"+formatDecimal(totalMonthly);
        }
    </ao:script>
    <input type="hidden" name="selectedStep" value="" />
    <skin:lightArea>
        <fmt:bundle basename="com.aoindustries.website.signup.ApplicationResources">
            <table cellspacing="0" cellpadding="2">
                <tr><th colspan="2" class='aoLightRow'>
                    <span style="font-size:large;"><ao:write scope="request" name="packageDefinition" property="display" /></span>
                </th></tr>
                <logic:notEmpty scope="request" name="backupOnsiteOptions">
                    <tr>
                        <th>
                            <fmt:message key="signupCustomizeManagementForm.selectBackupOnsite" /><br />
                            <html:errors bundle="/signup/ApplicationResources" property="backupOnsiteOption" />
                        </th>
                        <th><fmt:message key="signupCustomizeManagementForm.backupOnsiteMonthly" /></th>
                    </tr>
                    <logic:iterate scope="request" name="backupOnsiteOptions" id="option">
                        <tr>
                            <td style="white-space:nowrap">
                                <html:radio onclick="recalcMonthly();" property="backupOnsiteOption" idName="option" value="packageDefinitionLimit" />
                                <ao:write name="option" property="display" />
                            </td>
                            <td>$<ao:write name="option" property="priceDifference" /></td>
                        </tr>
                    </logic:iterate>
                </logic:notEmpty>
                <logic:notEmpty scope="request" name="backupOffsiteOptions">
                    <tr>
                        <th>
                            <fmt:message key="signupCustomizeManagementForm.selectBackupOffsite" /><br />
                            <html:errors bundle="/signup/ApplicationResources" property="backupOffsiteOption" />
                        </th>
                        <th><fmt:message key="signupCustomizeManagementForm.backupOffsiteMonthly" /></th>
                    </tr>
                    <logic:iterate scope="request" name="backupOffsiteOptions" id="option">
                        <tr>
                            <td style="white-space:nowrap">
                                <html:radio onclick="recalcMonthly();" property="backupOffsiteOption" idName="option" value="packageDefinitionLimit" />
                                <ao:write name="option" property="display" />
                            </td>
                            <td>$<ao:write name="option" property="priceDifference" /></td>
                        </tr>
                    </logic:iterate>
                </logic:notEmpty>
                <logic:notEmpty scope="request" name="distributionScanOptions">
                    <tr>
                        <th>
                            <fmt:message key="signupCustomizeManagementForm.selectDistributionScan" /><br />
                            <html:errors bundle="/signup/ApplicationResources" property="distributionScanOption" />
                        </th>
                        <th><fmt:message key="signupCustomizeManagementForm.distributionScanMonthly" /></th>
                    </tr>
                    <logic:iterate scope="request" name="distributionScanOptions" id="option">
                        <tr>
                            <td style="white-space:nowrap">
                                <html:radio onclick="recalcMonthly();" property="distributionScanOption" idName="option" value="packageDefinitionLimit" />
                                <ao:write name="option" property="display" />
                            </td>
                            <td>$<ao:write name="option" property="priceDifference" /></td>
                        </tr>
                    </logic:iterate>
                </logic:notEmpty>
                <logic:notEmpty scope="request" name="failoverOptions">
                    <tr>
                        <th>
                            <fmt:message key="signupCustomizeManagementForm.selectFailover" /><br />
                            <html:errors bundle="/signup/ApplicationResources" property="failoverOption" />
                        </th>
                        <th><fmt:message key="signupCustomizeManagementForm.failoverMonthly" /></th>
                    </tr>
                    <logic:iterate scope="request" name="failoverOptions" id="option">
                        <tr>
                            <td style="white-space:nowrap">
                                <html:radio onclick="recalcMonthly();" property="failoverOption" idName="option" value="packageDefinitionLimit" />
                                <ao:write name="option" property="display" />
                            </td>
                            <td>$<ao:write name="option" property="priceDifference" /></td>
                        </tr>
                    </logic:iterate>
                </logic:notEmpty>
                <tr>
                    <th><fmt:message key="signupCustomizeManagementForm.hardwareRate.title" /></th>
                    <th align='left'>
                        <input type="hidden" name="formCompleted" value="true" />
                        <input type="hidden" name="hardwareRate" value='<ao:write scope="request" name="hardwareRate" />' />
                        <input type="text" name="hardwareRateDisplay" readonly='readonly' size="10" value='$<ao:write scope="request" name="hardwareRate" />' />
                    </th>
                </tr>
                <tr>
                    <th><fmt:message key="signupCustomizeManagementForm.total" /></th>
                    <th align='left'>
                        <input type="text" name="totalMonthly" readonly='readonly' size="10" value='$<ao:write scope="request" name="hardwareRate" />' />
                    </th>
                </tr>
                <tr><td colspan="2" align="center"><br /><ao:input type="submit"><fmt:message key="signupCustomizeManagementForm.submit.label" /></ao:input><br /><br /></td></tr>
            </table>
        </fmt:bundle>
    </skin:lightArea>
</div>
