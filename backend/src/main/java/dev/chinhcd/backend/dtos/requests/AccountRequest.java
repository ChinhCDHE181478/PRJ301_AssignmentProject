package dev.chinhcd.backend.dtos.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record AccountRequest(
        @JsonProperty("userId")
        Integer id,

        String username,

        String password,

        Integer employeeId,

        @JsonProperty("role")
        RoleRequest roleRequests,

        String status
) {

}
