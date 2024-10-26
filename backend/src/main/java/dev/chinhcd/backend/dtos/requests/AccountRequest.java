package dev.chinhcd.backend.dtos.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Set;

public record AccountRequest(
        @JsonProperty("userId")
        Integer id,

        String username,

        String password,

        Integer employeeId,

        @JsonProperty("role")
        Set<RoleRequest> roleRequests,

        String status
) {

}
