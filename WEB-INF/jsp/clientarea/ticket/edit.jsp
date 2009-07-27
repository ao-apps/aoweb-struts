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
        <skin:path>/clientarea/ticket/edit.do?pkey=<ao:write scope="request" name="ticket" property="pkey" /></skin:path>
        <skin:title><fmt:message key="edit.title" /></skin:title>
        <skin:navImageAlt><fmt:message key="edit.navImageAlt" /></skin:navImageAlt>
        <skin:keywords><fmt:message key="edit.keywords" /></skin:keywords>
        <skin:description><fmt:message key="edit.description" /></skin:description>
        <jsp:include page="add-parents.jsp" />
        <jsp:include page="add-siblings.jsp" />

        <%
            org.apache.struts.action.ActionMessages errors = (org.apache.struts.action.ActionMessages)request.getAttribute(org.apache.struts.Globals.ERROR_KEY);
            String onload;
            if(errors==null) {
                onload = "";
            } else if(errors.get("summary").hasNext()) {
                onload = "document.forms['ticketForm'].summary.select(); document.forms['ticketForm'].summary.focus();";
            } else if(errors.get("annotationSummary").hasNext()) {
                onload = "document.forms['ticketForm'].annotationSummary.select(); document.forms['ticketForm'].annotationSummary.focus();";
            } else {
                onload = "";
            }
        %>
        <skin:skin onload="<%= onload %>">
            <skin:content>
                <skin:contentTitle><fmt:message key="edit.title" /></skin:contentTitle>
                <skin:contentHorizontalDivider />
                <skin:contentLine>
                    <logic:present scope="request" name="permissionDenied">
                        <%@ include file="../../permission-denied.jsp" %>
                    </logic:present>
                    <logic:notPresent scope="request" name="permissionDenied">
                        <html:javascript staticJavascript='false' bundle="/clientarea/ticket/ApplicationResources" formName="ticketForm" />
                        <html:form action="/edit-completed" onsubmit="return validateTicketForm(this);">
                            <bean:define scope="request" name="ticket" type="com.aoindustries.aoserv.client.Ticket" id="ticket" />
                            <div>
                                <skin:lightArea>
                                    <table cellspacing="0" cellpadding="4">
                                        <tr>
                                            <td style="white-space:nowrap"><fmt:message key="edit.label.pkey" /></td>
                                            <td><html:hidden name="ticket" property="pkey" write="true" /></td>
                                        </tr>
                                        <tr>
                                            <td style="white-space:nowrap"><fmt:message key="edit.label.status" /></td>
                                            <td>
                                                <bean:define scope="request" name="locale" type="java.util.Locale" id="locale" />
                                                <ao:write name="ticket" property="status" method="getDescription"/>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td style="white-space:nowrap"><fmt:message key="edit.label.openDate" /></td>
                                            <td><aoweb:dateTime><ao:write scope="request" name="ticket" property="openDate" /></aoweb:dateTime></td>
                                        </tr>
                                        <logic:notEmpty scope="request" name="ticket" property="createdBy">
                                            <tr>
                                                <td style="white-space:nowrap"><fmt:message key="edit.label.createdBy" /></td>
                                                <td><ao:write scope="request" name="ticket" property="createdBy.name" /></td>
                                            </tr>
                                        </logic:notEmpty>
                                        <logic:empty  name="ticket" property="createdBy">
                                            <logic:notEmpty name="siteSettings" property='<%= "rootAOServConnector.tickets.map("+ticket.getPkey()+")" %>'>
                                                <logic:notEmpty name="siteSettings" property='<%= "rootAOServConnector.tickets.map("+ticket.getPkey()+").createdBy" %>'>
                                                    <tr>
                                                        <td style="white-space:nowrap"><fmt:message key="edit.label.createdBy" /></td>
                                                        <td><ao:write name="siteSettings" property='<%= "rootAOServConnector.tickets.map("+ticket.getPkey()+").createdBy.name" %>' /></td>
                                                    </tr>
                                                </logic:notEmpty>
                                            </logic:notEmpty>
                                        </logic:empty>
                                        <tr>
                                            <td style="white-space:nowrap"><fmt:message key="TicketForm.field.accounting.prompt" /></td>
                                            <td>
                                                <logic:notEqual name="aoConn" property="businesses.size" value="1">
                                                    <logic:notEqual name="ticket" property="status.status" value="<%= com.aoindustries.aoserv.client.TicketStatus.CLOSED %>">
                                                        <html:select property="accounting">
                                                            <logic:empty scope="request" name="ticketForm" property="accounting">
                                                                <html:option value="" />
                                                            </logic:empty>
                                                            <html:optionsCollection name="aoConn" property="businesses.rows" label="accounting" value="accounting" />
                                                        </html:select>
                                                    </logic:notEqual>
                                                    <logic:equal name="ticket" property="status.status" value="<%= com.aoindustries.aoserv.client.TicketStatus.CLOSED %>">
                                                        <html:hidden property="accounting" write="true" />
                                                    </logic:equal>
                                                </logic:notEqual>
                                                <logic:equal name="aoConn" property="businesses.size" value="1">
                                                    <html:hidden property="accounting" write="true" />
                                                </logic:equal>
                                            </td>
                                            <td><html:errors bundle="/clientarea/ticket/ApplicationResources" property="accounting" /></td>
                                        </tr>
                                        <tr>
                                            <td style="white-space:nowrap"><fmt:message key="TicketForm.field.contactEmails.prompt" /></td>
                                            <td>
                                                <logic:notEqual name="ticket" property="status.status" value="<%= com.aoindustries.aoserv.client.TicketStatus.CLOSED %>">
                                                    <bean:define scope="request" name="ticketForm" property="contactEmails" type="java.lang.String" id="contactEmails" />
                                                    <% int numContactEmails = com.aoindustries.util.StringUtility.splitLines(contactEmails).size(); %>
                                                    <html:textarea property="contactEmails" cols="40" rows="<%= Integer.toString(Math.max(numContactEmails, 1)) %>" />
                                                </logic:notEqual>
                                                <logic:equal name="ticket" property="status.status" value="<%= com.aoindustries.aoserv.client.TicketStatus.CLOSED %>">
                                                    <logic:notEmpty name="ticketForm" property="contactEmails">
                                                        <div style="border:1px inset; padding: 4px"><pre style="display:inline"><html:hidden property="contactEmails" write="true" /></pre></div>
                                                    </logic:notEmpty>
                                                    <logic:empty name="ticketForm" property="contactEmails">
                                                        <html:hidden property="contactEmails" write="true" />
                                                    </logic:empty>
                                                </logic:equal>
                                            </td>
                                            <td><html:errors bundle="/clientarea/ticket/ApplicationResources" property="contactEmails" /></td>
                                        </tr>
                                        <tr>
                                            <td style="white-space:nowrap"><fmt:message key="TicketForm.field.contactPhoneNumbers.prompt" /></td>
                                            <td>
                                                <logic:notEqual name="ticket" property="status.status" value="<%= com.aoindustries.aoserv.client.TicketStatus.CLOSED %>">
                                                    <bean:define scope="request" name="ticketForm" property="contactPhoneNumbers" type="java.lang.String" id="contactPhoneNumbers" />
                                                    <% int numContactPhoneNumbers = com.aoindustries.util.StringUtility.splitLines(contactPhoneNumbers).size(); %>
                                                    <html:textarea property="contactPhoneNumbers" cols="40" rows="<%= Integer.toString(Math.max(numContactPhoneNumbers, 1)) %>" />
                                                </logic:notEqual>
                                                <logic:equal name="ticket" property="status.status" value="<%= com.aoindustries.aoserv.client.TicketStatus.CLOSED %>">
                                                    <logic:notEmpty name="ticketForm" property="contactPhoneNumbers">
                                                        <div style="border:1px inset; padding: 4px"><pre style="display:inline"><html:hidden property="contactPhoneNumbers" write="true" /></pre></div>
                                                    </logic:notEmpty>
                                                    <logic:empty name="ticketForm" property="contactPhoneNumbers">
                                                        <html:hidden property="contactPhoneNumbers" write="true" />
                                                    </logic:empty>
                                                </logic:equal>
                                            </td>
                                            <td><html:errors bundle="/clientarea/ticket/ApplicationResources" property="contactPhoneNumbers" /></td>
                                        </tr>
                                        <tr>
                                            <td style="white-space:nowrap"><fmt:message key="TicketForm.field.clientPriority.prompt" /></td>
                                            <td>
                                                <logic:notEqual name="ticket" property="status.status" value="<%= com.aoindustries.aoserv.client.TicketStatus.CLOSED %>">
                                                    <html:select property="clientPriority">
                                                        <html:optionsCollection name="aoConn" property="ticketPriorities.rows" label="priority" value="priority" />
                                                    </html:select>
                                                </logic:notEqual>
                                                <logic:equal name="ticket" property="status.status" value="<%= com.aoindustries.aoserv.client.TicketStatus.CLOSED %>">
                                                    <html:hidden property="clientPriority" write="true" />
                                                </logic:equal>
                                            </td>
                                            <td><html:errors bundle="/clientarea/ticket/ApplicationResources" property="clientPriority" /></td>
                                        </tr>
                                        <tr>
                                            <td style="white-space:nowrap"><fmt:message key="TicketForm.field.summary.prompt" /></td>
                                            <td>
                                                <logic:notEqual name="ticket" property="status.status" value="<%= com.aoindustries.aoserv.client.TicketStatus.CLOSED %>">
                                                    <html:text property="summary" size="60" />
                                                </logic:notEqual>
                                                <logic:equal name="ticket" property="status.status" value="<%= com.aoindustries.aoserv.client.TicketStatus.CLOSED %>">
                                                    <html:hidden property="summary" write="true" />
                                                </logic:equal>
                                            </td>
                                            <td><html:errors bundle="/clientarea/ticket/ApplicationResources" property="summary" /></td>
                                        </tr>
                                        <logic:notEqual name="ticket" property="status.status" value="<%= com.aoindustries.aoserv.client.TicketStatus.CLOSED %>">
                                            <tr>
                                                <td colspan="3" align="center">
                                                    <br />
                                                    <html:submit><fmt:message key="edit.field.submit.label" /></html:submit>
                                                </td>
                                            </tr>
                                        </logic:notEqual>
                                    </table>
                                </skin:lightArea>
                                <logic:notEmpty scope="request" name="ticketForm" property="details">
                                    <br />
                                    <skin:lightArea>
                                        <fmt:message key="TicketForm.field.details.header" />
                                        <hr />
                                        <div style="border:1px inset; padding:4px">
                                            <code><ao:text makeBr="true" makeNbsp="true"><html:hidden property="details" write="true" /></ao:text></code>
                                        </div>
                                        <%--<html:textarea readonly="<%= Boolean.TRUE %>" property="details" cols="80" rows="20" /><br />--%>
                                        <html:errors bundle="/clientarea/ticket/ApplicationResources" property="details" />
                                    </skin:lightArea>
                                </logic:notEmpty>
                                <bean:define scope="request" name="ticket" property="ticketActions" id="actions" />
                                <logic:notEmpty name="actions">
                                    <br />
                                    <skin:lightArea>
                                        <fmt:message key="edit.actions.header" />
                                        <hr />
                                        <table cellspacing="0" cellpadding="4">
                                            <tr>
                                                <th><fmt:message key="edit.header.time" /></th>
                                                <th><fmt:message key="edit.header.administrator" /></th>
                                                <th><fmt:message key="edit.header.actionType" /></th>
                                                <th><fmt:message key="edit.header.summary" /></th>
                                            </tr>
                                            <logic:iterate name="actions" type="com.aoindustries.aoserv.client.TicketAction" id="action">
                                                <skin:lightDarkTableRow pageAttributeId="isDark">
                                                    <td style="white-space:nowrap"><aoweb:dateTime><ao:write name="action" property="time" /></aoweb:dateTime></td>
                                                    <td style="white-space:nowrap">
                                                        <logic:notEmpty name="action" property="administrator">
                                                            <ao:write name="action" property="administrator.name" />
                                                        </logic:notEmpty>
                                                        <logic:empty  name="action" property="administrator">
                                                            <logic:notEmpty name="siteSettings" property='<%= "rootAOServConnector.ticketActions.map("+action.getPkey()+")" %>'>
                                                                <logic:notEmpty name="siteSettings" property='<%= "rootAOServConnector.ticketActions.map("+action.getPkey()+").administrator" %>'>
                                                                    <ao:write name="siteSettings" property='<%= "rootAOServConnector.ticketActions.map("+action.getPkey()+").administrator.name" %>' />
                                                                </logic:notEmpty>
                                                            </logic:notEmpty>
                                                        </logic:empty>
                                                    </td>
                                                    <td style="white-space:nowrap"><ao:write name="action" property="ticketActionType" /></td>
                                                    <td style="white-space:nowrap"><ao:write name="action" method="getSummary" /></td>
                                                </skin:lightDarkTableRow>
                                                <logic:notEmpty name="action" property="details">
                                                    <logic:equal name="isDark" value="true">
                                                        <tr class="aoLightRow">
                                                            <td colspan="4" style="width:100%;"><div style="border:1px inset; padding: 4px">
                                                                <code><ao:text makeBr="true" makeNbsp="true"><ao:write name="action" property="details" /></ao:text></code>
                                                            </div></td>
                                                        </tr>
                                                    </logic:equal>
                                                    <logic:equal name="isDark" value="false">
                                                        <tr class="aoDarkRow">
                                                            <td colspan="4" style="width:100%;"><div style="border:1px inset; padding: 4px">
                                                                <code><ao:text makeBr="true" makeNbsp="true"><ao:write name="action" property="details" /></ao:text></code>
                                                            </div></td>
                                                        </tr>
                                                    </logic:equal>
                                                </logic:notEmpty>
                                            </logic:iterate>
                                        </table>
                                    </skin:lightArea>
                                </logic:notEmpty>
                                <br />
                                <skin:lightArea>
                                    <logic:equal name="ticket" property="status.status" value="<%= com.aoindustries.aoserv.client.TicketStatus.CLOSED %>">
                                        <fmt:message key="edit.reopenTicket.header" />
                                    </logic:equal>
                                    <logic:equal name="ticket" property="status.status" value="<%= com.aoindustries.aoserv.client.TicketStatus.BOUNCED %>">
                                        <fmt:message key="edit.replyTicket.header" />
                                    </logic:equal>
                                    <logic:notEqual name="ticket" property="status.status" value="<%= com.aoindustries.aoserv.client.TicketStatus.CLOSED %>">
                                        <logic:notEqual name="ticket" property="status.status" value="<%= com.aoindustries.aoserv.client.TicketStatus.BOUNCED %>">
                                            <fmt:message key="edit.addAnnotation.header" />
                                        </logic:notEqual>
                                    </logic:notEqual>
                                    <hr />
                                    <table cellspacing="0" cellpadding="4">
                                        <tr>
                                            <td style="white-space:nowrap"><fmt:message key="TicketForm.field.annotationSummary.prompt" /></td>
                                            <td><html:text property="annotationSummary" size="60" /></td>
                                            <td><html:errors bundle="/clientarea/ticket/ApplicationResources" property="annotationSummary" /></td>
                                        </tr>
                                        <tr>
                                            <td style='white-space:nowrap' colspan="3">
                                                <br />
                                                <fmt:message key="TicketForm.field.annotationDetails.prompt" /><br />
                                                <html:textarea property="annotationDetails" cols="80" rows="20" /><br />
                                                <%--<textarea name="annotationDetails" cols="80" rows="20" wrap="hard"><bean:write scope="request" name="ticketForm" property="annotationDetails"/></textarea><br />--%>
                                                <html:errors bundle="/clientarea/ticket/ApplicationResources" property="annotationDetails" />
                                            </td>
                                        </tr>
                                        <tr>
                                            <td colspan="3" align="center">
                                                <br />
                                                <html:submit>
                                                    <logic:equal name="ticket" property="status.status" value="<%= com.aoindustries.aoserv.client.TicketStatus.CLOSED %>">
                                                        <fmt:message key="edit.field.submitAnnotation.label.reopen" />
                                                    </logic:equal>
                                                    <logic:equal name="ticket" property="status.status" value="<%= com.aoindustries.aoserv.client.TicketStatus.BOUNCED %>">
                                                        <fmt:message key="edit.field.submitAnnotation.label.replyTicket" />
                                                    </logic:equal>
                                                    <logic:notEqual name="ticket" property="status.status" value="<%= com.aoindustries.aoserv.client.TicketStatus.CLOSED %>">
                                                        <logic:notEqual name="ticket" property="status.status" value="<%= com.aoindustries.aoserv.client.TicketStatus.BOUNCED %>">
                                                            <fmt:message key="edit.field.submitAnnotation.label" />
                                                        </logic:notEqual>
                                                    </logic:notEqual>
                                                </html:submit>
                                            </td>
                                        </tr>
                                    </table>
                                </skin:lightArea>
                            </div>
                        </html:form>
                    </logic:notPresent>
                </skin:contentLine>
            </skin:content>
        </skin:skin>
    </fmt:bundle>
</html:html>
