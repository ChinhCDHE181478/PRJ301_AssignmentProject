package dev.chinhcd.backend.service;

import dev.chinhcd.backend.dtos.requests.EmployeeRequest;
import dev.chinhcd.backend.dtos.responses.EmployeeResponse;
import dev.chinhcd.backend.models.Department;
import dev.chinhcd.backend.models.Employee;
import dev.chinhcd.backend.repository.DepartmentRepository;
import dev.chinhcd.backend.repository.EmployeeRepository;
import dev.chinhcd.backend.service.InterfaceService.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final DepartmentServiceImpl departmentService;


    @Override
    public List<EmployeeResponse> getAllEmployees() {
        return employeeRepository.findAll().stream().map(employee -> EmployeeResponse.builder()
                .employeeName(employee.getEmployeeName())
                .departmentName(employee.getDepartment().getDepartmentName())
                .build()).collect(Collectors.toList());
    }

    @Override
    public List<EmployeeResponse> getEmployeesByEmployeeDtos(EmployeeRequest employeeRequest) {
        Optional<Department> d = departmentService.findByName(employeeRequest.departmentName());
        return new ArrayList<EmployeeResponse>() {{}};
    }

    @Override
    public Optional<Employee> getEmployeeById(int id) {
        return employeeRepository.getEmployeeByEmployeeId(id);
    }

}
