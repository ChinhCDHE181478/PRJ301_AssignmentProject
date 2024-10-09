package dev.chinhcd.backend.dtos;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.UniqueConstraint;

public record EmployeeDTO(

        @JsonProperty("Employee_ID")
        Integer id,

        @JsonProperty("Employee_Name")
        String employeeName,

        @JsonProperty("Department_Name")
        String departmentName
) {

}
