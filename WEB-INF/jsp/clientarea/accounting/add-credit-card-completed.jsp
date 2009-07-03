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
    <skin:path>/clientarea/accounting/add-credit-card-completed.do</skin:path>
    <logic:equal name="siteSettings" property="brand.aowebStrutsNoindex" value="true"><skin:meta name="ROBOTS">NOINDEX</skin:meta></logic:equal>
    <skin:title><bean:message bundle="/clientarea/accounting/ApplicationResources" key="addCreditCardCompleted.title" /></skin:title>
    <skin:navImageAlt><bean:message bundle="/clientarea/accounting/ApplicationResources" key="addCreditCardCompleted.navImageAlt" /></skin:navImageAlt>
    <skin:keywords><bean:message bundle="/clientarea/accounting/ApplicationResources" key="addCreditCardCompleted.keywords" /></skin:keywords>
    <skin:description><bean:message bundle="/clientarea/accounting/ApplicationResources" key="addCreditCardCompleted.description" /></skin:description>
    <%@ include file="add-parents.jsp" %>
    <skin:addParent useEncryption="true" path="/clientarea/accounting/credit-card-manager.do">
        <skin:title><bean:message bundle="/clientarea/accounting/ApplicationResources" key="creditCardManager.title" /></skin:title>
        <skin:navImageAlt><bean:message bundle="/clientarea/accounting/ApplicationResources" key="creditCardManager.navImageAlt" /></skin:navImageAlt>
    </skin:addParent>
    <%@ include file="add-siblings.jsp" %>
    <skin:skin>
        <skin:content width="600">
            <skin:contentTitle><bean:message bundle="/clientarea/accounting/ApplicationResources" key="addCreditCardCompleted.title" /></skin:contentTitle>
            <skin:contentHorizontalDivider />
            <skin:contentLine>
                <logic:present scope="request" name="permissionDenied">
                    <%@ include file="../../permission-denied.jsp" %>
                </logic:present>
                <logic:notPresent scope="request" name="permissionDenied">
                    <skin:lightArea>
                        <bean:message bundle="/clientarea/accounting/ApplicationResources" key="addCreditCardCompleted.successMessage.title" />
                        <hr />
                        <bean:define scope="request" name="cardNumber" id="cardNumber" type="java.lang.String" />
                        <bean:message bundle="/clientarea/accounting/ApplicationResources" key="addCreditCardCompleted.successMessage.text" arg0="<%= cardNumber.replace('X', 'x') %>" /><br />
                        <br />
                        <html:link action="/credit-card-manager"><bean:message bundle="/clientarea/accounting/ApplicationResources" key="addCreditCardCompleted.creditCardManager.link" /></html:link>
                    </skin:lightArea>
                </logic:notPresent>
            </skin:contentLine>
        </skin:content>
    </skin:skin>
</html:html>
