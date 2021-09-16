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

<aoweb:exists path="/WEB-INF/content/add-parents.inc.jsp"><jsp:include page="/WEB-INF/content/add-parents.inc.jsp" /></aoweb:exists>
<%@include file="contact.meta.inc.jsp" %>
<skin:skin onload="document.forms['contactForm'].from.select(); document.forms['contactForm'].from.focus();">
	<skin:content colspans="3" width="600">
		<ao:bundle basename="com.aoindustries.web.struts.i18n.ApplicationResources">
			<skin:contentTitle><ao:message key="contact.title" /></skin:contentTitle>
			<skin:contentHorizontalDivider colspansAndDirections="1,down,1" />
			<skin:contentLine>
				<ao:message key="contact.text.mainDescription" />
				<skin:contentVerticalDivider />
				<skin:lightArea>
					<table class="ao-no-border">
						<tbody>
							<tr>
								<th style='white-space:nowrap' colspan='2'><ao:message key="contact.header.dayPhone" /></th>
							</tr>
							<logic:notEmpty name="siteSettings" property="brand.supportTollFree">
								<tr>
									<td style="white-space:nowrap"><ao:message key="contact.label.tollfree" /></td>
									<td style="white-space:nowrap;text-align:right"><code><ao:write name="siteSettings" property="brand.supportTollFree" /></code></td>
								</tr>
							</logic:notEmpty>
							<logic:notEmpty name="siteSettings" property="brand.supportDayPhone">
								<tr>
									<td style="white-space:nowrap"><ao:message key="contact.label.direct" /></td>
									<td style="white-space:nowrap;text-align:right"><code><ao:write name="siteSettings" property="brand.supportDayPhone" /></code></td>
								</tr>
							</logic:notEmpty>
							<tr>
								<th style='white-space:nowrap' colspan='2'><ao:message key="contact.header.emergencyPhone" /></th>
							</tr>
							<logic:notEmpty name="siteSettings" property="brand.supportEmergencyPhone1">
								<tr>
									<td style="white-space:nowrap"><ao:message key="contact.label.primary" /></td>
									<td style="white-space:nowrap;text-align:right"><code><ao:write name="siteSettings" property="brand.supportEmergencyPhone1" /></code></td>
								</tr>
							</logic:notEmpty>
							<logic:notEmpty name="siteSettings" property="brand.supportEmergencyPhone2">
								<tr>
									<td style="white-space:nowrap"><ao:message key="contact.label.secondary" /></td>
									<td style="white-space:nowrap;text-align:right"><code><ao:write name="siteSettings" property="brand.supportEmergencyPhone2" /></code></td>
								</tr>
							</logic:notEmpty>
							<tr>
								<th style='white-space:nowrap' colspan='2'><ao:message key="contact.header.email" /></th>
							</tr>
							<tr>
								<td style='white-space:nowrap' colspan='2'>
									<a class="aoDarkLink" href="mailto:<ao:write name="siteSettings" property="brand.supportEmailAddress" />">
										<code><ao:write name="siteSettings" property="brand.supportEmailAddress" /></code>
									</a>
								</td>
							</tr>
							<logic:notEmpty name="siteSettings" property="brand.supportFax">
								<tr>
									<th style='white-space:nowrap' colspan='2'><ao:message key="contact.header.fax" /></th>
								</tr>
								<tr>
									<td style='white-space:nowrap' colspan='2'><code><ao:write name="siteSettings" property="brand.supportFax" /></code></td>
								</tr>
							</logic:notEmpty>
							<tr>
								<th style='white-space:nowrap' colspan='2'><ao:message key="contact.header.mailingAddress" /></th>
							</tr>
							<logic:notEmpty name="siteSettings" property="brand.supportMailingAddress1">
								<tr>
									<td style='white-space:nowrap' colspan='2'><code><ao:write name="siteSettings" property="brand.supportMailingAddress1" /></code></td>
								</tr>
							</logic:notEmpty>
							<logic:notEmpty name="siteSettings" property="brand.supportMailingAddress2">
								<tr>
									<td style='white-space:nowrap' colspan='2'><code><ao:write name="siteSettings" property="brand.supportMailingAddress2" /></code></td>
								</tr>
							</logic:notEmpty>
							<logic:notEmpty name="siteSettings" property="brand.supportMailingAddress3">
								<tr>
									<td style='white-space:nowrap' colspan='2'><code><ao:write name="siteSettings" property="brand.supportMailingAddress3" /></code></td>
								</tr>
							</logic:notEmpty>
							<logic:notEmpty name="siteSettings" property="brand.supportMailingAddress4">
								<tr>
									<td style='white-space:nowrap' colspan='2'><code><ao:write name="siteSettings" property="brand.supportMailingAddress4" /></code></td>
								</tr>
							</logic:notEmpty>
						</tbody>
					</table>
				</skin:lightArea>
			</skin:contentLine>
			<skin:contentHorizontalDivider colspansAndDirections="1,up,1" />
			<skin:contentLine colspan="3">
				<ao:message key="contact.text.formWelcome" /><ao:br />
				<ao:br />
				<skin:lightArea>
					<html:javascript staticJavascript='false' bundle="/ApplicationResources" formName="contactForm" />
					<html:form action="/contact-completed" onsubmit="return validateContactForm(this);">
						<table class="ao-packed">
							<tbody>
								<tr>
									<td style="white-space:nowrap"><ao:message key="contact.field.from.prompt" /></td>
									<td><html:text size="30" property="from" /></td>
									<td><html:errors bundle="/ApplicationResources" property="from" /></td>
								</tr>
								<tr>
									<td style="white-space:nowrap"><ao:message key="contact.field.subject.prompt" /></td>
									<td><html:text size="45" property="subject" /></td>
									<td><html:errors bundle="/ApplicationResources" property="subject" /></td>
								</tr>
								<tr><td colspan='2'>&#160;</td></tr>
								<tr><td colspan='2'><ao:message key="contact.field.message.prompt" /></td></tr>
								<tr><td colspan='2'><html:textarea property="message" cols="60" rows="12" /></td></tr>
							</tbody>
							<tfoot>
								<tr>
									<td colspan="2" style="text-align:center">
										<ao:br />
										<ao:input type="submit" value="${ao:message('contact.field.submit.label')}" />
									</td>
								</tr>
							</tfoot>
						</table>
					</html:form>
				</skin:lightArea>
			</skin:contentLine>
		</ao:bundle>
	</skin:content>
</skin:skin>
