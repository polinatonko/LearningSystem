package org.example.btp.featureflagsservice.config;

import org.example.btp.featureflagsservice.service.FeatureFlagsService;
import org.example.btp.featureflagsservice.service.LocalFeatureFlagsServiceImpl;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
@ConditionalOnProperty(value = "btp.services.feature-flags.enabled", havingValue = "false")
public class LocalFeatureFlagsAutoConfiguration {

    @Bean
    public FeatureFlagsService featureFlagsService() {
        return new LocalFeatureFlagsServiceImpl();
    }
}
