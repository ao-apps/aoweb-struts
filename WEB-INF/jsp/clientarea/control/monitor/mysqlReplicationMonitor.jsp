<%--
  Copyright 2000-2007 by AO Industries, Inc.,
  816 Azalea Rd, Mobile, Alabama, 36693, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld"       prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld"       prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld"      prefix="logic" %>
<%@ taglib uri="/WEB-INF/aoweb-struts-skin.tld" prefix="skin" %>
<skin:setContentType/>
<html:html lang="true">
    <skin:path>/clientarea/control/monitor/mysqlReplicationMonitor.do</skin:path>
    <skin:title><bean:message bundle="/clientarea/control/ApplicationResources" key="monitor.mysqlReplicationMonitor.title"/></skin:title>
    <skin:navImageAlt><bean:message bundle="/clientarea/control/ApplicationResources" key="monitor.mysqlReplicationMonitor.navImageAlt"/></skin:navImageAlt>
    <skin:keywords><bean:message bundle="/clientarea/control/ApplicationResources" key="monitor.mysqlReplicationMonitor.keywords"/></skin:keywords>
    <skin:description><bean:message bundle="/clientarea/control/ApplicationResources" key="monitor.mysqlReplicationMonitor.description"/></skin:description>
    <%@ include file="addParents.jsp" %>
    <%@ include file="addSiblings.jsp" %>
    <skin:skin>
        <skin:content width="600">
            <skin:contentTitle><bean:message bundle="/clientarea/control/ApplicationResources" key="monitor.mysqlReplicationMonitor.title"/></skin:contentTitle>
            <skin:contentHorizontalDivider/>
            <skin:contentLine>
                <logic:present scope="request" name="permissionDenied">
                    <%@ include file="../../../permissionDenied.jsp" %>
                </logic:present>
                <logic:notPresent scope="request" name="permissionDenied">
                    <skin:lightArea>
                        <b><bean:message bundle="/clientarea/control/ApplicationResources" key="monitor.mysqlReplicationMonitor.main.label"/></b>
                        <hr>
                        <table border='1' cellspacing='0' cellpadding='2'>
                            <tr>
                                <th nowrap><bean:message bundle="/clientarea/control/ApplicationResources" key="monitor.mysqlReplicationMonitor.header.version"/></th>
                                <th nowrap><bean:message bundle="/clientarea/control/ApplicationResources" key="monitor.mysqlReplicationMonitor.header.master"/></th>
                                <th nowrap><bean:message bundle="/clientarea/control/ApplicationResources" key="monitor.mysqlReplicationMonitor.header.slave"/></th>
                                <th nowrap><bean:message bundle="/clientarea/control/ApplicationResources" key="monitor.mysqlReplicationMonitor.header.secondsBehindMaster"/></th>
                                <th nowrap><bean:message bundle="/clientarea/control/ApplicationResources" key="monitor.mysqlReplicationMonitor.header.slaveIOState"/></th>
                                <th nowrap><bean:message bundle="/clientarea/control/ApplicationResources" key="monitor.mysqlReplicationMonitor.header.masterLogFile"/></th>
                                <th nowrap><bean:message bundle="/clientarea/control/ApplicationResources" key="monitor.mysqlReplicationMonitor.header.readMasterLogPos"/></th>
                                <th nowrap><bean:message bundle="/clientarea/control/ApplicationResources" key="monitor.mysqlReplicationMonitor.header.slaveIORunning"/></th>
                                <th nowrap><bean:message bundle="/clientarea/control/ApplicationResources" key="monitor.mysqlReplicationMonitor.header.slaveSQLRunning"/></th>
                                <th nowrap><bean:message bundle="/clientarea/control/ApplicationResources" key="monitor.mysqlReplicationMonitor.header.lastErrno"/></th>
                                <th nowrap><bean:message bundle="/clientarea/control/ApplicationResources" key="monitor.mysqlReplicationMonitor.header.lastError"/></th>
                            </tr>
                            <logic:iterate scope="request" name="mysqlServerRows" id="mysqlServerRow">
                                <bean:size name="mysqlServerRow" property="replications" id="replicationsSize"/>
                                <logic:iterate name="mysqlServerRow" property="replications" id="replicationRow" indexId="row">
                                    <tr>
                                        <logic:equal name="row" value="0">
                                            <td nowrap rowspan='<bean:write name="replicationsSize"/>'>
                                                <logic:equal name="mysqlServerRow" property="error" value="true"><span class="error"></logic:equal>
                                                <bean:write name="mysqlServerRow" property="version"/>
                                                <logic:equal name="mysqlServerRow" property="error" value="true"></span></logic:equal>
                                            </td>
                                            <td nowrap rowspan='<bean:write name="replicationsSize"/>'>
                                                <logic:equal name="mysqlServerRow" property="error" value="true"><span class="error"></logic:equal>
                                                <bean:write name="mysqlServerRow" property="master"/>
                                                <logic:equal name="mysqlServerRow" property="error" value="true"></span></logic:equal>
                                            </td>
                                        </logic:equal>
                                        <td nowrap>
                                            <logic:equal name="replicationRow" property="error" value="true"><span class="error"></logic:equal>
                                            <bean:write name="replicationRow" property="slave"/>
                                            <logic:equal name="replicationRow" property="error" value="true"></span></logic:equal>
                                        </td>
                                        <logic:notEmpty name="replicationRow" property="lineError">
                                            <td nowrap colspan="8">
                                                <logic:equal name="replicationRow" property="error" value="true"><span class="error"></logic:equal>
                                                <bean:write name="replicationRow" property="lineError"/>
                                                <logic:equal name="replicationRow" property="error" value="true"></span></logic:equal>
                                            </td>
                                        </logic:notEmpty>
                                        <logic:empty name="replicationRow" property="lineError">
                                            <td nowrap align='right'>
                                                <logic:equal name="replicationRow" property="error" value="true"><span class="error"></logic:equal>
                                                <bean:write name="replicationRow" property="secondsBehindMaster"/>
                                                <logic:equal name="replicationRow" property="error" value="true"></span></logic:equal>
                                            </td>
                                            <td nowrap>
                                                <logic:equal name="replicationRow" property="error" value="true"><span class="error"></logic:equal>
                                                <bean:write name="replicationRow" property="slaveIOState"/>
                                                <logic:equal name="replicationRow" property="error" value="true"></span></logic:equal>
                                            </td>
                                            <td nowrap>
                                                <logic:equal name="replicationRow" property="error" value="true"><span class="error"></logic:equal>
                                                <bean:write name="replicationRow" property="masterLogFile"/>
                                                <logic:equal name="replicationRow" property="error" value="true"></span></logic:equal>
                                            </td>
                                            <td nowrap align='right'>
                                                <logic:equal name="replicationRow" property="error" value="true"><span class="error"></logic:equal>
                                                <bean:write name="replicationRow" property="readMasterLogPos"/>
                                                <logic:equal name="replicationRow" property="error" value="true"></span></logic:equal>
                                            </td>
                                            <td nowrap>
                                                <logic:equal name="replicationRow" property="error" value="true"><span class="error"></logic:equal>
                                                <bean:write name="replicationRow" property="slaveIORunning"/>
                                                <logic:equal name="replicationRow" property="error" value="true"></span></logic:equal>
                                            </td>
                                            <td nowrap>
                                                <logic:equal name="replicationRow" property="error" value="true"><span class="error"></logic:equal>
                                                <bean:write name="replicationRow" property="slaveSQLRunning"/>
                                                <logic:equal name="replicationRow" property="error" value="true"></span></logic:equal>
                                            </td>
                                            <td nowrap align='right'>
                                                <logic:equal name="replicationRow" property="error" value="true"><span class="error"></logic:equal>
                                                <bean:write name="replicationRow" property="lastErrno"/>
                                                <logic:equal name="replicationRow" property="error" value="true"></span></logic:equal>
                                            </td>
                                            <td nowrap>
                                                <logic:equal name="replicationRow" property="error" value="true"><span class="error"></logic:equal>
                                                <logic:empty name="replicationRow" property="lastError">&nbsp;</logic:empty>
                                                <logic:notEmpty name="replicationRow" property="lastError"><bean:write name="replicationRow" property="lastError"/></logic:notEmpty>
                                                <logic:equal name="replicationRow" property="error" value="true"></span></logic:equal>
                                            </td>
                                        </logic:empty>
                                    </tr>
                                </logic:iterate>
                            </logic:iterate>
                        </table>
                    </skin:lightArea>
                </logic:notPresent>
            </skin:contentLine>
        </skin:content>
    </skin:skin>
</html:html>
