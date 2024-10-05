package dev.chinhcd.backend.dtos;


import com.fasterxml.jackson.annotation.JsonProperty;

public record EmployeeDTO(

        @JsonProperty("Employee_Name")
        String employeeName,

        @JsonProperty("Department_Name")
        Integer departmentName
) {

}
