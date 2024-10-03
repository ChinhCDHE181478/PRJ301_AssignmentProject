package dev.chinhcd.backend.service;

import dev.chinhcd.backend.dtos.EmployeeDTO;
import dev.chinhcd.backend.models.Employee;
import dev.chinhcd.backend.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;


    @Override
    public Employee createEmployee(EmployeeDTO employeeDTO) {
        var employee = Employee.builder()
                .employeeName(employeeDTO.employeeName())
                .salaryLevel(employeeDTO.salaryLevel())
                .build();
        return employeeRepository.save(employee);
    }

    @Override
    public Employee getEmployeeById(Integer id) {
        return null;
    }

    @Override
    public Employee updateEmployee(Integer id, EmployeeDTO employeeDTO) {
        return null;
    }

    @Override
    public void deleteEmployee(Integer id) {

    }
}
