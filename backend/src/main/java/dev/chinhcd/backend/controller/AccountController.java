package dev.chinhcd.backend.controller;

import dev.chinhcd.backend.dtos.AccountDTO;
import dev.chinhcd.backend.models.Account;
import dev.chinhcd.backend.responses.AccountResponse;
import dev.chinhcd.backend.service.AccountService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/accounts")
@AllArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @PostMapping("/create")
    public ResponseEntity<?> createNewAccount(@RequestBody AccountDTO accountDTO) {
        if(accountService.existsByUsername(accountDTO.username())){
            return ResponseEntity.badRequest().body("This username is already in use");
        }
        try {
            Account account = accountService.createAccount(accountDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(account);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<AccountResponse>> getAllAccounts() {
        return ResponseEntity.ok(accountService.getAllAccount());
    }

    @GetMapping("/search")
    public ResponseEntity<List<AccountResponse>> searchAccount(AccountDTO accountDTO) {
        return ResponseEntity.ok(accountService.getAccountsbyAccountDto(accountDTO));
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateAccount(@RequestBody AccountDTO accountDTO) {
        if(!accountService.existsByUsername(accountDTO.username())){
            return ResponseEntity.badRequest().body("This username is not exist");
        }
        try{
            accountService.updateAccount(accountDTO);
            return ResponseEntity.ok().body("Account updated successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
