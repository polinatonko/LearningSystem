package org.example.learningsystem.multitenancy.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Configuration properties for multitenancy setup.
 */
@Component
@ConfigurationProperties(prefix = "multitenancy")
@Getter
@Setter
public class MultitenancyProperties {

    /**
     * The default tenant identifier.
     */
    private String defaultTenant;

    /**
     * The default database schema name.
     */
    private String defaultSchema;
}
