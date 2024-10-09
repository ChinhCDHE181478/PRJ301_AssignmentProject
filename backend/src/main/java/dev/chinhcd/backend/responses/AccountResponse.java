package dev.chinhcd.backend.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AccountResponse {
    @JsonProperty("Username")
    String username;

    @JsonProperty("Employee_ID")
    Integer employeeId;

    @JsonProperty("Role")
    Set<String> role;

    @JsonProperty("Status")
    String status;
}
