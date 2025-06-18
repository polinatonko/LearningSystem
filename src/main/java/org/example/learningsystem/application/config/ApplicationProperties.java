package org.example.learningsystem.application.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Application configuration properties.
 * <p>
 * Contains a list of services that the application depends on, which will be used during the subscription
 * callback process.
 */
@Component
@ConfigurationProperties(prefix = "application")
@Getter
@Setter
public class ApplicationProperties {

    private String name;

    private String uri;

    private List<String> serviceDependencies;
}
