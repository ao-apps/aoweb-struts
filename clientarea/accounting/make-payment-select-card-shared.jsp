<%-- aoweb-struts: Do not edit --%>
<%--
  Copyright 2007-2011 by AO Industries, Inc.,
  7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" buffer="256kb" autoFlush="true" pageEncoding="UTF-8" %>
<%@include file="/_taglibs.jsp" %>

<form method="post" action="<ao:url>make-payment-stored-card.do</ao:url>">
    <div>
        <input name="accounting" type="hidden" value="<%= request.getParameter("accounting") %>" />
        <skin:lightArea>
            <fmt:bundle basename="com.aoindustries.website.clientarea.accounting.ApplicationResources">
                <fmt:message key="makePaymentSelectCard.selectCard.list.title" />
                <hr />
                <table cellspacing="0" cellpadding="2">
                    <tr>
                        <th style='white-space:nowrap'><fmt:message key="makePaymentSelectCard.select.header" /></th>
                        <th style='white-space:nowrap'><fmt:message key="makePaymentSelectCard.cardType.header" /></th>
                        <th style='white-space:nowrap'><fmt:message key="makePaymentSelectCard.cardNumber.header" /></th>
                        <th style='white-space:nowrap'><fmt:message key="makePaymentSelectCard.comments.header" /></th>
                    </tr>
                    <logic:iterate scope="request" name="creditCards" id="creditCard" type="com.aoindustries.aoserv.client.CreditCard">
                        <skin:lightDarkTableRow>
                            <td style="white-space:nowrap">
                                <logic:equal scope="request" name="lastPaymentCreditCard" value="<%= creditCard.getProviderUniqueId() %>">
                                    <input type="radio" name="pkey" value="<%= creditCard.getPkey() %>" checked="checked" />
                                </logic:equal>
                                <logic:notEqual scope="request" name="lastPaymentCreditCard" value="<%= creditCard.getProviderUniqueId() %>">
                                    <input type="radio" name="pkey" value="<%= creditCard.getPkey() %>" />
                                </logic:notEqual>
                            </td>
                            <c:set var="cardNumber" value="${creditCard.cardInfo}"/>
                            <td style="white-space:nowrap"><%@include file="_credit-card-image.jsp" %></td>
                            <td style="white-space:nowrap"><c:out value="${fn:replace(cardNumber, 'X', '*')}"/></td>
                            <td style="white-space:nowrap">
                                <logic:notEmpty name="creditCard" property="description">
                                    <ao:write name="creditCard" property="description" />
                                </logic:notEmpty>
                                <logic:empty name="creditCard" property="description">
                                    &#160;
                                </logic:empty>
                            </td>
                        </skin:lightDarkTableRow>
                    </logic:iterate>
                    <skin:lightDarkTableRow>
                        <td style="white-space:nowrap">
                            <logic:equal scope="request" name="lastPaymentCreditCard" value="">
                                <input type="radio" name="pkey" value="" checked="checked" />
                            </logic:equal>
                            <logic:notEqual scope="request" name="lastPaymentCreditCard" value="">
                                <input type="radio" name="pkey" value="" />
                            </logic:notEqual>
                        </td>
                        <td style='white-space:nowrap' colspan="3"><fmt:message key="makePaymentSelectCard.newCard.link" /></td>
                    </skin:lightDarkTableRow>
                    <tr>
                        <td style='white-space:nowrap' colspan="4" align="center">
                            <ao:input type="submit" name="submitButton"><fmt:message key="makePaymentSelectCard.field.submit.label" /></ao:input>
                        </td>
                    </tr>
                </table>
            </fmt:bundle>
        </skin:lightArea>
        <%@include file="security-policy.jsp" %>
    </div>
</form>
