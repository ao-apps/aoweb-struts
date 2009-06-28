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
    function selectStep(step) {
        var form = document.forms['signupTechnicalForm'];
        form.selectedStep.value=step;
        form.submit();
    }
</script>
<skin:lightArea>
    <table cellpadding="0" cellspacing="0">
        <tr><td COLSPAN="4"><b><bean:message bundle="/signup/ApplicationResources" key="signupTechnicalForm.stepLabel"/></b><br /><hr /></td></tr>
        <tr><td COLSPAN="4"><bean:message bundle="/signup/ApplicationResources" key="signupTechnicalForm.stepHelp"/><br /><br /></td></tr>
        <tr>
            <td style="white-space:nowrap"><bean:message bundle="/signup/ApplicationResources" key="signup.required"/></td>
            <td style="white-space:nowrap"><bean:message bundle="/signup/ApplicationResources" key="signupTechnicalForm.baName.prompt"/></td>
            <td style="white-space:nowrap"><html:text size="32" property="baName" maxlength="255"/></td>
            <td style="white-space:nowrap"><html:errors bundle="/signup/ApplicationResources" property="baName"/></td>
        </tr>
        <tr>
            <td style="white-space:nowrap"><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired"/></td>
            <td style="white-space:nowrap"><bean:message bundle="/signup/ApplicationResources" key="signupTechnicalForm.baTitle.prompt"/></td>
            <td style="white-space:nowrap"><html:text size="32" property="baTitle" maxlength="255"/></td>
            <td style="white-space:nowrap"><html:errors bundle="/signup/ApplicationResources" property="baTitle"/></td>
        </tr>
        <tr>
            <td style="white-space:nowrap"><bean:message bundle="/signup/ApplicationResources" key="signup.required"/></td>
            <td style="white-space:nowrap"><bean:message bundle="/signup/ApplicationResources" key="signupTechnicalForm.baWorkPhone.prompt"/></td>
            <td style="white-space:nowrap"><html:text size="18" property="baWorkPhone" maxlength="255"/></td>
            <td style="white-space:nowrap"><html:errors bundle="/signup/ApplicationResources" property="baWorkPhone"/></td>
        </tr>
        <tr>
            <td style="white-space:nowrap"><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired"/></td>
            <td style="white-space:nowrap"><bean:message bundle="/signup/ApplicationResources" key="signupTechnicalForm.baCellPhone.prompt"/></td>
            <td style="white-space:nowrap"><html:text size="18" property="baCellPhone" maxlength="255"/></td>
            <td style="white-space:nowrap"><html:errors bundle="/signup/ApplicationResources" property="baCellPhone"/></td>
        </tr>
        <tr>
            <td style="white-space:nowrap"><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired"/></td>
            <td style="white-space:nowrap"><bean:message bundle="/signup/ApplicationResources" key="signupTechnicalForm.baHomePhone.prompt"/></td>
            <td style="white-space:nowrap"><html:text size="18" property="baHomePhone" maxlength="255"/></td>
            <td style="white-space:nowrap"><html:errors bundle="/signup/ApplicationResources" property="baHomePhone"/></td>
        </tr>
        <tr>
            <td style="white-space:nowrap"><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired"/></td>
            <td style="white-space:nowrap"><bean:message bundle="/signup/ApplicationResources" key="signupTechnicalForm.baFax.prompt"/></td>
            <td style="white-space:nowrap"><html:text size="18" property="baFax" maxlength="255"/></td>
            <td style="white-space:nowrap"><html:errors bundle="/signup/ApplicationResources" property="baFax"/></td>
        </tr>
        <tr>
            <td style="white-space:nowrap"><bean:message bundle="/signup/ApplicationResources" key="signup.required"/></td>
            <td style="white-space:nowrap"><bean:message bundle="/signup/ApplicationResources" key="signupTechnicalForm.baEmail.prompt"/></td>
            <td style="white-space:nowrap"><html:text size="20" property="baEmail" maxlength="255"/></td>
            <td style="white-space:nowrap"><html:errors bundle="/signup/ApplicationResources" property="baEmail"/></td>
        </tr>
        <tr><td COLSPAN="4"><br /><bean:message bundle="/signup/ApplicationResources" key="signupTechnicalForm.addressHelp"/><br /><br /></td></tr>
        <tr>
            <td style="white-space:nowrap"><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired"/></td>
            <td style="white-space:nowrap"><bean:message bundle="/signup/ApplicationResources" key="signupTechnicalForm.baAddress1.prompt"/></td>
            <td style="white-space:nowrap"><html:text size="32" property="baAddress1" maxlength="255"/></td>
            <td style="white-space:nowrap"><html:errors bundle="/signup/ApplicationResources" property="baAddress1"/></td>
        </tr>
        <tr>
            <td style="white-space:nowrap"><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired"/></td>
            <td style="white-space:nowrap"><bean:message bundle="/signup/ApplicationResources" key="signupTechnicalForm.baAddress2.prompt"/></td>
            <td style="white-space:nowrap"><html:text size="32" property="baAddress2" maxlength="255"/></td>
            <td style="white-space:nowrap"><html:errors bundle="/signup/ApplicationResources" property="baAddress2"/></td>
        </tr>
        <tr>
            <td style="white-space:nowrap"><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired"/></td>
            <td style="white-space:nowrap"><bean:message bundle="/signup/ApplicationResources" key="signupTechnicalForm.baCity.prompt"/></td>
            <td style="white-space:nowrap"><html:text size="16" property="baCity" maxlength="255"/></td>
            <td style="white-space:nowrap"><html:errors bundle="/signup/ApplicationResources" property="baCity"/></td>
        </tr>
        <tr>
            <td style="white-space:nowrap"><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired"/></td>
            <td style="white-space:nowrap"><bean:message bundle="/signup/ApplicationResources" key="signupTechnicalForm.baState.prompt"/></td>
            <td style="white-space:nowrap"><html:text size="5" property="baState" maxlength="255"/></td>
            <td style="white-space:nowrap"><html:errors bundle="/signup/ApplicationResources" property="baState"/></td>
        </tr>
        <tr>
            <td style="white-space:nowrap"><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired"/></td>
            <td style="white-space:nowrap"><bean:message bundle="/signup/ApplicationResources" key="signupTechnicalForm.baCountry.prompt"/></td>
            <td style="white-space:nowrap">
                <html:select property="baCountry">
                    <bean:define id="didOne" type="java.lang.String" value="false"/>
                    <bean:define name="signupTechnicalForm" property="baCountry" id="baCountry" type="java.lang.String"/>
                    <logic:iterate scope="request" name="countryOptions" id="countryOption">
                        <logic:equal name="countryOption" property="code" value="<%= baCountry %>">
                            <% if(!didOne.equals("true")) { %>
                                <option value='<bean:write name="countryOption" property="code"/>'selected="selected"><bean:write name="countryOption" property="name"/></option>
                                <% didOne = "true"; %>
                            <% } else { %>
                                <option value='<bean:write name="countryOption" property="code"/>'><bean:write name="countryOption" property="name"/></option>
                            <% } %>
                        </logic:equal>
                        <logic:notEqual name="countryOption" property="code" value="<%= baCountry %>">
                            <option value='<bean:write name="countryOption" property="code"/>'><bean:write name="countryOption" property="name"/></option>
                        </logic:notEqual>
                    </logic:iterate>
                </html:select>
            </td>
            <td style="white-space:nowrap"><html:errors bundle="/signup/ApplicationResources" property="baCountry"/></td>
        </tr>
        <tr>
            <td style="white-space:nowrap"><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired"/></td>
            <td style="white-space:nowrap"><bean:message bundle="/signup/ApplicationResources" key="signupTechnicalForm.baZip.prompt"/></td>
            <td style="white-space:nowrap"><html:text size="10" property="baZip" maxlength="255"/></td>
            <td style="white-space:nowrap"><html:errors bundle="/signup/ApplicationResources" property="baZip"/></td>
        </tr>
        <tr><td colspan="4">&nbsp;</td><td></td></tr>
        <tr>
            <td style="white-space:nowrap"><bean:message bundle="/signup/ApplicationResources" key="signup.required"/></td>
            <td style="white-space:nowrap"><bean:message bundle="/signup/ApplicationResources" key="signupTechnicalForm.baUsername.prompt"/></td>
            <td style="white-space:nowrap"><html:text size="14" property="baUsername" maxlength="255"/></td>
            <td style="white-space:nowrap"><html:errors bundle="/signup/ApplicationResources" property="baUsername"/></td>
        </tr>
        <tr>
            <td style="white-space:nowrap"><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired"/></td>
            <td style="white-space:nowrap"><bean:message bundle="/signup/ApplicationResources" key="signupTechnicalForm.baPassword.prompt"/></td>
            <td style="white-space:nowrap">
                <html:select property="baPassword">
                    <html:options name="passwords"/>
                </html:select>
            </td>
            <td style="white-space:nowrap"><html:errors bundle="/signup/ApplicationResources" property="baPassword"/></td>
        </tr>
        <tr><td colspan="4" align="center"><br /><html:submit><bean:message bundle="/signup/ApplicationResources" key="signupTechnicalForm.submit.label"/></html:submit><br /><br /></td></tr>
    </table>
</skin:lightArea>
