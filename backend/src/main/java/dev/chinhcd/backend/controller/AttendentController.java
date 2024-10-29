package dev.chinhcd.backend.controller;

import dev.chinhcd.backend.dtos.requests.AttendentRequest;
import dev.chinhcd.backend.dtos.responses.AttendentResponse;
import dev.chinhcd.backend.service.InterfaceService.AttendentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/attendents")
@AllArgsConstructor
public class AttendentController {
    private final AttendentService attendentService;

    @GetMapping("/all")
    public ResponseEntity<List<AttendentResponse>> getAllAttendents() {
        return ResponseEntity.ok(attendentService.getAllAttendents());
    }

    @GetMapping("/search")
    public ResponseEntity<List<AttendentResponse>> searchAttendents(@RequestBody AttendentRequest request) {
        return ResponseEntity.ok(attendentService.searchAttendent(request));
    }

    @PostMapping("/create")
    public ResponseEntity<?> createAttendent(@RequestBody AttendentRequest request) {
        try {
            AttendentResponse response = attendentService.createAttendent(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateAttendent(@RequestBody AttendentRequest request) {
        try {
            AttendentResponse response = attendentService.updateAttendent(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteAttendent(@PathVariable int id) {
        attendentService.deleteAttendent(id);
        return ResponseEntity.ok("Deleted successfully");
    }
}
