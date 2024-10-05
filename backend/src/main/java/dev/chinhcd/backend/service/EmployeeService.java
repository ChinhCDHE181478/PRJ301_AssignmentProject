package dev.chinhcd.backend.service;

import dev.chinhcd.backend.dtos.EmployeeDTO;
import dev.chinhcd.backend.models.Employee;

import java.util.List;

public interface EmployeeService {
    List<Employee> getAllEmployees();
    List<Employee> getEmployeesByEmployeeDtos(EmployeeDTO employeeDto);
    Employee getEmployeeById(int id);
}
