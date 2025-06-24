package org.example.btp.featureflagsservice.config;

import org.example.btp.featureflagsservice.service.CloudFeatureFlagsServiceImpl;
import org.example.btp.featureflagsservice.service.FeatureFlagsService;
import org.example.btp.featureflagsservice.validator.CloudFeatureFlagsValidator;
import org.example.btp.featureflagsservice.validator.FeatureFlagsValidator;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestClient;

/**
 * {@link EnableAutoConfiguration Autoconfiguration} for integrating with SAP BTP Feature Flags Service.
 * <p>
 * Active by default and can be disabled by settings the property {@code btp.services.feature-flags.enabled} to {@code false}.
 *
 * @see LocalFeatureFlagsAutoConfiguration
 */
@AutoConfiguration
@ConditionalOnProperty(name = "btp.services.feature-flags.enabled", havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties(FeatureFlagsProperties.class)
public class CloudFeatureFlagsAutoConfiguration {

    @Bean
    public FeatureFlagsValidator featureFlagsValidator() {
        return new CloudFeatureFlagsValidator();
    }

    @Bean
    public FeatureFlagsService featureFlagsService(
            FeatureFlagsProperties featureFlagsProperties, FeatureFlagsValidator featureFlagsValidator, RestClient restClient) {
        return new CloudFeatureFlagsServiceImpl(featureFlagsProperties, featureFlagsValidator, restClient);
    }

    @Bean
    @ConditionalOnMissingBean
    public RestClient restClient(RestClient.Builder restClientBuilder) {
        return restClientBuilder.build();
    }
}
