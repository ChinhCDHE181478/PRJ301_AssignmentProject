package dev.chinhcd.backend.dtos.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CampaignRequest (
        Integer campaignId,

        Integer planId,

        @JsonProperty("product")
        ProductRequest productRequest,

        Integer quantity,

        Integer unitEffortDays
) {
}
