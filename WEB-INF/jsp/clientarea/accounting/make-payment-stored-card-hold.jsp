<%-- aoweb-struts: Do not edit --%>
<%--
  Copyright 2007-2009 by AO Industries, Inc.,
  7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" buffer="256kb" autoFlush="true" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/taglibs.jsp" %>

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
        <%@ include file="add-parents.jsp" %>
        <%@ include file="add-siblings.jsp" %>
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
                                    <% String cardInfo = creditCard.getCardInfo(); %>
                                    <% if(cardInfo.startsWith("34") || cardInfo.startsWith("37")) { %>
                                    <html:img src="amex.gif" bundle="/clientarea/accounting/ApplicationResources" altKey="creditCardManager.image.amex.alt" style="border:1px solid" width="64" height="40" />
                                    <% } else if(cardInfo.startsWith("60")) { %>
                                    <html:img src="discv.gif" bundle="/clientarea/accounting/ApplicationResources" altKey="creditCardManager.image.discv.alt" style="border:1px solid" width="63" height="40" />
                                    <% } else if(cardInfo.startsWith("51") || cardInfo.startsWith("52") || cardInfo.startsWith("53") || cardInfo.startsWith("54") || cardInfo.startsWith("55")) { %>
                                    <html:img src="mcard.gif" bundle="/clientarea/accounting/ApplicationResources" altKey="creditCardManager.image.mcard.alt" style="border:1px solid" width="64" height="40" />
                                    <% } else if(cardInfo.startsWith("4")) { %>
                                    <html:img src="visa.gif" bundle="/clientarea/accounting/ApplicationResources" altKey="creditCardManager.image.visa.alt" style="border:1px solid" width="64" height="40" />
                                    <% } else { %>
                                    <fmt:message key="creditCardManager.creditCard.cardType.unknown" />
                                    <% } %>
                                    <%= creditCard.getCardInfo().replace('X', 'x') %>
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
                                    <% int balance = business.getAccountBalance(); %>
                                    <% if(balance==0) { %>
                                        <fmt:message key="makePaymentStoredCardCompleted.newBalance.value.zero" />
                                    <% } else if(balance<0) { %>
                                        <bean:message
                                            bundle="/clientarea/accounting/ApplicationResources" key="makePaymentStoredCardCompleted.newBalance.value.credit"
                                            arg0="<%= com.aoindustries.sql.SQLUtility.getDecimal(-balance) %>"
                                        />
                                    <% } else { %>
                                        <bean:message
                                            bundle="/clientarea/accounting/ApplicationResources" key="makePaymentStoredCardCompleted.newBalance.value.debt"
                                            arg0="<%= com.aoindustries.sql.SQLUtility.getDecimal(balance) %>"
                                        />
                                    <% } %>
                                </td>
                            </tr>
                        </table><br />
                        <fmt:message key="makePaymentStoredCardCompleted.contactAndThankYou" />
                    </skin:lightArea>
                    <%@ include file="security-policy.jsp" %>
                </skin:contentLine>
            </skin:content>
        </skin:skin>
    </fmt:bundle>
</html:html>
