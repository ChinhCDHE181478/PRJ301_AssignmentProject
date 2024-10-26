package dev.chinhcd.backend.service.InterfaceService;

import dev.chinhcd.backend.models.WorkerSchedule;

import java.util.Optional;

public interface WorkerService {
    Optional<WorkerSchedule> getWorker(int id);
}
