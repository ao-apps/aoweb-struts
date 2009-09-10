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
        <skin:path>/clientarea/accounting/delete-credit-card.do?pkey=<%= request.getParameter("pkey") %></skin:path>
        <logic:equal name="siteSettings" property="brand.aowebStrutsNoindex" value="true"><skin:meta name="ROBOTS">NOINDEX</skin:meta></logic:equal>
        <skin:title><fmt:message key="deleteCreditCard.title" /></skin:title>
        <skin:navImageAlt><fmt:message key="deleteCreditCard.navImageAlt" /></skin:navImageAlt>
        <skin:keywords><fmt:message key="deleteCreditCard.keywords" /></skin:keywords>
        <skin:description><fmt:message key="deleteCreditCard.description" /></skin:description>
        <%@ include file="add-parents.jsp" %>
        <skin:addParent useEncryption="true" path="/clientarea/accounting/credit-card-manager.do">
            <skin:title><fmt:message key="creditCardManager.title" /></skin:title>
            <skin:navImageAlt><fmt:message key="creditCardManager.navImageAlt" /></skin:navImageAlt>
        </skin:addParent>
        <%@ include file="add-siblings.jsp" %>
        <skin:skin>
            <skin:content width="600">
                <skin:contentTitle><fmt:message key="deleteCreditCard.title" /></skin:contentTitle>
                <skin:contentHorizontalDivider />
                <skin:contentLine>
                    <logic:present scope="request" name="permissionDenied">
                        <%@ include file="../../permission-denied.jsp" %>
                    </logic:present>
                    <logic:notPresent scope="request" name="permissionDenied">
                        <bean:define scope="request" name="creditCard" id="creditCard" type="com.aoindustries.aoserv.client.CreditCard" />
                        <form method="post" action="<ao:url>delete-credit-card-completed.do</ao:url>"><div>
                            <input type="hidden" name="pkey" value="<ao:write name="creditCard" property="pkey" />" />
                            <skin:lightArea>
                                <fmt:message key="deleteCreditCard.confirmation.title" />
                                <hr />
                                <table cellspacing="0" cellpadding="2">
                                    <tr>
                                        <td colspan="2"><fmt:message key="deleteCreditCard.confirmation.prompt" /></td>
                                    </tr>
                                    <tr>
                                        <th style='white-space:nowrap' class="aoLightRow" align="left"><fmt:message key="deleteCreditCard.accounting.header" /></th>
                                        <td style="white-space:nowrap"><ao:write name="creditCard" property="business.accounting" /></td>
                                    </tr>
                                    <tr>
                                        <th style='white-space:nowrap' class="aoLightRow" align="left"><fmt:message key="deleteCreditCard.firstName.header" /></th>
                                        <td style="white-space:nowrap"><ao:write name="creditCard" property="firstName" /></td>
                                    </tr>
                                    <tr>
                                        <th style='white-space:nowrap' class="aoLightRow" align="left"><fmt:message key="deleteCreditCard.lastName.header" /></th>
                                        <td style="white-space:nowrap"><ao:write name="creditCard" property="lastName" /></td>
                                    </tr>
                                    <tr>
                                        <th style='white-space:nowrap' class="aoLightRow" align="left"><fmt:message key="deleteCreditCard.companyName.header" /></th>
                                        <td style="white-space:nowrap"><ao:write name="creditCard" property="companyName" /></td>
                                    </tr>
                                    <tr>
                                        <th style='white-space:nowrap' class="aoLightRow" align="left"><fmt:message key="deleteCreditCard.cardType.header" /></th>
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
                                        </td>
                                    </tr>
                                    <tr>
                                        <th style='white-space:nowrap' class="aoLightRow" align="left"><fmt:message key="deleteCreditCard.cardNumber.header" /></th>
                                        <td style="white-space:nowrap"><%= creditCard.getCardInfo().replace('X', 'x') %></td>
                                    </tr>
                                    <tr>
                                        <th style='white-space:nowrap' class="aoLightRow" align="left"><fmt:message key="deleteCreditCard.description.header" /></th>
                                        <td style="white-space:nowrap"><ao:write name="creditCard" property="description" /></td>
                                    </tr>
                                    <tr>
                                        <td style='white-space:nowrap' colspan="2" align="center">
                                            <br />
                                            <ao:input type="submit"><fmt:message key="deleteCreditCard.field.submit.label" /></ao:input>
                                            &#160;&#160;&#160;
                                            <ao:input type="button">
                                                <ao:value><fmt:message key="deleteCreditCard.field.cancel.label" /></ao:value>
                                                <ao:onclick>window.location.href=<ao:url>credit-card-manager.do</ao:url>;</ao:onclick>
                                            </ao:input>
                                        </td>
                                    </tr>
                                </table>
                            </skin:lightArea>
                        </div></form>
                    </logic:notPresent>
                </skin:contentLine>
            </skin:content>
        </skin:skin>
    </fmt:bundle>
</html:html>
