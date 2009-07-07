<%-- aoweb-struts: Do not edit --%>
<%--
  Copyright 2007-2009 by AO Industries, Inc.,
  7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" buffer="256kb" autoFlush="true" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/taglibs.jsp" %>

<aoweb:exists path="/WEB-INF/jsp/clientarea/accounting/add-siblings.override.jsp">
    <jsp:include page="/WEB-INF/jsp/clientarea/accounting/add-siblings.override.jsp" />
</aoweb:exists>
<aoweb:notExists path="/WEB-INF/jsp/clientarea/accounting/add-siblings.override.jsp">
    <fmt:bundle basename="com.aoindustries.website.clientarea.accounting.ApplicationResources">
        <%--skin:addSibling useEncryption="true" path="/clientarea/accounting/AccountHistory.ao">
            <skin:title><fmt:message key="accountHistory.title" /></skin:title>
            <skin:navImageAlt><fmt:message key="accountHistory.navImageAlt" /></skin:navImageAlt>
            <skin:description><fmt:message key="accountHistory.description" /></skin:description>
        </skin:addSibling--%>
        <skin:addSibling useEncryption="true" path="/clientarea/accounting/credit-card-manager.do">
            <skin:title><fmt:message key="creditCardManager.title" /></skin:title>
            <skin:navImageAlt><fmt:message key="creditCardManager.navImageAlt" /></skin:navImageAlt>
            <skin:description><fmt:message key="creditCardManager.description" /></skin:description>
        </skin:addSibling>
        <skin:addSibling useEncryption="true" path="/clientarea/accounting/make-payment.do">
            <skin:title><fmt:message key="makePayment.title" /></skin:title>
            <skin:navImageAlt><fmt:message key="makePayment.navImageAlt" /></skin:navImageAlt>
            <skin:description><fmt:message key="makePayment.description" /></skin:description>
        </skin:addSibling>
    </fmt:bundle>
</aoweb:notExists>
