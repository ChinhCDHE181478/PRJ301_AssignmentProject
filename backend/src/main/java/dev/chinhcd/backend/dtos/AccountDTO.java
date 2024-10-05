package dev.chinhcd.backend.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;


public record AccountDTO(
        @JsonProperty("Username")
        String username,

        @JsonProperty("Password")
        String password,

        @JsonProperty("Employee_ID")
        Integer employeeId
) {

}
