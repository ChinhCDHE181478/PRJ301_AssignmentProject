package dev.chinhcd.backend.dtos.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record DepartmentResponse(
        @JsonProperty("Department_ID")
        Integer departmentId,

        @JsonProperty("Department_Name")
        String departmentName,

        @JsonProperty("Department_Type")
        String departmentType
) {
}
