package dev.chinhcd.backend.service;

import dev.chinhcd.backend.dtos.responses.DepartmentResponse;
import dev.chinhcd.backend.repository.DepartmentRepository;
import dev.chinhcd.backend.service.InterfaceService.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {
    DepartmentRepository departmentRepository;

    @Override
    public Optional<DepartmentResponse> findById(int id) {
        return departmentRepository.findById(id).map(department -> DepartmentResponse.builder()
                .departmentId(department.getDepartmentId())
                .departmentName(department.getDepartmentName())
                .departmentType(department.getDepartmentType())
                .build());
    }

}
