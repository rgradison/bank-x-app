package io.absa.bankxapp.service;

import io.absa.bankxapp.model.Account;
import io.absa.bankxapp.model.Transaction;
import io.absa.bankxapp.repository.AccountsRepository;
import io.absa.bankxapp.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionService {

    private final AccountsRepository accountsRepository;
    private final TransactionRepository transactionRepository;

    public TransactionService(AccountsRepository accountsRepository, TransactionRepository transactionRepository) {
        this.accountsRepository = accountsRepository;
        this.transactionRepository = transactionRepository;
    }

    public void transferBetweenAccounts(Long fromAccountId, Long toAccountId, BigDecimal amount) {
        if(amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Transfer Amount must be greater than zero");
        }

        Account fromAccount = accountsRepository.findById(fromAccountId).
                orElseThrow(() -> new IllegalArgumentException("From account not found"));
        Account toAccount = accountsRepository.findById(toAccountId).
                orElseThrow(() -> new IllegalArgumentException("To account not found"));

        if(fromAccount.getCustomer().getId() != toAccount.getCustomer().getId()) {
            throw new IllegalArgumentException("From account and To account don't match");
        }

        fromAccount.debit(amount,BigDecimal.ZERO);
        toAccount.credit(amount);

        recordTransaction(fromAccount, amount.negate(), "Transfer to Account ID: " + toAccountId);
        recordTransaction(toAccount, amount, "Transfer from Account ID: " + fromAccountId);

        accountsRepository.save(fromAccount);
        accountsRepository.save(toAccount);

    }

    private void recordTransaction(Account account, BigDecimal amount, String description) {
        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setAmount(amount);
        transaction.setTimestamp(LocalDateTime.now());
        transaction.setDescription(description);
        transactionRepository.save(transaction);
   }

    public void makePayment(Long accountId, BigDecimal amount, String description) {
    }

    public List<Transaction> getTransactionHistory(Long accountId) {
        return List.of();
    }
}