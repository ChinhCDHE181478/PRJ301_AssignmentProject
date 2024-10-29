package dev.chinhcd.backend.controller;

import dev.chinhcd.backend.dtos.requests.EmployeeRequest;
import dev.chinhcd.backend.dtos.responses.EmployeeResponse;
import dev.chinhcd.backend.service.EmployeeServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    public ResponseEntity<List<EmployeeResponse>> getEmployeeByEmployeeDto(@RequestBody EmployeeRequest request) {
        return ResponseEntity.ok(employeeService.getEmployeesByEmployeeDtos(request));
    }
    
}
