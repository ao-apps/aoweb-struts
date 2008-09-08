<%--
  Copyright 2007-2008 by AO Industries, Inc.,
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
    <skin:path>/clientarea/accounting/configure-automatic-billing-completed.do</skin:path>
    <skin:title><bean:message bundle="/clientarea/accounting/ApplicationResources" key="configureAutomaticBillingCompleted.title"/></skin:title>
    <skin:navImageAlt><bean:message bundle="/clientarea/accounting/ApplicationResources" key="configureAutomaticBillingCompleted.navImageAlt"/></skin:navImageAlt>
    <skin:keywords><bean:message bundle="/clientarea/accounting/ApplicationResources" key="configureAutomaticBillingCompleted.keywords"/></skin:keywords>
    <skin:description><bean:message bundle="/clientarea/accounting/ApplicationResources" key="configureAutomaticBillingCompleted.description"/></skin:description>
    <%@ include file="add-parents.jsp" %>
    <skin:addParent useEncryption="true" path="/clientarea/accounting/credit-card-manager.do">
        <skin:title><bean:message bundle="/clientarea/accounting/ApplicationResources" key="creditCardManager.title"/></skin:title>
        <skin:navImageAlt><bean:message bundle="/clientarea/accounting/ApplicationResources" key="creditCardManager.navImageAlt"/></skin:navImageAlt>
    </skin:addParent>
    <%@ include file="add-siblings.jsp" %>
    <skin:skin>
        <skin:content width="600">
            <skin:contentTitle><bean:message bundle="/clientarea/accounting/ApplicationResources" key="configureAutomaticBillingCompleted.title"/></skin:contentTitle>
            <skin:contentHorizontalDivider/>
            <skin:contentLine>
                <logic:present scope="request" name="permissionDenied">
                    <%@ include file="../../permission-denied.jsp" %>
                </logic:present>
                <logic:notPresent scope="request" name="permissionDenied">
                    <skin:lightArea>
                        <bean:define scope="request" name="business" id="business" type="com.aoindustries.aoserv.client.Business"/>
                        <logic:present scope="request" name="creditCard">
                            <bean:define scope="request" name="creditCard" id="creditCard" type="com.aoindustries.aoserv.client.CreditCard"/>
                            <bean:message bundle="/clientarea/accounting/ApplicationResources" key="configureAutomaticBillingCompleted.setUseMonthly.title"/>
                            <hr>
                            <bean:message
                                bundle="/clientarea/accounting/ApplicationResources"
                                key="configureAutomaticBillingCompleted.setUseMonthly.text"
                                arg0="<%= business.getAccounting() %>"
                                arg1="<%= creditCard.getCardInfo().replace('X', 'x') %>"
                            />
                        </logic:present>
                        <logic:notPresent scope="request" name="creditCard">
                            <bean:message bundle="/clientarea/accounting/ApplicationResources" key="configureAutomaticBillingCompleted.clearUseMonthly.title"/>
                            <hr>
                            <bean:message
                                bundle="/clientarea/accounting/ApplicationResources"
                                key="configureAutomaticBillingCompleted.clearUseMonthly.text"
                                arg0="<%= business.getAccounting() %>"
                            />
                        </logic:notPresent>
                        <br>
                        <br>
                        <html:link action="/credit-card-manager"><bean:message bundle="/clientarea/accounting/ApplicationResources" key="configureAutomaticBillingCompleted.creditCardManager.link"/></html:link>
                    </skin:lightArea>
                </logic:notPresent>
            </skin:contentLine>
        </skin:content>
    </skin:skin>
</html:html>
