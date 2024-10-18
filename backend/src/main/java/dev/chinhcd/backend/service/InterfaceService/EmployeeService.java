package dev.chinhcd.backend.service.InterfaceService;

import dev.chinhcd.backend.dtos.requests.EmployeeRequest;
import dev.chinhcd.backend.dtos.responses.EmployeeResponse;
import dev.chinhcd.backend.models.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    List<EmployeeResponse> getAllEmployees();
    List<EmployeeResponse> getEmployeesByEmployeeDtos(EmployeeRequest employeeRequest);
    Optional<Employee> getEmployeeById(int id);
}
