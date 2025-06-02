package org.example.learningsystem.btp.destinationservice.service;

import lombok.RequiredArgsConstructor;
import org.example.learningsystem.core.web.oauth2.Oauth2TokenClient;
import org.example.learningsystem.btp.destinationservice.config.DestinationServiceProperties;
import org.example.learningsystem.btp.destinationservice.dto.DestinationDto;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpHeaders;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import static org.springframework.web.client.HttpClientErrorException.Unauthorized;

/**
 * Cloud {@link DestinationService} implementation.
 */
@Service
@RequiredArgsConstructor
@Profile("cloud")
public class CloudDestinationServiceImpl implements DestinationService {

    private static final String DESTINATION_URI_TEMPLATE = "%s/destination-configuration/v1/instanceDestinations/%s";

    private final Oauth2TokenClient oauth2TokenClient;
    private final DestinationServiceProperties properties;
    private final RestClient restClient;

    @Override
    @Retryable(retryFor = Unauthorized.class, maxAttempts = 2)
    public DestinationDto getByName(String name) {
        return tryGetDestination(name);
    }

    /**
     * Tries to get a destination from the Destination Service API by its name.
     *
     * @param destinationName the name of the destination
     * @return {@link DestinationDto} instance
     */
    private DestinationDto tryGetDestination(String destinationName) {
        try {
            var baseUri = properties.getUri();
            var uri = DESTINATION_URI_TEMPLATE.formatted(baseUri, destinationName);

            return restClient.get()
                    .uri(uri)
                    .headers(this::addBearerAuthenticationHeader)
                    .retrieve()
                    .body(DestinationDto.class);
        } catch (Unauthorized e) {
            refreshToken();
            throw e;
        }
    }

    /**
     * Adds Bearer Authentication header filled in with credentials from the {@link DestinationServiceProperties}
     * to the {@link HttpHeaders} instance.
     *
     * @param headers {@link HttpHeaders} instance
     */
    private void addBearerAuthenticationHeader(HttpHeaders headers) {
        var tokenUrl = properties.getTokenUrl();
        var clientId = properties.getClientId();
        var clientSecret = properties.getClientSecret();
        var accessToken = oauth2TokenClient.get(tokenUrl, clientId, clientSecret);
        headers.setBearerAuth(accessToken);
    }

    /**
     * Refreshes token for accessing the Destination service.
     */
    private void refreshToken() {
        var tokenUrl = properties.getTokenUrl();
        var clientId = properties.getClientId();
        var clientSecret = properties.getClientSecret();
        oauth2TokenClient.refresh(tokenUrl, clientId, clientSecret);
    }

}
