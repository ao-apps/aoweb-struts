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
    <bean:define name="actionPrefix" id="myActionPrefix" type="java.lang.String"/>
    <TABLE BORDER="0" CELLPADDING="0" CELLSPACING="0">
        <TR><TD COLSPAN="3"><B><bean:message bundle="/signup/ApplicationResources" key="serverConfirmation.stepLabel"/></B><BR><HR></TD></TR>
        <TR><TD COLSPAN="3"><bean:message bundle="/signup/ApplicationResources" key="serverConfirmation.stepHelp"/></TD></TR>
        <TR><TD colspan="3">&nbsp;</TD></TR>
        <TR>
            <TH colspan="3">
                <TABLE width="100%" border="0" cellspacing="0" cellpadding="0">
                    <TR>
                        <TH><bean:message bundle="/signup/ApplicationResources" key="steps.selectServer.label"/></TH>
                        <TD align='right'><html:link styleClass="ao_light_link" action='<%= "/" + myActionPrefix %>'><bean:message bundle="/signup/ApplicationResources" key="serverConfirmation.edit.link"/></html:link></TD>
                    </TR>
                </TABLE>
            </TH>
        </TR>
        <%@ include file="signupSelectServerConfirmation.jsp" %>
        <TR><TD colspan="3">&nbsp;</TD></TR>
        <TR>
            <TH colspan="3">
                <TABLE width="100%" border="0" cellspacing="0" cellpadding="0">
                    <TR>
                        <TH><bean:message bundle="/signup/ApplicationResources" key="steps.customizeServer.label"/></TH>
                        <TD align='right'><html:link styleClass="ao_light_link" action='<%= "/" + myActionPrefix +"2" %>'><bean:message bundle="/signup/ApplicationResources" key="serverConfirmation.edit.link"/></html:link></TD>
                    </TR>
                </TABLE>
            </TH>
        </TR>
        <%@ include file="signupCustomizeServerConfirmation.jsp" %>
        <TR><TD colspan="3">&nbsp;</TD></TR>
        <TR>
            <TH colspan="3">
                <TABLE width="100%" border="0" cellspacing="0" cellpadding="0">
                    <TR>
                        <TH><bean:message bundle="/signup/ApplicationResources" key="steps.customizeManagement.label"/></TH>
                        <TD align='right'><html:link styleClass="ao_light_link" action='<%= "/" + myActionPrefix +"3" %>'><bean:message bundle="/signup/ApplicationResources" key="serverConfirmation.edit.link"/></html:link></TD>
                    </TR>
                </TABLE>
            </TH>
        </TR>
        <%@ include file="signupCustomizeManagementConfirmation.jsp" %>
        <TR><TD colspan="3">&nbsp;</TD></TR>
        <TR>
            <TH colspan="3">
                <TABLE width="100%" border="0" cellspacing="0" cellpadding="0">
                    <TR>
                        <TH><bean:message bundle="/signup/ApplicationResources" key="steps.businessInfo.label"/></TH>
                        <TD align='right'><html:link styleClass="ao_light_link" action='<%= "/" + myActionPrefix +"4" %>'><bean:message bundle="/signup/ApplicationResources" key="serverConfirmation.edit.link"/></html:link></TD>
                    </TR>
                </TABLE>
            </TH>
        </TR>
        <%@ include file="signupBusinessConfirmation.jsp" %>
        <TR><TD colspan="3">&nbsp;</TD></TR>
        <TR>
            <TH colspan="3">
                <TABLE width="100%" border="0" cellspacing="0" cellpadding="0">
                    <TR>
                        <TH><bean:message bundle="/signup/ApplicationResources" key="steps.technicalInfo.label"/></TH>
                        <TD align='right'><html:link styleClass="ao_light_link" action='<%= "/" + myActionPrefix +"5" %>'><bean:message bundle="/signup/ApplicationResources" key="serverConfirmation.edit.link"/></html:link></TD>
                    </TR>
                </TABLE>
            </TH>
        </TR>
        <%@ include file="signupTechnicalConfirmation.jsp" %>
        <TR><TD colspan="3">&nbsp;</TD></TR>
        <TR>
            <TH colspan="3">
                <TABLE width="100%" border="0" cellspacing="0" cellpadding="0">
                    <TR>
                        <TH><bean:message bundle="/signup/ApplicationResources" key="steps.billingInformation.label"/></TH>
                        <TD align='right'><html:link styleClass="ao_light_link" action='<%= "/" + myActionPrefix +"6" %>'><bean:message bundle="/signup/ApplicationResources" key="serverConfirmation.edit.link"/></html:link></TD>
                    </TR>
                </TABLE>
            </TH>
        </TR>
        <%@ include file="signupBillingInformationConfirmation.jsp" %>
        <TR><TD colspan="3" align="center"><br><html:submit styleClass='ao_button'><bean:message bundle="/signup/ApplicationResources" key="serverConfirmation.submit.label"/></html:submit><br><br></td></tr>
    </TABLE>
</skin:lightArea>
