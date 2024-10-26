package dev.chinhcd.backend.service;

import dev.chinhcd.backend.dtos.responses.RoleResponse;
import dev.chinhcd.backend.models.Role;
import dev.chinhcd.backend.repository.RoleRepository;
import dev.chinhcd.backend.service.InterfaceService.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Transactional(readOnly = true)
    @Override
    public Set<RoleResponse> getAllRoles() {
        return roleRepository.findAll().stream().map(role -> RoleResponse.builder()
                .roleId(role.getRoleId())
                .roleName(role.getRoleName())
                .build()).collect(Collectors.toSet());
    }

    @Transactional(readOnly = true)
    @Override
    public Set<RoleResponse> findRoleByAccountId(Integer accountId) {
        return roleRepository.findRoleByAccountId(accountId).stream()
                .map(role -> RoleResponse.builder()
                        .roleId(role.getRoleId())
                        .roleName(role.getRoleName())
                        .build())
                .collect(Collectors.toSet());
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Role> findRoleById(Integer roleId) {
        return roleRepository.findById(roleId);
    }


}
