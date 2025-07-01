package org.example.learningsystem.btp.destinationservice.client;

import lombok.RequiredArgsConstructor;
import org.example.learningsystem.btp.destinationservice.model.DestinationServiceProperties;
import org.example.learningsystem.btp.destinationservice.dto.DestinationDto;
import org.example.learningsystem.core.web.oauth2.Oauth2ClientCredentials;
import org.example.learningsystem.core.web.oauth2.Oauth2TokenClient;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpHeaders;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import static org.springframework.web.client.HttpClientErrorException.Unauthorized;

/**
 * Client for interacting with the Destination Service REST API.
 * <p>
 * Automatically handles OAuth2 token management and refresh operations when unauthorized responses
 * are received.
 */
@Component
@Profile("cloud")
@RequiredArgsConstructor
public class DestinationServiceClient {

    private static final String DESTINATION_URI_TEMPLATE = "%s/destination-configuration/v1/destinations/%s";

    private final DestinationServiceProperties properties;
    private final Oauth2TokenClient oauth2TokenClient;
    private final RestClient restClient;

    /**
     * Retrieves a destination by its name.
     *
     * @param name        the name of the destination
     * @param credentials the OAuth2 client credentials required for authentication
     * @return an {@link DestinationDto} instance
     */
    @Retryable(retryFor = Unauthorized.class, maxAttempts = 2)
    public DestinationDto getByName(String name, Oauth2ClientCredentials credentials) {
        try {
            var baseUri = properties.getUri();
            var uri = DESTINATION_URI_TEMPLATE.formatted(baseUri, name);

            return restClient.get()
                    .uri(uri)
                    .headers(headers -> addBearerAuthenticationHeader(headers, credentials))
                    .retrieve()
                    .body(DestinationDto.class);
        } catch (Unauthorized e) {
            oauth2TokenClient.refresh(credentials);
            throw e;
        }
    }

    private void addBearerAuthenticationHeader(HttpHeaders headers, Oauth2ClientCredentials credentials) {
        var accessToken = oauth2TokenClient.get(credentials);
        headers.setBearerAuth(accessToken);
    }
}
