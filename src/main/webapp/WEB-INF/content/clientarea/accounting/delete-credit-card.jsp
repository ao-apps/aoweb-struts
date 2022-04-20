<%--
aoweb-struts - Template webapp for legacy Struts-based site framework with AOServ Platform control panels.
Copyright (C) 2007-2009, 2015, 2016, 2018, 2019, 2020, 2021, 2022  AO Industries, Inc.
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

<ao:bundle basename="com.aoindustries.web.struts.clientarea.accounting.i18n.ApplicationResources">
  <skin:path>
    /clientarea/accounting/delete-credit-card.do
    <ao:param name="id" value="${param.id}"/>
  </skin:path>
  <logic:equal name="siteSettings" property="brand.aowebStrutsNoindex" value="true"><skin:meta name="robots">noindex</skin:meta></logic:equal>
  <skin:title><ao:message key="deleteCreditCard.title" /></skin:title>
  <skin:navImageAlt><ao:message key="deleteCreditCard.navImageAlt" /></skin:navImageAlt>
  <skin:keywords><ao:message key="deleteCreditCard.keywords" /></skin:keywords>
  <skin:description><ao:message key="deleteCreditCard.description" /></skin:description>
  <%@include file="add-parents.jspf" %>
  <skin:parent><%@include file="credit-card-manager.meta.jspf" %></skin:parent>
  <skin:skin>
    <skin:content width="600">
      <skin:contentTitle><ao:message key="deleteCreditCard.title" /></skin:contentTitle>
      <skin:contentHorizontalDivider />
      <skin:contentLine>
        <logic:present scope="request" name="permissionDenied">
          <%@include file="../../permission-denied.jspf" %>
        </logic:present>
        <logic:notPresent scope="request" name="permissionDenied">
          <bean:define scope="request" name="creditCard" id="creditCard" type="com.aoindustries.aoserv.client.payment.CreditCard" />
          <form method="post" action="<ao:url>/clientarea/accounting/delete-credit-card-completed.do</ao:url>"><div>
            <ao:input type="hidden" name="id" value="${creditCard.id}" />
            <skin:lightArea>
              <b><ao:message key="deleteCreditCard.confirmation.title" /></b>
              <ao:hr />
              <table class="ao-spread">
                <thead>
                  <tr>
                    <td colspan="2"><ao:message key="deleteCreditCard.confirmation.prompt" /><ao:br /><ao:br /></td>
                  </tr>
                </thead>
                <tbody>
                  <tr>
                    <th style="white-space:nowrap;text-align:left" class="aoLightRow"><ao:message key="deleteCreditCard.account.header" /></th>
                    <td style="white-space:nowrap"><ao:write name="creditCard" property="account.name" /></td>
                  </tr>
                  <tr>
                    <th style="white-space:nowrap;text-align:left" class="aoLightRow"><ao:message key="deleteCreditCard.firstName.header" /></th>
                    <td style="white-space:nowrap"><ao:write name="creditCard" property="firstName" /></td>
                  </tr>
                  <tr>
                    <th style="white-space:nowrap;text-align:left" class="aoLightRow"><ao:message key="deleteCreditCard.lastName.header" /></th>
                    <td style="white-space:nowrap"><ao:write name="creditCard" property="lastName" /></td>
                  </tr>
                  <c:if test="${!empty creditCard.companyName}">
                    <tr>
                      <th style="white-space:nowrap;text-align:left" class="aoLightRow"><ao:message key="deleteCreditCard.companyName.header" /></th>
                      <td style="white-space:nowrap"><ao:write name="creditCard" property="companyName" /></td>
                    </tr>
                  </c:if>
                  <c:set var="cardNumber" value="${creditCard.cardInfo}"/>
                  <tr>
                    <th style="white-space:nowrap;text-align:left" class="aoLightRow"><ao:message key="deleteCreditCard.cardType.header" /></th>
                    <td style="white-space:nowrap"><%@include file="credit-card-image.jspf" %></td>
                  </tr>
                  <tr>
                    <th style="white-space:nowrap;text-align:left" class="aoLightRow"><ao:message key="deleteCreditCard.cardNumber.header" /></th>
                    <td style="white-space:nowrap; font-family: monospace"><c:out value="${aoweb:getCardNumberDisplay(cardNumber)}"/></td>
                  </tr>
                  <c:set var="expirationDisplay" value="${aoweb:getExpirationDisplay(creditCard.expirationMonth, creditCard.expirationYear)}"/>
                  <c:if test="${!empty expirationDisplay}">
                    <tr>
                      <th style="white-space:nowrap;text-align:left" class="aoLightRow"><ao:message key="deleteCreditCard.expirationDate.header" /></th>
                      <td style="white-space:nowrap; font-family: monospace"><c:out value="${expirationDisplay}"/></td>
                    </tr>
                  </c:if>
                  <c:if test="${!empty creditCard.description}">
                    <tr>
                      <th style="white-space:nowrap;text-align:left" class="aoLightRow"><ao:message key="deleteCreditCard.description.header" /></th>
                      <td style="white-space:nowrap"><ao:write name="creditCard" property="description" /></td>
                    </tr>
                  </c:if>
                </tbody>
                <tfoot>
                  <tr>
                    <td style="white-space:nowrap;text-align:center" colspan="2">
                      <ao:br />
                      <ao:input type="submit" value="${ao:message('deleteCreditCard.field.submit.label')}" />
                      &#160;&#160;&#160;
                      <ao:input type="button" value="${ao:message('deleteCreditCard.field.cancel.label')}">
                        <ao:onclick>window.location.href=<ao:url>/clientarea/accounting/credit-card-manager.do</ao:url>;</ao:onclick>
                      </ao:input>
                    </td>
                  </tr>
                </tfoot>
              </table>
            </skin:lightArea>
          </div></form>
        </logic:notPresent>
      </skin:contentLine>
    </skin:content>
  </skin:skin>
</ao:bundle>
