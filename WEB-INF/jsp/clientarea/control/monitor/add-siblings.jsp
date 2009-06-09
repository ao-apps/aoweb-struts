<%-- aoweb-struts: Do not edit --%>
<%--
  Copyright 2007-2009 by AO Industries, Inc.,
  7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" buffer="256kb" autoFlush="true" %>
<%@ include file="/WEB-INF/jsp/taglibs.jsp" %>

<skin:addSibling useEncryption="true" path="/clientarea/control/monitor/MRTG.ao">
    <skin:title><bean:message bundle="/clientarea/control/ApplicationResources" key="monitor.mrtg.title"/></skin:title>
    <skin:navImageAlt><bean:message bundle="/clientarea/control/ApplicationResources" key="monitor.mrtg.navImageAlt"/></skin:navImageAlt>
    <skin:description><bean:message bundle="/clientarea/control/ApplicationResources" key="monitor.mrtg.description"/></skin:description>
</skin:addSibling>
<skin:addSibling useEncryption="true" path="/clientarea/control/monitor/AllMRTG.ao">
    <skin:title><bean:message bundle="/clientarea/control/ApplicationResources" key="monitor.allMrtg.title"/></skin:title>
    <skin:navImageAlt><bean:message bundle="/clientarea/control/ApplicationResources" key="monitor.allMrtg.navImageAlt"/></skin:navImageAlt>
    <skin:description><bean:message bundle="/clientarea/control/ApplicationResources" key="monitor.allMrtg.description"/></skin:description>
</skin:addSibling>
<skin:addSibling useEncryption="true" path="/clientarea/control/monitor/BackupMonitor.ao">
    <skin:title><bean:message bundle="/clientarea/control/ApplicationResources" key="monitor.backupMonitor.title"/></skin:title>
    <skin:navImageAlt><bean:message bundle="/clientarea/control/ApplicationResources" key="monitor.backupMonitor.navImageAlt"/></skin:navImageAlt>
    <skin:description><bean:message bundle="/clientarea/control/ApplicationResources" key="monitor.backupMonitor.description"/></skin:description>
</skin:addSibling>
<skin:addSibling useEncryption="true" path="/clientarea/control/monitor/FileFailoverMonitor.ao">
    <skin:title><bean:message bundle="/clientarea/control/ApplicationResources" key="monitor.fileFailoverMonitor.title"/></skin:title>
    <skin:navImageAlt><bean:message bundle="/clientarea/control/ApplicationResources" key="monitor.fileFailoverMonitor.navImageAlt"/></skin:navImageAlt>
    <skin:description><bean:message bundle="/clientarea/control/ApplicationResources" key="monitor.fileFailoverMonitor.description"/></skin:description>
</skin:addSibling>
<skin:addSibling useEncryption="true" path="/clientarea/control/monitor/mysql-replication-monitor.do">
    <skin:title><bean:message bundle="/clientarea/control/ApplicationResources" key="monitor.mysqlReplicationMonitor.title"/></skin:title>
    <skin:navImageAlt><bean:message bundle="/clientarea/control/ApplicationResources" key="monitor.mysqlReplicationMonitor.navImageAlt"/></skin:navImageAlt>
    <skin:description><bean:message bundle="/clientarea/control/ApplicationResources" key="monitor.mysqlReplicationMonitor.description"/></skin:description>
</skin:addSibling>
