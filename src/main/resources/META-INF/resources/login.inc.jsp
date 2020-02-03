<%--
aoweb-struts-resources - Web resources for legacy Struts-based site framework with AOServ Platform control panels.
Copyright (C) 2007-2009, 2015, 2016, 2019, 2020  AO Industries, Inc.
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

<fmt:bundle basename="com.aoindustries.website.ApplicationResources">
	<skin:path>/login.do</skin:path>
	<skin:meta name="robots">noindex</skin:meta>
	<skin:title><fmt:message key="login.title" /></skin:title>
	<skin:navImageAlt><fmt:message key="login.navImageAlt" /></skin:navImageAlt>
	<skin:keywords><fmt:message key="login.keywords" /></skin:keywords>
	<skin:description><fmt:message key="login.description" /></skin:description>
	<aoweb:exists path="/add-parents.inc.jsp"><jsp:include page="/add-parents.inc.jsp" /></aoweb:exists>
	<skin:skin onload="document.forms['loginForm'].username.focus(); document.forms['loginForm'].username.select();">
		<skin:content width="600">
			<skin:contentTitle><fmt:message key="login.title" /></skin:contentTitle>
			<skin:contentHorizontalDivider />
			<skin:contentLine>
				<html:javascript staticJavascript='false' bundle="/ApplicationResources" formName="loginForm" />
				<skin:lightArea>
					<b>
						<logic:present scope="request" name="authenticationMessage"><ao:write scope="request" name="authenticationMessage" type="application/xhtml+xml" /></logic:present>
						<logic:notPresent scope="request" name="authenticationMessage"><fmt:message key="login.pleaseLogin" /></logic:notPresent>
					</b>
					<ao:hr />
					<html:form action="/login-completed" onsubmit="return validateLoginForm(this);" style="display:inline;"><div><%-- display:inline for IE6 only --%>
						<%-- Add the authenticationTarget to the form because the new session could expire before they login and lost their target --%>
						<logic:present scope="session" name="authenticationTarget">
							<ao:input type="hidden" name="authenticationTarget"><ao:write type="text/url" scope="session" name="authenticationTarget" /></ao:input>
						</logic:present>
						<table class="noborder">
							<tbody>
								<tr>
									<td><fmt:message key="login.field.username.prompt" /></td>
									<td><html:text size="16" property="username" /></td>
									<td><html:errors bundle="/ApplicationResources" property="username" /></td>
								</tr>
								<tr>
									<td><fmt:message key="login.field.password.prompt" /></td>
									<td><html:password size="16" property="password" /></td>
									<td><html:errors bundle="/ApplicationResources" property="password" /></td>
								</tr>
							</tbody>
							<tfoot>
								<tr>
									<td colspan="3" style="text-align:center"><ao:input type="submit"><fmt:message key="login.field.submit.label" /></ao:input></td>
								</tr>
							</tfoot>
						</table>
					</div></html:form>
				</skin:lightArea>
			</skin:contentLine>
		</skin:content>
	</skin:skin>
</fmt:bundle>
