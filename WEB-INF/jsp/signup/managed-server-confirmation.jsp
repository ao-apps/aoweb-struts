<%-- aoweb-struts: Do not edit --%>
<%--
  Copyright 2007-2009 by AO Industries, Inc.,
  7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" buffer="256kb" autoFlush="true" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/taglibs.jsp" %>

<skin:lightArea>
    <bean:define name="actionPrefix" id="myActionPrefix" type="java.lang.String"/>
    <table cellpadding="0" cellspacing="0">
        <tr><td COLSPAN="3"><b><bean:message bundle="/signup/ApplicationResources" key="serverConfirmation.stepLabel"/></b><br /><hr /></td></tr>
        <tr><td COLSPAN="3"><bean:message bundle="/signup/ApplicationResources" key="serverConfirmation.stepHelp"/></td></tr>
        <tr><td colspan="3">&#160;</td></tr>
        <tr>
            <th colspan="3">
                <table width="100%" cellspacing="0" cellpadding="0">
                    <tr>
                        <th><bean:message bundle="/signup/ApplicationResources" key="steps.selectServer.label"/></th>
                        <td align='right'><html:link styleClass="aoLightLink" action='<%= "/" + myActionPrefix %>'><bean:message bundle="/signup/ApplicationResources" key="serverConfirmation.edit.link"/></html:link></td>
                    </tr>
                </table>
            </th>
        </tr>
        <%@ include file="signup-select-server-confirmation.jsp" %>
        <tr><td colspan="3">&#160;</td></tr>
        <tr>
            <th colspan="3">
                <table width="100%" cellspacing="0" cellpadding="0">
                    <tr>
                        <th><bean:message bundle="/signup/ApplicationResources" key="steps.customizeServer.label"/></th>
                        <td align='right'><html:link styleClass="aoLightLink" action='<%= "/" + myActionPrefix +"-2" %>'><bean:message bundle="/signup/ApplicationResources" key="serverConfirmation.edit.link"/></html:link></td>
                    </tr>
                </table>
            </th>
        </tr>
        <%@ include file="signup-customize-server-confirmation.jsp" %>
        <tr><td colspan="3">&#160;</td></tr>
        <tr>
            <th colspan="3">
                <table width="100%" cellspacing="0" cellpadding="0">
                    <tr>
                        <th><bean:message bundle="/signup/ApplicationResources" key="steps.customizeManagement.label"/></th>
                        <td align='right'><html:link styleClass="aoLightLink" action='<%= "/" + myActionPrefix +"-3" %>'><bean:message bundle="/signup/ApplicationResources" key="serverConfirmation.edit.link"/></html:link></td>
                    </tr>
                </table>
            </th>
        </tr>
        <%@ include file="signup-customize-management-confirmation.jsp" %>
        <tr><td colspan="3">&#160;</td></tr>
        <tr>
            <th colspan="3">
                <table width="100%" cellspacing="0" cellpadding="0">
                    <tr>
                        <th><bean:message bundle="/signup/ApplicationResources" key="steps.businessInfo.label"/></th>
                        <td align='right'><html:link styleClass="aoLightLink" action='<%= "/" + myActionPrefix +"-4" %>'><bean:message bundle="/signup/ApplicationResources" key="serverConfirmation.edit.link"/></html:link></td>
                    </tr>
                </table>
            </th>
        </tr>
        <%@ include file="signup-business-confirmation.jsp" %>
        <tr><td colspan="3">&#160;</td></tr>
        <tr>
            <th colspan="3">
                <table width="100%" cellspacing="0" cellpadding="0">
                    <tr>
                        <th><bean:message bundle="/signup/ApplicationResources" key="steps.technicalInfo.label"/></th>
                        <td align='right'><html:link styleClass="aoLightLink" action='<%= "/" + myActionPrefix +"-5" %>'><bean:message bundle="/signup/ApplicationResources" key="serverConfirmation.edit.link"/></html:link></td>
                    </tr>
                </table>
            </th>
        </tr>
        <%@ include file="signup-technical-confirmation.jsp" %>
        <tr><td colspan="3">&#160;</td></tr>
        <tr>
            <th colspan="3">
                <table width="100%" cellspacing="0" cellpadding="0">
                    <tr>
                        <th><bean:message bundle="/signup/ApplicationResources" key="steps.billingInformation.label"/></th>
                        <td align='right'><html:link styleClass="aoLightLink" action='<%= "/" + myActionPrefix +"-6" %>'><bean:message bundle="/signup/ApplicationResources" key="serverConfirmation.edit.link"/></html:link></td>
                    </tr>
                </table>
            </th>
        </tr>
        <%@ include file="signup-billing-information-confirmation.jsp" %>
        <tr><td colspan="3" align="center"><br /><html:submit><bean:message bundle="/signup/ApplicationResources" key="serverConfirmation.submit.label"/></html:submit><br /><br /></td></tr>
    </table>
</skin:lightArea>
