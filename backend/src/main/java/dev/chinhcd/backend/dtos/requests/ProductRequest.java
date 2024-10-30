package dev.chinhcd.backend.dtos.requests;

import lombok.Builder;

@Builder
public record ProductRequest (
        Integer productId,

        String productName
) {
}
