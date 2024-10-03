package dev.chinhcd.backend.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public record AccountDTO(
        @NotBlank(message = "username cannot be blank")
        String username,

        @NotBlank(message = "password cannot be blank")
        String password,

        @NotNull(message = "employee id cannot be null")
        Integer employeeId
) {

}
