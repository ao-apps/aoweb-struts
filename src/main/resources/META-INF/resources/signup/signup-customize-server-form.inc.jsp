<%--
aoweb-struts-resources - Web resources for legacy Struts-based site framework with AOServ Platform control panels.
Copyright (C) 2007-2009, 2016, 2019  AO Industries, Inc.
	support@aoindustries.com
	7262 Bull Pen Cir
	Mobile, AL 36695

This file is part of aoweb-struts-resources.

aoweb-struts-resources is free software: you can redistribute it and/or modify
it under the terms of the GNU Lesser General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

aoweb-struts-resources is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public License
along with aoweb-struts-resources.  If not, see <http://www.gnu.org/licenses/>.
--%>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@include file="/_taglibs.inc.jsp" %>

<%-- TODO: Multi-currency --%>
<% pageContext.setAttribute("currency", java.util.Currency.getInstance("USD")); %>
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

			// TODO: Multi-currency
			var totalMonthly = Math.round(parseFloat(<ao:out value="${requestScope.basePrice.map[currency].value}" />)*100);

			// Add the Power options
			<bean:size scope="request" name="powerOptions" id="powerOptionsSize" />
			<logic:equal name="powerOptionsSize" value="1">
				<logic:iterate scope="request" name="powerOptions" id="option">
					// TODO: Multi-currency
					if(form.powerOption.checked) {
						<c:set var="priceDifference" value="${option.priceDifference.map[currency].value}" />
						<c:if test="${!empty priceDifference}">
							totalMonthly = totalMonthly + Math.round(parseFloat(<ao:out value="${priceDifference}" />)*100);
						</c:if>
					}
				</logic:iterate>
			</logic:equal>
			<logic:notEqual name="powerOptionsSize" value="1">
				<logic:iterate scope="request" name="powerOptions" id="option" indexId="index">
					// TODO: Multi-currency
					if(form.powerOption[parseInt(<ao:out value="${index}" />)].checked) {
						<c:set var="priceDifference" value="${option.priceDifference.map[currency].value}" />
						<c:if test="${!empty priceDifference}">
							totalMonthly = totalMonthly + Math.round(parseFloat(<ao:out value="${priceDifference}" />)*100);
						</c:if>
					}
				</logic:iterate>
			</logic:notEqual>

			// Add the CPU options
			<bean:size scope="request" name="cpuOptions" id="cpuOptionsSize" />
			<logic:equal name="cpuOptionsSize" value="1">
				<logic:iterate scope="request" name="cpuOptions" id="option">
					// TODO: Multi-currency
					if(form.cpuOption.checked) {
						<c:set var="priceDifference" value="${option.priceDifference.map[currency].value}" />
						<c:if test="${!empty priceDifference}">
							totalMonthly = totalMonthly + Math.round(parseFloat(<ao:out value="${priceDifference}" />)*100);
						</c:if>
					}
				</logic:iterate>
			</logic:equal>
			<logic:notEqual name="cpuOptionsSize" value="1">
				<logic:iterate scope="request" name="cpuOptions" id="option" indexId="index">
					// TODO: Multi-currency
					if(form.cpuOption[parseInt(<ao:out value="${index}" />)].checked) {
						<c:set var="priceDifference" value="${option.priceDifference.map[currency].value}" />
						<c:if test="${!empty priceDifference}">
							totalMonthly = totalMonthly + Math.round(parseFloat(<ao:out value="${priceDifference}" />)*100);
						</c:if>
					}
				</logic:iterate>
			</logic:notEqual>

			// Add the RAM options
			<bean:size scope="request" name="ramOptions" id="ramOptionsSize" />
			<logic:equal name="ramOptionsSize" value="1">
				<logic:iterate scope="request" name="ramOptions" id="option">
					// TODO: Multi-currency
					if(form.ramOption.checked) {
						<c:set var="priceDifference" value="${option.priceDifference.map[currency].value}" />
						<c:if test="${!empty priceDifference}">
							totalMonthly = totalMonthly + Math.round(parseFloat(<ao:out value="${priceDifference}" />)*100);
						</c:if>
					}
				</logic:iterate>
			</logic:equal>
			<logic:notEqual name="ramOptionsSize" value="1">
				<logic:iterate scope="request" name="ramOptions" id="option" indexId="index">
					// TODO: Multi-currency
					if(form.ramOption[parseInt(<ao:out value="${index}" />)].checked) {
						<c:set var="priceDifference" value="${option.priceDifference.map[currency].value}" />
						<c:if test="${!empty priceDifference}">
							totalMonthly = totalMonthly + Math.round(parseFloat(<ao:out value="${priceDifference}" />)*100);
						</c:if>
					}
				</logic:iterate>
			</logic:notEqual>

			// Add the SATA controller options
			<bean:size scope="request" name="sataControllerOptions" id="sataControllerOptionsSize" />
			<logic:equal name="sataControllerOptionsSize" value="1">
				<logic:iterate scope="request" name="sataControllerOptions" id="option">
					// TODO: Multi-currency
					if(form.sataControllerOption.checked) {
						<c:set var="priceDifference" value="${option.priceDifference.map[currency].value}" />
						<c:if test="${!empty priceDifference}">
							totalMonthly = totalMonthly + Math.round(parseFloat(<ao:out value="${priceDifference}" />)*100);
						</c:if>
					}
				</logic:iterate>
			</logic:equal>
			<logic:notEqual name="sataControllerOptionsSize" value="1">
				<logic:iterate scope="request" name="sataControllerOptions" id="option" indexId="index">
					// TODO: Multi-currency
					if(form.sataControllerOption[parseInt(<ao:out value="${index}" />)].checked) {
						<c:set var="priceDifference" value="${option.priceDifference.map[currency].value}" />
						<c:if test="${!empty priceDifference}">
							totalMonthly = totalMonthly + Math.round(parseFloat(<ao:out value="${priceDifference}" />)*100);
						</c:if>
					}
				</logic:iterate>
			</logic:notEqual>

			// Add the SCSI controller options
			<bean:size scope="request" name="scsiControllerOptions" id="scsiControllerOptionsSize" />
			<logic:equal name="scsiControllerOptionsSize" value="1">
				<logic:iterate scope="request" name="scsiControllerOptions" id="option">
					// TODO: Multi-currency
					if(form.scsiControllerOption.checked) {
						<c:set var="priceDifference" value="${option.priceDifference.map[currency].value}" />
						<c:if test="${!empty priceDifference}">
							totalMonthly = totalMonthly + Math.round(parseFloat(<ao:out value="${priceDifference}" />)*100);
						</c:if>
					}
				</logic:iterate>
			</logic:equal>
			<logic:notEqual name="scsiControllerOptionsSize" value="1">
				<logic:iterate scope="request" name="scsiControllerOptions" id="option" indexId="index">
					// TODO: Multi-currency
					if(form.scsiControllerOption[parseInt(<ao:out value="${index}" />)].checked) {
						<c:set var="priceDifference" value="${option.priceDifference.map[currency].value}" />
						<c:if test="${!empty priceDifference}">
							totalMonthly = totalMonthly + Math.round(parseFloat(<ao:out value="${priceDifference}" />)*100);
						</c:if>
					}
				</logic:iterate>
			</logic:notEqual>

			// Add the disk options
			<logic:iterate name="diskOptions" id="diskOptionList" indexId="index">
				<bean:size name="diskOptionList" id="diskOptionListSize" />
				<logic:equal name="diskOptionListSize" value="1">
					<logic:iterate name="diskOptionList" id="option">
						// TODO: Multi-currency
						if(form.elements["diskOptions["+<ao:out value="${index}" />+"]"].checked) {
							<c:set var="priceDifference" value="${option.priceDifference.map[currency].value}" />
							<c:if test="${!empty priceDifference}">
								totalMonthly = totalMonthly + Math.round(parseFloat(<ao:out value="${priceDifference}" />)*100);
							</c:if>
						}
					</logic:iterate>
				</logic:equal>
				<logic:notEqual name="diskOptionListSize" value="1">
					<logic:iterate name="diskOptionList" id="option" indexId="index2">
						// TODO: Multi-currency
						if(form.elements["diskOptions["+<ao:out value="${index}" />+"]"][parseInt(<ao:out value="${index2}" />)].checked) {
							<c:set var="priceDifference" value="${option.priceDifference.map[currency].value}" />
							<c:if test="${!empty priceDifference}">
								totalMonthly = totalMonthly + Math.round(parseFloat(<ao:out value="${priceDifference}" />)*100);
							</c:if>
						}
					</logic:iterate>
				</logic:notEqual>
			</logic:iterate>

			// TODO: Multi-currency
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
		<ao:bundle basename="com.aoindustries.website.signup.ApplicationResources">
			<table cellspacing="0" cellpadding="2">
				<tr><th colspan="2" class='aoLightRow'>
					<span style="font-size:large;"><ao:write scope="request" name="packageDefinition" property="display" /></span>
				</th></tr>
				<logic:notEmpty scope="request" name="powerOptions">
					<tr>
						<th>
							<ao:message key="signupCustomizeServerForm.selectPower" /><br />
							<html:errors bundle="/signup/ApplicationResources" property="powerOption" />
						</th>
						<th><ao:message key="signupCustomizeServerForm.powerMonthly" /></th>
					</tr>
					<logic:iterate scope="request" name="powerOptions" id="option">
						<tr>
							<td style="white-space:nowrap">
								<label><html:radio onclick="recalcMonthly();" property="powerOption" idName="option" value="packageDefinitionLimit" />
								<ao:write name="option" property="display" /></label>
							</td>
							<td><ao:write name="option" property="priceDifference" /></td>
						</tr>
					</logic:iterate>
				</logic:notEmpty>
				<tr>
					<th>
						<ao:message key="signupCustomizeServerForm.selectCPU" /><br />
						<html:errors bundle="/signup/ApplicationResources" property="cpuOption" />
					</th>
					<th><ao:message key="signupCustomizeServerForm.cpuMonthly" /></th>
				</tr>
				<logic:iterate scope="request" name="cpuOptions" id="option">
					<tr>
						<td style="white-space:nowrap">
							<label><html:radio onclick="recalcMonthly();" property="cpuOption" idName="option" value="packageDefinitionLimit" />
							<ao:write name="option" property="display" /></label>
						</td>
						<td><ao:write name="option" property="priceDifference" /></td>
					</tr>
				</logic:iterate>
				<tr>
					<th>
						<ao:message key="signupCustomizeServerForm.selectRAM" /><br />
						<html:errors bundle="/signup/ApplicationResources" property="ramOption" />
					</th>
					<th><ao:message key="signupCustomizeServerForm.ramMonthly" /></th>
				</tr>
				<logic:iterate scope="request" name="ramOptions" id="option">
					<tr>
						<td style="white-space:nowrap">
							<label><html:radio onclick="recalcMonthly();" property="ramOption" idName="option" value="packageDefinitionLimit" />
							<ao:write name="option" property="display" /></label>
						</td>
						<td><ao:write name="option" property="priceDifference" /></td>
					</tr>
				</logic:iterate>
				<logic:notEmpty scope="request" name="sataControllerOptions">
					<tr>
						<th>
							<ao:message key="signupCustomizeServerForm.selectSataController" /><br />
							<html:errors bundle="/signup/ApplicationResources" property="sataControllerOption" />
						</th>
						<th><ao:message key="signupCustomizeServerForm.sataControllerMonthly" /></th>
					</tr>
					<logic:iterate scope="request" name="sataControllerOptions" id="option">
						<tr>
							<td style="white-space:nowrap">
								<label><html:radio onclick="recalcMonthly();" property="sataControllerOption" idName="option" value="packageDefinitionLimit" />
								<ao:write name="option" property="display" /></label>
							</td>
							<td><ao:write name="option" property="priceDifference" /></td>
						</tr>
					</logic:iterate>
				</logic:notEmpty>
				<logic:notEmpty scope="request" name="scsiControllerOptions">
					<tr>
						<th>
							<ao:message key="signupCustomizeServerForm.selectScsiController" /><br />
							<html:errors bundle="/signup/ApplicationResources" property="scsiControllerOption" />
						</th>
						<th><ao:message key="signupCustomizeServerForm.scsiControllerMonthly" /></th>
					</tr>
					<logic:iterate scope="request" name="scsiControllerOptions" id="option">
						<tr>
							<td style="white-space:nowrap">
								<label><html:radio onclick="recalcMonthly();" property="scsiControllerOption" idName="option" value="packageDefinitionLimit" />
								<ao:write name="option" property="display" /></label>
							</td>
							<td><ao:write name="option" property="priceDifference" /></td>
						</tr>
					</logic:iterate>
				</logic:notEmpty>
				<logic:iterate name="diskOptions" id="diskOptionList" indexId="index">
					<tr>
						<th>
							<ao:message key="signupCustomizeServerForm.selectDisk" arg0="${index+1}" /><br />
							<c:if test="${index == 0}"><html:errors bundle="/signup/ApplicationResources" property="diskOptions" /></c:if>
						</th>
						<th><ao:message key="signupCustomizeServerForm.diskMonthly" /></th>
					</tr>
					<logic:iterate name="diskOptionList" id="option">
						<tr>
							<td style="white-space:nowrap">
								<label><html:radio onclick="recalcMonthly();" property="diskOptions[${index}]" idName="option" value="packageDefinitionLimit" />
								<ao:write name="option" property="display" /></label>
							</td>
							<td><ao:write name="option" property="priceDifference" /></td>
						</tr>
					</logic:iterate>
				</logic:iterate>
				<tr>
					<th><ao:message key="signupCustomizeServerForm.basePrice.title" /></th>
					<th align='left'><ao:input type="text" name="basePriceDisplay" readonly="true" size="10" value="${requestScope.basePrice}" /></th>
				</tr>
				<tr>
					<th><ao:message key="signupCustomizeServerForm.total" /></th>
					<th align='left'><ao:input type="text" name="totalMonthly" readonly='readonly' size="10" value="${requestScope.basePrice}" /></th>
				</tr>
				<tr><td colspan="2" align="center"><br /><ao:input type="submit"><ao:message key="signupCustomizeServerForm.submit.label" /></ao:input><br /><br /></td></tr>
			</table>
		</ao:bundle>
	</skin:lightArea>
</div>
