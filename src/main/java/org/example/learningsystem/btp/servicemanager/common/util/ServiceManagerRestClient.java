package org.example.learningsystem.btp.servicemanager.common.util;

import org.example.learningsystem.btp.servicemanager.common.dto.PaginatedResponseDto;

import java.net.URI;

/**
 * Interfaces defining REST client operations for the Service Manager API.
 */
public interface ServiceManagerRestClient {

    /**
     * Performs a DELETE request.
     *
     * @param uri the target URI for the request
     */
    void delete(URI uri);

    /**
     * Performs a GET request with pagination.
     *
     * @param uri      the target URI for the request
     * @param itemType the class type of the response item
     * @param <T>      the type of the response
     * @return {@link PaginatedResponseDto} with requested resource
     */
    <T> PaginatedResponseDto<T> getPaginated(URI uri, Class<T> itemType);

    /**
     * Performs a POST request.
     *
     * @param uri          the target URI for the request
     * @param body         the request body
     * @param responseType the class type of the response
     * @param <T>          the type of the response
     * @return requested resource
     */
    <T> T post(URI uri, Object body, Class<T> responseType);
}
