package dev.chinhcd.backend.service;

import dev.chinhcd.backend.dtos.AccountDTO;
import dev.chinhcd.backend.dtos.EmployeeDTO;
import dev.chinhcd.backend.models.Account;

import java.util.List;

public interface AccountService {
    Account createAccount(AccountDTO accountDto);

    List<Account> getAllAccount();

    List<Account> getAllAccountbyAccountDto(AccountDTO accountDto);

    void updateAccount(AccountDTO accountDto);
}
