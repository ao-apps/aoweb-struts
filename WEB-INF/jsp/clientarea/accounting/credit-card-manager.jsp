<%-- aoweb-struts --%>
<%--
  Copyright 2007-2009 by AO Industries, Inc.,
  7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" buffer="256kb" autoFlush="true" %>
<%@ include file="/WEB-INF/jsp/taglibs.jsp" %>

<skin:setContentType/>
<html:html lang="true">
    <skin:path>/clientarea/accounting/credit-card-manager.do</skin:path>
    <logic:equal name="siteSettings" property="brand.aowebStrutsNoindex" value="true"><skin:meta name="ROBOTS">NOINDEX</skin:meta></logic:equal>
    <skin:title><bean:message bundle="/clientarea/accounting/ApplicationResources" key="creditCardManager.title"/></skin:title>
    <skin:navImageAlt><bean:message bundle="/clientarea/accounting/ApplicationResources" key="creditCardManager.navImageAlt"/></skin:navImageAlt>
    <skin:keywords><bean:message bundle="/clientarea/accounting/ApplicationResources" key="creditCardManager.keywords"/></skin:keywords>
    <skin:description><bean:message bundle="/clientarea/accounting/ApplicationResources" key="creditCardManager.description"/></skin:description>
    <%@ include file="add-parents.jsp" %>
    <%@ include file="add-siblings.jsp" %>
    <skin:skin>
        <skin:content width="600">
            <skin:contentTitle><bean:message bundle="/clientarea/accounting/ApplicationResources" key="creditCardManager.title"/></skin:contentTitle>
            <skin:contentHorizontalDivider/>
            <skin:contentLine>
                <logic:present scope="request" name="permissionDenied">
                    <%@ include file="../../permission-denied.jsp" %>
                </logic:present>
                <logic:notPresent scope="request" name="permissionDenied">
                    <skin:popupGroup>
                        <skin:lightArea>
                            <table border="0" cellspacing="0" cellpadding="2">
                                <tr>
                                    <logic:equal scope="request" name="showAccounting" value="true">
                                        <th><bean:message bundle="/clientarea/accounting/ApplicationResources" key="creditCardManager.header.accounting"/></th>
                                    </logic:equal>
                                    <th><bean:message bundle="/clientarea/accounting/ApplicationResources" key="creditCardManager.header.cardType"/></th>
                                    <th><bean:message bundle="/clientarea/accounting/ApplicationResources" key="creditCardManager.header.maskedCardNumber"/></th>
                                    <th nowrap>
                                        <bean:message bundle="/clientarea/accounting/ApplicationResources" key="creditCardManager.header.status"/>
                                        <skin:popup>
                                            <bean:message bundle="/clientarea/accounting/ApplicationResources" key="creditCardManager.header.status.popup"/>
                                        </skin:popup>
                                    </th>
                                    <th colspan="2"><bean:message bundle="/clientarea/accounting/ApplicationResources" key="creditCardManager.header.actions"/></th>
                                    <th><bean:message bundle="/clientarea/accounting/ApplicationResources" key="creditCardManager.header.description"/></th>
                                </tr>
                                <logic:iterate scope="request" name="businessCreditCards" id="businessAndCreditCards" indexId="businessesIndex">
                                    <bean:define name="businessAndCreditCards" property="creditCards" id="creditCards" type="java.util.List<com.aoindustries.aoserv.client.CreditCard>"/>
                                    <bean:size name="creditCards" id="creditCardsSize"/>
                                    <%--tr class="<%= (businessesIndex&1)==0 ? "ao_light_row" : "ao_dark_row" %>">
                                        <td colspan="7"><hr></td>
                                    </tr--%>
                                    <logic:notEqual name="creditCardsSize" value="0">
                                        <tr class="<%= (businessesIndex&1)==0 ? "ao_light_row" : "ao_dark_row" %>">
                                            <logic:equal scope="request" name="showAccounting" value="true">
                                                <td nowrap rowspan="<%= creditCardsSize+1 %>"><bean:write name="businessAndCreditCards" property="business.accounting"/></td>
                                            </logic:equal>
                                            <logic:iterate name="creditCards" id="creditCard" type="com.aoindustries.aoserv.client.CreditCard" indexId="creditCardsIndex">
                                                <logic:notEqual name="creditCardsIndex" value="0">
                                                    </tr>
                                                    <tr class="<%= ((businessesIndex+creditCardsIndex)&1)==0 ? "ao_light_row" : "ao_dark_row" %>">
                                                </logic:notEqual>
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
                                                <logic:equal name="creditCard" property="isActive" value="true">
                                                    <logic:notEqual name="creditCard" property="useMonthly" value="true">
                                                        <td nowrap><bean:message bundle="/clientarea/accounting/ApplicationResources" key="creditCardManager.header.status.active"/></td>
                                                    </logic:notEqual>
                                                    <logic:equal name="creditCard" property="useMonthly" value="true">
                                                        <td nowrap>
                                                            <bean:message bundle="/clientarea/accounting/ApplicationResources" key="creditCardManager.header.status.useMonthly"/>
                                                            <skin:popup width="200">
                                                                <bean:message bundle="/clientarea/accounting/ApplicationResources" key="creditCardManager.status.useMonthly.popup"/>
                                                            </skin:popup>
                                                        </td>
                                                    </logic:equal>
                                                </logic:equal>
                                                <logic:notEqual name="creditCard" property="isActive" value="true">
                                                    <td nowrap>
                                                        <bean:message bundle="/clientarea/accounting/ApplicationResources" key="creditCardManager.header.status.deactivated"/>
                                                        <logic:notEmpty name="creditCard" property="deactivatedOnString">
                                                            <logic:notEmpty name="creditCard" property="deactivateReason">
                                                                <skin:popup width="280">
                                                                    <bean:message
                                                                        bundle="/clientarea/accounting/ApplicationResources"
                                                                        key="creditCardManager.header.status.deactivated.popup"
                                                                        arg0="<%= creditCard.getDeactivatedOnString() %>"
                                                                        arg1="<%= creditCard.getDeactivateReason() %>"
                                                                    />
                                                                </skin:popup>
                                                            </logic:notEmpty>
                                                        </logic:notEmpty>
                                                    </td>
                                                </logic:notEqual>
                                                <td nowrap>
                                                    <html:link action="/edit-credit-card" paramId="persistenceId" paramName="creditCard" paramProperty="pkey">
                                                        <logic:equal name="creditCard" property="isActive" value="true">
                                                            <bean:message bundle="/clientarea/accounting/ApplicationResources" key="creditCardManager.edit.link"/>
                                                        </logic:equal>
                                                        <logic:notEqual name="creditCard" property="isActive" value="true">
                                                            <bean:message bundle="/clientarea/accounting/ApplicationResources" key="creditCardManager.reactivate.link"/>
                                                        </logic:notEqual>
                                                    </html:link>
                                                </td>
                                                <td nowrap>
                                                    <html:link action="/delete-credit-card" paramId="pkey" paramName="creditCard" paramProperty="pkey">
                                                        <bean:message bundle="/clientarea/accounting/ApplicationResources" key="creditCardManager.delete.link"/>
                                                    </html:link>
                                                </td>
                                                <td nowrap>
                                                    <logic:notEmpty name="creditCard" property="description">
                                                        <bean:write name="creditCard" property="description"/>
                                                    </logic:notEmpty>
                                                    <logic:empty name="creditCard" property="description">
                                                        &nbsp;
                                                    </logic:empty>
                                                </td>
                                            </logic:iterate>
                                        </tr>
                                    </logic:notEqual>
                                    <tr class="<%= (businessesIndex&1)==0 ? "ao_light_row" : "ao_dark_row" %>">
                                        <logic:equal name="creditCardsSize" value="0">
                                            <logic:equal scope="request" name="showAccounting" value="true">
                                                <td rowspan="<%= creditCardsSize+1 %>"><bean:write name="businessAndCreditCards" property="business.accounting"/></td>
                                            </logic:equal>
                                        </logic:equal>
                                        <td nowrap colspan="7">
                                            <html:link action="/add-credit-card" paramId="accounting" paramName="businessAndCreditCards" paramProperty="business.accounting">
                                                <bean:message bundle="/clientarea/accounting/ApplicationResources" key="creditCardManager.addCreditCard.link"/>
                                            </html:link>
                                            <logic:equal name="businessAndCreditCards" property="hasActiveCard" value="true">
                                                |
                                                <html:link action="/configure-automatic-billing" paramId="accounting" paramName="businessAndCreditCards" paramProperty="business.accounting">
                                                    <bean:message bundle="/clientarea/accounting/ApplicationResources" key="creditCardManager.configureAutomaticBilling.link"/>
                                                </html:link>
                                            </logic:equal>
                                        </td>
                                    </tr>
                                </logic:iterate>
                            </table>
                        </skin:lightArea>
                        <%@ include file="security-policy.jsp" %>
                    </skin:popupGroup>
                </logic:notPresent>
            </skin:contentLine>
        </skin:content>
    </skin:skin>
</html:html>
