<%--
aoweb-struts - Template webapp for legacy Struts-based site framework with AOServ Platform control panels.
Copyright (C) 2007-2009, 2015, 2016, 2019, 2020, 2021, 2022  AO Industries, Inc.
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
    /clientarea/accounting/add-credit-card.do
    <ao:param name="account" value="${addCreditCardForm.account}" />
  </skin:path>
  <%-- TODO: This site-settings into the Skin implementations? --%>
  <logic:equal name="siteSettings" property="brand.aowebStrutsNoindex" value="true"><skin:meta name="robots">noindex</skin:meta></logic:equal>
  <skin:title><ao:message key="addCreditCard.title" /></skin:title>
  <skin:navImageAlt><ao:message key="addCreditCard.navImageAlt" /></skin:navImageAlt>
  <skin:keywords><ao:message key="addCreditCard.keywords" /></skin:keywords>
  <skin:description><ao:message key="addCreditCard.description" /></skin:description>
  <%@include file="add-parents.jspf" %>
  <skin:parent><%@include file="credit-card-manager.meta.jspf" %></skin:parent>
  <skin:skin formtype="struts1">
    <skin:content width="600">
      <skin:contentTitle><ao:message key="addCreditCard.title" /></skin:contentTitle>
      <skin:contentHorizontalDivider />
      <skin:contentLine>
        <logic:present scope="request" name="permissionDenied">
          <%@include file="../../permission-denied.jspf" %>
        </logic:present>
        <logic:notPresent scope="request" name="permissionDenied">
          <skin:popupGroup>
            <html:form action="/add-credit-card-completed">
              <skin:lightArea>
                <b><ao:message key="addCreditCard.form.title" /></b>
                <ao:hr />
                <table class="ao-no-border">
                  <tbody>
                    <c:set scope="request" var="creditCardForm" value="${addCreditCardForm}" />
                    <%@include file="credit-card-form.jspf" %>
                    <tr><td style="white-space:nowrap;text-align:center" colspan="4"><ao:input type="submit" value="${ao:message('addCreditCard.field.submit.label')}" /></td></tr>
                  </tbody>
                </table>
              </skin:lightArea>
            </html:form>
            <%@include file="security-policy.jspf" %>
          </skin:popupGroup>
        </logic:notPresent>
      </skin:contentLine>
    </skin:content>
  </skin:skin>
</ao:bundle>
