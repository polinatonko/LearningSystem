package org.example.learningsystem.core.email.service;

import lombok.RequiredArgsConstructor;
import org.example.learningsystem.btp.destinationservice.service.DestinationService;
import org.example.learningsystem.btp.featureflagsservice.service.FeatureFlagsService;
import org.example.learningsystem.core.email.config.EmailServerProperties;
import org.example.learningsystem.core.email.config.EmailServerPropertiesConfiguration;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServerPropertiesResolverImpl implements EmailServerPropertiesResolver {

    private static final String FLAG_DESTINATION_ENABLED = "destination-service-enabled";
    private static final String SMTP_DESTINATION = "smtp-destination";

    private final DestinationService destinationService;
    private final FeatureFlagsService featureFlagsService;
    private final EmailServerPropertiesConfiguration emailServerProperties;

    @Override
    public EmailServerProperties resolve() {
        return featureFlagsService.getBooleanByName(FLAG_DESTINATION_ENABLED)
                ? getPropertiesFromDestinationService()
                : emailServerProperties;
    }

    private EmailServerProperties getPropertiesFromDestinationService() {
        var mailDestination = destinationService.getByName(SMTP_DESTINATION);
        return (EmailServerProperties) mailDestination;
    }

}
