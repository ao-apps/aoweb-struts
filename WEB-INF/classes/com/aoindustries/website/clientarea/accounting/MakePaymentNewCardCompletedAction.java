package com.aoindustries.website.clientarea.accounting;

/*
 * Copyright 2007-2010 by AO Industries, Inc.,
 * 7262 Bull Pen Cir, Mobile, Alabama, 36695, U.S.A.
 * All rights reserved.
 */
import com.aoindustries.aoserv.client.AOServConnector;
import com.aoindustries.aoserv.client.Business;
import com.aoindustries.aoserv.client.CreditCard;
import com.aoindustries.aoserv.client.PaymentType;
import com.aoindustries.aoserv.client.TransactionType;
import com.aoindustries.aoserv.client.command.AddTransactionCommand;
import com.aoindustries.aoserv.client.command.ApproveTransactionCommand;
import com.aoindustries.aoserv.client.command.DeclineTransactionCommand;
import com.aoindustries.aoserv.client.command.HoldTransactionCommand;
import com.aoindustries.aoserv.client.command.SetCreditCardUseMonthlyCommand;
import com.aoindustries.aoserv.client.validator.AccountingCode;
import com.aoindustries.aoserv.creditcards.AOServConnectorPrincipal;
import com.aoindustries.aoserv.creditcards.BusinessGroup;
import com.aoindustries.aoserv.creditcards.CreditCardProcessorFactory;
import com.aoindustries.creditcards.AuthorizationResult;
import com.aoindustries.creditcards.CreditCardProcessor;
import com.aoindustries.creditcards.Transaction;
import com.aoindustries.creditcards.TransactionRequest;
import com.aoindustries.creditcards.TransactionResult;
import com.aoindustries.util.i18n.Money;
import com.aoindustries.website.SiteSettings;
import com.aoindustries.website.Skin;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Currency;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

/**
 * Payment from stored credit card.
 *
 * @author  AO Industries, Inc.
 */
public class MakePaymentNewCardCompletedAction extends MakePaymentNewCardAction {

