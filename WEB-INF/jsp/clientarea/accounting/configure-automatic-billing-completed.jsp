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
    <skin:path>/clientarea/accounting/configure-automatic-billing-completed.do</skin:path>
    <logic:equal name="siteSettings" property="brand.aowebStrutsNoindex" value="true"><skin:meta name="ROBOTS">NOINDEX</skin:meta></logic:equal>
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
                            <hr />
                            <bean:message
                                bundle="/clientarea/accounting/ApplicationResources"
                                key="configureAutomaticBillingCompleted.setUseMonthly.text"
                                arg0="<%= business.getAccounting() %>"
                                arg1="<%= creditCard.getCardInfo().replace('X', 'x') %>"
                            />
                        </logic:present>
                        <logic:notPresent scope="request" name="creditCard">
                            <bean:message bundle="/clientarea/accounting/ApplicationResources" key="configureAutomaticBillingCompleted.clearUseMonthly.title"/>
                            <hr />
                            <bean:message
                                bundle="/clientarea/accounting/ApplicationResources"
                                key="configureAutomaticBillingCompleted.clearUseMonthly.text"
                                arg0="<%= business.getAccounting() %>"
                            />
                        </logic:notPresent>
                        <br />
                        <br />
                        <html:link action="/credit-card-manager"><bean:message bundle="/clientarea/accounting/ApplicationResources" key="configureAutomaticBillingCompleted.creditCardManager.link"/></html:link>
                    </skin:lightArea>
                </logic:notPresent>
            </skin:contentLine>
        </skin:content>
    </skin:skin>
</html:html>
