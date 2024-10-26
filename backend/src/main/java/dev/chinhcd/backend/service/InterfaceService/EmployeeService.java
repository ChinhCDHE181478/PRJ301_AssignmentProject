package dev.chinhcd.backend.service.InterfaceService;

import dev.chinhcd.backend.dtos.requests.EmployeeRequest;
import dev.chinhcd.backend.dtos.responses.EmployeeResponse;
import dev.chinhcd.backend.models.Employee;

import java.util.Optional;
import java.util.Set;

public interface EmployeeService {
    Set<EmployeeResponse> getAllEmployees();

    Set<EmployeeResponse> getEmployeesByEmployeeDtos(EmployeeRequest employeeRequest);

    Optional<Employee> getEmployeeById(int id);

}
