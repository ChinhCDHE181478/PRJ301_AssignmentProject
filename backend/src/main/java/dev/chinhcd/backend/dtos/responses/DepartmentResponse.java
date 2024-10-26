package dev.chinhcd.backend.dtos.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record DepartmentResponse(
        Integer departmentId,

        String departmentName,

        String departmentType
) {
}
