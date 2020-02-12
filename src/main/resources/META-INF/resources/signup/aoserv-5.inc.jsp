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
	<skin:path>/signup/aoserv-5.do</skin:path>
	<skin:title><ao:message key="aoserv.title" /></skin:title>
	<skin:navImageAlt><ao:message key="aoserv.navImageAlt" /></skin:navImageAlt>
	<skin:keywords><ao:message key="aoserv.keywords" /></skin:keywords>
	<skin:description><ao:message key="aoserv.description" /></skin:description>
	<%@include file="add-parents.inc.jsp" %>
	<skin:skin>
		<skin:content width="600">
			<skin:contentTitle><ao:message key="aoserv.title" /></skin:contentTitle>
			<skin:contentHorizontalDivider />
			<skin:contentLine>
				<ao:script>
					function selectStep(step) {
							 if(step=="aoserv")   window.location.href=<ao:url>aoserv.do</ao:url>;
						else if(step=="aoserv-2") window.location.href=<ao:url>aoserv-2.do</ao:url>;
						else if(step=="aoserv-3") window.location.href=<ao:url>aoserv-3.do</ao:url>;
						else if(step=="aoserv-4") window.location.href=<ao:url>aoserv-4.do</ao:url>;
					}
				</ao:script>
				<bean:define toScope="request" type="java.lang.String" id="stepNumber" value="5" />
				<bean:define type="java.lang.String" id="actionPrefix" toScope="request" value="aoserv" />
				<%@include file="minimal-steps.inc.jsp" %>
				<ao:br />
				<form action="<ao:url>aoserv-5-completed.do</ao:url>" method="post">
					<%@include file="minimal-confirmation.inc.jsp" %>
				</form>
			</skin:contentLine>
		</skin:content>
	</skin:skin>
</ao:bundle>
