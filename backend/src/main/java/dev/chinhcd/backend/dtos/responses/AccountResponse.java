package dev.chinhcd.backend.dtos.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.Set;

@Builder
public record AccountResponse (
        @JsonProperty("User_ID")
        Integer Id,

        @JsonProperty("Username")
        String username,

        @JsonProperty("Password")
        String password,

        @JsonProperty("Employee_ID")
        Integer employeeId,

        @JsonProperty("Role")
        Set<RoleResponse> role,

        @JsonProperty("Status")
        String status
) {
}
