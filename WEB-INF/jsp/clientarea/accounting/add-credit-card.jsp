<%--
  Copyright 2007 by AO Industries, Inc.,
  816 Azalea Rd, Mobile, Alabama, 36693, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" %>
<%@ page buffer="256kb" %>
<%@ page autoFlush="true" %>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/aoweb-struts-skin.tld" prefix="skin" %>
<skin:setContentType/>
<html:html lang="true">
    <skin:path>/clientarea/accounting/add-credit-card.do?accounting=<bean:write scope="request" name="addCreditCardForm" property="accounting"/></skin:path>
    <skin:title><bean:message bundle="/clientarea/accounting/ApplicationResources" key="addCreditCard.title"/></skin:title>
    <skin:navImageAlt><bean:message bundle="/clientarea/accounting/ApplicationResources" key="addCreditCard.navImageAlt"/></skin:navImageAlt>
    <skin:keywords><bean:message bundle="/clientarea/accounting/ApplicationResources" key="addCreditCard.keywords"/></skin:keywords>
    <skin:description><bean:message bundle="/clientarea/accounting/ApplicationResources" key="addCreditCard.description"/></skin:description>
    <%@ include file="add-parents.jsp" %>
    <skin:addParent useEncryption="true" path="/clientarea/accounting/credit-card-manager.do">
        <skin:title><bean:message bundle="/clientarea/accounting/ApplicationResources" key="creditCardManager.title"/></skin:title>
        <skin:navImageAlt><bean:message bundle="/clientarea/accounting/ApplicationResources" key="creditCardManager.navImageAlt"/></skin:navImageAlt>
    </skin:addParent>
    <%@ include file="add-siblings.jsp" %>
    <skin:skin>
        <skin:content width="600">
            <skin:contentTitle><bean:message bundle="/clientarea/accounting/ApplicationResources" key="addCreditCard.title"/></skin:contentTitle>
            <skin:contentHorizontalDivider/>
            <skin:contentLine>
                <logic:present scope="request" name="permissionDenied">
                    <%@ include file="../../permission-denied.jsp" %>
                </logic:present>
                <logic:notPresent scope="request" name="permissionDenied">
                    <skin:popupGroup>
                        <html:form action="/add-credit-card-completed">
                            <skin:lightArea>
                                <bean:message bundle="/clientarea/accounting/ApplicationResources" key="addCreditCard.form.title"/>
                                <hr>
                                <table border="0" cellspacing="0" cellpadding="2">
                                    <bean:define name="addCreditCardForm" id="creditCardForm"/>
                                    <%@ include file="credit-card-form.jsp" %>
                                    <tr><td nowrap colspan="4" align="center"><html:submit><bean:message bundle="/clientarea/accounting/ApplicationResources" key="addCreditCard.field.submit.label"/></html:submit></td></tr>
                                </table>
                            </skin:lightArea>
                        </html:form>
                        <%@ include file="security-policy.jsp" %>
                    </skin:popupGroup>
                </logic:notPresent>
            </skin:contentLine>
        </skin:content>
    </skin:skin>
</html:html>
