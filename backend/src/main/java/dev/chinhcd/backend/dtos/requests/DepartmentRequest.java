package dev.chinhcd.backend.dtos.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public record DepartmentRequest(
        @JsonProperty("Department_ID")
        Integer departmentId,

        @JsonProperty("Department_Name")
        String departmentName,

        @JsonProperty("Department_Type")
        String departmentType
) {
}
