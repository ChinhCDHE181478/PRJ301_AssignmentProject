package dev.chinhcd.backend.service.InterfaceService;

import dev.chinhcd.backend.dtos.responses.ShiftResponse;
import dev.chinhcd.backend.models.Shift;

import java.util.List;
import java.util.Optional;

public interface ShiftService {
    List<ShiftResponse> getAllShifts();

    Optional<Shift> getShift(int id);
}
