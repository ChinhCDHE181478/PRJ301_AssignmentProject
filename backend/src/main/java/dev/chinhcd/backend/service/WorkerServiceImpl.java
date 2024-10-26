package dev.chinhcd.backend.service;

import dev.chinhcd.backend.models.WorkerSchedule;
import dev.chinhcd.backend.repository.WorkerRepository;
import dev.chinhcd.backend.service.InterfaceService.WorkerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WorkerServiceImpl implements WorkerService {
    private final WorkerRepository workerRepository;

    @Override
    public Optional<WorkerSchedule> getWorker(int id) {
        return workerRepository.findById(id);
    }
}
