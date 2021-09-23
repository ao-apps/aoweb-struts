<%--
aoweb-struts - Template webapp for legacy Struts-based site framework with AOServ Platform control panels.
Copyright (C) 2000-2009, 2016, 2018, 2019, 2020, 2021  AO Industries, Inc.
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
<%@include file="administrator-password-setter.meta.jspf" %>
<skin:skin>
	<skin:content width="600">
		<ao:bundle basename="com.aoindustries.web.struts.clientarea.control.password.i18n.ApplicationResources">
			<skin:contentTitle><ao:message key="administratorPasswordSetter.title" /></skin:contentTitle>
			<skin:contentHorizontalDivider />
			<skin:contentLine>
				<logic:empty scope="request" name="administratorPasswordSetterForm" property="packages">
					<b><ao:message key="administratorPasswordSetter.noAccounts" /></b>
				</logic:empty>
				<logic:notEmpty scope="request" name="administratorPasswordSetterForm" property="packages">
					<html:form action="/password/administrator-password-setter-completed">
						<skin:lightArea>
							<table class="ao-no-border">
								<thead>
									<tr>
										<bean:size scope="request" name="aoConn" property="billing.Package.map" id="packagesSize" />
										<logic:greaterThan name="packagesSize" value="1">
											<th><ao:message key="administratorPasswordSetter.header.package" /></th>
										</logic:greaterThan>
										<th><ao:message key="administratorPasswordSetter.header.username" /></th>
										<th colspan='2'><ao:message key="administratorPasswordSetter.header.newPassword" /></th>
										<th><ao:message key="administratorPasswordSetter.header.confirmPassword" /></th>
										<th>&#160;</th>
									</tr>
								</thead>
								<tbody>
									<logic:iterate scope="request" name="administratorPasswordSetterForm" property="packages" id="pack" indexId="index">
										<tr>
											<logic:greaterThan name="packagesSize" value="1">
												<td><ao:write name="pack" /></td>
											</logic:greaterThan>
											<td>
												<html:hidden property='<%= "packages[" + index + "]" %>' />
												<code><html:hidden property='<%= "usernames[" + index + "]" %>' write="true" /></code>
											</td>
											<td><html:password size="20" property='<%= "newPasswords[" + index + "]" %>' /></td>
											<td style="white-space:nowrap">
												<html:errors bundle="/clientarea/control/ApplicationResources" property='<%= "newPasswords[" + index + "].newPasswords" %>' />
												<html:messages id="message" message="true" bundle="/clientarea/control/ApplicationResources" property='<%= "newPasswords[" + index + "].newPasswords" %>'>
													<ao:write name="message" /><ao:br />
												</html:messages>
											</td>
											<td><html:password size="20" property='<%= "confirmPasswords[" + index + "]" %>' /></td>
											<td style="white-space:nowrap">
												<html:errors bundle="/clientarea/control/ApplicationResources" property='<%= "confirmPasswords[" + index + "].confirmPasswords" %>' />
												<html:messages id="message" message="true" bundle="/clientarea/control/ApplicationResources" property='<%= "confirmPasswords[" + index + "].confirmPasswords" %>'>
													<ao:write name="message" /><ao:br />
												</html:messages>
											</td>
										</tr>
									</logic:iterate>
								</tbody>
								<tfoot>
									<tr><td colspan="6" style="text-align:center"><ao:input type="submit" value="${ao:message('administratorPasswordSetter.field.submit.label')}" /></td></tr>
								</tfoot>
							</table>
						</skin:lightArea>
					</html:form>
				</logic:notEmpty>
			</skin:contentLine>
		</ao:bundle>
	</skin:content>
</skin:skin>
