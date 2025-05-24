package org.example.learningsystem.btp.featureflags.service;

import lombok.RequiredArgsConstructor;
import org.example.learningsystem.btp.featureflags.exception.FeatureFlagTypeMismatchException;
import org.example.learningsystem.btp.featureflags.config.FeatureFlagsProperties;
import org.example.learningsystem.btp.featureflags.dto.FlagDto;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpHeaders;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClient;

import static java.util.Objects.isNull;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

@Service
@RequiredArgsConstructor
@Profile("cloud")
public class FeatureFlagsServiceImpl implements FeatureFlagsService {

    private static final String BOOLEAN_FLAG_TYPE = "BOOLEAN";
    private static final String EVALUATE_FLAG_URI = "%s/api/v2/evaluate/%s";

    private final FeatureFlagsProperties properties;
    private final RestClient restClient;

    @Override
    @Retryable(retryFor = {
            HttpServerErrorException.BadGateway.class,
            HttpServerErrorException.GatewayTimeout.class,
            HttpServerErrorException.ServiceUnavailable.class})
    public FlagDto getByName(String name) {
        return tryGetFlag(name);
    }

    @Override
    public boolean getBooleanByName(String name) {
        var flagResponse = getByName(name);

        if (isNull(flagResponse) || flagResponse.httpStatus() != OK.value()) {
            return false;
        } else if (!flagResponse.type().equals(BOOLEAN_FLAG_TYPE)) {
            throw new FeatureFlagTypeMismatchException(BOOLEAN_FLAG_TYPE, name);
        }
        return Boolean.parseBoolean(flagResponse.variation());
    }

    @Recover
    public FlagDto recover(HttpServerErrorException e, String name) {
        if (e.getStatusCode() == NOT_FOUND) {
            return null;
        }
        throw e;
    }

    private FlagDto tryGetFlag(String name) {
        var path = EVALUATE_FLAG_URI.formatted(properties.getUri(), name);
        var requestHeaders = buildHeaders();

        return restClient.get()
                .uri(path)
                .headers(headers -> headers.addAll(requestHeaders))
                .retrieve()
                .body(FlagDto.class);
    }

    private HttpHeaders buildHeaders() {
        var authorizationHeader = HttpHeaders.encodeBasicAuth(
                properties.getUsername(), properties.getPassword(), null);
        var headers = new HttpHeaders();
        headers.setBasicAuth(authorizationHeader);
        return headers;
    }

}
