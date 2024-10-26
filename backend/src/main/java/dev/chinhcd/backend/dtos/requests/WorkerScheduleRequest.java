package dev.chinhcd.backend.dtos.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public record WorkerScheduleRequest(
        Integer workerId,

        Integer scheduleId,

        @JsonProperty("employee")
        EmployeeRequest employeeRequest,

        Integer quantity
) {
}
