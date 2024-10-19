package dev.chinhcd.backend.controller;

import dev.chinhcd.backend.dtos.requests.EmployeeRequest;
import dev.chinhcd.backend.dtos.responses.EmployeeResponse;
import dev.chinhcd.backend.service.EmployeeServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("${api.prefix}/employees")
@AllArgsConstructor
public class EmployeeController {
    private final EmployeeServiceImpl employeeService;

    @GetMapping("all")
    public ResponseEntity<Set<EmployeeResponse>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @GetMapping("search")
    public ResponseEntity<Set<EmployeeResponse>> getEmployeeByEmployeeDto(@RequestBody EmployeeRequest request) {
        return ResponseEntity.ok(employeeService.getEmployeesByEmployeeDtos(request));
    }
    
}
