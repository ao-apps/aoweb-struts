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
along with aoweb-struts.  If not, see <http://www.gnu.org/licenses/>.
--%>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/taglibs.jspf" %>

<%@include file="add-parents.jspf" %>
<ao:bundle basename="com.aoindustries.web.struts.clientarea.ticket.i18n.ApplicationResources">
	<skin:path>/clientarea/ticket/create.do</skin:path>
	<skin:title><ao:message key="create.title" /></skin:title>
	<skin:navImageAlt><ao:message key="create.navImageAlt" /></skin:navImageAlt>
	<skin:keywords><ao:message key="create.keywords" /></skin:keywords>
	<skin:description><ao:message key="create.description" /></skin:description>
	<skin:skin>
		<skin:content>
			<skin:contentTitle><ao:message key="create.title" /></skin:contentTitle>
			<skin:contentHorizontalDivider />
			<skin:contentLine>
				<logic:present scope="request" name="permissionDenied">
					<%@include file="../../permission-denied.jspf" %>
				</logic:present>
				<logic:notPresent scope="request" name="permissionDenied">
					<skin:lightArea>
						<ao:message type="xhtml" key="create-completed.message" />
						<ao:a href="/clientarea/ticket/edit.do" param.pkey="${requestScope.pkey}"><ao:write scope="request" name="pkey" /></ao:a>
					</skin:lightArea>
				</logic:notPresent>
			</skin:contentLine>
		</skin:content>
	</skin:skin>
</ao:bundle>
