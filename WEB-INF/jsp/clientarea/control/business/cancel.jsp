<%-- aoweb-struts --%>
<%--
  Copyright 2003-2009 by AO Industries, Inc.,
  7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" buffer="256kb" autoFlush="true" %>
<%@ include file="/WEB-INF/jsp/taglibs.jsp" %>

<skin:setContentType/>
<html:html lang="true">
    <skin:path>/clientarea/control/business/cancel.do</skin:path>
    <logic:equal name="siteSettings" property="noindexAowebStruts" value="true"><skin:meta name="ROBOTS">NOINDEX</skin:meta></logic:equal>
    <skin:title><bean:message bundle="/clientarea/control/ApplicationResources" key="business.cancel.title"/></skin:title>
    <skin:navImageAlt><bean:message bundle="/clientarea/control/ApplicationResources" key="business.cancel.navImageAlt"/></skin:navImageAlt>
    <skin:keywords><bean:message bundle="/clientarea/control/ApplicationResources" key="business.cancel.keywords"/></skin:keywords>
    <skin:description><bean:message bundle="/clientarea/control/ApplicationResources" key="business.cancel.description"/></skin:description>
    <%@ include file="add-parents.jsp" %>
    <%@ include file="add-siblings.jsp" %>
    <skin:skin>
        <skin:content width="600">
            <skin:contentTitle><bean:message bundle="/clientarea/control/ApplicationResources" key="business.cancel.title"/></skin:contentTitle>
            <skin:contentHorizontalDivider/>
            <skin:contentLine>
                <logic:present scope="request" name="permissionDenied">
                    <%@ include file="../../../permission-denied.jsp" %>
                </logic:present>
                <logic:notPresent scope="request" name="permissionDenied">
                    <table border='0' cellpadding='0' cellspacing='0'>
                        <tr>
                            <TD><%@ include file="cancel-message.jsp" %></TD>
                        </tr>
                        <tr>
                            <TD align='center'>
                                <skin:lightArea>
                                    <TABLE border='0' cellspacing='0' cellpadding='2'>
                                        <TR>
                                            <TH><bean:message bundle="/clientarea/control/ApplicationResources" key="business.cancel.header.businessName"/></TH>
                                            <TH><bean:message bundle="/clientarea/control/ApplicationResources" key="business.cancel.header.parent"/></TH>
                                            <TH><bean:message bundle="/clientarea/control/ApplicationResources" key="business.cancel.header.totalMonthlyCharges"/></TH>
                                            <TH><bean:message bundle="/clientarea/control/ApplicationResources" key="business.cancel.header.accountBalance"/></TH>
                                            <TH><bean:message bundle="/clientarea/control/ApplicationResources" key="business.cancel.header.created"/></TH>
                                            <TH><bean:message bundle="/clientarea/control/ApplicationResources" key="business.cancel.header.canceled"/></TH>
                                            <TH><bean:message bundle="/clientarea/control/ApplicationResources" key="business.cancel.header.actions"/></TH>
                                        </TR>
                                        <logic:iterate scope="request" name="businesses" id="bu" type="com.aoindustries.aoserv.client.Business" indexId="c">
                                            <TR class='<%= ((c & 1) == 0)?"ao_light_row":"ao_dark_row" %>'>
                                                <TD>
                                                    <logic:notEmpty name="bu" property="businessProfile">
                                                        <bean:define name="bu" property="businessProfile" id="bp" type="com.aoindustries.aoserv.client.BusinessProfile"/>
                                                        <bean:message
                                                            bundle="/clientarea/control/ApplicationResources"
                                                            key="business.cancel.field.businessNameAndAccounting"
                                                            arg0="<%= bp.getName() %>"
                                                            arg1="<%= bu.getAccounting() %>"
                                                        />
                                                    </logic:notEmpty>
                                                    <logic:empty name="bu" property="businessProfile">
                                                        <bean:message
                                                            bundle="/clientarea/control/ApplicationResources"
                                                            key="business.cancel.field.businessAccounting"
                                                            arg0="<%= bu.getAccounting() %>"
                                                        />
                                                    </logic:empty>
                                                </TD>
                                                <TD>
                                                    <logic:empty name="bu" property="parentBusiness">
                                                        &nbsp;
                                                    </logic:empty>
                                                    <logic:notEmpty name="bu" property="parentBusiness">
                                                        <bean:define name="bu" property="parentBusiness" id="parent" type="com.aoindustries.aoserv.client.Business"/>
                                                        <logic:notEmpty name="parent" property="businessProfile">
                                                            <bean:define name="parent" property="businessProfile" id="parentBP" type="com.aoindustries.aoserv.client.BusinessProfile"/>
                                                            <bean:message
                                                                bundle="/clientarea/control/ApplicationResources"
                                                                key="business.cancel.field.businessNameAndAccounting"
                                                                arg0="<%= parentBP.getName() %>"
                                                                arg1="<%= parent.getAccounting() %>"
                                                            />
                                                        </logic:notEmpty>
                                                        <logic:empty name="parent" property="businessProfile">
                                                            <bean:message
                                                                bundle="/clientarea/control/ApplicationResources"
                                                                key="business.cancel.field.businessAccounting"
                                                                arg0="<%= parent.getAccounting() %>"
                                                            />
                                                        </logic:empty>
                                                    </logic:notEmpty>
                                                </TD>
                                                <TD align='right'>
                                                    <logic:empty name="bu" property="totalMonthlyRateString">&nbsp;</logic:empty>
                                                    <logic:notEmpty name="bu" property="totalMonthlyRateString">
                                                        <bean:message
                                                            bundle="/clientarea/control/ApplicationResources"
                                                            key="business.cancel.field.totalMonthlyRate"
                                                            arg0="<%= bu.getTotalMonthlyRateString() %>"
                                                        />
                                                    </logic:notEmpty>
                                                </TD>
                                                <TD align='right'>
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
                                                </TD>
                                                <TD><%= com.aoindustries.sql.SQLUtility.getDate(bu.getCreated()) %></TD>
                                                <TD>
                                                    <% long canceled=bu.getCanceled(); %>
                                                    <% if(canceled==-1) { %>
                                                        &nbsp;
                                                    <% } else { %>
                                                        <%= com.aoindustries.sql.SQLUtility.getDate(canceled) %>
                                                    <% } %>
                                                </TD>
                                                <TD>
                                                    <% if(!bu.canCancel()) { %>
                                                        &nbsp;
                                                    <% } else { %>
                                                        <html:link action="/business/cancel-feedback" paramId="business" paramName="bu" paramProperty="accounting">
                                                            <bean:message bundle="/clientarea/control/ApplicationResources" key="business.cancel.field.link.cancel" />
                                                        </html:link>
                                                    <% } %>
                                                </TD>
                                            </TR>
                                        </logic:iterate>
                                    </TABLE>
                                </skin:lightArea>
                            </TD>
                        </tr>
                    </table>
                </logic:notPresent>
            </skin:contentLine>
        </skin:content>
    </skin:skin>
</html:html>
