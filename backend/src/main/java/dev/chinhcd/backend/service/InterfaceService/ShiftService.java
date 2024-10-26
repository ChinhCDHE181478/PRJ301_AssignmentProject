package dev.chinhcd.backend.service.InterfaceService;

import dev.chinhcd.backend.dtos.responses.ShiftResponse;
import dev.chinhcd.backend.models.Shift;

import java.util.Optional;
import java.util.Set;

public interface ShiftService {
    Set<ShiftResponse> getAllShifts();

    Optional<Shift> getShift(int id);
}
