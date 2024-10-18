package dev.chinhcd.backend.service;

import dev.chinhcd.backend.dtos.responses.RoleResponse;
import dev.chinhcd.backend.models.Role;
import dev.chinhcd.backend.repository.RoleRepository;
import dev.chinhcd.backend.service.InterfaceService.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public Optional<Role> findByName(String name) {
        return roleRepository.findByRoleName(name);
    }

    @Override
    public Set<Role> findBySetName(Set<String> name) {
        return roleRepository.findByRoleNameIn(name);
    }

    @Override
    public Set<RoleResponse> findRoleByAccountId(Integer accountId) {
        Set<Role> roles = roleRepository.findRoleByAccountId(accountId);
        return roles.stream()
                .map(role -> RoleResponse.builder().RoleID(role.getRoleId()).RoleName(role.getRoleName()).build())
                .collect(Collectors.toSet());
    }


}
