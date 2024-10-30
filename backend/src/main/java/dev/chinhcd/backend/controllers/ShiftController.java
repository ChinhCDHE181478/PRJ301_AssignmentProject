package dev.chinhcd.backend.controllers;

import dev.chinhcd.backend.dtos.responses.ShiftResponse;
import dev.chinhcd.backend.service.ShiftServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/shifts")
@AllArgsConstructor
public class ShiftController {
    private final ShiftServiceImpl shiftService;

    @GetMapping("/all")
    public ResponseEntity<List<ShiftResponse>> getAll() {
        return ResponseEntity.ok(shiftService.getAllShifts());
    }
}
