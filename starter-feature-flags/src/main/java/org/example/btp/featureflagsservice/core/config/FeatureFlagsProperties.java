package org.example.btp.featureflagsservice.core.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Represents the configuration of the Feature Flags Service.
 */
@ConfigurationProperties(prefix = "btp.services.feature-flags")
@Getter
@Setter
public class FeatureFlagsProperties {

    /**
     * URI of the Feature Flags Service API.
     */
    private String uri;

    /**
     * Username for accessing the Feature Flags Service API.
     */
    private String username;

    /**
     * Password for accessing the Feature Flags Service API.
     */
    private String password;
}
