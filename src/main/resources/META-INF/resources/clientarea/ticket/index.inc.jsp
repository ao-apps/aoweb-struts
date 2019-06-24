<%--
aoweb-struts-resources - Web resources for legacy Struts-based site framework with AOServ Platform control panels.
Copyright (C) 2000-2009, 2016, 2018  AO Industries, Inc.
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
<%@ page import="com.aoindustries.aoserv.client.ticket.Ticket" %>
<%@include file="/_taglibs.inc.jsp" %>

<skin:setContentType />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html:html lang="true" xhtml="true">
	<%@include file="../add-parents.inc.jsp" %>
	<%@include file="index.meta.inc.jsp" %>
	<%@include file="index.children.inc.jsp" %>
	<skin:skin>
		<skin:content width="600">
			<fmt:bundle basename="com.aoindustries.website.clientarea.ticket.ApplicationResources">
				<skin:contentTitle><fmt:message key="index.title" /></skin:contentTitle>
				<skin:contentHorizontalDivider />
				<skin:contentLine>
					<div style="text-align:left; padding-top:10px; padding-bottom:10px">
						<html:link action="/create"><fmt:message key="index.link.create" /></html:link>
					</div>
					<skin:lightArea>
						<table cellspacing="0" cellpadding="5">
							<tr>
								<th><fmt:message key="index.header.pkey" /></th>
								<th><fmt:message key="index.header.clientPriority" /></th>
								<th><fmt:message key="index.header.status" /></th>
								<th><fmt:message key="index.header.openDate" /></th>
								<th><fmt:message key="index.header.createdBy" /></th>
								<logic:notEqual name="aoConn" property="account.Account.size" value="1">
									<th><fmt:message key="index.header.accounting" /></th>
								</logic:notEqual>
								<th><fmt:message key="index.header.summary" /></th>
							</tr>
							<logic:empty name="tickets">
								<tr>
									<td colspan="7" align="center">
										<fmt:message key="index.noTickets" />
									</td>
								</tr>
							</logic:empty>
							<logic:notEmpty name="tickets">
								<logic:iterate name="tickets" id="ticket" type="com.aoindustries.aoserv.client.ticket.Ticket">
									<skin:lightDarkTableRow pageAttributeId="isDark">
										<td style="white-space:nowrap"><html:link action="/edit" paramId="pkey" paramName="ticket" paramProperty="pkey"><ao:write name="ticket" property="pkey" /></html:link></td>
										<td style="white-space:nowrap">
											<logic:equal name="ticket" property="status.status" value="<%= com.aoindustries.aoserv.client.ticket.Status.CLOSED %>">
												<logic:equal name="isDark" value="true">
													<ao:img height="25" width="25" src="p_dark_black.gif" alt="" style="vertical-align:middle;" />&#160;
												</logic:equal>
												<logic:equal name="isDark" value="false">
													<ao:img height="25" width="25" src="p_light_black.gif" alt=""  style="vertical-align:middle;" />&#160;
												</logic:equal>
											</logic:equal>
											<logic:notEqual name="ticket" property="status.status" value="<%= com.aoindustries.aoserv.client.ticket.Status.CLOSED %>">
												<logic:equal name="ticket" property="clientPriority.priority" value="<%= com.aoindustries.aoserv.client.ticket.Priority.LOW %>">
													<logic:equal name="isDark" value="true">
														<ao:img height="25" width="25" src="p_dark_green.gif" alt="" style="vertical-align:middle;" />&#160;
													</logic:equal>
													<logic:equal name="isDark" value="false">
														<ao:img height="25" width="25" src="p_light_green.gif" alt="" style="vertical-align:middle;" />&#160;
													</logic:equal>
												</logic:equal>
												<logic:equal name="ticket" property="clientPriority.priority" value="<%= com.aoindustries.aoserv.client.ticket.Priority.NORMAL %>">
													<logic:equal name="isDark" value="true">
														<ao:img height="25" width="25" src="p_dark_yellow.gif" alt="" style="vertical-align:middle;" />&#160;
													</logic:equal>
													<logic:equal name="isDark" value="false">
														<ao:img height="25" width="25" src="p_light_yellow.gif" alt="" style="vertical-align:middle;" />&#160;
													</logic:equal>
												</logic:equal>
												<logic:equal name="ticket" property="clientPriority.priority" value="<%= com.aoindustries.aoserv.client.ticket.Priority.HIGH %>">
													<logic:equal name="isDark" value="true">
														<ao:img height="25" width="25" src="p_dark_orange.gif" alt="" style="vertical-align:middle;" />&#160;
													</logic:equal>
													<logic:equal name="isDark" value="false">
														<ao:img height="25" width="25" src="p_light_orange.gif" alt="" style="vertical-align:middle;" />&#160;
													</logic:equal>
												</logic:equal>
												<logic:equal name="ticket" property="clientPriority.priority" value="<%= com.aoindustries.aoserv.client.ticket.Priority.URGENT %>">
													<logic:equal name="isDark" value="true">
														<ao:img height="25" width="25" src="p_dark_red.gif" alt="" style="vertical-align:middle;" />&#160;
													</logic:equal>
													<logic:equal name="isDark" value="false">
														<ao:img height="25" width="25" src="p_light_red.gif" alt="" style="vertical-align:middle;" />&#160;
													</logic:equal>
												</logic:equal>
											</logic:notEqual>
											<ao:write name="ticket" property="clientPriority" />
										</td>
										<td style="white-space:nowrap"><ao:write name="ticket" property="status" type="application/xhtml+xml" /></td>
										<td style="white-space:nowrap"><aoweb:dateTime><ao:write name="ticket" property="openDate.time" /></aoweb:dateTime></td>
										<td style="white-space:nowrap">
											<logic:notEmpty name="ticket" property="createdBy">
												<ao:write name="ticket" property="createdBy.name" />
											</logic:notEmpty>
											<logic:empty  name="ticket" property="createdBy">
												<logic:notEmpty name="siteSettings" property='<%= "rootAOServConnector.ticket.Ticket.map("+ticket.getPkey()+")" %>'>
													<logic:notEmpty name="siteSettings" property='<%= "rootAOServConnector.ticket.Ticket.map("+ticket.getPkey()+").createdBy" %>'>
														<ao:write name="siteSettings" property='<%= "rootAOServConnector.ticket.Ticket.map("+ticket.getPkey()+").createdBy.name" %>' />
													</logic:notEmpty>
												</logic:notEmpty>
											</logic:empty>
										</td>
										<logic:notEqual name="aoConn" property="account.Account.size" value="1">
											<td style="white-space:nowrap"><ao:write name="ticket" property="business" /></td>
										</logic:notEqual>
										<td style="white-space:nowrap"><ao:write name="ticket" property="summary" /></td>
									</skin:lightDarkTableRow>
								</logic:iterate>
							</logic:notEmpty>
						</table>
					</skin:lightArea>
					<div style="text-align:left; padding-top:10px; padding-bottom:10px">
						<html:link action="/create"><fmt:message key="index.link.create" /></html:link>
					</div>
				</skin:contentLine>
			</fmt:bundle>
		</skin:content>
	</skin:skin>
</html:html>