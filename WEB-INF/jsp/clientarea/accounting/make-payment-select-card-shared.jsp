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

<form method="POST" action="<%= response.encodeURL("make-payment-stored-card.do") %>">
    <input name="accounting" type="hidden" value="<%= request.getParameter("accounting") %>">
    <skin:lightArea>
        <bean:message bundle="/clientarea/accounting/ApplicationResources" key="makePaymentSelectCard.selectCard.list.title"/>
        <hr>
        <table border="0" cellspacing="0" cellpadding="2">
            <tr>
                <th nowrap><bean:message bundle="/clientarea/accounting/ApplicationResources" key="makePaymentSelectCard.select.header"/></th>
                <th nowrap><bean:message bundle="/clientarea/accounting/ApplicationResources" key="makePaymentSelectCard.cardType.header"/></th>
                <th nowrap><bean:message bundle="/clientarea/accounting/ApplicationResources" key="makePaymentSelectCard.cardNumber.header"/></th>
                <th nowrap><bean:message bundle="/clientarea/accounting/ApplicationResources" key="makePaymentSelectCard.comments.header"/></th>
            </tr>
            <logic:iterate scope="request" name="creditCards" id="creditCard" type="com.aoindustries.aoserv.client.CreditCard" indexId="row">
                <tr class="<%= (row&1)==0 ? "ao_light_row" : "ao_dark_row" %>"
                    <td nowrap>
                        <logic:equal scope="request" name="lastPaymentCreditCard" value="<%= creditCard.getProviderUniqueId() %>">
                            <input type="radio" name="pkey" value="<%= creditCard.getPkey() %>" checked>
                        </logic:equal>
                        <logic:notEqual scope="request" name="lastPaymentCreditCard" value="<%= creditCard.getProviderUniqueId() %>">
                            <input type="radio" name="pkey" value="<%= creditCard.getPkey() %>">
                        </logic:notEqual>
                    </td>
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
                    </td>
                    <td nowrap><%= creditCard.getCardInfo().replace('X', 'x') %></td>
                    <td nowrap>
                        <logic:notEmpty name="creditCard" property="description">
                            <bean:write name="creditCard" property="description"/>
                        </logic:notEmpty>
                        <logic:empty name="creditCard" property="description">
                            &nbsp;
                        </logic:empty>
                    </td>
                </tr>
            </logic:iterate>
            <bean:size scope="request" name="creditCards" id="creditCardsSize"/>
            <tr class="<%= (creditCardsSize&1)==0 ? "ao_light_row" : "ao_dark_row" %>">
                <td nowrap>
                    <logic:equal scope="request" name="lastPaymentCreditCard" value="">
                        <input type="radio" name="pkey" value="" checked>
                    </logic:equal>
                    <logic:notEqual scope="request" name="lastPaymentCreditCard" value="">
                        <input type="radio" name="pkey" value="">
                    </logic:notEqual>
                </td>
                <td nowrap colspan="3"><bean:message bundle="/clientarea/accounting/ApplicationResources" key="makePaymentSelectCard.newCard.link"/></td>
            </tr>
            <tr>
                <td nowrap colspan="4" align="center">
                    <input
                        type="submit"
                        name="submitButton"
                        value="<bean:message bundle="/clientarea/accounting/ApplicationResources" key="makePaymentSelectCard.field.submit.label"/>"
                    >
                </td>
            </tr>
        </table>
    </skin:lightArea><br>
    <skin:lightArea width="500">
        <%@ include file="security-policy.jsp" %>
    </skin:lightArea>
</form>
