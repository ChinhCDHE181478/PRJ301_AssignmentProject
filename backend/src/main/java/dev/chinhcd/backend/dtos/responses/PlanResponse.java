package dev.chinhcd.backend.dtos.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.sql.Date;

@Builder
public record PlanResponse (
        Integer planId,

        Date startDate,

        Date endDate,

        @JsonProperty("department")
        DepartmentResponse departmentResponse
) {
}
