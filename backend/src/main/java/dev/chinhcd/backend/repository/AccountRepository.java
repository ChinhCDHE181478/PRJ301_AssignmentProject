package dev.chinhcd.backend.repository;

import dev.chinhcd.backend.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    Boolean existsByUsername(String username);
}
