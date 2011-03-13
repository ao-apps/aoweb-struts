<%-- aoweb-struts: Do not edit --%>
<%--
  Copyright 2007-2011 by AO Industries, Inc.,
  7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" buffer="256kb" autoFlush="true" pageEncoding="UTF-8" %>
<%@include file="/_taglibs.jsp" %>
<%--
  This is intended to be used by other JSP pages, not to be used directly.
  It only provides the content of the page.
--%>
<skin:lightArea>
    <fmt:bundle basename="com.aoindustries.website.ApplicationResources">
        <b><fmt:message key="permissionDenied.permissionDenied" /></b>
        <hr />
        <logic:present scope="request" name="permissionDenied">
            <logic:empty scope="request" name="permissionDenied">
                <fmt:message key="permissionDenied.noPermissionInformation" />
            </logic:empty>
            <logic:notEmpty scope="request" name="permissionDenied">
                <bean:size scope="request" name="permissionDenied" id="permissionDeniedSize" />
                <logic:equal name="permissionDeniedSize" value="1">
                    <fmt:message key="permissionDenied.theFollowingPermissionRequired" />
                    <logic:iterate scope="request" name="permissionDenied" id="andPermission" type="com.aoindustries.aoserv.client.AOServPermission">
                        <p>
                            <table cellspacing='0' cellpadding='2'>
                                <tr>
                                    <td style="white-space:nowrap"><b><fmt:message key="permissionDenied.permission.display" /></b></td>
                                    <td style="white-space:nowrap"><ao:write name="andPermission" /></td>
                                </tr>
                                <tr>
                                    <td style="white-space:nowrap"><b><fmt:message key="permissionDenied.permission.description" /></b></td>
                                    <td style="white-space:nowrap"><ao:write name="andPermission" method="getDescription" /></td>
                                </tr>
                            </table>
                        </p>
                    </logic:iterate>
                </logic:equal>
                <logic:notEqual name="permissionDeniedSize" value="1">
                    <fmt:message key="permissionDenied.allOfTheFollowingPermissionsRequired" />
                    <p>
                        <table cellspacing='0' cellpadding='2'>
                            <tr>
                                <th style='white-space:nowrap'><fmt:message key="permissionDenied.andPermissions.header.display" /></th>
                                <th style='white-space:nowrap'><fmt:message key="permissionDenied.andPermissions.header.description" /></th>
                                <th style='white-space:nowrap'><fmt:message key="permissionDenied.andPermissions.header.hasPermission" /></th>
                            </tr>
                            <bean:define scope="request" name="aoConn" property="thisBusinessAdministrator" id="thisBusinessAdministrator" type="com.aoindustries.aoserv.client.BusinessAdministrator" />
                            <logic:iterate scope="request" name="permissionDenied" id="andPermission" type="com.aoindustries.aoserv.client.AOServPermission">
                                <tr>
                                    <td style="white-space:nowrap"><ao:write name="andPermission" /></td>
                                    <td style="white-space:nowrap"><ao:write name="andPermission" method="getDescription" /></td>
                                    <td style="white-space:nowrap">
                                        <% if(thisBusinessAdministrator.hasPermission(andPermission)) { %>
                                            <fmt:message key="permissionDenied.andPermissions.header.hasPermission.yes" />
                                        <% } else { %>
                                            <fmt:message key="permissionDenied.andPermissions.header.hasPermission.no" />
                                        <% } %>
                                    </td>
                                </tr>
                            </logic:iterate>
                        </table>
                    </p>
                </logic:notEqual>
            </logic:notEmpty>
        </logic:present>
        <logic:notPresent scope="request" name="permissionDenied">
            <fmt:message key="permissionDenied.noPermissionInformation" />
        </logic:notPresent>
    </fmt:bundle>
</skin:lightArea>
