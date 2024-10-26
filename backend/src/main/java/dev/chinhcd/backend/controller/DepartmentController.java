package dev.chinhcd.backend.controller;

import dev.chinhcd.backend.dtos.responses.DepartmentResponse;
import dev.chinhcd.backend.service.DepartmentServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("${api.prefix}/departments")
@AllArgsConstructor
public class DepartmentController {
    DepartmentServiceImpl departmentService;

    @GetMapping("/all")
    public ResponseEntity<Set<DepartmentResponse>> getAll() {
        return ResponseEntity.ok(departmentService.findAll());
    }
}
