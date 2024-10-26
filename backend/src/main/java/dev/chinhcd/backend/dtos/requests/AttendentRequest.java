package dev.chinhcd.backend.dtos.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AttendentRequest(
        Integer attendentId,

        @JsonProperty("worker")
        WorkerScheduleRequest workerScheduleRequest,

        Integer quantity,

        Double alpha
) {
}
