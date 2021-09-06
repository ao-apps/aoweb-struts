<%--
aoweb-struts - Template webapp for legacy Struts-based site framework with AOServ Platform control panels.
Copyright (C) 2009, 2015, 2016, 2019, 2020, 2021  AO Industries, Inc.
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

<ao:bundle basename="com.aoindustries.web.struts.signup.i18n.ApplicationResources">
	<skin:path>/signup/colocation-5.do</skin:path>
	<skin:title><ao:message key="colocation.title" /></skin:title>
	<skin:navImageAlt><ao:message key="colocation.navImageAlt" /></skin:navImageAlt>
	<skin:keywords><ao:message key="colocation.keywords" /></skin:keywords>
	<skin:description><ao:message key="colocation.description" /></skin:description>
	<%@include file="add-parents.inc.jsp" %>
	<skin:skin>
		<skin:content width="600">
			<skin:contentTitle><ao:message key="colocation.title" /></skin:contentTitle>
			<skin:contentHorizontalDivider />
			<skin:contentLine>
				<ao:script>
					function selectStep(step) {
							 if(step=="colocation")   window.location.href=<ao:url>colocation.do</ao:url>;
						else if(step=="colocation-2") window.location.href=<ao:url>colocation-2.do</ao:url>;
						else if(step=="colocation-3") window.location.href=<ao:url>colocation-3.do</ao:url>;
						else if(step=="colocation-4") window.location.href=<ao:url>colocation-4.do</ao:url>;
					}
				</ao:script>
				<bean:define toScope="request" type="java.lang.String" id="stepNumber" value="5" />
				<bean:define type="java.lang.String" id="actionPrefix" toScope="request" value="colocation" />
				<%@include file="minimal-steps.inc.jsp" %>
				<ao:br />
				<form action="<ao:url>colocation-5-completed.do</ao:url>" method="post">
					<%@include file="minimal-confirmation.inc.jsp" %>
				</form>
			</skin:contentLine>
		</skin:content>
	</skin:skin>
</ao:bundle>
