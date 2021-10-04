/*
 * aoweb-struts - Template webapp for legacy Struts-based site framework with AOServ Platform control panels.
 * Copyright (C) 2007-2009, 2015, 2016, 2017, 2018, 2019, 2021  AO Industries, Inc.
 *     support@aoindustries.com
 *     7262 Bull Pen Cir
 *     Mobile, AL 36695
 *
 * This file is part of aoweb-struts.
 *
 * aoweb-struts is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * aoweb-struts is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with aoweb-struts.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.aoindustries.web.struts.clientarea.accounting;

import com.aoapps.lang.i18n.Money;
import com.aoapps.lang.i18n.Resources;
import com.aoapps.lang.validation.ValidationException;
import com.aoapps.net.URIEncoder;
import com.aoapps.payments.AuthorizationResult;
import com.aoapps.payments.CaptureResult;
import com.aoapps.payments.CreditCardProcessor;
import com.aoapps.payments.TokenizedCreditCard;
import com.aoapps.payments.Transaction;
import com.aoapps.payments.TransactionRequest;
import com.aoindustries.aoserv.client.AOServConnector;
import com.aoindustries.aoserv.client.account.Account;
import com.aoindustries.aoserv.client.billing.Currency;
import com.aoindustries.aoserv.client.billing.TransactionType;
import com.aoindustries.aoserv.client.payment.CreditCard;
import com.aoindustries.aoserv.client.payment.PaymentType;
import com.aoindustries.aoserv.client.schema.Type;
import com.aoindustries.aoserv.creditcards.AOServConnectorPrincipal;
import com.aoindustries.aoserv.creditcards.AccountGroup;
import com.aoindustries.aoserv.creditcards.CreditCardFactory;
import com.aoindustries.aoserv.creditcards.CreditCardProcessorFactory;
import com.aoindustries.web.struts.SiteSettings;
import com.aoindustries.web.struts.Skin;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

/**
 * Payment from stored credit card.
 *
 * @author  AO Industries, Inc.
 */
public class MakePaymentStoredCardCompletedAction extends MakePaymentStoredCardAction {

	static final Resources RESOURCES =
		Resources.getResources(ResourceBundle::getBundle, MakePaymentStoredCardCompletedAction.class);

