package dev.chinhcd.backend.dtos.requests;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record EmployeeRequest(

        @JsonProperty("employeeId")
        Integer id,

        String employeeName,

        @JsonProperty("department")
        DepartmentRequest departmentRequest
) {

}
