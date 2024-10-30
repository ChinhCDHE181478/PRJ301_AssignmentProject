package dev.chinhcd.backend.controllers;

import dev.chinhcd.backend.dtos.requests.DepartmentRequest;
import dev.chinhcd.backend.dtos.requests.EmployeeRequest;
import dev.chinhcd.backend.dtos.responses.EmployeeResponse;
import dev.chinhcd.backend.service.EmployeeServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("${api.prefix}/employees")
@AllArgsConstructor
public class EmployeeController {
    private final EmployeeServiceImpl employeeService;

    @GetMapping("/all")
    public ResponseEntity<List<EmployeeResponse>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @GetMapping("/search")
    public ResponseEntity<List<EmployeeResponse>> getEmployeeByEmployeeDto(@RequestParam Optional<Integer> empId, @RequestParam Optional<String> name, @RequestParam Optional<Integer> dId) {
        EmployeeRequest emp = EmployeeRequest.builder()
                .id(empId.orElse(null))
                .employeeName(name.orElse(null))
                .departmentRequest(DepartmentRequest.builder()
                        .departmentId(dId.orElse(null))
                        .build())
                .build();
        return ResponseEntity.ok(employeeService.getEmployeesByEmployeeDtos(emp));
    }
    
}
