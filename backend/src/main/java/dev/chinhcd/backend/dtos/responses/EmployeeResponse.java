package dev.chinhcd.backend.dtos.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record EmployeeResponse(
        @JsonProperty("employeeId")
        Integer id,

        String employeeName,

        @JsonProperty("department")
        DepartmentResponse departmentResponse
) {

}
