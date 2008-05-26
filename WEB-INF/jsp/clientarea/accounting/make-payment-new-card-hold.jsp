<%--
  Copyright 2007 by AO Industries, Inc.,
  816 Azalea Rd, Mobile, Alabama, 36693, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" %>
<%@ page buffer="256kb" %>
<%@ page autoFlush="true" %>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/aoweb-struts-skin.tld" prefix="skin" %>
<skin:setContentType/>
<html:html lang="true">
    <skin:path>/clientarea/accounting/make-payment.do</skin:path>
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
                    <bean:message bundle="/clientarea/accounting/ApplicationResources" key="makePaymentStoredCardHold.hold.title"/>
                    <hr>
                    <bean:define scope="request" name="reviewReason" id="reviewReason" type="java.lang.String"/>
                    <bean:message bundle="/clientarea/accounting/ApplicationResources" key="makePaymentStoredCardHold.hold.followingProcessed" arg0="<%= reviewReason %>"/>
                    <%-- Card stored --%>
                    <logic:equal scope="request" name="cardStored" value="true">
                        <bean:message bundle="/clientarea/accounting/ApplicationResources" key="makePaymentNewCardCompleted.cardStored" />
                    </logic:equal>

                    <%-- Card store error --%>
                    <logic:present scope="request" name="storeError">
                        <bean:message bundle="/clientarea/accounting/ApplicationResources" key="makePaymentNewCardCompleted.storeError" />
                    </logic:present>
                    
                    <%-- Card set automatic --%>
                    <logic:equal scope="request" name="cardSetAutomatic" value="true">
                        <bean:message bundle="/clientarea/accounting/ApplicationResources" key="makePaymentNewCardCompleted.cardSetAutomatic" />
                    </logic:equal>
                    
                    <%-- Cart set automatic error --%>
                    <logic:present scope="request" name="setAutomaticError">
                        <bean:message bundle="/clientarea/accounting/ApplicationResources" key="makePaymentNewCardCompleted.setAutomaticError" />
                    </logic:present>

                    <bean:message bundle="/clientarea/accounting/ApplicationResources" key="makePaymentStoredCardHold.hold.detailsFollow"/>

                    <bean:define scope="request" name="business" id="business" type="com.aoindustries.aoserv.client.Business"/>
                    <bean:define scope="request" name="makePaymentNewCardForm" property="cardNumber" id="cardNumber" type="java.lang.String"/>
                    <table border='0' cellspacing='0' cellpadding='2'>
                        <tr>
                            <th align='left' nowrap><bean:message bundle="/clientarea/accounting/ApplicationResources" key="makePaymentStoredCard.business.prompt"/></td>
                            <td nowrap><bean:write scope="request" name="business"/></td>
                        </tr>
                        <tr>
                            <th align='left' nowrap><bean:message bundle="/clientarea/accounting/ApplicationResources" key="makePaymentStoredCard.card.prompt"/></td>
                            <td nowrap>
                                <% if(cardNumber.startsWith("34") || cardNumber.startsWith("37")) { %>
                                <html:img src="amex.gif" bundle="/clientarea/accounting/ApplicationResources" altKey="creditCardManager.image.amex.alt" align="absmiddle" border="1" width="64" height="40"/>
                                <% } else if(cardNumber.startsWith("60")) { %>
                                <html:img src="discv.gif" bundle="/clientarea/accounting/ApplicationResources" altKey="creditCardManager.image.discv.alt" align="absmiddle" border="1" width="63" height="40"/>
                                <% } else if(cardNumber.startsWith("51") || cardNumber.startsWith("52") || cardNumber.startsWith("53") || cardNumber.startsWith("54") || cardNumber.startsWith("55")) { %>
                                <html:img src="mcard.gif" bundle="/clientarea/accounting/ApplicationResources" altKey="creditCardManager.image.mcard.alt" align="absmiddle" border="1" width="64" height="40"/>
                                <% } else if(cardNumber.startsWith("4")) { %>
                                <html:img src="visa.gif" bundle="/clientarea/accounting/ApplicationResources" altKey="creditCardManager.image.visa.alt" align="absmiddle" border="1" width="64" height="40"/>
                                <% } else { %>
                                <bean:message bundle="/clientarea/accounting/ApplicationResources" key="creditCardManager.creditCard.cardType.unknown"/>
                                <% } %>
                                <%= com.aoindustries.creditcards.CreditCard.maskCreditCardNumber(cardNumber).replace('X', 'x') %>
                            </td>
                        </tr>
                        <tr>
                            <th nowrap align='left'><bean:message bundle="/clientarea/accounting/ApplicationResources" key="makePaymentStoredCard.cardComment.prompt"/></th>
                            <td nowrap>
                                <logic:notEmpty scope="request" name="makePaymentNewCardForm" property="description">
                                    <bean:write scope="request" name="makePaymentNewCardForm" property="description"/>
                                </logic:notEmpty>
                                <logic:empty scope="request" name="makePaymentNewCardForm" property="description">
                                    &nbsp;
                                </logic:empty>
                            </td>
                        </tr>
                        <tr>
                            <th align='left' nowrap><bean:message bundle="/clientarea/accounting/ApplicationResources" key="makePaymentStoredCard.paymentAmount.prompt"/></td>
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
                            <td nowrap>
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
                    </table><br>

                    <bean:message bundle="/clientarea/accounting/ApplicationResources" key="makePaymentStoredCardCompleted.contactAndThankYou"/>
                </skin:lightArea><br>
                <%@ include file="security-policy.jsp" %>
            </skin:contentLine>
        </skin:content>
    </skin:skin>
</html:html>
