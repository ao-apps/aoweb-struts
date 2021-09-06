<%--
aoweb-struts-resources - Template webapp for legacy Struts-based site framework with AOServ Platform control panels.
Copyright (C) 2007-2009, 2016, 2019, 2020, 2021  AO Industries, Inc.
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
			var form = document.forms[signupCustomizeManagementFormName];

			// TODO: Multi-currency
			var totalMonthly = Math.round(parseFloat(<ao:out value="${requestScope.hardwareRate.map[currency].value}" />)*100);

			// Add the backup onsite options
			<bean:size scope="request" name="backupOnsiteOptions" id="backupOnsiteOptionsSize" />
			<logic:equal name="backupOnsiteOptionsSize" value="1">
				<logic:iterate scope="request" name="backupOnsiteOptions" id="option">
					// TODO: Multi-currency
					if(form.backupOnsiteOption.checked) {
						<c:set var="priceDifference" value="${option.priceDifference.map[currency].value}" />
						<c:if test="${!empty priceDifference}">
							totalMonthly = totalMonthly + Math.round(parseFloat(<ao:out value="${priceDifference}" />)*100);
						</c:if>
					}
				</logic:iterate>
			</logic:equal>
			<logic:notEqual name="backupOnsiteOptionsSize" value="1">
				<logic:iterate scope="request" name="backupOnsiteOptions" id="option" indexId="index">
					// TODO: Multi-currency
					if(form.backupOnsiteOption[parseInt(<ao:out value="${index}" />)].checked) {
						<c:set var="priceDifference" value="${option.priceDifference.map[currency].value}" />
						<c:if test="${!empty priceDifference}">
							totalMonthly = totalMonthly + Math.round(parseFloat(<ao:out value="${priceDifference}" />)*100);
						</c:if>
					}
				</logic:iterate>
			</logic:notEqual>

			// Add the backup offsite options
			<bean:size scope="request" name="backupOffsiteOptions" id="backupOffsiteOptionsSize" />
			<logic:equal name="backupOffsiteOptionsSize" value="1">
				<logic:iterate scope="request" name="backupOffsiteOptions" id="option">
					// TODO: Multi-currency
					if(form.backupOffsiteOption.checked) {
						<c:set var="priceDifference" value="${option.priceDifference.map[currency].value}" />
						<c:if test="${!empty priceDifference}">
							totalMonthly = totalMonthly + Math.round(parseFloat(<ao:out value="${priceDifference}" />)*100);
						</c:if>
					}
				</logic:iterate>
			</logic:equal>
			<logic:notEqual name="backupOffsiteOptionsSize" value="1">
				<logic:iterate scope="request" name="backupOffsiteOptions" id="option" indexId="index">
					// TODO: Multi-currency
					if(form.backupOffsiteOption[parseInt(<ao:out value="${index}" />)].checked) {
						<c:set var="priceDifference" value="${option.priceDifference.map[currency].value}" />
						<c:if test="${!empty priceDifference}">
							totalMonthly = totalMonthly + Math.round(parseFloat(<ao:out value="${priceDifference}" />)*100);
						</c:if>
					}
				</logic:iterate>
			</logic:notEqual>

			// Add the distro scan options
			<bean:size scope="request" name="distributionScanOptions" id="distributionScanOptionsSize" />
			<logic:equal name="distributionScanOptionsSize" value="1">
				<logic:iterate scope="request" name="distributionScanOptions" id="option">
					// TODO: Multi-currency
					if(form.distributionScanOption.checked) {
						<c:set var="priceDifference" value="${option.priceDifference.map[currency].value}" />
						<c:if test="${!empty priceDifference}">
							totalMonthly = totalMonthly + Math.round(parseFloat(<ao:out value="${priceDifference}" />)*100);
						</c:if>
					}
				</logic:iterate>
			</logic:equal>
			<logic:notEqual name="distributionScanOptionsSize" value="1">
				<logic:iterate scope="request" name="distributionScanOptions" id="option" indexId="index">
					// TODO: Multi-currency
					if(form.distributionScanOption[parseInt(<ao:out value="${index}" />)].checked) {
						<c:set var="priceDifference" value="${option.priceDifference.map[currency].value}" />
						<c:if test="${!empty priceDifference}">
							totalMonthly = totalMonthly + Math.round(parseFloat(<ao:out value="${priceDifference}" />)*100);
						</c:if>
					}
				</logic:iterate>
			</logic:notEqual>

			// Add the failover options
			<bean:size scope="request" name="failoverOptions" id="failoverOptionsSize" />
			<logic:equal name="failoverOptionsSize" value="1">
				<logic:iterate scope="request" name="failoverOptions" id="option">
					// TODO: Multi-currency
					if(form.failoverOption.checked) {
						<c:set var="priceDifference" value="${option.priceDifference.map[currency].value}" />
						<c:if test="${!empty priceDifference}">
							totalMonthly = totalMonthly + Math.round(parseFloat(<ao:out value="${priceDifference}" />)*100);
						</c:if>
					}
				</logic:iterate>
			</logic:equal>
			<logic:notEqual name="failoverOptionsSize" value="1">
				<logic:iterate scope="request" name="failoverOptions" id="option" indexId="index">
					// TODO: Multi-currency
					if(form.failoverOption[parseInt(<ao:out value="${index}" />)].checked) {
						<c:set var="priceDifference" value="${option.priceDifference.map[currency].value}" />
						<c:if test="${!empty priceDifference}">
							totalMonthly = totalMonthly + Math.round(parseFloat(<ao:out value="${priceDifference}" />)*100);
						</c:if>
					}
				</logic:iterate>
			</logic:notEqual>

			// TODO: Multi-currency
			form.totalMonthly.value="$"+formatDecimal(totalMonthly);
		}
	</ao:script>
	<ao:input type="hidden" name="selectedStep" />
	<skin:lightArea>
		<ao:bundle basename="com.aoindustries.web.struts.signup.i18n.ApplicationResources">
			<table class="ao-no-border">
				<thead>
					<tr><th colspan="2" class='aoLightRow'>
						<span style="font-size:large;"><ao:write scope="request" name="packageDefinition" property="display" /></span>
					</th></tr>
				</thead>
				<tbody>
					<logic:notEmpty scope="request" name="backupOnsiteOptions">
						<tr>
							<th>
								<ao:message key="signupCustomizeManagementForm.selectBackupOnsite" /><ao:br />
								<html:errors bundle="/signup/ApplicationResources" property="backupOnsiteOption" />
							</th>
							<th><ao:message key="signupCustomizeManagementForm.backupOnsiteMonthly" /></th>
						</tr>
						<logic:iterate scope="request" name="backupOnsiteOptions" id="option">
							<tr>
								<td style="white-space:nowrap">
									<label><html:radio onclick="recalcMonthly();" property="backupOnsiteOption" idName="option" value="packageDefinitionLimit" />
									<ao:write name="option" property="display" /></label>
								</td>
								<td><ao:write name="option" property="priceDifference" /></td>
							</tr>
						</logic:iterate>
					</logic:notEmpty>
					<logic:notEmpty scope="request" name="backupOffsiteOptions">
						<tr>
							<th>
								<ao:message key="signupCustomizeManagementForm.selectBackupOffsite" /><ao:br />
								<html:errors bundle="/signup/ApplicationResources" property="backupOffsiteOption" />
							</th>
							<th><ao:message key="signupCustomizeManagementForm.backupOffsiteMonthly" /></th>
						</tr>
						<logic:iterate scope="request" name="backupOffsiteOptions" id="option">
							<tr>
								<td style="white-space:nowrap">
									<label><html:radio onclick="recalcMonthly();" property="backupOffsiteOption" idName="option" value="packageDefinitionLimit" />
									<ao:write name="option" property="display" /></label>
								</td>
								<td><ao:write name="option" property="priceDifference" /></td>
							</tr>
						</logic:iterate>
					</logic:notEmpty>
					<logic:notEmpty scope="request" name="distributionScanOptions">
						<tr>
							<th>
								<ao:message key="signupCustomizeManagementForm.selectDistributionScan" /><ao:br />
								<html:errors bundle="/signup/ApplicationResources" property="distributionScanOption" />
							</th>
							<th><ao:message key="signupCustomizeManagementForm.distributionScanMonthly" /></th>
						</tr>
						<logic:iterate scope="request" name="distributionScanOptions" id="option">
							<tr>
								<td style="white-space:nowrap">
									<label><html:radio onclick="recalcMonthly();" property="distributionScanOption" idName="option" value="packageDefinitionLimit" />
									<ao:write name="option" property="display" /></label>
								</td>
								<td><ao:write name="option" property="priceDifference" /></td>
							</tr>
						</logic:iterate>
					</logic:notEmpty>
					<logic:notEmpty scope="request" name="failoverOptions">
						<tr>
							<th>
								<ao:message key="signupCustomizeManagementForm.selectFailover" /><ao:br />
								<html:errors bundle="/signup/ApplicationResources" property="failoverOption" />
							</th>
							<th><ao:message key="signupCustomizeManagementForm.failoverMonthly" /></th>
						</tr>
						<logic:iterate scope="request" name="failoverOptions" id="option">
							<tr>
								<td style="white-space:nowrap">
									<label><html:radio onclick="recalcMonthly();" property="failoverOption" idName="option" value="packageDefinitionLimit" />
									<ao:write name="option" property="display" /></label>
								</td>
								<td><ao:write name="option" property="priceDifference" /></td>
							</tr>
						</logic:iterate>
					</logic:notEmpty>
					<tr>
						<th><ao:message key="signupCustomizeManagementForm.hardwareRate.title" /></th>
						<th style="text-align:left">
							<ao:input type="hidden" name="formCompleted" value="true" />
							<ao:input type="text" name="hardwareRateDisplay" readonly="true" size="10" value="${requestScope.hardwareRate}" />
						</th>
					</tr>
					<tr>
						<th><ao:message key="signupCustomizeManagementForm.total" /></th>
						<th style="text-align:left"><ao:input type="text" name="totalMonthly" readonly='readonly' size="10" value="${requestScope.hardwareRate}" /></th>
					</tr>
				</tbody>
				<tfoot>
					<tr><td colspan="2" style="text-align:center"><ao:br /><ao:input type="submit" value="${ao:message('signupCustomizeManagementForm.submit.label')}" /><ao:br /><ao:br /></td></tr>
				</tfoot>
			</table>
		</ao:bundle>
	</skin:lightArea>
</div>
