package org.example.btp.featureflagsservice.config;

import org.example.btp.featureflagsservice.service.FeatureFlagsService;
import org.example.btp.featureflagsservice.service.LocalFeatureFlagsServiceImpl;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

/**
 * {@link EnableAutoConfiguration Autoconfiguration} used for local development and testing without a real connection to
 * the SAP BTP Feature Flags Service.
 * <p>
 * Activates by setting the property {@code btp.services.feature-flags.enabled} to {@code false}.
 *
 * @see CloudFeatureFlagsAutoConfiguration
 */
@AutoConfiguration
@ConditionalOnProperty(value = "btp.services.feature-flags.enabled", havingValue = "false")
public class LocalFeatureFlagsAutoConfiguration {

    @Bean
    public FeatureFlagsService featureFlagsService() {
        return new LocalFeatureFlagsServiceImpl();
    }
}
