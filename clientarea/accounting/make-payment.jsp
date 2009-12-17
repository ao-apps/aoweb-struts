<%-- aoweb-struts: Do not edit --%>
<%--
  Copyright 2007-2009 by AO Industries, Inc.,
  7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" buffer="256kb" autoFlush="true" pageEncoding="UTF-8" %>
<%@ page import="java.math.BigDecimal" %>
<%@include file="/_taglibs.jsp" %>

<skin:setContentType />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html:html lang="true" xhtml="true">
    <%@include file="add-parents.jsp" %>
    <%@include file="make-payment.meta.jsp" %>
    <skin:skin>
        <skin:content width="600">
            <fmt:bundle basename="com.aoindustries.website.clientarea.accounting.ApplicationResources">
                <skin:contentTitle><fmt:message key="makePayment.title" /></skin:contentTitle>
                <skin:contentHorizontalDivider />
                <skin:contentLine>
                    <skin:lightArea>
                        <fmt:message key="makePayment.selectBusiness.list.title" />
                        <hr />
                        <table cellspacing="0" cellpadding="2">
                            <tr>
                                <th style='white-space:nowrap'><fmt:message key="makePayment.business.header" /></th>
                                <th style='white-space:nowrap'><fmt:message key="makePayment.monthlyRate.header" /></th>
                                <th style='white-space:nowrap'><fmt:message key="makePayment.balance.header" /></th>
                                <th style='white-space:nowrap'><fmt:message key="makePayment.makePayment.header" /></th>
                            </tr>
                            <logic:iterate scope="request" name="businesses" id="business" type="com.aoindustries.aoserv.client.Business">
                                <skin:lightDarkTableRow>
                                    <td style="white-space:nowrap"><ao:write name="business" property="accounting" /></td>
                                    <td style='white-space:nowrap' align='right'><ao:write name="business" property="monthlyRateString" /></td>
                                    <td style='white-space:nowrap' align='right'>
                                        <% BigDecimal balance = business.getAccountBalance(); %>
                                        <% if(balance.compareTo(BigDecimal.ZERO)==0) { %>
                                            <fmt:message key="makePayment.balance.value.zero" />
                                        <% } else if(balance.compareTo(BigDecimal.ZERO)<0) { %>
                                            <fmt:message key="makePayment.balance.value.credit">
                                                <fmt:param><c:out value="<%= balance.negate().toPlainString() %>" /></fmt:param>
                                            </fmt:message>
                                        <% } else { %>
                                            <fmt:message key="makePayment.balance.value.debt">
                                                <fmt:param><c:out value="<%= balance.toPlainString() %>" /></fmt:param>
                                            </fmt:message>
                                        <% } %>
                                    </td>
                                    <td style="white-space:nowrap">
                                        <html:link action="/make-payment-select-card" paramId="accounting" paramName="business" paramProperty="accounting">
                                            <fmt:message key="makePayment.makePayment.link" />
                                        </html:link>
                                    </td>
                                </skin:lightDarkTableRow>
                            </logic:iterate>
                        </table>
                    </skin:lightArea>
                </skin:contentLine>
            </fmt:bundle>
        </skin:content>
    </skin:skin>
</html:html>
