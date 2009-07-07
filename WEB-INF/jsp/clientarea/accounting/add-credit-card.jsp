<%-- aoweb-struts: Do not edit --%>
<%--
  Copyright 2007-2009 by AO Industries, Inc.,
  7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" buffer="256kb" autoFlush="true" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/taglibs.jsp" %>

<skin:setContentType />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html:html lang="true" xhtml="true">
    <fmt:bundle basename="com.aoindustries.website.clientarea.accounting.ApplicationResources">
        <skin:path>/clientarea/accounting/add-credit-card.do?accounting=<ao:write scope="request" name="addCreditCardForm" property="accounting" /></skin:path>
        <logic:equal name="siteSettings" property="brand.aowebStrutsNoindex" value="true"><skin:meta name="ROBOTS">NOINDEX</skin:meta></logic:equal>
        <skin:title><fmt:message key="addCreditCard.title" /></skin:title>
        <skin:navImageAlt><fmt:message key="addCreditCard.navImageAlt" /></skin:navImageAlt>
        <skin:keywords><fmt:message key="addCreditCard.keywords" /></skin:keywords>
        <skin:description><fmt:message key="addCreditCard.description" /></skin:description>
        <%@ include file="add-parents.jsp" %>
        <skin:addParent useEncryption="true" path="/clientarea/accounting/credit-card-manager.do">
            <skin:title><fmt:message key="creditCardManager.title" /></skin:title>
            <skin:navImageAlt><fmt:message key="creditCardManager.navImageAlt" /></skin:navImageAlt>
        </skin:addParent>
        <%@ include file="add-siblings.jsp" %>
        <skin:skin>
            <skin:content width="600">
                <skin:contentTitle><fmt:message key="addCreditCard.title" /></skin:contentTitle>
                <skin:contentHorizontalDivider />
                <skin:contentLine>
                    <logic:present scope="request" name="permissionDenied">
                        <%@ include file="../../permission-denied.jsp" %>
                    </logic:present>
                    <logic:notPresent scope="request" name="permissionDenied">
                        <skin:popupGroup>
                            <html:form action="/add-credit-card-completed">
                                <skin:lightArea>
                                    <fmt:message key="addCreditCard.form.title" />
                                    <hr />
                                    <table cellspacing="0" cellpadding="2">
                                        <bean:define name="addCreditCardForm" id="creditCardForm" />
                                        <%@ include file="credit-card-form.jsp" %>
                                        <tr><td style='white-space:nowrap' colspan="4" align="center"><html:submit><fmt:message key="addCreditCard.field.submit.label" /></html:submit></td></tr>
                                    </table>
                                </skin:lightArea>
                            </html:form>
                            <%@ include file="security-policy.jsp" %>
                        </skin:popupGroup>
                    </logic:notPresent>
                </skin:contentLine>
            </skin:content>
        </skin:skin>
    </fmt:bundle>
</html:html>
