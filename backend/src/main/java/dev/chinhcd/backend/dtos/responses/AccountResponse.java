package dev.chinhcd.backend.dtos.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record AccountResponse (
        @JsonProperty("userId")
        Integer id,

        String username,

        Integer employeeId,

        @JsonProperty("role")
        RoleResponse roleResponses,

        String status
) {
}
