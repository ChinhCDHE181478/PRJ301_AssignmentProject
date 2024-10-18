package dev.chinhcd.backend.service;

import dev.chinhcd.backend.dtos.requests.AccountRequest;
import dev.chinhcd.backend.dtos.requests.RoleRequest;
import dev.chinhcd.backend.dtos.responses.RoleResponse;
import dev.chinhcd.backend.exceptions.DataNotFoundException;
import dev.chinhcd.backend.models.Account;
import dev.chinhcd.backend.models.Employee;
import dev.chinhcd.backend.models.Role;
import dev.chinhcd.backend.repository.AccountRepository;
import dev.chinhcd.backend.dtos.responses.AccountResponse;
import dev.chinhcd.backend.repository.RoleRepository;
import dev.chinhcd.backend.service.InterfaceService.AccountService;
import dev.chinhcd.backend.service.InterfaceService.EmployeeService;
import dev.chinhcd.backend.service.InterfaceService.RoleService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final RoleRepository roleRepository;
    @PersistenceContext
    private EntityManager entityManager;
    private final AccountRepository accountRepository;
    private final EmployeeService employeeService;
    private final RoleService roleService;

    @Transactional
    @Override
    public Account createAccount(AccountRequest accountRequest) throws DataNotFoundException {
        // Kiểm tra xem username có tồn tại không
        if (accountRepository.existsByUsername(accountRequest.username())) {
            throw new IllegalArgumentException("Username already exists. Please choose another one.");
        }

        // Lấy Employee từ ID
        Optional<Employee> employee = employeeService.getEmployeeById(accountRequest.employeeId());
        if(employee.isEmpty()) {
            throw new DataNotFoundException("Employee not found for ID: " + accountRequest.employeeId());
        }

        Set<Role> roles = new HashSet<>();
        for (RoleRequest roleRequest : accountRequest.role()){
            roles.add(Role.builder().roleId(roleRequest.RoleID()).roleName(roleRequest.RoleName()).build());
        }

        // Tạo đối tượng Account
        var account = Account.builder()
                .username(accountRequest.username())
                .password(accountRequest.password())
                .employee(employee.get())
                .roles(roles)
                .status("Blocked")
                .build();

        // Lưu tài khoản vào repository
        return accountRepository.save(account);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccountResponse> getAllAccount() {
        return accountRepository.findAll().stream().map(account -> AccountResponse.builder()
                .Id(account.getUserId())
                .username(account.getUsername())
                .password(account.getPassword())
                .employeeId(account.getEmployee().getEmployeeId())
                .status(account.getStatus())
                .role(roleService.findRoleByAccountId(account.getUserId()))
                .build()).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccountResponse> getAccountsbyAccountDto(AccountRequest accountRequest) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Account> query = cb.createQuery(Account.class);
        Root<Account> account = query.from(Account.class);

        // Create a join to the Role entity
        Join<Account, Role> roleJoin = account.join("roles", JoinType.LEFT);

        Predicate predicate = cb.conjunction(); // Start with a true condition

        // Add filters based on non-null parameters
        if (accountRequest.username() != null) {
            predicate = cb.and(predicate, cb.equal(account.get("username"), accountRequest.username()));
        }

        if (accountRequest.status() != null) {
            predicate = cb.and(predicate, cb.equal(account.get("status"), accountRequest.status()));
        }

        if (accountRequest.employeeId() != null) {
            // Assuming you want to filter by employee ID
            predicate = cb.and(predicate, cb.equal(account.get("employee").get("id"), accountRequest.employeeId()));
        }

        // Filter by Role ID and Role Name
        if (accountRequest.role() != null && !accountRequest.role().isEmpty()) {
            // Create a predicate for roles
            Predicate rolePredicate = cb.disjunction(); // Start with a false condition for OR

            for (RoleRequest roleRequest : accountRequest.role()) {
                Predicate currentRolePredicate = cb.conjunction(); // Start with true for AND

                // Filter by Role ID if present
                if (roleRequest.RoleID() != null) {
                    currentRolePredicate = cb.and(currentRolePredicate, cb.equal(roleJoin.get("roleId"), roleRequest.RoleID()));
                }

                // Filter by Role Name if present
                if (roleRequest.RoleName() != null) {
                    currentRolePredicate = cb.and(currentRolePredicate, cb.equal(roleJoin.get("roleName"), roleRequest.RoleName()));
                }

                // Add the current role predicate to the main role predicate
                rolePredicate = cb.or(rolePredicate, currentRolePredicate);
            }

            // Combine the role predicate with the main predicate
            predicate = cb.and(predicate, rolePredicate);
        }

        query.where(predicate);
        List<Account> accounts = entityManager.createQuery(query).getResultList();

        // Map to AccountResponse
        return accounts.stream()
                .map(accountEntity -> AccountResponse.builder()
                        .Id(accountEntity.getUserId())
                        .username(accountEntity.getUsername())
                        .password(accountEntity.getPassword()) // Consider handling password securely
                        .employeeId(accountEntity.getEmployee().getEmployeeId())
                        .role(roleService.findRoleByAccountId(accountEntity.getUserId()))
                        .build())
                .collect(Collectors.toList());
    }


    @Override
    @Transactional
    public void updateAccount(AccountRequest accountRequest) throws DataNotFoundException {
        if(!this.existsByUsername(accountRequest.username())){
            throw new DataNotFoundException("This username is not exist.");
        }

        // Tìm tài khoản cần cập nhật theo username
        Account accountToUpdate = entityManager.createQuery(
                        "SELECT a FROM Account a WHERE a.username = ?1", Account.class)
                .setParameter(1, accountRequest.username())
                .getSingleResult();

        if (accountToUpdate == null) {
            throw new EntityNotFoundException("Account not found for username: " + accountRequest.username());
        }

        // Chỉ cập nhật status nếu nó khác null
        if (accountRequest.status() != null) {
            accountToUpdate.setStatus(accountRequest.status());
        }

        // Chỉ cập nhật password nếu nó khác null
        if (accountRequest.password() != null) {
            accountToUpdate.setPassword(accountRequest.password());
        }

        // Lưu thay đổi vào cơ sở dữ liệu
        entityManager.merge(accountToUpdate);
    }

    @Override
    public boolean existsByUsername(String username) {
        return accountRepository.existsByUsername(username);
    }

}
