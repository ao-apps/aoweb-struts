<%--
aoweb-struts-resources - Web resources for legacy Struts-based site framework with AOServ Platform control panels.
Copyright (C) 2009, 2015, 2016, 2019, 2020  AO Industries, Inc.
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

<ao:bundle basename="com.aoindustries.website.signup.ApplicationResources">
	<skin:path>/signup/backup-5.do</skin:path>
	<skin:title><ao:message key="backup.title" /></skin:title>
	<skin:navImageAlt><ao:message key="backup.navImageAlt" /></skin:navImageAlt>
	<skin:keywords><ao:message key="backup.keywords" /></skin:keywords>
	<skin:description><ao:message key="backup.description" /></skin:description>
	<%@include file="add-parents.inc.jsp" %>
	<skin:skin>
		<skin:content width="600">
			<skin:contentTitle><ao:message key="backup.title" /></skin:contentTitle>
			<skin:contentHorizontalDivider />
			<skin:contentLine>
				<ao:script>
					function selectStep(step) {
							 if(step=="backup")   window.location.href=<ao:url>backup.do</ao:url>;
						else if(step=="backup-2") window.location.href=<ao:url>backup-2.do</ao:url>;
						else if(step=="backup-3") window.location.href=<ao:url>backup-3.do</ao:url>;
						else if(step=="backup-4") window.location.href=<ao:url>backup-4.do</ao:url>;
					}
				</ao:script>
				<bean:define toScope="request" type="java.lang.String" id="stepNumber" value="5" />
				<bean:define type="java.lang.String" id="actionPrefix" toScope="request" value="backup" />
				<%@include file="minimal-steps.inc.jsp" %>
				<ao:br />
				<form action="<ao:url>backup-5-completed.do</ao:url>" method="post">
					<%@include file="minimal-confirmation.inc.jsp" %>
				</form>
			</skin:contentLine>
		</skin:content>
	</skin:skin>
</ao:bundle>
