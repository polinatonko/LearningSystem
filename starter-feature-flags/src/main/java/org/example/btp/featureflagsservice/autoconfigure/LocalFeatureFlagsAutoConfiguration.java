package org.example.btp.featureflagsservice.autoconfigure;

import org.example.btp.featureflagsservice.core.condition.ConditionalOnFeatureFlagsProperties;
import org.example.btp.featureflagsservice.core.service.FeatureFlagsService;
import org.example.btp.featureflagsservice.core.service.LocalFeatureFlagsServiceImpl;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
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
@ConditionalOnFeatureFlagsProperties(name = "enabled", value = "false", matchIfMissing = false)
public class LocalFeatureFlagsAutoConfiguration {

    @Bean
    public FeatureFlagsService featureFlagsService() {
        return new LocalFeatureFlagsServiceImpl();
    }
}
