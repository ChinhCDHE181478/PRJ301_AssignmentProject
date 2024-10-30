package dev.chinhcd.backend.dtos.responses;

import lombok.Builder;

@Builder
public record MessageResponse(
        String message
) {
}
