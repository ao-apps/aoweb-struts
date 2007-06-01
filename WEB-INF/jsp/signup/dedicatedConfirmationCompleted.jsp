<%--
  Copyright 2007 by AO Industries, Inc.,
  816 Azalea Rd, Mobile, Alabama, 36693, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld"       prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld"       prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld"      prefix="logic" %>
<%@ taglib uri="/WEB-INF/aoweb-struts-skin.tld" prefix="skin" %>

<skin:lightArea>
    <TABLE BORDER="0" CELLPADDING="0" CELLSPACING="0">
        <TR><TD nowrap colspan="3">
            <bean:define scope="request" name="statusKey" id="statusKey" type="java.lang.String"/>
            <bean:define scope="request" name="pkey" id="pkey" type="java.lang.String"/>
            <bean:message bundle="/signup/ApplicationResources" key="<%= statusKey %>" arg0="<%= pkey %>"/><BR>
            <BR>
            <logic:iterate scope="request" name="successAddresses" id="successAddress" type="java.lang.String">
                <bean:message bundle="/signup/ApplicationResources" key="serverConfirmationCompleted.successAddress" arg0="<%= successAddress %>"/><BR>
            </logic:iterate>
            <logic:iterate scope="request" name="failureAddresses" id="failureAddress" type="java.lang.String">
                <bean:message bundle="/signup/ApplicationResources" key="serverConfirmationCompleted.failureAddress" arg0="<%= failureAddress %>"/><BR>
            </logic:iterate>
            <BR>
            <bean:message bundle="/signup/ApplicationResources" key="serverConfirmationCompleted.belowIsSummary"/><BR>
            <HR>
        </TD></TR>
        <TR><TH colspan="3"><bean:message bundle="/signup/ApplicationResources" key="steps.selectServer.label"/></TH></TR>
        <%@ include file="signupSelectServerConfirmation.jsp" %>
        <TR><TD colspan="3">&nbsp;</TD></TR>
        <TR><TH colspan="3"><bean:message bundle="/signup/ApplicationResources" key="steps.customizeServer.label"/></TH></TR>
        <%@ include file="signupCustomizeServerConfirmation.jsp" %>
        <TR><TD colspan="3">&nbsp;</TD></TR>
        <TR><TH colspan="3"><bean:message bundle="/signup/ApplicationResources" key="steps.businessInfo.label"/></TH></TR>
        <%@ include file="signupBusinessConfirmation.jsp" %>
        <TR><TD colspan="3">&nbsp;</TD></TR>
        <TR><TH colspan="3"><bean:message bundle="/signup/ApplicationResources" key="steps.technicalInfo.label"/></TH></TR>
        <%@ include file="signupTechnicalConfirmation.jsp" %>
        <TR><TD colspan="3">&nbsp;</TD></TR>
        <TR><TH colspan="3"><bean:message bundle="/signup/ApplicationResources" key="steps.billingInformation.label"/></TH></TR>
        <%@ include file="signupBillingInformationConfirmation.jsp" %>
    </TABLE>
</skin:lightArea>
