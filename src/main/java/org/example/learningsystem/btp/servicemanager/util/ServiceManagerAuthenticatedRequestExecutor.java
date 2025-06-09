package org.example.learningsystem.btp.servicemanager.util;

import lombok.RequiredArgsConstructor;
import org.example.learningsystem.btp.servicemanager.config.ServiceManagerProperties;
import org.example.learningsystem.core.web.oauth2.Oauth2TokenClient;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.function.Function;

import static org.springframework.web.client.HttpClientErrorException.Unauthorized;

@Component
@RequiredArgsConstructor
public class ServiceManagerAuthenticatedRequestExecutor {

    private final Oauth2TokenClient oauth2TokenClient;
    private final RestClient restClient;
    private final ServiceManagerProperties serviceManagerProperties;

    public <T> T execute(Function<RestClient, T> operation) {
        try {
            return operation.apply(withCurrentToken());
        } catch (Unauthorized e) {
            return operation.apply(withRefreshedToken());
        }
    }

    private RestClient withCurrentToken() {
        return restClient.mutate()
                .requestInitializer(this::addBearerAuthenticationHeader)
                .build();
    }

    public RestClient withRefreshedToken() {
        oauth2TokenClient.refresh(serviceManagerProperties);
        return withCurrentToken();
    }

    private void addBearerAuthenticationHeader(ClientHttpRequest request) {
        var accessToken = oauth2TokenClient.get(serviceManagerProperties);
        var headers = request.getHeaders();
        headers.setBearerAuth(accessToken);
    }
}
