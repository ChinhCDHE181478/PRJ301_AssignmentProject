package dev.chinhcd.backend.service;

import dev.chinhcd.backend.dtos.AccountDTO;
import dev.chinhcd.backend.models.Account;
import dev.chinhcd.backend.responses.AccountResponse;

import java.util.List;

public interface AccountService {
    Account createAccount(AccountDTO accountDto);

    List<AccountResponse> getAllAccount();

    List<AccountResponse> getAccountsbyAccountDto(AccountDTO accountDto);

    void updateAccount(AccountDTO accountDto);

    boolean existsByUsername(String username);
}
