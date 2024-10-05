package dev.chinhcd.backend.service;

import dev.chinhcd.backend.dtos.EmployeeDTO;
import dev.chinhcd.backend.models.Employee;
import dev.chinhcd.backend.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;


    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public List<Employee> getEmployeesByEmployeeDtos(EmployeeDTO employeeDto) {

        // Tạo một example để lọc các thuộc tính
        ExampleMatcher matcher = ExampleMatcher.matchingAll()
                .withIgnoreNullValues()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING); // Chấp nhận chuỗi chứa giá trị tìm kiếm

        Employee employeeExample = Employee.builder()
                .employeeId(null) // Sử dụng giá trị ID nếu có
                .employeeName(employeeDto.employeeName() != null && !employeeDto.employeeName().isBlank() ? employeeDto.employeeName() : null) // Kiểm tra null hoặc rỗng
                .department(employeeDto.departmentName())
                .build();

        // Tạo example dựa trên matcher
        Example<Employee> example = Example.of(employeeExample, matcher);

        // Tìm tất cả dựa trên example
        return employeeRepository.findAll(example);
    }

    @Override
    public Employee getEmployeeById(int id) {
        return employeeRepository.findById(id).orElse(null);
    }
}
