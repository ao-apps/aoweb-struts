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
<skin:setContentType/>
<html:html lang="true">
    <skin:path>/signup/dedicated.do</skin:path>
    <skin:title><bean:message bundle="/signup/ApplicationResources" key="dedicated.title"/></skin:title>
    <skin:navImageAlt><bean:message bundle="/signup/ApplicationResources" key="dedicated.navImageAlt"/></skin:navImageAlt>
    <skin:keywords><bean:message bundle="/signup/ApplicationResources" key="dedicated.keywords"/></skin:keywords>
    <skin:description><bean:message bundle="/signup/ApplicationResources" key="dedicated.description"/></skin:description>
    <%@ include file="addParents.jsp" %>
    <%@ include file="addSiblings.jsp" %>
    <skin:skin onLoad="recalcMonthly();">
        <skin:content width="600">
            <skin:contentTitle><bean:message bundle="/signup/ApplicationResources" key="dedicated.title"/></skin:contentTitle>
            <skin:contentHorizontalDivider/>
            <skin:contentLine>
                <bean:define toScope="request" type="java.lang.String" id="stepNumber" value="7"/>
                <%@ include file="dedicatedSteps.jsp" %>
                <br>
                <skin:lightArea>
                     <TABLE BORDER="0" CELLPADDING="0" CELLSPACING="0">
                        <TR><TD nowrap colspan="3">
                            <bean:define scope="request" name="statusKey" id="statusKey" type="java.lang.String"/>
                            <bean:define scope="request" name="pkey" id="pkey" type="java.lang.String"/>
                            <bean:message bundle="/signup/ApplicationResources" key="<%= statusKey %>" arg0="<%= pkey %>"/><BR>
                            <BR>
                            <logic:iterate scope="request" name="successAddresses" id="successAddress" type="java.lang.String">
                                <bean:message bundle="/signup/ApplicationResources" key="dedicated6Completed.successAddress" arg0="<%= successAddress %>"/><BR>
                            </logic:iterate>
                            <logic:iterate scope="request" name="failureAddresses" id="failureAddress" type="java.lang.String">
                                <bean:message bundle="/signup/ApplicationResources" key="dedicated6Completed.failureAddress" arg0="<%= failureAddress %>"/><BR>
                            </logic:iterate>
                            <BR>
                            <bean:message bundle="/signup/ApplicationResources" key="dedicated6Completed.belowIsSummary"/><BR>
                            <HR>
                        </TD></TR>
                        <TR><TH colspan="3"><bean:message bundle="/signup/ApplicationResources" key="steps.selectServer.label"/></TH></TR>
                        <TR>
                            <TD><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired"/></TD>
                            <TD><bean:message bundle="/signup/ApplicationResources" key="dedicatedSignupSelectServerForm.packageDefinition.prompt"/></TD>
                            <TD><bean:write scope="request" name="packageDefinition" property="display"/></TD>
                        </TR>
                        <TR><TD colspan="3">&nbsp;</TD></TR>
                        <TR><TH colspan="3"><bean:message bundle="/signup/ApplicationResources" key="steps.customizeServer.label"/></TH></TR>
                        <logic:notEmpty scope="request" name="powerOption">
                            <TR>
                                <TD><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired"/></TD>
                                <TD><bean:message bundle="/signup/ApplicationResources" key="dedicated6.power.prompt"/></TD>
                                <TD><bean:write name="powerOption"/></TD>
                            </TR>
                        </logic:notEmpty>
                        <TR>
                            <TD><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired"/></TD>
                            <TD><bean:message bundle="/signup/ApplicationResources" key="dedicated6.cpu.prompt"/></TD>
                            <TD><bean:write name="cpuOption" filter="false"/></TD>
                        </TR>
                        <TR>
                            <TD><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired"/></TD>
                            <TD><bean:message bundle="/signup/ApplicationResources" key="dedicated6.ram.prompt"/></TD>
                            <TD><bean:write name="ramOption"/></TD>
                        </TR>
                        <logic:notEmpty scope="request" name="sataControllerOption">
                            <TR>
                                <TD><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired"/></TD>
                                <TD><bean:message bundle="/signup/ApplicationResources" key="dedicated6.sataController.prompt"/></TD>
                                <TD><bean:write name="sataControllerOption"/></TD>
                            </TR>
                        </logic:notEmpty>
                        <logic:notEmpty scope="request" name="scsiControllerOption">
                            <TR>
                                <TD><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired"/></TD>
                                <TD><bean:message bundle="/signup/ApplicationResources" key="dedicated6.scsiController.prompt"/></TD>
                                <TD><bean:write name="scsiControllerOption"/></TD>
                            </TR>
                        </logic:notEmpty>
                        <logic:iterate name="ideOptions" id="ideOption">
                            <TR>
                                <TD><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired"/></TD>
                                <TD><bean:message bundle="/signup/ApplicationResources" key="dedicated6.ide.prompt"/></TD>
                                <TD><bean:write name="ideOption"/></TD>
                            </TR>
                        </logic:iterate>
                        <logic:iterate name="sataOptions" id="sataOption">
                            <TR>
                                <TD><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired"/></TD>
                                <TD><bean:message bundle="/signup/ApplicationResources" key="dedicated6.sata.prompt"/></TD>
                                <TD><bean:write name="sataOption"/></TD>
                            </TR>
                        </logic:iterate>
                        <logic:iterate name="scsiOptions" id="scsiOption">
                            <TR>
                                <TD><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired"/></TD>
                                <TD><bean:message bundle="/signup/ApplicationResources" key="dedicated6.scsi.prompt"/></TD>
                                <TD><bean:write name="scsiOption"/></TD>
                            </TR>
                        </logic:iterate>
                        <TR>
                            <TD><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired"/></TD>
                            <TD><bean:message bundle="/signup/ApplicationResources" key="dedicated6.setup.prompt"/></TD>
                            <TD>
                                <logic:notPresent name="setup">
                                    <bean:message bundle="/signup/ApplicationResources" key="dedicated.setup.none"/>
                                </logic:notPresent>
                                <logic:present name="setup">
                                    $<bean:write name="setup"/>
                                </logic:present>
                            </TD>
                        </TR>
                        <TR>
                            <TD><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired"/></TD>
                            <TD><bean:message bundle="/signup/ApplicationResources" key="dedicated6.monthlyRate.prompt"/></TD>
                            <TD>$<bean:write name="monthlyRate"/></TD>
                        </TR>
                        <TR><TD colspan="3">&nbsp;</TD></TR>
                        <TR><TH colspan="3"><bean:message bundle="/signup/ApplicationResources" key="steps.businessInfo.label"/></TH></TR>
                        <TR>
                            <TD><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired"/></TD>
                            <TD><bean:message bundle="/signup/ApplicationResources" key="signupBusinessForm.businessName.prompt"/></TD>
                            <TD><bean:write scope="session" name="signupBusinessForm" property="businessName"/></TD>
                        </TR>
                        <TR>
                            <TD><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired"/></TD>
                            <TD><bean:message bundle="/signup/ApplicationResources" key="signupBusinessForm.businessPhone.prompt"/></TD>
                            <TD><bean:write scope="session" name="signupBusinessForm" property="businessPhone"/></TD>
                        </TR>
                        <TR>
                            <TD><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired"/></TD>
                            <TD><bean:message bundle="/signup/ApplicationResources" key="signupBusinessForm.businessFax.prompt"/></TD>
                            <TD><bean:write scope="session" name="signupBusinessForm" property="businessFax"/></TD>
                        </TR>
                        <TR>
                            <TD><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired"/></TD>
                            <TD><bean:message bundle="/signup/ApplicationResources" key="signupBusinessForm.businessAddress1.prompt"/></TD>
                            <TD><bean:write scope="session" name="signupBusinessForm" property="businessAddress1"/></TD>
                        </TR>
                        <logic:notEmpty scope="session" name="signupBusinessForm" property="businessAddress2">
                            <TR>
                                <TD><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired"/></TD>
                                <TD><bean:message bundle="/signup/ApplicationResources" key="signupBusinessForm.businessAddress2.prompt"/></TD>
                                <TD><bean:write scope="session" name="signupBusinessForm" property="businessAddress2"/></TD>
                            </TR>
                        </logic:notEmpty>
                        <TR>
                            <TD><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired"/></TD>
                            <TD><bean:message bundle="/signup/ApplicationResources" key="signupBusinessForm.businessCity.prompt"/></TD>
                            <TD><bean:write scope="session" name="signupBusinessForm" property="businessCity"/></TD>
                        </TR>
                        <TR>
                            <TD><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired"/></TD>
                            <TD><bean:message bundle="/signup/ApplicationResources" key="signupBusinessForm.businessState.prompt"/></TD>
                            <TD><bean:write scope="session" name="signupBusinessForm" property="businessState"/></TD>
                        </TR>
                        <TR>
                            <TD><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired"/></TD>
                            <TD><bean:message bundle="/signup/ApplicationResources" key="signupBusinessForm.businessCountry.prompt"/></TD>
                            <TD><bean:write scope="request" name="businessCountry"/></TD>
                        </TR>
                        <TR>
                            <TD><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired"/></TD>
                            <TD><bean:message bundle="/signup/ApplicationResources" key="signupBusinessForm.businessZip.prompt"/></TD>
                            <TD><bean:write scope="session" name="signupBusinessForm" property="businessZip"/></TD>
                        </TR>
                        <TR><TD colspan="3">&nbsp;</TD></TR>
                        <TR><TH colspan="3"><bean:message bundle="/signup/ApplicationResources" key="steps.technicalInfo.label"/></TH></TR>
                        <TR>
                            <TD><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired"/></TD>
                            <TD><bean:message bundle="/signup/ApplicationResources" key="signupTechnicalForm.baName.prompt"/></TD>
                            <TD><bean:write scope="session" name="signupTechnicalForm" property="baName"/></TD>
                        </TR>
                        <TR>
                            <TD><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired"/></TD>
                            <TD><bean:message bundle="/signup/ApplicationResources" key="signupTechnicalForm.baTitle.prompt"/></TD>
                            <TD><bean:write scope="session" name="signupTechnicalForm" property="baTitle"/></TD>
                        </TR>
                        <TR>
                            <TD><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired"/></TD>
                            <TD><bean:message bundle="/signup/ApplicationResources" key="signupTechnicalForm.baWorkPhone.prompt"/></TD>
                            <TD><bean:write scope="session" name="signupTechnicalForm" property="baWorkPhone"/></TD>
                        </TR>
                        <TR>
                            <TD><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired"/></TD>
                            <TD><bean:message bundle="/signup/ApplicationResources" key="signupTechnicalForm.baCellPhone.prompt"/></TD>
                            <TD><bean:write scope="session" name="signupTechnicalForm" property="baCellPhone"/></TD>
                        </TR>
                        <TR>
                            <TD><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired"/></TD>
                            <TD><bean:message bundle="/signup/ApplicationResources" key="signupTechnicalForm.baHomePhone.prompt"/></TD>
                            <TD><bean:write scope="session" name="signupTechnicalForm" property="baHomePhone"/></TD>
                        </TR>
                        <TR>
                            <TD><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired"/></TD>
                            <TD><bean:message bundle="/signup/ApplicationResources" key="signupTechnicalForm.baFax.prompt"/></TD>
                            <TD><bean:write scope="session" name="signupTechnicalForm" property="baFax"/></TD>
                        </TR>
                        <TR>
                            <TD><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired"/></TD>
                            <TD><bean:message bundle="/signup/ApplicationResources" key="signupTechnicalForm.baEmail.prompt"/></TD>
                            <TD><bean:write scope="session" name="signupTechnicalForm" property="baEmail"/></TD>
                        </TR>
                        <TR>
                            <TD><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired"/></TD>
                            <TD><bean:message bundle="/signup/ApplicationResources" key="signupTechnicalForm.baAddress1.prompt"/></TD>
                            <TD><bean:write scope="session" name="signupTechnicalForm" property="baAddress1"/></TD>
                        </TR>
                        <logic:notEmpty scope="session" name="signupTechnicalForm" property="baAddress2">
                            <TR>
                                <TD><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired"/></TD>
                                <TD><bean:message bundle="/signup/ApplicationResources" key="signupTechnicalForm.baAddress2.prompt"/></TD>
                                <TD><bean:write scope="session" name="signupTechnicalForm" property="baAddress2"/></TD>
                            </TR>
                        </logic:notEmpty>
                        <TR>
                            <TD><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired"/></TD>
                            <TD><bean:message bundle="/signup/ApplicationResources" key="signupTechnicalForm.baCity.prompt"/></TD>
                            <TD><bean:write scope="session" name="signupTechnicalForm" property="baCity"/></TD>
                        </TR>
                        <TR>
                            <TD><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired"/></TD>
                            <TD><bean:message bundle="/signup/ApplicationResources" key="signupTechnicalForm.baState.prompt"/></TD>
                            <TD><bean:write scope="session" name="signupTechnicalForm" property="baState"/></TD>
                        </TR>
                        <TR>
                            <TD><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired"/></TD>
                            <TD><bean:message bundle="/signup/ApplicationResources" key="signupTechnicalForm.baCountry.prompt"/></TD>
                            <TD><bean:write scope="request" name="baCountry"/></TD>
                        </TR>
                        <TR>
                            <TD><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired"/></TD>
                            <TD><bean:message bundle="/signup/ApplicationResources" key="signupTechnicalForm.baZip.prompt"/></TD>
                            <TD><bean:write scope="session" name="signupTechnicalForm" property="baZip"/></TD>
                        </TR>
                        <TR>
                            <TD><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired"/></TD>
                            <TD><bean:message bundle="/signup/ApplicationResources" key="signupTechnicalForm.baUsername.prompt"/></TD>
                            <TD><bean:write scope="session" name="signupTechnicalForm" property="baUsername"/></TD>
                        </TR>
                        <TR>
                            <TD><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired"/></TD>
                            <TD><bean:message bundle="/signup/ApplicationResources" key="signupTechnicalForm.baPassword.prompt"/></TD>
                            <TD><bean:write scope="session" name="signupTechnicalForm" property="baPassword"/></TD>
                        </TR>
                        <TR><TD colspan="3">&nbsp;</TD></TR>
                        <TR><TH colspan="3"><bean:message bundle="/signup/ApplicationResources" key="steps.billingInformation.label"/></TH></TR>
                        <TR>
                            <TD><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired"/></TD>
                            <TD><bean:message bundle="/signup/ApplicationResources" key="signupBillingInformationForm.billingContact.prompt"/></TD>
                            <TD><bean:write scope="session" name="signupBillingInformationForm" property="billingContact"/></TD>
                        </TR>
                        <TR>
                            <TD><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired"/></TD>
                            <TD><bean:message bundle="/signup/ApplicationResources" key="signupBillingInformationForm.billingEmail.prompt"/></TD>
                            <TD><bean:write scope="session" name="signupBillingInformationForm" property="billingEmail"/></TD>
                        </TR>
                        <TR>
                            <TD><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired"/></TD>
                            <TD><bean:message bundle="/signup/ApplicationResources" key="signupBillingInformationForm.billingCardholderName.prompt"/></TD>
                            <TD><bean:write scope="session" name="signupBillingInformationForm" property="billingCardholderName"/></TD>
                        </TR>
                        <TR>
                            <TD><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired"/></TD>
                            <TD><bean:message bundle="/signup/ApplicationResources" key="signupBillingInformationForm.billingCardNumber.prompt"/></TD>
                            <TD><bean:write scope="request" name="billingCardNumber"/></TD>
                        </TR>
                        <TR>
                            <TD><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired"/></TD>
                            <TD><bean:message bundle="/signup/ApplicationResources" key="signupBillingInformationForm.billingExpirationDate.prompt"/></TD>
                            <TD><bean:message bundle="/signup/ApplicationResources" key="dedicated6.billingExpirationDate.hidden"/></TD>
                        </TR>
                        <TR>
                            <TD><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired"/></TD>
                            <TD><bean:message bundle="/signup/ApplicationResources" key="signupBillingInformationForm.billingStreetAddress.prompt"/></TD>
                            <TD><bean:write scope="session" name="signupBillingInformationForm" property="billingStreetAddress"/></TD>
                        </TR>
                        <TR>
                            <TD><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired"/></TD>
                            <TD><bean:message bundle="/signup/ApplicationResources" key="signupBillingInformationForm.billingCity.prompt"/></TD>
                            <TD><bean:write scope="session" name="signupBillingInformationForm" property="billingCity"/></TD>
                        </TR>
                        <TR>
                            <TD><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired"/></TD>
                            <TD><bean:message bundle="/signup/ApplicationResources" key="signupBillingInformationForm.billingState.prompt"/></TD>
                            <TD><bean:write scope="session" name="signupBillingInformationForm" property="billingState"/></TD>
                        </TR>
                        <TR>
                            <TD><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired"/></TD>
                            <TD><bean:message bundle="/signup/ApplicationResources" key="signupBillingInformationForm.billingZip.prompt"/></TD>
                            <TD><bean:write scope="session" name="signupBillingInformationForm" property="billingZip"/></TD>
                        </TR>
                        <TR>
                            <TD><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired"/></TD>
                            <TD><bean:message bundle="/signup/ApplicationResources" key="dedicated6.billingUseMonthly.prompt"/></TD>
                            <TD>
                                <logic:equal scope="session" name="signupBillingInformationForm" property="billingUseMonthly" value="true">
                                    <bean:message bundle="/signup/ApplicationResources" key="dedicated6.billingUseMonthly.yes"/>
                                </logic:equal>
                                <logic:notEqual scope="session" name="signupBillingInformationForm" property="billingUseMonthly" value="true">
                                    <bean:message bundle="/signup/ApplicationResources" key="dedicated6.billingUseMonthly.no"/>
                                </logic:notEqual>
                            </TD>
                        </TR>
                        <TR>
                            <TD><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired"/></TD>
                            <TD><bean:message bundle="/signup/ApplicationResources" key="dedicated6.billingPayOneYear.prompt"/></TD>
                            <TD>
                                <logic:equal scope="session" name="signupBillingInformationForm" property="billingPayOneYear" value="true">
                                    <bean:message bundle="/signup/ApplicationResources" key="dedicated6.billingPayOneYear.yes"/>
                                </logic:equal>
                                <logic:notEqual scope="session" name="signupBillingInformationForm" property="billingPayOneYear" value="true">
                                    <bean:message bundle="/signup/ApplicationResources" key="dedicated6.billingPayOneYear.no"/>
                                </logic:notEqual>
                            </TD>
                        </TR>
                    </TABLE>
                </skin:lightArea>
            </skin:contentLine>
        </skin:content>
    </skin:skin>
</html:html>
