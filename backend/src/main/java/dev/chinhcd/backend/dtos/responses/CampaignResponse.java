package dev.chinhcd.backend.dtos.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record CampaignResponse (
        Integer campaignId,

        Integer planId,

        @JsonProperty("product")
        ProductResponse productResponse,

        Integer quantity,

        Integer unitEffortDays
) {

}
