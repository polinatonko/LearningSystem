package org.example.learningsystem.btp.featureflagsservice.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Represents the configuration of the Feature Flags Service.
 */
@ConfigurationProperties(prefix = "sap.security.services.feature-flags")
@Component
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
