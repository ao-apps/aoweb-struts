<%-- aoweb-struts: Do not edit --%>
<%--
  Copyright 2000-2009 by AO Industries, Inc.,
  7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" buffer="256kb" autoFlush="true" pageEncoding="UTF-8" %>
<%@include file="/_taglibs.jsp" %>

<skin:lightArea>
    <skin:popupGroup>
        <fmt:bundle basename="com.aoindustries.website.signup.ApplicationResources">
            <table cellspacing="0" cellpadding="2">
                <tr><td colspan="6"><b><fmt:message key="signupSelectServerForm.stepLabel" /></b><br /><hr /></td></tr>
                <tr><td colspan="6"><fmt:message key="signupSelectServerForm.stepHelp" /><br /><br /></td></tr>
                <tr>
                    <th style='white-space:nowrap'><fmt:message key="signupSelectPackageForm.select.header" /></th>
                    <th style='white-space:nowrap'><fmt:message key="signupSelectPackageForm.packageDefinition.header" /></th>
                    <th style='white-space:nowrap'><fmt:message key="signupSelectPackageForm.setup.header" /></th>
                    <th style='white-space:nowrap'><fmt:message key="signupSelectPackageForm.minimumMonthlyRate.header" /></th>
                    <th style='white-space:nowrap'><fmt:message key="signupSelectPackageForm.maximumMonthlyRate.header" /></th>
                    <th style='white-space:nowrap'>&#160;</th>
                </tr>
                <logic:iterate scope="request" name="servers" id="server" indexId="serverIndex">
                    <bean:define name="server" property="minimumConfiguration.packageDefinition" id="packageDefinition" type="java.lang.Integer" />
                    <skin:lightDarkTableRow>
                        <td style="white-space:nowrap"><html:radio property="packageDefinition" idName="server" value="minimumConfiguration.packageDefinition" /></td>
                        <td style="white-space:nowrap">
                            <b><ao:write name="server" property="minimumConfiguration.name" /></b>
                            <skin:popup>
                                <table cellspacing="0" cellpadding="2" style='font-size:80%;'>
                                    <tr>
                                        <td colspan="3" class='aoPopupLightRow' style='font-size:100%;'>
                                            <table style="width:100%" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <th class='aoPopupLightRow'><ao:write name="server" property="minimumConfiguration.name" /></th>
                                                    <td class='aoPopupLightRow' align="right"><skin:popupClose /></td>
                                                </tr>
                                            </table>
                                        </td>
                                    </tr>
                                    <tr>
                                        <th class='aoPopupDarkRow'><fmt:message key="signupSelectPackageForm.resource.header" /></th>
                                        <th class='aoPopupDarkRow'><fmt:message key="signupSelectPackageForm.minimum.header" /></th>
                                        <th class='aoPopupDarkRow'><fmt:message key="signupSelectPackageForm.maximum.header" /></th>
                                    </tr>
                                    <% int row2 = 0; %>
                                    <tr class='<%= ((row2++)&1)==0 ? "aoPopupLightRow" : "aoPopupDarkRow" %>'>
                                        <td style="white-space:nowrap"><b><fmt:message key="signupSelectPackageForm.cpu.header" /></b></td>
                                        <td style="white-space:nowrap"><ao:write name="server" property="minimumConfiguration.cpu" type="application/xhtml+xml" /></td>
                                        <td style="white-space:nowrap"><ao:write name="server" property="maximumConfiguration.cpu" type="application/xhtml+xml" /></td>
                                    </tr>
                                    <tr class='<%= ((row2++)&1)==0 ? "aoPopupLightRow" : "aoPopupDarkRow" %>'>
                                        <td style="white-space:nowrap"><b><fmt:message key="signupSelectPackageForm.ram.header" /></b></td>
                                        <td style="white-space:nowrap"><ao:write name="server" property="minimumConfiguration.ram" /></td>
                                        <td style="white-space:nowrap"><ao:write name="server" property="maximumConfiguration.ram" /></td>
                                    </tr>
                                    <logic:notEmpty name="server" property="maximumConfiguration.ide">
                                        <tr class='<%= ((row2++)&1)==0 ? "aoPopupLightRow" : "aoPopupDarkRow" %>'>
                                            <td style="white-space:nowrap"><b><fmt:message key="signupSelectPackageForm.ide.header" /></b></td>
                                            <td style="white-space:nowrap">
                                                <logic:empty name="server" property="minimumConfiguration.ide">&#160;</logic:empty>
                                                <logic:notEmpty name="server" property="minimumConfiguration.ide"><ao:write name="server" property="minimumConfiguration.ide" /></logic:notEmpty>
                                                </td>
                                            <td style="white-space:nowrap"><ao:write name="server" property="maximumConfiguration.ide" /></td>
                                        </tr>
                                    </logic:notEmpty>
                                    <logic:notEmpty name="server" property="maximumConfiguration.sata">
                                        <tr class='<%= ((row2++)&1)==0 ? "aoPopupLightRow" : "aoPopupDarkRow" %>'>
                                            <td style="white-space:nowrap"><b><fmt:message key="signupSelectPackageForm.sata.header" /></b></td>
                                            <td style="white-space:nowrap">
                                                <logic:empty name="server" property="minimumConfiguration.sata">&#160;</logic:empty>
                                                <logic:notEmpty name="server" property="minimumConfiguration.sata"><ao:write name="server" property="minimumConfiguration.sata" /></logic:notEmpty>
                                                </td>
                                            <td style="white-space:nowrap"><ao:write name="server" property="maximumConfiguration.sata" /></td>
                                        </tr>
                                    </logic:notEmpty>
                                    <logic:notEmpty name="server" property="maximumConfiguration.scsi">
                                        <tr class='<%= ((row2++)&1)==0 ? "aoPopupLightRow" : "aoPopupDarkRow" %>'>
                                            <td style="white-space:nowrap"><b><fmt:message key="signupSelectPackageForm.scsi.header" /></b></td>
                                            <td style="white-space:nowrap">
                                                <logic:empty name="server" property="minimumConfiguration.scsi">&#160;</logic:empty>
                                                <logic:notEmpty name="server" property="minimumConfiguration.scsi"><ao:write name="server" property="minimumConfiguration.scsi" /></logic:notEmpty>
                                                </td>
                                            <td style="white-space:nowrap"><ao:write name="server" property="maximumConfiguration.scsi" /></td>
                                        </tr>
                                    </logic:notEmpty>
                                </table>
                            </skin:popup>
                        </td>
                        <td style="white-space:nowrap; text-align:center">
                            <logic:empty name="server" property="minimumConfiguration.setup"><fmt:message key="signupSelectPackageForm.setup.none" /></logic:empty>
                            <logic:notEmpty name="server" property="minimumConfiguration.setup">$<ao:write name="server" property="minimumConfiguration.setup" /></logic:notEmpty>
                        </td>
                        <td style="white-space:nowrap; text-align:center">$<ao:write name="server" property="minimumConfiguration.monthly" /></td>
                        <td style="white-space:nowrap; text-align:center">$<ao:write name="server" property="maximumConfiguration.monthly" /></td>
                        <logic:equal name="serverIndex" value="0">
                            <bean:size scope="request" name="servers" id="serversSize" />
                            <td rowspan="<%= serversSize %>" style="white-space:nowrap">
                                <html:errors bundle="/signup/ApplicationResources" property="packageDefinition" />
                            </td>
                        </logic:equal>
                    </skin:lightDarkTableRow>
                </logic:iterate>
                <tr><td colspan="6" align="center"><br /><ao:input type="submit"><fmt:message key="signupSelectServerForm.submit.label" /></ao:input><br /><br /></td></tr>
            </table>
        </fmt:bundle>
    </skin:popupGroup>
</skin:lightArea>
