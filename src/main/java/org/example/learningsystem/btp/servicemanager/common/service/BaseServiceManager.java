package org.example.learningsystem.btp.servicemanager.common.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.learningsystem.btp.servicemanager.common.dto.PaginatedResponseDto;
import org.example.learningsystem.btp.servicemanager.common.util.ServiceManagerRestClient;
import org.example.learningsystem.btp.servicemanager.common.util.ServiceManagerURIBuilder;
import org.example.learningsystem.btp.servicemanager.common.validator.ServiceManagerResponseValidator;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.UUID;

@Service
@Profile("cloud")
@Slf4j
@RequiredArgsConstructor
public class BaseServiceManager {

    protected final ServiceManagerResponseValidator serviceManagerResponseValidator;
    protected final ServiceManagerRestClient serviceManagerRestClient;
    protected final ServiceManagerURIBuilder serviceManagerURIBuilder;

    public <T> T getByField(String field, String value, String path, Class<T> serviceResponseType) {
        var uri = serviceManagerURIBuilder.builder(path)
                .fieldQuery(field, value)
                .build();
        return get(uri, serviceResponseType, field, value);
    }

    public <T> T getByLabel(String label, String value, String path, Class<T> serviceResponseType) {
        var uri = serviceManagerURIBuilder.builder(path)
                .labelQuery(label, value)
                .build();
        return get(uri, serviceResponseType, label, value);
    }

    public <T> PaginatedResponseDto<T> getAll(URI uri, Class<T> itemType) {
        return serviceManagerRestClient.getPaginated(uri, itemType);
    }

    public void deleteById(String path, UUID id) {
        var pathSegments = String.join("/", path, id.toString());
        var uri = serviceManagerURIBuilder.builder(pathSegments)
                .build();
        serviceManagerRestClient.delete(uri);
    }

    private <T> T get(URI uri, Class<T> responseType, String property, String value) {
        var response = getAll(uri, responseType);
        serviceManagerResponseValidator.validatePaginated(response, property, value);

        var items = response.items();
        return items.getFirst();
    }
}
