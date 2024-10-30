package dev.chinhcd.backend.service;

import dev.chinhcd.backend.dtos.requests.CampaignRequest;
import dev.chinhcd.backend.dtos.responses.CampaignResponse;
import dev.chinhcd.backend.dtos.responses.ProductResponse;
import dev.chinhcd.backend.exceptions.DataNotFoundException;
import dev.chinhcd.backend.models.PlanCampaign;
import dev.chinhcd.backend.models.Product;
import dev.chinhcd.backend.models.ProductionPlan;
import dev.chinhcd.backend.repository.CampaignRepository;
import dev.chinhcd.backend.repository.ScheduleRepository;
import dev.chinhcd.backend.service.InterfaceService.CampaignService;
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

@Service
@RequiredArgsConstructor
public class CampaignServiceImpl implements CampaignService {
    private final EntityManager entityManager;
    private final CampaignRepository campaignRepository;
    private final ProductServiceImpl productService;
    private final PlanServiceImpl planService;
    private final ScheduleRepository scheduleRepository;

    @Transactional(readOnly = true)
    @Override
    public List<CampaignResponse> getAllCampaigns() {
        return campaignRepository.findAll().stream().map(planCampaign -> CampaignResponse.builder()
                .campaignId(planCampaign.getCampaignId())
                .planId(planCampaign.getPlan().getPlanId())
                .productResponse(ProductResponse.builder()
                        .productId(planCampaign.getProduct().getProductId())
                        .productName(planCampaign.getProduct().getProductName())
                        .build())
                .quantity(planCampaign.getQuantity())
                .unitEffortDays(planCampaign.getUnitEffort_days())
                .build()).toList();
    }

    @Transactional(readOnly = true)
    @Override
    public List<CampaignResponse> getCampaignsByCampaignRequest(CampaignRequest request) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<PlanCampaign> cq = cb.createQuery(PlanCampaign.class);
        Root<PlanCampaign> planCampaignRoot = cq.from(PlanCampaign.class);

        List<Predicate> predicates = new ArrayList<>();

        if (request.planId() != null) {
            predicates.add(cb.equal(planCampaignRoot.get("plan").get("planId"), request.planId()));
        }

        if (request.campaignId() != null) {
            predicates.add(cb.equal(planCampaignRoot.get("campaignId"), request.campaignId()));
        }

        if (request.productRequest().productId() != null) {
            predicates.add(cb.equal(planCampaignRoot.get("product").get("productId"), request.productRequest().productId()));
        }

        cq.where(predicates.toArray(new Predicate[0]));

        List<PlanCampaign> resultList = entityManager.createQuery(cq).getResultList();

        return resultList.stream().map(planCampaign -> CampaignResponse.builder()
                .campaignId(planCampaign.getCampaignId())
                .planId(planCampaign.getPlan().getPlanId())
                .productResponse(ProductResponse.builder()
                        .productId(planCampaign.getProduct().getProductId())
                        .productName(planCampaign.getProduct().getProductName())
                        .build())
                .quantity(planCampaign.getQuantity())
                .unitEffortDays(planCampaign.getUnitEffort_days())
                .build()).toList();
    }

    @Transactional
    @Override
    public CampaignResponse addCampaign(CampaignRequest request) throws DataNotFoundException {

        Product product = productService.getProduct(request.productRequest().productId())
                .orElseThrow(() -> new DataNotFoundException("Product not found"));

        ProductionPlan plan = planService.getProductionPlanById(request.planId())
                .orElseThrow(() -> new DataNotFoundException("Production plan not found"));

        PlanCampaign planCampaign = campaignRepository.save(PlanCampaign.builder()
                .plan(plan)
                .product(product)
                .quantity(request.quantity())
                .unitEffort_days(request.unitEffortDays())
                .build());

        return CampaignResponse.builder()
                .campaignId(planCampaign.getCampaignId())
                .planId(plan.getPlanId())
                .productResponse(ProductResponse.builder()
                        .productId(planCampaign.getProduct().getProductId())
                        .productName(planCampaign.getProduct().getProductName())
                        .build())
                .quantity(request.quantity())
                .unitEffortDays(request.unitEffortDays())
                .build();
    }

    @Transactional
    @Override
    public CampaignResponse updateCampaign(CampaignRequest request) throws DataNotFoundException {

        PlanCampaign campaign = campaignRepository.findById(request.campaignId())
                .orElseThrow(() -> new DataNotFoundException("Campaign not found"));

        Product product = productService.getProduct(request.productRequest().productId())
                .orElseThrow(() -> new DataNotFoundException("Product not found"));

        ProductionPlan plan = planService.getProductionPlanById(request.planId())
                .orElseThrow(() -> new DataNotFoundException("Production plan not found"));

        campaign.setPlan(plan);
        campaign.setQuantity(request.quantity());
        campaign.setUnitEffort_days(request.unitEffortDays());
        campaign.setProduct(product);

        campaign = campaignRepository.save(campaign);

        return CampaignResponse.builder()
                .campaignId(campaign.getCampaignId())
                .planId(plan.getPlanId())
                .productResponse(ProductResponse.builder()
                        .productId(campaign.getProduct().getProductId())
                        .productName(campaign.getProduct().getProductName())
                        .build())
                .quantity(request.quantity())
                .unitEffortDays(request.unitEffortDays())
                .build();
    }

    @Transactional
    @Override
    public void deleteCampaignById(int id) throws Exception {
        if(!scheduleRepository.findByCampaignId(id).isEmpty()) {
            throw new Exception("Cannot delete because it have children, you must delete children fist");
        }
        campaignRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<PlanCampaign> getCampaignById(int id) {
        return campaignRepository.findById(id);
    }

}
