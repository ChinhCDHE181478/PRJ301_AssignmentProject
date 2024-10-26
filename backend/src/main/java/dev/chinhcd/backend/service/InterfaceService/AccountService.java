package dev.chinhcd.backend.service.InterfaceService;

import dev.chinhcd.backend.dtos.requests.AccountRequest;
import dev.chinhcd.backend.exceptions.DataNotFoundException;
import dev.chinhcd.backend.models.Account;
import dev.chinhcd.backend.dtos.responses.AccountResponse;

import java.util.Set;

public interface AccountService {
    AccountResponse createAccount(AccountRequest accountRequest) throws DataNotFoundException;

    Set<AccountResponse> getAllAccount();

    Set<AccountResponse> getAccountsbyAccountDto(AccountRequest accountRequest);

    AccountResponse updateAccount(AccountRequest accountRequest) throws DataNotFoundException;

    void deleteAccountById(int id);

    String login(String username, String password) throws DataNotFoundException;
}
