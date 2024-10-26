package dev.chinhcd.backend.controller;

import dev.chinhcd.backend.dtos.requests.PlanRequest;
import dev.chinhcd.backend.dtos.responses.PlanResponse;
import dev.chinhcd.backend.service.PlanServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("${api.prefix}/production/plans")
@AllArgsConstructor
public class PlanController {
    private final PlanServiceImpl planService;

    @GetMapping("/all")
    public ResponseEntity<Set<PlanResponse>> getAllPlans() {
        return ResponseEntity.ok(planService.getAllPlan());
    }

    @GetMapping("/search")
    public ResponseEntity<Set<PlanResponse>> searchPlans(@RequestBody PlanRequest request) {
        return ResponseEntity.ok(planService.searchPlanByPlanRequest(request));
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
        planService.deletePlanById(id);
        return ResponseEntity.ok().body("Deleted successfully");
    }


}
