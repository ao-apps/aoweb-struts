<%-- aoweb-struts: Do not edit --%>
<%--
  Copyright 2007-2009 by AO Industries, Inc.,
  7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" buffer="256kb" autoFlush="true" %>
<%@ include file="/WEB-INF/jsp/taglibs.jsp" %>

<input type="hidden" name="selectedStep" value="" />
<script type='text/javascript'>
    // <![CDATA[
    function selectStep(step) {
        var form = document.forms['signupBillingInformationForm'];
        form.selectedStep.value=step;
        form.submit();
    }
    // ]]>
</script>
<skin:lightArea>
     <table cellpadding="0" cellspacing="0">
        <tr><td COLSPAN="4"><b><bean:message bundle="/signup/ApplicationResources" key="dedicated5.stepLabel"/></b><br /><hr /></td></tr>
        <tr><td COLSPAN="4"><bean:message bundle="/signup/ApplicationResources" key="dedicated5.stepHelp"/><br /><br /></td></tr>
        <tr>
            <td style="white-space:nowrap"><bean:message bundle="/signup/ApplicationResources" key="signup.required"/></td>
            <td style="white-space:nowrap"><bean:message bundle="/signup/ApplicationResources" key="signupBillingInformationForm.billingContact.prompt"/></td>
            <td style="white-space:nowrap"><html:text size="32" property="billingContact" maxlength="255"/></td>
            <td style="white-space:nowrap"><html:errors bundle="/signup/ApplicationResources" property="billingContact"/></td>
        </tr>
        <tr>
            <td style="white-space:nowrap"><bean:message bundle="/signup/ApplicationResources" key="signup.required"/></td>
            <td style="white-space:nowrap"><bean:message bundle="/signup/ApplicationResources" key="signupBillingInformationForm.billingEmail.prompt"/></td>
            <td style="white-space:nowrap"><html:text size="32" property="billingEmail" maxlength="255"/></td>
            <td style="white-space:nowrap"><html:errors bundle="/signup/ApplicationResources" property="billingEmail"/></td>
        </tr>
        <tr><td colspan="4">&nbsp;</td></tr>
        <tr><td colspan="4" align='center'>
            <html:img src="amex.gif" bundle="/signup/ApplicationResources" altKey="dedicated5.image.amex.alt" style="border:1px solid" width="64" height="40"/>
            &nbsp;&nbsp;<html:img src="discv.gif" bundle="/signup/ApplicationResources" altKey="dedicated5.image.discv.alt" style="border:1px solid" width="63" height="40"/>
            &nbsp;&nbsp;<html:img src="mcard.gif" bundle="/signup/ApplicationResources" altKey="dedicated5.image.mcard.alt" style="border:1px solid" width="64" height="40"/>
            &nbsp;&nbsp;<html:img src="visa.gif" bundle="/signup/ApplicationResources" altKey="dedicated5.image.visa.alt" style="border:1px solid" width="64" height="40"/>
        </td></tr>
        <tr><td colspan="4">&nbsp;</td></tr>
        <tr>
            <td style="white-space:nowrap"><bean:message bundle="/signup/ApplicationResources" key="signup.required"/></td>
            <td style="white-space:nowrap"><bean:message bundle="/signup/ApplicationResources" key="signupBillingInformationForm.billingCardholderName.prompt"/></td>
            <td style="white-space:nowrap"><html:text size="32" property="billingCardholderName" maxlength="255"/></td>
            <td style="white-space:nowrap"><html:errors bundle="/signup/ApplicationResources" property="billingCardholderName"/></td>
        </tr>
        <tr>
            <td style="white-space:nowrap"><bean:message bundle="/signup/ApplicationResources" key="signup.required"/></td>
            <td style="white-space:nowrap"><bean:message bundle="/signup/ApplicationResources" key="signupBillingInformationForm.billingCardNumber.prompt"/></td>
            <td style="white-space:nowrap"><html:text size="32" property="billingCardNumber" maxlength="255"/></td>
            <td style="white-space:nowrap"><html:errors bundle="/signup/ApplicationResources" property="billingCardNumber"/></td>
        </tr>
        <tr>
            <td style="white-space:nowrap"><bean:message bundle="/signup/ApplicationResources" key="signup.required"/></td>
            <td style="white-space:nowrap"><bean:message bundle="/signup/ApplicationResources" key="signupBillingInformationForm.billingExpirationDate.prompt"/></td>
            <td style="white-space:nowrap">
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
            </td>
            <td style="white-space:nowrap"><html:errors bundle="/signup/ApplicationResources" property="billingExpirationDate"/></td>
        </tr>
        <tr><td colspan="4">&nbsp;</td></tr>
        <tr><td colspan="4"><bean:message bundle="/signup/ApplicationResources" key="dedicated5.cardAddressHelp"/></td></tr>
        <tr><td colspan="4">&nbsp;</td></tr>
        <tr>
            <td style="white-space:nowrap"><bean:message bundle="/signup/ApplicationResources" key="signup.required"/></td>
            <td style="white-space:nowrap"><bean:message bundle="/signup/ApplicationResources" key="signupBillingInformationForm.billingStreetAddress.prompt"/></td>
            <td style="white-space:nowrap"><html:text size="32" property="billingStreetAddress" maxlength="255"/></td>
            <td style="white-space:nowrap"><html:errors bundle="/signup/ApplicationResources" property="billingStreetAddress"/></td>
        </tr>
        <tr>
            <td style="white-space:nowrap"><bean:message bundle="/signup/ApplicationResources" key="signup.required"/></td>
            <td style="white-space:nowrap"><bean:message bundle="/signup/ApplicationResources" key="signupBillingInformationForm.billingCity.prompt"/></td>
            <td style="white-space:nowrap"><html:text size="14" property="billingCity" maxlength="255"/></td>
            <td style="white-space:nowrap"><html:errors bundle="/signup/ApplicationResources" property="billingCity"/></td>
        </tr>
        <tr>
            <td style="white-space:nowrap"><bean:message bundle="/signup/ApplicationResources" key="signup.required"/></td>
            <td style="white-space:nowrap"><bean:message bundle="/signup/ApplicationResources" key="signupBillingInformationForm.billingState.prompt"/></td>
            <td style="white-space:nowrap"><html:text size="5" property="billingState" maxlength="255"/></td>
            <td style="white-space:nowrap"><html:errors bundle="/signup/ApplicationResources" property="billingState"/></td>
        </tr>
        <tr>
            <td style="white-space:nowrap"><bean:message bundle="/signup/ApplicationResources" key="signup.required"/></td>
            <td style="white-space:nowrap"><bean:message bundle="/signup/ApplicationResources" key="signupBillingInformationForm.billingZip.prompt"/></td>
            <td style="white-space:nowrap"><html:text size="10" property="billingZip" maxlength="255"/></td>
            <td style="white-space:nowrap"><html:errors bundle="/signup/ApplicationResources" property="billingZip"/></td>
        </tr>
        <tr><td colspan="4">&nbsp;</td></tr>
        <tr VALIGN='top'>
            <td NOWRAP colspan="2">&nbsp;</td>
            <td style="white-space:nowrap"><html:checkbox property="billingUseMonthly"/><bean:message bundle="/signup/ApplicationResources" key="signupBillingInformationForm.billingUseMonthly.value"/><br /><br />
            <td style="white-space:nowrap"><html:errors bundle="/signup/ApplicationResources" property="billingUseMonthly"/></td>
        </td><tr>
        <tr VALIGN='top'>
            <td NOWRAP colspan="2">&nbsp;</td>
            <td style="white-space:nowrap"><html:checkbox property="billingPayOneYear"/><bean:message bundle="/signup/ApplicationResources" key="signupBillingInformationForm.billingPayOneYear.value"/></td>
            <td style="white-space:nowrap"><html:errors bundle="/signup/ApplicationResources" property="billingPayOneYear"/></td>
        </tr>
        <tr><td colspan="4" align="center"><br /><html:submit><bean:message bundle="/signup/ApplicationResources" key="dedicated5.submit.label"/></html:submit><br /><br /></td></tr>
    </table>
</skin:lightArea>
