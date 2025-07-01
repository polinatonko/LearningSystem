package org.example.learningsystem.core.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

import static org.springframework.web.client.RestClient.Builder;

/**
 * Configuration for the {@link RestClient}.
 */
@Configuration
public class RestClientConfiguration {

    @Bean
    public RestClient restClient(Builder restClientBuilder) {
        return restClientBuilder.build();
    }
}
