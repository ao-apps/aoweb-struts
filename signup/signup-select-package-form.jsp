<%-- aoweb-struts: Do not edit --%>
<%--
  Copyright 2000-2010 by AO Industries, Inc.,
  7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" buffer="256kb" autoFlush="true" pageEncoding="UTF-8" %>
<%@include file="/_taglibs.jsp" %>

<skin:lightArea>
    <skin:popupGroup>
        <fmt:bundle basename="com.aoindustries.website.signup.ApplicationResources">
            <table cellspacing="0" cellpadding="2">
                <tr><td colspan="5"><b><fmt:message key="signupSelectPackageForm.stepLabel" /></b><br /><hr /></td></tr>
                <tr><td colspan="5"><fmt:message key="signupSelectPackageForm.stepHelp" /><br /><br /></td></tr>
                <tr>
                    <th style='white-space:nowrap'><fmt:message key="signupSelectPackageForm.select.header" /></th>
                    <th style='white-space:nowrap'><fmt:message key="signupSelectPackageForm.packageDefinition.header" /></th>
                    <th style='white-space:nowrap'><fmt:message key="signupSelectPackageForm.setup.header" /></th>
                    <th style='white-space:nowrap'><fmt:message key="signupSelectPackageForm.monthlyRate.header" /></th>
                    <th style='white-space:nowrap'>&#160;</th>
                </tr>
                <logic:iterate scope="request" name="packageDefinitions" id="packageDefinition" indexId="packageDefinitionIndex">
                    <skin:lightDarkTableRow>
                        <td style="white-space:nowrap"><html:radio property="packageDefinition" idName="packageDefinition" value="pkey" /></td>
                        <td style="white-space:nowrap">
                            <b><ao:write name="packageDefinition" /></b>
                            <skin:popup>
                                <table cellspacing="0" cellpadding="2" style='font-size:80%;'>
                                    <tr>
                                        <td colspan="4" class='aoPopupLightRow' style='font-size:100%;'>
                                            <table style="width:100%" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <th class='aoPopupLightRow'><ao:write name="packageDefinition" /></th>
                                                    <td class='aoPopupLightRow' align="right"><skin:popupClose /></td>
                                                </tr>
                                            </table>
                                        </td>
                                    </tr>
                                    <tr>
                                        <th class='aoPopupDarkRow'><fmt:message key="signupSelectPackageForm.resource.header" /></th>
                                        <th class='aoPopupDarkRow'><fmt:message key="signupSelectPackageForm.included.header" /></th>
                                        <th class='aoPopupDarkRow'><fmt:message key="signupSelectPackageForm.maximum.header" /></th>
                                        <th class='aoPopupDarkRow'><fmt:message key="signupSelectPackageForm.additionalRate.header" /></th>
                                    </tr>
                                    <% int row2 = 0; %>
                                    <c:forEach items="${packageDefinition.limits}" var="limit">
                                        <tr class='<%= ((row2++)&1)==0 ? "aoPopupLightRow" : "aoPopupDarkRow" %>'>
                                            <td style="white-space:nowrap"><b><ao:write name="limit" property="resource"/></b></td>
                                            <td style="white-space:nowrap">
                                                <c:choose>
                                                    <c:when test="${limit.softLimit==-1}"><fmt:message key="signupSelectPackageForm.unlimited" /></c:when>
                                                    <c:otherwise><ao:write name="limit" method="getSoftLimitDisplayUnit"/></c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td style="white-space:nowrap">
                                                <c:choose>
                                                    <c:when test="${limit.hardLimit==-1}"><fmt:message key="signupSelectPackageForm.unlimited" /></c:when>
                                                    <c:otherwise><ao:write name="limit" method="getHardLimitDisplayUnit"/></c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td style="white-space:nowrap">
                                                <c:if test="${limit.additionalRate!=null}"><ao:write name="limit" method="getAdditionalRatePerUnit"/></c:if>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </table>
                            </skin:popup>
                        </td>
                        <td style="white-space:nowrap; text-align:center">
                            <logic:empty name="packageDefinition" property="setupFee"><fmt:message key="signupSelectPackageForm.setup.none" /></logic:empty>
                            <logic:notEmpty name="packageDefinition" property="setupFee">$<ao:write name="packageDefinition" property="setupFee" /></logic:notEmpty>
                        </td>
                        <td style="white-space:nowrap; text-align:center">$<ao:write name="packageDefinition" property="monthlyRate" /></td>
                        <logic:equal name="packageDefinitionLimit" value="0">
                            <bean:size scope="request" name="packageDefinitions" id="packageDefinitionsSize" />
                            <td rowspan="<%= packageDefinitionsSize %>" style="white-space:nowrap">
                                <html:errors bundle="/signup/ApplicationResources" property="packageDefinition" />
                            </td>
                        </logic:equal>
                    </skin:lightDarkTableRow>
                </logic:iterate>
                <tr><td colspan="5" align="center"><br /><ao:input type="submit"><fmt:message key="signupSelectPackageForm.submit.label" /></ao:input><br /><br /></td></tr>
            </table>
        </fmt:bundle>
    </skin:popupGroup>
</skin:lightArea>
