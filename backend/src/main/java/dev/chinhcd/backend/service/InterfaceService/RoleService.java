package dev.chinhcd.backend.service.InterfaceService;

import dev.chinhcd.backend.dtos.responses.RoleResponse;
import dev.chinhcd.backend.models.Role;

import java.util.Optional;
import java.util.Set;

public interface RoleService {
    Optional<Role> findByName(String name);

    Set<Role> findBySetName(Set<String> name);

    Set<RoleResponse> findRoleByAccountId(Integer accountId);
}
