package io.absa.bankxapp.service;

import io.absa.bankxapp.model.Account;
import io.absa.bankxapp.model.AccountType;
import io.absa.bankxapp.model.Customer;
import io.absa.bankxapp.repository.AccountsRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AccountService {

    private final AccountsRepository accountsRepository;

    public AccountService(AccountsRepository accountsRepository) {
        this.accountsRepository = accountsRepository;
    }

    public Account createAccount(Customer customer, AccountType accountType, BigDecimal initialBalance) {
        Account account = new Account();
        account.setCustomer(customer);
        account.setAccountType(accountType);
        account.setBalance(initialBalance);
        return accountsRepository.save(account);
    }

}