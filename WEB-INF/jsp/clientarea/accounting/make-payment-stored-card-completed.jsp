<%-- aoweb-struts: Do not edit --%>
<%--
  Copyright 2007-2009 by AO Industries, Inc.,
  7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" buffer="256kb" autoFlush="true" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/taglibs.jsp" %>

<skin:setContentType/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html:html lang="true" xhtml="true">
    <skin:path>/clientarea/accounting/make-payment.do</skin:path>
    <logic:equal name="siteSettings" property="brand.aowebStrutsNoindex" value="true"><skin:meta name="ROBOTS">NOINDEX</skin:meta></logic:equal>
    <skin:title><bean:message bundle="/clientarea/accounting/ApplicationResources" key="makePayment.title"/></skin:title>
    <skin:navImageAlt><bean:message bundle="/clientarea/accounting/ApplicationResources" key="makePayment.navImageAlt"/></skin:navImageAlt>
    <skin:keywords><bean:message bundle="/clientarea/accounting/ApplicationResources" key="makePayment.keywords"/></skin:keywords>
    <skin:description><bean:message bundle="/clientarea/accounting/ApplicationResources" key="makePayment.description"/></skin:description>
    <%@ include file="add-parents.jsp" %>
    <%@ include file="add-siblings.jsp" %>
    <skin:skin>
        <skin:content width="600">
            <skin:contentTitle><bean:message bundle="/clientarea/accounting/ApplicationResources" key="makePayment.title"/></skin:contentTitle>
            <skin:contentHorizontalDivider/>
            <skin:contentLine>
                <skin:lightArea>
                    <bean:message bundle="/clientarea/accounting/ApplicationResources" key="makePaymentStoredCardCompleted.confirm.title"/>
                    <hr />
                    <bean:message bundle="/clientarea/accounting/ApplicationResources" key="makePaymentStoredCardCompleted.confirm.followingProcessed"/>
                    <bean:define scope="request" name="creditCard" id="creditCard" type="com.aoindustries.aoserv.client.CreditCard"/>
                    <bean:define scope="request" name="business" id="business" type="com.aoindustries.aoserv.client.Business"/>
                    <table cellspacing='0' cellpadding='2'>
                        <tr>
                            <th align='left' nowrap><bean:message bundle="/clientarea/accounting/ApplicationResources" key="makePaymentStoredCard.business.prompt"/></th>
                            <td style="white-space:nowrap"><bean:write scope="request" name="business"/></td>
                        </tr>
                        <tr>
                            <th align='left' nowrap><bean:message bundle="/clientarea/accounting/ApplicationResources" key="makePaymentStoredCard.card.prompt"/></th>
                            <td style="white-space:nowrap">
                                <% String cardInfo = creditCard.getCardInfo(); %>
                                <% if(cardInfo.startsWith("34") || cardInfo.startsWith("37")) { %>
                                <html:img src="amex.gif" bundle="/clientarea/accounting/ApplicationResources" altKey="creditCardManager.image.amex.alt" style="border:1px solid" width="64" height="40"/>
                                <% } else if(cardInfo.startsWith("60")) { %>
                                <html:img src="discv.gif" bundle="/clientarea/accounting/ApplicationResources" altKey="creditCardManager.image.discv.alt" style="border:1px solid" width="63" height="40"/>
                                <% } else if(cardInfo.startsWith("51") || cardInfo.startsWith("52") || cardInfo.startsWith("53") || cardInfo.startsWith("54") || cardInfo.startsWith("55")) { %>
                                <html:img src="mcard.gif" bundle="/clientarea/accounting/ApplicationResources" altKey="creditCardManager.image.mcard.alt" style="border:1px solid" width="64" height="40"/>
                                <% } else if(cardInfo.startsWith("4")) { %>
                                <html:img src="visa.gif" bundle="/clientarea/accounting/ApplicationResources" altKey="creditCardManager.image.visa.alt" style="border:1px solid" width="64" height="40"/>
                                <% } else { %>
                                <bean:message bundle="/clientarea/accounting/ApplicationResources" key="creditCardManager.creditCard.cardType.unknown"/>
                                <% } %>
                                <%= creditCard.getCardInfo().replace('X', 'x') %>
                            </td>
                        </tr>
                        <tr>
                            <th style='white-space:nowrap' align='left'><bean:message bundle="/clientarea/accounting/ApplicationResources" key="makePaymentStoredCard.cardComment.prompt"/></th>
                            <td style="white-space:nowrap">
                                <logic:notEmpty name="creditCard" property="description">
                                    <bean:write name="creditCard" property="description"/>
                                </logic:notEmpty>
                                <logic:empty name="creditCard" property="description">
                                    &#160;
                                </logic:empty>
                            </td>
                        </tr>
                        <tr>
                            <th align='left' nowrap><bean:message bundle="/clientarea/accounting/ApplicationResources" key="makePaymentStoredCard.paymentAmount.prompt"/></th>
                            <td style="white-space:nowrap">$<bean:write scope="request" name="transaction" property="transactionRequest.amount"/></td>
                        </tr>
                        <tr>
                            <th align='left' nowrap><bean:message bundle="/clientarea/accounting/ApplicationResources" key="makePaymentStoredCardCompleted.transid.prompt"/></th>
                            <td style="white-space:nowrap"><bean:write scope="request" name="aoTransaction" property="transID"/></td>
                        </tr>
                        <tr>
                            <th align='left' nowrap><bean:message bundle="/clientarea/accounting/ApplicationResources" key="makePaymentStoredCardCompleted.approvalCode.prompt"/></th>
                            <td style="white-space:nowrap"><bean:write scope="request" name="transaction" property="authorizationResult.approvalCode"/></td>
                        </tr>
                        <tr>
                            <th style='white-space:nowrap' align='left'><bean:message bundle="/clientarea/accounting/ApplicationResources" key="makePaymentStoredCardCompleted.newBalance.prompt"/></th>
                            <td style="white-space:nowrap">
                                <% int balance = business.getAccountBalance(); %>
                                <% if(balance==0) { %>
                                    <bean:message bundle="/clientarea/accounting/ApplicationResources" key="makePaymentStoredCardCompleted.newBalance.value.zero"/>
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
                    <bean:message bundle="/clientarea/accounting/ApplicationResources" key="makePaymentStoredCardCompleted.contactAndThankYou"/>
                </skin:lightArea>
                <%@ include file="security-policy.jsp" %>
            </skin:contentLine>
        </skin:content>
    </skin:skin>
</html:html>
