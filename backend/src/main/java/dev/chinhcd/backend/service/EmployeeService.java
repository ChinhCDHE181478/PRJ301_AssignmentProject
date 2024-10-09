package dev.chinhcd.backend.service;

import dev.chinhcd.backend.dtos.EmployeeDTO;
import dev.chinhcd.backend.models.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    List<Employee> getAllEmployees();
    List<Employee> getEmployeesByEmployeeDtos(EmployeeDTO employeeDto);
    Optional<Employee> getEmployeeById(int id);
}
