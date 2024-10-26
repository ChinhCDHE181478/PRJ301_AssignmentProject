package dev.chinhcd.backend.repository;

import dev.chinhcd.backend.models.PlanCampaign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface CampaignRepository extends JpaRepository<PlanCampaign, Integer> {
    @Query("select c from PlanCampaign c where c.plan.planId =:planId")
    Set<PlanCampaign> findByPlanId(int planId);
}
