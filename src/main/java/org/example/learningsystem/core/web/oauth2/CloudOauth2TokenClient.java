package org.example.learningsystem.core.web.oauth2;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.learningsystem.core.web.exception.InvalidApiResponseException;
import org.example.learningsystem.core.web.dto.Oauth2TokenResponseDto;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import java.util.Map;

import static java.util.Objects.isNull;
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
@CacheConfig(cacheNames = "accessTokens")
@Profile("cloud")
@RequiredArgsConstructor
@Slf4j
public class CloudOauth2TokenClient implements Oauth2TokenClient {

    /**
     * URI template of the token's endpoint of the authorization server.
     */
    private static final String ACCESS_TOKEN_URI_TEMPLATE = "%s/oauth/token";

    private final RestClient restClient;

    @Override
    @Cacheable(key = "#clientId")
    public String get(String url, String clientId, String clientSecret) {
        return getToken(url, clientId, clientSecret);
    }

    @Override
    @CachePut(key = "#clientId")
    public String refresh(String url, String clientId, String clientSecret) {
        return getToken(url, clientId, clientSecret);
    }

    /**
     * Requests an access token from the authorization server using client credentials grant.
     *
     * @param url          the URL of the authorization server
     * @param clientId     the client identifier issued by the authorization server
     * @param clientSecret the client secret associated with the client identifier
     * @return the access token
     */
    private String getToken(String url, String clientId, String clientSecret) {
        var accessTokenUri = ACCESS_TOKEN_URI_TEMPLATE.formatted(url);
        var body = buildCredentials(clientId, clientSecret);

        var accessToken = tryGetToken(accessTokenUri, body);
        log.info("Access token received for client_id = {}", clientId);
        return accessToken;
    }

    /**
     * Constructs the form data payload for client credentials grant request.
     *
     * @param clientId     the client identifier issued by the authorization server
     * @param clientSecret the client secret associated with the client identifier
     * @return a new {@link MultiValueMap} instance with the required OAuth 2.0 parameters
     */
    private MultiValueMap<String, String> buildCredentials(String clientId, String clientSecret) {
        var clientCredentialsMap = Map.of(
                GRANT_TYPE, CLIENT_CREDENTIALS.getValue(),
                CLIENT_ID, clientId,
                CLIENT_SECRET, clientSecret);

        return MultiValueMap.fromSingleValue(clientCredentialsMap);
    }

    /**
     * Executes the token request to the authorization server and processes the response.
     *
     * @param uri  the complete token endpoint URI
     * @param body the form data containing client credentials
     * @return the access token extracted from the response
     */
    private String tryGetToken(String uri, MultiValueMap<String, String> body) {
        var response = restClient.post()
                .uri(uri)
                .body(body)
                .contentType(APPLICATION_FORM_URLENCODED)
                .retrieve()
                .body(Oauth2TokenResponseDto.class);
        validateTokenResponse(response);
        return response.accessToken();
    }

    /**
     * Validates the token response from the authorization server.
     *
     * @param tokenResponse {@link Oauth2TokenResponseDto} instance
     */
    private void validateTokenResponse(Oauth2TokenResponseDto tokenResponse) {
        if (isNull(tokenResponse)) {
            throw new InvalidApiResponseException("Failed to parse the authentication server response");
        }
    }

}
