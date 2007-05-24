<%--
  Copyright 2007 by AO Industries, Inc.,
  816 Azalea Rd, Mobile, Alabama, 36693, U.S.A.
  All rights reserved.
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld"       prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld"      prefix="logic" %>
<%@ taglib uri="/WEB-INF/aoweb-struts-skin.tld" prefix="skin" %>

<skin:lightArea>
    <B><bean:message bundle="/signup/ApplicationResources" key="steps.title"/></B>
    <HR>
    <TABLE border=0 cellspacing=2 cellpadding=0>
        <TR>
            <TD>
                <logic:equal scope="request" name="stepNumber" value="1"><bean:message bundle="/signup/ApplicationResources" key="steps.arrow"/></logic:equal>
                <logic:notEqual scope="request" name="stepNumber" value="1">&nbsp;</logic:notEqual>
            </TD>
            <TD><bean:message bundle="/signup/ApplicationResources" key="steps.1"/></TD>
            <TD><bean:message bundle="/signup/ApplicationResources" key="steps.selectServer.label"/></TD>
            <TD>
                <logic:equal scope="request" name="dedicatedSignupSelectServerFormComplete" value="true">
                    <bean:message bundle="/signup/ApplicationResources" key="steps.completed"/>
                </logic:equal>
                <logic:notEqual scope="request" name="dedicatedSignupSelectServerFormComplete" value="true">
                    <bean:message bundle="/signup/ApplicationResources" key="steps.incomplete"/>
                </logic:notEqual>
            </TD>
        </TR>
        <TR>
            <TD>
                <logic:equal scope="request" name="stepNumber" value="2"><bean:message bundle="/signup/ApplicationResources" key="steps.arrow"/></logic:equal>
                <logic:notEqual scope="request" name="stepNumber" value="2">&nbsp;</logic:notEqual>
            </TD>
            <TD><bean:message bundle="/signup/ApplicationResources" key="steps.2"/></TD>
            <TD><bean:message bundle="/signup/ApplicationResources" key="steps.customizeServer.label"/></TD>
            <TD>
                <logic:equal scope="request" name="dedicatedSignupCustomizeServerFormComplete" value="true">
                    <bean:message bundle="/signup/ApplicationResources" key="steps.completed"/>
                </logic:equal>
                <logic:notEqual scope="request" name="dedicatedSignupCustomizeServerFormComplete" value="true">
                    <bean:message bundle="/signup/ApplicationResources" key="steps.incomplete"/>
                </logic:notEqual>
            </TD>
        </TR>
        <TR>
            <TD>
                <logic:equal scope="request" name="stepNumber" value="3"><bean:message bundle="/signup/ApplicationResources" key="steps.arrow"/></logic:equal>
                <logic:notEqual scope="request" name="stepNumber" value="3">&nbsp;</logic:notEqual>
            </TD>
            <TD><bean:message bundle="/signup/ApplicationResources" key="steps.3"/></TD>
            <TD><bean:message bundle="/signup/ApplicationResources" key="steps.businessInfo.label"/></TD>
            <TD>
                <logic:equal scope="request" name="signupBusinessFormComplete" value="true">
                    <bean:message bundle="/signup/ApplicationResources" key="steps.completed"/>
                </logic:equal>
                <logic:notEqual scope="request" name="signupBusinessFormComplete" value="true">
                    <bean:message bundle="/signup/ApplicationResources" key="steps.incomplete"/>
                </logic:notEqual>
            </TD>
        </TR>
        <TR>
            <TD>
                <logic:equal scope="request" name="stepNumber" value="4"><bean:message bundle="/signup/ApplicationResources" key="steps.arrow"/></logic:equal>
                <logic:notEqual scope="request" name="stepNumber" value="4">&nbsp;</logic:notEqual>
            </TD>
            <TD><bean:message bundle="/signup/ApplicationResources" key="steps.4"/></TD>
            <TD><bean:message bundle="/signup/ApplicationResources" key="steps.technicalInfo.label"/></TD>
            <TD>
                <logic:equal scope="request" name="signupTechnicalFormComplete" value="true">
                    <bean:message bundle="/signup/ApplicationResources" key="steps.completed"/>
                </logic:equal>
                <logic:notEqual scope="request" name="signupTechnicalFormComplete" value="true">
                    <bean:message bundle="/signup/ApplicationResources" key="steps.incomplete"/>
                </logic:notEqual>
            </TD>
        </TR>
        <TR>
            <TD>
                <logic:equal scope="request" name="stepNumber" value="5"><bean:message bundle="/signup/ApplicationResources" key="steps.arrow"/></logic:equal>
                <logic:notEqual scope="request" name="stepNumber" value="5">&nbsp;</logic:notEqual>
            </TD>
            <TD><bean:message bundle="/signup/ApplicationResources" key="steps.5"/></TD>
            <TD><bean:message bundle="/signup/ApplicationResources" key="steps.billingInformation.label"/></TD>
            <TD>
                <logic:equal scope="request" name="signupBillingInformationFormComplete" value="true">
                    <bean:message bundle="/signup/ApplicationResources" key="steps.completed"/>
                </logic:equal>
                <logic:notEqual scope="request" name="signupBillingInformationFormComplete" value="true">
                    <bean:message bundle="/signup/ApplicationResources" key="steps.incomplete"/>
                </logic:notEqual>
            </TD>
        </TR>
        <TR>
            <TD>
                <logic:equal scope="request" name="stepNumber" value="6"><bean:message bundle="/signup/ApplicationResources" key="steps.arrow"/></logic:equal>
                <logic:notEqual scope="request" name="stepNumber" value="6">&nbsp;</logic:notEqual>
            </TD>
            <TD><bean:message bundle="/signup/ApplicationResources" key="steps.6"/></TD>
            <TD><bean:message bundle="/signup/ApplicationResources" key="steps.confirmation.label"/></TD>
            <TD>
                <logic:equal scope="request" name="stepNumber" value="7">
                    <bean:message bundle="/signup/ApplicationResources" key="steps.completed"/>
                </logic:equal>
                <logic:notEqual scope="request" name="stepNumber" value="7">
                    <bean:message bundle="/signup/ApplicationResources" key="steps.incomplete"/>
                </logic:notEqual>
            </TD>
        </TR>
        <TR>
            <TD>
                <logic:equal scope="request" name="stepNumber" value="7"><bean:message bundle="/signup/ApplicationResources" key="steps.arrow"/></logic:equal>
                <logic:notEqual scope="request" name="stepNumber" value="7">&nbsp;</logic:notEqual>
            </TD>
            <TD><bean:message bundle="/signup/ApplicationResources" key="steps.7"/></TD>
            <TD><bean:message bundle="/signup/ApplicationResources" key="steps.finished.label"/></TD>
            <TD>&nbsp;</TD>
        </TR>
    </TABLE>
</skin:lightArea>
