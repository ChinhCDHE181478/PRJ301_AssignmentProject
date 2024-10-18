package dev.chinhcd.backend.dtos.requests;


import com.fasterxml.jackson.annotation.JsonProperty;

public record EmployeeRequest(

        @JsonProperty("Employee_ID")
        Integer id,

        @JsonProperty("Employee_Name")
        String employeeName,

        @JsonProperty("Department_Name")
        String departmentName
) {

}
