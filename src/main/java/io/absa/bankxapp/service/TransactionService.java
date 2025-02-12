package io.absa.bankxapp.service;

import io.absa.bankxapp.model.Account;
import io.absa.bankxapp.model.AccountType;
import io.absa.bankxapp.model.NotificationType;
import io.absa.bankxapp.model.Transaction;
import io.absa.bankxapp.repository.AccountsRepository;
import io.absa.bankxapp.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionService {

    private final AccountsRepository accountsRepository;
    private final TransactionRepository transactionRepository;
    private  final NotificationService notificationService;

    public TransactionService(AccountsRepository accountsRepository,
                              TransactionRepository transactionRepository,
                              NotificationService notificationService
                              ) {
        this.accountsRepository = accountsRepository;
        this.transactionRepository = transactionRepository;
        this.notificationService = notificationService;
    }

    @Transactional
    public void transferBetweenAccounts(Long fromAccountId, Long toAccountId, BigDecimal amount) {
        if(amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Transfer Amount must be greater than zero");
        }

        Account fromAccount = accountsRepository.findById(fromAccountId).orElseThrow(() -> new IllegalArgumentException("From account not found"));
        Account toAccount = accountsRepository.findById(toAccountId).orElseThrow(() -> new IllegalArgumentException("To account not found"));

        if(fromAccount.getCustomer().getId() != toAccount.getCustomer().getId()) {
            throw new IllegalArgumentException("From account and To account don't match");
        }

        fromAccount.debit(amount,BigDecimal.ZERO);
        toAccount.credit(amount);

        recordTransaction(fromAccount, amount.negate(), "Transfer to Account ID: " + toAccountId);
        recordTransaction(toAccount, amount, "Transfer from Account ID: " + fromAccountId);

        accountsRepository.save(fromAccount);
        accountsRepository.save(toAccount);

        // Send notifications
        notificationService.sendNotification(fromAccount.getCustomer(), NotificationType.TRANSACTION_SUCCESS,
                "You have transferred " + amount + " to account " + toAccountId);
        notificationService.sendNotification(toAccount.getCustomer(), NotificationType.PAYMENT_RECEIVED,
                "You have received " + amount + " from account " + fromAccountId);

    }

    //Records a transction in a database
    private void recordTransaction(Account account, BigDecimal amount, String description) {
        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setAmount(amount);
        transaction.setTimestamp(LocalDateTime.now());
        transaction.setDescription(description);
        transactionRepository.save(transaction);
   }

   @Transactional
    public void makePayment(Long accountId, BigDecimal amount, String description) {
        if(amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Payment Amount must be greater than zero");
        }

        Account account = accountsRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));

       if(account.getAccountType() != AccountType.CURRENT){
           throw new IllegalArgumentException("Payments can only be made from current account,Account type is not current");
       }

       BigDecimal transactionFee = amount.multiply(BigDecimal.valueOf(0.0005));
       account.debit(amount, transactionFee);

       recordTransaction(account, amount.negate(), description + " (Transaction Fee: " + transactionFee + ")");
       accountsRepository.save(account);

    }

    @Transactional(readOnly = true)
    public List<Transaction> getTransactionHistory(Long accountId) {
        Account account = accountsRepository.findById(accountId).
                orElseThrow(() -> new IllegalArgumentException("Account not found."));
        return transactionRepository.findByAccount(account);

    }
}