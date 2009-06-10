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
    <skin:path>/contact.do</skin:path>
    <skin:title><bean:message bundle="/ApplicationResources" key="contact.title"/></skin:title>
    <skin:navImageAlt><bean:message bundle="/ApplicationResources" key="contact.navImageAlt"/></skin:navImageAlt>
    <skin:keywords><bean:message bundle="/ApplicationResources" key="contact.keywords"/></skin:keywords>
    <skin:description><bean:message bundle="/ApplicationResources" key="contact.description"/></skin:description>
    <aoweb:exists path="/WEB-INF/jsp/add-parents.jsp">
        <jsp:include page="/WEB-INF/jsp/add-parents.jsp"/>
    </aoweb:exists>
    <aoweb:exists path="/WEB-INF/jsp/add-siblings.jsp">
        <jsp:include page="/WEB-INF/jsp/add-siblings.jsp"/>
    </aoweb:exists>
    <skin:skin onLoad="document.forms['contactForm'].from.select(); document.forms['contactForm'].form.focus();">
        <skin:content colspans="3" width="600">
            <skin:contentTitle><bean:message bundle="/ApplicationResources" key="contact.title"/></skin:contentTitle>
            <skin:contentHorizontalDivider colspansAndDirections="1,down,1"/>
            <skin:contentLine>
                <bean:message bundle="/ApplicationResources" key="contact.text.mainDescription"/>
                <skin:contentVerticalDivider/>
                <skin:lightArea>
                    <TABLE border=0 cellspacing=0 cellpadding=2>
                        <TR>
                            <TH nowrap colspan=2><bean:message bundle="/ApplicationResources" key="contact.header.dayPhone"/></TH>
                        </TR>
                        <logic:notEmpty name="siteSettings" property="brand.supportTollFree">
                            <TR>
                                <TD nowrap><bean:message bundle="/ApplicationResources" key="contact.label.tollfree"/></TD>
                                <TD nowrap align="right"><CODE><bean:write name="siteSettings" property="brand.supportTollFree"/></CODE></TD>
                            </TR>
                        </logic:notEmpty>
                        <logic:notEmpty name="siteSettings" property="brand.supportDayPhone">
                            <TR>
                                <TD nowrap><bean:message bundle="/ApplicationResources" key="contact.label.direct"/></TD>
                                <TD nowrap align="right"><CODE><bean:write name="siteSettings" property="brand.supportDayPhone"/></CODE></TD>
                            </TR>
                        </logic:notEmpty>
                        <TR>
                            <TH nowrap colspan=2><bean:message bundle="/ApplicationResources" key="contact.header.emergencyPhone"/></TH>
                        </TR>
                        <logic:notEmpty name="siteSettings" property="brand.supportEmergencyPhone1">
                            <TR>
                                <TD nowrap><bean:message bundle="/ApplicationResources" key="contact.label.primary"/></TD>
                                <TD nowrap align="right"><CODE><bean:write name="siteSettings" property="brand.supportEmergencyPhone1"/></CODE></TD>
                            </TR>
                        </logic:notEmpty>
                        <logic:notEmpty name="siteSettings" property="brand.supportEmergencyPhone2">
                            <TR>
                                <TD nowrap><bean:message bundle="/ApplicationResources" key="contact.label.secondary"/></TD>
                                <TD nowrap align="right"><CODE><bean:write name="siteSettings" property="brand.supportEmergencyPhone2"/></CODE></TD>
                            </TR>
                        </logic:notEmpty>
                        <TR>
                            <TH nowrap colspan=2><bean:message bundle="/ApplicationResources" key="contact.header.email"/></TH>
                        </TR>
                        <TR>
                            <TD nowrap colspan=2>
                                <bean:define id="supportEmail" type="com.aoindustries.aoserv.client.EmailAddress" name="siteSettings" property="brand.supportEmailAddress"/>
                                <html:link styleClass="ao_dark_link" href='<%= "mailto:"+supportEmail %>'>
                                    <CODE><bean:write name="siteSettings" property="brand.supportEmailAddress"/></CODE>
                                </html:link>
                            </TD>
                        </TR>
                        <logic:notEmpty name="siteSettings" property="brand.supportFax">
                            <TR>
                                <TH nowrap colspan=2><bean:message bundle="/ApplicationResources" key="contact.header.fax"/></TH>
                            </TR>
                            <TR>
                                <TD nowrap colspan=2><CODE><bean:write name="siteSettings" property="brand.supportFax"/></CODE></TD>
                            </TR>
                        </logic:notEmpty>
                        <TR>
                            <TH nowrap colspan=2><bean:message bundle="/ApplicationResources" key="contact.header.mailingAddress"/></TH>
                        </TR>
                        <logic:notEmpty name="siteSettings" property="brand.supportMailingAddress1">
                            <TR>
                                <TD nowrap colspan=2><CODE><bean:write name="siteSettings" property="brand.supportMailingAddress1"/></CODE></TD>
                            </TR>
                        </logic:notEmpty>
                        <logic:notEmpty name="siteSettings" property="brand.supportMailingAddress2">
                            <TR>
                                <TD nowrap colspan=2><CODE><bean:write name="siteSettings" property="brand.supportMailingAddress2"/></CODE></TD>
                            </TR>
                        </logic:notEmpty>
                        <logic:notEmpty name="siteSettings" property="brand.supportMailingAddress3">
                            <TR>
                                <TD nowrap colspan=2><CODE><bean:write name="siteSettings" property="brand.supportMailingAddress3"/></CODE></TD>
                            </TR>
                        </logic:notEmpty>
                        <logic:notEmpty name="siteSettings" property="brand.supportMailingAddress4">
                            <TR>
                                <TD nowrap colspan=2><CODE><bean:write name="siteSettings" property="brand.supportMailingAddress4"/></CODE></TD>
                            </TR>
                        </logic:notEmpty>
                    </TABLE>
                </skin:lightArea>
            </skin:contentLine>
            <skin:contentHorizontalDivider colspansAndDirections="1,up,1"/>
            <skin:contentLine colspan="3">
                <bean:message bundle="/ApplicationResources" key="contact.text.formWelcome"/>
                <skin:lightArea>
                    <html:javascript staticJavascript='false' bundle="/ApplicationResources" formName="contactForm"/>
                    <html:form action="/contact-completed" onsubmit="return validateContactForm(this);">
                        <table border=0 cellspacing=0 cellpadding=0>
                            <tr>
                                <td nowrap><bean:message bundle="/ApplicationResources" key="contact.field.from.prompt"/></td>
                                <td><html:text size="30" property="from" /></td>
                                <td><html:errors bundle="/ApplicationResources" property="from"/></td>
                            </tr>
                            <tr>
                                <td nowrap><bean:message bundle="/ApplicationResources" key="contact.field.subject.prompt"/></td>
                                <td><html:text size="45" property="subject" /></td>
                                <td><html:errors bundle="/ApplicationResources" property="subject"/></td>
                            </tr>
                            <tr><td colspan=2>&nbsp;</td></tr>
                            <tr><td colspan=2><bean:message bundle="/ApplicationResources" key="contact.field.message.prompt"/></td></tr>
                            <tr><td colspan=2><textarea name="message" cols="60" rows="12" wrap="hard"><bean:write scope="request" name="contactForm" property="message"/></textarea></td></tr>
                            <tr>
                                <td colspan=2 align="center">
                                    <br>
                                    <html:submit><bean:message bundle="/ApplicationResources" key="contact.field.submit.label"/></html:submit>
                                </td>
                            </tr>
                        </table>
                    </html:form>
                </skin:lightArea>
            </skin:contentLine>
        </skin:content>
    </skin:skin>
</html:html>
