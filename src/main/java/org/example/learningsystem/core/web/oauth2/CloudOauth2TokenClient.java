package org.example.learningsystem.core.web.oauth2;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.learningsystem.core.web.exception.InvalidApiResponseException;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import java.util.Map;

import static java.util.Objects.isNull;
import static org.example.learningsystem.core.cache.constant.CacheConstants.ACCESS_TOKENS_CACHE_NAME;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;
import static org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames.CLIENT_ID;
import static org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames.CLIENT_SECRET;
import static org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames.GRANT_TYPE;
import static org.springframework.security.oauth2.core.AuthorizationGrantType.CLIENT_CREDENTIALS;

/**
 * Cloud {@link Oauth2TokenClient} implementation.
 * <p>
 * Implementation uses caching of tokens using the clientId as a cache key, with automatic refresh capability.
 */
@Service
@CacheConfig(cacheNames = ACCESS_TOKENS_CACHE_NAME)
@Profile("cloud")
@RequiredArgsConstructor
@Slf4j
public class CloudOauth2TokenClient implements Oauth2TokenClient {

    private static final String ACCESS_TOKEN_URI_TEMPLATE = "%s/oauth/token";

    private final RestClient restClient;

    @Override
    @Cacheable(key = "#clientCredentials.clientId")
    public String get(Oauth2ClientCredentials clientCredentials) {
        return getToken(
                clientCredentials.getTokenUrl(),
                clientCredentials.getClientId(),
                clientCredentials.getClientSecret()
        );
    }

    @Override
    @CachePut(key = "#clientCredentials.clientId")
    public String refresh(Oauth2ClientCredentials clientCredentials) {
        return getToken(
                clientCredentials.getTokenUrl(),
                clientCredentials.getClientId(),
                clientCredentials.getClientSecret()
        );
    }

    private String getToken(String url, String clientId, String clientSecret) {
        var accessTokenUri = ACCESS_TOKEN_URI_TEMPLATE.formatted(url);
        var body = buildCredentials(clientId, clientSecret);

        var accessToken = retrieveToken(accessTokenUri, body);
        log.info("Access token received for client_id = {}", clientId);
        return accessToken;
    }

    private MultiValueMap<String, String> buildCredentials(String clientId, String clientSecret) {
        var clientCredentialsMap = Map.of(
                GRANT_TYPE, CLIENT_CREDENTIALS.getValue(),
                CLIENT_ID, clientId,
                CLIENT_SECRET, clientSecret);

        return MultiValueMap.fromSingleValue(clientCredentialsMap);
    }

    private String retrieveToken(String uri, MultiValueMap<String, String> body) {
        var response = restClient.post()
                .uri(uri)
                .body(body)
                .contentType(APPLICATION_FORM_URLENCODED)
                .retrieve()
                .body(Oauth2TokenResponseDto.class);
        validateTokenResponse(response);
        return response.accessToken();
    }

    private void validateTokenResponse(Oauth2TokenResponseDto tokenResponse) {
        if (isNull(tokenResponse)) {
            throw new InvalidApiResponseException("Failed to parse the authentication server response");
        }
    }
}
