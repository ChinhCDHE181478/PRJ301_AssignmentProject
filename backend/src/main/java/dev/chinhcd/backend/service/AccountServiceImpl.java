package dev.chinhcd.backend.service;

import dev.chinhcd.backend.components.JwtTokenUtil;
import dev.chinhcd.backend.configurations.AccountManagerConfig;
import dev.chinhcd.backend.dtos.requests.AccountRequest;
import dev.chinhcd.backend.dtos.responses.AccountResponse;
import dev.chinhcd.backend.dtos.responses.RoleResponse;
import dev.chinhcd.backend.exceptions.DataNotFoundException;
import dev.chinhcd.backend.models.Account;
import dev.chinhcd.backend.models.Employee;
import dev.chinhcd.backend.models.Role;
import dev.chinhcd.backend.repository.AccountRepository;
import dev.chinhcd.backend.service.InterfaceService.AccountService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
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
    private final AccountManagerConfig accountManagerConfig;

    @Transactional
    @Override
    public AccountResponse createAccount(AccountRequest accountRequest) throws DataNotFoundException {

        // Lấy Employee từ ID
        Employee employee = employeeService.getEmployeeById(accountRequest.employeeId())
                .orElseThrow(() -> new DataNotFoundException("Employee not found"));

        // Lấy các role từ cơ sở dữ liệu thay vì tạo mới
        Role existingRole = roleService.findRoleById(accountRequest.roleRequests().roleId())
                .orElseThrow(() -> new DataNotFoundException("Role not found"));

        Account savedAccount = accountRepository.save(Account.builder()
                .username(accountRequest.username())
                .password(passwordEncoder.encode(accountRequest.password()))
                .employee(employee)
                .role(existingRole)
                .status("Blocked")
                .build());

        return AccountResponse.builder()
                .id(savedAccount.getUserId())
                .username(savedAccount.getUsername())
                .roleResponses(RoleResponse.builder()
                        .roleId(savedAccount.getRole().getRoleId())
                        .roleName(savedAccount.getRole().getRoleName())
                        .build())
                .employeeId(savedAccount.getEmployee().getEmployeeId())
                .status(savedAccount.getStatus())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccountResponse> getAllAccount() {
        return accountRepository.findAll().stream().map(account -> AccountResponse.builder()
                .id(account.getUserId())
                .username(account.getUsername())
                .employeeId(account.getEmployee().getEmployeeId())
                .status(account.getStatus())
                .roleResponses(RoleResponse.builder()
                        .roleId(account.getRole().getRoleId())
                        .roleName(account.getRole().getRoleName())
                        .build())
                .build()).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccountResponse> getAccountsbyAccountDto(AccountRequest accountRequest) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Account> query = cb.createQuery(Account.class);
        Root<Account> account = query.from(Account.class);

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
        if (accountRequest.roleRequests() != null && accountRequest.roleRequests().roleId() != null) {
            predicate = cb.and(predicate,
                    cb.equal(account.get("role").get("roleId"), accountRequest.roleRequests().roleId()));
        }

        query.where(predicate);
        List<Account> accounts = entityManager.createQuery(query).getResultList();

        // Map to AccountResponse
        return accounts.stream()
                .map(accountEntity -> AccountResponse.builder()
                        .id(accountEntity.getUserId())
                        .username(accountEntity.getUsername())
                        .employeeId(accountEntity.getEmployee().getEmployeeId())
                        .roleResponses(RoleResponse.builder()
                                .roleId(accountEntity.getRole().getRoleId())
                                .roleName(accountEntity.getRole().getRoleName())
                                .build())
                        .status(accountEntity.getStatus())
                        .build())
                .toList();
    }


    @Transactional
    @Override
    public AccountResponse updateAccount(AccountRequest accountRequest) throws DataNotFoundException {
        // Tìm tài khoản cần cập nhật theo username
        Account existingAccount = accountRepository.findById(accountRequest.id())
                .orElseThrow(() -> new DataNotFoundException("This account does not exist"));

        Role role = roleService.findRoleById(accountRequest.roleRequests().roleId())
                .orElseThrow(() -> new DataNotFoundException("Role not found"));

        // Update password
        existingAccount.setPassword(accountRequest.password());

        // Update status
        existingAccount.setStatus(accountRequest.status());

        // Update roles
        existingAccount.setRole(role);

        existingAccount = accountRepository.save(existingAccount);

        // Lưu thay đổi vào cơ sở dữ liệu
        return AccountResponse.builder()
                .id(existingAccount.getUserId())
                .username(existingAccount.getUsername())
                .roleResponses(RoleResponse.builder()
                        .roleId(existingAccount.getRole().getRoleId())
                        .roleName(existingAccount.getRole().getRoleName())
                        .build())
                .employeeId(existingAccount.getEmployee().getEmployeeId())
                .status(existingAccount.getStatus())
                .build();
    }

    @Transactional
    @Override
    public void deleteAccountById(int id) {
        accountRepository.deleteById(id);
    }

    @Override
    public String login(String username, String password) throws DataNotFoundException {

        if (username.equals(accountManagerConfig.getUsername()) &&
                password.equals(accountManagerConfig.getPassword())) {

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);

            authenticationManager.authenticate(authenticationToken);

            // Tạo và trả về token cho accountManager
            return jwtTokenUtil.generateToken(Account.builder()
                    .username(accountManagerConfig.getUsername())
                    .password(accountManagerConfig.getPassword())
                    .build());
        }

        Account account = accountRepository.findByUsername(username)
                .orElseThrow(() -> new DataNotFoundException("Invalid username or password."));

        if (!passwordEncoder.matches(password, account.getPassword())) {
            throw new BadCredentialsException("Wrong username or password.");
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);

        authenticationManager.authenticate(authenticationToken);

        return jwtTokenUtil.generateToken(account);
    }

}
