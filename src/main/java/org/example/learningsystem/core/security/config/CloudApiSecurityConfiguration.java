package org.example.learningsystem.core.security.config;

import com.sap.cloud.security.spring.config.XsuaaServiceConfiguration;
import com.sap.cloud.security.spring.token.authentication.XsuaaTokenAuthorizationConverter;
import lombok.RequiredArgsConstructor;
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

import static com.sap.cloud.security.config.ServiceConstants.XSUAA.APP_ID;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@Profile("cloud")
@RequiredArgsConstructor
public class CloudApiSecurityConfiguration {

    private static final int API_FILTER_CHAIN_ORDER = 2;
    private static final String ALL_ENDPOINTS = "/**";
    private static final String API_DOCS_ENDPOINTS = "/v3/api-docs/**";
    private static final String SWAGGER_ENDPOINTS = "/swagger-ui/**";

    private final AccessDeniedHandler accessDeniedHandler;
    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final XsuaaServiceConfiguration xsuaaServiceConfiguration;

    @Bean
    @Order(API_FILTER_CHAIN_ORDER)
    public SecurityFilterChain apiSecurityFilterChain(
            HttpSecurity http, AuthenticationEntryPoint authenticationEntryPoint) throws Exception {
        return http
                .securityMatcher(ALL_ENDPOINTS)
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(this::configureSession)
                .authorizeHttpRequests(this::configureAuthorization)
                .oauth2ResourceServer(this::configureOauth2ResourceServer)
                .exceptionHandling(this::configureExceptionHandling)
                .build();
    }

    @Bean
    public Converter<Jwt, AbstractAuthenticationToken> xsuaaAuthConverter() {
        return new XsuaaTokenAuthorizationConverter(xsuaaServiceConfiguration.getProperty(APP_ID));
    }

    private void configureSession(SessionManagementConfigurer<HttpSecurity> session) {
        session.sessionCreationPolicy(STATELESS);
    }

    private void configureAuthorization(AuthorizeHttpRequestsConfigurer<?>.AuthorizationManagerRequestMatcherRegistry auth) {
        auth
                .requestMatchers(API_DOCS_ENDPOINTS).permitAll()
                .requestMatchers(SWAGGER_ENDPOINTS).permitAll()
                .anyRequest().authenticated();
    }

    private void configureOauth2ResourceServer(OAuth2ResourceServerConfigurer<HttpSecurity> oauth2ResourceServer) {
        oauth2ResourceServer.jwt(jwt -> jwt.jwtAuthenticationConverter(xsuaaAuthConverter()));
    }

    private void configureExceptionHandling(ExceptionHandlingConfigurer<HttpSecurity> exceptionHandler) {
        exceptionHandler
                .accessDeniedHandler(accessDeniedHandler)
                .authenticationEntryPoint(authenticationEntryPoint);
    }
}
