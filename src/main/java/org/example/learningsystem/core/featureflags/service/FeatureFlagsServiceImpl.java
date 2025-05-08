package org.example.learningsystem.core.featureflags.service;

import java.util.Base64;

import org.example.learningsystem.core.featureflags.config.FeatureFlagsProperties;
import org.example.learningsystem.core.featureflags.dto.FlagDto;
import org.example.learningsystem.core.featureflags.exception.FeatureFlagsException;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClient;

import java.net.URI;

@Service
@Profile("cloud")
public class FeatureFlagsServiceImpl implements FeatureFlagsService {
    private final RestClient restClient;
    private final String authHeader;

    public FeatureFlagsServiceImpl(FeatureFlagsProperties featureFlagsProperties) {
        var baseUri = URI.create(featureFlagsProperties.getUri().concat("/api/v2/evaluate"));
        restClient = RestClient.create(baseUri);
        authHeader = createAuthHeader(featureFlagsProperties.getUsername(), featureFlagsProperties.getPassword());
    }

    @Override
    public FlagDto getFeatureFlag(String featureName) {
        try {
            return tryGetFlag(featureName);
        }
        catch (HttpStatusCodeException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                return null;
            }
            throw new FeatureFlagsException(e.getStatusCode(), e.getResponseBodyAsString());
        }
    }

    @Override
    public boolean getBooleanFeatureFlag(String featureName) {
        var flagResponse = getFeatureFlag(featureName);

        if (flagResponse == null || flagResponse.httpStatus() != 200) {
            return false;
        } else if (!flagResponse.type().equals("BOOLEAN")) {
            throw new UnsupportedOperationException("Requested feature flag must be of type BOOLEAN [featureName=%s]"
                    .formatted(featureName));
        }
        return Boolean.parseBoolean(flagResponse.variation());
    }

    private FlagDto tryGetFlag(String featureName) {
        var path = "/%s".formatted(featureName);
        return restClient.get()
                .uri(path)
                .header(HttpHeaders.AUTHORIZATION, authHeader)
                .retrieve()
                .body(FlagDto.class);
    }

    private String createAuthHeader(String username, String password) {
        var auth = "%s:%s".formatted(username, password);
        var encoder = Base64.getEncoder();
        var encodedAuth = encoder.encodeToString(auth.getBytes());
        return "Basic ".concat(encodedAuth);
    }
}
