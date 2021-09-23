<%--
aoweb-struts - Template webapp for legacy Struts-based site framework with AOServ Platform control panels.
Copyright (C) 2003-2009, 2015, 2016, 2018, 2019, 2020, 2021  AO Industries, Inc.
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
<ao:bundle basename="com.aoindustries.web.struts.clientarea.control.account.i18n.ApplicationResources">
	<skin:path>/clientarea/control/account/cancel-feedback.do?account=${ao:encodeURIComponent(account.name)}</skin:path>
	<logic:equal name="siteSettings" property="brand.aowebStrutsNoindex" value="true"><skin:meta name="robots">noindex</skin:meta></logic:equal>
	<skin:title><ao:message key="cancel.title" /></skin:title>
	<skin:navImageAlt><ao:message key="cancel.navImageAlt" /></skin:navImageAlt>
	<skin:keywords><ao:message key="cancel.keywords" /></skin:keywords>
	<skin:description><ao:message key="cancel.description" /></skin:description>
	<skin:skin>
		<skin:content width="600">
			<skin:contentTitle><ao:message key="cancel.title" /></skin:contentTitle>
			<skin:contentHorizontalDivider />
			<skin:contentLine>
				<logic:present scope="request" name="permissionDenied">
					<%@include file="../../../permission-denied.jspf" %>
				</logic:present>
				<logic:notPresent scope="request" name="permissionDenied">
					<skin:lightArea>
						<ao:message type="xhtml" key="cancel-feedback.prompt" arg0="${fn:escapeXml(account.name)}" />
						<html:form action="/account/cancel-feedback-completed">
							<div>
								<html:hidden property="account" />
								<html:textarea property="reason" rows="16" cols="80" />
							</div>
							<div style="text-align:center; margin-top:1em;">
								<ao:input type="submit" value="${ao:message('cancel-feedback.submit.label')}" />
							</div>
						</html:form>
					</skin:lightArea>
				</logic:notPresent>
			</skin:contentLine>
		</skin:content>
	</skin:skin>
</ao:bundle>
