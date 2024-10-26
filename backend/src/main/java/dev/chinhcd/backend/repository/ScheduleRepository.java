package dev.chinhcd.backend.repository;

import dev.chinhcd.backend.models.ScheduleCampaign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends JpaRepository<ScheduleCampaign, Integer> {

}
