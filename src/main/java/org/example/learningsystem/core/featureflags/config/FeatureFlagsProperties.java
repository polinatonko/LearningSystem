package org.example.learningsystem.core.featureflags.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "feature-flags")
@Component
@Getter
@Setter
public class FeatureFlagsProperties {
    private String uri;
    private String username;
    private String password;
}
