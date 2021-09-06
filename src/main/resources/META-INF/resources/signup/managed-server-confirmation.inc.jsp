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

<skin:lightArea>
	<ao:bundle basename="com.aoindustries.web.struts.signup.i18n.ApplicationResources">
		<table class="ao-packed">
			<thead>
				<tr><td colspan="3"><b><ao:message key="serverConfirmation.stepLabel" /></b><ao:br /><ao:hr /></td></tr>
				<tr><td colspan="3"><i><ao:message key="serverConfirmation.stepHelp" /></i></td></tr>
			</thead>
			<tbody>
				<tr><td colspan="3">&#160;</td></tr>
				<tr>
					<th colspan="3">
						<table class="ao-packed" style="width:100%">
							<tbody>
								<tr>
									<th><ao:message key="steps.selectServer.label" /></th>
									<td style="text-align:right"><ao:a class="aoLightLink" href='${actionPrefix}.do'><ao:message key="serverConfirmation.edit.link" /></ao:a></td>
								</tr>
							</tbody>
						</table>
					</th>
				</tr>
				<%@include file="signup-select-server-confirmation.inc.jsp" %>
				<tr><td colspan="3">&#160;</td></tr>
				<tr>
					<th colspan="3">
						<table class="ao-packed" style="width:100%">
							<tbody>
								<tr>
									<th><ao:message key="steps.customizeServer.label" /></th>
									<td style="text-align:right"><ao:a class="aoLightLink" href='${actionPrefix}-2.do'><ao:message key="serverConfirmation.edit.link" /></ao:a></td>
								</tr>
							</tbody>
						</table>
					</th>
				</tr>
				<%@include file="signup-customize-server-confirmation.inc.jsp" %>
				<tr><td colspan="3">&#160;</td></tr>
				<tr>
					<th colspan="3">
						<table class="ao-packed" style="width:100%">
							<tbody>
								<tr>
									<th><ao:message key="steps.customizeManagement.label" /></th>
									<td style="text-align:right"><ao:a class="aoLightLink" href='${actionPrefix}-3.do'><ao:message key="serverConfirmation.edit.link" /></ao:a></td>
								</tr>
							</tbody>
						</table>
					</th>
				</tr>
				<%@include file="signup-customize-management-confirmation.inc.jsp" %>
				<tr><td colspan="3">&#160;</td></tr>
				<tr>
					<th colspan="3">
						<table class="ao-packed" style="width:100%">
							<tbody>
								<tr>
									<th><ao:message key="steps.organizationInfo.label" /></th>
									<td style="text-align:right"><ao:a class="aoLightLink" href='${actionPrefix}-4.do'><ao:message key="serverConfirmation.edit.link" /></ao:a></td>
								</tr>
							</tbody>
						</table>
					</th>
				</tr>
				<%@include file="signup-organization-confirmation.inc.jsp" %>
				<tr><td colspan="3">&#160;</td></tr>
				<tr>
					<th colspan="3">
						<table class="ao-packed" style="width:100%">
							<tbody>
								<tr>
									<th><ao:message key="steps.technicalInfo.label" /></th>
									<td style="text-align:right"><ao:a class="aoLightLink" href='${actionPrefix}-5.do'><ao:message key="serverConfirmation.edit.link" /></ao:a></td>
								</tr>
							</tbody>
						</table>
					</th>
				</tr>
				<%@include file="signup-technical-confirmation.inc.jsp" %>
				<tr><td colspan="3">&#160;</td></tr>
				<tr>
					<th colspan="3">
						<table class="ao-packed" style="width:100%">
							<tbody>
								<tr>
									<th><ao:message key="steps.billingInformation.label" /></th>
									<td style="text-align:right"><ao:a class="aoLightLink" href='${actionPrefix}-6.do'><ao:message key="serverConfirmation.edit.link" /></ao:a></td>
								</tr>
							</tbody>
						</table>
					</th>
				</tr>
				<%@include file="signup-billing-information-confirmation.inc.jsp" %>
			</tbody>
			<tfoot>
				<tr><td colspan="3" style="text-align:center"><ao:br /><ao:input type="submit" value="${ao:message('serverConfirmation.submit.label')}" /><ao:br /><ao:br /></td></tr>
			</tfoot>
		</table>
	</ao:bundle>
</skin:lightArea>
