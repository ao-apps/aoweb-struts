<%--
aoweb-struts - Template webapp for legacy Struts-based site framework with AOServ Platform control panels.
Copyright (C) 2003-2013, 2015, 2016, 2018, 2019, 2020, 2021, 2022  AO Industries, Inc.
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
along with aoweb-struts.  If not, see <https://www.gnu.org/licenses/>.
--%>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ page import="com.aoindustries.aoserv.client.account.Account" %>
<%@ page import="com.aoapps.lang.i18n.Monies" %>
<%@ page import="com.aoapps.lang.i18n.Money" %>
<%@include file="/WEB-INF/taglibs.jspf" %>

<%@include file="add-parents.jspf" %>
<%@include file="cancel.meta.jspf" %>
<skin:skin>
  <skin:content width="600">
    <ao:bundle basename="com.aoindustries.web.struts.clientarea.control.account.i18n.ApplicationResources">
      <skin:contentTitle><ao:message key="cancel.title" /></skin:contentTitle>
      <skin:contentHorizontalDivider />
      <skin:contentLine>
        <logic:present scope="request" name="permissionDenied">
          <%@include file="../../../permission-denied.jspf" %>
        </logic:present>
        <logic:notPresent scope="request" name="permissionDenied">
          <aoweb:scriptGroup>
            <table class="ao-packed">
              <tbody>
                <tr>
                  <%-- TODO: cancel screen "Customer Support line (800) 519-9541 or email support@aoindustries.com" instead link to contact form --%>
                  <td><%@include file="cancel-message.jspf" %></td>
                </tr>
                <tr>
                  <td style="text-align:center">
                    <c:choose>
                      <c:when test="${param.all}">
                        <ao:a href="/clientarea/control/account/cancel.do">
                          <ao:message key="cancel.link.showActive" />
                        </ao:a>
                      </c:when>
                      <c:otherwise>
                        <ao:a href="/clientarea/control/account/cancel.do?all=true">
                          <ao:message key="cancel.link.showAll" />
                        </ao:a>
                      </c:otherwise>
                    </c:choose>
                    <skin:lightArea align="left">
                      <table class="ao-no-border">
                        <thead>
                          <tr>
                            <th rowspan="2"><ao:message key="cancel.header.accountName" /></th>
                            <th rowspan="2"><ao:message key="cancel.header.parent" /></th>
                            <th colspan="2"><ao:message key="cancel.header.totalMonthlyCharges" /></th>
                            <th rowspan="2"><ao:message key="cancel.header.accountBalance" /></th>
                            <th rowspan="2"><ao:message key="cancel.header.created" /></th>
                            <th rowspan="2"><ao:message key="cancel.header.canceled" /></th>
                            <th rowspan="2"><ao:message key="cancel.header.actions" /></th>
                          </tr>
                          <tr>
                            <th><ao:message key="cancel.header.totalMonthlyCharges.source" /></th>
                            <th><ao:message key="cancel.header.totalMonthlyCharges.billedTo" /></th>
                          </tr>
                        </thead>
                        <tbody>
                          <logic:iterate scope="request" name="accounts" id="account" type="com.aoindustries.aoserv.client.account.Account">
                            <c:if test="${param.all || account.canceled == null || !account.accountBalance.isZero()}">
                              <skin:lightDarkTableRow>
                                <td>
                                  <logic:notEmpty name="account" property="profile">
                                    <bean:define name="account" property="profile" id="bp" type="com.aoindustries.aoserv.client.account.Profile" />
                                    <ao:message key="cancel.field.organizationNameAndAccountName" arg0="${account.profile.name}" arg1="${account.name}" />
                                  </logic:notEmpty>
                                  <logic:empty name="account" property="profile">
                                    <ao:message key="cancel.field.accountName" arg0="${account.name}" />
                                  </logic:empty>
                                </td>
                                <td>
                                  <logic:notEmpty name="account" property="parent">
                                    <bean:define name="account" property="parent" id="parent" type="com.aoindustries.aoserv.client.account.Account" />
                                    <logic:notEmpty name="parent" property="profile">
                                      <bean:define name="parent" property="profile" id="parentBP" type="com.aoindustries.aoserv.client.account.Profile" />
                                      <ao:message key="cancel.field.organizationNameAndAccountName" arg0="${parentBP.name}" arg1="${parent.name}" />
                                    </logic:notEmpty>
                                    <logic:empty name="parent" property="profile">
                                      <ao:message key="cancel.field.accountName" arg0="${parent.name}" />
                                    </logic:empty>
                                  </logic:notEmpty>
                                </td>
                                <td style="text-align:right; white-space:nowrap">
                                  <% Monies totalMonthlyRate = account.getMonthlyRate(); %>
                                  <% if (totalMonthlyRate != null) { %>
                                    <% for (Money monthlyRate : totalMonthlyRate) { %>
                                      <div>
                                        <ao:message key="cancel.field.totalMonthlyRate" arg0="<%= monthlyRate %>" />
                                      </div>
                                    <% } %>
                                  <% } %>
                                </td>
                                <td style="text-align:right; white-space:nowrap">
                                  <% Monies billingMonthlyRate = account.getBillingMonthlyRate(); %>
                                  <% if (billingMonthlyRate != null && !billingMonthlyRate.isEmpty()) { %>
                                    <% for (Money monthlyRate : billingMonthlyRate) { %>
                                      <div>
                                        <ao:message key="cancel.field.totalMonthlyRate" arg0="<%= monthlyRate %>" />
                                      </div>
                                    <% } %>
                                  <% } else { %>
                                    <% Account billingAccount = account.getBillingAccount(); %>
                                    <% if (!account.equals(billingAccount)) { %>
                                      <div><ao:out value="<%= billingAccount %>" /></div>
                                    <% } %>
                                  <% } %>
                                </td>
                                <td style="text-align:right; white-space:nowrap">
                                  <% for (Money balance : account.getAccountBalance()) { %>
                                    <%-- TODO: Link from cancel / account balance to make-payment --%>
                                    <% if (balance.getUnscaledValue() < 0) { %>
                                      <div>
                                        <ao:message key="cancel.field.balance.credit" arg0="<%= balance.negate() %>" />
                                      </div>
                                    <% } else if (balance.getUnscaledValue() > 0) { %>
                                      <div style="color:red"><strong>
                                        <ao:message key="cancel.field.balance.debt" arg0="<%= balance %>" />
                                      </strong></div>
                                    <% } %>
                                  <% } %>
                                </td>
                                <td><aoweb:date><ao:write name="account" property="created.time" /></aoweb:date></td>
                                <td>
                                  <% java.sql.Timestamp canceled = account.getCanceled(); %>
                                  <% if (canceled != null) { %>
                                    <aoweb:date><%= canceled.getTime() %></aoweb:date>
                                  <% } %>
                                </td>
                                <td>
                                  <% if (account.canCancel()) { %>
                                    <ao:a href="/clientarea/control/account/cancel-feedback.do" param.account="${account.name}">
                                      <ao:message key="cancel.field.link.cancel" />
                                    </ao:a>
                                  <% } %>
                                </td>
                              </skin:lightDarkTableRow>
                            </c:if>
                          </logic:iterate>
                        </tbody>
                      </table>
                    </skin:lightArea>
                    <c:choose>
                      <c:when test="${param.all}">
                        <ao:a href="/clientarea/control/account/cancel.do">
                          <ao:message key="cancel.link.showActive" />
                        </ao:a>
                      </c:when>
                      <c:otherwise>
                        <ao:a href="/clientarea/control/account/cancel.do?all=true">
                          <ao:message key="cancel.link.showAll" />
                        </ao:a>
                      </c:otherwise>
                    </c:choose>
                  </td>
                </tr>
              </tbody>
            </table>
          </aoweb:scriptGroup>
        </logic:notPresent>
      </skin:contentLine>
    </ao:bundle>
  </skin:content>
</skin:skin>
