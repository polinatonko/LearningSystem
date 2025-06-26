package org.example.btp.featureflagsservice.core.processor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.boot.env.PropertySourceLoader;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;

public class EnvironmentPropertiesPostProcessor implements EnvironmentPostProcessor {

    private static final String PROPERTIES_FILE_NAME = "feature-flags-default.yaml";
    private static final String PROPERTY_SOURCE_NAME = "starter-feature-flags";

    private final PropertySourceLoader propertySourceLoader = new YamlPropertySourceLoader();

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        var resource = new ClassPathResource(PROPERTIES_FILE_NAME);
        var propertySource = tryToLoadYaml(resource);
        environment.getPropertySources().addLast(propertySource);
    }

    private PropertySource<?> tryToLoadYaml(Resource resource) {
        if (!resource.exists()) {
            throw new IllegalStateException("Path %s must exist".formatted(resource));
        }
        try {
            return propertySourceLoader.load(PROPERTY_SOURCE_NAME, resource).getFirst();
        } catch (IOException ex) {
            throw new IllegalStateException("Failed to load yaml configuration from %s".formatted(resource), ex);
        }
    }
}
