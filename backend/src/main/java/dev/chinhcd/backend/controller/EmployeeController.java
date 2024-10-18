package dev.chinhcd.backend.controller;

import dev.chinhcd.backend.dtos.requests.EmployeeRequest;
import dev.chinhcd.backend.dtos.responses.EmployeeResponse;
import dev.chinhcd.backend.models.Employee;
import dev.chinhcd.backend.service.InterfaceService.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/employees")
@AllArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    @GetMapping("all")
    public ResponseEntity<List<EmployeeResponse>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @GetMapping("search")
    public ResponseEntity<List<EmployeeResponse>> getEmployeeByEmployeeDto(@RequestBody EmployeeRequest request) {
        return ResponseEntity.ok(employeeService.getEmployeesByEmployeeDtos(request));
    }
    
}
