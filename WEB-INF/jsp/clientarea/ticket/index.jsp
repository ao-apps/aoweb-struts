<%-- aoweb-struts: Do not edit --%>
<%--
  Copyright 2000-2009 by AO Industries, Inc.,
  7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" buffer="256kb" autoFlush="true" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/taglibs.jsp" %>

<skin:setContentType />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html:html lang="true" xhtml="true">
    <fmt:bundle basename="com.aoindustries.website.clientarea.ticket.ApplicationResources">
        <skin:path>/clientarea/ticket/index.do</skin:path>
        <logic:equal name="siteSettings" property="brand.aowebStrutsNoindex" value="true"><skin:meta name="ROBOTS">NOINDEX</skin:meta></logic:equal>
        <skin:title><fmt:message key="index.title" /></skin:title>
        <skin:navImageAlt><fmt:message key="index.navImageAlt" /></skin:navImageAlt>
        <skin:keywords><fmt:message key="index.keywords" /></skin:keywords>
        <skin:description><fmt:message key="index.description" /></skin:description>
        <%@ include file="../add-parents.jsp" %>
        <%@ include file="add-siblings.jsp" %>
        <skin:skin>
            <skin:content width="600">
                <skin:contentTitle><fmt:message key="index.title" /></skin:contentTitle>
                <skin:contentHorizontalDivider />
                <skin:contentLine>
                    <div style="text-align:left; padding-top:10px; padding-bottom:10px">
                        <html:link action="/create"><fmt:message key="index.link.create" /></html:link>
                    </div>
                    <skin:lightArea>
                        <table cellspacing="0" cellpadding="5">
                            <tr>
                                <th><fmt:message key="index.header.pkey" /></th>
                                <th><fmt:message key="index.header.clientPriority" /></th>
                                <th><fmt:message key="index.header.status" /></th>
                                <th><fmt:message key="index.header.openDate" /></th>
                                <th><fmt:message key="index.header.createdBy" /></th>
                                <logic:notEqual name="aoConn" property="businesses.size" value="1">
                                    <th><fmt:message key="index.header.accounting" /></th>
                                </logic:notEqual>
                                <th><fmt:message key="index.header.summary" /></th>
                            </tr>
                            <logic:empty name="tickets">
                                <tr>
                                    <td colspan="7" align="center">
                                        <fmt:message key="index.noTickets" />
                                    </td>
                                </tr>
                            </logic:empty>
                            <logic:notEmpty name="tickets">
                                <logic:iterate name="tickets" id="ticket">
                                    <skin:lightDarkTableRow pageAttributeId="isDark">
                                        <td style="white-space:nowrap"><html:link action="/edit" paramId="pkey" paramName="ticket" paramProperty="pkey"><ao:write name="ticket" property="pkey" /></html:link></td>
                                        <td style="white-space:nowrap">
                                            <logic:equal name="ticket" property="status.status" value="<%= com.aoindustries.aoserv.client.TicketStatus.CLOSED %>">
                                                <logic:equal name="isDark" value="true">
                                                    <img height="25" width="25" src="p_dark_black.gif" alt="" />&#160;
                                                </logic:equal>
                                                <logic:equal name="isDark" value="false">
                                                    <img height="25" width="25" src="p_light_black.gif" alt="" />&#160;
                                                </logic:equal>
                                            </logic:equal>
                                            <logic:notEqual name="ticket" property="status.status" value="<%= com.aoindustries.aoserv.client.TicketStatus.CLOSED %>">
                                                <logic:equal name="ticket" property="clientPriority.priority" value="<%= com.aoindustries.aoserv.client.TicketPriority.LOW %>">
                                                    <logic:equal name="isDark" value="true">
                                                        <img height="25" width="25" src="p_dark_green.gif" alt="" />&#160;
                                                    </logic:equal>
                                                    <logic:equal name="isDark" value="false">
                                                        <img height="25" width="25" src="p_light_green.gif" alt="" />&#160;
                                                    </logic:equal>
                                                </logic:equal>
                                                <logic:equal name="ticket" property="clientPriority.priority" value="<%= com.aoindustries.aoserv.client.TicketPriority.NORMAL %>">
                                                    <logic:equal name="isDark" value="true">
                                                        <img height="25" width="25" src="p_dark_yellow.gif" alt="" />&#160;
                                                    </logic:equal>
                                                    <logic:equal name="isDark" value="false">
                                                        <img height="25" width="25" src="p_light_yellow.gif" alt="" />&#160;
                                                    </logic:equal>
                                                </logic:equal>
                                                <logic:equal name="ticket" property="clientPriority.priority" value="<%= com.aoindustries.aoserv.client.TicketPriority.HIGH %>">
                                                    <logic:equal name="isDark" value="true">
                                                        <img height="25" width="25" src="p_dark_orange.gif" alt="" />&#160;
                                                    </logic:equal>
                                                    <logic:equal name="isDark" value="false">
                                                        <img height="25" width="25" src="p_light_orange.gif" alt="" />&#160;
                                                    </logic:equal>
                                                </logic:equal>
                                                <logic:equal name="ticket" property="clientPriority.priority" value="<%= com.aoindustries.aoserv.client.TicketPriority.URGENT %>">
                                                    <logic:equal name="isDark" value="true">
                                                        <img height="25" width="25" src="p_dark_red.gif" alt="" />&#160;
                                                    </logic:equal>
                                                    <logic:equal name="isDark" value="false">
                                                        <img height="25" width="25" src="p_light_red.gif" alt="" />&#160;
                                                    </logic:equal>
                                                </logic:equal>
                                            </logic:notEqual>
                                            <ao:write name="ticket" property="clientPriority" />
                                        </td>
                                        <td style="white-space:nowrap"><ao:write name="ticket" property="status" /></td>
                                        <td style="white-space:nowrap"><aoweb:dateTime><ao:write name="ticket" property="openDate" /></aoweb:dateTime></td>
                                        <td style="white-space:nowrap">
                                            <logic:notEmpty name="ticket" property="createdBy">
                                                <ao:write name="ticket" property="createdBy.name" />
                                            </logic:notEmpty>
                                        </td>
                                        <logic:notEqual name="aoConn" property="businesses.size" value="1">
                                            <td style="white-space:nowrap"><ao:write name="ticket" property="business" /></td>
                                        </logic:notEqual>
                                        <td style="white-space:nowrap"><ao:write name="ticket" property="summary" /></td>
                                    </skin:lightDarkTableRow>
                                </logic:iterate>
                            </logic:notEmpty>
                        </table>
                    </skin:lightArea>
                    <div style="text-align:left; padding-top:10px; padding-bottom:10px">
                        <html:link action="/create"><fmt:message key="index.link.create" /></html:link>
                    </div>
                </skin:contentLine>
            </skin:content>
        </skin:skin>
    </fmt:bundle>
</html:html>
