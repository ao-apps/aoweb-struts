<%-- aoweb-struts: Do not edit --%>
<%--
  Copyright 2007-2009 by AO Industries, Inc.,
  7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" buffer="256kb" autoFlush="true" %>
<%@ include file="/WEB-INF/jsp/taglibs.jsp" %>

<tr>
    <td nowrap colspan="4" align="center">
        <html:img src="amex.gif" bundle="/clientarea/accounting/ApplicationResources" altKey="creditCardManager.image.amex.alt" align="absmiddle" border="1" width="64" height="40"/>
        &nbsp;&nbsp;<html:img src="discv.gif" bundle="/clientarea/accounting/ApplicationResources" altKey="creditCardManager.image.discv.alt" align="absmiddle" border="1" width="63" height="40"/>
        &nbsp;&nbsp;<html:img src="mcard.gif" bundle="/clientarea/accounting/ApplicationResources" altKey="creditCardManager.image.mcard.alt" align="absmiddle" border="1" width="64" height="40"/>
        &nbsp;&nbsp;<html:img src="visa.gif" bundle="/clientarea/accounting/ApplicationResources" altKey="creditCardManager.image.visa.alt" align="absmiddle" border="1" width="64" height="40"/>
    </td>
</tr>
<tr>
    <td nowrap><bean:message bundle="/clientarea/accounting/ApplicationResources" key="creditCardForm.required.yes"/></td>
    <th nowrap align="left"><bean:message bundle="/clientarea/accounting/ApplicationResources" key="creditCardForm.accounting.prompt"/></th>
    <td nowrap><html:hidden property="accounting" write="true"/></td>
    <td nowrap><html:errors bundle="/clientarea/accounting/ApplicationResources" property="accounting"/></td>
</tr>
<tr>
    <td nowrap><bean:message bundle="/clientarea/accounting/ApplicationResources" key="creditCardForm.required.yes"/></td>
    <th nowrap align="left"><bean:message bundle="/clientarea/accounting/ApplicationResources" key="creditCardForm.firstName.prompt"/></th>
    <td nowrap><html:text property="firstName" size="16"/></td>
    <td nowrap><html:errors bundle="/clientarea/accounting/ApplicationResources" property="firstName"/></td>
</tr>
<tr>
    <td nowrap><bean:message bundle="/clientarea/accounting/ApplicationResources" key="creditCardForm.required.yes"/></td>
    <th nowrap align="left"><bean:message bundle="/clientarea/accounting/ApplicationResources" key="creditCardForm.lastName.prompt"/></th>
    <td nowrap><html:text property="lastName" size="16"/></td>
    <td nowrap><html:errors bundle="/clientarea/accounting/ApplicationResources" property="lastName"/></td>
</tr>
<tr>
    <td nowrap><bean:message bundle="/clientarea/accounting/ApplicationResources" key="creditCardForm.required.no"/></td>
    <th nowrap align="left"><bean:message bundle="/clientarea/accounting/ApplicationResources" key="creditCardForm.companyName.prompt"/></th>
    <td nowrap><html:text property="companyName" size="32"/></td>
    <td nowrap><html:errors bundle="/clientarea/accounting/ApplicationResources" property="companyName"/></td>
</tr>
<tr>
    <td nowrap><bean:message bundle="/clientarea/accounting/ApplicationResources" key="creditCardForm.required.yes"/></td>
    <th nowrap align="left"><bean:message bundle="/clientarea/accounting/ApplicationResources" key="creditCardForm.cardNumber.prompt"/></th>
    <td nowrap><html:text property="cardNumber" size="20"/></td>
    <td nowrap><html:errors bundle="/clientarea/accounting/ApplicationResources" property="cardNumber"/></td>
</tr>
<tr>
    <td nowrap><bean:message bundle="/clientarea/accounting/ApplicationResources" key="creditCardForm.required.yes"/></td>
    <th nowrap align="left"><bean:message bundle="/clientarea/accounting/ApplicationResources" key="creditCardForm.expirationDate.prompt"/></th>
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
    <td nowrap><bean:message bundle="/clientarea/accounting/ApplicationResources" key="creditCardForm.required.no"/></td>
    <th nowrap align="left"><bean:message bundle="/clientarea/accounting/ApplicationResources" key="creditCardForm.email.prompt"/></th>
    <td nowrap><html:text property="email" size="32"/></td>
    <td nowrap><html:errors bundle="/clientarea/accounting/ApplicationResources" property="email"/></td>
