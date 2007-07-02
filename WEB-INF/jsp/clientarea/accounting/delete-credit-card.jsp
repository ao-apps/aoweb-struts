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
    <skin:path>/clientarea/accounting/delete-credit-card.do?pkey=<%= request.getParameter("pkey") %></skin:path>
    <skin:title><bean:message bundle="/clientarea/accounting/ApplicationResources" key="deleteCreditCard.title"/></skin:title>
    <skin:navImageAlt><bean:message bundle="/clientarea/accounting/ApplicationResources" key="deleteCreditCard.navImageAlt"/></skin:navImageAlt>
    <skin:keywords><bean:message bundle="/clientarea/accounting/ApplicationResources" key="deleteCreditCard.keywords"/></skin:keywords>
    <skin:description><bean:message bundle="/clientarea/accounting/ApplicationResources" key="deleteCreditCard.description"/></skin:description>
    <%@ include file="add-parents.jsp" %>
    <skin:addParent useEncryption="true" path="/clientarea/accounting/credit-card-manager.do">
        <skin:title><bean:message bundle="/clientarea/accounting/ApplicationResources" key="creditCardManager.title"/></skin:title>
        <skin:navImageAlt><bean:message bundle="/clientarea/accounting/ApplicationResources" key="creditCardManager.navImageAlt"/></skin:navImageAlt>
    </skin:addParent>
    <%@ include file="add-siblings.jsp" %>
    <skin:skin>
        <skin:content width="600">
            <skin:contentTitle><bean:message bundle="/clientarea/accounting/ApplicationResources" key="deleteCreditCard.title"/></skin:contentTitle>
            <skin:contentHorizontalDivider/>
            <skin:contentLine>
                <logic:present scope="request" name="permissionDenied">
                    <%@ include file="../../permission-denied.jsp" %>
                </logic:present>
                <logic:notPresent scope="request" name="permissionDenied">
                    <bean:define scope="request" name="creditCard" id="creditCard" type="com.aoindustries.aoserv.client.CreditCard"/>
                    <form method="POST" action="<%= response.encodeURL("delete-credit-card-completed.do") %>">
                        <input type="hidden" name="pkey" value="<bean:write name="creditCard" property="PKey"/>">
                        <skin:lightArea>
                            <bean:message bundle="/clientarea/accounting/ApplicationResources" key="deleteCreditCard.confirmation.title"/>
                            <hr>
                            <table border="0" cellspacing="0" cellpadding="2">
                                <tr>
                                    <td colspan="2"><bean:message bundle="/clientarea/accounting/ApplicationResources" key="deleteCreditCard.confirmation.prompt"/></td>
                                </tr>
                                <tr>
                                    <th nowrap class="ao_light_row" align="left"><bean:message bundle="/clientarea/accounting/ApplicationResources" key="deleteCreditCard.accounting.header"/></th>
                                    <td nowrap><bean:write name="creditCard" property="business.accounting"/></td>
                                </tr>
                                <tr>
                                    <th nowrap class="ao_light_row" align="left"><bean:message bundle="/clientarea/accounting/ApplicationResources" key="deleteCreditCard.firstName.header"/></th>
                                    <td nowrap><bean:write name="creditCard" property="firstName"/></td>
                                </tr>
                                <tr>
                                    <th nowrap class="ao_light_row" align="left"><bean:message bundle="/clientarea/accounting/ApplicationResources" key="deleteCreditCard.lastName.header"/></th>
                                    <td nowrap><bean:write name="creditCard" property="lastName"/></td>
                                </tr>
                                <tr>
                                    <th nowrap class="ao_light_row" align="left"><bean:message bundle="/clientarea/accounting/ApplicationResources" key="deleteCreditCard.companyName.header"/></th>
                                    <td nowrap><bean:write name="creditCard" property="companyName"/></td>
                                </tr>
                                <tr>
                                    <th nowrap class="ao_light_row" align="left"><bean:message bundle="/clientarea/accounting/ApplicationResources" key="deleteCreditCard.cardType.header"/></th>
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
                                </tr>
                                <tr>
                                    <th nowrap class="ao_light_row" align="left"><bean:message bundle="/clientarea/accounting/ApplicationResources" key="deleteCreditCard.cardNumber.header"/></th>
                                    <td nowrap><%= creditCard.getCardInfo().replace('X', 'x') %></td>
                                </tr>
                                <tr>
                                    <th nowrap class="ao_light_row" align="left"><bean:message bundle="/clientarea/accounting/ApplicationResources" key="deleteCreditCard.description.header"/></th>
                                    <td nowrap><bean:write name="creditCard" property="description"/></td>
                                </tr>
                                <tr>
                                    <td nowrap colspan="2" align="center">
                                        <br>
                                        <input
                                            class="ao_button"
                                            type="submit"
                                            value="<bean:message bundle="/clientarea/accounting/ApplicationResources" key="deleteCreditCard.field.submit.label"/>"
                                        >
                                        &nbsp;&nbsp;&nbsp;
                                        <input
                                            class="ao_button"
                                            type="button"
                                            value="<bean:message bundle="/clientarea/accounting/ApplicationResources" key="deleteCreditCard.field.cancel.label"/>"
                                            onClick="window.location.href='<%= response.encodeURL("credit-card-manager.do") %>'"
                                        >
                                    </td>
                                </tr>
                            </table>
                        </skin:lightArea>
                    </form>
                </logic:notPresent>
            </skin:contentLine>
        </skin:content>
    </skin:skin>
</html:html>
