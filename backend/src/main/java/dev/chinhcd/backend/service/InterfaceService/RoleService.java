package dev.chinhcd.backend.service.InterfaceService;

import dev.chinhcd.backend.dtos.responses.RoleResponse;
import dev.chinhcd.backend.models.Role;

import java.util.Optional;
import java.util.Set;

public interface RoleService {
    Set<RoleResponse> getAllRoles();

    Set<RoleResponse> findRoleByAccountId(Integer accountId);

    Optional<Role> findRoleById(Integer roleId);
}
