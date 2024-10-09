package dev.chinhcd.backend.service;

import dev.chinhcd.backend.models.Role;

import java.util.Optional;

public interface RoleService {
    Optional<Role> findByName(String name);
}
