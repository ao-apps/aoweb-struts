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
    <skin:path>/clientarea/accounting/make-payment.do</skin:path>
    <logic:equal name="siteSettings" property="brand.aowebStrutsNoindex" value="true"><skin:meta name="ROBOTS">NOINDEX</skin:meta></logic:equal>
    <skin:title><bean:message bundle="/clientarea/accounting/ApplicationResources" key="makePayment.title"/></skin:title>
    <skin:navImageAlt><bean:message bundle="/clientarea/accounting/ApplicationResources" key="makePayment.navImageAlt"/></skin:navImageAlt>
    <skin:keywords><bean:message bundle="/clientarea/accounting/ApplicationResources" key="makePayment.keywords"/></skin:keywords>
    <skin:description><bean:message bundle="/clientarea/accounting/ApplicationResources" key="makePayment.description"/></skin:description>
    <%@ include file="add-parents.jsp" %>
    <%@ include file="add-siblings.jsp" %>
    <skin:skin>
        <skin:content width="600">
            <skin:contentTitle><bean:message bundle="/clientarea/accounting/ApplicationResources" key="makePayment.title"/></skin:contentTitle>
            <skin:contentHorizontalDivider/>
            <skin:contentLine>
                <skin:lightArea>
                    <bean:message bundle="/clientarea/accounting/ApplicationResources" key="makePayment.selectBusiness.list.title"/>
                    <hr />
                    <table cellspacing="0" cellpadding="2">
                        <tr>
                            <th style='white-space:nowrap'><bean:message bundle="/clientarea/accounting/ApplicationResources" key="makePayment.business.header"/></th>
                            <th style='white-space:nowrap'><bean:message bundle="/clientarea/accounting/ApplicationResources" key="makePayment.monthlyRate.header"/></th>
                            <th style='white-space:nowrap'><bean:message bundle="/clientarea/accounting/ApplicationResources" key="makePayment.balance.header"/></th>
                            <th style='white-space:nowrap'><bean:message bundle="/clientarea/accounting/ApplicationResources" key="makePayment.makePayment.header"/></th>
                        </tr>
                        <logic:iterate scope="request" name="businesses" id="business" type="com.aoindustries.aoserv.client.Business">
                            <skin:lightDarkTableRow>
                                <td style="white-space:nowrap"><bean:write name="business" property="accounting"/></td>
                                <td style='white-space:nowrap' align='right'><bean:write name="business" property="monthlyRateString"/></td>
                                <td style='white-space:nowrap' align='right'>
                                    <% int balance = business.getAccountBalance(); %>
                                    <% if(balance==0) { %>
                                        <bean:message bundle="/clientarea/accounting/ApplicationResources" key="makePayment.balance.value.zero"/>
                                    <% } else if(balance<0) { %>
                                        <bean:message
                                            bundle="/clientarea/accounting/ApplicationResources" key="makePayment.balance.value.credit"
                                            arg0="<%= com.aoindustries.sql.SQLUtility.getDecimal(-balance) %>"
                                        />
                                    <% } else { %>
                                        <bean:message
                                            bundle="/clientarea/accounting/ApplicationResources" key="makePayment.balance.value.debt"
                                            arg0="<%= com.aoindustries.sql.SQLUtility.getDecimal(balance) %>"
                                        />
                                    <% } %>
                                </td>
                                <td style="white-space:nowrap">
                                    <html:link action="/make-payment-select-card" paramId="accounting" paramName="business" paramProperty="accounting">
                                        <bean:message bundle="/clientarea/accounting/ApplicationResources" key="makePayment.makePayment.link"/>
                                    </html:link>
                                </td>
                            </skin:lightDarkTableRow>
                        </logic:iterate>
                    </table>
                </skin:lightArea>
            </skin:contentLine>
        </skin:content>
    </skin:skin>
</html:html>
