<%--
aoweb-struts-resources - Web resources for legacy Struts-based site framework with AOServ Platform control panels.
Copyright (C) 2007-2009, 2016  AO Industries, Inc.
	support@aoindustries.com
	7262 Bull Pen Cir
	Mobile, AL 36695

This file is part of aoweb-struts-resources.

aoweb-struts-resources is free software: you can redistribute it and/or modify
it under the terms of the GNU Lesser General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

aoweb-struts-resources is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public License
along with aoweb-struts-resources.  If not, see <http://www.gnu.org/licenses/>.
--%>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@include file="/_taglibs.inc.jsp" %>

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
