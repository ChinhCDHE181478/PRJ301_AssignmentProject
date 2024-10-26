package dev.chinhcd.backend.service.InterfaceService;

import dev.chinhcd.backend.dtos.requests.PlanRequest;
import dev.chinhcd.backend.dtos.responses.PlanResponse;
import dev.chinhcd.backend.exceptions.DataNotFoundException;
import dev.chinhcd.backend.models.ProductionPlan;

import java.util.Optional;
import java.util.Set;

public interface PlanService {
    Set<PlanResponse> getAllPlan();

    Set<PlanResponse> searchPlanByPlanRequest(PlanRequest planRequest);

    PlanResponse createPlan(PlanRequest planRequest) throws DataNotFoundException;

    PlanResponse updatePlan(PlanRequest planRequest) throws DataNotFoundException;

    void deletePlanById(int planId);

    Optional<ProductionPlan> getProductionPlanById(int planId);
}
