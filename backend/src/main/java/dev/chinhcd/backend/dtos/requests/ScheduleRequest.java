package dev.chinhcd.backend.dtos.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Date;

public record ScheduleRequest(
        Integer scheduleId,

        Integer campaignId,

        Date date,

        Integer quantity,

        @JsonProperty("shift")
        ShiftRequest shiftRequest
) {
}
