package dev.chinhcd.backend.dtos.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.sql.Date;

@Builder
public record PlanRequest(
    Integer planId,

    Date startDate,

    Date endDate,

    @JsonProperty("department")
    DepartmentRequest departmentRequest
) {
}
