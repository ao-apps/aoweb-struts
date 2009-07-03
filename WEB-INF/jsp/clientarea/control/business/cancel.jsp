<%-- aoweb-struts: Do not edit --%>
<%--
  Copyright 2003-2009 by AO Industries, Inc.,
  7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" buffer="256kb" autoFlush="true" pageEncoding="UTF-8" %>
<%@ page import="com.aoindustries.util.EncodingUtils" %>
<%@ include file="/WEB-INF/jsp/taglibs.jsp" %>

<skin:setContentType />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html:html lang="true" xhtml="true">
    <skin:path>/clientarea/control/business/cancel.do</skin:path>
    <logic:equal name="siteSettings" property="brand.aowebStrutsNoindex" value="true"><skin:meta name="ROBOTS">NOINDEX</skin:meta></logic:equal>
    <skin:title><bean:message bundle="/clientarea/control/ApplicationResources" key="business.cancel.title" /></skin:title>
    <skin:navImageAlt><bean:message bundle="/clientarea/control/ApplicationResources" key="business.cancel.navImageAlt" /></skin:navImageAlt>
    <skin:keywords><bean:message bundle="/clientarea/control/ApplicationResources" key="business.cancel.keywords" /></skin:keywords>
    <skin:description><bean:message bundle="/clientarea/control/ApplicationResources" key="business.cancel.description" /></skin:description>
    <%@ include file="add-parents.jsp" %>
    <%@ include file="add-siblings.jsp" %>
    <skin:skin>
        <skin:content width="600">
            <skin:contentTitle><bean:message bundle="/clientarea/control/ApplicationResources" key="business.cancel.title" /></skin:contentTitle>
            <skin:contentHorizontalDivider />
            <skin:contentLine>
                <logic:present scope="request" name="permissionDenied">
                    <%@ include file="../../../permission-denied.jsp" %>
                </logic:present>
                <logic:notPresent scope="request" name="permissionDenied">
                    <aoweb:scriptGroup>
                        <table cellpadding='0' cellspacing='0'>
                            <tr>
                                <td><%@ include file="cancel-message.jsp" %></td>
                            </tr>
                            <tr>
                                <td align='center'>
                                    <skin:lightArea>
                                        <table cellspacing='0' cellpadding='2'>
                                            <tr>
                                                <th><bean:message bundle="/clientarea/control/ApplicationResources" key="business.cancel.header.businessName" /></th>
                                                <th><bean:message bundle="/clientarea/control/ApplicationResources" key="business.cancel.header.parent" /></th>
                                                <th><bean:message bundle="/clientarea/control/ApplicationResources" key="business.cancel.header.totalMonthlyCharges" /></th>
                                                <th><bean:message bundle="/clientarea/control/ApplicationResources" key="business.cancel.header.accountBalance" /></th>
                                                <th><bean:message bundle="/clientarea/control/ApplicationResources" key="business.cancel.header.created" /></th>
                                                <th><bean:message bundle="/clientarea/control/ApplicationResources" key="business.cancel.header.canceled" /></th>
                                                <th><bean:message bundle="/clientarea/control/ApplicationResources" key="business.cancel.header.actions" /></th>
                                            </tr>
                                            <logic:iterate scope="request" name="businesses" id="bu" type="com.aoindustries.aoserv.client.Business">
                                                <skin:lightDarkTableRow>
                                                    <td>
                                                        <logic:notEmpty name="bu" property="businessProfile">
                                                            <bean:define name="bu" property="businessProfile" id="bp" type="com.aoindustries.aoserv.client.BusinessProfile" />
                                                            <bean:message
                                                                bundle="/clientarea/control/ApplicationResources"
                                                                key="business.cancel.field.businessNameAndAccounting"
                                                                arg0="<%= EncodingUtils.encodeHtml(bp.getName()) %>"
                                                                arg1="<%= EncodingUtils.encodeHtml(bu.getAccounting()) %>"
                                                            />
                                                        </logic:notEmpty>
                                                        <logic:empty name="bu" property="businessProfile">
                                                            <bean:message
                                                                bundle="/clientarea/control/ApplicationResources"
                                                                key="business.cancel.field.businessAccounting"
                                                                arg0="<%= EncodingUtils.encodeHtml(bu.getAccounting()) %>"
                                                            />
                                                        </logic:empty>
                                                    </td>
                                                    <td>
                                                        <logic:empty name="bu" property="parentBusiness">&#160;</logic:empty>
                                                        <logic:notEmpty name="bu" property="parentBusiness">
                                                            <bean:define name="bu" property="parentBusiness" id="parent" type="com.aoindustries.aoserv.client.Business" />
                                                            <logic:notEmpty name="parent" property="businessProfile">
                                                                <bean:define name="parent" property="businessProfile" id="parentBP" type="com.aoindustries.aoserv.client.BusinessProfile" />
                                                                <bean:message
                                                                    bundle="/clientarea/control/ApplicationResources"
                                                                    key="business.cancel.field.businessNameAndAccounting"
                                                                    arg0="<%= EncodingUtils.encodeHtml(parentBP.getName()) %>"
                                                                    arg1="<%= EncodingUtils.encodeHtml(parent.getAccounting()) %>"
                                                                />
                                                            </logic:notEmpty>
                                                            <logic:empty name="parent" property="businessProfile">
                                                                <bean:message
                                                                    bundle="/clientarea/control/ApplicationResources"
                                                                    key="business.cancel.field.businessAccounting"
                                                                    arg0="<%= EncodingUtils.encodeHtml(parent.getAccounting()) %>"
                                                                />
                                                            </logic:empty>
                                                        </logic:notEmpty>
                                                    </td>
                                                    <td align='right'>
                                                        <logic:empty name="bu" property="totalMonthlyRateString">&#160;</logic:empty>
                                                        <logic:notEmpty name="bu" property="totalMonthlyRateString">
                                                            <bean:message
                                                                bundle="/clientarea/control/ApplicationResources"
                                                                key="business.cancel.field.totalMonthlyRate"
                                                                arg0="<%= bu.getTotalMonthlyRateString() %>"
                                                            />
                                                        </logic:notEmpty>
                                                    </td>
                                                    <td align='right'>
                                                        <% int balance=bu.getAccountBalance(); %>
                                                        <% if(balance<0) { %>
                                                            <bean:message
                                                                bundle="/clientarea/control/ApplicationResources"
                                                                key="business.cancel.field.balance.credit"
                                                                arg0="<%= com.aoindustries.sql.SQLUtility.getDecimal(-balance) %>"
                                                            />
                                                        <% } else if(balance>0) { %>
                                                            <bean:message
                                                                bundle="/clientarea/control/ApplicationResources"
                                                                key="business.cancel.field.balance.debt"
                                                                arg0="<%= com.aoindustries.sql.SQLUtility.getDecimal(balance) %>"
                                                            />
                                                        <% } else { %>
                                                            <bean:message bundle="/clientarea/control/ApplicationResources" key="business.cancel.field.balance.zero" />
                                                        <% } %>
                                                    </td>
                                                    <td><aoweb:date><bean:write name="bu" property="created" /></aoweb:date></td>
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
                                                                <bean:message bundle="/clientarea/control/ApplicationResources" key="business.cancel.field.link.cancel" />
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
        </skin:content>
    </skin:skin>
</html:html>
