<%-- aoweb-struts: Do not edit --%>
<%--
  Copyright 2007-2009 by AO Industries, Inc.,
  7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" buffer="256kb" autoFlush="true" pageEncoding="UTF-8" %>
<%@include file="/_taglibs.jsp" %>

<skin:setContentType />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html:html lang="true" xhtml="true">
    <fmt:bundle basename="com.aoindustries.website.clientarea.accounting.ApplicationResources">
        <skin:path>
            /clientarea/accounting/configure-automatic-billing.do
            <ao:param name="accounting" value="${param['accounting']}"/>
        </skin:path>
        <logic:equal name="siteSettings" property="brand.aowebStrutsNoindex" value="true"><skin:meta name="ROBOTS">NOINDEX</skin:meta></logic:equal>
        <skin:title><fmt:message key="configureAutomaticBilling.title" /></skin:title>
        <skin:navImageAlt><fmt:message key="configureAutomaticBilling.navImageAlt" /></skin:navImageAlt>
        <skin:keywords><fmt:message key="configureAutomaticBilling.keywords" /></skin:keywords>
        <skin:description><fmt:message key="configureAutomaticBilling.description" /></skin:description>
        <%@include file="add-parents.jsp" %>
        <skin:parent><%@include file="credit-card-manager.meta.jsp" %></skin:parent>
        <skin:skin>
            <skin:content width="600">
                <skin:contentTitle><fmt:message key="configureAutomaticBilling.title" /></skin:contentTitle>
                <skin:contentHorizontalDivider />
                <skin:contentLine>
                    <logic:present scope="request" name="permissionDenied">
                        <%@include file="../../_permission-denied.jsp" %>
                    </logic:present>
                    <logic:notPresent scope="request" name="permissionDenied">
                        <form id="configurationAutomaticBillingForm" method="post" action="<ao:url>configure-automatic-billing-completed.do</ao:url>"><div>
                            <input name="accounting" type="hidden" value="<%= request.getParameter("accounting") %>" />
                            <skin:lightArea>
                                <fmt:message key="configureAutomaticBilling.cardList.title" />
                                <hr />
                                <fmt:message key="configureAutomaticBilling.business.label" />
                                <ao:write scope="request" name="business" property="accounting" /><br />
                                <br />
                                <table cellspacing="0" cellpadding="2">
                                    <tr>
                                        <th><fmt:message key="configureAutomaticBilling.header.select" /></th>
                                        <th><fmt:message key="configureAutomaticBilling.header.cardType" /></th>
                                        <th><fmt:message key="configureAutomaticBilling.header.maskedCardNumber" /></th>
                                        <th><fmt:message key="configureAutomaticBilling.header.description" /></th>
                                    </tr>
                                    <logic:iterate scope="request" name="activeCards" id="creditCard" type="com.aoindustries.aoserv.client.CreditCard" indexId="row">
                                        <skin:lightDarkTableRow>
                                            <td style="white-space:nowrap">
                                                <logic:notPresent scope="request" name="automaticCard">
                                                    <input
                                                        type="radio"
                                                        name="pkey"
                                                        value="<%= creditCard.getPkey() %>"
                                                        onchange='this.form.submitButton.disabled=false;'
                                                    />
                                                </logic:notPresent>
                                                <logic:present scope="request" name="automaticCard">
                                                    <logic:equal scope="request" name="automaticCard" property="pkey" value="<%= Integer.toString(creditCard.getPkey()) %>">
                                                        <input
                                                            type="radio"
                                                            name="pkey"
                                                            value="<%= creditCard.getPkey() %>"
                                                            checked="checked"
                                                            onchange='this.form.submitButton.disabled=true;'
                                                        />
                                                    </logic:equal>
                                                    <logic:notEqual scope="request" name="automaticCard" property="pkey" value="<%= Integer.toString(creditCard.getPkey()) %>">
                                                        <input
                                                            type="radio"
                                                            name="pkey"
                                                            value="<%= creditCard.getPkey() %>"
                                                            onchange='this.form.submitButton.disabled=false;'
                                                        />
                                                    </logic:notEqual>
                                                </logic:present>
                                            </td>
                                            <c:set var="cardNumber" value="${creditCard.cardInfo}"/>
                                            <td style="white-space:nowrap"><%@include file="_credit-card-image.jsp" %></td>
                                            <td style="white-space:nowrap"><c:out value="${fn:replace(cardNumber, 'X', '*')}"/></td>
                                            <td style="white-space:nowrap">
                                                <logic:notEmpty name="creditCard" property="description">
                                                    <ao:write name="creditCard" property="description" />
                                                </logic:notEmpty>
                                                <logic:empty name="creditCard" property="description">
                                                    &#160;
                                                </logic:empty>
                                            </td>
                                        </skin:lightDarkTableRow>
                                    </logic:iterate>
                                    <skin:lightDarkTableRow>
                                        <td style="white-space:nowrap">
                                            <logic:notPresent scope="request" name="automaticCard">
                                                <input type="radio" name="pkey" value="" checked="checked" onchange='this.form.submitButton.disabled=true;' />
                                            </logic:notPresent>
                                            <logic:present scope="request" name="automaticCard">
                                                <input type="radio" name="pkey" value="" onchange='this.form.submitButton.disabled=false;' />
                                            </logic:present>
                                        </td>
                                        <td style='white-space:nowrap' colspan="3"><fmt:message key="configureAutomaticBilling.noAutomaticBilling" /></td>
                                    </skin:lightDarkTableRow>
                                    <tr>
                                        <td style='white-space:nowrap' colspan="4" align="center">
                                            <ao:input type="submit" name="submitButton"><fmt:message key="configureAutomaticBilling.field.submit.label" /></ao:input>
                                            <%-- Disable using JavaScript to avoid dependency on JavaScript --%>
                                            <ao:script>document.forms["configurationAutomaticBillingForm"].submitButton.disabled = true;</ao:script>
                                        </td>
                                    </tr>
                                </table>
                            </skin:lightArea>
                        </div></form>
                    </logic:notPresent>
                </skin:contentLine>
            </skin:content>
        </skin:skin>
    </fmt:bundle>
</html:html>
