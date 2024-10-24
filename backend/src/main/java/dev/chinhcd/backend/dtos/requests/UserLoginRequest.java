package dev.chinhcd.backend.dtos.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record UserLoginRequest(
        @JsonProperty("Username")
        @NotBlank(message = "Username is required")
        String username,

        @JsonProperty("Password")
        @NotBlank(message = "Password cannot be blank")
        String password
) {
}
