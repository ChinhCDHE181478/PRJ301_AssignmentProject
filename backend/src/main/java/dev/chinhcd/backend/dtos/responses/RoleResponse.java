package dev.chinhcd.backend.dtos.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record RoleResponse(
        @JsonProperty("id")
        Integer roleId,

        @JsonProperty("name")
        String roleName
) {
}
