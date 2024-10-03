package dev.chinhcd.backend.controller;

import dev.chinhcd.backend.dtos.AccountDTO;
import dev.chinhcd.backend.models.Account;
import dev.chinhcd.backend.service.AccountService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/accounts")
@AllArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<Account> createNewAccount(@Valid @RequestBody AccountDTO accountDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(accountService.createAccount(accountDTO));
    }

    @GetMapping
    public ResponseEntity<List<Account>> getAllAccounts() {
        return ResponseEntity.ok(accountService.getAllAccount());
    }
}
