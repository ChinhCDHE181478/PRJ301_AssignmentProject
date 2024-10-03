package dev.chinhcd.backend.service;

import dev.chinhcd.backend.dtos.EmployeeDTO;
import dev.chinhcd.backend.models.Employee;

public interface EmployeeService {
    Employee createEmployee(EmployeeDTO employeeDTO);
    Employee getEmployeeById(Integer id);
    Employee updateEmployee(Integer id, EmployeeDTO employeeDTO);
    void deleteEmployee(Integer id);
}
