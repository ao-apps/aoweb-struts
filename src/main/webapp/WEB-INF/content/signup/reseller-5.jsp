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
<%@include file="/WEB-INF/taglibs.jspf" %>

<ao:bundle basename="com.aoindustries.web.struts.signup.i18n.ApplicationResources">
	<skin:path>/signup/reseller-5.do</skin:path>
	<skin:title><ao:message key="reseller.title" /></skin:title>
	<skin:navImageAlt><ao:message key="reseller.navImageAlt" /></skin:navImageAlt>
	<skin:keywords><ao:message key="reseller.keywords" /></skin:keywords>
	<skin:description><ao:message key="reseller.description" /></skin:description>
	<%@include file="add-parents.inc.jsp" %>
	<skin:skin>
		<skin:content width="600">
			<skin:contentTitle><ao:message key="reseller.title" /></skin:contentTitle>
			<skin:contentHorizontalDivider />
			<skin:contentLine>
				<ao:script>
					function selectStep(step) {
							 if(step=="reseller")   window.location.href=<ao:url>reseller.do</ao:url>;
						else if(step=="reseller-2") window.location.href=<ao:url>reseller-2.do</ao:url>;
						else if(step=="reseller-3") window.location.href=<ao:url>reseller-3.do</ao:url>;
						else if(step=="reseller-4") window.location.href=<ao:url>reseller-4.do</ao:url>;
					}
				</ao:script>
				<bean:define toScope="request" type="java.lang.String" id="stepNumber" value="5" />
				<bean:define type="java.lang.String" id="actionPrefix" toScope="request" value="reseller" />
				<%@include file="minimal-steps.jspf" %>
				<ao:br />
				<form action="<ao:url>reseller-5-completed.do</ao:url>" method="post">
					<%@include file="minimal-confirmation.jspf" %>
				</form>
			</skin:contentLine>
		</skin:content>
	</skin:skin>
</ao:bundle>
