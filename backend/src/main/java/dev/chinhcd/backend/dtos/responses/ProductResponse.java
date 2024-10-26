package dev.chinhcd.backend.dtos.responses;

import lombok.Builder;

@Builder
public record ProductResponse(
        Integer productId,

        String productName
) {
}
