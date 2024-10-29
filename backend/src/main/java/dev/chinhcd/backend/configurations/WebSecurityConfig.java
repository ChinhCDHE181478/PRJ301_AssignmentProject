package dev.chinhcd.backend.configurations;

import dev.chinhcd.backend.filters.JwtTokenFilter;
import dev.chinhcd.backend.models.Feature;
import dev.chinhcd.backend.service.FeatureServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Slf4j
@Configuration
@EnableWebSecurity
@EnableWebMvc
@RequiredArgsConstructor
public class WebSecurityConfig {
    @Value("${api.prefix}")
    private String apiPrefix;

    private final JwtTokenFilter jwtTokenFilter;
    private final FeatureServiceImpl featureService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        Set<Feature> features = featureService.getAllFeatures();

        log.info(features.toString());

        http.csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(requests -> {

                    requests.requestMatchers(String.format("%s/accounts/login", apiPrefix)).permitAll()
                            .requestMatchers(String.format("%s/roles/all", apiPrefix)).permitAll()
                            .requestMatchers(String.format("%s/shifts/all", apiPrefix)).permitAll()
                            .requestMatchers(String.format("%s/users/all", apiPrefix)).permitAll()
                            .requestMatchers(String.format("%s/departments/all", apiPrefix)).permitAll();

                    if (!features.isEmpty()) {
                        features.forEach(feature -> {
                            requests.requestMatchers(HttpMethod.valueOf(feature.getMethod().toUpperCase()), feature.getPath())
                                    .hasAnyRole(feature.getRole().getRoleName().toUpperCase());
                        });

                        requests.anyRequest().authenticated();
                    }

                }).cors(httpSecurityCorsConfigurer -> {
                    CorsConfiguration configuration = new CorsConfiguration();
                    configuration.setAllowedOrigins(List.of("*"));
                    configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
                    configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type", "x-auth-token"));
                    configuration.setExposedHeaders(List.of("x-auth-token"));
                    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                    source.registerCorsConfiguration("/**", configuration);
                    httpSecurityCorsConfigurer.configurationSource(source);
                });

        return http.build();
    }
}
