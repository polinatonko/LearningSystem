package org.example.learningsystem.btp.accesstoken.service;

import lombok.extern.slf4j.Slf4j;
import org.example.learningsystem.btp.destination.dto.AccessTokenResponseDto;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import java.util.Map;

import static java.util.Objects.nonNull;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;
import static org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames.CLIENT_ID;
import static org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames.CLIENT_SECRET;
import static org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames.GRANT_TYPE;
import static org.springframework.security.oauth2.core.AuthorizationGrantType.CLIENT_CREDENTIALS;

@Service
@CacheConfig(cacheNames = "accessTokens")
@Slf4j
public class AccessTokenServiceImpl implements AccessTokenService {

    private static final String ACCESS_TOKEN_URI = "%s/oauth/token";
    private final RestClient restClient;

    public AccessTokenServiceImpl(RestClient.Builder restClientBuilder) {
        restClient = restClientBuilder.build();
    }

    @Override
    @Cacheable(key = "#clientId")
    public String getCacheable(String url, String clientId, String clientSecret) {
        var accessTokenUri = ACCESS_TOKEN_URI.formatted(url);
        var body = buildCredentialsMap(clientId, clientSecret);

        var accessToken = tryGetAccessToken(accessTokenUri, body);
        log.info("Access token received for client_id = {}", clientId);
        return accessToken;
    }

    @Override
    @CacheEvict
    public void evictCache(String clientId) {
    }

    private MultiValueMap<String, String> buildCredentialsMap(String clientId, String clientSecret) {
        var clientCredentialsMap = Map.of(
                GRANT_TYPE, CLIENT_CREDENTIALS.getValue(),
                CLIENT_ID, clientId,
                CLIENT_SECRET, clientSecret);

        return MultiValueMap.fromSingleValue(clientCredentialsMap);
    }

    private String tryGetAccessToken(String uri, MultiValueMap<String, String> body) {
        var response = restClient.post()
                .uri(uri)
                .body(body)
                .contentType(APPLICATION_FORM_URLENCODED)
                .retrieve()
                .body(AccessTokenResponseDto.class);
        return nonNull(response) ? response.accessToken() : null;
    }
}
