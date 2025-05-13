package org.example.learningsystem.core.featureflags.service;

import java.util.Base64;

import org.example.learningsystem.core.featureflags.exception.FeatureFlagTypeMismatchException;
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

    private final FeatureFlagsProperties properties;
    private final String authorizationHeader;
    private final RestClient restClient = RestClient.create();
    private static final Base64.Encoder encoder = Base64.getEncoder();
    private static final String BOOLEAN_FLAG_TYPE = "BOOLEAN";
    private static final String EVALUATE_FLAG_URI = "%s/api/v2/evaluate/%s";
    private static final String BASIC_AUTH_HEADER = "Basic %s";

    public FeatureFlagsServiceImpl(FeatureFlagsProperties featureFlagsProperties) {
        properties = featureFlagsProperties;
        authorizationHeader = buildAuthorizationHeader(properties.getUsername(), properties.getPassword());
    }

    @Override
    @Retryable(retryFor = HttpStatusCodeException.class, maxAttempts = 5)
    public FlagDto getFeatureFlag(String featureName) {
        return tryGetFlag(featureName);
    }

    @Override
    @Retryable(retryFor = HttpStatusCodeException.class, maxAttempts = 5)
    public boolean getBooleanFeatureFlag(String featureName) {
        var flagResponse = getFeatureFlag(featureName);

        if (isNull(flagResponse) || flagResponse.httpStatus() != OK.value()) {
            return false;
        } else if (!flagResponse.type().equals(BOOLEAN_FLAG_TYPE)) {
            throw new FeatureFlagTypeMismatchException(BOOLEAN_FLAG_TYPE, featureName);
        }
        return Boolean.parseBoolean(flagResponse.variation());
    }

    @Recover
    public FlagDto recover(HttpStatusCodeException e, String featureName) {
        if (e.getStatusCode() == NOT_FOUND) {
            return null;
        }
        throw e;
    }

    private FlagDto tryGetFlag(String featureName) {
        var path = EVALUATE_FLAG_URI.formatted(properties.getUri(), featureName);

        return restClient.get()
                .uri(path)
                .header(AUTHORIZATION, authorizationHeader)
                .retrieve()
                .body(FlagDto.class);
    }

    private String buildAuthorizationHeader(String username, String password) {
        var credentials = "%s:%s".formatted(username, password);
        var encodedCredentials = encoder.encodeToString(credentials.getBytes());
        return BASIC_AUTH_HEADER.formatted(encodedCredentials);
    }

}
