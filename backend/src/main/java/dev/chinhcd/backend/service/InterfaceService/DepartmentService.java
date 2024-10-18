package dev.chinhcd.backend.service.InterfaceService;

import dev.chinhcd.backend.models.Department;

import java.util.Optional;

public interface DepartmentService {
    Optional<Department> findById(int id);

    Optional<Department> findByName(String name);
}
