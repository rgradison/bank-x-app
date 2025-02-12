package io.absa.bankxapp.service;

import io.absa.bankxapp.model.AccountType;
import io.absa.bankxapp.model.Customer;
import io.absa.bankxapp.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final AccountService accountService;

    public CustomerService(CustomerRepository customerRepository, AccountService accountService) {
        this.customerRepository = customerRepository;
        this.accountService = accountService;
    }

    @Transactional
    public Customer onboardCustomer(String name, String email) {
        Customer customer = new Customer();
        customer.setName(name);
        customer.setEmail(email);
        customer = customerRepository.save(customer);

        accountService.createAccount(customer, AccountType.CURRENT, BigDecimal.ZERO);
        accountService.createAccount(customer, AccountType.SAVINGS, new BigDecimal("500.00"));

        return customer;

    }
}