<%-- aoweb-struts --%>
<%--
  Copyright 2007-2009 by AO Industries, Inc.,
  7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" buffer="256kb" autoFlush="true" %>
<%@ include file="/WEB-INF/jsp/taglibs.jsp" %>

<input type="hidden" name="selectedStep" value="">
<script language="JavaScript1.2"><!--
    function selectStep(step) {
        var form = document.forms['signupBusinessForm'];
        form.selectedStep.value=step;
        form.submit();
    }
// --></script>
<skin:lightArea>
     <TABLE BORDER="0" CELLPADDING="0" CELLSPACING="0">
        <TR><TD COLSPAN="4"><B><bean:message bundle="/signup/ApplicationResources" key="signupBusinessForm.stepLabel"/></B><BR><HR></TD></TR>
        <TR><TD COLSPAN="4"><bean:message bundle="/signup/ApplicationResources" key="signupBusinessForm.stepHelp"/><BR><BR></TD></TR>
        <TR>
            <TD NOWRAP><bean:message bundle="/signup/ApplicationResources" key="signup.required"/></TD>
            <TD NOWRAP><bean:message bundle="/signup/ApplicationResources" key="signupBusinessForm.businessName.prompt"/></TD>
            <TD NOWRAP><html:text size="32" property="businessName" maxlength="255"/></TD>
            <TD NOWRAP><html:errors bundle="/signup/ApplicationResources" property="businessName"/></TD>
        </TR>
        <TR>
            <TD NOWRAP><bean:message bundle="/signup/ApplicationResources" key="signup.required"/></TD>
            <TD NOWRAP><bean:message bundle="/signup/ApplicationResources" key="signupBusinessForm.businessPhone.prompt"/></TD>
            <TD NOWRAP><html:text size="18" property="businessPhone" maxlength="255"/></TD>
            <TD NOWRAP><html:errors bundle="/signup/ApplicationResources" property="businessPhone"/></TD>
        </TR>
        <TR>
            <TD NOWRAP><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired"/></TD>
            <TD NOWRAP><bean:message bundle="/signup/ApplicationResources" key="signupBusinessForm.businessFax.prompt"/></TD>
            <TD NOWRAP><html:text size="18" property="businessFax" maxlength="255"/></TD>
            <TD NOWRAP><html:errors bundle="/signup/ApplicationResources" property="businessFax"/></TD>
        </TR>
        <TR><TD colspan="4">&nbsp;</TD></TR>
        <TR>
            <TD NOWRAP><bean:message bundle="/signup/ApplicationResources" key="signup.required"/></TD>
            <TD NOWRAP><bean:message bundle="/signup/ApplicationResources" key="signupBusinessForm.businessAddress1.prompt"/></TD>
            <TD NOWRAP><html:text size="32" property="businessAddress1" maxlength="255"/></TD>
            <TD NOWRAP><html:errors bundle="/signup/ApplicationResources" property="businessAddress1"/></TD>
        </TR>
        <TR>
            <TD NOWRAP><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired"/></TD>
            <TD NOWRAP><bean:message bundle="/signup/ApplicationResources" key="signupBusinessForm.businessAddress2.prompt"/></TD>
            <TD NOWRAP><html:text size="32" property="businessAddress2" maxlength="255"/></TD>
            <TD NOWRAP><html:errors bundle="/signup/ApplicationResources" property="businessAddress2"/></TD>
        </TR>
        <TR>
            <TD NOWRAP><bean:message bundle="/signup/ApplicationResources" key="signup.required"/></TD>
            <TD NOWRAP><bean:message bundle="/signup/ApplicationResources" key="signupBusinessForm.businessCity.prompt"/></TD>
            <TD NOWRAP><html:text size="16" property="businessCity" maxlength="255"/></TD>
            <TD NOWRAP><html:errors bundle="/signup/ApplicationResources" property="businessCity"/></TD>
        </TR>
        <TR>
            <TD NOWRAP><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired"/></TD>
            <TD NOWRAP><bean:message bundle="/signup/ApplicationResources" key="signupBusinessForm.businessState.prompt"/></TD>
            <TD NOWRAP><html:text size="5" property="businessState" maxlength="255"/></TD>
            <TD NOWRAP><html:errors bundle="/signup/ApplicationResources" property="businessState"/></TD>
        </TR>
        <TR>
            <TD NOWRAP><bean:message bundle="/signup/ApplicationResources" key="signup.required"/></TD>
            <TD NOWRAP><bean:message bundle="/signup/ApplicationResources" key="signupBusinessForm.businessCountry.prompt"/></TD>
            <TD NOWRAP>
                <html:select property="businessCountry">
                    <bean:define id="didOne" type="java.lang.String" value="false"/>
                    <bean:define name="signupBusinessForm" property="businessCountry" id="businessCountry" type="java.lang.String"/>
                    <logic:iterate scope="request" name="countryOptions" id="countryOption">
                        <logic:equal name="countryOption" property="code" value="<%= businessCountry %>">
                            <% if(!didOne.equals("true")) { %>
                                <option value='<bean:write name="countryOption" property="code"/>' selected><bean:write name="countryOption" property="name"/></option>
                                <% didOne = "true"; %>
                            <% } else { %>
                                <option value='<bean:write name="countryOption" property="code"/>'><bean:write name="countryOption" property="name"/></option>
                            <% } %>
                        </logic:equal>
                        <logic:notEqual name="countryOption" property="code" value="<%= businessCountry %>">
                            <option value='<bean:write name="countryOption" property="code"/>'><bean:write name="countryOption" property="name"/></option>
                        </logic:notEqual>
                    </logic:iterate>
                </html:select>
            </TD>
            <TD NOWRAP><html:errors bundle="/signup/ApplicationResources" property="businessCountry"/></TD>
        </TR>
        <TR>
            <TD NOWRAP><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired"/></TD>
            <TD NOWRAP><bean:message bundle="/signup/ApplicationResources" key="signupBusinessForm.businessZip.prompt"/></TD>
            <TD NOWRAP><html:text size="10" property="businessZip" maxlength="255"/></TD>
            <TD NOWRAP><html:errors bundle="/signup/ApplicationResources" property="businessZip"/></TD>
        </TR>
        <TR><TD colspan="4" align="center"><br><html:submit><bean:message bundle="/signup/ApplicationResources" key="signupBusinessForm.submit.label"/></html:submit><br><br></TD></TR>
     </TABLE>
</skin:lightArea>
