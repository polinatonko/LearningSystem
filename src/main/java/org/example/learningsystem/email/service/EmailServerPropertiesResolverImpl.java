package org.example.learningsystem.email.service;

import lombok.RequiredArgsConstructor;
import org.example.learningsystem.btp.destination.converter.DestinationConverter;
import org.example.learningsystem.btp.destination.service.DestinationService;
import org.example.learningsystem.btp.featureflags.service.FeatureFlagsService;
import org.example.learningsystem.email.config.EmailServerProperties;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServerPropertiesResolverImpl implements EmailServerPropertiesResolver {

    private static final String FLAG_DESTINATION_ENABLED = "destination-service-enabled";
    private static final String SMTP_DESTINATION = "smtp-destination";
    private final DestinationConverter destinationConverter;
    private final DestinationService destinationService;
    private final FeatureFlagsService featureFlagsService;
    private final EmailServerProperties emailServerProperties;

    @Override
    public EmailServerProperties resolve() {
        return !featureFlagsService.getBooleanFeatureFlag(FLAG_DESTINATION_ENABLED)
                ? getPropertiesFromDestinationService()
                : emailServerProperties;
    }

    private EmailServerProperties getPropertiesFromDestinationService() {
        var properties = destinationService.getDestinationByName(SMTP_DESTINATION);
        return destinationConverter.tryConvert(properties, EmailServerProperties.class);
    }

}
