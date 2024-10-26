package dev.chinhcd.backend.dtos.responses;

import lombok.Builder;

import java.sql.Time;

@Builder
public record ShiftResponse(
        Integer shiftId,

        String shiftName,

        Time shiftStart,

        Time shiftEnd
) {
}
