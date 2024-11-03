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
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        List<Feature> features = featureService.getAllFeatures();

        http.csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(requests -> {

                    requests.requestMatchers(String.format("%s/accounts/login", apiPrefix)).permitAll()
                            .requestMatchers(String.format("%s/roles/all", apiPrefix)).permitAll()
                            .requestMatchers(String.format("%s/shifts/all", apiPrefix)).permitAll()
                            .requestMatchers(String.format("%s/users/all", apiPrefix)).permitAll()
                            .requestMatchers(String.format("%s/departments/all", apiPrefix)).permitAll()
                            .requestMatchers(String.format("%s/products/all", apiPrefix)).permitAll();

                    if (!features.isEmpty()) {
                        Map<String, List<String>> groupedFeatures = features.stream()
                                .collect(Collectors.groupingBy(
                                        feature -> feature.getMethod().toUpperCase() + ":" + feature.getPath(),
                                        Collectors.mapping(feature -> feature.getRole().getRoleName().toUpperCase(), Collectors.toList())
                                ));

                        // Apply authorization rules for each group
                        groupedFeatures.forEach((methodPath, roles) -> {
                            String[] parts = methodPath.split(":");
                            HttpMethod method = HttpMethod.valueOf(parts[0]);
                            String path = parts[1];
                            requests.requestMatchers(method, path)
                                    .hasAnyRole(roles.toArray(new String[0]));
                        });
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
