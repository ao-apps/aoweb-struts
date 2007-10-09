<%--
  Copyright 2007 by AO Industries, Inc.,
  816 Azalea Rd, Mobile, Alabama, 36693, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" %>
<%@ page buffer="256kb" %>
<%@ page autoFlush="true" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld"       prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld"       prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld"      prefix="logic" %>
<%@ taglib uri="/WEB-INF/aoweb-struts-skin.tld" prefix="skin" %>
<skin:setContentType/>
<html:html lang="true">
    <skin:path>/clientarea/accounting/make-payment-new-card.do?accounting=<bean:write scope="request" name="makePaymentNewCardForm" property="accounting"/></skin:path>
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
                <skin:popupGroup>
                    <html:form action="/make-payment-new-card-completed">
                        <skin:lightArea>
                            <bean:message bundle="/clientarea/accounting/ApplicationResources" key="makePaymentNewCard.form.title"/>
                            <hr>
                            <logic:present scope="request" name="errorReason">
                                <bean:define scope="request" name="errorReason" id="errorReason" type="java.lang.String"/>
                                <bean:message bundle="/clientarea/accounting/ApplicationResources" key="makePaymentStoredCardError.error.description" arg0="<%= errorReason %>"/>
                                <hr>
                            </logic:present>
                            <logic:present scope="request" name="declineReason">
                                <bean:define scope="request" name="declineReason" id="declineReason" type="java.lang.String"/>
                                <bean:message bundle="/clientarea/accounting/ApplicationResources" key="makePaymentStoredCardDeclined.declined.description" arg0="<%= declineReason %>"/>
                                <hr>
                            </logic:present>
                            <bean:define scope="request" name="business" id="business" type="com.aoindustries.aoserv.client.Business"/>
                            <table border="0" cellspacing="0" cellpadding="2">
                                <bean:define name="makePaymentNewCardForm" id="creditCardForm"/>
                                <%@ include file="credit-card-form.jsp" %>
                                <tr>
                                    <td nowrap><bean:message bundle="/clientarea/accounting/ApplicationResources" key="creditCardForm.required.no"/></td>
                                    <th nowrap align='left'><bean:message bundle="/clientarea/accounting/ApplicationResources" key="makePaymentStoredCard.accountBalance.prompt"/></th>
                                    <td nowrap>
                                        <% int balance = business.getAccountBalance(); %>
                                        <% if(balance==0) { %>
                                            <bean:message bundle="/clientarea/accounting/ApplicationResources" key="makePaymentSelectCard.balance.value.zero"/>
                                        <% } else if(balance<0) { %>
                                            <bean:message
                                                bundle="/clientarea/accounting/ApplicationResources" key="makePaymentSelectCard.balance.value.credit"
                                                arg0="<%= com.aoindustries.sql.SQLUtility.getDecimal(-balance) %>"
                                            />
                                        <% } else { %>
                                            <bean:message
                                                bundle="/clientarea/accounting/ApplicationResources" key="makePaymentSelectCard.balance.value.debt"
                                                arg0="<%= com.aoindustries.sql.SQLUtility.getDecimal(balance) %>"
                                            />
                                        <% } %>
                                    </td>
                                    <td nowrap><html:errors bundle="/clientarea/accounting/ApplicationResources" property="accountBalance"/></td>
                                </tr>
                                <tr>
                                    <td nowrap><bean:message bundle="/clientarea/accounting/ApplicationResources" key="creditCardForm.required.yes"/></td>
                                    <th nowrap align='left'><bean:message bundle="/clientarea/accounting/ApplicationResources" key="makePaymentStoredCard.paymentAmount.prompt"/></th>
                                    <td nowrap>$<html:text property="paymentAmount" size="8"/></td>
                                    <td nowrap><html:errors bundle="/clientarea/accounting/ApplicationResources" property="paymentAmount"/></td>
                                </tr>
                                <tr>
                                    <td nowrap><bean:message bundle="/clientarea/accounting/ApplicationResources" key="creditCardForm.required.no"/></td>
                                    <th nowrap align='left'><bean:message bundle="/clientarea/accounting/ApplicationResources" key="makePaymentNewCard.storeCard.prompt"/></th>
                                    <td nowrap colspan="2">
                                        <html:radio property="storeCard" value=""><bean:message bundle="/clientarea/accounting/ApplicationResources" key="makePaymentNewCard.storeCard.no"/></html:radio><br>
                                        <html:radio property="storeCard" value="store"><bean:message bundle="/clientarea/accounting/ApplicationResources" key="makePaymentNewCard.storeCard.store"/></html:radio><br>
                                        <html:radio property="storeCard" value="automatic"><bean:message bundle="/clientarea/accounting/ApplicationResources" key="makePaymentNewCard.storeCard.automatic"/></html:radio>
                                    </td>
                                </tr>
                                <tr><td nowrap colspan="4" align="center"><html:submit><bean:message bundle="/clientarea/accounting/ApplicationResources" key="makePaymentNewCard.field.submit.label"/></html:submit></td></tr>
                            </table>
                        </skin:lightArea>
                    </html:form><br>
                    <skin:lightArea width="500">
                        <%@ include file="security-policy.jsp" %>
                    </skin:lightArea>
                </skin:popupGroup>
            </skin:contentLine>
        </skin:content>
    </skin:skin>
</html:html>
