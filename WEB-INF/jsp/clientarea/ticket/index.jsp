<%-- aoweb-struts: Do not edit --%>
<%--
  Copyright 2000-2009 by AO Industries, Inc.,
  7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" buffer="256kb" autoFlush="true" %>
<%@ include file="/WEB-INF/jsp/taglibs.jsp" %>

<skin:setContentType/>
<html:html lang="true">
    <skin:path>/clientarea/ticket/index.do</skin:path>
    <logic:equal name="siteSettings" property="brand.aowebStrutsNoindex" value="true"><skin:meta name="ROBOTS">NOINDEX</skin:meta></logic:equal>
    <skin:title><bean:message bundle="/clientarea/ticket/ApplicationResources" key="index.title"/></skin:title>
    <skin:navImageAlt><bean:message bundle="/clientarea/ticket/ApplicationResources" key="index.navImageAlt"/></skin:navImageAlt>
    <skin:keywords><bean:message bundle="/clientarea/ticket/ApplicationResources" key="index.keywords"/></skin:keywords>
    <skin:description><bean:message bundle="/clientarea/ticket/ApplicationResources" key="index.description"/></skin:description>
    <%@ include file="../add-parents.jsp" %>
    <%@ include file="add-siblings.jsp" %>
    <skin:skin>
        <skin:content width="600">
            <skin:contentTitle><bean:message bundle="/clientarea/ticket/ApplicationResources" key="index.title"/></skin:contentTitle>
            <skin:contentHorizontalDivider/>
            <skin:contentLine>
                <div style="text-align:left; padding-top:10px; padding-bottom:10px">
                    <html:link action="/create"><bean:message bundle="/clientarea/ticket/ApplicationResources" key="index.link.create"/></html:link>
                </div>
                <skin:lightArea>
                    <table border="0" cellspacing="0" cellpadding="5">
                        <tr>
                            <th><bean:message bundle="/clientarea/ticket/ApplicationResources" key="index.header.pkey"/></th>
                            <th><bean:message bundle="/clientarea/ticket/ApplicationResources" key="index.header.clientPriority"/></th>
                            <th><bean:message bundle="/clientarea/ticket/ApplicationResources" key="index.header.status"/></th>
                            <th><bean:message bundle="/clientarea/ticket/ApplicationResources" key="index.header.openDate"/></th>
                            <th><bean:message bundle="/clientarea/ticket/ApplicationResources" key="index.header.createdBy"/></th>
                            <logic:notEqual name="aoConn" property="businesses.size" value="1">
                                <th><bean:message bundle="/clientarea/ticket/ApplicationResources" key="index.header.accounting"/></th>
                            </logic:notEqual>
                            <th><bean:message bundle="/clientarea/ticket/ApplicationResources" key="index.header.summary"/></th>
                        </tr>
                        <logic:empty name="tickets">
                            <tr>
                                <td colspan="7" align="center">
                                    <bean:message bundle="/clientarea/ticket/ApplicationResources" key="index.noTickets"/>
                                </td>
                            </tr>
                        </logic:empty>
                        <logic:notEmpty name="tickets">
                            <logic:iterate name="tickets" id="ticket">
                                <skin:lightDarkTableRow pageAttributeId="isDark">
                                    <td nowrap><html:link action="/edit" paramId="pkey" paramName="ticket" paramProperty="pkey"><bean:write name="ticket" property="pkey"/></html:link></td>
                                    <td nowrap>
                                        <logic:equal name="ticket" property="status.status" value="<%= com.aoindustries.aoserv.client.TicketStatus.CLOSED %>">
                                            <logic:equal name="isDark" value="true">
                                                <img align="absmiddle" height="25" width="25" src="p_dark_black.gif">&nbsp;
                                            </logic:equal>
                                            <logic:equal name="isDark" value="false">
                                                <img align="absmiddle" height="25" width="25" src="p_light_black.gif">&nbsp;
                                            </logic:equal>
                                        </logic:equal>
                                        <logic:notEqual name="ticket" property="status.status" value="<%= com.aoindustries.aoserv.client.TicketStatus.CLOSED %>">
                                            <logic:equal name="ticket" property="clientPriority.priority" value="<%= com.aoindustries.aoserv.client.TicketPriority.LOW %>">
                                                <logic:equal name="isDark" value="true">
                                                    <img align="absmiddle" height="25" width="25" src="p_dark_green.gif">&nbsp;
                                                </logic:equal>
                                                <logic:equal name="isDark" value="false">
                                                    <img align="absmiddle" height="25" width="25" src="p_light_green.gif">&nbsp;
                                                </logic:equal>
                                            </logic:equal>
                                            <logic:equal name="ticket" property="clientPriority.priority" value="<%= com.aoindustries.aoserv.client.TicketPriority.NORMAL %>">
                                                <logic:equal name="isDark" value="true">
                                                    <img align="absmiddle" height="25" width="25" src="p_dark_yellow.gif">&nbsp;
                                                </logic:equal>
                                                <logic:equal name="isDark" value="false">
                                                    <img align="absmiddle" height="25" width="25" src="p_light_yellow.gif">&nbsp;
                                                </logic:equal>
                                            </logic:equal>
                                            <logic:equal name="ticket" property="clientPriority.priority" value="<%= com.aoindustries.aoserv.client.TicketPriority.HIGH %>">
                                                <logic:equal name="isDark" value="true">
                                                    <img align="absmiddle" height="25" width="25" src="p_dark_orange.gif">&nbsp;
                                                </logic:equal>
                                                <logic:equal name="isDark" value="false">
                                                    <img align="absmiddle" height="25" width="25" src="p_light_orange.gif">&nbsp;
                                                </logic:equal>
                                            </logic:equal>
                                            <logic:equal name="ticket" property="clientPriority.priority" value="<%= com.aoindustries.aoserv.client.TicketPriority.URGENT %>">
                                                <logic:equal name="isDark" value="true">
                                                    <img align="absmiddle" height="25" width="25" src="p_dark_red.gif">&nbsp;
                                                </logic:equal>
                                                <logic:equal name="isDark" value="false">
                                                    <img align="absmiddle" height="25" width="25" src="p_light_red.gif">&nbsp;
                                                </logic:equal>
                                            </logic:equal>
                                        </logic:notEqual>
                                        <bean:write name="ticket" property="clientPriority"/>
                                    </td>
                                    <td nowrap><bean:write name="ticket" property="status"/></td>
                                    <td nowrap><aoweb:dateTime><bean:write name="ticket" property="openDate"/></aoweb:dateTime></td>
                                    <td nowrap>
                                        <logic:notEmpty name="ticket" property="createdBy">
                                            <bean:write name="ticket" property="createdBy.name"/>
                                        </logic:notEmpty>
                                    </td>
                                    <logic:notEqual name="aoConn" property="businesses.size" value="1">
                                        <td nowrap><bean:write name="ticket" property="business"/></td>
                                    </logic:notEqual>
                                    <td nowrap><bean:write name="ticket" property="summary"/></td>
                                </skin:lightDarkTableRow>
                            </logic:iterate>
                        </logic:notEmpty>
                    </table>
                </skin:lightArea>
                <div style="text-align:left; padding-top:10px; padding-bottom:10px">
                    <html:link action="/create"><bean:message bundle="/clientarea/ticket/ApplicationResources" key="index.link.create"/></html:link>
                </div>
            </skin:contentLine>
        </skin:content>
    </skin:skin>
</html:html>
