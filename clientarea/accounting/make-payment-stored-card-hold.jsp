<%-- aoweb-struts: Do not edit --%>
<%--
  Copyright 2007-2009 by AO Industries, Inc.,
  7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" buffer="256kb" autoFlush="true" pageEncoding="UTF-8" %>
<%@ page import="java.math.BigDecimal" %>
<%@include file="/_taglibs.jsp" %>

<skin:setContentType />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html:html lang="true" xhtml="true">
    <fmt:bundle basename="com.aoindustries.website.clientarea.accounting.ApplicationResources">
        <skin:path>/clientarea/accounting/make-payment.do</skin:path>
        <logic:equal name="siteSettings" property="brand.aowebStrutsNoindex" value="true"><skin:meta name="ROBOTS">NOINDEX</skin:meta></logic:equal>
        <skin:title><fmt:message key="makePayment.title" /></skin:title>
        <skin:navImageAlt><fmt:message key="makePayment.navImageAlt" /></skin:navImageAlt>
        <skin:keywords><fmt:message key="makePayment.keywords" /></skin:keywords>
        <skin:description><fmt:message key="makePayment.description" /></skin:description>
        <%@include file="add-parents.jsp" %>
        <skin:skin>
            <skin:content width="600">
                <skin:contentTitle><fmt:message key="makePayment.title" /></skin:contentTitle>
                <skin:contentHorizontalDivider />
                <skin:contentLine>
                    <skin:lightArea>
                        <fmt:message key="makePaymentStoredCardHold.hold.title" />
                        <hr />
                        <fmt:message key="makePaymentStoredCardHold.hold.followingProcessed">
                            <fmt:param><c:out value="${reviewReason}" /></fmt:param>
                        </fmt:message>
                        <fmt:message key="makePaymentStoredCardHold.hold.detailsFollow" />
                        <bean:define scope="request" name="creditCard" id="creditCard" type="com.aoindustries.aoserv.client.CreditCard" />
                        <bean:define scope="request" name="business" id="business" type="com.aoindustries.aoserv.client.Business" />
                        <table cellspacing='0' cellpadding='2'>
                            <tr>
                                <th style="text-align:left; white-space:nowrap;"><fmt:message key="makePaymentStoredCard.business.prompt" /></th>
                                <td style="white-space:nowrap"><ao:write scope="request" name="business" /></td>
                            </tr>
                            <tr>
                                <th style="text-align:left; white-space:nowrap;"><fmt:message key="makePaymentStoredCard.card.prompt" /></th>
                                <td style="white-space:nowrap">
                                    <c:set var="cardNumber" value="${creditCard.cardInfo}"/>
                                    <%@include file="_credit-card-image.jsp" %>
                                    <c:out value="${fn:replace(cardNumber, 'X', '*')}"/>
                                </td>
                            </tr>
                            <tr>
                                <th style="text-align:left; white-space:nowrap;"><fmt:message key="makePaymentStoredCard.cardComment.prompt" /></th>
                                <td style="white-space:nowrap">
                                    <logic:notEmpty name="creditCard" property="description">
                                        <ao:write name="creditCard" property="description" />
                                    </logic:notEmpty>
                                    <logic:empty name="creditCard" property="description">
                                        &#160;
                                    </logic:empty>
                                </td>
                            </tr>
                            <tr>
                                <th style="text-align:left; white-space:nowrap;"><fmt:message key="makePaymentStoredCard.paymentAmount.prompt" /></th>
                                <td style="white-space:nowrap">$<ao:write scope="request" name="transaction" property="transactionRequest.amount" /></td>
                            </tr>
                            <tr>
                                <th style="text-align:left; white-space:nowrap;"><fmt:message key="makePaymentStoredCardCompleted.transid.prompt" /></th>
                                <td style="white-space:nowrap"><ao:write scope="request" name="aoTransaction" property="transID" /></td>
                            </tr>
                            <tr>
                                <th style="text-align:left; white-space:nowrap;"><fmt:message key="makePaymentStoredCardCompleted.approvalCode.prompt" /></th>
                                <td style="white-space:nowrap"><ao:write scope="request" name="transaction" property="authorizationResult.approvalCode" /></td>
                            </tr>
                            <tr>
                                <th style='white-space:nowrap' align='left'><fmt:message key="makePaymentStoredCardCompleted.newBalance.prompt" /></th>
                                <td style="white-space:nowrap">
                                    <% BigDecimal balance = business.getAccountBalance(); %>
                                    <% if(balance.signum()==0) { %>
                                        <fmt:message key="makePaymentStoredCardCompleted.newBalance.value.zero" />
                                    <% } else if(balance.signum()<0) { %>
                                        <fmt:message key="makePaymentStoredCardCompleted.newBalance.value.credit">
                                            <fmt:param><c:out value="<%= balance.negate().toPlainString() %>" /></fmt:param>
                                        </fmt:message>
                                    <% } else { %>
                                        <fmt:message key="makePaymentStoredCardCompleted.newBalance.value.debt">
                                            <fmt:param><c:out value="<%= balance.toPlainString() %>" /></fmt:param>
                                        </fmt:message>
                                    <% } %>
                                </td>
                            </tr>
                        </table><br />
                        <fmt:message key="makePaymentStoredCardCompleted.contactAndThankYou" />
                    </skin:lightArea>
                    <%@include file="security-policy.jsp" %>
                </skin:contentLine>
            </skin:content>
        </skin:skin>
    </fmt:bundle>
</html:html>
