package dev.chinhcd.backend.dtos;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EmployeeDTO(
        @NotBlank()
        String employeeName,

        @NotNull()
        Integer roleId,

        @NotNull()
        Integer departmentId,

        @NotNull()
        Integer salaryLevel
) {

}
