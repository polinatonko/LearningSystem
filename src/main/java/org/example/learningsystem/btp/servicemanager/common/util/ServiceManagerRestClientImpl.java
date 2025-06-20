package org.example.learningsystem.btp.servicemanager.common.util;

import lombok.RequiredArgsConstructor;
import org.example.learningsystem.btp.servicemanager.common.dto.PaginatedResponseDto;
import org.springframework.context.annotation.Profile;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.ResolvableType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

import java.net.URI;

/**
 * Implementation of {@link ServiceManagerRestClient} that uses {@link ServiceManagerAuthenticatedRequestExecutor}
 * for handling OAuth 2.0 token flow.
 */
@Component
@Profile("cloud")
@RequiredArgsConstructor
public class ServiceManagerRestClientImpl implements ServiceManagerRestClient {

    private final ServiceManagerAuthenticatedRequestExecutor serviceManagerAuthenticatedRequestExecutor;

    @Override
    public void delete(URI uri) {
        serviceManagerAuthenticatedRequestExecutor.execute(restClient ->
                restClient.delete()
                        .uri(uri)
                        .retrieve()
                        .toBodilessEntity());
    }

    @Override
    public <T> PaginatedResponseDto<T> getPaginated(URI uri, Class<T> itemType) {
        var typeReference = getParameterizedTypeReference(itemType);
        return serviceManagerAuthenticatedRequestExecutor.execute(restClient ->
                restClient.get()
                        .uri(uri)
                        .retrieve()
                        .body(typeReference));
    }

    @Override
    public <T> T post(URI uri, Object body, Class<T> responseType) {
        return serviceManagerAuthenticatedRequestExecutor.execute(restClient ->
                restClient.post()
                        .uri(uri)
                        .body(body)
                        .retrieve()
                        .body(responseType));
    }

    private <T> ParameterizedTypeReference<PaginatedResponseDto<T>> getParameterizedTypeReference(Class<T> itemType) {
        var resolvableType = ResolvableType.forClassWithGenerics(
                PaginatedResponseDto.class,
                itemType
        );
        return ParameterizedTypeReference.forType(resolvableType.getType());
    }
}
