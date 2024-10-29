package dev.chinhcd.backend.configurations;

import dev.chinhcd.backend.models.Role;
import dev.chinhcd.backend.repository.AccountRepository;
import dev.chinhcd.backend.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final AccountRepository accountRepository;
    private final AccountManagerConfig accountManagerConfig;
    private final RoleRepository roleRepository;

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            if (username.equals(accountManagerConfig.getUsername())) {
                Role role = roleRepository.findByRoleId(accountManagerConfig.getRoleId())
                        .orElseThrow(() -> new UsernameNotFoundException("Not found role"));

                List<GrantedAuthority> authorities = new ArrayList<>();
                authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleName().toUpperCase()));

                return User.withUsername(accountManagerConfig.getUsername())
                        .password(passwordEncoder().encode(accountManagerConfig.getPassword()))
                        .authorities(authorities)
                        .build();
            }

            // Nếu không phải accountManager, tiếp tục truy vấn trong cơ sở dữ liệu
            return accountRepository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("Cannot find user: " + username));
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
