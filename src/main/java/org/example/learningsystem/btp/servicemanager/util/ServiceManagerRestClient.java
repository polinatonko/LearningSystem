package org.example.learningsystem.btp.servicemanager.util;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

import java.net.URI;

@Component
@RequiredArgsConstructor
public class ServiceManagerRestClient {

    private final ServiceManagerAuthenticatedRequestExecutor serviceManagerAuthenticatedRequestExecutor;

    public void delete(URI uri) {
        serviceManagerAuthenticatedRequestExecutor.execute(restClient ->
                restClient.delete()
                        .uri(uri)
                        .retrieve()
                        .toBodilessEntity());
    }

    public <T> T get(URI uri, ParameterizedTypeReference<T> typeReference) {
        return serviceManagerAuthenticatedRequestExecutor.execute(restClient ->
                restClient.get()
                        .uri(uri)
                        .retrieve()
                        .body(typeReference));
    }

    public <T> T post(URI uri, Object body, Class<T> responseType) {
        return serviceManagerAuthenticatedRequestExecutor.execute(restClient ->
                restClient.post()
                        .uri(uri)
                        .body(body)
                        .retrieve()
                        .body(responseType));
    }
}
