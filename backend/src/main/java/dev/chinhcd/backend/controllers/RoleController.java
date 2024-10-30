package dev.chinhcd.backend.controllers;

import dev.chinhcd.backend.dtos.responses.RoleResponse;
import dev.chinhcd.backend.service.RoleServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/roles")
@AllArgsConstructor
public class RoleController {
    private final RoleServiceImpl roleService;

    @GetMapping("/all")
    public ResponseEntity<List<RoleResponse>> getAllRoles() {
        return  ResponseEntity.ok(roleService.getAllRoles());
    }
}
