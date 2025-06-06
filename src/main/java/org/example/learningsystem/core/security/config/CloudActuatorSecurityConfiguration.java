package org.example.learningsystem.core.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

import static org.example.learningsystem.core.security.role.UserRole.MANAGER;
import static org.springframework.security.config.Customizer.withDefaults;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@Profile("cloud")
@RequiredArgsConstructor
public class CloudActuatorSecurityConfiguration {

    private static final int ACTUATOR_FILTER_CHAIN_ORDER = 1;
    private static final String ACTUATOR_ENDPOINTS = "/actuator/**";
    private static final String ACTUATOR_HEALTH_ENDPOINT = "/actuator/health";

    private final AccessDeniedHandler accessDeniedHandler;
    private final AuthenticationEntryPoint authenticationEntryPoint;

    @Bean
    @Order(ACTUATOR_FILTER_CHAIN_ORDER)
    public SecurityFilterChain actuatorSecurityFilterChain(
            HttpSecurity http, AuthenticationEntryPoint authenticationEntryPoint) throws Exception {
        return http
                .securityMatcher(ACTUATOR_ENDPOINTS)
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(this::configureSession)
                .authorizeHttpRequests(this::configureAuthorization)
                .httpBasic(withDefaults())
                .exceptionHandling(this::configureExceptionHandling)
                .build();
    }

    private void configureSession(SessionManagementConfigurer<HttpSecurity> session) {
        session.sessionCreationPolicy(STATELESS);
    }

    private void configureAuthorization(AuthorizeHttpRequestsConfigurer<?>.AuthorizationManagerRequestMatcherRegistry auth) {
        auth
                .requestMatchers(ACTUATOR_HEALTH_ENDPOINT).permitAll()
                .requestMatchers(ACTUATOR_ENDPOINTS).hasRole(MANAGER.toString());
    }

    private void configureExceptionHandling(ExceptionHandlingConfigurer<HttpSecurity> exceptionHandler) {
        exceptionHandler
                .accessDeniedHandler(accessDeniedHandler)
                .authenticationEntryPoint(authenticationEntryPoint);
    }
}
