package dev.chinhcd.backend.dtos.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.chinhcd.backend.dtos.requests.RoleRequest;
import lombok.Builder;

import java.util.Set;

@Builder
public record AccountResponse (
        @JsonProperty("userId")
        Integer id,

        String username,

        Integer employeeId,

        @JsonProperty("role")
        Set<RoleResponse> roleResponses,

        String status
) {
}
