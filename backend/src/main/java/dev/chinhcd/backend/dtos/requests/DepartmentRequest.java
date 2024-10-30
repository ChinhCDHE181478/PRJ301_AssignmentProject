package dev.chinhcd.backend.dtos.requests;

import lombok.Builder;

@Builder
public record DepartmentRequest(
        Integer departmentId,

        String departmentName,

        String departmentType
) {
}
