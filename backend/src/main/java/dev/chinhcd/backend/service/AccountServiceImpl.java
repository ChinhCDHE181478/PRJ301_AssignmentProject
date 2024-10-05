package dev.chinhcd.backend.service;

import dev.chinhcd.backend.dtos.AccountDTO;
import dev.chinhcd.backend.dtos.EmployeeDTO;
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
    public List<Account> getAllAccountbyAccountDto(AccountDTO accountDto) {
        return List.of();
    }

    @Override
    public void updateAccount(AccountDTO accountDto) {

    }

}
