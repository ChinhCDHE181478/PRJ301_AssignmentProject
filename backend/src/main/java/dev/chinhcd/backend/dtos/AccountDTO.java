package dev.chinhcd.backend.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.UniqueElements;

import java.util.Set;


public record AccountDTO(
        @JsonProperty("Username")
        String username,

        @JsonProperty("Password")
        String password,

        @JsonProperty("Employee_ID")
        Integer employeeId,

        @JsonProperty("Role")
        Set<String> role,

        @JsonProperty("Status")
        String status
) {

}