    @Override
    public ActionForward execute(
        ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response,
        SiteSettings siteSettings,
        Skin skin,
        AOServConnector<?,?> aoConn
    ) throws Exception {
        MakePaymentNewCardForm makePaymentNewCardForm=(MakePaymentNewCardForm)form;

        // Init request values
        initRequestAttributes(request, getServlet().getServletContext());

        String accountingS = makePaymentNewCardForm.getAccounting();
        AccountingCode accounting = accountingS==null ? null : AccountingCode.valueOf(accountingS);
        Business business = accounting==null ? null : aoConn.getBusinesses().get(accounting);
        if(business==null) {
            // Redirect back to make-payment if business not found
            return mapping.findForward("make-payment");
        }
        request.setAttribute("business", business);

        // Validation
        ActionMessages errors = makePaymentNewCardForm.validate(mapping, request);
        if(errors!=null && !errors.isEmpty()) {
            saveErrors(request, errors);

            return mapping.findForward("input");
        }

        // Convert to money
        Money paymentAmount = new Money(Currency.getInstance(makePaymentNewCardForm.getCurrency()), new BigDecimal(makePaymentNewCardForm.getPaymentAmount()));

        // Encapsulate the values into a new credit card
        String cardNumber = makePaymentNewCardForm.getCardNumber();
        com.aoindustries.creditcards.CreditCard newCreditCard = new com.aoindustries.creditcards.CreditCard(
            null,
            null,
            null,
            null,
            null,
            cardNumber,
            null,
            Byte.parseByte(makePaymentNewCardForm.getExpirationMonth()),
            Short.parseShort(makePaymentNewCardForm.getExpirationYear()),
            null,
            makePaymentNewCardForm.getFirstName(),
            makePaymentNewCardForm.getLastName(),
            makePaymentNewCardForm.getCompanyName(),
            makePaymentNewCardForm.getEmail(),
            makePaymentNewCardForm.getPhone(),
            makePaymentNewCardForm.getFax(),
            null,
            makePaymentNewCardForm.getCustomerTaxId(),
            makePaymentNewCardForm.getStreetAddress1(),
            makePaymentNewCardForm.getStreetAddress2(),
            makePaymentNewCardForm.getCity(),
            makePaymentNewCardForm.getState(),
            makePaymentNewCardForm.getPostalCode(),
            makePaymentNewCardForm.getCountryCode(),
            makePaymentNewCardForm.getDescription()
        );

        // Perform the transaction
        AOServConnector<?,?> rootConn = siteSettings.getRootAOServConnector();
        
        // 1) Pick a processor
        CreditCardProcessor rootProcessor = CreditCardProcessorFactory.getCreditCardProcessor(rootConn);
        com.aoindustries.aoserv.client.CreditCardProcessor rootAoProcessor = rootConn.getCreditCardProcessors().get(rootProcessor.getProviderId());

        // 2) Add the transaction as pending on this processor
        Business rootBusiness = rootConn.getBusinesses().get(accounting);
        TransactionType paymentTransactionType = rootConn.getTransactionTypes().get(TransactionType.PAYMENT);
        String paymentTypeName;
        if(cardNumber.startsWith("34") || cardNumber.startsWith("37")) {
            paymentTypeName = PaymentType.AMEX;
        } else if(cardNumber.startsWith("60")) {
            paymentTypeName = PaymentType.DISCOVER;
        } else if(cardNumber.startsWith("51") || cardNumber.startsWith("52") || cardNumber.startsWith("53") || cardNumber.startsWith("54") || cardNumber.startsWith("55")) {
            paymentTypeName = PaymentType.MASTERCARD;
        } else if(cardNumber.startsWith("4")) {
            paymentTypeName = PaymentType.VISA;
        } else {
            paymentTypeName = null;
        }
        PaymentType paymentType;
        if(paymentTypeName==null) paymentType = null;
        else {
            paymentType = rootConn.getPaymentTypes().get(paymentTypeName);
        }

        int transID = new AddTransactionCommand(
            rootBusiness,
            rootBusiness,
            aoConn.getThisBusinessAdministrator(),
            paymentTransactionType,
            ApplicationResources.accessor.getMessage("makePaymentStoredCardCompleted.transaction.description"),
            BigDecimal.ONE,
            paymentAmount.negate(),
            paymentType,
            com.aoindustries.creditcards.CreditCard.maskCreditCardNumber(cardNumber),
            rootAoProcessor,
            com.aoindustries.aoserv.client.Transaction.Status.W
        ).execute(rootConn);
        com.aoindustries.aoserv.client.Transaction aoTransaction = rootConn.getTransactions().get(transID);

        // 3) Process
        AOServConnectorPrincipal principal = new AOServConnectorPrincipal(rootConn, aoConn.getThisBusinessAdministrator().getUsername().getUsername().toString());
        BusinessGroup businessGroup = new BusinessGroup(rootBusiness, accounting.toString());
        Transaction transaction = rootProcessor.sale(
            principal,
            businessGroup,
            new TransactionRequest(
                false,
                request.getRemoteAddr(),
                120,
                Integer.toString(transID),
                paymentAmount.getCurrency(),
                paymentAmount.getValue(),
                null,
                false,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                false,
                null,
                null,
                null,
                ApplicationResources.accessor.getMessage("makePaymentStoredCardCompleted.transaction.description")
            ),
            newCreditCard
        );

        // 4) Decline/approve based on results
        AuthorizationResult authorizationResult = transaction.getAuthorizationResult();
        switch(authorizationResult.getCommunicationResult()) {
            case LOCAL_ERROR :
            case IO_ERROR :
            case GATEWAY_ERROR :
            {
                // Update transaction as failed
                new DeclineTransactionCommand(
                    aoTransaction,
                    rootConn.getCreditCardTransactions().get(Integer.parseInt(transaction.getPersistenceUniqueId()))
                ).execute(rootConn);

                TransactionResult.ErrorCode errorCode = authorizationResult.getErrorCode();
                ActionMessages mappedErrors = makePaymentNewCardForm.mapTransactionError(errorCode);
                if(mappedErrors==null || mappedErrors.isEmpty()) {
                    // Not mapped, store to request attributes as generate error (to be displayed separate from specific fields)
                    request.setAttribute("errorReason", errorCode);
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
                        new HoldTransactionCommand(
                            aoTransaction,
                            rootConn.getCreditCardTransactions().get(Integer.parseInt(transaction.getPersistenceUniqueId()))
                        ).execute(rootConn);

                        // Store to request attributes
                        request.setAttribute("transaction", transaction);
                        request.setAttribute("aoTransaction", aoTransaction);
                        request.setAttribute("reviewReason", authorizationResult.getReviewReason());

                        String storeCard = makePaymentNewCardForm.getStoreCard();
                        if(
                            "store".equals(storeCard)
                            || "automatic".equals(storeCard)
                        ) {
                            // Store card
                            boolean storeSuccess;
                            try {
                                storeCard(rootProcessor, principal, businessGroup, newCreditCard);
                                request.setAttribute("cardStored", "true");
                                storeSuccess = true;
                            } catch(IOException err) {
                                getServlet().log("Unable to store card", err);
                                request.setAttribute("storeError", err);
                                storeSuccess = false;
                            } catch(RuntimeException err) {
                                getServlet().log("Unable to store card", err);
                                request.setAttribute("storeError", err);
                                storeSuccess = false;
                            }
                            if(storeSuccess && "automatic".equals(storeCard)) {
                                // Set automatic
                                try {
                                    setAutomatic(rootConn, newCreditCard, business);
                                    request.setAttribute("cardSetAutomatic", "true");
                                } catch(RuntimeException err) {
                                    getServlet().log("Unable to set automatic", err);
                                    request.setAttribute("setAutomaticError", err);
                                }
                            }
                        }
                        return mapping.findForward("hold");
                    }
                    case DECLINED :
                    {
                        // Update transaction as declined
                        new DeclineTransactionCommand(
                            aoTransaction,
                            rootConn.getCreditCardTransactions().get(Integer.parseInt(transaction.getPersistenceUniqueId()))
                        ).execute(rootConn);

                        // Store to request attributes
                        request.setAttribute("declineReason", authorizationResult.getDeclineReason());
                        return mapping.findForward("declined");
                    }
                    case APPROVED :
                    {
                        // Update transaction as successful
                        new ApproveTransactionCommand(
                            aoTransaction,
                            aoConn.getCreditCardTransactions().get(Integer.parseInt(transaction.getPersistenceUniqueId()))
                        ).execute(aoConn);

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
                                storeCard(rootProcessor, principal, businessGroup, newCreditCard);
                                request.setAttribute("cardStored", "true");
                                storeSuccess = true;
                            } catch(IOException err) {
                                getServlet().log("Unable to store card", err);
                                request.setAttribute("storeError", err);
                                storeSuccess = false;
                            } catch(RuntimeException err) {
                                getServlet().log("Unable to store card", err);
                                request.setAttribute("storeError", err);
                                storeSuccess = false;
                            }
                            if(storeSuccess && "automatic".equals(storeCard)) {
                                // Set automatic
                                try {
                                    setAutomatic(rootConn, newCreditCard, business);
                                    request.setAttribute("cardSetAutomatic", "true");
                                } catch(RuntimeException err) {
                                    getServlet().log("Unable to set automatic", err);
                                    request.setAttribute("setAutomaticError", err);
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
    
    private void storeCard(CreditCardProcessor rootProcessor, AOServConnectorPrincipal principal, BusinessGroup businessGroup, com.aoindustries.creditcards.CreditCard newCreditCard) throws IOException, SQLException {
        rootProcessor.storeCreditCard(
            principal,
            businessGroup,
            newCreditCard
        );
    }
    
    /**
     * @param  rootConn  Since rootConn is used to store the card, it must also be used to get the new instance.
     *                   Otherwise there is a race condition between the non-root AOServConnector getting the invalidation signal
     *                   and this method being called.
     */
    private void setAutomatic(AOServConnector<?,?> rootConn, com.aoindustries.creditcards.CreditCard newCreditCard, Business business) throws IOException {
        String persistenceUniqueId = newCreditCard.getPersistenceUniqueId();
        CreditCard creditCard = rootConn.getCreditCards().get(Integer.parseInt(persistenceUniqueId));
        if(!creditCard.getBusiness().equals(business)) throw new AssertionError("Requested business and CreditCard business do not match: "+creditCard.getBusiness().getAccounting()+"!="+business.getAccounting());
        new SetCreditCardUseMonthlyCommand(business, creditCard).execute(business.getService().getConnector());
    }
}
