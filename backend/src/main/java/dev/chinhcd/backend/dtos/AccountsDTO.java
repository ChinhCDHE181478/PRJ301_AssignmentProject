package dev.chinhcd.backend.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AccountsDTO {
    @JsonProperty("User_ID")
    private int accountID;

    @JsonProperty("Username")
    private String username;

    @JsonProperty("Password")
    private String password;


}
