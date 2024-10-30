package dev.chinhcd.backend.dtos.responses;

import lombok.Builder;

@Builder
public record UserLoginResponse(
        String message,

        String token,

        String role
) {
}
