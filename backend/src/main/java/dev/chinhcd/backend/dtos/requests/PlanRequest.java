package dev.chinhcd.backend.dtos.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Date;

public record PlanRequest(
    Integer planId,

    Date startDate,

    Date endDate,

    @JsonProperty("department")
    DepartmentRequest departmentRequest
) {
}
