<%-- aoweb-struts: Do not edit --%>
<%--
  Copyright 2007-2009 by AO Industries, Inc.,
  7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" buffer="256kb" autoFlush="true" %>
<%@ include file="/WEB-INF/jsp/taglibs.jsp" %>

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
        <%@ include file="signup-select-server-confirmation.jsp" %>
        <TR><TD colspan="3">&nbsp;</TD></TR>
        <TR>
            <TH colspan="3">
                <TABLE width="100%" border="0" cellspacing="0" cellpadding="0">
                    <TR>
                        <TH><bean:message bundle="/signup/ApplicationResources" key="steps.customizeServer.label"/></TH>
                        <TD align='right'><html:link styleClass="ao_light_link" action='<%= "/" + myActionPrefix +"-2" %>'><bean:message bundle="/signup/ApplicationResources" key="serverConfirmation.edit.link"/></html:link></TD>
                    </TR>
                </TABLE>
            </TH>
        </TR>
        <%@ include file="signup-customize-server-confirmation.jsp" %>
        <TR><TD colspan="3">&nbsp;</TD></TR>
        <TR>
            <TH colspan="3">
                <TABLE width="100%" border="0" cellspacing="0" cellpadding="0">
                    <TR>
                        <TH><bean:message bundle="/signup/ApplicationResources" key="steps.businessInfo.label"/></TH>
                        <TD align='right'><html:link styleClass="ao_light_link" action='<%= "/" + myActionPrefix +"-3" %>'><bean:message bundle="/signup/ApplicationResources" key="serverConfirmation.edit.link"/></html:link></TD>
                    </TR>
                </TABLE>
            </TH>
        </TR>
        <%@ include file="signup-business-confirmation.jsp" %>
        <TR><TD colspan="3">&nbsp;</TD></TR>
        <TR>
            <TH colspan="3">
                <TABLE width="100%" border="0" cellspacing="0" cellpadding="0">
                    <TR>
                        <TH><bean:message bundle="/signup/ApplicationResources" key="steps.technicalInfo.label"/></TH>
                        <TD align='right'><html:link styleClass="ao_light_link" action='<%= "/" + myActionPrefix +"-4" %>'><bean:message bundle="/signup/ApplicationResources" key="serverConfirmation.edit.link"/></html:link></TD>
                    </TR>
                </TABLE>
            </TH>
        </TR>
        <%@ include file="signup-technical-confirmation.jsp" %>
        <TR><TD colspan="3">&nbsp;</TD></TR>
        <TR>
            <TH colspan="3">
                <TABLE width="100%" border="0" cellspacing="0" cellpadding="0">
                    <TR>
                        <TH><bean:message bundle="/signup/ApplicationResources" key="steps.billingInformation.label"/></TH>
                        <TD align='right'><html:link styleClass="ao_light_link" action='<%= "/" + myActionPrefix +"-5" %>'><bean:message bundle="/signup/ApplicationResources" key="serverConfirmation.edit.link"/></html:link></TD>
                    </TR>
                </TABLE>
            </TH>
        </TR>
        <%@ include file="signup-billing-information-confirmation.jsp" %>
        <TR><TD colspan="3" align="center"><br><html:submit><bean:message bundle="/signup/ApplicationResources" key="serverConfirmation.submit.label"/></html:submit><br><br></TD></TR>
    </TABLE>
</skin:lightArea>
