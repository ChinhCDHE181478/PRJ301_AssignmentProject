package dev.chinhcd.backend.controller;

import dev.chinhcd.backend.dtos.requests.AccountRequest;
import dev.chinhcd.backend.dtos.requests.UserLoginRequest;
import dev.chinhcd.backend.dtos.responses.AccountResponse;
import dev.chinhcd.backend.service.AccountServiceImpl;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("${api.prefix}/accounts")
@AllArgsConstructor
public class AccountController {
    private final AccountServiceImpl accountService;

    @PostMapping("/create")
    public ResponseEntity<?> createNewAccount(@RequestBody AccountRequest accountRequest) {
        try {
            AccountResponse account = accountService.createAccount(accountRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(account);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<Set<AccountResponse>> getAllAccounts() {
        return ResponseEntity.ok(accountService.getAllAccount());
    }

    @GetMapping("/search")
    public ResponseEntity<Set<AccountResponse>> searchAccount(@RequestBody AccountRequest accountRequest) {
        return ResponseEntity.ok(accountService.getAccountsbyAccountDto(accountRequest));
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateAccount(@RequestBody AccountRequest accountRequest) {
        try{
            AccountResponse account = accountService.updateAccount(accountRequest);
            return ResponseEntity.ok(account);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteAccount(@PathVariable int id) {
        accountService.deleteAccountById(id);
        return ResponseEntity.ok("Deleted successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody UserLoginRequest userLoginRequest) {
        try {
            String token = accountService.login(userLoginRequest.username(), userLoginRequest.password());
            return ResponseEntity.ok(token);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
