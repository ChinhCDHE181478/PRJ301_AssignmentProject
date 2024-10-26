package dev.chinhcd.backend.dtos.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record AttendentResponse(
        Integer attendentId,

        @JsonProperty("worker")
        WorkerScheduleResponse workerScheduleResponse,

        Integer quantity,

        Double alpha
) {
}
