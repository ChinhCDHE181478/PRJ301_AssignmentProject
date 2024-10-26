package dev.chinhcd.backend.dtos.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.sql.Date;

@Builder
public record ScheduleResponse(
        Integer scheduleId,

        Integer campaignId,

        Date date,

        Integer quantity,

        @JsonProperty("shift")
        ShiftResponse shiftResponse
) {
}
