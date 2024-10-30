package dev.chinhcd.backend.controllers;

import dev.chinhcd.backend.dtos.requests.AccountRequest;
import dev.chinhcd.backend.dtos.requests.RoleRequest;
import dev.chinhcd.backend.dtos.requests.UserLoginRequest;
import dev.chinhcd.backend.dtos.responses.AccountResponse;
import dev.chinhcd.backend.dtos.responses.MessageResponse;
import dev.chinhcd.backend.service.AccountServiceImpl;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("${api.prefix}/accounts")
@AllArgsConstructor
public class AccountController {
    private final AccountServiceImpl accountService;

    @PostMapping("create")
    public ResponseEntity<?> createNewAccount(@RequestBody AccountRequest accountRequest) {
        try {
            AccountResponse account = accountService.createAccount(accountRequest);
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
    public ResponseEntity<List<AccountResponse>> searchAccount(@RequestParam Optional<String> username, @RequestParam Optional<Integer> empId, @RequestParam Optional<Integer> roleId, @RequestParam Optional<String> status) {
        AccountRequest accountRequest = AccountRequest.builder()
                .username(username.orElse(null))
                .employeeId(empId.orElse(null))
                .roleRequests(RoleRequest.builder()
                        .roleId(roleId.orElse(null))
                        .build())
                .status(status.orElse(null))
                .build();
        return ResponseEntity.ok(accountService.getAccountsbyAccountDto(accountRequest));
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateAccount(@RequestBody AccountRequest accountRequest) {
        try {
            AccountResponse account = accountService.updateAccount(accountRequest);
            return ResponseEntity.ok(account);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteAccount(@PathVariable int id) {
        accountService.deleteAccountById(id);
        return ResponseEntity.ok(MessageResponse.builder().message("Deleted successfully").build());
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserLoginRequest userLoginRequest) {
        try {
            return ResponseEntity.ok(accountService.login(userLoginRequest.username(), userLoginRequest.password()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
