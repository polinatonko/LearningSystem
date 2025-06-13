package org.example.learningsystem.core.web.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("!cloud")
public class LocalOpenApiConfiguration extends OpenApiConfiguration {

    public LocalOpenApiConfiguration(
            @Value("${spring.application.name}") String applicationName,
            @Value("${spring.application.version}") String applicationVersion) {
        super(applicationName, applicationVersion);
    }

    @Override
    public OpenAPI openAPI() {
        return super.openAPI();
    }
}
