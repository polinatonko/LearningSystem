package org.example.learningsystem.core.featureflags.service;

import java.util.Base64;

import org.example.learningsystem.core.featureflags.config.FeatureFlagsProperties;
import org.example.learningsystem.core.featureflags.dto.FlagDto;
import org.springframework.context.annotation.Profile;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClient;

import static java.util.Objects.isNull;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

@Service
@Profile("cloud")
public class FeatureFlagsServiceImpl implements FeatureFlagsService {

    private final RestClient restClient;
    private final String authorizationHeader;
    private static final String BOOLEAN_FLAG_TYPE = "BOOLEAN";
    private static final String EVALUATE_FLAG_URI = "%s/api/v2/evaluate";
    private static final String FLAG_SEGMENT = "/%s";

    public FeatureFlagsServiceImpl(FeatureFlagsProperties featureFlagsProperties) {
        var uri = EVALUATE_FLAG_URI.formatted(featureFlagsProperties.getUri());
        restClient = RestClient.create(uri);
        authorizationHeader = buildAuthorizationHeader(featureFlagsProperties.getUsername(),
                featureFlagsProperties.getPassword());
    }

    @Override
    @Retryable(retryFor = HttpStatusCodeException.class, maxAttempts = 5)
    public FlagDto getFeatureFlag(String featureName) {
        return tryGetFlag(featureName);
    }

    @Recover
    public FlagDto recover(HttpStatusCodeException e, String featureName) {
        if (e.getStatusCode() == NOT_FOUND) {
            return null;
        }
        throw e;
    }

    @Override
    public boolean getBooleanFeatureFlag(String featureName) {
        var flagResponse = getFeatureFlag(featureName);

        if (isNull(flagResponse) || flagResponse.httpStatus() != OK.value()) {
            return false;
        } else if (!flagResponse.type().equals(BOOLEAN_FLAG_TYPE)) {
            throw new UnsupportedOperationException("Requested feature flag must be of type %s [featureName=%s]"
                    .formatted(BOOLEAN_FLAG_TYPE, featureName));
        }
        return Boolean.parseBoolean(flagResponse.variation());
    }

    private FlagDto tryGetFlag(String featureName) {
        var path = FLAG_SEGMENT.formatted(featureName);

        return restClient.get()
                .uri(path)
                .header(AUTHORIZATION, authorizationHeader)
                .retrieve()
                .body(FlagDto.class);
    }

    private String buildAuthorizationHeader(String username, String password) {
        var auth = "%s:%s".formatted(username, password);
        var encoder = Base64.getEncoder();
        var encodedAuth = encoder.encodeToString(auth.getBytes());
        return "Basic ".concat(encodedAuth);
    }

}
