package dev.chinhcd.backend.dtos.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record EmployeeResponse(
        @JsonProperty("Employee_ID")
        Integer id,

        @JsonProperty("Employee_Name")
        String employeeName,

        @JsonProperty("Department_Name")
        String departmentName
) {

}
