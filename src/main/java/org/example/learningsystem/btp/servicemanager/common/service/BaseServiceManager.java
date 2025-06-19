package org.example.learningsystem.btp.servicemanager.common.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.learningsystem.btp.servicemanager.common.dto.PaginatedResponseDto;
import org.example.learningsystem.btp.servicemanager.common.util.ServiceManagerRestClient;
import org.example.learningsystem.btp.servicemanager.common.builder.ServiceManagerUriBuilder;
import org.example.learningsystem.btp.servicemanager.common.validator.ServiceManagerResponseValidator;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.UUID;

/**
 * Provides common operations for interacting with the Service Manager API.
 */
@Service
@Profile("cloud")
@Slf4j
@RequiredArgsConstructor
public class BaseServiceManager {

    protected final ServiceManagerResponseValidator serviceManagerResponseValidator;
    protected final ServiceManagerRestClient serviceManagerRestClient;
    protected final ServiceManagerUriBuilder serviceManagerURIBuilder;

    /**
     * Retrieves a service by searching with a field query.
     *
     * @param field               the name of the field to search by
     * @param value               the value to match against the field
     * @param path                the API endpoint path
     * @param serviceResponseType the class type of the response
     * @param <T>                 the type of the response
     * @return the found service response
     */
    public <T> T getByField(String field, String value, String path, Class<T> serviceResponseType) {
        var uri = serviceManagerURIBuilder.builder(path)
                .fieldQuery(field, value)
                .build();
        return get(uri, serviceResponseType, field, value);
    }

    /**
     * Retrieves a service by searching with a label query.
     *
     * @param label               the name of the label to search by
     * @param value               the value to match against the label
     * @param path                the API endpoint path
     * @param serviceResponseType the class type of the response
     * @param <T>                 the type of the response
     * @return the found service response
     */
    public <T> T getByLabel(String label, String value, String path, Class<T> serviceResponseType) {
        var uri = serviceManagerURIBuilder.builder(path)
                .labelQuery(label, value)
                .build();
        return get(uri, serviceResponseType, label, value);
    }

    /**
     * Retrieves a collection of services.
     *
     * @param uri      the URI of the resource
     * @param itemType the class type of the response item
     * @param <T>      the type of the response item
     * @return {@link PaginatedResponseDto} with service instances
     */
    public <T> PaginatedResponseDto<T> getAll(URI uri, Class<T> itemType) {
        return serviceManagerRestClient.getPaginated(uri, itemType);
    }

    /**
     * Deletes a service resource by id.
     *
     * @param path the API endpoint path
     * @param id   the id of the service
     */
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
