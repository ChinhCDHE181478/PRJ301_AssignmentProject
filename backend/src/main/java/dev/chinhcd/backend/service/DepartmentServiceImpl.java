package dev.chinhcd.backend.service;

import dev.chinhcd.backend.models.Department;
import dev.chinhcd.backend.repository.DepartmentRepository;
import dev.chinhcd.backend.service.InterfaceService.DepartmentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {
    DepartmentRepository departmentRepository;

    @Override
    public Optional<Department> findById(int id) {
        return departmentRepository.findById(id);
    }

    @Override
    public Optional<Department> findByName(String name) {
        return departmentRepository.findByDepartmentName(name);
    }
}
