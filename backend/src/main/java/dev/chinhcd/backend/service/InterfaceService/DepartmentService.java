package dev.chinhcd.backend.service.InterfaceService;

import dev.chinhcd.backend.dtos.responses.DepartmentResponse;
import dev.chinhcd.backend.models.Department;

import java.util.List;
import java.util.Optional;

public interface DepartmentService {
    Optional<DepartmentResponse> findById(int id);
    Optional<Department> searchById(int id);
    List<DepartmentResponse> findAll();
}
