<%--
  Copyright 2007 by AO Industries, Inc.,
  816 Azalea Rd, Mobile, Alabama, 36693, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld"       prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld"       prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld"      prefix="logic" %>
<%@ taglib uri="/WEB-INF/aoweb-struts-skin.tld" prefix="skin" %>
<skin:setContentType/>
<html:html lang="true">
    <skin:path>/signup/dedicated2.do</skin:path>
    <skin:title><bean:message bundle="/signup/ApplicationResources" key="dedicated.title"/></skin:title>
    <skin:navImageAlt><bean:message bundle="/signup/ApplicationResources" key="dedicated.navImageAlt"/></skin:navImageAlt>
    <skin:keywords><bean:message bundle="/signup/ApplicationResources" key="dedicated.keywords"/></skin:keywords>
    <skin:description><bean:message bundle="/signup/ApplicationResources" key="dedicated.description"/></skin:description>
    <%@ include file="addParents.jsp" %>
    <%@ include file="addSiblings.jsp" %>
    <skin:skin onLoad="recalcMonthly();">
        <skin:content width="600">
            <skin:contentTitle><bean:message bundle="/signup/ApplicationResources" key="dedicated.title"/></skin:contentTitle>
            <skin:contentHorizontalDivider/>
            <skin:contentLine>
                <script language="JavaScript1.2"><!--
                    function selectStep(step) {
                        var form = document.forms['dedicatedSignupCustomizeServerForm'];
                        form.selectedStep.value=step;
                        form.submit();
                    }
                // --></script>
                <bean:define toScope="request" type="java.lang.String" id="stepNumber" value="2"/>
                <%@ include file="dedicatedSteps.jsp" %>
                <br>
                <script language="JavaScript"><!--
                    function formatDecimal(pennies) {
                        var penniesOnly=pennies%100;
                        var dollars=(pennies-penniesOnly)/100;
                        if(penniesOnly<10) return dollars+'.0'+penniesOnly;
                        return dollars+'.'+penniesOnly;
                    }
                    function recalcMonthly() {
                        var form = document.forms['dedicatedSignupCustomizeServerForm'];
                        var totalMonthly = Math.round(Number(form.basePrice.value)*100);

                        // Add the Power options
                        <logic:iterate scope="request" name="powerOptions" id="option" indexId="index">
                            if(form.powerOption[<bean:write name="index"/>].checked) totalMonthly = totalMonthly + Math.round(<bean:write name="option" property="priceDifference"/>*100);
                        </logic:iterate>

                        // Add the CPU options
                        <logic:iterate scope="request" name="cpuOptions" id="option" indexId="index">
                            if(form.cpuOption[<bean:write name="index"/>].checked) totalMonthly = totalMonthly + Math.round(<bean:write name="option" property="priceDifference"/>*100);
                        </logic:iterate>

                        // Add the RAM options
                        <logic:iterate scope="request" name="ramOptions" id="option" indexId="index">
                            if(form.ramOption[<bean:write name="index"/>].checked) totalMonthly = totalMonthly + Math.round(<bean:write name="option" property="priceDifference"/>*100);
                        </logic:iterate>

                        // Add the SATA controller options
                        <logic:iterate scope="request" name="sataControllerOptions" id="option" indexId="index">
                            if(form.sataControllerOption[<bean:write name="index"/>].checked) totalMonthly = totalMonthly + Math.round(<bean:write name="option" property="priceDifference"/>*100);
                        </logic:iterate>

                        // Add the SCSI controller options
                        <logic:iterate scope="request" name="scsiControllerOptions" id="option" indexId="index">
                            if(form.scsiControllerOption[<bean:write name="index"/>].checked) totalMonthly = totalMonthly + Math.round(<bean:write name="option" property="priceDifference"/>*100);
                        </logic:iterate>

                        // Add the IDE options
                        <logic:iterate name="ideOptions" id="ideOptionList" indexId="index">
                            <logic:iterate name="ideOptionList" id="option" indexId="index2">
                                if(form.elements['ideOptions[<bean:write name="index"/>]'][<bean:write name="index2"/>].checked) totalMonthly = totalMonthly + Math.round(<bean:write name="option" property="priceDifference"/>*100);
                            </logic:iterate>
                        </logic:iterate>

                        // Add the SATA options
                        <logic:iterate name="sataOptions" id="sataOptionList" indexId="index">
                            <logic:iterate name="sataOptionList" id="option" indexId="index2">
                                if(form.elements['sataOptions[<bean:write name="index"/>]'][<bean:write name="index2"/>].checked) totalMonthly = totalMonthly + Math.round(<bean:write name="option" property="priceDifference"/>*100);
                            </logic:iterate>
                        </logic:iterate>

                        // Add the SCSI options
                        <logic:iterate name="scsiOptions" id="scsiOptionList" indexId="index">
                            <logic:iterate name="scsiOptionList" id="option" indexId="index2">
                                if(form.elements['scsiOptions[<bean:write name="index"/>]'][<bean:write name="index2"/>].checked) totalMonthly = totalMonthly + Math.round(<bean:write name="option" property="priceDifference"/>*100);
                            </logic:iterate>
                        </logic:iterate>

                        form.totalMonthly.value="$"+formatDecimal(totalMonthly);
                    }
                // --></script>
                <html:form action="/dedicated2Completed.do">
                    <input type="hidden" name="selectedStep" value="">
                    <skin:lightArea>
                        <table border="0" cellspacing="0" cellpadding="2">
                            <tr><th colspan="2" class='ao_light_row'>
                                <font size="+1"><bean:write scope="request" name="packageDefinition" property="display"/></font>
                            </th></tr>
                            <logic:notEmpty scope="request" name="powerOptions">
                                <tr>
                                    <th>
                                        <bean:message bundle="/signup/ApplicationResources" key="dedicated2.selectPower"/><br>
                                        <html:errors bundle="/signup/ApplicationResources" property="powerOption"/>
                                    </th>
                                    <th><bean:message bundle="/signup/ApplicationResources" key="dedicated2.powerMonthly"/></th>
                                </tr>
                                <logic:iterate scope="request" name="powerOptions" id="option">
                                    <tr>
                                        <td nowrap>
                                            <html:radio onclick="recalcMonthly();" property="powerOption" idName="option" value="packageDefinitionLimit"/>
                                            <bean:write name="option" property="display"/>
                                        </td>
                                        <td>$<bean:write name="option" property="priceDifference"/></td>
                                    </tr>
                                </logic:iterate>
                            </logic:notEmpty>
                            <logic:empty scope="request" name="powerOptions">
                                <input type="hidden" name="powerOption" value="-1">
                            </logic:empty>
                            <tr>
                                <th>
                                    <bean:message bundle="/signup/ApplicationResources" key="dedicated2.selectCPU"/><br>
                                    <html:errors bundle="/signup/ApplicationResources" property="cpuOption"/>
                                </th>
                                <th><bean:message bundle="/signup/ApplicationResources" key="dedicated2.cpuMonthly"/></th>
                            </tr>
                            <logic:iterate scope="request" name="cpuOptions" id="option">
                                <tr>
                                    <td nowrap>
                                        <html:radio onclick="recalcMonthly();" property="cpuOption" idName="option" value="packageDefinitionLimit"/>
                                        <bean:write name="option" property="display"/>
                                    </td>
                                    <td>$<bean:write name="option" property="priceDifference"/></td>
                                </tr>
                            </logic:iterate>
                            <tr>
                                <th>
                                    <bean:message bundle="/signup/ApplicationResources" key="dedicated2.selectRAM"/><br>
                                    <html:errors bundle="/signup/ApplicationResources" property="ramOption"/>
                                </th>
                                <th><bean:message bundle="/signup/ApplicationResources" key="dedicated2.ramMonthly"/></th>
                            </tr>
                            <logic:iterate scope="request" name="ramOptions" id="option">
                                <tr>
                                    <td nowrap>
                                        <html:radio onclick="recalcMonthly();" property="ramOption" idName="option" value="packageDefinitionLimit"/>
                                        <bean:write name="option" property="display"/>
                                    </td>
                                    <td>$<bean:write name="option" property="priceDifference"/></td>
                                </tr>
                            </logic:iterate>
                            <logic:notEmpty scope="request" name="sataControllerOptions">
                                <tr>
                                    <th>
                                        <bean:message bundle="/signup/ApplicationResources" key="dedicated2.selectSataController"/><br>
                                        <html:errors bundle="/signup/ApplicationResources" property="sataControllerOption"/>
                                    </th>
                                    <th><bean:message bundle="/signup/ApplicationResources" key="dedicated2.sataControllerMonthly"/></th>
                                </tr>
                                <logic:iterate scope="request" name="sataControllerOptions" id="option">
                                    <tr>
                                        <td nowrap>
                                            <html:radio onclick="recalcMonthly();" property="sataControllerOption" idName="option" value="packageDefinitionLimit"/>
                                            <bean:write name="option" property="display"/>
                                        </td>
                                        <td>$<bean:write name="option" property="priceDifference"/></td>
                                    </tr>
                                </logic:iterate>
                            </logic:notEmpty>
                            <logic:empty scope="request" name="sataControllerOptions">
                                <input type="hidden" name="sataControllerOption" value="-1">
                            </logic:empty>
                            <logic:notEmpty scope="request" name="scsiControllerOptions">
                                <tr>
                                    <th>
                                        <bean:message bundle="/signup/ApplicationResources" key="dedicated2.selectScsiController"/><br>
                                        <html:errors bundle="/signup/ApplicationResources" property="scsiControllerOption"/>
                                    </th>
                                    <th><bean:message bundle="/signup/ApplicationResources" key="dedicated2.scsiControllerMonthly"/></th>
                                </tr>
                                <logic:iterate scope="request" name="scsiControllerOptions" id="option">
                                    <tr>
                                        <td nowrap>
                                            <html:radio onclick="recalcMonthly();" property="scsiControllerOption" idName="option" value="packageDefinitionLimit"/>
                                            <bean:write name="option" property="display"/>
                                        </td>
                                        <td>$<bean:write name="option" property="priceDifference"/></td>
                                    </tr>
                                </logic:iterate>
                            </logic:notEmpty>
                            <logic:empty scope="request" name="scsiControllerOptions">
                                <input type="hidden" name="scsiControllerOption" value="-1">
                            </logic:empty>
                            <logic:iterate name="ideOptions" id="ideOptionList" indexId="index">
                                <tr>
                                    <th>
                                        <bean:message bundle="/signup/ApplicationResources" key="dedicated2.selectIDE" arg0="<%= Integer.toString(index.intValue()+1) %>"/><br>
                                        <logic:equal name="index" value="0"><html:errors bundle="/signup/ApplicationResources" property="ideOptions"/></logic:equal>
                                    </th>
                                    <th><bean:message bundle="/signup/ApplicationResources" key="dedicated2.ideMonthly"/></th>
                                </tr>
                                <logic:iterate name="ideOptionList" id="option">
                                    <tr>
                                        <td nowrap>
                                            <html:radio onclick="recalcMonthly();" property='<%= "ideOptions[" + index + "]" %>' idName="option" value="packageDefinitionLimit"/>
                                            <bean:write name="option" property="display"/>
                                        </td>
                                        <td>$<bean:write name="option" property="priceDifference"/></td>
                                    </tr>
                                </logic:iterate>
                            </logic:iterate>
                            <logic:iterate name="sataOptions" id="sataOptionList" indexId="index">
                                <tr>
                                    <th>
                                        <bean:message bundle="/signup/ApplicationResources" key="dedicated2.selectSATA" arg0="<%= Integer.toString(index.intValue()+1) %>"/><br>
                                        <logic:equal name="index" value="0"><html:errors bundle="/signup/ApplicationResources" property="sataOptions"/></logic:equal>
                                    </th>
                                    <th><bean:message bundle="/signup/ApplicationResources" key="dedicated2.sataMonthly"/></th>
                                </tr>
                                <logic:iterate name="sataOptionList" id="option">
                                    <tr>
                                        <td nowrap>
                                            <html:radio onclick="recalcMonthly();" property='<%= "sataOptions[" + index + "]" %>' idName="option" value="packageDefinitionLimit"/>
                                            <bean:write name="option" property="display"/>
                                        </td>
                                        <td>$<bean:write name="option" property="priceDifference"/></td>
                                    </tr>
                                </logic:iterate>
                            </logic:iterate>
                            <logic:iterate name="scsiOptions" id="scsiOptionList" indexId="index">
                                <tr>
                                    <th>
                                        <bean:message bundle="/signup/ApplicationResources" key="dedicated2.selectSCSI" arg0="<%= Integer.toString(index.intValue()+1) %>"/><br>
                                        <logic:equal name="index" value="0"><html:errors bundle="/signup/ApplicationResources" property="scsiOptions"/></logic:equal>
                                    </th>
                                    <th><bean:message bundle="/signup/ApplicationResources" key="dedicated2.scsiMonthly"/></th>
                                </tr>
                                <logic:iterate name="scsiOptionList" id="option">
                                    <tr>
                                        <td nowrap>
                                            <html:radio onclick="recalcMonthly();" property='<%= "scsiOptions[" + index + "]" %>' idName="option" value="packageDefinitionLimit"/>
                                            <bean:write name="option" property="display"/>
                                        </td>
                                        <td>$<bean:write name="option" property="priceDifference"/></td>
                                    </tr>
                                </logic:iterate>
                            </logic:iterate>
                            <tr>
                                <th><bean:message bundle="/signup/ApplicationResources" key="dedicated2.total"/></th>
                                <th align='left'>
                                    <input type="hidden" name="basePrice" value='<bean:write scope="request" name="basePrice"/>'>
                                    <input type="text" name="totalMonthly" readonly size="8" value='$<bean:write scope="request" name="basePrice"/>'>
                                </th>
                            </tr>
                            <tr><td colspan="2" align="center"><br><html:submit styleClass='ao_button'><bean:message bundle="/signup/ApplicationResources" key="dedicated2.submit.label"/></html:submit><br><br></td></tr>
                        </table>
                    </skin:lightArea>
                </html:form>
            </skin:contentLine>
        </skin:content>
    </skin:skin>
</html:html>
