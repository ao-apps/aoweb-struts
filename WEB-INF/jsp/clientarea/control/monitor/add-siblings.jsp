<%--
  Copyright 2007 by AO Industries, Inc.,
  816 Azalea Rd, Mobile, Alabama, 36693, U.S.A.
  All rights reserved.
--%>
<%@ taglib uri="/WEB-INF/aoweb-struts-skin.tld" prefix="skin" %>

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