	@Override
	public final ActionForward executePermissionGranted(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response,
		AOServConnector aoConn
	) throws Exception {
		MakePaymentStoredCardForm makePaymentStoredCardForm = (MakePaymentStoredCardForm)form;

		// Init request values
		Account account;
		try {
			account = aoConn.getAccount().getAccount().get(Account.Name.valueOf(makePaymentStoredCardForm.getAccount()));
		} catch(ValidationException e) {
			return mapping.findForward("make-payment");
		}
		if(account == null) {
			// Redirect back to make-payment if account not found
			return mapping.findForward("make-payment");
		}
		request.setAttribute("account", account);

		// If the card id in "", new card was selected
		String idString = makePaymentStoredCardForm.getId();
		if(idString==null) {
			// id not provided, redirect back to make-payment
			return mapping.findForward("make-payment");
		}
		if(idString.isEmpty()) {
			StringBuilder href = new StringBuilder();
			href
				.append(Skin.getSkin(request).getUrlBase(request))
				.append("clientarea/accounting/make-payment-new-card.do?account=")
				.append(URIEncoder.encodeURIComponent(request.getParameter("account")));
			String currency = request.getParameter("currency");
			if(!GenericValidator.isBlankOrNull(currency)) {
				href
					.append("&currency=")
					.append(URIEncoder.encodeURIComponent(currency));
			}
			// TODO: Many of these after-POST sendRedirect should be converted to 303 redirects
			response.sendRedirect(
				response.encodeRedirectURL(
					URIEncoder.encodeURI(
						href.toString()
					)
				)
			);
			return null;
		}

		int id;
		try {
			id = Integer.parseInt(idString);
		} catch(NumberFormatException err) {
			// Can't parse int, redirect back to make-payment
			return mapping.findForward("make-payment");
		}
		CreditCard creditCard = aoConn.getPayment().getCreditCard().get(id);
		if(creditCard == null) {
			// creditCard not found, redirect back to make-payment
			return mapping.findForward("make-payment");
		}

		request.setAttribute("account", account);
		request.setAttribute("creditCard", creditCard);

		// Validation
		ActionMessages errors = makePaymentStoredCardForm.validate(mapping, request);
		if(errors!=null && !errors.isEmpty()) {
			saveErrors(request, errors);

			return mapping.findForward("input");
		}

		Currency currency = aoConn.getBilling().getCurrency().get(makePaymentStoredCardForm.getCurrency());
		assert currency != null : "A valid form must have a valid currency";

		// Convert to money
		Money paymentAmount = new Money(currency.getCurrency(), new BigDecimal(makePaymentStoredCardForm.getPaymentAmount()));

		// Perform the transaction
		SiteSettings siteSettings = SiteSettings.getInstance(getServlet().getServletContext());
		AOServConnector rootConn = siteSettings.getRootAOServConnector();

		// 1) Pick a processor
		CreditCard rootCreditCard = rootConn.getPayment().getCreditCard().get(creditCard.getId());
		if(rootCreditCard == null) throw new SQLException("Unable to find CreditCard: " + creditCard.getId());
		com.aoindustries.aoserv.client.payment.Processor rootAoProcessor = rootCreditCard.getCreditCardProcessor();
		CreditCardProcessor rootProcessor = CreditCardProcessorFactory.getCreditCardProcessor(rootAoProcessor);

		// 2) Add the transaction as pending on this processor
		Account rootAccount = rootConn.getAccount().getAccount().get(account.getName());
		if(rootAccount == null) throw new SQLException("Unable to find Account: " + account.getName());
		TransactionType paymentTransactionType = rootConn.getBilling().getTransactionType().get(TransactionType.PAYMENT);
		if(paymentTransactionType == null) throw new SQLException("Unable to find TransactionType: " + TransactionType.PAYMENT);
		String cardInfo = creditCard.getCardInfo();
		PaymentType paymentType;
		{
			String paymentTypeName;
			// TODO: Move to a card-type microproject API and shared with ao-payments implementation
			if(
				cardInfo.startsWith("34")
				|| cardInfo.startsWith("37")
				|| cardInfo.startsWith("3" + com.aoapps.payments.CreditCard.UNKNOWN_DIGIT)
			) {
				paymentTypeName = PaymentType.AMEX;
			} else if(cardInfo.startsWith("60")) {
				paymentTypeName = PaymentType.DISCOVER;
			} else if(
				cardInfo.startsWith("51")
				|| cardInfo.startsWith("52")
				|| cardInfo.startsWith("53")
				|| cardInfo.startsWith("54")
				|| cardInfo.startsWith("55")
				|| cardInfo.startsWith("5" + com.aoapps.payments.CreditCard.UNKNOWN_DIGIT)
			) {
				paymentTypeName = PaymentType.MASTERCARD;
			} else if(cardInfo.startsWith("4")) {
				paymentTypeName = PaymentType.VISA;
			} else {
				paymentTypeName = null;
			}
			if(paymentTypeName==null) paymentType = null;
			else {
				paymentType = rootConn.getPayment().getPaymentType().get(paymentTypeName);
				if(paymentType == null) throw new SQLException("Unable to find PaymentType: " + paymentTypeName);
			}
		}
		int transid = rootConn.getBilling().getTransaction().add(
			Type.TIME,
			null,
			rootAccount,
			rootAccount,
			aoConn.getCurrentAdministrator(),
			paymentTransactionType,
			RESOURCES.getMessage("transaction.description"),
			1000,
			paymentAmount.negate(),
			paymentType,
			com.aoapps.payments.CreditCard.getCardNumberDisplay(cardInfo),
			rootAoProcessor,
			com.aoindustries.aoserv.client.billing.Transaction.WAITING_CONFIRMATION
		);
		com.aoindustries.aoserv.client.billing.Transaction aoTransaction = rootConn.getBilling().getTransaction().get(transid);
		if(aoTransaction == null) throw new SQLException("Unable to find Transaction: " + transid);

		// 3) Process
		AOServConnectorPrincipal principal = new AOServConnectorPrincipal(rootConn, aoConn.getCurrentAdministrator().getUsername().getUsername().toString());
		AccountGroup accountGroup = new AccountGroup(rootAccount, account.getName().toString());
		Transaction transaction;
		if(MakePaymentNewCardCompletedAction.DEBUG_AUTHORIZE_THEN_CAPTURE) {
			transaction = rootProcessor.authorize(
				principal,
				accountGroup,
				new TransactionRequest(
					false, // testMode
					request.getRemoteAddr(), // customerIp
					MakePaymentNewCardCompletedAction.DUPLICATE_WINDOW,
					Integer.toString(transid), // orderNumber
					paymentAmount.getCurrency(), // currency
					paymentAmount.getValue(), // amount
					null, // taxAmount
					false, // taxExempt
					null, // shippingAmount
					null, // dutyAmount
					null, // shippingFirstName
					null, // shippingLastName
					null, // shippingCompanyName
					null, // shippingStreetAddress1
					null, // shippingStreetAddress2
					null, // shippingCity
					null, // shippingState
					null, // shippingPostalCode
					null, // shippingCountryCode
					false, // emailCustomer
					null, // merchantEmail
					null, // invoiceNumber
					null, // purchaseOrderNumber
					RESOURCES.getMessage(Locale.US, "transaction.description") // description
				),
				CreditCardFactory.getCreditCard(rootCreditCard)
			);
		} else {
			transaction = rootProcessor.sale(
				principal,
				accountGroup,
				new TransactionRequest(
					false, // testMode
					request.getRemoteAddr(), // customerIp
					MakePaymentNewCardCompletedAction.DUPLICATE_WINDOW,
					Integer.toString(transid), // orderNumber
					paymentAmount.getCurrency(), // currency
					paymentAmount.getValue(), // amount
					null, // taxAmount
					false, // taxExempt
					null, // shippingAmount
					null, // dutyAmount
					null, // shippingFirstName
					null, // shippingLastName
					null, // shippingCompanyName
					null, // shippingStreetAddress1
					null, // shippingStreetAddress2
					null, // shippingCity
					null, // shippingState
					null, // shippingPostalCode
					null, // shippingCountryCode
					false, // emailCustomer
					null, // merchantEmail
					null, // invoiceNumber
					null, // purchaseOrderNumber
					RESOURCES.getMessage(Locale.US, "transaction.description") // description
				),
				CreditCardFactory.getCreditCard(rootCreditCard)
			);
		}
		// CreditCard might have been updated on root connector, invalidate and get fresh object always to avoid possible race condition
		{
			aoConn.getPayment().getCreditCard().clearCache();
			CreditCard newCreditCard = aoConn.getPayment().getCreditCard().get(id);
			if(newCreditCard != null) {
				creditCard = newCreditCard;
				request.setAttribute("creditCard", creditCard);
			}
		}

		// 4) Decline/approve based on results
		AuthorizationResult authorizationResult = transaction.getAuthorizationResult();
		TokenizedCreditCard tokenizedCreditCard = authorizationResult.getTokenizedCreditCard();
		switch(authorizationResult.getCommunicationResult()) {
			case LOCAL_ERROR :
			case IO_ERROR :
			case GATEWAY_ERROR :
			{
				// Update transaction as failed
				aoTransaction.declined(
					Integer.parseInt(transaction.getPersistenceUniqueId()),
					tokenizedCreditCard == null ? null : com.aoapps.payments.CreditCard.getCardNumberDisplay(tokenizedCreditCard.getReplacementMaskedCardNumber())
				);
				// Get the list of active credit cards stored for this account
				List<CreditCard> allCreditCards = account.getCreditCards();
				List<CreditCard> creditCards = new ArrayList<>(allCreditCards.size());
				for(CreditCard tCreditCard : allCreditCards) {
					if(tCreditCard.getDeactivatedOn()==null) creditCards.add(tCreditCard);
				}
				// Store to request attributes, return success
				request.setAttribute("account", account);
				request.setAttribute("creditCards", creditCards);
				request.setAttribute("lastPaymentCreditCard", creditCard.getProviderUniqueId());
				request.setAttribute("errorReason", authorizationResult.getErrorCode().toString());
				return mapping.findForward("error");
			}
			case SUCCESS :
				// Check approval result
				switch(authorizationResult.getApprovalResult()) {
					case HOLD :
					{
						aoTransaction.held(
							Integer.parseInt(transaction.getPersistenceUniqueId()),
							tokenizedCreditCard == null ? null : com.aoapps.payments.CreditCard.getCardNumberDisplay(tokenizedCreditCard.getReplacementMaskedCardNumber())
						);
						request.setAttribute("account", account);
						request.setAttribute("creditCard", creditCard);
						request.setAttribute("transaction", transaction);
						request.setAttribute("aoTransaction", aoTransaction);
						request.setAttribute("reviewReason", authorizationResult.getReviewReason().toString());
						return mapping.findForward("hold");
					}
					case DECLINED :
					{
						// Update transaction as declined
						aoTransaction.declined(
							Integer.parseInt(transaction.getPersistenceUniqueId()),
							tokenizedCreditCard == null ? null : com.aoapps.payments.CreditCard.getCardNumberDisplay(tokenizedCreditCard.getReplacementMaskedCardNumber())
						);
						// Get the list of active credit cards stored for this account
						List<CreditCard> allCreditCards = account.getCreditCards();
						List<CreditCard> creditCards = new ArrayList<>(allCreditCards.size());
						for(CreditCard tCreditCard : allCreditCards) {
							if(tCreditCard.getDeactivatedOn()==null) creditCards.add(tCreditCard);
						}
						// Store to request attributes, return success
						request.setAttribute("account", account);
						request.setAttribute("creditCards", creditCards);
						request.setAttribute("lastPaymentCreditCard", creditCard.getProviderUniqueId());
						request.setAttribute("declineReason", authorizationResult.getDeclineReason().toString());
						return mapping.findForward("declined");
					}
					case APPROVED :
					{
						if(MakePaymentNewCardCompletedAction.DEBUG_AUTHORIZE_THEN_CAPTURE) {
							// Perform capture in second step
							CaptureResult captureResult = rootProcessor.capture(principal, transaction);
							switch(captureResult.getCommunicationResult()) {
								case LOCAL_ERROR :
								case IO_ERROR :
								case GATEWAY_ERROR :
								{
									// Update transaction as failed
									aoTransaction.declined(
										Integer.parseInt(transaction.getPersistenceUniqueId()),
										tokenizedCreditCard == null ? null : com.aoapps.payments.CreditCard.getCardNumberDisplay(tokenizedCreditCard.getReplacementMaskedCardNumber())
									);
									// Get the list of active credit cards stored for this account
									List<CreditCard> allCreditCards = account.getCreditCards();
									List<CreditCard> creditCards = new ArrayList<>(allCreditCards.size());
									for(CreditCard tCreditCard : allCreditCards) {
										if(tCreditCard.getDeactivatedOn()==null) creditCards.add(tCreditCard);
									}
									// Store to request attributes, return success
									request.setAttribute("account", account);
									request.setAttribute("creditCards", creditCards);
									request.setAttribute("lastPaymentCreditCard", creditCard.getProviderUniqueId());
									request.setAttribute("errorReason", authorizationResult.getErrorCode().toString());
									return mapping.findForward("error");
								}
								case SUCCESS :
								{
									// Continue with processing of SUCCESS below, same as used for direct sale(...)
									break;
								}
								default:
									throw new RuntimeException("Unexpected value for capture communication result: "+captureResult.getCommunicationResult());
							}
						}
						// Update transaction as successful
						aoTransaction.approved(
							Integer.parseInt(transaction.getPersistenceUniqueId()),
							tokenizedCreditCard == null ? null : com.aoapps.payments.CreditCard.getCardNumberDisplay(tokenizedCreditCard.getReplacementMaskedCardNumber())
						);
						request.setAttribute("account", account);
						request.setAttribute("creditCard", creditCard);
						request.setAttribute("transaction", transaction);
						request.setAttribute("aoTransaction", aoTransaction);
						return mapping.findForward("success");
					}
					default:
						throw new RuntimeException("Unexpected value for authorization approval result: "+authorizationResult.getApprovalResult());
				}
			default:
				throw new RuntimeException("Unexpected value for authorization communication result: "+authorizationResult.getCommunicationResult());
		}
	}
}
