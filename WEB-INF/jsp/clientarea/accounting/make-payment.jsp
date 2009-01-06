<%--
  Copyright 2007-2009 by AO Industries, Inc.,
  7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" %>
<%@ page buffer="256kb" %>
<%@ page autoFlush="true" %>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/aoweb-struts-skin.tld" prefix="skin" %>
<skin:setContentType/>
<html:html lang="true">
    <skin:path>/clientarea/accounting/make-payment.do</skin:path>
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
                    <hr>
                    <table border="0" cellspacing="0" cellpadding="2">
                        <tr>
                            <th nowrap><bean:message bundle="/clientarea/accounting/ApplicationResources" key="makePayment.business.header"/></th>
                            <th nowrap><bean:message bundle="/clientarea/accounting/ApplicationResources" key="makePayment.monthlyRate.header"/></th>
                            <th nowrap><bean:message bundle="/clientarea/accounting/ApplicationResources" key="makePayment.balance.header"/></th>
                            <th nowrap><bean:message bundle="/clientarea/accounting/ApplicationResources" key="makePayment.makePayment.header"/></th>
                        </tr>
                        <logic:iterate scope="request" name="businesses" id="business" type="com.aoindustries.aoserv.client.Business" indexId="row">
                            <tr class="<%= (row&1)==0 ? "ao_light_row" : "ao_dark_row" %>"
                                <td nowrap><bean:write name="business" property="accounting"/></td>
                                <td nowrap align='right'><bean:write name="business" property="monthlyRateString"/></td>
                                <td nowrap align='right'>
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
                                <td nowrap>
                                    <html:link action="/make-payment-select-card" paramId="accounting" paramName="business" paramProperty="accounting">
                                        <bean:message bundle="/clientarea/accounting/ApplicationResources" key="makePayment.makePayment.link"/>
                                    </html:link>
                                </td>
                            </tr>
                        </logic:iterate>
                    </table>
                </skin:lightArea>
            </skin:contentLine>
        </skin:content>
    </skin:skin>
</html:html>
