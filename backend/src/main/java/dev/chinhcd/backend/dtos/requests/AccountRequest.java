package dev.chinhcd.backend.dtos.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Set;


public record AccountRequest(
        @JsonProperty("User_ID")
        Integer Id,

        @JsonProperty("Username")
        String username,

        @JsonProperty("Password")
        String password,

        @JsonProperty("Employee_ID")
        Integer employeeId,

        @JsonProperty("Role")
        Set<RoleRequest> role,

        @JsonProperty("Status")
        String status
) {

}
