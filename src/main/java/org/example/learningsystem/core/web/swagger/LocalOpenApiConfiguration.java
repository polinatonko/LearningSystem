package org.example.learningsystem.core.web.swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Map;
import java.util.function.Predicate;

@Configuration
@Profile("!cloud")
public class LocalOpenApiConfiguration extends OpenApiConfiguration {

    private static final String ACTUATOR_HEALTH_PREFIX = "/actuator/health";

    public LocalOpenApiConfiguration(
            @Value("${spring.application.name}") String applicationName,
            @Value("${spring.application.version}") String applicationVersion) {
        super(applicationName, applicationVersion);
    }

    @Bean
    @Override
    public OpenAPI openAPI() {
        return super.openAPI()
                .addSecurityItem(new SecurityRequirement()
                        .addList(BASIC_AUTH_SCHEME))
                .components(new Components()
                        .addSecuritySchemes(BASIC_AUTH_SCHEME, basicAuthSecurityScheme()));
    }

    @Bean
    public OpenApiCustomizer pathSecurityCustomizer() {
        Predicate<Map.Entry<String, PathItem>> predicate = entry ->
                !entry.getKey()
                        .startsWith(ACTUATOR_HEALTH_PREFIX);
        return openApi -> customizePath(openApi, predicate, BASIC_AUTH_SCHEME);
    }
}
