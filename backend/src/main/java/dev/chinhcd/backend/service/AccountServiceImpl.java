package dev.chinhcd.backend.service;

import dev.chinhcd.backend.components.JwtTokenUtil;
import dev.chinhcd.backend.dtos.requests.AccountRequest;
import dev.chinhcd.backend.dtos.requests.RoleRequest;
import dev.chinhcd.backend.exceptions.DataNotFoundException;
import dev.chinhcd.backend.models.Account;
import dev.chinhcd.backend.models.Employee;
import dev.chinhcd.backend.models.Role;
import dev.chinhcd.backend.repository.AccountRepository;
import dev.chinhcd.backend.dtos.responses.AccountResponse;
import dev.chinhcd.backend.service.InterfaceService.AccountService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AccountServiceImpl implements AccountService {
    @PersistenceContext
    private final EntityManager entityManager;
    private final AccountRepository accountRepository;
    private final EmployeeServiceImpl employeeService;
    private final RoleServiceImpl roleService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;

    @Transactional
    @Override
    public Account createAccount(AccountRequest accountRequest) throws DataNotFoundException {

        // Lấy Employee từ ID
        Optional<Employee> employee = employeeService.getEmployeeById(accountRequest.employeeId());
        if (employee.isEmpty()) {
            throw new DataNotFoundException("Employee not found for ID: " + accountRequest.employeeId());
        }

        Set<Role> roles = new HashSet<>();
        // Lấy các role từ cơ sở dữ liệu thay vì tạo mới
        if (accountRequest.roleRequests() != null) {
            for (RoleRequest roleRequest : accountRequest.roleRequests()) {
                Optional<Role> existingRole = roleService.findRoleById(roleRequest.RoleID());
                if (existingRole.isPresent()) {
                    roles.add(existingRole.get());
                } else {
                    throw new DataNotFoundException("Role not found with ID: " + roleRequest.RoleID());
                }
            }
        }

        // Tạo đối tượng Account
        var account = Account.builder()
                .username(accountRequest.username())
                .password(passwordEncoder.encode(accountRequest.password()))
                .employee(employee.get())
                .roles(roles)
                .status("Blocked")
                .build();

        // Lưu tài khoản vào repository
        return accountRepository.save(account);
    }

    @Override
    @Transactional(readOnly = true)
    public Set<AccountResponse> getAllAccount() {
        return accountRepository.findAll().stream().map(account -> AccountResponse.builder()
                .Id(account.getUserId())
                .username(account.getUsername())
                .password(account.getPassword())
                .employeeId(account.getEmployee().getEmployeeId())
                .status(account.getStatus())
                .roleResponses(roleService.findRoleByAccountId(account.getUserId()))
                .build()).collect(Collectors.toSet());
    }

    @Override
    @Transactional(readOnly = true)
    public Set<AccountResponse> getAccountsbyAccountDto(AccountRequest accountRequest) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Account> query = cb.createQuery(Account.class);
        Root<Account> account = query.from(Account.class);

        // Create a join to the Role entity
        Join<Account, Role> roleJoin = account.join("roles", JoinType.LEFT);

        Predicate predicate = cb.conjunction(); // Start with a true condition

        // Add filters based on non-null parameters
        if (accountRequest.username() != null && !accountRequest.username().isBlank()) {
            // Sử dụng LOWER để so sánh không phân biệt hoa thường cho username
            predicate = cb.and(predicate,
                    cb.like(cb.lower(account.get("username")),
                            "%" + accountRequest.username().toLowerCase() + "%"));
        }

        if (accountRequest.status() != null && !accountRequest.status().isBlank()) {
            predicate = cb.and(predicate, cb.equal(account.get("status"), accountRequest.status()));
        }

        // Check if employeeId is not null
        if (accountRequest.employeeId() != null) {
            predicate = cb.and(predicate,
                    cb.equal(account.get("employee").get("id"), accountRequest.employeeId()));
        }


        // Filter by Role ID and Role Name
        if (accountRequest.roleRequests() != null && !accountRequest.roleRequests().isEmpty()) {
            // Create a predicate for roles
            Predicate rolePredicate = cb.disjunction(); // Start with false condition for OR

            for (RoleRequest roleRequest : accountRequest.roleRequests()) {
                Predicate currentRolePredicate = cb.conjunction(); // Start with true for AND

                // Filter by Role ID if present
                if (roleRequest.RoleID() != null) {
                    currentRolePredicate = cb.and(currentRolePredicate, cb.equal(roleJoin.get("roleId"), roleRequest.RoleID()));
                }

                // Filter by Role Name if present
                if (roleRequest.RoleName() != null) {
                    currentRolePredicate = cb.and(currentRolePredicate, cb.equal(cb.lower(roleJoin.get("roleName")), roleRequest.RoleName().toLowerCase()));
                }

                // Add the current role predicate to the main role predicate
                rolePredicate = cb.or(rolePredicate, currentRolePredicate); // Use AND here
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
                        .roleResponses(roleService.findRoleByAccountId(accountEntity.getUserId()))
                        .build())
                .collect(Collectors.toSet());
    }


    @Override
    public Account updateAccount(AccountRequest accountRequest) throws DataNotFoundException {
        // Tìm tài khoản cần cập nhật theo username
        Optional<Account> accountToUpdate = accountRepository.findById(accountRequest.Id());

        if (accountToUpdate.isEmpty()) {
            throw new DataNotFoundException("This employee does not exist.");
        }

        Account existingAccount = accountToUpdate.get();

        // Update password if provided
        if (accountRequest.password() != null) {
            existingAccount.setPassword(accountRequest.password());
        }

        // Update status if provided
        if (accountRequest.status() != null) {
            existingAccount.setStatus(accountRequest.status());
        }

        // Update roles if provided
        if (accountRequest.roleRequests() != null) {
            Set<Role> roles = new HashSet<>();
            for (RoleRequest roleRequest : accountRequest.roleRequests()) {
                roles.add(Role.builder()
                        .roleId(roleRequest.RoleID())
                        .roleName(roleRequest.RoleName())
                        .build());
            }
            existingAccount.setRoles(roles);
        }

        // Lưu thay đổi vào cơ sở dữ liệu
        return accountRepository.save(existingAccount);
    }

    @Override
    public String login(String username, String password) throws DataNotFoundException {
        Optional<Account> account = accountRepository.findByUsername(username);
        if(account.isEmpty()){
            throw new DataNotFoundException("Invalid username or password.");
        }

        if(!passwordEncoder.matches(password, account.get().getPassword())){
            throw new BadCredentialsException("Wrong username or password.");
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);

        authenticationManager.authenticate(authenticationToken);

        return jwtTokenUtil.generateToken(account.get());
    }

}
