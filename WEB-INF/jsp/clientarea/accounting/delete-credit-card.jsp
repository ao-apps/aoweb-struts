<%-- aoweb-struts: Do not edit --%>
<%--
  Copyright 2007-2009 by AO Industries, Inc.,
  7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" buffer="256kb" autoFlush="true" pageEncoding="UTF-8" %>
<%@ page import="com.aoindustries.util.EncodingUtils" %>
<%@ page import="com.aoindustries.util.StringUtility" %>
<%@ include file="/WEB-INF/jsp/taglibs.jsp" %>

<skin:setContentType />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html:html lang="true" xhtml="true">
    <skin:path>/clientarea/accounting/delete-credit-card.do?pkey=<%= request.getParameter("pkey") %></skin:path>
    <logic:equal name="siteSettings" property="brand.aowebStrutsNoindex" value="true"><skin:meta name="ROBOTS">NOINDEX</skin:meta></logic:equal>
    <skin:title><bean:message bundle="/clientarea/accounting/ApplicationResources" key="deleteCreditCard.title" /></skin:title>
    <skin:navImageAlt><bean:message bundle="/clientarea/accounting/ApplicationResources" key="deleteCreditCard.navImageAlt" /></skin:navImageAlt>
    <skin:keywords><bean:message bundle="/clientarea/accounting/ApplicationResources" key="deleteCreditCard.keywords" /></skin:keywords>
    <skin:description><bean:message bundle="/clientarea/accounting/ApplicationResources" key="deleteCreditCard.description" /></skin:description>
    <%@ include file="add-parents.jsp" %>
    <skin:addParent useEncryption="true" path="/clientarea/accounting/credit-card-manager.do">
        <skin:title><bean:message bundle="/clientarea/accounting/ApplicationResources" key="creditCardManager.title" /></skin:title>
        <skin:navImageAlt><bean:message bundle="/clientarea/accounting/ApplicationResources" key="creditCardManager.navImageAlt" /></skin:navImageAlt>
    </skin:addParent>
    <%@ include file="add-siblings.jsp" %>
    <skin:skin>
        <skin:content width="600">
            <skin:contentTitle><bean:message bundle="/clientarea/accounting/ApplicationResources" key="deleteCreditCard.title" /></skin:contentTitle>
            <skin:contentHorizontalDivider />
            <skin:contentLine>
                <logic:present scope="request" name="permissionDenied">
                    <%@ include file="../../permission-denied.jsp" %>
                </logic:present>
                <logic:notPresent scope="request" name="permissionDenied">
                    <bean:define scope="request" name="creditCard" id="creditCard" type="com.aoindustries.aoserv.client.CreditCard" />
                    <form method="post" action="<%= response.encodeURL("delete-credit-card-completed.do") %>">
                        <input type="hidden" name="pkey" value="<ao:write name="creditCard" property="pkey" />" />
                        <skin:lightArea>
                            <bean:message bundle="/clientarea/accounting/ApplicationResources" key="deleteCreditCard.confirmation.title" />
                            <hr />
                            <table cellspacing="0" cellpadding="2">
                                <tr>
                                    <td colspan="2"><bean:message bundle="/clientarea/accounting/ApplicationResources" key="deleteCreditCard.confirmation.prompt" /></td>
                                </tr>
                                <tr>
                                    <th style='white-space:nowrap' class="aoLightRow" align="left"><bean:message bundle="/clientarea/accounting/ApplicationResources" key="deleteCreditCard.accounting.header" /></th>
                                    <td style="white-space:nowrap"><ao:write name="creditCard" property="business.accounting" /></td>
                                </tr>
                                <tr>
                                    <th style='white-space:nowrap' class="aoLightRow" align="left"><bean:message bundle="/clientarea/accounting/ApplicationResources" key="deleteCreditCard.firstName.header" /></th>
                                    <td style="white-space:nowrap"><ao:write name="creditCard" property="firstName" /></td>
                                </tr>
                                <tr>
                                    <th style='white-space:nowrap' class="aoLightRow" align="left"><bean:message bundle="/clientarea/accounting/ApplicationResources" key="deleteCreditCard.lastName.header" /></th>
                                    <td style="white-space:nowrap"><ao:write name="creditCard" property="lastName" /></td>
                                </tr>
                                <tr>
                                    <th style='white-space:nowrap' class="aoLightRow" align="left"><bean:message bundle="/clientarea/accounting/ApplicationResources" key="deleteCreditCard.companyName.header" /></th>
                                    <td style="white-space:nowrap"><ao:write name="creditCard" property="companyName" /></td>
                                </tr>
                                <tr>
                                    <th style='white-space:nowrap' class="aoLightRow" align="left"><bean:message bundle="/clientarea/accounting/ApplicationResources" key="deleteCreditCard.cardType.header" /></th>
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
                                            <bean:message bundle="/clientarea/accounting/ApplicationResources" key="creditCardManager.creditCard.cardType.unknown" />
                                        <% } %>
                                    </td>
                                </tr>
                                <tr>
                                    <th style='white-space:nowrap' class="aoLightRow" align="left"><bean:message bundle="/clientarea/accounting/ApplicationResources" key="deleteCreditCard.cardNumber.header" /></th>
                                    <td style="white-space:nowrap"><%= creditCard.getCardInfo().replace('X', 'x') %></td>
                                </tr>
                                <tr>
                                    <th style='white-space:nowrap' class="aoLightRow" align="left"><bean:message bundle="/clientarea/accounting/ApplicationResources" key="deleteCreditCard.description.header" /></th>
                                    <td style="white-space:nowrap"><ao:write name="creditCard" property="description" /></td>
                                </tr>
                                <tr>
                                    <td style='white-space:nowrap' colspan="2" align="center">
                                        <br />
                                        <input
                                            type="submit"
                                            value="<bean:message bundle="/clientarea/accounting/ApplicationResources" key="deleteCreditCard.field.submit.label" />"
                                        />
                                        &#160;&#160;&#160;
                                        <input
                                            type="button"
                                            value="<bean:message bundle="/clientarea/accounting/ApplicationResources" key="deleteCreditCard.field.cancel.label" />"
                                            onclick="window.location.href='<% EncodingUtils.encodeJavaScriptStringInXmlAttribute(StringUtility.replace(response.encodeURL("credit-card-manager.do"), "&amp;", "&"), out); %>'"
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
