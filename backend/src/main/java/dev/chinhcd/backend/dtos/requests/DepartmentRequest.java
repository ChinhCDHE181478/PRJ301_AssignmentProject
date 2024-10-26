package dev.chinhcd.backend.dtos.requests;

public record DepartmentRequest(
        Integer departmentId,

        String departmentName,

        String departmentType
) {
}
