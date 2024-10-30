package dev.chinhcd.backend.repository;

import dev.chinhcd.backend.models.ScheduleCampaign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<ScheduleCampaign, Integer> {
    @Query("select s from ScheduleCampaign s where s.campaign.campaignId =:campaignId")
    List<ScheduleCampaign> findByCampaignId(int campaignId);
}
