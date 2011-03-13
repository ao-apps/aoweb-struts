<%-- aoweb-struts: Do not edit --%>
<%--
  Copyright 2007-2011 by AO Industries, Inc.,
  7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" buffer="256kb" autoFlush="true" pageEncoding="UTF-8" %>
<%@include file="/_taglibs.jsp" %>

<skin:setContentType />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html:html lang="true" xhtml="true">
    <%@include file="add-parents.jsp" %>
    <%@include file="credit-card-manager.meta.jsp" %>
    <skin:skin>
        <skin:content width="600">
            <fmt:bundle basename="com.aoindustries.website.clientarea.accounting.ApplicationResources">
                <skin:contentTitle><fmt:message key="creditCardManager.title" /></skin:contentTitle>
                <skin:contentHorizontalDivider />
                <skin:contentLine>
                    <logic:present scope="request" name="permissionDenied">
                        <%@include file="../../_permission-denied.jsp" %>
                    </logic:present>
                    <logic:notPresent scope="request" name="permissionDenied">
                        <skin:popupGroup>
                            <skin:lightArea>
                                <table cellspacing="0" cellpadding="2">
                                    <tr>
                                        <logic:equal scope="request" name="showAccounting" value="true">
                                            <th><fmt:message key="creditCardManager.header.accounting" /></th>
                                        </logic:equal>
                                        <th><fmt:message key="creditCardManager.header.cardType" /></th>
                                        <th><fmt:message key="creditCardManager.header.maskedCardNumber" /></th>
                                        <th style='white-space:nowrap'>
                                            <fmt:message key="creditCardManager.header.status" />
                                            <skin:popup>
                                                <fmt:message key="creditCardManager.header.status.popup" />
                                            </skin:popup>
                                        </th>
                                        <th colspan="2"><fmt:message key="creditCardManager.header.actions" /></th>
                                        <th><fmt:message key="creditCardManager.header.description" /></th>
                                    </tr>
                                    <logic:iterate scope="request" name="businessCreditCards" id="businessAndCreditCards" indexId="businessesIndex">
                                        <bean:define name="businessAndCreditCards" property="creditCards" id="creditCards" type="java.util.List<com.aoindustries.aoserv.client.CreditCard>" />
                                        <bean:size name="creditCards" id="creditCardsSize" />
                                        <%--tr class="<%= (businessesIndex&1)==0 ? "aoLightRow" : "aoDarkRow" %>">
                                            <td colspan="7"><hr /></td>
                                        </tr--%>
                                        <logic:notEqual name="creditCardsSize" value="0">
                                            <tr class="<%= (businessesIndex&1)==0 ? "aoLightRow" : "aoDarkRow" %>">
                                                <logic:equal scope="request" name="showAccounting" value="true">
                                                    <td style='white-space:nowrap' rowspan="<%= creditCardsSize+1 %>"><ao:write name="businessAndCreditCards" property="business.accounting" /></td>
                                                </logic:equal>
                                                <logic:iterate name="creditCards" id="creditCard" type="com.aoindustries.aoserv.client.CreditCard" indexId="creditCardsIndex">
                                                    <logic:notEqual name="creditCardsIndex" value="0">
                                                        </tr>
                                                        <tr class="<%= ((businessesIndex+creditCardsIndex)&1)==0 ? "aoLightRow" : "aoDarkRow" %>">
                                                    </logic:notEqual>
                                                    <c:set var="cardNumber" value="${creditCard.cardInfo}"/>
                                                    <td style="white-space:nowrap"><%@include file="_credit-card-image.jsp" %></td>
                                                    <td style="white-space:nowrap"><c:out value="${fn:replace(cardNumber, 'X', '*')}"/></td>
                                                    <logic:equal name="creditCard" property="isActive" value="true">
                                                        <logic:notEqual name="creditCard" property="useMonthly" value="true">
                                                            <td style="white-space:nowrap"><fmt:message key="creditCardManager.header.status.active" /></td>
                                                        </logic:notEqual>
                                                        <logic:equal name="creditCard" property="useMonthly" value="true">
                                                            <td style="white-space:nowrap">
                                                                <fmt:message key="creditCardManager.header.status.useMonthly" />
                                                                <skin:popup width="200">
                                                                    <fmt:message key="creditCardManager.status.useMonthly.popup" />
                                                                </skin:popup>
                                                            </td>
                                                        </logic:equal>
                                                    </logic:equal>
                                                    <logic:notEqual name="creditCard" property="isActive" value="true">
                                                        <td style="white-space:nowrap">
                                                            <fmt:message key="creditCardManager.header.status.deactivated" />
                                                            <logic:notEmpty name="creditCard" property="deactivatedOnString">
                                                                <logic:notEmpty name="creditCard" property="deactivateReason">
                                                                    <skin:popup width="280">
                                                                        <fmt:message key="creditCardManager.header.status.deactivated.popup">
                                                                            <fmt:param><c:out value="${creditCard.deactivatedOnString}" /></fmt:param>
                                                                            <fmt:param><c:out value="${creditCard.deactivateReason}" /></fmt:param>
                                                                        </fmt:message>
                                                                    </skin:popup>
                                                                </logic:notEmpty>
                                                            </logic:notEmpty>
                                                        </td>
                                                    </logic:notEqual>
                                                    <td style="white-space:nowrap">
                                                        <html:link action="/edit-credit-card" paramId="persistenceId" paramName="creditCard" paramProperty="pkey">
                                                            <logic:equal name="creditCard" property="isActive" value="true">
                                                                <fmt:message key="creditCardManager.edit.link" />
                                                            </logic:equal>
                                                            <logic:notEqual name="creditCard" property="isActive" value="true">
                                                                <fmt:message key="creditCardManager.reactivate.link" />
                                                            </logic:notEqual>
                                                        </html:link>
                                                    </td>
                                                    <td style="white-space:nowrap">
                                                        <html:link action="/delete-credit-card" paramId="pkey" paramName="creditCard" paramProperty="pkey">
                                                            <fmt:message key="creditCardManager.delete.link" />
                                                        </html:link>
                                                    </td>
                                                    <td style="white-space:nowrap">
                                                        <logic:notEmpty name="creditCard" property="description">
                                                            <ao:write name="creditCard" property="description" />
                                                        </logic:notEmpty>
                                                        <logic:empty name="creditCard" property="description">
                                                            &#160;
                                                        </logic:empty>
                                                    </td>
                                                </logic:iterate>
                                            </tr>
                                        </logic:notEqual>
                                        <tr class="<%= (businessesIndex&1)==0 ? "aoLightRow" : "aoDarkRow" %>">
                                            <logic:equal name="creditCardsSize" value="0">
                                                <logic:equal scope="request" name="showAccounting" value="true">
                                                    <td rowspan="<%= creditCardsSize+1 %>"><ao:write name="businessAndCreditCards" property="business.accounting" /></td>
                                                </logic:equal>
                                            </logic:equal>
                                            <td style='white-space:nowrap' colspan="7">
                                                <html:link action="/add-credit-card" paramId="accounting" paramName="businessAndCreditCards" paramProperty="business.accounting">
                                                    <fmt:message key="creditCardManager.addCreditCard.link" />
                                                </html:link>
                                                <logic:equal name="businessAndCreditCards" property="hasActiveCard" value="true">
                                                    |
                                                    <html:link action="/configure-automatic-billing" paramId="accounting" paramName="businessAndCreditCards" paramProperty="business.accounting">
                                                        <fmt:message key="creditCardManager.configureAutomaticBilling.link" />
                                                    </html:link>
                                                </logic:equal>
                                            </td>
                                        </tr>
                                    </logic:iterate>
                                </table>
                            </skin:lightArea>
                            <%@include file="security-policy.jsp" %>
                        </skin:popupGroup>
                    </logic:notPresent>
                </skin:contentLine>
            </fmt:bundle>
        </skin:content>
    </skin:skin>
</html:html>
