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
    <skin:path>/clientarea/accounting/configure-automatic-billing.do?accounting=<%= request.getParameter("accounting") %></skin:path>
    <skin:title><bean:message bundle="/clientarea/accounting/ApplicationResources" key="configureAutomaticBilling.title"/></skin:title>
    <skin:navImageAlt><bean:message bundle="/clientarea/accounting/ApplicationResources" key="configureAutomaticBilling.navImageAlt"/></skin:navImageAlt>
    <skin:keywords><bean:message bundle="/clientarea/accounting/ApplicationResources" key="configureAutomaticBilling.keywords"/></skin:keywords>
    <skin:description><bean:message bundle="/clientarea/accounting/ApplicationResources" key="configureAutomaticBilling.description"/></skin:description>
    <%@ include file="add-parents.jsp" %>
    <skin:addParent useEncryption="true" path="/clientarea/accounting/credit-card-manager.do">
        <skin:title><bean:message bundle="/clientarea/accounting/ApplicationResources" key="creditCardManager.title"/></skin:title>
        <skin:navImageAlt><bean:message bundle="/clientarea/accounting/ApplicationResources" key="creditCardManager.navImageAlt"/></skin:navImageAlt>
    </skin:addParent>
    <%@ include file="add-siblings.jsp" %>
    <skin:skin>
        <skin:content width="600">
            <skin:contentTitle><bean:message bundle="/clientarea/accounting/ApplicationResources" key="configureAutomaticBilling.title"/></skin:contentTitle>
            <skin:contentHorizontalDivider/>
            <skin:contentLine>
                <logic:present scope="request" name="permissionDenied">
                    <%@ include file="../../permission-denied.jsp" %>
                </logic:present>
                <logic:notPresent scope="request" name="permissionDenied">
                    <form method="POST" action="<%= response.encodeURL("configure-automatic-billing-completed.do") %>">
                        <input name="accounting" type="hidden" value="<%= request.getParameter("accounting") %>">
                        <skin:lightArea>
                            <bean:message bundle="/clientarea/accounting/ApplicationResources" key="configureAutomaticBilling.cardList.title"/>
                            <hr>
                            <bean:message bundle="/clientarea/accounting/ApplicationResources" key="configureAutomaticBilling.business.label"/>
                            <bean:write scope="request" name="business" property="accounting"/><br>
                            <br>
                            <table border="0" cellspacing="0" cellpadding="2">
                                <tr>
                                    <th><bean:message bundle="/clientarea/accounting/ApplicationResources" key="configureAutomaticBilling.header.select"/>
                                    <th><bean:message bundle="/clientarea/accounting/ApplicationResources" key="configureAutomaticBilling.header.cardType"/></th>
                                    <th><bean:message bundle="/clientarea/accounting/ApplicationResources" key="configureAutomaticBilling.header.maskedCardNumber"/></th>
                                    <th><bean:message bundle="/clientarea/accounting/ApplicationResources" key="configureAutomaticBilling.header.description"/></th>
                                </tr>
                                <logic:iterate scope="request" name="activeCards" id="creditCard" type="com.aoindustries.aoserv.client.CreditCard" indexId="row">
                                    <tr class="<%= (row&1)==0 ? "ao_light_row" : "ao_dark_row" %>">
                                        <td nowrap>
                                            <logic:notPresent scope="request" name="automaticCard">
                                                <input
                                                    type="radio"
                                                    name="pkey"
                                                    value="<%= creditCard.getPKey() %>"
                                                    onChange='this.form.submitButton.disabled=false;'
                                                >
                                            </logic:notPresent>
                                            <logic:present scope="request" name="automaticCard">
                                                <logic:equal scope="request" name="automaticCard" property="PKey" value="<%= Integer.toString(creditCard.getPKey()) %>">
                                                    <input
                                                        type="radio"
                                                        name="pkey"
                                                        value="<%= creditCard.getPKey() %>"
                                                        checked
                                                        onChange='this.form.submitButton.disabled=true;'
                                                    >
                                                </logic:equal>
                                                <logic:notEqual scope="request" name="automaticCard" property="PKey" value="<%= Integer.toString(creditCard.getPKey()) %>">
                                                    <input
                                                        type="radio"
                                                        name="pkey"
                                                        value="<%= creditCard.getPKey() %>"
                                                        onChange='this.form.submitButton.disabled=false;'
                                                    >
                                                </logic:notEqual>
                                            </logic:present>
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
                                <bean:size scope="request" name="activeCards" id="activeCardsSize"/>
                                <tr class="<%= (activeCardsSize&1)==0 ? "ao_light_row" : "ao_dark_row" %>">
                                    <td nowrap>
                                        <logic:notPresent scope="request" name="automaticCard">
                                            <input type="radio" name="pkey" value="" checked onChange='this.form.submitButton.disabled=true;'>
                                        </logic:notPresent>
                                        <logic:present scope="request" name="automaticCard">
                                            <input type="radio" name="pkey" value="" onChange='this.form.submitButton.disabled=false;'>
                                        </logic:present>
                                    </td>
                                    <td nowrap colspan="3"><bean:message bundle="/clientarea/accounting/ApplicationResources" key="configureAutomaticBilling.noAutomaticBilling"/></td>
                                </tr>
                                <tr>
                                    <td nowrap colspan="4" align="center">
                                        <input
                                            type="submit"
                                            name="submitButton"
                                            value="<bean:message bundle="/clientarea/accounting/ApplicationResources" key="configureAutomaticBilling.field.submit.label"/>"
                                            disabled
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
