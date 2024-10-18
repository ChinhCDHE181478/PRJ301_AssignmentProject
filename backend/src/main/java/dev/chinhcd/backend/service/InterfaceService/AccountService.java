package dev.chinhcd.backend.service.InterfaceService;

import dev.chinhcd.backend.dtos.requests.AccountRequest;
import dev.chinhcd.backend.exceptions.DataNotFoundException;
import dev.chinhcd.backend.models.Account;
import dev.chinhcd.backend.dtos.responses.AccountResponse;

import java.util.List;

public interface AccountService {
    Account createAccount(AccountRequest accountRequest) throws DataNotFoundException;

    List<AccountResponse> getAllAccount();

    List<AccountResponse> getAccountsbyAccountDto(AccountRequest accountRequest);

    void updateAccount(AccountRequest accountRequest) throws DataNotFoundException;

    boolean existsByUsername(String username);
}
