<%-- aoweb-struts: Do not edit --%>
<%--
  Copyright 2000-2009 by AO Industries, Inc.,
  7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" buffer="256kb" autoFlush="true" %>
<%@ include file="/WEB-INF/jsp/taglibs.jsp" %>

<skin:lightArea>
    <skin:popupGroup>
        <table cellspacing="0" cellpadding="2">
            <tr><td COLSPAN="6"><b><bean:message bundle="/signup/ApplicationResources" key="signupSelectServerForm.stepLabel"/></b><br /><hr /></td></tr>
            <tr><td COLSPAN="6"><bean:message bundle="/signup/ApplicationResources" key="signupSelectServerForm.stepHelp"/><br /><br /></td></tr>
            <tr>
                <th style='white-space:nowrap'><bean:message bundle="/signup/ApplicationResources" key="signupSelectServerForm.select.header"/></th>
                <th style='white-space:nowrap'><bean:message bundle="/signup/ApplicationResources" key="signupSelectServerForm.packageDefinition.header"/></th>
                <th style='white-space:nowrap'><bean:message bundle="/signup/ApplicationResources" key="signupSelectServerForm.setup.header"/></th>
                <th style='white-space:nowrap'><bean:message bundle="/signup/ApplicationResources" key="signupSelectServerForm.minimumMonthlyRate.header"/></th>
                <th style='white-space:nowrap'><bean:message bundle="/signup/ApplicationResources" key="signupSelectServerForm.maximumMonthlyRate.header"/></th>
                <th style='white-space:nowrap'>&nbsp;</th>
            </tr>
            <logic:iterate scope="request" name="servers" id="server" indexId="serverIndex">
                <bean:define name="server" property="minimumConfiguration.packageDefinition" id="packageDefinition" type="java.lang.Integer"/>
                <skin:lightDarkTableRow>
                    <td style="white-space:nowrap"><html:radio property="packageDefinition" idName="server" value="minimumConfiguration.packageDefinition"/></td>
                    <td style="white-space:nowrap">
                        <b><bean:write name="server" property="minimumConfiguration.name"/></b>
                        <skin:popup>
                            <table cellspacing="0" cellpadding="2" style='font-size:80%;'>
                                <tr>
                                    <td colspan="3" class='aoPopupLightRow' style='font-size:100%;'>
                                        <table cellspacing="0" cellpadding="0" width="100%">
                                            <tr>
                                                <th class='aoPopupLightRow'><bean:write name="server" property="minimumConfiguration.name"/></th>
                                                <td class='aoPopupLightRow' align="right"><skin:popupClose/></td>
                                            </tr>
                                        </table>
                                    </td>
                                </tr>
                                <tr>
                                    <th class='aoPopupDarkRow'><bean:message bundle="/signup/ApplicationResources" key="signupSelectServerForm.resource.header"/></th>
                                    <th class='aoPopupDarkRow'><bean:message bundle="/signup/ApplicationResources" key="signupSelectServerForm.minimum.header"/></th>
                                    <th class='aoPopupDarkRow'><bean:message bundle="/signup/ApplicationResources" key="signupSelectServerForm.maximum.header"/></th>
                                </tr>
                                <% int row2 = 0; %>
                                <tr class='<%= ((row2++)&1)==0 ? "aoPopupLightRow" : "aoPopupDarkRow" %>'>
                                    <td style="white-space:nowrap"><b><bean:message bundle="/signup/ApplicationResources" key="signupSelectServerForm.cpu.header"/></b></td>
                                    <td style="white-space:nowrap"><bean:write name="server" property="minimumConfiguration.cpu" filter="false"/></td>
                                    <td style="white-space:nowrap"><bean:write name="server" property="maximumConfiguration.cpu" filter="false"/></td>
                                </tr>
                                <tr class='<%= ((row2++)&1)==0 ? "aoPopupLightRow" : "aoPopupDarkRow" %>'>
                                    <td style="white-space:nowrap"><b><bean:message bundle="/signup/ApplicationResources" key="signupSelectServerForm.ram.header"/></b></td>
                                    <td style="white-space:nowrap"><bean:write name="server" property="minimumConfiguration.ram"/></td>
                                    <td style="white-space:nowrap"><bean:write name="server" property="maximumConfiguration.ram"/></td>
                                </tr>
                                <logic:notEmpty name="server" property="maximumConfiguration.ide">
                                    <tr class='<%= ((row2++)&1)==0 ? "aoPopupLightRow" : "aoPopupDarkRow" %>'>
                                        <td style="white-space:nowrap"><b><bean:message bundle="/signup/ApplicationResources" key="signupSelectServerForm.ide.header"/></b></td>
                                        <td style="white-space:nowrap">
                                            <logic:empty name="server" property="minimumConfiguration.ide">&nbsp;</logic:empty>
                                            <logic:notEmpty name="server" property="minimumConfiguration.ide"><bean:write name="server" property="minimumConfiguration.ide"/></logic:notEmpty>
                                            </td>
                                        <td style="white-space:nowrap"><bean:write name="server" property="maximumConfiguration.ide"/></td>
                                    </tr>
                                </logic:notEmpty>
                                <logic:notEmpty name="server" property="maximumConfiguration.sata">
                                    <tr class='<%= ((row2++)&1)==0 ? "aoPopupLightRow" : "aoPopupDarkRow" %>'>
                                        <td style="white-space:nowrap"><b><bean:message bundle="/signup/ApplicationResources" key="signupSelectServerForm.sata.header"/></b></td>
                                        <td style="white-space:nowrap">
                                            <logic:empty name="server" property="minimumConfiguration.sata">&nbsp;</logic:empty>
                                            <logic:notEmpty name="server" property="minimumConfiguration.sata"><bean:write name="server" property="minimumConfiguration.sata"/></logic:notEmpty>
                                            </td>
                                        <td style="white-space:nowrap"><bean:write name="server" property="maximumConfiguration.sata"/></td>
                                    </tr>
                                </logic:notEmpty>
                                <logic:notEmpty name="server" property="maximumConfiguration.scsi">
                                    <tr class='<%= ((row2++)&1)==0 ? "aoPopupLightRow" : "aoPopupDarkRow" %>'>
                                        <td style="white-space:nowrap"><b><bean:message bundle="/signup/ApplicationResources" key="signupSelectServerForm.scsi.header"/></b></td>
                                        <td style="white-space:nowrap">
                                            <logic:empty name="server" property="minimumConfiguration.scsi">&nbsp;</logic:empty>
                                            <logic:notEmpty name="server" property="minimumConfiguration.scsi"><bean:write name="server" property="minimumConfiguration.scsi"/></logic:notEmpty>
                                            </td>
                                        <td style="white-space:nowrap"><bean:write name="server" property="maximumConfiguration.scsi"/></td>
                                    </tr>
                                </logic:notEmpty>
                            </table>
                        </skin:popup>
                    </td>
                    <td style="white-space:nowrap">
                        <logic:empty name="server" property="minimumConfiguration.setup"><bean:message bundle="/signup/ApplicationResources" key="signupSelectServerForm.setup.none"/></logic:empty>
                        <logic:notEmpty name="server" property="minimumConfiguration.setup">$<bean:write name="server" property="minimumConfiguration.setup"/></logic:notEmpty>
                        </td>
                    <td style="white-space:nowrap">$<bean:write name="server" property="minimumConfiguration.monthly"/></td>
                    <td style="white-space:nowrap">$<bean:write name="server" property="maximumConfiguration.monthly"/></td>
                    <logic:equal name="serverIndex" value="0">
                        <bean:size scope="request" name="servers" property="map" id="serversSize"/>
                        <td rowspan="<%= serversSize %>" nowrap>
                            <html:errors bundle="/signup/ApplicationResources" property="packageDefinition"/>
                            </td>
                    </logic:equal>
                </skin:lightDarkTableRow>
            </logic:iterate>
            <tr><td colspan="6" align="center"><br /><html:submit><bean:message bundle="/signup/ApplicationResources" key="signupSelectServerForm.submit.label"/></html:submit><br /><br /></td></tr>
        </table>
    </skin:popupGroup>
</skin:lightArea>
