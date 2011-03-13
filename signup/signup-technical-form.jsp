<%-- aoweb-struts: Do not edit --%>
<%--
  Copyright 2007-2011 by AO Industries, Inc.,
  7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" buffer="256kb" autoFlush="true" pageEncoding="UTF-8" %>
<%@include file="/_taglibs.jsp" %>

<div>
    <input type="hidden" name="selectedStep" value="" />
    <ao:script>
        function selectStep(step) {
            var form = document.forms['signupTechnicalForm'];
            form.selectedStep.value=step;
            form.submit();
        }
    </ao:script>
    <skin:lightArea>
        <fmt:bundle basename="com.aoindustries.website.signup.ApplicationResources">
            <table cellpadding="0" cellspacing="0">
                <tr><td colspan="4"><b><fmt:message key="signupTechnicalForm.stepLabel" /></b><br /><hr /></td></tr>
                <tr><td colspan="4"><fmt:message key="signupTechnicalForm.stepHelp" /><br /><br /></td></tr>
                <tr>
                    <td style="white-space:nowrap"><fmt:message key="signup.required" /></td>
                    <td style="white-space:nowrap"><fmt:message key="signupTechnicalForm.baName.prompt" /></td>
                    <td style="white-space:nowrap"><html:text size="32" property="baName" maxlength="255" /></td>
                    <td style="white-space:nowrap"><html:errors bundle="/signup/ApplicationResources" property="baName" /></td>
                </tr>
                <tr>
                    <td style="white-space:nowrap"><fmt:message key="signup.notRequired" /></td>
                    <td style="white-space:nowrap"><fmt:message key="signupTechnicalForm.baTitle.prompt" /></td>
                    <td style="white-space:nowrap"><html:text size="32" property="baTitle" maxlength="255" /></td>
                    <td style="white-space:nowrap"><html:errors bundle="/signup/ApplicationResources" property="baTitle" /></td>
                </tr>
                <tr>
                    <td style="white-space:nowrap"><fmt:message key="signup.required" /></td>
                    <td style="white-space:nowrap"><fmt:message key="signupTechnicalForm.baWorkPhone.prompt" /></td>
                    <td style="white-space:nowrap"><html:text size="18" property="baWorkPhone" maxlength="255" /></td>
                    <td style="white-space:nowrap"><html:errors bundle="/signup/ApplicationResources" property="baWorkPhone" /></td>
                </tr>
                <tr>
                    <td style="white-space:nowrap"><fmt:message key="signup.notRequired" /></td>
                    <td style="white-space:nowrap"><fmt:message key="signupTechnicalForm.baCellPhone.prompt" /></td>
                    <td style="white-space:nowrap"><html:text size="18" property="baCellPhone" maxlength="255" /></td>
                    <td style="white-space:nowrap"><html:errors bundle="/signup/ApplicationResources" property="baCellPhone" /></td>
                </tr>
                <tr>
                    <td style="white-space:nowrap"><fmt:message key="signup.notRequired" /></td>
                    <td style="white-space:nowrap"><fmt:message key="signupTechnicalForm.baHomePhone.prompt" /></td>
                    <td style="white-space:nowrap"><html:text size="18" property="baHomePhone" maxlength="255" /></td>
                    <td style="white-space:nowrap"><html:errors bundle="/signup/ApplicationResources" property="baHomePhone" /></td>
                </tr>
                <tr>
                    <td style="white-space:nowrap"><fmt:message key="signup.notRequired" /></td>
                    <td style="white-space:nowrap"><fmt:message key="signupTechnicalForm.baFax.prompt" /></td>
                    <td style="white-space:nowrap"><html:text size="18" property="baFax" maxlength="255" /></td>
                    <td style="white-space:nowrap"><html:errors bundle="/signup/ApplicationResources" property="baFax" /></td>
                </tr>
                <tr>
                    <td style="white-space:nowrap"><fmt:message key="signup.required" /></td>
                    <td style="white-space:nowrap"><fmt:message key="signupTechnicalForm.baEmail.prompt" /></td>
                    <td style="white-space:nowrap"><html:text size="20" property="baEmail" maxlength="255" /></td>
                    <td style="white-space:nowrap"><html:errors bundle="/signup/ApplicationResources" property="baEmail" /></td>
                </tr>
                <tr><td colspan="4"><br /><fmt:message key="signupTechnicalForm.addressHelp" /><br /><br /></td></tr>
                <tr>
                    <td style="white-space:nowrap"><fmt:message key="signup.notRequired" /></td>
                    <td style="white-space:nowrap"><fmt:message key="signupTechnicalForm.baAddress1.prompt" /></td>
                    <td style="white-space:nowrap"><html:text size="32" property="baAddress1" maxlength="255" /></td>
                    <td style="white-space:nowrap"><html:errors bundle="/signup/ApplicationResources" property="baAddress1" /></td>
                </tr>
                <tr>
                    <td style="white-space:nowrap"><fmt:message key="signup.notRequired" /></td>
                    <td style="white-space:nowrap"><fmt:message key="signupTechnicalForm.baAddress2.prompt" /></td>
                    <td style="white-space:nowrap"><html:text size="32" property="baAddress2" maxlength="255" /></td>
                    <td style="white-space:nowrap"><html:errors bundle="/signup/ApplicationResources" property="baAddress2" /></td>
                </tr>
                <tr>
                    <td style="white-space:nowrap"><fmt:message key="signup.notRequired" /></td>
                    <td style="white-space:nowrap"><fmt:message key="signupTechnicalForm.baCity.prompt" /></td>
                    <td style="white-space:nowrap"><html:text size="16" property="baCity" maxlength="255" /></td>
                    <td style="white-space:nowrap"><html:errors bundle="/signup/ApplicationResources" property="baCity" /></td>
                </tr>
                <tr>
                    <td style="white-space:nowrap"><fmt:message key="signup.notRequired" /></td>
                    <td style="white-space:nowrap"><fmt:message key="signupTechnicalForm.baState.prompt" /></td>
                    <td style="white-space:nowrap"><html:text size="5" property="baState" maxlength="255" /></td>
                    <td style="white-space:nowrap"><html:errors bundle="/signup/ApplicationResources" property="baState" /></td>
                </tr>
                <tr>
                    <td style="white-space:nowrap"><fmt:message key="signup.notRequired" /></td>
                    <td style="white-space:nowrap"><fmt:message key="signupTechnicalForm.baCountry.prompt" /></td>
                    <td style="white-space:nowrap">
                        <html:select property="baCountry">
                            <bean:define id="didOne" type="java.lang.String" value="false" />
                            <bean:define name="signupTechnicalForm" property="baCountry" id="baCountry" type="java.lang.String" />
                            <logic:iterate scope="request" name="countryOptions" id="countryOption">
                                <logic:equal name="countryOption" property="code" value="<%= baCountry %>">
                                    <% if(!didOne.equals("true")) { %>
                                        <option value='<ao:write name="countryOption" property="code" />' selected="selected"><ao:write name="countryOption" property="name" /></option>
                                        <% didOne = "true"; %>
                                    <% } else { %>
                                        <option value='<ao:write name="countryOption" property="code" />'><ao:write name="countryOption" property="name" /></option>
                                    <% } %>
                                </logic:equal>
                                <logic:notEqual name="countryOption" property="code" value="<%= baCountry %>">
                                    <option value='<ao:write name="countryOption" property="code" />'><ao:write name="countryOption" property="name" /></option>
                                </logic:notEqual>
                            </logic:iterate>
                        </html:select>
                    </td>
                    <td style="white-space:nowrap"><html:errors bundle="/signup/ApplicationResources" property="baCountry" /></td>
                </tr>
                <tr>
                    <td style="white-space:nowrap"><fmt:message key="signup.notRequired" /></td>
                    <td style="white-space:nowrap"><fmt:message key="signupTechnicalForm.baZip.prompt" /></td>
                    <td style="white-space:nowrap"><html:text size="10" property="baZip" maxlength="255" /></td>
                    <td style="white-space:nowrap"><html:errors bundle="/signup/ApplicationResources" property="baZip" /></td>
                </tr>
                <tr><td colspan="4">&#160;</td><td></td></tr>
                <tr>
                    <td style="white-space:nowrap"><fmt:message key="signup.required" /></td>
                    <td style="white-space:nowrap"><fmt:message key="signupTechnicalForm.baUsername.prompt" /></td>
                    <td style="white-space:nowrap"><html:text size="14" property="baUsername" maxlength="255" /></td>
                    <td style="white-space:nowrap"><html:errors bundle="/signup/ApplicationResources" property="baUsername" /></td>
                </tr>
                <tr>
                    <td style="white-space:nowrap"><fmt:message key="signup.notRequired" /></td>
                    <td style="white-space:nowrap"><fmt:message key="signupTechnicalForm.baPassword.prompt" /></td>
                    <td style="white-space:nowrap">
                        <html:select property="baPassword">
                            <html:options name="passwords" />
                        </html:select>
                    </td>
                    <td style="white-space:nowrap"><html:errors bundle="/signup/ApplicationResources" property="baPassword" /></td>
                </tr>
                <tr><td colspan="4" align="center"><br /><ao:input type="submit"><fmt:message key="signupTechnicalForm.submit.label" /></ao:input><br /><br /></td></tr>
            </table>
        </fmt:bundle>
    </skin:lightArea>
</div>
