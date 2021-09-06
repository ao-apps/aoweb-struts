<%--
aoweb-struts - Template webapp for legacy Struts-based site framework with AOServ Platform control panels.
Copyright (C) 2000-2009, 2016, 2018, 2019, 2020, 2021  AO Industries, Inc.
	support@aoindustries.com
	7262 Bull Pen Cir
	Mobile, AL 36695

This file is part of aoweb-struts.

aoweb-struts is free software: you can redistribute it and/or modify
it under the terms of the GNU Lesser General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

aoweb-struts is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public License
along with aoweb-struts.  If not, see <http://www.gnu.org/licenses/>.
--%>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@include file="/_taglibs.inc.jsp" %>

<%@include file="add-parents.inc.jsp" %>
<%@include file="create.meta.inc.jsp" %>
<skin:skin onload="document.forms['ticketForm'].summary.select(); document.forms['ticketForm'].summary.focus();">
	<skin:content>
		<ao:bundle basename="com.aoindustries.web.struts.clientarea.ticket.i18n.ApplicationResources">
			<skin:contentTitle><ao:message key="create.title" /></skin:contentTitle>
			<skin:contentHorizontalDivider />
			<skin:contentLine>
				<logic:present scope="request" name="permissionDenied">
					<%@include file="../../_permission-denied.inc.jsp" %>
				</logic:present>
				<logic:notPresent scope="request" name="permissionDenied">
					<html:javascript staticJavascript='false' bundle="/clientarea/ticket/ApplicationResources" formName="ticketForm" />
					<html:form action="/create-completed" onsubmit="return validateTicketForm(this);">
						<skin:lightArea>
							<table class="ao-spread">
								<tbody>
									<tr>
										<td style="white-space:nowrap"><ao:message key="TicketForm.field.account.prompt" /></td>
										<td>
											<logic:notEqual name="aoConn" property="account.Account.size" value="1">
												<html:select property="account">
													<logic:empty scope="request" name="ticketForm" property="account">
														<html:option value="" />
													</logic:empty>
													<html:optionsCollection name="aoConn" property="account.Account.rows" label="name" value="name" />
												</html:select>
											</logic:notEqual>
											<logic:equal name="aoConn" property="account.Account.size" value="1">
												<html:hidden property="account" write="true" />
											</logic:equal>
										</td>
										<td><html:errors bundle="/clientarea/ticket/ApplicationResources" property="account" /></td>
									</tr>
									<tr>
										<td style="white-space:nowrap"><ao:message key="TicketForm.field.contactEmails.prompt" /></td>
										<td><html:textarea property="contactEmails" cols="40" rows="3" /></td>
										<td><html:errors bundle="/clientarea/ticket/ApplicationResources" property="contactEmails" /></td>
									</tr>
									<tr>
										<td style="white-space:nowrap"><ao:message key="TicketForm.field.contactPhoneNumbers.prompt" /></td>
										<td><html:textarea property="contactPhoneNumbers" cols="40" rows="3" /></td>
										<td><html:errors bundle="/clientarea/ticket/ApplicationResources" property="contactPhoneNumbers" /></td>
									</tr>
									<tr>
										<td style="white-space:nowrap"><ao:message key="TicketForm.field.clientPriority.prompt" /></td>
										<td>
											<html:select property="clientPriority">
												<html:optionsCollection name="aoConn" property="ticket.Priority.rows" label="priority" value="priority" />
											</html:select>
										</td>
										<td><html:errors bundle="/clientarea/ticket/ApplicationResources" property="clientPriority" /></td>
									</tr>
									<tr>
										<td style="white-space:nowrap"><ao:message key="TicketForm.field.summary.prompt" /></td>
										<td><html:text property="summary" size="60" /></td>
										<td><html:errors bundle="/clientarea/ticket/ApplicationResources" property="summary" /></td>
									</tr>
									<tr>
										<td style='white-space:nowrap' colspan="3">
											<ao:br />
											<ao:message key="TicketForm.field.details.prompt" /><ao:br />
											<html:textarea property="details" cols="80" rows="20" /><ao:br />
											<%--<textarea name="details" cols="80" rows="20" wrap="hard"><bean:write scope="request" name="ticketForm" property="details"/></textarea><ao:br />--%>
											<html:errors bundle="/clientarea/ticket/ApplicationResources" property="details" />
										</td>
									</tr>
								</tbody>
								<tfoot>
									<tr>
										<td colspan="3" style="text-align:center">
											<ao:br />
											<ao:input type="submit" value="${ao:message('create.field.submit.label')}" />
										</td>
									</tr>
								</tfoot>
							</table>
						</skin:lightArea>
					</html:form>
				</logic:notPresent>
			</skin:contentLine>
		</ao:bundle>
	</skin:content>
</skin:skin>
