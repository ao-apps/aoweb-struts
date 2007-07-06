<%--
  Copyright 2000-2007 by AO Industries, Inc.,
  816 Azalea Rd, Mobile, Alabama, 36693, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" %>
<%@ page buffer="256kb" %>
<%@ page autoFlush="false" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld"       prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld"       prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld"      prefix="logic" %>
<%@ taglib uri="/WEB-INF/aoweb-struts-skin.tld" prefix="skin" %>
<skin:setContentType/>
<html:html lang="true">
    <skin:path>/clientarea/control/password/business-administrator-password-setter.do</skin:path>
    <skin:title><bean:message bundle="/clientarea/control/ApplicationResources" key="password.businessAdministratorPasswordSetter.title"/></skin:title>
    <skin:navImageAlt><bean:message bundle="/clientarea/control/ApplicationResources" key="password.businessAdministratorPasswordSetter.navImageAlt"/></skin:navImageAlt>
    <skin:keywords><bean:message bundle="/clientarea/control/ApplicationResources" key="password.businessAdministratorPasswordSetter.keywords"/></skin:keywords>
    <skin:description><bean:message bundle="/clientarea/control/ApplicationResources" key="password.businessAdministratorPasswordSetter.description"/></skin:description>
    <%@ include file="add-parents.jsp" %>
    <%@ include file="add-siblings.jsp" %>
    <skin:skin>
        <skin:content width="600">
            <skin:contentTitle><bean:message bundle="/clientarea/control/ApplicationResources" key="password.businessAdministratorPasswordSetter.title"/></skin:contentTitle>
            <skin:contentHorizontalDivider/>
            <skin:contentLine>
                <logic:empty scope="request" name="businessAdministratorPasswordSetterForm" property="packages">
                    <B><bean:message bundle="/clientarea/control/ApplicationResources" key="password.businessAdministratorPasswordSetter.noAccounts"/></B>
                </logic:empty>
                <logic:notEmpty scope="request" name="businessAdministratorPasswordSetterForm" property="packages">
                    <html:form action="/password/business-administrator-password-setter-completed">
                        <skin:lightArea>
                            <TABLE cellspacing='0' cellpadding='2' border='0'>
                                <TR>
                                    <bean:size scope="request" name="aoConn" property="packages" id="packagesSize"/>
                                    <logic:greaterThan name="packagesSize" value="1">
                                        <TH><bean:message bundle="/clientarea/control/ApplicationResources" key="password.businessAdministratorPasswordSetter.header.package"/></TH>
                                    </logic:greaterThan>
                                    <TH><bean:message bundle="/clientarea/control/ApplicationResources" key="password.businessAdministratorPasswordSetter.header.username"/></TH>
                                    <TH colspan='2'><bean:message bundle="/clientarea/control/ApplicationResources" key="password.businessAdministratorPasswordSetter.header.newPassword"/></TH>
                                    <TH><bean:message bundle="/clientarea/control/ApplicationResources" key="password.businessAdministratorPasswordSetter.header.confirmPassword"/></TH>
                                    <TH>&nbsp;</TH>
                                </TR>
                                <logic:iterate scope="request" name="businessAdministratorPasswordSetterForm" property="packages" id="pack" indexId="index">
                                    <TR>
                                        <html:hidden property='<%= "packages[" + index + "]" %>'/>
                                        <logic:greaterThan name="packagesSize" value="1">
                                            <TD><bean:write name="pack" filter="true"/></TD>
                                        </logic:greaterThan>
                                        <TD><CODE><html:hidden property='<%= "usernames[" + index + "]" %>' write="true" /></CODE></TD>
                                        <TD><html:password size="20" property='<%= "newPasswords[" + index + "]" %>' /></TD>
                                        <TD nowrap>
                                            <html:errors bundle="/clientarea/control/ApplicationResources" property='<%= "newPasswords[" + index + "].newPasswords" %>'/>
                                            <html:messages id="message" message="true" bundle="/clientarea/control/ApplicationResources" property='<%= "newPasswords[" + index + "].newPasswords" %>'>
                                                <bean:write name="message"/><br>
                                            </html:messages>
                                        </TD>
                                        <TD><html:password size="20" property='<%= "confirmPasswords[" + index + "]" %>' /></TD>
                                        <TD nowrap>
                                            <html:errors bundle="/clientarea/control/ApplicationResources" property='<%= "confirmPasswords[" + index + "].confirmPasswords" %>'/>
                                            <html:messages id="message" message="true" bundle="/clientarea/control/ApplicationResources" property='<%= "confirmPasswords[" + index + "].confirmPasswords" %>'>
                                                <bean:write name="message"/><br>
                                            </html:messages>
                                        </TD>
                                    </TR>
                                </logic:iterate>
                                <TR><TD colspan='6' align='center'><html:submit><bean:message bundle="/clientarea/control/ApplicationResources" key="password.businessAdministratorPasswordSetter.field.submit.label"/></html:submit></TD></TR>
                            </TABLE>
                        </skin:lightArea>
                    </html:form>
                </logic:notEmpty>
            </skin:contentLine>
        </skin:content>
    </skin:skin>
</html:html>
