package org.example.learningsystem.email.service;

import lombok.RequiredArgsConstructor;
import org.example.learningsystem.btp.destinationservice.service.DestinationService;
import org.example.learningsystem.btp.featureflagsservice.service.FeatureFlagsService;
import org.example.learningsystem.email.config.EmailServerProperties;
import org.springframework.stereotype.Service;

/**
 * Feature-flag aware implementation of {@link EmailServerPropertiesResolver}.
 * <p>
 * Chooses between dynamic configuration from {@link DestinationService} (when {@value #FLAG_DESTINATION_SERVICE_ENABLED}
 * flag is enabled) and static configuration from {@link EmailServerProperties}.
 * </p>
 */
@Service
@RequiredArgsConstructor
public class EmailServerPropertiesResolverImpl implements EmailServerPropertiesResolver {

    /**
     * Feature flag name controlling whether to use the destination service.
     */
    private static final String FLAG_DESTINATION_SERVICE_ENABLED = "destination-service-enabled";
    /**
     * Identifier of the SMTP destination in the destination service.
     */
    private static final String SMTP_DESTINATION = "smtp-destination";

    private final DestinationService destinationService;
    private final FeatureFlagsService featureFlagsService;
    private final EmailServerProperties emailServerProperties;

    @Override
    public EmailServerProperties resolve() {
        return featureFlagsService.getBooleanByName(FLAG_DESTINATION_SERVICE_ENABLED)
                ? getPropertiesFromDestinationService()
                : emailServerProperties;
    }

    private EmailServerProperties getPropertiesFromDestinationService() {
        var mailDestination = destinationService.getByName(SMTP_DESTINATION);
        return (EmailServerProperties) mailDestination;
    }

}
