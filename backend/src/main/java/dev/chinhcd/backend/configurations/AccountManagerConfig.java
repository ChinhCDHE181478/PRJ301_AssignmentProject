package dev.chinhcd.backend.configurations;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class AccountManagerConfig {
    @Value("${accountManager.username}")
    private String username;

    @Value("${accountManager.password}")
    private String password;

    @Value("${accountManager.roleId}")
    private int roleId;
}
