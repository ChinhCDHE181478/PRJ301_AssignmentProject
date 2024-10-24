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

    @Transactional
    @Override
    public Set<RoleResponse> getAllRoles() {
        return roleRepository.findAll().stream().map(role -> RoleResponse.builder()
                .RoleID(role.getRoleId())
                .RoleName(role.getRoleName())
                .build()).collect(Collectors.toSet());
    }

    @Transactional
    @Override
    public Set<RoleResponse> findRoleByAccountId(Integer accountId) {
        Set<Role> roles = roleRepository.findRoleByAccountId(accountId);
        return roles.stream()
                .map(role -> RoleResponse.builder().RoleID(role.getRoleId()).RoleName(role.getRoleName()).build())
                .collect(Collectors.toSet());
    }

    @Transactional
    @Override
    public Optional<Role> findRoleById(Integer roleId) {
        return roleRepository.findById(roleId);
    }


}
