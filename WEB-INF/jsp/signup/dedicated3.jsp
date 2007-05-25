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
    <skin:path>/signup/dedicated3.do</skin:path>
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
                <script language="JavaScript1.2"><!--
                    function selectStep(step) {
                        var form = document.forms['signupBusinessForm'];
                        form.selectedStep.value=step;
                        form.submit();
                    }
                // --></script>
                <bean:define toScope="request" type="java.lang.String" id="stepNumber" value="3"/>
                <%@ include file="dedicatedSteps.jsp" %>
                <br>
                <html:form action="/dedicated3Completed.do">
                    <input type="hidden" name="selectedStep" value="">
                    <skin:lightArea>
                         <TABLE BORDER="0" CELLPADDING="0" CELLSPACING="0">
                            <TR><TD COLSPAN="4"><B><bean:message bundle="/signup/ApplicationResources" key="dedicated3.stepLabel"/></B><BR><HR></TD></TR>
                            <TR><TD COLSPAN="4"><bean:message bundle="/signup/ApplicationResources" key="dedicated3.stepHelp"/><BR><BR></TD></TR>
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
                            <TR><TD colspan="4" align="center"><br><html:submit styleClass='ao_button'><bean:message bundle="/signup/ApplicationResources" key="dedicated3.submit.label"/></html:submit><br><br></td></tr>
                         </TABLE>
                    </skin:lightArea>
                </html:form>
            </skin:contentLine>
        </skin:content>
    </skin:skin>
</html:html>
