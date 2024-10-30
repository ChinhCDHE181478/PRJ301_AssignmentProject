package dev.chinhcd.backend.controllers;

import dev.chinhcd.backend.dtos.requests.ScheduleRequest;
import dev.chinhcd.backend.dtos.responses.MessageResponse;
import dev.chinhcd.backend.dtos.responses.ScheduleResponse;
import dev.chinhcd.backend.service.ScheduleServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/schedules")
@AllArgsConstructor
public class ScheduleController {
    private final ScheduleServiceImpl scheduleService;

    @GetMapping("/all")
    public ResponseEntity<List<ScheduleResponse>> getAllSchedules(){
        return ResponseEntity.ok(scheduleService.getAllSchedules());
    }

    @GetMapping("/search")
    public ResponseEntity<List<ScheduleResponse>> searchSchedule(@RequestBody ScheduleRequest request){
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
        try{
            scheduleService.deleteSchedule(id);
            return ResponseEntity.ok(MessageResponse.builder().message("Deleted successfully").build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
