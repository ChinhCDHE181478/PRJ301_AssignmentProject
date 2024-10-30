package dev.chinhcd.backend.controllers;

import dev.chinhcd.backend.dtos.requests.DepartmentRequest;
import dev.chinhcd.backend.dtos.requests.PlanRequest;
import dev.chinhcd.backend.dtos.responses.MessageResponse;
import dev.chinhcd.backend.dtos.responses.PlanResponse;
import dev.chinhcd.backend.service.PlanServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("${api.prefix}/production/plans")
@AllArgsConstructor
public class PlanController {
    private final PlanServiceImpl planService;

    @GetMapping("/all")
    public ResponseEntity<List<PlanResponse>> getAllPlans() {
        return ResponseEntity.ok(planService.getAllPlan());
    }

    @GetMapping("/search")
    public ResponseEntity<List<PlanResponse>> searchPlans(@RequestParam Optional<Date> from, @RequestParam Optional<Date> to, @RequestParam Optional<Integer> dId) {
        PlanRequest plan = PlanRequest.builder()
                .startDate(from.orElse(null))
                .endDate(to.orElse(null))
                .departmentRequest(DepartmentRequest.builder()
                        .departmentId(dId.orElse(null))
                        .build())
                .build();
        return ResponseEntity.ok(planService.searchPlanByPlanRequest(plan));
    }

    @PostMapping("/create")
    public ResponseEntity<?> createPlan(@RequestBody PlanRequest request) {
        try {
            PlanResponse plan = planService.createPlan(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(plan);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updatePlan(@RequestBody PlanRequest request) {
        try {
            PlanResponse plan = planService.updatePlan(request);
            return ResponseEntity.ok(plan);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deletePlan(@PathVariable int id) {
        try {
            planService.deletePlanById(id);
            return ResponseEntity.ok(MessageResponse.builder().message("Deleted successfully").build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
