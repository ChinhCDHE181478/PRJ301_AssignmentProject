package dev.chinhcd.backend.service;

import dev.chinhcd.backend.dtos.responses.ShiftResponse;
import dev.chinhcd.backend.models.Shift;
import dev.chinhcd.backend.repository.ShiftRepository;
import dev.chinhcd.backend.service.InterfaceService.ShiftService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShiftServiceImpl implements ShiftService {
    private final ShiftRepository shiftRepository;

    @Transactional(readOnly = true)
    @Override
    public List<ShiftResponse> getAllShifts() {
        return shiftRepository.findAll().stream().map(shift -> ShiftResponse.builder()
                .shiftId(shift.getShiftId())
                .shiftName(shift.getShiftName())
                .shiftStart(shift.getShiftStart())
                .shiftEnd(shift.getShiftEnd())
                .build()).toList();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Shift> getShift(int id) {
        return shiftRepository.findById(id);
    }
}
