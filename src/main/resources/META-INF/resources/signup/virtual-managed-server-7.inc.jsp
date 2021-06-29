<%--
aoweb-struts-resources - Web resources for legacy Struts-based site framework with AOServ Platform control panels.
Copyright (C) 2007-2009, 2015, 2016, 2019, 2020, 2021  AO Industries, Inc.
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

<ao:bundle basename="com.aoindustries.web.struts.signup.i18n.ApplicationResources">
	<skin:path>/signup/virtual-managed-server-7.do</skin:path>
	<skin:title><ao:message key="virtualManaged.title" /></skin:title>
	<skin:navImageAlt><ao:message key="virtualManaged.navImageAlt" /></skin:navImageAlt>
	<skin:keywords><ao:message key="virtualManaged.keywords" /></skin:keywords>
	<skin:description><ao:message key="virtualManaged.description" /></skin:description>
	<%@include file="add-parents.inc.jsp" %>
	<skin:skin>
		<skin:content width="600">
			<skin:contentTitle><ao:message key="virtualManaged.title" /></skin:contentTitle>
			<skin:contentHorizontalDivider />
			<skin:contentLine>
				<ao:script>
					function selectStep(step) {
							 if(step=="virtual-managed-server")   window.location.href=<ao:url>virtual-managed-server.do</ao:url>;
						else if(step=="virtual-managed-server-2") window.location.href=<ao:url>virtual-managed-server-2.do</ao:url>;
						else if(step=="virtual-managed-server-3") window.location.href=<ao:url>virtual-managed-server-3.do</ao:url>;
						else if(step=="virtual-managed-server-4") window.location.href=<ao:url>virtual-managed-server-4.do</ao:url>;
						else if(step=="virtual-managed-server-5") window.location.href=<ao:url>virtual-managed-server-5.do</ao:url>;
						else if(step=="virtual-managed-server-6") window.location.href=<ao:url>virtual-managed-server-6.do</ao:url>;
					}
				</ao:script>
				<bean:define toScope="request" type="java.lang.String" id="stepNumber" value="7" />
				<bean:define type="java.lang.String" id="actionPrefix" toScope="request" value="virtual-managed-server" />
				<%@include file="managed-server-steps.inc.jsp" %>
				<ao:br />
				<form action="<ao:url>virtual-managed-server-7-completed.do</ao:url>" method="post">
					<%@include file="managed-server-confirmation.inc.jsp" %>
				</form>
			</skin:contentLine>
		</skin:content>
	</skin:skin>
</ao:bundle>
