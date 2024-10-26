package dev.chinhcd.backend.dtos.requests;

import java.sql.Time;

public record ShiftRequest (
        Integer shiftId,

        String shiftName,

        Time shiftStart,

        Time shiftEnd
) {

}
