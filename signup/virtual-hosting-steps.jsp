<%-- aoweb-struts: Do not edit --%>
<%--
  Copyright 2009 by AO Industries, Inc.,
  7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@include file="/_taglibs.jsp" %>

<skin:lightArea>
    <fmt:bundle basename="com.aoindustries.website.signup.ApplicationResources">
        <b><fmt:message key="steps.title" /></b>
        <hr />
        <bean:define scope="request" name="signupSelectPackageFormComplete" id="signupSelectPackageFormComplete" type="java.lang.String" />
        <bean:define scope="request" name="signupDomainFormComplete" id="signupDomainFormComplete" type="java.lang.String" />
        <bean:define scope="request" name="signupBusinessFormComplete" id="signupBusinessFormComplete" type="java.lang.String" />
        <bean:define scope="request" name="signupTechnicalFormComplete" id="signupTechnicalFormComplete" type="java.lang.String" />
        <bean:define scope="request" name="signupBillingInformationFormComplete" id="signupBillingInformationFormComplete" type="java.lang.String" />
        <bean:define name="stepNumber" id="myStepNumber" type="java.lang.String" />
        <table cellspacing='2' cellpadding='0'>
            <tr>
                <td>
                    <logic:equal name="myStepNumber" value="1"><fmt:message key="steps.arrow" /></logic:equal>
                    <logic:notEqual name="myStepNumber" value="1">&#160;</logic:notEqual>
                </td>
                <td><fmt:message key="steps.1" /></td>
                <td>
                    <% if(myStepNumber.equals("7")) { %>
                        <fmt:message key="steps.selectPackage.label" />
                    <% } else { %>
                        <a class="aoDarkLink" href="javascript:selectStep('<ao:write scope="request" name="actionPrefix" />');"><fmt:message key="steps.selectPackage.label" /></a>
                    <% } %>
                </td>
                <td>
                    <logic:equal scope="request" name="signupSelectPackageFormComplete" value="true">
                        <fmt:message key="steps.completed" />
                    </logic:equal>
                    <logic:notEqual scope="request" name="signupSelectPackageFormComplete" value="true">
                        <fmt:message key="steps.incomplete" />
                    </logic:notEqual>
                </td>
            </tr>
            <tr>
                <td>
                    <logic:equal name="myStepNumber" value="2"><fmt:message key="steps.arrow" /></logic:equal>
                    <logic:notEqual name="myStepNumber" value="2">&#160;</logic:notEqual>
                </td>
                <td><fmt:message key="steps.2" /></td>
                <td>
                    <% if(myStepNumber.equals("7") || !signupSelectPackageFormComplete.equals("true")) { %>
                        <fmt:message key="steps.selectDomain.label" />
                    <% } else { %>
                        <a class="aoDarkLink" href="javascript:selectStep('<ao:write scope="request" name="actionPrefix" />-2');"><fmt:message key="steps.selectDomain.label" /></a>
                    <% } %>
                </td>
                <td>
                    <logic:equal scope="request" name="signupDomainFormComplete" value="true">
                        <fmt:message key="steps.completed" />
                    </logic:equal>
                    <logic:notEqual scope="request" name="signupDomainFormComplete" value="true">
                        <fmt:message key="steps.incomplete" />
                    </logic:notEqual>
                </td>
            </tr>
            <tr>
                <td>
                    <logic:equal name="myStepNumber" value="3"><fmt:message key="steps.arrow" /></logic:equal>
                    <logic:notEqual name="myStepNumber" value="3">&#160;</logic:notEqual>
                </td>
                <td><fmt:message key="steps.3" /></td>
                <td>
                    <% if(
                            myStepNumber.equals("7")
                            || !signupSelectPackageFormComplete.equals("true")
                            || !signupDomainFormComplete.equals("true")
                       ) { %>
                        <fmt:message key="steps.businessInfo.label" />
                    <% } else { %>
                        <a class="aoDarkLink" href="javascript:selectStep('<ao:write scope="request" name="actionPrefix" />-3');"><fmt:message key="steps.businessInfo.label" /></a>
                    <% } %>
                </td>
                <td>
                    <logic:equal scope="request" name="signupBusinessFormComplete" value="true">
                        <fmt:message key="steps.completed" />
                    </logic:equal>
                    <logic:notEqual scope="request" name="signupBusinessFormComplete" value="true">
                        <fmt:message key="steps.incomplete" />
                    </logic:notEqual>
                </td>
            </tr>
            <tr>
                <td>
                    <logic:equal name="myStepNumber" value="4"><fmt:message key="steps.arrow" /></logic:equal>
                    <logic:notEqual name="myStepNumber" value="4">&#160;</logic:notEqual>
                </td>
                <td><fmt:message key="steps.4" /></td>
                <td>
                    <% if(
                            myStepNumber.equals("7")
                            || !signupSelectPackageFormComplete.equals("true")
                            || !signupDomainFormComplete.equals("true")
                            || !signupBusinessFormComplete.equals("true")
                       ) { %>
                        <fmt:message key="steps.technicalInfo.label" />
                    <% } else { %>
                        <a class="aoDarkLink" href="javascript:selectStep('<ao:write scope="request" name="actionPrefix" />-4');"><fmt:message key="steps.technicalInfo.label" /></a>
                    <% } %>
                </td>
                <td>
                    <logic:equal scope="request" name="signupTechnicalFormComplete" value="true">
                        <fmt:message key="steps.completed" />
                    </logic:equal>
                    <logic:notEqual scope="request" name="signupTechnicalFormComplete" value="true">
                        <fmt:message key="steps.incomplete" />
                    </logic:notEqual>
                </td>
            </tr>
            <tr>
                <td>
                    <logic:equal name="myStepNumber" value="5"><fmt:message key="steps.arrow" /></logic:equal>
                    <logic:notEqual name="myStepNumber" value="5">&#160;</logic:notEqual>
                </td>
                <td><fmt:message key="steps.5" /></td>
                <td>
                    <% if(
                            myStepNumber.equals("7")
                            || !signupSelectPackageFormComplete.equals("true")
                            || !signupDomainFormComplete.equals("true")
                            || !signupBusinessFormComplete.equals("true")
                            || !signupTechnicalFormComplete.equals("true")
                       ) { %>
                        <fmt:message key="steps.billingInformation.label" />
                    <% } else { %>
                        <a class="aoDarkLink" href="javascript:selectStep('<ao:write scope="request" name="actionPrefix" />-5');"><fmt:message key="steps.billingInformation.label" /></a>
                    <% } %>
                </td>
                <td>
                    <logic:equal scope="request" name="signupBillingInformationFormComplete" value="true">
                        <fmt:message key="steps.completed" />
                    </logic:equal>
                    <logic:notEqual scope="request" name="signupBillingInformationFormComplete" value="true">
                        <fmt:message key="steps.incomplete" />
                    </logic:notEqual>
                </td>
            </tr>
            <tr>
                <td>
                    <logic:equal name="myStepNumber" value="6"><fmt:message key="steps.arrow" /></logic:equal>
                    <logic:notEqual name="myStepNumber" value="6">&#160;</logic:notEqual>
                </td>
                <td><fmt:message key="steps.6" /></td>
                <td>
                    <% if(
                            myStepNumber.equals("7")
                            || !signupSelectPackageFormComplete.equals("true")
                            || !signupDomainFormComplete.equals("true")
                            || !signupBusinessFormComplete.equals("true")
                            || !signupTechnicalFormComplete.equals("true")
                            || !signupBillingInformationFormComplete.equals("true")
                       ) { %>
                        <fmt:message key="steps.confirmation.label" />
                    <% } else { %>
                        <a class="aoDarkLink" href="javascript:selectStep('<ao:write scope="request" name="actionPrefix" />-6');"><fmt:message key="steps.confirmation.label" /></a>
                    <% } %>
                </td>
                <td>
                    <logic:equal name="myStepNumber" value="7">
                        <fmt:message key="steps.completed" />
                    </logic:equal>
                    <logic:notEqual name="myStepNumber" value="7">
                        <fmt:message key="steps.incomplete" />
                    </logic:notEqual>
                </td>
            </tr>
            <tr>
                <td>
                    <logic:equal name="myStepNumber" value="7"><fmt:message key="steps.arrow" /></logic:equal>
                    <logic:notEqual name="myStepNumber" value="7">&#160;</logic:notEqual>
                </td>
                <td><fmt:message key="steps.7" /></td>
                <td><fmt:message key="steps.finished.label" /></td>
                <td>&#160;</td>
            </tr>
        </table>
    </fmt:bundle>
</skin:lightArea>
