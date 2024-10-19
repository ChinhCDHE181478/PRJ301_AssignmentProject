package dev.chinhcd.backend.service.InterfaceService;

import dev.chinhcd.backend.dtos.requests.AccountRequest;
import dev.chinhcd.backend.exceptions.DataNotFoundException;
import dev.chinhcd.backend.models.Account;
import dev.chinhcd.backend.dtos.responses.AccountResponse;

import java.util.Set;

public interface AccountService {
    Account createAccount(AccountRequest accountRequest) throws DataNotFoundException;

    Set<AccountResponse> getAllAccount();

    Set<AccountResponse> getAccountsbyAccountDto(AccountRequest accountRequest);

    Account updateAccount(AccountRequest accountRequest) throws DataNotFoundException;

    String login(String username, String password) throws DataNotFoundException;
}
