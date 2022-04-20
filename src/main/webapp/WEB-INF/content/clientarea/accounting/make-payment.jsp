<%--
aoweb-struts - Template webapp for legacy Struts-based site framework with AOServ Platform control panels.
Copyright (C) 2007-2009, 2016, 2018, 2019, 2020, 2021, 2022  AO Industries, Inc.
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
<%@include file="/WEB-INF/taglibs.jspf" %>

<%@include file="add-parents.jspf" %>
<%@include file="make-payment.meta.jspf" %>
<skin:skin>
  <skin:content width="600">
    <ao:bundle basename="com.aoindustries.web.struts.clientarea.accounting.i18n.ApplicationResources">
      <skin:contentTitle><ao:message key="makePayment.title" /></skin:contentTitle>
      <skin:contentHorizontalDivider />
      <skin:contentLine>
        <skin:lightArea>
          <b><ao:message key="makePayment.selectAccount.list.title" /></b>
          <ao:hr />
          <table class="ao-no-border">
            <thead>
              <tr>
                <th style='white-space:nowrap'><ao:message key="makePayment.account.header" /></th>
                <th style='white-space:nowrap'><ao:message key="makePayment.monthlyRate.header" /></th>
                <th style='white-space:nowrap'><ao:message key="makePayment.balance.header" /></th>
                <th style='white-space:nowrap'><ao:message key="makePayment.makePayment.header" /></th>
              </tr>
            </thead>
            <tbody>
              <c:forEach var="entry" items="${accountsAndBalances}">
                <c:set var="account" value="${entry.key}" />
                <skin:lightDarkTableRow>
                  <td style="white-space:nowrap"><ao:out value="${account.name}" /></td>
                  <td style="white-space:nowrap;text-align:right">
                    <c:forEach var="monthlyRate" items="${account.monthlyRate.values}">
                      <div><ao:out value="${monthlyRate}" /></div>
                    </c:forEach>
                  </td>
                  <td style="white-space:nowrap;text-align:right">
                    <c:forEach var="balance" items="${entry.value.values}">
                      <ao:choose>
                        <ao:when test="#{balance.unscaledValue < 0}">
                          <div><ao:message key="makePayment.balance.value.credit" arg0="${balance.negate()}" /></div>
                        </ao:when>
                        <ao:when test="#{balance.unscaledValue > 0}">
                          <div style="color:red"><strong><ao:message key="makePayment.balance.value.debt" arg0="${balance}" /></strong></div>
                        </ao:when>
                        <ao:otherwise>
                          <div><ao:message key="makePayment.balance.value.zero" arg0="${balance}" /></div>
                        </ao:otherwise>
                      </ao:choose>
                    </c:forEach>
                  </td>
                  <td style="white-space:nowrap">
                    <ao:choose>
                      <ao:when test="#{fn:length(entry.value.currencies) == 0}">
                        <%-- Handle the no-currencies case --%>
                        <div>
                          <ao:a href="/clientarea/accounting/make-payment-select-card.do" param.account="${account.name}">
                            <ao:message key="makePayment.makePayment.link" />
                          </ao:a>
                        </div>
                      </ao:when>
                      <ao:otherwise>
                        <c:forEach var="currency" items="${entry.value.currencies}">
                          <div>
                            <ao:a href="/clientarea/accounting/make-payment-select-card.do" param.account="${account.name}" param.currency="${currency.currencyCode}">
                              <ao:message key="makePayment.makePayment.link" />
                            </ao:a>
                          </div>
                        </c:forEach>
                      </ao:otherwise>
                    </ao:choose>
                  </td>
                </skin:lightDarkTableRow>
              </c:forEach>
            </tbody>
          </table>
        </skin:lightArea>
      </skin:contentLine>
    </ao:bundle>
  </skin:content>
</skin:skin>
