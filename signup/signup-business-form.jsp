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
            var form = document.forms['signupBusinessForm'];
            form.selectedStep.value=step;
            form.submit();
        }
    </ao:script>
    <skin:lightArea>
        <fmt:bundle basename="com.aoindustries.website.signup.ApplicationResources">
             <table cellpadding="0" cellspacing="0">
                <tr><td colspan="4"><b><fmt:message key="signupBusinessForm.stepLabel" /></b><br /><hr /></td></tr>
                <tr><td colspan="4"><fmt:message key="signupBusinessForm.stepHelp" /><br /><br /></td></tr>
                <tr>
                    <td style="white-space:nowrap"><fmt:message key="signup.required" /></td>
                    <td style="white-space:nowrap"><fmt:message key="signupBusinessForm.businessName.prompt" /></td>
                    <td style="white-space:nowrap"><html:text size="32" property="businessName" maxlength="255" /></td>
                    <td style="white-space:nowrap"><html:errors bundle="/signup/ApplicationResources" property="businessName" /></td>
                </tr>
                <tr>
                    <td style="white-space:nowrap"><fmt:message key="signup.required" /></td>
                    <td style="white-space:nowrap"><fmt:message key="signupBusinessForm.businessPhone.prompt" /></td>
                    <td style="white-space:nowrap"><html:text size="18" property="businessPhone" maxlength="255" /></td>
                    <td style="white-space:nowrap"><html:errors bundle="/signup/ApplicationResources" property="businessPhone" /></td>
                </tr>
                <tr>
                    <td style="white-space:nowrap"><fmt:message key="signup.notRequired" /></td>
                    <td style="white-space:nowrap"><fmt:message key="signupBusinessForm.businessFax.prompt" /></td>
                    <td style="white-space:nowrap"><html:text size="18" property="businessFax" maxlength="255" /></td>
                    <td style="white-space:nowrap"><html:errors bundle="/signup/ApplicationResources" property="businessFax" /></td>
                </tr>
                <tr><td colspan="4">&#160;</td></tr>
                <tr>
                    <td style="white-space:nowrap"><fmt:message key="signup.required" /></td>
                    <td style="white-space:nowrap"><fmt:message key="signupBusinessForm.businessAddress1.prompt" /></td>
                    <td style="white-space:nowrap"><html:text size="32" property="businessAddress1" maxlength="255" /></td>
                    <td style="white-space:nowrap"><html:errors bundle="/signup/ApplicationResources" property="businessAddress1" /></td>
                </tr>
                <tr>
                    <td style="white-space:nowrap"><fmt:message key="signup.notRequired" /></td>
                    <td style="white-space:nowrap"><fmt:message key="signupBusinessForm.businessAddress2.prompt" /></td>
                    <td style="white-space:nowrap"><html:text size="32" property="businessAddress2" maxlength="255" /></td>
                    <td style="white-space:nowrap"><html:errors bundle="/signup/ApplicationResources" property="businessAddress2" /></td>
                </tr>
                <tr>
                    <td style="white-space:nowrap"><fmt:message key="signup.required" /></td>
                    <td style="white-space:nowrap"><fmt:message key="signupBusinessForm.businessCity.prompt" /></td>
                    <td style="white-space:nowrap"><html:text size="16" property="businessCity" maxlength="255" /></td>
                    <td style="white-space:nowrap"><html:errors bundle="/signup/ApplicationResources" property="businessCity" /></td>
                </tr>
                <tr>
                    <td style="white-space:nowrap"><fmt:message key="signup.notRequired" /></td>
                    <td style="white-space:nowrap"><fmt:message key="signupBusinessForm.businessState.prompt" /></td>
                    <td style="white-space:nowrap"><html:text size="5" property="businessState" maxlength="255" /></td>
                    <td style="white-space:nowrap"><html:errors bundle="/signup/ApplicationResources" property="businessState" /></td>
                </tr>
                <tr>
                    <td style="white-space:nowrap"><fmt:message key="signup.required" /></td>
                    <td style="white-space:nowrap"><fmt:message key="signupBusinessForm.businessCountry.prompt" /></td>
                    <td style="white-space:nowrap">
                        <html:select property="businessCountry">
                            <bean:define id="didOne" type="java.lang.String" value="false" />
                            <bean:define name="signupBusinessForm" property="businessCountry" id="businessCountry" type="java.lang.String" />
                            <logic:iterate scope="request" name="countryOptions" id="countryOption">
                                <logic:equal name="countryOption" property="code" value="<%= businessCountry %>">
                                    <% if(!didOne.equals("true")) { %>
                                        <option value='<ao:write name="countryOption" property="code" />' selected="selected"><ao:write name="countryOption" property="name" /></option>
                                        <% didOne = "true"; %>
                                    <% } else { %>
                                        <option value='<ao:write name="countryOption" property="code" />'><ao:write name="countryOption" property="name" /></option>
                                    <% } %>
                                </logic:equal>
                                <logic:notEqual name="countryOption" property="code" value="<%= businessCountry %>">
                                    <option value='<ao:write name="countryOption" property="code" />'><ao:write name="countryOption" property="name" /></option>
                                </logic:notEqual>
                            </logic:iterate>
                        </html:select>
                    </td>
                    <td style="white-space:nowrap"><html:errors bundle="/signup/ApplicationResources" property="businessCountry" /></td>
                </tr>
                <tr>
                    <td style="white-space:nowrap"><fmt:message key="signup.notRequired" /></td>
                    <td style="white-space:nowrap"><fmt:message key="signupBusinessForm.businessZip.prompt" /></td>
                    <td style="white-space:nowrap"><html:text size="10" property="businessZip" maxlength="255" /></td>
                    <td style="white-space:nowrap"><html:errors bundle="/signup/ApplicationResources" property="businessZip" /></td>
                </tr>
                <tr><td colspan="4" align="center"><br /><ao:input type="submit"><fmt:message key="signupBusinessForm.submit.label" /></ao:input><br /><br /></td></tr>
             </table>
         </fmt:bundle>
    </skin:lightArea>
</div>
