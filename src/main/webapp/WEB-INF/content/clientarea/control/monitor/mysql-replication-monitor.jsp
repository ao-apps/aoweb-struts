<%--
aoweb-struts - Template webapp for legacy Struts-based site framework with AOServ Platform control panels.
Copyright (C) 2000-2009, 2016, 2019, 2020, 2021  AO Industries, Inc.
	support@aoindustries.com
	7262 Bull Pen Cir
	Mobile, AL 36695

This file is part of aoweb-struts.

aoweb-struts is free software: you can redistribute it and/or modify
it under the terms of the GNU Lesser General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

aoweb-struts is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public License
along with aoweb-struts.  If not, see <https://www.gnu.org/licenses/>.
--%>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/taglibs.jspf" %>

<%@include file="add-parents.jspf" %>
<%@include file="mysql-replication-monitor.meta.jspf" %>
<skin:skin>
	<skin:content width="600">
		<ao:bundle basename="com.aoindustries.web.struts.clientarea.control.monitor.i18n.ApplicationResources">
			<skin:contentTitle><ao:message key="mysqlReplicationMonitor.title" /></skin:contentTitle>
			<skin:contentHorizontalDivider />
			<skin:contentLine>
				<logic:present scope="request" name="permissionDenied">
					<%@include file="../../../permission-denied.jspf" %>
				</logic:present>
				<logic:notPresent scope="request" name="permissionDenied">
					<skin:lightArea>
						<b><ao:message key="mysqlReplicationMonitor.main.label" /></b>
						<ao:hr />
						<table class="ao-no-border">
							<thead>
								<tr>
									<th style="white-space:pre"><ao:message key="mysqlReplicationMonitor.header.version" /></th>
									<th style="white-space:pre"><ao:message key="mysqlReplicationMonitor.header.master" /></th>
									<th style="white-space:pre"><ao:message key="mysqlReplicationMonitor.header.masterLogFile" /></th>
									<th style="white-space:pre"><ao:message key="mysqlReplicationMonitor.header.masterLogPos" /></th>
									<th style="white-space:pre"><ao:message key="mysqlReplicationMonitor.header.slave" /></th>
									<th style="white-space:pre"><ao:message key="mysqlReplicationMonitor.header.secondsBehindMaster" /></th>
									<th style="white-space:pre"><ao:message key="mysqlReplicationMonitor.header.slaveIOState" /></th>
									<th style="white-space:pre"><ao:message key="mysqlReplicationMonitor.header.slaveLogFile" /></th>
									<th style="white-space:pre"><ao:message key="mysqlReplicationMonitor.header.slaveLogPos" /></th>
									<th style="white-space:pre"><ao:message key="mysqlReplicationMonitor.header.slaveIORunning" /></th>
									<th style="white-space:pre"><ao:message key="mysqlReplicationMonitor.header.slaveSQLRunning" /></th>
									<th style="white-space:pre"><ao:message key="mysqlReplicationMonitor.header.lastErrno" /></th>
									<th style="white-space:pre"><ao:message key="mysqlReplicationMonitor.header.lastError" /></th>
								</tr>
							</thead>
							<tbody>
								<logic:iterate scope="request" name="mysqlServerRows" id="mysqlServerRow">
									<bean:size name="mysqlServerRow" property="replications" id="replicationsSize" />
									<logic:iterate name="mysqlServerRow" property="replications" id="replicationRow" indexId="row">
										<tr>
											<logic:equal name="row" value="0">
												<td style="white-space:nowrap" rowspan='<ao:write name="replicationsSize" />'>
													<logic:equal name="mysqlServerRow" property="error" value="true"><span class="error"></logic:equal>
													<ao:write name="mysqlServerRow" property="version" />
													<logic:equal name="mysqlServerRow" property="error" value="true"></span></logic:equal>
												</td>
												<td style="white-space:nowrap" rowspan='<ao:write name="replicationsSize" />'>
													<logic:equal name="mysqlServerRow" property="error" value="true"><span class="error"></logic:equal>
													<ao:write name="mysqlServerRow" property="master" />
													<logic:equal name="mysqlServerRow" property="error" value="true"></span></logic:equal>
												</td>
												<logic:notEmpty name="mysqlServerRow" property="lineError">
													<td style="white-space:nowrap" colspan="2" rowspan='<ao:write name="replicationsSize" />'>
														<logic:equal name="mysqlServerRow" property="error" value="true"><span class="error"></logic:equal>
														<ao:write name="mysqlServerRow" property="lineError" />
														<logic:equal name="mysqlServerRow" property="error" value="true"></span></logic:equal>
													</td>
												</logic:notEmpty>
												<logic:empty name="mysqlServerRow" property="lineError">
													<td style="white-space:nowrap" rowspan='<ao:write name="replicationsSize" />'>
														<logic:equal name="mysqlServerRow" property="error" value="true"><span class="error"></logic:equal>
														<ao:write name="mysqlServerRow" property="masterLogFile" />
														<logic:equal name="mysqlServerRow" property="error" value="true"></span></logic:equal>
													</td>
													<td style="white-space:nowrap" rowspan='<ao:write name="replicationsSize" />'>
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
												<td style="white-space:nowrap" colspan="8">
													<logic:equal name="replicationRow" property="error" value="true"><span class="error"></logic:equal>
													<ao:write name="replicationRow" property="lineError" />
													<logic:equal name="replicationRow" property="error" value="true"></span></logic:equal>
												</td>
											</logic:notEmpty>
											<logic:empty name="replicationRow" property="lineError">
												<td style="white-space:nowrap;text-align:right">
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
												<td style="white-space:nowrap;text-align:right">
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
												<td style="white-space:nowrap;text-align:right">
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
							</tbody>
						</table>
					</skin:lightArea>
				</logic:notPresent>
			</skin:contentLine>
		</ao:bundle>
	</skin:content>
</skin:skin>
