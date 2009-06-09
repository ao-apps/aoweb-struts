<%-- aoweb-struts: Do not edit --%>
<%--
  Copyright 2007-2009 by AO Industries, Inc.,
  7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" buffer="256kb" autoFlush="true" %>
<%@ include file="/WEB-INF/jsp/taglibs.jsp" %>

<aoweb:exists path="/WEB-INF/jsp/clientarea/accounting/add-siblings.override.jsp">
    <jsp:include page="/WEB-INF/jsp/clientarea/accounting/add-siblings.override.jsp"/>
</aoweb:exists>
<aoweb:notExists path="/WEB-INF/jsp/clientarea/accounting/add-siblings.override.jsp">
    <%--skin:addSibling useEncryption="true" path="/clientarea/accounting/AccountHistory.ao">
        <skin:title><bean:message bundle="/clientarea/accounting/ApplicationResources" key="accountHistory.title"/></skin:title>
        <skin:navImageAlt><bean:message bundle="/clientarea/accounting/ApplicationResources" key="accountHistory.navImageAlt"/></skin:navImageAlt>
        <skin:description><bean:message bundle="/clientarea/accounting/ApplicationResources" key="accountHistory.description"/></skin:description>
    </skin:addSibling--%>
    <skin:addSibling useEncryption="true" path="/clientarea/accounting/credit-card-manager.do">
        <skin:title><bean:message bundle="/clientarea/accounting/ApplicationResources" key="creditCardManager.title"/></skin:title>
        <skin:navImageAlt><bean:message bundle="/clientarea/accounting/ApplicationResources" key="creditCardManager.navImageAlt"/></skin:navImageAlt>
        <skin:description><bean:message bundle="/clientarea/accounting/ApplicationResources" key="creditCardManager.description"/></skin:description>
    </skin:addSibling>
    <skin:addSibling useEncryption="true" path="/clientarea/accounting/make-payment.do">
        <skin:title><bean:message bundle="/clientarea/accounting/ApplicationResources" key="makePayment.title"/></skin:title>
        <skin:navImageAlt><bean:message bundle="/clientarea/accounting/ApplicationResources" key="makePayment.navImageAlt"/></skin:navImageAlt>
        <skin:description><bean:message bundle="/clientarea/accounting/ApplicationResources" key="makePayment.description"/></skin:description>
    </skin:addSibling>
</aoweb:notExists>
