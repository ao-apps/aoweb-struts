<%-- aoweb-struts: Do not edit --%>
<%--
  Copyright 2007-2009, 2015 by AO Industries, Inc.,
  7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
  All rights reserved.
--%>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ page import="java.math.BigDecimal" %>
<%@include file="/_taglibs.jsp" %>

<skin:setContentType />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html:html lang="true" xhtml="true">
	<fmt:bundle basename="com.aoindustries.website.clientarea.accounting.ApplicationResources">
		<skin:path>
			/clientarea/accounting/make-payment-new-card.do
			<ao:param name="accounting"><ao:write scope="request" name="makePaymentNewCardForm" property="accounting" /></ao:param>
		</skin:path>
		<logic:equal name="siteSettings" property="brand.aowebStrutsNoindex" value="true"><skin:meta name="ROBOTS">NOINDEX</skin:meta></logic:equal>
		<skin:title><fmt:message key="makePayment.title" /></skin:title>
		<skin:navImageAlt><fmt:message key="makePayment.navImageAlt" /></skin:navImageAlt>
		<skin:keywords><fmt:message key="makePayment.keywords" /></skin:keywords>
		<skin:description><fmt:message key="makePayment.description" /></skin:description>
		<%@include file="add-parents.jsp" %>
		<skin:skin>
			<skin:content width="600">
				<skin:contentTitle><fmt:message key="makePayment.title" /></skin:contentTitle>
				<skin:contentHorizontalDivider />
				<skin:contentLine>
					<skin:popupGroup>
						<html:form action="/make-payment-new-card-completed">
							<skin:lightArea>
								<fmt:message key="makePaymentNewCard.form.title" />
								<hr />
								<logic:present scope="request" name="errorReason">
									<fmt:message key="makePaymentStoredCardError.error.description">
										<fmt:param><c:out value="${errorReason}" /></fmt:param>
									</fmt:message>
									<hr />
								</logic:present>
								<logic:present scope="request" name="declineReason">
									<fmt:message key="makePaymentStoredCardDeclined.declined.description">
										<fmt:param><c:out value="${declineReason}" /></fmt:param>
									</fmt:message>
									<hr />
								</logic:present>
								<bean:define scope="request" name="business" id="business" type="com.aoindustries.aoserv.client.Business" />
								<table cellspacing="0" cellpadding="2">
									<bean:define name="makePaymentNewCardForm" id="creditCardForm" />
									<%@include file="credit-card-form.jsp" %>
									<tr>
										<td style="white-space:nowrap"><fmt:message key="creditCardForm.required.no" /></td>
										<th style='white-space:nowrap' align='left'><fmt:message key="makePaymentStoredCard.accountBalance.prompt" /></th>
										<td style="white-space:nowrap">
											<% BigDecimal balance = business.getAccountBalance(); %>
											<% if(balance.signum()==0) { %>
												<fmt:message key="makePaymentSelectCard.balance.value.zero" />
											<% } else if(balance.signum()<0) { %>
												<fmt:message key="makePaymentSelectCard.balance.value.credit">
													<fmt:param><c:out value="<%= balance.negate().toPlainString() %>" /></fmt:param>
												</fmt:message>
											<% } else { %>
												<fmt:message key="makePaymentSelectCard.balance.value.debt">
													<fmt:param><c:out value="<%= balance.toPlainString() %>" /></fmt:param>
												</fmt:message>
											<% } %>
										</td>
										<td style="white-space:nowrap"><html:errors bundle="/clientarea/accounting/ApplicationResources" property="accountBalance" /></td>
									</tr>
									<tr>
										<td style="white-space:nowrap"><fmt:message key="creditCardForm.required.yes" /></td>
										<th style='white-space:nowrap' align='left'><fmt:message key="makePaymentStoredCard.paymentAmount.prompt" /></th>
										<td style="white-space:nowrap">$<html:text property="paymentAmount" size="8" /></td>
										<td style="white-space:nowrap"><html:errors bundle="/clientarea/accounting/ApplicationResources" property="paymentAmount" /></td>
									</tr>
									<tr>
										<td style="white-space:nowrap"><fmt:message key="creditCardForm.required.no" /></td>
										<th style='white-space:nowrap' align='left'><fmt:message key="makePaymentNewCard.storeCard.prompt" /></th>
										<td style='white-space:nowrap' colspan="2">
											<html:radio property="storeCard" value=""><fmt:message key="makePaymentNewCard.storeCard.no" /></html:radio><br />
											<html:radio property="storeCard" value="store"><fmt:message key="makePaymentNewCard.storeCard.store" /></html:radio><br />
											<html:radio property="storeCard" value="automatic"><fmt:message key="makePaymentNewCard.storeCard.automatic" /></html:radio>
										</td>
									</tr>
									<tr><td style='white-space:nowrap' colspan="4" align="center">
										<ao:input type="submit">
											<ao:onclick>
												this.disabled='true';
												this.form.submit();
												return false;
											</ao:onclick>
											<fmt:message key="makePaymentNewCard.field.submit.label" />
										</ao:input>
									</td></tr>
								</table>
							</skin:lightArea>
						</html:form>
						<%@include file="security-policy.jsp" %>
					</skin:popupGroup>
				</skin:contentLine>
			</skin:content>
		</skin:skin>
	</fmt:bundle>
</html:html>
