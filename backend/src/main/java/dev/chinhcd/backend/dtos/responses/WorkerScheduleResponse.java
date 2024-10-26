package dev.chinhcd.backend.dtos.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record WorkerScheduleResponse(
        Integer workerId,

        Integer scheduleId,

        @JsonProperty("employee")
        EmployeeResponse employeeResponse,

        Integer quantity
) {
}
