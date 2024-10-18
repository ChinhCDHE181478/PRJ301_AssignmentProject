package dev.chinhcd.backend.dtos.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RoleRequest (
        @JsonProperty("Role_ID")
        Integer RoleID,

        @JsonProperty("Role_Name")
        String RoleName
) {

}
