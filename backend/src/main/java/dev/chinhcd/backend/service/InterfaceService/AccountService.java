package dev.chinhcd.backend.service.InterfaceService;

import dev.chinhcd.backend.dtos.requests.AccountRequest;
import dev.chinhcd.backend.dtos.responses.AccountResponse;
import dev.chinhcd.backend.exceptions.DataNotFoundException;

import java.util.List;

public interface AccountService {
    AccountResponse createAccount(AccountRequest accountRequest) throws DataNotFoundException;

    List<AccountResponse> getAllAccount();

    List<AccountResponse> getAccountsbyAccountDto(AccountRequest accountRequest);

    AccountResponse updateAccount(AccountRequest accountRequest) throws DataNotFoundException;

    void deleteAccountById(int id);

    String login(String username, String password) throws DataNotFoundException;
}
