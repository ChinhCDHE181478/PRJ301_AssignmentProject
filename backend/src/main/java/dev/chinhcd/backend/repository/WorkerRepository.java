package dev.chinhcd.backend.repository;

import dev.chinhcd.backend.models.WorkerSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkerRepository extends JpaRepository<WorkerSchedule, Integer> {
}
