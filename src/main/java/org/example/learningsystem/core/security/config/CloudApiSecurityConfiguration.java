package org.example.learningsystem.core.security.config;

import com.sap.cloud.security.xsuaa.XsuaaServiceConfiguration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.learningsystem.btp.xsuaa.converter.JwtConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

import static org.example.learningsystem.core.security.constant.ApiConstants.API_SUBSCRIPTION_ENDPOINT;
import static org.example.learningsystem.core.security.constant.ApiConstants.API_DOCS_ENDPOINTS;
import static org.example.learningsystem.core.security.constant.ApiConstants.API_ENDPOINTS;
import static org.example.learningsystem.core.security.constant.ApiConstants.API_FILTER_CHAIN_ORDER;
import static org.example.learningsystem.core.security.constant.ApiConstants.API_INFO_ENDPOINT;
import static org.example.learningsystem.core.security.constant.ApiConstants.SWAGGER_ENDPOINTS;
import static org.example.learningsystem.core.security.role.UserRole.ADMIN;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@Profile("cloud")
@RequiredArgsConstructor
@Slf4j
public class CloudApiSecurityConfiguration {

    private final AccessDeniedHandler accessDeniedHandler;
    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final XsuaaServiceConfiguration xsuaaServiceConfiguration;

    @Bean
    @Order(API_FILTER_CHAIN_ORDER)
    public SecurityFilterChain apiSecurityFilterChain(HttpSecurity http) throws Exception {
        return http
                .securityMatcher(API_ENDPOINTS)
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(this::configureSession)
                .authorizeHttpRequests(this::configureAuthorization)
                .oauth2ResourceServer(this::configureOauth2ResourceServer)
                .exceptionHandling(this::configureExceptionHandling)
                .build();
    }

    @Bean
    public Converter<Jwt, AbstractAuthenticationToken> customXsuaaAuthConverter() {
        return new JwtConverter(xsuaaServiceConfiguration);
    }

    private void configureSession(SessionManagementConfigurer<HttpSecurity> session) {
        session.sessionCreationPolicy(STATELESS);
    }

    private void configureAuthorization(AuthorizeHttpRequestsConfigurer<?>.AuthorizationManagerRequestMatcherRegistry auth) {
        auth
                .requestMatchers(API_SUBSCRIPTION_ENDPOINT).hasAuthority("Callback")
                .requestMatchers(API_DOCS_ENDPOINTS).permitAll()
                .requestMatchers(SWAGGER_ENDPOINTS).permitAll()
                .requestMatchers(API_INFO_ENDPOINT).hasRole(ADMIN.toString())
                .anyRequest().authenticated();
    }

    private void configureOauth2ResourceServer(OAuth2ResourceServerConfigurer<HttpSecurity> oauth2ResourceServer) {
        oauth2ResourceServer.jwt(jwt -> jwt.jwtAuthenticationConverter(customXsuaaAuthConverter()));
    }

    private void configureExceptionHandling(ExceptionHandlingConfigurer<HttpSecurity> exceptionHandler) {
        exceptionHandler
                .accessDeniedHandler(accessDeniedHandler)
                .authenticationEntryPoint(authenticationEntryPoint);
    }
}
