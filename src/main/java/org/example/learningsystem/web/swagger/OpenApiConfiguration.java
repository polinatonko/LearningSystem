package org.example.learningsystem.web.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {

    private final String applicationName;
    private final String applicationVersion;

    public OpenApiConfiguration(
            @Value("${spring.application.name}") String applicationName,
            @Value("${spring.application.version}") String applicationVersion
    ) {
        this.applicationName = applicationName;
        this.applicationVersion = applicationVersion;
    }

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title(applicationName)
                        .version(applicationVersion)
                        .description("API provides a functionality for managing the learning system and allows " +
                                "Students to enroll in variety of Courses using\n" +
                                "virtual coins.")
                );
    }

}
