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
    <skin:path>/signup/dedicated5.do</skin:path>
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
                <bean:define toScope="request" type="java.lang.String" id="stepNumber" value="5"/>
                <%@ include file="dedicatedSteps.jsp" %>
                <br>
                <html:form action="/dedicated5Completed.do">
                    <skin:lightArea>
                         <TABLE BORDER="0" CELLPADDING="0" CELLSPACING="0">
                            <TR><TD COLSPAN="4"><B><bean:message bundle="/signup/ApplicationResources" key="dedicated5.stepLabel"/></B><BR><HR></TD></TR>
                            <TR><TD COLSPAN="4"><bean:message bundle="/signup/ApplicationResources" key="dedicated5.stepHelp"/><BR><BR></TD></TR>
                            <TR>
                                <TD NOWRAP><bean:message bundle="/signup/ApplicationResources" key="signup.required"/></TD>
                                <TD NOWRAP><bean:message bundle="/signup/ApplicationResources" key="signupBillingInformationForm.billingContact.prompt"/></TD>
                                <TD NOWRAP><html:text size="32" property="billingContact" maxlength="255"/></TD>
                                <TD NOWRAP><html:errors bundle="/signup/ApplicationResources" property="billingContact"/></TD>
                            </TR>
                            <TR>
                                <TD NOWRAP><bean:message bundle="/signup/ApplicationResources" key="signup.required"/></TD>
                                <TD NOWRAP><bean:message bundle="/signup/ApplicationResources" key="signupBillingInformationForm.billingEmail.prompt"/></TD>
                                <TD NOWRAP><html:text size="32" property="billingEmail" maxlength="255"/></TD>
                                <TD NOWRAP><html:errors bundle="/signup/ApplicationResources" property="billingEmail"/></TD>
                            </TR>
                            <TR><TD colspan="4">&nbsp;</TD></TR>
                            <TR><TD colspan="4" align='center'>
                                <html:img src="amex.gif" bundle="/signup/ApplicationResources" altKey="dedicated5.image.amex.alt" align="absmiddle" border="1" width="64" height="40"/>
                                &nbsp;&nbsp;<html:img src="discv.gif" bundle="/signup/ApplicationResources" altKey="dedicated5.image.discv.alt" align="absmiddle" border="1" width="63" height="40"/>
                                &nbsp;&nbsp;<html:img src="mcard.gif" bundle="/signup/ApplicationResources" altKey="dedicated5.image.mcard.alt" align="absmiddle" border="1" width="64" height="40"/>
                                &nbsp;&nbsp;<html:img src="visa.gif" bundle="/signup/ApplicationResources" altKey="dedicated5.image.visa.alt" align="absmiddle" border="1" width="64" height="40"/>
                            </TD></TR>
                            <TR><TD colspan="4">&nbsp;</TD></TR>
                            <TR>
                                <TD NOWRAP><bean:message bundle="/signup/ApplicationResources" key="signup.required"/></TD>
                                <TD NOWRAP><bean:message bundle="/signup/ApplicationResources" key="signupBillingInformationForm.billingCardholderName.prompt"/></TD>
                                <TD NOWRAP><html:text size="32" property="billingCardholderName" maxlength="255"/></TD>
                                <TD NOWRAP><html:errors bundle="/signup/ApplicationResources" property="billingCardholderName"/></TD>
                            </TR>
                            <TR>
                                <TD NOWRAP><bean:message bundle="/signup/ApplicationResources" key="signup.required"/></TD>
                                <TD NOWRAP><bean:message bundle="/signup/ApplicationResources" key="signupBillingInformationForm.billingCardNumber.prompt"/></TD>
                                <TD NOWRAP><html:text size="32" property="billingCardNumber" maxlength="255"/></TD>
                                <TD NOWRAP><html:errors bundle="/signup/ApplicationResources" property="billingCardNumber"/></TD>
                            </TR>
                            <TR>
                                <TD NOWRAP><bean:message bundle="/signup/ApplicationResources" key="signup.required"/></TD>
                                <TD NOWRAP><bean:message bundle="/signup/ApplicationResources" key="signupBillingInformationForm.billingExpirationDate.prompt"/></TD>
                                <TD NOWRAP>
                                    <html:select property="billingExpirationMonth">
                                        <html:option bundle="/signup/ApplicationResources" key="signupBillingInformationForm.billingExpirationMonth.none.display" value=""/>
                                        <html:option bundle="/signup/ApplicationResources" key="signupBillingInformationForm.billingExpirationMonth.jan.display" value="01"/>
                                        <html:option bundle="/signup/ApplicationResources" key="signupBillingInformationForm.billingExpirationMonth.feb.display" value="02"/>
                                        <html:option bundle="/signup/ApplicationResources" key="signupBillingInformationForm.billingExpirationMonth.mar.display" value="03"/>
                                        <html:option bundle="/signup/ApplicationResources" key="signupBillingInformationForm.billingExpirationMonth.apr.display" value="04"/>
                                        <html:option bundle="/signup/ApplicationResources" key="signupBillingInformationForm.billingExpirationMonth.may.display" value="05"/>
                                        <html:option bundle="/signup/ApplicationResources" key="signupBillingInformationForm.billingExpirationMonth.jun.display" value="06"/>
                                        <html:option bundle="/signup/ApplicationResources" key="signupBillingInformationForm.billingExpirationMonth.jul.display" value="07"/>
                                        <html:option bundle="/signup/ApplicationResources" key="signupBillingInformationForm.billingExpirationMonth.aug.display" value="08"/>
                                        <html:option bundle="/signup/ApplicationResources" key="signupBillingInformationForm.billingExpirationMonth.sep.display" value="09"/>
                                        <html:option bundle="/signup/ApplicationResources" key="signupBillingInformationForm.billingExpirationMonth.oct.display" value="10"/>
                                        <html:option bundle="/signup/ApplicationResources" key="signupBillingInformationForm.billingExpirationMonth.nov.display" value="11"/>
                                        <html:option bundle="/signup/ApplicationResources" key="signupBillingInformationForm.billingExpirationMonth.dec.display" value="12"/>
                                    </html:select>&nbsp;/&nbsp;<html:select property="billingExpirationYear">
                                        <html:option bundle="/signup/ApplicationResources" key="signupBillingInformationForm.billingExpirationYear.none.display" value=""/>
                                        <html:options name="billingExpirationYears"/>
                                    </html:select>
                                </TD>
                                <TD NOWRAP><html:errors bundle="/signup/ApplicationResources" property="billingExpirationDate"/></TD>
                            </TR>
                            <TR><TD colspan="4">&nbsp;</TD></TR>
                            <TR><TD colspan="4"><bean:message bundle="/signup/ApplicationResources" key="dedicated5.cardAddressHelp"/></TD></TR>
                            <TR><TD colspan="4">&nbsp;</TD></TR>
                            <TR>
                                <TD NOWRAP><bean:message bundle="/signup/ApplicationResources" key="signup.required"/></TD>
                                <TD NOWRAP><bean:message bundle="/signup/ApplicationResources" key="signupBillingInformationForm.billingStreetAddress.prompt"/></TD>
                                <TD NOWRAP><html:text size="32" property="billingStreetAddress" maxlength="255"/></TD>
                                <TD NOWRAP><html:errors bundle="/signup/ApplicationResources" property="billingStreetAddress"/></TD>
                            </TR>
                            <TR>
                                <TD NOWRAP><bean:message bundle="/signup/ApplicationResources" key="signup.required"/></TD>
                                <TD NOWRAP><bean:message bundle="/signup/ApplicationResources" key="signupBillingInformationForm.billingCity.prompt"/></TD>
                                <TD NOWRAP><html:text size="14" property="billingCity" maxlength="255"/></TD>
                                <TD NOWRAP><html:errors bundle="/signup/ApplicationResources" property="billingCity"/></TD>
                            </TR>
                            <TR>
                                <TD NOWRAP><bean:message bundle="/signup/ApplicationResources" key="signup.required"/></TD>
                                <TD NOWRAP><bean:message bundle="/signup/ApplicationResources" key="signupBillingInformationForm.billingState.prompt"/></TD>
                                <TD NOWRAP><html:text size="5" property="billingState" maxlength="255"/></TD>
                                <TD NOWRAP><html:errors bundle="/signup/ApplicationResources" property="billingState"/></TD>
                            </TR>
                            <TR>
                                <TD NOWRAP><bean:message bundle="/signup/ApplicationResources" key="signup.required"/></TD>
                                <TD NOWRAP><bean:message bundle="/signup/ApplicationResources" key="signupBillingInformationForm.billingZip.prompt"/></TD>
                                <TD NOWRAP><html:text size="10" property="billingZip" maxlength="255"/></TD>
                                <TD NOWRAP><html:errors bundle="/signup/ApplicationResources" property="billingZip"/></TD>
                            </TR>
                            <TR><TD colspan="4">&nbsp;</TD></TR>
                            <TR VALIGN='top'>
                                <TD NOWRAP colspan="2">&nbsp;</TD>
                                <TD NOWRAP><html:checkbox property="billingUseMonthly"/><bean:message bundle="/signup/ApplicationResources" key="signupBillingInformationForm.billingUseMonthly.prompt"/><BR><BR>
                                <TD NOWRAP><html:errors bundle="/signup/ApplicationResources" property="billingUseMonthly"/></TD>
                            </TD><TR>
                            <TR VALIGN='top'>
                                <TD NOWRAP colspan="2">&nbsp;</TD>
                                <TD NOWRAP><html:checkbox property="billingPayOneYear"/><bean:message bundle="/signup/ApplicationResources" key="signupBillingInformationForm.billingPayOneYear.prompt"/></TD>
                                <TD NOWRAP><html:errors bundle="/signup/ApplicationResources" property="billingPayOneYear"/></TD>
                            </TD><TR>
                            <TR><TD colspan="4" align="center"><br><html:submit styleClass='ao_button'><bean:message bundle="/signup/ApplicationResources" key="dedicated5.submit.label"/></html:submit><br><br></td></tr>
                        </TABLE>
                    </skin:lightArea>
                </html:form>
            </skin:contentLine>
        </skin:content>
    </skin:skin>
</html:html>
