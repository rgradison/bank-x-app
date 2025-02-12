package io.absa.bankxapp.repository;

import io.absa.bankxapp.model.Account;
import org.springframework.data.repository.CrudRepository;

public interface AccountsRepository extends CrudRepository<Account, Long> {
}