</tr>
<tr>
    <td nowrap><bean:message bundle="/clientarea/accounting/ApplicationResources" key="creditCardForm.required.no"/></td>
    <th nowrap align="left"><bean:message bundle="/clientarea/accounting/ApplicationResources" key="creditCardForm.phone.prompt"/></th>
    <td nowrap><html:text property="phone" size="18"/></td>
    <td nowrap><html:errors bundle="/clientarea/accounting/ApplicationResources" property="phone"/></td>
</tr>
<tr>
    <td nowrap><bean:message bundle="/clientarea/accounting/ApplicationResources" key="creditCardForm.required.no"/></td>
    <th nowrap align="left"><bean:message bundle="/clientarea/accounting/ApplicationResources" key="creditCardForm.fax.prompt"/></th>
    <td nowrap><html:text property="fax" size="18"/></td>
    <td nowrap><html:errors bundle="/clientarea/accounting/ApplicationResources" property="fax"/></td>
</tr>
<tr>
    <td nowrap><bean:message bundle="/clientarea/accounting/ApplicationResources" key="creditCardForm.required.no"/></td>
    <th nowrap align="left"><bean:message bundle="/clientarea/accounting/ApplicationResources" key="creditCardForm.customerTaxId.prompt"/></th>
    <td nowrap><html:text property="customerTaxId" size="13"/></td>
    <td nowrap><html:errors bundle="/clientarea/accounting/ApplicationResources" property="customerTaxId"/></td>
</tr>
<tr>
    <td nowrap><bean:message bundle="/clientarea/accounting/ApplicationResources" key="creditCardForm.required.yes"/></td>
    <th nowrap align="left"><bean:message bundle="/clientarea/accounting/ApplicationResources" key="creditCardForm.streetAddress1.prompt"/></th>
    <td nowrap><html:text property="streetAddress1" size="32"/></td>
    <td nowrap><html:errors bundle="/clientarea/accounting/ApplicationResources" property="streetAddress1"/></td>
</tr>
<tr>
    <td nowrap><bean:message bundle="/clientarea/accounting/ApplicationResources" key="creditCardForm.required.no"/></td>
    <th nowrap align="left"><bean:message bundle="/clientarea/accounting/ApplicationResources" key="creditCardForm.streetAddress2.prompt"/></th>
    <td nowrap><html:text property="streetAddress2" size="32"/></td>
    <td nowrap><html:errors bundle="/clientarea/accounting/ApplicationResources" property="streetAddress2"/></td>
</tr>
<tr>
    <td nowrap><bean:message bundle="/clientarea/accounting/ApplicationResources" key="creditCardForm.required.yes"/></td>
    <th nowrap align="left"><bean:message bundle="/clientarea/accounting/ApplicationResources" key="creditCardForm.city.prompt"/></th>
    <td nowrap><html:text property="city" size="16"/></td>
    <td nowrap><html:errors bundle="/clientarea/accounting/ApplicationResources" property="city"/></td>
</tr>
<tr>
    <td nowrap><bean:message bundle="/clientarea/accounting/ApplicationResources" key="creditCardForm.required.yes"/></td>
    <th nowrap align="left"><bean:message bundle="/clientarea/accounting/ApplicationResources" key="creditCardForm.state.prompt"/></th>
    <td nowrap><html:text property="state" size="5"/></td>
    <td nowrap><html:errors bundle="/clientarea/accounting/ApplicationResources" property="state"/></td>
</tr>
<tr>
    <td nowrap><bean:message bundle="/clientarea/accounting/ApplicationResources" key="creditCardForm.required.yes"/></td>
    <th nowrap align="left"><bean:message bundle="/clientarea/accounting/ApplicationResources" key="creditCardForm.countryCode.prompt"/></th>
    <td nowrap>
        <html:select property="countryCode">
            <bean:define id="didOne" type="java.lang.String" value="false"/>
            <bean:define name="creditCardForm" property="countryCode" id="country" type="java.lang.String"/>
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
    <td nowrap><bean:message bundle="/clientarea/accounting/ApplicationResources" key="creditCardForm.required.yes"/></td>
    <th nowrap align="left"><bean:message bundle="/clientarea/accounting/ApplicationResources" key="creditCardForm.postalCode.prompt"/></th>
    <td nowrap><html:text property="postalCode" size="10"/></td>
    <td nowrap><html:errors bundle="/clientarea/accounting/ApplicationResources" property="postalCode"/></td>
</tr>
<tr>
    <td nowrap><bean:message bundle="/clientarea/accounting/ApplicationResources" key="creditCardForm.required.no"/></td>
    <th nowrap align="left"><bean:message bundle="/clientarea/accounting/ApplicationResources" key="creditCardForm.description.prompt"/></th>
    <td nowrap><html:text property="description" size="32"/></td>
    <td nowrap><html:errors bundle="/clientarea/accounting/ApplicationResources" property="description"/></td>
</tr>
