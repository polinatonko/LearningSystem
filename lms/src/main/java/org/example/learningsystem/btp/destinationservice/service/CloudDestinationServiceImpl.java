package org.example.learningsystem.btp.destinationservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.learningsystem.btp.destinationservice.dto.DestinationDto;
import org.example.learningsystem.core.web.oauth2.Oauth2TokenClient;
import org.example.learningsystem.btp.destinationservice.config.DestinationServiceProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpHeaders;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Optional;

import static org.example.learningsystem.btp.xsuaa.util.XsuaaUtils.getTenantTokenUrl;
import static org.springframework.web.client.HttpClientErrorException.NotFound;
import static org.springframework.web.client.HttpClientErrorException.Unauthorized;

/**
 * Cloud {@link DestinationService} implementation that integrates with a remote Destination Service.
 */
@Service
@RequiredArgsConstructor
@Profile("cloud")
@Slf4j
public class CloudDestinationServiceImpl implements DestinationService {

    private static final String DESTINATION_URI_TEMPLATE = "%s/destination-configuration/v1/destinations/%s";

    private final Oauth2TokenClient oauth2TokenClient;
    private final DestinationServiceProperties properties;
    private final RestClient restClient;

    @Override
    @Retryable(retryFor = Unauthorized.class, maxAttempts = 2)
    public DestinationDto getByName(String name) {
        var tenantDestination = tryToGetTenantDestination(name);
        return tenantDestination.orElseGet(() -> getProviderDestination(name));
    }

    private Optional<DestinationDto> tryToGetTenantDestination(String name) {
        var tenantTokenUrl = getTenantTokenUrl();
        if (tenantTokenUrl.isPresent()) {
            try {
                log.info("Trying to retrieve destination {} for tenant url {}", name, tenantTokenUrl.get());
                var destination = tryToGetDestination(tenantTokenUrl.get(), name);
                return Optional.of(destination);
            } catch (NotFound e) {
                log.info("Destination {} wasn't found for current tenant", name);
            }
        }
        return Optional.empty();
    }

    private DestinationDto getProviderDestination(String name) {
        var providerTokenUrl = properties.getTokenUrl();
        log.info("Retrieving destination {} for provider url {}", name, providerTokenUrl);
        return tryToGetDestination(providerTokenUrl, name);
    }

    private DestinationDto tryToGetDestination(String tokenUrl, String name) {
        try {
            var baseUri = properties.getUri();
            var uri = DESTINATION_URI_TEMPLATE.formatted(baseUri, name);

            return restClient.get()
                    .uri(uri)
                    .headers(headers -> addBearerAuthenticationHeader(headers, tokenUrl))
                    .retrieve()
                    .body(DestinationDto.class);
        } catch (Unauthorized e) {
            refreshToken(tokenUrl);
            throw e;
        }
    }

    private void addBearerAuthenticationHeader(HttpHeaders headers, String tokenUrl) {
        var credentials = properties.getOauth2ClientCredentials()
                .withTokenUrl(tokenUrl);
        var accessToken = oauth2TokenClient.get(credentials);
        headers.setBearerAuth(accessToken);
    }

    private void refreshToken(String tokenUrl) {
        var credentials = properties.getOauth2ClientCredentials()
                .withTokenUrl(tokenUrl);
        oauth2TokenClient.refresh(credentials);
    }
}
