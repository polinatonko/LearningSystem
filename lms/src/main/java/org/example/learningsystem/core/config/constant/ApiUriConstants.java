package org.example.learningsystem.core.config.constant;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class ApiUriConstants {

    public static final String ACTUATOR_ENDPOINTS = "/actuator/**";
    public static final String ACTUATOR_HEALTH_ENDPOINT = "/actuator/health";
    public static final String API_ENDPOINTS = "/api/v1/**";
    public static final String API_SUBSCRIPTIONS_ENDPOINTS = "/api/v1/subscriptions/**";
    public static final String API_DOCS_ENDPOINTS = "/v3/api-docs/**";
    public static final String API_INFO_ENDPOINT = "/api/v1/application-info";
    public static final String SWAGGER_ENDPOINTS = "/swagger-ui/**";
}
