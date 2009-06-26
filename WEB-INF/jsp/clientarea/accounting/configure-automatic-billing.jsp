<%-- aoweb-struts: Do not edit --%>
<%--
  Copyright 2007-2009 by AO Industries, Inc.,
  7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" buffer="256kb" autoFlush="true" %>
<%@ include file="/WEB-INF/jsp/taglibs.jsp" %>

<skin:setContentType/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html:html lang="true" xhtml="true">
    <skin:path>/clientarea/accounting/configure-automatic-billing.do?accounting=<%= request.getParameter("accounting") %></skin:path>
    <logic:equal name="siteSettings" property="brand.aowebStrutsNoindex" value="true"><skin:meta name="ROBOTS">NOINDEX</skin:meta></logic:equal>
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
                        <input name="accounting" type="hidden" value="<%= request.getParameter("accounting") %>" />
                        <skin:lightArea>
                            <bean:message bundle="/clientarea/accounting/ApplicationResources" key="configureAutomaticBilling.cardList.title"/>
                            <hr />
                            <bean:message bundle="/clientarea/accounting/ApplicationResources" key="configureAutomaticBilling.business.label"/>
                            <bean:write scope="request" name="business" property="accounting"/><br />
                            <br />
                            <table cellspacing="0" cellpadding="2">
                                <tr>
                                    <th><bean:message bundle="/clientarea/accounting/ApplicationResources" key="configureAutomaticBilling.header.select"/>
                                    <th><bean:message bundle="/clientarea/accounting/ApplicationResources" key="configureAutomaticBilling.header.cardType"/></th>
                                    <th><bean:message bundle="/clientarea/accounting/ApplicationResources" key="configureAutomaticBilling.header.maskedCardNumber"/></th>
                                    <th><bean:message bundle="/clientarea/accounting/ApplicationResources" key="configureAutomaticBilling.header.description"/></th>
                                </tr>
                                <logic:iterate scope="request" name="activeCards" id="creditCard" type="com.aoindustries.aoserv.client.CreditCard" indexId="row">
                                    <skin:lightDarkTableRow>
                                        <td style="white-space:nowrap">
                                            <logic:notPresent scope="request" name="automaticCard">
                                                <input
                                                    type="radio"
                                                    name="pkey"
                                                    value="<%= creditCard.getPkey() %>"
                                                    onChange='this.form.submitButton.disabled=false;'
                                                />
                                            </logic:notPresent>
                                            <logic:present scope="request" name="automaticCard">
                                                <logic:equal scope="request" name="automaticCard" property="pkey" value="<%= Integer.toString(creditCard.getPkey()) %>">
                                                    <input
                                                        type="radio"
                                                        name="pkey"
                                                        value="<%= creditCard.getPkey() %>"
                                                        checked
                                                        onChange='this.form.submitButton.disabled=true;'
                                                    />
                                                </logic:equal>
                                                <logic:notEqual scope="request" name="automaticCard" property="pkey" value="<%= Integer.toString(creditCard.getPkey()) %>">
                                                    <input
                                                        type="radio"
                                                        name="pkey"
                                                        value="<%= creditCard.getPkey() %>"
                                                        onChange='this.form.submitButton.disabled=false;'
                                                    />
                                                </logic:notEqual>
                                            </logic:present>
                                        </td>
                                        <td style="white-space:nowrap">
                                            <% String cardInfo = creditCard.getCardInfo(); %>
                                            <% if(cardInfo.startsWith("34") || cardInfo.startsWith("37")) { %>
                                                <html:img src="amex.gif" bundle="/clientarea/accounting/ApplicationResources" altKey="creditCardManager.image.amex.alt" align="absmiddle" style="border:1px solid" width="64" height="40"/>
                                            <% } else if(cardInfo.startsWith("60")) { %>
                                                <html:img src="discv.gif" bundle="/clientarea/accounting/ApplicationResources" altKey="creditCardManager.image.discv.alt" align="absmiddle" style="border:1px solid" width="63" height="40"/>
                                            <% } else if(cardInfo.startsWith("51") || cardInfo.startsWith("52") || cardInfo.startsWith("53") || cardInfo.startsWith("54") || cardInfo.startsWith("55")) { %>
                                                <html:img src="mcard.gif" bundle="/clientarea/accounting/ApplicationResources" altKey="creditCardManager.image.mcard.alt" align="absmiddle" style="border:1px solid" width="64" height="40"/>
                                            <% } else if(cardInfo.startsWith("4")) { %>
                                                <html:img src="visa.gif" bundle="/clientarea/accounting/ApplicationResources" altKey="creditCardManager.image.visa.alt" align="absmiddle" style="border:1px solid" width="64" height="40"/>
                                            <% } else { %>
                                                <bean:message bundle="/clientarea/accounting/ApplicationResources" key="creditCardManager.creditCard.cardType.unknown"/>
                                            <% } %>
                                        </td>
                                        <td style="white-space:nowrap"><%= creditCard.getCardInfo().replace('X', 'x') %></td>
                                        <td style="white-space:nowrap">
                                            <logic:notEmpty name="creditCard" property="description">
                                                <bean:write name="creditCard" property="description"/>
                                            </logic:notEmpty>
                                            <logic:empty name="creditCard" property="description">
                                                &nbsp;
                                            </logic:empty>
                                        </td>
                                    </skin:lightDarkTableRow>
                                </logic:iterate>
                                <skin:lightDarkTableRow>
                                    <td style="white-space:nowrap">
                                        <logic:notPresent scope="request" name="automaticCard">
                                            <input type="radio" name="pkey" value="" checked onChange='this.form.submitButton.disabled=true;' />
                                        </logic:notPresent>
                                        <logic:present scope="request" name="automaticCard">
                                            <input type="radio" name="pkey" value="" onChange='this.form.submitButton.disabled=false;' />
                                        </logic:present>
                                    </td>
                                    <td style='white-space:nowrap' colspan="3"><bean:message bundle="/clientarea/accounting/ApplicationResources" key="configureAutomaticBilling.noAutomaticBilling"/></td>
                                </skin:lightDarkTableRow>
                                <tr>
                                    <td style='white-space:nowrap' colspan="4" align="center">
                                        <input
                                            type="submit"
                                            name="submitButton"
                                            value="<bean:message bundle="/clientarea/accounting/ApplicationResources" key="configureAutomaticBilling.field.submit.label"/>"
                                            disabled
                                        />
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
