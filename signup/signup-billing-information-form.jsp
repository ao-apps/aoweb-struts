<%-- aoweb-struts: Do not edit --%>
<%--
  Copyright 2007-2009 by AO Industries, Inc.,
  7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@include file="/_taglibs.jsp" %>

<div>
    <input type="hidden" name="selectedStep" value="" />
    <ao:script>
        function selectStep(step) {
            var form = document.forms['signupBillingInformationForm'];
            form.selectedStep.value=step;
            form.submit();
        }
    </ao:script>
    <skin:lightArea>
        <fmt:bundle basename="com.aoindustries.website.signup.ApplicationResources">
             <table cellpadding="0" cellspacing="0">
                <tr><td colspan="4"><b><fmt:message key="dedicated5.stepLabel" /></b><br /><hr /></td></tr>
                <tr><td colspan="4"><fmt:message key="dedicated5.stepHelp" /><br /><br /></td></tr>
                <tr>
                    <td style="white-space:nowrap"><fmt:message key="signup.required" /></td>
                    <td style="white-space:nowrap"><fmt:message key="signupBillingInformationForm.billingContact.prompt" /></td>
                    <td style="white-space:nowrap"><html:text size="32" property="billingContact" maxlength="255" /></td>
                    <td style="white-space:nowrap"><html:errors bundle="/signup/ApplicationResources" property="billingContact" /></td>
                </tr>
                <tr>
                    <td style="white-space:nowrap"><fmt:message key="signup.required" /></td>
                    <td style="white-space:nowrap"><fmt:message key="signupBillingInformationForm.billingEmail.prompt" /></td>
                    <td style="white-space:nowrap"><html:text size="32" property="billingEmail" maxlength="255" /></td>
                    <td style="white-space:nowrap"><html:errors bundle="/signup/ApplicationResources" property="billingEmail" /></td>
                </tr>
                <tr><td colspan="4">&#160;</td></tr>
                <tr><td colspan="4" align='center'><%@include file="/clientarea/accounting/_credit-card-images.jsp" %></td></tr>
                <tr><td colspan="4">&#160;</td></tr>
                <tr>
                    <td style="white-space:nowrap"><fmt:message key="signup.required" /></td>
                    <td style="white-space:nowrap"><fmt:message key="signupBillingInformationForm.billingCardholderName.prompt" /></td>
                    <td style="white-space:nowrap"><html:text size="32" property="billingCardholderName" maxlength="255" /></td>
                    <td style="white-space:nowrap"><html:errors bundle="/signup/ApplicationResources" property="billingCardholderName" /></td>
                </tr>
                <tr>
                    <td style="white-space:nowrap"><fmt:message key="signup.required" /></td>
                    <td style="white-space:nowrap"><fmt:message key="signupBillingInformationForm.billingCardNumber.prompt" /></td>
                    <td style="white-space:nowrap"><html:text size="32" property="billingCardNumber" maxlength="255" /></td>
                    <td style="white-space:nowrap"><html:errors bundle="/signup/ApplicationResources" property="billingCardNumber" /></td>
                </tr>
                <tr>
                    <td style="white-space:nowrap"><fmt:message key="signup.required" /></td>
                    <td style="white-space:nowrap"><fmt:message key="signupBillingInformationForm.billingExpirationDate.prompt" /></td>
                    <td style="white-space:nowrap">
                        <html:select property="billingExpirationMonth">
                            <html:option value=""><ao:xhtml><ao:text><fmt:message key="signupBillingInformationForm.billingExpirationMonth.none.display" /></ao:text></ao:xhtml></html:option>
                            <html:option value="01"><ao:xhtml><ao:text><fmt:message key="signupBillingInformationForm.billingExpirationMonth.jan.display" /></ao:text></ao:xhtml></html:option>
                            <html:option value="02"><ao:xhtml><ao:text><fmt:message key="signupBillingInformationForm.billingExpirationMonth.feb.display" /></ao:text></ao:xhtml></html:option>
                            <html:option value="03"><ao:xhtml><ao:text><fmt:message key="signupBillingInformationForm.billingExpirationMonth.mar.display" /></ao:text></ao:xhtml></html:option>
                            <html:option value="04"><ao:xhtml><ao:text><fmt:message key="signupBillingInformationForm.billingExpirationMonth.apr.display" /></ao:text></ao:xhtml></html:option>
                            <html:option value="05"><ao:xhtml><ao:text><fmt:message key="signupBillingInformationForm.billingExpirationMonth.may.display" /></ao:text></ao:xhtml></html:option>
                            <html:option value="06"><ao:xhtml><ao:text><fmt:message key="signupBillingInformationForm.billingExpirationMonth.jun.display" /></ao:text></ao:xhtml></html:option>
                            <html:option value="07"><ao:xhtml><ao:text><fmt:message key="signupBillingInformationForm.billingExpirationMonth.jul.display" /></ao:text></ao:xhtml></html:option>
                            <html:option value="08"><ao:xhtml><ao:text><fmt:message key="signupBillingInformationForm.billingExpirationMonth.aug.display" /></ao:text></ao:xhtml></html:option>
                            <html:option value="09"><ao:xhtml><ao:text><fmt:message key="signupBillingInformationForm.billingExpirationMonth.sep.display" /></ao:text></ao:xhtml></html:option>
                            <html:option value="10"><ao:xhtml><ao:text><fmt:message key="signupBillingInformationForm.billingExpirationMonth.oct.display" /></ao:text></ao:xhtml></html:option>
                            <html:option value="11"><ao:xhtml><ao:text><fmt:message key="signupBillingInformationForm.billingExpirationMonth.nov.display" /></ao:text></ao:xhtml></html:option>
                            <html:option value="12"><ao:xhtml><ao:text><fmt:message key="signupBillingInformationForm.billingExpirationMonth.dec.display" /></ao:text></ao:xhtml></html:option>
                        </html:select>&#160;/&#160;<html:select property="billingExpirationYear">
                            <html:option value=""><ao:xhtml><ao:text><fmt:message key="signupBillingInformationForm.billingExpirationYear.none.display" /></ao:text></ao:xhtml></html:option>
                            <html:options name="billingExpirationYears" />
                        </html:select>
                    </td>
                    <td style="white-space:nowrap"><html:errors bundle="/signup/ApplicationResources" property="billingExpirationDate" /></td>
                </tr>
                <tr><td colspan="4">&#160;</td></tr>
                <tr><td colspan="4"><fmt:message key="dedicated5.cardAddressHelp" /></td></tr>
                <tr><td colspan="4">&#160;</td></tr>
                <tr>
                    <td style="white-space:nowrap"><fmt:message key="signup.required" /></td>
                    <td style="white-space:nowrap"><fmt:message key="signupBillingInformationForm.billingStreetAddress.prompt" /></td>
                    <td style="white-space:nowrap"><html:text size="32" property="billingStreetAddress" maxlength="255" /></td>
                    <td style="white-space:nowrap"><html:errors bundle="/signup/ApplicationResources" property="billingStreetAddress" /></td>
                </tr>
                <tr>
                    <td style="white-space:nowrap"><fmt:message key="signup.required" /></td>
                    <td style="white-space:nowrap"><fmt:message key="signupBillingInformationForm.billingCity.prompt" /></td>
                    <td style="white-space:nowrap"><html:text size="14" property="billingCity" maxlength="255" /></td>
                    <td style="white-space:nowrap"><html:errors bundle="/signup/ApplicationResources" property="billingCity" /></td>
                </tr>
                <tr>
                    <td style="white-space:nowrap"><fmt:message key="signup.required" /></td>
                    <td style="white-space:nowrap"><fmt:message key="signupBillingInformationForm.billingState.prompt" /></td>
                    <td style="white-space:nowrap"><html:text size="5" property="billingState" maxlength="255" /></td>
                    <td style="white-space:nowrap"><html:errors bundle="/signup/ApplicationResources" property="billingState" /></td>
                </tr>
                <tr>
                    <td style="white-space:nowrap"><fmt:message key="signup.required" /></td>
                    <td style="white-space:nowrap"><fmt:message key="signupBillingInformationForm.billingZip.prompt" /></td>
                    <td style="white-space:nowrap"><html:text size="10" property="billingZip" maxlength="255" /></td>
                    <td style="white-space:nowrap"><html:errors bundle="/signup/ApplicationResources" property="billingZip" /></td>
                </tr>
                <tr><td colspan="4">&#160;</td></tr>
                <tr valign='top'>
                    <td style="white-space:nowrap" colspan="2">&#160;</td>
                    <td style="white-space:nowrap"><html:checkbox property="billingUseMonthly" /><fmt:message key="signupBillingInformationForm.billingUseMonthly.value" /><br /><br /></td>
                    <td style="white-space:nowrap"><html:errors bundle="/signup/ApplicationResources" property="billingUseMonthly" /></td>
                </tr>
                <tr valign='top'>
                    <td style="white-space:nowrap" colspan="2">&#160;</td>
                    <td style="white-space:nowrap"><html:checkbox property="billingPayOneYear" /><fmt:message key="signupBillingInformationForm.billingPayOneYear.value" /></td>
                    <td style="white-space:nowrap"><html:errors bundle="/signup/ApplicationResources" property="billingPayOneYear" /></td>
                </tr>
                <tr><td colspan="4" align="center"><br /><ao:input type="submit"><fmt:message key="dedicated5.submit.label" /></ao:input><br /><br /></td></tr>
            </table>
        </fmt:bundle>
    </skin:lightArea>
</div>
