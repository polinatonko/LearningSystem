package org.example.learningsystem.btp.destination.service;

import org.example.learningsystem.btp.accesstoken.service.AccessTokenService;
import org.example.learningsystem.btp.destination.config.DestinationServiceProperties;
import org.example.learningsystem.btp.destination.dto.DestinationDto;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpHeaders;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClient;

@Service
@Profile("!cloud")
public class DestinationServiceImpl implements DestinationService {

    private static final String DESTINATION_URI = "%s/destination-configuration/v1/instanceDestinations/%s";
    private static final String SMTP_DESTINATION = "smtp-destination";
    private final AccessTokenService accessTokenService;
    private final DestinationServiceProperties properties;
    private final RestClient restClient;

    public DestinationServiceImpl(AccessTokenService accessTokenService,
                                  DestinationServiceProperties properties,
                                  RestClient.Builder restClientBuilder) {
        this.accessTokenService = accessTokenService;
        this.properties = properties;
        restClient = restClientBuilder.build();
    }

    @Override
    @Retryable(retryFor = {
            HttpClientErrorException.Unauthorized.class,
            HttpServerErrorException.BadGateway.class,
            HttpServerErrorException.GatewayTimeout.class,
            HttpServerErrorException.ServiceUnavailable.class}, maxAttempts = 2)
    public DestinationDto getDestinationByName(String name) {
        return tryGetDestination(name);
    }

    @Recover
    public DestinationDto recoverDestinationDto(HttpClientErrorException.Unauthorized e, String name) {
        accessTokenService.evictCache(properties.getClientId());
        return getDestinationByName(name);
    }

    private DestinationDto tryGetDestination(String destinationName) {
        var accessToken = accessTokenService.getAccessTokenCacheable(
                properties.getUrl(), properties.getClientId(), properties.getClientSecret());
        var requestHeaders = new HttpHeaders();
        requestHeaders.setBearerAuth(accessToken);

        var uri = DESTINATION_URI.formatted(properties.getUri(), destinationName);

        return restClient.get()
                .uri(uri)
                .headers(headers -> headers.addAll(requestHeaders))
                .retrieve()
                .body(DestinationDto.class);
    }

}
