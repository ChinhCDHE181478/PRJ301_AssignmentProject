package dev.chinhcd.backend.service;

import dev.chinhcd.backend.dtos.AccountDTO;
import dev.chinhcd.backend.models.Account;
import dev.chinhcd.backend.models.Employee;
import dev.chinhcd.backend.models.Role;
import dev.chinhcd.backend.repository.AccountRepository;
import dev.chinhcd.backend.responses.AccountResponse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {
    @PersistenceContext
    private EntityManager entityManager;
    private final AccountRepository accountRepository;
    private final EmployeeService employeeService;
    private final RoleService roleService;

    @Override
    public Account createAccount(AccountDTO accountDto) {
        // Kiểm tra xem username có tồn tại không
        if (accountRepository.existsByUsername(accountDto.username())) {
            throw new IllegalArgumentException("Username already exists. Please choose another one.");
        }

        // Lấy Employee từ ID
        var employee = employeeService.getEmployeeById(accountDto.employeeId());

        // Tạo đối tượng Account
        var account = Account.builder()
                .username(accountDto.username())
                .password(accountDto.password())
                .employee(employee)
                .status("Blocked")
                .build();

        // Lưu tài khoản vào repository
        return accountRepository.save(account);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccountResponse> getAllAccount() {
        return accountRepository.findAll().stream().map(account -> {
            return AccountResponse.builder()
                    .username(account.getUsername())
                    .employeeId(account.getEmployee().getEmployeeId())
                    .status(account.getStatus())
                    .build();
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccountResponse> getAccountsbyAccountDto(AccountDTO accountDto) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Account> query = cb.createQuery(Account.class);
        Root<Account> accountRoot = query.from(Account.class);
        Join<Account, Role> roleJoin = accountRoot.join("roles", JoinType.LEFT);

        List<Predicate> predicates = new ArrayList<>();

        // Thêm các điều kiện tìm kiếm theo accountDto
        if (accountDto.username() != null && !accountDto.username().isBlank()) {
            predicates.add(cb.equal(accountRoot.get("username"), accountDto.username()));
        }
        if (accountDto.password() != null && !accountDto.password().isBlank()) {
            predicates.add(cb.equal(accountRoot.get("password"), accountDto.password()));
        }
        if (accountDto.employeeId() != null) {
            predicates.add(cb.equal(accountRoot.get("employee").get("employeeId"), accountDto.employeeId()));
        }
        if (accountDto.status() != null && !accountDto.status().isBlank()) {
            predicates.add(cb.equal(accountRoot.get("status"), accountDto.status()));
        }

        query.select(accountRoot).where(predicates.toArray(new Predicate[0]));

        // Lấy danh sách tài khoản
        List<Account> accounts = entityManager.createQuery(query).getResultList();

        // Chuyển đổi danh sách tài khoản sang danh sách AccountResponse và loại bỏ bản sao
        return accounts.stream()
                .distinct() // Đảm bảo tài khoản duy nhất
                .map(account -> {
                    Set<String> roles = account.getRoles().stream()
                            .map(Role::getRoleName) //getRoleName trong Role
                            .collect(Collectors.toSet());

                    return AccountResponse.builder()
                            .username(account.getUsername())
                            .employeeId(account.getEmployee().getEmployeeId())
                            .role(roles)
                            .status(account.getStatus())
                            .build();
                })
                .collect(Collectors.toList());
    }


    @Override
    @Transactional
    public void updateAccount(AccountDTO accountDto) {
        // Kiểm tra username có khác null không
        if (accountDto.username() == null) {
            throw new IllegalArgumentException("Username must not be null for updating an account.");
        }

        // Tìm tài khoản cần cập nhật theo username
        Account accountToUpdate = entityManager.createQuery(
                        "SELECT a FROM Account a WHERE a.username = ?1", Account.class)
                .setParameter(1, accountDto.username())
                .getSingleResult();

        if (accountToUpdate == null) {
            throw new EntityNotFoundException("Account not found for username: " + accountDto.username());
        }

        // Chỉ cập nhật status nếu nó khác null
        if (accountDto.status() != null) {
            accountToUpdate.setStatus(accountDto.status());
        }

        // Chỉ cập nhật password nếu nó khác null
        if (accountDto.password() != null) {
            accountToUpdate.setPassword(accountDto.password());
        }

        if (accountDto.role() != null && !accountDto.role().isEmpty()) {
            Set<Role> rolesToAdd = accountDto.role().stream()
                    .map(roleName -> roleService.findByName(roleName)
                            .orElseThrow(() -> new EntityNotFoundException("Role not found: " + roleName)))
                    .collect(Collectors.toSet());
            accountToUpdate.setRoles(rolesToAdd);
        }

        // Lưu thay đổi vào cơ sở dữ liệu
        entityManager.merge(accountToUpdate);
    }

    @Override
    public boolean existsByUsername(String username) {
        return accountRepository.existsByUsername(username);
    }

}
