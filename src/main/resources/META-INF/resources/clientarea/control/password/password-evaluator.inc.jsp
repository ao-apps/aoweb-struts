<%--
aoweb-struts-resources - Template webapp for legacy Struts-based site framework with AOServ Platform control panels.
Copyright (C) 2000-2009, 2016, 2018, 2019, 2020, 2021  AO Industries, Inc.
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

<%@include file="add-parents.inc.jsp" %>
<%@include file="password-evaluator.meta.inc.jsp" %>
<skin:skin onload="document.forms.passwordEvaluatorForm.password.select(); document.forms.passwordEvaluatorForm.password.focus();">
	<skin:content width="600">
		<ao:bundle basename="com.aoindustries.web.struts.clientarea.control.i18n.ApplicationResources">
			<skin:contentTitle><ao:message key="password.passwordEvaluator.title" /></skin:contentTitle>
			<skin:contentHorizontalDivider />
			<skin:contentLine align="center">
				<html:javascript staticJavascript='false' bundle="/clientarea/control/ApplicationResources" formName="passwordEvaluatorForm" />
				<skin:lightArea align="left">
					<html:form action="/password/password-evaluator-completed" onsubmit="return validatePasswordEvaluatorForm(this);">
						<div>
							<b><ao:message key="password.passwordEvaluator.prompt" /></b>
							<ao:hr />
							<ao:message key="password.passwordEvaluator.field.password.prompt" /><html:password size="16" property="password" /> <html:errors bundle="/clientarea/control/ApplicationResources" property="password" />
							<logic:present scope="request" name="results">
								<ao:br /><ao:br />
								<table class="ao-spread">
									<tbody>
										<logic:iterate scope="request" name="results" id="result" type="com.aoindustries.aoserv.client.password.PasswordChecker.Result">
											<tr>
												<td><ao:write name="result" property="category" />:</td>
												<td><ao:write name="result" property="result" /></td>
											</tr>
										</logic:iterate>
									</tbody>
								</table>
							</logic:present><ao:br />
							<ao:br />
							<div style="text-align:center"><ao:input type="submit" value="${ao:message('password.passwordEvaluator.field.submit.label')}" /></div>
						</div>
					</html:form>
				</skin:lightArea>
			</skin:contentLine>
		</ao:bundle>
	</skin:content>
</skin:skin>
