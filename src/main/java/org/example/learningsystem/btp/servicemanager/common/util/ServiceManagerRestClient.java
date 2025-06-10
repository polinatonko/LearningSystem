package org.example.learningsystem.btp.servicemanager.common.util;

import org.example.learningsystem.btp.servicemanager.common.dto.PaginatedResponseDto;

import java.net.URI;

public interface ServiceManagerRestClient {

    void delete(URI uri);

    <T> T get(URI uri, Class<T> responseType);

    <T> PaginatedResponseDto<T> getPaginated(URI uri, Class<T> itemType);

    <T> T post(URI uri, Object body, Class<T> responseType);
}
