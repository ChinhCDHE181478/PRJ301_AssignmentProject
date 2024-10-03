package dev.chinhcd.backend.service;

import dev.chinhcd.backend.dtos.AccountDTO;
import dev.chinhcd.backend.models.Account;
import dev.chinhcd.backend.models.Employee;
import dev.chinhcd.backend.repository.AccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final EmployeeService employeeService;

    @Override
    public Account createAccount(AccountDTO accountDto) {
        var employee = employeeService.getEmployeeById(accountDto.employeeId());
        var account = Account.builder()
                .username(accountDto.username())
                .password(accountDto.password())
                .employee(employee)
                .build();
        return accountRepository.save(account);
    }

    @Override
    public List<Account> getAllAccount() {
        return accountRepository.findAll();
    }

    @Override
    public List<Account> getAccountByEmployee(Employee employee) {
        return accountRepository.findAllByEmployee(employee);
    }

    @Override
    public Account getAccountByUsername(String username) {
        return accountRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Account not found"));
    }

    @Override
    public Account getAccountById(Integer accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));
    }

    @Override
    public Account updateAccount(Integer id, AccountDTO accountDto) {
        var employee = employeeService.getEmployeeById(accountDto.employeeId());
        var account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        account.setUsername(accountDto.username());
        account.setPassword(accountDto.password());
        account.setEmployee(employee);
        return accountRepository.save(account);
    }

    @Override
    public void deleteAccount(Integer accountId) {
        var account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        accountRepository.delete(account);
    }
}
