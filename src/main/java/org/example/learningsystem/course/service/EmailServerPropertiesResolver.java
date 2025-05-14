package org.example.learningsystem.course.service;

import lombok.RequiredArgsConstructor;
import org.example.learningsystem.btp.destination.service.DestinationService;
import org.example.learningsystem.btp.featureflags.service.FeatureFlagsService;
import org.example.learningsystem.email.config.EmailServerProperties;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServerPropertiesResolver {

    private final DestinationService destinationService;
    private final FeatureFlagsService featureFlagsService;
    private final EmailServerProperties emailServerProperties;
    private static final String FLAG_DESTINATION_ENABLED = "destination-service-enabled";

    public EmailServerProperties getEmailServerProperties() {
        return !featureFlagsService.getBooleanFeatureFlag(FLAG_DESTINATION_ENABLED)
                ? destinationService.getEmailServerProperties()
                : emailServerProperties;
    }
}
