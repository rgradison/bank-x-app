package io.absa.bankxapp.repository;

import io.absa.bankxapp.model.Account;
import io.absa.bankxapp.model.Transaction;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TransactionRepository extends CrudRepository<Transaction, Long> {
    List<Transaction> findByAccount(Account account);
}