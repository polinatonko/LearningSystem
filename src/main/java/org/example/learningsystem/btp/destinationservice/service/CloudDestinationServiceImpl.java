package org.example.learningsystem.btp.destinationservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.learningsystem.btp.destinationservice.dto.DestinationDto;
import org.example.learningsystem.btp.destinationservice.client.DestinationServiceClient;
import org.example.learningsystem.btp.xsuaa.util.XsuaaUrlProvider;
import org.example.learningsystem.core.exception.model.LearningManagementSystemException;
import org.example.learningsystem.core.web.oauth2.Oauth2ClientCredentials;
import org.example.learningsystem.btp.destinationservice.config.DestinationServiceProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Supplier;

import static org.springframework.web.client.HttpClientErrorException.NotFound;

/**
 * Cloud {@link DestinationService} implementation that integrates with a remote Destination Service.
 */
@Service
@RequiredArgsConstructor
@Profile("cloud")
@Slf4j
public class CloudDestinationServiceImpl implements DestinationService {

    private final DestinationServiceClient destinationServiceClient;
    private final DestinationServiceProperties properties;
    private final XsuaaUrlProvider xsuaaUrlProvider;

    @Override
    public DestinationDto getByName(String name) {
        return findDestinationOnSubscriberLevel(name)
                .or(() -> findDestinationOnProviderLevel(name))
                .orElseThrow(() -> new LearningManagementSystemException("Destination was not found [name = %s]".formatted(name)));
    }

    private Optional<DestinationDto> findDestinationOnSubscriberLevel(String name) {
        Supplier<Oauth2ClientCredentials> clientCredentialsSupplier = this::buildClientCredentialsForSubscriber;
        return tryToGetTenantDestination(name, clientCredentialsSupplier);
    }

    private Optional<DestinationDto> findDestinationOnProviderLevel(String name) {
        Supplier<Oauth2ClientCredentials> clientCredentialsSupplier = this::buildClientCredentialsForProvider;
        return tryToGetTenantDestination(name, clientCredentialsSupplier);
    }

    private Oauth2ClientCredentials buildClientCredentialsForSubscriber() {
        var tenantTokenUrl = xsuaaUrlProvider.get();
        return new Oauth2ClientCredentials(
                properties.getClientId(),
                properties.getClientSecret(),
                tenantTokenUrl
        );
    }

    private Oauth2ClientCredentials buildClientCredentialsForProvider() {
        return new Oauth2ClientCredentials(
                properties.getClientId(),
                properties.getClientSecret(),
                properties.getTokenUrl()
        );
    }

    private Optional<DestinationDto> tryToGetTenantDestination(String name, Supplier<Oauth2ClientCredentials> clientCredentialsSupplier) {
        try {
            var clientCredentials = clientCredentialsSupplier.get();
            log.info("Trying to get destination [name = {}, url = {}]", name, clientCredentials.tokenUrl());
            var destination = destinationServiceClient.getByName(name, clientCredentials);
            return Optional.ofNullable(destination);
        } catch (NotFound e) {
            log.info("Failed to get destination [name = {}]", name);
            return Optional.empty();
        }
    }
}
