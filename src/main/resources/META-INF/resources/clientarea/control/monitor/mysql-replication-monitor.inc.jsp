<%-- aoweb-struts: Do not edit --%>
<%--
Copyright 2000-2009, 2016 by AO Industries, Inc.,
7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
All rights reserved.
--%>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@include file="/_taglibs.inc.jsp" %>

<skin:setContentType />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html:html lang="true" xhtml="true">
	<%@include file="add-parents.inc.jsp" %>
	<%@include file="mysql-replication-monitor.meta.inc.jsp" %>
	<skin:skin>
		<skin:content width="600">
			<fmt:bundle basename="com.aoindustries.website.clientarea.control.ApplicationResources">
				<skin:contentTitle><fmt:message key="monitor.mysqlReplicationMonitor.title" /></skin:contentTitle>
				<skin:contentHorizontalDivider />
				<skin:contentLine>
					<logic:present scope="request" name="permissionDenied">
						<%@include file="../../../_permission-denied.inc.jsp" %>
					</logic:present>
					<logic:notPresent scope="request" name="permissionDenied">
						<skin:lightArea>
							<b><fmt:message key="monitor.mysqlReplicationMonitor.main.label" /></b>
							<hr />
							<table style='border:1px' cellspacing='0' cellpadding='2'>
								<tr>
									<th style='white-space:nowrap'><fmt:message key="monitor.mysqlReplicationMonitor.header.version" /></th>
									<th style='white-space:nowrap'><fmt:message key="monitor.mysqlReplicationMonitor.header.master" /></th>
									<th style='white-space:nowrap'><fmt:message key="monitor.mysqlReplicationMonitor.header.masterLogFile" /></th>
									<th style='white-space:nowrap'><fmt:message key="monitor.mysqlReplicationMonitor.header.masterLogPos" /></th>
									<th style='white-space:nowrap'><fmt:message key="monitor.mysqlReplicationMonitor.header.slave" /></th>
									<th style='white-space:nowrap'><fmt:message key="monitor.mysqlReplicationMonitor.header.secondsBehindMaster" /></th>
									<th style='white-space:nowrap'><fmt:message key="monitor.mysqlReplicationMonitor.header.slaveIOState" /></th>
									<th style='white-space:nowrap'><fmt:message key="monitor.mysqlReplicationMonitor.header.slaveLogFile" /></th>
									<th style='white-space:nowrap'><fmt:message key="monitor.mysqlReplicationMonitor.header.slaveLogPos" /></th>
									<th style='white-space:nowrap'><fmt:message key="monitor.mysqlReplicationMonitor.header.slaveIORunning" /></th>
									<th style='white-space:nowrap'><fmt:message key="monitor.mysqlReplicationMonitor.header.slaveSQLRunning" /></th>
									<th style='white-space:nowrap'><fmt:message key="monitor.mysqlReplicationMonitor.header.lastErrno" /></th>
									<th style='white-space:nowrap'><fmt:message key="monitor.mysqlReplicationMonitor.header.lastError" /></th>
								</tr>
								<logic:iterate scope="request" name="mysqlServerRows" id="mysqlServerRow">
									<bean:size name="mysqlServerRow" property="replications" id="replicationsSize" />
									<logic:iterate name="mysqlServerRow" property="replications" id="replicationRow" indexId="row">
										<tr>
											<logic:equal name="row" value="0">
												<td style='white-space:nowrap' rowspan='<ao:write name="replicationsSize" />'>
													<logic:equal name="mysqlServerRow" property="error" value="true"><span class="error"></logic:equal>
													<ao:write name="mysqlServerRow" property="version" />
													<logic:equal name="mysqlServerRow" property="error" value="true"></span></logic:equal>
												</td>
												<td style='white-space:nowrap' rowspan='<ao:write name="replicationsSize" />'>
													<logic:equal name="mysqlServerRow" property="error" value="true"><span class="error"></logic:equal>
													<ao:write name="mysqlServerRow" property="master" />
													<logic:equal name="mysqlServerRow" property="error" value="true"></span></logic:equal>
												</td>
												<logic:notEmpty name="mysqlServerRow" property="lineError">
													<td style='white-space:nowrap' colspan="2" rowspan='<ao:write name="replicationsSize" />'>
														<logic:equal name="mysqlServerRow" property="error" value="true"><span class="error"></logic:equal>
														<ao:write name="mysqlServerRow" property="lineError" />
														<logic:equal name="mysqlServerRow" property="error" value="true"></span></logic:equal>
													</td>
												</logic:notEmpty>
												<logic:empty name="mysqlServerRow" property="lineError">
													<td style='white-space:nowrap' rowspan='<ao:write name="replicationsSize" />'>
														<logic:equal name="mysqlServerRow" property="error" value="true"><span class="error"></logic:equal>
														<ao:write name="mysqlServerRow" property="masterLogFile" />
														<logic:equal name="mysqlServerRow" property="error" value="true"></span></logic:equal>
													</td>
													<td style='white-space:nowrap' rowspan='<ao:write name="replicationsSize" />'>
														<logic:equal name="mysqlServerRow" property="error" value="true"><span class="error"></logic:equal>
														<ao:write name="mysqlServerRow" property="masterLogPos" />
														<logic:equal name="mysqlServerRow" property="error" value="true"></span></logic:equal>
													</td>
												</logic:empty>
											</logic:equal>
											<td style="white-space:nowrap">
												<logic:equal name="replicationRow" property="error" value="true"><span class="error"></logic:equal>
												<ao:write name="replicationRow" property="slave" />
												<logic:equal name="replicationRow" property="error" value="true"></span></logic:equal>
											</td>
											<logic:notEmpty name="replicationRow" property="lineError">
												<td style='white-space:nowrap' colspan="8">
													<logic:equal name="replicationRow" property="error" value="true"><span class="error"></logic:equal>
													<ao:write name="replicationRow" property="lineError" />
													<logic:equal name="replicationRow" property="error" value="true"></span></logic:equal>
												</td>
											</logic:notEmpty>
											<logic:empty name="replicationRow" property="lineError">
												<td style='white-space:nowrap' align='right'>
													<logic:equal name="replicationRow" property="error" value="true"><span class="error"></logic:equal>
													<ao:write name="replicationRow" property="secondsBehindMaster" />
													<logic:equal name="replicationRow" property="error" value="true"></span></logic:equal>
												</td>
												<td style="white-space:nowrap">
													<logic:equal name="replicationRow" property="error" value="true"><span class="error"></logic:equal>
													<ao:write name="replicationRow" property="slaveIOState" />
													<logic:equal name="replicationRow" property="error" value="true"></span></logic:equal>
												</td>
												<td style="white-space:nowrap">
													<logic:equal name="replicationRow" property="error" value="true"><span class="error"></logic:equal>
													<ao:write name="replicationRow" property="slaveLogFile" />
													<logic:equal name="replicationRow" property="error" value="true"></span></logic:equal>
												</td>
												<td style='white-space:nowrap' align='right'>
													<logic:equal name="replicationRow" property="error" value="true"><span class="error"></logic:equal>
													<ao:write name="replicationRow" property="slaveLogPos" />
													<logic:equal name="replicationRow" property="error" value="true"></span></logic:equal>
												</td>
												<td style="white-space:nowrap">
													<logic:equal name="replicationRow" property="error" value="true"><span class="error"></logic:equal>
													<ao:write name="replicationRow" property="slaveIORunning" />
													<logic:equal name="replicationRow" property="error" value="true"></span></logic:equal>
												</td>
												<td style="white-space:nowrap">
													<logic:equal name="replicationRow" property="error" value="true"><span class="error"></logic:equal>
													<ao:write name="replicationRow" property="slaveSQLRunning" />
													<logic:equal name="replicationRow" property="error" value="true"></span></logic:equal>
												</td>
												<td style='white-space:nowrap' align='right'>
													<logic:equal name="replicationRow" property="error" value="true"><span class="error"></logic:equal>
													<ao:write name="replicationRow" property="lastErrno" />
													<logic:equal name="replicationRow" property="error" value="true"></span></logic:equal>
												</td>
												<td style="white-space:nowrap">
													<logic:equal name="replicationRow" property="error" value="true"><span class="error"></logic:equal>
													<logic:empty name="replicationRow" property="lastError">&#160;</logic:empty>
													<logic:notEmpty name="replicationRow" property="lastError"><ao:write name="replicationRow" property="lastError" /></logic:notEmpty>
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
			</fmt:bundle>
		</skin:content>
	</skin:skin>
</html:html>
