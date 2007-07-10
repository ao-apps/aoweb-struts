<%--
  Copyright 2007 by AO Industries, Inc.,
  816 Azalea Rd, Mobile, Alabama, 36693, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" %>
<%@ page buffer="256kb" %>
<%@ page autoFlush="false" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld"       prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld"       prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld"      prefix="logic" %>
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
                        <logic:iterate scope="request" name="businesses" id="business" indexId="row">
                            <tr class="<%= (row&1)==0 ? "ao_light_row" : "ao_dark_row" %>"
                                <td nowrap><bean:write name="business" property="accounting"/></td>
                                <td nowrap align='right'><bean:write name="business" property="monthlyRateString"/></td>
                                <td nowrap align='right'><bean:write name="business" property="accountBalanceString"/></td>
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
