<%--
aoweb-struts-resources - Web resources for legacy Struts-based site framework with AOServ Platform control panels.
Copyright (C) 2003-2013, 2015, 2016, 2018  AO Industries, Inc.
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
<%@ page import="java.sql.Timestamp" %>
<%@include file="/_taglibs.inc.jsp" %>

<skin:setContentType />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html:html lang="true" xhtml="true">
	<%@include file="add-parents.inc.jsp" %>
	<%@include file="cancel.meta.inc.jsp" %>
	<skin:skin>
		<skin:content width="600">
			<fmt:bundle basename="com.aoindustries.website.clientarea.control.ApplicationResources">
				<skin:contentTitle><fmt:message key="business.cancel.title" /></skin:contentTitle>
				<skin:contentHorizontalDivider />
				<skin:contentLine>
					<logic:present scope="request" name="permissionDenied">
						<%@include file="../../../_permission-denied.inc.jsp" %>
					</logic:present>
					<logic:notPresent scope="request" name="permissionDenied">
						<aoweb:scriptGroup>
							<table cellpadding='0' cellspacing='0'>
								<tr>
									<td><%@include file="cancel-message.inc.jsp" %></td>
								</tr>
								<tr>
									<td align='center'>
										<skin:lightArea>
											<table cellspacing='0' cellpadding='2'>
												<tr>
													<th><fmt:message key="business.cancel.header.businessName" /></th>
													<th><fmt:message key="business.cancel.header.parent" /></th>
													<th><fmt:message key="business.cancel.header.totalMonthlyCharges" /></th>
													<th><fmt:message key="business.cancel.header.accountBalance" /></th>
													<th><fmt:message key="business.cancel.header.created" /></th>
													<th><fmt:message key="business.cancel.header.canceled" /></th>
													<th><fmt:message key="business.cancel.header.actions" /></th>
												</tr>
												<logic:iterate scope="request" name="businesses" id="bu" type="com.aoindustries.aoserv.client.account.Account">
													<skin:lightDarkTableRow>
														<td>
															<logic:notEmpty name="bu" property="businessProfile">
																<bean:define name="bu" property="businessProfile" id="bp" type="com.aoindustries.aoserv.client.account.Profile" />
																<fmt:message key="business.cancel.field.businessNameAndAccounting">
																	<fmt:param><c:out value="${bu.businessProfile.name}" /></fmt:param>
																	<fmt:param><c:out value="${bu.accounting}" /></fmt:param>
																</fmt:message>
															</logic:notEmpty>
															<logic:empty name="bu" property="businessProfile">
																<fmt:message key="business.cancel.field.businessAccounting">
																	<fmt:param><c:out value="${bu.accounting}" /></fmt:param>
																</fmt:message>
															</logic:empty>
														</td>
														<td>
															<logic:empty name="bu" property="parentBusiness">&#160;</logic:empty>
															<logic:notEmpty name="bu" property="parentBusiness">
																<bean:define name="bu" property="parentBusiness" id="parent" type="com.aoindustries.aoserv.client.account.Account" />
																<logic:notEmpty name="parent" property="businessProfile">
																	<bean:define name="parent" property="businessProfile" id="parentBP" type="com.aoindustries.aoserv.client.account.Profile" />
																	<fmt:message key="business.cancel.field.businessNameAndAccounting">
																		<fmt:param><c:out value="${parentBP.name}" /></fmt:param>
																		<fmt:param><c:out value="${parent.accounting}" /></fmt:param>
																	</fmt:message>
																</logic:notEmpty>
																<logic:empty name="parent" property="businessProfile">
																	<fmt:message key="business.cancel.field.businessAccounting">
																		<fmt:param><c:out value="${parent.accounting}" /></fmt:param>
																	</fmt:message>
																</logic:empty>
															</logic:notEmpty>
														</td>
														<td align='right'>
															<logic:empty name="bu" property="totalMonthlyRate">&#160;</logic:empty>
															<logic:notEmpty name="bu" property="totalMonthlyRate">
																<fmt:message key="business.cancel.field.totalMonthlyRate">
																	<fmt:param><c:out value="${bu.totalMonthlyRate}" /></fmt:param>
																</fmt:message>
															</logic:notEmpty>
														</td>
														<td align='right'>
															<% BigDecimal balance=bu.getAccountBalance(); %>
															<% if(balance.signum()<0) { %>
																<fmt:message key="business.cancel.field.balance.credit">
																	<fmt:param><c:out value="<%= balance.negate().toPlainString() %>" /></fmt:param>
																</fmt:message>
															<% } else if(balance.signum()>0) { %>
																<fmt:message key="business.cancel.field.balance.debt">
																	<fmt:param><c:out value="<%= balance.toPlainString() %>" /></fmt:param>
																</fmt:message>
															<% } else { %>
																<fmt:message key="business.cancel.field.balance.zero" />
															<% } %>
														</td>
														<td><aoweb:date><ao:write name="bu" property="created.time" /></aoweb:date></td>
														<td>
															<% Timestamp canceled=bu.getCanceled(); %>
															<% if(canceled==null) { %>
																&#160;
															<% } else { %>
																<aoweb:date><%= canceled.getTime() %></aoweb:date>
															<% } %>
														</td>
														<td>
															<% if(!bu.canCancel()) { %>
																&#160;
															<% } else { %>
																<html:link action="/business/cancel-feedback" paramId="business" paramName="bu" paramProperty="accounting">
																	<fmt:message key="business.cancel.field.link.cancel" />
																</html:link>
															<% } %>
														</td>
													</skin:lightDarkTableRow>
												</logic:iterate>
											</table>
										</skin:lightArea>
									</td>
								</tr>
							</table>
						</aoweb:scriptGroup>
					</logic:notPresent>
				</skin:contentLine>
			</fmt:bundle>
		</skin:content>
	</skin:skin>
</html:html>
