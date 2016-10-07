<%-- aoweb-struts: Do not edit --%>
<%--
  Copyright 2007-2009, 2016 by AO Industries, Inc.,
  7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" pageEncoding="UTF-8" %>
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
            var form = document.forms[signupCustomizeServerFormName];
            var totalMonthly = Math.round(Number(form.basePrice.value)*100);

            // Add the Power options
            <bean:size scope="request" name="powerOptions" id="powerOptionsSize" />
            <logic:equal name="powerOptionsSize" value="1">
                <logic:iterate scope="request" name="powerOptions" id="option">
                    if(form.powerOption.checked) totalMonthly = totalMonthly + Math.round(parseFloat(<ao:write name="option" property="priceDifference" />)*100);
                </logic:iterate>
            </logic:equal>
            <logic:notEqual name="powerOptionsSize" value="1">
                <logic:iterate scope="request" name="powerOptions" id="option" indexId="index">
                    if(form.powerOption[parseInt(<ao:write name="index" />)].checked) totalMonthly = totalMonthly + Math.round(parseFloat(<ao:write name="option" property="priceDifference" />)*100);
                </logic:iterate>
            </logic:notEqual>

            // Add the CPU options
            <bean:size scope="request" name="cpuOptions" id="cpuOptionsSize" />
            <logic:equal name="cpuOptionsSize" value="1">
                <logic:iterate scope="request" name="cpuOptions" id="option">
                    if(form.cpuOption.checked) totalMonthly = totalMonthly + Math.round(parseFloat(<ao:write name="option" property="priceDifference" />)*100);
                </logic:iterate>
            </logic:equal>
            <logic:notEqual name="cpuOptionsSize" value="1">
                <logic:iterate scope="request" name="cpuOptions" id="option" indexId="index">
                    if(form.cpuOption[parseInt(<ao:write name="index" />)].checked) totalMonthly = totalMonthly + Math.round(parseFloat(<ao:write name="option" property="priceDifference" />)*100);
                </logic:iterate>
            </logic:notEqual>

            // Add the RAM options
            <bean:size scope="request" name="ramOptions" id="ramOptionsSize" />
            <logic:equal name="ramOptionsSize" value="1">
                <logic:iterate scope="request" name="ramOptions" id="option">
                    if(form.ramOption.checked) totalMonthly = totalMonthly + Math.round(parseFloat(<ao:write name="option" property="priceDifference" />)*100);
                </logic:iterate>
            </logic:equal>
            <logic:notEqual name="ramOptionsSize" value="1">
                <logic:iterate scope="request" name="ramOptions" id="option" indexId="index">
                    if(form.ramOption[parseInt(<ao:write name="index" />)].checked) totalMonthly = totalMonthly + Math.round(parseFloat(<ao:write name="option" property="priceDifference" />)*100);
                </logic:iterate>
            </logic:notEqual>

            // Add the SATA controller options
            <bean:size scope="request" name="sataControllerOptions" id="sataControllerOptionsSize" />
            <logic:equal name="sataControllerOptionsSize" value="1">
                <logic:iterate scope="request" name="sataControllerOptions" id="option">
                    if(form.sataControllerOption.checked) totalMonthly = totalMonthly + Math.round(parseFloat(<ao:write name="option" property="priceDifference" />)*100);
                </logic:iterate>
            </logic:equal>
            <logic:notEqual name="sataControllerOptionsSize" value="1">
                <logic:iterate scope="request" name="sataControllerOptions" id="option" indexId="index">
                    if(form.sataControllerOption[parseInt(<ao:write name="index" />)].checked) totalMonthly = totalMonthly + Math.round(parseFloat(<ao:write name="option" property="priceDifference" />)*100);
                </logic:iterate>
            </logic:notEqual>

            // Add the SCSI controller options
            <bean:size scope="request" name="scsiControllerOptions" id="scsiControllerOptionsSize" />
            <logic:equal name="scsiControllerOptionsSize" value="1">
                <logic:iterate scope="request" name="scsiControllerOptions" id="option">
                    if(form.scsiControllerOption.checked) totalMonthly = totalMonthly + Math.round(parseFloat(<ao:write name="option" property="priceDifference" />)*100);
                </logic:iterate>
            </logic:equal>
            <logic:notEqual name="scsiControllerOptionsSize" value="1">
                <logic:iterate scope="request" name="scsiControllerOptions" id="option" indexId="index">
                    if(form.scsiControllerOption[parseInt(<ao:write name="index" />)].checked) totalMonthly = totalMonthly + Math.round(parseFloat(<ao:write name="option" property="priceDifference" />)*100);
                </logic:iterate>
            </logic:notEqual>

            // Add the disk options
            <logic:iterate name="diskOptions" id="diskOptionList" indexId="index">
                <bean:size name="diskOptionList" id="diskOptionListSize" />
                <logic:equal name="diskOptionListSize" value="1">
                    <logic:iterate name="diskOptionList" id="option">
                        if(form.elements["diskOptions["+<ao:write name="index" />+"]"].checked) totalMonthly = totalMonthly + Math.round(parseFloat(<ao:write name="option" property="priceDifference" />)*100);
                    </logic:iterate>
                </logic:equal>
                <logic:notEqual name="diskOptionListSize" value="1">
                    <logic:iterate name="diskOptionList" id="option" indexId="index2">
                        if(form.elements["diskOptions["+<ao:write name="index" />+"]"][parseInt(<ao:write name="index2" />)].checked) totalMonthly = totalMonthly + Math.round(parseFloat(<ao:write name="option" property="priceDifference" />)*100);
                    </logic:iterate>
                </logic:notEqual>
            </logic:iterate>

            form.totalMonthly.value="$"+formatDecimal(totalMonthly);
        }
    </ao:script>
    <input type="hidden" name="selectedStep" value="" />
    <logic:empty scope="request" name="sataControllerOptions">
        <input type="hidden" name="sataControllerOption" value="-1" />
    </logic:empty>
    <logic:empty scope="request" name="powerOptions">
        <input type="hidden" name="powerOption" value="-1" />
    </logic:empty>
    <logic:empty scope="request" name="scsiControllerOptions">
        <input type="hidden" name="scsiControllerOption" value="-1" />
    </logic:empty>
    <skin:lightArea>
        <fmt:bundle basename="com.aoindustries.website.signup.ApplicationResources">
            <table cellspacing="0" cellpadding="2">
                <tr><th colspan="2" class='aoLightRow'>
                    <span style="font-size:large;"><ao:write scope="request" name="packageDefinition" property="display" /></span>
                </th></tr>
                <logic:notEmpty scope="request" name="powerOptions">
                    <tr>
                        <th>
                            <fmt:message key="signupCustomizeServerForm.selectPower" /><br />
                            <html:errors bundle="/signup/ApplicationResources" property="powerOption" />
                        </th>
                        <th><fmt:message key="signupCustomizeServerForm.powerMonthly" /></th>
                    </tr>
                    <logic:iterate scope="request" name="powerOptions" id="option">
                        <tr>
                            <td style="white-space:nowrap">
                                <html:radio onclick="recalcMonthly();" property="powerOption" idName="option" value="packageDefinitionLimit" />
                                <ao:write name="option" property="display" />
                            </td>
                            <td>$<ao:write name="option" property="priceDifference" /></td>
                        </tr>
                    </logic:iterate>
                </logic:notEmpty>
                <tr>
                    <th>
                        <fmt:message key="signupCustomizeServerForm.selectCPU" /><br />
                        <html:errors bundle="/signup/ApplicationResources" property="cpuOption" />
                    </th>
                    <th><fmt:message key="signupCustomizeServerForm.cpuMonthly" /></th>
                </tr>
                <logic:iterate scope="request" name="cpuOptions" id="option">
                    <tr>
                        <td style="white-space:nowrap">
                            <html:radio onclick="recalcMonthly();" property="cpuOption" idName="option" value="packageDefinitionLimit" />
                            <ao:write name="option" property="display" />
                        </td>
                        <td>$<ao:write name="option" property="priceDifference" /></td>
                    </tr>
                </logic:iterate>
                <tr>
                    <th>
                        <fmt:message key="signupCustomizeServerForm.selectRAM" /><br />
                        <html:errors bundle="/signup/ApplicationResources" property="ramOption" />
                    </th>
                    <th><fmt:message key="signupCustomizeServerForm.ramMonthly" /></th>
                </tr>
                <logic:iterate scope="request" name="ramOptions" id="option">
                    <tr>
                        <td style="white-space:nowrap">
                            <html:radio onclick="recalcMonthly();" property="ramOption" idName="option" value="packageDefinitionLimit" />
                            <ao:write name="option" property="display" />
                        </td>
                        <td>$<ao:write name="option" property="priceDifference" /></td>
                    </tr>
                </logic:iterate>
                <logic:notEmpty scope="request" name="sataControllerOptions">
                    <tr>
                        <th>
                            <fmt:message key="signupCustomizeServerForm.selectSataController" /><br />
                            <html:errors bundle="/signup/ApplicationResources" property="sataControllerOption" />
                        </th>
                        <th><fmt:message key="signupCustomizeServerForm.sataControllerMonthly" /></th>
                    </tr>
                    <logic:iterate scope="request" name="sataControllerOptions" id="option">
                        <tr>
                            <td style="white-space:nowrap">
                                <html:radio onclick="recalcMonthly();" property="sataControllerOption" idName="option" value="packageDefinitionLimit" />
                                <ao:write name="option" property="display" />
                            </td>
                            <td>$<ao:write name="option" property="priceDifference" /></td>
                        </tr>
                    </logic:iterate>
                </logic:notEmpty>
                <logic:notEmpty scope="request" name="scsiControllerOptions">
                    <tr>
                        <th>
                            <fmt:message key="signupCustomizeServerForm.selectScsiController" /><br />
                            <html:errors bundle="/signup/ApplicationResources" property="scsiControllerOption" />
                        </th>
                        <th><fmt:message key="signupCustomizeServerForm.scsiControllerMonthly" /></th>
                    </tr>
                    <logic:iterate scope="request" name="scsiControllerOptions" id="option">
                        <tr>
                            <td style="white-space:nowrap">
                                <html:radio onclick="recalcMonthly();" property="scsiControllerOption" idName="option" value="packageDefinitionLimit" />
                                <ao:write name="option" property="display" />
                            </td>
                            <td>$<ao:write name="option" property="priceDifference" /></td>
                        </tr>
                    </logic:iterate>
                </logic:notEmpty>
                <logic:iterate name="diskOptions" id="diskOptionList" indexId="index">
                    <tr>
                        <th>
                            <fmt:message key="signupCustomizeServerForm.selectDisk">
                                <fmt:param><c:out value="${index+1}" /></fmt:param>
                            </fmt:message><br />
                            <logic:equal name="index" value="0"><html:errors bundle="/signup/ApplicationResources" property="diskOptions" /></logic:equal>
                        </th>
                        <th><fmt:message key="signupCustomizeServerForm.diskMonthly" /></th>
                    </tr>
                    <logic:iterate name="diskOptionList" id="option">
                        <tr>
                            <td style="white-space:nowrap">
                                <html:radio onclick="recalcMonthly();" property='<%= "diskOptions[" + index + "]" %>' idName="option" value="packageDefinitionLimit" />
                                <ao:write name="option" property="display" />
                            </td>
                            <td>$<ao:write name="option" property="priceDifference" /></td>
                        </tr>
                    </logic:iterate>
                </logic:iterate>
                <tr>
                    <th><fmt:message key="signupCustomizeServerForm.basePrice.title" /></th>
                    <th align='left'>
                        <input type="hidden" name="basePrice" value='<ao:write scope="request" name="basePrice" />' />
                        <input type="text" name="basePriceDisplay" readonly='readonly' size="10" value='$<ao:write scope="request" name="basePrice" />' />
                    </th>
                </tr>
                <tr>
                    <th><fmt:message key="signupCustomizeServerForm.total" /></th>
                    <th align='left'>
                        <input type="text" name="totalMonthly" readonly='readonly' size="10" value='$<ao:write scope="request" name="basePrice" />' />
                    </th>
                </tr>
                <tr><td colspan="2" align="center"><br /><ao:input type="submit"><fmt:message key="signupCustomizeServerForm.submit.label" /></ao:input><br /><br /></td></tr>
            </table>
        </fmt:bundle>
    </skin:lightArea>
</div>
