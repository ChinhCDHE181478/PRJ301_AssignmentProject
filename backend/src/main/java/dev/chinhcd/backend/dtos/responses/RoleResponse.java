package dev.chinhcd.backend.dtos.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record RoleResponse(
        @JsonProperty("Role_ID")
        Integer RoleID,

        @JsonProperty("Role_Name")
        String RoleName
) {
}
