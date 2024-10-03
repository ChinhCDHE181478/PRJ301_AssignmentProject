package dev.chinhcd.backend.repository;

import dev.chinhcd.backend.models.Account;
import dev.chinhcd.backend.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    List<Account> findAllByEmployee(Employee employee);
    Optional<Account> findByUsername(String username);
}
