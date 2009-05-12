<%--
  Copyright 2007-2009 by AO Industries, Inc.,
  7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
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
    <skin:path>/clientarea/accounting/edit-credit-card-completed.do</skin:path>
    <logic:equal name="siteSettings" property="noindexAowebStruts" value="true"><skin:meta name="ROBOTS">NOINDEX</skin:meta></logic:equal>
    <skin:title>
        <logic:notEqual name="editCreditCardForm" property="isActive" value="false">
            <bean:message bundle="/clientarea/accounting/ApplicationResources" key="editCreditCardCompleted.title.edit"/>
        </logic:notEqual>
        <logic:equal name="editCreditCardForm" property="isActive" value="false">
            <bean:message bundle="/clientarea/accounting/ApplicationResources" key="editCreditCardCompleted.title.reactivate"/>
        </logic:equal>
    </skin:title>
    <skin:navImageAlt>
        <logic:notEqual name="editCreditCardForm" property="isActive" value="false">
            <bean:message bundle="/clientarea/accounting/ApplicationResources" key="editCreditCardCompleted.navImageAlt.edit"/>
        </logic:notEqual>
        <logic:equal name="editCreditCardForm" property="isActive" value="false">
            <bean:message bundle="/clientarea/accounting/ApplicationResources" key="editCreditCardCompleted.navImageAlt.reactivate"/>
        </logic:equal>
    </skin:navImageAlt>
    <skin:keywords><bean:message bundle="/clientarea/accounting/ApplicationResources" key="editCreditCardCompleted.keywords"/></skin:keywords>
    <skin:description><bean:message bundle="/clientarea/accounting/ApplicationResources" key="editCreditCardCompleted.description"/></skin:description>
    <%@ include file="add-parents.jsp" %>
    <skin:addParent useEncryption="true" path="/clientarea/accounting/credit-card-manager.do">
        <skin:title><bean:message bundle="/clientarea/accounting/ApplicationResources" key="creditCardManager.title"/></skin:title>
        <skin:navImageAlt><bean:message bundle="/clientarea/accounting/ApplicationResources" key="creditCardManager.navImageAlt"/></skin:navImageAlt>
    </skin:addParent>
    <%@ include file="add-siblings.jsp" %>
    <skin:skin>
        <skin:content width="600">
            <skin:contentTitle>
                <logic:notEqual name="editCreditCardForm" property="isActive" value="false">
                    <bean:message bundle="/clientarea/accounting/ApplicationResources" key="editCreditCardCompleted.title.edit"/>
                </logic:notEqual>
                <logic:equal name="editCreditCardForm" property="isActive" value="false">
                    <bean:message bundle="/clientarea/accounting/ApplicationResources" key="editCreditCardCompleted.title.reactivate"/>
                </logic:equal>
            </skin:contentTitle>
            <skin:contentHorizontalDivider/>
            <skin:contentLine>
                <logic:present scope="request" name="permissionDenied">
                    <%@ include file="../../permission-denied.jsp" %>
                </logic:present>
                <logic:notPresent scope="request" name="permissionDenied">
                    <skin:lightArea>
                        <logic:notEqual name="editCreditCardForm" property="isActive" value="false">
                            <bean:message bundle="/clientarea/accounting/ApplicationResources" key="editCreditCardCompleted.successMessage.title.edit"/>
                        </logic:notEqual>
                        <logic:equal name="editCreditCardForm" property="isActive" value="false">
                            <bean:message bundle="/clientarea/accounting/ApplicationResources" key="editCreditCardCompleted.successMessage.title.reactivate"/>
                        </logic:equal>
                        <hr>
                        <bean:define scope="request" name="cardNumber" id="cardNumber" type="java.lang.String"/>
                        <logic:notEqual name="editCreditCardForm" property="isActive" value="false">
                            <bean:message bundle="/clientarea/accounting/ApplicationResources" key="editCreditCardCompleted.successMessage.text.edit" arg0="<%= cardNumber.replace('X', 'x') %>"/><br>
                        </logic:notEqual>
                        <logic:equal name="editCreditCardForm" property="isActive" value="false">
                            <bean:message bundle="/clientarea/accounting/ApplicationResources" key="editCreditCardCompleted.successMessage.text.reactivate" arg0="<%= cardNumber.replace('X', 'x') %>"/><br>
                        </logic:equal>
                        <ul>
                            <logic:equal scope="request" name="updatedCardNumber" value="true">
                                <li><bean:message bundle="/clientarea/accounting/ApplicationResources" key="editCreditCardCompleted.successMessage.updatedCardNumber"/></li>
                            </logic:equal>
                            <logic:equal scope="request" name="updatedExpirationDate" value="true">
                                <li><bean:message bundle="/clientarea/accounting/ApplicationResources" key="editCreditCardCompleted.successMessage.updatedExpirationDate"/></li>
                            </logic:equal>
                            <logic:equal scope="request" name="updatedCardDetails" value="true">
                                <li><bean:message bundle="/clientarea/accounting/ApplicationResources" key="editCreditCardCompleted.successMessage.updatedCardDetails"/></li>
                            </logic:equal>
                            <logic:equal scope="request" name="reactivatedCard" value="true">
                                <li><bean:message bundle="/clientarea/accounting/ApplicationResources" key="editCreditCardCompleted.successMessage.reactivatedCard"/></li>
                            </logic:equal>
                        </ul>
                        <html:link action="/credit-card-manager"><bean:message bundle="/clientarea/accounting/ApplicationResources" key="editCreditCardCompleted.creditCardManager.link"/></html:link>
                    </skin:lightArea>
                </logic:notPresent>
            </skin:contentLine>
        </skin:content>
    </skin:skin>
</html:html>
