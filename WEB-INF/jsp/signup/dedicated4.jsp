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
    <skin:path>/signup/dedicated4.do</skin:path>
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
                <bean:define toScope="request" type="java.lang.String" id="stepNumber" value="4"/>
                <%@ include file="dedicatedSteps.jsp" %>
                <br>
                <html:form action="/dedicated4Completed.do">
                    <skin:lightArea>
                         <TABLE BORDER="0" CELLPADDING="0" CELLSPACING="0">
                            <TR><TD COLSPAN="4"><B><bean:message bundle="/signup/ApplicationResources" key="dedicated4.stepLabel"/></B><BR><HR></TD></TR>
                            <TR><TD COLSPAN="4"><bean:message bundle="/signup/ApplicationResources" key="dedicated4.stepHelp"/><BR><BR></TD></TR>
                            <TR>
                                <TD NOWRAP><bean:message bundle="/signup/ApplicationResources" key="signup.required"/></TD>
                                <TD NOWRAP><bean:message bundle="/signup/ApplicationResources" key="signupTechnicalForm.baName.prompt"/></TD>
                                <TD NOWRAP><html:text size="32" property="baName" maxlength="255"/></TD>
                                <TD NOWRAP><html:errors bundle="/signup/ApplicationResources" property="baName"/></TD>
                            </TR>
                            <TR>
                                <TD NOWRAP><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired"/></TD>
                                <TD NOWRAP><bean:message bundle="/signup/ApplicationResources" key="signupTechnicalForm.baTitle.prompt"/></TD>
                                <TD NOWRAP><html:text size="32" property="baTitle" maxlength="255"/></TD>
                                <TD NOWRAP><html:errors bundle="/signup/ApplicationResources" property="baTitle"/></TD>
                            </TR>
                            <TR>
                                <TD NOWRAP><bean:message bundle="/signup/ApplicationResources" key="signup.required"/></TD>
                                <TD NOWRAP><bean:message bundle="/signup/ApplicationResources" key="signupTechnicalForm.baWorkPhone.prompt"/></TD>
                                <TD NOWRAP><html:text size="18" property="baWorkPhone" maxlength="255"/></TD>
                                <TD NOWRAP><html:errors bundle="/signup/ApplicationResources" property="baWorkPhone"/></TD>
                            </TR>
                            <TR>
                                <TD NOWRAP><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired"/></TD>
                                <TD NOWRAP><bean:message bundle="/signup/ApplicationResources" key="signupTechnicalForm.baCellPhone.prompt"/></TD>
                                <TD NOWRAP><html:text size="18" property="baCellPhone" maxlength="255"/></TD>
                                <TD NOWRAP><html:errors bundle="/signup/ApplicationResources" property="baCellPhone"/></TD>
                            </TR>
                            <TR>
                                <TD NOWRAP><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired"/></TD>
                                <TD NOWRAP><bean:message bundle="/signup/ApplicationResources" key="signupTechnicalForm.baHomePhone.prompt"/></TD>
                                <TD NOWRAP><html:text size="18" property="baHomePhone" maxlength="255"/></TD>
                                <TD NOWRAP><html:errors bundle="/signup/ApplicationResources" property="baHomePhone"/></TD>
                            </TR>
                            <TR>
                                <TD NOWRAP><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired"/></TD>
                                <TD NOWRAP><bean:message bundle="/signup/ApplicationResources" key="signupTechnicalForm.baFax.prompt"/></TD>
                                <TD NOWRAP><html:text size="18" property="baFax" maxlength="255"/></TD>
                                <TD NOWRAP><html:errors bundle="/signup/ApplicationResources" property="baFax"/></TD>
                            </TR>
                            <TR>
                                <TD NOWRAP><bean:message bundle="/signup/ApplicationResources" key="signup.required"/></TD>
                                <TD NOWRAP><bean:message bundle="/signup/ApplicationResources" key="signupTechnicalForm.baEmail.prompt"/></TD>
                                <TD NOWRAP><html:text size="20" property="baEmail" maxlength="255"/></TD>
                                <TD NOWRAP><html:errors bundle="/signup/ApplicationResources" property="baEmail"/></TD>
                            </TR>
                            <TR><TD COLSPAN="4"><BR><bean:message bundle="/signup/ApplicationResources" key="dedicated4.addressHelp"/><BR><BR></TD></TR>
                            <TR>
                                <TD NOWRAP><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired"/></TD>
                                <TD NOWRAP><bean:message bundle="/signup/ApplicationResources" key="signupTechnicalForm.baAddress1.prompt"/></TD>
                                <TD NOWRAP><html:text size="32" property="baAddress1" maxlength="255"/></TD>
                                <TD NOWRAP><html:errors bundle="/signup/ApplicationResources" property="baAddress1"/></TD>
                            </TR>
                            <TR>
                                <TD NOWRAP><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired"/></TD>
                                <TD NOWRAP><bean:message bundle="/signup/ApplicationResources" key="signupTechnicalForm.baAddress2.prompt"/></TD>
                                <TD NOWRAP><html:text size="32" property="baAddress2" maxlength="255"/></TD>
                                <TD NOWRAP><html:errors bundle="/signup/ApplicationResources" property="baAddress2"/></TD>
                            </TR>
                            <TR>
                                <TD NOWRAP><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired"/></TD>
                                <TD NOWRAP><bean:message bundle="/signup/ApplicationResources" key="signupTechnicalForm.baCity.prompt"/></TD>
                                <TD NOWRAP><html:text size="16" property="baCity" maxlength="255"/></TD>
                                <TD NOWRAP><html:errors bundle="/signup/ApplicationResources" property="baCity"/></TD>
                            </TR>
                            <TR>
                                <TD NOWRAP><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired"/></TD>
                                <TD NOWRAP><bean:message bundle="/signup/ApplicationResources" key="signupTechnicalForm.baState.prompt"/></TD>
                                <TD NOWRAP><html:text size="5" property="baState" maxlength="255"/></TD>
                                <TD NOWRAP><html:errors bundle="/signup/ApplicationResources" property="baState"/></TD>
                            </TR>
                            <TR>
                                <TD NOWRAP><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired"/></TD>
                                <TD NOWRAP><bean:message bundle="/signup/ApplicationResources" key="signupTechnicalForm.baCountry.prompt"/></TD>
                                <TD NOWRAP>
                                    <html:select property="baCountry">
                                        <bean:define id="didOne" type="java.lang.String" value="false"/>
                                        <bean:define name="signupTechnicalForm" property="baCountry" id="baCountry" type="java.lang.String"/>
                                        <logic:iterate scope="request" name="countryOptions" id="countryOption">
                                            <logic:equal name="countryOption" property="code" value="<%= baCountry %>">
                                                <% if(!didOne.equals("true")) { %>
                                                    <option value='<bean:write name="countryOption" property="code"/>' selected><bean:write name="countryOption" property="name"/></option>
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
                                </TD>
                                <TD NOWRAP><html:errors bundle="/signup/ApplicationResources" property="baCountry"/></TD>
                            </TR>
                            <TR>
                                <TD NOWRAP><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired"/></TD>
                                <TD NOWRAP><bean:message bundle="/signup/ApplicationResources" key="signupTechnicalForm.baZip.prompt"/></TD>
                                <TD NOWRAP><html:text size="10" property="baZip" maxlength="255"/></TD>
                                <TD NOWRAP><html:errors bundle="/signup/ApplicationResources" property="baZip"/></TD>
                            </TR>
                            <TR><TD colspan="4">&nbsp;</TD><TD></TD></TR>
                            <TR>
                                <TD NOWRAP><bean:message bundle="/signup/ApplicationResources" key="signup.required"/></TD>
                                <TD NOWRAP><bean:message bundle="/signup/ApplicationResources" key="signupTechnicalForm.baUsername.prompt"/></TD>
                                <TD NOWRAP><html:text size="14" property="baUsername" maxlength="255"/></TD>
                                <TD NOWRAP><html:errors bundle="/signup/ApplicationResources" property="baUsername"/></TD>
                            </TR>
                            <TR>
                                <TD NOWRAP><bean:message bundle="/signup/ApplicationResources" key="signup.notRequired"/></TD>
                                <TD NOWRAP><bean:message bundle="/signup/ApplicationResources" key="signupTechnicalForm.baPassword.prompt"/></TD>
                                <TD NOWRAP>
                                    <html:select property="baPassword">
                                        <html:options name="passwords"/>
                                    </html:select>
                                </TD>
                                <TD NOWRAP><html:errors bundle="/signup/ApplicationResources" property="baPassword"/></TD>
                            </TR>
                            <TR><TD colspan="4" align="center"><br><html:submit styleClass='ao_button'><bean:message bundle="/signup/ApplicationResources" key="dedicated4.submit.label"/></html:submit><br><br></td></tr>
                        </TABLE>
                    </skin:lightArea>
                </html:form>
            </skin:contentLine>
        </skin:content>
    </skin:skin>
</html:html>
