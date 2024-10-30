package dev.chinhcd.backend.dtos.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record RoleRequest (
        @JsonProperty("id")
        Integer roleId,

        @JsonProperty("name")
        String roleName
) {

}
