package dev.chinhcd.backend.components;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

//bean, in Spring Container
@Component
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class AccountManager {
    @Value("${accountManager.username}")
    private String username;

    @Value("${accountManager.password}")
    private String password;

    @Value("${accountManager.roleId}")
    private int roleId;
}
