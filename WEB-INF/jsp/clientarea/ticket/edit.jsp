<%-- aoweb-struts: Do not edit --%>
<%--
  Copyright 2000-2009 by AO Industries, Inc.,
  7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" buffer="256kb" autoFlush="true" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/taglibs.jsp" %>

<skin:setContentType/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html:html lang="true" xhtml="true">
    <skin:path>/clientarea/ticket/edit.do?pkey=<bean:write scope="request" name="ticket" property="pkey"/></skin:path>
    <skin:title><bean:message bundle="/clientarea/ticket/ApplicationResources" key="edit.title"/></skin:title>
    <skin:navImageAlt><bean:message bundle="/clientarea/ticket/ApplicationResources" key="edit.navImageAlt"/></skin:navImageAlt>
    <skin:keywords><bean:message bundle="/clientarea/ticket/ApplicationResources" key="edit.keywords"/></skin:keywords>
    <skin:description><bean:message bundle="/clientarea/ticket/ApplicationResources" key="edit.description"/></skin:description>
    <jsp:include page="add-parents.jsp"/>
    <jsp:include page="add-siblings.jsp"/>

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
            <skin:contentTitle><bean:message bundle="/clientarea/ticket/ApplicationResources" key="edit.title"/></skin:contentTitle>
            <skin:contentHorizontalDivider/>
            <skin:contentLine>
                <logic:present scope="request" name="permissionDenied">
                    <%@ include file="../../permission-denied.jsp" %>
                </logic:present>
                <logic:notPresent scope="request" name="permissionDenied">
                    <html:javascript staticJavascript='false' bundle="/clientarea/ticket/ApplicationResources" formName="ticketForm"/><noscript><!-- Do nothing --></noscript>
                    <html:form action="/edit-completed" onsubmit="return validateTicketForm(this);">
                        <skin:lightArea>
                            <table cellspacing="0" cellpadding="4">
                                <tr>
                                    <td style="white-space:nowrap"><bean:message bundle="/clientarea/ticket/ApplicationResources" key="edit.label.pkey"/></td>
                                    <td><html:hidden name="ticket" property="pkey" write="true"/></td>
                                </tr>
                                <tr>
                                    <td style="white-space:nowrap"><bean:message bundle="/clientarea/ticket/ApplicationResources" key="edit.label.status"/></td>
                                    <td>
                                        <bean:define scope="request" name="ticket" type="com.aoindustries.aoserv.client.Ticket" id="ticket"/>
                                        <bean:define scope="request" name="locale" type="java.util.Locale" id="locale"/>
                                        <bean:define id="statusDescription"><%= ticket.getStatus().getDescription(locale) %></bean:define>
                                        <bean:write name="statusDescription"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td style="white-space:nowrap"><bean:message bundle="/clientarea/ticket/ApplicationResources" key="edit.label.openDate"/></td>
                                    <td><aoweb:dateTime><bean:write scope="request" name="ticket" property="openDate"/></aoweb:dateTime></td>
                                </tr>
                                <logic:notEmpty scope="request" name="ticket" property="createdBy">
                                    <tr>
                                        <td style="white-space:nowrap"><bean:message bundle="/clientarea/ticket/ApplicationResources" key="edit.label.createdBy"/></td>
                                        <td><bean:write scope="request" name="ticket" property="createdBy.name"/></td>
                                    </tr>
                                </logic:notEmpty>
                                <tr>
                                    <td style="white-space:nowrap"><bean:message bundle="/clientarea/ticket/ApplicationResources" key="TicketForm.field.accounting.prompt"/></td>
                                    <td>
                                        <logic:notEqual name="aoConn" property="businesses.size" value="1">
                                            <logic:notEqual name="ticket" property="status.status" value="<%= com.aoindustries.aoserv.client.TicketStatus.CLOSED %>">
                                                <html:select property="accounting">
                                                    <logic:empty scope="request" name="ticketForm" property="accounting">
                                                        <html:option value=""/>
                                                    </logic:empty>
                                                    <html:optionsCollection name="aoConn" property="businesses.rows" label="accounting" value="accounting"/>
                                                </html:select>
                                            </logic:notEqual>
                                            <logic:equal name="ticket" property="status.status" value="<%= com.aoindustries.aoserv.client.TicketStatus.CLOSED %>">
                                                <html:hidden property="accounting" write="true"/>
                                            </logic:equal>
                                        </logic:notEqual>
                                        <logic:equal name="aoConn" property="businesses.size" value="1">
                                            <html:hidden property="accounting" write="true"/>
                                        </logic:equal>
                                    </td>
                                    <td><html:errors bundle="/clientarea/ticket/ApplicationResources" property="accounting"/></td>
                                </tr>
                                <tr>
                                    <td style="white-space:nowrap"><bean:message bundle="/clientarea/ticket/ApplicationResources" key="TicketForm.field.contactEmails.prompt"/></td>
                                    <td>
                                        <logic:notEqual name="ticket" property="status.status" value="<%= com.aoindustries.aoserv.client.TicketStatus.CLOSED %>">
                                            <bean:define scope="request" name="ticketForm" property="contactEmails" type="java.lang.String" id="contactEmails"/>
                                            <% int numContactEmails = com.aoindustries.util.StringUtility.splitLines(contactEmails).size(); %>
                                            <html:textarea property="contactEmails" cols="40" rows="<%= Integer.toString(Math.max(numContactEmails, 1)) %>"/>
                                        </logic:notEqual>
                                        <logic:equal name="ticket" property="status.status" value="<%= com.aoindustries.aoserv.client.TicketStatus.CLOSED %>">
                                            <div style="border:1px inset; padding: 4px"><pre style="display:inline"><html:hidden property="contactEmails" write="true"/></pre></div>
                                        </logic:equal>
                                    </td>
                                    <td><html:errors bundle="/clientarea/ticket/ApplicationResources" property="contactEmails"/></td>
                                </tr>
                                <tr>
                                    <td style="white-space:nowrap"><bean:message bundle="/clientarea/ticket/ApplicationResources" key="TicketForm.field.contactPhoneNumbers.prompt"/></td>
                                    <td>
                                        <logic:notEqual name="ticket" property="status.status" value="<%= com.aoindustries.aoserv.client.TicketStatus.CLOSED %>">
                                            <bean:define scope="request" name="ticketForm" property="contactPhoneNumbers" type="java.lang.String" id="contactPhoneNumbers"/>
                                            <% int numContactPhoneNumbers = com.aoindustries.util.StringUtility.splitLines(contactPhoneNumbers).size(); %>
                                            <html:textarea property="contactPhoneNumbers" cols="40" rows="<%= Integer.toString(Math.max(numContactPhoneNumbers, 1)) %>"/>
                                        </logic:notEqual>
                                        <logic:equal name="ticket" property="status.status" value="<%= com.aoindustries.aoserv.client.TicketStatus.CLOSED %>">
                                            <div style="border:1px inset; padding: 4px"><pre style="display:inline"><html:hidden property="contactPhoneNumbers" write="true"/></pre></div>
                                        </logic:equal>
                                    </td>
                                    <td><html:errors bundle="/clientarea/ticket/ApplicationResources" property="contactPhoneNumbers"/></td>
                                </tr>
                                <tr>
                                    <td style="white-space:nowrap"><bean:message bundle="/clientarea/ticket/ApplicationResources" key="TicketForm.field.clientPriority.prompt"/></td>
                                    <td>
                                        <logic:notEqual name="ticket" property="status.status" value="<%= com.aoindustries.aoserv.client.TicketStatus.CLOSED %>">
                                            <html:select property="clientPriority">
                                                <html:optionsCollection name="aoConn" property="ticketPriorities.rows" label="priority" value="priority"/>
                                            </html:select>
                                        </logic:notEqual>
                                        <logic:equal name="ticket" property="status.status" value="<%= com.aoindustries.aoserv.client.TicketStatus.CLOSED %>">
                                            <html:hidden property="clientPriority" write="true"/>
                                        </logic:equal>
                                    </td>
                                    <td><html:errors bundle="/clientarea/ticket/ApplicationResources" property="clientPriority"/></td>
                                </tr>
                                <tr>
                                    <td style="white-space:nowrap"><bean:message bundle="/clientarea/ticket/ApplicationResources" key="TicketForm.field.summary.prompt"/></td>
                                    <td>
                                        <logic:notEqual name="ticket" property="status.status" value="<%= com.aoindustries.aoserv.client.TicketStatus.CLOSED %>">
                                            <html:text property="summary" size="60"/>
                                        </logic:notEqual>
                                        <logic:equal name="ticket" property="status.status" value="<%= com.aoindustries.aoserv.client.TicketStatus.CLOSED %>">
                                            <html:hidden property="summary" write="true"/>
                                        </logic:equal>
                                    </td>
                                    <td><html:errors bundle="/clientarea/ticket/ApplicationResources" property="summary"/></td>
                                </tr>
                                <logic:notEqual name="ticket" property="status.status" value="<%= com.aoindustries.aoserv.client.TicketStatus.CLOSED %>">
                                    <tr>
                                        <td colspan="3" align="center">
                                            <br />
                                            <html:submit><bean:message bundle="/clientarea/ticket/ApplicationResources" key="edit.field.submit.label"/></html:submit>
                                        </td>
                                    </tr>
                                </logic:notEqual>
                            </table>
                        </skin:lightArea>
                        <logic:notEmpty scope="request" name="ticketForm" property="details">
                            <br />
                            <skin:lightArea>
                                <bean:message bundle="/clientarea/ticket/ApplicationResources" key="TicketForm.field.details.header"/>
                                <hr />
                                <div style="border:1px inset; padding:4px"><pre><html:hidden property="details" write="true"/></pre></div>
                                <%--<html:textarea readonly="<%= Boolean.TRUE %>" property="details" cols="80" rows="20"/><br />--%>
                                <html:errors bundle="/clientarea/ticket/ApplicationResources" property="details"/>
                            </skin:lightArea>
                        </logic:notEmpty>
                        <bean:define scope="request" name="ticket" property="ticketActions" id="actions"/>
                        <logic:notEmpty name="actions">
                            <br />
                            <skin:lightArea>
                                <bean:message bundle="/clientarea/ticket/ApplicationResources" key="edit.actions.header"/>
                                <hr />
                                <table cellspacing="0" cellpadding="4">
                                    <tr>
                                        <th><bean:message bundle="/clientarea/ticket/ApplicationResources" key="edit.header.time"/></th>
                                        <th><bean:message bundle="/clientarea/ticket/ApplicationResources" key="edit.header.administrator"/></th>
                                        <th><bean:message bundle="/clientarea/ticket/ApplicationResources" key="edit.header.actionType"/></th>
                                        <th><bean:message bundle="/clientarea/ticket/ApplicationResources" key="edit.header.summary"/></th>
                                    </tr>
                                    <logic:iterate name="actions" type="com.aoindustries.aoserv.client.TicketAction" id="action">
                                        <skin:lightDarkTableRow pageAttributeId="isDark">
                                            <td style="white-space:nowrap"><aoweb:dateTime><bean:write name="action" property="time"/></aoweb:dateTime></td>
                                            <td style="white-space:nowrap">
                                                <logic:present name="action" property="administrator">
                                                    <bean:write name="action" property="administrator.name"/>
                                                </logic:present>
                                            </td>
                                            <td style="white-space:nowrap"><bean:write name="action" property="ticketActionType"/></td>
                                            <td style="white-space:nowrap">
                                                <bean:define scope="request" name="locale" type="java.util.Locale" id="locale"/>
                                                <bean:define id="summary"><%= action.getSummary(locale) %></bean:define>
                                                <bean:write name="summary"/>
                                            </td>
                                        </skin:lightDarkTableRow>
                                        <logic:notEmpty name="action" property="details">
                                            <logic:equal name="isDark" value="true">
                                                <tr class="aoLightRow">
                                                    <td colspan="4" width="100%"><div style="border:1px inset; padding: 4px"><pre><bean:write name="action" property="details"/></pre></div></td>
                                                </tr>
                                            </logic:equal>
                                            <logic:equal name="isDark" value="false">
                                                <tr class="aoDarkRow">
                                                    <td colspan="4" width="100%"><div style="border:1px inset; padding: 4px"><pre><bean:write name="action" property="details"/></pre></div></td>
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
                                <bean:message bundle="/clientarea/ticket/ApplicationResources" key="edit.reopenTicket.header"/>
                            </logic:equal>
                            <logic:equal name="ticket" property="status.status" value="<%= com.aoindustries.aoserv.client.TicketStatus.BOUNCED %>">
                                <bean:message bundle="/clientarea/ticket/ApplicationResources" key="edit.replyTicket.header"/>
                            </logic:equal>
                            <logic:notEqual name="ticket" property="status.status" value="<%= com.aoindustries.aoserv.client.TicketStatus.CLOSED %>">
                                <logic:notEqual name="ticket" property="status.status" value="<%= com.aoindustries.aoserv.client.TicketStatus.BOUNCED %>">
                                    <bean:message bundle="/clientarea/ticket/ApplicationResources" key="edit.addAnnotation.header"/>
                                </logic:notEqual>
                            </logic:notEqual>
                            <hr />
                            <table cellspacing="0" cellpadding="4">
                                <tr>
                                    <td style="white-space:nowrap"><bean:message bundle="/clientarea/ticket/ApplicationResources" key="TicketForm.field.annotationSummary.prompt"/></td>
                                    <td><html:text property="annotationSummary" size="60"/></td>
                                    <td><html:errors bundle="/clientarea/ticket/ApplicationResources" property="annotationSummary"/></td>
                                </tr>
                                <tr>
                                    <td style='white-space:nowrap' colspan="3">
                                        <br />
                                        <bean:message bundle="/clientarea/ticket/ApplicationResources" key="TicketForm.field.annotationDetails.prompt"/><br />
                                        <html:textarea property="annotationDetails" cols="80" rows="20"/><br />
                                        <html:errors bundle="/clientarea/ticket/ApplicationResources" property="annotationDetails"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="3" align="center">
                                        <br />
                                        <html:submit>
                                            <logic:equal name="ticket" property="status.status" value="<%= com.aoindustries.aoserv.client.TicketStatus.CLOSED %>">
                                                <bean:message bundle="/clientarea/ticket/ApplicationResources" key="edit.field.submitAnnotation.label.reopen"/>
                                            </logic:equal>
                                            <logic:equal name="ticket" property="status.status" value="<%= com.aoindustries.aoserv.client.TicketStatus.BOUNCED %>">
                                                <bean:message bundle="/clientarea/ticket/ApplicationResources" key="edit.field.submitAnnotation.label.replyTicket"/>
                                            </logic:equal>
                                            <logic:notEqual name="ticket" property="status.status" value="<%= com.aoindustries.aoserv.client.TicketStatus.CLOSED %>">
                                                <logic:notEqual name="ticket" property="status.status" value="<%= com.aoindustries.aoserv.client.TicketStatus.BOUNCED %>">
                                                    <bean:message bundle="/clientarea/ticket/ApplicationResources" key="edit.field.submitAnnotation.label"/>
                                                </logic:notEqual>
                                            </logic:notEqual>
                                        </html:submit>
                                    </td>
                                </tr>
                            </table>
                        </skin:lightArea>
                    </html:form>
                </logic:notPresent>
            </skin:contentLine>
        </skin:content>
    </skin:skin>
</html:html>
