/*
 * aoweb-struts - Template webapp for legacy Struts-based site framework with AOServ Platform control panels.
 * Copyright (C) 2007-2009, 2015, 2016, 2017, 2018, 2019, 2020, 2021  AO Industries, Inc.
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

import com.aoapps.lang.Strings;
import com.aoapps.lang.i18n.Money;
import com.aoapps.lang.validation.ValidationException;
import com.aoapps.net.Email;
import com.aoapps.payments.AuthorizationResult;
import com.aoapps.payments.CaptureResult;
import com.aoapps.payments.CreditCardProcessor;
import com.aoapps.payments.TokenizedCreditCard;
import com.aoapps.payments.Transaction;
import com.aoapps.payments.TransactionRequest;
import com.aoapps.payments.TransactionResult;
import com.aoindustries.aoserv.client.AOServConnector;
import com.aoindustries.aoserv.client.account.Account;
import com.aoindustries.aoserv.client.account.Profile;
import com.aoindustries.aoserv.client.billing.Currency;
import com.aoindustries.aoserv.client.billing.TransactionType;
import com.aoindustries.aoserv.client.payment.CreditCard;
import com.aoindustries.aoserv.client.payment.PaymentType;
import com.aoindustries.aoserv.client.schema.Type;
import com.aoindustries.aoserv.creditcards.AOServConnectorPrincipal;
import com.aoindustries.aoserv.creditcards.AccountGroup;
import com.aoindustries.aoserv.creditcards.CreditCardProcessorFactory;
import com.aoindustries.web.struts.SiteSettings;
import com.aoindustries.web.struts.Skin;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.MessageResources;

/**
 * Payment from stored credit card.
 *
 * @author  AO Industries, Inc.
 */
public class MakePaymentNewCardCompletedAction extends MakePaymentNewCardAction {

	/**
	 * Process as separate authorize/capture.  This is useful for testing payment API implementations.
	 * This should always be "false" for a production release.
	 * TODO: Make this a context parameter?
	 */
	static final boolean DEBUG_AUTHORIZE_THEN_CAPTURE = false;

	static final int DUPLICATE_WINDOW = 120;

	static String getFirstBillingEmail(Profile profile) {
		if(profile == null) return null;
		Set<Email> emails = profile.getBillingEmail();
		if(emails.isEmpty()) return null;
		return emails.iterator().next().toString();
	}

