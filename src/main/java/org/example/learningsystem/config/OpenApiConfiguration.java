package org.example.learningsystem.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("LearningManagementSystem")
                        .description("API provides a functionality for managing the learning system and allows " +
                                "Students to enroll in variety of Courses using\n" +
                                "virtual coins.")
                        .version("0.1.0"));
    }
}
