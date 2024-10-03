package dev.chinhcd.backend.service;

import dev.chinhcd.backend.dtos.AccountDTO;
import dev.chinhcd.backend.models.Account;
import dev.chinhcd.backend.models.Employee;

import java.util.List;

public interface AccountService {
    Account createAccount(AccountDTO account);
    List<Account> getAllAccount();

    List<Account> getAccountByEmployee(Employee employee);

    Account getAccountByUsername(String username);

    Account getAccountById(Integer accountId);

    Account updateAccount(Integer id, AccountDTO account);

    void deleteAccount(Integer accountId);
}
