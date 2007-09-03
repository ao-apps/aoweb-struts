<%--
  Copyright 2007 by AO Industries, Inc.,
  816 Azalea Rd, Mobile, Alabama, 36693, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" %>
<%@ page buffer="256kb" %>
<%@ page autoFlush="false" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld"       prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld"       prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld"      prefix="logic" %>
<%@ taglib uri="/WEB-INF/aoweb-struts-skin.tld" prefix="skin" %>
<skin:setContentType/>
<html:html lang="true">
    <skin:path>/clientarea/accounting/make-payment-stored-card.do?accounting=<bean:write scope="request" name="makePaymentStoredCardForm" property="accounting"/>&pkey=<bean:write scope="request" name="makePaymentStoredCardForm" property="pkey"/></skin:path>
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
                    <hr>
                    <bean:message bundle="/clientarea/accounting/ApplicationResources" key="makePaymentStoredCardCompleted.confirm.followingProcessed"/>
                    <bean:define scope="request" name="creditCard" id="creditCard" type="com.aoindustries.aoserv.client.CreditCard"/>
                    <table border='0' cellspacing='0' cellpadding='2'>
                        <tr>
                            <th align='left' nowrap><bean:message bundle="/clientarea/accounting/ApplicationResources" key="makePaymentSelectCard.business.prompt"/></td>
                            <td nowrap><bean:write scope="request" name="business"/></td>
                        </tr>
                        <tr>
                            <th align='left' nowrap><bean:message bundle="/clientarea/accounting/ApplicationResources" key="makePaymentSelectCard.card.prompt"/></td>
                            <td nowrap>
                                <% String cardInfo = creditCard.getCardInfo(); %>
                                <% if(cardInfo.startsWith("34") || cardInfo.startsWith("37")) { %>
                                <html:img src="amex.gif" bundle="/clientarea/accounting/ApplicationResources" altKey="creditCardManager.image.amex.alt" align="absmiddle" border="1" width="64" height="40"/>
                                <% } else if(cardInfo.startsWith("60")) { %>
                                <html:img src="discv.gif" bundle="/clientarea/accounting/ApplicationResources" altKey="creditCardManager.image.discv.alt" align="absmiddle" border="1" width="63" height="40"/>
                                <% } else if(cardInfo.startsWith("51") || cardInfo.startsWith("52") || cardInfo.startsWith("53") || cardInfo.startsWith("54") || cardInfo.startsWith("55")) { %>
                                <html:img src="mcard.gif" bundle="/clientarea/accounting/ApplicationResources" altKey="creditCardManager.image.mcard.alt" align="absmiddle" border="1" width="64" height="40"/>
                                <% } else if(cardInfo.startsWith("4")) { %>
                                <html:img src="visa.gif" bundle="/clientarea/accounting/ApplicationResources" altKey="creditCardManager.image.visa.alt" align="absmiddle" border="1" width="64" height="40"/>
                                <% } else { %>
                                <bean:message bundle="/clientarea/accounting/ApplicationResources" key="creditCardManager.creditCard.cardType.unknown"/>
                                <% } %>
                                <%= creditCard.getCardInfo().replace('X', 'x') %>
                            </td>
                        </tr>
                        <tr>
                            <th nowrap align='left'><bean:message bundle="/clientarea/accounting/ApplicationResources" key="makePaymentSelectCard.cardComment.prompt"/></th>
                            <td nowrap>
                                <logic:notEmpty name="creditCard" property="description">
                                    <bean:write name="creditCard" property="description"/>
                                </logic:notEmpty>
                                <logic:empty name="creditCard" property="description">
                                    &nbsp;
                                </logic:empty>
                            </td>
                        </tr>
                        <tr>
                            <th align='left' nowrap><bean:message bundle="/clientarea/accounting/ApplicationResources" key="makePaymentSelectCard.paymentAmount.prompt"/></td>
                            <td nowrap>$<bean:write scope="request" name="transaction" property="transactionRequest.amount"/></td>
                        </tr>
                        <tr>
                            <th align='left' nowrap><bean:message bundle="/clientarea/accounting/ApplicationResources" key="makePaymentStoredCardCompleted.transid.prompt"/></td>
                            <td nowrap><bean:write scope="request" name="aoTransaction" property="transID"/></td>
                        </tr>
                        <tr>
                            <th align='left' nowrap><bean:message bundle="/clientarea/accounting/ApplicationResources" key="makePaymentStoredCardCompleted.approvalCode.prompt"/></td>
                            <td nowrap><bean:write scope="request" name="transaction" property="authorizationResult.approvalCode"/></td>
                        </tr>
                        <tr>
                            <th nowrap align='left'><bean:message bundle="/clientarea/accounting/ApplicationResources" key="makePaymentStoredCardCompleted.newBalance.prompt"/></th>
                            <td nowrap><bean:write scope="request" name="business" property="accountBalanceString"/></td>
                        </tr>
                    </table><br>
                    <bean:message bundle="/clientarea/accounting/ApplicationResources" key="makePaymentStoredCardCompleted.contactAndThankYou"/>
                </skin:lightArea><br>
                <skin:lightArea width="500">
                    <%@ include file="security-policy.jsp" %>
                </skin:lightArea>
            </skin:contentLine>
        </skin:content>
    </skin:skin>
</html:html>
