<%--
aoweb-struts-resources - Template webapp for legacy Struts-based site framework with AOServ Platform control panels.
Copyright (C) 2000-2009, 2015, 2016, 2020, 2021  AO Industries, Inc.
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

<ao:bundle basename="com.aoindustries.web.struts.clientarea.control.i18n.ApplicationResources">
	<skin:path>/clientarea/control/monitor/mysql-replication-monitor.do</skin:path>
	<logic:equal name="siteSettings" property="brand.aowebStrutsNoindex" value="true"><skin:meta name="robots">noindex</skin:meta></logic:equal>
	<skin:title><ao:message key="monitor.mysqlReplicationMonitor.title" /></skin:title>
	<skin:navImageAlt><ao:message key="monitor.mysqlReplicationMonitor.navImageAlt" /></skin:navImageAlt>
	<skin:keywords><ao:message key="monitor.mysqlReplicationMonitor.keywords" /></skin:keywords>
	<skin:description><ao:message key="monitor.mysqlReplicationMonitor.description" /></skin:description>
</ao:bundle>
