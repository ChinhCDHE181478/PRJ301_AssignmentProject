package dev.chinhcd.backend.service;

import dev.chinhcd.backend.dtos.requests.DepartmentRequest;
import dev.chinhcd.backend.dtos.requests.EmployeeRequest;
import dev.chinhcd.backend.dtos.responses.EmployeeResponse;
import dev.chinhcd.backend.models.Department;
import dev.chinhcd.backend.models.Employee;
import dev.chinhcd.backend.repository.EmployeeRepository;
import dev.chinhcd.backend.service.InterfaceService.EmployeeService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EntityManager entityManager;
    private final EmployeeRepository employeeRepository;
    private final DepartmentServiceImpl departmentService;


    @Transactional(readOnly = true)
    @Override
    public List<EmployeeResponse> getAllEmployees() {
        return employeeRepository.findAll().stream().map(employee -> EmployeeResponse.builder()
                .id(employee.getEmployeeId())
                .employeeName(employee.getEmployeeName())
                .departmentResponse(departmentService.findById(employee.getDepartment().getDepartmentId()).orElse(null))
                .build()).toList();
    }

    @Transactional(readOnly = true)
    @Override
    public List<EmployeeResponse> getEmployeesByEmployeeDtos(EmployeeRequest employeeRequest) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Employee> query = cb.createQuery(Employee.class);
        Root<Employee> employee = query.from(Employee.class);

        Join<Employee, Department> departmentJoin = employee.join("department", JoinType.LEFT);

        Predicate predicate = cb.conjunction();

        if(employeeRequest.id() != null) {
            predicate = cb.and(predicate, cb.equal(employee.get("employeeId"), employeeRequest.id()));
        }

        if (employeeRequest.employeeName() != null && !employeeRequest.employeeName().isBlank()) {
            predicate = cb.and(predicate,
                    cb.like(cb.lower(employee.get("employeeName")),
                            "%" + employeeRequest.employeeName().toLowerCase() + "%"));
        }

        if(employeeRequest.departmentRequest() != null) {
            DepartmentRequest departmentRequest = employeeRequest.departmentRequest();

            if(departmentRequest.departmentName() != null && !departmentRequest.departmentName().isBlank()) {
                predicate = cb.and(predicate,
                        cb.like(cb.lower(departmentJoin.get("departmentName")), "%" + departmentRequest.departmentName().toLowerCase() + "%"));
            }
        }

        query.where(predicate);
        List<Employee> employees = entityManager.createQuery(query).getResultList();

        return employees.stream().map(employeeMap -> EmployeeResponse.builder()
                .id(employeeMap.getEmployeeId())
                .employeeName(employeeMap.getEmployeeName())
                .departmentResponse(departmentService.findById(employeeMap.getDepartment().getDepartmentId()).orElse(null))
                .build()).toList();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Employee> getEmployeeById(int id) {
        return employeeRepository.getEmployeeByEmployeeId(id);
    }

}
