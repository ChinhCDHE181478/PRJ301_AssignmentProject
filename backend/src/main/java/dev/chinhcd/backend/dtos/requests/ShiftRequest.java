package dev.chinhcd.backend.dtos.requests;

import lombok.Builder;

import java.sql.Time;

@Builder
public record ShiftRequest (
        Integer shiftId,

        String shiftName,

        Time shiftStart,

        Time shiftEnd
) {

}
