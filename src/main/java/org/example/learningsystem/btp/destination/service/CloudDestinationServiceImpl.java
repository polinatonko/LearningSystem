package org.example.learningsystem.btp.destination.service;

import lombok.RequiredArgsConstructor;
import org.example.learningsystem.btp.accesstoken.service.AccessTokenService;
import org.example.learningsystem.btp.destination.config.DestinationServiceProperties;
import org.example.learningsystem.btp.destination.dto.DestinationDto;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpHeaders;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import static org.springframework.web.client.HttpClientErrorException.Unauthorized;
import org.springframework.web.client.HttpServerErrorException.BadGateway;
import org.springframework.web.client.HttpServerErrorException.GatewayTimeout;
import org.springframework.web.client.HttpServerErrorException.ServiceUnavailable;

@Service
@RequiredArgsConstructor
@Profile("cloud")
public class CloudDestinationServiceImpl implements DestinationService {

    private static final String DESTINATION_URI = "%s/destination-configuration/v1/instanceDestinations/%s";

    private final AccessTokenService accessTokenService;
    private final DestinationServiceProperties properties;
    private final RestClient restClient;

    @Override
    @Retryable(retryFor = {Unauthorized.class, BadGateway.class, GatewayTimeout.class, ServiceUnavailable.class},
            maxAttempts = 2)
    public DestinationDto getByName(String name) {
        return tryGetDestination(name);
    }

    private DestinationDto tryGetDestination(String destinationName) {
        try {
            var accessToken = accessTokenService.get(
                    properties.getTokenUrl(), properties.getClientId(), properties.getClientSecret());
            var requestHeaders = new HttpHeaders();
            requestHeaders.setBearerAuth(accessToken);

            var uri = DESTINATION_URI.formatted(properties.getUri(), destinationName);

            return restClient.get()
                    .uri(uri)
                    .headers(headers -> headers.addAll(requestHeaders))
                    .retrieve()
                    .body(DestinationDto.class);
        } catch (Unauthorized e) {
            accessTokenService.refresh(properties.getTokenUrl(), properties.getClientId(), properties.getClientSecret());
            throw e;
        }
    }

}
