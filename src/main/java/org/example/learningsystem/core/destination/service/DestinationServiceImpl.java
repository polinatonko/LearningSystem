package org.example.learningsystem.core.destination.service;

import lombok.RequiredArgsConstructor;
import org.example.learningsystem.core.destination.config.DestinationServiceProperties;
import org.example.learningsystem.core.destination.dto.AccessTokenResponseDto;
import org.example.learningsystem.core.destination.dto.SmtpDestinationResponseDto;
import org.example.learningsystem.email.config.EmailServerProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClient;

import java.util.Map;

import static java.util.Objects.nonNull;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Service
@RequiredArgsConstructor
@Profile("cloud")
public class DestinationServiceImpl implements DestinationService {

    private final DestinationServiceProperties destinationServiceProperties;
    private final RestClient restClient = RestClient.create();
    private static final String ACCESS_TOKEN_URI = "%s/oauth/token";
    private static final String DESTINATION_URI = "%s/destination-configuration/v1/instanceDestinations/%s";
    private static final String SMTP_DESTINATION = "smtp-destination";
    private static final String BEARER_AUTH_HEADER = "Bearer %s";

    @Retryable(retryFor = HttpStatusCodeException.class)
    public EmailServerProperties getEmailServerProperties() {
        var smtpDestination = tryGetDestination(SMTP_DESTINATION, SmtpDestinationResponseDto.class);

        return new EmailServerProperties(smtpDestination.from(), smtpDestination.user(), smtpDestination.password(),
                smtpDestination.host(), smtpDestination.port(), smtpDestination.protocol(),
                smtpDestination.auth(), smtpDestination.startTls());
    }

    private <T> T tryGetDestination(String destinationName, Class<T> responseType) {
        var uri = DESTINATION_URI.formatted(destinationServiceProperties.getUri(), destinationName);
        var accessToken = getAccessToken();
        var authHeader = BEARER_AUTH_HEADER.formatted(accessToken);

        return restClient.get()
                .uri(uri)
                .header(AUTHORIZATION, authHeader)
                .retrieve()
                .body(responseType);
    }

    private String getAccessToken() {
        var uri = ACCESS_TOKEN_URI.formatted(destinationServiceProperties.getUrl());
        var body = buildCredentialsMap(destinationServiceProperties);

        return tryGetAccessToken(uri, body);
    }

    private MultiValueMap<String, String> buildCredentialsMap(DestinationServiceProperties destinationServiceProperties) {
        var clientCredentialsMap = Map.of(
                "grant_type", "client_credentials",
                "client_id", destinationServiceProperties.getClientId(),
                "client_secret", destinationServiceProperties.getClientSecret());
        return MultiValueMap.fromSingleValue(clientCredentialsMap);
    }

    private String tryGetAccessToken(String uri, MultiValueMap<String, String> body) {
        var response = restClient.post()
                .uri(uri)
                .body(body)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .retrieve()
                .body(AccessTokenResponseDto.class);
        return nonNull(response) ? response.accessToken() : null;
    }

}
