<%-- aoweb-struts: Do not edit --%>
<%--
  Copyright 2007-2009 by AO Industries, Inc.,
  7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" buffer="256kb" autoFlush="true" %>
<%@ include file="/WEB-INF/jsp/taglibs.jsp" %>

<skin:lightArea>
    <table cellpadding="0" cellspacing="0">
        <tr><td style='white-space:nowrap' colspan="3">
            <bean:define scope="request" name="statusKey" id="statusKey" type="java.lang.String"/>
            <bean:define scope="request" name="pkey" id="pkey" type="java.lang.String"/>
            <bean:message bundle="/signup/ApplicationResources" key="<%= statusKey %>" arg0="<%= pkey %>"/><br />
            <br />
            <logic:iterate scope="request" name="successAddresses" id="successAddress" type="java.lang.String">
                <bean:message bundle="/signup/ApplicationResources" key="serverConfirmationCompleted.successAddress" arg0="<%= successAddress %>"/><br />
            </logic:iterate>
            <logic:iterate scope="request" name="failureAddresses" id="failureAddress" type="java.lang.String">
                <bean:message bundle="/signup/ApplicationResources" key="serverConfirmationCompleted.failureAddress" arg0="<%= failureAddress %>"/><br />
            </logic:iterate>
            <br />
            <bean:message bundle="/signup/ApplicationResources" key="serverConfirmationCompleted.belowIsSummary"/><br />
            <hr />
        </td></tr>
        <tr><th colspan="3"><bean:message bundle="/signup/ApplicationResources" key="steps.selectServer.label"/></th></tr>
        <%@ include file="signup-select-server-confirmation.jsp" %>
        <tr><td colspan="3">&nbsp;</td></tr>
        <tr><th colspan="3"><bean:message bundle="/signup/ApplicationResources" key="steps.customizeServer.label"/></th></tr>
        <%@ include file="signup-customize-server-confirmation.jsp" %>
        <tr><td colspan="3">&nbsp;</td></tr>
        <tr><th colspan="3"><bean:message bundle="/signup/ApplicationResources" key="steps.customizeManagement.label"/></th></tr>
        <%@ include file="signup-customize-management-confirmation.jsp" %>
        <tr><td colspan="3">&nbsp;</td></tr>
        <tr><th colspan="3"><bean:message bundle="/signup/ApplicationResources" key="steps.businessInfo.label"/></th></tr>
        <%@ include file="signup-business-confirmation.jsp" %>
        <tr><td colspan="3">&nbsp;</td></tr>
        <tr><th colspan="3"><bean:message bundle="/signup/ApplicationResources" key="steps.technicalInfo.label"/></th></tr>
        <%@ include file="signup-technical-confirmation.jsp" %>
        <tr><td colspan="3">&nbsp;</td></tr>
        <tr><th colspan="3"><bean:message bundle="/signup/ApplicationResources" key="steps.billingInformation.label"/></th></tr>
        <%@ include file="signup-billing-information-confirmation.jsp" %>
    </table>
</skin:lightArea>
