<%-- aoweb-struts: Do not edit --%>
<%--
  Copyright 2007-2009 by AO Industries, Inc.,
  7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" buffer="256kb" autoFlush="true" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/taglibs.jsp" %>

<skin:lightArea>
    <b><bean:message bundle="/signup/ApplicationResources" key="steps.title" /></b>
    <hr />
    <bean:define scope="request" name="signupSelectServerFormComplete" id="signupSelectServerFormComplete" type="java.lang.String" />
    <bean:define scope="request" name="signupCustomizeServerFormComplete" id="signupCustomizeServerFormComplete" type="java.lang.String" />
    <bean:define scope="request" name="signupCustomizeManagementFormComplete" id="signupCustomizeManagementFormComplete" type="java.lang.String" />
    <bean:define scope="request" name="signupBusinessFormComplete" id="signupBusinessFormComplete" type="java.lang.String" />
    <bean:define scope="request" name="signupTechnicalFormComplete" id="signupTechnicalFormComplete" type="java.lang.String" />
    <bean:define scope="request" name="signupBillingInformationFormComplete" id="signupBillingInformationFormComplete" type="java.lang.String" />
    <bean:define name="stepNumber" id="myStepNumber" type="java.lang.String" />
    <table cellspacing='2' cellpadding='0'>
        <tr>
            <td>
                <logic:equal name="myStepNumber" value="1"><bean:message bundle="/signup/ApplicationResources" key="steps.arrow" /></logic:equal>
                <logic:notEqual name="myStepNumber" value="1">&#160;</logic:notEqual>
            </td>
            <td><bean:message bundle="/signup/ApplicationResources" key="steps.1" /></td>
            <td>
                <% if(myStepNumber.equals("8")) { %>
                    <bean:message bundle="/signup/ApplicationResources" key="steps.selectServer.label" />
                <% } else { %>
                    <a class="aoDarkLink" href="javascript:selectStep('<bean:write scope="request" name="actionPrefix" />');"><bean:message bundle="/signup/ApplicationResources" key="steps.selectServer.label" /></a>
                <% } %>
            </td>
            <td>
                <logic:equal scope="request" name="signupSelectServerFormComplete" value="true">
                    <bean:message bundle="/signup/ApplicationResources" key="steps.completed" />
                </logic:equal>
                <logic:notEqual scope="request" name="signupSelectServerFormComplete" value="true">
                    <bean:message bundle="/signup/ApplicationResources" key="steps.incomplete" />
                </logic:notEqual>
            </td>
        </tr>
        <tr>
            <td>
                <logic:equal name="myStepNumber" value="2"><bean:message bundle="/signup/ApplicationResources" key="steps.arrow" /></logic:equal>
                <logic:notEqual name="myStepNumber" value="2">&#160;</logic:notEqual>
            </td>
            <td><bean:message bundle="/signup/ApplicationResources" key="steps.2" /></td>
            <td>
                <% if(myStepNumber.equals("8") || !signupSelectServerFormComplete.equals("true")) { %>
                    <bean:message bundle="/signup/ApplicationResources" key="steps.customizeServer.label" />
                <% } else { %>
                    <a class="aoDarkLink" href="javascript:selectStep('<bean:write scope="request" name="actionPrefix" />-2');"><bean:message bundle="/signup/ApplicationResources" key="steps.customizeServer.label" /></a>
                <% } %>
            </td>
            <td>
                <logic:equal scope="request" name="signupCustomizeServerFormComplete" value="true">
                    <bean:message bundle="/signup/ApplicationResources" key="steps.completed" />
                </logic:equal>
                <logic:notEqual scope="request" name="signupCustomizeServerFormComplete" value="true">
                    <bean:message bundle="/signup/ApplicationResources" key="steps.incomplete" />
                </logic:notEqual>
            </td>
        </tr>
        <tr>
            <td>
                <logic:equal name="myStepNumber" value="3"><bean:message bundle="/signup/ApplicationResources" key="steps.arrow" /></logic:equal>
                <logic:notEqual name="myStepNumber" value="3">&#160;</logic:notEqual>
            </td>
            <td><bean:message bundle="/signup/ApplicationResources" key="steps.3" /></td>
            <td>
                <% if(
                        myStepNumber.equals("8")
                        || !signupSelectServerFormComplete.equals("true")
                        || !signupCustomizeServerFormComplete.equals("true")
                   ) { %>
                    <bean:message bundle="/signup/ApplicationResources" key="steps.customizeManagement.label" />
                <% } else { %>
                    <a class="aoDarkLink" href="javascript:selectStep('<bean:write scope="request" name="actionPrefix" />-3');"><bean:message bundle="/signup/ApplicationResources" key="steps.customizeManagement.label" /></a>
                <% } %>
            </td>
            <td>
                <logic:equal scope="request" name="signupCustomizeManagementFormComplete" value="true">
                    <bean:message bundle="/signup/ApplicationResources" key="steps.completed" />
                </logic:equal>
                <logic:notEqual scope="request" name="signupCustomizeManagementFormComplete" value="true">
                    <bean:message bundle="/signup/ApplicationResources" key="steps.incomplete" />
                </logic:notEqual>
            </td>
        </tr>
        <tr>
            <td>
                <logic:equal name="myStepNumber" value="4"><bean:message bundle="/signup/ApplicationResources" key="steps.arrow" /></logic:equal>
                <logic:notEqual name="myStepNumber" value="4">&#160;</logic:notEqual>
            </td>
            <td><bean:message bundle="/signup/ApplicationResources" key="steps.4" /></td>
            <td>
                <% if(
                        myStepNumber.equals("8")
                        || !signupSelectServerFormComplete.equals("true")
                        || !signupCustomizeServerFormComplete.equals("true")
                        || !signupCustomizeManagementFormComplete.equals("true")
                   ) { %>
                    <bean:message bundle="/signup/ApplicationResources" key="steps.businessInfo.label" />
                <% } else { %>
                    <a class="aoDarkLink" href="javascript:selectStep('<bean:write scope="request" name="actionPrefix" />-4');"><bean:message bundle="/signup/ApplicationResources" key="steps.businessInfo.label" /></a>
                <% } %>
            </td>
            <td>
                <logic:equal scope="request" name="signupBusinessFormComplete" value="true">
                    <bean:message bundle="/signup/ApplicationResources" key="steps.completed" />
                </logic:equal>
                <logic:notEqual scope="request" name="signupBusinessFormComplete" value="true">
                    <bean:message bundle="/signup/ApplicationResources" key="steps.incomplete" />
                </logic:notEqual>
            </td>
        </tr>
        <tr>
            <td>
                <logic:equal name="myStepNumber" value="5"><bean:message bundle="/signup/ApplicationResources" key="steps.arrow" /></logic:equal>
                <logic:notEqual name="myStepNumber" value="5">&#160;</logic:notEqual>
            </td>
            <td><bean:message bundle="/signup/ApplicationResources" key="steps.5" /></td>
            <td>
                <% if(
                        myStepNumber.equals("8")
                        || !signupSelectServerFormComplete.equals("true")
                        || !signupCustomizeServerFormComplete.equals("true")
                        || !signupCustomizeManagementFormComplete.equals("true")
                        || !signupBusinessFormComplete.equals("true")
                   ) { %>
                    <bean:message bundle="/signup/ApplicationResources" key="steps.technicalInfo.label" />
                <% } else { %>
                    <a class="aoDarkLink" href="javascript:selectStep('<bean:write scope="request" name="actionPrefix" />-5');"><bean:message bundle="/signup/ApplicationResources" key="steps.technicalInfo.label" /></a>
                <% } %>
            </td>
            <td>
                <logic:equal scope="request" name="signupTechnicalFormComplete" value="true">
                    <bean:message bundle="/signup/ApplicationResources" key="steps.completed" />
                </logic:equal>
                <logic:notEqual scope="request" name="signupTechnicalFormComplete" value="true">
                    <bean:message bundle="/signup/ApplicationResources" key="steps.incomplete" />
                </logic:notEqual>
            </td>
        </tr>
        <tr>
            <td>
                <logic:equal name="myStepNumber" value="6"><bean:message bundle="/signup/ApplicationResources" key="steps.arrow" /></logic:equal>
                <logic:notEqual name="myStepNumber" value="6">&#160;</logic:notEqual>
            </td>
            <td><bean:message bundle="/signup/ApplicationResources" key="steps.6" /></td>
            <td>
                <% if(
                        myStepNumber.equals("8")
                        || !signupSelectServerFormComplete.equals("true")
                        || !signupCustomizeServerFormComplete.equals("true")
                        || !signupCustomizeManagementFormComplete.equals("true")
                        || !signupBusinessFormComplete.equals("true")
                        || !signupTechnicalFormComplete.equals("true")
                   ) { %>
                    <bean:message bundle="/signup/ApplicationResources" key="steps.billingInformation.label" />
                <% } else { %>
                    <a class="aoDarkLink" href="javascript:selectStep('<bean:write scope="request" name="actionPrefix" />-6');"><bean:message bundle="/signup/ApplicationResources" key="steps.billingInformation.label" /></a>
                <% } %>
            </td>
            <td>
                <logic:equal scope="request" name="signupBillingInformationFormComplete" value="true">
                    <bean:message bundle="/signup/ApplicationResources" key="steps.completed" />
                </logic:equal>
                <logic:notEqual scope="request" name="signupBillingInformationFormComplete" value="true">
                    <bean:message bundle="/signup/ApplicationResources" key="steps.incomplete" />
                </logic:notEqual>
            </td>
        </tr>
        <tr>
            <td>
                <logic:equal name="myStepNumber" value="7"><bean:message bundle="/signup/ApplicationResources" key="steps.arrow" /></logic:equal>
                <logic:notEqual name="myStepNumber" value="7">&#160;</logic:notEqual>
            </td>
            <td><bean:message bundle="/signup/ApplicationResources" key="steps.7" /></td>
            <td>
                <% if(
                        myStepNumber.equals("8")
                        || !signupSelectServerFormComplete.equals("true")
                        || !signupCustomizeServerFormComplete.equals("true")
                        || !signupCustomizeManagementFormComplete.equals("true")
                        || !signupBusinessFormComplete.equals("true")
                        || !signupTechnicalFormComplete.equals("true")
                        || !signupBillingInformationFormComplete.equals("true")
                   ) { %>
                    <bean:message bundle="/signup/ApplicationResources" key="steps.confirmation.label" />
                <% } else { %>
                    <a class="aoDarkLink" href="javascript:selectStep('<bean:write scope="request" name="actionPrefix" />-7');"><bean:message bundle="/signup/ApplicationResources" key="steps.confirmation.label" /></a>
                <% } %>
            </td>
            <td>
                <logic:equal name="myStepNumber" value="8">
                    <bean:message bundle="/signup/ApplicationResources" key="steps.completed" />
                </logic:equal>
                <logic:notEqual name="myStepNumber" value="8">
                    <bean:message bundle="/signup/ApplicationResources" key="steps.incomplete" />
                </logic:notEqual>
            </td>
        </tr>
        <tr>
            <td>
                <logic:equal name="myStepNumber" value="8"><bean:message bundle="/signup/ApplicationResources" key="steps.arrow" /></logic:equal>
                <logic:notEqual name="myStepNumber" value="8">&#160;</logic:notEqual>
            </td>
            <td><bean:message bundle="/signup/ApplicationResources" key="steps.8" /></td>
            <td><bean:message bundle="/signup/ApplicationResources" key="steps.finished.label" /></td>
            <td>&#160;</td>
        </tr>
    </table>
</skin:lightArea>
