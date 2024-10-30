package dev.chinhcd.backend.service.InterfaceService;

import dev.chinhcd.backend.dtos.requests.PlanRequest;
import dev.chinhcd.backend.dtos.responses.PlanResponse;
import dev.chinhcd.backend.exceptions.DataNotFoundException;
import dev.chinhcd.backend.models.ProductionPlan;

import java.util.List;
import java.util.Optional;

public interface PlanService {
    List<PlanResponse> getAllPlan();

    List<PlanResponse> searchPlanByPlanRequest(PlanRequest planRequest);

    PlanResponse createPlan(PlanRequest planRequest) throws DataNotFoundException;

    PlanResponse updatePlan(PlanRequest planRequest) throws DataNotFoundException;

    void deletePlanById(int planId) throws Exception;

    Optional<ProductionPlan> getProductionPlanById(int planId);
}
