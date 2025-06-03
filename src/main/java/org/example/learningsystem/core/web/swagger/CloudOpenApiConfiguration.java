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

import java.util.function.Predicate;

import static java.util.Map.Entry;

@Configuration
@Profile("cloud")
public class CloudOpenApiConfiguration extends OpenApiConfiguration {

    private static final String ACTUATOR_PREFIX = "/actuator";

    public CloudOpenApiConfiguration(
            @Value("${spring.application.name}") String applicationName,
            @Value("${spring.application.version}") String applicationVersion
    ) {
        super(applicationName, applicationVersion);
    }

    @Override
    public OpenAPI openAPI() {
        return super.openAPI()
                .addSecurityItem(new SecurityRequirement()
                        .addList(BEARER_AUTH_SCHEME))
                .components(new Components()
                        .addSecuritySchemes(BASIC_AUTH_SCHEME, basicAuthSecurityScheme())
                        .addSecuritySchemes(BEARER_AUTH_SCHEME, bearerAuthSecurityScheme()));
    }

    @Bean
    public OpenApiCustomizer pathSecurityCustomizer() {
        Predicate<Entry<String, PathItem>> actuatorPredicate = entry ->
                entry.getKey()
                        .startsWith(ACTUATOR_PREFIX);

        return openApi -> {
            customizePath(openApi, actuatorPredicate, BASIC_AUTH_SCHEME);
            customizePath(openApi, actuatorPredicate.negate(), BEARER_AUTH_SCHEME);
        };
    }

}