	@Override
	@SuppressWarnings({"UseSpecificCatch", "TooBroadCatch"})
	final public ActionForward execute(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response,
		Locale locale,
		Skin skin,
		AOServConnector aoConn
	) throws Exception {
		MakePaymentNewCardForm makePaymentNewCardForm=(MakePaymentNewCardForm)form;

		// Init request values
		initRequestAttributes(request, getServlet().getServletContext());

		Account account;
		try {
			account = aoConn.getAccount().getAccount().get(Account.Name.valueOf(makePaymentNewCardForm.getAccount()));
		} catch(ValidationException e) {
			return mapping.findForward("make-payment");
		}
		if(account == null) {
			// Redirect back to make-payment if account not found
			return mapping.findForward("make-payment");
		}
		request.setAttribute("account", account);

		// Validation
		ActionMessages errors = makePaymentNewCardForm.validate(mapping, request);
		if(errors!=null && !errors.isEmpty()) {
			saveErrors(request, errors);

			return mapping.findForward("input");
		}

		Currency currency = aoConn.getBilling().getCurrency().get(makePaymentNewCardForm.getCurrency());
		assert currency != null : "A valid form must have a valid currency";

		// Convert to money
		Money paymentAmount = new Money(currency.getCurrency(), new BigDecimal(makePaymentNewCardForm.getPaymentAmount()));

		// Encapsulate the values into a new credit card
		String cardNumber = makePaymentNewCardForm.getCardNumber();
		String principalName = aoConn.getCurrentAdministrator().getUsername().getUsername().toString();
		String groupName = account.getName().toString();
		Profile profile = account.getProfile();
		com.aoapps.payments.CreditCard newCreditCard = new com.aoapps.payments.CreditCard(
			null, // persistenceUniqueId
			principalName,
			groupName,
			null, // providerId
			null, // providerUniqueId
			cardNumber,
			null, // maskedCardNumber
			Byte.parseByte(makePaymentNewCardForm.getExpirationMonth()),
			Short.parseShort(makePaymentNewCardForm.getExpirationYear()),
			makePaymentNewCardForm.getCardCode(),
			makePaymentNewCardForm.getFirstName(),
			makePaymentNewCardForm.getLastName(),
			makePaymentNewCardForm.getCompanyName(),
			getFirstBillingEmail(profile),
			profile == null ? null : Strings.trimNullIfEmpty(profile.getPhone()),
			profile == null ? null : Strings.trimNullIfEmpty(profile.getFax()),
			null, // customerId: TODO: Set from account.Account once there is a constant identifier
			null, // customerTaxId
			makePaymentNewCardForm.getStreetAddress1(),
			makePaymentNewCardForm.getStreetAddress2(),
			makePaymentNewCardForm.getCity(),
			makePaymentNewCardForm.getState(),
			makePaymentNewCardForm.getPostalCode(),
			makePaymentNewCardForm.getCountryCode(),
			makePaymentNewCardForm.getDescription()
		);

		// Perform the transaction
		SiteSettings siteSettings = SiteSettings.getInstance(getServlet().getServletContext());
		AOServConnector rootConn = siteSettings.getRootAOServConnector();

		// 1) Pick a processor
		CreditCardProcessor rootProcessor = CreditCardProcessorFactory.getCreditCardProcessor(rootConn);
		if(rootProcessor==null) throw new SQLException("Unable to find enabled CreditCardProcessor for root connector");
		com.aoindustries.aoserv.client.payment.Processor rootAoProcessor = rootConn.getPayment().getProcessor().get(rootProcessor.getProviderId());
		if(rootAoProcessor == null) throw new SQLException("Unable to find CreditCardProcessor: " + rootProcessor.getProviderId());

		// 2) Add the transaction as pending on this processor
		Account rootAccount = rootConn.getAccount().getAccount().get(account.getName());
		if(rootAccount == null) throw new SQLException("Unable to find Account: " + account.getName());
		TransactionType paymentTransactionType = rootConn.getBilling().getTransactionType().get(TransactionType.PAYMENT);
		if(paymentTransactionType == null) throw new SQLException("Unable to find TransactionType: " + TransactionType.PAYMENT);
		MessageResources applicationResources = (MessageResources)request.getAttribute("/clientarea/accounting/ApplicationResources");
		String cardInfo = com.aoapps.payments.CreditCard.maskCreditCardNumber(cardNumber);
		PaymentType paymentType;
		{
			String paymentTypeName;
			// TODO: Move to a card-type microproject API and shared with ao-payments implementation
			if(
				cardNumber.startsWith("34")
				|| cardNumber.startsWith("37")
				|| cardNumber.startsWith("3" + com.aoapps.payments.CreditCard.UNKNOWN_DIGIT)) {
				paymentTypeName = PaymentType.AMEX;
			} else if(cardNumber.startsWith("60")) {
				paymentTypeName = PaymentType.DISCOVER;
			} else if(
				cardNumber.startsWith("51")
				|| cardNumber.startsWith("52")
				|| cardNumber.startsWith("53")
				|| cardNumber.startsWith("54")
				|| cardNumber.startsWith("55")
				|| cardNumber.startsWith("5" + com.aoapps.payments.CreditCard.UNKNOWN_DIGIT)
			) {
				paymentTypeName = PaymentType.MASTERCARD;
			} else if(cardNumber.startsWith("4")) {
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
			applicationResources.getMessage(locale, "makePaymentStoredCardCompleted.transaction.description"),
			1000,
			paymentAmount.negate(),
			paymentType,
			com.aoapps.payments.CreditCard.getCardNumberDisplay(cardInfo),
			rootAoProcessor,
			com.aoindustries.aoserv.client.billing.Transaction.WAITING_CONFIRMATION
		);
		com.aoindustries.aoserv.client.billing.Transaction aoTransaction = rootConn.getBilling().getTransaction().get(transid);
		if(aoTransaction == null) throw new SQLException("Unable to find Transaction: " + transid);

		// TODO: Store card before sale, and use its stored card ID (once ao-payments can throw ErrorCodeException on store card)
		// TODO: This currently implementation provides disconnected first payment and stored card in Stripe.

		// 3) Process
		AOServConnectorPrincipal principal = new AOServConnectorPrincipal(rootConn, principalName);
		AccountGroup accountGroup = new AccountGroup(rootAccount, groupName);
		Transaction transaction;
		if(DEBUG_AUTHORIZE_THEN_CAPTURE) {
			transaction = rootProcessor.authorize(
				principal,
				accountGroup,
				new TransactionRequest(
					false, // testMode
					request.getRemoteAddr(), // customerIp
					DUPLICATE_WINDOW,
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
					applicationResources.getMessage(Locale.US, "makePaymentStoredCardCompleted.transaction.description") // description
				),
				newCreditCard
			);
		} else {
			transaction = rootProcessor.sale(
				principal,
				accountGroup,
				new TransactionRequest(
					false, // testMode
					request.getRemoteAddr(), // customerIp
					DUPLICATE_WINDOW,
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
					applicationResources.getMessage(Locale.US, "makePaymentStoredCardCompleted.transaction.description") // description
				),
				newCreditCard
			);
		}
		// TODO: CreditCard might have been updated on root connector, invalidate and get fresh object always to avoid possible race condition

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

				TransactionResult.ErrorCode errorCode = authorizationResult.getErrorCode();
				ActionMessages mappedErrors = makePaymentNewCardForm.mapTransactionError(errorCode);
				if(mappedErrors==null || mappedErrors.isEmpty()) {
					// Not mapped, store to request attributes as generate error (to be displayed separate from specific fields)
					request.setAttribute("errorReason", errorCode.toString());
				} else {
					// Store for display with specific fields
					saveErrors(request, mappedErrors);
				}
				return mapping.findForward("error");
			}
			case SUCCESS :
				// Check approval result
				switch(authorizationResult.getApprovalResult()) {
					case HOLD :
					{
						// Update transaction as held
						aoTransaction.held(
							Integer.parseInt(transaction.getPersistenceUniqueId()),
							tokenizedCreditCard == null ? null : com.aoapps.payments.CreditCard.getCardNumberDisplay(tokenizedCreditCard.getReplacementMaskedCardNumber())
						);

						// Store to request attributes
						request.setAttribute("transaction", transaction);
						request.setAttribute("aoTransaction", aoTransaction);
						request.setAttribute("reviewReason", authorizationResult.getReviewReason().toString());

						String storeCard = makePaymentNewCardForm.getStoreCard();
						if(
							"store".equals(storeCard)
							|| "automatic".equals(storeCard)
						) {
							// Store card
							boolean storeSuccess;
							try {
								storeCard(rootProcessor, principal, accountGroup, newCreditCard);
								request.setAttribute("cardStored", "true");
								storeSuccess = true;
							} catch(ThreadDeath td) {
								throw td;
							} catch(Throwable t) {
								getServlet().log("Unable to store card", t);
								request.setAttribute("storeError", t);
								storeSuccess = false;
							}
							if(storeSuccess && "automatic".equals(storeCard)) {
								// Set automatic
								try {
									setAutomatic(rootConn, newCreditCard, account);
									request.setAttribute("cardSetAutomatic", "true");
								} catch(ThreadDeath td) {
									throw td;
								} catch(Throwable t) {
									getServlet().log("Unable to set automatic", t);
									request.setAttribute("setAutomaticError", t);
								}
							}
						}
						return mapping.findForward("hold");
					}
					case DECLINED :
					{
						// Update transaction as declined
						aoTransaction.declined(
							Integer.parseInt(transaction.getPersistenceUniqueId()),
							tokenizedCreditCard == null ? null : com.aoapps.payments.CreditCard.getCardNumberDisplay(tokenizedCreditCard.getReplacementMaskedCardNumber())
						);

						// Store to request attributes
						request.setAttribute("declineReason", authorizationResult.getDeclineReason().toString());
						return mapping.findForward("declined");
					}
					case APPROVED :
					{
						if(DEBUG_AUTHORIZE_THEN_CAPTURE) {
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

									TransactionResult.ErrorCode errorCode = authorizationResult.getErrorCode();
									ActionMessages mappedErrors = makePaymentNewCardForm.mapTransactionError(errorCode);
									if(mappedErrors==null || mappedErrors.isEmpty()) {
										// Not mapped, store to request attributes as generate error (to be displayed separate from specific fields)
										request.setAttribute("errorReason", errorCode.toString());
									} else {
										// Store for display with specific fields
										saveErrors(request, mappedErrors);
									}
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

						// Store to request attributes
						request.setAttribute("transaction", transaction);
						request.setAttribute("aoTransaction", aoTransaction);

						String storeCard = makePaymentNewCardForm.getStoreCard();
						if(
							"store".equals(storeCard)
							|| "automatic".equals(storeCard)
						) {
							// Store card
							boolean storeSuccess;
							try {
								storeCard(rootProcessor, principal, accountGroup, newCreditCard);
								request.setAttribute("cardStored", "true");
								storeSuccess = true;
							} catch(ThreadDeath td) {
								throw td;
							} catch(Throwable t) {
								getServlet().log("Unable to store card", t);
								request.setAttribute("storeError", t);
								storeSuccess = false;
							}
							if(storeSuccess && "automatic".equals(storeCard)) {
								// Set automatic
								try {
									setAutomatic(rootConn, newCreditCard, account);
									request.setAttribute("cardSetAutomatic", "true");
								} catch(ThreadDeath td) {
									throw td;
								} catch(Throwable t) {
									getServlet().log("Unable to set automatic", t);
									request.setAttribute("setAutomaticError", t);
								}
							}
						}
						return mapping.findForward("success");
					}
					default:
						throw new RuntimeException("Unexpected value for authorization approval result: "+authorizationResult.getApprovalResult());
				}
			default:
				throw new RuntimeException("Unexpected value for authorization communication result: "+authorizationResult.getCommunicationResult());
		}
	}

	private void storeCard(CreditCardProcessor rootProcessor, AOServConnectorPrincipal principal, AccountGroup accountGroup, com.aoapps.payments.CreditCard newCreditCard) throws SQLException, IOException {
		rootProcessor.storeCreditCard(
			principal,
			accountGroup,
			newCreditCard
		);
	}

	/**
	 * @param  rootConn  Since rootConn is used to store the card, it must also be used to get the new instance.
	 *                   Otherwise there is a race condition between the non-root AOServConnector getting the invalidation signal
	 *                   and this method being called.
	 */
	private void setAutomatic(AOServConnector rootConn, com.aoapps.payments.CreditCard newCreditCard, Account account) throws SQLException, IOException {
		String persistenceUniqueId = newCreditCard.getPersistenceUniqueId();
		CreditCard creditCard = rootConn.getPayment().getCreditCard().get(Integer.parseInt(persistenceUniqueId));
		if(creditCard == null) throw new SQLException("Unable to find CreditCard: " + persistenceUniqueId);
		if(!creditCard.getAccount().equals(account)) throw new SQLException("Requested account and CreditCard account do not match: " + creditCard.getAccount_name() + "!=" + account.getName());
		account.setUseMonthlyCreditCard(creditCard);
	}
}
