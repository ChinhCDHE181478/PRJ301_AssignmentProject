package dev.chinhcd.backend.service;

import dev.chinhcd.backend.dtos.responses.DepartmentResponse;
import dev.chinhcd.backend.models.Department;
import dev.chinhcd.backend.repository.DepartmentRepository;
import dev.chinhcd.backend.service.InterfaceService.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;

    @Transactional(readOnly = true)
    @Override
    public Optional<DepartmentResponse> findById(int id) {
        return departmentRepository.findByDepartmentId(id).map(department -> DepartmentResponse.builder()
                .departmentId(department.getDepartmentId())
                .departmentName(department.getDepartmentName())
                .departmentType(department.getDepartmentType())
                .build());
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Department> searchById(int id) {
        return departmentRepository.findByDepartmentId(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<DepartmentResponse> findAll() {
        return departmentRepository.findAll().stream().map(department -> DepartmentResponse.builder()
                .departmentId(department.getDepartmentId())
                .departmentName(department.getDepartmentName())
                .departmentType(department.getDepartmentType())
                .build()).toList();
    }

}
