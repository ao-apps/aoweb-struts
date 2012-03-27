<%-- aoweb-struts: Do not edit --%>
<%--
  Copyright 2003-2009 by AO Industries, Inc.,
  7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" buffer="256kb" autoFlush="true" pageEncoding="UTF-8" %>
<%@ page import="com.aoindustries.util.EncodingUtils" %>
<%@ page import="java.math.BigDecimal" %>
<%@include file="/_taglibs.jsp" %>

<skin:setContentType />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html:html lang="true" xhtml="true">
    <%@include file="add-parents.jsp" %>
    <%@include file="cancel.meta.jsp" %>
    <skin:skin>
        <skin:content width="600">
            <fmt:bundle basename="com.aoindustries.website.clientarea.control.ApplicationResources">
                <skin:contentTitle><fmt:message key="business.cancel.title" /></skin:contentTitle>
                <skin:contentHorizontalDivider />
                <skin:contentLine>
                    <logic:present scope="request" name="permissionDenied">
                        <%@include file="../../../_permission-denied.jsp" %>
                    </logic:present>
                    <logic:notPresent scope="request" name="permissionDenied">
                        <aoweb:scriptGroup>
                            <table cellpadding='0' cellspacing='0'>
                                <tr>
                                    <td><%@include file="cancel-message.jsp" %></td>
                                </tr>
                                <tr>
                                    <td align='center'>
                                        <skin:lightArea>
                                            <table cellspacing='0' cellpadding='2'>
                                                <tr>
                                                    <th><fmt:message key="business.cancel.header.businessName" /></th>
                                                    <th><fmt:message key="business.cancel.header.parent" /></th>
                                                    <th><fmt:message key="business.cancel.header.totalMonthlyCharges" /></th>
                                                    <th><fmt:message key="business.cancel.header.accountBalance" /></th>
                                                    <th><fmt:message key="business.cancel.header.created" /></th>
                                                    <th><fmt:message key="business.cancel.header.canceled" /></th>
                                                    <th><fmt:message key="business.cancel.header.actions" /></th>
                                                </tr>
                                                <logic:iterate scope="request" name="businesses" id="bu" type="com.aoindustries.aoserv.client.Business">
                                                    <skin:lightDarkTableRow>
                                                        <td>
                                                            <logic:notEmpty name="bu" property="businessProfile">
                                                                <bean:define name="bu" property="businessProfile" id="bp" type="com.aoindustries.aoserv.client.BusinessProfile" />
                                                                <fmt:message key="business.cancel.field.businessNameAndAccounting">
                                                                    <fmt:param><c:out value="${bu.businessProfile.name}" /></fmt:param>
                                                                    <fmt:param><c:out value="${bu.accounting}" /></fmt:param>
                                                                </fmt:message>
                                                            </logic:notEmpty>
                                                            <logic:empty name="bu" property="businessProfile">
                                                                <fmt:message key="business.cancel.field.businessAccounting">
                                                                    <fmt:param><c:out value="${bu.accounting}" /></fmt:param>
                                                                </fmt:message>
                                                            </logic:empty>
                                                        </td>
                                                        <td>
                                                            <logic:empty name="bu" property="parentBusiness">&#160;</logic:empty>
                                                            <logic:notEmpty name="bu" property="parentBusiness">
                                                                <bean:define name="bu" property="parentBusiness" id="parent" type="com.aoindustries.aoserv.client.Business" />
                                                                <logic:notEmpty name="parent" property="businessProfile">
                                                                    <bean:define name="parent" property="businessProfile" id="parentBP" type="com.aoindustries.aoserv.client.BusinessProfile" />
                                                                    <fmt:message key="business.cancel.field.businessNameAndAccounting">
                                                                        <fmt:param><c:out value="${parentBP.name}" /></fmt:param>
                                                                        <fmt:param><c:out value="${parent.accounting}" /></fmt:param>
                                                                    </fmt:message>
                                                                </logic:notEmpty>
                                                                <logic:empty name="parent" property="businessProfile">
                                                                    <fmt:message key="business.cancel.field.businessAccounting">
                                                                        <fmt:param><c:out value="${parent.accounting}" /></fmt:param>
                                                                    </fmt:message>
                                                                </logic:empty>
                                                            </logic:notEmpty>
                                                        </td>
                                                        <td align='right'>
                                                            <logic:empty name="bu" property="totalMonthlyRate">&#160;</logic:empty>
                                                            <logic:notEmpty name="bu" property="totalMonthlyRate">
                                                                <fmt:message key="business.cancel.field.totalMonthlyRate">
                                                                    <fmt:param><c:out value="${bu.totalMonthlyRate}" /></fmt:param>
                                                                </fmt:message>
                                                            </logic:notEmpty>
                                                        </td>
                                                        <td align='right'>
                                                            <% BigDecimal balance=bu.getAccountBalance(); %>
                                                            <% if(balance.signum()<0) { %>
                                                                <fmt:message key="business.cancel.field.balance.credit">
                                                                    <fmt:param><c:out value="<%= balance.negate().toPlainString() %>" /></fmt:param>
                                                                </fmt:message>
                                                            <% } else if(balance.signum()>0) { %>
                                                                <fmt:message key="business.cancel.field.balance.debt">
                                                                    <fmt:param><c:out value="<%= balance.toPlainString() %>" /></fmt:param>
                                                                </fmt:message>
                                                            <% } else { %>
                                                                <fmt:message key="business.cancel.field.balance.zero" />
                                                            <% } %>
                                                        </td>
                                                        <td><aoweb:date><ao:write name="bu" property="created" /></aoweb:date></td>
                                                        <td>
                                                            <% long canceled=bu.getCanceled(); %>
                                                            <% if(canceled==-1) { %>
                                                                &#160;
                                                            <% } else { %>
                                                                <aoweb:date><%= canceled %></aoweb:date>
                                                            <% } %>
                                                        </td>
                                                        <td>
                                                            <% if(!bu.canCancel()) { %>
                                                                &#160;
                                                            <% } else { %>
                                                                <html:link action="/business/cancel-feedback" paramId="business" paramName="bu" paramProperty="accounting">
                                                                    <fmt:message key="business.cancel.field.link.cancel" />
                                                                </html:link>
                                                            <% } %>
                                                        </td>
                                                    </skin:lightDarkTableRow>
                                                </logic:iterate>
                                            </table>
                                        </skin:lightArea>
                                    </td>
                                </tr>
                            </table>
                        </aoweb:scriptGroup>
                    </logic:notPresent>
                </skin:contentLine>
            </fmt:bundle>
        </skin:content>
    </skin:skin>
</html:html>
