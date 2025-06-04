package org.example.learningsystem.btp.featureflagsservice.service;

import lombok.RequiredArgsConstructor;
import org.example.learningsystem.btp.featureflagsservice.config.FeatureFlagsProperties;
import org.example.learningsystem.btp.featureflagsservice.dto.FlagDto;
import org.example.learningsystem.btp.featureflagsservice.validator.FeatureFlagsValidator;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

/**
 * Cloud {@link FeatureFlagsService} implementation that integrates with a remote Feature Flags Service.
 */
@Service
@RequiredArgsConstructor
@Profile("cloud")
public class CloudFeatureFlagsServiceImpl implements FeatureFlagsService {

    private static final String BOOLEAN_FLAG_TYPE = "BOOLEAN";
    private static final String EVALUATE_FLAG_URI_TEMPLATE = "%s/api/v2/evaluate/%s";

    private final FeatureFlagsProperties properties;
    private final FeatureFlagsValidator featureFlagsValidator;
    private final RestClient restClient;

    @Override
    public FlagDto getByName(String name) {
        var uri = properties.getUri();
        var path = EVALUATE_FLAG_URI_TEMPLATE.formatted(uri, name);

        return restClient.get()
                .uri(path)
                .headers(this::addBasicAuthHeader)
                .retrieve()
                .body(FlagDto.class);
    }

    @Override
    public boolean getBooleanByName(String name) {
        var flag = getByName(name);
        if (!featureFlagsValidator.isValid(flag, BOOLEAN_FLAG_TYPE)) {
            return false;
        }
        return Boolean.parseBoolean(flag.variation());
    }

    private void addBasicAuthHeader(HttpHeaders headers) {
        var username = properties.getUsername();
        var password = properties.getPassword();
        var authorizationHeader = HttpHeaders.encodeBasicAuth(username, password, null);
        headers.setBasicAuth(authorizationHeader);
    }
}
