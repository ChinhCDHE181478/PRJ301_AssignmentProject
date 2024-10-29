package dev.chinhcd.backend.repository;

import dev.chinhcd.backend.models.PlanCampaign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CampaignRepository extends JpaRepository<PlanCampaign, Integer> {
}
