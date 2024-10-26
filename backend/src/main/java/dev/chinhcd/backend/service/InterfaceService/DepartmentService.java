package dev.chinhcd.backend.service.InterfaceService;

import dev.chinhcd.backend.dtos.responses.DepartmentResponse;
import dev.chinhcd.backend.models.Department;

import java.util.Optional;
import java.util.Set;

public interface DepartmentService {
    Optional<DepartmentResponse> findById(int id);
    Optional<Department> searchById(int id);
    Set<DepartmentResponse> findAll();
}
