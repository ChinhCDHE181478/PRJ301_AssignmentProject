package dev.chinhcd.backend.controllers;

import dev.chinhcd.backend.dtos.requests.ScheduleRequest;
import dev.chinhcd.backend.dtos.requests.ShiftRequest;
import dev.chinhcd.backend.dtos.responses.MessageResponse;
import dev.chinhcd.backend.dtos.responses.ScheduleResponse;
import dev.chinhcd.backend.service.ScheduleServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

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
    public ResponseEntity<List<ScheduleResponse>> searchSchedule(
            @RequestParam Optional<Integer> scheduleId,
            @RequestParam Optional<Integer> campId,
            @RequestParam Optional<Date> date,
            @RequestParam Optional<Integer> shiftid)
    {
        ScheduleRequest sche = ScheduleRequest.builder()
                .scheduleId(scheduleId.orElse(null))
                .campaignId(campId.orElse(null))
                .date(date.orElse(null))
                .shiftRequest(ShiftRequest.builder()
                        .shiftId(shiftid.orElse(null))
                        .build())
                .build();
        return ResponseEntity.ok(scheduleService.searchSchedule(sche));
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
