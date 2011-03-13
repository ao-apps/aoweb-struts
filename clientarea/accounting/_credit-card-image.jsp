<%-- aoweb-struts: Do not edit --%>
<%--
  Copyright 2007-2011 by AO Industries, Inc.,
  7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" buffer="256kb" autoFlush="true" pageEncoding="UTF-8" %>
<%@include file="/_taglibs.jsp" %>

<%-- The card number string should be provided in an attribute named "cardNumber" --%>
<fmt:bundle basename="com.aoindustries.website.clientarea.accounting.ApplicationResources">
    <c:choose>
        <c:when test="${fn:startsWith(cardNumber, '34') || fn:startsWith(cardNumber, '37')}">
            <ao:img src="amex.gif" style="border:1px solid; vertical-align:middle" width="64" height="40">
                <ao:alt><fmt:message key="creditCardManager.image.amex.alt"/></ao:alt>
            </ao:img>
        </c:when>
        <c:when test="${fn:startsWith(cardNumber, '60')}">
            <ao:img src="discv.gif" style="border:1px solid; vertical-align:middle" width="63" height="40">
                <ao:alt><fmt:message key="creditCardManager.image.discv.alt"/></ao:alt>
            </ao:img>
        </c:when>
        <c:when test="${fn:startsWith(cardNumber, '51') || fn:startsWith(cardNumber, '52') || fn:startsWith(cardNumber, '53') || fn:startsWith(cardNumber, '54') || fn:startsWith(cardNumber, '55')}">
            <ao:img src="mcard.gif" style="border:1px solid; vertical-align:middle" width="64" height="40">
                <ao:alt><fmt:message key="creditCardManager.image.mcard.alt"/></ao:alt>
            </ao:img>
        </c:when>
        <c:when test="${fn:startsWith(cardNumber, '4')}">
            <ao:img src="visa.gif" style="border:1px solid; vertical-align:middle" width="64" height="40">
                <ao:alt><fmt:message key="creditCardManager.image.visa.alt"/></ao:alt>
            </ao:img>
        </c:when>
        <c:otherwise>
            <fmt:message key="creditCardManager.creditCard.cardType.unknown" />
        </c:otherwise>
    </c:choose>
</fmt:bundle>
