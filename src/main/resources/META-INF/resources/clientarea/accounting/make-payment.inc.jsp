<%--
aoweb-struts-resources - Web resources for legacy Struts-based site framework with AOServ Platform control panels.
Copyright (C) 2007-2009, 2016, 2018  AO Industries, Inc.
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
<%@ page import="java.math.BigDecimal" %>
<%@include file="/_taglibs.inc.jsp" %>

<skin:setContentType />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html:html lang="true" xhtml="true">
	<%@include file="add-parents.inc.jsp" %>
	<%@include file="make-payment.meta.inc.jsp" %>
	<skin:skin>
		<skin:content width="600">
			<fmt:bundle basename="com.aoindustries.website.clientarea.accounting.ApplicationResources">
				<skin:contentTitle><fmt:message key="makePayment.title" /></skin:contentTitle>
				<skin:contentHorizontalDivider />
				<skin:contentLine>
					<skin:lightArea>
						<fmt:message key="makePayment.selectBusiness.list.title" />
						<hr />
						<table cellspacing="0" cellpadding="2">
							<tr>
								<th style='white-space:nowrap'><fmt:message key="makePayment.business.header" /></th>
								<th style='white-space:nowrap'><fmt:message key="makePayment.monthlyRate.header" /></th>
								<th style='white-space:nowrap'><fmt:message key="makePayment.balance.header" /></th>
								<th style='white-space:nowrap'><fmt:message key="makePayment.makePayment.header" /></th>
							</tr>
							<logic:iterate scope="request" name="businesses" id="business" type="com.aoindustries.aoserv.client.account.Account">
								<skin:lightDarkTableRow>
									<td style="white-space:nowrap"><ao:write name="business" property="name" /></td>
									<td style='white-space:nowrap' align='right'><ao:write name="business" property="monthlyRateString" /></td>
									<td style='white-space:nowrap' align='right'>
										<% BigDecimal balance = business.getAccountBalance(); %>
										<% if(balance.signum()==0) { %>
											<fmt:message key="makePayment.balance.value.zero" />
										<% } else if(balance.signum()<0) { %>
											<fmt:message key="makePayment.balance.value.credit">
												<fmt:param><c:out value="<%= balance.negate().toPlainString() %>" /></fmt:param>
											</fmt:message>
										<% } else { %>
											<fmt:message key="makePayment.balance.value.debt">
												<fmt:param><c:out value="<%= balance.toPlainString() %>" /></fmt:param>
											</fmt:message>
										<% } %>
									</td>
									<td style="white-space:nowrap">
										<html:link action="/make-payment-select-card" paramId="accounting" paramName="business" paramProperty="name">
											<fmt:message key="makePayment.makePayment.link" />
										</html:link>
									</td>
								</skin:lightDarkTableRow>
							</logic:iterate>
						</table>
					</skin:lightArea>
				</skin:contentLine>
			</fmt:bundle>
		</skin:content>
	</skin:skin>
</html:html>