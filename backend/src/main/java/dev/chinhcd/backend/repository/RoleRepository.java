package dev.chinhcd.backend.repository;

import dev.chinhcd.backend.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByRoleName(String name);

    Set<Role> findByRoleNameIn(Set<String> roleName);

    @Query("Select r FROM Role r join r.accounts a where a.userId =:accountId")
    Set<Role> findRoleByAccountId(Integer accountId);
}
