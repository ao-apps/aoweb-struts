<%--
  Copyright 2007 by AO Industries, Inc.,
  816 Azalea Rd, Mobile, Alabama, 36693, U.S.A.
  All rights reserved.
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld"       prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld"       prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld"      prefix="logic" %>
<%@ taglib uri="/WEB-INF/aoweb-struts-skin.tld" prefix="skin" %>
<%--
  This is intended to be used by other JSP pages, not to be used directly.
  It only provides the content of the page.
--%>
<bean:define scope="request" name="locale" id="locale" type="java.util.Locale"/>
<skin:lightArea>
    <b><bean:message bundle="/ApplicationResources" key="permissionDenied.permissionDenied"/></b>
    <hr>
    <logic:present scope="request" name="permissionDenied">
        <logic:empty scope="request" name="permissionDenied">
            <bean:message bundle="/ApplicationResources" key="permissionDenied.noPermissionInformation"/>
        </logic:empty>
        <logic:notEmpty scope="request" name="permissionDenied">
            <bean:size scope="request" name="permissionDenied" id="permissionDeniedSize"/>
            <logic:equal name="permissionDeniedSize" value="1">
                <bean:message bundle="/ApplicationResources" key="permissionDenied.theFollowingPermissionRequired"/>
                <%-- We are only grabbing the first item from the list.  I can't find a more efficient way so I'm just doing an interate
                     which will result in the first item being pulled out as desired.  I was trying something like this:
                    <bean:define name="permissionDenied" property="[0].displayKey" id="permissionDisplayKey" type="java.lang.String"/>
                    <bean:define name="permissionDenied" property="[0].descriptionKey" id="permissionDescriptionKey" type="java.lang.String"/>
                --%>
                <logic:iterate scope="request" name="permissionDenied" id="andPermission" type="com.aoindustries.aoserv.client.AOServPermission">
                    <bean:define id="permissionDisplay" type="java.lang.String"><%= andPermission.getDisplay(locale) %></bean:define>
                    <bean:define id="permissionDescription" type="java.lang.String"><%= andPermission.getDescription(locale) %></bean:define>
                    <p>
                        <table border='0' cellspacing='0' cellpadding='2'>
                            <tr>
                                <td><b><bean:message bundle="/ApplicationResources" key="permissionDenied.permission.display"/></b></td>
                                <td><bean:write name="permissionDisplay"/></td>
                            </tr>
                            <tr>
                                <td><b><bean:message bundle="/ApplicationResources" key="permissionDenied.permission.description"/></b></td>
                                <td><bean:write name="permissionDescription"/></td>
                            </tr>
                        </table>
                    </p>
                </logic:iterate>
            </logic:equal>
            <logic:notEqual name="permissionDeniedSize" value="1">
                <bean:message bundle="/ApplicationResources" key="permissionDenied.allOfTheFollowingPermissionsRequired"/>
                <p>
                    <table border='0' cellspacing='0' cellpadding='2'>
                        <tr>
                            <th><bean:message bundle="/ApplicationResources" key="permissionDenied.andPermissions.header.display"/></th>
                            <th><bean:message bundle="/ApplicationResources" key="permissionDenied.andPermissions.header.description"/></th>
                            <th><bean:message bundle="/ApplicationResources" key="permissionDenied.andPermissions.header.hasPermission"/></th>
                        </tr>
                        <bean:define scope="request" name="aoConn" property="thisBusinessAdministrator" id="thisBusinessAdministrator" type="com.aoindustries.aoserv.client.BusinessAdministrator"/>
                        <logic:iterate scope="request" name="permissionDenied" id="andPermission" type="com.aoindustries.aoserv.client.AOServPermission">
                            <bean:define id="permissionDisplay" type="java.lang.String"><%= andPermission.getDisplay(locale) %></bean:define>
                            <bean:define id="permissionDescription" type="java.lang.String"><%= andPermission.getDescription(locale) %></bean:define>
                            <tr>
                                <td nowrap><bean:write name="permissionDisplay"/></td>
                                <td nowrap><bean:write name="permissionDescription"/></td>
                                <td nowrap>
                                    <% if(thisBusinessAdministrator.hasPermission(andPermission)) { %>
                                        <bean:message bundle="/ApplicationResources" key="permissionDenied.andPermissions.header.hasPermission.yes"/>
                                    <% } else { %>
                                        <bean:message bundle="/ApplicationResources" key="permissionDenied.andPermissions.header.hasPermission.no"/>
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
        <bean:message bundle="/ApplicationResources" key="permissionDenied.noPermissionInformation"/>
    </logic:notPresent>
</skin:lightArea>
