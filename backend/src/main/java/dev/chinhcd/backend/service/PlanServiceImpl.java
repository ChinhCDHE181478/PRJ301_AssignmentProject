package dev.chinhcd.backend.service;

import dev.chinhcd.backend.dtos.requests.PlanRequest;
import dev.chinhcd.backend.dtos.responses.DepartmentResponse;
import dev.chinhcd.backend.dtos.responses.PlanResponse;
import dev.chinhcd.backend.exceptions.DataNotFoundException;
import dev.chinhcd.backend.models.Department;
import dev.chinhcd.backend.models.ProductionPlan;
import dev.chinhcd.backend.repository.PlanRepository;
import dev.chinhcd.backend.service.InterfaceService.PlanService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlanServiceImpl implements PlanService {
    private final EntityManager entityManager;
    private final PlanRepository planRepository;
    private final DepartmentServiceImpl departmentService;

    @Transactional(readOnly = true)
    @Override
    public Set<PlanResponse> getAllPlan() {
        return planRepository.findAll().stream().map(plan -> PlanResponse.builder()
                .planId(plan.getPlanId())
                .startDate(plan.getStarDate())
                .endDate(plan.getEndDate())
                .departmentResponse(DepartmentResponse.builder()
                        .departmentId(plan.getDepartment().getDepartmentId())
                        .departmentName(plan.getDepartment().getDepartmentName())
                        .departmentType(plan.getDepartment().getDepartmentType())
                        .build())
                .build()).collect(Collectors.toSet());
    }

    @Transactional(readOnly = true)
    @Override
    public Set<PlanResponse> searchPlanByPlanRequest(PlanRequest planRequest) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<ProductionPlan> cq = cb.createQuery(ProductionPlan.class);
        Root<ProductionPlan> productionPlan = cq.from(ProductionPlan.class);

        List<Predicate> predicates = new ArrayList<>();

        if (planRequest.planId() != null) {
            predicates.add(cb.equal(productionPlan.get("planId"), planRequest.planId()));
        }

        // Check for non-null startDate
        if (planRequest.startDate() != null) {
            predicates.add(cb.greaterThanOrEqualTo(productionPlan.get("starDate"), planRequest.startDate()));
        }

        // Check for non-null endDate
        if (planRequest.endDate() != null) {
            predicates.add(cb.lessThanOrEqualTo(productionPlan.get("endDate"), planRequest.endDate()));
        }

        // Check for non-null department
        if (planRequest.departmentRequest() != null && planRequest.departmentRequest().departmentId() != null) {
            predicates.add(cb.equal(productionPlan.get("department").get("departmentId"), planRequest.departmentRequest().departmentId()));
        }

        // Combine all predicates
        cq.where(predicates.toArray(new Predicate[0]));

        List<ProductionPlan> resultList = entityManager.createQuery(cq).getResultList();

        // Convert to PlanResponse (Assuming you have a conversion function)
        return resultList.stream().map(plan -> PlanResponse.builder()
                        .planId(plan.getPlanId())
                        .departmentResponse(DepartmentResponse.builder()
                                .departmentId(plan.getDepartment().getDepartmentId())
                                .departmentName(plan.getDepartment().getDepartmentName())
                                .departmentType(plan.getDepartment().getDepartmentType())
                                .build())
                        .startDate(plan.getStarDate())
                        .endDate(plan.getEndDate())
                        .build())

                .collect(Collectors.toSet());
    }

    @Transactional
    @Override
    public PlanResponse createPlan(PlanRequest planRequest) throws DataNotFoundException {

        Department dept = departmentService.searchById(planRequest.departmentRequest().departmentId())
                .orElseThrow(() -> new DataNotFoundException("Department not found"));

        ProductionPlan plan = planRepository.save(ProductionPlan.builder()
                .starDate(planRequest.startDate())
                .endDate(planRequest.endDate())
                .department(dept)
                .build());

        return PlanResponse.builder()
                .planId(plan.getPlanId())
                .startDate(plan.getStarDate())
                .endDate(plan.getEndDate())
                .departmentResponse(DepartmentResponse.builder()
                        .departmentId(plan.getDepartment().getDepartmentId())
                        .departmentName(plan.getDepartment().getDepartmentName())
                        .departmentType(plan.getDepartment().getDepartmentType())
                        .build())
                .build();
    }

    @Transactional
    @Override
    public PlanResponse updatePlan(PlanRequest planRequest) throws DataNotFoundException {
        ProductionPlan productionPlan = planRepository.findById(planRequest.planId())
                .orElseThrow(() -> new DataNotFoundException("Plan not found"));

        Department dept = departmentService.searchById(planRequest.departmentRequest().departmentId())
                .orElseThrow(() -> new DataNotFoundException("Department not found"));

        productionPlan.setStarDate(planRequest.startDate());
        productionPlan.setEndDate(planRequest.endDate());
        productionPlan.setDepartment(dept);

        productionPlan = planRepository.save(productionPlan);

        return PlanResponse.builder()
                .planId(productionPlan.getPlanId())
                .startDate(productionPlan.getStarDate())
                .endDate(productionPlan.getEndDate())
                .departmentResponse(DepartmentResponse.builder()
                        .departmentId(productionPlan.getDepartment().getDepartmentId())
                        .departmentName(productionPlan.getDepartment().getDepartmentName())
                        .departmentType(productionPlan.getDepartment().getDepartmentType())
                        .build())
                .build();
    }

    @Transactional(readOnly = true)
    @Override
    public void deletePlanById(int planId) {
        planRepository.deleteById(planId);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<ProductionPlan> getProductionPlanById(int planId) {
        return planRepository.findById(planId);
    }
}
