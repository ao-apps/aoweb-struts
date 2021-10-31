<%--
aoweb-struts - Template webapp for legacy Struts-based site framework with AOServ Platform control panels.
Copyright (C) 2000-2009, 2015, 2016, 2019, 2020, 2021  AO Industries, Inc.
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
along with aoweb-struts.  If not, see <https://www.gnu.org/licenses/>.
--%>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/taglibs.jspf" %>

<%@include file="add-parents.jspf" %>
<ao:bundle basename="com.aoindustries.web.struts.clientarea.ticket.i18n.ApplicationResources">
	<skin:path>
		/clientarea/ticket/edit.do
		<ao:param name="pkey" value="${ticket.pkey}" />
	</skin:path>
	<skin:title><ao:message key="edit.title" /></skin:title>
	<skin:navImageAlt><ao:message key="edit.navImageAlt" /></skin:navImageAlt>
	<skin:keywords><ao:message key="edit.keywords" /></skin:keywords>
	<skin:description><ao:message key="edit.description" /></skin:description>
	<skin:skin>
		<skin:content>
			<skin:contentTitle><ao:message key="edit.title" /></skin:contentTitle>
			<skin:contentHorizontalDivider />
			<skin:contentLine>
				<logic:present scope="request" name="permissionDenied">
					<%@include file="../../permission-denied.jspf" %>
				</logic:present>
				<logic:notPresent scope="request" name="permissionDenied">
					<skin:lightArea>
						<b><ao:message key="edit-completed.ticketChanges" /></b>
						<ao:hr />
						<ul>
							<logic:equal scope="request" name="accountUpdated" value="true">
								<li><ao:message key="edit-completed.accountUpdated" /></li>
							</logic:equal>
							<logic:equal scope="request" name="contactEmailsUpdated" value="true">
								<li><ao:message key="edit-completed.contactEmailsUpdated" /></li>
							</logic:equal>
							<logic:equal scope="request" name="contactPhoneNumbersUpdated" value="true">
								<li><ao:message key="edit-completed.contactPhoneNumbersUpdated" /></li>
							</logic:equal>
							<logic:equal scope="request" name="clientPriorityUpdated" value="true">
								<li><ao:message key="edit-completed.clientPriorityUpdated" /></li>
							</logic:equal>
							<logic:equal scope="request" name="summaryUpdated" value="true">
								<li><ao:message key="edit-completed.summaryUpdated" /></li>
							</logic:equal>
							<logic:equal scope="request" name="annotationAdded" value="true">
								<li><ao:message key="edit-completed.annotationAdded" /></li>
							</logic:equal>
							<logic:equal scope="request" name="nothingChanged" value="true">
								<li><ao:message key="edit-completed.nothingChanged" /></li>
							</logic:equal>
						</ul>
						<ao:message key="edit-completed.index.backTo" /><ao:a href="/clientarea/ticket/index.do"><ao:message key="edit-completed.index.link" /></ao:a>
					</skin:lightArea>
				</logic:notPresent>
			</skin:contentLine>
		</skin:content>
	</skin:skin>
</ao:bundle>
