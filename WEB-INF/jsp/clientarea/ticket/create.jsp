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
    <skin:path>/clientarea/ticket/create.do</skin:path>
    <skin:title><bean:message bundle="/clientarea/ticket/ApplicationResources" key="create.title"/></skin:title>
    <skin:navImageAlt><bean:message bundle="/clientarea/ticket/ApplicationResources" key="create.navImageAlt"/></skin:navImageAlt>
    <skin:keywords><bean:message bundle="/clientarea/ticket/ApplicationResources" key="create.keywords"/></skin:keywords>
    <skin:description><bean:message bundle="/clientarea/ticket/ApplicationResources" key="create.description"/></skin:description>
    <jsp:include page="add-parents.jsp"/>
    <jsp:include page="add-siblings.jsp"/>
    <skin:skin onLoad="document.forms['ticketForm'].summary.select(); document.forms['ticketForm'].summary.focus();">
        <skin:content>
            <skin:contentTitle><bean:message bundle="/clientarea/ticket/ApplicationResources" key="create.title"/></skin:contentTitle>
            <skin:contentHorizontalDivider/>
            <skin:contentLine>
                <logic:present scope="request" name="permissionDenied">
                    <%@ include file="../../permission-denied.jsp" %>
                </logic:present>
                <logic:notPresent scope="request" name="permissionDenied">
                    <html:javascript staticJavascript='false' bundle="/clientarea/ticket/ApplicationResources" formName="ticketForm"/>
                    <html:form action="/create-completed" onsubmit="return validateTicketForm(this);">
                        <skin:lightArea>
                            <table border="0" cellspacing="0" cellpadding="4">
                                <tr>
                                    <td nowrap><bean:message bundle="/clientarea/ticket/ApplicationResources" key="TicketForm.field.accounting.prompt"/></td>
                                    <td>
                                        <logic:notEqual name="aoConn" property="businesses.size" value="1">
                                            <html:select property="accounting">
                                                <%--<html:option value=""/>--%>
                                                <html:optionsCollection name="aoConn" property="businesses.rows" label="accounting" value="accounting"/>
                                            </html:select>
                                        </logic:notEqual>
                                        <logic:equal name="aoConn" property="businesses.size" value="1">
                                            <html:hidden property="accounting" write="true"/>
                                        </logic:equal>
                                    </td>
                                    <td><html:errors bundle="/clientarea/ticket/ApplicationResources" property="accounting"/></td>
                                </tr>
                                <tr>
                                    <td nowrap><bean:message bundle="/clientarea/ticket/ApplicationResources" key="TicketForm.field.contactEmails.prompt"/></td>
                                    <td><html:textarea property="contactEmails" cols="40" rows="3"/></td>
                                    <td><html:errors bundle="/clientarea/ticket/ApplicationResources" property="contactEmails"/></td>
                                </tr>
                                <tr>
                                    <td nowrap><bean:message bundle="/clientarea/ticket/ApplicationResources" key="TicketForm.field.contactPhoneNumbers.prompt"/></td>
                                    <td><html:textarea property="contactPhoneNumbers" cols="40" rows="3"/></td>
                                    <td><html:errors bundle="/clientarea/ticket/ApplicationResources" property="contactPhoneNumbers"/></td>
                                </tr>
                                <tr>
                                    <td nowrap><bean:message bundle="/clientarea/ticket/ApplicationResources" key="TicketForm.field.clientPriority.prompt"/></td>
                                    <td>
                                        <html:select property="clientPriority">
                                            <html:optionsCollection name="aoConn" property="ticketPriorities.rows" label="priority" value="priority"/>
                                        </html:select>
                                    </td>
                                    <td><html:errors bundle="/clientarea/ticket/ApplicationResources" property="clientPriority"/></td>
                                </tr>
                                <tr>
                                    <td nowrap><bean:message bundle="/clientarea/ticket/ApplicationResources" key="TicketForm.field.summary.prompt"/></td>
                                    <td><html:text property="summary" size="60"/></td>
                                    <td><html:errors bundle="/clientarea/ticket/ApplicationResources" property="summary"/></td>
                                </tr>
                                <tr>
                                    <td nowrap colspan="3">
                                        <br>
                                        <bean:message bundle="/clientarea/ticket/ApplicationResources" key="TicketForm.field.details.prompt"/><br>
                                        <textarea name="details" cols="80" rows="20" wrap="hard"><bean:write scope="request" name="ticketForm" property="details"/></textarea><br>
                                        <html:errors bundle="/clientarea/ticket/ApplicationResources" property="details"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="3" align="center">
                                        <br>
                                        <html:submit><bean:message bundle="/clientarea/ticket/ApplicationResources" key="create.field.submit.label"/></html:submit>
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
