package dev.chinhcd.backend.dtos.requests;

import jakarta.validation.constraints.NotBlank;

public record UserLoginRequest(
        @NotBlank(message = "Username is required")
        String username,

        @NotBlank(message = "Password cannot be blank")
        String password
) {
}
