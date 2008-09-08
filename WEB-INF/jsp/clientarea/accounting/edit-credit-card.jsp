<%--
  Copyright 2007-2008 by AO Industries, Inc.,
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
    <skin:path>/clientarea/accounting/edit-credit-card.do?persistenceId=<bean:write scope="request" name="editCreditCardForm" property="persistenceId"/></skin:path>
    <skin:title>
        <logic:notEqual name="editCreditCardForm" property="isActive" value="false">
            <bean:message bundle="/clientarea/accounting/ApplicationResources" key="editCreditCard.title.edit"/>
        </logic:notEqual>
        <logic:equal name="editCreditCardForm" property="isActive" value="false">
            <bean:message bundle="/clientarea/accounting/ApplicationResources" key="editCreditCard.title.reactivate"/>
        </logic:equal>
    </skin:title>
    <skin:navImageAlt>
        <logic:notEqual name="editCreditCardForm" property="isActive" value="false">
            <bean:message bundle="/clientarea/accounting/ApplicationResources" key="editCreditCard.navImageAlt.edit"/>
        </logic:notEqual>
        <logic:equal name="editCreditCardForm" property="isActive" value="false">
            <bean:message bundle="/clientarea/accounting/ApplicationResources" key="editCreditCard.navImageAlt.reactivate"/>
        </logic:equal>
    </skin:navImageAlt>
    <skin:keywords><bean:message bundle="/clientarea/accounting/ApplicationResources" key="editCreditCard.keywords"/></skin:keywords>
    <skin:description><bean:message bundle="/clientarea/accounting/ApplicationResources" key="editCreditCard.description"/></skin:description>
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
                    <bean:message bundle="/clientarea/accounting/ApplicationResources" key="editCreditCard.title.edit"/>
                </logic:notEqual>
                <logic:equal name="editCreditCardForm" property="isActive" value="false">
                    <bean:message bundle="/clientarea/accounting/ApplicationResources" key="editCreditCard.title.reactivate"/>
                </logic:equal>
            </skin:contentTitle>
            <skin:contentHorizontalDivider/>
            <skin:contentLine>
                <logic:present scope="request" name="permissionDenied">
                    <%@ include file="../../permission-denied.jsp" %>
                </logic:present>
                <logic:notPresent scope="request" name="permissionDenied">
                    <bean:define scope="request" name="creditCard" id="creditCard" type="com.aoindustries.aoserv.client.CreditCard"/>
                    <skin:popupGroup>
                        <html:form action="/edit-credit-card-completed">
                            <html:hidden property="persistenceId"/>
                            <html:hidden property="isActive"/>
                            <skin:lightArea>
                                <logic:notEqual name="editCreditCardForm" property="isActive" value="false">
                                    <bean:message bundle="/clientarea/accounting/ApplicationResources" key="editCreditCard.form.title.edit"/>
                                </logic:notEqual>
                                <logic:equal name="editCreditCardForm" property="isActive" value="false">
                                    <bean:message bundle="/clientarea/accounting/ApplicationResources" key="editCreditCard.form.title.reactivate"/>
                                </logic:equal>
                                <hr>
                                <table border="0" cellspacing="0" cellpadding="2">
                                    <tr>
                                        <td nowrap colspan="4" align="center">
                                            <html:img src="amex.gif" bundle="/clientarea/accounting/ApplicationResources" altKey="creditCardManager.image.amex.alt" align="absmiddle" border="1" width="64" height="40"/>
                                            &nbsp;&nbsp;<html:img src="discv.gif" bundle="/clientarea/accounting/ApplicationResources" altKey="creditCardManager.image.discv.alt" align="absmiddle" border="1" width="63" height="40"/>
                                            &nbsp;&nbsp;<html:img src="mcard.gif" bundle="/clientarea/accounting/ApplicationResources" altKey="creditCardManager.image.mcard.alt" align="absmiddle" border="1" width="64" height="40"/>
                                            &nbsp;&nbsp;<html:img src="visa.gif" bundle="/clientarea/accounting/ApplicationResources" altKey="creditCardManager.image.visa.alt" align="absmiddle" border="1" width="64" height="40"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td nowrap><bean:message bundle="/clientarea/accounting/ApplicationResources" key="editCreditCard.required.yes"/></td>
                                        <td nowrap><bean:message bundle="/clientarea/accounting/ApplicationResources" key="creditCardForm.accounting.prompt"/></td>
                                        <td nowrap><html:hidden property="accounting" write="true"/></td>
                                        <td nowrap><html:errors bundle="/clientarea/accounting/ApplicationResources" property="accounting"/></td>
                                    </tr>
                                    <tr>
                                        <td nowrap><bean:message bundle="/clientarea/accounting/ApplicationResources" key="editCreditCard.required.yes"/></td>
                                        <td nowrap><bean:message bundle="/clientarea/accounting/ApplicationResources" key="creditCardForm.firstName.prompt"/></td>
                                        <td nowrap><html:text property="firstName" size="16"/></td>
                                        <td nowrap><html:errors bundle="/clientarea/accounting/ApplicationResources" property="firstName"/></td>
                                    </tr>
                                    <tr>
                                        <td nowrap><bean:message bundle="/clientarea/accounting/ApplicationResources" key="editCreditCard.required.yes"/></td>
                                        <td nowrap><bean:message bundle="/clientarea/accounting/ApplicationResources" key="creditCardForm.lastName.prompt"/></td>
                                        <td nowrap><html:text property="lastName" size="16"/></td>
                                        <td nowrap><html:errors bundle="/clientarea/accounting/ApplicationResources" property="lastName"/></td>
                                    </tr>
                                    <tr>
                                        <td nowrap><bean:message bundle="/clientarea/accounting/ApplicationResources" key="editCreditCard.required.no"/></td>
                                        <td nowrap><bean:message bundle="/clientarea/accounting/ApplicationResources" key="creditCardForm.companyName.prompt"/></td>
                                        <td nowrap><html:text property="companyName" size="32"/></td>
                                        <td nowrap><html:errors bundle="/clientarea/accounting/ApplicationResources" property="companyName"/></td>
                                    </tr>
                                    <tr>
                                        <td nowrap><bean:message bundle="/clientarea/accounting/ApplicationResources" key="editCreditCard.required.no"/></td>
                                        <td nowrap><bean:message bundle="/clientarea/accounting/ApplicationResources" key="editCreditCard.maskedCardNumber.prompt"/></td>
                                        <td nowrap><%= creditCard.getCardInfo().replace('X', 'x') %></td>
                                        <td nowrap><html:errors bundle="/clientarea/accounting/ApplicationResources" property="maskedCardNumber"/></td>
                                    </tr>
                                    <tr>
                                        <td nowrap><bean:message bundle="/clientarea/accounting/ApplicationResources" key="editCreditCard.required.no"/></td>
                                        <td nowrap><bean:message bundle="/clientarea/accounting/ApplicationResources" key="editCreditCard.cardNumber.prompt"/></td>
                                        <td nowrap><html:text property="cardNumber" size="20"/></td>
                                        <td nowrap><html:errors bundle="/clientarea/accounting/ApplicationResources" property="cardNumber"/></td>
                                    </tr>
                                    <tr>
                                        <td nowrap><bean:message bundle="/clientarea/accounting/ApplicationResources" key="editCreditCard.required.no"/></td>
                                        <td nowrap><bean:message bundle="/clientarea/accounting/ApplicationResources" key="editCreditCard.newExpirationDate.prompt"/></TD>
                                        <td nowrap>
                                            <html:select property="expirationMonth">
                                                <html:option bundle="/signup/ApplicationResources" key="signupBillingInformationForm.billingExpirationMonth.none.display" value=""/>
                                                <html:option bundle="/signup/ApplicationResources" key="signupBillingInformationForm.billingExpirationMonth.jan.display" value="01"/>
                                                <html:option bundle="/signup/ApplicationResources" key="signupBillingInformationForm.billingExpirationMonth.feb.display" value="02"/>
                                                <html:option bundle="/signup/ApplicationResources" key="signupBillingInformationForm.billingExpirationMonth.mar.display" value="03"/>
                                                <html:option bundle="/signup/ApplicationResources" key="signupBillingInformationForm.billingExpirationMonth.apr.display" value="04"/>
                                                <html:option bundle="/signup/ApplicationResources" key="signupBillingInformationForm.billingExpirationMonth.may.display" value="05"/>
                                                <html:option bundle="/signup/ApplicationResources" key="signupBillingInformationForm.billingExpirationMonth.jun.display" value="06"/>
                                                <html:option bundle="/signup/ApplicationResources" key="signupBillingInformationForm.billingExpirationMonth.jul.display" value="07"/>
                                                <html:option bundle="/signup/ApplicationResources" key="signupBillingInformationForm.billingExpirationMonth.aug.display" value="08"/>
                                                <html:option bundle="/signup/ApplicationResources" key="signupBillingInformationForm.billingExpirationMonth.sep.display" value="09"/>
                                                <html:option bundle="/signup/ApplicationResources" key="signupBillingInformationForm.billingExpirationMonth.oct.display" value="10"/>
                                                <html:option bundle="/signup/ApplicationResources" key="signupBillingInformationForm.billingExpirationMonth.nov.display" value="11"/>
                                                <html:option bundle="/signup/ApplicationResources" key="signupBillingInformationForm.billingExpirationMonth.dec.display" value="12"/>
                                            </html:select>&nbsp;/&nbsp;<html:select property="expirationYear">
                                                <html:option bundle="/signup/ApplicationResources" key="signupBillingInformationForm.billingExpirationYear.none.display" value=""/>
                                                <html:options name="expirationYears"/>
                                            </html:select>
                                        </td>
                                        <td nowrap><html:errors bundle="/clientarea/accounting/ApplicationResources" property="expirationDate"/></td>
                                    </tr>
                                    <tr>
                                        <td nowrap><bean:message bundle="/clientarea/accounting/ApplicationResources" key="editCreditCard.required.no"/></td>
                                        <td nowrap><bean:message bundle="/clientarea/accounting/ApplicationResources" key="creditCardForm.email.prompt"/></td>
                                        <td nowrap><html:text property="email" size="32"/></td>
                                        <td nowrap><html:errors bundle="/clientarea/accounting/ApplicationResources" property="email"/></td>
                                    </tr>
                                    <tr>
                                        <td nowrap><bean:message bundle="/clientarea/accounting/ApplicationResources" key="editCreditCard.required.no"/></td>
                                        <td nowrap><bean:message bundle="/clientarea/accounting/ApplicationResources" key="creditCardForm.phone.prompt"/></td>
                                        <td nowrap><html:text property="phone" size="18"/></td>
                                        <td nowrap><html:errors bundle="/clientarea/accounting/ApplicationResources" property="phone"/></td>
                                    </tr>
                                    <tr>
                                        <td nowrap><bean:message bundle="/clientarea/accounting/ApplicationResources" key="editCreditCard.required.no"/></td>
                                        <td nowrap><bean:message bundle="/clientarea/accounting/ApplicationResources" key="creditCardForm.fax.prompt"/></td>
                                        <td nowrap><html:text property="fax" size="18"/></td>
                                        <td nowrap><html:errors bundle="/clientarea/accounting/ApplicationResources" property="fax"/></td>
                                    </tr>
                                    <tr>
                                        <td nowrap><bean:message bundle="/clientarea/accounting/ApplicationResources" key="editCreditCard.required.no"/></td>
                                        <td nowrap><bean:message bundle="/clientarea/accounting/ApplicationResources" key="creditCardForm.customerTaxId.prompt"/></td>
                                        <td nowrap><html:text property="customerTaxId" size="13"/></td>
                                        <td nowrap><html:errors bundle="/clientarea/accounting/ApplicationResources" property="customerTaxId"/></td>
                                    </tr>
                                    <tr>
                                        <td nowrap><bean:message bundle="/clientarea/accounting/ApplicationResources" key="editCreditCard.required.yes"/></td>
                                        <td nowrap><bean:message bundle="/clientarea/accounting/ApplicationResources" key="creditCardForm.streetAddress1.prompt"/></td>
                                        <td nowrap><html:text property="streetAddress1" size="32"/></td>
                                        <td nowrap><html:errors bundle="/clientarea/accounting/ApplicationResources" property="streetAddress1"/></td>
                                    </tr>
                                    <tr>
                                        <td nowrap><bean:message bundle="/clientarea/accounting/ApplicationResources" key="editCreditCard.required.no"/></td>
                                        <td nowrap><bean:message bundle="/clientarea/accounting/ApplicationResources" key="creditCardForm.streetAddress2.prompt"/></td>
                                        <td nowrap><html:text property="streetAddress2" size="32"/></td>
                                        <td nowrap><html:errors bundle="/clientarea/accounting/ApplicationResources" property="streetAddress2"/></td>
                                    </tr>
                                    <tr>
                                        <td nowrap><bean:message bundle="/clientarea/accounting/ApplicationResources" key="editCreditCard.required.yes"/></td>
                                        <td nowrap><bean:message bundle="/clientarea/accounting/ApplicationResources" key="creditCardForm.city.prompt"/></td>
                                        <td nowrap><html:text property="city" size="16"/></td>
                                        <td nowrap><html:errors bundle="/clientarea/accounting/ApplicationResources" property="city"/></td>
                                    </tr>
                                    <tr>
                                        <td nowrap><bean:message bundle="/clientarea/accounting/ApplicationResources" key="editCreditCard.required.no"/></td>
                                        <td nowrap><bean:message bundle="/clientarea/accounting/ApplicationResources" key="creditCardForm.state.prompt"/></td>
                                        <td nowrap><html:text property="state" size="5"/></td>
                                        <td nowrap><html:errors bundle="/clientarea/accounting/ApplicationResources" property="state"/></td>
                                    </tr>
                                    <tr>
                                        <td nowrap><bean:message bundle="/clientarea/accounting/ApplicationResources" key="editCreditCard.required.yes"/></td>
                                        <td nowrap><bean:message bundle="/clientarea/accounting/ApplicationResources" key="creditCardForm.countryCode.prompt"/></td>
                                        <td nowrap>
                                            <html:select property="countryCode">
                                                <bean:define id="didOne" type="java.lang.String" value="false"/>
                                                <bean:define name="editCreditCardForm" property="countryCode" id="country" type="java.lang.String"/>
                                                <logic:iterate scope="request" name="countryOptions" id="countryOption">
                                                    <logic:equal name="countryOption" property="code" value="<%= country %>">
                                                        <% if(!didOne.equals("true")) { %>
                                                            <option value='<bean:write name="countryOption" property="code"/>' selected><bean:write name="countryOption" property="name"/></option>
                                                            <% didOne = "true"; %>
                                                        <% } else { %>
                                                            <option value='<bean:write name="countryOption" property="code"/>'><bean:write name="countryOption" property="name"/></option>
                                                        <% } %>
                                                    </logic:equal>
                                                    <logic:notEqual name="countryOption" property="code" value="<%= country %>">
                                                        <option value='<bean:write name="countryOption" property="code"/>'><bean:write name="countryOption" property="name"/></option>
                                                    </logic:notEqual>
                                                </logic:iterate>
                                            </html:select>
                                        </td>
                                        <td nowrap><html:errors bundle="/clientarea/accounting/ApplicationResources" property="countryCode"/></td>
                                    </tr>
                                    <tr>
                                        <td nowrap><bean:message bundle="/clientarea/accounting/ApplicationResources" key="editCreditCard.required.no"/></td>
                                        <td nowrap><bean:message bundle="/clientarea/accounting/ApplicationResources" key="creditCardForm.postalCode.prompt"/></td>
                                        <td nowrap><html:text property="postalCode" size="10"/></td>
                                        <td nowrap><html:errors bundle="/clientarea/accounting/ApplicationResources" property="postalCode"/></td>
                                    </tr>
                                    <tr>
                                        <td nowrap><bean:message bundle="/clientarea/accounting/ApplicationResources" key="editCreditCard.required.no"/></td>
                                        <td nowrap><bean:message bundle="/clientarea/accounting/ApplicationResources" key="creditCardForm.description.prompt"/></td>
                                        <td nowrap><html:text property="description" size="32"/></td>
                                        <td nowrap><html:errors bundle="/clientarea/accounting/ApplicationResources" property="description"/></td>
                                    </tr>
                                    <tr>
                                        <td nowrap colspan="4" align="center">
                                            <html:submit>
                                                <logic:notEqual name="editCreditCardForm" property="isActive" value="false">
                                                    <bean:message bundle="/clientarea/accounting/ApplicationResources" key="editCreditCard.field.submit.label.edit"/>
                                                </logic:notEqual>
                                                <logic:equal name="editCreditCardForm" property="isActive" value="false">
                                                    <bean:message bundle="/clientarea/accounting/ApplicationResources" key="editCreditCard.field.submit.label.reactivate"/>
                                                </logic:equal>
                                            </html:submit>
                                        </td>
                                    </tr>
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
