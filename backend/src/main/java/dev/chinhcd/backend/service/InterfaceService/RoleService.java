package dev.chinhcd.backend.service.InterfaceService;

import dev.chinhcd.backend.dtos.responses.RoleResponse;
import dev.chinhcd.backend.models.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    List<RoleResponse> getAllRoles();

    Optional<Role> findRoleById(Integer roleId);
}
