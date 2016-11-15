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

<fmt:bundle basename="com.aoindustries.website.clientarea.accounting.ApplicationResources">
	<ao:img src="/clientarea/accounting/amex.gif" style="border:1px solid" width="64" height="40">
		<ao:alt><fmt:message key="creditCardManager.image.amex.alt"/></ao:alt>
	</ao:img>
	&#160;&#160;<ao:img src="/clientarea/accounting/discv.gif" style="border:1px solid" width="63" height="40">
		<ao:alt><fmt:message key="creditCardManager.image.discv.alt"/></ao:alt>
	</ao:img>
	&#160;&#160;<ao:img src="/clientarea/accounting/mcard.gif" style="border:1px solid" width="64" height="40">
		<ao:alt><fmt:message key="creditCardManager.image.mcard.alt"/></ao:alt>
	</ao:img>
	&#160;&#160;<ao:img src="/clientarea/accounting/visa.gif" style="border:1px solid" width="64" height="40">
		<ao:alt><fmt:message key="creditCardManager.image.visa.alt"/></ao:alt>
	</ao:img>
</fmt:bundle>
