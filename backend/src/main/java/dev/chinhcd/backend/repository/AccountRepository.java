package dev.chinhcd.backend.repository;

import dev.chinhcd.backend.dtos.responses.AccountResponse;
import dev.chinhcd.backend.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    Optional<Account> findByUsername(String username);

    @Query("select a from Account a where a.employee.employeeId =:employeeId")
    Set<Account> findAccountsByEmplyeeId(int employeeId);
}
