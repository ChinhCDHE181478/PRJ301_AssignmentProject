package dev.chinhcd.backend.repository;

import dev.chinhcd.backend.models.WorkerSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkerRepository extends JpaRepository<WorkerSchedule, Integer> {
    @Query("select w from WorkerSchedule w where w.schedule.scheduleId =:id")
    List<WorkerSchedule> findByScheduleId(int id);
}
