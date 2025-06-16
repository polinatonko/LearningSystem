package org.example.learningsystem.core.web.swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

import java.util.Collections;
import java.util.function.Predicate;

import static io.swagger.v3.oas.models.security.SecurityScheme.Type.HTTP;
import static java.util.Map.Entry;

/**
 * Configuration for the OpenAPI.
 */
public class OpenApiConfiguration {

    protected static final String BASIC_AUTH_SCHEME = "basicAuth";
    protected static final String BEARER_AUTH_SCHEME = "bearerAuth";

    private final String applicationName;
    private final String applicationVersion;

    public OpenApiConfiguration(String applicationName, String applicationVersion) {
        this.applicationName = applicationName;
        this.applicationVersion = applicationVersion;
    }

    protected OpenAPI openAPI() {
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement()
                        .addList(BASIC_AUTH_SCHEME))
                .components(new Components()
                        .addSecuritySchemes(BASIC_AUTH_SCHEME, basicAuthSecurityScheme()))
                .info(new Info()
                        .title(applicationName)
                        .version(applicationVersion)
                        .description("API provides a functionality for managing the learning system and allows " +
                                "Students to enroll in variety of Courses using virtual coins.")
                );
    }

    protected SecurityScheme bearerAuthSecurityScheme() {
        return new SecurityScheme()
                .type(HTTP)
                .name(BASIC_AUTH_SCHEME)
                .bearerFormat("JWT")
                .scheme("bearer");
    }

    protected SecurityScheme basicAuthSecurityScheme() {
        return new SecurityScheme()
                .type(HTTP)
                .name(BEARER_AUTH_SCHEME)
                .scheme("basic");
    }

    protected void customizePath(OpenAPI openApi, Predicate<Entry<String, PathItem>> predicate, String requirement) {
        var securityRequirement = new SecurityRequirement()
                .addList(requirement);

        openApi.getPaths()
                .entrySet()
                .stream()
                .filter(predicate)
                .forEach(entry -> customizePathEntry(entry, securityRequirement));
    }

    private void customizePathEntry(Entry<String, PathItem> entry, SecurityRequirement securityRequirement) {
        entry.getValue()
                .readOperations()
                .forEach(operation ->
                        operation.setSecurity(Collections.singletonList(securityRequirement))
                );
    }
}
