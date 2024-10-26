package dev.chinhcd.backend.controller;

import dev.chinhcd.backend.dtos.requests.ScheduleRequest;
import dev.chinhcd.backend.dtos.responses.ScheduleResponse;
import dev.chinhcd.backend.service.ScheduleServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("${api.prefix}/schedules")
@AllArgsConstructor
public class ScheduleController {
    private final ScheduleServiceImpl scheduleService;

    @GetMapping("/all")
    public ResponseEntity<Set<ScheduleResponse>> getAllSchedules(){
        return ResponseEntity.ok(scheduleService.getAllSchedules());
    }

    @GetMapping("/search")
    public ResponseEntity<Set<ScheduleResponse>> searchSchedule(@RequestBody ScheduleRequest request){
        return ResponseEntity.ok(scheduleService.searchSchedule(request));
    }

    @PostMapping("/create")
    public ResponseEntity<?> createSchedule(@RequestBody ScheduleRequest request){
        try{
            ScheduleResponse resp = scheduleService.addSchedule(request);
            return ResponseEntity.ok(resp);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateSchedule(@RequestBody ScheduleRequest request){
        try{
            ScheduleResponse resp = scheduleService.updateSchedule(request);
            return ResponseEntity.ok(resp);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteSchedule(@PathVariable int id){
        scheduleService.deleteSchedule(id);
        return ResponseEntity.ok("Deleted successfully");
    }

}
